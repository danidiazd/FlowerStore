package Connections;

import InputControl.InputControl;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;

import java.sql.*;

public class MySQLConnection {

    public Connection sqlDatabase;

    public MySQLConnection() {
        try {
            this.sqlDatabase = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/floresPaquitaSL", "root", "gilipichi");
            System.out.println("Connection successful");
        } catch (SQLException e) {
            System.err.println("An error occurred while trying to access MySQL Database");
            e.printStackTrace();
        }
    }

    //idea de realizar la conexión a través de un bloque estático:
    /*
    static {
        try {
            sqlDatabase = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/flowerShop", "root", "gilipichi");
        } catch (SQLException e) {
            System.err.println("An error occurred while trying to access MySQL Database");
            e.printStackTrace();
        }
    } */

    private void disconnectMySQL() {
        if (sqlDatabase != null) {
            try {
                sqlDatabase.close();
            } catch (SQLException e) {
                System.err.println("An error occurred when disconnecting from MySQL Database");
            }
        }
    }

    public Connection getSqlDatabase() {
        return sqlDatabase;
    }
}

