package com.panduran.mientien.service.TexToSpeechService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.panduran.mientien.dto.AudioMp3DTO;
import com.panduran.mientien.dto.TraducaoDTO;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TtsService {


    public AudioMp3DTO.Response.Audio gerarAudio(TraducaoDTO.Response.Traducao traducao) throws JsonProcessingException {
        OkHttpClient client = new OkHttpClient();

        String json = String.format("{\"texto\": \"%s\"}", traducao.getTextZH());

        RequestBody body = RequestBody.create(
                json, MediaType.parse("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url("http://127.0.0.1:8000/gerar-audio")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();

            if (response.isSuccessful()) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(responseBody, AudioMp3DTO.Response.Audio.class);
            } else {
                throw new RuntimeException("Erro ao gerar áudio: " + response.code() + " - " + responseBody);
            }

        } catch (IOException e) {
            throw new RuntimeException("Erro na requisição ao servidor de áudio", e);
        }
    }
}

