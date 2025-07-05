package com.panduran.mientien.repository.dia;

public class DiaRepositoryImpl {

    public static final String CADASTRAR_DIA = """
            INSERT INTO DIA (TEXTO_PT, TEXTO_ZH, PING_YING, CAMINHO_AUDIO, DATA, PALAVRAS_TRADUZIDAS)
               VALUES (:textoPt, :textoZh, :pingYing, :caminhoAudio, :data, :palavrasTraduzidas )
            """;
}
