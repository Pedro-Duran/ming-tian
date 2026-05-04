package com.panduran.mientien.dto;

import lombok.Data;

public class AuthDTO {

    @Data
    public static class Request {
        String email;
        String senha;
    }

    @Data
    public static class Response {
        private final String token;
    }
}
