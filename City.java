import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class City {
    private String name;
    private List<Museum> museums;  // List to hold museums in the city

    // Constructor
    public City(String name) {
        this.name = name;
        this.museums = new ArrayList<>();
    }

    // Check if the city exists in the database
    public static boolean cityExists(String cityName) {
        String query = "SELECT * FROM Cities WHERE Name = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, cityName);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Returns true if city exists, false otherwise
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get a list of all museums in the city
    public List<Museum> getMuseums() {
        return new ArrayList<>(museums);
    }

    // Get the city's name
    public String getName() {
        return name;
    }

    // Set the city's name
    public void setName(String name) {
        this.name = name;
    }
}
