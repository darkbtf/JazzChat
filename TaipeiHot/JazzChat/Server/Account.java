package TaipeiHot.JazzChat.Server;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

import TaipeiHot.JazzChat.Function;
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
	private Deque<Byte> bufferInput = new LinkedList<Byte>();
	private Queue<String> messages = new LinkedList<String>();
	private BufferedInputStream in = null;
	private OutputStream out = null;
	Thread getMessageToBuffer, startThread;
	private Boolean connecting;
	
	public Account(){}
	public Account(Socket _s){
		socket=_s;
		//socket.setSoTimeout(15000);
		try {
			in = new BufferedInputStream(socket.getInputStream());
			out = new BufferedOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		openInputThread();
	}
	public void SetNickname(String N){
		nickname = N;
	}
	
	private void openInputThread(){
		System.out.println("new Thread");
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
							Thread.sleep(500);
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
		getMessageToBuffer.start();
		startThread.start();
	}
	
	public String getMessage(){
		while(messages.isEmpty()){
			while(!Function.parseByte(bufferInput, messages)){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return messages.poll();
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
			if(Server.clientMap.containsKey(email)){
				System.out.println("Duplicate email");
				sendMessage(("Duplicate email").getBytes());
				return false;
			}
			id = ++Account.TotalID;
			nickname = email;
			status = "How are you today?";
			Server.clientMap.put(email, id);
	        Server.userArray.add(this);
	        if(Account.TotalID+1 != Server.userArray.size())
	        	System.out.println("Total id and array size not match");
	        System.out.println("Register success! Become User "+id);
	        sendMessage(("Register success! Become User "+id).getBytes());
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
					sendMessage(("login success, become User "+this.id).getBytes());
					//TODO Send success message
					return true;
				}
				else{
					System.out.println("login fail, wrong password");
					sendMessage("login fail, wrong password".getBytes());
					//TODO send fail message
					return false;
				}
		    }
			else{
				System.out.println("login fail, wrong email");
				sendMessage("login fail, wrong email".getBytes());
				//TODO send fail message
				return false;
			}
		}
		else {
			System.out.println("In login, what are you talking QQ");
			sendMessage("In login, what are you talking QQ".getBytes());
			//TODO send fail message
		}
		return false;
	}
	public Boolean sendMessage(byte[] byteStream){
		byte[] length = Function.intToByteArray(byteStream.length);
		try {
			out.write(length);
			out.write(byteStream);
			out.flush();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
}