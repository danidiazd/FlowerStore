import Connections.MongoDBConnection;
import FlowerStore.FlowerStore;
import FlowerStore.Products.*;
import FlowerStore.Products.Infrastructure.MongoDB.ProductRepositoryMongoDB;
import FlowerStore.Utils.Utils;
import InputControl.InputControl;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class Demo implements Runnable {

    private static FlowerStore flowerStore;
    private MongoDBConnection mongoDBConnection;
    private ProductRepositoryMongoDB productRepositoryMongoDB;

    private Utils utils;

    public Demo(MongoDBConnection mongoDBConnection) {
        this.mongoDBConnection = mongoDBConnection;
        this.productRepositoryMongoDB = new ProductRepositoryMongoDB(mongoDBConnection);
    }

    @Override
    public void run() {
        productRepositoryMongoDB.addPrimaryStock();
        menu();
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
                products = productRepositoryMongoDB.getFlowers();
                break;
            case 2:
                products = productRepositoryMongoDB.getTrees();
                break;
            case 3:
                products = productRepositoryMongoDB.getDecorations();
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

        List<Product> products = productRepositoryMongoDB.getAllProducts();
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
