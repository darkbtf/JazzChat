package TaipeiHot.JazzChat;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;


public class Server extends Function {
    static public Set<Integer> ClientSet;
    static public ArrayList<User> UserArray ;
    public Server() {
    	ClientSet= new TreeSet<Integer>();// TODO read from SQL
    	UserArray = new ArrayList<User>();
    	UserArray.add(new User());
    }
    public static void main(String args[]) {
    	Server server = new Server();
        (new SocketThread()).start();
    }
}
