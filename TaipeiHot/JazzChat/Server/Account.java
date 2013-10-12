package TaipeiHot.JazzChat.Server;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

import TaipeiHot.JazzChat.Util;
import TaipeiHot.JazzChat.Server.JdbcMysql.AccountTable;
import TaipeiHot.JazzChat.Server.JdbcMysql.ActiveRecord;
import TaipeiHot.JazzChat.Server.JdbcMysql.FriendTable;
import TaipeiHot.JazzChat.ServerCommand.ServerCommandManager;

public class Account extends ActiveRecord {
	//User Data
	static public int totalID = 0;
	public String email, nickname,status;
	public String password;
	public short visible;

	//Communicate
	public Socket socket;
	private Deque<Byte> bufferInput = new LinkedList<Byte>();
	private Queue<String> messages = new LinkedList<String>();
	private BufferedInputStream in = null;
	private OutputStream out = null;
	Thread getMessageToBuffer, startThread;
	public Boolean connecting=true;
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
	
	public Account(int id,String email, String password,String nickname,String status,short visible){
		this.id = id;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.status = status;
		this.visible = visible;
	}
	public boolean isonline(){
		if(visible==0)return false;
		if(Server.accountMap.get(id)==null)return false;
		return true;
	}
	public void login(){
		for(Account c:friends())
			showFriend(c);
		for(Friend f:FriendTable.where("account_id2=? && status=?",new String[]{id+"","waiting"})){
			Account c = AccountTable.find(f.another(id));
			sendMessage(new String[]{"friend","add",c.id+"",c.nickname,f.message});
		}
		online();
	}
	public void save(){
		AccountTable.update(this);
	}
	public void showFriend(Account c){
		sendMessage(new String[]{"friend","show",c.id+"",c.nickname,c.status,c.isonline()?"true":"false"});
	}
	public void online(){
		if(this.visible==0)
			return;
		for(Account c: friends()){
			Util.errorReport(c.nickname);
			Account tar = Server.accountMap.get(c.id);
			if(tar !=null)
				tar.sendMessage(new String[]{"friend","online",this.id+""});
		}
	}
	
	public void offline(){
		if(this.visible==0)
			return;
		for(Account c: friends()){
			Account tar = Server.accountMap.get(c.id);
			if(tar !=null)
				tar.sendMessage(new String[]{"friend","offline",this.id+""});
		}
	}
	public void changeStatus(String status){
		this.status = status;
		this.save();
		for(Account c: friends()){
			Account tar = Server.accountMap.get(c.id);
			if(tar !=null)
				tar.sendMessage(new String[]{"friend","status",this.id+"",status});
		}
	}
	
	public void changeName(String nickname){
		this.nickname = nickname;
		this.save();
		for(Account c: friends()){
			Account tar = Server.accountMap.get(c.id);
			if(tar !=null)
				tar.sendMessage(new String[]{"friend","name",this.id+"",nickname});
		}
	}
	private Account[] friends(){
		Friend friends[]=FriendTable.where("(account_id1=? || account_id2=?) && status=?",new String[]{id+"",id+"","accept"});
		Account ret[] = new Account[friends.length];
		for( int i=0;i<friends.length;i++)
			ret[i] = AccountTable.find(friends[i].another(id));
		return ret;
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
					offline();
					connecting=false;
					Server.accountMap.put(id,null);
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
				if(!connecting)return "";
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		Util.errorReport("get message from "+nickname+": "+messages.peek());
		return messages.poll();
	}
	
	public void clone(Account tmp){ // copy data
		this.id       = tmp.id;
		this.password = tmp.password;
		this.nickname = tmp.nickname;
		this.status   = tmp.status;
		this.visible  = tmp.visible;
		//this.roomMap  = tmp.roomMap;
	}
	private Boolean trylogin(){// NOTICE: cmdMgr's read function can only run one command in one time
		String cmd=getMessage();
		return cmdMgr.parseCmd(cmd);
	}
	
	public Boolean sendMessage(String[] messages){
		for(String str: messages)
			if(!sendMessage(str.getBytes()))
				return false;
		return true;
	}
	
	public Boolean sendMessage(byte[] byteStream){
		byte[] length = Util.intToByteArray(byteStream.length);
		try {
			out.write(length);
			out.write(byteStream);
			out.flush();
		} catch (IOException e) {
			return Util.errorReport("sendMessage to "+nickname+" fail");
		}
		return true;
	}
}