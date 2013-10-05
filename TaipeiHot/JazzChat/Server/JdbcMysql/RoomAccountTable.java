package TaipeiHot.JazzChat.Server.JdbcMysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import TaipeiHot.JazzChat.Util;
import TaipeiHot.JazzChat.Server.RoomAccount;

public class RoomAccountTable extends Table{
	static private String tableName = "";
	static private String dropdbSQL, createdbSQL, insertdbSQL, selectSQL;
	static private ArrayList<ColumnElement> columns = new ArrayList<ColumnElement>();
	static private Statement stat = null; 
	static private ResultSet rs = null; 
	static private PreparedStatement pst = null;
	public RoomAccountTable() {
		super();
		tableName="room_account";
		columns.add(new ColumnElement("room_id","INTEGER"));
		columns.add(new ColumnElement("account_id","INTEGER"));
		dropdbSQL = "DROP TABLE IF EXISTS "+tableName; 
		dropTable(dropdbSQL);
		try {
			createTable(createdbSQL, tableName, columns, stat);
		} catch (SQLException e) {
		}
		insertdbSQL = makeInsertdbCmd(tableName, columns);
		selectSQL = "select * from "+tableName+" ";
	}
	//新增資料 
	static public void insert(RoomAccount a) { 
		try {
			pst = con.prepareStatement(insertdbSQL);
			if(a.id==0)a.id=++RoomAccount.totalID;
			pst.setInt(1, a.id);
			pst.setInt(2, a.room_id); 
			pst.setInt(3, a.account_id);
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
	static public ArrayList<RoomAccount> All(){
		ArrayList<RoomAccount> ret = new ArrayList<RoomAccount>();
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
	static public RoomAccount[] where(String format, String[]parameters){
		ArrayList<RoomAccount> ret = new ArrayList<RoomAccount>();
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
			RoomAccount a[]=new RoomAccount[ret.size()];
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
		return new RoomAccount[0]; 
	}
	static public RoomAccount[] where(String format){
		ArrayList<RoomAccount> ret = new ArrayList<RoomAccount>();
		try { 
			String cmd = new String(selectSQL);
			cmd += "WHERE "+format;
			pst = con.prepareStatement(cmd);
			rs = pst.executeQuery(); 
			while(rs.next()) { 
				ret.add(instance(rs));
			} 
			RoomAccount a[]=new RoomAccount[ret.size()];
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
		return new RoomAccount[0]; 
	}
	static public RoomAccount find(int id){
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
	static public void SelectTable(){ 
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
			Util.errorReport("SelectDB Exception :" + e.toString()); 
		} 
		finally { 
			Close(); 
		} 
	}
	static public RoomAccount instance(ResultSet rs){
		try {
			return new RoomAccount(rs.getInt("id"),
					rs.getInt("room_id"),
					rs.getInt("account_id"));
		} catch (SQLException e) {
		}
		return null;
	}
}
