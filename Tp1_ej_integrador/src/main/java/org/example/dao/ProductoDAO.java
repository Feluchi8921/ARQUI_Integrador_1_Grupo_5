package org.example.dao;
import org.example.dto.ProductoQueMasRecaudoDTO;
import org.example.entity.Cliente;
import org.example.entity.Producto;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.factory.ConexionMySQL;

import java.sql.*;
import java.util.Optional;

public class ProductoDAO implements DAO<Producto> {
    private Connection conn;

    public ProductoDAO(Connection conn) {
    }

    /*-------------Creacion de tabla---------------*/
    @Override
    public void createTable() throws SQLException {
        conn = ConexionMySQL.getInstance().connect();
        String sql = "CREATE TABLE IF NOT EXISTS producto (" +
                "idProducto INT PRIMARY KEY, " +
                "nombre VARCHAR(255), " +
                "valor DECIMAL(10, 2))";

        conn.prepareStatement(sql).execute();
        conn.commit();
        ConexionMySQL.getInstance().closeConn();
    }


    /*--------------------CRUD-------------------*/
    @Override
    public void cargar(CSVParser datos) throws SQLException {
        conn = ConexionMySQL.getInstance().connect();
        String sql = "INSERT INTO producto (idProducto, nombre, valor) VALUES (?, ?, ?)";

        for(CSVRecord fila: datos) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(fila.get("idProducto")));
            ps.setString(2, fila.get("nombre"));
            ps.setFloat(3, Float.parseFloat(fila.get("valor")));
            ps.executeUpdate();
            conn.commit();
            ps.close();
        }
        ConexionMySQL.getInstance().closeConn();
    }

    @Override
    public Producto get(int pk) {
        String query = "SELECT p.nombre, p.valor FROM producto p WHERE p.idProducto = ?";
        Producto productoById = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexionMySQL.getInstance().connect();
            ps = conn.prepareStatement(query);
            ps.setInt(1, pk);  // Establecer el parámetro en la consulta SQL
            rs = ps.executeQuery();
            if (rs.next()) { // Verificar si hay resultados
                String nombre = rs.getString("nombre");
                Float email = rs.getFloat("valor");

                // Crear una nueva instancia de Producto con los datos recuperados de la consulta
                productoById = new Producto(pk, nombre, email);
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
        return productoById;
    }


    @Override
    public void update(Producto producto, String[] params) {
        String query = "UPDATE cliente SET nombre = ?, valor = ? WHERE idProducto = ?";
        PreparedStatement ps = null;

        try {
            conn = ConexionMySQL.getInstance().connect();
            ps = conn.prepareStatement(query);
            ps.setString(1, params[0]);  // Se toma el primer parámetro para el nombre
            ps.setFloat(2, Float.parseFloat(params[1]));  // Se toma el segundo parámetro para el valor
            ps.setInt(3, producto.getProductId());  // El ID del producto que se desea actualizar
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
    public void delete(Producto producto) {
        String query = "DELETE FROM producto WHERE idProducto = ?";
        PreparedStatement ps = null;
        try {
            conn = ConexionMySQL.getInstance().connect();
            ps = conn.prepareStatement(query);
            ps.setInt(1, producto.getProductId());  // Se usa el idProducto del objeto Producto para eliminarlo
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

    public ProductoQueMasRecaudoDTO obtenerProductoConMayorRecaudacion() throws SQLException {
        conn = ConexionMySQL.getInstance().connect();

        ProductoQueMasRecaudoDTO productoConMayorRecaudacion = null;

        String consulta = "SELECT p.idProducto, p.nombre, SUM(p.valor * fp.cantidad) AS recaudo " +
                "FROM producto p " +
                "JOIN facturaProducto fp ON (p.idProducto = fp.idProducto) " +
                "GROUP BY p.idProducto " +
                "ORDER BY recaudo DESC " +
                "LIMIT 1";

        // Preparo consulta
        PreparedStatement query = conn.prepareStatement(consulta);
        ResultSet resultado = query.executeQuery();

        // Itero el resultado
        while (resultado.next()) {
            productoConMayorRecaudacion = new ProductoQueMasRecaudoDTO(
                    resultado.getInt(1),         // ID del producto
                    resultado.getString(2),      // Nombre del producto
                    resultado.getFloat(3)        // Recaudación total
            );
        }

        query.close();
        resultado.close();

        ConexionMySQL.getInstance().closeConn();

        return productoConMayorRecaudacion;
    }




}
