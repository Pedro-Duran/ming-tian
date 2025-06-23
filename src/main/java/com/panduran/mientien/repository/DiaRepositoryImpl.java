package com.panduran.mientien.repository;

public class DiaRepositoryImpl {

    public static final String CADASTRAR_DIA = """
            INSERT INTO MIENTIEN.DIA (TEXTO_PT, TEXTO_ZH, PING_YING, CAMINHO_AUDIO, DATA)
               VALUES (:textoPt, :textoZh, :pingYing, :caminhoAudio, :data)
            """;
}
