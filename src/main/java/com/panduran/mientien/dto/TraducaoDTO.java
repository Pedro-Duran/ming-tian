package com.panduran.mientien.dto;


import lombok.Data;

public class TraducaoDTO {;


    private interface Texto {
        String getTexto();
    }

    private interface TextZH {
        String getTexto();
    }

    private interface PingYing {
        String getPingYing();
    }

    private interface CaminhoAudio {
        String getCaminhoAudio();
    }


    public class Request {


        @Data
        public static class Translate implements Texto {
            String texto;
        }
    }


    public class Response {

        @Data
        public static class Traducao implements Texto, TextZH, PingYing, CaminhoAudio{
            String texto;
            String textZH;
            String pingYing;
            String caminhoAudio;
        }
    }
}

