package TaipeiHot.JazzChat.Server;

import TaipeiHot.JazzChat.Server.JdbcMysql.ActiveRecord;

public class Room extends ActiveRecord{
	//public ArrayList<Integer> accountBelong = new ArrayList<Integer>();
	static public int totalID = 0;
	public String name;
	public Room(){}
	public Room(int id, String name){
		this.id = id;
		this.name = name;
	}
}
