package com.gamelib.game_lib.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class CompanyExceptionHandler {
    @ExceptionHandler(value = {CompanyRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(CompanyRequestException e) {
        // Create a payload containing exception details
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ZonedDateTime timeZone = ZonedDateTime.now(ZoneId.of("Z"));
        CompanyException companyException = new CompanyException(e.getMessage(), badRequest, timeZone);
        // Returns response entity
        return new ResponseEntity<>(companyException, badRequest);
    }
}
