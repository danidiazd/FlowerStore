import Connections.MongoDBConnection;
import InputControl.InputControl;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class Main {

    public static void main(String[] args) {

        Demo demo = new Demo();
        demo.run();

    }
}