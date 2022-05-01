package com.bartek.sulima.soft.application.rest;

import com.bartek.sulima.soft.domain.PasswordNotMatchException;
import com.bartek.sulima.soft.domain.user.UsernameExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = UsernameExistsException.class )
    protected ResponseEntity<?> handleUsernameExists(
            UsernameExistsException ex, WebRequest request) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(value = PasswordNotMatchException.class )
    protected ResponseEntity<?> handlePasswordNotMatch(
            UsernameExistsException ex, WebRequest request) {
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }
}
