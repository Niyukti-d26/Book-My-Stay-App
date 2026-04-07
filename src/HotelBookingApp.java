import java.util.*;
class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public synchronized void addRequest(Reservation reservation) {
        queue.add(reservation);
        notifyAll();
    }

    public synchronized Reservation getRequest() {
        while (queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return queue.poll();
    }
}
class RoomInventory {
    private Map<String, Integer> rooms = new HashMap<>();

    public RoomInventory() {
        rooms.put("Single", 5);
        rooms.put("Double", 3);
        rooms.put("Suite", 2);
    }

    public boolean allocateRoom(String roomType) {
        int available = rooms.getOrDefault(roomType, 0);
        if (available > 0) {
            rooms.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public void printInventory() {
        System.out.println("\nRemaining Inventory:");
        for (String type : rooms.keySet()) {
            System.out.println(type + ": " + rooms.get(type));
        }
    }
}
class RoomAllocationService {
    public void allocateRoom(Reservation reservation, RoomInventory inventory) {
        if (inventory.allocateRoom(reservation.roomType)) {
            System.out.println("Booking confirmed for Guest: "
                    + reservation.guestName + ", Room Type: "
                    + reservation.roomType);
        } else {
            System.out.println("Booking failed for Guest: "
                    + reservation.guestName + " (No "
                    + reservation.roomType + " rooms available)");
        }
    }
}
class ConcurrentBookingProcessor implements Runnable {

    private BookingRequestQueue bookingQueue;
    private RoomInventory inventory;
    private RoomAllocationService allocationService;

    public ConcurrentBookingProcessor(
            BookingRequestQueue bookingQueue,
            RoomInventory inventory,
            RoomAllocationService allocationService) {
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
        this.allocationService = allocationService;
    }

    @Override
    public void run() {
        while (true) {
            Reservation reservation;
            synchronized (bookingQueue) {
                reservation = bookingQueue.getRequest();
            }

            synchronized (inventory) {
                allocationService.allocateRoom(reservation, inventory);
            }
        }
    }
}

public class HotelBookingApp {

    public static void main(String[] args) throws InterruptedException {

        BookingRequestQueue queue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService service = new RoomAllocationService();

        queue.addRequest(new Reservation("Abhi", "Single"));
        queue.addRequest(new Reservation("Vanmathi", "Double"));
        queue.addRequest(new Reservation("Kural", "Suite"));
        queue.addRequest(new Reservation("Subha", "Single"));

        Thread t1 = new Thread(new ConcurrentBookingProcessor(queue, inventory, service));
        Thread t2 = new Thread(new ConcurrentBookingProcessor(queue, inventory, service));
        t1.start();
        t2.start();
        Thread.sleep(2000);
        inventory.printInventory();
        System.exit(0);
    }
}