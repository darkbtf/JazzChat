package TaipeiHot.JazzChat.Command;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import TaipeiHot.JazzChat.User;
import TaipeiHot.JazzChat.Client.Client;

public class FriendCommand implements Command {

	@Override
	public void exec() throws CommandParsingErrorException {
		String cmd = Client.getMessage();
		if (cmd.equals("add")) {
			int userId = Integer.parseInt(Client.getMessage());
			String userName = Client.getMessage();
			String greeting = Client.getMessage();
			User tmpUser = new User(userName, greeting);
			System.out.println(userName + "++" + greeting);
			Client.friendsToAdd.add(tmpUser);
			Client.mainWindow.pendingListShow(tmpUser);
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
				System.out.println("gan ni lao mu");
				Client.mainWindow.addFriendFail(reason);
			}
		} else if (cmd.equals("show")) {
			int userId = Integer.parseInt(Client.getMessage());
			String userName = Client.getMessage();
			String status = Client.getMessage();
			boolean online = Client.getMessage().equals("true");
			String profilePic = Client.getMessage();
			User tmpUser = new User(userName, status);
			tmpUser.id = userId;
			tmpUser.setOnline(online);
			tmpUser.setProfilePicUrl(profilePic);
			System.out.println(Integer.toString(userId) + " " + userName);
			Client.userSet.put(userId, tmpUser);
			try {
				Client.mainWindow.friendShow(tmpUser);
			} catch (MalformedURLException ex) {
				Logger.getLogger(FriendCommand.class.getName()).log(
						Level.SEVERE, null, ex);
			} catch (IOException ex) {
				Logger.getLogger(FriendCommand.class.getName()).log(
						Level.SEVERE, null, ex);
			}
			if (online) {
				Client.mainWindow.setOnlineById(userId);
			} else {
				Client.mainWindow.setOfflineById(userId);
			}
		} else if (cmd.equals("online")) {
			int userId = Integer.parseInt(Client.getMessage());
			Client.mainWindow.setOnlineById(userId);
		} else if (cmd.equals("offline")) {
			int userId = Integer.parseInt(Client.getMessage());
			Client.mainWindow.setOfflineById(userId);
		} else if (cmd.equals("photo")) {
			int userId = Integer.parseInt(Client.getMessage());
			String url = Client.getMessage();
			Client.userSet.get(userId).setProfilePicUrl(url);
			Client.mainWindow.changePhotoById(userId);
		} else if (cmd.equals("status")) {
			int userId = Integer.parseInt(Client.getMessage());
			String status = Client.getMessage();
			Client.mainWindow.changeStatusById(userId, status);
		} else if (cmd.equals("name")) {
			int userId = Integer.parseInt(Client.getMessage());
			String name = Client.getMessage();
			Client.mainWindow.changeNicknameById(userId, name);

		}
	}
}
