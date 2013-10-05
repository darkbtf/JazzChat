package TaipeiHot.JazzChat.Client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

import TaipeiHot.JazzChat.Parameter;
import TaipeiHot.JazzChat.Util;
import TaipeiHot.JazzChat.Command.CommandManager;
import TaipeiHot.JazzChat.Command.CommandParsingErrorException;
import TaipeiHot.JazzChat.UI.MainWindow;

public class Client {
	private final static String address = "140.112.18.198";
	private static Socket client = new Socket();
	private final static CommandManager cmdMgr = new CommandManager();
	public static OutputStream out = null;
	public static InputStream in = null;
	private final static Deque<Byte> bufferInput = new LinkedList<Byte>();
	private final static Queue<String> messages = new LinkedList<String>();
	public static MainWindow mainWindow;

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
		return messages.poll();

	}
}
