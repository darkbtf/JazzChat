package TaipeiHot.JazzChat;

import java.util.ArrayList;

public class User {
	public int id;
	protected String email, nickname, status;
	protected String password;
	protected ArrayList<User> Friends;

	public User() {
	}

	public void setNickname(String N) {
	}

	public String getNickname() {
		return nickname;
	}

	public String getMessage() {
		return "";
	}
}