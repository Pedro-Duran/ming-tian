package com.panduran.mientien.controller;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.panduran.mientien.dto.TraducaoDTO;
import com.panduran.mientien.service.OpenAiTraducao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TraducaoController {

    @Autowired
    OpenAiTraducao openAiTraducao;

    @PostMapping("/traduzir")
    public ResponseEntity<TraducaoDTO.Response.Traducao> traduzir(
            @RequestBody TraducaoDTO.Request.Translate request) throws JsonProcessingException {
        TraducaoDTO.Response.Traducao response = openAiTraducao.generation(request);
        return ResponseEntity.ok(response);
    }
}
