package TaipeiHot.JazzChat.Server;
import TaipeiHot.JazzChat.Server.JdbcMysql.ActiveRecord;

public class Friend extends ActiveRecord {
	static public int totalID = 0;
	public int account_id1,account_id2;
	public String status;
	public String message;
	
	public Friend(){}
	
	public Friend(int id, int account_id1, int account_id2,String status,String message){
		this.id = id;
		this.account_id1 = account_id1;
		this.account_id2 = account_id2;
		this.status = status;
		this.message = message;
	}
	
}