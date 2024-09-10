package org.example.entity;

public class Factura {

    private int facturaId;
    private int clienteId;

    public Factura(int facturaId, int clienteId) {
        this.facturaId = facturaId;
        this.clienteId = clienteId;
    }

    public int getInvoiceId() {
        return facturaId;
    }

    public void setInvoiceId(int invoiceId) {
        this.facturaId = invoiceId;
    }

    public int getClientId() {
        return clienteId;
    }

    public void setClientId(int clientId) {
        this.clienteId = clientId;
    }

    @Override
    public String toString() {
        return "Factura [clienteId=" + clienteId + ", facturaId=" + facturaId + "]";
    }
}
