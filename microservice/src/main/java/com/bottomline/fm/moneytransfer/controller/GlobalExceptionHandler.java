package com.bottomline.fm.moneytransfer.controller;


import com.bottomline.fm.moneytransfer.dto.ErrorResponse;
import com.bottomline.fm.moneytransfer.exception.BadRequestException;
import com.bottomline.fm.moneytransfer.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({BadRequestException.class})
    protected ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return this.handleExceptionInternal(ex, new ErrorResponse(ex.getMessage()), headers, status, request);
    }
    @ExceptionHandler({NotFoundException.class})
    protected ResponseEntity<Object> handleNotFoundException(BadRequestException ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.NOT_FOUND;
        return this.handleExceptionInternal(ex, new ErrorResponse(ex.getMessage()), headers, status, request);
    }
}
