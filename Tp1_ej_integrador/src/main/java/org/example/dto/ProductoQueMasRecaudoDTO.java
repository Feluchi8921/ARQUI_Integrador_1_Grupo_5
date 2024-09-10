package org.example.dto;

public class ProductoQueMasRecaudoDTO {
    private int productId;
    private String name;
    private Float totalCollected;


    public ProductoQueMasRecaudoDTO(int productId, String name, Float totalCollected) {
        this.productId = productId;
        this.name = name;
        this.totalCollected = totalCollected;
    }


    @Override
    public String toString() {
        return "idProducto= " + productId + ", Nombre= " + name + ", Total Recaudado= $" + totalCollected ;
    }
}
