package com.panduran.mientien.service;

import com.panduran.mientien.repository.exercicios.ExerciciosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExerciciosService {

    @Autowired
    ExerciciosRepository exerciciosRepository;

    public List<String> buscarIdeogramas() {
        return exerciciosRepository.buscarIdeogramas();
    }

    public List<List<String>> palavrasPorDia() {
        List<String> frasesPorDia = buscarIdeogramas();
        List<List<String>> ideogramas = new ArrayList<>();

        frasesPorDia.forEach(frase -> {
            List<String> palavrasDoDia = new ArrayList<>();
            for (char c : frase.trim().toCharArray()) {
                palavrasDoDia.add(String.valueOf(c));
            }
            ideogramas.add(palavrasDoDia);
        });

        return ideogramas;
    }
}