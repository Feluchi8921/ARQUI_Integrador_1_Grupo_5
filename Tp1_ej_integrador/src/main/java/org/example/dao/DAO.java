package org.example.dao;

import org.apache.commons.csv.CSVParser;

import java.sql.SQLException;
import java.util.Optional;

public interface DAO<T>{

    void createTable() throws SQLException;

    //CRUD
    void cargar(CSVParser datos) throws SQLException;
    T get(int pk) throws SQLException;

    void update(T t, String[] params) throws SQLException;

    void delete(T t) throws SQLException;



}