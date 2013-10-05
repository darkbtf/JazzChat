package TaipeiHot.JazzChat.ServerCommand;

import TaipeiHot.JazzChat.Util;
import TaipeiHot.JazzChat.Server.Account;
import TaipeiHot.JazzChat.Server.Server;
import TaipeiHot.JazzChat.Server.JdbcMysql.AccountTable;

public class AccountRegister extends ServerCommand {

	public AccountRegister(Account a){
		super(a);
	}
	@Override
	public Boolean exec() {
		//if(account.connecting)return false;
		account.email = account.getMessage();
		account.password = account.getMessage();
		Account[] qry = AccountTable.where("email = ?",new String[]{account.email});
		if(qry.length!=0){
			account.sendMessage(("Duplicate email").getBytes());
			return Util.errorReport("Duplicate email");
		}
		account.id = 0;
		account.nickname = account.email;
		account.status = "How are you today?";
		//Server.clientMap.put(account.email, account.id);
        Server.accountMap.put(account.id, account);
        AccountTable.insert(account);
        System.out.println("Register success! Become User "+account.id);
        account.sendMessage(("Register success! Become User "+account.id).getBytes());
        return true;
	}
}
