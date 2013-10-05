package TaipeiHot.JazzChat.Command;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
	static private Map<String, Command> cmdMap = new HashMap<String, Command>();

	public CommandManager() {
		cmdMap.put("login", new Login());
	}

	public void parseCmd(String cmd) throws CommandParsingErrorException {
		cmdMap.get(cmd).exec();
	}
}
