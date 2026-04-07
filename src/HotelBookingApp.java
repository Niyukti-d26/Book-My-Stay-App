import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

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

class RoomInventory {
    private Set<String> availableRoomTypes;

    public RoomInventory() {
        availableRoomTypes = new HashSet<>();
        availableRoomTypes.add("Single");
        availableRoomTypes.add("Double");
        availableRoomTypes.add("Suite");
    }

    public boolean isRoomTypeAvailable(String roomType) {
        return availableRoomTypes.contains(roomType); // case-sensitive
    }
}

class ReservationValidator {
    public void validate(String guestName, String roomType, RoomInventory inventory)
            throws InvalidBookingException {

        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        if (roomType == null || roomType.trim().isEmpty()) {
            throw new InvalidBookingException("Room type cannot be empty.");
        }

        if (!inventory.isRoomTypeAvailable(roomType)) {
            throw new InvalidBookingException("Invalid room type selected.");
        }
    }
}

public class HotelBookingApp {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        RoomInventory inventory = new RoomInventory();
        ReservationValidator validator = new ReservationValidator();

        try {
            System.out.println("Booking Validation");

            System.out.print("Enter guest name: ");
            String guestName = sc.nextLine();

            System.out.print("Enter room type (Single/Double/Suite): ");
            String roomType = sc.nextLine();

            validator.validate(guestName, roomType, inventory);

            Reservation r = new Reservation(guestName, roomType);
            System.out.println("Booking successful for " + r.getGuestName());

        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        }

        sc.close();
    }
}