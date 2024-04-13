package Contexts.Product.Infrastructure.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static Infrastructure.Connections.MySQLConnection.getMySQLDatabase;

public class DBLoader {


    public static void createAndUseDB(String dataBaseName, Connection connection) {
        try {
            QueriesSQLDB.setDatabaseName(dataBaseName);
            // Creamos la base de datos si no existe
            String createDatabaseQuery = QueriesSQLDB.createDatabaseQuery();
            try (PreparedStatement createDBStatement = connection.prepareStatement(createDatabaseQuery)) {
                createDBStatement.executeUpdate();
            }

            String useDatabaseQuery = QueriesSQLDB.useDatabaseQuery();
            try (PreparedStatement useDbStatement = connection.prepareStatement(useDatabaseQuery)) {
                useDbStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace(System.out);

        }
    }


    public static void createTables(Connection connection) {
        try {
            String createProductTableQuery = QueriesSQLDB.createProductTableQuery();
            try (PreparedStatement createProductTableStatement = connection.prepareStatement(createProductTableQuery)) {
                createProductTableStatement.executeUpdate();
            }

            String createDecorationTableQuery = QueriesSQLDB.createDecorationTableQuery();
            try (PreparedStatement createDecorationTableStatement = connection.prepareStatement(createDecorationTableQuery)) {
                createDecorationTableStatement.executeUpdate();
            }

            String createFlowerTableQuery = QueriesSQLDB.createFlowerTableQuery();
            try (PreparedStatement createFlowerTableStatement = connection.prepareStatement(createFlowerTableQuery)) {
                createFlowerTableStatement.executeUpdate();
            }

            String createTicketTableQuery = QueriesSQLDB.createTicketTableQuery();
            try (PreparedStatement createTicketTableStatement = connection.prepareStatement(createTicketTableQuery)) {
                createTicketTableStatement.executeUpdate();
            }

            String createProductTicketTableQuery = QueriesSQLDB.createProductTicketTableQuery();
            try (PreparedStatement createProductTicketTableStatement = connection.prepareStatement(createProductTicketTableQuery)) {
                createProductTicketTableStatement.executeUpdate();
            }

            String createTreeTableQuery = QueriesSQLDB.createTreeTableQuery();
            try (PreparedStatement createTreeTableStatement = connection.prepareStatement(createTreeTableQuery)) {
                createTreeTableStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }
}
