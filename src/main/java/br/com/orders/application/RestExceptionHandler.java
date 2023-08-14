package br.com.orders.application;

import br.com.orders.application.exceptions.OrderNotFoundException;
import br.com.orders.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    private ResponseEntity<ErrorDto> handleException(final OrderNotFoundException ex,
                                                   final WebRequest request) {
        final ErrorDto error = new ErrorDto(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ErrorDto> handleException(final Exception ex,
                                                   final WebRequest request) {
        final ErrorDto error = new ErrorDto(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
