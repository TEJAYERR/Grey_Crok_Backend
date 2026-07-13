package com.jashu.shopping_website.exception;

import com.jashu.shopping_website.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> resourceNotFoundException(
            ResourceNotFoundException resourceNotFoundException,
            HttpServletRequest httpServletRequest){

        ErrorResponse errorResponse = ErrorResponse.builder()
                .localDateTime(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(resourceNotFoundException.getMessage())
                .path(httpServletRequest.getServletPath())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> outOfStockException(
            OutOfStockException outOfStockException,
            HttpServletRequest httpServletRequest
    ){

        ErrorResponse errorResponse = ErrorResponse.builder()
                .localDateTime(LocalDateTime.now())
                .path(httpServletRequest.getServletPath())
                .message(outOfStockException.getMessage())
                .status(HttpStatus.UNPROCESSABLE_CONTENT.value())
                .error(HttpStatus.UNPROCESSABLE_CONTENT.getReasonPhrase())
                .build();

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT)
                .body(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> unAuthorizedOperationException(
            UnauthorizedOperationException unauthorizedOperationException,
            HttpServletRequest httpServletRequest
    ){

        ErrorResponse errorResponse = ErrorResponse.builder()
                .localDateTime(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .message(unauthorizedOperationException.getMessage())
                .path(httpServletRequest.getServletPath())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> duplicatePaymentVerificationException(
            DuplicateOperationException duplicateOperationException,
            HttpServletRequest httpServletRequest
    ){

        ErrorResponse errorResponse = ErrorResponse.builder()
                .localDateTime(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .message(duplicateOperationException.getMessage())
                .path(httpServletRequest.getServletPath())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> duplicateEntryException(
            DuplicateEntryException duplicateEntryException,
            HttpServletRequest httpServletRequest
    ){

        ErrorResponse errorResponse = ErrorResponse.builder()
                .localDateTime(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .message(duplicateEntryException.getMessage())
                .path(httpServletRequest.getServletPath())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> invalidPaymentSignatureException(
            InvalidSignatureException invalidSignatureException,
            HttpServletRequest httpServletRequest
    ){

        ErrorResponse errorResponse = ErrorResponse.builder()
                .localDateTime(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(invalidSignatureException.getMessage())
                .path(httpServletRequest.getServletPath())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler({DataAccessException.class, TransactionSystemException.class})
    public ResponseEntity<ErrorResponse> handleDatabaseExceptions(
            Exception databaseException,
            HttpServletRequest httpServletRequest
    ){

        ErrorResponse errorResponse = ErrorResponse.builder()
                .localDateTime(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message("A temporary database error occurred. Your transaction is safe, please try again.")
                .path(httpServletRequest.getServletPath())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> badRequestException(
            BadRequestException badRequestException,
            HttpServletRequest httpServletRequest
    ){

        ErrorResponse errorResponse = ErrorResponse.builder()
                .localDateTime(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(badRequestException.getMessage())
                .path(httpServletRequest.getServletPath())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(
            Exception exception,
            HttpServletRequest httpServletRequest
    ){

        ErrorResponse errorResponse = ErrorResponse.builder()
                .localDateTime(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message("An unexpected error occurred on the server. Please try again later.")
                .path(httpServletRequest.getServletPath())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }

}
