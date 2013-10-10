package TaipeiHot.JazzChat.Command;

import TaipeiHot.JazzChat.Client.Client;

public class RegisterCommand implements Command {
	@Override
	public void exec() {
		String cmd = Client.getMessage();
		if (cmd.equals("failed")) {
			cmd = Client.getMessage();
			if (cmd.equals("username")) {
				Client.mainWindow.registerFail("username");
			}
		} else if (cmd.equals("success")) {
			cmd = Client.getMessage();
			Client.mainWindow.registerSuccess();
		}
	}
}
