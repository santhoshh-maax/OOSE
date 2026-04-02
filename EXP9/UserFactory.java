public class UserFactory {

    public static User getUser(String type, String name) {

        if (type.equalsIgnoreCase("passenger"))
            return new Passenger(name);

        else if (type.equalsIgnoreCase("staff"))
            return new Staff(name);

        else if (type.equalsIgnoreCase("admin"))
            return new Admin(name);

        return null;
    }
}