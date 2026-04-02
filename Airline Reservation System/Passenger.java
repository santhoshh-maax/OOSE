import java.util.Vector;

public class Passenger extends User {
    private int passengerId;
    public String name;
    public String email;

    public Vector<Booking> bookings;

    public Passenger() {
        bookings = new Vector<>();
    }

    public void login() {
        System.out.println("Passenger logged in");
    }

    public void searchFlight() {
        System.out.println("Searching flights");
    }

    public void bookTicket() {
        System.out.println("Ticket booked");
    }

    public void cancelTicket() {
        System.out.println("Ticket cancelled");
    }
}