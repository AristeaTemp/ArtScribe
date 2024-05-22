import java.sql.*;
import java.util.Scanner;

public class Login {
    private static final String DB_URL = "jdbc:sqlite:C:/Users/despina/Desktop/ceid/τλ/artscribe.db"; // Adjust the path if needed

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        while (choice != 3) {
            System.out.println("Select an option: ");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Quit");
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

        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            String query = "INSERT INTO users (name, surname, phone_number, email, password, ) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setString(3, phoneNumber);
            statement.setString(4, email);
            statement.setString(5, password);
            statement.executeUpdate();
            System.out.println("Registration successful.");
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE constraint failed")) {
                System.out.println("User already exists.");
            } else {
                e.printStackTrace();
            }
        }
    }

    static void loginUser(Scanner scanner) {
        System.out.println("Enter your email:");
        String email = scanner.nextLine();
        System.out.println("Enter your password:");
        String password = scanner.nextLine();

        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Login successful.");
                User user = new User(
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("phone_number"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                );
                System.out.println("Welcome, " + user.getName() + " " + user.getSurname());
            } else {
                System.out.println("Invalid email or password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}