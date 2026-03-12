import java.util.LinkedList;
import java.util.Queue;

class Reservation{
    private String guestName;
    private String roomType;

    public Reservation(String guestName,String roomType){
        this.guestName=guestName;
        this.roomType=roomType;
    }

    public String getGuestName(){return guestName;}
    public String getRoomType(){return roomType;}
}

class BookingRequestQueue{
    private Queue<Reservation> requestQueue;
    public BookingRequestQueue(){requestQueue= new LinkedList<>();}
    public void addRequest(Reservation reservation){requestQueue.offer(reservation);}
    public Reservation getNextRequest(){return requestQueue.poll();}
    public boolean hasPendingRequests(){return !requestQueue.isEmpty();}
}

public class HotelBookingApp{
    public static void main(String[] args){
        BookingRequestQueue bookingRequestQueue= new BookingRequestQueue();
        Reservation r1=new Reservation("Abhi","Single");
        Reservation r2=new Reservation("Subha","Double");
        Reservation r3=new Reservation("Vanmathi","Suite");

        bookingRequestQueue.addRequest(r1);
        bookingRequestQueue.addRequest(r2);
        bookingRequestQueue.addRequest(r3);

        while (bookingRequestQueue.hasPendingRequests()){
            Reservation current = bookingRequestQueue.getNextRequest();
            System.out.println("Processing Booking for Guest: "+current.getGuestName()+", Room Type :"+current.getRoomType());
        }
    }
}