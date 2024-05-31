package net.codejava;



public class Reservation {
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
}
