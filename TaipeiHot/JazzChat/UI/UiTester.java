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
        RoomWindow a=new RoomWindow();
        a.show(true);
        a.getMessege("hahaha");
        MainWindow xx=new MainWindow();
        xx.show();
        String aaa="lalal";
        //xx.closeDialog();
        while(true)
        {
            BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
            String line=buffer.readLine();
            a.getMessege(line);
        }
        
    }
}
