import Connections.MongoDBConnection;
import Connections.MySQLConnection;
import FlowerStore.FlowerStore;
import FlowerStore.Utils.Utils;
import Infrastructure.MongoDB.ProductRepositoryMongoDB;
import Infrastructure.SQL.ProductRepositorySQL;
import InputControl.InputControl;
import Products.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import java.util.ArrayList;
import java.util.List;

public class Demo implements Runnable {

    private static FlowerStore flowerStore;
    private MongoDBConnection mongoDBConnection;
    private ProductRepositoryMongoDB productRepositoryMongoDB;
    private static ProductsRepository productsRepository;

    private Utils utils;

    public Demo() {
    }

    @Override
    public void run() {
        configureRepository();
        productsRepository.addPrimaryStock();
        menu();
    }

    public void configureRepository() {
        String userDatabase = InputControl.readString("Select the database you would like to work with (MySQL or MongoDB)");
        if (userDatabase.equalsIgnoreCase("MongoDB")) {
            MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
            String nameStore = InputControl.readString("Indicate the name of the flower shop");
            MongoDBConnection mongoDBConnection = new MongoDBConnection( nameStore, mongoClient);
            productsRepository = new ProductRepositoryMongoDB(mongoDBConnection);
        } else if (userDatabase.equalsIgnoreCase("MySQL")) {
            MySQLConnection mySQLConnection = new MySQLConnection();
            productsRepository = new ProductRepositorySQL(mySQLConnection);
        } else {
            System.err.println("This database type is not valid");
            configureRepository();
        }
    }


