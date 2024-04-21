package Contexts.Product.Infrastructure.SQL;

import Contexts.Product.Domain.*;
import Infrastructure.Connections.MySQLConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static Contexts.Product.Infrastructure.SQL.DBLoader.createTables;
import static Infrastructure.Connections.MySQLConnection.getMySQLDatabase;

public class ProductRepositorySQL implements ProductsRepository {
    private MySQLConnection mySQLConnection;
    private String dataBaseName;

    public ProductRepositorySQL(MySQLConnection mySQLConnection, String dataBaseName) {
        this.mySQLConnection = mySQLConnection;
        this.dataBaseName = dataBaseName;
    }


    @Override
    public void initialize() {
        try {
            Connection connection = getMySQLDatabase();

            DBLoader.createAndUseDB(dataBaseName, connection);
            DBLoader.createTables(connection);

            if (!productsExist(connection)) {
                addPrimaryStock();
            }

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    private boolean productsExist(Connection connection) throws SQLException {
        String selectQuery = QueriesSQLDB.COUNT_PRODUCTS;
        try (PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int productCount = resultSet.getInt(1);
                return productCount > 0;
            }
        }
        return false;
    }

    @Override
    public Product getProduct(int id) {
        Product product = null;
        try {
            Connection connection = getMySQLDatabase();
            PreparedStatement statement = connection.prepareStatement(QueriesSQLCRUD.SQL_SELECT_PRODUCT);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                product = resultSetToProduct(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return product;
    }

    @Override
    public Product getLastProduct() {
        Product lastProduct = null;
        try {
            Connection connection = getMySQLDatabase();
            PreparedStatement statement = connection.prepareStatement(QueriesSQLCRUD.SQL_SELECT_LAST_PRODUCT);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                lastProduct = resultSetToProduct(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return lastProduct;
    }

    private Product resultSetToProduct(ResultSet productRs) throws SQLException {
        int productId = productRs.getInt("idproduct");
        String name = productRs.getString("name");
        int quantity = productRs.getInt("quantity");
        double price = productRs.getDouble("price");
        String typeString = productRs.getString("type");
        String attribute = productRs.getString("attribute");

        ProductType type = ProductType.valueOf(typeString);

        Product product;
        if (type == ProductType.FLOWER) {
            product = new Flower<>(productId, name, quantity, price, attribute);
        } else if (type == ProductType.DECORATION) {
            product = new Decoration<>(productId, name, quantity, price, attribute);
        } else if (type == ProductType.TREE) {
            product = new Tree<>(productId, name, quantity, price, Double.parseDouble(attribute));
        } else {
            throw new IllegalArgumentException("Invalid product type : " + typeString);
        }

        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        try {
            Connection connection = getMySQLDatabase();
            PreparedStatement statement = connection.prepareStatement(QueriesSQLCRUD.SQL_SELECT);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Product product = resultSetToProduct(rs);
                products.add(product);
            }
        } catch (SQLException e) {
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
        Object[][] allData = {
                {"manzano", 1.5, 50, 20.5, ProductType.TREE},
                {"olivo", 2.0, 50, 11.99, ProductType.TREE},
                {"pino", 3.0, 50, 8.50, ProductType.TREE},
                {"rosal", 0.5, 50, 6.50, ProductType.TREE},
                {"rosa", "roja", 50, 4.0, ProductType.FLOWER},
                {"girasol", "blanca", 50, 3.50, ProductType.FLOWER},
                {"amapola", "roja", 50, 2.75, ProductType.FLOWER},
                {"lirio", "aaranja", 50, 1.5, ProductType.FLOWER},
                {"clavel", "amarillo", 50, 9.50, ProductType.FLOWER},
                {"jarron", "madera", 50, 20.50, ProductType.DECORATION},
                {"tiesto", "plastico", 50, 13.50, ProductType.DECORATION},
                {"jarron", "plastico", 50, 9.99, ProductType.DECORATION},
                {"tiesto", "madera", 50, 10.0, ProductType.DECORATION}
        };

        try {
            Connection connection = getMySQLDatabase();
            PreparedStatement productStatement = connection.prepareStatement(QueriesSQLCRUD.SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

            for (Object[] rowData : allData) {
                String name = (String) rowData[0];
                int quantity = (int) rowData[2];
                double price = (double) rowData[3];
                ProductType productType = (ProductType) rowData[4];

                productStatement.setString(1, name);
                productStatement.setInt(2, quantity);
                productStatement.setDouble(3, price);
                productStatement.setString(4, productType.name());
                productStatement.executeUpdate();

                ResultSet generatedKeys = productStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int productId = generatedKeys.getInt(1);
                    String specificAttribute = (productType == ProductType.TREE) ? "height" :
                            ((productType == ProductType.FLOWER) ? "color" : "material");

                    String formattedQuery = String.format(QueriesSQLCRUD.SQL_INSERT_ATTRIBUTE, productType.name().toLowerCase(), specificAttribute);
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
        try {
            Connection connection = getMySQLDatabase();
            PreparedStatement statement = connection.prepareStatement(QueriesSQLCRUD.SQL_UPDATE);
            statement.setString(1, product.getName());
            statement.setInt(2, product.getQuantity());
            statement.setDouble(3, product.getPrice());
            statement.setString(4, product.getType().toString());
            statement.setInt(5, product.getProductId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public void deleteProduct(Product product) {
        try {
            Connection connection = getMySQLDatabase();
            PreparedStatement statement = connection.prepareStatement(QueriesSQLCRUD.SQL_DELETE);
            statement.setInt(1, product.getProductId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public void addProduct(Product product) {
        try {
            Connection connection = getMySQLDatabase();
            PreparedStatement productStatement = connection.prepareStatement(QueriesSQLCRUD.SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

            productStatement.setString(1, product.getName());
            productStatement.setInt(2, product.getQuantity());
            productStatement.setDouble(3, product.getPrice());
            productStatement.setString(4, product.getType().name());
            productStatement.executeUpdate();

            ResultSet generatedKeys = productStatement.getGeneratedKeys();
            int productId = -1;
            if (generatedKeys.next()) {
                productId = generatedKeys.getInt(1);
            }

            String specificAttribute = (product.getType() == ProductType.TREE) ? "height" :
                    ((product.getType() == ProductType.FLOWER) ? "color" : "material");

            String formattedQuery = String.format(QueriesSQLCRUD.SQL_INSERT_ATTRIBUTE, product.getType().name().toLowerCase(), specificAttribute);
            try (PreparedStatement specificDataStatement = connection.prepareStatement(formattedQuery)) {
                specificDataStatement.setInt(1, productId);
                specificDataStatement.setString(2, product.getAttributes().toString());
                specificDataStatement.executeUpdate();
            }

            System.out.println("Product was added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
