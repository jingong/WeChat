package db;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import tables.MemberManager;

/**
 *
 * @author jiajingong
 */
public class Main {

    public static void main(String[] args) {
        try {
            int n = MemberManager.Login("sa", "123");
            System.out.println("" + n);
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
