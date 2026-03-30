/**
 * Application entry point for Use Case 2: Basic Room Types & Static Availability
 * 
 * Goal: Introduce object modeling through inheritance and abstraction
 * before introducing data structures, allowing students to focus on domain 
 * design rather than optimization.
 *
 * @author Book My Stay App
 * @version 2.0
 */
public class UseCase2RoomInitialization {
    public static void main(String[] args) {
        // Initialize room objects representing different room types
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Static availability representation using simple variables
        int singleRoomAvailability = 5;
        int doubleRoomAvailability = 3;
        int suiteRoomAvailability = 2;

        System.out.println("===========================================");
        System.out.println("    Use Case 2: Room Types & Availability  ");
        System.out.println("===========================================\n");

        // Display Room details and availability information
        singleRoom.displayDetails();
        System.out.println("Available: " + singleRoomAvailability);
        System.out.println("-------------------------------------------");

        doubleRoom.displayDetails();
        System.out.println("Available: " + doubleRoomAvailability);
        System.out.println("-------------------------------------------");

        suiteRoom.displayDetails();
        System.out.println("Available: " + suiteRoomAvailability);
        System.out.println("-------------------------------------------");
    }
}

/**
 * Abstract class representing a general Room concept.
 */
abstract class Room {
    private String roomType;
    private int numberOfBeds;
    private double size;
    private double price;

    public Room(String roomType, int numberOfBeds, double size, double price) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.size = size;
        this.price = price;
    }

    public String getRoomType() { return roomType; }
    public int getNumberOfBeds() { return numberOfBeds; }
    public double getSize() { return size; }
    public double getPrice() { return price; }

    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + size + " sq.ft");
        System.out.println("Price: $" + price);
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 250.0, 100.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 400.0, 150.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 650.0, 250.0);
    }
}
