package TaipeiHot.JazzChat.ServerCommand;

import TaipeiHot.JazzChat.Server.Account;

public class ServerGetMessage extends ServerCommand {

	public ServerGetMessage(Account a){
		super(a);
	}
	@Override
	public Boolean exec() {
		return false;
		// showMessage(parameter.toString());
	}
}
