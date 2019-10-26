package com.company.adminapi.util.feign;

import com.company.adminapi.view.LevelUpViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@FeignClient(name = "level-up-service")
public interface LevelUpServiceClient {

    //add level up
    @RequestMapping(value = "/levelup",method= RequestMethod.POST)
    public LevelUpViewModel saveLevelUp(@RequestBody @Valid LevelUpViewModel levelUpViewModel);

    //get all level ups - it was getAllLevelUps but i changed the name
    @RequestMapping(value = "/levelup",method= RequestMethod.GET)
    public List<LevelUpViewModel> findAllLevelUps();

    //get level up
    @RequestMapping(value = "/levelup/{id}",method=RequestMethod.GET)
    public LevelUpViewModel findLevelUpById(@PathVariable("id") int id);

    //update level ups
    @RequestMapping(value = "/levelup/{id}",method=RequestMethod.PUT)
    public void updateLevelUp(@PathVariable("id") int id, @RequestBody @Valid LevelUpViewModel levelUpViewModel);

    //delete level ups
    @RequestMapping(value = "/levelup/{id}",method=RequestMethod.DELETE)
    public void removeLevelUp(@PathVariable("id") int id);

    //get level up poitns by customer id
    @RequestMapping(value = "/levelup/customer/{id}", method = RequestMethod.GET)
    public int getLevelUpPointsByCustomerId(@PathVariable("id") int id);
}
