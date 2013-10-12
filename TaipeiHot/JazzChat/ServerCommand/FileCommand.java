package TaipeiHot.JazzChat.ServerCommand;

import TaipeiHot.JazzChat.Util;
import TaipeiHot.JazzChat.Server.Account;
import TaipeiHot.JazzChat.Server.FileChat;
import TaipeiHot.JazzChat.Server.Server;
import TaipeiHot.JazzChat.Server.JdbcMysql.FileTable;
import TaipeiHot.JazzChat.Server.JdbcMysql.RoomTable;

public class FileCommand extends ServerCommand {

	public FileCommand(Account a){
		super(a);
	}
	@Override
	public Boolean exec() {
		String cmd = account.getMessage();
		if(cmd.equals("upload"))
			return uploadFile();
		else if(cmd.equals("finish"))
			return finishUpload();
		else return Util.errorReport("Unknown command in File");
	}
	
	private Boolean uploadFile(){
		int room_id = Integer.valueOf(account.getMessage());
		String filepath = account.getMessage();
		FileChat f = new FileChat(filepath,room_id,account.id);
		FileTable.insert(f);
		account.sendMessage(new String[]{"file","upload",f.filepath,f.encryptedFileName,f.id+""});
		return true;
	}
	
	private Boolean finishUpload(){
		int file_id = Integer.valueOf(account.getMessage());
		FileChat f = FileTable.find(file_id);
		f.uploaded=1;
		f.save();
		for(Account a:RoomTable.accounts(f.room_id))
			if(a.id==account.id)continue;
			else if(Server.accountMap.get(a.id)!=null)
				Server.accountMap.get(a.id).sendMessage(new String[]{"file","download",f.room_id+"",f.fileName,FileTable.find(file_id).encryptedFileName});
		return true;
	}
}
