package TaipeiHot.JazzChat.ServerCommand;

import TaipeiHot.JazzChat.Util;
import TaipeiHot.JazzChat.Server.Account;
import TaipeiHot.JazzChat.Server.Room;
import TaipeiHot.JazzChat.Server.RoomAccount;
import TaipeiHot.JazzChat.Server.Server;
import TaipeiHot.JazzChat.Server.JdbcMysql.RoomAccountTable;
import TaipeiHot.JazzChat.Server.JdbcMysql.RoomTable;

public class RoomCommand extends ServerCommand {

	public RoomCommand(Account a){
		super(a);
	}
	@Override
	public Boolean exec() {
		try{
			String cmd = account.getMessage();
			Server.accountMap.get(account.id).sendMessage("room".getBytes());
			if(cmd.equals("new"))
				return createRoom();
			else if(cmd.equals("adduser"))
				return addUserToRoom();
			else if(cmd.equals("name"))
				return renameRoom();
			return true;
		}catch (NumberFormatException e){
			return Util.errorReport("Wrong Format parameter in CreateRoom");
		}
	}
	private Boolean createRoom(){
		String roomkind = account.getMessage();
		Server.accountMap.get(account.id).sendMessage("new".getBytes());
		Server.accountMap.get(account.id).sendMessage(roomkind.getBytes());
		if(roomkind.equals("private")){
			int user_id = Integer.valueOf(account.getMessage());
			RoomAccount[] qry = RoomAccountTable.where("account_id="+account.id);
			Room r = null;
			for(RoomAccount ra : qry){
				Room[] rs = RoomTable.where("id="+ra.room_id+" && count=2");
				for(int i=0;i<rs.length;i++)
					if(rs[i].have(user_id))
						r=rs[i];
				break;
			}
			if(r==null){
				r = new Room(0,2,account.id,user_id,"");
				RoomTable.insert(r);
				RoomAccountTable.insert(new RoomAccount(0,r.id,account.id));
				RoomAccountTable.insert(new RoomAccount(0,r.id,user_id));
			}
			Server.accountMap.get(account.id).sendMessage((r.id+"").getBytes());
			return true;
		}
		else if(roomkind.equals("public")){
			String roomName = account.getMessage();
			Room r = new Room(0,1,account.id,0,roomName);
			RoomTable.insert(r);
			RoomAccountTable.insert(new RoomAccount(0,r.id,account.id));
			Server.accountMap.get(account.id).sendMessage((r.id+"").getBytes());
			return true;
		}
		else return Util.errorReport("room new command fail: "+roomkind);
	}
	
	private Boolean addUserToRoom(){
		int room_id = Integer.valueOf(account.getMessage());
		int user_id = Integer.valueOf(account.getMessage());
		if(RoomAccountTable.where("account_id="+user_id+" && room_id="+room_id).length!=0)
			return true;// User has already in room
		for(RoomAccount a:RoomAccountTable.where("room_id="+room_id)){
			Account ac=Server.accountMap.get(a.account_id);
			ac.sendMessage("adduser".getBytes());
			ac.sendMessage((""+room_id).getBytes());
			ac.sendMessage((""+user_id).getBytes()); // TODO: not id only
		}
		RoomAccountTable.insert(new RoomAccount(0,room_id,user_id));
		return true;
	}
	
	private Boolean renameRoom(){
		int room_id = Integer.valueOf(account.getMessage());
		String name = account.getMessage();
		Room r = RoomTable.find(room_id);
		r.name = name;
		//TODO Table.save
		return true;
	}
}
