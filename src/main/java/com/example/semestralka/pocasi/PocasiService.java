package com.example.semestralka.pocasi;

import com.example.semestralka.mesto.Mesto;
import com.example.semestralka.mesto.MestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PocasiService {

    private final PocasiRepository pocasiRepository;
    private final MestoRepository mestoRepository;

    @Autowired
    public PocasiService(PocasiRepository pocasiRepository, MestoRepository mestoRepository) {
        this.pocasiRepository = pocasiRepository;
        this.mestoRepository = mestoRepository;
    }

    public void addNewPocasi(Pocasi pocasi) {
        pocasi.setTime(LocalDateTime.now());
        pocasiRepository.save(pocasi);
    }

    public List<Pocasi> getCurrentPocasi()
    {
        List<Mesto> ms = mestoRepository.findAll();
        Sys sys;
        List<Pocasi> ps;
        List<Pocasi> r = new ArrayList<>();
        Pocasi np = null;
        for(Mesto m : ms)
        {
            sys = new Sys(m.getState());
            ps = pocasiRepository.findPocasiByNameAndSys(m.getName(),sys);
            LocalDateTime nearest = LocalDateTime.now().minusDays(14);

            for(Pocasi p : ps)
            {
                if(p.getTime().isAfter(nearest))
                {
                    nearest = p.getTime();
                    np =p;
                }
            }
            r.add(np);
        }

        return r;
    }

    public List<Pocasi> getAVGofPocasi(Integer interval)
    {
        List<Mesto> ms = mestoRepository.findAll();
        Sys sys;
        List<Pocasi> ps;
        List<Pocasi> r = new ArrayList<>();
        double sumT = 0;
        int ammountT = 0;
        int sumP = 0;
        int ammountP = 0;
        for(Mesto m : ms)
        {
            sys = new Sys(m.getState());
            ps = pocasiRepository.findPocasiByNameAndSys(m.getName(),sys);
            LocalDateTime nearest = LocalDateTime.now().minusDays(interval);

            for(Pocasi p : ps)
            {
                if(p.getTime().isAfter(nearest))
                {
                    sumT = sumT + p.getMain().getTemp();
                    ammountT++;
                    sumP = sumP+p.getMain().getPressure();
                    ammountP++;
                }
            }
            Main main = new Main(sumT/ammountT,sumP/ammountP);
            r.add(new Pocasi(m.getName(),main,new Sys(m.getState()),LocalDateTime.now()));
        }

        return r;
    }

    public Pocasi getPocasiInCity(String name, String tag)
    {
        Sys sys = new Sys(tag);
        List<Pocasi> ps = pocasiRepository.findPocasiByNameAndSys(name,sys);
        LocalDateTime nearest = LocalDateTime.now().minusDays(14);
        Pocasi np = null;
        for(Pocasi p : ps)
        {
            if(p.getTime().isAfter(nearest))
            {
                nearest = p.getTime();
                np =p;
            }
        }
        return np;
    }

    public List<Pocasi> getExpired(LocalDateTime expDate)
    {
        return pocasiRepository.findByTimeBefore(expDate);
    }

    public void deleteExpired(LocalDateTime expDate) {
        List<Pocasi> ps = pocasiRepository.findByTimeBefore(expDate);
        if(ps.isEmpty()) System.out.println("no expiring records");
        else {
            for (Pocasi p : ps) {
                pocasiRepository.delete(p);

            }
        }
    }
}
