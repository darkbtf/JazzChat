package TaipeiHot.JazzChat.Server.JdbcMysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import TaipeiHot.JazzChat.Util;

public class Table { 
	static public Connection con = null;
	protected Statement stat = null; 
	protected ResultSet rs = null; 
	protected PreparedStatement pst = null;
	protected String tableName = "";
	protected String dropdbSQL; 
	protected String createdbSQL;
	protected String insertdbSQL; 
	protected String selectSQL;
	protected ArrayList<ColumnElement> columns = new ArrayList<ColumnElement>();
	public Table() {
		try {
			Class.forName("com.mysql.jdbc.Driver"); 
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/jazzchat_development?useUnicode=true&characterEncoding=UTF-8", 
					"root","root");
		}
		catch(ClassNotFoundException e){
			Util.errorReport("DriverClassNotFound :"+e.toString());
		}
		catch(SQLException e) {
			Util.errorReport("Exception :"+e.toString());
		}
	}
	//建立table的方式 
	public void createTable() {
		createdbSQL = "CREATE TABLE "+tableName+" ( id INTEGER ";
		for(ColumnElement c:columns)
			createdbSQL += ", "+c.name+" "+c.type;
		createdbSQL += ")";
		try { 
			stat = con.createStatement(); 
			stat.executeUpdate(createdbSQL); 
		} 
		catch(SQLException e) { 
			System.out.println("CreateDB Exception :" + e.toString()); 
		} 
		finally { 
			Close(); 
		} 
	} 
	public void dropTable() { 
		try { 
			stat = con.createStatement(); 
			stat.executeUpdate(dropdbSQL); 
		} 
		catch(SQLException e) { 
			Util.errorReport("DropDB Exception :" + e.toString()); 
    	} 
		finally { 
			Close(); 
		} 
	} 
	//查詢資料 
	public void SelectTable(){}
	
	//private String insertdbSQL = "insert into User(id,name,passwd) " + 
    //      "select ifNULL(max(id),0)+1,?,? FROM User"; 
	public void makeInsertdbCmd(){
		insertdbSQL = "insert into "+tableName+"(id";
		for(ColumnElement c:columns)
			insertdbSQL += ","+c.name;
		insertdbSQL += ") select ifNULL(max(id),0)+1";
		for(ColumnElement c:columns)
			insertdbSQL += ",?";
		insertdbSQL += "FROM "+tableName;
	}
	//要關閉所有Object 
	protected void Close() { 
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
	class ColumnElement{
		String name;
		String type;
		public ColumnElement(){}
		public ColumnElement(String name,String type){
			this.name = name;
			this.type = type;
		}
	}
}
