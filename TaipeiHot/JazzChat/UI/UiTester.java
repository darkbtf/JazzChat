/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TaipeiHot.JazzChat.UI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Paul
 */
public class UiTester {
    public static void main(String args[]) throws IOException{
        MainWindow mainWindow=new MainWindow();
        mainWindow.newRoom(123);
        mainWindow.newRoom(234);
        mainWindow.loginShow();
        //xx.closeDialog();
        while(true)
        {
            
            BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
            String line=buffer.readLine();
            mainWindow.roomWindowMap.get(123).showMessege("test username",line);
        }
        
    }
}
