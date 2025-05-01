package com.panduran.mientien.dto;


import lombok.Data;
import lombok.Value;

public class TraducaoDTO {;

    private interface TextPT {
        String getTextPT();
    }

    private interface TextZH {
        String getTextZH();
    }

    private interface Pinyin {
        String getPinyin();
    }


    public class Request {

        @Value
        @Data
        public static class Translate implements TextPT {
            String textPT;
        }
    }


    public class Response {
        @Value
        @Data
        public static class Traducao implements TextPT, TextZH, Pinyin {
            String textPT;
            String textZH;
            String pinyin;
        }
    }
}

