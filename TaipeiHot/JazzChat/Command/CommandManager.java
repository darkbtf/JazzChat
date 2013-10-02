package TaipeiHot.JazzChat.Command;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
	static private Map<String, Command> cmdMap = new HashMap<String, Command>();

	public CommandManager() {
		cmdMap.put("getMsg", new GetMessage());
		cmdMap.put("sendMsg", new SendMessage());
	}

	public void parseCmd(byte[] byteStream) {
		// TODO(darkbtf): parse other cmds
		// if (byteStream[0] == 0)
		cmdMap.get("sendMsg").exec(byteStream);
	}
}
