import java.util.ArrayList;
import java.util.List;

public class City {
    private String name;
    private List<Museum> museums;  // List to hold museums in the city

    // Constructor
    public City(String name) {
        this.name = name;
        this.museums = new ArrayList<>();
    }

    // Add a museum to the city
    public void addMuseum(Museum museum) {
        if (!museums.contains(museum)) {
            museums.add(museum);
            System.out.println(museum.getName() + " has been added to " + this.name);
        } else {
            System.out.println("This museum is already registered in " + this.name);
        }
    }

    // Remove a museum from the city
    public void removeMuseum(Museum museum) {
        if (museums.remove(museum)) {
            System.out.println(museum.getName() + " has been removed from " + this.name);
        } else {
            System.out.println("This museum was not found in " + this.name);
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