package TaipeiHot.JazzChat.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import TaipeiHot.JazzChat.Parameter;

public class Server {
    static public Set<Integer> ClientSet;
    static public ArrayList<Account> UserArray ;
    public Server() {
    	ClientSet= new TreeSet<Integer>();// TODO read from SQL
    	UserArray = new ArrayList<Account>();
    	UserArray.add(new Account());
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
	            System.out.println("IOException :" + e.toString());
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
	                Account NewAccount=new Account(socket);
	                Server.ClientSet.add(NewAccount.id);
	                String first_msg = NewAccount.getMessage();
	                System.out.println(first_msg);
	            } catch (IOException e) {
	                System.out.println("Socket連線有問題 !");
	                System.out.println("IOException :" + e.toString());
	            }
	        }
	    }
	}
}