package TaipeiHot.JazzChat.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import TaipeiHot.JazzChat.Parameter;
import TaipeiHot.JazzChat.Util;
import TaipeiHot.JazzChat.Server.JdbcMysql.ActiveRecord;
import TaipeiHot.JazzChat.Server.JdbcMysql.SqlManager;

public class Server {
    //static public Map<String, Integer> clientMap;
    static public Map<Integer, Account> accountMap ;
    public static SqlManager dbMgr = new SqlManager(); 
    //static public Map<ArrayList, Room> roomMap; 
    public Server() {
    	accountMap = new HashMap<Integer, Account>();
    	readUserData();
    	//initClientMap();
    }
    public static void main(String args[]) {
    	Server server = new Server();
    	(new SocketThread()).start();
    	server.equals(new Integer(123)); // to remove unused warning = =
    }
    
    static private void readUserData(){
    	try {
    		for(ActiveRecord a:dbMgr.table("account").All()){
    			accountMap.put(a.id,(Account)a);
    			Account.TotalID = Math.max(Account.TotalID,a.id);
    		}
		}
    	catch(Exception e){Util.errorReport("In read user data, Exception");}
    }
    /*private void initClientMap(){
    	clientMap= new HashMap<String, Integer>();
    	for(Account a:accountArray)
    		clientMap.put(a.email, a.id);
    }*/
	static class SocketThread extends Thread{
	    protected ServerSocket server;
	    
	    public SocketThread() {
	        try {
	            server = new ServerSocket(Parameter.port);
	        } catch (java.io.IOException e) {
	            System.out.println("Socket啟動有問題 !");
	        }
	    }
	 
	    public void run() {
	        System.out.println("伺服器已啟動!");
	        while (true) {
	        	Socket socket = null;
	            try {
	                synchronized (server) {
	                	socket = server.accept();
	                }
	                System.out.println("取得連線: InetAddress = "
	                        + socket.getInetAddress());
	                new Account(socket);
	            } catch (IOException e) {
	                System.out.println("Socket連線有問題 !");
	            }
	        }
	    }
	}
}
