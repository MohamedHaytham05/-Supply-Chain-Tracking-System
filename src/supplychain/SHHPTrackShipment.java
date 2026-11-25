package supplychain;
import java.awt.*;
import javax.swing.*;

public class SHHPTrackShipment extends javax.swing.JFrame {
     SHHPShipperDashboard dashboard;
    private JTextField shipmentField;
    private JTextArea resultArea;
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SHHPTrackShipment.class.getName());

   public SHHPTrackShipment(SHHPShipperDashboard dashboard) {
    this.dashboard = dashboard;
    initComponents();
    setLocationRelativeTo(null);

    setupCustomUI(); 
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
     
    }

    private void setupCustomUI() {
       

    setTitle("Track Shipment");
    setSize(1000, 600);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    setLayout(new BorderLayout());

    // ===== HEADER =====
    JPanel header = new JPanel(new BorderLayout());
    header.setBackground(Color.WHITE);
    header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

    JLabel title = new JLabel("Track Shipment");
    title.setFont(new Font("Segoe UI", Font.BOLD, 22));

    JButton backBtn = new JButton("Back");

    header.add(title, BorderLayout.WEST);
    header.add(backBtn, BorderLayout.EAST);

    add(header, BorderLayout.NORTH);

    // ===== TOP INPUT =====
    JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

    JLabel idLabel = new JLabel("Shipment ID:");
    idLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

    shipmentField = new JTextField();
    shipmentField.setPreferredSize(new Dimension(200, 30));

    JButton trackBtn = new JButton("Track");
    trackBtn.setPreferredSize(new Dimension(100, 30));

    topPanel.add(idLabel);
    topPanel.add(shipmentField);
    topPanel.add(trackBtn);

    add(topPanel, BorderLayout.BEFORE_FIRST_LINE);

    // ===== CENTER MAIN =====
    JPanel centerPanel = new JPanel(new GridLayout(1, 2, 20, 0));
    centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

    // ===== TIMELINE =====
    JTextArea timelineArea = new JTextArea();
    timelineArea.setEditable(false);
    timelineArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    timelineArea.setLineWrap(true);

    JScrollPane timelineScroll = new JScrollPane(timelineArea);
    timelineScroll.setBorder(BorderFactory.createTitledBorder("Tracking Timeline"));

    // ===== DETAILS =====
    resultArea = new JTextArea();
    resultArea.setEditable(false);
    resultArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    resultArea.setLineWrap(true);

    JScrollPane detailsScroll = new JScrollPane(resultArea);
    detailsScroll.setBorder(BorderFactory.createTitledBorder("Shipment Details"));

    centerPanel.add(timelineScroll);
    centerPanel.add(detailsScroll);

    add(centerPanel, BorderLayout.CENTER);

    // ===== ACTIONS =====
    trackBtn.addActionListener(e -> {

        String id = shipmentField.getText();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter Shipment ID");
            return;
        }

        timelineArea.setText(
                "🟢 Registered — Cairo Factory\n" +
                "🟢 Picked Up — Cairo Factory\n" +
                "🔵 In Transit — Cairo Highway\n" +
                "🔵 Location Updated — Alexandria Port\n" +
                "⚪ Arriving Soon — Cairo Warehouse"
        );

        resultArea.setText(
                "Shipment ID: " + id + "\n\n" +
                "Status: In Transit\n" +
                "Origin: Cairo Factory\n" +
                "Current Location: Alexandria Port\n\n" +
                "Storage: STR-001 / A1\n" +
                "Delivery: DEL-SHP-001\n" +
                "Shipper: Mohamed Ali\n\n" +
                "Events Logged: 4"
        );
    });

    backBtn.addActionListener(e -> {
        this.dispose();
        dashboard.setVisible(true);
    });
    addWindowListener(new java.awt.event.WindowAdapter() {
    @Override
    public void windowClosing(java.awt.event.WindowEvent e) {
        dispose();
        if (dashboard != null) {
            dashboard.setVisible(true);
        }
    }
});
}}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

