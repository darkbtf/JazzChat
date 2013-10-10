package TaipeiHot.JazzChat.Command;

import TaipeiHot.JazzChat.User;
import TaipeiHot.JazzChat.Client.Client;

public class LoginCommand implements Command {
	@Override
	public void exec() throws CommandParsingErrorException {
		String cmd = Client.getMessage();
		if (cmd.equals("fail")) {
			cmd = Client.getMessage();
			if (cmd.equals("password"))
				Client.mainWindow.loginFail("password");
			else if (cmd.equals("username"))
				Client.mainWindow.loginFail("username");
			else
				throw new CommandParsingErrorException("Login Failed");
		} else if (cmd.equals("success")) {
			int id = Integer.parseInt(Client.getMessage());
			String myName = Client.getMessage();
			String status = Client.getMessage();
			Client.user = new User(myName, status);
			Client.user.id = id;
			Client.mainWindow.loginSuccess();
		}
	}
}