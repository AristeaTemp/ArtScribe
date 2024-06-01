package net.codejava;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Menu extends JFrame {
    private JTextField nameField;
    private JTextField surnameField;
    private JTextField phoneField;
    private JTextField emailField;
    private JPasswordField passwordField;

    private static Connection dbConnection = null;
    private User loggedInUser = null;

    public Menu() {
        setTitle("ArtScribe - Welcome");
        setSize(350, 600); // Adjust the size as per the content
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen
        setResizable(false);
    }

    private void displayInitialMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        panel.setBackground(new Color(135, 206, 235)); // Sky blue color

        JLabel titleLabel = new JLabel("Welcome to ArtScribe", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Choose an option below to continue", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton registerButton = new JButton("Register");
        JButton loginButton = new JButton("Login");

        styleButton(registerButton);
        styleButton(loginButton);

        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        registerButton.addActionListener(e -> displayRegistrationForm());
        loginButton.addActionListener(e -> loginUser());

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(subtitleLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(registerButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(loginButton);

        setContentPane(panel);
        setVisible(true);
    }

    private void displayRegistrationForm() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        Color skyBlue = new Color(135, 206, 235); // RGB values for sky blue color
        panel.setBackground(skyBlue);

        JLabel titleLabel = new JLabel("ArtScribe", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Εγγραφή", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        emailField = new PlaceholderTextField("Email");
        nameField = new PlaceholderTextField("First Name");
        surnameField = new PlaceholderTextField("Last Name");
        phoneField = new PlaceholderTextField("Phone Number");
        passwordField = new PlaceholderPasswordField("Password");

        // Styling and adding fields
        setupTextField(emailField);
        setupTextField(nameField);
        setupTextField(surnameField);
        setupTextField(phoneField);
        setupPasswordField(passwordField);

        JButton registerButton = new JButton("Submit");
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.addActionListener(e -> registerUser());

        JButton googleButton = new JButton("Continue with Google");
        googleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        styleButton(googleButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelButton.addActionListener(e -> displayInitialMenu());
        styleButton(cancelButton);

        // Adding components to the panel
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(subtitleLabel);
        panel.add(Box.createVerticalStrut(20));
        addFormField(panel, emailField);
        addFormField(panel, nameField);
        addFormField(panel, surnameField);
        addFormField(panel, phoneField);
        addFormField(panel, passwordField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(registerButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(googleButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(cancelButton);

        setContentPane(panel);
        revalidate();
        repaint();
    }

    private void loginUser() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        Color lightBlue = new Color(135, 206, 235); // Sky blue color
        panel.setBackground(lightBlue);

        JLabel titleLabel = new JLabel("ArtScribe", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Σύνδεση", SwingConstants.CENTER); // "Login" in Greek
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField loginEmailField = new PlaceholderTextField("Email");
        JPasswordField loginPasswordField = new PlaceholderPasswordField("Κωδικός Πρόσβασης"); // "Password" in Greek

        setupTextField(loginEmailField);
        setupPasswordField(loginPasswordField);

        JButton loginButton = new JButton("Σύνδεση"); // "Login" in Greek
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        styleButton(loginButton);

        loginButton.addActionListener(e -> {
            if (validateLogin(loginEmailField.getText(), new String(loginPasswordField.getPassword()))) {
                JOptionPane.showMessageDialog(null, "Login successful.");
                displayMainMenu();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid email or password.");
            }
        });

        JButton googleButton = new JButton("Continue with Google");
        googleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        styleButton(googleButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelButton.addActionListener(e -> displayInitialMenu());
        styleButton(cancelButton);

        // Styling and layout adjustments
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(subtitleLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(loginEmailField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(loginPasswordField);
        panel.add(Box.createVerticalStrut(20));
        panel.add(loginButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(googleButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(cancelButton);

        setContentPane(panel);
        revalidate();
        repaint();
    }
    private void logoutUser() {
        loggedInUser = null;
        JOptionPane.showMessageDialog(null, "You have been logged out.");
        displayInitialMenu();
    }
    private void registerUser() {
        String email = emailField.getText();
        String name = nameField.getText();
        String surname = surnameField.getText();
        String phone = phoneField.getText();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || name.isEmpty() || surname.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields.");
            return;
        }

        try (Connection con = DriverManager.getConnection("jdbc:sqlite:/C://Users//Αριστέα//Downloads//artscribe (1).db")) {
            String sql = "INSERT INTO User (Name, Surname, Email, Phone, Password) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, surname);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.setString(5, password);
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "Registration successful.");
            } else {
                JOptionPane.showMessageDialog(null, "Registration failed.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
            ex.printStackTrace();
        }
        displayInitialMenu(); // Redirect back to the initial menu after registration
    }

    private boolean validateLogin(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields.");
            return false;
        }

        try (Connection con = DriverManager.getConnection("jdbc:sqlite:/C://Users//Αριστέα//Downloads//artscribe (1).db")) {
            String sql = "SELECT * FROM User WHERE Email = ? AND Password = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                loggedInUser = new User(rs.getString("Name"), rs.getString("Surname"), rs.getString("Phone"), email, password);
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    private void displayMainMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 1));

        JButton profileButton = new JButton("Profile");
        JButton scanQRButton = new JButton("Scan QR Code");
        JButton searchByCityButton = new JButton("Search Museums by City");
        JButton searchByKeywordButton = new JButton("Search Museums by Keyword");
        JButton onlineReservationButton = new JButton("Online Reservation");
        JButton checkTrafficButton = new JButton("Check Traffic");
        JButton quitButton = new JButton("Quit");

        panel.add(profileButton);
        panel.add(scanQRButton);
        panel.add(searchByCityButton);
        panel.add(searchByKeywordButton);
        panel.add(onlineReservationButton);
        panel.add(checkTrafficButton);
        panel.add(quitButton);

        profileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (loggedInUser != null) {
                    showProfile(loggedInUser);
                } else {
                    JOptionPane.showMessageDialog(null, "Please log in first.");
                }
            }
        });

        scanQRButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scanQRCode();
            }
        });

        searchByCityButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchMuseumsByCity();
            }
        });

        searchByKeywordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchMuseumsByKeyword();
            }
        });

        onlineReservationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onlineReservation();
            }
        });

        checkTrafficButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkTraffic();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JOptionPane.showOptionDialog(null, panel, "Main Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
    }

    public static boolean establishConnection() {
        try {
            System.out.println("Connecting to database...");
            dbConnection = DriverManager.getConnection("jdbc:sqlite:/C://Users//Αριστέα//Downloads//artscribe (1).db");
            dbConnection.createStatement().execute("PRAGMA busy_timeout = 5000");
            System.out.println("Connected to database...");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showProfile(User user) {
        JPanel panel = new JPanel(new GridLayout(8, 2));  // Updated to 8 rows
        nameField = new JTextField(user.getName());
        surnameField = new JTextField(user.getSurname());
        phoneField = new JTextField(user.getPhone());
        emailField = new JTextField(user.getEmail());

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Surname:"));
        panel.add(surnameField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);

        JButton viewTicketsButton = new JButton("View Tickets");
        JButton cancelTicketsButton = new JButton("Cancel Tickets");
        JButton updateProfileButton = new JButton("Update Profile");
        JButton logoutButton = new JButton("Logout");  // Added Logout button

        panel.add(viewTicketsButton);
        panel.add(cancelTicketsButton);
        panel.add(updateProfileButton);
        panel.add(logoutButton);  // Added Logout button to panel

        viewTicketsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewTickets(user);
            }
        });

        cancelTicketsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelTickets(user);
            }
        });

        updateProfileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateProfile(user);
            }
        });
        

        logoutButton.addActionListener(new ActionListener() {  // Added Logout button action listener
            public void actionPerformed(ActionEvent e) {
                logoutUser();
            }
        });

        int result = JOptionPane.showConfirmDialog(null, panel, "Profile", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            // Handle profile updates here if needed
        }
    }
    private void updateProfile(User user) {
        String name = nameField.getText();
        String surname = surnameField.getText();
        String phoneNumber = phoneField.getText();
        String email = emailField.getText();

        if (name.isEmpty() || surname.isEmpty() || phoneNumber.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error: All fields are required.");
            showProfile(user); // Show profile again if fields are empty
        } else {
            Connection con = null;
            try {
                con = DriverManager.getConnection("jdbc:sqlite:/C://Users//Αριστέα//Downloads//artscribe (1).db");
                con.createStatement().execute("PRAGMA busy_timeout = 5000"); // Set busy timeout to 5000ms (5 seconds)

                String sql = "UPDATE User SET Name = ?, Surname = ?, Phone = ?, Email = ? WHERE Email = ?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, name);
                stmt.setString(2, surname);
                stmt.setString(3, phoneNumber);
                stmt.setString(4, email);
                stmt.setString(5, user.getEmail());

                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    user.setName(name);
                    user.setSurname(surname);
                    user.setPhoneNumber(phoneNumber);
                    user.setEmail(email);

                    JOptionPane.showMessageDialog(null, "Profile updated successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "Error: Unable to update profile.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            } finally {
                if (con != null) {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            showProfile(user);
        }
    }

    private void viewTickets(User user) {
        List<Reservation> reservations = user.getReservations();
        if (reservations.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No tickets found.");
            return;
        }

        StringBuilder ticketDetails = new StringBuilder();
        for (Reservation reservation : reservations) {
            ticketDetails.append("Museum: ").append(reservation.getMuseum())
                         .append(", Date: ").append(reservation.getDateTime())
                         .append(", Price: ").append(reservation.getTicketPrice())
                         .append("\n");
        }

        JOptionPane.showMessageDialog(null, "Your Tickets:\n" + ticketDetails.toString());
  
         
            // Θα αποθηκεύσουμε το μουσείο από το πρώτο εισιτήριο
            String museum = reservations.get(0).getMuseum();

            // Τώρα πρέπει να ανακτήσουμε τα εκθέματα του συγκεκριμένου μουσείου από τη βάση δεδομένων
            try {
                List<Exhibit> exhibits = getExhibitsByMuseum(museum); // Υποθέτουμε ότι υπάρχει μια μέθοδος που ανακτά τα εκθέματα από τη βάση δεδομένων
                if (exhibits.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No exhibits found for this museum.");
                    return;
                }

                StringBuilder exhibitDetails = new StringBuilder();
                for (Exhibit exhibit : exhibits) {
                    exhibitDetails.append("Number: ").append(exhibit.getNumber())
                                   .append(", Description: ").append(exhibit.getDescription())
                                   .append("\n");
                }

                JOptionPane.showMessageDialog(null, "Exhibits in " + museum + ":\n" + exhibitDetails.toString());
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "An error occurred while retrieving exhibits.");
            }
        }

        // Προσθέτουμε μια μέθοδο που ανακτά τα εκθέματα από τη βάση δεδομένων με βάση το μουσείο
    public static List<Exhibit> getExhibitsByMuseum(String museumName) throws SQLException {
        String query = "SELECT * FROM exhibit WHERE museum = ?";
        List<Exhibit> exhibits = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, museumName);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int number = resultSet.getInt("number");
                    String description = resultSet.getString("description");
                    Exhibit exhibit = new Exhibit(number, description, museumName);
                    exhibits.add(exhibit);
                }
            }
        }

        return exhibits;
    }



    
 

    private void cancelTickets(User user) {
        List<Reservation> reservations = user.getReservations();
        if (reservations.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No tickets found.");
            return;
        }

        String[] options = new String[reservations.size()];
        for (int i = 0; i < reservations.size(); i++) {
            Reservation reservation = reservations.get(i);
            options[i] = "Museum: " + reservation.getMuseum() + ", Date: " + reservation.getDateTime() + ", Price: " + reservation.getTicketPrice();
        }

        String selectedOption = (String) JOptionPane.showInputDialog(null, "Select a ticket to cancel:", "Cancel Tickets", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (selectedOption != null) {
            for (Iterator<Reservation> iterator = reservations.iterator(); iterator.hasNext(); ) {
                Reservation reservation = iterator.next();
                if (selectedOption.contains(reservation.getMuseum()) && selectedOption.contains(reservation.getDateTime())) {
                    iterator.remove();
                    JOptionPane.showMessageDialog(null, "Ticket canceled:\n" + selectedOption);
                    break;
                }
            }
        }
    }

    private Exhibit getExhibitByNumber(int exhibitNumber) {
        Exhibit exhibit = null;
        try {
            Connection con = DriverManager.getConnection("jdbc:sqlite:/C://Users//Αριστέα//Downloads//artscribe (1).db");
            String sql = "SELECT * FROM Exhibit WHERE Number = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, exhibitNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Retrieve exhibit details from the database
                int number = rs.getInt("Number");
                String description = rs.getString("Description");
                int museumId = rs.getInt("MuseumId");
                Museum museum = getMuseumById(museumId); // Assuming you have implemented getMuseumById method
                exhibit = new Exhibit(number, description, museum);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exhibit;
    }

    private Museum getMuseumById(int museumId) {
        Museum museum = null;
        try {
            Connection con = DriverManager.getConnection("jdbc:sqlite:/C://Users//Αριστέα//Downloads//artscribe (1).db");
            String sql = "SELECT * FROM Museum WHERE Id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, museumId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Retrieve museum details from the database
                String museumname = rs.getString("MuseumName");
                String workHours = rs.getString("WorkHours");
                String address = rs.getString("Address");
                String category = rs.getString("Category");
                double ticketPrice = rs.getDouble("TicketPrice");
                String keyWord = rs.getString("KeyWord");
                String trafficinfo = rs.getString("TrafficInfo");
                museum = new Museum(museumname, workHours, address, category, ticketPrice, keyWord, trafficinfo);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return museum;
    }

    private Exhibit getExhibitByQRCode(String qrCode) {
        Exhibit exhibit = null;
        String query = "SELECT * FROM Exhibit WHERE QRCode = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:/C://Users//Αριστέα//Downloads//artscribe (1).db");
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, qrCode);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int exhibitNumber = resultSet.getInt("ExhibitNumber");
                String description = resultSet.getString("Description");
                // Other exhibit details can be fetched similarly

                // Create the Exhibit object
                Museum museum = new Museum("Museum Name", "Work Hours", "Address", "Category", 0.0, "Keyword","TrafficInfo");
                exhibit = new Exhibit(exhibitNumber, description, museum);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exhibit;
    }

    private void scanQRCode() {
        JOptionPane.showMessageDialog(null, "Get ready to scan the QR code.");

        // Simulate opening the camera
        int cameraResult = JOptionPane.showConfirmDialog(null, "Open camera to scan QR code?", "Camera", JOptionPane.YES_NO_OPTION);
        if (cameraResult == JOptionPane.YES_OPTION) {
            // Simulate audio guidance
            JOptionPane.showMessageDialog(null, "Align the QR code within the frame. Hold the camera steady.");

            
            String codeData = "QR_CODE_DATA_HERE";

            // Fetch exhibit description based on the scanned QR code
            Exhibit exhibit = getExhibitByQRCode(codeData);

            if (exhibit != null) {
                // Display the exhibit description
                JOptionPane.showMessageDialog(null, "Scanned exhibit description:\n" + exhibit.getDescription());
                // Optionally, you can perform further actions based on the scanned exhibit
            } else {
                JOptionPane.showMessageDialog(null, "Error: Unable to find exhibit information for the scanned QR code.");
            }
        }
    }

    private void searchMuseumsByCity() {
        List<City> cities = City.getAllCities();
        if (cities.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No cities found in the database.");
            return;
        }

        String[] cityNames = new String[cities.size()];
        for (int i = 0; i < cities.size(); i++) {
            cityNames[i] = cities.get(i).getName();
        }

        String selectedCity = (String) JOptionPane.showInputDialog(null, "Select a city:", 
                "City Selection", JOptionPane.QUESTION_MESSAGE, null, cityNames, cityNames[0]);

        if (selectedCity != null && !selectedCity.trim().isEmpty()) {
            City city = null;
            for (City c : cities) {
                if (c.getName().equals(selectedCity)) {
                    city = c;
                    break;
                }
            }
            if (city == null) {
                JOptionPane.showMessageDialog(null, "Selected city not found.");
                return;
            }

            List<Museum> museumsInCity = city.getMuseums();
            if (museumsInCity.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No museums found in " + selectedCity);
                return;
            }

            String[] museumNames = new String[museumsInCity.size() + 1];
            for (int i = 0; i < museumsInCity.size(); i++) {
                museumNames[i] = museumsInCity.get(i).getMuseumName();
            }
            museumNames[museumsInCity.size()] = "Cancel";

            while (true) {
                int selection = JOptionPane.showOptionDialog(null, "Select a museum", 
                        "Museums in " + selectedCity, JOptionPane.DEFAULT_OPTION, 
                        JOptionPane.INFORMATION_MESSAGE, null, museumNames, museumNames[0]);

                if (selection >= 0 && selection < museumsInCity.size()) {
                    Museum selectedMuseum = museumsInCity.get(selection);
                    int detailsOption = JOptionPane.showConfirmDialog(null, "Name: " + selectedMuseum.getMuseumName() +
                                    "\nWork Hours: " + selectedMuseum.getWorkHours() +
                                    "\nAddress: " + selectedMuseum.getAddress() +
                                    "\nCategory: " + selectedMuseum.getCategory() +
                                    "\nTicket Price: " + selectedMuseum.getTicketPrice() +
                                    "\nKey Word: " + selectedMuseum.getKeyWord(), "Museum Details",
                            JOptionPane.YES_NO_OPTION);

                    if (detailsOption == JOptionPane.NO_OPTION) {
                        JOptionPane.showMessageDialog(null, "Returning to menu.");
                        break;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Returning to menu.");
                    break;
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No city selected. Returning to menu.");
        }
    }

    private void searchMuseumsByKeyword() {
        String keyword = JOptionPane.showInputDialog("Enter a keyword for the museum:");
        if (keyword != null && !keyword.trim().isEmpty()) {
            List<Museum> filteredMuseums = Museum.getMuseumsByKeywordFromDatabase(keyword);

            if (filteredMuseums.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No museums found for the keyword: " + keyword);
                return;
            }

            String[] museumNames = new String[filteredMuseums.size() + 1];
            for (int i = 0; i < filteredMuseums.size(); i++) {
                museumNames[i] = filteredMuseums.get(i).getMuseumName();
            }
            museumNames[filteredMuseums.size()] = "Cancel";

            int selection = JOptionPane.showOptionDialog(null, "Select a museum", "Museums matching keyword: " + keyword, 
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, museumNames, museumNames[0]);

            if (selection >= 0 && selection < filteredMuseums.size()) {
                Museum selectedMuseum = filteredMuseums.get(selection);
                int detailsOption = JOptionPane.showConfirmDialog(null, "Name: " + selectedMuseum.getMuseumName() +
                                "\nWork Hours: " + selectedMuseum.getWorkHours() +
                                "\nAddress: " + selectedMuseum.getAddress() +
                                "\nCategory: " + selectedMuseum.getCategory() +
                                "\nTicket Price: " + selectedMuseum.getTicketPrice() +
                                "\nKey Word: " + selectedMuseum.getKeyWord(), "Museum Details",
                        JOptionPane.YES_NO_OPTION);

                if (detailsOption == JOptionPane.YES_OPTION) {
                    int reservationOption = JOptionPane.showConfirmDialog(null, "Do you want to make a reservation?", "Reservation", JOptionPane.YES_NO_OPTION);
                    if (reservationOption == JOptionPane.YES_OPTION) {
                        onlineReservation();
                    } else {
                        JOptionPane.showMessageDialog(null, "Returning to menu.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Returning to menu.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Returning to menu.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No keyword entered. Returning to menu.");
        }
    }

    private void onlineReservation() {
        // Ask the user to select a museum
        Museum selectedMuseum = selectMuseum();

        if (selectedMuseum != null) {
            // Display museum details
            int detailsOption = JOptionPane.showConfirmDialog(null, "Name: " + selectedMuseum.getMuseumName() +
                    "\nWork Hours: " + selectedMuseum.getWorkHours() +
                    "\nAddress: " + selectedMuseum.getAddress() +
                    "\nCategory: " + selectedMuseum.getCategory() +
                    "\nTicket Price: " + selectedMuseum.getTicketPrice() +
                    "\nKey Word: " + selectedMuseum.getKeyWord(), "Museum Details",
                    JOptionPane.YES_NO_OPTION);

            if (detailsOption == JOptionPane.YES_OPTION) {
                // Prompt for reservation details
                String name = JOptionPane.showInputDialog("Enter your name:");
                String surname = JOptionPane.showInputDialog("Enter your surname:");
                String phone = JOptionPane.showInputDialog("Enter your phone number:");
                String email = JOptionPane.showInputDialog("Enter your email:");
                String datetime = JOptionPane.showInputDialog("Enter datetime:");
                

                // Display reservation details
                int reservationOption = JOptionPane.showConfirmDialog(null,
                        "Name: " + name +
                                "\nSurname: " + surname +
                                "\nPhone: " + phone +
                                "\nEmail: " + email +
                		 "\nTicket Price: " + selectedMuseum.getTicketPrice() +
                         "\nDatetime: " + datetime,
                 "Reservation Details",
                 JOptionPane.YES_NO_OPTION);

         if (reservationOption == JOptionPane.YES_OPTION) {
             // Process payment (Always consider payment successful)
             boolean paymentSuccess = true;

             if (paymentSuccess) {
                 // Add reservation to user's list
                 Reservation reservation = new Reservation(name, surname, phone, email, datetime, selectedMuseum.getTicketPrice(), selectedMuseum.getMuseumName());
                 loggedInUser.addReservation(reservation);
                 JOptionPane.showMessageDialog(null, "Reservation successful.");
             } else {
                 JOptionPane.showMessageDialog(null, "Payment failed. Please try again.");
             }
         } else {
             JOptionPane.showMessageDialog(null, "Reservation cancelled.");
         }
     } else {
         JOptionPane.showMessageDialog(null, "Reservation cancelled.");
     }
 }
}

private Museum selectMuseum() {
 // Ask the user to input the museum name
 String museumName = JOptionPane.showInputDialog("Enter the name of the museum:");

 if (museumName == null || museumName.trim().isEmpty()) {
     JOptionPane.showMessageDialog(null, "No museum selected.");
     return null;
 }

 // Retrieve the museum from the database based on the entered name
 Museum museum = Museum.getMuseumByNameFromDatabase(museumName); // Implement this method

 if (museum == null) {
     JOptionPane.showMessageDialog(null, "Museum not found: " + museumName);
 }

 return museum;
}

private void checkTraffic() {
 String museumName = JOptionPane.showInputDialog("Enter museum name to check traffic:");
 Museum museum = Museum.getMuseumByNameFromDatabase(museumName);
 if (museum != null) {
     // Display traffic information from the database
     String trafficInfo = museum.getTrafficInfo();
     if (trafficInfo != null && !trafficInfo.isEmpty()) {
         JOptionPane.showMessageDialog(null, "Traffic information for " + museum.getMuseumName() + ":\n" + trafficInfo);
     } else {
         JOptionPane.showMessageDialog(null, "No traffic information available for " + museum.getMuseumName());
     }
 } else {
     JOptionPane.showMessageDialog(null, "Museum not found.");
 }
}

public static void main(String[] args) {
 if (establishConnection()) {
     SwingUtilities.invokeLater(new Runnable() {
         public void run() {
             new Menu().displayInitialMenu();
         }
     });
 } else {
     System.out.println("Failed to establish database connection.");
 }
}

private void setupTextField(JTextField textField) {
 textField.setMaximumSize(new Dimension(300, 30));
 textField.setAlignmentX(Component.CENTER_ALIGNMENT);
 textField.setBorder(BorderFactory.createCompoundBorder(
     BorderFactory.createLineBorder(Color.GRAY, 1),
     BorderFactory.createEmptyBorder(5, 10, 5, 10)));
 textField.setBackground(Color.WHITE);
}

private void setupPasswordField(JPasswordField passwordField) {
 passwordField.setMaximumSize(new Dimension(300, 30));
 passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
 passwordField.setBorder(BorderFactory.createCompoundBorder(
     BorderFactory.createLineBorder(Color.GRAY, 1),
     BorderFactory.createEmptyBorder(5, 10, 5, 10)));
 passwordField.setBackground(Color.WHITE);
}

private void addFormField(JPanel panel, JComponent field) {
 field.setAlignmentX(Component.CENTER_ALIGNMENT);
 panel.add(field);
 panel.add(Box.createVerticalStrut(10));
}

private void styleButton(JButton button) {
 button.setMaximumSize(new Dimension(300, 35));
 button.setFont(new Font("Arial", Font.BOLD, 14));
 button.setBackground(new Color(255, 255, 255)); // White background
 button.setOpaque(true);
 button.setBorderPainted(true);
 button.setAlignmentX(Component.CENTER_ALIGNMENT);
}
}

//PlaceholderTextField class to simulate placeholder behavior
class PlaceholderTextField extends JTextField {
	
public PlaceholderTextField(String placeholder) {
 super(placeholder);
 setForeground(Color.GRAY);
 addFocusListener(new FocusAdapter() {
     public void focusGained(FocusEvent e) {
         if (getText().equals(placeholder)) {
             setText("");
             setForeground(Color.BLACK);
         }
     }

     public void focusLost(FocusEvent e) {
         if (getText().isEmpty()) {
             setText(placeholder);
             setForeground(Color.GRAY);
         }
     }
 });
}
}

//Similar PlaceholderPasswordField to manage password fields with placeholders
class PlaceholderPasswordField extends JPasswordField {
char defaultChar;
String placeholder;

public PlaceholderPasswordField(String placeholder) {
 super(placeholder);
 this.placeholder = placeholder;
 defaultChar = getEchoChar();
 setEchoChar((char) 0);
 setForeground(Color.GRAY);
 addFocusListener(new FocusAdapter() {
     public void focusGained(FocusEvent e) {
         JPasswordField pf = PlaceholderPasswordField.this;
         if (new String(pf.getPassword()).equals(placeholder)) {
             pf.setText("");
             pf.setForeground(Color.BLACK);
             pf.setEchoChar(defaultChar); // This sets the character used to mask passwords
         }
     }

     public void focusLost(FocusEvent e) {
         JPasswordField pf = PlaceholderPasswordField.this;
         if (new String(pf.getPassword()).isEmpty()) {
             pf.setForeground(Color.GRAY);
             pf.setText(placeholder);
             pf.setEchoChar((char) 0); // Disable masking
         }
     }
 });
}
}
