package supplychain;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class WarehouseDashboard extends JFrame {

    private final Color primaryBlue = new Color(26, 54, 104);
    private final Color sidebarDark = new Color(33, 43, 54);
    private final Color bgLight = new Color(240, 244, 249);
    
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainContent = new JPanel(cardLayout);
    private WarehouseStaffController controller = new WarehouseStaffController();

    public WarehouseDashboard() {
        setTitle("Supply Chain G17 - Warehouse System");
        setSize(1280, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Sidebar
        add(createSidebar(), BorderLayout.WEST);

        // Pages
        mainContent.add(createDashboardHome(), "Home");
        mainContent.add(createRecordArrivalPanel(), "ArrivalPage");
        mainContent.add(createArrangeStoragePanel(), "StoragePage");

        add(mainContent, BorderLayout.CENTER);
        
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(sidebarDark);
        sidebar.setPreferredSize(new Dimension(240, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel logo = new JLabel("SC Supply Chain G17");
        logo.setForeground(Color.WHITE); logo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        logo.setBorder(new EmptyBorder(30, 20, 30, 20));
        sidebar.add(logo);

        sidebar.add(createMenuButton("Dashboard", "Home"));
        sidebar.add(createMenuButton("Record Arrival", "ArrivalPage"));
        sidebar.add(createMenuButton("Arrange Storage", "StoragePage"));

        return sidebar;
    }

    private JButton createMenuButton(String text, String pageName) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(240, 45));
        btn.setBackground(sidebarDark);
        btn.setForeground(new Color(200, 200, 200));
        btn.setFocusable(false); btn.setBorderPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.addActionListener(e -> cardLayout.show(mainContent, pageName));
        return btn;
    }

    private JScrollPane createDashboardHome() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(bgLight);
        content.setBorder(new EmptyBorder(20, 25, 20, 25));

        JLabel header = new JLabel("Warehouse Staff Dashboard");
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        content.add(header);
        content.add(Box.createRigidArea(new Dimension(0, 20)));

        // Stats Cards
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        statsPanel.setOpaque(false);
        statsPanel.setMaximumSize(new Dimension(2000, 120));
        String[] stats = controller.getDashboardStats();
        statsPanel.add(createStatCard("ACTIVE SHIPMENT", stats[0], new Color(102, 51, 255)));
        statsPanel.add(createStatCard("STORAGE STATUS", stats[1], new Color(40, 167, 69)));
        statsPanel.add(createStatCard("INSPECTIONS", stats[2], new Color(23, 162, 184)));
        statsPanel.add(createStatCard("STEP", stats[3], new Color(255, 193, 7)));
        content.add(statsPanel);
        content.add(Box.createRigidArea(new Dimension(0, 30)));

        // Bottom (Table & Activity)
        JPanel bottom = new JPanel(new GridLayout(1, 2, 25, 0));
        bottom.setOpaque(false);
        bottom.setMaximumSize(new Dimension(2000, 400));
        bottom.add(createTableCard("Storage Details — STR-001"));
        bottom.add(createActivityCard("Recent Activity"));
        content.add(bottom);

        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        return scroll;
    }

    private JPanel createRecordArrivalPanel() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(bgLight);
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(30, 50, 30, 50));

        JTextField tf = new JTextField(20);
        JButton btn = new JButton("Save Arrival");
        btn.setBackground(primaryBlue); btn.setForeground(Color.WHITE);
        btn.addActionListener(e -> {
            controller.recordArrival(tf.getText());
            JOptionPane.showMessageDialog(this, "Success!");
            tf.setText("");
        });

        card.add(new JLabel("Shipment ID:")); card.add(tf);
        card.add(Box.createRigidArea(new Dimension(0, 20))); card.add(btn);
        wrapper.add(card);
        return wrapper;
    }

    private JPanel createArrangeStoragePanel() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(bgLight);
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(30, 50, 30, 50));

        JTextField f1 = new JTextField(20); JTextField f2 = new JTextField(20); JTextField f3 = new JTextField(20);

        card.add(new JLabel("Shipment ID:")); card.add(f1);
        card.add(new JLabel("Storage ID:")); card.add(f2);
        card.add(new JLabel("Shelf Number:")); card.add(f3);
        card.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton btn = new JButton("Confirm Storage");
        btn.setBackground(primaryBlue); btn.setForeground(Color.WHITE);
        btn.addActionListener(e -> {
            controller.arrangeStorage(f1.getText(), f2.getText(), f3.getText());
            JOptionPane.showMessageDialog(this, "Location Assigned!");
            f1.setText(""); f2.setText(""); f3.setText("");
        });
        card.add(btn);
        wrapper.add(card);
        return wrapper;
    }

    private JPanel createStatCard(String title, String val, Color accent) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 0, accent));
        JPanel p = new JPanel(new GridLayout(2,1)); p.setBackground(Color.WHITE);
        p.setBorder(new EmptyBorder(15,15,15,15));
        JLabel t = new JLabel(title); t.setFont(new Font("Segoe UI", Font.BOLD, 11));
        JLabel v = new JLabel(val); v.setFont(new Font("Segoe UI", Font.BOLD, 22)); v.setForeground(accent);
        p.add(t); p.add(v); card.add(p); return card;
    }

    private JPanel createTableCard(String title) {
        JPanel card = new JPanel(new BorderLayout()); card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(15,15,15,15)); card.add(new JLabel(title), BorderLayout.NORTH);
        String[][] data = {{"Storage ID", "STR-001"}, {"Shelf", "A1"}, {"Capacity", "50"}};
        JTable table = new JTable(data, new String[]{"Property", "Value"});
        card.add(new JScrollPane(table)); return card;
    }

    private JPanel createActivityCard(String title) {
        JPanel card = new JPanel(new BorderLayout()); card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(15,15,15,15)); card.add(new JLabel(title), BorderLayout.NORTH);
        DefaultListModel<String> model = new DefaultListModel<>();
        for(String s : controller.getRecentActivity()) model.addElement(" ● " + s);
        card.add(new JList<>(model)); return card;
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        new WarehouseDashboard();
    }
}