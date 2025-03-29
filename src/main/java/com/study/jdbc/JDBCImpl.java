package com.study.jdbc;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Properties;

import com.google.gson.Gson;

public class JDBCImpl {

    private String url = null;
    private String user = null;
    private String pwd = null;
    private JDBCDaoImpl jdbcDaoImpl = null;

    /**
     * Main function to demonstrate the JDBC Statement and Prepared Statement. The
     * {@link #setDBCreds()} function is used to set the JDBC connection properties,
     * then the {@link JDBCDaoImpl} is instantiated with the connection properties.
     * The {@link #implementJDBCStatement()} and
     * {@link #implementJDBCPreparedStatement(int)} functions are used to
     * demonstrate the Statement and Prepared Statement respectively. The results of
     * the query are printed to the console.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            JDBCImpl jdbc = new JDBCImpl();
            jdbc.setDBCreds();
            jdbc.jdbcDaoImpl = new JDBCDaoImpl(jdbc.url, jdbc.user, jdbc.pwd);
            System.out.println("Implements JDBC Statement");
            jdbc.implementJDBCStatement();
            System.out.println("Implements JDBC PreparedStatement");
            jdbc.implementJDBCPreparedStatement(1);
            // There is a CallableStatement that can be implemented as well
            // Will implement later
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Executes a SQL query to retrieve all persons from the database and prints the
     * results to the console. The query selects the person_id and fullname from the
     * persons table.
     * 
     * @throws SQLException if a database access error occurs
     */
    public void implementJDBCStatement() throws SQLException {
        String sql = "SELECT person_id, CONCAT(first_name, ' ', last_name) as fullname FROM persons";
        jdbcDaoImpl.statementExecuteQuery(sql);
    }

    /**
     * Executes a prepared SQL query to retrieve a person from the database by their
     * ID and prints the results to the console. The query selects the person_id and
     * fullname from the persons table where the person_id matches the specified ID.
     * 
     * @param id the ID of the person to retrieve
     * @throws SQLException if a database access error occurs
     */
    public void implementJDBCPreparedStatement(int id) throws SQLException {
        String sql = "SELECT person_id, CONCAT(first_name, ' ', last_name) as fullname FROM persons WHERE person_id = ?";
        jdbcDaoImpl.prepareStatementExecuteQuery(sql, id);
    }

    /**
     * Reads the database connection properties from a properties file and sets the
     * instance variables url, user, and pwd. The properties file is identified by
     * the resource file "config.json" which is loaded using the class loader. The
     * properties file must contain the following entries:
     * <ul>
     * <li>db.url - the URL of the database server</li>
     * <li>db.user - the username to use for the database connection</li>
     * <li>db.pwd - the password to use for the database connection</li>
     * </ul>
     * If the properties file cannot be read, an IOException is thrown.
     */
    private void setDBCreds() throws IOException {
        String filePath = getCredentialsFilePathFromResource("config.json");
        try (InputStream inStream = Files.newInputStream(Path.of(filePath));) {
            Properties props = new Properties();
            props.load(inStream);
            setDbProperties(
                    props.getProperty("db.url"),
                    props.getProperty("db.user"),
                    props.getProperty("db.pwd"));
        } catch (IOException e) {
            System.out.println("[ERROR] Exception while Reading properties file: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Sets the database connection properties from the given parameters.
     * 
     * @param url  the URL of the database server
     * @param user the username to use for the database connection
     * @param pwd  the password to use for the database connection
     */
    private void setDbProperties(String url, String user, String pwd) {
        this.url = url;
        this.user = user;
        this.pwd = pwd;
    }

    /**
     * Reads the database connection properties from a properties file and returns
     * the value of the "db_credentials_path" entry. The properties file is
     * identified by the resource file parameter. If the properties file cannot be
     * read, an IOException is thrown.
     * 
     * @param resourceFile the resource file to use when loading the properties
     *                     file
     * @return the value of the "db_credentials_path" entry in the properties file
     * @throws IOException if the properties file cannot be read
     */
    private String getCredentialsFilePathFromResource(String resourceFile) {
        String filePath = null;
        try {
            URL resourceUrl = getClass().getClassLoader().getResource(resourceFile);
            File file = Paths.get(resourceUrl.toURI()).toFile();
            Properties properties = new Gson().fromJson(new FileReader(file), Properties.class);
            filePath = properties.getProperty("db_credentials_path");
        } catch (Exception e) {
            System.out.println("[ERROR] Exception while Reading config.json file: " + e.getMessage());
            e.printStackTrace();
        }
        return filePath;
    }
}
