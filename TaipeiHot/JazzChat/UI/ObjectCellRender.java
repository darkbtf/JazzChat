/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TaipeiHot.JazzChat.UI;

import TaipeiHot.JazzChat.Util;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Random;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Paul
 */
public class ObjectCellRender extends JLabel implements ListCellRenderer{



    private static final long serialVersionUID = 1L;
    private JPanel pnl = new JPanel();
    private Random rnd = new Random();

    private JCheckBox checkBox;

    public ObjectCellRender() throws MalformedURLException, IOException {
        setOpaque(true);
        setLayout(new BorderLayout(3, 3));
        pnl = new JPanel(new GridLayout(0, 1, 0, 0));
        pnl.setBorder(new EmptyBorder(0, 35, 0, 0));
        add(pnl, BorderLayout.SOUTH); 
        
        //super.getListeners(null);
    }
    protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
       
        int selectedIndex = index;
       pnl.removeAll();
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
    
         
        this.setPreferredSize(new Dimension(300 , 200));
        //Util.errorReport(((FriendNameAndStatus)value).name);
        //Util.errorReport(value.toString());
        pnl.add(new FriendNameAndStatus(value.toString()));
        //pnl.add((FriendNameAndStatus)value);
        //add((FriendNameAndStatus)value);
        //FriendNameAndStatus a=new FriendNameAndStatus(value.toString());
        return this;
        //Set the icon and text.  If icon was null, say so.
        //Image image;

        //return super.getListCellRendererComponent(list, value, index, isSelected,
           //     cellHasFocus);
        //FriendNameAndStatus renderer = (FriendNameAndStatus) defaultRenderer.getListCellRendererComponent(list, value, index,
        //isSelected, cellHasFocus);
        //renderer.setBorder(cellHasFocus ? focusBorder : noFocusBorder);
        //return renderer;
    }
}
