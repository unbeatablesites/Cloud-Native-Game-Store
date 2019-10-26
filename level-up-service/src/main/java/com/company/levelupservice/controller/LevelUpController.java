package com.company.levelupservice.controller;

import com.company.levelupservice.service.LevelUpServiceLayer;
import com.company.levelupservice.view.LevelUpViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
public class LevelUpController {

    @Autowired
    LevelUpServiceLayer service;

    //add level up
    @RequestMapping(value = "/levelup",method= RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public LevelUpViewModel saveLevelUp(@RequestBody @Valid LevelUpViewModel levelUpViewModel){
        return service.saveLevelUp(levelUpViewModel);
    }

    //get all level ups - it was getAllLevelUps but i changed the name
    @RequestMapping(value = "/levelup",method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<LevelUpViewModel> findAllLevelUps(){
        return service.findAllLevelUps();
    }

    //get level up
    @RequestMapping(value = "/levelup/{id}",method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public LevelUpViewModel findLevelUpById(@PathVariable("id") int id){
        LevelUpViewModel levelUpViewModel = service.findLevelUp(id);

        if(levelUpViewModel == null){
            //throw exception. illgal argument
        }
        return levelUpViewModel;
    }

    //update level ups
    @RequestMapping(value = "/levelup/{id}",method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLevelUp(@PathVariable("id") int id, @RequestBody @Valid LevelUpViewModel levelUpViewModel){
        if(id != levelUpViewModel.getLevelUpId()){
            //throw illegal argument
        }
        service.updateLevelUp(levelUpViewModel);
    }

    //delete level ups
    @RequestMapping(value = "/levelup/{id}",method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeLevelUp(@PathVariable("id") int id){
        service.removeLevelUp(id);
    }

    //get level up poitns by customer id
    @RequestMapping(value = "/levelup/customer/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public int getLevelUpPointsByCustomerId(@PathVariable("id") int id){
        int points = service.getLevelUpPointsByCustomerId(id);

        if(points == 0){
            //throw exception. illgal argument
        }
        return points;
    }
}
