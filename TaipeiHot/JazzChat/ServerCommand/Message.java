package TaipeiHot.JazzChat.ServerCommand;

import java.sql.Timestamp;
import java.util.Date;

import TaipeiHot.JazzChat.Util;
import TaipeiHot.JazzChat.Server.Account;
import TaipeiHot.JazzChat.Server.History;
import TaipeiHot.JazzChat.Server.Room;
import TaipeiHot.JazzChat.Server.RoomAccount;
import TaipeiHot.JazzChat.Server.Server;
import TaipeiHot.JazzChat.Server.JdbcMysql.HistoryTable;
import TaipeiHot.JazzChat.Server.JdbcMysql.RoomAccountTable;
import TaipeiHot.JazzChat.Server.JdbcMysql.RoomTable;

public class Message extends ServerCommand {

	public Message(Account a){
		super(a);
	}
	@Override
	public Boolean exec() {
		try{
			int roomID = Integer.parseInt(account.getMessage());
			return send(roomID);
		}catch (NumberFormatException e){
			return Util.errorReport("Wrong Format parameter in SendToRoom");
		}
	}
	private Boolean send(int room_id){
		Room r = RoomTable.find(room_id);
		if(r == null)return Util.errorReport("Room id "+room_id+" not found");
		String msg = account.getMessage();
		for(RoomAccount a : RoomAccountTable.where("room_id="+room_id)){
			Account ac = Server.accountMap.get(a.account_id);
			if(ac!=null)
				ac.sendMessage(new String[]{"message",""+r.id,""+account.id,account.nickname,msg});
		}
		HistoryTable.insert(new History(0,room_id,account.id,msg,new Timestamp((new Date()).getTime())));
		return true;
	}
}
