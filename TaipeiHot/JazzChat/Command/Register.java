package TaipeiHot.JazzChat.Command;

import TaipeiHot.JazzChat.Client.Client;

public class Register implements Command {
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
                        System.out.println(cmd);
			Client.mainWindow.registerSuccess();
                        System.out.println("register lala");
			// TODO(darkbtf): implement this
		}
	}
}
