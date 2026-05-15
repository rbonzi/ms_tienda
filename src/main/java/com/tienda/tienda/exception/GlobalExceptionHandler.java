package com.tienda.tienda.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
        // Se lanza cuando no se cumplen @Valid
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String,String>> handleValidationErrors(
                MethodArgumentNotValidException ex){

            Map<String, String> errores = new LinkedHashMap<>();

            ex.getBindingResult().getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));

            return  ResponseEntity.badRequest().body(errores);
        }

        // Se dispara cuando service lanza RunTimeException
        @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<Map<String, String>> handleRuntimeException(
                RuntimeException ex
        ){
            Map<String, String> error = new LinkedHashMap<>();
            error.put("Error:", ex.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
}
