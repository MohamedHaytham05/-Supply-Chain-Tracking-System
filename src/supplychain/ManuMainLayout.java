package supplychain;
import javax.swing.*;
import java.awt.*;
public class ManuMainLayout extends javax.swing.JFrame {
    private CardLayout cardLayout;
    private JPanel content;
    private ManuDashboardPanel dashboard;
    public ManuMainLayout() {
        initComponents();
        setupLayout();
    }
    private void setupLayout() {

        setTitle("Supply Chain Tracking System");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(new Color(36, 64, 98));
        top.setPreferredSize(new Dimension(1200, 60));

        JLabel title = new JLabel("  Supply Chain Tracking System");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        right.setOpaque(false);

        JLabel user = new JLabel("Ahmed Hassan");
        user.setForeground(Color.WHITE);

        JLabel role = new JLabel("Manufacturer");
        role.setOpaque(true);
        role.setBackground(new Color(13, 110, 253));
        role.setForeground(Color.WHITE);
        role.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JButton logout = new JButton("Logout");

        right.add(user);
        right.add(role);
        right.add(logout);

        top.add(title, BorderLayout.WEST);
        top.add(right, BorderLayout.EAST);

        add(top, BorderLayout.NORTH);

        JPanel sidebar = new JPanel(new GridLayout(7, 1, 0, 10));
        sidebar.setBackground(new Color(30, 55, 85));
        sidebar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        sidebar.setPreferredSize(new Dimension(220, 600));

        JButton btnDashboard = createSidebarButton("Dashboard");
        JButton btnRegister = createSidebarButton("Register Product");
        JButton btnExport = createSidebarButton("Export Product");
        JButton btnCreate = createSidebarButton("Create Shipment");
        JButton btnStatus = createSidebarButton("Shipment Status");
        JButton btnEvent = createSidebarButton("Event Log");

        sidebar.add(btnDashboard);
        sidebar.add(btnRegister);
        sidebar.add(btnExport);
        sidebar.add(btnCreate);
        sidebar.add(btnStatus);
        sidebar.add(btnEvent);

        add(sidebar, BorderLayout.WEST);

        cardLayout = new CardLayout();
        content = new JPanel(cardLayout);

        // ✅ CREATE SHARED OBJECTS (VERY IMPORTANT)
        dashboard = new ManuDashboardPanel();
        ManuEventLogPanel eventPanel = new ManuEventLogPanel();
        ManuShipmentStatusPanel statusPanel = new ManuShipmentStatusPanel();

        // ✅ PASS SAME INSTANCES TO ALL PANELS
        ManuRegisterProductPanel registerPanel =
                new ManuRegisterProductPanel(dashboard, eventPanel);

        ManuExportProductPanel exportPanel =
                new ManuExportProductPanel(dashboard, eventPanel);

        ManuCreateShipmentPanel createPanel =
                new ManuCreateShipmentPanel(dashboard, statusPanel, eventPanel);

        // ✅ ADD TO CARD LAYOUT (USE SAME eventPanel, NOT new)
        content.add(dashboard, "Dashboard");
        content.add(registerPanel, "Register");
        content.add(exportPanel, "Export");
        content.add(createPanel, "Create");
        content.add(statusPanel, "Status");
        content.add(eventPanel, "Event");

        add(content, BorderLayout.CENTER);

        btnDashboard.addActionListener(e -> cardLayout.show(content, "Dashboard"));
        btnRegister.addActionListener(e -> cardLayout.show(content, "Register"));
        btnExport.addActionListener(e -> cardLayout.show(content, "Export"));
        btnCreate.addActionListener(e -> cardLayout.show(content, "Create"));
        btnStatus.addActionListener(e -> cardLayout.show(content, "Status"));
        btnEvent.addActionListener(e -> cardLayout.show(content, "Event"));

        cardLayout.show(content, "Dashboard");

        revalidate();
        repaint();
    }

    private JButton createSidebarButton(String text) {

        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(52, 86, 120));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(12, 10, 12, 10));
        btn.setHorizontalAlignment(SwingConstants.LEFT);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(70, 110, 150));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(52, 86, 120));
            }
        });

        return btn;
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ManuMainLayout.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManuMainLayout.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManuMainLayout.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManuMainLayout.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManuMainLayout().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
