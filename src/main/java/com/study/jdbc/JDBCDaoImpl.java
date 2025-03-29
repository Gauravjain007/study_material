package com.study.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCDaoImpl {

    private String url = null;
    private String user = null;
    private String pwd = null;

    /**
     * Constructs a new JDBCDaoImpl object with the specified database connection
     * 
     * @param url
     * @param user
     * @param pwd
     */
    public JDBCDaoImpl(String url, String user, String pwd) {
        this.url = url;
        this.user = user;
        this.pwd = pwd;
    }

    /**
     * Executes a SQL query using a Statement object and prints the results. The
     * query is expected to retrieve a result set containing the "person_id" and
     * "fullname" of persons from the database. The method prints each record's ID
     * and name, and finally prints the total number of records retrieved.
     *
     * @param sql the SQL query to be executed
     * @throws SQLException if a database access error occurs or the SQL statement
     *                      is invalid
     */
    void statementExecuteQuery(String sql) throws SQLException {
        try (Connection con = DriverManager.getConnection(url, user, pwd);
                Statement stmt = con.createStatement();) {
            ResultSet rs = stmt.executeQuery(sql);
            int records = 0;
            while (rs.next()) {
                records++;
                int id = rs.getInt("person_id");
                String name = rs.getString("fullname");
                System.out.println("ID: " + id + ", Name: " + name);
            }
            System.out.println("Total Records: " + records);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Executes a prepared SQL query using the provided SQL string and ID parameter.
     * The query is expected to retrieve a result set containing the "fullname" of a
     * person
     * with the specified ID from the database. The method prints each record's ID
     * and name,
     * and finally prints the total number of records retrieved.
     *
     * @param sql the SQL query to be executed, with a placeholder for the ID
     *            parameter
     * @param id  the ID to set in the SQL query for fetching the relevant record
     * @throws SQLException if a database access error occurs or the SQL statement
     *                      is invalid
     */

    public void prepareStatementExecuteQuery(String sql, int id) throws SQLException {
        try (Connection con = DriverManager.getConnection(url, user, pwd);
                PreparedStatement stmt = con.prepareStatement(sql);) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            int records = 0;
            while (rs.next()) {
                records++;
                String name = rs.getString("fullname");
                System.out.println("ID: " + id + ", Name: " + name);
            }
            System.out.println("Total Records: " + records);
        }
    }
}
