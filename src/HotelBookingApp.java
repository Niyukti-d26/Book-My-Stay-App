import java.util.HashMap;
import java.util.Map;

public class HotelBookingApp {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        HotelBookingApp app = new HotelBookingApp();
        app.searchAvailableRooms(inventory, singleRoom, doubleRoom, suiteRoom);
    }

    public void searchAvailableRooms(RoomInventory inventory, Room SingleRoom, Room DoubleRoom, Room SuiteRoom) {
        Map<String, Integer> availability = inventory.getRoomAvailability();
        if (availability.get("Single") > 0) {
            System.out.println("Single Room Details:");
            SingleRoom.displayRoomDetails();
            System.out.println();
        }

        if (availability.get("Double") > 0) {
            System.out.println("Double Room Details:");
            DoubleRoom.displayRoomDetails();
            System.out.println();
        }

        if (availability.get("Suite") > 0) {
            System.out.println("Suite Room Details:");
            SuiteRoom.displayRoomDetails();
            System.out.println();
        }
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

abstract class Room {
    protected int numberOfBeds;
    protected int squareFeet;
    protected int pricePerNight;

    public Room(int numberOfBeds, int squareFeet, int pricePerNight) {
        this.numberOfBeds = numberOfBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
    }

    public void displayRoomDetails() {
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + squareFeet + " sq.ft");
        System.out.println("Price per night: " + pricePerNight);
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 250, 1500);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 400, 2500);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 750, 5000);
    }
}