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
