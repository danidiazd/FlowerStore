package Infrastructure.SQL;

import Connections.MySQLConnection;
import FlowerStore.Ticket.Ticket;
import Products.Product;
import Products.ProductType;
import Products.ProductsRepository;
import org.bson.Document;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static Connections.MySQLConnection.getMySQLDatabase;

public class ProductDAO implements ProductsRepository {

    private static final String SQL_CREATE =
            "CREATE TABLE IF NOT EXISTS product (" +
                    "idproduct INT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(45) NOT NULL, " +
                    "quantity INT NOT NULL, " +
                    "price DOUBLE NOT NULL, " +
                    "type ENUM('FLOWER', 'TREE', 'DECORATION') NULL DEFAULT NULL" +
                    ")";

    private static final String SQL_SELECT = "SELECT * FROM product";

    private static final String SQL_INSERT = "INSERT INTO product(name, quantity, price, type) VALUES(?, ?, ?, ?)";

    private static final String SQL_UPDATE = "UPDATE product SET name = ?, quantity = ?, price = ?, type = ? WHERE idproduct = ?";

    private static final String SQL_DELETE = "DELETE FROM product WHERE idproduct = ?";

    public void createTable() {
        try(Connection conn = getMySQLDatabase();
            Statement stmt = conn.createStatement()) {
            stmt.execute(SQL_CREATE);
        } catch(SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        try(Connection conn = getMySQLDatabase();
            PreparedStatement stmt = conn.prepareStatement(SQL_SELECT);
            ResultSet rs = stmt.executeQuery()) {
            while(rs.next()) {
                Product product = new Product(
                        rs.getInt("idproduct"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        ProductType.valueOf(rs.getString("type")),
                        null
                );
                products.add(product);
            }
        } catch (SQLException e){
            e.printStackTrace(System.out);
        }
        return products;
    }

    @Override
    public List<Product> getFlowers() {
        List<Product> allProducts = getAllProducts();
        return allProducts.stream()
                .filter(product -> product.getType() == ProductType.FLOWER)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> getTrees() {
        List<Product> allProducts = getAllProducts();
        return allProducts.stream()
                .filter(product -> product.getType() == ProductType.TREE)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> getDecorations() {
        List<Product> allProducts = getAllProducts();
        return allProducts.stream()
                .filter(product -> product.getType() == ProductType.DECORATION)
                .collect(Collectors.toList());
    }

    @Override
    public void addPrimaryStock() {

        List<Product> stock = new ArrayList<>();

        stock.add(new Product("Manzano",
                        0,
                        0.0,
                        ProductType.TREE,
                        5));

        stock.add(new Product("Olivo",
                0,
                0.0,
                ProductType.TREE,
                2.0));

        stock.add(new Product("Pino",
                0,
                0.0,
                ProductType.TREE,
                3.0));

        stock.add(new Product("Rosal",
                0,
                0.0,
                ProductType.TREE,
                0.5));

        stock.add(new Product("Rosa",
                0,
                0.0,
                ProductType.FLOWER,
                "Roja"));

        stock.add(new Product("Girasol",
                0,
                0.0,
                ProductType.FLOWER,
                "Blanca"));

        stock.add(new Product("Amapola",
                0,
                0.0,
                ProductType.FLOWER,
                "Roja"));

        stock.add(new Product("Lirio",
                0,
                0.0,
                ProductType.FLOWER,
                "Naranja"));

        stock.add(new Product("Clavel",
                0,
                0.0,
                ProductType.FLOWER,
                "Amarillo"));

        stock.add(new Product("Jarron",
                0,
                0.0,
                ProductType.DECORATION,
                "Madera"));

        stock.add(new Product("Tiesto",
                0,
                0.0,
                ProductType.DECORATION,
                "Plastico"));

        stock.add(new Product("Jarron",
                0,
                0.0,
                ProductType.DECORATION,
                "Plastico"));

        stock.add(new Product("Tiesto",
                0,
                0.0,
                ProductType.DECORATION,
                "Madera"));

        for(Product product:stock) {
            addProduct(product);
        }

    }

    public void addProduct(Product product) {
        try(Connection conn = getMySQLDatabase();
            PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {
            stmt.setString(1, product.getName());
            stmt.setInt(2, product.getQuantity());
            stmt.setDouble(3, product.getPrice());
            stmt.setString(4, product.getType().toString());
            stmt.executeUpdate();
        }catch(SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public void updateProduct(Product product) {
        try(Connection conn = getMySQLDatabase();
            PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
            stmt.setString(1, product.getName());
            stmt.setInt(2, product.getQuantity());
            stmt.setDouble(3, product.getPrice());
            stmt.setString(4, product.getType().toString());
            stmt.setInt(5, product.getProductId());
            stmt.executeUpdate();
        }catch(SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public void deleteProduct(Product product) {
        try(Connection conn = getMySQLDatabase();
            PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {
            stmt.setInt(1, product.getProductId());
            stmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public void newTicket(Map<Product, Integer> ticketInfo) {

    }

    @Override
    public List<Ticket> getAllTickets() {
        return null;
    }
}
