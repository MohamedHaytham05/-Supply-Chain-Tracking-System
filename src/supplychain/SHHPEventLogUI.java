package supplychain;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SHHPEventLogUI extends javax.swing.JFrame {
    private JTable table;
    private DefaultTableModel model;
    SHHPShipperDashboard dashboard;
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SHHPEventLogUI.class.getName());

    public SHHPEventLogUI(SHHPShipperDashboard dashboard, List<Event> events) {
        initComponents();
        getContentPane().removeAll();
getContentPane().setLayout(new BorderLayout());
        this.dashboard = dashboard;
setTitle("Event Log");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Event Log");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        JButton backBtn = new JButton("Back");

        header.add(title, BorderLayout.WEST);
        header.add(backBtn, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        String[] columns = {"Event ID", "Type", "Description", "Timestamp"};
        model = new DefaultTableModel(columns, 0);

        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        add(scroll, BorderLayout.CENTER);

        for(Event e : events){
            model.addRow(new Object[]{
                e.getId(),
                e.getType(),
                e.getDescription(),
                e.getTime()
            });
        }

        backBtn.addActionListener(e -> {
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
