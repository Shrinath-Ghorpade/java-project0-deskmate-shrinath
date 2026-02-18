package com.deskmate.main.dao;

import com.deskmate.main.model.Desk;

import java.util.List;
import java.util.Optional;

public interface DeskDao {
    long insertDesk(String code, String name);
    void deactivateDesk(long deskId);
    Optional<Desk> findByCode(String code);
    Optional<Desk> findById(long deskId);
    List<Desk> listActive();
}
