package TaipeiHot.JazzChat.Server.JdbcMysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import TaipeiHot.JazzChat.Util;

public class UserTable extends Table{
	/*private String createdbSQL = "CREATE TABLE User (" + 
    "    id     INTEGER " + 
    "  , name    VARCHAR(20) " + 
    "  , passwd  VARCHAR(20))";
	private String insertdbSQL = "insert into User(id,name,passwd) " + 
      "select ifNULL(max(id),0)+1,?,? FROM User"; */
	//private String selectSQL = "select * from User ";
	public UserTable() {
		super();
		tableName="user";
		dropdbSQL = "DROP TABLE IF EXISTS "+tableName; 
		dropTable();
		columns.add(new ColumnElement("email","VARCHAR(40)"));
		columns.add(new ColumnElement("password","VARCHAR(20)"));
		//columns.add(new ColumnElement("nickname","VARCHAR(40)"));
		//columns.add(new ColumnElement("status","TINYTEXT"));
		createTable();
		makeInsertdbCmd();
		selectSQL = "select * from "+tableName+" ";
		insertTable("蔣盛文", "12356"); 
		insertTable("yku2", "7890"); 
		SelectTable(); 
	}
	//新增資料 
	public void insertTable( String name,String passwd) { 
		try {
			pst = con.prepareStatement(insertdbSQL); 
      
			pst.setString(1, name); 
			pst.setString(2, passwd); 
			pst.executeUpdate(); 
		} 
		catch(SQLException e) { 
			Util.errorReport("InsertDB Exception :" + e.toString());
		} 
		finally { 
			Close(); 
		} 
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
	/*public static void main(String[] args) { 
		Model dbMgr = new Model();
		dbMgr.dropTable(); 
		dbMgr.createTable(); 
		dbMgr.insertTable("蔣盛文", "12356"); 
		dbMgr.insertTable("yku2", "7890"); 
		dbMgr.SelectTable(); 
	} */
}
