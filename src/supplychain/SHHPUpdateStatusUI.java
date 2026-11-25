package supplychain;
import java.awt.*;
import javax.swing.*;

public class SHHPUpdateStatusUI extends javax.swing.JFrame {
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SHHPUpdateStatusUI.class.getName());
 SHHPShipperDashboard dashboard;

    public SHHPUpdateStatusUI(SHHPShipperDashboard aThis) {
         this.dashboard = aThis;
        initComponents();
        getContentPane().removeAll();
getContentPane().setLayout(new BorderLayout());

setTitle("Update Shipment Status");
setSize(600, 450);
setLocationRelativeTo(null);
setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

JPanel main = new JPanel(new BorderLayout());
main.setBackground(new Color(240,242,245));

JPanel form = new JPanel(new GridLayout(6,1,10,10));
form.setBorder(BorderFactory.createTitledBorder("Update Status Form"));
form.setBackground(Color.WHITE);

JTextField shipmentField = new JTextField();

JComboBox<String> statusBox = new JComboBox<>(new String[]{
    "Registered","Picked Up","In Transit","Out for Delivery","Arrived"
});

JTextField noteField = new JTextField();

JButton updateBtn = new JButton("Update Status");
JButton cancelBtn = new JButton("Cancel");

form.add(new JLabel("Shipment ID"));
form.add(shipmentField);
form.add(new JLabel("New Status"));
form.add(statusBox);
form.add(new JLabel("Note"));
form.add(noteField);

JPanel buttons = new JPanel();
buttons.add(updateBtn);
buttons.add(cancelBtn);

main.add(form, BorderLayout.CENTER);
main.add(buttons, BorderLayout.SOUTH);

add(main, BorderLayout.CENTER);

revalidate();
repaint();

updateBtn.addActionListener(e -> {
    String id = shipmentField.getText();
    if(id.isEmpty()){
        JOptionPane.showMessageDialog(this, "Enter Shipment ID");
        return;
    }
    JOptionPane.showMessageDialog(this, "Status Updated");
});

cancelBtn.addActionListener(e -> {
    this.dispose();
    dashboard.setVisible(true);
});
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
