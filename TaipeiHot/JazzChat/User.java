package TaipeiHot.JazzChat;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;

public class User {
	static public int TotalID = 0;
	public int id;
	public String email, nickname;
	private String encrypted_password;
	private Socket socket;
	public User(){}
	public User(String E,String P){
		id=++TotalID;
		email = E;
		encrypted_password = P;
		nickname = E;
	}
	public User(Socket _s){
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