package supplychain;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
public class ManuDashboardPanel extends javax.swing.JPanel {
    private int shipmentCount = 0;
    private int exportCount = 0;
    private int productCount = 0;
    private JLabel shipmentsValue, exportsValue, productsValue, pendingValue;
    private JLabel lblId, lblStatus, lblLocation, lblStorage, lblDelivery, lblProducts;
    private JPanel activityPanel;
    private List<JLabel> activityDots = new ArrayList<>();
    private List<String> registeredProducts = new ArrayList<>();
    public ManuDashboardPanel() {
        initComponents();
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));
        setupUI();
    }
    private void setupUI() {

        JPanel topCards = new JPanel(new GridLayout(1, 4, 15, 15));
        topCards.setBorder(new EmptyBorder(15, 15, 15, 15));
        topCards.setOpaque(false);

        productsValue = createValueLabel("0", new Color(13,110,253));
        exportsValue = createValueLabel("0", new Color(13,110,253));
        shipmentsValue = createValueLabel("0", new Color(25,135,84));
        pendingValue = createValueLabel("0", new Color(255,193,7));

        topCards.add(createCard("PRODUCTS REGISTERED", productsValue, "P", new Color(220,230,255)));
        topCards.add(createCard("EXPORTED TODAY", exportsValue, "E", new Color(220,240,255)));
        topCards.add(createCard("ACTIVE SHIPMENTS", shipmentsValue, "S", new Color(220,255,230)));
        
        add(topCards, BorderLayout.NORTH);

        JSplitPane split = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                createActivityCard(),
                createOverviewCard()
        );

        split.setDividerLocation(500);
        split.setResizeWeight(0.6);
        split.setBorder(null);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBorder(new EmptyBorder(10, 15, 15, 15));
        wrapper.setOpaque(false);
        wrapper.add(split, BorderLayout.CENTER);

        add(wrapper, BorderLayout.CENTER);
    }

    private JPanel createActivityCard() {

        JPanel card = createMainCard("Recent Activity");

        activityPanel = new JPanel();
        activityPanel.setLayout(new BoxLayout(activityPanel, BoxLayout.Y_AXIS));
        activityPanel.setBackground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(activityPanel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        card.add(scroll, BorderLayout.CENTER);
        return card;
    }

    private JPanel createOverviewCard() {

        JPanel card = createMainCard("Shipment Overview");

        JPanel grid = new JPanel(new GridLayout(6, 2, 0, 15));
        grid.setBackground(Color.WHITE);
        grid.setBorder(new EmptyBorder(10, 10, 10, 10));

        lblId = createValue("-");
        lblStatus = createValue("-");
        lblLocation = createValue("-");
        lblStorage = createValue("-");
        lblDelivery = createValue("-");
        lblProducts = createValue("-");

        addRow(grid, "Active Shipment", lblId);
        addRow(grid, "Status", lblStatus);
        addRow(grid, "Location", lblLocation);
        addRow(grid, "Storage", lblStorage);
        addRow(grid, "Delivery", lblDelivery);
        addRow(grid, "Products", lblProducts);

        card.add(grid, BorderLayout.CENTER);
        return card;
    }

    private JPanel createMainCard(String title) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new CompoundBorder(
                new LineBorder(new Color(220,220,220),1,true),
                new EmptyBorder(10,10,10,10)
        ));

        JLabel header = new JLabel(title);
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBorder(new EmptyBorder(5,5,10,5));

        panel.add(header, BorderLayout.NORTH);
        return panel;
    }

    private JPanel createCard(String title, JLabel value, String iconText, Color iconBg) {

        JPanel panel = new JPanel(new BorderLayout(10,10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new CompoundBorder(
                new LineBorder(new Color(220,220,220),1,true),
                new EmptyBorder(15,15,15,15)
        ));

        JPanel icon = new JPanel(new GridBagLayout());
        icon.setPreferredSize(new Dimension(45,45));
        icon.setBackground(iconBg);

        JLabel iconLabel = new JLabel(iconText);
        iconLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        icon.add(iconLabel);

        JPanel text = new JPanel();
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.setOpaque(false);

        JLabel t = new JLabel(title);
        t.setForeground(Color.GRAY);
        t.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        text.add(t);
        text.add(Box.createVerticalStrut(5));
        text.add(value);

        panel.add(icon, BorderLayout.WEST);
        panel.add(text, BorderLayout.CENTER);

        return panel;
    }

    private void addRow(JPanel panel, String title, JLabel value) {
        JLabel lbl = new JLabel(title);
        lbl.setForeground(new Color(120,120,120));
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        value.setFont(new Font("Segoe UI", Font.BOLD, 13));

        panel.add(lbl);
        panel.add(value);
    }

    private JLabel createValueLabel(String text, Color color) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lbl.setForeground(color);
        return lbl;
    }

    private JLabel createValue(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setHorizontalAlignment(SwingConstants.RIGHT);
        return lbl;
    }

    public void incrementProducts() {
        productCount++;
        productsValue.setText(String.valueOf(productCount));
    }

    public void incrementShipments() {
        shipmentCount++;
        shipmentsValue.setText(String.valueOf(shipmentCount));
    }

    public void incrementExports() {
        exportCount++;
        exportsValue.setText(String.valueOf(exportCount));
    }

    public void addRegisteredProduct(String id) {
        registeredProducts.add(id);
    }

    public List<String> getRegisteredProducts() {
        return registeredProducts;
    }

    public void updateShipmentOverview(String id, String status, String location,
                                       String storage, String delivery, int products) {

        lblId.setText(id);
        lblStatus.setText(status);
        lblLocation.setText(location);
        lblStorage.setText(storage);
        lblDelivery.setText(delivery);
        lblProducts.setText(products + " registered");
    }

    public void addActivity(String text, String time) {

        for (JLabel dot : activityDots) {
            dot.setForeground(new Color(25,135,84));
        }

        JPanel item = new JPanel(new BorderLayout(10,0));
        item.setOpaque(false);
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JLabel dot = new JLabel("●");
        dot.setForeground(new Color(13,110,253));
        dot.setFont(new Font("Segoe UI", Font.BOLD, 14));

        activityDots.add(dot);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        JLabel title = new JLabel(text);
        title.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JLabel subtitle = new JLabel("Today " + time);
        subtitle.setForeground(Color.GRAY);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        textPanel.add(title);
        textPanel.add(subtitle);

        item.add(dot, BorderLayout.WEST);
        item.add(textPanel, BorderLayout.CENTER);

        activityPanel.add(item);

        activityPanel.revalidate();
        activityPanel.repaint();
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
