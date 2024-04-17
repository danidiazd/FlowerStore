package Infrastructure.Connections;


import Infrastructure.Config.MongoDBConfig;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


@MongoDBConfig(config = "mongodb.properties")
public class MongoDBConnection {

    public final MongoClient mongoClient;
    public MongoDatabase mongoDatabase;


    public MongoDBConnection(String databaseName) {
        Properties properties = loadProperties();
        String host = properties.getProperty("mongodb.host");
        int port = Integer.parseInt(properties.getProperty("mongodb.port"));
        mongoClient = MongoClients.create("mongodb://" + host + ":" + port);
        this.mongoDatabase = mongoClient.getDatabase(databaseName);
    }


    private Properties loadProperties() {
        MongoDBConfig config = MongoDBConnection.class.getAnnotation(MongoDBConfig.class);
        if (config != null) {
            String filename = config.config();
            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename)) {
                if (inputStream != null) {
                    Properties properties = new Properties();
                    properties.load(inputStream);
                    return properties;
                } else {
                    throw new IOException("Properties file '" + filename + "' not found.");
                }
            } catch (IOException e) {
                throw new RuntimeException("Error loading MongoDB properties file: " + e.getMessage());
            }
        } else {
            throw new RuntimeException("MongoDBConfig annotation not found on MongoDBConnection class.");
        }
    }

    public void disconnectMongo() {
        try {
            if (mongoClient != null) {
                mongoClient.close();
            }
        } catch (MongoException e) {
            System.err.println("An error occurred when disconnecting from the database");
        }
    }
}
