package TaipeiHot.JazzChat.Server.JdbcMysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import TaipeiHot.JazzChat.Util;
import TaipeiHot.JazzChat.Server.Account;

public class AccountTable extends Table{
	/*private String createdbSQL = "CREATE TABLE User (" + 
    "    id     INTEGER " + 
    "  , name    VARCHAR(20) " + 
    "  , passwd  VARCHAR(20))";
	private String insertdbSQL = "insert into User(id,name,passwd) " + 
      "select ifNULL(max(id),0)+1,?,? FROM User"; */
	//private String selectSQL = "select * from User ";
	public AccountTable() {
		super();
		tableName="account";
		dropdbSQL = "DROP TABLE IF EXISTS "+tableName; 
		//dropTable();
		columns.add(new ColumnElement("email","VARCHAR(40)"));
		columns.add(new ColumnElement("password","VARCHAR(20)"));
		columns.add(new ColumnElement("nickname","VARCHAR(40)"));
		columns.add(new ColumnElement("status","TINYTEXT"));
		createTable();
		makeInsertdbCmd();
		selectSQL = "select * from "+tableName+" ";
	}
	//新增資料 
	@Override
	public void insert(ActiveRecord arg) { 
		try {
			Account a = (Account)arg;
			pst = con.prepareStatement(insertdbSQL);
			pst.setString(1, a.email); 
			pst.setString(2, a.password); 
			pst.setString(3, a.nickname); 
			pst.setString(4, a.status); 
			pst.executeUpdate(); 
		} 
		catch(SQLException e) { 
			Util.errorReport("InsertDB Exception :" + e.toString());
		} 
		finally { 
			Close(); 
		} 
	} 
	
	@Override
	public ArrayList<ActiveRecord> All(){
		ArrayList<ActiveRecord> ret = new ArrayList<ActiveRecord>();
		try { 
			stat = con.createStatement(); 
			rs = stat.executeQuery(selectSQL); 
			while(rs.next()) 
				ret.add(instance(rs));
			return ret;
		} 
		catch(SQLException e){ 
			Util.errorReport("DropDB Exception :" + e.toString()); 
		} 
		finally { 
			Close(); 
		}
		return ret; 
	}
	@Override
	public Account[] where(String format, String[]parameters){
		ArrayList<Account> ret = new ArrayList<Account>();
		try { 
			String cmd = new String(selectSQL);
			cmd += "WHERE "+format;
			pst = con.prepareStatement(cmd);
			for(int i=0;i<parameters.length;i++)
				pst.setString(i+1, parameters[i]); 
			rs = pst.executeQuery(); 
			while(rs.next()) { 
				ret.add(instance(rs));
			} 
			Account a[]=new Account[ret.size()];
			for(int i=0;i<ret.size();i++)
				a[i]=ret.get(i);
			return a;
		} 
		catch(SQLException e){ 
			Util.errorReport("DropDB Exception :" + e.toString()); 
		} 
		finally { 
			Close(); 
		}
		return new Account[0]; 
	}
	//查詢資料 
	@Override
	public void SelectTable(){ 
		try { 
			stat = con.createStatement(); 
			rs = stat.executeQuery(selectSQL); 
			System.out.println("ID\t\tName\t\tPASSWORD"); 
			while(rs.next()) { 
				System.out.println(rs.getInt("id")+"\t\t"+ 
						rs.getString("email")+"\t\t"+rs.getString("password")); 
			} 
		} 
		catch(SQLException e){ 
			Util.errorReport("DropDB Exception :" + e.toString()); 
		} 
		finally { 
			Close(); 
		} 
	}
	@Override
	public Account instance(ResultSet rs){
		try {
			return new Account(rs.getInt("id"),
					rs.getString("email"),
					rs.getString("password"),
					rs.getString("nickname"),
					rs.getString("status"));
		} catch (SQLException e) {
		}
		return null;
	}
	/*public static void main(String[] args) { 
		Model dbMgr = new Model();
		dbMgr.dropTable(); 
		dbMgr.createTable(); 
		dbMgr.insertTable("蔣盛文", "12356"); 
		dbMgr.insertTable("yku2", "7890"); 
		dbMgr.SelectTable(); 
	} */
}
