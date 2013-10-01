package TaipeiHot.JazzChat;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.io.BufferedOutputStream;
 
public class Client extends Function {
    private String address = "140.112.18.198";
    private int port = 8765;
    private Socket client;
    
    public Client() {
        client = new Socket();
        InetSocketAddress isa = new InetSocketAddress(this.address, this.port);
        try {
            client.connect(isa, 10000);
            for (int i = 0; i < 100; ++i)
                sendMessageToServer("洨");
            client.close();
        } catch (java.io.IOException e) {
            System.out.println("GG!");
            System.out.println("IOException :" + e.toString());
        }
    }
 
    public static void main(String args[]) {
        new Client();
    }

    public boolean sendMessageToServer(final String msg) {
        try {
            BufferedOutputStream out = new BufferedOutputStream(client
                .getOutputStream());
            System.out.println(msg);
            out.write(msg.getBytes());
            out.flush();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}