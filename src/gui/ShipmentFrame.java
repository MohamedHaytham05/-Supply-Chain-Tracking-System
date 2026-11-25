package gui;

import controller.ShipmentController;
import supplychain.Delivery;
import supplychain.Event;
import supplychain.ROI;
import supplychain.Shipment;
import supplychain.ShipmentFacade;
import supplychain.Storage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import supplychain.Delivery;
import supplychain.Event;
import supplychain.ROI;
import supplychain.Shipment;
import supplychain.ShipmentFacade;
import supplychain.Storage;

public class ShipmentFrame extends JFrame {

    private static final Color NAVY = new Color(22, 62, 93);
    private static final Color NAVY_DARK = new Color(18, 55, 84);
    private static final Color NAVY_SELECTED = new Color(34, 75, 108);
    private static final Color BACKGROUND = new Color(239, 244, 248);
    private static final Color CARD = Color.WHITE;
    private static final Color BORDER = new Color(216, 225, 233);
    private static final Color TEXT = new Color(36, 45, 55);
    private static final Color MUTED = new Color(125, 137, 150);
    private static final Color GREEN = new Color(25, 157, 116);
    private static final Color TEAL = new Color(30, 152, 168);
    private static final Color BLUE = new Color(48, 102, 220);
    private static final Color RED = new Color(218, 56, 67);
    private static final Color AMBER = new Color(190, 124, 25);
    private static final Color SOFT_GREEN = new Color(224, 250, 239);
    private static final Color SOFT_BLUE = new Color(228, 237, 255);
    private static final Color SOFT_TEAL = new Color(224, 247, 250);
    private static final Color SOFT_AMBER = new Color(255, 247, 220);
    private static final Color SOFT_RED = new Color(255, 234, 236);

    private static final String DASHBOARD = "Dashboard";
    private static final String TRACK = "Track and Deliver";
    private static final String INSPECTION = "Final Inspection";
    private static final String CONFIRM = "Confirm Delivery";
    private static final String DETAILS = "Delivery Details";
    private static final String EVENTS = "Event Log";
    private static final String ROI_REPORT = "ROI Report";
    private static final String RUN_PROCESS = "Run Full Process";

    private final ShipmentController controller;
    private final List<LogItem> guiEvents;
    private final List<SidebarButton> navButtons;
    private final JPanel contentHost;
    private final JLabel footerLabel;

    private String currentPage = DASHBOARD;

