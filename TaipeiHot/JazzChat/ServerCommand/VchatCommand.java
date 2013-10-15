package TaipeiHot.JazzChat.ServerCommand;

import TaipeiHot.JazzChat.Util;
import TaipeiHot.JazzChat.Server.Account;
import TaipeiHot.JazzChat.Server.Server;
import TaipeiHot.JazzChat.Server.JdbcMysql.RoomTable;

public class VchatCommand extends ServerCommand {

	public VchatCommand(Account a){
		super(a);
	}
	@Override
	public Boolean exec() {
		try{
			String cmd = account.getMessage();
			if(cmd.equals("invite"))
				return inviteVchat();
			else if(cmd.equals("accept"))
				return acceptVchat();
			else if(cmd.equals("reject"))
				return rejectVchat();
			return true;
		}catch (NumberFormatException e){
			return Util.errorReport("error in VchatCommand");
		}
	}
	private Boolean inviteVchat(){
		int roomID = Integer.valueOf(account.getMessage());
		for(Account a:RoomTable.accounts(roomID)){
			Account ac = Server.accountMap.get(a.id);
			if(ac.id == account.id)continue;
			if(ac==null)return account.rejectVchat(roomID);
			ac.sendMessage(new String[]{"vchat","invite",roomID+""});
		}
		return true;
	}
	
	private Boolean acceptVchat(){
		int roomID = Integer.valueOf(account.getMessage());
		String []IP=new String [2];
		Account [] as = RoomTable.accounts(roomID);
		if(as.length!=2){
			return Util.errorReport("Do not vchat in public room!");
		}
		for(int i=0;i<as.length;i++){
			Account ac = Server.accountMap.get(as[i].id);
			IP[i]=ac.socket.getInetAddress().toString().substring(1);
			Util.errorReport(IP[i]);
		}
		for(int i=0;i<as.length;i++){
			Account ac = Server.accountMap.get(as[i].id);
			if(ac == null)continue;
			ac.sendMessage(new String[]{"vchat","start",roomID+"",IP[1-i]});
		}
		return true;
	}
	
	private Boolean rejectVchat(){
		int roomID = Integer.valueOf(account.getMessage());
		Account [] as = RoomTable.accounts(roomID);
		if(as.length!=2){
			return Util.errorReport("Do not vchat in public room!");
		}
		for(Account a:as){
			if(a.id == account.id)continue;
			account.rejectVchat(roomID);
		}
		return true;
	}
	
}
