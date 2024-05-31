package net.codejava;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        // Constructor
    }

    private void displayInitialMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));

        JButton registerButton = new JButton("Register");
        JButton loginButton = new JButton("Login");

        panel.add(registerButton);
        panel.add(loginButton);

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

        JOptionPane.showOptionDialog(null, panel, "Welcome", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
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
            String phone = phoneField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            try (Connection con = DriverManager.getConnection("jdbc:sqlite:/C://Users//Αριστέα//Downloads//artscribe (1).db")) {
                String sql = "INSERT INTO User (Name, Surname, Phone, Email, Password) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, name);
                stmt.setString(2, surname);
                stmt.setString(3, phone);
                stmt.setString(4, email);
                stmt.setString(5, password);
                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(null, "Registration successful.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
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

            try (Connection con = DriverManager.getConnection("jdbc:sqlite:/C://Users//Αριστέα//Downloads//artscribe (1).db")) {
                String sql = "SELECT * FROM User WHERE Email = ? AND Password = ?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, email);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("Name");
                    String surname = rs.getString("Surname");
                    loggedInUser = new User(name, surname, rs.getString("Phone"), email, password);
                    JOptionPane.showMessageDialog(null, "Login successful. Welcome, " + name + " " + surname);
                    displayMainMenu();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid email or password.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        }
    }

    private void showProfile(User user) {
        JPanel panel = new JPanel(new GridLayout(7, 2));
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

        panel.add(viewTicketsButton);
        panel.add(cancelTicketsButton);
        panel.add(updateProfileButton);

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

        int result = JOptionPane.showConfirmDialog(null, panel, "Profile - " + user.getName(), JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            // No additional action needed here since updates are handled by the updateProfile method
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
            showProfile(user); // Show updated profile
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
                         .append(", Price: ").append(reservation.getPrice())
                         .append("\n");
        }

        JOptionPane.showMessageDialog(null, "Your Tickets:\n" + ticketDetails.toString());
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
            options[i] = "Museum: " + reservation.getMuseum().getMuseumName() + ", Date: " + reservation.getDateTime() + ", Price: " + reservation.getPrice();
        }

        String selectedOption = (String) JOptionPane.showInputDialog(null, "Select a ticket to cancel:", "Cancel Tickets", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (selectedOption != null) {
            for (Iterator<Reservation> iterator = reservations.iterator(); iterator.hasNext(); ) {
                Reservation reservation = iterator.next();
                if (selectedOption.contains(reservation.getMuseum().getMuseumName()) && selectedOption.contains(reservation.getDateTime())) {
                    iterator.remove();
                    JOptionPane.showMessageDialog(null, "Ticket canceled:\n" + selectedOption);
                    break;
                }
            }
        }
    }
    

    private void scanQRCode() {
        // Implementation of scan QR code
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
                        onlineReservation(); // Using the same reservation method
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
        // Implementation of online reservation
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
}
