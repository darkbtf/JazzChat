package TaipeiHot.JazzChat.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import TaipeiHot.JazzChat.Parameter;
import TaipeiHot.JazzChat.Server.JdbcMysql.AccountTable;
import TaipeiHot.JazzChat.Server.JdbcMysql.FileTable;
import TaipeiHot.JazzChat.Server.JdbcMysql.FriendTable;
import TaipeiHot.JazzChat.Server.JdbcMysql.HistoryTable;
import TaipeiHot.JazzChat.Server.JdbcMysql.RoomAccountTable;
import TaipeiHot.JazzChat.Server.JdbcMysql.RoomTable;
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
    	new Server();
    	(new SocketThread()).start();
    }
    static private void readDataFromSql(){//TODO save max id to sql? or some much good method
    	for(Account a:AccountTable.All())
    		Account.totalID = Math.max(Account.totalID,a.id);
    	for(Friend a:FriendTable.All())
    		Friend.totalID = Math.max(Friend.totalID,a.id);
    	for(Room a:RoomTable.All())
    		Room.totalID = Math.max(Room.totalID,a.id);
    	for(RoomAccount a:RoomAccountTable.All())
    		RoomAccount.totalID = Math.max(RoomAccount.totalID,a.id);
    	for(FileChat a:FileTable.All())
    		FileChat.totalID = Math.max(FileChat.totalID,a.id);
    	for(History a:HistoryTable.All())
    		History.totalID = Math.max(History.totalID,a.id);
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
