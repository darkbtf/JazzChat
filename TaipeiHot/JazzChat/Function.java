package TaipeiHot.JazzChat;

import java.net.ServerSocket;
import java.net.Socket;

public class Function extends Thread {
	protected final int ServerPort = 8765;// �n�ʱ���port
	protected Socket client;
	protected boolean sendMessageToServer(){
		return true;
	}
}
