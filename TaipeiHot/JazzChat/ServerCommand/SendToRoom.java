package TaipeiHot.JazzChat.ServerCommand;

import TaipeiHot.JazzChat.Util;
import TaipeiHot.JazzChat.Server.Account;
import TaipeiHot.JazzChat.Server.Room;
import TaipeiHot.JazzChat.Server.RoomAccount;
import TaipeiHot.JazzChat.Server.Server;
import TaipeiHot.JazzChat.Server.JdbcMysql.RoomAccountTable;
import TaipeiHot.JazzChat.Server.JdbcMysql.RoomTable;

public class SendToRoom extends ServerCommand {

	public SendToRoom(Account a){
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
		for(RoomAccount a : RoomAccountTable.where("room_id="+room_id))
			Server.accountMap.get(a.account_id).sendMessage(msg.getBytes());
		return true;
	}
}