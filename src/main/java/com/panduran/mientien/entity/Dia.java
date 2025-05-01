package com.panduran.mientien.entity;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Dia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String textoPT;
    @Column
    private String textoZh;
    @Column
    private String pingYing;
    private String caminhoAudio;

    private LocalDate data;

    @ElementCollection
    private List<String> topicos;
}