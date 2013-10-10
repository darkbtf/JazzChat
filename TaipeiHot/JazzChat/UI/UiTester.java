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
		mainWindow.newRoom(123, "me", userName);
		mainWindow.newRoom(234, "me", userName);
		mainWindow.loginShow();
                mainWindow.loginSuccess();
		// xx.closeDialog();
		while (true) {

			BufferedReader buffer = new BufferedReader(new InputStreamReader(
					System.in));
			String line = buffer.readLine();
			mainWindow.roomWindowMap.get(123)
					.showMessage("test username", line);
		}

	}
}
