import javax.swing.*;
import java.awt.event.*;

public class ScanQR {
    private String description;  // Description linked to the QR code
    private Exhibit exhibit;     // The exhibit linked to the QR code
    private User user;           // The user who scanned the QR code
    private JFrame frame;
    private JButton scanButton;

    // Constructor
    public ScanQR(User user) {
        this.user = user;

        frame = new JFrame("QR Code Scanner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        scanButton = new JButton("Scan QR Code");
        scanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Museum museum = new Museum("Example Museum"); 
                scanCode("QR_CODE_DATA_HERE", new Exhibit(1, "Example Exhibit", museum)); 
            }
        });

        frame.getContentPane().add(scanButton);
        frame.pack();
        frame.setVisible(true);
    }

    // Method to simulate scanning a QR code
    public void scanCode(String codeData, Exhibit exhibit) {
        this.exhibit = exhibit;  // Linking the exhibit associated with the QR code
        this.description = decodeQRCode(codeData);
        System.out.println("User " + user.getName() + " scanned QR Code at " + exhibit.getDescription() + ": " + this.description);
    }

    
    private String decodeQRCode(String codeData) {
        // Return the exhibit's detailed description 
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

    public static void main(String[] args) {
        User user = new User("John");
        new ScanQR(user);
    }
}


class Exhibit {
    private int number;
    private String description;
    private Museum museum;

    public Exhibit(int number, String description, Museum museum) {
        this.number = number;
        this.description = description;
        this.museum = museum;
    }

    public String getDescription() {
        return description;
    }
}

class Museum {
    private String name;

    public Museum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class User {
    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
