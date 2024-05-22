public class ScanQR {
    private String description;  // Description linked to the QR code
    private Exhibit exhibit;     // The exhibit linked to the QR code
    private User user;           // The user who scanned the QR code

    // Constructor
    public ScanQR(User user) {
        this.user = user;
    }

    // Method to simulate scanning a QR code
    public void scanCode(String codeData, Exhibit exhibit) {
        this.exhibit = exhibit;  // Linking the exhibit associated with the QR code
        this.description = decodeQRCode(codeData);
        System.out.println("User " + user.getName() + " scanned QR Code at " + exhibit.getDescription() + ": " + this.description);
    }

    // Simulate decoding the QR code - In practice, this would use a QR decoding library
    private String decodeQRCode(String codeData) {
        // Return the exhibit's detailed description or some specific info related to the QR code
        return exhibit.getDescription();
    }

    // Getters and setters
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Exhibit getExhibit() {
        return exhibit;
    }

    public void setExhibit(Exhibit exhibit) {
        this.exhibit = exhibit;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}