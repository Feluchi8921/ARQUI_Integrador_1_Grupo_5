package org.example.entity;

public class FacturaProducto {

    private int facturaId;
    private int productoId;
    private int cantidad;

    public FacturaProducto(int facturaId, int productoId, int cantidad) {
        this.facturaId = facturaId;
        this.productoId = productoId;
        this.cantidad = cantidad;
    }

    public int getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(int invoiceId) {
        this.facturaId = invoiceId;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productId) {
        this.productoId = productId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setQuantity(int quantity) {
        this.cantidad = quantity;
    }

    @Override
    public String toString() {
        return "FacturaProducto [id Factura=" + facturaId + ", id Producto=" + productoId + ", cantidad=" + cantidad
                + "]";
    }
}
