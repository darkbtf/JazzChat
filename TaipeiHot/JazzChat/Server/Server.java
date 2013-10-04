package TaipeiHot.JazzChat.Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import TaipeiHot.JazzChat.Parameter;
import TaipeiHot.JazzChat.Util;
import TaipeiHot.JazzChat.Server.JdbcMysql.Table;
import TaipeiHot.JazzChat.Server.JdbcMysql.UserTable;

public class Server {
    static public Map<String, Integer> clientMap;
    static public ArrayList<Account> accountArray ;
    static private Table dbMgr = new Table(); 
    //static public Map<ArrayList, Room> roomMap; 
    public Server() {
    	accountArray = new ArrayList<Account>();
    	accountArray.add(new Account());
    	readUserData();
    	initClientMap();
    }
    public static void main(String args[]) {
    	Server server = new Server();
    	(new SocketThread()).start();
    	server.equals(new Integer(123)); // to remove unused warning = =
    	Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
            	writeUserData();
            }
        }, "Shutdown-thread"));
    }
    
    static private void readUserData(){// TODO read from SQL
    	try {
    		dbMgr = new UserTable();
    		DataInputStream in = new DataInputStream(new FileInputStream("userData.dat"));
    		BufferedReader br = new BufferedReader(new InputStreamReader(in));
    		int n= Integer.valueOf(br.readLine());
    		for(int i=0;i < n; i++) {
    			String s = br.readLine();
    			Account a = new Account();
    			a.email = s;
    			a.password = s;
    			accountArray.add(a);
    		}
			br.close();
		} catch (FileNotFoundException e) {
			Util.errorReport("In read user data, userData not found");
		}
    	catch(Exception e){Util.errorReport("In read user data, Exception");}
    }
    
    static public void writeUserData(){
    	try {
    		System.out.print("writing user data\n");
    		DataOutputStream in = new DataOutputStream(new FileOutputStream("userData.dat"));
    		BufferedWriter br = new BufferedWriter(new OutputStreamWriter(in));
    		br.write((accountArray.size()-1)+"\n");
    		for(int i=1;i<accountArray.size();i++){
    			Account a = accountArray.get(i);
    			br.write(a.email+"\n"+a.password+"\n");
    		}
			br.close();
		} catch (FileNotFoundException e) {
			Util.errorReport("In write user data, userData not found");
		}
    	catch(Exception e){Util.errorReport("In write user data, Exception");}
    	
    }
    private void initClientMap(){
    	clientMap= new HashMap<String, Integer>();
    	for(Account a:accountArray)
    		clientMap.put(a.email, a.id);
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
