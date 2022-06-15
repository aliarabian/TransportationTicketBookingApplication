package com.platform.controllers;

import com.platform.ApiErrorResponse;
import com.platform.ApiResponseEntity;
import com.platform.business.exception.ApplicationException;
import com.platform.business.exception.BadRequestException;
import com.platform.business.exception.ResourceNotFoundException;
import com.platform.business.service.auth.exception.AuthenticationFailedException;
import com.platform.business.service.booking.exception.BookingException;
import com.platform.business.service.users.CustomerExistsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiResponseEntity<Object>> handleResourceFoundException(
            ResourceNotFoundException resourceNotFoundException
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createResponseBody(resourceNotFoundException.getMessage(), resourceNotFoundException.errorCode()));
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ApiResponseEntity<Object>> handleAuthenticationException(AuthenticationFailedException authenticationException) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                createResponseBody(authenticationException.getMessage(), authenticationException.errorCode()));
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponseEntity<Object>> handleBadRequestException(BadRequestException badRequestException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseEntity<>(new ApiErrorResponse(
                badRequestException.getMessage(), 400)));
    }

    @ExceptionHandler({CustomerExistsException.class, BookingException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ApiResponseEntity<Object>> handleCustomerExistsException(ApplicationException applicationException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponseEntity<>(new ApiErrorResponse(applicationException.getMessage(), applicationException.errorCode())));
    }


    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseEntity<>(new ApiErrorResponse(
                ex.getMessage(), 400)));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        // TODO Add binding validation error list messages property to ApiErrorResponse Class
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseEntity<>(new ApiErrorResponse(
                ex.getMessage(), 400)));
    }


    private ApiResponseEntity<Object> createResponseBody(String msg, int errorCode) {
        return new ApiResponseEntity<>(new ApiErrorResponse(msg, errorCode));
    }
}
