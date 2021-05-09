package internet;

/*
 * Authentication.java ver 1.0
 *
 * Created on 2003/07/06, 15:56
 */

/**
 *
 * @author  thiro
 */
import javax.swing.JOptionPane;
import javax.swing.JApplet;
import java.net.URL;
import java.net.MalformedURLException;

public class Authentication extends javax.swing.JApplet {
    
    /** Creates a new instance of Authentication */
    public Authentication() {
    }
    /** Initializes the applet test */
    public void init() {
        initComponents();
    }
    
    /** This method is called from within the init() method to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        ID_label = new javax.swing.JLabel();
        ID_text_field = new javax.swing.JTextField();
        Passwd_label = new javax.swing.JLabel();
        Passwd_field = new javax.swing.JPasswordField();
        OK_button = new javax.swing.JButton();
        Clear_button = new javax.swing.JButton();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setName("main_panel");
        ID_label.setText("ID");
        getContentPane().add(ID_label, new java.awt.GridBagConstraints());
        ID_label.getAccessibleContext().setAccessibleName("ID_label");

        ID_text_field.setColumns(20);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        getContentPane().add(ID_text_field, gridBagConstraints);

        Passwd_label.setText("Passwd");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        getContentPane().add(Passwd_label, gridBagConstraints);

        Passwd_field.setColumns(20);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        getContentPane().add(Passwd_field, gridBagConstraints);

        OK_button.setText("OK");
        OK_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OK_buttonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        getContentPane().add(OK_button, gridBagConstraints);

        Clear_button.setText("Clear");
        Clear_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Clear_buttonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        getContentPane().add(Clear_button, gridBagConstraints);

    }

    private void OK_buttonActionPerformed(java.awt.event.ActionEvent evt) {
        // Add your handling code here:
        if(ID_text_field.getText().equals("test")){
            if(Passwd_field.getText().equals("Test0#")){
               JOptionPane.showMessageDialog(null,"SUCCESS!");
                try{ 
                    ((JApplet)this).getAppletContext().showDocument(
                    new URL("http://members.home.ne.jp/hirotate"));
                } catch (MalformedURLException er) {} 
            }
            else{
               JOptionPane.showMessageDialog(null,"password not correct!");
            }
        }
        else{
            JOptionPane.showMessageDialog(null,"ID not correct!");
       }
    }
 
    
    private void Clear_buttonActionPerformed(java.awt.event.ActionEvent evt) {
        // Add your handling code here:
        ID_text_field.setText("");
        Passwd_field.setText("");
    }

    public void repaint() {
    }    
    
    
    // Variables declaration - do not modify
    private javax.swing.JButton Clear_button;
    private javax.swing.JLabel ID_label;
    private javax.swing.JTextField ID_text_field;
    private javax.swing.JButton OK_button;
    private javax.swing.JPasswordField Passwd_field;
    private javax.swing.JLabel Passwd_label;
    // End of variables declaration
     
}
