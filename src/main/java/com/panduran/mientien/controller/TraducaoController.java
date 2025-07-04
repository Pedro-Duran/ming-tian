package com.panduran.mientien.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.panduran.mientien.dto.ExerciciosBuscarIdeogramas;
import com.panduran.mientien.dto.TraducaoDTO;
import com.panduran.mientien.service.ExerciciosService;
import com.panduran.mientien.service.OpenAiDiario.OpenAiDiario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TraducaoController {

    @Autowired
    OpenAiDiario openAiDiario;

    @Autowired
    ExerciciosService exerciciosService;

    @PostMapping("/traduzir")
    public ResponseEntity<TraducaoDTO.Response.Traducao> traduzir(
            @RequestBody TraducaoDTO.Request.Translate request) throws JsonProcessingException {
        TraducaoDTO.Response.Traducao response = openAiDiario.generation(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscarIdeogramas")
    public List<String> buscarIdeogramas(ExerciciosBuscarIdeogramas.Request data) {

        return exerciciosService.buscarIdeogramas(data);
    }


}


