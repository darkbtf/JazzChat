package TaipeiHot.JazzChat.ServerCommand;

import TaipeiHot.JazzChat.Util;
import TaipeiHot.JazzChat.Server.Account;
import TaipeiHot.JazzChat.Server.Friend;
import TaipeiHot.JazzChat.Server.Server;
import TaipeiHot.JazzChat.Server.JdbcMysql.AccountTable;
import TaipeiHot.JazzChat.Server.JdbcMysql.FriendTable;

public class FriendCommand extends ServerCommand {

	public FriendCommand(Account a){
		super(a);
	}
	@Override
	public Boolean exec() {
		String cmd = account.getMessage();
		if(cmd.equals("add"))
			return addFriend();
		else if(cmd.equals("accept"))
			return acceptFriend();
		else if(cmd.equals("reject"))
			return rejectFriend();
		else return Util.errorReport("Unknown command in Friend");
	}
	
	private Boolean addFriend(){
		String name = account.getMessage();
		String text = account.getMessage();
		Account[] qry = AccountTable.where("email=?",new String[]{name});
		if(qry.length==0){
			account.sendMessage(new String[]{"friend","response",name,"fail","Account not exist."});
			return true;
		}
		Account ac=qry[0];
		if(FriendTable.where("account_id1="+ac.id+" && account_id2="+account.id).length != 0 || FriendTable.where("account_id2="+ac.id+" && account_id1="+account.id).length != 0){
			account.sendMessage(new String[]{"friend","response",name,"fail","You are friends or wating for response."});
			return true;
		}
		if(Server.accountMap.get(ac.id) != null)
			Server.accountMap.get(ac.id).sendMessage(new String[]{"friend", "add", ""+account.id,account.email,text});
		FriendTable.insert(new Friend(0,account.id,ac.id,"waiting",text));
		return true;
	}
	private Boolean acceptFriend(){
		int acID = Integer.valueOf(account.getMessage());
		Account ac = AccountTable.find(acID);
		Friend friend=FriendTable.where("account_id1="+acID+" && account_id2="+account.id)[0];
		friend.status = "accept";
		FriendTable.update(friend);
		if(Server.accountMap.get(acID)!=null){
			Server.accountMap.get(acID).sendMessage(new String[]{"friend","response",account.email,"accept"});
			Server.accountMap.get(acID).sendMessage(new String[]{"friend","show",account.id+"", account.email,account.status,account.isonline()?"true":"false"});
		}
		account.sendMessage(new String[]{"friend","show",acID+"", ac.email, ac.status,ac.isonline()?"true":"false"});	
		return true;
	}
	
	private Boolean rejectFriend(){
		int acID = Integer.valueOf(account.getMessage());
		Account ac = Server.accountMap.get(acID);
		if(ac!=null)
			ac.sendMessage(new String[]{"friend","response",account.email,"reject"});
		FriendTable.delete("account_id1="+acID+" && account_id2="+account.id);
		return true;
	}
}
