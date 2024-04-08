package Infrastructure.Scripts;

import Infrastructure.Connections.MySQLConfig;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

@MySQLConfig(config = "mysql.properties")
public class SQLScriptExecutor {
    private static String path;

    public static void executeScript(String databaseName) {
        Properties properties = new Properties();
        String propertiesFile = SQLScriptExecutor.class.getAnnotation(MySQLConfig.class).config();

        try {
            properties.load(new FileInputStream(propertiesFile));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        String path = properties.getProperty("mysql.path");
        String scriptContent = readScript(path);
        String scriptWithDatabaseName = scriptContent.replace("nombre_a_cambiar", databaseName);

        executeSQLScript(scriptWithDatabaseName, propertiesFile);
    }

    private static String readScript(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    private static void executeSQLScript(String script, String propertiesFile) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(propertiesFile));
            String url = properties.getProperty("mysql.url");
            String username = properties.getProperty("mysql.username");
            String password = properties.getProperty("mysql.password");

            try (Connection connection = DriverManager.getConnection(url, username, password);
                 Statement statement = connection.createStatement()) {
                statement.execute(script);
                System.out.println("Script SQL ejecutado correctamente.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
