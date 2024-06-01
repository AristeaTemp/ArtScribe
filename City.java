package net.codejava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;




public class City {
    private String name;
    private List<Museum> museums;

    public City(String name) {
        this.name = name;
        this.museums = new ArrayList<>();
    }

    public static boolean cityExists(String cityName) {
        String query = "SELECT * FROM City WHERE City name = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:/C://Users//Αριστέα//Downloads//artscribe (1).db");
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
                City city = new City(cityName);
                city.loadMuseums(); // Load museums for this city
                cities.add(city);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }

    public List<Museum> getMuseums() {
        return new ArrayList<>(museums);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Load museums for this city
    private void loadMuseums() {
        String query = "SELECT * FROM Museum WHERE Museum_city = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:/C://Users//Αριστέα//Downloads//artscribe (1).db");
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, this.name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String museumName = resultSet.getString("Museum_name");
                Museum museum = new Museum(museumName);
                museums.add(museum);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
