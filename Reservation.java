import java.sql.*;
import javax.swing.*;

public class Reservation {
    private static final String DB_URL = "jdbc:sqlite:C:/Users/despina/Desktop/ceid/tl/tl2/artscribe.db";

    private String dateTime;
    private double price;
    private String museum;
    private String user;

    public Reservation(String dateTime, double price, String museum, String user) {
        this.dateTime = dateTime;
        this.price = price;
        this.museum = museum;
        this.user = user;
    }

    public String getDateTime() {
        return dateTime;
    }

    public double getPrice() {
        return price;
    }

    public String getMuseum() {
        return museum;
    }

    public String getUser() {
        return user;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setMuseum(String museum) {
        this.museum = museum;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public static void addReservation(Reservation reservation) {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            String insertQuery = "INSERT INTO Reservation (dateTime, price, museum, user) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, reservation.getDateTime());
                preparedStatement.setDouble(2, reservation.getPrice());
                preparedStatement.setString(3, reservation.getMuseum());
                preparedStatement.setString(4, reservation.getUser());

                preparedStatement.executeUpdate();
                System.out.println("Reservation added successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void viewReservations() {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            String query = "SELECT * FROM Reservation";
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);
                System.out.println("Reservations in the database:");
                while (resultSet.next()) {
                    String dateTime = resultSet.getString("dateTime");
                    double price = resultSet.getDouble("price");
                    String museum = resultSet.getString("museum");
                    String user = resultSet.getString("user");
                    System.out.println("DateTime: " + dateTime + ", Price: " + price + ", Museum: " + museum + ", User: " + user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Reservation Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel);

        JButton addReservationButton = new JButton("Add Reservation");
        JButton viewReservationsButton = new JButton("View Reservations");

        panel.add(addReservationButton);
        panel.add(viewReservationsButton);

        addReservationButton.addActionListener(e -> addReservationGUI());
        viewReservationsButton.addActionListener(e -> viewReservationsGUI());

        frame.pack();
        frame.setVisible(true);
    }

    private static void addReservationGUI() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                JTextField dateTimeField = new JTextField(10);
                JTextField priceField = new JTextField(10);
                JTextField museumField = new JTextField(10);
                JTextField userField = new JTextField(10);

                JPanel myPanel = new JPanel();
                myPanel.add(new JLabel("DateTime (YYYY-MM-DD HH:MM):"));
                myPanel.add(dateTimeField);
                myPanel.add(Box.createHorizontalStrut(15)); // a spacer
                myPanel.add(new JLabel("Price:"));
                myPanel.add(priceField);
                myPanel.add(Box.createHorizontalStrut(15)); // a spacer
                myPanel.add(new JLabel("Museum:"));
                myPanel.add(museumField);
                myPanel.add(Box.createHorizontalStrut(15)); // a spacer
                myPanel.add(new JLabel("User:"));
                myPanel.add(userField);

                int result = JOptionPane.showConfirmDialog(null, myPanel, 
                         "Please Enter Reservation Details", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    String dateTime = dateTimeField.getText();
                    double price = Double.parseDouble(priceField.getText());
                    String museum = museumField.getText();
                    String user = userField.getText();

                    Reservation reservation = new Reservation(dateTime, price, museum, user);
                    Reservation.addReservation(reservation);
                }
                return null;
            }
        };

        worker.execute();
    }

    private static void viewReservationsGUI() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                try (Connection connection = DriverManager.getConnection(DB_URL)) {
                    String query = "SELECT * FROM Reservation";
                    try (Statement statement = connection.createStatement()) {
                        ResultSet resultSet = statement.executeQuery(query);
                        StringBuilder reservationsList = new StringBuilder("Reservations in the database:\n");
                        while (resultSet.next()) {
                            String dateTime = resultSet.getString("dateTime");
                            double price = resultSet.getDouble("price");
                            String museum = resultSet.getString("museum");
                            String user = resultSet.getString("user");
                            reservationsList.append("DateTime: ").append(dateTime)
                                    .append(", Price: ").append(price)
                                    .append(", Museum: ").append(museum)
                                    .append(", User: ").append(user)
                                    .append("\n");
                        }
                        JOptionPane.showMessageDialog(null, reservationsList.toString());
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
