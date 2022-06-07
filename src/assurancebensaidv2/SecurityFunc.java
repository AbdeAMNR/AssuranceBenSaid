/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assurancebensaidv2;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author amnrLaptop
 */
public class SecurityFunc {

    public static jdbcManager cnx;
    public static String logIn;
    public static String userType;

    public SecurityFunc() {
        cnx = new jdbcManager();
    }

    public static void SuccessfullyOperation() {
        Timer blinkTimer = new Timer(200, new ActionListener() {
            int count = 0;
            int maxCount = 1;
            boolean on = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (count >= maxCount) {
                    MainForm.pnlFooter.setBackground(Color.decode("#5CB0CA"));
                    System.out.println("original footer color changed");
                    ((Timer) e.getSource()).stop();
                } else {
                    MainForm.pnlFooter.setBackground(Color.decode("#E74700"));
                    System.out.println(" footer color changed");
                    on = !on;
                    count++;
                }
            }
        });
        blinkTimer.start();
        System.out.println("color mehtode");
    }

    public static void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 15; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }
            if (width > 300) {
                width = 300;
            }
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }

    public static boolean isNullOrWhiteSpace(String str) {
        String chaine = str.trim();
        if (chaine.isEmpty() || chaine == null) {
            return true;
        }
        return false;
    }

    public static DefaultTableModel modelNoEditableCell() {
        DefaultTableModel model = new DefaultTableModel() {
            /* set every cell in the table to no editable */
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        return model;
    }

    public static final void waterMark(JTextField txt, String str, Color Focus, Color unFocus) {
        txt.setForeground(unFocus);
        txt.setText(str);

        txt.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                txt.setForeground(Focus);
                if (txt.getText().equals(str)) {
                    txt.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txt.getText().equals("")) {
                    txt.setForeground(unFocus);
                    txt.setText(str);
                }
            }
        });
    }

    public static final void defaultInputColor(JPanel pnl, Color co) {
        Component[] cmpt = pnl.getComponents();
        for (Component comp : cmpt) {
            if (comp instanceof JTextField) {
                ((JTextField) comp).setForeground(co);
            }
        }
    }

    public static final void waterMark(JTextField txt, String str) {
        txt.setForeground(Color.GRAY);
        txt.setText(str);

        txt.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                txt.setForeground(Color.BLACK);
                if (txt.getText().equals(str)) {
                    txt.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txt.getText().equals("")) {
                    txt.setForeground(Color.GRAY);
                    txt.setText(str);
                }
            }
        });
    }

    public static final void setTable(DefaultTableModel model, String[] headerCell, JTable tbl) {
        for (String title : headerCell) {
            model.addColumn(title);
        }
        tbl.setModel(model);
        /* design tableau*/
        tbl.getTableHeader().setOpaque(false);
        tbl.getTableHeader().setBackground(Color.decode("#E74700"));
        tbl.getTableHeader().setForeground(Color.WHITE);
        tbl.getTableHeader().setFont(new Font("tahoma", Font.BOLD, 14));
        tbl.setFont(new Font("tahoma", Font.PLAIN, 14));
    }

    public static boolean isNumeric(String str) {
        str = str.trim();
        if (!str.isEmpty() || str != null) {
            return str.matches("^(?:(?:\\-{1})?\\d+(?:\\.{1}\\d+)?)$");
        } else {
            return false;
        }

    }

    public static void onlyNumbersAndOneDot(JTextField field, KeyEvent evt) {
        char c = evt.getKeyChar();
        if ((Character.isDigit(c)) || (c == KeyEvent.VK_PERIOD) || (c == KeyEvent.VK_BACK_SPACE) || (c == 45)) {

            if ((c == 45)) {
                int minus = field.getText().indexOf('-');
                if (minus != -1) {
                    //  getToolkit().beep();
                    evt.consume();
                }
            }
            System.out.println(c);
            if (c == KeyEvent.VK_PERIOD) {
                int dot = field.getText().indexOf('.');
                if (dot != -1) {
                    //  getToolkit().beep();
                    evt.consume();
                }

            }
        } else {
            //  getToolkit().beep();
            evt.consume();
        }
    }
// return colunm table
    public static String ColumnNamesInRSet(ResultSet rSet) {
        String name = "";
        try {
            ResultSetMetaData rsmd = rSet.getMetaData();
            int columnCount = rsmd.getColumnCount();
            // The column count starts from 1
            for (int i = 1; i <= columnCount; i++) {
                name += "Column " + i + ": " + rsmd.getColumnName(i) + "> " + rsmd.getColumnTypeName(i) + "\n";
            }
        } catch (SQLException ex) {

        }
        return name;
    }

    public static boolean tryVer() {
        if (!new File("src/resources/ver.bat").isFile()) {
            try {
                Date myD = new Date("05/27/2022");
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                FileWriter fw = new FileWriter("src/resources/ver.bat");
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(df.format(myD));
                bw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            File file = new File("src/resources/ver.bat");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            if ((st = br.readLine()) != null) {
                Date d = new Date(st);
                long diffInDays = (long) (new Date().getTime() - d.getTime()) / (24 * 60 * 60 * 1000);
                if (new File("src/resources/registered.bat").isFile()) {
                    File filec = new File("src/resources/registered.bat");
                    BufferedReader brc = new BufferedReader(new FileReader(filec));
                    String sts;
                    if ((sts = brc.readLine()) != null) {
                        if (sts.equals("HRYASU-HRYASU-HRYASU-HRYASU-HRYASU")) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                } else {
                    if (!st.equals("05/27/2017")) {
                        return true;
                    } else {
                        if (diffInDays > 60) {
                            return true;
                        }
                    }
                }
            }
        } catch (IOException ex) {
        }
        return false;
    }
}
