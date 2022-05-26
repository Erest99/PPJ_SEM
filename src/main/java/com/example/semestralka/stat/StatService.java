package com.example.semestralka.stat;

import com.example.semestralka.SemestralkaApplication;
import com.example.semestralka.mesto.Mesto;
import com.example.semestralka.mesto.MestoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StatService {

    private final StatRepository statRepository;

    Logger logger = LoggerFactory.getLogger(StatService.class);



    @Autowired
    public StatService( StatRepository statRepository) {
        this.statRepository = statRepository;
    }


    public List<Stat> getStates()
    {
        return statRepository.findAll();
    }


    public void addNewState(Stat state) {
        Optional<Stat> stat =  statRepository.findById(state.getTag());
        if(stat.isPresent())
        {
            logger.error("přidání státu: stát již existuje");
        }
        statRepository.save(state);


    }

    public void deleteStat(String tag) {
        statRepository.findById(tag);
        boolean exists = statRepository.existsById(tag);
        if(!exists)
        {
            logger.error("mazání státu: stát s id: "+ tag+ " neexistuje");
        }
        statRepository.deleteById(tag);
    }

    @Transactional
    public void updateStat(String tag, String name  ) {

        Stat stat = statRepository.findById(tag).orElseThrow(() -> new IllegalStateException("aktualizace státu: stát s tagem "+tag+" neexistuje"));
        if(name != null&&name.length() > 0 && !Objects.equals(stat.getName(),name))
        {
            stat.setName(name);
        }else logger.warn("aktualizace státu: neplatný název "+ name);

    }
}
