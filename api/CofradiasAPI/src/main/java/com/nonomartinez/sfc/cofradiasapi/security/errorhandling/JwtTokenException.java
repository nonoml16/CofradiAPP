package com.nonomartinez.sfc.cofradiasapi.security.errorhandling;

public class JwtTokenException extends RuntimeException{
    public JwtTokenException(String msg) {
        super(msg);
    }
}
