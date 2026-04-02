import java.util.*;

public class BookingSystem {

    List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer o) {
        observers.add(o);
    }

    public void confirmBooking() {
        notifyObservers("Booking Confirmed");
    }

    public void cancelBooking() {
        notifyObservers("Booking Cancelled");
    }

    public void notifyObservers(String msg) {
        for (Observer o : observers) {
            o.update(msg);
        }
    }
}