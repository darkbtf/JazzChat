package TaipeiHot.JazzChat.ServerCommand;

import TaipeiHot.JazzChat.Server.Account;
import TaipeiHot.JazzChat.Server.Server;

public class AccountLogin extends ServerCommand {

	public AccountLogin(Account a){
		super(a);
	}
	@Override
	public Boolean exec() {
		account.email = account.getMessage();
		account.password = account.getMessage();
		if(Server.clientMap.containsKey(account.email)){
			System.out.println("get index in map = "+Server.clientMap.get(account.email));
			Account tmp = Server.accountArray.get(Server.clientMap.get(account.email));
			if(account.password.equals(tmp.password)){
				account.clone(tmp);
				System.out.println("login success, become User "+account.id);
				account.sendMessage(("login success, become User "+account.id).getBytes());
				//TODO Send success message
				return true;
			}
			else{
				System.out.println("login fail, wrong password");
				account.sendMessage("login fail, wrong password".getBytes());
				//TODO send fail message
				return false;
			}
	    }
		else{
			System.out.println("login fail, wrong email");
			account.sendMessage("login fail, wrong email".getBytes());
			//TODO send fail message
			return false;
		}
	}
}
