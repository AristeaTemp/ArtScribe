import java.time.LocalDateTime;
import java.util.Set;

public class Reservation {
    private LocalDateTime dateTime;
    private double price;
    private Museum museum;
    private User user;

    // Constructor
    public Reservation(LocalDateTime dateTime, double price, Museum museum, User user) {
        this.dateTime = dateTime;
        this.price = price;
        this.museum = museum;
        this.user = user;
    }

    // Getters
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public double getPrice() {
        return price;
    }

    public Museum getMuseum() {
        return museum;
    }

    public User getUser() {
        return user;
    }

    // Setters
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setMuseum(Museum museum) {
        this.museum = museum;
    }

    public void setUser(User user) {
        this.user = user;
    }
}