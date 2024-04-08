package FlowerStore;

import Contexts.Products.Domain.Product;
import Contexts.Products.Domain.ProductsRepository;
import Contexts.Products.Infrastructure.MongoDB.ProductRepositoryMongoDB;
import Contexts.Products.Infrastructure.SQL.ProductRepositorySQL;
import Infrastructure.Connections.MongoDBConnection;
import Infrastructure.Connections.MySQLConnection;
import Infrastructure.Scripts.SQLScriptExecutor;
import Utils.InputControl.InputControl;

import java.util.ArrayList;

public class FlowerStore {
     private String nameStore;
    private static FlowerStore instance;
    private static ProductsRepository productsRepository;


    private FlowerStore() {
        this.nameStore = nameStore;
    }

    public static FlowerStore getInstance() {
        if (instance == null) {
            instance = new FlowerStore();
        }
        return instance;
    }

    public String getNameStore() {
        return nameStore;
    }

    public void setNameStore(String nameStore) {
        this.nameStore = nameStore;
    }


    @Override
    public String toString() {
        return "FlowerStore with name: " + getNameStore();
    }


}
