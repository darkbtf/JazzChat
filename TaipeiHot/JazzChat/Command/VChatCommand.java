package TaipeiHot.JazzChat.Command;

import TaipeiHot.JazzChat.Client.Client;
import TaipeiHot.JazzChat.Client.MediaUtils;

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
			MediaUtils.setLocalPlayer(roomId, ip);
			MediaUtils.setRemotePlayer(roomId);
		}
	}
}
