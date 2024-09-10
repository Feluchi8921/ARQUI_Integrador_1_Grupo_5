package org.example.factory;

import org.example.dao.ClienteDAO;
import org.example.dao.FacturaDAO;
import org.example.dao.FacturaProductoDAO;
import org.example.dao.ProductoDAO;

import java.sql.SQLException;

public abstract class AbstractFactory {

	public static final int MYSQL_DB = 1;

	public abstract ClienteDAO getDAOClient() throws SQLException;
	public abstract FacturaDAO getDAOInvoice() throws SQLException;
	public abstract FacturaProductoDAO getDAOInvoiceProduct() throws SQLException;
	public abstract ProductoDAO getDAOProduct() throws SQLException;

	public static AbstractFactory getDAOFactory(int factory){
		switch (factory)
		{
			case MYSQL_DB: return ConexionMySQL.getInstance();
			default:return null;
		}
	}

}
