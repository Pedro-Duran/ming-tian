package com.panduran.mientien.service;

import com.panduran.mientien.dto.ExerciciosBuscarIdeogramas;
import com.panduran.mientien.entity.Dia;
import com.panduran.mientien.repository.exercicios.ExerciciosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExerciciosService {

    @Autowired
    ExerciciosRepository exerciciosRepository;

    public List<Dia> buscarIdeogramas(ExerciciosBuscarIdeogramas.Request request) {
      List<Dia> buscarIdeogramas = exerciciosRepository.buscarIdeogramas(request.getData());
        System.out.println(buscarIdeogramas);
        return buscarIdeogramas;
    }

    public ExerciciosBuscarIdeogramas.Response palavrasPorDia(ExerciciosBuscarIdeogramas.Request request) {
        List<Dia> frasesPorDia = buscarIdeogramas(request);
        Dia primeiraFrasedoDia = frasesPorDia.get(0);
        ExerciciosBuscarIdeogramas.Response response = new ExerciciosBuscarIdeogramas.Response();
        response.setPalavrasTraduzidas(primeiraFrasedoDia.getPalavrasTraduzidas());
        response.setPingYing(primeiraFrasedoDia.getPingYing());

        return response;

    }
}