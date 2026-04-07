import java.io.*;
import java.util.*;
class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}
class RoomInventory {
    private Map<String, Integer> rooms = new HashMap<>();

    public RoomInventory() {
        rooms.put("Single", 5);
        rooms.put("Double", 3);
        rooms.put("Suite", 2);
    }

    public Map<String, Integer> getRooms() {
        return rooms;
    }

    public void setRoom(String type, int count) {
        rooms.put(type, count);
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
        System.out.println("\nCurrent Inventory:");
        for (String type : rooms.keySet()) {
            System.out.println(type + ": " + rooms.get(type));
        }
    }
}

class FilePersistenceService {
    public void saveInventory(RoomInventory inventory, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, Integer> entry : inventory.getRooms().entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }
            System.out.println("Inventory saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving inventory.");
        }
    }
    public void loadInventory(RoomInventory inventory, String filePath) {
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("System Recovery");
            System.out.println("No valid inventory data found. Starting fresh.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            inventory.getRooms().clear();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    inventory.setRoom(parts[0], Integer.parseInt(parts[1]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading inventory.");
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
public class HotelBookingApp {

    public static void main(String[] args) {

        String filePath = "inventory.txt";

        RoomInventory inventory = new RoomInventory();
        RoomAllocationService service = new RoomAllocationService();
        FilePersistenceService persistence = new FilePersistenceService();
        persistence.loadInventory(inventory, filePath);
        inventory.printInventory();

        service.allocateRoom(new Reservation("Abhi", "Single"), inventory);
        service.allocateRoom(new Reservation("Vanmathi", "Double"), inventory);
        service.allocateRoom(new Reservation("Kural", "Suite"), inventory);
        service.allocateRoom(new Reservation("Subha", "Single"), inventory);
        persistence.saveInventory(inventory, filePath);
    }
}