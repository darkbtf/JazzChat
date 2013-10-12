/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TaipeiHot.JazzChat.UI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Random;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

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
        super();
        //super.getListeners(null);
    }
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
       setOpaque(true);
       setLayout(new BorderLayout(3, 3));            
       add((Component)value);
    
            this.setPreferredSize(new Dimension(300 , 200));

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        //FriendNameAndStatus a=new FriendNameAndStatus(value.toString());
        
        //Set the icon and text.  If icon was null, say so.
        //Image image;

        return this;
    }
}
