package br.com.orders.application;

import br.com.orders.application.exceptions.OrderNotFoundException;
import br.com.orders.dto.ErrorDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

@DisplayName("Teste do REST Exception Handler")
@ExtendWith(MockitoExtension.class)
class RestExceptionHandlerTest {

    @InjectMocks
    private RestExceptionHandler exceptionHandler;

    @Mock
    private WebRequest request;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testOrderNotFoundException() {
        final OrderNotFoundException exception = new OrderNotFoundException("teste");

        final ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage("teste");
        errorDto.setStatus(HttpStatus.NOT_FOUND.value());

        ResponseEntity<ErrorDto> response = exceptionHandler.handleException(exception, request);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals(errorDto, response.getBody());
    }

    @Test
    void testException() {
        final Exception exception = new Exception("teste");

        final ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage("teste");
        errorDto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        ResponseEntity<ErrorDto> response = exceptionHandler.handleException(exception, request);

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Assertions.assertEquals(errorDto, response.getBody());
    }
}
