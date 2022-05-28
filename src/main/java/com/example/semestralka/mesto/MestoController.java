package com.example.semestralka.mesto;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Arrays;


@RestController
@RequestMapping("api/v1/mesto")
public class MestoController {


    private final MestoService mestoService;

    @Autowired
    public MestoController(MestoService mestoService) {
        this.mestoService = mestoService;
    }


    @GetMapping
    public List<Mesto> getCities()
    {
        return mestoService.getCities();
    }

    @Profile("normal")
    @PostMapping
    public void registerNewCity(@RequestBody Mesto mesto)
    {
        mestoService.addNewMesto(mesto);
    }

    @Profile("normal")
    @DeleteMapping(path = "{mestoId}")
    public void deleteMesto(@PathVariable("mestoId") Long mestoId)
    {
        mestoService.deleteMesto(mestoId);
    }

    @Profile("normal")
    @PutMapping(path = "{mestoId}")
    public void updateMesto(@PathVariable("mestoId") Long mestoId, @RequestParam(required = false) String name, @RequestParam(required = false)String state)
    {
        mestoService.updateMesto(mestoId,name,state);
    }
}
