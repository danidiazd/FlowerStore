package Infrastructure.Connections;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@MySQLConfig(config = "mysql.properties")
public class MySQLConnection {
    private static Connection mySQLDatabase;
    private String url;
    private String username;
    private String password;

    public MySQLConnection() {

        MySQLConfig annotation = MySQLConnection.class.getAnnotation(MySQLConfig.class);
        String configFileName = annotation.config();
        Properties properties = loadProperties(configFileName);


        url = properties.getProperty("mysql.url");
        username = properties.getProperty("mysql.username");
        password = properties.getProperty("mysql.password");
        try {
            mySQLDatabase = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.err.println("Error establishing the database connection.");
            e.printStackTrace();
        }

    }
    private Properties loadProperties(String filename) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename)) {
            if (inputStream != null) {
                Properties properties = new Properties();
                properties.load(inputStream);
                return properties;
            } else {
                throw new IOException("Properties file '" + filename + "' not found.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading MySQL properties file: " + e.getMessage());
        }
    }


    public static Connection getMySQLDatabase() {
        return mySQLDatabase;
    }

    public void disconnectMySQL() {

            try {
                if (mySQLDatabase != null) {
                    mySQLDatabase.close();
                }
            }catch(SQLException e){
                System.err.println("An error occurred when disconnecting from the database");
                }
            }
}
