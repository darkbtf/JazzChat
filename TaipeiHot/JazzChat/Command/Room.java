package TaipeiHot.JazzChat.Command;

import TaipeiHot.JazzChat.Client.Client;

public class Room implements Command {

	@Override
	public void exec() throws CommandParsingErrorException {
		String cmd = Client.getMessage();
		if (cmd.equals("new")) {
			cmd = Client.getMessage();
			if (cmd.equals("public")) {

			} else if (cmd.equals("private")) {

			}
		} else if (cmd.equals("name")) {

		} else if (cmd.equals("adduser")) {

		}
	}
}
