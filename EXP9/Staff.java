public class Staff extends User {

    public Staff(String name) {
        this.name = name;
    }

    public void role() {
        System.out.println(name + " is Staff");
    }
}