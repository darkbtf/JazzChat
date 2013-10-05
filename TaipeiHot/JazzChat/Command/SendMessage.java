package TaipeiHot.JazzChat.Command;

import TaipeiHot.JazzChat.Client.Client;
import TaipeiHot.JazzChat.Client.ClientUtils;

public class SendMessage implements Command {

	@Override
	public void exec() {
		try {
			ClientUtils.sendCommandToServer(Client.out, null);
		} catch (Exception e) {

		}
	}
}
