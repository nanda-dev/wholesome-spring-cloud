package com.demo.accountservice.controller;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.demo.accountservice.controller.model.ErrorResponseDTO;
import com.demo.accountservice.exception.DuplicatePhoneNumberException;
import com.demo.accountservice.exception.InvalidAgeException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    // Handling basic Javax request/input field validation errors 
    Map<String, String> details =
        ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .collect(
                Collectors.toMap(
                    FieldError::getField,
                    FieldError::getDefaultMessage,
                    (oldVal, newVal) -> oldVal));
    
    return ResponseEntity.of(Optional.ofNullable((buildError("Invalid arguments", details))));
  }

  @ExceptionHandler(value = DuplicatePhoneNumberException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDTO handleDuplicatePhoneNumberException(Exception ex) {
    return buildError(ex.getMessage());
  }

  @ExceptionHandler(value = InvalidAgeException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDTO handleInvalidAgeException(Exception ex) {
    return buildError(ex.getMessage());
  }

  @ExceptionHandler(value = Throwable.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorResponseDTO handleAllOtherExceptions(Exception ex) {
    return buildError(ex.getMessage());
  }

  private ErrorResponseDTO buildError(String message) {
    return buildError(message, null);
  }

  private ErrorResponseDTO buildError(String message, Map<String, String> details) {
    return new ErrorResponseDTO(
        message,
        ZonedDateTime.now(),
        ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString(),
        details);
  }
}
