package com.panduran.mientien.service;

import com.panduran.mientien.repository.exercicios.ExerciciosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExerciciosService {

    @Autowired
    ExerciciosRepository exerciciosRepository;

    public List<String> buscarIdeogramas() {
       return exerciciosRepository.buscarIdeogramas();
    }
}
