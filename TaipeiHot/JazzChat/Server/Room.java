package TaipeiHot.JazzChat.Server;

import java.util.ArrayList;

public class Room {
	public ArrayList<Account> accountBelong = new ArrayList<Account>();
	static public int totalID = 0;
	public int id;
	public Room(){}
	public Room(ArrayList<Account> accounts){
		id = ++Room.totalID;
		accountBelong.addAll(accounts);
		System.out.println("new Room "+id+", have accounts:");
		for(Account a : accountBelong){
			System.out.print(a.email + " ");
			a.roomMap.put(id, this);
		}
		System.out.println("");
	}
}
