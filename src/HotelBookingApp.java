import java.util.*;
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}
class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
    }

    public Reservation getNextRequest() {
        return requestQueue.poll();
    }

    public boolean hasPendingRequests() {
        return !requestQueue.isEmpty();
    }
}
class RoomInventory {

    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        initializeInventory();
    }

    private void initializeInventory() {
        roomAvailability = new HashMap<>();

        roomAvailability.put("Single", 10);
        roomAvailability.put("Double", 6);
        roomAvailability.put("Suite", 3);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }
}
class RoomAllocationService {

    private Set<String> allocatedRoomIds;
    private Map<String, Set<String>> assignedRoomsByType;

    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
    }

    public void allocateRoom(Reservation reservation, RoomInventory inventory) {

        String roomType = reservation.getRoomType();
        Map<String, Integer> availability = inventory.getRoomAvailability();

        if (availability.get(roomType) > 0) {

            String roomId = generateRoomId(roomType);

            allocatedRoomIds.add(roomId);

            assignedRoomsByType
                    .computeIfAbsent(roomType, k -> new HashSet<>())
                    .add(roomId);

            availability.put(roomType, availability.get(roomType) - 1);

            System.out.println("Booking confirmed for Guest : "
                    + reservation.getGuestName()
                    + " , Room id : "
                    + roomId);
        }
        else {
            System.out.println("No rooms available for " + roomType);
        }
    }

    private String generateRoomId(String roomType) {

        Set<String> rooms =
                assignedRoomsByType.getOrDefault(roomType, new HashSet<>());

        int nextId = rooms.size() + 1;

        return roomType + "-" + nextId;
    }
}
class Service {

    private String serviceName;
    private double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}
class AddOnServiceManager {

    private Map<String, List<Service>> servicesByReservation;

    public AddOnServiceManager() {
        servicesByReservation = new HashMap<>();
    }

    public void addService(String reservationId, Service service) {

        servicesByReservation
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
    }

    public double calculateTotalServiceCost(String reservationId) {

        double total = 0;

        List<Service> services = servicesByReservation.get(reservationId);

        if (services != null) {
            for (Service s : services) {
                total += s.getCost();
            }
        }

        return total;
    }
}
public class HotelBookingApp {

    public static void main(String[] args) {

        BookingRequestQueue bookingRequestQueue =
                new BookingRequestQueue();

        RoomInventory inventory =
                new RoomInventory();

        RoomAllocationService allocationService =
                new RoomAllocationService();

        AddOnServiceManager serviceManager =
                new AddOnServiceManager();

        Reservation r1 = new Reservation("Abhi", "Single");
        Reservation r2 = new Reservation("Subha", "Single");
        Reservation r3 = new Reservation("Vanmathi", "Suite");

        bookingRequestQueue.addRequest(r1);
        bookingRequestQueue.addRequest(r2);
        bookingRequestQueue.addRequest(r3);

        while (bookingRequestQueue.hasPendingRequests()) {

            Reservation current =
                    bookingRequestQueue.getNextRequest();

            allocationService.allocateRoom(current, inventory);
        }

        String reservationId = "Single-1";

        Service breakfast = new Service("Breakfast", 500);
        Service airportPickup = new Service("Airport Pickup", 1000);

        serviceManager.addService(reservationId, breakfast);
        serviceManager.addService(reservationId, airportPickup);

        System.out.println("\nAdd-On Service Selection");
        System.out.println("Reservation ID: " + reservationId);

        double totalCost =
                serviceManager.calculateTotalServiceCost(reservationId);

        System.out.println("Total Add-On Cost: " + totalCost);
    }
}