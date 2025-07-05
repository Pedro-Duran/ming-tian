package com.panduran.mientien.controller;

import com.panduran.mientien.service.OpenAiPodcast.OpenAiTTS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/criar")
public class PodcastController {

    @Autowired
    OpenAiTTS openAiTTS;

    @PostMapping("/podcast")
    public void criarPodcast(String texto, String nomeArquivo) throws IOException {
        openAiTTS.gerarAudio(texto, nomeArquivo);
    }
}
