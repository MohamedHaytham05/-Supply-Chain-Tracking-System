package supplychain;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
public class ManuCreateShipmentPanel extends javax.swing.JPanel {
        private JTextField txtShipmentId, txtOrigin, txtStorageId, txtShelf;
    private JButton btnCreate, btnClear;
    private JTable table;
    private DefaultTableModel model;

    private ManuDashboardPanel dashboard;
    private ManuShipmentStatusPanel statusPanel;
    private ManuEventLogPanel eventLog;
    public ManuCreateShipmentPanel(ManuDashboardPanel dashboard, ManuShipmentStatusPanel statusPanel, ManuEventLogPanel eventLog) {
        this.dashboard = dashboard;
        this.statusPanel = statusPanel;
        this.eventLog = eventLog;
        initComponents();
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

        JLabel title = new JLabel("Create Shipment");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JLabel subtitle = new JLabel("Start a new shipment and assign storage.");
        subtitle.setForeground(Color.GRAY);

        main.add(title);
        main.add(Box.createVerticalStrut(5));
        main.add(subtitle);
        main.add(Box.createVerticalStrut(20));

        JPanel formCard = createCard("New Shipment Form");
        formCard.setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridLayout(4, 2, 15, 15));
        form.setOpaque(false);

        txtShipmentId = createField("SHP-001");
        txtOrigin = createField("Cairo Factory");
        txtStorageId = createField("STR-001");
        txtShelf = createField("A1");

        form.add(new JLabel("Shipment ID")); form.add(txtShipmentId);
        form.add(new JLabel("Origin Location")); form.add(txtOrigin);
        form.add(new JLabel("Storage ID")); form.add(txtStorageId);
        form.add(new JLabel("Shelf Number")); form.add(txtShelf);

        formCard.add(form, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPanel.setOpaque(false);

        btnCreate = createPrimaryButton("Create Shipment");
        btnClear = new JButton("Clear");

        btnPanel.add(btnCreate);
        btnPanel.add(btnClear);

        formCard.add(btnPanel, BorderLayout.SOUTH);

        main.add(formCard);
        main.add(Box.createVerticalStrut(20));

        model = new DefaultTableModel(
                new String[]{"Shipment ID", "Origin", "Status", "Storage"}, 0
        );

        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        JPanel tableCard = createCard("Active Shipments");
        tableCard.setLayout(new BorderLayout());
        tableCard.add(scroll, BorderLayout.CENTER);

        main.add(tableCard);

        add(main, BorderLayout.CENTER);
    }

    private void setupActions() {
        btnCreate.addActionListener(e -> createShipment());
        btnClear.addActionListener(e -> clearFields());
    }

    private void createShipment() {

        String id = txtShipmentId.getText().trim();
        String origin = txtOrigin.getText().trim();
        String storage = txtStorageId.getText().trim();
        String shelf = txtShelf.getText().trim();

        if (id.isEmpty() || origin.isEmpty() || storage.isEmpty() || shelf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill all fields correctly.");
            return;
        }

        String location = storage + " / " + shelf;
        String time = new SimpleDateFormat("HH:mm").format(new Date());

        model.addRow(new Object[]{
                id,
                origin,
                "Registered",
                location
        });

        if (statusPanel != null) {
            statusPanel.addShipment(id, origin, "Registered", location);
        }

        if (eventLog != null) {
            eventLog.logEvent(
                    "Shipment Created",
                    "Shipment " + id + " created",
                    time
            );
        }

        if (dashboard != null) {

            dashboard.incrementShipments();

            dashboard.updateShipmentOverview(
                    id,
                    "Registered",
                    origin,
                    location,
                    "DEL-" + id,
                    1
            );

            dashboard.addActivity(
                    "Shipment " + id + " created",
                    time
            );
        }

        JOptionPane.showMessageDialog(this, "Shipment Created Successfully!");
        clearFields();
    }

    private void clearFields() {
        txtShipmentId.setText("");
        txtOrigin.setText("");
        txtStorageId.setText("");
        txtShelf.setText("");
    }

    private JPanel createCard(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new LineBorder(new Color(220,220,220)));

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
        btn.setBackground(new Color(52,120,246));
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
