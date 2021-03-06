/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TaipeiHot.JazzChat.UI;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.UIManager;

import TaipeiHot.JazzChat.User;
import TaipeiHot.JazzChat.Util;
import TaipeiHot.JazzChat.Client.Client;

/**
 * 
 * @author Paul
 */
public class MainWindow extends javax.swing.JFrame {

	/**
	 * Creates new form FreindList
	 */
	BufferedImage image;

	@Override
	public void paintComponents(Graphics g) {

		super.paintComponents(g); // To change body of generated methods, choose
									// Tools | Templates.
		int x = (getWidth() - image.getWidth()) / 2;
		int y = (getHeight() - image.getHeight()) / 2;
		g.drawImage(image, x, y, this);
	}

	public MainWindow() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			// URL ur2 = new
			// URL("ftp://nmlab198:taipeihot@140.112.18.198/jizzer2.jpg");
			// icon=Util.url2Icon("ftp://nmlab198:taipeihot@140.112.18.198/jizzer2.jpg",50,50);
			/*
			 * Image tmpImage=icon.getImage(); Image tmp=; icon=new
			 * ImageIcon(tmp);
			 */
			// Image a=new Image(new
			// URL("http://3.bp.blogspot.com/-CpxmJu3Km3k/UMPhfSoHaQI/AAAAAAAAF0Y/H5iQtXUZJuQ/s1600/Fire+lion.jpg"));
			// friendModel.addElement("tesing");
			initComponents();
			URL url = new URL(
					"http://3.bp.blogspot.com/-CpxmJu3Km3k/UMPhfSoHaQI/AAAAAAAAF0Y/H5iQtXUZJuQ/s1600/Fire+lion.jpg");

