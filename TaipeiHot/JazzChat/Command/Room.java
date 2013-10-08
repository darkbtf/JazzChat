package TaipeiHot.JazzChat.Command;

import TaipeiHot.JazzChat.Client.Client;

public class Room implements Command {

	@Override
	public void exec() throws CommandParsingErrorException {
		String cmd = Client.getMessage();
		if (cmd.equals("new")) {
			cmd = Client.getMessage();
			int roomId = Integer.parseInt(Client.getMessage());
			if (cmd.equals("public")) {
				Client.mainWindow.newRoom(roomId);
			} else if (cmd.equals("private")) {
				Client.mainWindow.newRoom(roomId);
			}
		} else if (cmd.equals("name")) {

		} else if (cmd.equals("adduser")) {

		}
	}
}
