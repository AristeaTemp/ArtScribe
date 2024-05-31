package net.codejava;

import java.sql.;
import java.util.ArrayList;
import java.util.List;
import javax.swing.;

public class City {
    private String name;
    private List<Museum> museums;  // List to hold museums in the city

    // Constructor
    public City(String name) {
        this.name = name;
        this.museums = new ArrayList<>();
    }


    public static boolean cityExists(String cityName) {
        String query = "SELECT * FROM City WHERE City name = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:/C://Users//Αριστέα//Downloads//THEOS.db");
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, cityName);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<City> getAllCities() {
        List<City> cities = new ArrayList<>();
        String query = "SELECT * FROM City";
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:/C://Users//Αριστέα//Downloads//artscribe (1).db");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                String cityName = resultSet.getString("City name");
                cities.add(new City(cityName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
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
