package TaipeiHot.JazzChat.ServerCommand;


import TaipeiHot.JazzChat.Util;
import TaipeiHot.JazzChat.Server.Account;
import TaipeiHot.JazzChat.Server.Server;
import TaipeiHot.JazzChat.Server.JdbcMysql.AccountTable;

public class AccountLogin extends ServerCommand {

	public AccountLogin(Account a){
		super(a);
	}
	@Override
	public Boolean exec() {
		account.email = account.getMessage();
		account.password = account.getMessage();
		Util.errorReport(account.email);
		Util.errorReport(account.password);
		Account[] qry = AccountTable.where("email = ?",new String[]{account.email});
		if(qry.length!=0){
			Account tmp = qry[0];
			if(account.password.equals(tmp.password)){
				account.clone(tmp);
				Server.accountMap.put(account.id, account);
				System.out.println("login success, become User "+account.id);
				account.sendMessage(new String[]{"login","success",""+account.id,account.nickname,account.status});
				return true;
			}
			else{
				Util.errorReport("login fail, wrong password of account "+account.email);
				account.sendMessage(new String[]{"login","fail","password"});
				return false;
			}
	    }
		else{
			Util.errorReport("login fail, wrong username:"+account.email);
			account.sendMessage(new String[]{"login","fail","username"});
			return false;
		}
	}
}
