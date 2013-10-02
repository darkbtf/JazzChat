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
	protected String email, nickname,status;
	protected String password;
	protected ArrayList<User> Friends = new ArrayList<User>();
	
	//Communicate
	private Socket socket;
	private Deque<Byte> BufferInput = new LinkedList<Byte>();
	private Queue<String> Messages = new LinkedList<String>();
	BufferedInputStream in;
	Thread GetMessageToBuffer;
	
	
	public Account(){}
	public Account(String E,String P){
		id=++TotalID;
		email = E;
		password = P;
		nickname = E;
	}
	public Account(Socket _s) throws IOException{
		id=++TotalID;
		socket=_s;
		//socket.setSoTimeout(15000);
		in = new BufferedInputStream(socket.getInputStream());
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
        Messages.add(data);
		System.out.println(length+" "+data);
        return true;
	}
	private void openInputThread(){
		System.out.print("new Thread");
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
		GetMessageToBuffer.start();
	}
	
	public String getMessage(){
		if(Messages.isEmpty())return "";
		return Messages.poll();
	}
	
	public void run(){
		while(true){
			while(ParseByte());
        	String first_msg = getMessage();
        	if(first_msg != "")
        		System.out.print(first_msg);
        	try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}