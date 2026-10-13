package net.codejava;

public class Exhibit {
    private int number;
    private String description;
    private Museum museum;  // Reference to the Museum to maintain a link between an exhibit and its museum

    // Constructor
    public Exhibit(int number, String description, Museum museum) {
        this.number = number;
        this.description = description;
        this.museum = museum;
    }

    // Getters
    public int getNumber() {
        return number;
    }

    public String getDescription() {
        return description;
    }

    public Museum getMuseum() {
        return museum;
    }

    // Setters
    public void setNumber(int number) {
        this.number = number;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMuseum(Museum museum) {
        this.museum = museum;
    }
    public String toString() {
        return "Exhibit{" +
                "number=" + number +
                ", description='" + description + '\'' +
                ", museum=" + museum +
                '}';
    }
    
    
}
