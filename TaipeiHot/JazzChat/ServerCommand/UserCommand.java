package TaipeiHot.JazzChat.ServerCommand;

import TaipeiHot.JazzChat.Util;
import TaipeiHot.JazzChat.Server.Account;

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
			account.offline();
			account.visible = 0;
			account.save();
			return true;
		}
		else return Util.errorReport("user setvisible command fail: "+cmd);
	}
	
	private Boolean setStatus(){
		account.changeStatus(account.getMessage());
		return true;
	}
	
	private Boolean setNickname(){
		account.changeName(account.getMessage());
		return true;
	}
	
	private Boolean changePhoto(){
		String cmd = account.getMessage();
		if(cmd.equals("finish")){
			account.photo = account.id+"";
			account.save();
			account.sendMessage(new String[]{"user","photo","change",account.photoUrl()});
			account.changePhoto();
		}
		else if(cmd.equals("upload")){
			String filepath = account.getMessage();
			account.sendMessage(new String[]{"user","photo","upload", filepath, "profile_pics/"+account.id});
		}
		else return Util.errorReport("photo command error");
		return true;
	}
}
