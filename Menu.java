import java.util.HashMap;
import java.util.Scanner;
import javax.swing.SwingWorker;

public class Menu {
    private static HashMap<String, User> users = new HashMap<>();
    private static User loggedInUser = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        while (choice != 6) {
            System.out.println("Select an option: ");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Select City");
            System.out.println("4. Scan QR Code");
            System.out.println("5. Make Reservation");
            System.out.println("6. Quit");
            choice = scanner.nextInt();
            scanner.nextLine(); 
            switch (choice) {
                case 1:
                    registerUser(scanner);
                    break;
                case 2:
                    loginUser(scanner);
                    break;
                case 3:
                    if (loggedInUser != null) {
                        selectCity(scanner);
                    } else {
                        System.out.println("Please login first.");
                    }
                    break;
                case 4:
                    if (loggedInUser != null) {
                        scanQRCode(scanner);
                    } else {
                        System.out.println("Please login first.");
                    }
                    break;
                case 5:
                    if (loggedInUser != null) {
                        makeReservation(scanner);
                    } else {
                        System.out.println("Please login first.");
                    }
                    break;
                case 6:
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

    static void selectCity(Scanner scanner) {
        System.out.println("Enter your city:");
        String city = scanner.nextLine();
        loggedInUser.setCity(city);
        System.out.println("City selected: " + city);
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

    static Exhibit getExhibitByNumber(int exhibitNumber) {
        Museum museum = new Museum("Sample Museum");
        return new Exhibit(exhibitNumber, "Sample Exhibit Description", museum);
    }

    static Museum getMuseumByName(String museumName) {
        return new Museum(museumName);
    }
}

class User {
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private String password;
    private String paymentDetails;
    private String city;

    public User(String name, String surname, String phoneNumber, String email, String password, String paymentDetails) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.paymentDetails = paymentDetails;
        this.city = "";
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPaymentDetails() {
        return paymentDetails;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}

class ScanQR {
    private User user;

    public ScanQR(User user) {
        this.user = user;
    }

    public void scanCode(String codeData, Exhibit exhibit) {
        // Simulate scanning QR code
        System.out.println("User " + user.getName() + " scanned QR code for exhibit " + exhibit.getDescription());
    }
}

class Exhibit {
    private int number;
    private String description;
    private Museum museum;

    public Exhibit(int number, String description, Museum museum) {
        this.number = number;
        this.description = description;
        this.museum = museum;
    }

    public int getNumber() {
        return number;
    }

    public String getDescription() {
        return description;
    }

    public Museum getMuseum() {
        return museum;
    }
}

class Museum {
    private String name;

    public Museum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Reservation {
    private String dateTime;
    private double price;
    private Museum museum;
    private User user;

    public Reservation(String dateTime, double price, Museum museum, User user) {
        this.dateTime = dateTime;
        this.price = price;
        this.museum = museum;
        this.user = user;
    }

    public void makeReservation() {
        // Simulate making a reservation
        System.out.println("Reservation made for " + user.getName() + " at " + museum.getName() + " on " + dateTime);
    }
}
