package TaipeiHot.JazzChat.Command;

import TaipeiHot.JazzChat.User;
import TaipeiHot.JazzChat.Client.Client;
import TaipeiHot.JazzChat.UI.RoomWindow;

public class MessageCommand implements Command {

	@Override
	public void exec() throws CommandParsingErrorException {
		int roomId = Integer.parseInt(Client.getMessage());
		int userId = Integer.parseInt(Client.getMessage());
		String content = Client.getMessage();
		RoomWindow room = Client.mainWindow.getRoomById(roomId);
		User user = Client.getUserById(userId);
		room.showMessage(user.getNickname(), content);
	}

}
