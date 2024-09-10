package org.example;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import org.example.dao.ClienteDAO;
import org.example.dao.FacturaDAO;
import org.example.dao.FacturaProductoDAO;
import org.example.dao.ProductoDAO;

import org.example.dto.ClientesFacturacionMayorDTO;

import org.example.entity.Cliente;
import org.example.entity.Factura;
import org.example.factory.AbstractFactory;

public class Main {

    private static ClienteDAO daoClient;
    private static FacturaDAO daoInvoice;
    private static FacturaProductoDAO daoInvoiceProduct;
    private static ProductoDAO daoProduct;
    private static AbstractFactory factory = AbstractFactory.getDAOFactory(AbstractFactory.MYSQL_DB);
    public static void main(String[] args) throws SQLException, IOException {

        instantiationDAOs();
        createTables();
        populateTables();

        System.out.println("\n" + "Producto que más recaudó: " + "\n");
        System.out.println("\t" + daoProduct.obtenerProductoConMayorRecaudacion() + "\n");

        System.out.println("\n" + "Listado de clientes ordenado por mayor facturación: " + "\n");
        for (ClientesFacturacionMayorDTO clientAux : daoClient.getClientesMayorRecaudacion() ) {
            System.out.println("\t" + clientAux);
        }

        System.out.println("-----------------CRUD----------------");
        /*------------------Obtener cliente por Id-----------------------*/
        System.out.println("Obtener cliente por id = 1: "+daoClient.get(1));
        Cliente c = new Cliente(1, "Xantha M. Guzman", "vitae@incursuset.org");

        /*------------------Modificar cliente-----------------------*/
        daoClient.update(c, new String[]{"Sarasa", "sarasa@example.com"});
        System.out.println("Cliente modificado: "+daoClient.get(1));
        daoClient.update(c, new String[]{"Xantha M. Guzman", "vitae@incursuset.org"});
        System.out.println("Vuelvo a los valores dados: "+daoClient.get(1));

        /*------------------Obtener factura por Id-----------------------*/
        System.out.println("La factura con id 1 es: "+daoInvoice.get(1));

        /*------------------Modificar factura-----------------------*/
        Factura f = new Factura(1,1);
        // Actualiza idCliente de la factura con idFactura 1 a 2
        daoInvoice.update(f, new String[]{"2"});
        System.out.println("Factura modificada: " + daoInvoice.get(1));

        // Vuelve a actualizar idCliente de la factura con idFactura 1 a 1
        daoInvoice.update(f, new String[]{"1"});
        System.out.println("Vuelvo a los valores originales: " + daoInvoice.get(1));

    }

    public static void instantiationDAOs() throws SQLException {
        daoClient = factory.getDAOClient();
        daoInvoice = factory.getDAOInvoice();
        daoInvoiceProduct = factory.getDAOInvoiceProduct();
        daoProduct = factory.getDAOProduct();
    }

    public static void createTables() throws SQLException {
        daoClient.createTable();
        daoInvoice.createTable();
        daoProduct.createTable();
        daoInvoiceProduct.createTable();
    }

    public static void populateTables() throws SQLException, IOException {


       CSVParser client = CSVFormat.DEFAULT.withHeader().parse(new FileReader("./Tp1_ej_integrador\\src\\main\\resources\\csv\\clientes.csv"));
        CSVParser invoice = CSVFormat.DEFAULT.withHeader().parse(new FileReader("./Tp1_ej_integrador\\src\\main\\resources\\csv\\facturas.csv"));
        CSVParser invoiceProduct = CSVFormat.DEFAULT.withHeader().parse(new FileReader("./Tp1_ej_integrador\\src\\main\\resources\\csv\\facturas-productos.csv"));
        CSVParser product = CSVFormat.DEFAULT.withHeader().parse(new FileReader("./Tp1_ej_integrador\\src\\main\\resources\\csv\\productos.csv"));
        daoClient.cargar(client);
        daoInvoice.cargar(invoice);
        daoProduct.cargar(product);
        daoInvoiceProduct.cargar(invoiceProduct);
    }
}
