package TaipeiHot.JazzChat.Server;

import TaipeiHot.JazzChat.Server.JdbcMysql.ActiveRecord;

public class Room extends ActiveRecord{
	//public ArrayList<Integer> accountBelong = new ArrayList<Integer>();
	static public int totalID = 0;
	public int count = 0;
	public int user1_id=0,user2_id=0; // QQ
	public Room(){}
	public Room(int id,int count, int user1_id,int user2_id){
		this.id = id;
		this.user1_id = user1_id;
		this.user2_id = user2_id;
		this.count = 2;
	}
	
	public Boolean have(int user_id){
		return user_id == user1_id || user_id == user2_id;
	}
}