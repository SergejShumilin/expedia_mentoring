package com.epam.esm.controller;

import com.epam.esm.exception.ApiError;
import com.epam.esm.exception.CertificateNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), error);
        return new ResponseEntity<Object>(
                apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({CertificateNotFoundException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            CertificateNotFoundException ex, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND.value(), ex.getLocalizedMessage());
        return new ResponseEntity<Object>(
                apiError, HttpStatus.NOT_FOUND);
    }
}
