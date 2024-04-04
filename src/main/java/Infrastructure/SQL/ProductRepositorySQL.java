package Infrastructure.SQL;
import Products.Flower;
import Products.Product;
import Products.ProductType;
import Products.ProductsRepository;
import java.util.List;
import Connections.MySQLConnection;
import java.sql.*;
import java.util.ArrayList;

public class ProductRepositorySQL implements ProductsRepository {
    private MySQLConnection mySQLConnection;

    public ProductRepositorySQL(MySQLConnection mySQLConnection) {
        this.mySQLConnection = mySQLConnection;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM product";
        try (PreparedStatement statement = mySQLConnection.getMySQLDatabase().prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Product product = resultSetToProduct(resultSet);
                products.add(product);
            }
        } catch (SQLException e) {
            //TODO Añadir excepcion personalizada
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Product> getFlowers() {
        return null;
    }

    @Override
    public List<Product> getTrees() {
        return null;
    }

    @Override
    public List<Product> getDecorations() {
        return null;
    }

    @Override
    public void addPrimaryStock() {
        Flower<String> newFlower1 = new Flower<>("Rosa", 0, 0.0, "Roja");
        Flower<String> newFlower2 = new Flower<>("Girasol", 0, 0.0, "Blanca");
        Flower<String> newFlower3 = new Flower<>("Lirio", 0, 0.0, "Naranja");
        Flower<String> newFlower4 = new Flower<>("Clavel", 0, 0.0, "Amarillo");
        Flower<String> newFlower5 = new Flower<>("Amapola", 0, 0.0, "Amarillo");


        String insertProductQuery = "INSERT INTO product (name, quantity, price, type) VALUES (?, ?, ?, ?)";

        try (PreparedStatement productStatement = mySQLConnection.getMySQLDatabase().prepareStatement(insertProductQuery, Statement.RETURN_GENERATED_KEYS)) {

            insertProduct(productStatement, newFlower1);
            insertProduct(productStatement, newFlower2);
            insertProduct(productStatement, newFlower3);
            insertProduct(productStatement, newFlower4);
            insertProduct(productStatement, newFlower4);
            insertProduct(productStatement, newFlower5);
            System.out.println("Flowers were added successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertProduct(PreparedStatement productStatement, Flower<String> flower) throws SQLException {
        productStatement.setString(1, flower.getName());
        productStatement.setInt(2, flower.getQuantity());
        productStatement.setDouble(3, flower.getPrice());
        productStatement.setString(4, ProductType.FLOWER.name());
        productStatement.executeUpdate();
    }


    @Override
    public void updateProduct(Product product) {

    }

    @Override
    public void deleteProduct(Product product) {

    }

    @Override
    public void newTicket() {

    }

    private Product resultSetToProduct(ResultSet resultSet) throws SQLException {


        return null;
    }

}
