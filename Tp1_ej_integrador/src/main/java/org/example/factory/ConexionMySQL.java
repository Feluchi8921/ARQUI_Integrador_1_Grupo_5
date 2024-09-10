package org.example.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.example.dao.ClienteDAO;
import org.example.dao.FacturaDAO;
import org.example.dao.FacturaProductoDAO;
import org.example.dao.ProductoDAO;

public class ConexionMySQL extends AbstractFactory {

    private static Connection conn;
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URI = "jdbc:mysql://localhost:3306/integrador1";
    private static final String USER = "root";
    private static final String PASS = ""; //Ingresar su contraseña si es necesario

    private static ConexionMySQL instance = new ConexionMySQL();

    private ConexionMySQL() {
        // Constructor privado para evitar instanciación
    }

    public static synchronized ConexionMySQL getInstance() {
        if (instance == null) {
            instance = new ConexionMySQL();
        }
        return instance;
    }

    public Connection connect() {
        if (conn == null) {
            try {
                Class.forName(DRIVER);
                conn = DriverManager.getConnection(URI, USER, PASS);
                conn.setAutoCommit(false);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        return conn;
    }

    public void closeConn() {
        if (conn != null) {
            try {
                conn.close();
                conn = null; // Clear the connection reference
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ClienteDAO getDAOClient() throws SQLException {
        return new ClienteDAO(connect());
    }

    @Override
    public FacturaDAO getDAOInvoice() throws SQLException {
        return new FacturaDAO(connect());
    }

    @Override
    public FacturaProductoDAO getDAOInvoiceProduct() throws SQLException {
        return new FacturaProductoDAO(connect());
    }

    @Override
    public ProductoDAO getDAOProduct() throws SQLException {
        return new ProductoDAO(connect());
    }
}
