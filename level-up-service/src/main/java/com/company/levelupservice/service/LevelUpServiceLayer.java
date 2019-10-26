package com.company.levelupservice.service;

import com.company.levelupservice.dao.LevelUpDao;
import com.company.levelupservice.dto.LevelUp;
import com.company.levelupservice.view.LevelUpViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LevelUpServiceLayer {

    private LevelUpDao levelUpDao;
    @Autowired
    public LevelUpServiceLayer(LevelUpDao levelUpDao){
        this.levelUpDao=levelUpDao;
    }

    //save levelUp
    public LevelUpViewModel saveLevelUp(LevelUpViewModel levelUpViewModel){
        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(levelUpViewModel.getCustomerId());
        levelUp.setPoints(levelUpViewModel.getPoints());
        levelUp.setMemberDate(levelUpViewModel.getMemberDate());

        levelUp = levelUpDao.addLevelUp(levelUp);

        levelUpViewModel.setLevelUpId(levelUp.getLevelUpId());

        return levelUpViewModel;
    }

    //find levelUp by id
    public LevelUpViewModel findLevelUp(int id){
        LevelUp levelUp = levelUpDao.getLevelUpById(id);

        if(levelUp == null){
            return null;
        } else{
            return buildLevelUpViewModel(levelUp);
        }
    }

    //find all levelUps
    public List<LevelUpViewModel> findAllLevelUps(){
        List<LevelUp> levelUpList = levelUpDao.getAllLevelUps();
        List<LevelUpViewModel> lvmList = new ArrayList<>();

        levelUpList.stream()
                .forEach(
                        levelUp -> {
                            LevelUpViewModel lvm = buildLevelUpViewModel(levelUp);
                            lvmList.add(lvm);
                        }
                );
        return lvmList;
    }

    //update level up
    public void updateLevelUp(LevelUpViewModel levelUpViewModel){
        LevelUp levelUp = new LevelUp();

        levelUp.setLevelUpId(levelUpViewModel.getLevelUpId());
        levelUp.setCustomerId(levelUpViewModel.getCustomerId());
        levelUp.setPoints(levelUpViewModel.getPoints());
        levelUp.setMemberDate(levelUpViewModel.getMemberDate());

        levelUpDao.updateLevelUp(levelUp);
    }

    //remove level up
    public void removeLevelUp(int id){
        levelUpDao.deleteLevelUp(id);
    }

    //get level up points by customer id
    public int getLevelUpPointsByCustomerId(int id){
        return levelUpDao.getLevelUpPointsByCustomerId(id);
    }


    //helper method
    private LevelUpViewModel buildLevelUpViewModel(LevelUp levelUp){
        LevelUpViewModel levelUpViewModel = new LevelUpViewModel();

        levelUpViewModel.setLevelUpId(levelUp.getLevelUpId());
        levelUpViewModel.setCustomerId(levelUp.getCustomerId());
        levelUpViewModel.setPoints(levelUp.getPoints());
        levelUpViewModel.setMemberDate(levelUp.getMemberDate());

        return levelUpViewModel;
    }

}
