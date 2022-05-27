package com.example.semestralka.pocasi;


import com.example.semestralka.mesto.Mesto;
import com.example.semestralka.mesto.MestoService;
import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;




@RestController
@RequestMapping("api/v1/pocasi")
public class PocasiController {


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
    public List<Pocasi> showAvgPocasi(@PathVariable Integer interval)
    {
        return pocasiService.getAVGofPocasi(interval);
    }

    @Profile("normal")
    @PostMapping
    public void savePocasi(@RequestBody Pocasi pocasi) {
//        pocasi.setId(pocasiService.getSequenceNumber(SEQUENCE_NAME));
        pocasiService.addNewPocasi(pocasi);

    }
    //TODO Zkusit obejít bug, kdy controller anotovaný profilem se spouští v libovolném profilu
    @Profile("normal")
    @PostMapping("/upload")
    public void uploadCSV(@RequestParam("file")MultipartFile file) throws Exception {
        pocasiService.dataUpload(file);
    }


    @RequestMapping(path = "/download")
    public void getAllPocasiInCsv(HttpServletResponse servletResponse) throws IOException {
        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition","attachment; filename=\"pocasi.csv\"");
        pocasiService.dataExport(servletResponse.getWriter());
    }

    @Profile("normal")
    @PostMapping(path = "/add")
    public void insertPocasi(@RequestBody Pocasi pocasi)
    {
//        pocasi.setId(pocasiService.getSequenceNumber(SEQUENCE_NAME));
        pocasiService.addPocasi(pocasi);
    }

    @Profile("normal")
    @DeleteMapping(path = "/del")
    public void deletePocasi(@RequestBody Pocasi pocasi)
    {
        pocasiService.deletePocasi(pocasi);
    }

    @Profile("normal")
    @PutMapping(path = "/upd")
    public void updatePocasi(@RequestBody Pocasi pocasi, @RequestParam Double temp)
    {
        pocasiService.updatePocasi(pocasi,temp);
    }
}
