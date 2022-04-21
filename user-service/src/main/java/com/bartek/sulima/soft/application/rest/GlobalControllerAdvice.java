package com.bartek.sulima.soft.application.rest;

import com.bartek.sulima.soft.domain.user.UsernameExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = UsernameExistsException.class )
    protected ResponseEntity<?> handleConflict(
            UsernameExistsException ex, WebRequest request) {
        return ResponseEntity.badRequest().build();
    }
}
