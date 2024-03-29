/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assurancebensaidv2;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author AbdeAMNR
 */
public class TryVersion extends javax.swing.JPanel {

    /**
     * Creates new form AboutFrame
     */
    public TryVersion() {
        initComponents();
  
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmdFB = new javax.swing.JPanel();
        lblFB = new javax.swing.JLabel();
        fbLogo = new javax.swing.JLabel();
        cmdMail = new javax.swing.JPanel();
        lblMAIL = new javax.swing.JLabel();
        mailLogo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();

        setBackground(new java.awt.Color(37, 149, 184));

        jPanel1.setBackground(new java.awt.Color(231, 71, 0));
        jPanel1.setMaximumSize(null);

        cmdFB.setBackground(new java.awt.Color(231, 71, 0));
        cmdFB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdFB.setMaximumSize(null);
        cmdFB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmdFBMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cmdFBMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cmdFBMouseExited(evt);
            }
        });

        lblFB.setBackground(new java.awt.Color(0, 0, 0));
        lblFB.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblFB.setForeground(new java.awt.Color(255, 255, 255));
        lblFB.setText("Retrouvez-moi sur Facebook");

        fbLogo.setForeground(new java.awt.Color(255, 255, 255));
        fbLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/facebook.png"))); // NOI18N

        javax.swing.GroupLayout cmdFBLayout = new javax.swing.GroupLayout(cmdFB);
        cmdFB.setLayout(cmdFBLayout);
        cmdFBLayout.setHorizontalGroup(
            cmdFBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cmdFBLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(fbLogo)
                .addGap(10, 10, 10)
                .addComponent(lblFB)
                .addGap(10, 10, 10))
        );
        cmdFBLayout.setVerticalGroup(
            cmdFBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cmdFBLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(cmdFBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(fbLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblFB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cmdMail.setBackground(new java.awt.Color(231, 71, 0));
        cmdMail.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdMail.setMaximumSize(null);
        cmdMail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmdMailMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cmdMailMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cmdMailMouseExited(evt);
            }
        });

        lblMAIL.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblMAIL.setForeground(new java.awt.Color(255, 255, 255));
        lblMAIL.setText("Envoyer un e-mail");

        mailLogo.setForeground(new java.awt.Color(255, 255, 255));
        mailLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/mail.png"))); // NOI18N

        javax.swing.GroupLayout cmdMailLayout = new javax.swing.GroupLayout(cmdMail);
        cmdMail.setLayout(cmdMailLayout);
        cmdMailLayout.setHorizontalGroup(
            cmdMailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cmdMailLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(mailLogo)
                .addGap(10, 10, 10)
                .addComponent(lblMAIL)
                .addGap(10, 10, 10))
        );
        cmdMailLayout.setVerticalGroup(
            cmdMailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cmdMailLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(cmdMailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(mailLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMAIL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setBackground(new java.awt.Color(231, 71, 0));

        jTextArea1.setEditable(false);
        jTextArea1.setBackground(new java.awt.Color(231, 71, 0));
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextArea1.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea1.setRows(4);
        jTextArea1.setText("Système de GESTION DE CAISSE\nVersion:  1.1.19\nCette application est developpé par Abderrahim AMANAR \nPour plus d'informations ou besoin d'aide, contactez-moi");
        jTextArea1.setCaretColor(new java.awt.Color(255, 255, 255));
        jTextArea1.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        jTextArea1.setEnabled(false);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel3.setBackground(new java.awt.Color(231, 71, 0));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("©2017 Designed and Developed by Abderrahim AMANAR");
        jLabel3.addHierarchyBoundsListener(new java.awt.event.HierarchyBoundsListener() {
            public void ancestorMoved(java.awt.event.HierarchyEvent evt) {
                jLabel3AncestorMoved(evt);
            }
            public void ancestorResized(java.awt.event.HierarchyEvent evt) {
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cmdFB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cmdMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmdFB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel3)
                .addGap(0, 0, 0))
        );

        jPanel2.setBackground(new java.awt.Color(37, 149, 184));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Entrez la clé que vous avez reçu sur l'achat du produit");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Cette fenêtre ne s'affichera pas pour la version enregistrée");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Après que vous achetez la licence, aucune installation supplémentaire n'est nécessaire. Toutes les données actuelles seront gardées");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(231, 71, 0));

        jButton1.setBackground(new java.awt.Color(231, 71, 0));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("Enregistrer maintenant");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(116, 116, 116)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField1)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel3AncestorMoved(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_jLabel3AncestorMoved
     }//GEN-LAST:event_jLabel3AncestorMoved

    private void cmdFBMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdFBMouseEntered
        cmdFB.setBackground(Color.WHITE);
        lblFB.setForeground(Color.BLACK);
        fbLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/facebook Focus.png")));

    }//GEN-LAST:event_cmdFBMouseEntered

    private void cmdFBMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdFBMouseExited
        cmdFB.setBackground(Color.decode("#E74700"));
        lblFB.setForeground(Color.WHITE);
        fbLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/facebook.png"))); // NOI18N
    }//GEN-LAST:event_cmdFBMouseExited

    private void cmdMailMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdMailMouseEntered
        cmdMail.setBackground(Color.WHITE);
        lblMAIL.setForeground(Color.BLACK);
        mailLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/mail Focus.png"))); // NOI18N
    }//GEN-LAST:event_cmdMailMouseEntered

    private void cmdMailMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdMailMouseExited
        cmdMail.setBackground(Color.decode("#E74700"));
        lblMAIL.setForeground(Color.WHITE);
        mailLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/mail.png"))); // NOI18N
    }//GEN-LAST:event_cmdMailMouseExited

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (jTextField1.getText().equals("HRYASU-HRYASU-HRYASU-HRYASU-HRYASU")) {
            if (!new File("src/resources/registered.bat").isFile()) {
                try {
                    FileWriter fw = new FileWriter("src/resources/registered.bat");
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(jTextField1.getText());
                    bw.close();
                    JOptionPane.showMessageDialog(null, "Votre application est enregistrée avec succès", "Enregistré", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "La clé de licence n'est pas valide", "Non enregistré", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cmdFBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdFBMouseClicked
        try {
            Desktop.getDesktop().browse(new URL("https://www.facebook.com/amanar.abderrahim").toURI());
        } catch (IOException | URISyntaxException e) {
            System.err.println(e);
        }


    }//GEN-LAST:event_cmdFBMouseClicked

    private void cmdMailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdMailMouseClicked
        try {
            Desktop desktop;
            if (Desktop.isDesktopSupported()
                    && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
                URI mailto = new URI("mailto:miinotek15@gmailcom?subject=Hello%20World");
                desktop.mail(mailto);
            } else {
                throw new RuntimeException("desktop doesn't support mailto; mail is dead anyway ;)");
            }

        } catch (IOException | RuntimeException | URISyntaxException e) {
            System.err.println(e);
        }


    }//GEN-LAST:event_cmdMailMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel cmdFB;
    private javax.swing.JPanel cmdMail;
    private javax.swing.JLabel fbLogo;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    public static final javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblFB;
    private javax.swing.JLabel lblMAIL;
    private javax.swing.JLabel mailLogo;
    // End of variables declaration//GEN-END:variables
}
