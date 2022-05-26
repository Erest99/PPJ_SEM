package com.example.semestralka.stat;


import com.example.semestralka.mesto.Mesto;
import com.example.semestralka.mesto.MestoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/stat")
public class StatController {



    private final StatService statService;

    @Autowired
    public StatController(StatService statService) {
        this.statService = statService;
    }


    @GetMapping
    public List<Stat> getCountries()
    {
        return statService.getStates();
    }

    @Profile("normal")
    @PostMapping
    public void registerNewState(@RequestBody Stat stat)
    {
        statService.addNewState(stat);
    }

    @Profile("normal")
    @DeleteMapping(path = "{tag}")
    public void deleteStat(@PathVariable("tag") String tag)
    {
        statService.deleteStat(tag);
    }

    @Profile("normal")
    @PutMapping(path = "{tag}")
    public void updateStat(@PathVariable("tag") String tag, @RequestParam String name)
    {
        statService.updateStat(tag,name);
    }
}
