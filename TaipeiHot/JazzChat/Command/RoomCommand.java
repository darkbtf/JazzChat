package TaipeiHot.JazzChat.Command;

import TaipeiHot.JazzChat.User;
import TaipeiHot.JazzChat.Client.Client;
import TaipeiHot.JazzChat.UI.RoomWindow;

public class RoomCommand implements Command {

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
			int roomId = Integer.parseInt(Client.getMessage());
			String userName = Client.getMessage();
			RoomWindow room = Client.mainWindow.getRoomById(roomId);
			room.addUser(new User(userName, ""));
		} else if (cmd.equals("adduser")) {
			int roomId = Integer.parseInt(Client.getMessage());
			String userName = Client.getMessage();
			RoomWindow room = Client.mainWindow.getRoomById(roomId);
			room.addUser(new User(userName, ""));
		}
	}
}
