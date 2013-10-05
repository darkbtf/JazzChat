package TaipeiHot.JazzChat.Server.JdbcMysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import TaipeiHot.JazzChat.Util;
import TaipeiHot.JazzChat.Server.Room;

public class RoomTable extends Table{
	static private String tableName = "";
	static private String dropdbSQL, createdbSQL, insertdbSQL, selectSQL;
	static private ArrayList<ColumnElement> columns = new ArrayList<ColumnElement>();
	static private Statement stat = null; 
	static private ResultSet rs = null; 
	static private PreparedStatement pst = null;
	public RoomTable() {
		super();
		tableName="room";
		columns.add(new ColumnElement("count","INTEGER"));
		columns.add(new ColumnElement("user1_id","INTEGER"));
		columns.add(new ColumnElement("user2_id","INTEGER"));
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
	static public void insert(Room a) { 
		try {
			Util.errorReport(insertdbSQL);
			pst = con.prepareStatement(insertdbSQL);
			if(a.id==0)a.id=++Room.totalID;
			pst.setInt(1, a.id);
			pst.setInt(2, a.count); 
			pst.setInt(3, a.user1_id);
			pst.setInt(4, a.user2_id);
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
	static public Room instance(ResultSet rs){
		try {
			return new Room(rs.getInt("id"),
					rs.getInt("count"),
					rs.getInt("user1_id"),
					rs.getInt("user2_id"));
		} catch (SQLException e) {
		}
		return null;
	}
}
