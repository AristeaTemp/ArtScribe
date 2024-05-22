import java.util.ArrayList;
import java.util.List;

public class Museum {
    private String name;
    private String workHours;
    private String address;
    private String category;
    private double ticketPrice;
    private List<String> keyWords;
    private List<Exhibit> exhibits;
    private List<Reservation> reservations;

    // Constructor
    public Museum(String name, String workHours, String address, String category, double ticketPrice) {
        this.name = name;
        this.workHours = workHours;
        this.address = address;
        this.category = category;
        this.ticketPrice = ticketPrice;
        this.keyWords = new ArrayList<>();
        this.exhibits = new ArrayList<>();
        this.reservations = new ArrayList<>();
    }

    // Getters and setters for fields
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkHours() {
        return workHours;
    }

    public void setWorkHours(String workHours) {
        this.workHours = workHours;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public List<String> getKeyWords() {
        return new ArrayList<>(keyWords);
    }

    public void addKeyWord(String keyWord) {
        this.keyWords.add(keyWord);
    }

    public void removeKeyWord(String keyWord) {
        this.keyWords.remove(keyWord);
    }

    public List<Exhibit> getExhibits() {
        return new ArrayList<>(exhibits);
    }

    public List<Reservation> getReservations() {
        return new ArrayList<>(reservations);
    }
}