package supplychain;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
public class ManuEventLogPanel extends javax.swing.JPanel {
    
    private JTable table;
    private DefaultTableModel tableModel;
    private DefaultTableModel masterModel;
    private JTextField txtSearch;
    private JComboBox<String> cmbType;
    private JTextField txtDate;

    private int eventCounter = 1;
    public ManuEventLogPanel() {
        initComponents();
        setupUI();
    }
    private void setupUI() {

        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        JPanel container = new JPanel(new BorderLayout(15,15));
        container.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        container.setBackground(new Color(245,247,250));

        JPanel filterPanel = new JPanel(new GridLayout(1,4,10,10));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Search & Filter"));
        filterPanel.setBackground(Color.WHITE);

        txtSearch = new JTextField();
        txtSearch.setBorder(BorderFactory.createTitledBorder("Search events..."));

        cmbType = new JComboBox<>(new String[]{
                "All",
                "Product Registered",
                "Product Exported",
                "Shipment Created"
        });
        cmbType.setBorder(BorderFactory.createTitledBorder("Filter by type"));

        txtDate = new JTextField();
        txtDate.setBorder(BorderFactory.createTitledBorder("Filter by date"));

        JButton btnClear = new JButton("Clear Filters");

        filterPanel.add(txtSearch);
        filterPanel.add(cmbType);
        filterPanel.add(txtDate);
        filterPanel.add(btnClear);

        String[] cols = {"Event ID", "Type", "Description", "Timestamp"};

        masterModel = new DefaultTableModel(cols, 0);
        tableModel = masterModel;

        table = new JTable(tableModel);
        table.setRowHeight(28);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("All Events"));

        container.add(filterPanel, BorderLayout.NORTH);
        container.add(scroll, BorderLayout.CENTER);

        add(container, BorderLayout.CENTER);

        btnClear.addActionListener(e -> {
            txtSearch.setText("");
            txtDate.setText("");
            cmbType.setSelectedIndex(0);
            applyFilter();
        });

        txtSearch.addActionListener(e -> applyFilter());
        cmbType.addActionListener(e -> applyFilter());
        txtDate.addActionListener(e -> applyFilter());
    }

    private void applyFilter() {

        String search = txtSearch.getText().toLowerCase();
        String type = cmbType.getSelectedItem().toString();
        String date = txtDate.getText().toLowerCase();

        DefaultTableModel filtered = new DefaultTableModel(
                new String[]{"Event ID", "Type", "Description", "Timestamp"}, 0
        );

        for (int i = 0; i < masterModel.getRowCount(); i++) {

            String id = masterModel.getValueAt(i,0).toString().toLowerCase();
            String t = masterModel.getValueAt(i,1).toString();
            String desc = masterModel.getValueAt(i,2).toString().toLowerCase();
            String time = masterModel.getValueAt(i,3).toString().toLowerCase();

            boolean matchSearch = id.contains(search) || desc.contains(search);
            boolean matchType = type.equals("All") || t.equals(type);
            boolean matchDate = date.isEmpty() || time.contains(date);

            if (matchSearch && matchType && matchDate) {
                filtered.addRow(new Object[]{
                        masterModel.getValueAt(i,0),
                        masterModel.getValueAt(i,1),
                        masterModel.getValueAt(i,2),
                        masterModel.getValueAt(i,3)
                });
            }
        }

        table.setModel(filtered);
    }

    public void logEvent(String type, String desc, String time) {
        String id = "EVT-" + String.format("%03d", eventCounter++);
        masterModel.addRow(new Object[]{id, type, desc, time});
        applyFilter();
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
