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
		account.sendMessage(("register").getBytes());
		if(qry.length!=0){
			account.sendMessage("failed".getBytes());
			account.sendMessage("username".getBytes());
			return Util.errorReport("Duplicate UserName");
		}
		account.id = 0;
		account.nickname = account.email;
		account.status = "I love Joy Wang ^^";
		account.visible = 1;
		account.photo = "default";
        Server.accountMap.put(account.id, account);
        AccountTable.insert(account);
        account.sendMessage("success".getBytes());
        account.sendMessage((""+account.id).getBytes());
        Util.errorReport("Register success! Become User "+account.id);
        return true;
	}
}
