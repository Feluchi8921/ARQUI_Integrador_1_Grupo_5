package org.example.dao;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.entity.Cliente;
import org.example.entity.Factura;
import org.example.factory.ConexionMySQL;

import java.sql.*;
import java.util.Optional;

public class FacturaDAO implements DAO<Factura> {
    private Connection conn;

    public FacturaDAO(Connection connect) {
    }

    /*-------------Creacion de tabla---------------*/

    @Override
    public void createTable() throws SQLException {
        conn = ConexionMySQL.getInstance().connect();
        String sql = "CREATE TABLE IF NOT EXISTS factura (" +
                "idFactura INT PRIMARY KEY, " +
                "idCliente INT, " +
                "FOREIGN KEY (idCliente) REFERENCES cliente(idCliente))";

        conn.prepareStatement(sql).execute();
        conn.commit();
        ConexionMySQL.getInstance().closeConn();
    }

    /*--------------------CRUD------------------*/
    @Override
    public void cargar(CSVParser datos) throws SQLException {
        conn = ConexionMySQL.getInstance().connect();
        String sql = "INSERT INTO factura (idFactura, idCliente) VALUES (?, ?)";

        for(CSVRecord fila: datos) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(fila.get("idFactura")));
            ps.setInt(2, Integer.parseInt(fila.get("idCliente")));
            ps.executeUpdate();
            conn.commit();
            ps.close();
        }
        ConexionMySQL.getInstance().closeConn();
    }

    @Override
    public Factura get(int pk) {
        String query = "SELECT * FROM factura f WHERE f.idCliente = ?";
        Factura facturaById = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexionMySQL.getInstance().connect();
            ps = conn.prepareStatement(query);
            ps.setInt(1, pk);  // Establecer el parámetro en la consulta SQL
            rs = ps.executeQuery();
            if (rs.next()) { // Verificar si hay resultados
                int idFactura = rs.getInt("idCliente");

                // Crear una nueva instancia de Cliente con los datos recuperados de la consulta
                facturaById = new Factura(pk, idFactura);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                conn.commit();
                ConexionMySQL.getInstance().closeConn();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return facturaById;
    }


    @Override
    public void update(Factura factura, String[] params) {
        String query = "UPDATE factura SET idCliente = ? WHERE idFactura = ?";
        PreparedStatement ps = null;

        try {
            conn = ConexionMySQL.getInstance().connect();
            ps = conn.prepareStatement(query);
            ps.setString(1, params[0]);  // Se toma el primer parámetro para el idCliente
            ps.setInt(2, factura.getInvoiceId());  // El ID del factura que se desea actualizar
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();  // En caso de error, se realiza un rollback
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                ConexionMySQL.getInstance().closeConn();  // Cerrar la conexión
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(Factura factura) {
        String query = "DELETE FROM factura WHERE idFactura = ?";
        PreparedStatement ps = null;
        try {
            conn = ConexionMySQL.getInstance().connect();
            ps = conn.prepareStatement(query);
            ps.setInt(1, factura.getInvoiceId());  // Se usa el idFactura del objeto Factura para eliminarlo
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();  // En caso de error, se realiza un rollback
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                ConexionMySQL.getInstance().closeConn();  // Cerrar la conexión
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
