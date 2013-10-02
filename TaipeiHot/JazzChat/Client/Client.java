package TaipeiHot.JazzChat.Client;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;

import TaipeiHot.JazzChat.Parameter;
import TaipeiHot.JazzChat.Command.CommandManager;

public class Client {
	private final static String address = "140.112.18.198";
	private static Socket client = new Socket();
	private final static CommandManager cmdMgr = new CommandManager();

	public Client() {
	}

	public static void main(String args[]) {
		BufferedReader buf = new BufferedReader(
				new InputStreamReader(System.in));
		while (true) {
			client = new Socket();
			try {
				cmdMgr.parseCmd((buf.readLine() + "\0").getBytes());
			} catch (Exception e) {
			}
		}
		// cmdMgr.parseCmd("幹你媽媽媽媽".getBytes());
	}

	public static void sendCommandToServer(byte[] byteStream) throws Exception {
		InetSocketAddress isa = new InetSocketAddress(address, Parameter.port);
		client.connect(isa, 10000);
		BufferedOutputStream out = new BufferedOutputStream(
				client.getOutputStream());
		out.write(byteStream);
		out.flush();
		client.close();
	}
}
