package TaipeiHot.JazzChat.Server;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

import TaipeiHot.JazzChat.Parameter;
import TaipeiHot.JazzChat.User;

public class Account extends Thread{
	//User Data
	static public int TotalID = 0;
	public int id;
	public String email, nickname,status;
	protected String password;
	protected ArrayList<User> Friends = new ArrayList<User>();
	
	//Communicate
	public Socket socket;
	private Deque<Byte> BufferInput = new LinkedList<Byte>();
	private Queue<String> Messages = new LinkedList<String>();
	BufferedInputStream in;
	Thread GetMessageToBuffer, startThread;
	
	
	public Account(){}
	public Account(Socket _s){
		socket=_s;
		//socket.setSoTimeout(15000);
		try {
			in = new BufferedInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		openInputThread();
	}
	public void SetNickname(String N){
		nickname = N;
	}
	private Boolean ParseByte(){
		if(BufferInput.size() < Parameter.bytesForLength)return false;
		Byte[] tmp=new Byte[Parameter.bytesForLength];
		int length=0;
		for(int i=0;i<Parameter.bytesForLength;i++){
			tmp[i] = BufferInput.pollFirst();
			length = (length << 1) + tmp[i].intValue(); 
		}
		if(BufferInput.size() < length){
			for(int i=Parameter.bytesForLength-1;i>=0;i--)
				BufferInput.addFirst(tmp[i]);
			return false;
		}
		String data = "";
		byte[] b = new byte[length];
		for(int i=0;i<length;i++)
			b[i]=BufferInput.pollFirst();
        data += new String(b, 0, length);
        System.out.println(data);
        Messages.add(data);
        return true;
	}
	private void openInputThread(){
		System.out.println("new Thread");
		GetMessageToBuffer=new Thread(new Runnable(){
			@Override
			public void run() {
		        byte[] b = new byte[1024];
				Integer length;
				try{
					while ((length = in.read(b)) > 0){
						for(int i=0;i<length;i++)
							BufferInput.add(new Byte(b[i]));
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
			        }
				}catch(IOException e){
					System.out.println("getMessage Error");
				}
			}
		});
		startThread = new Thread(new Runnable(){
			@Override
			public void run(){
				while(!trylogin());
				while(true){
		        	String msg = getMessage();
		        	if(msg != "")
		        		System.out.print(msg);
		        	try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		GetMessageToBuffer.start();
		startThread.start();
	}
	
	public String getMessage(){
		while(Messages.isEmpty()){
			while(!ParseByte()){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return Messages.poll();
	}
	
	public void run(){
		
	}
	
	private void clone(Account tmp){ // copy all data except socket
		this.nickname = tmp.nickname;
		this.Friends  = tmp.Friends;
		this.status   = tmp.status;
		this.id       = tmp.id;
	}
	private Boolean trylogin(){// TODO commandMgr
		String cmd=getMessage();
		if(cmd.equals("register")){
			email = getMessage();
			password = getMessage();
			id = ++Account.TotalID;
			nickname = email;
			status = "How are you today?";
			Server.clientMap.put(email, id);
	        Server.userArray.add(this);
	        if(Account.TotalID+1 != Server.userArray.size())
	        	System.out.println("Total id and array size not match");
	        System.out.println("Register success! Become User "+id);
	        return true;
		}
		else if(cmd.equals("login")){
			email = getMessage();
			password = getMessage();
			if(Server.clientMap.containsKey(email)){
				System.out.println("get index in map = "+Server.clientMap.get(email));
				Account tmp = Server.userArray.get(Server.clientMap.get(email));
				if(password.equals(tmp.password)){
					clone(tmp);
					System.out.println("login success, become User "+this.id);
					//TODO Send success message
					return true;
				}
				else{
					System.out.println("login fail, wrong password");
					//TODO send fail message
					return false;
				}
		    }
			else{
				System.out.println("login fail, wrong email");
				//TODO send fail message
				return false;
			}
		}
		else System.out.println("In login, what are you talking QQ");
		return false;
	}
}