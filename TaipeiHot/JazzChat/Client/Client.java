package TaipeiHot.JazzChat.Client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

import TaipeiHot.JazzChat.Parameter;
import TaipeiHot.JazzChat.Command.CommandManager;

public class Client {
	private final static String address = "140.112.18.198";
	private static Socket client = new Socket();
	private final static CommandManager cmdMgr = new CommandManager();
	private static OutputStream out = null;
	private static InputStream in = null;
	private final static Deque<Byte> bufferInput = new LinkedList<Byte>();
	private final static Queue<String> messages = new LinkedList<String>();

	public Client() {
	}

	public static void main(String args[]) {
		BufferedReader buf = new BufferedReader(
				new InputStreamReader(System.in));
		InetSocketAddress isa = new InetSocketAddress(address, Parameter.port);
		try {
			client.connect(isa, 10000);
			out = new BufferedOutputStream(client.getOutputStream());
			in = new BufferedInputStream(client.getInputStream());
		} catch (IOException e2) {
		}

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
					System.out.println("getMessage Error");
				}
			}
		});

		Thread parseCommandThread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					while (parseByte()) {

					}
					try {
						Thread.sleep(500);
					} catch (Exception e) {
					}
					String data = messages.poll();
					if (data != null)
						System.out.println(data);
				}
			}
		});

		getCommandThread.start();
		parseCommandThread.start();

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

	private static Boolean parseByte() {
		if (bufferInput.size() < Parameter.bytesForLength)
			return false;
		Byte[] tmp = new Byte[Parameter.bytesForLength];
		int length = 0;
		for (int i = 0; i < Parameter.bytesForLength; i++) {
			tmp[i] = bufferInput.pollFirst();
			length = (length << 1) + tmp[i].intValue();
		}
		if (bufferInput.size() < length) {
			for (int i = Parameter.bytesForLength - 1; i >= 0; i--)
				bufferInput.addFirst(tmp[i]);
			return false;
		}
		String data = "";
		byte[] b = new byte[length];
		for (int i = 0; i < length; i++)
			b[i] = bufferInput.pollFirst();
		data += new String(b, 0, length);
		messages.add(data);
		return true;
	}
}
