import java.io.File;
import java.sql.*;
import java.util.Scanner;
import javax.swing.*;

public class Login {
    // Update this to the absolute path of your database file
    private static final String DB_URL = "jdbc:sqlite:C:/Users/despina/Desktop/ceid/tl/tl2/artscribe.db";

    public static void main(String[] args) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to load SQLite JDBC driver.");
            e.printStackTrace();
            return;
        }

        // Print the current working directory
        String workingDirectory = System.getProperty("user.dir");
        System.out.println("Current working directory: " + workingDirectory);

        // Check if the database file exists
        File dbFile = new File(DB_URL.replace("jdbc:sqlite:", ""));
        if (dbFile.exists() && !dbFile.isDirectory()) {
            System.out.println("Database file found at: " + dbFile.getAbsolutePath());
        } else {
            System.err.println("Database file not found at: " + dbFile.getAbsolutePath());
            return;
        }

        // Verify database connection and check tables
        if (!verifyDatabaseConnection()) {
            System.err.println("Failed to connect to the database.");
            return;
        }

        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static boolean verifyDatabaseConnection() {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            if (connection != null) {
                System.out.println("Connected to the database successfully.");
                // List all tables
                listTables(connection);
                // Print the schema of the User table
                printTableSchema(connection, "User");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void listTables(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT name FROM sqlite_master WHERE type='table';");
            System.out.println("Tables in the database:");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void printTableSchema(Connection connection, String tableName) {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("PRAGMA table_info(" + tableName + ");");
            System.out.println("Schema of table " + tableName + ":");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name") + " - " + resultSet.getString("type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel);

        JButton registerButton = new JButton("Register");
        JButton loginButton = new JButton("Login");

        panel.add(registerButton);
        panel.add(loginButton);

        registerButton.addActionListener(e -> registerUser());
        loginButton.addActionListener(e -> loginUser());

        frame.pack();
        frame.setVisible(true);
    }

    private static void registerUser() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                try (Connection connection = DriverManager.getConnection(DB_URL)) {
                    String insertQuery = "INSERT INTO user (name, surname, phone_number, email, password) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                        Scanner scanner = new Scanner(System.in);
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

                        preparedStatement.setString(1, name);
                        preparedStatement.setString(2, surname);
                        preparedStatement.setString(3, phoneNumber);
                        preparedStatement.setString(4, email);
                        preparedStatement.setString(5, password);

                        preparedStatement.executeUpdate();
                        System.out.println("Registration successful.");
                    }
                } catch (SQLException e) {
                    if (e.getMessage().contains("UNIQUE constraint failed")) {
                        System.out.println("User already exists.");
                    } else {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        };

        worker.execute();
    }

    private static void loginUser() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                try (Connection connection = DriverManager.getConnection(DB_URL)) {
                    String query = "SELECT * FROM user WHERE email = ? AND password = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        Scanner scanner = new Scanner(System.in);
                        System.out.println("Enter your email:");
                        String email = scanner.nextLine();
                        System.out.println("Enter your password:");
                        String password = scanner.nextLine();

                        preparedStatement.setString(1, email);
                        preparedStatement.setString(2, password);

                        ResultSet resultSet = preparedStatement.executeQuery();

                        if (resultSet.next()) {
                            System.out.println("Login successful.");
                            String name = resultSet.getString("Name");
                            String surname = resultSet.getString("Surname");
                            System.out.println("Welcome, " + name + " " + surname);
                        } else {
                            System.out.println("Invalid email or password.");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        worker.execute();
    }
}
