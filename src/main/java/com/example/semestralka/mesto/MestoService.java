package com.example.semestralka.mesto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MestoService {

    private final MestoRepository mestoRepository;

    @Autowired
    public MestoService(MestoRepository mestoRepository) {
        this.mestoRepository = mestoRepository;
    }


    @GetMapping
    public List<Mesto> getCities()
    {
        return mestoRepository.findAll();
    }

    public void addNewMesto(Mesto mesto) {
        Optional<Mesto> mestoByNameAndState =  mestoRepository.findMestoByNameAndState(mesto.getName(), mesto.getState());
        if(mestoByNameAndState.isPresent())
        {
            throw new IllegalStateException("Město již existuje");
        }

        mestoRepository.save(mesto);
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
