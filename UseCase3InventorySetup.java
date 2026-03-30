import java.util.HashMap;
import java.util.Map;

/**
 * Application entry point for Use Case 3: Centralized Room Inventory Management
 *
 * @author Book My Stay App
 * @version 3.0
 */
public class UseCase3InventorySetup {
    public static void main(String[] args) {
        System.out.println("--- Hotel Booking System: System Boot & Inventory Setup ---\n");

        // The system initializes the inventory component.
        RoomInventory inventory = new RoomInventory();

        // Initialize room objects representing what a room is (Domain Objects)
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Room types are registered with their available counts in the centralized system
        inventory.registerRoom(singleRoom.getRoomType(), 5);
        inventory.registerRoom(doubleRoom.getRoomType(), 3);
        inventory.registerRoom(suiteRoom.getRoomType(), 2);

        // Display basic details of one room to show Separation of Concerns
        System.out.println("Loaded Domain Configurations:");
        singleRoom.displayDetails();
        System.out.println();

        // Retrieving availability from the centralized HashMap
        System.out.println("Initial Availability Check:");
        System.out.println(singleRoom.getRoomType() + " initial inventory: " + inventory.getAvailableRooms(singleRoom.getRoomType()));
        System.out.println();

        // Updates to availability are performed through controlled methods (e.g., booking a room)
        System.out.println("Event: 1 Single Room Booked -> Updating Inventory...");
        inventory.updateAvailability(singleRoom.getRoomType(), -1);
        System.out.println();

        // The current inventory state is displayed when requested
        inventory.displayInventory();
    }
}

/**
 * Centralized room inventory management using HashMap.
 */
class RoomInventory {
    // HashMap to store room availability (O(1) lookup, Single Source of Truth)
    private Map<String, Integer> inventory;

    public RoomInventory() {
        this.inventory = new HashMap<>();
    }

    public void registerRoom(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public int getAvailableRooms(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType, int change) {
        if (inventory.containsKey(roomType)) {
            int current = inventory.get(roomType);
            inventory.put(roomType, current + change);
        } else {
            System.out.println("Error: Room type '" + roomType + "' does not exist in inventory.");
        }
    }

    public void displayInventory() {
        System.out.println("=== Room Inventory Status ===");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue() + " rooms available");
        }
        System.out.println("=============================");
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
