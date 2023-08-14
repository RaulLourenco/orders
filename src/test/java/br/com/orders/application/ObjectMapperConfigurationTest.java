package br.com.orders.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("Teste da Config do ObjectMapper")
@ExtendWith(MockitoExtension.class)
class ObjectMapperConfigurationTest {

    @InjectMocks
    private ObjectMapperConfiguration configuration;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConfig() {
        final ObjectMapper objectMapper = configuration.objectMapper();

        Assertions.assertNotNull(objectMapper);
    }
}
