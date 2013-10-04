package TaipeiHot.JazzChat.ServerCommand;

import TaipeiHot.JazzChat.Util;
import TaipeiHot.JazzChat.Server.Account;
import TaipeiHot.JazzChat.Server.Server;

public class AccountRegister extends ServerCommand {

	public AccountRegister(Account a){
		super(a);
	}
	@Override
	public Boolean exec() {
		//if(account.connecting)return false;
		account.email = account.getMessage();
		account.password = account.getMessage();
		if(Server.clientMap.containsKey(account.email)){
			account.sendMessage(("Duplicate email").getBytes());
			return Util.errorReport("Duplicate email");
		}
		account.id = ++Account.TotalID;
		account.nickname = account.email;
		account.status = "How are you today?";
		Server.clientMap.put(account.email, account.id);
        Server.accountArray.add(account);
        if(Account.TotalID+1 != Server.accountArray.size())
        	Util.errorReport("Total id and array size not match");
        System.out.println("Register success! Become User "+account.id);
        account.sendMessage(("Register success! Become User "+account.id).getBytes());
        return true;
	}
}
