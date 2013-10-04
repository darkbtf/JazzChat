package TaipeiHot.JazzChat.ServerCommand;

import java.util.ArrayList;

import TaipeiHot.JazzChat.Util;
import TaipeiHot.JazzChat.Server.Account;
import TaipeiHot.JazzChat.Server.Room;
import TaipeiHot.JazzChat.Server.RoomAccount;
import TaipeiHot.JazzChat.Server.Server;
import TaipeiHot.JazzChat.Server.JdbcMysql.RoomAccountTable;
import TaipeiHot.JazzChat.Server.JdbcMysql.RoomTable;

public class CreateRoom extends ServerCommand {

	public CreateRoom(Account a){
		super(a);
	}
	@Override
	public Boolean exec() {
		try{
			int n = Integer.parseInt(account.getMessage());
			ArrayList<Integer> tmpList = new ArrayList<Integer>();
			tmpList.add(account.id);
			for(int i=0;i<n;i++){
				int id = Integer.parseInt(account.getMessage());
				tmpList.add(id);
			}
			Room r = null;
			RoomAccount[] qry = RoomAccountTable.where("account_id="+account.id);
			if(tmpList.size()==2)
				for(RoomAccount ra : qry){
					Room[] rs = RoomTable.where("id=? && count=2", new String[]{ra.room_id+""});
					for(int i=0;i<rs.length;i++)
						if(rs[i].have(tmpList.get(1)))
							r=rs[i];
					break;
				}
			if(r==null){
				r = new Room(0,tmpList.size(),tmpList.get(0),tmpList.get(1));
				RoomTable.insert(r);
				for(Integer i : tmpList)
					RoomAccountTable.insert(new RoomAccount(0,r.id,i));
			}
			for(RoomAccount a : RoomAccountTable.where("room_id="+r.id))
				Server.accountMap.get(a.account_id).sendMessage(("Room "+r.id).getBytes());
			return true;
		}catch (NumberFormatException e){
			return Util.errorReport("Wrong Format parameter in CreateRoom");
		}
	}
}
