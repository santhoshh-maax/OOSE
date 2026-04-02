
public class Admin extends User {

    public Admin(String name) {
        this.name = name;
    }

    public void role() {
        System.out.println(name + " is Admin");
    }
}