package com.company.levelupservice.service;

import com.company.levelupservice.dao.LevelUpDao;
import com.company.levelupservice.dao.LevelUpDaoJdbcTemplateImpl;
import com.company.levelupservice.dto.LevelUp;
import com.company.levelupservice.view.LevelUpViewModel;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class LevelUpServiceLayerTest {

    LevelUpDao levelUpDao;
    LevelUpServiceLayer levelUpServiceLayer;

    @Before
    public void setUp() throws Exception {
        setUpLevelUpDaoMock();
        levelUpServiceLayer = new LevelUpServiceLayer(levelUpDao);
    }

    private void setUpLevelUpDaoMock(){
        levelUpDao = mock(LevelUpDaoJdbcTemplateImpl.class);

        LevelUp levelUp = new LevelUp();
        levelUp.setLevelUpId(1);
        levelUp.setCustomerId(1);
        levelUp.setPoints(10);
        levelUp.setMemberDate(LocalDate.of(2019,8,13));

        LevelUp levelUp1 = new LevelUp();
        levelUp1.setCustomerId(1);
        levelUp1.setPoints(10);
        levelUp1.setMemberDate(LocalDate.of(2019,8,13));

        List<LevelUp> levelUpList = new ArrayList<>();
        levelUpList.add(levelUp);

        doReturn(levelUp).when(levelUpDao).addLevelUp(levelUp1);
        doReturn(levelUp).when(levelUpDao).getLevelUpById(1);
        doReturn(levelUpList).when(levelUpDao).getAllLevelUps();
        doReturn(10).when(levelUpDao).getLevelUpPointsByCustomerId(1);
    }

    @Test
    public void saveFindLevelUp() {
        LevelUpViewModel levelUpViewModel = new LevelUpViewModel();
        levelUpViewModel.setLevelUpId(1);
        levelUpViewModel.setCustomerId(1);
        levelUpViewModel.setPoints(10);
        levelUpViewModel.setMemberDate(LocalDate.of(2019,8,13));

        LevelUpViewModel fromService = levelUpServiceLayer.findLevelUp(levelUpViewModel.getLevelUpId());

        assertEquals(levelUpViewModel,fromService);
    }

    @Test
    public void findAllLevelUps() {
        LevelUpViewModel levelUpViewModel = new LevelUpViewModel();
        levelUpViewModel.setLevelUpId(1);
        levelUpViewModel.setCustomerId(1);
        levelUpViewModel.setPoints(10);
        levelUpViewModel.setMemberDate(LocalDate.of(2019,8,13));

        List<LevelUpViewModel> fromService = levelUpServiceLayer.findAllLevelUps();
        assertEquals(fromService.size(),1);
        assertEquals(levelUpViewModel, fromService.get(0));
    }

    @Test
    public void getLevelUpPointsByCustomerId(){
        LevelUpViewModel levelUpViewModel = new LevelUpViewModel();
        levelUpViewModel.setLevelUpId(1);
        levelUpViewModel.setCustomerId(1);
        levelUpViewModel.setPoints(10);
        levelUpViewModel.setMemberDate(LocalDate.of(2019,8,13));

        int fromService = levelUpServiceLayer.getLevelUpPointsByCustomerId(1);

        assertEquals(new Long(fromService),new Long(levelUpViewModel.getPoints()));
    }
}