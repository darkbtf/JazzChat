package TaipeiHot.JazzChat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketThread extends Function{
    protected ServerSocket server;
    
    public SocketThread() {
        try {
            server = new ServerSocket(port);
 
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
                User NewUser=new User(socket);
                Server.ClientSet.add(NewUser.id);
                String first_msg = NewUser.getMessage();
                System.out.println(first_msg);
            } catch (IOException e) {
                System.out.println("Socket連線有問題 !");
                System.out.println("IOException :" + e.toString());
            }
        }
    }
}
