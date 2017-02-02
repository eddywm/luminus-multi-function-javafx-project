package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by eddylloyd on 10/24/16.
 */
public class ConnectionClass {

    static Connection conn = null;
    static String url = "jdbc:mysql://localhost/luminus";
    static String username = "root";
    static String password = "password";

    public static Connection Connector() {

        if (conn == null) {

            try {
                Class.forName("com.mysql.jdbc.Driver");

                conn = DriverManager.getConnection(url, username, password);


            } catch (SQLException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }


        }
        return conn;
    }





}
