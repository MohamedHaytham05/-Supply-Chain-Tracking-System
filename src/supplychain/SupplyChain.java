package supplychain;

import controller.ShipmentController;
import java.util.Scanner;

public class SupplyChain {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ShipmentController controller = new ShipmentController();

    private static Manufacturer  manufacturer;
    private static Shipper       shipper;
    private static WarehouseStaff warehouseStaff;
    private static Retailer      retailer;

    public static void main(String[] args) {
        printHeader("SUPPLY CHAIN TRACKING SYSTEM — G17");
        mainMenu();
        scanner.close();
    }

    private static void mainMenu() {
        while (true) {
            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║         MAIN MENU            ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║  1. Register Users           ║");
            System.out.println("║  2. Login                    ║");
            System.out.println("║  3. Manufacturer Menu        ║");
            System.out.println("║  4. Shipper Menu             ║");
            System.out.println("║  5. Warehouse Staff Menu     ║");
            System.out.println("║  6. Retailer Menu            ║");
            System.out.println("║  7. Controller Menu          ║");
            System.out.println("║  8. View Event Log           ║");
            System.out.println("║  9. State Pattern Demo       ║");
            System.out.println("║  0. Exit                     ║");
            System.out.println("╚══════════════════════════════╝");
            System.out.print("Choose: ");

            switch (readLine()) {
                case "1": registerMenu();     break;
                case "2": loginMenu();        break;
                case "3": manufacturerMenu(); break;
                case "4": shipperMenu();      break;
                case "5": warehouseMenu();    break;
                case "6": retailerMenu();     break;
                case "7": controllerMenu();   break;
                case "8": viewEventLog();     break;
                case "9": statePatternDemo(); break;
                case "0":
                    printHeader("SYSTEM EXIT — GOODBYE");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void registerMenu() {
        printSection("USER REGISTRATION");
        System.out.println("1. Register Manufacturer");
        System.out.println("2. Register Shipper");
        System.out.println("3. Register Warehouse Staff");
        System.out.println("4. Register Retailer");
        System.out.println("0. Back");
        System.out.print("Choose: ");
        String choice = readLine();

        if (choice.equals("0")) return;

        System.out.print("Username : "); String u = readLine();
        System.out.print("Password : "); String p = readLine();
        System.out.print("Email    : "); String e = readLine();

        switch (choice) {
            case "1":
                manufacturer = new Manufacturer(u, p, e);
                manufacturer.signup();
                break;
            case "2":
                shipper = new Shipper(u, p, e);
                shipper.signup();
                break;
            case "3":
                warehouseStaff = new WarehouseStaff(u, p, e);
                warehouseStaff.signup();
                break;
            case "4":
                retailer = new Retailer(u, p, e);
                retailer.signup();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void loginMenu() {
        printSection("LOGIN");
        System.out.println("1. Login as Manufacturer");
        System.out.println("2. Login as Shipper");
        System.out.println("3. Login as Warehouse Staff");
        System.out.println("4. Login as Retailer");
        System.out.println("0. Back");
        System.out.print("Choose: ");
        String choice = readLine();

        if (choice.equals("0")) return;

        System.out.print("Username : "); String u = readLine();
        System.out.print("Password : "); String p = readLine();

        switch (choice) {
            case "1":
                if (manufacturer == null) { System.out.println("Manufacturer not registered yet."); return; }
                manufacturer.login(u, p);
                break;
            case "2":
                if (shipper == null) { System.out.println("Shipper not registered yet."); return; }
                shipper.login(u, p);
                break;
            case "3":
                if (warehouseStaff == null) { System.out.println("Warehouse Staff not registered yet."); return; }
                warehouseStaff.login(u, p);
                break;
            case "4":
                if (retailer == null) { System.out.println("Retailer not registered yet."); return; }
                retailer.login(u, p);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void manufacturerMenu() {
        if (!checkUser(manufacturer, "Manufacturer")) return;
        while (true) {
            printSection("MANUFACTURER MENU — " + manufacturer.getUsername());
            System.out.println("1. Register Product");
            System.out.println("2. Export Product");
            System.out.println("3. Logout");
            System.out.println("0. Back");
            System.out.print("Choose: ");
            String choice = readLine();
            switch (choice) {
                case "1":
                    System.out.print("Product ID: ");
                    manufacturer.regProduct(readLine());
                    break;
                case "2":
                    System.out.print("Product ID: ");
                    manufacturer.exaProduct(readLine());
                    break;
                case "3":
                    manufacturer.logout();
                    return;
                case "0": return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void shipperMenu() {
        if (!checkUser(shipper, "Shipper")) return;
        if (!checkShipmentExists()) return;
        Shipment shipment = controller.getProcessShipment();
        while (true) {
            printSection("SHIPPER MENU — " + shipper.getUsername());
            System.out.println("1. Update Shipment Status");
            System.out.println("2. Track Pickup");
            System.out.println("3. Logout");
            System.out.println("0. Back");
            System.out.print("Choose: ");
            String choice = readLine();
            switch (choice) {
                case "1":
                    System.out.print("New Status: ");
                    shipper.updateShipmentStatus(shipment, readLine());
                    break;
                case "2":
                    shipper.trackPickup(shipment);
                    break;
                case "3":
                    shipper.logout();
                    return;
                case "0": return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void warehouseMenu() {
        if (!checkUser(warehouseStaff, "Warehouse Staff")) return;
        if (!checkShipmentExists()) return;
        Shipment shipment = controller.getProcessShipment();
        Storage  storage  = controller.getFacade().getStorage();
        while (true) {
            printSection("WAREHOUSE STAFF MENU — " + warehouseStaff.getUsername());
            System.out.println("1. Record Arrival");
            System.out.println("2. Arrange Storage");
            System.out.println("3. Perform Inspection");
            System.out.println("4. Show Storage Details");
            System.out.println("5. Show Shipment Details");
            System.out.println("6. Logout");
            System.out.println("0. Back");
            System.out.print("Choose: ");
            String choice = readLine();
            switch (choice) {
                case "1": warehouseStaff.recordArrival(shipment);           break;
                case "2": warehouseStaff.arrangeStorage(storage, shipment); break;
                case "3": warehouseStaff.performInspection(shipment);       break;
                case "4": storage.showStorageDetails();                      break;
                case "5": shipment.showShipmentDetails();                   break;
                case "6": warehouseStaff.logout(); return;
                case "0": return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void retailerMenu() {
        if (!checkUser(retailer, "Retailer")) return;
        if (!checkShipmentExists()) return;
        Shipment shipment = controller.getProcessShipment();
        Delivery delivery = controller.getFacade().getDelivery();
        while (true) {
            printSection("RETAILER MENU — " + retailer.getUsername());
            System.out.println("1. Confirm Delivery");
            System.out.println("2. Final Inspection");
            System.out.println("3. Show Delivery Details");
            System.out.println("4. Logout");
            System.out.println("0. Back");
            System.out.print("Choose: ");
            String choice = readLine();
            switch (choice) {
                case "1": retailer.confirmDelivery(delivery);    break;
                case "2": retailer.finalInspection(shipment);    break;
                case "3": delivery.showDeliveryDetails();        break;
                case "4": retailer.logout(); return;
                case "0": return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void controllerMenu() {
        while (true) {
            printSection("CONTROLLER MENU");
            System.out.println("1.  Create New Shipment");
            System.out.println("2.  Track Shipment");
            System.out.println("3.  Update Location");
            System.out.println("4.  Get Shipment Status");
            System.out.println("5.  Perform Inspection");
            System.out.println("6.  Confirm Delivery");
            System.out.println("7.  Facade — Process Shipment (Step 1)");
            System.out.println("8.  Facade — Track and Deliver (Step 2)");
            System.out.println("9.  Facade — Inspect and Confirm (Step 3)");
            System.out.println("10. Run Full Process (Steps 1-2-3 + ROI)");
            System.out.println("11. ROI Report");
            System.out.println("0.  Back");
            System.out.print("Choose: ");
            String choice = readLine();
            switch (choice) {
                case "1":  createShipmentInput();                                    break;
                case "2":  controller.trackShipment();                               break;
                case "3":
                    System.out.print("New Location: ");
                    controller.updateShipmentLocation(readLine());
                    break;
                case "4":  controller.getShipmentStatus();                           break;
                case "5":  controller.performInspection();                           break;
                case "6":  controller.confirmDelivery();                             break;
                case "7":  if (checkFacadeExists()) controller.getFacade().processShipment();   break;
                case "8":  if (checkFacadeExists()) controller.getFacade().trackAndDeliver();   break;
                case "9":  if (checkFacadeExists()) controller.getFacade().inspectAndConfirm(); break;
                case "10": controller.runFullProcess();                              break;
                case "11": roiReport();                                              break;
                case "0":  return;
                default:   System.out.println("Invalid choice.");
            }
        }
    }

    private static void createShipmentInput() {
        printSection("CREATE NEW SHIPMENT");
        System.out.print("Shipment ID      : "); String sid   = readLine();
        System.out.print("Origin Location  : "); String orig  = readLine();
        System.out.print("Storage ID       : "); String stid  = readLine();
        System.out.print("Storage Location : "); String stloc = readLine();
        System.out.print("Shelf Number     : "); String shelf = readLine();
        System.out.print("Capacity         : ");
        int cap = 10;
        try { cap = Integer.parseInt(readLine()); }
        catch (NumberFormatException e) { System.out.println("Invalid capacity. Defaulting to 10."); }
        controller.createAndStartShipment(sid, orig, stid, stloc, shelf, cap);
    }

    private static void roiReport() {
        if (!checkShipmentExists()) return;
        printSection("ROI REPORT");
        ROI      roi      = controller.getRoi();
        Shipment shipment = controller.getProcessShipment();
        Delivery delivery = controller.getFacade().getDelivery();
        double cost    = roi.estimateCost(shipment);
        double revenue = roi.estimateRevenue(delivery);
        double roiVal  = roi.calculateROI(shipment, delivery);
        System.out.println("Shipment ID : " + shipment.getShipmentID());
        System.out.println("Status      : " + shipment.getStatus());
        System.out.println("Delivery    : " + delivery.getDeliveryStatus());
        System.out.printf("Cost        : $%.2f%n", cost);
        System.out.printf("Revenue     : $%.2f%n", revenue);
        System.out.printf("ROI         : $%.2f%n", roiVal);
    }

    private static void viewEventLog() {
        printSection("EVENT LOG");
        if (controller.getEventLog().isEmpty()) {
            System.out.println("No events recorded yet.");
            return;
        }
        System.out.println("Total events: " + controller.getEventLog().size());
        for (Event e : controller.getEventLog()) {
            System.out.println("  " + e.getSummary());
        }
    }

    private static void statePatternDemo() {
        printSection("STATE PATTERN DEMO");
        System.out.print("Enter Demo Shipment ID (or press Enter for SHP-DEMO): ");
        String id = readLine();
        if (id.isEmpty()) id = "SHP-DEMO";

        Shipment demo = new Shipment(id, "Registered", "Factory");
        System.out.println("\nCreated: " + demo.getShipmentID() + " | Status: " + demo.getStatus());

        ShipmentState s1 = new RegisteredState();
        s1.updateStatus(demo);
        System.out.println("updateStatus()  → " + demo.getStatus());
        s1.nextState(demo);
        System.out.println("nextState()     → " + demo.getStatus());

        ShipmentState s2 = new ArrivedState();
        s2.nextState(demo);
        System.out.println("nextState()     → " + demo.getStatus());

        ShipmentState s3 = new StorageArrangedState();
        s3.nextState(demo);
        System.out.println("nextState()     → " + demo.getStatus());

        ShipmentState s4 = new InTransitState();
        s4.nextState(demo);
        System.out.println("nextState()     → " + demo.getStatus());

        ShipmentState s5 = new DeliveredState();
        s5.nextState(demo);
        System.out.println("Final Status    → " + demo.getStatus());
    }

    private static boolean checkUser(User user, String role) {
        if (user == null) {
            System.out.println(role + " not registered. Go to Register Users first.");
            return false;
        }
        if (!user.isAuthenticated()) {
            System.out.println(role + " is not logged in. Go to Login first.");
            return false;
        }
        return true;
    }

    private static boolean checkShipmentExists() {
        if (controller.getProcessShipment() == null) {
            System.out.println("No active shipment. Create one from the Controller Menu first.");
            return false;
        }
        return true;
    }

    private static boolean checkFacadeExists() {
        if (controller.getFacade() == null) {
            System.out.println("No active shipment. Create one from the Controller Menu first.");
            return false;
        }
        return true;
    }

    private static String readLine() {
        return scanner.nextLine().trim();
    }

    private static void printHeader(String title) {
        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  " + title);
        System.out.println("╚══════════════════════════════════════════════════╝");
    }

    private static void printSection(String title) {
        System.out.println("\n──────────────────────────────────────────────────");
        System.out.println("  " + title);
        System.out.println("──────────────────────────────────────────────────");
    }
}