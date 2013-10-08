package TaipeiHot.JazzChat.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import TaipeiHot.JazzChat.Parameter;
import TaipeiHot.JazzChat.Server.JdbcMysql.AccountTable;
import TaipeiHot.JazzChat.Server.JdbcMysql.SqlManager;

public class Server {
    static public Map<Integer, Account> accountMap ;
    //static public Map<Integer, Room> roomMap ;
    static public SqlManager dbMgr = new SqlManager(); 
    public Server() {
    	accountMap = new HashMap<Integer, Account>();
    	readDataFromSql();
    }
    public static void main(String args[]) {
    	Server server = new Server();
    	(new SocketThread()).start();
    	server.equals(new Integer(123)); // to remove unused warning = =
    }
    static private void readDataFromSql(){
    	for(Account a:AccountTable.All())
    		Account.totalID = Math.max(Account.totalID,a.id);
    }
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
