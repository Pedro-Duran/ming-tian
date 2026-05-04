package com.panduran.mientien.controller;

import com.panduran.mientien.dto.ExerciciosBuscarIdeogramas;
import com.panduran.mientien.dto.TraducaoDTO;
import com.panduran.mientien.service.exercicios.ExerciciosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/exercicios")
public class ExerciciosController {

    @Autowired
    ExerciciosService exerciciosService;
    
        @GetMapping("/palavrasPorDia")
    public List<ExerciciosBuscarIdeogramas.Response> palavrasPorDia(ExerciciosBuscarIdeogramas.Request request) {
       return exerciciosService.palavrasPorDia(request);
    }

    @GetMapping("/buscarDiarios")
    public List<TraducaoDTO.Response.Traducao> buscarDiarios(ExerciciosBuscarIdeogramas.Request request) {
        return exerciciosService.buscarDiarios(request);
    }

}
