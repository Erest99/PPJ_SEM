package com.example.semestralka.pocasi;


import com.example.semestralka.mesto.Mesto;
import com.example.semestralka.mesto.MestoService;
import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
//@Controller
@RequestMapping("api/v1/pocasi")
public class PocasiController {

    @Autowired
    private RestTemplate restTemplate;

    private final MestoService mestoService;
    private final PocasiService pocasiService;

    @Autowired
    public PocasiController(PocasiService pocasiService, MestoService mestoService) {
        this.pocasiService = pocasiService;
        this.mestoService = mestoService;
    }

    @GetMapping("/current_{mesto}_{tag}")
    public Pocasi showCurrentPocasiFor(@PathVariable("mesto") String mesto,@PathVariable("tag") String tag)
    {
        return pocasiService.getPocasiInCity(mesto,tag);
    }

    @GetMapping("/current")
    public List<Pocasi> showCurrentPocasi()
    {
        return pocasiService.getCurrentPocasi();
    }

    @GetMapping("/avg_{interval}")
    public List<Pocasi> showCurrentPocasi(@PathVariable Integer interval)
    {
        return pocasiService.getAVGofPocasi(interval);
    }

    @PostMapping
    public void savePocasi(@RequestBody Pocasi pocasi) {


        pocasiService.addNewPocasi(pocasi);

    }

}
