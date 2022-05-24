package com.example.semestralka.mesto;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping
    public void registerNewCity(@RequestBody Mesto mesto)
    {
        mestoService.addNewMesto(mesto);
    }
    @DeleteMapping(path = "{mestoId}")
    public void deleteMesto(@PathVariable("mestoId") Long mestoId)
    {
        mestoService.deleteMesto(mestoId);
    }

    @PutMapping(path = "{mestoId}")
    public void updateMesto(@PathVariable("mestoId") Long mestoId, @RequestParam(required = false) String name, @RequestParam(required = false)String state)
    {
        mestoService.updateMesto(mestoId,name,state);
    }
}
