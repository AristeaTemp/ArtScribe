import java.time.LocalDateTime;
import javax.swing.*;

public class Reservation {
    private LocalDateTime dateTime;
    private double price;
    private Museum museum;
    private User user;

    public Reservation(LocalDateTime dateTime, double price, Museum museum, User user) {
        this.dateTime = dateTime;
        this.price = price;
        this.museum = museum;
        this.user = user;
    }

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

    public void makeReservationAsync() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Simulate database operation for making a reservation
                // You would typically perform database operations here
                // For demonstration, let's just print the reservation details
                System.out.println("Making a reservation for user: " + user.getName() +
                                   ", Museum: " + museum.getName() +
                                   ", Date and Time: " + dateTime.toString() +
                                   ", Price: $" + price);
                // Simulate saving the reservation in the user's reservations
                user.addReservation(Reservation.this); // Add this reservation to the user's reservations
                System.out.println("Reservation saved for user: " + user.getName());
                return null;
            }
        };
        worker.execute();
    }
}
