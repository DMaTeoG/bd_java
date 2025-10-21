package com.ucc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ucc.connection.DatabaseConnection;
import com.ucc.repository.ActorRepository;
import com.ucc.repository.IRepository;

public class Main {
    public static void main(String[] args) throws SQLException {
       
        try(Connection myConn = DatabaseConnection.getInstanceConnection()){
            IRepository actorRepository = new ActorRepository();
            actorRepository.findAll().forEach(System.out::println);

        } catch (Exception e) {
            System.out.println("Conexion Fail");
        } 
    }
}   