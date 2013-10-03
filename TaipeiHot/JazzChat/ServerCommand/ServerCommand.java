package TaipeiHot.JazzChat.ServerCommand;

import TaipeiHot.JazzChat.Server.Account;

public class ServerCommand {
	protected Account account;
	public ServerCommand(Account a){
		account=a;
	}
	public Boolean exec(){return false;}
}
