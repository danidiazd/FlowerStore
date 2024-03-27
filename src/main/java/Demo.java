import Connections.MongoDBConnection;
import FlowerStore.FlowerStore;
import FlowerStore.Products.Decoration;
import FlowerStore.Products.Flower;
import FlowerStore.Products.Infrastructure.MongoDB.ProductRepositoryMongoDB;
import FlowerStore.Products.Product;
import FlowerStore.Products.Tree;
import FlowerStore.Ticket.TicketStore;
import InputControl.InputControl;
import com.mongodb.client.MongoClients;

public class Demo implements Runnable {

    private static FlowerStore flowerStore;
    private MongoDBConnection mongoDBConnection;
    private ProductRepositoryMongoDB productRepositoryMongoDB;

    public Demo(MongoDBConnection mongoDBConnection) {
        this.mongoDBConnection = mongoDBConnection;
        this.productRepositoryMongoDB = new ProductRepositoryMongoDB(mongoDBConnection);
    }

    @Override
    public void run() {
        menu();
    }


    public void menu() {
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
}
