package com.volvo.taxcalculator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdviceHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomProblemException.class)
    protected ResponseEntity<String> handleCustomProblemException(CustomProblemException ex) {
        return generateProblemResponse(ex.getProblem().getKey(), ex.getProblem().getStatus(), ex.getMessage());
    }

    private ResponseEntity<String> generateProblemResponse(String code, HttpStatus httpStatus, String msg) {
        return new ResponseEntity<>("(code) " + code + " (message):" + msg, httpStatus);
    }
}
