package TaipeiHot.JazzChat.Server;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import TaipeiHot.JazzChat.User;

public class Account {
	static public int TotalID = 0;
	public int id;
	protected String email, nickname,status;
	protected String password;
	protected ArrayList<User> Friends;
	private Socket socket;
	public Account(){}
	public Account(String E,String P){
		id=++TotalID;
		email = E;
		password = P;
		nickname = E;
	}
	public Account(Socket _s){
		id=++TotalID;
		socket=_s;
	}
	public void SetNickname(String N){
		nickname = N;
	}
	public String getMessage(){
		try{
			socket.setSoTimeout(15000);
			BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
	        byte[] b = new byte[32];
	        String data = "";
	        Integer length;
	        while ((length = in.read(b)) > 0)// <=0的話就是結束了
	        {
	            data += new String(b, 0, length);
	            for(int i=0;i<length;i++)
	            	System.out.printf("%d",(int)b[i]);
	            //System.out.println(" QQ "+length.toString());
	        }
	        //System.out.println("我取得的值:" + data);
	        in.close();
	        in = null;
	        return data;
		}catch(IOException e){
			System.out.println("getMessage Error");
		}
		return "";
	}
}