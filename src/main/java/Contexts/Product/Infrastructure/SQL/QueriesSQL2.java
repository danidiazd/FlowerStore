package Contexts.Product.Infrastructure.SQL;

public class QueriesSQL2 {

    private static String DATABASE_NAME;

    public static void setDatabaseName(String databaseName) {
        DATABASE_NAME = databaseName;
    }

    public static String createDatabaseQuery() {
        return "CREATE DATABASE IF NOT EXISTS `" + DATABASE_NAME + "` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci";
    }

    public static String useDatabaseQuery() {
        return "USE `" + DATABASE_NAME + "`";
    }

    public static String createProductTableQuery() {
        return "CREATE TABLE IF NOT EXISTS `" + DATABASE_NAME + "`.`product` ("
                + "`idproduct` INT NOT NULL AUTO_INCREMENT, "
                + "`name` VARCHAR(45) NOT NULL, "
                + "`quantity` INT NOT NULL, "
                + "`price` DOUBLE NOT NULL, "
                + "`type` ENUM('FLOWER', 'TREE', 'DECORATION') NULL DEFAULT NULL, "
                + "PRIMARY KEY (`idproduct`)) ENGINE = InnoDB "
                + "AUTO_INCREMENT = 1 DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci";
    }

    public static String createDecorationTableQuery() {
        return "CREATE TABLE IF NOT EXISTS `" + DATABASE_NAME + "`.`decoration` ("
                + "`material` VARCHAR(45) NOT NULL, "
                + "`product_idproduct` INT NOT NULL, "
                + "INDEX `fk_decoration_product1_idx` (`product_idproduct` ASC) VISIBLE, "
                + "FOREIGN KEY (`product_idproduct`) "
                + "REFERENCES `" + DATABASE_NAME + "`.`product` (`idproduct`) "
                + "ON DELETE CASCADE) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci";
    }

    public static String createFlowerTableQuery() {
        return "CREATE TABLE IF NOT EXISTS `" + DATABASE_NAME + "`.`flower` ("
                + "`color` VARCHAR(45) NOT NULL, "
                + "`product_idproduct` INT NOT NULL, "
                + "INDEX `fk_flower_product1_idx` (`product_idproduct` ASC) VISIBLE, "
                + "FOREIGN KEY (`product_idproduct`) "
                + "REFERENCES `" + DATABASE_NAME + "`.`product` (`idproduct`) ON DELETE CASCADE) "
                + "ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci";
    }

    public static String createTicketTableQuery() {
        return "CREATE TABLE IF NOT EXISTS `" + DATABASE_NAME + "`.`ticket` ("
                + "`idticket` INT NOT NULL AUTO_INCREMENT, "
                + "`date` DATE NULL, "
                + "`totalPrice` FLOAT NULL, "
                + "PRIMARY KEY (`idticket`)) ENGINE = InnoDB "
                + "DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci";
    }

    public static String createProductTicketTableQuery() {
        return "CREATE TABLE IF NOT EXISTS `" + DATABASE_NAME + "`.`product_ticket` ("
                + "`idproduct_ticket` INT NOT NULL AUTO_INCREMENT, "
                + "`amount` SMALLINT(10) NULL, "
                + "`product_idproduct` INT NOT NULL, "
                + "`ticket_idticket` INT NOT NULL, "
                + "PRIMARY KEY (`idproduct_ticket`), "
                + "INDEX `fk_product_ticket_product1_idx` (`product_idproduct` ASC) VISIBLE, "
                + "INDEX `fk_product_ticket_ticket1_idx` (`ticket_idticket` ASC) VISIBLE, "
                + "FOREIGN KEY (`product_idproduct`) "
                + "REFERENCES `" + DATABASE_NAME + "`.`product` (`idproduct`) ON DELETE CASCADE, "
                + "FOREIGN KEY (`ticket_idticket`) "
                + "REFERENCES `" + DATABASE_NAME + "`.`ticket` (`idticket`) ON DELETE CASCADE) "
                + "ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci";
    }

    public static String createTreeTableQuery() {
        return "CREATE TABLE IF NOT EXISTS `" + DATABASE_NAME + "`.`tree` ("
                + "`height` DOUBLE NOT NULL, "
                + "`product_idproduct` INT NOT NULL, "
                + "INDEX `fk_tree_product1_idx` (`product_idproduct` ASC) VISIBLE, "
                + "FOREIGN KEY (`product_idproduct`) "
                + "REFERENCES `" + DATABASE_NAME + "`.`product` (`idproduct`) ON DELETE CASCADE) "
                + "ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci";
    }

    public static final String COUNT_PRODUCTS = "SELECT COUNT(*) FROM product";
}
