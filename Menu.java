import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class Menu extends JFrame {
    private static HashMap<String, User> users = new HashMap<>();
    private static User loggedInUser = null;

    private JTextField nameField;
    private JTextField surnameField;
    private JTextField phoneField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField cityField;
    private JTextField qrCodeField;
    private JTextField exhibitNumberField;
    private JTextField museumNameField;
    private JTextField dateTimeField;
    private JTextField priceField;

    public Menu() {
        setTitle("Menu");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));

        JButton registerButton = new JButton("Register");
        JButton loginButton = new JButton("Login");
        JButton selectCityButton = new JButton("Select City");
        JButton scanQRButton = new JButton("Scan QR Code");
        JButton makeReservationButton = new JButton("Make Reservation");
        JButton quitButton = new JButton("Quit");

        panel.add(registerButton);
        panel.add(loginButton);
        panel.add(selectCityButton);
        panel.add(scanQRButton);
        panel.add(makeReservationButton);
        panel.add(quitButton);

        add(panel);

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginUser();
            }
        });

        selectCityButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (loggedInUser != null) {
                    selectCity();
                } else {
                    JOptionPane.showMessageDialog(null, "Please login first.");
                }
            }
        });

        scanQRButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (loggedInUser != null) {
                    scanQRCode();
                } else {
                    JOptionPane.showMessageDialog(null, "Please login first.");
                }
            }
        });

        makeReservationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (loggedInUser != null) {
                    makeReservation();
                } else {
                    JOptionPane.showMessageDialog(null, "Please login first.");
                }
            }
        });

        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void registerUser() {
        JPanel panel = new JPanel(new GridLayout(5, 2));
        nameField = new JTextField();
        surnameField = new JTextField();
        phoneField = new JTextField();
        emailField = new JTextField();
        passwordField = new JPasswordField();

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Surname:"));
        panel.add(surnameField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Register", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String surname = surnameField.getText();
            String phoneNumber = phoneField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (users.containsKey(email)) {
                JOptionPane.showMessageDialog(null, "User already exists.");
            } else {
                User user = new User(name, surname, phoneNumber, email, password);
                users.put(email, user);
                JOptionPane.showMessageDialog(null, "Registration successful.");
            }
        }
    }

    private void loginUser() {
        JPanel panel = new JPanel(new GridLayout(2, 2));
        emailField = new JTextField();
        passwordField = new JPasswordField();

        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            User user = users.get(email);
            if (user != null && user.getPassword().equals(password)) {
                loggedInUser = user;
                JOptionPane.showMessageDialog(null, "Login successful. Welcome, " + user.getName() + " " + user.getSurname());
            } else {
                JOptionPane.showMessageDialog(null, "Invalid email or password.");
            }
        }
    }

    private void selectCity() {
        String city = JOptionPane.showInputDialog("Enter your city:");
        if (city != null && !city.trim().isEmpty()) {
            loggedInUser.setCity(city);
            JOptionPane.showMessageDialog(null, "City selected: " + city);
        }
    }

    private void scanQRCode() {
        JPanel panel = new JPanel(new GridLayout(2, 2));
        qrCodeField = new JTextField();
        exhibitNumberField = new JTextField();

        panel.add(new JLabel("QR Code Data:"));
        panel.add(qrCodeField);
        panel.add(new JLabel("Exhibit Number:"));
        panel.add(exhibitNumberField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Scan QR Code", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String codeData = qrCodeField.getText();
            int exhibitNumber = Integer.parseInt(exhibitNumberField.getText());

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
                        JOptionPane.showMessageDialog(null, "QR code scanned successfully.");
                    }
                };
                worker.execute();
            } else {
                JOptionPane.showMessageDialog(null, "Exhibit not found.");
            }
        }
    }

    private void makeReservation() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        museumNameField = new JTextField();
        dateTimeField = new JTextField();
        priceField = new JTextField();

        panel.add(new JLabel("Museum Name:"));
        panel.add(museumNameField);
        panel.add(new JLabel("Reservation Date and Time (YYYY-MM-DD HH:MM):"));
        panel.add(dateTimeField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Make Reservation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String museumName = museumNameField.getText();
            String dateTime = dateTimeField.getText();
            double price = Double.parseDouble(priceField.getText());

            Museum museum = getMuseumByName(museumName);
            if (museum != null) {
                Reservation reservation = new Reservation(dateTime, price, museum, loggedInUser);
                reservation.makeReservation();
                JOptionPane.showMessageDialog(null, "Reservation successful.");
            } else {
                JOptionPane.showMessageDialog(null, "Museum not found.");
            }
        }
    }

    static Exhibit getExhibitByNumber(int exhibitNumber) {
        Museum museum = new Museum("Sample Museum");
        return new Exhibit(exhibitNumber, "Sample Exhibit Description", museum);
    }

    static Museum getMuseumByName(String museumName) {
        return new Museum(museumName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }
}

class User {
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private String password;
    private String city;

    public User(String name, String surname, String phoneNumber, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
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
