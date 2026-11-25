package supplychain;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
public class ManuShipmentStatusPanel extends javax.swing.JPanel {
    private JTable table;
    private DefaultTableModel tableModel;

    private JLabel dId, dStatus, dOrigin, dLocation, dStorage, dShelf, dDelivery, dProducts, dCreated;
   
    public ManuShipmentStatusPanel() {
        initComponents();
        setupUI();
    }
    private void setupUI() {

        setLayout(new BorderLayout());
        setBackground(new Color(245,247,250));

        JPanel container = new JPanel(new BorderLayout(15,15));
        container.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        container.setBackground(new Color(245,247,250));

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> refreshTable());

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topBar.setOpaque(false);
        topBar.add(refreshBtn);

        String[] cols = {"ID", "Origin", "Status", "Location"};

        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);
        table.setRowHeight(28);

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBorder(BorderFactory.createTitledBorder("All Shipments"));

        JPanel details = new JPanel(new GridLayout(9,1,5,5));
        details.setBorder(BorderFactory.createTitledBorder("Shipment Detail"));

        dId = new JLabel();
        dStatus = new JLabel();
        dOrigin = new JLabel();
        dLocation = new JLabel();
        dStorage = new JLabel();
        dShelf = new JLabel();
        dDelivery = new JLabel();
        dProducts = new JLabel();
        dCreated = new JLabel();

        details.add(createRow("Shipment ID", dId));
        details.add(createRow("Status", dStatus));
        details.add(createRow("Origin", dOrigin));
        details.add(createRow("Current Location", dLocation));
        details.add(createRow("Storage ID", dStorage));
        details.add(createRow("Shelf", dShelf));
        details.add(createRow("Delivery ID", dDelivery));
        details.add(createRow("Products", dProducts));
        details.add(createRow("Created", dCreated));

        JPanel center = new JPanel(new GridLayout(1,2,15,15));
        center.add(tableScroll);
        center.add(details);

        container.add(topBar, BorderLayout.NORTH); // ✅ replaced old top panel
        container.add(center, BorderLayout.CENTER);

        add(container, BorderLayout.CENTER);

        table.getSelectionModel().addListSelectionListener(e -> {

            int row = table.getSelectedRow();

            if (row >= 0) {

                String id = table.getValueAt(row,0).toString();
                String origin = table.getValueAt(row,1).toString();
                String status = table.getValueAt(row,2).toString();
                String location = table.getValueAt(row,3).toString();

                String[] parts = location.split(" / ");
                String storage = parts.length > 0 ? parts[0] : "";
                String shelf = parts.length > 1 ? parts[1] : "";

                dId.setText(id);
                dStatus.setText(status);
                dOrigin.setText(origin);
                dLocation.setText(location);
                dStorage.setText(storage);
                dShelf.setText(shelf);
                dDelivery.setText("DEL-" + id);
                dProducts.setText("1 item");
                dCreated.setText("Today");
            }
        });
    }

    private JPanel createRow(String label, JLabel value) {
        JPanel row = new JPanel(new BorderLayout());
        row.add(new JLabel(label + ": "), BorderLayout.WEST);
        row.add(value, BorderLayout.EAST);
        return row;
    }

    public void addShipment(String id, String origin, String status, String location) {
        tableModel.addRow(new Object[]{id, origin, status, location});
    }

    private void refreshTable() {
        table.repaint();
        JOptionPane.showMessageDialog(this, "Refreshed!");
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
