import Infrastructure.Connections.MongoDBConnection;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MongoDBConnectionTest {

    private MongoDBConnection mongoDBConnection;
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;

    @BeforeEach
    public void setUp() {
        mongoDBConnection = new MongoDBConnection("mongoDatabaseTest");
    }

    @Test
    public void testMongoDBConnectionInitialization() {
        assertNotNull(mongoDBConnection);
    }
    @AfterEach
    public void tearDown() {
        mongoClient.close();
    }


}
