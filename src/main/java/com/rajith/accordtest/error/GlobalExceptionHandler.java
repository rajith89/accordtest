package com.rajith.accordtest.error;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

// Annotates this class as a global exception handler for all @RestController beans

/**
 * This handles all of the general exceptions in the REST controller APIs.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    public static final String ERROR_MSG_PATTERNS = " Error {}";


    @ExceptionHandler({ DataRetrievalFailureException.class, EntityNotFoundException.class, ResourceNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<APIError> handleNotFound(Exception exception) {
        LOGGER.error(ERROR_MSG_PATTERNS, exception.getMessage(), exception);
        // Return the error response with HTTP 404 Not Found status
        return apiExceptionResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }
    // Handles specific exceptions
    @ExceptionHandler({ ConstraintViolationException.class, DataIntegrityViolationException.class })
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public ResponseEntity<APIError> handleConstraintViolationException(Exception exception) {
        LOGGER.error(ERROR_MSG_PATTERNS, exception.getMessage(), exception);
        // Return the error response with HTTP 412 Precondition Failed.
        return apiExceptionResponse(HttpStatus.PRECONDITION_FAILED, exception.getMessage());
    }

    // Handles validation exceptions
    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        // Extract validation errors from the exception
        List<String> errors = ex.getBindingResult().getAllErrors().stream()
                .map(objectError -> objectError.getDefaultMessage())
                .collect(Collectors.toList());
        // Create a custom error response for validation errors
        // Return the error response with HTTP 400 Bad Request status
        return apiExceptionResponse(HttpStatus.BAD_REQUEST, errors.toString());
    }


    private ResponseEntity<APIError> apiExceptionResponse(HttpStatus httpStatus, String message) {
        
    	APIError newobj = new APIError();
    	newobj.setMessage(message);
    	newobj.setTimestamp(LocalDateTime.now());
    	newobj.setStatus(httpStatus.toString());
    	
    	return ResponseEntity.status(httpStatus).body(newobj);
    }
}
