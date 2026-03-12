import java.util.HashMap;
import java.util.Map;

public class HotelBookingApp {

    private Map<String, Room> roomAvailability;
    public HotelBookingApp() {
        initializeInventory();
    }

    private void initializeInventory() {
        roomAvailability = new HashMap<>();

        roomAvailability.put("Single Room", new SingleRoom());
        roomAvailability.put("Double Room", new DoubleRoom());
        roomAvailability.put("Suite Room", new SuiteRoom());
    }

    public Map<String, Room> getRoomAvailability() {
        return roomAvailability;
    }

    public static void main(String[] args) {

        HotelBookingApp inventory = new HotelBookingApp();
        for (Map.Entry<String, Room> entry : inventory.getRoomAvailability().entrySet()) {
            System.out.println(entry.getKey() + " Details:");
            entry.getValue().displayRoomDetails();
            System.out.println();
        }
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