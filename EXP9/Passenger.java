public class Passenger extends User {

    public Passenger(String name) {
        this.name = name;
    }

    public void role() {
        System.out.println(name + " is Passenger");
    }
}