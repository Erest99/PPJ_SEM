package com.example.semestralka.mesto;

import com.example.semestralka.stat.Stat;
import com.example.semestralka.stat.StatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MestoService {

    private final MestoRepository mestoRepository;
    private final StatRepository statRepository;


    @Autowired
    public MestoService(MestoRepository mestoRepository, StatRepository statRepository) {
        this.mestoRepository = mestoRepository;
        this.statRepository = statRepository;
    }


    public List<Mesto> getCities()
    {
        return mestoRepository.findAll();
    }

    public List<String> getCityNames()
    {
        List <Mesto> ms = mestoRepository.findAll();
        List<String> r  = new ArrayList<>();
        for(Mesto m : ms)
        {
            r.add(m.getName());

        }
        return r;
    }

    public Mesto getCity(String name,String tag)
    {
        Optional <Mesto> mesto = mestoRepository.findMestoByNameAndState(name,tag);
        if(mesto.isPresent())return  new Mesto(name,tag);
        else return null;

    }

    public void addNewMesto(Mesto mesto) {
        Optional<Mesto> mestoByNameAndState =  mestoRepository.findMestoByNameAndState(mesto.getName(), mesto.getState());
        if(mestoByNameAndState.isPresent())
        {
            throw new IllegalStateException("Město již existuje");
        }
        List<Stat> ss = statRepository.findAll();
        for(Stat s : ss)
        {
            if(s.getTag().equals(mesto.getState()))
            {
                mestoRepository.save(mesto);
                break;
            }

        }


    }

    public void deleteMesto(Long mestoId) {
        mestoRepository.findById(mestoId);
        boolean exists = mestoRepository.existsById(mestoId);
        if(!exists)
        {
            throw new IllegalStateException("mesto s id "+ mestoId + "neexistuje");
        }
        mestoRepository.deleteById(mestoId);
    }

    @Transactional
    public void updateMesto(Long mestoId, String name, String state) {

        Mesto mesto = mestoRepository.findById(mestoId).orElseThrow(() -> new IllegalStateException("mesto s id "+mestoId+" neexistuje"));
        Optional<Mesto> mestoOptional = mestoRepository.findMestoByNameAndState(name,state);
        if(mestoOptional.isPresent())
        {
            throw new IllegalStateException("Město existuje");
        }
        if(name != null&&name.length() > 0 && !Objects.equals(mesto.getName(),name))
        {
            mesto.setName(name);
        }
        if(state != null&&state.length() > 0 && !Objects.equals(mesto.getState(),state))
        {
            mesto.setState(state);
        }

    }
}
