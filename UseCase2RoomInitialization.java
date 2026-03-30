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
