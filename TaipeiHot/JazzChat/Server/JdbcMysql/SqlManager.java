package TaipeiHot.JazzChat.Server.JdbcMysql;

import java.util.HashMap;
import java.util.Map;

import TaipeiHot.JazzChat.Util;

public class SqlManager {
	private Map<String, Table> tableMap = new HashMap<String, Table>();
	public SqlManager() {
		tableMap.put("account", new AccountTable());
	}

	public Table table(String t) {
		Table tableKind = tableMap.get(t); 
		if(tableKind == null){
			Util.errorReport("Undefined Table: "+t);
			return null;
		}
		return tableKind;
	}
}
