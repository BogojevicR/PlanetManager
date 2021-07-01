package com.planetManager.controller.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ErrorModel {
    private int status;
    private String message;
    private List<String> errors = new ArrayList<>();
    private Date timestamp;

    public ErrorModel(HttpStatus httpStatus) {
        this.status = httpStatus.value();
        this.message = httpStatus.getReasonPhrase();
        this.timestamp = new Date();
    }
}
