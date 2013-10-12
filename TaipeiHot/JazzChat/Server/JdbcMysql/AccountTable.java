package TaipeiHot.JazzChat.Server.JdbcMysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	static private String tableName = "";
	static private String dropdbSQL, createdbSQL, insertdbSQL, selectSQL, updateSQL;//, deleteSQL;
	static private ArrayList<ColumnElement> columns = new ArrayList<ColumnElement>();
	static private Statement stat = null; 
	static private ResultSet rs = null; 
	static private PreparedStatement pst = null;
	public AccountTable() {
		super();
		tableName="account";
		columns.add(new ColumnElement("email","VARCHAR(40)"));
		columns.add(new ColumnElement("password","VARCHAR(20)"));
		columns.add(new ColumnElement("nickname","VARCHAR(40)"));
		columns.add(new ColumnElement("status","TINYTEXT"));
		columns.add(new ColumnElement("visible","TINYINT"));
		//initSQL(tableName,dropdbSQL, createdbSQL, insertdbSQL, selectSQL, updateSQL, deleteSQL,columns,stat);
		dropdbSQL = "DROP TABLE IF EXISTS "+tableName; 
		dropTable(dropdbSQL);
		createTable(createdbSQL, tableName, columns, stat);
		insertdbSQL = makeInsertdbCmd(tableName, columns);
		selectSQL = "select * from "+tableName+" ";
		updateSQL = makeUpdatedbCmd(tableName, columns);
		//deleteSQL = "delete from "+tableName+" where ";
	}
	static private void makePrepareStat(PreparedStatement pst,Account a)throws SQLException{
		if(a.id==0)a.id=++Account.totalID;
		pst.setInt(1, a.id);
		pst.setString(2, a.email); 
		pst.setString(3, a.password); 
		pst.setString(4, a.nickname); 
		pst.setString(5, a.status); 
		pst.setShort(6, a.visible);
	}
	
	static public void insert(Account a) { 
		try {
			pst = con.prepareStatement(insertdbSQL);
			makePrepareStat(pst,a);
			pst.executeUpdate(); 
		} 
		catch(SQLException e) { 
			Util.errorReport("InsertDB Exception :" + e.toString());
		} 
		finally { 
			Close(); 
		} 
	} 
	static public void update(Account a) { 
		try {
			pst = con.prepareStatement(updateSQL);
			makePrepareStat(pst,a);
			pst.setInt(columns.size()+2, a.id);
			pst.executeUpdate(); 
		} 
		catch(SQLException e) { 
			Util.errorReport("updateDB Exception :" + e.toString());
		} 
		finally { 
			Close(); 
		} 
	} 
	static private void Close() { 
		try{ 
			if(rs!=null) { 
				rs.close(); 
				rs = null; 
			} 
			if(stat!=null) { 
				stat.close(); 
				stat = null; 
      		} 
			if(pst!=null) { 
				pst.close(); 
				pst = null; 
      		} 
		} 
		catch(SQLException e) { 
			Util.errorReport("Close Exception :" + e.toString()); 
		} 
	} 
	static public Account[] All(){
		ArrayList<Account> ret = new ArrayList<Account>();
		try { 
			stat = con.createStatement(); 
			rs = stat.executeQuery(selectSQL);
			while(rs.next()) 
				ret.add(instance(rs));
			Account a[]=new Account[ret.size()];
			for(int i=0;i<ret.size();i++)
				a[i]=ret.get(i);
			return a;
		} 
		catch(SQLException e){ 
			Util.errorReport("AllDB Exception :" + e.toString()); 
		} 
		finally { 
			Close(); 
		}
		return new Account[0]; 
	}
	static public Account[] where(String format, String[]parameters){
		ArrayList<Account> ret = new ArrayList<Account>();
		try { 
			String cmd = new String(selectSQL);
			cmd += "WHERE BINARY "+format;
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
			Util.errorReport("whereDB Exception :" + e.toString()); 
		} 
		finally { 
			Close(); 
		}
		return new Account[0]; 
	}
	static public Account[] where(String format){
		ArrayList<Account> ret = new ArrayList<Account>();
		try { 
			String cmd = new String(selectSQL);
			cmd += "WHERE BINARY "+format;
			Util.errorReport(cmd);
			pst = con.prepareStatement(cmd);
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
			Util.errorReport("whereDB Exception :" + e.toString()); 
		} 
		finally { 
			Close(); 
		}
		return new Account[0]; 
	}
	static public Account find(int id){
		try { 
			stat = con.createStatement(); 
			rs = stat.executeQuery(selectSQL+"where id="+id);
			while(rs.next())
				return instance(rs);
		}catch(SQLException e){ 
			Util.errorReport("DBfind Exception :" + e.toString()); 
		} finally { 
			Close(); 
		} 
		return null;
	}

	static public Account instance(ResultSet rs){
		try {
			return new Account(rs.getInt("id"),
					rs.getString("email"),
					rs.getString("password"),
					rs.getString("nickname"),
					rs.getString("status"),
					rs.getShort("visible"));
		} catch (SQLException e) {
			Util.errorReport("instance SQLexception: "+e.toString());
		}
		return null;
	}
}
