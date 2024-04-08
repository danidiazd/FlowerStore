import Contexts.Product.Domain.*;
import Contexts.Product.Infrastructure.MongoDB.ProductRepositoryMongoDB;
import Contexts.Product.Infrastructure.SQL.ProductRepositorySQL;
import Contexts.Ticket.Domain.TicketRepository;
import Contexts.Ticket.Infrastructure.MongoDB.TicketRepositoryMongoDB;
import Contexts.Ticket.Infrastructure.SQL.TicketRepositorySQL;
import FlowerStore.ManagerTickets;
import FlowerStore.FlowerStore;
import Infrastructure.Connections.MongoDBConnection;
import Infrastructure.Connections.MySQLConnection;
import Utils.InputControl.InputControl;
import FlowerStore.*;
import Utils.Utils;

import java.util.List;

public class Demo implements Runnable {

    private static FlowerStore flowerStore;
    private MongoDBConnection mongoDBConnection;
    private MySQLConnection mySQLConnection;
    private ProductRepositoryMongoDB productRepositoryMongoDB;
    private static ProductsRepository productsRepository;
    private static TicketRepository ticketRepository;
    private ManagerProducts managerProducts;
    private ManagerTickets managerTickets;
    private Utils utils;

    public Demo() {
        ProductsRepository productsRepository = configureRepository();
        managerProducts = ManagerProducts.getInstance(productsRepository);
        managerTickets = ManagerTickets.getInstance(ticketRepository, productsRepository);
    }

    @Override
    public void run() {
        productsRepository.addPrimaryStock();
        menu();
    }

    public ProductsRepository configureRepository() {
        String userDatabase = InputControl.readString("Select the database you would like to work with (MySQL or MongoDB)");
        String nameStore = InputControl.readString("Indicate the name of the flower shop");
        if (userDatabase.equalsIgnoreCase("MongoDB")) {
            MongoDBConnection mongoDBConnection = new MongoDBConnection(nameStore);
            productsRepository = new ProductRepositoryMongoDB(mongoDBConnection);
            ticketRepository = new TicketRepositoryMongoDB(mongoDBConnection);
        } else if (userDatabase.equalsIgnoreCase("MySQL")) {
            MySQLConnection mySQLConnection = new MySQLConnection();
            productsRepository = new ProductRepositorySQL(mySQLConnection);
            ticketRepository = new TicketRepositorySQL(mySQLConnection);
        } else {
            System.err.println("This database type is not valid");
            configureRepository();
        }
        return productsRepository;
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
                    managerProducts.addProduct();
                    break;
                case 2:
                    managerProducts.showAllProducts();
                    break;
                case 3:
                    managerProducts.updateStock();
                    break;
                case 4:
                    managerProducts.deleteProduct();
                    break;
                case 5:
                    List<Product> typeProduct = managerProducts.getType();
                    managerProducts.showTypeProducts(typeProduct);
                    break;
                case 6:
                    managerProducts.totalValue();
                    break;
                case 7:
                    managerTickets.addProductsToTicket();
                    break;
                case 8:
                    managerTickets.showAllTickets();
                    break;
                case 9:
                    // TODO: showFlowerShopBenefits
                    break;
                case 10:
                    exit();
                    break;
                default:
                    System.out.println("Please, introduce a valid option");
                    break;
            }
        } while (true);
    }

    private void exit() {
        System.exit(0);
        mongoDBConnection.disconnectMongo();
        mySQLConnection.disconnectMySQL();
    }
}
