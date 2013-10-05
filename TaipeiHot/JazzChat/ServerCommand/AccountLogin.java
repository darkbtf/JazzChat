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
				account.sendMessage(("login").getBytes());
				account.sendMessage(("success").getBytes());
				account.sendMessage((""+account.id).getBytes());
				//TODO Send success message
				return true;
			}
			else{
				System.out.println("login fail, wrong password");
				account.sendMessage("login".getBytes());
				account.sendMessage("fail".getBytes());
				account.sendMessage("password".getBytes());
				return false;
			}
	    }
		else{
			System.out.println("login fail, wrong username");
			account.sendMessage("login".getBytes());
			account.sendMessage("fail".getBytes());
			account.sendMessage("username".getBytes());
			return false;
		}
	}
}
