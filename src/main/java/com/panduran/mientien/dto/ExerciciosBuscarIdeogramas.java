package com.panduran.mientien.dto;

import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.util.Map;

public enum ExerciciosBuscarIdeogramas {;

    @Nullable
    public interface  Data{
        LocalDate getData();
    }

    private interface PingYing {
        String getPingYing();
    }

    private interface PalavrasTraduzidas {
        Map<String, String> getPalavrasTraduzidas();
    }

    @lombok.Data
    public class Request implements Data {
        LocalDate data;
    }

    @lombok.Data
    public static class Response implements PalavrasTraduzidas, PingYing{
        String pingYing;
        Map<String, String> palavrasTraduzidas;
    }
}
