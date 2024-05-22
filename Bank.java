import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<Integer, Transaction> transactions;  // Map to store transaction details by transaction ID

    public Bank() {
        this.transactions = new HashMap<>();
    }

    // Simulate processing a payment
    public boolean processPayment(User user, double amount) {
        if (verifyPaymentDetails(user.getPaymentDetails())) {
            Transaction newTransaction = new Transaction(user, amount);
            transactions.put(newTransaction.getId(), newTransaction);
            System.out.println("Payment processed for user: " + user.getName() + ", Amount: $" + amount);
            return true;
        } else {
            System.out.println("Payment failed: Invalid payment details.");
            return false;
        }
    }

    // Simulate verifying payment details
    private boolean verifyPaymentDetails(String paymentDetails) {
        // Here, you would implement real validation logic
        return paymentDetails != null && !paymentDetails.isEmpty();
    }

    // Retrieve a transaction by ID
    public Transaction getTransactionById(int transactionId) {
        return transactions.get(transactionId);
    }
}

// Supporting Transaction class to represent each transaction
class Transaction {
    private static int nextId = 1;
    private int id;
    private User user;
    private double amount;

    public Transaction(User user, double amount) {
        this.id = nextId++;
        this.user = user;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public double getAmount() {
        return amount;
    }
}