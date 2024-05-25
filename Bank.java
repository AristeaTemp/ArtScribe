import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class Bank {
    private static final String DB_URL = "jdbc:sqlite:/absolute/path/to/artscribe.db";
    private Map<Integer, Transaction> transactions;

    public Bank() {
        this.transactions = new HashMap<>();
    }

    public boolean processPayment(User user, double amount) {
        if (verifyPaymentDetails(user.getPaymentDetails())) {
            SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
                @Override
                protected Boolean doInBackground() throws Exception {
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

                @Override
                protected void done() {
                    try {
                        if (get()) {
                            System.out.println("Payment processed for user: " + user.getName() + ", Amount: $" + amount);
                        } else {
                            System.out.println("Payment failed: Unable to process transaction.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            worker.execute();
            return true;
        } else {
            System.out.println("Payment failed: Invalid payment details.");
            return false;
        }
    }

    private boolean verifyPaymentDetails(String paymentDetails) {
        // Implement real validation logic
        return paymentDetails != null && !paymentDetails.isEmpty();
    }

    public Transaction getTransactionById(int transactionId) {
        return transactions.get(transactionId);
    }
}
