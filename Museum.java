import java.sql.*;
import javax.swing.*;

public class Museum {
    private static final String DB_URL = "jdbc:sqlite:C:/Users/despina/Desktop/ceid/tl/tl2/artscribe.db";

    private String name;
    private String workHours;
    private String address;
    private String category;
    private double ticketPrice;
    private String keyWord;

    public Museum(String name, String workHours, String address, String category, double ticketPrice, String keyWord) {
        this.name = name;
        this.workHours = workHours;
        this.address = address;
        this.category = category;
        this.ticketPrice = ticketPrice;
        this.keyWord = keyWord;
    }

    public String getName() {
        return name;
    }

    public String getWorkHours() {
        return workHours;
    }

    public String getAddress() {
        return address;
    }

    public String getCategory() {
        return category;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWorkHours(String workHours) {
        this.workHours = workHours;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public static void addMuseum(Museum museum) {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            String insertQuery = "INSERT INTO Museum (name, work_hours, address, category, ticket_price, key_word) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, museum.getName());
                preparedStatement.setString(2, museum.getWorkHours());
                preparedStatement.setString(3, museum.getAddress());
                preparedStatement.setString(4, museum.getCategory());
                preparedStatement.setDouble(5, museum.getTicketPrice());
                preparedStatement.setString(6, museum.getKeyWord());

                preparedStatement.executeUpdate();
                System.out.println("Museum added successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void viewMuseums() {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            String query = "SELECT * FROM Museum";
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);
                System.out.println("Museums in the database:");
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String workHours = resultSet.getString("work_hours");
                    String address = resultSet.getString("address");
                    String category = resultSet.getString("category");
                    double ticketPrice = resultSet.getDouble("ticket_price");
                    String keyWord = resultSet.getString("key_word");
                    System.out.println("Name: " + name + ", Work Hours: " + workHours + ", Address: " + address + ", Category: " + category + ", Ticket Price: " + ticketPrice + ", Key Word: " + keyWord);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Museum Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel);

        JButton addMuseumButton = new JButton("Add Museum");
        JButton viewMuseumsButton = new JButton("View Museums");

        panel.add(addMuseumButton);
        panel.add(viewMuseumsButton);

        addMuseumButton.addActionListener(e -> addMuseumGUI());
        viewMuseumsButton.addActionListener(e -> viewMuseumsGUI());

        frame.pack();
        frame.setVisible(true);
    }

    private static void addMuseumGUI() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                JTextField nameField = new JTextField(10);
                JTextField workHoursField = new JTextField(10);
                JTextField addressField = new JTextField(10);
                JTextField categoryField = new JTextField(10);
                JTextField ticketPriceField = new JTextField(10);
                JTextField keyWordField = new JTextField(10);

                JPanel myPanel = new JPanel();
                myPanel.add(new JLabel("Name:"));
                myPanel.add(nameField);
                myPanel.add(Box.createHorizontalStrut(15)); // a spacer
                myPanel.add(new JLabel("Work Hours:"));
                myPanel.add(workHoursField);
                myPanel.add(Box.createHorizontalStrut(15)); // a spacer
                myPanel.add(new JLabel("Address:"));
                myPanel.add(addressField);
                myPanel.add(Box.createHorizontalStrut(15)); // a spacer
                myPanel.add(new JLabel("Category:"));
                myPanel.add(categoryField);
                myPanel.add(Box.createHorizontalStrut(15)); // a spacer
                myPanel.add(new JLabel("Ticket Price:"));
                myPanel.add(ticketPriceField);
                myPanel.add(Box.createHorizontalStrut(15)); // a spacer
                myPanel.add(new JLabel("Key Word:"));
                myPanel.add(keyWordField);

                int result = JOptionPane.showConfirmDialog(null, myPanel, 
                         "Please Enter Museum Details", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    String name = nameField.getText();
                    String workHours = workHoursField.getText();
                    String address = addressField.getText();
                    String category = categoryField.getText();
                    double ticketPrice = Double.parseDouble(ticketPriceField.getText());
                    String keyWord = keyWordField.getText();

                    Museum museum = new Museum(name, workHours, address, category, ticketPrice, keyWord);
                    Museum.addMuseum(museum);
                }
                return null;
            }
        };

        worker.execute();
    }

    private static void viewMuseumsGUI() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                try (Connection connection = DriverManager.getConnection(DB_URL)) {
                    String query = "SELECT * FROM Museum";
                    try (Statement statement = connection.createStatement()) {
                        ResultSet resultSet = statement.executeQuery(query);
                        StringBuilder museumsList = new StringBuilder("Museums in the database:\n");
                        while (resultSet.next()) {
                            String name = resultSet.getString("name");
                            String workHours = resultSet.getString("work_hours");
                            String address = resultSet.getString("address");
                            String category = resultSet.getString("category");
                            double ticketPrice = resultSet.getDouble("ticket_price");
                            String keyWord = resultSet.getString("key_word");
                            museumsList.append("Name: ").append(name)
                                    .append(", Work Hours: ").append(workHours)
                                    .append(", Address: ").append(address)
                                    .append(", Category: ").append(category)
                                    .append(", Ticket Price: ").append(ticketPrice)
                                    .append(", Key Word: ").append(keyWord)
                                    .append("\n");
                        }
                        JOptionPane.showMessageDialog(null, museumsList.toString());
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