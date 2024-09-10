package org.example.entity;

public class Producto {
    private int productoId;
    private String name;
    private Float value;

    public Producto(int productoId, String name, Float value) {
        this.productoId = productoId;
        this.name = name;
        this.value = value;
    }

    public int getProductId() {
        return productoId;
    }

    public void setProductId(int productId) {
        this.productoId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "id Producto= " + productoId + ", Nombre= " + name + ", Valor= " + value ;
    }
}