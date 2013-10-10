/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TaipeiHot.JazzChat.UI;

import TaipeiHot.JazzChat.Client.Client;
import TaipeiHot.JazzChat.User;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * 
 * @author Paul
 */
public class RoomWindow extends javax.swing.JFrame {

	/**
	 * Creates new form RoomWindow
	 */

	public RoomWindow(int _roomId, String _roomName,
			ArrayList<String> _userNameList) {
		initComponents();
		roomId = _roomId;
	}

	public RoomWindow(int _roomId) {
		initComponents();
		roomId = _roomId;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jLayeredPane1 = new javax.swing.JLayeredPane();
		jScrollPane2 = new javax.swing.JScrollPane();
		showText = new javax.swing.JTextArea();
		jScrollPane1 = new javax.swing.JScrollPane();
		typeText = new javax.swing.JTextArea();
		sendTextButton = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setBackground(new java.awt.Color(102, 153, 255));

		jLayeredPane1.setBackground(new java.awt.Color(102, 204, 255));

		showText.setEditable(false);
		showText.setBackground(new java.awt.Color(153, 204, 255));
		showText.setColumns(20);
		showText.setRows(5);
		showText.setBorder(new javax.swing.border.SoftBevelBorder(
				javax.swing.border.BevelBorder.RAISED, null, null, null,
				new java.awt.Color(51, 51, 51)));
		showText.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
		jScrollPane2.setViewportView(showText);

		jScrollPane2.setBounds(20, 10, 390, 230);
		jLayeredPane1.add(jScrollPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);

		typeText.setBackground(new java.awt.Color(153, 204, 255));
		typeText.setColumns(20);
		typeText.setRows(2);
		typeText.setBorder(new javax.swing.border.SoftBevelBorder(
				javax.swing.border.BevelBorder.RAISED, null, null,
				new java.awt.Color(0, 0, 0), new java.awt.Color(0, 51, 255)));
		typeText.addKeyListener(new java.awt.event.KeyAdapter() {
			@Override
			public void keyTyped(java.awt.event.KeyEvent evt) {
				typeTextKeyTyped(evt);
			}
		});
		jScrollPane1.setViewportView(typeText);

		jScrollPane1.setBounds(10, 250, 334, 48);
		jLayeredPane1.add(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);

		sendTextButton.setText("jButton1");
		sendTextButton.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseReleased(java.awt.event.MouseEvent evt) {
				sendTextButtonMouseReleased(evt);
			}
		});
		sendTextButton.setBounds(350, 250, 170, 50);
		jLayeredPane1.add(sendTextButton,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jLayeredPane1,
								javax.swing.GroupLayout.PREFERRED_SIZE, 508,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(52, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addGap(18, 18, 18)
						.addComponent(jLayeredPane1,
								javax.swing.GroupLayout.PREFERRED_SIZE, 309,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(45, Short.MAX_VALUE)));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void sendTextButtonMouseReleased(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_sendTextButtonMouseReleased
		// TODO add your handling code here:
		send();

	}// GEN-LAST:event_sendTextButtonMouseReleased

	private void typeTextKeyTyped(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_typeTextKeyTyped
		// TODO add your handling code here:
		// int a=evt.getKeyChar();
		// System.out.printf("%c %d\n", a,KeyEvent.VK_ENTER);
		if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
			send();
		}
	}// GEN-LAST:event_typeTextKeyTyped

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed"
		// desc=" Look and feel setting code (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase
		 * /tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(RoomWindow.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(RoomWindow.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(RoomWindow.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(RoomWindow.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new RoomWindow(1).setVisible(true);
			}
		});
	}

	/* funcrions and parameters by paul */
	private final int roomId;
	private ArrayList<String> userNameList;
	private String roomName;

	public void roomNameChange(String _roomName) {
		roomName = _roomName;
	}

	public void roomUserNameListChange(ArrayList<String> _userNameList) {
		userNameList = _userNameList;
	}
        public void addUser(User user){
            userNameList.add(user.getNickname());
        }
	public void showMessage(String name, String text) {
		showText.append(name + ":" + text + "\n");
	}

	private void send() {
		
                if (!typeText.getText().equals("")) {
			Client.sendMessage(roomId,typeText.getText());
                        showMessage(Client.user.getNickname(), typeText.getText());
			typeText.setText("");
		}
	}
        
	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JLayeredPane jLayeredPane1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JButton sendTextButton;
	private javax.swing.JTextArea showText;
	private javax.swing.JTextArea typeText;
	// End of variables declaration//GEN-END:variables
}
