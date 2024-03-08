package dataAccess;

import java.sql.*;
import java.util.Properties;

public class DatabaseManager {
    private static final String databaseName;
    private static final String user;
    private static final String password;
    private static final String connectionUrl;



    /*
     * Load the database information for the db.properties file.
     */
    static {
        try {
            try (var propStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties")) {
                if (propStream == null) throw new Exception("Unable to laod db.properties");
                Properties props = new Properties();
                props.load(propStream);
                databaseName = props.getProperty("db.name");
                user = props.getProperty("db.user");
                password = props.getProperty("db.password");

                var host = props.getProperty("db.host");
                var port = Integer.parseInt(props.getProperty("db.port"));
                connectionUrl = String.format("jdbc:mysql://%s:%d", host, port);
            }
        } catch (Exception ex) {
            throw new RuntimeException("unable to process db.properties. " + ex.getMessage());
        }
    }

    /**
     * Creates and configures the database if it does not already exist.
     *
     */
    public static void createDatabase() throws DataAccessException {
        try {
            var statement = "CREATE DATABASE IF NOT EXISTS " + databaseName;
            var conn = DriverManager.getConnection(connectionUrl, user, password);

            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.executeUpdate();
            }
            conn.setCatalog(databaseName);
            var createUserTable = """
            CREATE TABLE IF NOT EXISTS user (
                username VARCHAR(50) NOT NULL,
                password VARCHAR(50) NOT NULL,
                email VARCHAR(50) NOT NULL
            )""";
            try (var createUserStatement = conn.prepareStatement(createUserTable)) {
                createUserStatement.executeUpdate();
            }
            var createAuthTable = """
            CREATE TABLE  IF NOT EXISTS auth (
                authToken VARCHAR(50) NOT NULL,
                username VARCHAR(50) NOT NULL
            )""";
            try (var createAuthStatement = conn.prepareStatement(createAuthTable)) {
                createAuthStatement.executeUpdate();
            }
            var createGameTable = """
            CREATE TABLE  IF NOT EXISTS game (
                gameID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                whiteUsername VARCHAR(50),
                blackUsername VARCHAR(50),
                gameName VARCHAR(50) NOT NULL,
                game VARCHAR(1000) NOT NULL
            )""";
            try (var createGameStatement = conn.prepareStatement(createGameTable)) {
                createGameStatement.executeUpdate();
            }

        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }


    /**
     * Create a connection to the database and sets the catalog based upon the
     * properties specified in db.properties. Connections to the database should
     * be short-lived, and you must close the connection when you are done with it.
     * The easiest way to do that is with a try-with-resource block.
     * <br/>
     * <code>
     * try (var conn = DbInfo.getConnection(databaseName)) {
     * // execute SQL statements.
     * }
     * </code>
     */
    static Connection getConnection() throws DataAccessException {
        try {
            var conn = DriverManager.getConnection(connectionUrl, user, password);
            conn.setCatalog(databaseName);
            return conn;
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}
