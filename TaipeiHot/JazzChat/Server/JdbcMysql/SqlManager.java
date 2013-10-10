package TaipeiHot.JazzChat.Server.JdbcMysql;

public class SqlManager {
	public SqlManager() {
		new AccountTable();
		new RoomTable();
		new RoomAccountTable();
		new FriendTable();
		new FileTable();
	}
}
