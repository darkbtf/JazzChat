package TaipeiHot.JazzChat.Command;

import java.awt.FileDialog;
import java.io.File;

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
			FileDialog filedialog = new FileDialog(Client.mainWindow, "new",
					FileDialog.SAVE);
			filedialog.setDirectory(System.getProperty("user.home")
					+ File.separator + "Downloads");
			filedialog.setFile(fileName);
			filedialog.setVisible(true);
			String myPath = filedialog.getDirectory() + File.separator
					+ filedialog.getFile();
			FtpUtils.downloadFTPFile(fileName, filePath, myPath);
			room.showFile(myPath, fileName);
		}
	}
}
