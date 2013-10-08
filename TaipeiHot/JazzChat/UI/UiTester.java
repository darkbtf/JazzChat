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
        RoomWindow a=new RoomWindow(2);
        a.show(true);
        a.getMessege("tt","hahaha");
        MainWindow xx=new MainWindow();
        xx.newRoom(123);
        xx.newRoom(234);

        xx.loginShow();
        String aaa="lalal";
        //xx.closeDialog();
        while(true)
        {
            
            BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
            String line=buffer.readLine();
            a.getMessege("tt",line);
        }
        
    }
}
