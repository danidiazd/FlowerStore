package Connections;

import java.sql.*;
public class MySQLConnection {
    private Connection mySQLDatabase;
    public MySQLConnection() {
        try {
            mySQLDatabase = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/floresPaquitaSL", "root", "gilipichi");
        } catch (SQLException e) {
            System.err.println("Error establishing the database connection.");
            e.printStackTrace();
        }

    }

    public Connection getMySQLDatabase() {
        return mySQLDatabase;
    }

    private void disconnectMySQL() {

            try {
                if (mySQLDatabase != null) {
                    mySQLDatabase.close();
                }
            }catch(SQLException e){
                System.err.println("An error occurred when disconnecting from the database");
                }
            }
}
