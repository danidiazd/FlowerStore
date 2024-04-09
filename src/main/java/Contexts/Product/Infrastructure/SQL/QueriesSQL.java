package Contexts.Product.Infrastructure.SQL;

public class QueriesSQL {

    public static final String SQL_SELECT =
            "SELECT p.idproduct, p.name, p.quantity, p.price, p.type, " +
                    "COALESCE(f.color, t.height, d.material) AS attribute " +
                    "FROM product p " +
                    "LEFT JOIN flower f ON p.idproduct = f.product_idproduct " +
                    "LEFT JOIN decoration d ON p.idproduct = d.product_idproduct " +
                    "LEFT JOIN tree t ON p.idproduct = t.product_idproduct";

    public static final String SQL_SELECT_LAST_PRODUCT = "SELECT p.idproduct, p.name, p.quantity, p.price, p.type, " +
            "COALESCE(f.color, t.height, d.material) AS attribute " +
            "FROM product p " +
            "LEFT JOIN flower f ON p.idproduct = f.product_idproduct " +
            "LEFT JOIN decoration d ON p.idproduct = d.product_idproduct " +
            "LEFT JOIN tree t ON p.idproduct = t.product_idproduct " +
            "ORDER BY idproduct DESC " +
            "LIMIT 1";
    public static final String SQL_SELECT_PRODUCT = "SELECT p.idproduct, p.name, p.quantity, p.price, p.type, " +
            "COALESCE(f.color, t.height, d.material) AS attribute " +
            "FROM product p " +
            "LEFT JOIN flower f ON p.idproduct = f.product_idproduct " +
            "LEFT JOIN decoration d ON p.idproduct = d.product_idproduct " +
            "LEFT JOIN tree t ON p.idproduct = t.product_idproduct " +
            "WHERE idproduct = ?";
    public static final String SQL_INSERT = "INSERT INTO product(name, quantity, price, type) VALUES(?, ?, ?, ?)";
    public static final String SQL_INSERT_ATTRIBUTE = "INSERT INTO %s (product_idproduct, %s) VALUES (?, ?)";

    public static final String SQL_UPDATE = "UPDATE product SET name = ?, quantity = ?, price = ?, type = ? WHERE idproduct = ?";

    public static final String SQL_DELETE = "DELETE product, flower, decoration, tree " +
            "FROM product " +
            "LEFT JOIN flower ON product.idproduct = flower.product_idproduct " +
            "LEFT JOIN decoration ON product.idproduct = decoration.product_idproduct " +
            "LEFT JOIN tree ON product.idproduct = tree.product_idproduct " +
            "WHERE product.idproduct = ?";

    public static final String SQL_RESET = "USE floresPaquitaSL;" +
            "DELETE FROM product;" +
            "DELETE FROM ticket;" +
            "DELETE FROM tree;" +
            "DELETE FROM flower;" +
            "DELETE FROM decoration;" +
            "DELETE FROM product_ticket;" +
            "ALTER TABLE product AUTO_INCREMENT = 1;" +
            "ALTER TABLE ticket AUTO_INCREMENT = 1;" +
            "ALTER TABLE product_ticket AUTO_INCREMENT = 1;";

}
