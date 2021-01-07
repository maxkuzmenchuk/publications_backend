package com.kuzmenchuk.publications.util;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String JWT_TOKEN;

    public JwtResponse(String JWT_TOKEN) {
        this.JWT_TOKEN = JWT_TOKEN;
    }

    public String getToken() {
        return this.JWT_TOKEN;
    }
}