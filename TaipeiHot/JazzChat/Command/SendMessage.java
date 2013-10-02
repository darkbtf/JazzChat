package TaipeiHot.JazzChat.Command;

import TaipeiHot.JazzChat.Client.Client;

public class SendMessage implements Command {

	@Override
	public void exec(byte[] parameter) {
		try {
			Client.sendCommandToServer(parameter);
		} catch (Exception e) {

		}
	}

}
