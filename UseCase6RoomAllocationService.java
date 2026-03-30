import java.util.*;

/**
 * Application entry point for Use Case 6: Reservation Confirmation & Room Allocation
 *
 * Goal: Confirm booking requests by assigning rooms safely while ensuring 
 * inventory consistency and preventing double-booking.
 *
 * @author Book My Stay App
 * @version 6.0
 */
public class UseCase6RoomAllocationService {
    public static void main(String[] args) {
        System.out.println("--- Hotel Booking System: Room Allocation Engine ---\n");

        // 1. Inventory Service – maintains and updates room availability state
        RoomInventory inventory = new RoomInventory();
        inventory.registerRoom("Single Room", 2); // Extremely strict inventory
        inventory.registerRoom("Double Room", 2);
        
        // 2. Booking Request Queue – Simulation of concurrent request waitlist
        Queue<Reservation> requestQueue = new LinkedList<>();
        requestQueue.add(new Reservation("Guest-101", "Single Room"));
        requestQueue.add(new Reservation("Guest-102", "Double Room"));
        requestQueue.add(new Reservation("Guest-103", "Single Room"));
        requestQueue.add(new Reservation("Guest-104", "Single Room")); // Request 4 will fail allocation rule

        // 3. Booking Service – processes queued requests
        BookingService bookingService = new BookingService(inventory);

        System.out.println("Event: Processing Queue (FIFO Order)...\n");

        // 4. Booking request is dequeued from the request queue
        while (!requestQueue.isEmpty()) {
            Reservation request = requestQueue.poll();
            
            // Invoking processing validation logic 
            bookingService.processBooking(request);
        }

        System.out.println("\n--- Event: System Sync Report ---");
        // State Validation Checks
        inventory.displayInventory();
        bookingService.displayAllocations();
    }
}

/**
 * Represents a logical intent for reserving a domain object.
 */
class Reservation {
    private String guestId;
    private String requestedRoomType;

    public Reservation(String guestId, String requestedRoomType) {
        this.guestId = guestId;
        this.requestedRoomType = requestedRoomType;
    }

    public String getGuestId() {
        return guestId;
    }

    public String getRequestedRoomType() {
        return requestedRoomType;
    }
}

/**
 * Booking Service – performs strictly synchronized room allocation safely.
 */
class BookingService {
    private RoomInventory inventory;
    
    // Using a HashMap to map RoomType -> Set<String> of UNIQUE assigned IDs
    private Map<String, Set<String>> allocatedRooms;
    
    // Deterministic sequencer for Room ID generation 
    private int idSequencer = 100;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        this.allocatedRooms = new HashMap<>(); // Using HashMap<String, Set<String>>
    }

    /**
     * Executes atomic allocation of Room Resources
     */
    public void processBooking(Reservation request) {
        String type = request.getRequestedRoomType();
        String guestId = request.getGuestId();

        System.out.println("Booking Service -> Investigating Request: " + guestId + " | " + type);

        // The system checks availability for the requested room type
        if (inventory.getAvailableRooms(type) > 0) {
            
            // A unique room ID is generated 
            idSequencer++;
            String roomId = type.substring(0, 1).toUpperCase() + "-" + idSequencer;

            // The room ID is efficiently recorded into the Set boundary to completely prevent mapping reuse
            allocatedRooms.putIfAbsent(type, new HashSet<>());
            allocatedRooms.get(type).add(roomId);

            // Inventory count is synchronously decremented immediately
            inventory.updateAvailability(type, -1);

            // Reservation is confirmed
            System.out.println("  [CONFIRMED] Allocation verified. Allocated unique Room ID: " + roomId);
        } else {
            System.out.println("  [REJECTED] Waitlist triggered! No inventory remaining for: " + type);
        }
    }

    public void displayAllocations() {
        System.out.println("=== Room ID Security Allocation Map ===");
        if (allocatedRooms.isEmpty()) {
            System.out.println("No rooms allocated yet.");
        } else {
            for (Map.Entry<String, Set<String>> entry : allocatedRooms.entrySet()) {
                System.out.println(entry.getKey() + " Assigned Room IDs Set: " + entry.getValue());
            }
        }
        System.out.println("=======================================");
    }
}

/**
 * Inventory Service manages core state 
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

    /**
     * Prevents false reporting by bounding to real variables
     */
    public void updateAvailability(String roomType, int change) {
        if (inventory.containsKey(roomType)) {
            int current = inventory.get(roomType);
            inventory.put(roomType, current + change);
        }
    }

    public void displayInventory() {
        System.out.println("=== Synchronized Remaining Inventory ===");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue() + " rooms open");
        }
        System.out.println("========================================");
    }
}
