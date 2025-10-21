package com.ucc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ucc.Connection.DatabaseConnection;
import com.ucc.model.Actor;
import com.ucc.repository.ActorRepository;
import com.ucc.repository.IRepository;

public class Main {
    public static void main(String[] args) {
    try (Connection ignored = DatabaseConnection.getInstanceConnection()) {
        IRepository repo = new ActorRepository();

        Actor actor = new Actor();
        actor.setActor_id(999);
        actor.setFirst_name("PepitoCode2");
        actor.setLast_name("pepitoCode2");
        repo.save(actor);

        Actor mod = new Actor();
        mod.setActor_id(1000);
        mod.setFirst_name("NuevoNombre");
        mod.setLast_name("NuevoApellido");
        repo.update(999, mod);

        repo.findByIdOrName(1000, null).forEach(System.out::println);
        repo.findByIdOrName(null, "nuevo").forEach(System.out::println);

        if (repo.delete(1000)) {
            System.out.println("Actor borrado");
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
        }       
}