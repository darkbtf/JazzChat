package TaipeiHot.JazzChat.Server.JdbcMysql;

import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.PreparedStatement; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.Statement; 

import TaipeiHot.JazzChat.Util;

public class JdbcMysql { 
	private Connection con = null; //Database objects 
	private Statement stat = null; 
	private ResultSet rs = null; 
	private PreparedStatement pst = null; 
	  //執行,傳入之sql為預儲之字申,需要傳入變數之位置 
	  //先利用?來做標示 
	private String dropdbSQL = "DROP TABLE User "; 
	private String createdbSQL = "CREATE TABLE User (" + 
    "    id     INTEGER " + 
    "  , name    VARCHAR(20) " + 
    "  , passwd  VARCHAR(20))";
	private String insertdbSQL = "insert into User(id,name,passwd) " + 
      "select ifNULL(max(id),0)+1,?,? FROM User"; 
	private String selectSQL = "select * from User ";
	public JdbcMysql() {
		try {
			Class.forName("com.mysql.jdbc.Driver"); 
			//註冊driver 
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
	//刪除Table
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
	public void SelectTable(){ 
		try { 
			stat = con.createStatement(); 
			rs = stat.executeQuery(selectSQL); 
			System.out.println("ID\t\tName\t\tPASSWORD"); 
			while(rs.next()) { 
				System.out.println(rs.getInt("id")+"\t\t"+ 
						rs.getString("name")+"\t\t"+rs.getString("passwd")); 
			} 
		} 
		catch(SQLException e){ 
			Util.errorReport("DropDB Exception :" + e.toString()); 
		} 
		finally { 
			Close(); 
		} 
	} 
	//要關閉所有Object 
	private void Close() { 
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
	
	public static void main(String[] args) { 
		JdbcMysql dbMgr = new JdbcMysql();
		dbMgr.dropTable(); 
		dbMgr.createTable(); 
		dbMgr.insertTable("蔣盛文", "12356"); 
		dbMgr.insertTable("yku2", "7890"); 
		dbMgr.SelectTable(); 
	} 
}
