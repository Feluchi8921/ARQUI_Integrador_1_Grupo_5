package org.example.entity;

public class Cliente {
    private int clienteId;
    private String clienteName;
    private String clienteEmail;

    public Cliente(int clienteId, String clienteName, String clienteEmail) {
        this.clienteId = clienteId;
        this.clienteName = clienteName;
        this.clienteEmail = clienteEmail;
    }

    public int getClientId() {
        return clienteId;
    }


    public void setClientId(int clientId) {
        this.clienteId = clientId;
    }

    public String getClientName() {
        return clienteName;
    }

    public void setClientName(String clientName) {
        this.clienteName = clientName;
    }

    public String getClientEmail() {
        return clienteEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clienteEmail = clientEmail;
    }

    @Override
    public String toString() {
        return  "Id= " + clienteId + " , Nombre= " + clienteName + " , Email= " + clienteEmail;
    }


}
