package TaipeiHot.JazzChat.Client;

import java.io.IOException;
import java.io.OutputStream;

import TaipeiHot.JazzChat.Util;

public class ClientUtils {
	/*
	 * public static void sendCommandToServer(OutputStream out, byte[]
	 * byteStream) throws Exception { out.write(byteStream); out.flush(); }
	 */
	public static void sendStringsToServer(OutputStream out, String[] strs) {
		synchronized (out) {
			for (String str : strs) {

				if (str.length() > 250) {
					str = str.substring(0, 250);
				}

				byte[] strBytes = str.getBytes();
				// System.out.println("length = " + str.length() + " " +
				// strBytes.length);
				byte[] length = Util.intToByteArray(strBytes.length);
				try {
					out.write(length);
					out.write(strBytes);
				} catch (IOException e) {
					Util.errorReport("sendStringsToserverLOL");
				}
			}
			try {
				out.flush();
			} catch (IOException e) {
				Util.errorReport("sendStringsToserverLOL");
			}
		}
	}
}
