package TaipeiHot.JazzChat.Server.JdbcMysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import TaipeiHot.JazzChat.Util;
/*
 * To add/remove a column, you need change:
 * drop or alter table in mysql
 * constructor()
 * insert(), instance(), update()
 * that class extends ActiveRecord
 */
public class Table { 
	static public Connection con = null;
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
	/*public void initSQL(String tableName,String dropdbSQL,String createdbSQL,String insertdbSQL,String selectSQL,String updateSQL,String deleteSQL,ArrayList<ColumnElement> columns,Statement stat){
		
	}*/
	protected void createTable(String createdbSQL, String tableName, ArrayList<ColumnElement> columns, Statement stat) {
		createdbSQL = "CREATE TABLE IF NOT EXISTS "+tableName+" ( id INTEGER ";
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
			try{
				stat.close();
			}catch(SQLException e){
				Util.errorReport("create stat close:"+e.toString());
			}
		} 
	} 
	protected void dropTable(String dropdbSQL) { 
		try {
			System.out.println("Closed "+dropdbSQL);
			//stat = con.createStatement(); 
			//stat.executeUpdate(dropdbSQL); 
		} 
		finally { 
			//stat.close();
		} 
	} 
	protected String makeInsertdbCmd(String tableName, ArrayList<ColumnElement> columns) {
		String insertdbSQL = "insert into "+tableName+"(id";
		for(ColumnElement c:columns)
			insertdbSQL += ","+c.name;
		insertdbSQL += ") VALUES (?";
		for(int i=0;i<columns.size();i++)
			insertdbSQL += ",?";
		insertdbSQL += ")";
		Util.errorReport(insertdbSQL);
		return insertdbSQL;
	}
	
	protected String makeUpdatedbCmd(String tableName, ArrayList<ColumnElement> columns){
		String ret = "update "+tableName+" set id=?";
		for(int i=0;i<columns.size();i++)
			ret += ","+columns.get(i).name+"=? ";
		ret+= "where id=?";
		Util.errorReport(ret);
		return ret;
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
