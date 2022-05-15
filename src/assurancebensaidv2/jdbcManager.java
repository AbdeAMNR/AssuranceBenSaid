/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assurancebensaidv2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;

/**
 *
 * @author AbdeAMNR
 */
public class jdbcManager {

    public HashMap<String, String> item = new HashMap<>();
    public static Connection conn;
    private Statement s;
    private ResultSet rs;

    public jdbcManager() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "Erreur Driver  ", "Etat de L'opération", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            Logger.getLogger(jdbcManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        //===================================================================
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agenceassurance", "amanar", "12345");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            //  javax.swing.JOptionPane.showMessageDialog(null, "Erreur Connection Base de donnée", "Etat de L'operation", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public Connection getCnx() {
        return conn;
    }

    public ResultSet select(String req) {
        s = null;
        rs = null;
        try {
            s = conn.createStatement();
            rs = s.executeQuery(req);
        } catch (SQLException ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "Erreur get data", "Etat de L'op�ration", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            Logger.getLogger(jdbcManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    public int update(String req) {
        s = null;
        int i = 0;
        try {
            s = conn.createStatement();
            i = s.executeUpdate(req);
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return i;
    }

    public final void fillCbx(String Query, JComboBox cbx, String column) {
        this.item.clear();
        s = null;
        rs = null;
        try {
            s = conn.createStatement();
            rs = s.executeQuery(Query);
            cbx.removeAllItems();
            while (rs.next()) {
                item.put(rs.getString(column), Integer.toString(rs.getInt(1)));
                cbx.addItem(rs.getString(column));
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public final void fillCombobox(String Query, JComboBox cbx, String displayValue, String selectedValue) {
        this.item.clear();
        s = null;
        rs = null;
        try {
            s = conn.createStatement();
            rs = s.executeQuery(Query);
            cbx.removeAllItems();
            while (rs.next()) {
                item.put(rs.getString(displayValue), rs.getString(selectedValue));
                cbx.addItem(rs.getString(displayValue));
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public void GeState() {
        try {
            String q = "SELECT sum(primeReele) AS tPr,sum(reglement) AS tR ,sum(perte) AS tP,sum(c.creditSum) AS  tC "
                    + "FROM quittance q JOIN credit c ON q.numQuitt=c.numQuitt "
                    + "WHERE q.dateQuitt=CURDATE();";
            rs = null;
            rs = this.select(q);

            if (rs.first()) {
                PnlHome.lblPR.setText(rs.getString("tPr"));
                PnlHome.lblRglment.setText(rs.getString("tR"));
                PnlHome.lblP.setText(rs.getString("tP"));
                PnlHome.lblCredit.setText(rs.getString("tC"));
            } else {
                PnlHome.lblPR.setText("0.0");
                PnlHome.lblRglment.setText("0.0");
                PnlHome.lblP.setText("0.0");
                PnlHome.lblCredit.setText("0.0");
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public void saveOperation(String opt, String user) {
        String q = "INSERT INTO operation (libelOpt, uName) VALUES ('" + opt + "', '" + user + "');";
        System.out.println(q);
        this.update(q);
    }

}
