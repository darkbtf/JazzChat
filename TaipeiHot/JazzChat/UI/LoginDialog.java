/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TaipeiHot.JazzChat.UI;

import TaipeiHot.JazzChat.Client.Client;
import java.awt.event.KeyEvent;

/**
 *
 * @author Paul
 */
public class LoginDialog extends javax.swing.JDialog {

    /**
     * Creates new form LoginDialog
     */
    public LoginDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    LoginDialog() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        accountTextField = new javax.swing.JTextField();
        passwordTextField = new javax.swing.JPasswordField();
        loginButton = new javax.swing.JButton();
        registerButton = new javax.swing.JButton();
        errorMessege = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        accountTextField.setBounds(80, 50, 186, 21);
        jLayeredPane1.add(accountTextField, javax.swing.JLayeredPane.DEFAULT_LAYER);

        passwordTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                passwordTextFieldKeyTyped(evt);
            }
        });
        passwordTextField.setBounds(80, 80, 186, 21);
        jLayeredPane1.add(passwordTextField, javax.swing.JLayeredPane.DEFAULT_LAYER);

        loginButton.setText("Login");
        loginButton.setToolTipText("");
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginButtonMouseClicked(evt);
            }
        });
        loginButton.setBounds(140, 120, 71, 23);
        jLayeredPane1.add(loginButton, javax.swing.JLayeredPane.DEFAULT_LAYER);

        registerButton.setText("Register");
        registerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registerButtonMouseClicked(evt);
            }
        });
        registerButton.setBounds(140, 150, 71, 23);
        jLayeredPane1.add(registerButton, javax.swing.JLayeredPane.DEFAULT_LAYER);

        errorMessege.setText("            ");
        errorMessege.setBounds(160, 180, 40, 20);
        jLayeredPane1.add(errorMessege, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TaipeiHot/JazzChat/UI/FireLionMain.jpg"))); // NOI18N
        jLabel1.setText("jLabel1");
        jLabel1.setBounds(0, 0, 350, 220);
        jLayeredPane1.add(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loginButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginButtonMouseClicked
        // TODO add your handling code here:
        String ID=accountTextField.getText();
        String pass=passwordTextField.getText();
        Client.userLogin(ID,pass);
    }//GEN-LAST:event_loginButtonMouseClicked

    private void registerButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerButtonMouseClicked
        // TODO add your handling code here:
        //this.setVisible(false);
        ((MainWindow)(getOwner())).registerShow();
    }//GEN-LAST:event_registerButtonMouseClicked

    private void passwordTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordTextFieldKeyTyped
        // TODO add your handling code here:
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            String ID=accountTextField.getText();
            String pass=passwordTextField.getText();
            Client.userLogin(ID,pass);
        }
    }//GEN-LAST:event_passwordTextFieldKeyTyped

    /**
     * @param args the command line arguments
     */
    public void setErrorMessege(String messege){
        errorMessege.setText(messege);
    }
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                LoginDialog dialog = new LoginDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField accountTextField;
    private javax.swing.JLabel errorMessege;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JButton loginButton;
    private javax.swing.JPasswordField passwordTextField;
    private javax.swing.JButton registerButton;
    // End of variables declaration//GEN-END:variables
}
