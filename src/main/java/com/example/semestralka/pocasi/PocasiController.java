package com.example.semestralka.mesto;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Arrays;


@RestController
@RequestMapping("api/v1/mesto")
public class MestoController {

    @Autowired
    private RestTemplate restTemplate;

    private final MestoService mestoService;

    @Autowired
    public MestoController(MestoService mestoService) {
        this.mestoService = mestoService;
    }

    @GetMapping("/country")
    public List<Object> getCountry()
    {
        String url = "http://api.openweathermap.org/data/2.5/weather?q=Prague,cz&APPID=95cb3d154ca2cae3b3327826b3bf1c0e";
        Object[] objects = restTemplate.getForObject(url,Object[].class);

        return Arrays.asList(objects);
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
