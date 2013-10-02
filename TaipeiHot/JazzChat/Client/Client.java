package TaipeiHot.JazzChat.Client;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import TaipeiHot.JazzChat.Parameter;
import TaipeiHot.JazzChat.Command.CommandManager;

public class Client {
	private final static String address = "140.112.18.198";
	private static Socket client = new Socket();
	private final static CommandManager cmdMgr = new CommandManager();
	private static OutputStream out = null;

	public Client() {
	}

	public static void main(String args[]) {
		BufferedReader buf = new BufferedReader(
				new InputStreamReader(System.in));
		InetSocketAddress isa = new InetSocketAddress(address, Parameter.port);
		try {
			client.connect(isa, 10000);
			out = new BufferedOutputStream(client.getOutputStream());
		} catch (IOException e2) {
		}

		while (true) {
			try {
				byte[] cmdString = buf.readLine().getBytes();
				byte[] length = intToByteArray(cmdString.length);
				cmdMgr.parseCmd(length);
				cmdMgr.parseCmd(cmdString);
			} catch (Exception e) {
			}
		}
	}

	public static void sendCommandToServer(byte[] byteStream) throws Exception {
		out.write(byteStream);
		out.flush();
	}

	private static byte[] intToByteArray(int value) {
		return new byte[] { (byte) (value >>> 24), (byte) (value >>> 16),
				(byte) (value >>> 8), (byte) value };
	}
}
