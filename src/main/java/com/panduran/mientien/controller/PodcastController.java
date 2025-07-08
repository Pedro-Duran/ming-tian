package com.panduran.mientien.controller;

import com.panduran.mientien.service.OpenAiPodcast.PodcastGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/criar")
public class PodcastController {

    @Autowired
    PodcastGenerator podcastGenerator;

    @PostMapping("/podcast")
    public void criarPodcast(@RequestParam String tema,@RequestParam String nivel, @RequestParam String situacao, @RequestParam String caminhoSaida) throws IOException {
        podcastGenerator.gerarRoteiroEducativo(  tema,  nivel,  situacao,  caminhoSaida);
    }
}