    public ShipmentFrame() {
        super("Supply Chain G17 - Shipment GUI");
        this.controller = new ShipmentController();
        this.guiEvents = new ArrayList<>();
        this.navButtons = new ArrayList<>();
        this.contentHost = new JPanel(new BorderLayout());
        this.footerLabel = smallLabel("", new Color(178, 196, 210));

        seedDemoShipment();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(1180, 720));
        setPreferredSize(new Dimension(1280, 780));
        setLocationByPlatform(true);
        setContentPane(createShell());
        renderCurrentPage();
        pack();
    }

    private void seedDemoShipment() {
        controller.createAndStartShipment(
            "SHP-001",
            "Cairo Factory",
            "STR-001",
            "Cairo Warehouse",
            "Shelf A-01",
            10
        );
        if (controller.getFacade() != null) {
            controller.getFacade().processShipment();
            addGuiEvent("Storage Arranged", "Shipment SHP-001 stored in STR-001.");
        }
    }

    private JPanel createShell() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BACKGROUND);
        root.add(createSidebar(), BorderLayout.WEST);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(BACKGROUND);
        main.add(createTopBar(), BorderLayout.NORTH);
        contentHost.setBackground(BACKGROUND);
        main.add(contentHost, BorderLayout.CENTER);
        root.add(main, BorderLayout.CENTER);
        return root;
    }

    private JPanel createTopBar() {
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(NAVY);
        top.setBorder(new EmptyBorder(14, 20, 14, 22));
        top.setPreferredSize(new Dimension(10, 66));

        JLabel title = new JLabel("Supply Chain G17");
        title.setForeground(Color.WHITE);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 19f));
        top.add(title, BorderLayout.WEST);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.setOpaque(false);
        JLabel name = new JLabel("Laila Farouk");
        name.setForeground(new Color(207, 221, 231));
        right.add(name);
        right.add(pill("Retailer", GREEN, Color.WHITE));
        JButton logout = ghostButton("Logout");
        logout.addActionListener(e -> dispose());
        right.add(logout);
        top.add(right, BorderLayout.EAST);
        return top;
    }

    private JPanel createSidebar() {
        JPanel side = new JPanel(new BorderLayout());
        side.setBackground(NAVY_DARK);
        side.setPreferredSize(new Dimension(230, 10));

        JPanel top = new JPanel(new BorderLayout(10, 0));
        top.setOpaque(false);
        top.setBorder(new EmptyBorder(22, 22, 18, 20));
        JLabel logo = new JLabel("SC", SwingConstants.CENTER);
        logo.setOpaque(true);
        logo.setBackground(new Color(39, 108, 234));
        logo.setForeground(Color.WHITE);
        logo.setFont(logo.getFont().deriveFont(Font.BOLD, 15f));
        logo.setPreferredSize(new Dimension(42, 42));
        top.add(logo, BorderLayout.WEST);

        JLabel brand = new JLabel("Supply Chain G17");
        brand.setForeground(Color.WHITE);
        brand.setFont(brand.getFont().deriveFont(Font.BOLD, 17f));
        top.add(brand, BorderLayout.CENTER);
        side.add(top, BorderLayout.NORTH);

        JPanel menu = new JPanel();
        menu.setOpaque(false);
        menu.setBorder(new EmptyBorder(6, 0, 0, 0));
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.add(sectionLabel("OVERVIEW"));
        menu.add(navButton(DASHBOARD));
        menu.add(Box.createVerticalStrut(14));
        menu.add(sectionLabel("DELIVERY"));
        menu.add(navButton(TRACK));
        menu.add(navButton(INSPECTION));
        menu.add(navButton(CONFIRM));
        menu.add(navButton(DETAILS));
        menu.add(Box.createVerticalStrut(14));
        menu.add(sectionLabel("REPORTS"));
        menu.add(navButton(EVENTS));
        menu.add(navButton(ROI_REPORT));
        menu.add(navButton(RUN_PROCESS));
        side.add(menu, BorderLayout.CENTER);

        JPanel foot = new JPanel(new BorderLayout());
        foot.setOpaque(false);
        foot.setBorder(new EmptyBorder(12, 24, 18, 20));
        foot.add(footerLabel, BorderLayout.CENTER);
        side.add(foot, BorderLayout.SOUTH);
        return side;
    }

    private JLabel sectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(139, 164, 184));
        label.setFont(label.getFont().deriveFont(Font.BOLD, 10f));
        label.setBorder(new EmptyBorder(10, 24, 4, 0));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private SidebarButton navButton(String page) {
        SidebarButton button = new SidebarButton(page);
        button.addActionListener(e -> showPage(page));
        navButtons.add(button);
        return button;
    }

    private void showPage(String page) {
        currentPage = page;
        renderCurrentPage();
    }

    private void renderCurrentPage() {
        updateNavigation();
        contentHost.removeAll();
        contentHost.add(wrapPage(createPage(currentPage)), BorderLayout.CENTER);
        contentHost.revalidate();
        contentHost.repaint();
    }

    private void updateNavigation() {
        for (SidebarButton button : navButtons) {
            button.setSelected(button.getText().equals(currentPage));
        }
        footerLabel.setText("Shipment GUI - " + currentPage);
    }

    private JScrollPane wrapPage(JPanel page) {
        JScrollPane scroll = new JScrollPane(page);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(BACKGROUND);
        scroll.getVerticalScrollBar().setUnitIncrement(18);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return scroll;
    }

    private JPanel createPage(String page) {
        switch (page) {
            case TRACK:
                return trackPage();
            case INSPECTION:
                return inspectionPage();
            case CONFIRM:
                return confirmPage();
            case DETAILS:
                return detailsPage();
            case EVENTS:
                return eventsPage();
            case ROI_REPORT:
                return roiPage();
            case RUN_PROCESS:
                return runProcessPage();
            case DASHBOARD:
            default:
                return dashboardPage();
        }
    }

    private JPanel basePage(String title, String subtitle) {
        JPanel page = new JPanel(new BorderLayout(0, 14));
        page.setBackground(BACKGROUND);
        page.setBorder(new EmptyBorder(18, 18, 14, 18));

        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        JLabel h1 = new JLabel(title);
        h1.setForeground(TEXT);
        h1.setFont(h1.getFont().deriveFont(Font.BOLD, 25f));
        JLabel sub = new JLabel(subtitle);
        sub.setForeground(MUTED);
        sub.setFont(sub.getFont().deriveFont(13f));
        header.add(h1);
        header.add(sub);
        page.add(header, BorderLayout.NORTH);
        return page;
    }

    private JPanel dashboardPage() {
        JPanel page = basePage("Shipment Dashboard", "Manage deliveries, confirm arrivals, and view reports.");
        JPanel body = new JPanel();
        body.setOpaque(false);
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

        JPanel metrics = new JPanel(new GridLayout(1, 4, 14, 0));
        metrics.setOpaque(false);
        Shipment shipment = shipment();
        Delivery delivery = delivery();
        metrics.add(metricCard("DELIVERY ID", value(delivery != null ? delivery.getDeliveryID() : null), TEAL));
        metrics.add(metricCard("DELIVERY STATUS", value(delivery != null ? delivery.getDeliveryStatus() : null), statusColor(delivery != null ? delivery.getDeliveryStatus() : "")));
        metrics.add(metricCard("EST. REVENUE", money(revenue()), GREEN));
        metrics.add(metricCard("ROI", money(roiValue()), roiValue() >= 0 ? BLUE : RED));
        body.add(metrics);
        body.add(Box.createVerticalStrut(16));

        JPanel actions = new JPanel(new GridLayout(1, 4, 14, 0));
        actions.setOpaque(false);
        actions.add(actionCard("D", "Track & Deliver", "Dispatch the shipment to the retailer.", SOFT_TEAL, TEAL, () -> showPage(TRACK)));
        actions.add(actionCard("I", "Final Inspection", "Perform the final quality check.", SOFT_BLUE, BLUE, () -> showPage(INSPECTION)));
        actions.add(actionCard("C", "Confirm Delivery", "Confirm the shipment has been received.", SOFT_GREEN, GREEN, () -> showPage(CONFIRM)));
        actions.add(actionCard("R", "ROI Report", "View financial return on investment.", SOFT_AMBER, AMBER, () -> showPage(ROI_REPORT)));
        body.add(actions);
        body.add(Box.createVerticalStrut(16));

        JPanel lower = new JPanel(new GridLayout(1, 2, 14, 0));
        lower.setOpaque(false);
        lower.add(deliverySummaryCard());
        lower.add(eventTimelineCard("Event Log", 7));
        body.add(lower);

        page.add(body, BorderLayout.CENTER);
        return page;
    }

    private JPanel trackPage() {
        JPanel page = basePage("Track and Deliver", "Dispatch the shipment and update delivery status to In Transit.");
        JPanel body = twoColumnBody();

        RoundedPanel left = cardPanel();
        left.setLayout(new BorderLayout(0, 16));
        left.add(cardTitle("Facade - Track and Deliver"), BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.add(Box.createVerticalStrut(120));
        center.add(label("This runs trackAndDeliver() through the ShipmentFacade.", Font.BOLD, 14f, TEXT));
        center.add(Box.createVerticalStrut(8));
        center.add(wrapText("It advances the shipment to In Transit, updates the delivery, and removes it from storage when the facade has an active shipment."));
        center.add(Box.createVerticalStrut(10));
        center.add(statusLine("Shipment " + shipmentId() + " is ready to dispatch", GREEN));
        center.add(Box.createVerticalStrut(10));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttons.setOpaque(false);
        JButton track = primaryButton("Track and Deliver", TEAL);
        track.addActionListener(e -> runAction(() -> {
            ShipmentFacade facade = facade();
            if (facade == null) {
                throw new IllegalStateException("No active shipment facade.");
            }
            facade.trackAndDeliver();
            addGuiEvent("In Transit", "Dispatched from Storage " + facade.getStorage().getStorageID() + ".");
        }));
        buttons.add(track);
        buttons.add(secondaryButton("Cancel", e -> showPage(DASHBOARD)));
        center.add(buttons);
        left.add(center, BorderLayout.CENTER);

        RoundedPanel happens = cardPanel();
        happens.setLayout(new BorderLayout());
        happens.add(cardTitle("What Happens"), BorderLayout.NORTH);
        JTextArea list = plainText(
            "1. State advances: Storage Arranged -> In Transit\n\n"
                + "2. Delivery status is set to: In Transit\n\n"
                + "3. Shipment is removed from storage\n\n"
                + "4. Action appears in the GUI event log"
        );
        happens.add(list, BorderLayout.CENTER);

        JPanel leftStack = new JPanel();
        leftStack.setOpaque(false);
        leftStack.setLayout(new BoxLayout(leftStack, BoxLayout.Y_AXIS));
        leftStack.add(left);
        leftStack.add(Box.createVerticalStrut(14));
        leftStack.add(happens);

        body.add(leftStack);
        body.add(detailsListCard("Current Shipment", shipmentRows(false)));
        page.add(body, BorderLayout.CENTER);
        return page;
    }

    private JPanel inspectionPage() {
        JPanel page = basePage("Final Inspection", "Perform a final quality check before confirming delivery.");
        JPanel body = twoColumnBody();

        RoundedPanel form = cardPanel();
        form.setLayout(new BorderLayout(0, 14));
        form.add(cardTitle("Final Inspection Form"), BorderLayout.NORTH);

        JPanel inputs = new JPanel(new GridBagLayout());
        inputs.setOpaque(false);
        GridBagConstraints gbc = formGbc();
        inputs.add(fieldBlock("Inspection ID", "EXAM-FINAL-" + shipmentId()), gbc);
        gbc.gridy++;
        inputs.add(fieldBlock("Shipment ID", shipmentId()), gbc);
        gbc.gridy++;
        inputs.add(fieldBlock("Final check", "No damage, all items present"), gbc);
        gbc.gridy++;
        JTextArea notes = new JTextArea("No damage found");
        notes.setRows(4);
        notes.setLineWrap(true);
        notes.setWrapStyleWord(true);
        notes.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER),
            new EmptyBorder(8, 10, 8, 10)
        ));
        inputs.add(labeledComponent("Inspection Notes", notes), gbc);
        form.add(inputs, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttons.setOpaque(false);
        JButton submit = primaryButton("Submit Inspection", GREEN);
        submit.addActionListener(e -> runAction(controller::performInspection));
        buttons.add(submit);
        JButton failed = dangerButton("Mark as Failed");
        failed.addActionListener(e -> noExistingMethod("A failed-inspection workflow was not found in the current controller or facade."));
        buttons.add(failed);
        buttons.add(secondaryButton("Cancel", e -> showPage(DASHBOARD)));
        form.add(buttons, BorderLayout.SOUTH);

        RoundedPanel history = cardPanel();
        history.setLayout(new BorderLayout());
        history.add(cardTitle("Inspection History"), BorderLayout.NORTH);
        history.add(simpleHistoryTable(), BorderLayout.CENTER);

        JPanel leftStack = new JPanel();
        leftStack.setOpaque(false);
        leftStack.setLayout(new BoxLayout(leftStack, BoxLayout.Y_AXIS));
        leftStack.add(form);
        leftStack.add(Box.createVerticalStrut(14));
        leftStack.add(history);

        body.add(leftStack);
        body.add(detailsListCard("Inspection Details", inspectionRows()));
        page.add(body, BorderLayout.CENTER);
        return page;
    }

    private JPanel confirmPage() {
        JPanel page = basePage("Confirm Delivery", "Confirm that the shipment has been received.");
        JPanel body = twoColumnBody();

        RoundedPanel form = cardPanel();
        form.setLayout(new BorderLayout(0, 14));
        form.add(cardTitle("Delivery Confirmation"), BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.add(Box.createVerticalStrut(120));
        center.add(label("Review the delivery details below and confirm receipt.", Font.BOLD, 14f, TEXT));
        center.add(Box.createVerticalStrut(18));
        center.add(detailRows(new String[][] {
            {"Delivery ID", deliveryId()},
            {"Shipment", shipmentId()},
            {"Current Status", shipmentStatus()},
            {"Retailer", "Laila Farouk"},
            {"Package Condition", "Received in acceptable condition"}
        }));
        form.add(center, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttons.setOpaque(false);
        JButton confirm = primaryButton("Confirm Delivery", GREEN);
        confirm.addActionListener(e -> runAction(controller::confirmDelivery));
        buttons.add(confirm);
        JButton reject = dangerButton("Reject Delivery");
        reject.addActionListener(e -> noExistingMethod("A reject-delivery method was not found in the current shipment controller."));
        buttons.add(reject);
        buttons.add(secondaryButton("Cancel", e -> showPage(DASHBOARD)));
        form.add(buttons, BorderLayout.SOUTH);

        RoundedPanel post = cardPanel();
        post.setLayout(new BorderLayout());
        post.add(cardTitle("Post-Confirmation"), BorderLayout.NORTH);
        post.add(plainText(
            "After confirming, the system will:\n\n"
                + "1. Set delivery status to Delivered\n\n"
                + "2. Call delivery.checkStatus()\n\n"
                + "3. Record a Delivery Confirmed event\n\n"
                + "4. Keep the active shipment available for reporting"
        ), BorderLayout.CENTER);

        JPanel leftStack = new JPanel();
        leftStack.setOpaque(false);
        leftStack.setLayout(new BoxLayout(leftStack, BoxLayout.Y_AXIS));
        leftStack.add(form);
        leftStack.add(Box.createVerticalStrut(14));
        leftStack.add(post);

        body.add(leftStack);
        body.add(detailsListCard("Delivery Status", deliveryRows()));
        page.add(body, BorderLayout.CENTER);
        return page;
    }

    private JPanel detailsPage() {
        JPanel page = basePage("Delivery Details", "View the full delivery record and timeline.");
        JPanel body = new JPanel();
        body.setOpaque(false);
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

        JPanel metrics = new JPanel(new GridLayout(1, 4, 14, 0));
        metrics.setOpaque(false);
        metrics.add(metricCard("DELIVERY", deliveryId(), BLUE));
        metrics.add(metricCard("STATUS", deliveryStatus(), statusColor(deliveryStatus())));
        metrics.add(metricCard("SHIPMENT", shipmentId(), TEAL));
        metrics.add(metricCard("DATE", dateLabel(delivery() != null ? delivery().getDeliveryDate() : null), TEXT));
        body.add(metrics);
        body.add(Box.createVerticalStrut(16));

        JPanel lower = new JPanel(new GridLayout(1, 2, 14, 0));
        lower.setOpaque(false);
        lower.add(detailsListCard("Full Delivery Info", fullDeliveryRows()));
        lower.add(eventTimelineCard("Delivery Timeline", 8));
        body.add(lower);
        page.add(body, BorderLayout.CENTER);
        return page;
    }

    private JPanel eventsPage() {
        JPanel page = basePage("Event Log", "Search and review shipment activity.");
        JPanel body = new JPanel();
        body.setOpaque(false);
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

        RoundedPanel filter = cardPanel();
        filter.setLayout(new BorderLayout(12, 0));
        filter.add(cardTitle("Search & Filter"), BorderLayout.NORTH);
        JPanel controls = new JPanel(new BorderLayout(12, 0));
        controls.setOpaque(false);
        JTextField search = new JTextField();
        search.setBorder(fieldBorder("Search events..."));
        controls.add(search, BorderLayout.CENTER);

        JComboBox<String> type = new JComboBox<>();
        type.setModel(new DefaultComboBoxModel<>(eventTypes().toArray(new String[0])));
        type.setPreferredSize(new Dimension(190, 32));
        controls.add(type, BorderLayout.EAST);
        JButton clear = secondaryButton("Clear Filters", e -> {
            search.setText("");
            type.setSelectedIndex(0);
        });
        JPanel clearPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        clearPanel.setOpaque(false);
        clearPanel.add(clear);
        filter.add(controls, BorderLayout.CENTER);
        filter.add(clearPanel, BorderLayout.EAST);
        body.add(filter);
        body.add(Box.createVerticalStrut(14));

        RoundedPanel tableCard = cardPanel();
        tableCard.setLayout(new BorderLayout(0, 10));
        tableCard.add(cardTitle("All Events"), BorderLayout.NORTH);
        DefaultTableModel model = new DefaultTableModel(new String[] {"Event ID", "Type", "Description", "Timestamp"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        styleTable(table);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER));
        tableCard.add(scroll, BorderLayout.CENTER);
        body.add(tableCard);

        Runnable applyFilter = () -> fillEventTable(model, search.getText(), String.valueOf(type.getSelectedItem()));
        search.getDocument().addDocumentListener(new SimpleDocumentListener(applyFilter));
        type.addActionListener(e -> applyFilter.run());
        applyFilter.run();

        page.add(body, BorderLayout.CENTER);
        return page;
    }

    private JPanel roiPage() {
        JPanel page = basePage("ROI Report", "View cost, revenue and return on investment.");
        JPanel body = twoColumnBody();

        JPanel leftStack = new JPanel();
        leftStack.setOpaque(false);
        leftStack.setLayout(new BoxLayout(leftStack, BoxLayout.Y_AXIS));

        JPanel metrics = new JPanel(new GridLayout(1, 2, 14, 0));
        metrics.setOpaque(false);
        metrics.add(metricCard("ESTIMATED COST", money(cost()), RED));
        metrics.add(metricCard("ESTIMATED REVENUE", money(revenue()), GREEN));
        leftStack.add(metrics);
        leftStack.add(Box.createVerticalStrut(16));

        RoundedPanel breakdown = cardPanel();
        breakdown.setLayout(new BorderLayout(0, 14));
        breakdown.add(cardTitle("ROI Breakdown"), BorderLayout.NORTH);
        JTextArea area = plainText(
            "Shipment ID: " + shipmentId() + "\n\n"
                + "Delivery: " + deliveryId() + "\n\n"
                + "Estimated cost: " + money(cost()) + "\n\n"
                + "Estimated revenue: " + money(revenue()) + "\n\n"
                + "ROI: " + money(roiValue())
        );
        breakdown.add(area, BorderLayout.CENTER);
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttons.setOpaque(false);
        buttons.add(primaryButton("Generate Report", BLUE, e -> renderCurrentPage()));
        buttons.add(secondaryButton("Recalculate", e -> renderCurrentPage()));
        breakdown.add(buttons, BorderLayout.SOUTH);
        leftStack.add(breakdown);
        leftStack.add(Box.createVerticalStrut(16));

        RoundedPanel formula = cardPanel();
        formula.setLayout(new BorderLayout());
        formula.add(cardTitle("How ROI is Calculated"), BorderLayout.NORTH);
        formula.add(plainText(
            "estimateCost(Shipment s) returns " + money(cost()) + " base cost\n\n"
                + "estimateRevenue(Delivery d) returns " + money(revenue()) + " for the current delivery status\n\n"
                + "calculateROI(s, d) = estimateRevenue - estimateCost\n\n"
                + "Final ROI = " + money(revenue()) + " - " + money(cost()) + " = " + money(roiValue())
        ), BorderLayout.CENTER);
        leftStack.add(formula);

        RoundedPanel chart = cardPanel();
        chart.setLayout(new BorderLayout());
        chart.add(cardTitle("Financial Bars"), BorderLayout.NORTH);
        JPanel bars = new JPanel(new GridLayout(3, 1, 0, 18));
        bars.setOpaque(false);
        bars.setBorder(new EmptyBorder(70, 18, 70, 18));
        double max = Math.max(Math.max(cost(), revenue()), Math.abs(roiValue()));
        if (max <= 0) {
            max = 1.0;
        }
        bars.add(barRow("Cost", cost(), max, RED));
        bars.add(barRow("Revenue", revenue(), max, GREEN));
        bars.add(barRow("ROI", roiValue(), max, roiValue() >= 0 ? BLUE : RED));
        chart.add(bars, BorderLayout.CENTER);

        body.add(leftStack);
        body.add(chart);
        page.add(body, BorderLayout.CENTER);
        return page;
    }

    private JPanel runProcessPage() {
        JPanel page = basePage("Run Full Process", "Execute the complete supply chain lifecycle in sequence.");
        JPanel body = new JPanel();
        body.setOpaque(false);
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

        RoundedPanel top = cardPanel();
        top.setLayout(new BorderLayout(0, 16));
        top.add(cardTitle("Full Process - 3 Facade Steps + ROI"), BorderLayout.NORTH);
        JPanel steps = new JPanel(new GridLayout(1, 4, 14, 0));
        steps.setOpaque(false);
        steps.add(processStepCard("processShipment()", "Storage arranged", SOFT_BLUE, BLUE));
        steps.add(processStepCard("trackAndDeliver()", "In transit", SOFT_TEAL, TEAL));
        steps.add(processStepCard("inspectAndConfirm()", "Delivered", SOFT_GREEN, GREEN));
        steps.add(processStepCard("ROI Calculated", money(roiValue()), SOFT_AMBER, AMBER));
        top.add(steps, BorderLayout.CENTER);
        body.add(top);
        body.add(Box.createVerticalStrut(16));

        JPanel lower = new JPanel(new GridLayout(1, 2, 14, 0));
        lower.setOpaque(false);

        RoundedPanel process = cardPanel();
        process.setLayout(new BorderLayout(0, 14));
        process.add(cardTitle("Process Steps"), BorderLayout.NORTH);
        process.add(plainText(
            "Step 1 - processShipment()\n"
                + "State: Registered -> In Transit -> Storage Arranged\n\n"
                + "Step 2 - trackAndDeliver()\n"
                + "State: Storage Arranged -> In Transit | Delivery: In Transit\n\n"
                + "Step 3 - inspectAndConfirm()\n"
                + "Final exam created | State: Delivered | Delivery confirmed"
        ), BorderLayout.CENTER);
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttons.setOpaque(false);
        JButton run = primaryButton("Run Full Process", GREEN);
        run.addActionListener(e -> runAction(controller::runFullProcess));
        buttons.add(run);
        buttons.add(secondaryButton("Cancel", e -> showPage(DASHBOARD)));
        process.add(buttons, BorderLayout.SOUTH);

        lower.add(process);
        lower.add(detailsListCard("Process Result", processRows()));
        body.add(lower);
        page.add(body, BorderLayout.CENTER);
        return page;
    }

    private RoundedPanel deliverySummaryCard() {
        RoundedPanel card = cardPanel();
        card.setLayout(new BorderLayout(0, 10));
        card.add(cardTitle("Delivery Details"), BorderLayout.NORTH);
        card.add(detailRows(deliveryRows()), BorderLayout.CENTER);
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttons.setOpaque(false);
        JButton confirm = primaryButton("Confirm Delivery", GREEN);
        confirm.addActionListener(e -> showPage(CONFIRM));
        buttons.add(confirm);
        buttons.add(secondaryButton("Final Inspection", e -> showPage(INSPECTION)));
        card.add(buttons, BorderLayout.SOUTH);
        return card;
    }

    private RoundedPanel eventTimelineCard(String title, int limit) {
        RoundedPanel card = cardPanel();
        card.setLayout(new BorderLayout(0, 10));
        card.add(cardTitle(title), BorderLayout.NORTH);

        JPanel events = new JPanel();
        events.setOpaque(false);
        events.setLayout(new BoxLayout(events, BoxLayout.Y_AXIS));
        List<LogItem> log = logItems();
        int count = 0;
        for (LogItem item : log) {
            if (count++ >= limit) {
                break;
            }
            events.add(timelineRow(item));
        }
        if (log.isEmpty()) {
            events.add(wrapText("No events recorded yet."));
        }
        card.add(events, BorderLayout.CENTER);
        return card;
    }

    private JPanel timelineRow(LogItem item) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setOpaque(false);
        row.setBorder(new EmptyBorder(7, 4, 7, 4));
        JLabel dot = new JLabel(" ");
        dot.setOpaque(true);
        dot.setBackground(statusColor(item.type));
        dot.setPreferredSize(new Dimension(12, 12));
        row.add(dot, BorderLayout.WEST);

        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.add(label(item.type, Font.BOLD, 13f, TEXT));
        text.add(label(item.description + " - " + timeLabel(item.timestamp), Font.PLAIN, 11f, MUTED));
        row.add(text, BorderLayout.CENTER);
        return row;
    }

    private RoundedPanel detailsListCard(String title, String[][] rows) {
        RoundedPanel card = cardPanel();
        card.setLayout(new BorderLayout(0, 10));
        card.add(cardTitle(title), BorderLayout.NORTH);
        card.add(detailRows(rows), BorderLayout.CENTER);
        return card;
    }

    private JPanel detailRows(String[][] rows) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        for (String[] row : rows) {
            panel.add(detailRow(row[0], row[1]));
        }
        return panel;
    }

    private JPanel detailRow(String key, String value) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);
        row.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(232, 238, 243)),
            new EmptyBorder(8, 0, 8, 0)
        ));
        JLabel left = label(key, Font.PLAIN, 13f, MUTED);
        JLabel right = label(value(value), Font.BOLD, 13f, statusColor(value));
        right.setHorizontalAlignment(SwingConstants.RIGHT);
        row.add(left, BorderLayout.WEST);
        row.add(right, BorderLayout.EAST);
        return row;
    }

    private String[][] shipmentRows(boolean compact) {
        Shipment s = shipment();
        Storage storage = storage();
        Delivery d = delivery();
        return new String[][] {
            {"Shipment ID", value(s != null ? s.getShipmentID() : null)},
            {"Current Status", value(s != null ? s.getStatus() : null)},
            {"Location", value(s != null ? s.getCurrentLocation() : null)},
            {"Storage", value(storage != null ? storage.getStorageID() : null)},
            {"Delivery", value(d != null ? d.getDeliveryID() : null)},
            {"Delivery Status", value(d != null ? d.getDeliveryStatus() : null)},
            {"Slots Left", storage != null ? String.valueOf(storage.getAvailableSlots()) : "N/A"},
            {"Events Logged", String.valueOf(logItems().size())}
        };
    }

    private String[][] deliveryRows() {
        Delivery d = delivery();
        return new String[][] {
            {"Delivery ID", value(d != null ? d.getDeliveryID() : null)},
            {"Shipment", shipmentId()},
            {"Status", deliveryStatus()},
            {"Retailer", "Laila Farouk"},
            {"Date", dateLabel(d != null ? d.getDeliveryDate() : null)},
            {"Is Delivered", d != null && d.isDelivered() ? "Yes" : "Pending Confirmation"}
        };
    }

    private String[][] fullDeliveryRows() {
        return new String[][] {
            {"Delivery ID", deliveryId()},
            {"Shipment ID", shipmentId()},
            {"Status", deliveryStatus()},
            {"Is Delivered", delivery() != null && delivery().isDelivered() ? "Yes" : "No"},
            {"Delivery Date", dateLabel(delivery() != null ? delivery().getDeliveryDate() : null)},
            {"Retailer", "Laila Farouk"},
            {"Strategy", "DefaultStatusStrategy"},
            {"Confirmed By", delivery() != null && delivery().isDelivered() ? "Laila Farouk" : "Pending"}
        };
    }

    private String[][] inspectionRows() {
        return new String[][] {
            {"Exam ID", "EXAM-FINAL-" + shipmentId()},
            {"Inspector", "Laila Farouk (Retailer)"},
            {"Result", isDelivered() ? "Passed" : "Pending"},
            {"Shipment", shipmentId()},
            {"Linked To", "Shipment + Storage"},
            {"Date", dateLabel(new Date())},
            {"Notes", isDelivered() ? "No damage found" : "Awaiting inspection"}
        };
    }

    private String[][] processRows() {
        ShipmentFacade facade = facade();
        return new String[][] {
            {"Shipment", shipmentId()},
            {"Final Status", shipmentStatus()},
            {"Delivery", deliveryId()},
            {"Delivery Status", deliveryStatus()},
            {"Completed", facade != null && facade.isProcessCompleted() ? "Yes" : "No"},
            {"Events Logged", String.valueOf(logItems().size())},
            {"Cost", money(cost())},
            {"Revenue", money(revenue())},
            {"ROI", money(roiValue())}
        };
    }

    private Component simpleHistoryTable() {
        DefaultTableModel model = new DefaultTableModel(new String[] {"Exam ID", "Result", "Inspector", "Date"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addRow(new Object[] {
            "EXAM-FINAL-" + shipmentId(),
            isDelivered() ? "Passed" : "Pending",
            "Laila Farouk",
            dateLabel(new Date())
        });
        JTable table = new JTable(model);
        styleTable(table);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER));
        return scroll;
    }

    private JPanel twoColumnBody() {
        JPanel body = new JPanel(new GridLayout(1, 2, 14, 0));
        body.setOpaque(false);
        return body;
    }

    private RoundedPanel metricCard(String label, String value, Color accent) {
        RoundedPanel card = cardPanel();
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(150, 72));
        JLabel l = smallLabel(label, MUTED);
        l.setFont(l.getFont().deriveFont(Font.BOLD, 10f));
        JLabel v = label(value, Font.BOLD, 20f, accent);
        JPanel stack = new JPanel();
        stack.setOpaque(false);
        stack.setLayout(new BoxLayout(stack, BoxLayout.Y_AXIS));
        stack.add(l);
        stack.add(Box.createVerticalStrut(4));
        stack.add(v);
        card.add(stack, BorderLayout.CENTER);
        return card;
    }

    private RoundedPanel actionCard(String initial, String title, String description, Color soft, Color accent, Runnable action) {
        RoundedPanel card = cardPanel();
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.setLayout(new BorderLayout(0, 10));
        JLabel badge = new JLabel(initial, SwingConstants.CENTER);
        badge.setOpaque(true);
        badge.setBackground(soft);
        badge.setForeground(accent);
        badge.setFont(badge.getFont().deriveFont(Font.BOLD, 15f));
        badge.setPreferredSize(new Dimension(38, 34));
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        top.setOpaque(false);
        top.add(badge);
        card.add(top, BorderLayout.NORTH);

        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.add(label(title, Font.BOLD, 13f, TEXT));
        text.add(Box.createVerticalStrut(4));
        text.add(label(description, Font.PLAIN, 11f, MUTED));
        card.add(text, BorderLayout.CENTER);

        MouseAdapter listener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                action.run();
            }
        };
        card.addMouseListener(listener);
        badge.addMouseListener(listener);
        text.addMouseListener(listener);
        return card;
    }

    private RoundedPanel processStepCard(String title, String subtitle, Color soft, Color accent) {
        RoundedPanel card = cardPanel();
        card.setBackground(Color.WHITE);
        card.setLayout(new BorderLayout());
        JLabel titleLabel = label(title, Font.BOLD, 18f, accent);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel sub = label(subtitle, Font.PLAIN, 12f, MUTED);
        sub.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel stack = new JPanel();
        stack.setOpaque(false);
        stack.setLayout(new BoxLayout(stack, BoxLayout.Y_AXIS));
        stack.add(titleLabel);
        stack.add(Box.createVerticalStrut(4));
        stack.add(sub);
        card.add(stack, BorderLayout.CENTER);
        card.setBorder(new EmptyBorder(16, 12, 16, 12));
        card.setBackground(soft);
        return card;
    }

    private JPanel barRow(String label, double value, double max, Color color) {
        JPanel row = new JPanel(new BorderLayout(14, 0));
        row.setOpaque(false);
        JLabel left = label(label, Font.BOLD, 13f, TEXT);
        left.setPreferredSize(new Dimension(90, 24));
        row.add(left, BorderLayout.WEST);
        JPanel bar = new JPanel(new BorderLayout());
        bar.setOpaque(false);
        ProgressBarPanel progress = new ProgressBarPanel(value, max, color);
        bar.add(progress, BorderLayout.CENTER);
        JLabel amount = label(money(value), Font.BOLD, 13f, TEXT);
        amount.setPreferredSize(new Dimension(80, 24));
        bar.add(amount, BorderLayout.EAST);
        row.add(bar, BorderLayout.CENTER);
        return row;
    }

    private RoundedPanel cardPanel() {
        RoundedPanel panel = new RoundedPanel(10, BORDER);
        panel.setBackground(CARD);
        panel.setBorder(new EmptyBorder(14, 16, 14, 16));
        return panel;
    }

    private JLabel cardTitle(String text) {
        JLabel label = label(text, Font.BOLD, 15f, TEXT);
        label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(229, 235, 240)));
        label.setPreferredSize(new Dimension(10, 28));
        return label;
    }

    private JLabel label(String text, int style, float size, Color color) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(label.getFont().deriveFont(style, size));
        return label;
    }

    private JLabel smallLabel(String text, Color color) {
        return label(text, Font.PLAIN, 11f, color);
    }

    private JLabel wrapText(String text) {
        JLabel label = new JLabel("<html><body style='width:420px'>" + escape(text) + "</body></html>");
        label.setForeground(MUTED);
        label.setFont(label.getFont().deriveFont(12f));
        return label;
    }

    private JLabel statusLine(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 12f));
        label.setBorder(new EmptyBorder(0, 0, 0, 0));
        return label;
    }

    private JTextArea plainText(String text) {
        JTextArea area = new JTextArea(text);
        area.setEditable(false);
        area.setOpaque(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setForeground(TEXT);
        area.setFont(area.getFont().deriveFont(13f));
        area.setBorder(new EmptyBorder(12, 4, 4, 4));
        return area;
    }

    private JPanel fieldBlock(String label, String value) {
        JTextField field = new JTextField(value(value));
        field.setEditable(false);
        field.setBorder(fieldBorder(label));
        return labeledComponent(label, field);
    }

    private JPanel labeledComponent(String label, Component component) {
        JPanel panel = new JPanel(new BorderLayout(0, 4));
        panel.setOpaque(false);
        panel.add(label(label, Font.BOLD, 11f, TEXT), BorderLayout.NORTH);
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }

    private GridBagConstraints formGbc() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 0);
        return gbc;
    }

    private javax.swing.border.Border fieldBorder(String placeholder) {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER),
            new EmptyBorder(7, 10, 7, 10)
        );
    }

    private JButton primaryButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setOpaque(true);
        button.setBorder(new EmptyBorder(8, 20, 8, 20));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JButton primaryButton(String text, Color color, java.awt.event.ActionListener listener) {
        JButton button = primaryButton(text, color);
        button.addActionListener(listener);
        return button;
    }

    private JButton secondaryButton(String text, java.awt.event.ActionListener listener) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setForeground(TEXT);
        button.setBackground(Color.WHITE);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER),
            new EmptyBorder(7, 20, 7, 20)
        ));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addActionListener(listener);
        return button;
    }

    private JButton ghostButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setForeground(new Color(222, 234, 242));
        button.setBackground(NAVY);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(54, 91, 120)),
            new EmptyBorder(6, 18, 6, 18)
        ));
        return button;
    }

    private JButton dangerButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setForeground(RED);
        button.setBackground(SOFT_RED);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(238, 152, 160)),
            new EmptyBorder(7, 20, 7, 20)
        ));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JLabel pill(String text, Color bg, Color fg) {
        JLabel pill = new JLabel(text, SwingConstants.CENTER);
        pill.setOpaque(true);
        pill.setBackground(bg);
        pill.setForeground(fg);
        pill.setFont(pill.getFont().deriveFont(Font.BOLD, 11f));
        pill.setBorder(new EmptyBorder(6, 22, 6, 22));
        return pill;
    }

    private void styleTable(JTable table) {
        table.setRowHeight(30);
        table.setFillsViewportHeight(true);
        table.setGridColor(new Color(232, 238, 243));
        table.setShowVerticalLines(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setBackground(Color.WHITE);
        table.getTableHeader().setForeground(TEXT);
        table.getTableHeader().setFont(table.getTableHeader().getFont().deriveFont(Font.BOLD, 12f));
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                component.setForeground(column == 1 ? statusColor(String.valueOf(value)) : TEXT);
                setBorder(new EmptyBorder(0, 8, 0, 8));
                return component;
            }
        });
    }

    private void fillEventTable(DefaultTableModel model, String search, String type) {
        model.setRowCount(0);
        String query = search == null ? "" : search.trim().toLowerCase(Locale.ROOT);
        String selectedType = type == null ? "All Types" : type;
        for (LogItem item : logItems()) {
            boolean matchesText = query.isEmpty()
                || item.id.toLowerCase(Locale.ROOT).contains(query)
                || item.type.toLowerCase(Locale.ROOT).contains(query)
                || item.description.toLowerCase(Locale.ROOT).contains(query);
            boolean matchesType = "All Types".equals(selectedType) || item.type.equals(selectedType);
            if (matchesText && matchesType) {
                model.addRow(new Object[] {
                    item.id,
                    item.type,
                    item.description,
                    item.timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                });
            }
        }
    }

    private List<String> eventTypes() {
        List<String> types = new ArrayList<>();
        types.add("All Types");
        for (LogItem item : logItems()) {
            if (!types.contains(item.type)) {
                types.add(item.type);
            }
        }
        return types;
    }

    private List<LogItem> logItems() {
        List<LogItem> items = new ArrayList<>();
        for (Event event : controller.getEventLog()) {
            items.add(new LogItem(
                event.getEventID(),
                event.getEventType(),
                event.getEventDescription(),
                event.getTimestamp()
            ));
        }
        items.addAll(guiEvents);
        items.sort(Comparator.comparing((LogItem item) -> item.timestamp).reversed());
        return items;
    }

    private void addGuiEvent(String type, String description) {
        guiEvents.add(new LogItem(
            "GUI-EVT-" + (guiEvents.size() + 1),
            type,
            description,
            LocalDateTime.now()
        ));
    }

    private void runAction(Runnable action) {
        try {
            action.run();
            renderCurrentPage();
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Shipment GUI", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void noExistingMethod(String message) {
        JOptionPane.showMessageDialog(this, message, "Shipment GUI", JOptionPane.INFORMATION_MESSAGE);
    }

    private Shipment shipment() {
        return controller.getProcessShipment();
    }

    private ShipmentFacade facade() {
        return controller.getFacade();
    }

    private Delivery delivery() {
        return facade() != null ? facade().getDelivery() : null;
    }

    private Storage storage() {
        return facade() != null ? facade().getStorage() : null;
    }

    private String shipmentId() {
        return value(shipment() != null ? shipment().getShipmentID() : null);
    }

    private String shipmentStatus() {
        return value(shipment() != null ? shipment().getStatus() : null);
    }

    private String deliveryId() {
        return value(delivery() != null ? delivery().getDeliveryID() : null);
    }

    private String deliveryStatus() {
        return value(delivery() != null ? delivery().getDeliveryStatus() : null);
    }

    private boolean isDelivered() {
        return delivery() != null && delivery().isDelivered();
    }

    private double cost() {
        ROI roi = controller.getRoi();
        Shipment s = shipment();
        return s != null ? roi.estimateCost(s) : 0.0;
    }

    private double revenue() {
        ROI roi = controller.getRoi();
        Delivery d = delivery();
        return d != null ? roi.estimateRevenue(d) : 0.0;
    }

    private double roiValue() {
        ROI roi = controller.getRoi();
        Shipment s = shipment();
        Delivery d = delivery();
        return s != null && d != null ? roi.calculateROI(s, d) : 0.0;
    }

    private String money(double value) {
        return String.format(Locale.US, "$%.2f", value);
    }

    private String value(String value) {
        return value == null || value.trim().isEmpty() ? "N/A" : value;
    }

    private String dateLabel(Date date) {
        if (date == null) {
            return "N/A";
        }
        return new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.US).format(date);
    }

    private String timeLabel(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    private Color statusColor(String status) {
        String s = status == null ? "" : status.toLowerCase(Locale.ROOT);
        if (s.contains("delivered") || s.contains("passed") || s.contains("complete")) {
            return GREEN;
        }
        if (s.contains("transit")) {
            return TEAL;
        }
        if (s.contains("registered") || s.contains("storage")) {
            return BLUE;
        }
        if (s.contains("failed") || s.contains("reject") || s.contains("hold")) {
            return RED;
        }
        if (s.contains("pending") || s.contains("created")) {
            return AMBER;
        }
        return TEXT;
    }

    private String escape(String text) {
        return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // Use the default Swing look and feel if the system one is unavailable.
            }
            new ShipmentFrame().setVisible(true);
        });
    }

    private static final class LogItem {
        private final String id;
        private final String type;
        private final String description;
        private final LocalDateTime timestamp;

        private LogItem(String id, String type, String description, LocalDateTime timestamp) {
            this.id = id;
            this.type = type;
            this.description = description;
            this.timestamp = timestamp;
        }
    }

    private static final class RoundedPanel extends JPanel {
        private final int arc;
        private final Color borderColor;

        private RoundedPanel(int arc, Color borderColor) {
            this.arc = arc;
            this.borderColor = borderColor;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);
            g2.setColor(borderColor);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    private static final class SidebarButton extends JButton {
        private boolean selected;

        private SidebarButton(String text) {
            super(text);
            setHorizontalAlignment(SwingConstants.LEFT);
            setForeground(new Color(202, 219, 232));
            setFont(getFont().deriveFont(Font.BOLD, 13f));
            setFocusPainted(false);
            setBorder(new EmptyBorder(11, 24, 11, 12));
            setContentAreaFilled(false);
            setOpaque(false);
            setAlignmentX(Component.LEFT_ALIGNMENT);
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void setSelected(boolean selected) {
            this.selected = selected;
            setForeground(selected ? Color.WHITE : new Color(202, 219, 232));
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            if (selected) {
                g2.setColor(NAVY_SELECTED);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(GREEN);
                g2.fillRect(0, 0, 5, getHeight());
            }
            g2.dispose();
            super.paintComponent(g);
        }
    }

    private static final class ProgressBarPanel extends JPanel {
        private final double value;
        private final double max;
        private final Color color;

        private ProgressBarPanel(double value, double max, Color color) {
            this.value = Math.abs(value);
            this.max = max;
            this.color = color;
            setOpaque(false);
            setPreferredSize(new Dimension(200, 26));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int width = getWidth();
            int height = 18;
            int y = (getHeight() - height) / 2;
            g2.setColor(new Color(224, 231, 238));
            g2.fillRoundRect(0, y, width, height, 8, 8);
            int fill = (int) Math.max(8, Math.round(width * Math.min(1.0, value / max)));
            g2.setColor(color);
            g2.fillRoundRect(0, y, fill, height, 8, 8);
            g2.dispose();
        }
    }

    private static final class SimpleDocumentListener implements DocumentListener {
        private final Runnable action;

        private SimpleDocumentListener(Runnable action) {
            this.action = action;
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            action.run();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            action.run();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            action.run();
        }
    }
}
