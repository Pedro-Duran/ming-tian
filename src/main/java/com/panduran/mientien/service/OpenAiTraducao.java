package com.panduran.mientien.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.panduran.mientien.dto.TraducaoDTO;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class OpenAiTraducao{


    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    public OpenAiTraducao(ChatClient chatClient, ObjectMapper objectMapper) {
        this.chatClient = chatClient;
        this.objectMapper = objectMapper;
    }

    public TraducaoDTO.Response.Traducao generation(TraducaoDTO.Request.Translate request) throws JsonProcessingException {
        String prompt = """
            Você deve responder APENAS com um JSON válido no seguinte formato:
            {
              "textPT": "Texto original em português",
              "textZH": "Tradução em chinês",
              "pinyin": "Pinyin correspondente"
            }

            Traduza o seguinte texto para chinês com pinyin e preencha os campos do JSON:
            "%s"
        """.formatted(request.getTextPT());

        String json = this.chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        TraducaoDTO.Response.Traducao traducao = objectMapper.readValue(json, TraducaoDTO.Response.Traducao.class);
        System.out.println(json);
        System.out.println(traducao);
        return traducao;
    }
}