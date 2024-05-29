
package net.codejava;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;



public class Menu extends JFrame {
    private JTextField nameField;
    private JTextField surnameField;
    private JTextField phoneField;
    private JTextField emailField;
    private JPasswordField passwordField;

    static Connection dbConnection = null;
    
    
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
                displayMainMenu();
            }
        });

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginUser();
                displayMainMenu();
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
                showProfile();
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
            dbConnection = DriverManager.getConnection("jdbc:sqlite:/C:/Users/Αριστέα/Downloads/artscribe (1).db");
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
            String phoneNumber = phoneField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            try (Connection con = DriverManager.getConnection("jdbc:sqlite:/C:/Users/Αριστέα/Downloads/artscribe (1).db")) {
                String sql = "INSERT INTO User (Name, Surname, Phone, Email, Password) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, name);
                stmt.setString(2, surname);
                stmt.setString(3, phoneNumber);
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

            try (Connection con = DriverManager.getConnection("jdbc:sqlite:/C:/Users/Αριστέα/Downloads/artscribe (1).db")) {
                String sql = "SELECT * FROM User WHERE Email = ? AND Password = ?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, email);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("Name");
                    String surname = rs.getString("Surname");
                    JOptionPane.showMessageDialog(null, "Login successful. Welcome, " + name + " " + surname);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid email or password.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        if (establishConnection()) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new Menu().displayInitialMenu();
                }
            });
        }
    }
}

               