			mouseListener = new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent mouseEvent) {
					JList theList = (JList) mouseEvent.getSource();
					if (mouseEvent.getClickCount() == 2) {
						int index = theList.locationToIndex(mouseEvent
								.getPoint());
						if (index >= 0) {
							// Util.errorReport("lala");
							Client.openPrivateRoom(userList.get(index).id);
							System.out.printf("%d\n", userList.get(index).id);
						}
					}
				}
			};
			friendList.addMouseListener(mouseListener);
			friendList.setCellRenderer(new ObjectCellRender());

                        mouseListener2 = new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent mouseEvent) {
					JList theList = (JList) mouseEvent.getSource();
					if (mouseEvent.getClickCount() == 2) {
						int index = theList.locationToIndex(mouseEvent
								.getPoint());
						if (index >= 0) {
							// Util.errorReport("lala");
							AcceptMap.get(pendingList.get(index).id)
									.setVisible(true);

						}
					}
				}
			};
                        
		} catch (Exception e) {
		}
		// this.repaint();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        addFirendButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        friendList = new javax.swing.JList();
        afwButton = new javax.swing.JToggleButton();
        photoBtn = new javax.swing.JButton();
        onlineBtn = new javax.swing.JButton();
        multiChat = new javax.swing.JToggleButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 0, 51));
        setIconImages(null);
        setPreferredSize(new java.awt.Dimension(400, 570));
        setResizable(false);

        addFirendButton.setText("+");
        addFirendButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addFirendButtonMouseClicked(evt);
            }
        });
        addFirendButton.setBounds(290, 490, 71, 23);
        jLayeredPane1.add(addFirendButton, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(275, 150));

        friendList.setBackground(new java.awt.Color(0, 51, 51));
        friendList.setModel(friendModel);
        friendList.setPreferredSize(new java.awt.Dimension(275, 150));
        jScrollPane1.setViewportView(friendList);

        jScrollPane1.setBounds(50, 130, 320, 350);
        jLayeredPane1.add(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        afwButton.setText("Wating Friend");
        afwButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                afwButtonMouseClicked(evt);
            }
        });
        afwButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                afwButtonActionPerformed(evt);
            }
        });
        afwButton.setBounds(50, 490, 99, 23);
        jLayeredPane1.add(afwButton, javax.swing.JLayeredPane.DEFAULT_LAYER);

        photoBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TaipeiHot/JazzChat/UI/default_head_piture.jpg"))); // NOI18N
        photoBtn.setPreferredSize(new java.awt.Dimension(100, 100));
        photoBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                photoBtnMouseClicked(evt);
            }
        });
        photoBtn.setBounds(50, 20, 100, 100);
        jLayeredPane1.add(photoBtn, javax.swing.JLayeredPane.DEFAULT_LAYER);

        onlineBtn.setBackground(new java.awt.Color(255, 0, 0));
        onlineBtn.setText("ON/OFF");
        onlineBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onlineBtnMouseClicked(evt);
            }
        });
        onlineBtn.setBounds(290, 90, 73, 23);
        jLayeredPane1.add(onlineBtn, javax.swing.JLayeredPane.DEFAULT_LAYER);

        multiChat.setText("MultiChat");
        multiChat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                multiChatMouseClicked(evt);
            }
        });
        multiChat.setBounds(290, 50, 80, 23);
        jLayeredPane1.add(multiChat, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TaipeiHot/JazzChat/UI/Tech-Help-Screen-Wallpaper.jpg"))); // NOI18N
        jLabel1.setText("jLabel1");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel1.setPreferredSize(new java.awt.Dimension(500, 800));
        jLabel1.setBounds(0, 0, 420, 540);
        jLayeredPane1.add(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void multiChatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_multiChatMouseClicked
        // TODO add your handling code here:
        Client.openPublicRoom("");
        
    }//GEN-LAST:event_multiChatMouseClicked

	private void afwButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_afwButtonActionPerformed
		// TODO add your handling code here:
		// acceptFriendWindow.setVisible(true);
	}// GEN-LAST:event_afwButtonActionPerformed

	private void afwButtonMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_afwButtonMouseClicked
		// TODO add your handling code here:
		if (afwIsOpen) {
			acceptFriendWindow.setVisible(false);
			afwIsOpen = false;
		} else {
			acceptFriendWindow.setVisible(true);
			afwIsOpen = true;
		}

	}// GEN-LAST:event_afwButtonMouseClicked

	private void addFirendButtonMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_addFirendButtonMouseClicked
		// TODO add your handling code here:
		addFriendDialog.setVisible(true);
	}// GEN-LAST:event_addFirendButtonMouseClicked

	private boolean online = false;

	private void onlineBtnMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_onlineBtnMouseClicked
		// TODO add your handling code here:
		if (online) {
			Client.setVisible(false);
			online = false;
		} else {
			Client.setVisible(true);
			online = true;
		}
	}// GEN-LAST:event_onlineBtnMouseClicked

	private void photoBtnMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_photoBtnMouseClicked
		// TODO add your handling code here:
                StatusChange s=new StatusChange(Client.user.getNickname(),Client.user.getStatus(),Util.url2Icon(Client.user.getProfilePicUrl(), 95  , 95));
                s.setVisible(true);
	}// GEN-LAST:event_photoBtnMouseClicked

	private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jPanel2MouseClicked
		System.out.println("hahah");
		// TODO add your handling code here:
	}// GEN-LAST:event_jPanel2MouseClicked

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
			java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					new MainWindow().setVisible(true);
				} catch (Exception ex) {
					Logger.getLogger(MainWindow.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
		});
	}

	/* functions and parameters by paul */
	public Map<Integer, RoomWindow> roomWindowMap = new HashMap<Integer, RoomWindow>();
	public Map<Integer, AcceptFriendDialog> AcceptMap = new HashMap<Integer, AcceptFriendDialog>();
	FriendNameAndStatus f = new FriendNameAndStatus();
	LoginDialog loginDialog = new LoginDialog(this, true);
	RegisterDialog registerDialog = new RegisterDialog(this, true);
	AddFriendDialog addFriendDialog = new AddFriendDialog(this, true);
	AcceptFriendWindow acceptFriendWindow = new AcceptFriendWindow();
	public ArrayList<User> userList = new ArrayList<User>();
	private final ArrayList<String> friendName = new ArrayList<String>();
	DefaultListModel friendModel = new DefaultListModel();
	MouseListener mouseListener;
	MouseListener mouseListener2;
        MouseListener mouseListener3;
        MouseListener mouseListener4;
	ImageIcon icon;
        public static ImageIcon godIcon;
        public static ImageIcon ChatIcon;
	boolean afwIsOpen = false;

	// JList friendLsit=new JList();
	public void loginSuccess() throws MalformedURLException, IOException {
		loginDialog.setVisible(false);
		setVisible(true);
		photoBtn.setIcon(Util.url2Icon(Client.user.getProfilePicUrl(), 95  , 95));
                ChatIcon=Util.url2Icon(Client.user.getProfilePicUrl(), 50  , 50);
                godIcon=Util.url2Icon("ftp://nmlab198:taipeihot@140.112.18.198/godIcon.png", 50,50);
		// acceptFriendWindow.setVisible(true);
	}

	public void loginFail(String messege) {
		loginDialog.setErrorMessege(messege);
	}

	public void loginShow() {
		try {
			// reg.setVisible(true);
			loginDialog.setVisible(true);
			// this.add(b);
			// System.out.print("done");
			// this.add(f);
			// System.out.print("strat");

			// Point p=f.getLocation();
			// System.out.printf("%d %d\n", p.x,p.y);
			// f.setVisible(true);
		} catch (Exception e) {
			// System.out.print("haha");
		}
	}

	public void registerSuccess() {
		registerDialog.setVisible(false);
		System.out.println("register close");
		loginDialog.setVisible(true);
		System.out.println("login show");
	}

	public void registerFail(String messege) {
		registerDialog.setErrorMessege(messege);
	}

	public void registerShow() {
		registerDialog.setVisible(true);
	}

	public void acceptFriendShow(int userId, String userName, String greeting) {
		AcceptFriendDialog acceptFriendDialog = new AcceptFriendDialog(this,
				true, userId);
		acceptFriendDialog.setFriend(userName, greeting);
		AcceptMap.put(userId, acceptFriendDialog);
		acceptFriendDialog.setVisible(true);
	}

	public void accepFriendSccess(int userId, String message) {
		AcceptMap.get(userId).setVisible(false);
	}

	public void accepFriendFail(int userId, String message) {

	}

	public void addFriendSuccess(String message) {
		addFriendDialog.setVisible(false);
	}

	public void addFriendFail(String message) {
		addFriendDialog.setErrorMessage(message);
	}

	public RoomWindow getRoomById(int roomId) {
		if (roomWindowMap.get(roomId) == (null)) {
			return newRoom(roomId);
		} else
			return roomWindowMap.get(roomId);
	}

	public RoomWindow newRoom(int roomId, String _roomName,
			ArrayList<String> _userNameList) {
		RoomWindow room = new RoomWindow(roomId, _roomName, _userNameList);
		roomWindowMap.put(roomId, room);
		room.setVisible(true);
		return room;
	}

	public RoomWindow newRoom(int roomId) {
		RoomWindow room = new RoomWindow(roomId);
		// add(room);
		roomWindowMap.put(roomId, room);
		room.setVisible(true);
                
		return room;
	}
        
        
        public Map<Integer, PublicRoom> publicRoomMap = new HashMap<Integer, PublicRoom>();
        public PublicRoom newPublicRoom(int roomId) {
		PublicRoom room = new PublicRoom(roomId);
		// add(room);
		publicRoomMap.put(roomId, room);
		room.setVisible(true);
		return room;
	}
	public void closeDialog() {
		try {
			loginDialog.setVisible(false);
		} catch (Exception e) {
		}

	}

	public static Map<Integer, FriendNameAndStatus> friendStatusMap = new HashMap<Integer, FriendNameAndStatus>();
	public static Map<Integer, ImageIcon> iconMap = new HashMap<Integer, ImageIcon>();

	public void friendShow(User user) throws MalformedURLException, IOException {
		userList.add(user);
		ImageIcon tmpIcon = Util.url2Icon(user.getProfilePicUrl(), 50, 50);
		iconMap.put(user.id, tmpIcon);
		FriendNameAndStatus newStatus = new FriendNameAndStatus(
				user.getNickname(), tmpIcon,user.getStatus());
		friendStatusMap.put(user.id, newStatus);
		friendModel.addElement(newStatus);
		// friendName.add(user.getNickname());
		// friendList=new JList(friendModel);
		// friendList.addMouseListener(mouseListener);
	}

	private final ArrayList<User> pendingList = new ArrayList<User>();
	private final DefaultListModel pendingModel = new DefaultListModel();

	public void pendingListShow(User user) {
		pendingList.add(user);
		pendingModel.addElement(user.getNickname());
		// friendName.add(user.getNickname());
		acceptFriendWindow.friendListChange(pendingModel, mouseListener2);
		// acceptFriendWindow.friendList=new JList(pendingModel);
		// acceptFriendWindow.friendList.addMouseListener(mouseListener);
	}

	public void friendListShow(Map<Integer, User> user) {

	}

	public void setOnlineById(Integer userId) {
		Util.errorReport(userId.toString() + "online\n");
		friendStatusMap.get(userId).online(true);
		this.repaint();
	}

	public void setOfflineById(Integer userId) {
		Util.errorReport(userId.toString() + "offline\n");
		friendStatusMap.get(userId).online(false);
		this.repaint();
	}

	public void changePhoto() {
		String url = Client.user.getProfilePicUrl();
		photoBtn.setIcon(Util.url2Icon(url, 95, 95));
		repaint();
	}

	public void changePhotoById(int userId) {
		friendStatusMap.get(userId).changeIcon(
				Client.userSet.get(userId).getProfilePicUrl());
		iconMap.put(userId, Util.url2Icon(Client.userSet.get(userId)
				.getProfilePicUrl(), 95, 95));
		repaint();
	}
        public void statusPhoto() {
		String url = Client.user.getProfilePicUrl();
		photoBtn.setIcon(Util.url2Icon(url, 95, 95));
                ChatIcon=Util.url2Icon(Client.user.getProfilePicUrl(), 50  , 50);
		repaint();
	}

	public void changeStatusById(int userId,String status) {
		friendStatusMap.get(userId).chanegeStatus(status);
		repaint();
	}
        public void changeNicknameById(int userId,String status) {
		friendStatusMap.get(userId).changeNickname(status);
		repaint();
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addFirendButton;
    private javax.swing.JToggleButton afwButton;
    public javax.swing.JList friendList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToggleButton multiChat;
    private javax.swing.JButton onlineBtn;
    private javax.swing.JButton photoBtn;
    // End of variables declaration//GEN-END:variables
}
