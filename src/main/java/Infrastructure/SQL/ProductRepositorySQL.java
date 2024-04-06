package Infrastructure.SQL;
import FlowerStore.Ticket.Ticket;
import Products.Flower;
import Products.Product;
import Products.ProductType;
import Products.ProductsRepository;
import java.util.List;
import Connections.MySQLConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

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
        Object[][] allData = {
                {"Rosa", "Roja", ProductType.FLOWER},
                {"Girasol", "Blanca", ProductType.FLOWER},
                {"Amapola", "Roja", ProductType.FLOWER},
                {"Lirio", "Naranja", ProductType.FLOWER},
                {"Clavel", "Amarillo", ProductType.FLOWER},
                {"Manzano", 1.5, ProductType.TREE},
                {"Olivo", 2.0, ProductType.TREE},
                {"Pino", 3.0, ProductType.TREE},
                {"Rosal", 0.5, ProductType.TREE},
                {"Jarron", "Madera", ProductType.DECORATION},
                {"Tiesto", "Plastico", ProductType.DECORATION},
                {"Jarron", "Plastico", ProductType.DECORATION},
                {"Tiesto", "Madera", ProductType.DECORATION}
        };

        String insertProductQuery = "INSERT INTO product (name, quantity, price, type) VALUES (?, ?, ?, ?)";
        String insertSpecificDataQuery = "INSERT INTO %s (product_idproduct, %s) VALUES (?, ?)";

        try (Connection connection = mySQLConnection.getMySQLDatabase();
             PreparedStatement productStatement = connection.prepareStatement(insertProductQuery, Statement.RETURN_GENERATED_KEYS)) {

            for (Object[] rowData : allData) {
                String name = (String) rowData[0];
                ProductType productType = (ProductType) rowData[2];

                productStatement.setString(1, name);
                productStatement.setInt(2, 0);
                productStatement.setDouble(3, 0.0);
                productStatement.setString(4, productType.name());
                productStatement.executeUpdate();

                ResultSet generatedKeys = productStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int productId = generatedKeys.getInt(1);
                    String specificColumnName = (productType == ProductType.TREE) ? "height" :
                            ((productType == ProductType.FLOWER) ? "color" : "material");

                    String formattedQuery = String.format(insertSpecificDataQuery, productType.name().toLowerCase(), specificColumnName);
                    try (PreparedStatement specificDataStatement = connection.prepareStatement(formattedQuery)) {
                        specificDataStatement.setInt(1, productId);
                        if (rowData[1] instanceof Double) {
                            specificDataStatement.setDouble(2, (Double) rowData[1]);
                        } else {
                            specificDataStatement.setString(2, (String) rowData[1]);
                        }
                        specificDataStatement.executeUpdate();
                    }
                }
            }
            System.out.println("Primary Stock was added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateProduct(Product product) {

    }

    @Override
    public void deleteProduct(Product product) {

    }

    @Override
    public void newTicket(Map<Product, Integer> ticketInfo) {

    }

    @Override
    public List<Ticket> getAllTickets() {
        return null;
    }

    @Override
    public void addProduct(Product product) {

    }

    private Product resultSetToProduct(ResultSet resultSet) throws SQLException {


        return null;
    }

}
