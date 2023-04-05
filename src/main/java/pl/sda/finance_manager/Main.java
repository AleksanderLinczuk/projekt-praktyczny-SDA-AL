package pl.sda.finance_manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static final String JDBC_URL = "jdbc:mysql://localhost:3306/";
    public static final String DB_NAME = "db_name";
    public static final String DB_USER = "db_user";
    public static final String DB_PASSWORD = "db_password";

    public static void main(String[] args) {
        try (final Connection connection = DriverManager.getConnection(JDBC_URL + System.getenv(DB_NAME),
                System.getenv(DB_USER), System.getenv(DB_PASSWORD));){
            System.out.println("Connected to DB!");




        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
