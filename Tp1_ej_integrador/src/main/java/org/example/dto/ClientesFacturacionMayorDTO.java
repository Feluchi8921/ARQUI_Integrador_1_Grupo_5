package org.example.dto;

public class ClientesFacturacionMayorDTO {
    private int idcliente;
    private String nombre;
    private float totalFacturado;

    public ClientesFacturacionMayorDTO(int idcliente, String nombre, float totalFacturado) {
        this.idcliente = idcliente;
        this.nombre = nombre;
        this.totalFacturado = totalFacturado;
    }

    public String toString() {
        return  "idCliente= " + idcliente + " , Nombre= " + nombre + " , Total Facturado= $" + totalFacturado;
    }
}