    public void menu() {

        System.out.println("¿Que acción desea realizar?");
        do {
            int selectAction = InputControl.readInt("\nType of action \n" +
                    "1. Create a product. \n" +
                    "2. Show all products.\n" +
                    "3. Update stock. \n" +
                    "4. Remove a product.\n" +
                    "5. Show all stock. \n" +
                    "6. Show flower shop value. \n" +
                    "7. Create ticket. \n" +
                    "8. Show all tickets. \n" +
                    "9. Show flower shop benefits. \n" +
                    "10. Exit flower shop.");

            switch (selectAction) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    showAllProducts();
                    break;
                case 3:
                    updateStock();
                    break;
                case 4:
                    deleteProduct();
                    break;
                case 5:
                    showAllProducts();
                    break;
                case 6:
                    totalValue();
                    break;
                case 7:
                    // TODO: createTicket;
                    break;
                case 8:
                    // TODO: showAllTickets;
                    break;
                case 9:
                    // TODO: showFlowerShopBenefits
                    break;
                case 10:
                    System.exit(0); // to exit the program
                    break;
                default:
                    System.out.println("Please, introduce a valid option");
                    break;
            }
        } while (true);
    }

    private void updateStock() {

        Product product = getOneProduct();

        int stockToAdd = InputControl.readInt("You selected " + product.getName() + "\n" +
                "How many stock do you want to add?");
        product.setQuantity(stockToAdd);
        double price = InputControl.readDouble("Choose a price for " + product.getName());
        product.setPrice(price);

        productsRepository.updateProduct(product);

    }

    private void deleteProduct() {
        Product product = getOneProduct();
        productsRepository.deleteProduct(product);
    }
    private void addProduct() {

        String name = InputControl.readString("Type a name for product.");
        int quantity = InputControl.readInt("Type a quantity stock.");
        double price = InputControl.readDouble("Type a price.");
        int type = InputControl.readInt("Type 1 for Tree.\n" +
                "2 for Flower.\n" +
                "3 for Decoration");
        String typeProduct = "";
        switch (type) {
            case 1:
                typeProduct = ProductType.TREE.toString();
                double attribute = InputControl.readDouble("Type height for the tree");
                Tree newTree = new Tree<>(name, quantity, price, attribute);
                productsRepository.addProduct(newTree);
                break;
            case 2:
                typeProduct = ProductType.FLOWER.toString();
                String flowerAttribute = InputControl.readString("Type color for the flower");
                Flower newFlower = new Flower<>(name, quantity, price, flowerAttribute);
                productsRepository.addProduct(newFlower);
                break;
            case 3:
                typeProduct = ProductType.DECORATION.toString();
                String decorationAttribute = InputControl.readString("Type material for the decoration");
                Decoration newDecoration = new Decoration<>(name, quantity, price, decorationAttribute);
                productsRepository.addProduct(newDecoration);
                break;
        }
    }
    private Product getOneProduct() {
        Product selectProduct;
        List<Product> products = getTypetoAdd();
        showTypeProducts(products);

        int typeId;
        do {
            typeId = InputControl.readInt("Type the ID of product to select: ");
            if (typeId < 1 || typeId > products.size()) {
                System.out.println("Invalid ID. Please enter a valid ID.");
            }
        } while (typeId < 1 || typeId > products.size());

        selectProduct = products.get(typeId - 1);
        return selectProduct;
    }

    private void totalValue() {
        List<Product> products = productsRepository.getAllProducts();
        double price = 0;
        for (Product product : products) {
            price += product.getPrice();
        }
        System.out.println("La floristeria " + flowerStore.getNameStore() +
                " tiene un valor de " + price + "€");
    }

    private List<Product> getTypetoAdd() {

        List<Product> products = new ArrayList<>();

        int option = InputControl.readInt("What you want insert?\n" +
                "1. FLOWER.\n" +
                "2. TREE.\n" +
                "3. DECORATION.\n");
        switch (option) {

            case 1:
                products = productsRepository.getFlowers();
                break;
            case 2:
                products = productsRepository.getTrees();
                break;
            case 3:
                products = productsRepository.getDecorations();
                break;
        }
        return products;
    }

    private void showTypeProducts(List<Product> products) {
        Product.resetIdProduct();
        int idWidth = 5, nameWidth = 15, quantityWidth = 10, priceWidth = 10,
                typeWidth = 15, attributeWidth = 15;

        // Print table headers
        System.out.printf("%-" + idWidth + "s %-" + nameWidth + "s %-" + quantityWidth
                        + "s %-" + priceWidth + "s %-" + typeWidth + "s %-" + attributeWidth + "s%n",
                "ID", "Name", "Quantity", "Price", "Type", "Attributes");

        // Print a line under the header
        System.out.printf("%-" + (idWidth + nameWidth + quantityWidth + priceWidth + typeWidth
                + attributeWidth + 10) + "s%n", "");

        // Print each product as a row in the table
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            System.out.printf("%-" + idWidth + "d %-" + nameWidth + "s %-" + quantityWidth
                            + "d %-" + priceWidth + ".2f %-" + typeWidth + "s %-" + attributeWidth + "s%n",
                    i + 1,
                    product.getName(),
                    product.getQuantity(),
                    product.getPrice(),
                    product.getType().toString(),
                    product.getAttributes());
        }
    }

    private void showAllProducts() {

        List<Product> products = productsRepository.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products found");
            //utils.waitForKeyPress();
        } else {
            int idWidth = 5, nameWidth = 15, quantityWidth = 10, priceWidth = 10,
                    typeWidth = 15, attributeWidth = 15;

            // Print table headers
            System.out.printf("%-" + idWidth + "s %-" + nameWidth + "s %-" + quantityWidth
                            + "s %-" + priceWidth + "s %-" + typeWidth + "s %-" + attributeWidth + "s%n",
                    "ID", "Name", "Quantity", "Price", "Type", "Attributes");

            // Print a line under the header
            System.out.printf("%-" + (idWidth + nameWidth + quantityWidth + priceWidth
                    + typeWidth + attributeWidth + 10) + "s%n", "");

            // Print each product as a row in the table
            for (int i = 0; i < products.size(); i++) {
                Product product = products.get(i);
                System.out.printf("%-" + idWidth + "d %-" + nameWidth + "s %-" + quantityWidth
                                + "d %-" + priceWidth + ".2f %-" + typeWidth + "s %-" + attributeWidth + "s%n",
                        i + 1,
                        product.getName(),
                        product.getQuantity(),
                        product.getPrice(),
                        product.getType().toString(),
                        product.getAttributes());
            }
            //utils.waitForKeypress();
        }
    }

    private void addProductsToTicket() {
        showAllProducts();
    }
}
