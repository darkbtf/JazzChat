package TaipeiHot.JazzChat.ServerCommand;

import java.util.ArrayList;

import TaipeiHot.JazzChat.Util;
import TaipeiHot.JazzChat.Server.Account;
import TaipeiHot.JazzChat.Server.Room;
import TaipeiHot.JazzChat.Server.Server;

public class CreateRoom extends ServerCommand {

	public CreateRoom(Account a){
		super(a);
	}
	@Override
	public Boolean exec() {
		try{
			int n = Integer.parseInt(account.getMessage());
			ArrayList<Account> tmpList = new ArrayList<Account>();
			tmpList.add(account);
			for(int i=0;i<n;i++){
				int id = Integer.parseInt(account.getMessage());
				tmpList.add(Server.accountArray.get(id));
			}
			Room r = new Room(tmpList);
			Server.roomList.add(r);
			account.sendMessage("newRoom".getBytes());
			account.sendMessage(Integer.toString(r.id).getBytes());
			return true;
		}catch (NumberFormatException e){
			return Util.errorReport("Wrong Format parameter in CreateRoom");
		}
	}
}
