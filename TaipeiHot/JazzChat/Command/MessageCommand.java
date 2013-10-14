package TaipeiHot.JazzChat.Command;

import TaipeiHot.JazzChat.Util;
import TaipeiHot.JazzChat.Client.Client;
import TaipeiHot.JazzChat.UI.RoomWindow;

public class MessageCommand implements Command {

	@Override
	public void exec() throws CommandParsingErrorException {
		int roomId = Integer.parseInt(Client.getMessage());
		int userId = Integer.parseInt(Client.getMessage());
		String userName = Client.getMessage();
		String content = Client.getMessage();
		RoomWindow room = Client.mainWindow.getRoomById(roomId);
		// User user = Client.getUserById(userId);
		// System.out.println(Integer.toString(userId));
		if (userId != Client.user.id) {
			if (Client.checkMessageType(content).equals("image"))
				room.showImg(userName, Client.getImgUrlByString(content));
			else if (Client.checkMessageType(content).equals("text"))
				room.showMessage(userName, content);
			else
				Util.errorReport("checkMessageType error: " + content);
		}
	}

}
