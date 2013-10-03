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
			Room r = null;
			if(tmpList.size()==2)
				for(Room tmp : account.roomMap.values())
					if(tmp.accountBelong.equals(tmpList)){
						r=tmp;
						break;
					}
			if(r==null)r = new Room(tmpList);
			for(Account a : r.accountBelong)
				a.sendMessage(("Room "+r.id).getBytes());
			return true;
		}catch (NumberFormatException e){
			return Util.errorReport("Wrong Format parameter in CreateRoom");
		}
	}
}
