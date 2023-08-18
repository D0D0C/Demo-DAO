package org.example.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseManager {

    public class DatabaseManager {

        public static Connection getPostgreSQLConnection() throws SQLException {
            String url = "jdbc:postgresql://localhost:5432/demo_JDBC";
            String user = "postgres";
            String password = "0000";

            Connection connection = DriverManager.getConnection(url,user,password);

            return connection;
        }
    }
}
