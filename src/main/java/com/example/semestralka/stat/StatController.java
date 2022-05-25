package com.example.semestralka.stat;


import com.example.semestralka.mesto.Mesto;
import com.example.semestralka.mesto.MestoService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public void registerNewState(@RequestBody Stat stat)
    {
        statService.addNewState(stat);
    }

    @DeleteMapping(path = "{tag}")
    public void deleteStat(@PathVariable("tag") String tag)
    {
        statService.deleteStat(tag);
    }

    @PutMapping(path = "{tag}")
    public void updateStat(@PathVariable("tag") String tag, @RequestParam(required = false) String name)
    {
        statService.updateStat(tag,name);
    }
}
