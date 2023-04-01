package org.example;

import java.sql.*;

public class DBUtill {
    private static final String DB_URL ="jdbc:mysql://localhost:3306";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Kamilek44@";

    public static Connection connect() throws SQLException{
        return connect(DB_URL,DB_USER,DB_PASSWORD);
    }

    private static Connection connect(String Url, String User, String Password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(Url, User, Password);
    }catch (SQLException  | ClassNotFoundException throwables){
        throw new RuntimeException(throwables);

    }
    }
    public static void printData(Connection conn,String query, String... columnNames) throws SQLException{
        try (PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();){
            while (resultSet.next()){
                for (String param : columnNames){
                    System.out.println(resultSet.getString(param));
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
