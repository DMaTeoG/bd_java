package com.ucc.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ucc.Connection.DatabaseConnection;
import com.ucc.model.Actor;

public class ActorRepository implements IRepository{

    private Connection getConnection() throws SQLException{
        return DatabaseConnection.getInstanceConnection();
    }


    @Override
    public List<Actor> findAll() throws SQLException{
        List<Actor> actors = new ArrayList<>();
        try (Statement myStat = getConnection().createStatement();
            ResultSet myRes= myStat.executeQuery("Select * from sakila.actor")) {
            while (myRes.next()) {
                Actor newActor = new Actor();
                newActor.setActor_id(myRes.getInt("actor_id"));
                newActor.setFirst_name(myRes.getString("first_name"));
                newActor.setLast_name(myRes.getString("last_name"));
                actors.add(newActor);
            }
        } 
        return actors;
    }

    @Override
    public Actor save(Actor actor) throws SQLException {
        String sql = "INSERT INTO sakila.actor(actor_id,first_name,last_name) VALUES (?,?,?)";
        try(PreparedStatement myPrepare = getConnection().prepareStatement(sql);  ){
            myPrepare.setInt(1, actor.getActor_id() );
            myPrepare.setString(2, actor.getFirst_name() );
            myPrepare.setString(3,actor.getLast_name() );    
            myPrepare.executeUpdate();
        }
        return actor;
    }
    
    @Override
    public Actor update(int currentActorId, Actor actor) throws SQLException {
        String sql = "UPDATE sakila.actor SET actor_id = ?, first_name = ?, last_name = ? WHERE actor_id = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, actor.getActor_id());
            ps.setString(2, actor.getFirst_name());
            ps.setString(3, actor.getLast_name());
            ps.setInt(4, currentActorId);
        if (ps.executeUpdate() == 0) {
            throw new SQLException("No existe actor con id " + currentActorId);
        }
    }
    return actor;
}
    @Override
    public boolean delete(int actorId) throws SQLException {
    String sql = "DELETE FROM sakila.actor WHERE actor_id = ?";
    try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
        ps.setInt(1, actorId);
        return ps.executeUpdate() > 0;
    }
}
   
@Override
public List<Actor> findByIdOrName(Integer actorId, String name) throws SQLException {
    if (actorId == null && (name == null || name.isBlank())) {
        return findAll();
    } 
    List<Actor> actors = new ArrayList<>();
    String sql = "SELECT * FROM sakila.actor WHERE 1=1";
    if (actorId != null) {
        sql += " AND actor_id = ?";
    }
    if (name != null && !name.isBlank()) {
        sql += " AND (first_name LIKE ? OR last_name LIKE ?)";
    }
    try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
        int index = 1;
        if (actorId != null) {
            ps.setInt(index++, actorId);
        }
        if (name != null && !name.isBlank()) {
            ps.setString(index++, "%" + name + "%");
            ps.setString(index, "%" + name + "%");
        }
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Actor actor = new Actor();
                actor.setActor_id(rs.getInt("actor_id"));
                actor.setFirst_name(rs.getString("first_name"));
                actor.setLast_name(rs.getString("last_name"));
                actors.add(actor);
                 }
            }
        }
     return actors;
    }
}
