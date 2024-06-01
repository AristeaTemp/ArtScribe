package net.codejava;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private String surname;
    private String phone;
    private String email;
    private String password;
    private List<Reservation> reservations;

    public User(String name, String surname, String phone, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.reservations = new ArrayList<>();
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

    public String getPhone() {
        return phone;
    }

    public void setPhoneNumber(String phone) {
        this.phone = phone;
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

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }
    
    public static class Ticket {
        private String firstName;
        private String lastName;
        private String phone;
        private String date;
        private String time;
        private double price;

        public Ticket(String firstName, String lastName, String phone, String date, String time, double price) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.phone = phone;
            this.date = date;
            this.time = time;
            this.price = price;
        }

        // Getters and setters
        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }
    
}
