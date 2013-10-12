package TaipeiHot.JazzChat.Command;

import TaipeiHot.JazzChat.Client.Client;
import TaipeiHot.JazzChat.Client.FtpUtils;
import TaipeiHot.JazzChat.UI.RoomWindow;

public class FileCommand implements Command {

	@Override
	public void exec() throws CommandParsingErrorException {
		String cmd = Client.getMessage();
		if (cmd.equals("upload")) {
			String filePath = Client.getMessage();
			String fileName = Client.getMessage();
			int fileId = Integer.parseInt(Client.getMessage());
			FtpUtils.uploadFTPFile(filePath, fileName, fileId);
		} else if (cmd.equals("download")) {
			int roomId = Integer.parseInt(Client.getMessage());
			String fileName = Client.getMessage();
			String filePath = Client.getMessage();
			RoomWindow room = Client.mainWindow.getRoomById(roomId);
			room.confirmDownload(roomId, fileName, filePath);

		}
	}
}
