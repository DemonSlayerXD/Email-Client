/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package email;

import java.awt.Color;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import jdk.nashorn.internal.ir.ContinueNode;

/**
 *
 * @author Chris
 */
public class ViewMail extends javax.swing.JFrame {
    static String mailid;
    static String pass;
    static String mess="";

    /**
     * Creates new form Mail
     */
    public ViewMail(String mailid,String pass) {
        this.mailid=mailid;
        this.pass=pass;
        initComponents();
    }
    public static void downloadEmails(String protocol, String host, String port) {
        try
        {
        Properties properties = new Properties();
        properties.put(String.format("mail.%s.host", protocol), host);
        properties.put(String.format("mail.%s.port", protocol), port);
 
        // SSL setting
        properties.setProperty(
                String.format("mail.%s.socketFactory.class", protocol),
                "javax.net.ssl.SSLSocketFactory");
        properties.setProperty(
                String.format("mail.%s.socketFactory.fallback", protocol),
                "false");
        properties.setProperty(
                String.format("mail.%s.socketFactory.port", protocol),
                String.valueOf(port));
//        properties.put(String.format("mail.%s.starttls.enable",protocol), "true");
        Session session = Session.getDefaultInstance(properties);
 
        
            // connects to the message store
            Store store = session.getStore(protocol);
            store.connect(host,mailid, pass);
 
            // opens the inbox folder
            Folder folderInbox = store.getFolder("INBOX");
            folderInbox.open(Folder.READ_ONLY);
 
            // fetches new messages from server
            Message[] messages = folderInbox.getMessages();
 
            for (int i = 0; i < messages.length; i++) {
                Message msg = messages[i];
                Address[] fromAddress = msg.getFrom();
                String from = fromAddress[0].toString();
                String subject = msg.getSubject();
                String toList = parseAddresses(msg
                        .getRecipients(Message.RecipientType.TO));
                String ccList = parseAddresses(msg
                        .getRecipients(Message.RecipientType.CC));
                String sentDate = msg.getSentDate().toString();
 
                // print out details of each message
                mess=mess.concat("\nMessage #" + (i + 1) + ":");
                mess=mess.concat("\nFrom: " + from);
                mess=mess.concat("\nTo: " + toList);
                mess=mess.concat("\nCC: " + ccList);
                mess=mess.concat("\nSubject: " + subject);
                mess=mess.concat("\nSent Date: " + sentDate);
                mess=mess.concat("\nMessage: ");
                writePart(msg);
            }
 
            // disconnect
            folderInbox.close(false);
//            store.close();
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Exception Occured");
        }
    }
    private static String parseAddresses(Address[] address) {
        String listAddress = "";
 
        if (address != null) {
            for (int i = 0; i < address.length; i++) {
                listAddress += address[i].toString() + ", ";
            }
        }
        if (listAddress.length() > 1) {
            listAddress = listAddress.substring(0, listAddress.length() - 2);
        }
 
        return listAddress;
    }
    public static void writePart(Part p) throws Exception {

      //check if the content is plain text
      if (p.isMimeType("text/plain")) {
        mess=mess.concat((String) p.getContent());
      } 
      //check if the content has attachment
      else if (p.isMimeType("multipart/*")) {
         Multipart mp = (Multipart) p.getContent();
         int count = mp.getCount();
         for (int i = 0; i < count; i++)
            writePart(mp.getBodyPart(i));
      } 
      //check if the content is a nested message
      else if (p.isMimeType("message/rfc822")) {
         writePart((Part) p.getContent());
      }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        messArea = new javax.swing.JTextArea();
        serverBox = new javax.swing.JComboBox<>();
        refreshServer = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        searchBox = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(null);

        messArea.setColumns(20);
        messArea.setRows(5);
        jScrollPane1.setViewportView(messArea);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(20, 100, 510, 330);

        serverBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "POP3", "IMAP" }));
        serverBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serverBoxActionPerformed(evt);
            }
        });
        jPanel1.add(serverBox);
        serverBox.setBounds(150, 20, 70, 22);

        refreshServer.setText("Refresh");
        refreshServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshServerActionPerformed(evt);
            }
        });
        jPanel1.add(refreshServer);
        refreshServer.setBounds(250, 20, 90, 23);

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Select Server:");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(40, 20, 80, 20);

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Search:");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(40, 60, 50, 30);

        searchBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBoxActionPerformed(evt);
            }
        });
        jPanel1.add(searchBox);
        searchBox.setBounds(90, 60, 150, 30);

        searchButton.setText("Search");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });
        jPanel1.add(searchButton);
        searchButton.setBounds(260, 60, 70, 23);

        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });
        jPanel1.add(backButton);
        backButton.setBounds(430, 40, 55, 23);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/email/Background (2).jpg"))); // NOI18N
        jPanel1.add(jLabel3);
        jLabel3.setBounds(0, 0, 550, 450);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void refreshServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshServerActionPerformed
        // TODO add your handling code here:
        messArea.setRows(0);
        String ser=(String)serverBox.getSelectedItem();
        if("POP3".equals(ser))
        {
            String protocol = "pop3";
            String host = "pop.gmail.com";
            String port = "995";
            downloadEmails(protocol, host, port);
            messArea.setText(mess);
        }
        else
        {   
            String protocol = "imap";
            String host = "imap.gmail.com";
            String port = "993";
            downloadEmails(protocol, host, port);
            messArea.setText(mess);
        }
    }//GEN-LAST:event_refreshServerActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        // TODO add your handling code here:
        messArea.getHighlighter().removeAllHighlights();
        Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter( Color.cyan );
        String text=messArea.getText();
        String searchWord=searchBox.getText().trim();
        if(searchBox.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Enter the search text");
        }
        else
        {
            int offset = text.indexOf(searchWord);
            int length = searchWord.length();
            while ( offset != -1)
            {
                try
                {
                    messArea.getHighlighter().addHighlight(offset, offset + length, painter);
                    offset = text.indexOf(searchWord, offset+1);
                }
                catch(Exception ble) { JOptionPane.showMessageDialog(null, "Exception Occured"); }
            }
        }
    }//GEN-LAST:event_searchButtonActionPerformed

    private void searchBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchBoxActionPerformed

    private void serverBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serverBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_serverBoxActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        // TODO add your handling code here:
        Mail m=new Mail(mailid, pass);
        m.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_backButtonActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(ViewMail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewMail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewMail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewMail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewMail(mailid,pass).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea messArea;
    private javax.swing.JButton refreshServer;
    private javax.swing.JTextField searchBox;
    private javax.swing.JButton searchButton;
    private javax.swing.JComboBox<String> serverBox;
    // End of variables declaration//GEN-END:variables
}
