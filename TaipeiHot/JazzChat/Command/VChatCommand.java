package TaipeiHot.JazzChat.Command;

import TaipeiHot.JazzChat.Client.Client;
import TaipeiHot.JazzChat.Client.MediaUtils;
import TaipeiHot.JazzChat.UI.RoomWindow;

public class VChatCommand implements Command {

	@Override
	public void exec() throws CommandParsingErrorException {
		String cmd = Client.getMessage();
		if (cmd.equals("invite")) {
			int roomId = Integer.parseInt(Client.getMessage());
			Client.respondVideoChat(true, roomId);
			// TODO: meow
		} else if (cmd.equals("reject")) {
			int roomId = Integer.parseInt(Client.getMessage());
			// TODO: meow
		} else if (cmd.equals("start")) {
			int roomId = Integer.parseInt(Client.getMessage());
			String ip = Client.getMessage();
			System.out.println(ip);
			RoomWindow room = Client.mainWindow.getRoomById(roomId);
			room.videoWindow.setVisible(true);

			MediaUtils.setLocalPlayer(roomId, ip);
			// Client.sendVideoDone(roomId);
			MediaUtils.setRemotePlayer(roomId);
		} else if (cmd.equals("done")) {
			int roomId = Integer.parseInt(Client.getMessage());
			// MediaUtils.setRemotePlayer(roomId);
		}
	}
}
