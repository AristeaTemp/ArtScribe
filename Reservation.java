package net.codejava;

import java.sql.*;
import javax.swing.*;

public class Reservation {
    private String name;
    private String surname;
    private String phone;
    private String email;
    private String dateTime;
    private double ticketPrice;
    private String museum;

    public Reservation(String name, String surname, String phone, String email, String dateTime, double ticketPrice, String museum) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.dateTime = dateTime;
        this.ticketPrice = ticketPrice;
        this.museum = museum;
    }

    // Getters and setters for other fields

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getMuseum() {
        return museum;
    }

    public void setMuseum(String museum) {
        this.museum = museum;
    }
   
   
}
