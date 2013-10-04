package TaipeiHot.JazzChat.Server;

import TaipeiHot.JazzChat.Server.JdbcMysql.ActiveRecord;

public class RoomAccount extends ActiveRecord{
	//public ArrayList<Integer> accountBelong = new ArrayList<Integer>();
	static public int totalID = 0;
	public int room_id;
	public int account_id;
	public RoomAccount(){}
	public RoomAccount(int id,int room_id, int account_id){
		this.id=id;
		this.room_id = room_id;
		this.account_id = account_id;
	}
}
