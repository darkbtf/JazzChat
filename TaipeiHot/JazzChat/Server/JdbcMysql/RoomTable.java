package TaipeiHot.JazzChat.Server.JdbcMysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import TaipeiHot.JazzChat.Util;
import TaipeiHot.JazzChat.Server.Account;
import TaipeiHot.JazzChat.Server.Room;
import TaipeiHot.JazzChat.Server.RoomAccount;

public class RoomTable extends Table{
	static private String tableName = "";
	static private String dropdbSQL, createdbSQL, insertdbSQL, selectSQL, updateSQL;//, deleteSQL;
	static private ArrayList<ColumnElement> columns = new ArrayList<ColumnElement>();
	static private Statement stat = null; 
	static private ResultSet rs = null; 
	static private PreparedStatement pst = null;
	public RoomTable() {
		super();
		tableName="room";
		columns.add(new ColumnElement("name","TINYTEXT"));
		//initSQL(tableName,dropdbSQL, createdbSQL, insertdbSQL, selectSQL, updateSQL, deleteSQL,columns,stat);
		dropdbSQL = "DROP TABLE IF EXISTS "+tableName; 
		dropTable(dropdbSQL);
		createTable(createdbSQL, tableName, columns, stat);
		insertdbSQL = makeInsertdbCmd(tableName, columns);
		selectSQL = "select * from "+tableName+" ";
		updateSQL = makeUpdatedbCmd(tableName, columns);
		//deleteSQL = "delete from "+tableName+" where ";
	}
	static private void makePrepareStat(PreparedStatement pst,Room a)throws SQLException{
		if(a.id==0)a.id=++Room.totalID;
		pst.setInt(1, a.id);
		pst.setString(2, a.name);
	}
	
	static public void insert(Room a) { 
		try {
			Util.errorReport(insertdbSQL);
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
	static public void update(Room a) { 
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
	static public ArrayList<Room> All(){
		ArrayList<Room> ret = new ArrayList<Room>();
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
	static public Room[] where(String format, String[]parameters){
		ArrayList<Room> ret = new ArrayList<Room>();
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
			Room a[]=new Room[ret.size()];
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
		return new Room[0]; 
	}
	static public Room[] where(String format){
		ArrayList<Room> ret = new ArrayList<Room>();
		try { 
			String cmd = new String(selectSQL);
			cmd += "WHERE BINARY "+format;
			pst = con.prepareStatement(cmd);
			rs = pst.executeQuery(); 
			while(rs.next()) { 
				ret.add(instance(rs));
			} 
			Room a[]=new Room[ret.size()];
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
		return new Room[0]; 
	}
	static public Room find(int id){
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
	static public Account[] accounts(int id){
		RoomAccount[] ras=RoomAccountTable.where("room_id="+id);
		Account[] ret = new Account[ras.length];
		for(int i=0;i<ras.length;i++)
			ret[i]=AccountTable.find(ras[i].account_id);
		return ret;
	}
	static public Room instance(ResultSet rs){
		try {
			return new Room(rs.getInt("id"),
					rs.getString("name"));
		} catch (SQLException e) {
		}
		return null;
	}
}
