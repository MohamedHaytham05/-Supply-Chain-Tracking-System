package supplychain;
import java.awt.*;
import javax.swing.*;

public class SHHPTrackPickupUI extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SHHPTrackPickupUI.class.getName());
private SHHPShipperDashboard dashboard;
private CardLayout cardLayout;
private JPanel mainPanel;
  
    public SHHPTrackPickupUI() {
        initComponents();
    setTitle("Pickup Tracking");
        setSize(900, 600);
        setLocationRelativeTo(null);
setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);        
        getContentPane().removeAll();
        getContentPane().setLayout(new BorderLayout());
        
        getContentPane().add(createPickupPanel());
        
        getContentPane().revalidate();
        getContentPane().repaint();
    }
    
    private JPanel createPickupPanel() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(new Color(240, 242, 245));
        
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Color.WHITE);
        top.setBorder(BorderFactory.createTitledBorder("Pickup Tracking"));
        
        JTextArea trackingArea = new JTextArea();
        trackingArea.setText("Shipment SHP-001\n\nStatus: Picked Up\nLocation: Cairo Factory");
        trackingArea.setEditable(false);
        trackingArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        top.add(new JScrollPane(trackingArea), BorderLayout.CENTER);
        
        JPanel btns = new JPanel();
        
        JButton confirmBtn = new JButton("Confirm Pickup");
        JButton issueBtn = new JButton("Report Issue");
        
        confirmBtn.setBackground(new Color(0, 123, 255));
        confirmBtn.setForeground(Color.WHITE);
        
        btns.add(confirmBtn);
        btns.add(issueBtn);
        
        top.add(btns, BorderLayout.SOUTH);
        
        JPanel notesPanel = new JPanel(new BorderLayout());
        notesPanel.setBackground(Color.WHITE);
        notesPanel.setBorder(BorderFactory.createTitledBorder("Pickup Notes"));
        
        JTextArea notes = new JTextArea(
            "No damage found during pickup inspection.\nPackage secured and loaded."
        );
        notes.setEditable(false);
        notesPanel.add(notes, BorderLayout.CENTER);
        
        JPanel trackingPanel = new JPanel();
        trackingPanel.setLayout(new BoxLayout(trackingPanel, BoxLayout.Y_AXIS));
        trackingPanel.setBackground(new Color(240, 242, 245));
        trackingPanel.setBorder(BorderFactory.createTitledBorder("Shipment Route"));
        
        trackingPanel.add(createStep("Cairo Factory", "Origin / Pickup", Color.GREEN));
        trackingPanel.add(createStep("Cairo Warehouse", "Next Stop", Color.BLUE));
        trackingPanel.add(createStep("Cairo Retail Hub", "Final Destination", Color.LIGHT_GRAY));
        
        JPanel center = new JPanel(new GridLayout(1, 2, 15, 15));
        center.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        center.setBackground(new Color(240, 242, 245));
        
        center.add(notesPanel);
        center.add(trackingPanel);
        
        main.add(top, BorderLayout.NORTH);
        main.add(center, BorderLayout.CENTER);
        
        confirmBtn.addActionListener(e -> {
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
        issueBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Issue reported to dispatcher!");
        });
        
        return main;
    }
    
    private JPanel createStep(String title, String desc, Color color) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel dot = new JLabel("●");
        dot.setForeground(color);
        dot.setFont(new Font("Segoe UI", Font.BOLD, 16));
        
        JLabel text = new JLabel(title + " - " + desc);
        text.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        panel.add(dot, BorderLayout.WEST);
        panel.add(text, BorderLayout.CENTER);
        
        return panel;
        
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
        java.awt.EventQueue.invokeLater(() -> new SHHPTrackPickupUI().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
