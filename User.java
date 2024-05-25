import javax.swing.*;

public class User {
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private String password;

    // Constructor
    public User(String name, String surname, String phoneNumber, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
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

    // Method to display user details
    public void displayDetails() {
        System.out.println("Name: " + name);
        System.out.println("Surname: " + surname);
        System.out.println("Phone Number: " + phoneNumber);
        System.out.println("Email: " + email);
    }

    // Method to change phone number
    public void changePhoneNumber(String newPhoneNumber) {
        this.phoneNumber = newPhoneNumber;
        System.out.println("Phone number changed to: " + newPhoneNumber);
    }

    public static void main(String[] args) {
        // Example usage
        User user = new User("John", "Doe", "1234567890", "john@example.com", "password");
        user.displayDetails();
        user.changePhoneNumber("0987654321");
        user.displayDetails();
    }
}
