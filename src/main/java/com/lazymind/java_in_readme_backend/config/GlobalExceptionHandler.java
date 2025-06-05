package com.lazymind.java_in_readme_backend.config;

import com.lazymind.java_in_readme_backend.utility.Utility;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle Invalid Path Variables or Request Parameters
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleTypeMismatch(final MethodArgumentTypeMismatchException ex) {
        var responseMap = Utility.createBasicResponse("Invalid request parameter type.", null, false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
    }

    // Handle Generic Server Errors
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Map<String,Object>> handleUncaughtException(final Exception ex) {

        var responseMap = Utility.createBasicResponse(ex.getLocalizedMessage(),null,false);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMap);
    }

    // Handle Request Validation Errors (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(final MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Invalid request data.");

        var responseMap = Utility.createBasicResponse(errorMessage, null, false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
    }

}
