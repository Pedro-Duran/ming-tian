package com.panduran.mientien.configuration;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
class SpringAIConfig {


    @Value("${OPENAI_API_KEY}")
    private String apiKey;
    @Bean
    OpenAiApi openAiApi(WebClient.Builder webClientBuilder) {
        return OpenAiApi.builder()
                .apiKey(apiKey)
                .baseUrl("https://api.openai.com/v1") // URL padrão da OpenAI
                .webClientBuilder(webClientBuilder)
                .build();
    }

    @Bean
    ChatClient chatClient(ChatClient.Builder builder) {
        return builder.build();
    }

}