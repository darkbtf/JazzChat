package TaipeiHot.JazzChat.Command;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
	static private Map<String, Command> cmdMap = new HashMap<String, Command>();

	public CommandManager() {
		cmdMap.put("login", new Login());
		cmdMap.put("register", new Register());
		cmdMap.put("message", new Message());
		cmdMap.put("friend", new Friend());
		cmdMap.put("room", new Room());
	}

	public void parseCmd(String cmd) throws CommandParsingErrorException {
		cmdMap.get(cmd).exec();
	}
}
