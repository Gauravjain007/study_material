package com.study.designpatterns.singleton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

import com.google.gson.Gson;
import com.study.exception_handling.MyException;

/**
 * Singleton class to manage database connections. This implementation ensures
 * that only one instance of DatabaseManager exists, providing a single point of
 * access to the database connection.
 * <p>
 * In Production code, consider using a connection pool (e.g., HikariCP, Apache
 * DBCP, or C3P0) for better performance and resource management.
 * <p>
 * Disadvantages:
 * <ul>
 * <li>Scalability: A single connection cannot handle concurrent requests.
 * <li>Reliability: If the connection fails, the entire application is affected.
 * <li>Resource Management: A single connection may lead to resource contention.
 * <li>Performance:
 * <ul>
 * <li>Single connection can become a bottleneck under high load.
 * <li>Connection creation is expensive; pooling reuses connections.
 * </ul>
 * </ul>
 */
public class DatabaseManager {
    private static final AtomicReference<DatabaseManager> instance = new AtomicReference<>();
    private Connection conn;

    private DatabaseManager() throws MyException {
        if (instance.get() != null) {
            throw new IllegalStateException("Database Manager Instance is already created!");
        }
        try {
            URL resourceUrl = getClass().getClassLoader().getResource("config.json");
            if (resourceUrl == null) {
                throw new FileNotFoundException("config.json file not found in resources directory");
            }
            File file = Paths.get(resourceUrl.toURI()).toFile();
            try (FileReader reader = new FileReader(file)) {
                Properties properties = new Gson().fromJson(reader, Properties.class);
                String filePath = properties.getProperty("db_credentials_path");
                if (filePath == null) {
                    throw new MyException("db_credentials_path not found in config file");
                }
                try (InputStream inStream = Files.newInputStream(Path.of(filePath));) {
                    Properties props = new Properties();
                    props.load(inStream);
                    String url = props.getProperty("db.url");
                    String user = props.getProperty("db.user");
                    String pwd = props.getProperty("db.pwd");
                    if (url == null || user == null || pwd == null) {
                        throw new MyException("Database credentials missing in properties file.");
                    }
                    conn = DriverManager.getConnection(url, user, pwd);
                }
            }
        } catch (Exception e) {
            throw new MyException("Failed to initialize DatabaseManager: " + e.getMessage(), e);
        }
    }

    public static DatabaseManager getInstance() throws MyException {
        DatabaseManager current = instance.get();
        if (current == null) {
            current = new DatabaseManager();
            if (!instance.compareAndSet(null, current)) {
                // Another thread set the instance, use that one
                current = instance.get();
            }
        }
        return current;
    }

    public Connection getConnection() {
        return conn;
    }

    public void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("[ERROR] Exception while closing connection: " + e.getMessage());
            }
        }
    }

    public int updateQuery(String sql) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            return stmt.executeUpdate(sql);
        }
    }

    /**
     * This is a simple test main method to demonstrate the usage of
     * DatabaseManager. It establishes a connection to the database, executes a
     * query, and closes the connection.
     */
    public static void main(String[] args) {
        try {
            DatabaseManager dbManager = DatabaseManager.getInstance();
            System.out.println("Database connection established successfully.");

            // Example query execution
            try (Statement stmt = dbManager.getConnection().createStatement()) {
                String sql = "SELECT person_id, CONCAT(first_name, ' ', last_name) as fullname FROM persons WHERE person_id = 1";
                ResultSet resultSet = stmt.executeQuery(sql);
                while (resultSet.next()) {
                    int id = resultSet.getInt("person_id");
                    String fullname = resultSet.getString("fullname");
                    System.out.println("ID: " + id + ", Full Name: " + fullname);
                }
            }

            dbManager.close();
            System.out.println("Database connection closed.");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

}
