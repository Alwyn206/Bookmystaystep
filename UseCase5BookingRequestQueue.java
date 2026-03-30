import java.util.LinkedList;
import java.util.Queue;

/**
 * Application entry point for Use Case 5: Booking Request (First-Come-First-Served)
 *
 * Goal: Handle multiple booking requests fairly by introducing a request intake 
 * mechanism that preserves arrival order.
 *
 * @author Book My Stay App
 * @version 5.0
 */
public class UseCase5BookingRequestQueue {
    public static void main(String[] args) {
        System.out.println("--- Hotel Booking System: Booking Request Intake ---\n");

        // The system initializes the booking queue component
        BookingRequestQueue queue = new BookingRequestQueue();

        // Simulate incoming booking requests during peak demand
        System.out.println("Event: Receiving simultaneous booking requests...\n");

        Reservation req1 = new Reservation("Guest-101", "Single Room"); // First arrived
        queue.addRequest(req1);

        Reservation req2 = new Reservation("Guest-102", "Suite Room");  // Arrived second
        queue.addRequest(req2);

        Reservation req3 = new Reservation("Guest-103", "Single Room"); // Arrived third
        queue.addRequest(req3);

        System.out.println("\nEvent Check: All requests submitted to Central Booking.");
        
        // Show that they wait securely for processing
        queue.displayQueue();
        
        System.out.println("\nSystem Notice: No inventory mutation has occurred yet. Safe intake verified.");
    }
}

/**
 * Represents a guest's intent to book a room.
 * Encapsulating request data to decouple Intake from Allocation logic.
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

    @Override
    public String toString() {
        return "Reservation[Guest: " + guestId + ", RoomType: " + requestedRoomType + "]";
    }
}

/**
 * Manages and orders incoming booking requests.
 * Uses a Queue interface implemented as a LinkedList.
 */
class BookingRequestQueue {
    // Queue Data Structure - FIFO ensures earliest requests go first!
    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        // LinkedList implements Queue naturally preserving insertion order
        this.requestQueue = new LinkedList<>();
    }

    /**
     * Process an incoming request by submitting it to the tail sequence.
     * @param reservation Guest intent data
     */
    public void addRequest(Reservation reservation) {
        requestQueue.add(reservation);
        System.out.println("  -> Processed: Request for " + reservation.getRequestedRoomType() + 
                           " from " + reservation.getGuestId() + " secured in Queue.");
    }

    /**
     * Loops through queue outputting strictly unmutated sequence data
     */
    public void displayQueue() {
        System.out.println("=== Booking Queue Status (" + requestQueue.size() + " total requests waiting) ===");
        int position = 1;
        for (Reservation res : requestQueue) {
            System.out.println(position + ". " + res.toString());
            position++;
        }
        System.out.println("===============================================================");
    }
}
