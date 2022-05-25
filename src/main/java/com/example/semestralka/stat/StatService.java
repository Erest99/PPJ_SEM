package com.example.semestralka.stat;

import com.example.semestralka.mesto.Mesto;
import com.example.semestralka.mesto.MestoRepository;
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
            throw new IllegalStateException("Stát již existuje");
        }
        statRepository.save(state);


    }

    public void deleteStat(String tag) {
        statRepository.findById(tag);
        boolean exists = statRepository.existsById(tag);
        if(!exists)
        {
            throw new IllegalStateException("stát s tagem "+ tag + "neexistuje");
        }
        statRepository.deleteById(tag);
    }

    @Transactional
    public void updateStat(String tag, String name) {

        Stat stat = statRepository.findById(tag).orElseThrow(() -> new IllegalStateException("stát s tagem "+tag+" neexistuje"));
        if(name != null&&name.length() > 0 && !Objects.equals(stat.getName(),name))
        {
            stat.setName(name);
        }
        if(tag != null&&tag.length() > 0 && !Objects.equals(stat.getTag(),tag))
        {
            stat.setTag(tag);
        }

    }
}
