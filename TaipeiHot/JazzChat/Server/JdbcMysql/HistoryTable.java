package TaipeiHot.JazzChat.Server.JdbcMysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import TaipeiHot.JazzChat.Util;
import TaipeiHot.JazzChat.Server.History;

public class HistoryTable extends Table{
	static private String tableName = "";
	static private String dropdbSQL, createdbSQL, insertdbSQL, selectSQL, updateSQL, deleteSQL;
	static private ArrayList<ColumnElement> columns = new ArrayList<ColumnElement>();
	static private Statement stat = null; 
	static private ResultSet rs = null; 
	static private PreparedStatement pst = null;
	public HistoryTable() {
		super();
		tableName="history";
		columns.add(new ColumnElement("room_id","INTEGER"));
		columns.add(new ColumnElement("account_id","INTEGER"));
		columns.add(new ColumnElement("message","TEXT"));
		columns.add(new ColumnElement("sendTime","TIMESTAMP"));
		//initSQL(tableName,dropdbSQL, createdbSQL, insertdbSQL, selectSQL, updateSQL, deleteSQL,columns,stat);
		dropdbSQL = "DROP TABLE IF EXISTS "+tableName; 
		dropTable(dropdbSQL);
		createTable(createdbSQL, tableName, columns, stat);
		insertdbSQL = makeInsertdbCmd(tableName, columns);
		selectSQL = "select * from "+tableName+" ";
		updateSQL = makeUpdatedbCmd(tableName, columns);
		deleteSQL = "delete from "+tableName+" where ";
	}
	static private void makePrepareStat(PreparedStatement pst,History a)throws SQLException{
		if(a.id==0)a.id=++History.totalID;
		pst.setInt(1, a.id);
		pst.setInt(2, a.room_id);
		pst.setInt(3, a.account_id);
		pst.setString(4, a.message);
		pst.setTimestamp(5, a.sendTime);
	}
	
	static public void insert(History a) { 
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
	
	static public void update(History a) { 
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
	static public void delete(History f){  
		try {
			pst = con.prepareStatement(deleteSQL+"id="+f.id);////////
			pst.executeUpdate(); 
		} 
		catch(SQLException e) { 
			Util.errorReport("deleteDB Exception :" + e.toString());
		} 
		finally { 
			Close(); 
		} 
	}
	
	static public void delete(String format){
		try {
			pst = con.prepareStatement(deleteSQL+format);
			pst.executeUpdate(); 
		} 
		catch(SQLException e) { 
			Util.errorReport("deleteDB Exception :" + e.toString());
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
	static public ArrayList<History> All(){
		ArrayList<History> ret = new ArrayList<History>();
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
	static public History[] where(String format, String[]parameters){
		ArrayList<History> ret = new ArrayList<History>();
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
			History a[]=new History[ret.size()];
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
		return new History[0]; 
	}
	static public History[] where(String format){
		ArrayList<History> ret = new ArrayList<History>();
		try { 
			String cmd = new String(selectSQL);
			cmd += "WHERE BINARY "+format;
			pst = con.prepareStatement(cmd);
			rs = pst.executeQuery(); 
			while(rs.next()) { 
				ret.add(instance(rs));
			} 
			History a[]=new History[ret.size()];
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
		return new History[0]; 
	}
	static public History find(int id){
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
	static public History instance(ResultSet rs){
		try {
			return new History(rs.getInt("id"),
					rs.getInt("roomd_id"),
					rs.getInt("account_id"),
					rs.getString("message"),
					rs.getTimestamp("sendTime"));
		} catch (SQLException e) {
		}
		return null;
	}
}
