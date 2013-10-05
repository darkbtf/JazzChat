package TaipeiHot.JazzChat.ServerCommand;

import java.util.HashMap;
import java.util.Map;



import TaipeiHot.JazzChat.Util;
import TaipeiHot.JazzChat.Server.Account;

public class ServerCommandManager {
	private Map<String, ServerCommand> cmdMap = new HashMap<String, ServerCommand>();
	private Account account;
	public ServerCommandManager(Account a) {
		account=a;
		cmdMap.put("register", new AccountRegister(account));
		cmdMap.put("login", new AccountLogin(account));
		cmdMap.put("newRoom", new CreateRoom(account));
		cmdMap.put("send_to_room", new SendToRoom(account));
	}

	public Boolean parseCmd(String cmd) {
		ServerCommand cmdKind = cmdMap.get(cmd); 
		if(cmdKind == null){
			account.sendMessage(cmd.getBytes());
			return Util.errorReport("Undefined Command: "+cmd);
		}
		return cmdKind.exec();
	}
}