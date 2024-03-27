import Connections.MongoDBConnection;
import FlowerStore.FlowerStore;
import FlowerStore.Products.Decoration;
import FlowerStore.Products.Flower;
import FlowerStore.Products.Infrastructure.MongoDB.ProductRepositoryMongoDB;
import FlowerStore.Products.Product;
import FlowerStore.Products.Tree;
import FlowerStore.Ticket.TicketStore;
import FlowerStore.Utils.Utils;
import InputControl.InputControl;
import com.mongodb.client.MongoClients;

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
        menu();
    }


    public void menu() {

        System.out.println("¿Que acción desea realizar?");
        do {
            int selectAction = InputControl.readInt("Type of action \n" +
                    "1. Insert a product. \n" +
                    "2. Show all products. \n" +
                    "3. Remove all products. \n" +
                    "4. Show all stock. \n" +
                    "5. Show flower shop value. \n" +
                    "6. Create ticket. \n" +
                    "7. Show all tickets. \n" +
                    "8. Show flower shop benefits. \n" +
                    "9. Exit flower shop.");

            switch(selectAction) {
                case 1:
                    insertProduct();
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
        } while(true);
    }

    private void insertProduct() {

        boolean addMore;
        String decoration = new String();

        System.out.println("Indique el stock inicial de la tienda.");

        do {
            int chooseProduct = InputControl.readInt("Tipo de producto? \n" +
                    "1. Flor.\n" +
                    "2. Arbol.\n" +
                    "3. Decoracion.");

            String nameProduct = InputControl.readString("Nombre de producto? ");
            int quantityProduct = InputControl.readInt("Cantidad ?");
            double priceProduct = InputControl.readDouble("Precio unidad?");
            switch (chooseProduct) {
                case 1:
                    String color = InputControl.readString("Color?");
                    createProduct(chooseProduct, nameProduct, quantityProduct, priceProduct, color);
                    break;
                case 2:
                    double height = InputControl.readDouble("Altura del arbol? ");
                    createProduct(chooseProduct, nameProduct, quantityProduct, priceProduct, height);
                    break;
                case 3:
                    do {
                        decoration = InputControl.readString("Tipo de material?\n" +
                                "Plastico / Madera? ");
                        if (!decoration.equalsIgnoreCase("Plastico") && !decoration.equalsIgnoreCase("Madera")) {
                            System.out.println("Por favor, ingresa solo 'Plastico' o 'Madera'.");
                        }
                    } while (!decoration.equalsIgnoreCase("Plastico") && !decoration.equalsIgnoreCase("Madera"));
                    break;
            }

            createProduct(chooseProduct, nameProduct, quantityProduct, priceProduct, decoration);
            addMore = InputControl.readBoolean("¿Desea añadir más?");
        } while (addMore);

    }

    private <T> void createProduct(int type, String name, int quantity, double price, T attribute) {
        Product<T> product;
        switch (type) {
            case 1:
                product = new Flower(name, quantity, price, attribute);
                break;
            case 2:
                product = new Tree(name, quantity, price, attribute);
                break;
            case 3:
                product = new Decoration(name, quantity, price, attribute);
                break;
            default:
                throw new RuntimeException("ProductTypeNotDefined");
        }

        FlowerStore.addToStock(product);
        productRepositoryMongoDB.addProduct(product);
    }

    private void showAllProducts() {

        List<Product> products = productRepositoryMongoDB.getAllProducts();
        if(products.isEmpty()) {
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
                        product.getAttributes().toString());
            }

            //utils.waitForKeypress();

        }
    }
}
