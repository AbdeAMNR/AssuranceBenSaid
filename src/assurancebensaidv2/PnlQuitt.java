/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assurancebensaidv2;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author amnrLaptop
 */
public class PnlQuitt extends javax.swing.JPanel {

    private HashMap<String, String> attestItem = new HashMap<>();
    public static HashMap<String, String> cltList = new HashMap<>();
    private final jdbcManager connect;
    private ResultSet res = null;
    private final DefaultTableModel quittModel = SecurityFunc.modelNoEditableCell();
    private final String[] cltHeader = {"numQuitt", "CIN", "Nom complète", "Usage", "Date de quittance", "Prime réelle", "Reglement", "Perte", "Attestation",
        "1er Fractionnement", "2ème Fractionnement", "3ème Fractionnement", "4ème Fractionnement", "Credit",
        "1er paiement", "2ème paiement", "3ème paiement", "4ème paiement", "5ème paiement", "6ème paiement", "Résiliation", "Annuler"};
    private final SimpleDateFormat sdf;
    private PnlHome home = new PnlHome();

    public PnlQuitt() {
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        initComponents();
        this.connect = new jdbcManager();
        connect.fillCbx("SELECT * FROM attestation", cbxAttest, "typeAttest");
        attestItem.putAll(connect.item);
        connect.fillCombobox("SELECT * FROM client ORDER BY nomComplet", cbxCltName, "nomComplet", "cin");
        cltList.putAll(connect.item);
        /*===== Inside JComboBox: adding automatic completion ======*/
        AutoCompleteDecorator.decorate(cbxCltName);
        this.init();
        this.pnlName.setVisible(false);
        SecurityFunc.setTable(quittModel, cltHeader, tblFrac);
       this.loadQuitt();
        lblQuettID.setText(Integer.toString(getQuettID()));

        tblFrac.getColumn("Annuler").setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                // Check the column name, if it is "version"

                // You know version column includes string
                String versionVal = (String) value;
                if (versionVal != null) {
                    if (versionVal.contains("Annuler")) {
                        //set to red bold font
                        c.setForeground(Color.RED);
                        c.setFont(new Font("Dialog", Font.BOLD, 12));
                    } else {
                        //stay at default
                        c.setForeground(Color.BLACK);
                        c.setFont(new Font("Dialog", Font.PLAIN, 12));
                    }
                }
                return c;
            }
        }
        );
    }

    private void loadTable(String query) {
        try {
            res = null;
            quittModel.setRowCount(0);
            //  System.out.println(">loadTable: " + query);
            res = connect.select(query);
            // System.out.println(SecurityFunc.ColumnNamesInRSet(res));
            while (res.next()) {
                quittModel.addRow(new Object[]{
                    res.getString("q.numQuitt"),
                    res.getString("cin"),
                    res.getString("nomComplet"),
                    res.getString("libaleUsage"),
                    res.getDate("dateQuitt"),
                    res.getFloat("primeReele"),
                    res.getFloat("reglement"),
                    res.getFloat("perte"),
                    res.getString("typeAttest"),
                    res.getString("1er Fractionnement"),
                    res.getString("2eme Fractionnement"),
                    res.getString("3eme Fractionnement"),
                    res.getString("4eme Fractionnement"),
                    res.getString("Credit"),
                    res.getString("1er paiement"),
                    res.getString("2eme paiement"),
                    res.getString("3eme paiement"),
                    res.getString("4eme paiement"),
                    res.getString("5eme paiement"),
                    res.getString("6eme paiement"), res.getFloat("resiliation"),
                    res.getString("quittAnnuler"),});
            }
            //  System.out.println(">finish loadin\n");
//            tblFrac.setAutoResizeMode(tblFrac.AUTO_RESIZE_OFF);
//            SecurityFunc.resizeColumnWidth(tblFrac);
//            jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            //   tblFrac.setPreferredScrollableViewportSize(Toolkit.getDefaultToolkit().getScreenSize());
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    private void loadQuitt() {
        String query = "SELECT q.numQuitt, c.cin,c.nomComplet,u.libaleUsage,q.dateQuitt,q.primeReele,q.reglement,q.perte,a.typeAttest,CONCAT(\"  \"+f.datePremiereFrac, \" | \", f.fracOne, \" \", \"MAD\") AS '1er Fractionnement',CONCAT(\"  \"+f.dateDeuxiemeFrac, \" | \", f.fracTwo, \" \", \"MAD\") AS '2eme Fractionnement',CONCAT(\"  \"+f.dateTroisiemeFrac, \" | \", f.fracThree, \" \", \"MAD\")AS '3eme Fractionnement',CONCAT(\"  \"+f.dateQuatriemeFrac, \" | \", f.fracFour, \" \", \"MAD\") AS '4eme Fractionnement', CONCAT(\"  \"+cr.creditSum, \" Decouper en: \", cr.Diviser) AS 'Credit',CONCAT(\"  \"+cr.dateFragOne, \" | \", cr.fragOne, \" \", \"MAD\")AS '1er paiement',CONCAT(\"  \"+cr.dateFragTwo, \" | \", cr.fragOne, \" \", \"MAD\") AS '2eme paiement',CONCAT(\"  \"+cr.dateFragThree, \" | \", cr.fragOne, \" \", \"MAD\") AS '3eme paiement',CONCAT(\"  \"+cr.dateFragFour, \" | \", cr.fragOne, \" \", \"MAD\") AS '4eme paiement',CONCAT(\"  \"+cr.dateFragFive, \" | \", cr.fragOne, \" \", \"MAD\") AS '5eme paiement',CONCAT(\"  \"+cr.dateFragSix, \" | \", cr.fragOne, \" \", \"MAD\") AS '6eme paiement' ,q.resiliation"
                + ",q.quittAnnuler FROM client c JOIN vehicle v ON c.cin = v.cin "
                + "JOIN usages u ON v.idUsage = u.idUsage "
                + "JOIN quittance q ON c.cin = q.cin "
                + "JOIN fractionnement f ON q.numQuitt = f.numQuitt "
                + "JOIN attestation a ON f.idAttest = a.idAttest "
                + "JOIN credit cr ON q.numQuitt = cr.numQuitt ORDER BY q.numQuitt";
        loadTable(query);
    }

    private void loadQuitt(String where) {
        String query = "SELECT q.numQuitt,  c.cin,c.nomComplet,u.libaleUsage,q.dateQuitt,q.primeReele,q.reglement,q.perte,a.typeAttest,CONCAT(\"  \"+f.datePremiereFrac, \" | \", f.fracOne, \" \", \"MAD\") AS '1er Fractionnement',CONCAT(\"  \"+f.dateDeuxiemeFrac, \" | \", f.fracTwo, \" \", \"MAD\") AS '2eme Fractionnement',CONCAT(\"  \"+f.dateTroisiemeFrac, \" | \", f.fracThree, \" \", \"MAD\")AS '3eme Fractionnement',CONCAT(\"  \"+f.dateQuatriemeFrac, \" | \", f.fracFour, \" \", \"MAD\") AS '4eme Fractionnement', CONCAT(\"  \"+cr.creditSum, \" Decouper en: \", cr.Diviser) AS 'Credit',CONCAT(\"  \"+cr.dateFragOne, \" | \", cr.fragOne, \" \", \"MAD\")AS '1er paiement',CONCAT(\"  \"+cr.dateFragTwo, \" | \", cr.fragOne, \" \", \"MAD\") AS '2eme paiement',CONCAT(\"  \"+cr.dateFragThree, \" | \", cr.fragOne, \" \", \"MAD\") AS '3eme paiement',CONCAT(\"  \"+cr.dateFragFour, \" | \", cr.fragOne, \" \", \"MAD\") AS '4eme paiement',CONCAT(\"  \"+cr.dateFragFive, \" | \", cr.fragOne, \" \", \"MAD\") AS '5eme paiement',CONCAT(\"  \"+cr.dateFragSix, \" | \", cr.fragOne, \" \", \"MAD\") AS '6eme paiement' ,q.resiliation"
                + ",q.quittAnnuler FROM client c JOIN vehicle v ON c.cin = v.cin "
                + "JOIN usages u ON v.idUsage = u.idUsage "
                + "JOIN quittance q ON c.cin = q.cin "
                + "JOIN fractionnement f ON q.numQuitt = f.numQuitt "
                + "JOIN attestation a ON f.idAttest = a.idAttest "
                + "JOIN credit cr ON q.numQuitt = cr.numQuitt "
                + "WHERE c.cin LIKE '%" + where + "%' or c.nomComplet LIKE '%" + where + "%' ORDER BY q.numQuitt";
        loadTable(query);
    }

    private void loadQuitt(String where, String timeRageOne, String timeRageTwo) {
        String query = "SELECT q.numQuitt,  c.cin,c.nomComplet,u.libaleUsage,q.dateQuitt,q.primeReele,q.reglement,q.perte,a.typeAttest,CONCAT(\"  \"+f.datePremiereFrac, \" | \", f.fracOne, \" \", \"MAD\") AS '1er Fractionnement',CONCAT(\"  \"+f.dateDeuxiemeFrac, \" | \", f.fracTwo, \" \", \"MAD\") AS '2eme Fractionnement',CONCAT(\"  \"+f.dateTroisiemeFrac, \" | \", f.fracThree, \" \", \"MAD\")AS '3eme Fractionnement',CONCAT(\"  \"+f.dateQuatriemeFrac, \" | \", f.fracFour, \" \", \"MAD\") AS '4eme Fractionnement', CONCAT(\"  \"+cr.creditSum, \" Decouper en: \", cr.Diviser) AS 'Credit',CONCAT(\"  \"+cr.dateFragOne, \" | \", cr.fragOne, \" \", \"MAD\")AS '1er paiement',CONCAT(\"  \"+cr.dateFragTwo, \" | \", cr.fragOne, \" \", \"MAD\") AS '2eme paiement',CONCAT(\"  \"+cr.dateFragThree, \" | \", cr.fragOne, \" \", \"MAD\") AS '3eme paiement',CONCAT(\"  \"+cr.dateFragFour, \" | \", cr.fragOne, \" \", \"MAD\") AS '4eme paiement',CONCAT(\"  \"+cr.dateFragFive, \" | \", cr.fragOne, \" \", \"MAD\") AS '5eme paiement',CONCAT(\"  \"+cr.dateFragSix, \" | \", cr.fragOne, \" \", \"MAD\") AS '6eme paiement' ,q.resiliation"
                + ",q.quittAnnuler FROM client c JOIN vehicle v ON c.cin = v.cin "
                + "JOIN usages u ON v.idUsage = u.idUsage "
                + "JOIN quittance q ON c.cin = q.cin "
                + "JOIN fractionnement f ON q.numQuitt = f.numQuitt "
                + "JOIN attestation a ON f.idAttest = a.idAttest "
                + "JOIN credit cr ON q.numQuitt = cr.numQuitt "
                + "WHERE (c.cin LIKE '%" + where + "%' or c.nomComplet LIKE '%" + where + "%') AND (q.dateQuitt BETWEEN '" + timeRageOne + "' AND '" + timeRageTwo + "') ORDER BY q.numQuitt;";
        loadTable(query);
    }

    private void loadQuitt(String timeRageOne, String timeRageTwo) {
        String query = "SELECT q.numQuitt,   c.cin,c.nomComplet,u.libaleUsage,q.dateQuitt,q.primeReele,q.reglement,q.perte,a.typeAttest,CONCAT(\"  \"+f.datePremiereFrac, \" | \", f.fracOne, \" \", \"MAD\") AS '1er Fractionnement',CONCAT(\"  \"+f.dateDeuxiemeFrac, \" | \", f.fracTwo, \" \", \"MAD\") AS '2eme Fractionnement',CONCAT(\"  \"+f.dateTroisiemeFrac, \" | \", f.fracThree, \" \", \"MAD\")AS '3eme Fractionnement',CONCAT(\"  \"+f.dateQuatriemeFrac, \" | \", f.fracFour, \" \", \"MAD\") AS '4eme Fractionnement', CONCAT(\"  \"+cr.creditSum, \" Decouper en: \", cr.Diviser) AS 'Credit',CONCAT(\"  \"+cr.dateFragOne, \" | \", cr.fragOne, \" \", \"MAD\")AS '1er paiement',CONCAT(\"  \"+cr.dateFragTwo, \" | \", cr.fragOne, \" \", \"MAD\") AS '2eme paiement',CONCAT(\"  \"+cr.dateFragThree, \" | \", cr.fragOne, \" \", \"MAD\") AS '3eme paiement',CONCAT(\"  \"+cr.dateFragFour, \" | \", cr.fragOne, \" \", \"MAD\") AS '4eme paiement',CONCAT(\"  \"+cr.dateFragFive, \" | \", cr.fragOne, \" \", \"MAD\") AS '5eme paiement',CONCAT(\"  \"+cr.dateFragSix, \" | \", cr.fragOne, \" \", \"MAD\") AS '6eme paiement',q.resiliation "
                + ",q.quittAnnuler FROM client c JOIN vehicle v ON c.cin = v.cin "
                + "JOIN usages u ON v.idUsage = u.idUsage "
                + "JOIN quittance q ON c.cin = q.cin "
                + "JOIN fractionnement f ON q.numQuitt = f.numQuitt "
                + "JOIN attestation a ON f.idAttest = a.idAttest "
                + "JOIN credit cr ON q.numQuitt = cr.numQuitt "
                + "WHERE  (q.dateQuitt BETWEEN '" + timeRageOne + "' AND '" + timeRageTwo + "') ORDER BY q.numQuitt;";
        loadTable(query);
    }

    public final void init() {
        pnlAddFrac.setVisible(false);
        pnlFrag.setVisible(false);
        AdvcedSrch(false);

        //--bind watermark on textBox------------------
        SecurityFunc.waterMark(txtPrimeReele, " Prime reele");
        SecurityFunc.waterMark(txtReglement, " Reglement");
        SecurityFunc.waterMark(txtcredit, " Credit");
        SecurityFunc.waterMark(fracOne, " Première fraction");
        SecurityFunc.waterMark(fracTwo, " Deuxième fraction");
        SecurityFunc.waterMark(fracThree, " Troisième fraction");
        SecurityFunc.waterMark(fracFour, " Quatrième fraction");
        /*credit waterMark===================================*/
        SecurityFunc.waterMark(fragOne, " Première paiement");
        SecurityFunc.waterMark(fragTwo, " Deuxième paiement");
        SecurityFunc.waterMark(fragThree, " Troisième paiement");
        SecurityFunc.waterMark(fragFoor, " Quatrième paiement");
        SecurityFunc.waterMark(fragFive, " Cinquième  paiement");
        SecurityFunc.waterMark(fragSix, " Sixième paiement");
        /*=================================================*/
        SecurityFunc.waterMark(txtSearch, " Commencez la recherche par nom de client ou le CIN");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlTbl = new javax.swing.JPanel();
        pnlAddQuittance = new javax.swing.JPanel();
        txtPrimeReele = new javax.swing.JTextField();
        txtcredit = new javax.swing.JTextField();
        txtReglement = new javax.swing.JTextField();
        cbxCltName = new javax.swing.JComboBox<>();
        dateQuitt = new com.toedter.calendar.JDateChooser();
        cbxAttest = new javax.swing.JComboBox<>();
        chkFrag = new javax.swing.JCheckBox();
        iconAddQuett = new javax.swing.JLabel();
        lblQuettID = new javax.swing.JLabel();
        cmdResiliation = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtResiliation = new javax.swing.JTextField();
        pnlName = new javax.swing.JPanel();
        lblName = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        pnlFrag = new javax.swing.JPanel();
        dateFragOne = new com.toedter.calendar.JDateChooser();
        fragOne = new javax.swing.JTextField();
        dateFragTwo = new com.toedter.calendar.JDateChooser();
        fragTwo = new javax.swing.JTextField();
        dateFragThree = new com.toedter.calendar.JDateChooser();
        fragThree = new javax.swing.JTextField();
        dateFragFoor = new com.toedter.calendar.JDateChooser();
        fragFoor = new javax.swing.JTextField();
        dateFragFive = new com.toedter.calendar.JDateChooser();
        fragFive = new javax.swing.JTextField();
        dateFragSix = new com.toedter.calendar.JDateChooser();
        fragSix = new javax.swing.JTextField();
        pnlAddFrac = new javax.swing.JPanel();
        fracOne = new javax.swing.JTextField();
        fracTwo = new javax.swing.JTextField();
        fracThree = new javax.swing.JTextField();
        fracFour = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        pnlControles = new javax.swing.JPanel();
        cmdRemove = new javax.swing.JPanel();
        btnTxtAdd4 = new javax.swing.JLabel();
        cmdAdd = new javax.swing.JPanel();
        btnTxtAdd1 = new javax.swing.JLabel();
        cmdUpdate = new javax.swing.JPanel();
        btnTxtAdd5 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        chkAdvcedSrch = new javax.swing.JCheckBox();
        dateOne = new com.toedter.calendar.JDateChooser();
        dateTwo = new com.toedter.calendar.JDateChooser();
        lblDe = new javax.swing.JLabel();
        lblA = new javax.swing.JLabel();
        btnRefrech = new javax.swing.JLabel();
        cmdCancel = new javax.swing.JPanel();
        btnTxtAdd6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFrac = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));

        pnlTbl.setBackground(new java.awt.Color(255, 255, 255));

        pnlAddQuittance.setBackground(new java.awt.Color(255, 255, 255));
        pnlAddQuittance.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ajouter une nouvelle quittance pour un client", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(231, 71, 0))); // NOI18N

        txtPrimeReele.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtPrimeReele.setMaximumSize(new java.awt.Dimension(400, 30));
        txtPrimeReele.setMinimumSize(new java.awt.Dimension(100, 30));
        txtPrimeReele.setName(""); // NOI18N
        txtPrimeReele.setPreferredSize(new java.awt.Dimension(100, 30));
        txtPrimeReele.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPrimeReeleKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrimeReeleKeyTyped(evt);
            }
        });

        txtcredit.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtcredit.setMaximumSize(new java.awt.Dimension(200, 30));
        txtcredit.setMinimumSize(new java.awt.Dimension(100, 30));
        txtcredit.setName(""); // NOI18N
        txtcredit.setPreferredSize(new java.awt.Dimension(100, 30));
        txtcredit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcreditKeyTyped(evt);
            }
        });

        txtReglement.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtReglement.setMaximumSize(new java.awt.Dimension(400, 30));
        txtReglement.setMinimumSize(new java.awt.Dimension(100, 30));
        txtReglement.setName(""); // NOI18N
        txtReglement.setPreferredSize(new java.awt.Dimension(100, 30));
        txtReglement.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtReglementKeyTyped(evt);
            }
        });

        cbxCltName.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cbxCltName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxCltName.setMaximumSize(new java.awt.Dimension(300, 30));
        cbxCltName.setMinimumSize(new java.awt.Dimension(100, 30));
        cbxCltName.setName(""); // NOI18N
        cbxCltName.setPreferredSize(new java.awt.Dimension(100, 30));
        cbxCltName.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxCltNameItemStateChanged(evt);
            }
        });

        dateQuitt.setDate(new Date());
        dateQuitt.setDateFormatString("yyyy-MM-dd");
        dateQuitt.setMaximumSize(new java.awt.Dimension(300, 30));
        dateQuitt.setMinimumSize(new java.awt.Dimension(100, 30));
        dateQuitt.setPreferredSize(new java.awt.Dimension(100, 30));

        cbxAttest.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cbxAttest.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxAttest.setMaximumSize(new java.awt.Dimension(1000, 30));
        cbxAttest.setMinimumSize(new java.awt.Dimension(100, 30));
        cbxAttest.setName(""); // NOI18N
        cbxAttest.setPreferredSize(new java.awt.Dimension(100, 30));
        cbxAttest.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxAttestItemStateChanged(evt);
            }
        });

        chkFrag.setBackground(new java.awt.Color(255, 255, 255));
        chkFrag.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        chkFrag.setText("Diviser le crédit");
        chkFrag.setMaximumSize(new java.awt.Dimension(200, 30));
        chkFrag.setMinimumSize(new java.awt.Dimension(100, 30));
        chkFrag.setPreferredSize(new java.awt.Dimension(100, 30));
        chkFrag.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkFragItemStateChanged(evt);
            }
        });

        iconAddQuett.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/addQuettNofocus.png"))); // NOI18N
        iconAddQuett.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        iconAddQuett.setMinimumSize(new java.awt.Dimension(120, 29));
        iconAddQuett.setPreferredSize(new java.awt.Dimension(120, 29));
        iconAddQuett.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                iconAddQuettMouseExited(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconAddQuettMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                iconAddQuettMouseEntered(evt);
            }
        });

        lblQuettID.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblQuettID.setText("11");
        lblQuettID.setMaximumSize(new java.awt.Dimension(100, 26));
        lblQuettID.setMinimumSize(new java.awt.Dimension(30, 26));
        lblQuettID.setPreferredSize(new java.awt.Dimension(30, 26));

        cmdResiliation.setBackground(new java.awt.Color(77, 102, 132));
        cmdResiliation.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdResiliation.setMaximumSize(new java.awt.Dimension(100, 30));
        cmdResiliation.setMinimumSize(new java.awt.Dimension(100, 30));
        cmdResiliation.setPreferredSize(new java.awt.Dimension(100, 30));
        cmdResiliation.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmdResiliationMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cmdResiliationMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cmdResiliationMouseExited(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Résiliation");

        javax.swing.GroupLayout cmdResiliationLayout = new javax.swing.GroupLayout(cmdResiliation);
        cmdResiliation.setLayout(cmdResiliationLayout);
        cmdResiliationLayout.setHorizontalGroup(
            cmdResiliationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cmdResiliationLayout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addContainerGap(13, Short.MAX_VALUE))
        );
        cmdResiliationLayout.setVerticalGroup(
            cmdResiliationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cmdResiliationLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtResiliation.setBackground(new java.awt.Color(237, 67, 55));
        txtResiliation.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        txtResiliation.setForeground(new java.awt.Color(255, 255, 255));
        txtResiliation.setText("-0");
        txtResiliation.setMaximumSize(new java.awt.Dimension(100, 30));
        txtResiliation.setMinimumSize(new java.awt.Dimension(100, 30));
        txtResiliation.setPreferredSize(new java.awt.Dimension(100, 30));
        txtResiliation.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtResiliationKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout pnlAddQuittanceLayout = new javax.swing.GroupLayout(pnlAddQuittance);
        pnlAddQuittance.setLayout(pnlAddQuittanceLayout);
        pnlAddQuittanceLayout.setHorizontalGroup(
            pnlAddQuittanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAddQuittanceLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAddQuittanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(iconAddQuett, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblQuettID, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAddQuittanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateQuitt, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                    .addComponent(cbxCltName, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(pnlAddQuittanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtReglement, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                    .addComponent(txtPrimeReele, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(pnlAddQuittanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtcredit, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                    .addComponent(chkFrag, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(cbxAttest, 0, 151, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(pnlAddQuittanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtResiliation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdResiliation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        pnlAddQuittanceLayout.setVerticalGroup(
            pnlAddQuittanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddQuittanceLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAddQuittanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAddQuittanceLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(pnlAddQuittanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(iconAddQuett, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxCltName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrimeReele, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chkFrag, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(cbxAttest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtResiliation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlAddQuittanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateQuitt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblQuettID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtReglement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcredit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdResiliation, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pnlName.setBackground(new java.awt.Color(255, 255, 255));
        pnlName.setMaximumSize(new java.awt.Dimension(350, 104));
        pnlName.setMinimumSize(new java.awt.Dimension(48, 104));
        pnlName.setRequestFocusEnabled(false);

        lblName.setBackground(new java.awt.Color(51, 255, 51));
        lblName.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        lblName.setForeground(new java.awt.Color(231, 71, 0));
        lblName.setText("n");

        javax.swing.GroupLayout pnlNameLayout = new javax.swing.GroupLayout(pnlName);
        pnlName.setLayout(pnlNameLayout);
        pnlNameLayout.setHorizontalGroup(
            pnlNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNameLayout.createSequentialGroup()
                .addContainerGap(44, Short.MAX_VALUE)
                .addComponent(lblName)
                .addContainerGap(45, Short.MAX_VALUE))
        );
        pnlNameLayout.setVerticalGroup(
            pnlNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNameLayout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addComponent(lblName)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        pnlFrag.setBackground(new java.awt.Color(255, 255, 255));
        pnlFrag.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Définir les dates du paiement du crédit", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(231, 71, 0))); // NOI18N
        pnlFrag.setMinimumSize(new java.awt.Dimension(612, 96));

        dateFragOne.setDate(new Date());
        dateFragOne.setDateFormatString("yyyy-MM-dd");
        dateFragOne.setMinimumSize(new java.awt.Dimension(90, 20));
        dateFragOne.setPreferredSize(new java.awt.Dimension(90, 20));

        fragOne.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        fragOne.setMinimumSize(new java.awt.Dimension(90, 20));
        fragOne.setPreferredSize(new java.awt.Dimension(90, 20));

        dateFragTwo.setDate(new Date());
        dateFragTwo.setDateFormatString("yyyy-MM-dd");
        dateFragTwo.setMinimumSize(new java.awt.Dimension(90, 20));
        dateFragTwo.setPreferredSize(new java.awt.Dimension(90, 20));

        fragTwo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        fragTwo.setMinimumSize(new java.awt.Dimension(90, 20));
        fragTwo.setPreferredSize(new java.awt.Dimension(90, 20));

        dateFragThree.setDate(new Date());
        dateFragThree.setDateFormatString("yyyy-MM-dd");
        dateFragThree.setMinimumSize(new java.awt.Dimension(90, 20));
        dateFragThree.setPreferredSize(new java.awt.Dimension(90, 20));

        fragThree.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        fragThree.setMinimumSize(new java.awt.Dimension(90, 20));
        fragThree.setPreferredSize(new java.awt.Dimension(90, 20));

        dateFragFoor.setDate(new Date());
        dateFragFoor.setDateFormatString("yyyy-MM-dd");
        dateFragFoor.setMinimumSize(new java.awt.Dimension(90, 20));
        dateFragFoor.setPreferredSize(new java.awt.Dimension(90, 20));

        fragFoor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        fragFoor.setMinimumSize(new java.awt.Dimension(90, 20));
        fragFoor.setPreferredSize(new java.awt.Dimension(90, 20));

        dateFragFive.setDate(new Date());
        dateFragFive.setDateFormatString("yyyy-MM-dd");
        dateFragFive.setMinimumSize(new java.awt.Dimension(90, 20));
        dateFragFive.setPreferredSize(new java.awt.Dimension(90, 20));

        fragFive.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        fragFive.setMinimumSize(new java.awt.Dimension(90, 20));
        fragFive.setPreferredSize(new java.awt.Dimension(90, 20));

        dateFragSix.setDate(new Date());
        dateFragSix.setDateFormatString("yyyy-MM-dd");
        dateFragSix.setMinimumSize(new java.awt.Dimension(90, 20));
        dateFragSix.setPreferredSize(new java.awt.Dimension(90, 20));

        fragSix.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        fragSix.setMinimumSize(new java.awt.Dimension(90, 20));
        fragSix.setPreferredSize(new java.awt.Dimension(90, 20));

        javax.swing.GroupLayout pnlFragLayout = new javax.swing.GroupLayout(pnlFrag);
        pnlFrag.setLayout(pnlFragLayout);
        pnlFragLayout.setHorizontalGroup(
            pnlFragLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFragLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFragLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateFragOne, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fragOne, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFragLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fragTwo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateFragTwo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFragLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateFragThree, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fragThree, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFragLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateFragFoor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fragFoor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFragLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateFragFive, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fragFive, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFragLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateFragSix, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fragSix, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        pnlFragLayout.setVerticalGroup(
            pnlFragLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFragLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnlFragLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFragLayout.createSequentialGroup()
                        .addComponent(dateFragSix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(fragSix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlFragLayout.createSequentialGroup()
                        .addComponent(dateFragFive, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(fragFive, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlFragLayout.createSequentialGroup()
                        .addComponent(dateFragFoor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(fragFoor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlFragLayout.createSequentialGroup()
                        .addComponent(dateFragThree, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(fragThree, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlFragLayout.createSequentialGroup()
                        .addComponent(dateFragTwo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(fragTwo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlFragLayout.createSequentialGroup()
                        .addComponent(dateFragOne, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(fragOne, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );

        pnlAddFrac.setBackground(new java.awt.Color(255, 255, 255));
        pnlAddFrac.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Définir une fraction à une quittance", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(231, 71, 0))); // NOI18N
        pnlAddFrac.setMinimumSize(new java.awt.Dimension(250, 96));
        pnlAddFrac.setPreferredSize(new java.awt.Dimension(250, 96));

        fracOne.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        fracOne.setEnabled(false);

        fracTwo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        fracTwo.setEnabled(false);

        fracThree.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        fracThree.setEnabled(false);

        fracFour.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        fracFour.setEnabled(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("1ere");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("3eme");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("2eme");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("4eme");

        javax.swing.GroupLayout pnlAddFracLayout = new javax.swing.GroupLayout(pnlAddFrac);
        pnlAddFrac.setLayout(pnlAddFracLayout);
        pnlAddFracLayout.setHorizontalGroup(
            pnlAddFracLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddFracLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlAddFracLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlAddFracLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fracThree)
                    .addComponent(fracOne))
                .addGap(18, 18, 18)
                .addGroup(pnlAddFracLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlAddFracLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fracFour)
                    .addComponent(fracTwo))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlAddFracLayout.setVerticalGroup(
            pnlAddFracLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddFracLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAddFracLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(pnlAddFracLayout.createSequentialGroup()
                        .addGroup(pnlAddFracLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fracOne, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fracTwo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlAddFracLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fracThree, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fracFour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2))))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(pnlAddFrac, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlFrag, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(2, 2, 2))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlAddFrac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlFrag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pnlControles.setBackground(new java.awt.Color(255, 204, 153));
        pnlControles.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        cmdRemove.setBackground(new java.awt.Color(77, 102, 132));
        cmdRemove.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdRemove.setMaximumSize(null);
        cmdRemove.setMinimumSize(new java.awt.Dimension(60, 32));
        cmdRemove.setName(""); // NOI18N
        cmdRemove.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmdRemoveMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cmdRemoveMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cmdRemoveMouseExited(evt);
            }
        });

        btnTxtAdd4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnTxtAdd4.setForeground(new java.awt.Color(255, 255, 255));
        btnTxtAdd4.setText("Supprimer");

        javax.swing.GroupLayout cmdRemoveLayout = new javax.swing.GroupLayout(cmdRemove);
        cmdRemove.setLayout(cmdRemoveLayout);
        cmdRemoveLayout.setHorizontalGroup(
            cmdRemoveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cmdRemoveLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTxtAdd4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        cmdRemoveLayout.setVerticalGroup(
            cmdRemoveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cmdRemoveLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTxtAdd4, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cmdAdd.setBackground(new java.awt.Color(77, 102, 132));
        cmdAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdAdd.setMaximumSize(null);
        cmdAdd.setMinimumSize(new java.awt.Dimension(60, 32));
        cmdAdd.setName(""); // NOI18N
        cmdAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmdAddMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cmdAddMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cmdAddMouseExited(evt);
            }
        });

        btnTxtAdd1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnTxtAdd1.setForeground(new java.awt.Color(255, 255, 255));
        btnTxtAdd1.setText("Ajouter");

        javax.swing.GroupLayout cmdAddLayout = new javax.swing.GroupLayout(cmdAdd);
        cmdAdd.setLayout(cmdAddLayout);
        cmdAddLayout.setHorizontalGroup(
            cmdAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cmdAddLayout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(btnTxtAdd1)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        cmdAddLayout.setVerticalGroup(
            cmdAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cmdAddLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTxtAdd1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cmdUpdate.setBackground(new java.awt.Color(77, 102, 132));
        cmdUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdUpdate.setMaximumSize(null);
        cmdUpdate.setMinimumSize(new java.awt.Dimension(60, 32));
        cmdUpdate.setName(""); // NOI18N
        cmdUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cmdUpdateMouseExited(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmdUpdateMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cmdUpdateMouseEntered(evt);
            }
        });

        btnTxtAdd5.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnTxtAdd5.setForeground(new java.awt.Color(255, 255, 255));
        btnTxtAdd5.setText("Modifier");

        javax.swing.GroupLayout cmdUpdateLayout = new javax.swing.GroupLayout(cmdUpdate);
        cmdUpdate.setLayout(cmdUpdateLayout);
        cmdUpdateLayout.setHorizontalGroup(
            cmdUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cmdUpdateLayout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(btnTxtAdd5)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        cmdUpdateLayout.setVerticalGroup(
            cmdUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cmdUpdateLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTxtAdd5, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtSearch.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtSearch.setMaximumSize(new java.awt.Dimension(400, 24));
        txtSearch.setMinimumSize(new java.awt.Dimension(200, 24));
        txtSearch.setPreferredSize(new java.awt.Dimension(200, 24));
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        chkAdvcedSrch.setBackground(new java.awt.Color(255, 204, 153));
        chkAdvcedSrch.setText("Recherche Avancée");
        chkAdvcedSrch.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkAdvcedSrchItemStateChanged(evt);
            }
        });

        dateOne.setDate(new Date());
        dateOne.setDateFormatString("yyyy-MM-dd");
        dateOne.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dateOnePropertyChange(evt);
            }
        });

        dateTwo.setDate(new Date());
        dateTwo.setDateFormatString("yyyy-MM-dd");
        dateTwo.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dateTwoPropertyChange(evt);
            }
        });

        lblDe.setText("De :");

        lblA.setText("A :");

        btnRefrech.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/refrech unFocus.png"))); // NOI18N
        btnRefrech.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRefrech.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRefrechMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRefrechMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRefrechMouseExited(evt);
            }
        });

        cmdCancel.setBackground(new java.awt.Color(77, 102, 132));
        cmdCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdCancel.setMaximumSize(null);
        cmdCancel.setMinimumSize(new java.awt.Dimension(60, 32));
        cmdCancel.setName(""); // NOI18N
        cmdCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmdCancelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cmdCancelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cmdCancelMouseExited(evt);
            }
        });

        btnTxtAdd6.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnTxtAdd6.setForeground(new java.awt.Color(255, 255, 255));
        btnTxtAdd6.setText("Annuler");

        javax.swing.GroupLayout cmdCancelLayout = new javax.swing.GroupLayout(cmdCancel);
        cmdCancel.setLayout(cmdCancelLayout);
        cmdCancelLayout.setHorizontalGroup(
            cmdCancelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cmdCancelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTxtAdd6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        cmdCancelLayout.setVerticalGroup(
            cmdCancelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cmdCancelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTxtAdd6, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlControlesLayout = new javax.swing.GroupLayout(pnlControles);
        pnlControles.setLayout(pnlControlesLayout);
        pnlControlesLayout.setHorizontalGroup(
            pnlControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlControlesLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(cmdUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cmdAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkAdvcedSrch, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDe)
                    .addComponent(lblA))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dateOne, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                    .addComponent(dateTwo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addComponent(btnRefrech)
                .addGap(18, 18, 18)
                .addComponent(cmdCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cmdRemove, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlControlesLayout.setVerticalGroup(
            pnlControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlControlesLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(pnlControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlControlesLayout.createSequentialGroup()
                        .addGroup(pnlControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblDe, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dateOne, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlControlesLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(dateTwo, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(pnlControlesLayout.createSequentialGroup()
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(chkAdvcedSrch, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5))
            .addGroup(pnlControlesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmdCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdRemove, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlControlesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRefrech, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tblFrac.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblFrac.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblFrac.setGridColor(new java.awt.Color(61, 61, 61));
        tblFrac.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblFracMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblFrac);

        javax.swing.GroupLayout pnlTblLayout = new javax.swing.GroupLayout(pnlTbl);
        pnlTbl.setLayout(pnlTblLayout);
        pnlTblLayout.setHorizontalGroup(
            pnlTblLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTblLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTblLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTblLayout.createSequentialGroup()
                        .addComponent(pnlAddQuittance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(pnlName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlControles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        pnlTblLayout.setVerticalGroup(
            pnlTblLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTblLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTblLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlAddQuittance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlControles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(pnlTbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(pnlTbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmdRemoveMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdRemoveMouseEntered
        cmdRemove.setBackground(Color.decode("#FF4C65"));
    }//GEN-LAST:event_cmdRemoveMouseEntered

    private void cmdRemoveMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdRemoveMouseExited
        cmdRemove.setBackground(Color.decode("#4D6684"));
    }//GEN-LAST:event_cmdRemoveMouseExited

    private void cmdAddMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdAddMouseEntered
        cmdAdd.setBackground(Color.decode("#3D3D3D"));
    }//GEN-LAST:event_cmdAddMouseEntered

    private void cmdAddMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdAddMouseExited
        cmdAdd.setBackground(Color.decode("#4D6684"));
    }//GEN-LAST:event_cmdAddMouseExited

    private void cmdUpdateMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdUpdateMouseEntered
        cmdUpdate.setBackground(Color.decode("#3D3D3D"));
    }//GEN-LAST:event_cmdUpdateMouseEntered

    private void cmdUpdateMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdUpdateMouseExited
        cmdUpdate.setBackground(Color.decode("#4D6684"));
    }//GEN-LAST:event_cmdUpdateMouseExited

    private void chkFragItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkFragItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
            pnlFrag.setVisible(true);
        } else {//checkbox has been deselected
            pnlFrag.setVisible(false);
        }
    }//GEN-LAST:event_chkFragItemStateChanged

    private void cbxAttestItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxAttestItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            String item = this.cbxAttest.getSelectedItem().toString();
            if (!item.equals("Definitive") && !item.equals("Provisoire")) {
                pnlAddFrac.setVisible(true);
            } else {
                pnlAddFrac.setVisible(false);

            }
        }
    }//GEN-LAST:event_cbxAttestItemStateChanged
    private void AdvcedSrch(boolean val) {
        dateOne.setVisible(val);
        dateTwo.setVisible(val);
        lblDe.setVisible(val);
        lblA.setVisible(val);
    }
    private void chkAdvcedSrchItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkAdvcedSrchItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
            AdvcedSrch(true);
        } else {//checkbox has been deselected
            AdvcedSrch(false);
            loadQuitt();
        }
    }//GEN-LAST:event_chkAdvcedSrchItemStateChanged

    private void cbxCltNameItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxCltNameItemStateChanged
        //   if (!this.getPreferredSize().equals(new Dimension(1100, 650))) {
        if (cbxCltName.getSelectedItem() != null) {
            pnlName.setVisible(true);
            //        System.out.println("df" + cbxCltName.getSelectedItem().toString());
            lblName.setText(cbxCltName.getSelectedItem().toString());
        }
        //      }
    }//GEN-LAST:event_cbxCltNameItemStateChanged

    private void cmdAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdAddMouseClicked
        boolean continuityGranted = false;
        String rqtInsert;
        //  int QuetID = getQuettID();
        int QuetID = Integer.parseInt(lblQuettID.getText());
        String cin = cltList.get(cbxCltName.getSelectedItem().toString());
        String quittDate = (String) sdf.format(this.dateQuitt.getDate());
        String primeR = txtPrimeReele.getText().trim();
        String rglment = txtReglement.getText().trim();
        String sumCredi = this.txtcredit.getText().trim();

        if (SecurityFunc.isNumeric(primeR) && SecurityFunc.isNumeric(rglment) && SecurityFunc.isNumeric(sumCredi)) {
            float perte = (Float.parseFloat(primeR) - Float.parseFloat(rglment) - Float.parseFloat(sumCredi));
            System.out.println("perte> " + perte);
            res = connect.select("SELECT numQuitt FROM quittance WHERE numQuitt=" + Integer.parseInt(lblQuettID.getText()));
            try {
                if (res.first()) {
                    JOptionPane.showMessageDialog(null, "Une quittance déjà existe avec ce numéro", "Invalide", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } else {
                    rqtInsert = "INSERT INTO quittance (cin, dateQuitt, primeReele, reglement, perte) "
                            + "VALUES ('" + cin + "','" + quittDate + "', " + primeR + ", " + rglment + ", " + perte + ");";
                    //   System.out.println("quittance query> " + rqtInsert);
                    connect.update(rqtInsert);
                    //      System.out.println("successful");
                    connect.saveOperation("Insertion de quittance avec le numéro " + Integer.parseInt(lblQuettID.getText()), SecurityFunc.logIn);
                    continuityGranted = true;
                }
            } catch (SQLException ex) {
                Logger.getLogger(PnlClients.class.getName()).log(Level.SEVERE, null, ex);
            }
            //********* check if "Fractionnement + provisoire" used********************************************************************************************************
            int attestID = Integer.parseInt(attestItem.get(cbxAttest.getSelectedItem().toString()));
            if (pnlAddFrac.isVisible()) {
                if (continuityGranted) {
                    String valFrac1 = fracOne.getText().trim();
                    String valFrac2 = fracTwo.getText().trim();
                    String valFrac3 = fracThree.getText().trim();
                    String valFrac4 = fracFour.getText().trim();
                    Date dt = new Date();
                    Calendar c = Calendar.getInstance();
                    c.setTime(dt); // Now use today date.
                    String Frac1 = (String) sdf.format(dt);
                    c.add(Calendar.DATE, 30); // Adding 30 days
                    String Frac2 = (String) sdf.format(c.getTime());
                    c.add(Calendar.DATE, 30); // Adding 60 days
                    String Frac3 = (String) sdf.format(c.getTime());
                    c.add(Calendar.DATE, 30); // Adding 90 days
                    String Frac4 = (String) sdf.format(c.getTime());

//                    System.out.println("date1:>" + Frac1 + "\n");
//                    System.out.println("date2:>" + Frac2 + "\n");
//                    System.out.println("date3:>" + Frac3 + "\n");
//                    System.out.println("date4:>" + Frac4 + "\n");
                    if (SecurityFunc.isNumeric(valFrac1) && SecurityFunc.isNumeric(valFrac2) && SecurityFunc.isNumeric(valFrac3) && SecurityFunc.isNumeric(valFrac4)) {
                        rqtInsert = "INSERT INTO fractionnement(idAttest, numQuitt, datePremiereFrac, fracOne, dateDeuxiemeFrac, fracTwo, dateTroisiemeFrac, fracThree, dateQuatriemeFrac, fracFour) "
                                + "VALUES (" + attestID + ", " + QuetID + ", '" + Frac1 + "', " + Float.parseFloat(valFrac1) + ", '" + Frac2 + "', " + Float.parseFloat(valFrac2) + ", '" + Frac3 + "', " + Float.parseFloat(valFrac3) + ", '" + Frac4 + "', " + Float.parseFloat(valFrac4) + ");";
                        //      System.out.println("fractionnement query> " + rqtInsert);
                        connect.update(rqtInsert);
                        System.out.println("successful");
                        connect.saveOperation("Insertion d\\'un fractionnement avec numéro de quittance: " + QuetID, SecurityFunc.logIn);
                    } else {
                        JOptionPane.showMessageDialog(null, "La valeur de fractionnement peut être seulement numérique", "Invalide", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } else {
                if (continuityGranted) {
                    rqtInsert = "INSERT INTO fractionnement(idAttest, numQuitt) "
                            + "VALUES (" + attestID + ", " + QuetID + ")";
                    //    System.out.println("fractionnement query> " + rqtInsert);
                    connect.update(rqtInsert);
                    connect.saveOperation("Insertion d\\'un fractionnement avec numéro de quittance: " + QuetID, SecurityFunc.logIn);
                    //     System.out.println("successful");
                }
            }

            //********* check if "Diviser le crédit" used********************************************************************************************************
            int nbrDiv = 1;
            if (pnlFrag.isVisible() && chkFrag.isSelected()) {
                if (continuityGranted) {
                    rqtInsert = "";
                    String valFrag1 = fragOne.getText().trim();
                    String valFrag2 = fragTwo.getText().trim();
                    String valFrag3 = fragThree.getText().trim();
                    String valFrag4 = fragFoor.getText().trim();
                    String valFrag5 = fragFive.getText().trim();
                    String valFrag6 = fragSix.getText().trim();
                    String Frag1 = (String) sdf.format(this.dateFragOne.getDate());
                    String Frag2 = (String) sdf.format(this.dateFragTwo.getDate());
                    String Frag3 = (String) sdf.format(this.dateFragThree.getDate());
                    String Frag4 = (String) sdf.format(this.dateFragFoor.getDate());
                    String Frag5 = (String) sdf.format(this.dateFragFive.getDate());
                    String Frag6 = (String) sdf.format(this.dateFragSix.getDate());
                    if (SecurityFunc.isNumeric(valFrag1)) {
                        nbrDiv++;
                        rqtInsert = "INSERT INTO Credit (numQuitt, creditSum, Diviser, dateFragOne, fragOne) "
                                + "VALUES (" + QuetID + "," + Float.parseFloat(sumCredi) + "," + nbrDiv + ",'" + Frag1 + "'," + Float.parseFloat(valFrag1) + ");";
                    }
                    if (SecurityFunc.isNumeric(valFrag2)) {
                        nbrDiv++;
                        rqtInsert = "INSERT INTO Credit (numQuitt, creditSum, Diviser, dateFragOne, fragOne, dateFragTwo, fragTwo )"
                                + "VALUES (" + QuetID + "," + Float.parseFloat(sumCredi) + "," + nbrDiv + ",'" + Frag1 + "'," + Float.parseFloat(valFrag1) + ",'" + Frag2 + "'," + Float.parseFloat(valFrag2) + ");";
                    }
                    if (SecurityFunc.isNumeric(valFrag3)) {
                        rqtInsert = "INSERT INTO Credit (numQuitt, creditSum, Diviser, dateFragOne, fragOne, dateFragTwo, fragTwo, dateFragThree, fragThree)"
                                + "VALUES (" + QuetID + "," + Float.parseFloat(sumCredi) + "," + nbrDiv + ",'" + Frag1 + "'," + Float.parseFloat(valFrag1) + ",'" + Frag2 + "'," + Float.parseFloat(valFrag2) + ",'" + Frag3 + "'," + Float.parseFloat(valFrag3) + ");";
                        nbrDiv++;
                    }
                    if (SecurityFunc.isNumeric(valFrag4)) {
                        nbrDiv++;
                        rqtInsert = "INSERT INTO Credit (numQuitt, creditSum, Diviser, dateFragOne, fragOne, dateFragTwo, fragTwo, dateFragThree, fragThree, dateFragFour, fragFour)"
                                + "VALUES (" + QuetID + "," + Float.parseFloat(sumCredi) + "," + nbrDiv + ",'" + Frag1 + "'," + Float.parseFloat(valFrag1) + ",'" + Frag2 + "'," + Float.parseFloat(valFrag2) + ",'" + Frag3 + "'," + Float.parseFloat(valFrag3) + ",'" + Frag4 + "'," + Float.parseFloat(valFrag4) + ");";
                    }
                    if (SecurityFunc.isNumeric(valFrag5)) {
                        nbrDiv++;
                        rqtInsert = "INSERT INTO Credit (numQuitt, creditSum, Diviser, dateFragOne, fragOne, dateFragTwo, fragTwo, dateFragThree, fragThree, dateFragFour, fragFour,dateFragFive, fragFive)"
                                + "VALUES (" + QuetID + "," + Float.parseFloat(sumCredi) + "," + nbrDiv + ",'" + Frag1 + "'," + Float.parseFloat(valFrag1) + ",'" + Frag2 + "'," + Float.parseFloat(valFrag2) + ",'" + Frag3 + "'," + Float.parseFloat(valFrag3) + ",'" + Frag4 + "'," + Float.parseFloat(valFrag4) + ",'" + Frag5 + "'," + Float.parseFloat(valFrag5) + ");";
                    }
                    if (SecurityFunc.isNumeric(valFrag6)) {
                        nbrDiv++;
                        rqtInsert = "INSERT INTO Credit (numQuitt, creditSum, Diviser, dateFragOne, fragOne, dateFragTwo, fragTwo, dateFragThree, fragThree, dateFragFour, fragFour,dateFragFive, fragFive, dateFragSix, fragSix)"
                                + "VALUES (" + QuetID + "," + Float.parseFloat(sumCredi) + "," + nbrDiv + ",'" + Frag1 + "'," + Float.parseFloat(valFrag1) + ",'" + Frag2 + "'," + Float.parseFloat(valFrag2) + ",'" + Frag3 + "'," + Float.parseFloat(valFrag3) + ",'" + Frag4 + "'," + Float.parseFloat(valFrag4) + ",'" + Frag5 + "'," + Float.parseFloat(valFrag5) + ",'" + Frag6 + "'," + Float.parseFloat(valFrag6) + ");";
                    }
                    if (nbrDiv > 1) {
                        //     System.out.println("Credit query> " + rqtInsert);
                        connect.update(rqtInsert);
                        connect.saveOperation("Insertion de Crédit avec numéro de quittance: " + QuetID, SecurityFunc.logIn);
                        //   System.out.println("successful");
                    } else {
                        JOptionPane.showMessageDialog(null, "Définir au moins un fragment de crédit", "Invalide", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                if (continuityGranted) {
                    rqtInsert = "INSERT INTO Credit (numQuitt, creditSum, Diviser)"
                            + "VALUES (" + QuetID + "," + Float.parseFloat(sumCredi) + "," + nbrDiv + ");";
                    //     System.out.println("Credit query> " + rqtInsert);
                    connect.update(rqtInsert);
                    connect.saveOperation("Insertion de Crédit avec numéro de quittance: " + QuetID, SecurityFunc.logIn);
                    ///       System.out.println("successful");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Tous les détails de la quittances doivent être fournis", "Invalide", JOptionPane.ERROR_MESSAGE);
        }
        lblQuettID.setText(Integer.toString(getQuettID()));
        loadQuitt();
        connect.GeState();
        home.loadTable();
        SecurityFunc.SuccessfullyOperation();

    }//GEN-LAST:event_cmdAddMouseClicked

    private void iconAddQuettMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconAddQuettMouseEntered
        iconAddQuett.setIcon(new ImageIcon(getClass().getResource("/resources/addQuett.png")));
    }//GEN-LAST:event_iconAddQuettMouseEntered

    private void iconAddQuettMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconAddQuettMouseExited
        iconAddQuett.setIcon(new ImageIcon(getClass().getResource("/resources/addQuettNofocus1.png")));
    }//GEN-LAST:event_iconAddQuettMouseExited
    private int getQuettID() {
        int id = 0;
        try {
            String query;
            query = "SELECT max(numQuitt) as quettID FROM quittance;";
            res = connect.select(query);
            if (res.first()) {
                id = res.getInt("quettID");
                id++;
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return id;
    }
    private void iconAddQuettMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconAddQuettMouseClicked
        int id = getQuettID();
        lblQuettID.setText(Integer.toString(id));
    }//GEN-LAST:event_iconAddQuettMouseClicked

    private void txtPrimeReeleKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrimeReeleKeyReleased
        if (SecurityFunc.isNumeric(txtPrimeReele.getText())) {
            float PR = Float.parseFloat(txtPrimeReele.getText().trim());
            final float constant = 17;
            float val1 = (PR - 17) / 2;
            float val2 = (PR - 17) / 2;
            float val3 = (PR - 17) / 3;
            float val4 = (PR - 17) / 4;
            this.fracOne.setText(Float.toString(val1 + 17));
            this.fracTwo.setText(Float.toString(val2 + 17));
            this.fracThree.setText(Float.toString(val3 + 17));
            this.fracFour.setText(Float.toString(val4 + 17));
        }
    }//GEN-LAST:event_txtPrimeReeleKeyReleased

    private void txtPrimeReeleKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrimeReeleKeyTyped
        SecurityFunc.onlyNumbersAndOneDot(txtPrimeReele, evt);
    }//GEN-LAST:event_txtPrimeReeleKeyTyped

    private void txtcreditKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcreditKeyTyped
        SecurityFunc.onlyNumbersAndOneDot(txtcredit, evt);
    }//GEN-LAST:event_txtcreditKeyTyped

    private void txtReglementKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtReglementKeyTyped
        SecurityFunc.onlyNumbersAndOneDot(txtReglement, evt);
    }//GEN-LAST:event_txtReglementKeyTyped

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        String key = txtSearch.getText().trim();
        if (SecurityFunc.isNullOrWhiteSpace(key)) {
            this.loadQuitt();
        } else {
            if (chkAdvcedSrch.isSelected()) {
                String from = (String) sdf.format(this.dateOne.getDate());
                String to = (String) sdf.format(this.dateTwo.getDate());
                loadQuitt(key, from, to);
            } else {
                loadQuitt(key);
            }
        }
    }//GEN-LAST:event_txtSearchKeyReleased

    private void dateOnePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dateOnePropertyChange
        if (chkAdvcedSrch.isSelected()) {
            String from = (String) sdf.format(this.dateOne.getDate());
            String to = (String) sdf.format(this.dateTwo.getDate());
            loadQuitt(from, to);
        }
    }//GEN-LAST:event_dateOnePropertyChange

    private void tblFracMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblFracMouseClicked
        int row = tblFrac.getSelectedRow();
        lblQuettID.setText(tblFrac.getValueAt(row, 0).toString());
        PnlQuitt.cbxCltName.setSelectedItem(tblFrac.getValueAt(row, 2));
        this.dateQuitt.setDate((Date) tblFrac.getValueAt(row, 4));
        this.txtPrimeReele.setText(tblFrac.getValueAt(row, 5).toString());
        this.txtReglement.setText(tblFrac.getValueAt(row, 6).toString());
        cbxAttest.setSelectedItem(tblFrac.getValueAt(row, 8).toString());
        this.txtResiliation.setText(tblFrac.getValueAt(row, 20).toString());

        try {
            String q = "SELECT c.creditSum FROM quittance q JOIN credit c ON q.numQuitt=c.numQuitt WHERE q.numQuitt=" + lblQuettID.getText() + ";";
            res = connect.select(q);
            //System.out.println(q);
            if (res.first()) {
                txtcredit.setText(Float.toString(res.getFloat("c.creditSum")));
            }
        } catch (SQLException e) {

        }
        SecurityFunc.defaultInputColor(pnlAddQuittance, Color.BLACK);
        SecurityFunc.defaultInputColor(pnlAddFrac, Color.BLACK);
        SecurityFunc.defaultInputColor(pnlFrag, Color.BLACK);
        this.txtResiliation.setForeground(Color.WHITE);
    }//GEN-LAST:event_tblFracMouseClicked

    private void btnRefrechMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRefrechMouseEntered
        btnRefrech.setIcon(new ImageIcon(getClass().getResource("/resources/refrech Focus.png")));
    }//GEN-LAST:event_btnRefrechMouseEntered

    private void btnRefrechMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRefrechMouseExited
        btnRefrech.setIcon(new ImageIcon(getClass().getResource("/resources/refrech unFocus.png")));
    }//GEN-LAST:event_btnRefrechMouseExited

    private void btnRefrechMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRefrechMouseClicked
        this.loadQuitt();
        SecurityFunc.SuccessfullyOperation();

    }//GEN-LAST:event_btnRefrechMouseClicked

    private void cmdCancelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdCancelMouseEntered
        cmdCancel.setBackground(Color.RED);
    }//GEN-LAST:event_cmdCancelMouseEntered

    private void cmdCancelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdCancelMouseExited
        cmdCancel.setBackground(Color.decode("#4D6684"));
    }//GEN-LAST:event_cmdCancelMouseExited

    private void cmdRemoveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdRemoveMouseClicked
        try {
            int optChoisie = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer cette quittance?", "Attendez une seconde", JOptionPane.YES_NO_OPTION);
            if (optChoisie == 0) {
                String value = tblFrac.getValueAt(tblFrac.getSelectedRow(), 0).toString();
                //     System.out.println(quittanceID);
                String delQuery = "DELETE FROM quittance WHERE numQuitt =  " + value + ";";
                connect.update(delQuery);
                connect.saveOperation("Quittance avec numéro " + value + " est supprimé", SecurityFunc.logIn);
                this.loadQuitt();
                SecurityFunc.SuccessfullyOperation();
            }

        } catch (ArrayIndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(null, "Sélectionnez une quittance", "Invalide", JOptionPane.ERROR_MESSAGE);
        }
        tblFrac.getSelectionModel().clearSelection();
        connect.GeState();
        home.loadTable();
    }//GEN-LAST:event_cmdRemoveMouseClicked

    private void cmdCancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdCancelMouseClicked

        int optChoisie = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment annuler cette quittance?", "Attendez une seconde", JOptionPane.YES_NO_OPTION);
        if (optChoisie == 0) {
            String value = tblFrac.getValueAt(tblFrac.getSelectedRow(), 0).toString();
            //     System.out.println(quittanceID);
            String delQuery = "UPDATE quittance set quittAnnuler='    Annuler' WHERE numQuitt =  " + value + ";";
            System.out.println(delQuery);
            connect.update(delQuery);
            connect.saveOperation("Annulation de quittance numéro " + value, SecurityFunc.logIn);
            //    System.out.println(effects + " updated row(s)");    
            SecurityFunc.SuccessfullyOperation();
            this.loadQuitt();
        }

    }//GEN-LAST:event_cmdCancelMouseClicked

    private void cmdUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdUpdateMouseClicked
        boolean continuityGranted = false;
        String updateQuery = "";
        //  int QuetID = getQuettID();
        //int QuetID = Integer.parseInt(lblQuettID.getText());
        //String cin = cltList.get(cbxCltName.getSelectedItem().toString());
        //    String quittDate = (String) sdf.format(this.dateQuitt.getDate());
        String primeR = txtPrimeReele.getText().trim();
        String rglment = txtReglement.getText().trim();
        String sumCredi = this.txtcredit.getText().trim();

        if (SecurityFunc.isNumeric(primeR) && SecurityFunc.isNumeric(rglment) && SecurityFunc.isNumeric(sumCredi)) {
            float perte = (Float.parseFloat(primeR) - Float.parseFloat(rglment) - Float.parseFloat(sumCredi));
            System.out.println("perte> " + perte);
            res = connect.select("SELECT numQuitt FROM quittance WHERE numQuitt=" + Integer.parseInt(lblQuettID.getText()));
            try {
                if (res.first()) {
                    // do update quittence here here
                    updateQuery = "UPDATE quittance set primeReele=" + primeR + ", reglement=" + primeR + ", perte=" + perte
                            + " WHERE numQuitt=" + Integer.parseInt(lblQuettID.getText());
                    System.out.println("update quittance query> " + updateQuery);
                    connect.update(updateQuery);
                    //   System.out.println("successful");
                    connect.saveOperation("Modification de la quittance avec le numéro: " + Integer.parseInt(lblQuettID.getText()), SecurityFunc.logIn);

                    continuityGranted = true;
                }
            } catch (SQLException ex) {
                Logger.getLogger(PnlClients.class.getName()).log(Level.SEVERE, null, ex);
            }
            //********* check if "Fractionnement + provisoire" used********************************************************************************************************
            int attestID = Integer.parseInt(attestItem.get(cbxAttest.getSelectedItem().toString()));
            if (cbxAttest.getSelectedItem().toString().equals("Fractionnement + provisoire") && pnlAddFrac.isVisible()) {
                if (continuityGranted) {
                    String valFrac1 = fracOne.getText().trim();
                    String valFrac2 = fracTwo.getText().trim();
                    String valFrac3 = fracThree.getText().trim();
                    String valFrac4 = fracFour.getText().trim();

                    if (SecurityFunc.isNumeric(valFrac1) && SecurityFunc.isNumeric(valFrac2) && SecurityFunc.isNumeric(valFrac3) && SecurityFunc.isNumeric(valFrac4)) {
                        // do update here
                        updateQuery = "UPDATE fractionnement SET idAttest=" + attestID + ",fracOne=" + Float.parseFloat(valFrac1) + ",fracTwo=" + Float.parseFloat(valFrac2) + ", fracThree=" + Float.parseFloat(valFrac3) + ",fracFour=" + Float.parseFloat(valFrac4) + " WHERE numQuitt=" + Integer.parseInt(lblQuettID.getText());
                        System.out.println("update fractionnement query> " + updateQuery);
                        connect.update(updateQuery);
                        connect.saveOperation("Modification de fractionnement avec numéro de quittance: " + Integer.parseInt(lblQuettID.getText()), SecurityFunc.logIn);

                        //  System.out.println("successful");
                    } else {
                        JOptionPane.showMessageDialog(null, "La valeur de fractionnement peut être seulement numérique", "Invalide", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } else {
                if (continuityGranted) {
                    // do update here fractionnement
                    updateQuery = "UPDATE fractionnement SET idAttest=" + attestID + " WHERE numQuitt=" + Integer.parseInt(lblQuettID.getText());
                    System.out.println("fractionnement query> " + updateQuery);
                    connect.update(updateQuery);
                    connect.saveOperation("Modification de fractionnement avec numéro de quittance: " + Integer.parseInt(lblQuettID.getText()), SecurityFunc.logIn);
                    System.out.println("successful");
                }
            }

            //********* check if "Diviser le crédit" used********************************************************************************************************
            int nbrDiv = 1;
            if (pnlFrag.isVisible() && chkFrag.isSelected()) {
                if (continuityGranted) {
                    updateQuery = "";
                    String valFrag1 = fragOne.getText().trim();
                    String valFrag2 = fragTwo.getText().trim();
                    String valFrag3 = fragThree.getText().trim();
                    String valFrag4 = fragFoor.getText().trim();
                    String valFrag5 = fragFive.getText().trim();
                    String valFrag6 = fragSix.getText().trim();
                    String Frag1 = (String) sdf.format(this.dateFragOne.getDate());
                    String Frag2 = (String) sdf.format(this.dateFragTwo.getDate());
                    String Frag3 = (String) sdf.format(this.dateFragThree.getDate());
                    String Frag4 = (String) sdf.format(this.dateFragFoor.getDate());
                    String Frag5 = (String) sdf.format(this.dateFragFive.getDate());
                    String Frag6 = (String) sdf.format(this.dateFragSix.getDate());
                    if (SecurityFunc.isNumeric(valFrag1)) {
                        nbrDiv++;
                        updateQuery = "UPDATE credit SET creditSum=" + Float.parseFloat(sumCredi) + ",Diviser="
                                + nbrDiv + ",dateFragOne='" + Frag1 + "', fragOne=" + Float.parseFloat(valFrag1)
                                + " WHERE numQuitt=" + Integer.parseInt(lblQuettID.getText());
                    }
                    if (SecurityFunc.isNumeric(valFrag2)) {
                        nbrDiv++;
                        updateQuery = "UPDATE credit SET creditSum=" + Float.parseFloat(sumCredi) + ",Diviser=" + nbrDiv + ",dateFragOne='" + Frag1 + "', fragOne=" + Float.parseFloat(valFrag1) + ", "
                                + "dateFragTwo='" + Frag2 + "', fragTwo=" + Float.parseFloat(valFrag2) + " WHERE numQuitt=" + Integer.parseInt(lblQuettID.getText());
                    }
                    if (SecurityFunc.isNumeric(valFrag3)) {
                        nbrDiv++;
                        updateQuery = "UPDATE credit SET creditSum=" + Float.parseFloat(sumCredi) + ",Diviser=" + nbrDiv + ",dateFragOne='" + Frag1 + "', fragOne=" + Float.parseFloat(valFrag1) + ", "
                                + "dateFragTwo='" + Frag2 + "', fragTwo=" + Float.parseFloat(valFrag2) + ", dateFragThree='" + Frag3 + "', fragThree=" + Float.parseFloat(valFrag3) + " WHERE numQuitt=" + Integer.parseInt(lblQuettID.getText());

                    }
                    if (SecurityFunc.isNumeric(valFrag4)) {
                        nbrDiv++;
                        updateQuery = "UPDATE credit SET creditSum=" + Float.parseFloat(sumCredi) + ",Diviser=" + nbrDiv + ",dateFragOne='" + Frag1 + "', fragOne=" + Float.parseFloat(valFrag1) + ", "
                                + "dateFragTwo='" + Frag2 + "', fragTwo=" + Float.parseFloat(valFrag2) + ", dateFragThree='" + Frag3 + "', fragThree=" + Float.parseFloat(valFrag3)
                                + ", dateFragFour='" + Frag4 + "', fragFour=" + Float.parseFloat(valFrag4) + " WHERE numQuitt=" + Integer.parseInt(lblQuettID.getText());
                    }
                    if (SecurityFunc.isNumeric(valFrag5)) {
                        nbrDiv++;
                        updateQuery = "UPDATE credit SET creditSum=" + Float.parseFloat(sumCredi) + ",Diviser=" + nbrDiv + ",dateFragOne='" + Frag1 + "', fragOne=" + Float.parseFloat(valFrag1) + ", "
                                + "dateFragTwo='" + Frag2 + "', fragTwo=" + Float.parseFloat(valFrag2) + ", dateFragThree='" + Frag3 + "', fragThree=" + Float.parseFloat(valFrag3)
                                + ", dateFragFour='" + Frag4 + "', fragFour=" + Float.parseFloat(valFrag4) + ", dateFragFive='" + Frag5 + "', "
                                + "fragFive=" + Float.parseFloat(valFrag5) + " WHERE numQuitt=" + Integer.parseInt(lblQuettID.getText());
                    }
                    if (SecurityFunc.isNumeric(valFrag6)) {
                        nbrDiv++;
                        updateQuery = "UPDATE credit SET creditSum=" + Float.parseFloat(sumCredi) + ",Diviser=" + nbrDiv + ",dateFragOne='" + Frag1 + "', fragOne=" + Float.parseFloat(valFrag1) + ", "
                                + "dateFragTwo='" + Frag2 + "', fragTwo=" + Float.parseFloat(valFrag2) + ", dateFragThree='" + Frag3 + "', fragThree=" + Float.parseFloat(valFrag3)
                                + ", dateFragFour='" + Frag4 + "', fragFour=" + Float.parseFloat(valFrag4) + ", dateFragFive='" + Frag5 + "', "
                                + "fragFive=" + Float.parseFloat(valFrag5) + ", dateFragSix='" + Frag6 + "', fragSix=" + Float.parseFloat(valFrag6)
                                + " WHERE numQuitt=" + Integer.parseInt(lblQuettID.getText());
                    }
                    if (nbrDiv > 1) {
                        System.out.println("Credit query> " + updateQuery);
                        connect.update(updateQuery);
                        connect.saveOperation("Modification de credit avec numéro de quittance: " + Integer.parseInt(lblQuettID.getText()), SecurityFunc.logIn);
                        System.out.println("successful");
                    } else {
                        JOptionPane.showMessageDialog(null, "Définir au moins un fragment de crédit", "Invalide", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                if (continuityGranted) {
                    // do update here 
                    updateQuery = "UPDATE credit set creditSum=" + Float.parseFloat(sumCredi) + " WHERE numQuitt=" + Integer.parseInt(lblQuettID.getText());
                    System.out.println("update Credit query> " + updateQuery);
                    connect.update(updateQuery);
                    connect.saveOperation("Modification de credit avec numéro de quittance: " + Integer.parseInt(lblQuettID.getText()), SecurityFunc.logIn);
                    System.out.println("successful");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Tous les détails de la quittances doivent être fournis", "Invalide", JOptionPane.ERROR_MESSAGE);
        }
        lblQuettID.setText(Integer.toString(getQuettID()));
        loadQuitt();
        connect.GeState();
        home.loadTable();
        SecurityFunc.SuccessfullyOperation();
    }//GEN-LAST:event_cmdUpdateMouseClicked

    private void cmdResiliationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdResiliationMouseClicked
        try {
            String quittanceID = tblFrac.getValueAt(tblFrac.getSelectedRow(), 0).toString();
            String resiliation = txtResiliation.getText();
            //     System.out.println(quittanceID);
            String delQuery = "UPDATE quittance set resiliation=" + Float.parseFloat(resiliation) + " WHERE numQuitt =  " + quittanceID + ";";
            //  System.out.println(delQuery);
            connect.update(delQuery);
            connect.saveOperation("Résiliation de quittance numero" + quittanceID, SecurityFunc.logIn);
            //    System.out.println(effects + " updated row(s)");
            this.loadQuitt();
            SecurityFunc.SuccessfullyOperation();
        } catch (ArrayIndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(null, "Sélectionnez une quittance", "Invalide", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_cmdResiliationMouseClicked

    private void cmdResiliationMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdResiliationMouseEntered
        cmdResiliation.setBackground(Color.decode("#3D3D3D"));
    }//GEN-LAST:event_cmdResiliationMouseEntered

    private void cmdResiliationMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdResiliationMouseExited
        cmdResiliation.setBackground(Color.decode("#4D6684"));
    }//GEN-LAST:event_cmdResiliationMouseExited

    private void txtResiliationKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtResiliationKeyTyped
        SecurityFunc.onlyNumbersAndOneDot(txtResiliation, evt);
    }//GEN-LAST:event_txtResiliationKeyTyped

    private void dateTwoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dateTwoPropertyChange
        if (chkAdvcedSrch.isSelected()) {
            String from = (String) sdf.format(this.dateOne.getDate());
            String to = (String) sdf.format(this.dateTwo.getDate());
            loadQuitt(from, to);
        }
    }//GEN-LAST:event_dateTwoPropertyChange


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnRefrech;
    private javax.swing.JLabel btnTxtAdd1;
    private javax.swing.JLabel btnTxtAdd4;
    private javax.swing.JLabel btnTxtAdd5;
    private javax.swing.JLabel btnTxtAdd6;
    private javax.swing.JComboBox<String> cbxAttest;
    public static javax.swing.JComboBox<String> cbxCltName;
    private javax.swing.JCheckBox chkAdvcedSrch;
    private javax.swing.JCheckBox chkFrag;
    private javax.swing.JPanel cmdAdd;
    private javax.swing.JPanel cmdCancel;
    private javax.swing.JPanel cmdRemove;
    private javax.swing.JPanel cmdResiliation;
    private javax.swing.JPanel cmdUpdate;
    private com.toedter.calendar.JDateChooser dateFragFive;
    private com.toedter.calendar.JDateChooser dateFragFoor;
    private com.toedter.calendar.JDateChooser dateFragOne;
    private com.toedter.calendar.JDateChooser dateFragSix;
    private com.toedter.calendar.JDateChooser dateFragThree;
    private com.toedter.calendar.JDateChooser dateFragTwo;
    private com.toedter.calendar.JDateChooser dateOne;
    private com.toedter.calendar.JDateChooser dateQuitt;
    private com.toedter.calendar.JDateChooser dateTwo;
    private javax.swing.JTextField fracFour;
    private javax.swing.JTextField fracOne;
    private javax.swing.JTextField fracThree;
    private javax.swing.JTextField fracTwo;
    private javax.swing.JTextField fragFive;
    private javax.swing.JTextField fragFoor;
    private javax.swing.JTextField fragOne;
    private javax.swing.JTextField fragSix;
    private javax.swing.JTextField fragThree;
    private javax.swing.JTextField fragTwo;
    private javax.swing.JLabel iconAddQuett;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblA;
    private javax.swing.JLabel lblDe;
    public static javax.swing.JLabel lblName;
    private javax.swing.JLabel lblQuettID;
    private javax.swing.JPanel pnlAddFrac;
    public static javax.swing.JPanel pnlAddQuittance;
    private javax.swing.JPanel pnlControles;
    private javax.swing.JPanel pnlFrag;
    private javax.swing.JPanel pnlName;
    private javax.swing.JPanel pnlTbl;
    private javax.swing.JTable tblFrac;
    private javax.swing.JTextField txtPrimeReele;
    private javax.swing.JTextField txtReglement;
    private javax.swing.JTextField txtResiliation;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtcredit;
    // End of variables declaration//GEN-END:variables

}
