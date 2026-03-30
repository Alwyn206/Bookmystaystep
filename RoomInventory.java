import java.util.HashMap;
import java.util.Map;

/**
 * Centralized room inventory management using HashMap.
 * Goal: Introduce centralized inventory management by replacing scattered 
 * availability variables with a single, consistent data structure.
 *
 * @author Book My Stay App
 * @version 3.0
 */
public class RoomInventory {
    // HashMap to store room availability (O(1) lookup, Single Source of Truth)
    private Map<String, Integer> inventory;

    /**
     * Initializes the inventory component.
     */
    public RoomInventory() {
        this.inventory = new HashMap<>();
    }

    /**
     * Registers a room type with its initial available count.
     * 
     * @param roomType The logical key representing the room.
     * @param count    The number of available rooms.
     */
    public void registerRoom(String roomType, int count) {
        inventory.put(roomType, count);
    }

    /**
     * Retrieves the current availability of a given room type.
     * 
     * @param roomType The room type to look up.
     * @return The number of available rooms, or 0 if not found.
     */
    public int getAvailableRooms(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    /**
     * Controlled update to room availability.
     * 
     * @param roomType The room type to update.
     * @param change   The positive or negative value to alter inventory by.
     */
    public void updateAvailability(String roomType, int change) {
        if (inventory.containsKey(roomType)) {
            int current = inventory.get(roomType);
            inventory.put(roomType, current + change);
        } else {
            System.out.println("Error: Room type '" + roomType + "' does not exist in inventory.");
        }
    }

    /**
     * Displays the current inventory state.
     */
    public void displayInventory() {
        System.out.println("=== Room Inventory Status ===");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue() + " rooms available");
        }
        System.out.println("=============================");
    }
}
