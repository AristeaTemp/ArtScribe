import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

public class User {
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private String password;
    private List<String> bookings;

    // Constructor
    public User(String name, String surname, String phoneNumber, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.bookings = new ArrayList<>();  // Initialize the bookings list
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getBookings() {
        return bookings;
    }

    public void addBooking(String booking) {
        this.bookings.add(booking);
    }

    // Method to change phone number using SwingWorker
    public void changePhoneNumber(String newPhoneNumber) {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Simulate some background work
                Thread.sleep(2000); // Simulate a delay in updating phone number
                phoneNumber = newPhoneNumber;
                return null;
            }

            @Override
            protected void done() {
                try {
                    get(); // Retrieve the result of doInBackground() method
                    JOptionPane.showMessageDialog(null, "Phone number updated successfully!");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error updating phone number: " + e.getMessage());
                }
            }
        };

        worker.execute();
    }

    public static void main(String[] args) {
        User user = new User("John", "Doe", "1234567890", "john.doe@example.com", "password123");
        user.addBooking("Booking 1");
        user.addBooking("Booking 2");

        // Display bookings
        System.out.println("Bookings: " + user.getBookings());

        // Change phone number
        user.changePhoneNumber("0987654321");
    }
}
