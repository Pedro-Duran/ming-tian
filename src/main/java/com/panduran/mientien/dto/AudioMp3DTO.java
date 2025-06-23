package com.panduran.mientien.dto;

import lombok.Data;
import lombok.Value;

public enum AudioMp3DTO {;

    private interface  CaminhoAudio {
        String getCaminhoAudio();
    }

    private interface Mensagem{
        String getMensagem();
    }
    public class Response {

        @Data
        public static class Audio implements CaminhoAudio, Mensagem {
            String mensagem;
            String caminhoAudio;
        }
    }
}
