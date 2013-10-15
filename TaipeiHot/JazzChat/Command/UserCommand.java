package TaipeiHot.JazzChat.Command;

import TaipeiHot.JazzChat.Client.Client;
import TaipeiHot.JazzChat.Client.FtpUtils;

public class UserCommand implements Command {

	@Override
	public void exec() throws CommandParsingErrorException {
		String cmd = Client.getMessage();
		if (cmd.equals("photo")) {
			String filePath = Client.getMessage();
			String fileName = Client.getMessage();
			FtpUtils.uploadFTPPhoto(filePath, fileName);
		} else if (cmd.equals("change")) {
			String newUrl = Client.getMessage();
			Client.user.setProfilePicUrl(newUrl);
			Client.mainWindow.changePhoto();
		}
	}

}