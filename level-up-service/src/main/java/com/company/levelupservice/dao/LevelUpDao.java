package com.company.levelupservice.dao;

import com.company.levelupservice.dto.LevelUp;

import java.util.List;

public interface LevelUpDao {

    //CRUD + get all
    LevelUp addLevelUp(LevelUp levelUp);

    LevelUp getLevelUpById(int id);

    List<LevelUp> getAllLevelUps();

    LevelUp updateLevelUp(LevelUp levelUp);

    void deleteLevelUp(int id);

    int getLevelUpPointsByCustomerId(int id);
}
