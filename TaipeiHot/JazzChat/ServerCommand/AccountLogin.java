package TaipeiHot.JazzChat.ServerCommand;


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
		Account[] qry = AccountTable.where("email = ?",new String[]{account.email});
		if(qry.length!=0){
			Account tmp = qry[0];
			if(account.password.equals(tmp.password)){
				account.clone(tmp);
				Server.accountMap.put(account.id, account);
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
