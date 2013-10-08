package TaipeiHot.JazzChat.Command;

import TaipeiHot.JazzChat.User;
import TaipeiHot.JazzChat.Client.Client;

public class Friend implements Command {

	@Override
	public void exec() throws CommandParsingErrorException {
		String cmd = Client.getMessage();
		if (cmd.equals("add")) {
			int userId = Integer.parseInt(Client.getMessage());
			String userName = Client.getMessage();
			String greeting = Client.getMessage();
			Client.friendsToAdd.add(new User(userName, greeting));
			Client.mainWindow.acceptFriendShow(userId, userName, greeting);
		} else if (cmd.equals("response")) {
			String userName = Client.getMessage();
			String response = Client.getMessage();
			if (response.equals("accept")) {
				Client.mainWindow.addFriendSuccess("accept");
			} else if (response.equals("reject")) {
				Client.mainWindow.addFriendFail("reject");
			} else if (response.equals("fail")) {
				Client.mainWindow.addFriendFail("fail");
			}
		} else if (cmd.equals("show")) {
			int userId = Integer.parseInt(Client.getMessage());
			String userName = Client.getMessage();
			// TODO: implement this
		}
	}
}
