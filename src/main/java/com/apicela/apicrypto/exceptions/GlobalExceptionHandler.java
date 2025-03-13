package com.apicela.apicrypto.exceptions;

import com.apicela.apicrypto.models.responses.DefaultApiResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.MissingRequestValueException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {
    @ExceptionHandler(SaveException.class)
    public Mono<ResponseEntity<Object>> handleSaveException(SaveException ex) {
        log.error("handleSaveException {}", ex);
        var response = new DefaultApiResponse<>(ex.getMessage(), 400);
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response));
    }

    @ExceptionHandler(UpdateException.class)
    public Mono<ResponseEntity<Object>> handleUpdateException(UpdateException ex) {
        log.error("handleUpdateException {}", ex);
        var response = new DefaultApiResponse<>(ex.getMessage(), 400);
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response));
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public Mono<ResponseEntity<Object>> handleUpdateException(EmailAlreadyInUseException ex) {
        log.error("handleEmailAlreadyInUseException {}", ex);
        var response = new DefaultApiResponse<>(ex.getMessage(), 409);
        return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(response));
    }

    @ExceptionHandler(NotFoundException.class)
    public Mono<ResponseEntity<Object>> handleNotFound(NotFoundException ex) {
        log.error("handleNotFound {}", ex);
        var response = new DefaultApiResponse<>(ex.getMessage(), 404);
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(response));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Validation Error");
        response.put("status", HttpStatus.BAD_REQUEST.value());

        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        response.put("errors", errors);
        log.error(" handleValidationExceptions {}", ex);
        return Mono.just(new ResponseEntity<>(response, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<Map<String, Object>> handleValidationException(WebExchangeBindException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("message", "Validation Error");

        // Extrai os erros do DTO e coloca em um mapa de campo -> mensagem
        Map<String, String> errors = ex.getFieldErrors().stream()
                .collect(Collectors.toMap(
                        fieldError -> fieldError.getField(),
                        fieldError -> fieldError.getDefaultMessage(),
                        (existing, replacement) -> existing // Caso haja duplicatas, mantém o primeiro erro
                ));

        response.put("errors", errors);
        log.error("handleValidationException {}", ex);
        return Mono.just(response);
    }

    @ExceptionHandler(MissingRequestValueException.class)
    public Mono<ResponseEntity<Object>> handleMissingRequestValueException(MissingRequestValueException ex) {
        log.error("handleMissingRequestValueException {}", ex);
        var response = new DefaultApiResponse<>(ex.getMessage(), 400);
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<Object>> exception(Exception ex) {
        log.error("handleException {}", ex);
        var response = new DefaultApiResponse<>(ex.getMessage(), 500);
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response));
    }
}
