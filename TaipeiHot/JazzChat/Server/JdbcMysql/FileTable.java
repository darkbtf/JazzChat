package TaipeiHot.JazzChat.Server.JdbcMysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import TaipeiHot.JazzChat.Util;
import TaipeiHot.JazzChat.Server.FileChat;

public class FileTable extends Table{
	static private String tableName = "";
	static private String dropdbSQL, createdbSQL, insertdbSQL, selectSQL, updateSQL, deleteSQL;
	static private ArrayList<ColumnElement> columns = new ArrayList<ColumnElement>();
	static private Statement stat = null; 
	static private ResultSet rs = null; 
	static private PreparedStatement pst = null;
	public FileTable() {
		super();
		tableName="file";
		columns.add(new ColumnElement("filepath","TINYTEXT"));
		columns.add(new ColumnElement("fileName","TINYTEXT"));
		columns.add(new ColumnElement("encryptedFileName","TINYTEXT"));
		columns.add(new ColumnElement("room_id","INTEGER"));
		columns.add(new ColumnElement("account_id","INTEGER"));
		columns.add(new ColumnElement("uploaded","TINYINT"));
		//initSQL(tableName,dropdbSQL, createdbSQL, insertdbSQL, selectSQL, updateSQL, deleteSQL,columns,stat);
		dropdbSQL = "DROP TABLE IF EXISTS "+tableName; 
		dropTable(dropdbSQL);
		createTable(createdbSQL, tableName, columns, stat);
		insertdbSQL = makeInsertdbCmd(tableName, columns);
		selectSQL = "select * from "+tableName+" ";
		updateSQL = makeUpdatedbCmd(tableName, columns);
		deleteSQL = "delete from "+tableName+" where ";
	}
	
	static private void makePrepareStat(PreparedStatement pst,FileChat a)throws SQLException{
		if(a.id==0)a.id=++FileChat.totalID;
		pst.setInt(1, a.id);
		pst.setString(2, a.filepath);
		pst.setString(3, a.fileName);
		pst.setString(4, a.encryptedFileName);
		pst.setInt(5, a.room_id);
		pst.setInt(6, a.account_id);
		pst.setShort(7, a.uploaded);
	}
	
	static public void insert(FileChat a) { 
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
	
	static public void update(FileChat a) { 
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
	static public void delete(FileChat f){  
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
	static public ArrayList<FileChat> All(){
		ArrayList<FileChat> ret = new ArrayList<FileChat>();
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
	static public FileChat[] where(String format, String[]parameters){
		ArrayList<FileChat> ret = new ArrayList<FileChat>();
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
			FileChat a[]=new FileChat[ret.size()];
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
		return new FileChat[0]; 
	}
	static public FileChat[] where(String format){
		ArrayList<FileChat> ret = new ArrayList<FileChat>();
		try { 
			String cmd = new String(selectSQL);
			cmd += "WHERE BINARY "+format;
			pst = con.prepareStatement(cmd);
			rs = pst.executeQuery(); 
			while(rs.next()) { 
				ret.add(instance(rs));
			} 
			FileChat a[]=new FileChat[ret.size()];
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
		return new FileChat[0]; 
	}
	static public FileChat find(int id){
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
	static public FileChat instance(ResultSet rs){
		try {
			return new FileChat(rs.getInt("id"),
					rs.getString("filepath"),
					rs.getString("fileName"),
					rs.getString("encryptedFileName"),
					rs.getInt("room_id"),
					rs.getInt("account_id"),
					rs.getShort("uploaded"));
		} catch (SQLException e) {
		}
		return null;
	}
}
