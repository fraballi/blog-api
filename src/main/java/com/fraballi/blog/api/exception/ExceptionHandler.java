package com.fraballi.blog.api.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

   @org.springframework.web.bind.annotation.ExceptionHandler(EntityNotFoundException.class)
   public ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException e, WebRequest request) {
      final Map<String, Object> body = new HashMap<>();
      body.put("timestamp", LocalDateTime.now());
      body.put("message", "Entity Not Found");

      return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
   }

   @org.springframework.web.bind.annotation.ExceptionHandler(FailedOperationException.class)
   public ResponseEntity<Object> handleFailedOperation(FailedOperationException e, WebRequest request) {
      final Map<String, Object> body = new HashMap<>();
      body.put("timestamp", LocalDateTime.now());
      body.put("message", "Failed Operation: " + e.getOperation());

      return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
   }
}
