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
			if(cmd.equals("new"))
				return createRoom();
			else if(cmd.equals("adduser"))
				return addUserToRoom();
			else if(cmd.equals("name"))
				return renameRoom();
			return true;
		}catch (NumberFormatException e){
			return Util.errorReport("error in RoomCommand");
		}
	}
	private Boolean createRoom(){
		String roomkind = account.getMessage();
		if(roomkind.equals("private")){
			int user_id = Integer.valueOf(account.getMessage());
			RoomAccount[] qry = RoomAccountTable.where("account_id="+account.id);
			Room r = null;
			for(RoomAccount ra : qry){
				Account[] acc = RoomTable.accounts(ra.room_id);
				if(acc.length!=2)continue;
				if(acc[0].id==user_id || acc[1].id==user_id){
					r=RoomTable.find(ra.room_id);
					break;
				}
			}
			if(r==null){
				r = new Room(0,"");
				RoomTable.insert(r);
				RoomAccountTable.insert(new RoomAccount(0,r.id,account.id));
				RoomAccountTable.insert(new RoomAccount(0,r.id,user_id));
			}
			account.sendMessage(new String[]{"room","new","private",r.id+""});
			return true;
		}
		else if(roomkind.equals("public")){
			String roomName = account.getMessage();
			Room r = new Room(0,roomName);
			
			RoomTable.insert(r);
			RoomAccountTable.insert(new RoomAccount(0,r.id,account.id));

			account.sendMessage(new String[]{"room","new","public",r.id+""});
			return true;
		}
		else return Util.errorReport("room new command fail: "+roomkind);
	}
	
	private Boolean addUserToRoom(){
		int room_id = Integer.valueOf(account.getMessage());
		int user_id = Integer.valueOf(account.getMessage());
		if(RoomAccountTable.where("account_id="+user_id+" && room_id="+room_id).length!=0)
			return true;// User has already in room
		for(RoomAccount a:RoomAccountTable.where("room_id="+room_id))
			Server.accountMap.get(a).sendMessage(new String[]{"room","adduser",""+room_id,""+user_id});// TODO: not id only
		RoomAccountTable.insert(new RoomAccount(0,room_id,user_id));
		return true;
	}
	
	private Boolean renameRoom(){
		int room_id = Integer.valueOf(account.getMessage());
		String name = account.getMessage();
		Room r = RoomTable.find(room_id);
		r.name = name;
		r.save();
		return true;
	}
}
