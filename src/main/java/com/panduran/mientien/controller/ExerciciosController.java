package com.panduran.mientien.controller;

import com.panduran.mientien.dto.ExerciciosBuscarIdeogramas;
import com.panduran.mientien.service.ExerciciosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exercicios")
public class ExerciciosController {

    @Autowired
    ExerciciosService exerciciosService;
    
        @GetMapping("/palavrasPorDia")
    public ExerciciosBuscarIdeogramas.Response palavrasPorDia(ExerciciosBuscarIdeogramas.Request request) {
       return exerciciosService.palavrasPorDia(request);
    }

}
