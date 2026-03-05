public class HotelBookingApp {

    public static void main(String[] args) {

        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        System.out.println("Single Room Details:");
        single.displayRoomDetails();

        System.out.println("\nDouble Room Details:");
        doubleRoom.displayRoomDetails();

        System.out.println("\nSuite Room Details:");
        suite.displayRoomDetails();
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