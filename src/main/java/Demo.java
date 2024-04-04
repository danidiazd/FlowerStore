import Connections.MongoDBConnection;
import Connections.MySQLConnection;
import FlowerStore.FlowerStore;
import FlowerStore.Products.*;
import FlowerStore.Products.Infrastructure.MongoDB.ProductRepositoryMongoDB;
import FlowerStore.Products.Infrastructure.SQL.ProductRepositorySQL;
import FlowerStore.Utils.Utils;
import InputControl.InputControl;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class Demo implements Runnable {

    private static FlowerStore flowerStore;
    private MongoDBConnection mongoDBConnection;
    private MySQLConnection mySQLConnection;
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
            productsRepository = new ProductRepositorySQL();
        } else {
            System.out.println("Tipo de base de datos no válido.");
            configureRepository();
        }
    }


    public void menu() {

        System.out.println("¿Que acción desea realizar?");
        do {
            int selectAction = InputControl.readInt("\nType of action \n" +
                    "1. Insert a product. \n" +
                    "2. Show all products. \n" +
                    "3. Remove a product. \n" +
                    "4. Show all stock. \n" +
                    "5. Show flower shop value. \n" +
                    "6. Create ticket. \n" +
                    "7. Show all tickets. \n" +
                    "8. Show flower shop benefits. \n" +
                    "9. Exit flower shop.");

            switch (selectAction) {
                case 1:
                    addStock();
                    break;
                case 2:
                    showAllProducts();
                    break;
                case 3:
                    // TODO: deleteProduct
                    break;
                case 4:
                    // TODO: showAllStock
                    break;
                case 5:
                    // TODO: showFlowerShopValue
                    break;
                case 6:
                    // TODO: createTicket;
                    break;
                case 7:
                    // TODO: showAllTickets;
                    break;
                case 8:
                    // TODO: showFlowerShopBenefits
                    break;
                case 9:
                    System.exit(0); // to exit the program
                    break;
                default:
                    System.out.println("Please, introduce a valid option");
                    break;
            }
        } while (true);
    }

    private void addStock() {

        Product selectProduct;
        List<Product> products = getTypetoAdd();
        showTypeProducts(products);


        int typeId;
        do {
            typeId = InputControl.readInt("Type the ID of product to ADD: ");
            if (typeId < 1 || typeId > products.size()) {
                System.out.println("Invalid ID. Please enter a valid ID.");
            }
        } while (typeId < 1 || typeId > products.size());

        selectProduct = products.get(typeId - 1); // Obtener el producto correspondiente al ID seleccionado
        int stockToAdd = InputControl.readInt("You selected " + selectProduct.getName() + "\n" +
                "How many stock do you want to add?");
        selectProduct.setQuantity(stockToAdd);
        double price = InputControl.readDouble("Choose a price for " + selectProduct.getName());
        selectProduct.setPrice(price);

        // TODO: FALTA consulta a MongoDB (y SQL) para actualizar el artículo.


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
        int idWidth = 5;
        int nameWidth = 15;
        int quantityWidth = 10;
        int priceWidth = 10;
        int typeWidth = 15;
        int attributeWidth = 15;

        // Print table headers
        System.out.printf("%-" + idWidth + "s %-" + nameWidth + "s %-" + quantityWidth + "s %-" + priceWidth + "s %-" + typeWidth + "s %-" + attributeWidth + "s%n",
                "ID", "Name", "Quantity", "Price", "Type", "Attributes");

        // Print a line under the header
        System.out.printf("%-" + (idWidth + nameWidth + quantityWidth + priceWidth + typeWidth + attributeWidth + 10) + "s%n", "");

        // Print each product as a row in the table
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            System.out.printf("%-" + idWidth + "d %-" + nameWidth + "s %-" + quantityWidth + "d %-" + priceWidth + ".2f %-" + typeWidth + "s %-" + attributeWidth + "s%n",
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

            int idWidth = 5;
            int nameWidth = 15;
            int quantityWidth = 10;
            int priceWidth = 10;
            int typeWidth = 15;
            int attributeWidth = 15;

            // Print table headers
            System.out.printf("%-" + idWidth + "s %-" + nameWidth + "s %-" + quantityWidth + "s %-" + priceWidth + "s %-" + typeWidth + "s %-" + attributeWidth + "s%n",
                    "ID", "Name", "Quantity", "Price", "Type", "Attributes");

            // Print a line under the header
            System.out.printf("%-" + (idWidth + nameWidth + quantityWidth + priceWidth + typeWidth + attributeWidth + 10) + "s%n", "");

            // Print each product as a row in the table
            for (Product product : products) {
                System.out.printf("%-" + idWidth + "d %-" + nameWidth + "s %-" + quantityWidth + "d %-" + priceWidth + ".2f %-" + typeWidth + "s %-" + attributeWidth + "s%n",
                        product.getProductId(),
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
