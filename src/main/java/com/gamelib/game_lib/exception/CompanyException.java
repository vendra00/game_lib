package com.gamelib.game_lib.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;


public record CompanyException(String message, HttpStatus httpStatus, ZonedDateTime timeStamp) {
}
