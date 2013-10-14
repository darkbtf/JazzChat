package TaipeiHot.JazzChat.Client;

import java.awt.FileDialog;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import TaipeiHot.JazzChat.Parameter;
import TaipeiHot.JazzChat.User;
import TaipeiHot.JazzChat.Util;
import TaipeiHot.JazzChat.Command.CommandManager;
import TaipeiHot.JazzChat.Command.CommandParsingErrorException;
import TaipeiHot.JazzChat.UI.MainWindow;
import TaipeiHot.JazzChat.UI.RoomWindow;

public class Client {
	private final static String address = "140.112.18.198";
	private static Socket client = new Socket();
	private final static CommandManager cmdMgr = new CommandManager();
	public static OutputStream out = null;
	public static InputStream in = null;
	private final static Deque<Byte> bufferInput = new LinkedList<Byte>();
	private final static Queue<String> messages = new LinkedList<String>();
	public static MainWindow mainWindow;
	public static Map<Integer, RoomWindow> roomSet = new HashMap<Integer, RoomWindow>();
	public static Map<Integer, User> userSet = new HashMap<Integer, User>();
	public static List<User> friendsToAdd = new ArrayList<User>();
	public static User user;

	public Client() {
	}

	private static void connectServer() {
		InetSocketAddress isa = new InetSocketAddress(address, Parameter.port);
		try {
			client.connect(isa, 10000);
			out = new BufferedOutputStream(client.getOutputStream());
			in = new BufferedInputStream(client.getInputStream());
		} catch (IOException e2) {
		}
	}

	private static void getCommand() {
		Thread getCommandThread = new Thread(new Runnable() {

			@Override
			public void run() {
				byte[] b = new byte[1024];
				Integer length;
				try {
					while ((length = in.read(b)) > 0) {
						for (int i = 0; i < length; i++)
							bufferInput.add(new Byte(b[i]));
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				} catch (IOException e) {
					// TODO: reconnect
					System.out.println("getMessage Error");
				}
			}
		});
		getCommandThread.start();
	}

	private static void parseCommand() {

		Thread parseCommandThread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						cmdMgr.parseCmd(getMessage());
						try {
							Thread.sleep(100);
						} catch (Exception e) {
						}
					} catch (CommandParsingErrorException e) {
						e.printStackTrace();
					}
				}
			}
		});
		parseCommandThread.start();
	}

	public static void main(String args[]) {

		connectServer();
		getCommand();
		parseCommand();
		mainWindow = new MainWindow();
		mainWindow.loginShow();
	}

	public static void userLogin(String account, String password) {
		ClientUtils.sendStringsToServer(out, new String[] { "login", account,
				password });
	}

	public static void userRegister(String account, String password) {
		ClientUtils.sendStringsToServer(out, new String[] { "register",
				account, password });
	}

	public static void addFriend(String userName, String greeting) {
		ClientUtils.sendStringsToServer(out, new String[] { "friend", "add",
				userName, greeting });
	}

	public static void acceptFriend(int userId) {
		ClientUtils.sendStringsToServer(out, new String[] { "friend", "accept",
				Integer.toString(userId) });
	}

	public static void rejectFriend(int userId) {
		ClientUtils.sendStringsToServer(out, new String[] { "friend", "reject",
				Integer.toString(userId) });
	}

	public static void sendMessage(int roomId, String content) {
		ClientUtils.sendStringsToServer(out,
				new String[] { "message", Integer.toString(roomId), content });
	}

	public static void setVisible(boolean visible) {
		String trueOrFalse = visible ? "true" : "false";
		ClientUtils.sendStringsToServer(out, new String[] { "user", "visible",
				trueOrFalse });
	}

	public static User getUserById(int userId) {
		return userSet.get(userId);
	}

	public static void openPrivateRoom(int userId) {
		ClientUtils.sendStringsToServer(out, new String[] { "room", "new",
				"private", Integer.toString(userId) });
	}

	public static void openPublicRoom(String roomName) {
		ClientUtils.sendStringsToServer(out, new String[] { "room", "new",
				"public", roomName });
	}

	public static void uploadFile(int roomId, String path) {
		ClientUtils.sendStringsToServer(out, new String[] { "file", "upload",
				Integer.toString(roomId), path });
	}

	public static void finishUpload(int fileId) {
		ClientUtils.sendStringsToServer(out, new String[] { "file", "finish",
				Integer.toString(fileId) });
	}

	public static void startDownload(int roomId, String fileName,
			String filePath) {
		FileDialog filedialog = new FileDialog(Client.mainWindow, "new",
				FileDialog.SAVE);
		filedialog.setDirectory(System.getProperty("user.home")
				+ File.separator + "Downloads");
		filedialog.setFile(fileName);
		filedialog.setVisible(true);
		String myPath = filedialog.getDirectory() + File.separator
				+ filedialog.getFile();
		FtpUtils.downloadFTPFile(filePath, myPath);
		RoomWindow room = Client.mainWindow.getRoomById(roomId);
		room.showFile(myPath, fileName);
	}

	public static String getMessage() {
		while (messages.isEmpty()) {
			while (!Util.parseByte(bufferInput, messages)) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		Util.errorReport("receive: " + messages.peek());
		return messages.poll();

	}

	private static String getImgUrlById(int id) {
		return "http://fuuu.us/" + id + ".png";
	}

	public static void sendImg(int roomId, int id) {
		RoomWindow room = Client.mainWindow.getRoomById(roomId);
		room.showImg(Client.user.getNickname(), getImgUrlById(id));
		ClientUtils.sendStringsToServer(out,
				new String[] { "message", Integer.toString(roomId),
						"(" + id + "}" });
	}

	public static String getImgUrlByString(String s) {
		return getImgUrlById(Integer.valueOf(s.substring(1, s.length() - 2)));
	}

	public static boolean isNumber(String s) {
		for (int i = 0; i < s.length(); i++)
			if (s.charAt(i) < '0' || s.charAt(i) > '9')
				return false;
		return true;
	}

	public static String checkMessageType(String message) {
		if (message.startsWith("(") && message.endsWith("}")
				&& Client.isNumber(message.substring(1, message.length() - 2)))
			return "image";
		return "text";
	}

}
