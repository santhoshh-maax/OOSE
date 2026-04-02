public class PassengerObserver implements Observer {

    String name;

    public PassengerObserver(String name) {
        this.name = name;
    }

    public void update(String message) {
        System.out.println("Notification to " + name + ": " + message);
    }
}