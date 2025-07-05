package com.panduran.mientien.entity;


import com.panduran.mientien.service.OpenAiDiario.converter.PalavrasTraduzidasConverter;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
@Entity
public class Dia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TEXTO_PT")
    private String textoPt;
    @Column(name = "TEXTO_ZH")
    private String textoZh;
    @Column(name = "PING_YING")
    private String pingYing;
    @Column(name = "CAMINHO_AUDIO")
    private String caminhoAudio;
    @Column(name = "DATA")
    private LocalDate data;
    @Lob
    @Convert(converter = PalavrasTraduzidasConverter.class)
    @Column(name = "PALAVRAS_TRADUZIDAS")
    private Map<String, String> palavrasTraduzidas;

}