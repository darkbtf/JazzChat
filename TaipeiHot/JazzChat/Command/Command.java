package TaipeiHot.JazzChat.Command;

public interface Command {
	public void exec() throws CommandParsingErrorException;
}
