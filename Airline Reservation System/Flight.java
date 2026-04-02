import java.util.Vector;

public class Flight {
    private int flightId;
    private String source;
    private String destination;
    private String schedule;
    public Vector<Passenger> passengers;
    public Flight() {
        passengers = new Vector<>();
    }
        public void checkAvailability() {
        System.out.println("Checking seat availability");
    }
        public void showFlightDetails() {
        System.out.println("Displaying flight details");
    }
}