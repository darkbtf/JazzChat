package TaipeiHot.JazzChat.Command;

import TaipeiHot.JazzChat.User;
import TaipeiHot.JazzChat.Client.Client;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginCommand implements Command {
	@Override
	public void exec() throws CommandParsingErrorException {
		String cmd = Client.getMessage();
		if (cmd.equals("fail")) {
			cmd = Client.getMessage();
			if (cmd.equals("password"))
				Client.mainWindow.loginFail("password");
			else if (cmd.equals("username"))
				Client.mainWindow.loginFail("username");
			else
				throw new CommandParsingErrorException("Login Failed");
		} else if (cmd.equals("success")) {
			int id = Integer.parseInt(Client.getMessage());
			String myName = Client.getMessage();
			String status = Client.getMessage();
			boolean online = Client.getMessage().equals("true");
			String picUrl = Client.getMessage();
			User tmpUser = new User(myName, status);
			tmpUser.id = id;
			tmpUser.setOnline(online);
			tmpUser.setProfilePicUrl(picUrl);
			Client.userSet.put(id, tmpUser);
			Client.user = tmpUser;
                    try {
                        Client.mainWindow.loginSuccess();
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(LoginCommand.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(LoginCommand.class.getName()).log(Level.SEVERE, null, ex);
                    }
		}
	}
}