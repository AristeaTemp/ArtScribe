package net.codejava;

import java.sql.*;
import javax.swing.*;

public class Bank {
    private static final String DB_URL ="jdbc:sqlite:/C://Users//Αριστέα//Downloads//artscribe (1).db";

    public boolean processReservation(User user, double amount, String cardNumber, String expirationDate, String cardName, String cvv) {
        if (!verifyCreditCard(user, cardNumber, expirationDate, cardName, cvv)) {
            System.out.println("Reservation failed: Credit card verification failed.");
            return false;
        }
        
        double userBalance = getUserBalance(user.getId());
        if (userBalance < amount) {
            System.out.println("Reservation failed: Insufficient balance.");
            return false;
        }

        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            protected Boolean doInBackground() {
                try (Connection connection = DriverManager.getConnection(DB_URL)) {
                    String insertQuery = "INSERT INTO Transactions (UserId, Amount) VALUES (?, ?)";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                        preparedStatement.setInt(1, user.getId());
                        preparedStatement.setDouble(2, amount);
                        preparedStatement.executeUpdate();
                    }
                    return true;
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }

            protected void done() {
                try {
                    if (get()) {
                        System.out.println("Reservation successful for user: " + user.getName() + ", Amount: $" + amount);
                    } else {
                        System.out.println("Reservation failed: Unable to process transaction.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
        return true;
    }
  
        public static String processPayment(String cardNumber, String expirationDate, String cvv, double amount) {
            // Simulate payment processing
            System.out.println("Payment successful.");
            return "ok successful";
        }
    


    private boolean verifyCreditCard(User user, String cardNumber, String expirationDate, String cardName, String cvv) {

        return true;
    }

    private double getUserBalance(int userId) {
        
        return 1000.0;
    }

    private static class User {
        private int id;
        private String name;

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
