package TaipeiHot.JazzChat.Server;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import TaipeiHot.JazzChat.Server.JdbcMysql.ActiveRecord;
import TaipeiHot.JazzChat.Server.JdbcMysql.FileTable;

public class FileChat extends ActiveRecord {
	static public int totalID = 0;
	public String filepath,fileName, encryptedFileName;
	public int room_id;
	public int account_id;
	public short uploaded;
	public FileChat(int id,String filepath,String fileName, String encryptedFileName,int room_id,int account_id, short uploaded){
		this.id = id;
		this.filepath = filepath;
		this.fileName = fileName;
		this.encryptedFileName = encryptedFileName;
		this.room_id = room_id;
		this.account_id = account_id;
		this.uploaded = uploaded;
	}
	
	public FileChat(String filepath,int room_id,int account_id){
		this.id = ++FileChat.totalID;
		this.filepath = filepath;
		this.room_id = room_id;
		this.account_id = account_id;
		this.uploaded = 0;
		setFileName(filepath);
	}
	
	public void save(){
		FileTable.update(this);
	}
	
	private void setFileName(String filepath){
		File f = new File(filepath);
		fileName = f.getName();
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		encryptedFileName = fileName + "_"+timeStamp+"_"+(new Random()).nextInt(); 
	}
}