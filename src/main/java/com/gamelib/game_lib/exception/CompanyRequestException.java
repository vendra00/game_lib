package com.gamelib.game_lib.exception;

import java.io.Serial;

public class CompanyRequestException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public CompanyRequestException(String message) {
        super(message);
    }

    public CompanyRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
