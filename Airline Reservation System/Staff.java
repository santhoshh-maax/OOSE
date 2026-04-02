import java.util.Vector;

public class Staff extends User {
    private int staffId;
    public String name;
    public String email;

    public Vector<Booking> bookings;

    public Staff() {
        bookings = new Vector<>();
    }

    public void login() {
        System.out.println("Staff logged in");
    }

    public void checkPassenger() {
        System.out.println("Checking passenger details");
    }

    public void assistBooking() {
        System.out.println("Assisting booking process");
    }
}