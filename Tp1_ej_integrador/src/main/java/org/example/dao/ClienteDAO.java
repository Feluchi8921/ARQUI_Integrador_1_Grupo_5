package org.example.dao;
import org.example.dto.ClientesFacturacionMayorDTO;
import org.example.entity.Cliente;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.factory.ConexionMySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;


public class ClienteDAO  implements DAO <Cliente> {
    private Connection conn;

    public ClienteDAO(Connection conn) {
    }

    /*-------------Creacion de tabla---------------*/
    @Override
    public void createTable() throws SQLException {
        conn = ConexionMySQL.getInstance().connect();

        String client = "CREATE TABLE IF NOT EXISTS cliente ( " +
                "idCliente INT, " +
                "nombre VARCHAR(500), " +
                "email VARCHAR(150), " +
                "PRIMARY KEY (idCliente)) ";
        conn.prepareStatement(client).execute();
        conn.commit();
        ConexionMySQL.getInstance().closeConn();
    }

    /*--------------------CRUD-------------------*/
    @Override
    public void cargar(CSVParser datos) throws SQLException {
        conn = ConexionMySQL.getInstance().connect();
        String sql = "INSERT INTO cliente (idCliente, nombre, email) VALUES (?, ?, ?)";

        for (CSVRecord fila : datos) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(fila.get("idCliente")));
            ps.setString(2, fila.get("nombre"));
            ps.setString(3, fila.get("email"));
            ps.executeUpdate();
            conn.commit();
            ps.close();
        }
        ConexionMySQL.getInstance().closeConn();
    }
    @Override
    public Cliente get(int pk) {
        String query = "SELECT c.nombre, c.email FROM cliente c WHERE c.idCliente = ?";
        Cliente clienteById = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexionMySQL.getInstance().connect();
            ps = conn.prepareStatement(query);
            ps.setInt(1, pk);  // Establecer el parámetro en la consulta SQL
            rs = ps.executeQuery();
            if (rs.next()) { // Verificar si hay resultados
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");

                // Crear una nueva instancia de Cliente con los datos recuperados de la consulta
                clienteById = new Cliente(pk, nombre, email);
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

        return clienteById;
    }

    @Override
    public void update(Cliente cliente, String[] params) {
        String query = "UPDATE cliente SET nombre = ?, email = ? WHERE idCliente = ?";
        PreparedStatement ps = null;

        try {
            conn = ConexionMySQL.getInstance().connect();
            ps = conn.prepareStatement(query);
            ps.setString(1, params[0]);  // Se toma el primer parámetro para el nombre
            ps.setString(2, params[1]);  // Se toma el segundo parámetro para el email
            ps.setInt(3, cliente.getClientId());  // El ID del cliente que se desea actualizar
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
    public void delete(Cliente cliente) {
        String query = "DELETE FROM cliente WHERE idCliente = ?";
        PreparedStatement ps = null;
        try {
            conn = ConexionMySQL.getInstance().connect();
            ps = conn.prepareStatement(query);
            ps.setInt(1, cliente.getClientId());  // Se usa el idCliente del objeto Cliente para eliminarlo
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

    public ArrayList<ClientesFacturacionMayorDTO> getClientesMayorRecaudacion() throws SQLException{
        conn = ConexionMySQL.getInstance().connect();

        ArrayList<ClientesFacturacionMayorDTO> clients = new ArrayList<ClientesFacturacionMayorDTO>();

        String query = "SELECT c.idCliente, c.nombre, SUM(p.valor * fp.cantidad) AS Facturado " +
                "FROM cliente c " +
                "JOIN factura f ON c.idCliente = f.idCliente " +
                "JOIN facturaProducto fp ON f.idFactura = fp.idFactura " +
                "JOIN producto p ON p.idProducto = fp.idProducto " +
                "GROUP BY c.idCliente " +
                "ORDER BY Facturado DESC";

        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet result = ps.executeQuery();

        while(result.next()){
            ClientesFacturacionMayorDTO client = new ClientesFacturacionMayorDTO(result.getInt(1), result.getString(2), result.getFloat(3));
            clients.add(client);
        }

        conn.commit();
        ConexionMySQL.getInstance().closeConn();
        ps.close();
        result.close();

        return clients;
    }



}
