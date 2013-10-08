package TaipeiHot.JazzChat.Server.JdbcMysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import TaipeiHot.JazzChat.Util;
import TaipeiHot.JazzChat.Server.Friend;

public class FriendTable extends Table{
	static private String tableName = "";
	static private String dropdbSQL, createdbSQL, insertdbSQL, selectSQL;
	static private ArrayList<ColumnElement> columns = new ArrayList<ColumnElement>();
	static private Statement stat = null; 
	static private ResultSet rs = null; 
	static private PreparedStatement pst = null;
	public FriendTable() {
		super();
		tableName="friend";
		columns.add(new ColumnElement("account_id1","INTEGER"));
		columns.add(new ColumnElement("account_id2","INTEGER"));
		columns.add(new ColumnElement("status","VARCHAR(30)"));
		columns.add(new ColumnElement("message","TINYTEXT"));
		dropdbSQL = "DROP TABLE IF EXISTS "+tableName; 
		dropTable(dropdbSQL);
		try {
			createTable(createdbSQL, tableName, columns, stat);
		} catch (SQLException e) {
			Util.errorReport("creat fail");
		}
		insertdbSQL = makeInsertdbCmd(tableName, columns);
		Util.errorReport(insertdbSQL);
		selectSQL = "select * from "+tableName+" ";
	}
	//新增資料 
	static public void insert(Friend a) { 
		try {
			Util.errorReport(insertdbSQL);
			pst = con.prepareStatement(insertdbSQL);
			if(a.id==0)a.id=++Friend.totalID;
			pst.setInt(1, a.id);
			pst.setInt(2, a.account_id1);
			pst.setInt(3, a.account_id2);
			pst.setString(4, a.status);
			pst.setString(5, a.message);
			pst.executeUpdate(); 
		} 
		catch(SQLException e) { 
			Util.errorReport("InsertDB Exception :" + e.toString());
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
	static public ArrayList<Friend> All(){
		ArrayList<Friend> ret = new ArrayList<Friend>();
		try { 
			stat = con.createStatement(); 
			rs = stat.executeQuery(selectSQL); 
			while(rs.next()) 
				ret.add(instance(rs));
			return ret;
		} 
		catch(SQLException e){ 
			Util.errorReport("AllDB Exception :" + e.toString()); 
		} 
		finally { 
			Close(); 
		}
		return ret; 
	}
	static public Friend[] where(String format, String[]parameters){
		ArrayList<Friend> ret = new ArrayList<Friend>();
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
			Friend a[]=new Friend[ret.size()];
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
		return new Friend[0]; 
	}
	static public Friend[] where(String format){
		ArrayList<Friend> ret = new ArrayList<Friend>();
		try { 
			String cmd = new String(selectSQL);
			cmd += "WHERE BINARY "+format;
			pst = con.prepareStatement(cmd);
			rs = pst.executeQuery(); 
			while(rs.next()) { 
				ret.add(instance(rs));
			} 
			Friend a[]=new Friend[ret.size()];
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
		return new Friend[0]; 
	}
	static public Friend find(int id){
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
	static public Friend instance(ResultSet rs){
		try {
			return new Friend(rs.getInt("id"),
					rs.getInt("account_id1"),
					rs.getInt("account_id2"),
					rs.getString("status"),
					rs.getString("message"));
		} catch (SQLException e) {
		}
		return null;
	}
}
