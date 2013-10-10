package TaipeiHot.JazzChat.Command;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
	static private Map<String, Command> cmdMap = new HashMap<String, Command>();

	public CommandManager() {
		cmdMap.put("login", new LoginCommand());
		cmdMap.put("register", new RegisterCommand());
		cmdMap.put("message", new MessageCommand());
		cmdMap.put("friend", new FriendCommand());
		cmdMap.put("room", new RoomCommand());
	}

	public void parseCmd(String cmd) throws CommandParsingErrorException {
		System.out.println("cmd = " + cmd);
		cmdMap.get(cmd).exec();
	}
}
