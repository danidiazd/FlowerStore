package Connections;

import java.sql.*;
public class MySQLConnection {

    public static Connection connectMySQL() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/flowerShop", "root", "gilipichi");
        } catch (SQLException e) {
            System.err.println("An error occurred when trying to access the data");
            e.printStackTrace();
        }


        return connection;
    }

    //TODO Manera mas fina de sintaxis para añadir productos a la base de datos
    public static void createTable(String tableName) throws SQLException {
        try (Connection connection = connectMySQL()) {
            Statement statement = connection.createStatement();
            String createTableQuery = "";
            switch (tableName) {
                case "tree":
                    createTableQuery = "CREATE TABLE IF NOT EXISTS tree (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100) NOT NULL, height INT NOT NULL, price INT NOT NULL)";
                    break;
                case "flower":
                    createTableQuery = "CREATE TABLE IF NOT EXISTS flower (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100) NOT NULL, color VARCHAR(50) NOT NULL, price INT NOT NULL)";
                    break;
                case "decoration":
                    createTableQuery = "CREATE TABLE IF NOT EXISTS decoration (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100) NOT NULL, material VARCHAR(50) NOT NULL, price INT NOT NULL)";
                    break;
            }
            statement.execute(createTableQuery);
        }
    }


    private static void createFlowerShop(String name) {

    }

    public static void addTree(String name, String height, int price) throws SQLException {
        createTable("tree");
        Statement statement = connectMySQL().createStatement();
        statement.executeUpdate("INSERT INTO tree (name, height, price) VALUES ('" + name + "'," + height + "," + price + ")");
    }

    private static void addFlower(String name, String color, int price) throws SQLException {
        createTable("flower");
        Statement statement = connectMySQL().createStatement();
        statement.executeUpdate("INSERT INTO flower (name, color, price) VALUES ('" + name + "'," + color + "," + price + ")");
    }

    private static void addDecoration(String name, String material, int price) throws SQLException {
        createTable("decoration");
        Statement statement = connectMySQL().createStatement();
        statement.executeUpdate("INSERT INTO decoration (name, material, price) VALUES ('" + name + "'," + material + "," + price + ")");
    }

    private static void showProducts() {

    }




}
