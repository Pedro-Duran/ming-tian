package com.panduran.mientien.service.OpenAiDiario;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.panduran.mientien.dto.AudioMp3DTO;
import com.panduran.mientien.dto.TraducaoDTO;
import com.panduran.mientien.repository.dia.DiaRepository;
import com.panduran.mientien.service.TexToSpeechService.TtsService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class OpenAiDiario {

    private final DiaRepository diaRepository;
    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;
    private final TtsService ttsService;


    public TraducaoDTO.Response.Traducao generation(TraducaoDTO.Request.Translate request) throws JsonProcessingException {
        String prompt = """
                Você deve responder ESTRITAMENTE com um JSON válido no seguinte formato e NADA MAIS.Como no exemplo abaixo:
                
                
                {
                  "textPT": "Texto original em português",
                  "textZH": "Tradução em chinês simplificado",
                  "pingYing": "Pinyin correspondente",
                  "palavrasTraduzidas": {
                    "我": "eu",
                    "喜": "gostar",
                    "欢": "gostar",
                    "茶": "chá",
                    "的": "partícula possessiva"
                  }

                    Traduza o seguinte texto para chinês com pinyin e preencha os campos do JSON "%s" (tenha em mente que o
                     palavrasTraduzidas é um destrinchamento chave-valor, onde cada chave é um caracter do campo textZH,
                      e o valor é sua respectiva traducao. Note que no exemplo que de dei, quando nao havia 
                      uma palavra equivalente a "的", o campo veio como "partícula possessiva". Quero 
                      que essa logica seja seguida, quando nao pudermos traduzir diretamente, como com o caracter "吗", "个", 
                      e assim por diante) 
                    
                """.formatted(request.getTexto());

        String json = this.chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        TraducaoDTO.Response.Traducao traducao = objectMapper.readValue(json, TraducaoDTO.Response.Traducao.class);
        traducao.setTexto(request.getTexto());
        System.out.println(traducao.getPalavrasTraduzidas());
        AudioMp3DTO.Response.Audio url = ttsService.gerarAudio(traducao);
        traducao.setCaminhoAudio(url.getCaminhoAudio());
        diaRepository.cadastrar(traducao.getTexto(), traducao.getTextZH(), traducao.getPingYing(), url.getCaminhoAudio(), LocalDateTime.now(), traducao.getPalavrasTraduzidas());
        return traducao;
    }
}