package Connections;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {

    public final MongoClient mongoClient;
    public MongoDatabase mongoDatabase;

    public MongoDBConnection(String databaseName, MongoClient mongoClient) {
        this.mongoClient = mongoClient;
        try {
            this.mongoDatabase = mongoClient.getDatabase(databaseName);
        } catch (MongoException e) {
            System.out.println("Error al conectar con la base de datos.");
        }
    }

    private void disconnectMongo() {
        try {
            if (mongoClient != null) {
                mongoClient.close();
            }
        } catch (MongoException e) {
            System.out.println("Error al desconectar con la base de datos.");
        }
    }

}