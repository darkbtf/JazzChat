package TaipeiHot.JazzChat.ServerCommand;

import TaipeiHot.JazzChat.Util;
import TaipeiHot.JazzChat.Server.Account;
import TaipeiHot.JazzChat.Server.Server;
import TaipeiHot.JazzChat.Server.JdbcMysql.ActiveRecord;

public class AccountRegister extends ServerCommand {

	public AccountRegister(Account a){
		super(a);
	}
	@Override
	public Boolean exec() {
		//if(account.connecting)return false;
		account.email = account.getMessage();
		account.password = account.getMessage();
		ActiveRecord[] qry = Server.dbMgr.table("account").where("email = ?",new String[]{account.email});
		if(qry.length!=0){
			account.sendMessage(("Duplicate email").getBytes());
			return Util.errorReport("Duplicate email");
		}
		account.id = ++Account.TotalID;
		account.nickname = account.email;
		account.status = "How are you today?";
		//Server.clientMap.put(account.email, account.id);
        Server.accountMap.put(account.id, account);
        Server.dbMgr.table("account").insert(account);
        if(Account.TotalID+1 != Server.accountMap.size())
        	Util.errorReport("Total id and array size not match");
        System.out.println("Register success! Become User "+account.id);
        account.sendMessage(("Register success! Become User "+account.id).getBytes());
        return true;
	}
}
