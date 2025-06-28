package com.panduran.mientien.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.panduran.mientien.dto.AudioMp3DTO;
import com.panduran.mientien.dto.TraducaoDTO;
import com.panduran.mientien.repository.dia.DiaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class OpenAiTraducao {

    private final DiaRepository diaRepository;
    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;
    private final TtsService ttsService;


    public TraducaoDTO.Response.Traducao generation(TraducaoDTO.Request.Translate request) throws JsonProcessingException {
        String prompt = """
                    Você deve responder APENAS com um JSON válido no seguinte formato:
                    {
                      "textPT": "Texto original em português",
                      "textZH": "Tradução em chinês simplificado",
                      "pingYing": "Pingying correspondente"
                    }
                
                    Traduza o seguinte texto para chinês com pinyin e preencha os campos do JSON:
                    "%s"
                """.formatted(request.getTexto());

        String json = this.chatClient.prompt()
                .user(prompt)
                .call()
                .content();



        TraducaoDTO.Response.Traducao traducao = objectMapper.readValue(json, TraducaoDTO.Response.Traducao.class);
        traducao.setTexto(request.getTexto());
        AudioMp3DTO.Response.Audio url = ttsService.gerarAudio(traducao);
        diaRepository.cadastrar(traducao.getTexto(), traducao.getTextZH(), traducao.getPingYing(), url.getCaminhoAudio(), LocalDateTime.now());
        return traducao;
    }
}