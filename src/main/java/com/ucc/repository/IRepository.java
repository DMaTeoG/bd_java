package com.ucc.repository;

import java.sql.SQLException;
import java.util.List;

import com.mysql.cj.x.protobuf.MysqlxCrud.Update;
import com.ucc.model.Actor;

public interface IRepository {
    List<Actor> findAll() throws SQLException;
    Actor save(Actor actor) throws SQLException;
    Actor update(int currentActorId, Actor actor) throws SQLException;
    boolean delete(int actorId) throws SQLException;
    List<Actor> findByIdOrName(Integer actorId, String name) throws SQLException;
                                                                                                                                                                                                              
}
