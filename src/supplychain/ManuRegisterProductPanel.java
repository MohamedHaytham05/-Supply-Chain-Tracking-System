package supplychain;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
public class ManuRegisterProductPanel extends javax.swing.JPanel {
    private JTextField txtId, txtName, txtCategory, txtQty, txtDate, txtPrice;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnAdd, btnClear;

    private ManuDashboardPanel dashboard;
    private ManuEventLogPanel eventLog;
    public ManuRegisterProductPanel(ManuDashboardPanel dashboard, ManuEventLogPanel eventLog) {
        this.dashboard = dashboard;
        this.eventLog = eventLog;
        initComponents();
        buildUI();
        setupLogic();
    }
    private void buildUI() {

        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        JLabel title = new JLabel("Register Product");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setBorder(new EmptyBorder(15, 20, 10, 10));
        add(title, BorderLayout.NORTH);

        JPanel mainCenter = new JPanel(new BorderLayout());
        mainCenter.setOpaque(false);
        add(mainCenter, BorderLayout.CENTER);

        JPanel topSection = new JPanel(new GridLayout(1, 2, 20, 0));
        topSection.setBorder(new EmptyBorder(10, 20, 10, 20));
        topSection.setOpaque(false);
        mainCenter.add(topSection, BorderLayout.NORTH);

        JPanel card = createCard();
        card.setLayout(new BorderLayout(10,10));

        JPanel form = new JPanel(new GridLayout(6, 2, 10, 10));
        form.setOpaque(false);

        txtId = createField("e.g. PROD-001");
        txtName = createField("e.g. Electronics Box");
        txtCategory = createField("e.g. Electronics");
        txtQty = createField("e.g. 100");
        txtDate = createField("YYYY-MM-DD");
        txtPrice = createField("e.g. 250.00");

        form.add(new JLabel("Product ID")); form.add(txtId);
        form.add(new JLabel("Product Name")); form.add(txtName);
        form.add(new JLabel("Category")); form.add(txtCategory);
        form.add(new JLabel("Quantity")); form.add(txtQty);
        form.add(new JLabel("Production Date")); form.add(txtDate);
        form.add(new JLabel("Unit Price")); form.add(txtPrice);

        card.add(form, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPanel.setOpaque(false);

        btnAdd = createPrimaryButton("Register Product");
        btnClear = new JButton("Clear");

        btnPanel.add(btnAdd);
        btnPanel.add(btnClear);

        card.add(btnPanel, BorderLayout.SOUTH);

        topSection.add(card);

        JPanel rightCard = createCard();
        rightCard.setLayout(new BoxLayout(rightCard, BoxLayout.Y_AXIS));

        rightCard.add(createHint("• Enter unique Product ID"));
        rightCard.add(createHint("• Fill product name & category"));
        rightCard.add(createHint("• Enter quantity"));
        rightCard.add(createHint("• Set production date"));
        rightCard.add(createHint("• Add unit price"));
        rightCard.add(createHint("• Click Register"));

        topSection.add(rightCard);

        model = new DefaultTableModel(
                new String[]{"Product ID", "Name", "Qty", "Date"}, 0
        );

        table = new JTable(model);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new EmptyBorder(10, 20, 20, 20));

        mainCenter.add(scroll, BorderLayout.CENTER);
    }

    private void setupLogic() {

        btnAdd.addActionListener(e -> {

            String id = txtId.getText().trim();
            String name = txtName.getText().trim();
            String category = txtCategory.getText().trim();
            String qtyStr = txtQty.getText().trim();
            String date = txtDate.getText().trim();
            String priceStr = txtPrice.getText().trim();

            if (!id.matches("PROD-\\d{3}")) {
                JOptionPane.showMessageDialog(this, "ID must be like PROD-001");
                return;
            }

            if (!name.matches("[a-zA-Z ]{3,}")) {
                JOptionPane.showMessageDialog(this, "Name must contain letters only");
                return;
            }

            if (!category.matches("[a-zA-Z ]{3,}")) {
                JOptionPane.showMessageDialog(this, "Category must contain letters only");
                return;
            }

            int qty;
            try {
                qty = Integer.parseInt(qtyStr);
                if (qty <= 0) throw new Exception();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Quantity must be a number");
                return;
            }

            if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                JOptionPane.showMessageDialog(this, "Date format: YYYY-MM-DD");
                return;
            }

            double price;
            try {
                price = Double.parseDouble(priceStr);
                if (price <= 0) throw new Exception();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Price must be valid");
                return;
            }

            model.addRow(new Object[]{id, name, qty, date});

            String time = new SimpleDateFormat("HH:mm").format(new Date());

            if (eventLog != null) {
                eventLog.logEvent(
                        "Product Registered",
                        "Product " + id + " registered",
                        time
                );
            }

            if (dashboard != null) {
                dashboard.incrementProducts();
                dashboard.addRegisteredProduct(id);
                dashboard.addActivity("Product " + id + " registered", time);
            }

            clearForm();
        });

        btnClear.addActionListener(e -> clearForm());
    }

    private void clearForm() {
        txtId.setText("");
        txtName.setText("");
        txtCategory.setText("");
        txtQty.setText("");
        txtDate.setText("");
        txtPrice.setText("");
    }

    private JPanel createCard() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(new CompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                new EmptyBorder(15, 15, 15, 15)
        ));
        return panel;
    }

    private JTextField createField(String hint) {
        JTextField field = new JTextField(hint);
        field.setForeground(Color.GRAY);
        field.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));

        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(hint)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(hint);
                    field.setForeground(Color.GRAY);
                }
            }
        });

        return field;
    }

    private JButton createPrimaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(52, 120, 246));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(8, 15, 8, 15));
        return btn;
    }

    private JLabel createHint(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(new Color(120, 120, 120));
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lbl.setBorder(new EmptyBorder(5, 0, 5, 0));
        return lbl;
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
