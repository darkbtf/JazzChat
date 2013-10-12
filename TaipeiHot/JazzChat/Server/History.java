package TaipeiHot.JazzChat.Server;
import java.sql.Date;

import TaipeiHot.JazzChat.Server.JdbcMysql.ActiveRecord;

public class History extends ActiveRecord {
	static public int totalID = 0;
	public int room_id,account_id;
	public String message;
	public Date sendTime;
	public History(){}
	
	public History(int id, int room_id, int account_id,String message,Date sendTime){
		this.id = id;
		this.room_id = room_id;
		this.account_id = account_id;
		this.message = message;
		this.sendTime = sendTime;
	}
}