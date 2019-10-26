package com.company.retailqueueconsumer.util.feign;

import com.company.retailqueueconsumer.util.message.LevelUp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "level-up-service")
public interface LevelUpServiceClient {
    //add level up
    @RequestMapping(value = "/levelup",method= RequestMethod.POST)
    public LevelUp saveLevelUp(@RequestBody @Valid LevelUp levelUp);

    //get all level ups - it was getAllLevelUps but i changed the name
    @RequestMapping(value = "/levelup",method= RequestMethod.GET)
    public List<LevelUp> findAllLevelUps();

    //get level up
    @RequestMapping(value = "/levelup/{id}",method=RequestMethod.GET)
    public LevelUp findLevelUpById(@PathVariable("id") int id);

    //update level ups
    @RequestMapping(value = "/levelup/{id}",method=RequestMethod.PUT)
    public void updateLevelUp(@PathVariable("id") int id, @RequestBody @Valid LevelUp levelUp);

    //delete level ups
    @RequestMapping(value = "/levelup/{id}",method=RequestMethod.DELETE)
    public void removeLevelUp(@PathVariable("id") int id);

    //get level up poitns by customer id
    @RequestMapping(value = "/levelup/customer/{id}", method = RequestMethod.GET)
    public int getLevelUpPointsByCustomerId(@PathVariable("id") int id);
}
