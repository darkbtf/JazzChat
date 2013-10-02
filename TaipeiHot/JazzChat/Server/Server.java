package TaipeiHot.JazzChat.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import TaipeiHot.JazzChat.Parameter;

public class Server {
    static public Map<String, Integer> clientMap;
    static public ArrayList<Account> userArray ;
    public Server() {
    	clientMap= new TreeMap<String, Integer>();// TODO read from SQL
    	userArray = new ArrayList<Account>();
    	userArray.add(new Account());
    }
    public static void main(String args[]) {
    	Server server = new Server();
    	(new SocketThread()).start();
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
	        Socket socket;
	        System.out.println("伺服器已啟動!");
	        while (true) {
	        	socket = null;
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
