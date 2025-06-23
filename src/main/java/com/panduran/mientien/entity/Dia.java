package com.panduran.mientien.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

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
    private LocalDateTime data;

}