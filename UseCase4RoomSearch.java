import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Application entry point for Use Case 4: Room Search & Availability Check
 *
 * @author Book My Stay App
 * @version 4.0
 */
public class UseCase4RoomSearch {
    public static void main(String[] args) {
        System.out.println("--- Hotel Booking System: System Boot & Search Test ---\n");

        // 1. System initializes the inventory component
        RoomInventory inventory = new RoomInventory();

        // 2. Initialize room objects representing what a room is (Domain Objects)
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // 3. Register availability into centralized HashMap
        inventory.registerRoom(singleRoom.getRoomType(), 5);
        // Setting Double Room to 0 to demonstrate the filtering validation logic
        inventory.registerRoom(doubleRoom.getRoomType(), 0);
        inventory.registerRoom(suiteRoom.getRoomType(), 2);

        // Prepare our list of all recognized domain rooms
        List<Room> allRooms = new ArrayList<>();
        allRooms.add(singleRoom);
        allRooms.add(doubleRoom);
        allRooms.add(suiteRoom);

        // 4. Guest initiates a room search request via SearchService
        System.out.println("Event: Guest initiated a Search request...");
        SearchService searchService = new SearchService(inventory);

        // Search operation (read-only)
        searchService.searchAvailableRooms(allRooms);

        // 5. Verifying system state remained unchanged
        System.out.println("\nPost-Search Inventory Check (State Unchanged):");
        inventory.displayInventory();
    }
}

/**
 * Handles read-only access to inventory and room information.
 */
class SearchService {
    private RoomInventory inventory;

    public SearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void searchAvailableRooms(List<Room> rooms) {
        System.out.println("--- Room Availability Search Results ---");
        boolean found = false;

        for (Room room : rooms) {
            String roomType = room.getRoomType();
            int availableCount = inventory.getAvailableRooms(roomType);

            // Validation Logic - Display only room types with availability > 0
            if (availableCount > 0) {
                found = true;
                System.out.println("\nAVAILABLE [" + availableCount + " left] ->");
                room.displayDetails();
            }
        }

        if (!found) {
            System.out.println("No rooms are currently available.");
        }
        System.out.println("----------------------------------------");
    }
}

/**
 * Centralized room inventory management using HashMap.
 */
class RoomInventory {
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
