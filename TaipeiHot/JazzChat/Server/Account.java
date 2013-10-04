package TaipeiHot.JazzChat.Server;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import TaipeiHot.JazzChat.User;
import TaipeiHot.JazzChat.Util;
import TaipeiHot.JazzChat.Server.JdbcMysql.ActiveRecord;
import TaipeiHot.JazzChat.ServerCommand.ServerCommandManager;

public class Account extends ActiveRecord {
	//User Data
	static public int TotalID = 0;
	public String email, nickname,status;
	public String password;
	protected ArrayList<User> friends = new ArrayList<User>();
	public Map<Integer, Room> roomMap = new HashMap<Integer, Room>();
	//Communicate
	public Socket socket;
	private Deque<Byte> bufferInput = new LinkedList<Byte>();
	private Queue<String> messages = new LinkedList<String>();
	private BufferedInputStream in = null;
	private OutputStream out = null;
	Thread getMessageToBuffer, startThread;
	public Boolean connecting;
	private ServerCommandManager cmdMgr = null;
	
	public Account(){}
	public Account(Socket _s){
		socket=_s;
		try {
			in = new BufferedInputStream(socket.getInputStream());
			out = new BufferedOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		cmdMgr = new ServerCommandManager(this);
		openInputThread();
	}
	
	public Account(int id,String email, String password,String nickname,String status){
		this.id = id;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.status = status;
		this.roomMap = new HashMap<Integer, Room>(); // TODO database
		this.friends = new ArrayList<User>(); //TODO database
	}
	
	public void SetNickname(String N){
		nickname = N;
	}
	
	private void openInputThread(){
		getMessageToBuffer=new Thread(new Runnable(){
			@Override
			public void run() {
		        byte[] b = new byte[1024];
				Integer length;
				try{
					while ((length = in.read(b)) > 0){
						for(int i=0;i<length;i++)
							bufferInput.add(new Byte(b[i]));
						try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
			        }
				}catch(IOException e){
					connecting=false;
					System.out.println("getMessage Error");
				}
			}
		});
		startThread = new Thread(new Runnable(){
			@Override
			public void run(){
				while(!trylogin());
				connecting=true;
				while(connecting){
					String cmd=getMessage();
					cmdMgr.parseCmd(cmd);
				}
			}
		});
		getMessageToBuffer.start();
		startThread.start();
	}
	
	public String getMessage(){
		while(messages.isEmpty()){
			while(!Util.parseByte(bufferInput, messages)){
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return messages.poll();
	}
	
	public void clone(Account tmp){ // copy data
		this.nickname = tmp.nickname;
		this.friends  = tmp.friends;
		this.status   = tmp.status;
		this.id       = tmp.id;
		this.password = tmp.password;
		this.roomMap  = tmp.roomMap;
	}
	private Boolean trylogin(){// NOTICE: cmdMgr's read function can only run one command in one time
		String cmd=getMessage();
		return cmdMgr.parseCmd(cmd);
	}
	public Boolean sendMessage(byte[] byteStream){
		byte[] length = Util.intToByteArray(byteStream.length);
		try {
			out.write(length);
			out.write(byteStream);
			out.flush();
		} catch (IOException e) {
			return Util.errorReport("IOException in Account "+id+" sendMessage");
		}
		return true;
	}
}