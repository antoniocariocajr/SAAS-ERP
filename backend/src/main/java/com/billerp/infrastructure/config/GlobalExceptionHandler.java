package com.billerp.infrastructure.config;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.billerp.domain.exception.BusinessException;
import com.billerp.domain.exception.ResourceNotFoundException;
import com.billerp.domain.exception.UserUnauthorizedException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(BusinessException.class)
        public ResponseEntity<ErrorResponse> handleBusiness(BusinessException ex,
                        HttpServletRequest req) {
                return ResponseEntity.badRequest()
                                .body(new ErrorResponse(ex.getMessage(),
                                                ex.getCode(),
                                                ex.getField(),
                                                req.getRequestURI(),
                                                Instant.now()));
        }

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex,
                        HttpServletRequest req) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new ErrorResponse(ex.getMessage(),
                                                ex.getCode(),
                                                ex.getField(),
                                                req.getRequestURI(),
                                                Instant.now()));
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleAll(Exception ex, HttpServletRequest req) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ErrorResponse("Internal server error",
                                                "INTERNAL",
                                                null,
                                                req.getRequestURI(),
                                                Instant.now()));
        }

        @ExceptionHandler(UserUnauthorizedException.class)
        public ResponseEntity<ErrorResponse> handleUnauthorized(UserUnauthorizedException ex, HttpServletRequest req) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body(new ErrorResponse(ex.getMessage(),
                                                ex.getCode(),
                                                ex.getField(),
                                                req.getRequestURI(),
                                                Instant.now()));
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                        HttpServletRequest req) {
                return ResponseEntity.badRequest()
                                .body(new ErrorResponse(ex.getMessage(),
                                                "VALIDATION",
                                                null,
                                                req.getRequestURI(),
                                                Instant.now()));
        }

        public record ErrorResponse(
                        String message,
                        String code,
                        String field,
                        String path,
                        Instant timestamp) {
        }
}