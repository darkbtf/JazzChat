/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TaipeiHot.JazzChat.UI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * 
 * @author Paul
 */
public class UiTester {
	public static void main(String args[]) throws IOException {
		MainWindow mainWindow = new MainWindow();
		ArrayList<String> userName = new ArrayList<String>();
		//mainWindow.newRoom(123, "me", userName);
		//mainWindow.newRoom(234, "me", userName);
		//mainWindow.loginShow();
                //mainWindow.loginSuccess();
                //for(int i=0;i<20;i++)mainWindow.friendModel.addElement(i);
                mainWindow.setVisible(true);
                //mainWindow.friendModel.addElement(new FriendNameAndStatus("1"));
                //mainWindow.friendModel.addElement(new FriendNameAndStatus("22"));
                //mainWindow.friendModel.addElement(new FriendNameAndStatus("3"));
                //mainWindow.friendModel.addElement(new FriendNameAndStatus("4"));
                //mainWindow.friendList.setModel(mainWindow.friendModel);
                //mainWindow.friendModel.addElement(new FriendNameAndStatus("lala"));
                //mainWindow.friendModel.addElement(new FriendNameAndStatus("lala"));
		// xx.closeDialog();
		while (true) {

			BufferedReader buffer = new BufferedReader(new InputStreamReader(
					System.in));
			String line = buffer.readLine();
			mainWindow.roomWindowMap.get(123)
					.showMessage(-1, line);
		}

	}
}
