import java.sql.*;
public class MainPatternDemo {

    public static void main(String[] args) {

        // Singleton Test
        Connection c1 = DBConnection.getConnection();
        Connection c2 = DBConnection.getConnection();

        if (c1 == c2) {
            System.out.println("Singleton working");
        }

        // Factory Test
        User u1 = UserFactory.getUser("passenger", "Santhosh");
        User u2 = UserFactory.getUser("admin", "Manager");

        u1.role();
        u2.role();

        // Observer Test
        BookingSystem b = new BookingSystem();

        PassengerObserver p1 = new PassengerObserver("Santhosh");
        PassengerObserver p2 = new PassengerObserver("Sara");

        b.addObserver(p1);
        b.addObserver(p2);

        b.confirmBooking();
    }
}