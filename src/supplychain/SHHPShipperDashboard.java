package supplychain;
import java.awt.*;
import javax.swing.*;

public class SHHPShipperDashboard extends javax.swing.JFrame {
    private java.util.List<Event> events = new java.util.ArrayList<>();
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SHHPShipperDashboard.class.getName());
 private Shipper shipper;
private java.util.List<String> statuses;
private StatusStrategy strategy;
private JLabel statusLabel;
private boolean isRegistered = false;
    
    public SHHPShipperDashboard() {
        
        initComponents();
        events.add(new Event("CTRL-EVT-1", "Registered",
        "Shipment SHP-001 created at Cairo Factory", "Today 09:00"));

events.add(new Event("CTRL-EVT-2", "Picked Up",
        "Shipment collected by Mohamed Ali", "Today 09:30"));

events.add(new Event("CTRL-EVT-3", "Location Updated",
        "Shipment moved to Cairo Highway", "Today 10:30"));
        shipper = new Shipper("Omar", "456", "o@mail.com");
statuses = new java.util.ArrayList<>();
strategy = new DefaultStatusStrategy();
getContentPane().removeAll();
getContentPane().setLayout(new BorderLayout());

JPanel sidebar = new JPanel();
sidebar.setBackground(new Color(20, 30, 50));
sidebar.setPreferredSize(new Dimension(180, getHeight()));
sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

JLabel logo = new JLabel("SC System");
logo.setForeground(Color.WHITE);
logo.setFont(new Font("Segoe UI", Font.BOLD, 18));
logo.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

sidebar.add(logo);

String[] menu = {"Dashboard", "Update Status", "Track Pickup", "Track Shipment", "Event Log"};

for(String item : menu){

    JButton btn = new JButton(item);
    btn.setMaximumSize(new Dimension(160,40));
    btn.setBackground(new Color(30,40,60));
    btn.setForeground(Color.WHITE);
    btn.setFocusPainted(false);
    btn.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

    if(item.equals("Event Log")){
    btn.addActionListener(e -> {
        new SHHPEventLogUI(this, events).setVisible(true);
        this.setVisible(false);
    });
}
    if(item.equals("Track Shipment")){
        btn.addActionListener(e -> {
            new SHHPTrackShipment(this).setVisible(true);
            this.setVisible(false);
        });
    }

    if(item.equals("Update Status")){
        btn.addActionListener(e -> {
            new SHHPUpdateStatusUI(this).setVisible(true);
            this.setVisible(false);
        });
    }

    if(item.equals("Track Pickup")){
        btn.addActionListener(e -> {
            new SHHPTrackPickupUI().setVisible(true);
        });
    }

    sidebar.add(btn);
}

add(sidebar, BorderLayout.WEST);

JPanel header = new JPanel(new BorderLayout());
header.setBackground(Color.WHITE);
header.setPreferredSize(new Dimension(0,60));

JLabel title = new JLabel("Shipper Dashboard");
title.setFont(new Font("Segoe UI", Font.BOLD, 20));
title.setBorder(BorderFactory.createEmptyBorder(10,20,10,10));

JLabel user = new JLabel("Omar Ali (Shipper)");
user.setBorder(BorderFactory.createEmptyBorder(10,10,10,20));

header.add(title, BorderLayout.WEST);
header.add(user, BorderLayout.EAST);

add(header, BorderLayout.NORTH);

JPanel main = new JPanel(new BorderLayout());
main.setBackground(new Color(240,242,245));

JPanel stats = new JPanel(new GridLayout(1,4,10,10));
stats.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
stats.setBackground(new Color(240,242,245));

stats.add(createStat("SHP-001", "Active Shipment"));
stats.add(createStat("Registered", "Current Status"));
stats.add(createStat("Cairo Factory", "Location"));
stats.add(createStat("2", "Events Logged"));

main.add(stats, BorderLayout.NORTH);

JPanel center = new JPanel(new BorderLayout());
center.setBackground(new Color(240,242,245));

JPanel info = new JPanel(new GridLayout(2,2,10,10));
info.setBorder(BorderFactory.createTitledBorder("Shipment Info"));
info.setBackground(Color.WHITE);

JTextField shipmentField = new JTextField();

info.add(new JLabel("Shipment ID:"));
info.add(shipmentField);
info.add(new JLabel("Status:"));
statusLabel = new JLabel("Not Registered");

info.add(new JLabel("Shipment ID:"));
info.add(shipmentField);
info.add(new JLabel("Status:"));
info.add(statusLabel);

center.add(info, BorderLayout.NORTH);

JPanel cards = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
cards.setBackground(new Color(240,242,245));

JButton trackShipmentBtn = createCard("Track Shipment", new Color(0,123,255));JButton updateBtn = createCard("Update Status", new Color(40,167,69));
JButton pickupBtn = createCard("Track Pickup", new Color(255,193,7));



cards.add(trackShipmentBtn);
cards.add(updateBtn);
cards.add(pickupBtn);


trackShipmentBtn.addActionListener(e -> {
    new SHHPTrackShipment(this).setVisible(true);
    this.setVisible(false);
});

center.add(cards, BorderLayout.CENTER);

main.add(center, BorderLayout.CENTER);

JTextArea outputArea = new JTextArea(6,30);
outputArea.setEditable(false);
outputArea.setBorder(BorderFactory.createTitledBorder("Activity Log"));

main.add(new JScrollPane(outputArea), BorderLayout.SOUTH);

add(main, BorderLayout.CENTER);

trackShipmentBtn.addActionListener(e -> {
    String result = strategy.calculateStatus(statuses);
    outputArea.setText("Final Status: " + result);
});

updateBtn.addActionListener(e -> {
    new SHHPUpdateStatusUI(this).setVisible(true);
this.setVisible(false);
});
pickupBtn.addActionListener(e -> {
    new SHHPTrackPickupUI().setVisible(true);
});
JPopupMenu registerMenu = new JPopupMenu();

JMenuItem regItem = new JMenuItem("Register");
JMenuItem notRegItem = new JMenuItem("Not Register");

registerMenu.add(regItem);
registerMenu.add(notRegItem);

statusLabel.addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        registerMenu.show(statusLabel, 0, statusLabel.getHeight());
    }
});

regItem.addActionListener(e -> {
    statusLabel.setText("Registered");
    statusLabel.setForeground(new Color(40,167,69));
});

notRegItem.addActionListener(e -> {
    statusLabel.setText("Not Registered");
    statusLabel.setForeground(Color.RED);
});

    }

private JButton createCard(String text, Color color){
    JButton btn = new JButton(text);
    btn.setBackground(color);
    btn.setForeground(Color.WHITE);
    btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
    btn.setFocusPainted(false);
    btn.setPreferredSize(new Dimension(150, 60));
    btn.setBorder(BorderFactory.createLineBorder(new Color(180,180,180)));
    return btn;
}
private JPanel createStat(String value, String title){
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(Color.WHITE);
    panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

    JLabel val = new JLabel(value);
    val.setFont(new Font("Segoe UI", Font.BOLD, 16));

    JLabel t = new JLabel(title);
    t.setForeground(Color.GRAY);

    panel.add(val, BorderLayout.CENTER);
    panel.add(t, BorderLayout.SOUTH);

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
        java.awt.EventQueue.invokeLater(() -> new SHHPShipperDashboard().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
