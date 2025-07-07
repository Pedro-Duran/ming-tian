package com.panduran.mientien.service.exercicios;

import com.panduran.mientien.dto.ExerciciosBuscarIdeogramas;
import com.panduran.mientien.entity.Dia;
import com.panduran.mientien.repository.exercicios.ExerciciosRepository;
import com.panduran.mientien.service.exercicios.exerciciosServiceUtils.ExerciciosServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExerciciosService {

    @Autowired
    ExerciciosRepository exerciciosRepository;
    @Autowired
    static ExerciciosServiceUtils exerciciosServiceUtils;

    public List<String> buscarIdeogramas(ExerciciosBuscarIdeogramas.Request request) {
      List<Dia> buscarPalavrasPorDiaPorData = exerciciosRepository.buscarIdeogramas(request.getData());
        List<String> buscarIdeogramas = new ArrayList<>();
        buscarPalavrasPorDiaPorData.forEach(dia -> buscarIdeogramas.add(dia.getTextoZh()));
        return buscarIdeogramas;
    }

    public List<ExerciciosBuscarIdeogramas.Response> palavrasPorDia(ExerciciosBuscarIdeogramas.Request request) {
        List<Dia> buscarPalavrasPorDiaPorData = exerciciosRepository.buscarIdeogramas(request.getData());
        List<ExerciciosBuscarIdeogramas.Response> listaDeDiasComPalavrasPorDia = new ArrayList<>();

        iteraDiaParaAdicionarDadosAoResponse(buscarPalavrasPorDiaPorData, listaDeDiasComPalavrasPorDia);

        return listaDeDiasComPalavrasPorDia;

    }

    private static void iteraDiaParaAdicionarDadosAoResponse(List<Dia> buscarPalavrasPorDiaPorData, List<ExerciciosBuscarIdeogramas.Response> listaDeDiasComPalavrasPorDia) {
        buscarPalavrasPorDiaPorData.forEach(dia -> {
            ExerciciosBuscarIdeogramas.Response response = new ExerciciosBuscarIdeogramas.Response();
        response.setPalavrasTraduzidas(exerciciosServiceUtils.juntarCaracteresComMesmoSignificado(dia.getPalavrasTraduzidas()));
        response.setPingYing(dia.getPingYing());
        listaDeDiasComPalavrasPorDia.add(response);
        });
    }
}