package com.panduran.mientien.service.OpenAiPodcast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

/**
 * Gera um roteiro educativo em mandarim e o respectivo áudio em MP3 via API da OpenAI.
 * <p>
 * Passos do fluxo:
 * <ol>
 *     <li>{@link #gerarTexto(String, String, String)} → chama chat‑completion, devolve JSON;</li>
 *     <li>{@link #extrairRoteiro(String)} → extrai o campo {@code choices[0].message.content};</li>
 *     <li>{@link #gerarAudio(String, String)} → envia o roteiro para TTS e salva em MP3;</li>
 *     <li>{@link #gerarRoteiroEducativo(String, String, String, String)} orquestra tudo.</li>
 * </ol>
 */
@Service
public class PodcastGenerator {

    private static final String CHAT_ENDPOINT = "https://api.openai.com/v1/chat/completions";
    private static final String TTS_ENDPOINT = "https://api.openai.com/v1/audio/speech";

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .connectTimeout(Duration.ofSeconds(20))          // TCP handshake
            .writeTimeout(Duration.ofMinutes(2))             // upload do corpo
            .readTimeout(Duration.ofMinutes(2))              // espera por resposta
            .callTimeout(Duration.ofMinutes(3))              // tempo total da call
            .pingInterval(Duration.ofSeconds(30))            // mantém HTTP/2 vivo
            .protocols(java.util.List.of(Protocol.HTTP_2, Protocol.HTTP_1_1))
            .build();

    private final String apiKey = System.getenv("OPENAI_API_KEY");



    // ========================= CHAT COMPLETION ========================= //

    private String buildChatJson(String tema, String nivel, String contexto) {
        ObjectNode root = MAPPER.createObjectNode()
                .put("model", "gpt-4o");

        ArrayNode messages = root.putArray("messages");

        messages.addObject()
                .put("role", "system")
                .put("content", "Você é um gerador de roteiros de podcast 100% didático.");

        String prompt = """
            Simule um episódio de podcast educativo de mandarim com dois locutores:
            - John, aluno estadounidense curioso, faz perguntas simples.
            - Li Mei, professora chinesa didática e entusiasta.

            Tema: "%s"
            Nível do aluno: %s
            Contexto: %s
            Estrutura:
            1. Apresentação.
            2. Perguntas sobre o contexto.
            3. Vocabulário essencial (3‑5 palavras, com tom, repetir 2×).
            4. Diálogo em mandarim.
            5. Discussão geral do diálogo.
            6. Repetição frase a frase com tradução (2×).
            7. Comentário cultural comparando com expressões em inglês.
            O roteiro deve ser conduzido por perguntas e respostas.
            """.formatted(tema, nivel, contexto);

        messages.addObject()
                .put("role", "user")
                .put("content", prompt);

        return root.toPrettyString();
    }

    /**
     * Chama o endpoint de chat completion e devolve o JSON bruto da resposta.
     */
    public String gerarTexto(String tema, String nivel, String contexto) throws IOException {
        String jsonBody = buildChatJson(tema, nivel, contexto);

        Request request = new Request.Builder()
                .url(CHAT_ENDPOINT)
                .post(RequestBody.create(jsonBody, JSON))
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

        try (Response response = CLIENT.newCall(request).execute()) {
            String body = response.body() != null ? response.body().string() : "";
            if (!response.isSuccessful()) {
                throw new IOException("Erro ao gerar texto " + response.code() + " → " + body);
            }
            return body; // JSON completo
        }
    }

    /**
     * Extrai o roteiro em texto do JSON retornado pela chat completion.
     */
    private String extrairRoteiro(String respostaJson) throws JsonProcessingException {
        JsonNode root = MAPPER.readTree(respostaJson);
        return root.path("choices")
                .get(0)
                .path("message")
                .path("content")
                .asText("")
                .trim();
    }

    // ========================= TEXT‑TO‑SPEECH ========================= //

    private String buildTtsJson(String texto) {
        ObjectNode root = MAPPER.createObjectNode()
                .put("model", "tts-1-hd")
                .put("input", texto)
                .put("voice", "nova")
                .put("response_format", "mp3");
        return root.toPrettyString();
    }

    /**
     * Gera áudio MP3 a partir do texto fornecido e salva em {@code caminhoSaida}.
     * @return {@link Path} para o arquivo gerado.
     */
    private Path gerarAudio(String texto, String caminhoSaida) throws IOException {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("Texto para TTS não pode estar vazio");
        }

        String jsonBody = buildTtsJson(texto);

        Request request = new Request.Builder()
                .url(TTS_ENDPOINT)
                .post(RequestBody.create(jsonBody, JSON))
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

        try (Response response = CLIENT.newCall(request).execute()) {
            byte[] bytes = response.body() != null ? response.body().bytes() : new byte[0];
            if (!response.isSuccessful()) {
                String errBody = new String(bytes);
                throw new IOException("Erro ao gerar áudio " + response.code() + " → " + errBody);
            }

            Path out = Paths.get(caminhoSaida);
            Files.createDirectories(out.getParent());
            Files.write(out, bytes);
            return out;
        }
    }

    // ========================= ORQUESTRAÇÃO ========================= //

    /**
     * Orquestra a geração de roteiro + áudio.
     * @param caminhoSaida onde gravar o MP3 (relativo ou absoluto)
     * @return {@link Path} para o MP3
     */
    public Path gerarRoteiroEducativo(String tema, String nivel, String contexto, String caminhoSaida) throws IOException {
        // 1) Gera o texto (JSON)
        String respostaChat = gerarTexto(tema, nivel, contexto);
        // 2) Extrai o roteiro puro
        String roteiro = extrairRoteiro(respostaChat);

        if (roteiro.isBlank()) {
            throw new IllegalStateException("Roteiro vazio retornado pela API de chat");
        }

        // 3) Gera o áudio
        return gerarAudio(roteiro, caminhoSaida);
    }
}
