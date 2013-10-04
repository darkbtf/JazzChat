package TaipeiHot.JazzChat.Server;

import java.util.ArrayList;

public class Room {
	public ArrayList<Integer> accountBelong = new ArrayList<Integer>();
	static public int totalID = 0;
	public int id;
	public Room(){}
	public Room(ArrayList<Integer> accounts){
		id = ++Room.totalID;
		accountBelong.addAll(accounts);
		System.out.println("new Room "+id+", have accounts:");
		for(Integer a : accountBelong){
			System.out.print(Server.accountMap.get(a).email + " ");
			Server.accountMap.get(a).roomMap.put(id, this);
		}
		System.out.println("");
	}
}
