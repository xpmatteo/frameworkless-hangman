package it.xpug.frameworkless.hangman.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class HttpExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { RuntimeException.class })
    protected ResponseEntity<Object> handleException(RuntimeException ex, WebRequest request) {
        HttpStatus returnedStatus = getHttpStatus(ex);
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("status", returnedStatus.value());
        return handleExceptionInternal(ex, body, new HttpHeaders(), returnedStatus, request);
    }

    private HttpStatus getHttpStatus(RuntimeException ex) {
        ResponseStatus annotation = ex.getClass().getAnnotation(ResponseStatus.class);
        return (null != annotation) ? annotation.code() :  HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
