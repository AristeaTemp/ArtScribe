import java.util.HashMap;
import java.util.Scanner;
import javax.swing.SwingWorker;

public class Menu {
    private static HashMap<String, User> users = new HashMap<>();
    private static User loggedInUser = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        while (choice != 5) {
            System.out.println("Select an option: ");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Scan QR Code");
            System.out.println("4. Make Reservation");
            System.out.println("5. Quit");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    registerUser(scanner);
                    break;
                case 2:
                    loginUser(scanner);
                    break;
                case 3:
                    if (loggedInUser != null) {
                        scanQRCode(scanner);
                    } else {
                        System.out.println("Please login first.");
                    }
                    break;
                case 4:
                    if (loggedInUser != null) {
                        makeReservation(scanner);
                    } else {
                        System.out.println("Please login first.");
                    }
                    break;
                case 5:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
        scanner.close();
    }

    static void registerUser(Scanner scanner) {
        System.out.println("Enter your name:");
        String name = scanner.nextLine();
        System.out.println("Enter your surname:");
        String surname = scanner.nextLine();
        System.out.println("Enter your phone number:");
        String phoneNumber = scanner.nextLine();
        System.out.println("Enter your email:");
        String email = scanner.nextLine();
        System.out.println("Enter your password:");
        String password = scanner.nextLine();
        System.out.println("Enter your payment details:");
        String paymentDetails = scanner.nextLine();

        if (users.containsKey(email)) {
            System.out.println("User already exists.");
        } else {
            User user = new User(name, surname, phoneNumber, email, password, paymentDetails);
            users.put(email, user);
            System.out.println("Registration successful.");
        }
    }

    static void loginUser(Scanner scanner) {
        System.out.println("Enter your email:");
        String email = scanner.nextLine();
        System.out.println("Enter your password:");
        String password = scanner.nextLine();

        User user = users.get(email);
        if (user != null && user.getPassword().equals(password)) {
            loggedInUser = user;
            System.out.println("Login successful.");
            System.out.println("Welcome, " + user.getName() + " " + user.getSurname());
        } else {
            System.out.println("Invalid email or password.");
        }
    }

    static void scanQRCode(Scanner scanner) {
        System.out.println("Enter the QR code data:");
        String codeData = scanner.nextLine();
        System.out.println("Enter the exhibit number:");
        int exhibitNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Exhibit exhibit = getExhibitByNumber(exhibitNumber);
        if (exhibit != null) {
            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    // Simulate scanning QR code
                    ScanQR scanQR = new ScanQR(loggedInUser);
                    scanQR.scanCode(codeData, exhibit);
                    return null;
                }

                @Override
                protected void done() {
                    System.out.println("QR code scanned successfully.");
                }
            };
            worker.execute();
        } else {
            System.out.println("Exhibit not found.");
        }
    }

    static void makeReservation(Scanner scanner) {
        System.out.println("Enter the museum name:");
        String museumName = scanner.nextLine();
        Museum museum = getMuseumByName(museumName);
        if (museum != null) {
            System.out.println("Enter the reservation date and time (YYYY-MM-DD HH:MM):");
            String dateTime = scanner.nextLine();
            System.out.println("Enter the price:");
            double price = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            Reservation reservation = new Reservation(dateTime, price, museum, loggedInUser);
            reservation.makeReservation();
            System.out.println("Reservation successful.");
        } else {
            System.out.println("Museum not found.");
        }
    }

    // This is a stub. In a real application, you would implement this method to retrieve the exhibit from a database.
    static Exhibit getExhibitByNumber(int exhibitNumber) {
        // Stub: return a dummy exhibit for demonstration purposes
        Museum museum = new Museum("Sample Museum");
        return new Exhibit(exhibitNumber, "Sample Exhibit Description", museum);
    }

    // This is a stub. In a real application, you would implement this method to retrieve the museum from a database.
    static Museum getMuseumByName(String museumName) {
        // Stub: return a dummy museum for demonstration purposes
        return new Museum(museumName);
    }
}
