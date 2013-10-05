package TaipeiHot.JazzChat.Command;

import TaipeiHot.JazzChat.Client.Client;

public class LoginFail implements Command {
	@Override
	public void exec() throws CommandParsingErrorException {
		String cmd = Client.getMessage();
		if (cmd.equals("password"))
			Client.mainWindow.loginFail("password");
		else if (cmd.equals("username"))
			Client.mainWindow.loginFail("username");
		else
			throw new CommandParsingErrorException("LoginFail Failed");

	}
}