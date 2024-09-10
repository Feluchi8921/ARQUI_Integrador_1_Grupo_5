package org.example.dao;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.entity.FacturaProducto;
import org.example.entity.Producto;
import org.example.factory.ConexionMySQL;


import java.sql.*;
import java.util.Optional;

public class FacturaProductoDAO implements DAO<FacturaProducto> {
    private Connection conn;

    public FacturaProductoDAO(Connection connect) {
    }

    /*-------------Creacion de tabla---------------*/
    @Override
    public void createTable() throws SQLException {
        conn = ConexionMySQL.getInstance().connect();
        String sql = "CREATE TABLE IF NOT EXISTS facturaProducto (" +
                "idFactura INT, " +
                "idProducto INT, " +
                "cantidad INT, " +
                "PRIMARY KEY (idFactura, idProducto), " +
                "FOREIGN KEY (idFactura) REFERENCES factura(idFactura), " +
                "FOREIGN KEY (idProducto) REFERENCES producto(idProducto))";

        conn.prepareStatement(sql).execute();
        conn.commit();
        ConexionMySQL.getInstance().closeConn();
    }

    /*--------------------CRUD-------------------*/

    @Override
    public void cargar(CSVParser datos) throws SQLException {
        conn = ConexionMySQL.getInstance().connect();
        String sql = "INSERT INTO facturaProducto (idFactura, idProducto, cantidad) VALUES (?, ?, ?)";

        for(CSVRecord fila: datos) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(fila.get("idFactura")));
            ps.setInt(2, Integer.parseInt(fila.get("idProducto")));
            ps.setInt(3, Integer.parseInt(fila.get("cantidad")));
            ps.executeUpdate();
            conn.commit();
            ps.close();
        }
        ConexionMySQL.getInstance().closeConn();
    }


    @Override
    public FacturaProducto get(int pk) throws SQLException {
        conn = ConexionMySQL.getInstance().connect();
        String sql = "SELECT * FROM facturaProducto WHERE idFactura = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, pk);

        ResultSet rs = ps.executeQuery();
        FacturaProducto facturaProducto = null;

        if (rs.next()) {
            facturaProducto = new FacturaProducto(
                    rs.getInt("idFactura"),
                    rs.getInt("idProducto"),
                    rs.getInt("cantidad")
            );
        }

        rs.close();
        ps.close();
        ConexionMySQL.getInstance().closeConn();

        return facturaProducto;
    }


    @Override
    public void update(FacturaProducto facturaProducto, String[] params) throws SQLException {
        conn = ConexionMySQL.getInstance().connect();
        String sql = "UPDATE facturaProducto SET cantidad = ? WHERE idFactura = ? AND idProducto = ?";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, facturaProducto.getCantidad());  // El nuevo valor de la cantidad
        ps.setInt(2, facturaProducto.getFacturaId()); // El valor del idFactura
        ps.setInt(3, facturaProducto.getProductoId()); // El valor del idProducto

        ps.executeUpdate();
        conn.commit();
        ps.close();
        ConexionMySQL.getInstance().closeConn();
    }

    @Override
    public void delete(FacturaProducto facturaProducto) throws SQLException {
        conn = ConexionMySQL.getInstance().connect();
        String sql = "DELETE FROM facturaProducto WHERE idFactura = ? AND idProducto = ?";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, facturaProducto.getFacturaId());
        ps.setInt(2, facturaProducto.getProductoId());

        ps.executeUpdate();
        conn.commit();
        ps.close();
        ConexionMySQL.getInstance().closeConn();
    }


}

