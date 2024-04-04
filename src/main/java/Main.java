import Connections.MongoDBConnection;
import Connections.MySQLConnection;
import FlowerStore.Products.Infrastructure.SQL.ProductRepositorySQL;
import FlowerStore.Products.ProductsRepository;
import InputControl.InputControl;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class Main {

    public static void main(String[] args) {
/*
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        String nameStore = InputControl.readString("Indicate the name of the flower shop");
        MongoDBConnection mongoDBConnection = new MongoDBConnection( nameStore, mongoClient);
        MySQLConnection mySQLConnection = new MySQLConnection();


        MySQLConnection mySQLConnection = new MySQLConnection();
        ProductsRepository productsRepository = new ProductRepositorySQL(mySQLConnection);
        Demo demo = new Demo(productsRepository);
        demo.run();
*/



        Demo demo = new Demo();
        demo.run();

    }
}