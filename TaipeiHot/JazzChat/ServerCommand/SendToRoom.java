package TaipeiHot.JazzChat.ServerCommand;

import TaipeiHot.JazzChat.Util;
import TaipeiHot.JazzChat.Server.Account;
import TaipeiHot.JazzChat.Server.Room;
import TaipeiHot.JazzChat.Server.Server;

public class SendToRoom extends ServerCommand {

	public SendToRoom(Account a){
		super(a);
	}
	@Override
	public Boolean exec() {
		try{
			int roomID = Integer.parseInt(account.getMessage());
			Room r = account.roomMap.get(new Integer(roomID));
			if(r == null)return Util.errorReport("Room id "+roomID+" not found");
			String msg = account.getMessage();
			for(Integer a : r.accountBelong)
				Server.accountArray.get(a).sendMessage(msg.getBytes());
			return true;
		}catch (NumberFormatException e){
			return Util.errorReport("Wrong Format parameter in SendToRoom");
		}
	}
}
