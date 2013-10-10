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
				String reason = Client.getMessage();
				Client.mainWindow.addFriendFail(reason);
			}
		} else if (cmd.equals("show")) {
			int userId = Integer.parseInt(Client.getMessage());
			String userName = Client.getMessage();
			String status = Client.getMessage();
			User tmpUser = new User(userName, status);
			tmpUser.id = userId;
			Client.userSet.put(userId, tmpUser);
			Client.mainWindow.friendShow(tmpUser);
		} else if (cmd.equals("online")) {
			int userId = Integer.parseInt(Client.getMessage());
			Client.mainWindow.setOnlineById(userId);
		} else if (cmd.equals("offline")) {
			int userId = Integer.parseInt(Client.getMessage());
			Client.mainWindow.setOfflineById(userId);
		}
	}
}
