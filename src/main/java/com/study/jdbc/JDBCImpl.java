package com.study.jdbc;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCImpl {

    private static final String FILE_PATH = "E:\\BE Development\\database\\db_creds.properties.txt";
    private static String url = null;
    private static String user = null;
    private static String pwd = null;

    /**
     * Retrieves database connection properties from a file, connects to the
     * database, and executes a SQL query to retrieve person records. The query is
     * executed using a Statement object.</br>
     *
     * @param args the command line arguments
     * @throws SQLException if a database access error occurs
     */
    public static void main(String[] args) {
        getDBCreds();
        try (Connection con = DriverManager.getConnection(url, user, pwd);
                Statement stmt = con.createStatement();) {
            String sql = "SELECT person_id, CONCAT(first_name, ' ', last_name) as fullname FROM persons";
            ResultSet rs = stmt.executeQuery(sql);
            int records = 0;
            while (rs.next()) {
                records++;
                int id = rs.getInt("person_id");
                String name = rs.getString("fullname");
                System.out.println("ID: " + id + ", Name: " + name); // Print result on console
            }
            System.out.println("Total Records: " + records);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves database connection properties from a file. The file is expected to
     * be named
     * "db_creds.properties.txt" and should be located in the same location as this
     * class.
     * The file should contain the following properties:</br>
     * <ul>
     * <li>db.url - the URL of the database</li>
     * <li>db.user - the user name to use when connecting to the database</li>
     * <li>db.pwd - the password to use when connecting to the database</li>
     * </ul>
     */
    private static void getDBCreds() {
        try (InputStream inStream = Files.newInputStream(Path.of(FILE_PATH));) {
            Properties props = new Properties();
            props.load(inStream);
            url = props.getProperty("db.url");
            user = props.getProperty("db.user");
            pwd = props.getProperty("db.pwd");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
