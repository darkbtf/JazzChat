package TaipeiHot.JazzChat.Command;

public class GetMessage implements Command {

	@Override
	public void exec(byte[] parameter) {
		// TODO(paul): please replace it into your API
		System.out.println(parameter.toString());
		// showMessage(parameter.toString());
	}
}
