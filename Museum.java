
package net.codejava;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Museum {
    private static final String DB_URL = "jdbc:sqlite:/C://Users//Αριστέα//Downloads//artscribe (1).db";
    
    private String museumName;
    private String workHours;
    private String address;
    private String category;
    private double ticketPrice;
    private String keyWord;
    private String TrafficInfo;

    public Museum(String museumName, String workHours, String address, String category, double ticketPrice, String keyWord,String TrafficInfo) {
        this.museumName = museumName;
        this.workHours = workHours;
        this.address = address;
        this.category = category;
        this.ticketPrice = ticketPrice;
        this.keyWord = keyWord;
        this.TrafficInfo = TrafficInfo;
    }

 
    public static Museum getMuseumByNameFromDatabase(String museumName) {
        Museum museum = null;
        String query = "SELECT * FROM Museum WHERE name = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, museumName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String workHours = resultSet.getString("work_hours");
                String address = resultSet.getString("address");
                String category = resultSet.getString("category");
                double ticketPrice = resultSet.getDouble("ticket_price");
                String keyWord = resultSet.getString("key_word");
                String trafficInfo = resultSet.getString("traffic_info");

                museum = new Museum(museumName, workHours, address, category, ticketPrice, keyWord, trafficInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return museum;
    }


    public static List<Museum> getMuseumsByKeywordFromDatabase(String keyword) {
        List<Museum> museums = new ArrayList<>();
        String query = "SELECT * FROM Museum WHERE key_word LIKE ?";

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:/C://Users//Αριστέα//Downloads//artscribe (1).db");
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "%" + keyword + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String museumName = resultSet.getString("name");
                String workHours = resultSet.getString("work_hours");
                String address = resultSet.getString("address");
                String category = resultSet.getString("category");
                double ticketPrice = resultSet.getDouble("ticket_price");
                String keyWord = resultSet.getString("key_word");
                String TrafficInfo = resultSet.getString("TrafficInfo");

                Museum museum = new Museum(museumName, workHours, address, category, ticketPrice, keyWord,TrafficInfo);
                museums.add(museum);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return museums;
    }

    private Museum getMuseumById(int museumId) {
        Museum museum = null;
        try {
            Connection con = DriverManager.getConnection("jdbc:sqlite:/C://Users//Αριστέα//Downloads//artscribe (1).db");
            String sql = "SELECT * FROM Museum WHERE Id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, museumId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Retrieve museum details from the database
                String name = rs.getString("Name");
                String workHours = rs.getString("WorkHours");
                String address = rs.getString("Address");
                String category = rs.getString("Category");
                double ticketPrice = rs.getDouble("TicketPrice");
                String keyWord = rs.getString("KeyWord");
                museum = new Museum(museumName, workHours, address, category, ticketPrice, keyWord,TrafficInfo);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return museum;
    }
    
    // Getters and setters
    public String getMuseumName() {
        return museumName;
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

    public void setMuseumName(String museumName) {
        this.museumName = museumName;
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
    public String getTrafficInfo() {
        return TrafficInfo;
    }

    public void setTrafficInfo(String TrafficInfo) {
    	this.TrafficInfo = TrafficInfo;
    }
    
    
}
