package TaipeiHot.JazzChat;

public class User {
	public int id;
	protected boolean online;
	protected String email;
	protected String nickname;
	protected String status;
	protected String profilePicUrl;
	protected String password;

	// protected ArrayList<User> Friends;

	public User() {
	}

	public User(String nickname, String status) {
		this.nickname = nickname;
		this.status = status;
	}

	public void setNickname(String N) {
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public void setProfilePicUrl(String url) {
		this.profilePicUrl = url;
	}

	public String getProfilePicUrl() {
		return this.profilePicUrl;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}
}