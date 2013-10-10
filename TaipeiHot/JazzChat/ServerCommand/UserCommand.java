package TaipeiHot.JazzChat.ServerCommand;

import TaipeiHot.JazzChat.Util;
import TaipeiHot.JazzChat.Server.Account;
import TaipeiHot.JazzChat.Server.Room;
import TaipeiHot.JazzChat.Server.RoomAccount;
import TaipeiHot.JazzChat.Server.Server;
import TaipeiHot.JazzChat.Server.JdbcMysql.RoomAccountTable;
import TaipeiHot.JazzChat.Server.JdbcMysql.RoomTable;

public class UserCommand extends ServerCommand {

	public UserCommand(Account a){
		super(a);
	}
	@Override
	public Boolean exec() {
		try{
			String cmd = account.getMessage();
			if(cmd.equals("visible"))
				return setVisible();
			else if(cmd.equals("status"))
				return setStatus();
			else if(cmd.equals("name"))
				return setNickname();
			else if(cmd.equals("photo"))
				return changePhoto();
			return true;
		}catch (NumberFormatException e){
			return Util.errorReport("error in UserCommand");
		}
	}
	private Boolean setVisible(){
		String cmd = account.getMessage();
		if(cmd.equals("true") && account.visible == 0){
			account.visible = 1;
			account.save();
			account.online();
			return true;
		}
		else if(cmd.equals("false") && account.visible == 1){
			account.visible = 0;
			account.save();
			account.offline();
			return true;
		}
		else return Util.errorReport("user setvisible command fail: "+cmd);
	}
	
	private Boolean setStatus(){
		String status = account.getMessage();
		account.changeStatus(account.getMessage());
		return true;
	}
	
	private Boolean setNickname(){
		String name = account.getMessage();
		account.changeName(account.getMessage());
		return true;
	}
	
	private Boolean changePhoto(){
			//QQ
		return true;
	}
}
