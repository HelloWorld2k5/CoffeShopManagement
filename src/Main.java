
// File Main chạy App

import java.sql.Connection;
import com.coffeeshop.config.DatabaseConnection;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Coffe Shop Management");

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            System.out.println("Success");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
