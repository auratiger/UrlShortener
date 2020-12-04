package com.springboot.projectdemo.UrlShortener.config;

public class MissingBearerTokenException extends RuntimeException {

    public MissingBearerTokenException() {
        super();
    }

    MissingBearerTokenException(String message){
        super(message);
    }

    public MissingBearerTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingBearerTokenException(Throwable cause) {
        super(cause);
    }

    protected MissingBearerTokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
