package com.company.levelupservice.dao;

import com.company.levelupservice.dto.LevelUp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LevelUpDaoJdbcTemplateImplTest {

    @Autowired
    LevelUpDao levelUpDao;

    @Before
    public void setUp() throws Exception {
        List<LevelUp> levelUpList = levelUpDao.getAllLevelUps();

        for(LevelUp levelUp : levelUpList){
            levelUpDao.deleteLevelUp(levelUp.getLevelUpId());
        }
    }

    @Test
    public void addGetDeleteLevelUp() {
        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(1);
        levelUp.setPoints(10);
        levelUp.setMemberDate(LocalDate.of(2019,8,13));

        levelUp = levelUpDao.addLevelUp(levelUp);

        LevelUp levelUp1 = levelUpDao.getLevelUpById(levelUp.getLevelUpId());

        assertEquals(levelUp,levelUp1);


        levelUpDao.deleteLevelUp(levelUp.getLevelUpId());

        levelUp1 = levelUpDao.getLevelUpById(levelUp.getLevelUpId());

        assertNull(levelUp1);
    }

    @Test
    public void getAllLevelUps() {
        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(1);
        levelUp.setPoints(10);
        levelUp.setMemberDate(LocalDate.of(2019,8,13));

        levelUpDao.addLevelUp(levelUp);

        levelUp = new LevelUp();
        levelUp.setCustomerId(2);
        levelUp.setPoints(30);
        levelUp.setMemberDate(LocalDate.of(2019,1,13));

        levelUpDao.addLevelUp(levelUp);

        List<LevelUp> levelUpList = levelUpDao.getAllLevelUps();

        assertEquals(levelUpList.size(),2);
    }

    @Test
    public void updateLevelUp() {
        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(1);
        levelUp.setPoints(10);
        levelUp.setMemberDate(LocalDate.of(2019,8,13));

        levelUp = levelUpDao.addLevelUp(levelUp);

        levelUp.setCustomerId(2);
        levelUp.setPoints(30);
        levelUp.setMemberDate(LocalDate.of(2019,1,13));

        levelUpDao.updateLevelUp(levelUp);

        LevelUp levelUp1 = levelUpDao.getLevelUpById(levelUp.getLevelUpId());
        assertEquals(levelUp,levelUp1);
    }

    @Test
    public void getLevelUpPointsByCustomerId(){
        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(1);
        levelUp.setPoints(10);
        levelUp.setMemberDate(LocalDate.of(2019,8,13));

        levelUp = levelUpDao.addLevelUp(levelUp);

        int levelUpPoint = levelUpDao.getLevelUpPointsByCustomerId(1);

        assertEquals(new Long(levelUp.getPoints()),new Long(levelUpPoint));
    }

}