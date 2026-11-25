package supplychain;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
public class ManuExportProductPanel extends javax.swing.JPanel {
    private JTextField txtProductId, txtDestination, txtQuantity;
    private JButton btnExport;
    private JTable table;
    private DefaultTableModel model;

    private ManuDashboardPanel dashboard;
    private ManuEventLogPanel eventLog;
    public ManuExportProductPanel(ManuDashboardPanel dashboard, ManuEventLogPanel eventLog) {
        initComponents();
        this.dashboard = dashboard;
        this.eventLog = eventLog;
        setupUI();
        setupActions();
    }
    private void setupUI() {

        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBorder(new EmptyBorder(20, 20, 20, 20));
        main.setOpaque(false);

        JLabel title = new JLabel("Export Product");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JLabel subtitle = new JLabel("Mark products as ready and export them for shipment.");
        subtitle.setForeground(Color.GRAY);

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setOpaque(false);

        header.add(title);
        header.add(Box.createVerticalStrut(5));
        header.add(subtitle);

        main.add(header);
        main.add(Box.createVerticalStrut(20));

        JPanel formCard = createCard("Export Product Form");
        formCard.setLayout(new BorderLayout(10, 10));
        formCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

        JPanel form = new JPanel(new GridLayout(3, 1, 10, 10));
        form.setOpaque(false);

        txtProductId = createField("Product ID (e.g. PROD-001)");
        txtDestination = createField("Export From Location");
        txtQuantity = createField("Quantity");

        form.add(txtProductId);
        form.add(txtDestination);
        form.add(txtQuantity);

        formCard.add(form, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPanel.setOpaque(false);

        btnExport = createPrimaryButton("Export Product");

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> clearFields());

        btnPanel.add(btnExport);
        btnPanel.add(btnCancel);

        formCard.add(btnPanel, BorderLayout.SOUTH);

        main.add(formCard);
        main.add(Box.createVerticalStrut(20));

        JPanel tableCard = createCard("Export History");
        tableCard.setLayout(new BorderLayout());
        tableCard.setPreferredSize(new Dimension(0, 300));

        model = new DefaultTableModel(
                new String[]{"Product ID", "Exported From", "Date", "Status"}, 0
        );

        table = new JTable(model);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(null);

        tableCard.add(scroll, BorderLayout.CENTER);

        main.add(tableCard);

        add(main, BorderLayout.CENTER);
    }

    private void setupActions() {

        btnExport.addActionListener(e -> {

            String id = txtProductId.getText().trim();
            String location = txtDestination.getText().trim();
            String qtyStr = txtQuantity.getText().trim();

            if (id.isEmpty() || location.isEmpty() || qtyStr.isEmpty()
                    || id.startsWith("Product") || location.startsWith("Export")) {

                JOptionPane.showMessageDialog(this, "Please fill all fields correctly.");
                return;
            }

            int qty;
            try {
                qty = Integer.parseInt(qtyStr);
                if (qty <= 0) throw new Exception();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Quantity must be a valid number.");
                return;
            }

            String time = new SimpleDateFormat("HH:mm").format(new Date());

            model.addRow(new Object[]{
                    id,
                    location,
                    "Today " + time,
                    "Exported"
            });

            if (eventLog != null) {
                eventLog.logEvent(
                        "Product Exported",
                        "Product " + id + " exported from " + location,
                        time
                );
            }

            if (dashboard != null) {
                dashboard.incrementExports();
                dashboard.addActivity("Product " + id + " exported", time);
            }

            JOptionPane.showMessageDialog(this,
                    "Product " + id + " exported successfully!");

            clearFields();
        });
    }

    private void clearFields() {
        txtProductId.setText("Product ID (e.g. PROD-001)");
        txtDestination.setText("Export From Location");
        txtQuantity.setText("Quantity");

        txtProductId.setForeground(Color.GRAY);
        txtDestination.setForeground(Color.GRAY);
        txtQuantity.setForeground(Color.GRAY);
    }

    private JPanel createCard(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new CompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel lbl = new JLabel(title);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));

        panel.add(lbl, BorderLayout.NORTH);
        return panel;
    }

    private JTextField createField(String hint) {
        JTextField field = new JTextField(hint);
        field.setForeground(Color.GRAY);

        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (field.getText().equals(hint)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }
        });

        return field;
    }

    private JButton createPrimaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(255, 153, 0));
        btn.setForeground(Color.WHITE);
        return btn;
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
