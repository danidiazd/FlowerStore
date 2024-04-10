import Contexts.Product.Domain.Product;
import Contexts.Product.Infrastructure.MongoDB.ProductRepositoryMongoDB;
import Infrastructure.Connections.MongoDBConnection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

public class ProductRepositoryMongoDBTest {

    @Mock
    MongoDBConnection mongoDBConnection;
    @Mock
    MongoCollection<Document> collection;
    ProductRepositoryMongoDB<Product> productRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mongoDBConnection = new MongoDBConnection("mongoDatabaseTest");
        productRepository = new ProductRepositoryMongoDB<>(mongoDBConnection);
        setPrivateField(productRepository, "collection", collection);
    }

    @Test
    void testGetProduct() {
        Document document = new Document("productId", 1)
                .append("name", "Test Product")
                .append("quantity", 10)
                .append("price", 5.0)
                .append("type", "FLOWER")
                .append("attribute", "Red");

        FindIterable<Document> findIterable = Mockito.mock(FindIterable.class);
        when(collection.find(any(Document.class))).thenReturn(findIterable);
        when(findIterable.first()).thenReturn(document);

        Product product = productRepository.getProduct(1);

    }

    private void setPrivateField(Object obj, String fieldName, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
