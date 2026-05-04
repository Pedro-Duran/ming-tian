package com.panduran.mientien.service.exercicios.exerciciosServiceUtils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class ExerciciosServiceUtils {

    public static Map<String, String> juntarCaracteresComMesmoSignificado(Map <String, String> listaCaracteresCrua){


                Map<String, String> resultado = new LinkedHashMap<>();
                List<String> chavesPendentes = new ArrayList<>();
                String valorAnterior = null;

                for (Map.Entry<String, String> entry : listaCaracteresCrua.entrySet()) {
                    String chave = entry.getKey();
                    String valor = entry.getValue();

                    if (valor.equals(valorAnterior)) {
                        chavesPendentes.add(chave);
                    } else {
                        // Processa o grupo anterior
                        if (!chavesPendentes.isEmpty()) {
                            String chaveAgrupada = String.join("", chavesPendentes);
                            resultado.put(chaveAgrupada, valorAnterior);
                            chavesPendentes.clear();
                        }
                        chavesPendentes.add(chave);
                        valorAnterior = valor;
                    }
                }

                // Processa o último grupo
                if (!chavesPendentes.isEmpty()) {
                    String chaveAgrupada = String.join("", chavesPendentes);
                    resultado.put(chaveAgrupada, valorAnterior);
                }

                return resultado;
            }
    }


