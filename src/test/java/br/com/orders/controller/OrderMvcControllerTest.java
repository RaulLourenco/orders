package br.com.orders.controller;

import br.com.orders.application.Constantes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Teste MVC do Controller de Pedidos")
class OrderMvcControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testSuccessGetTotalValue() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/order/total_value/30")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void testSuccessGetTotalQuantity() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/order/total_quantity/456")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void testSuccessGetOrdersList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/order/orders/456")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }


    @Test
    void testFailedGetTotalValue() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/order/total_value/500")
                        .contentType("application/json"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.mensagem",
                        is(String.format("%s: %s", Constantes.ORDER_NOT_FOUND_MESSAGE, 500))));
    }
    @Test
    void testFailedGetTotalQuantity() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/order/total_quantity/1000")
                        .contentType("application/json"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.mensagem",
                        is(String.format("%s: %s", Constantes.ORDER_LIST_NOT_FOUND_MESSAGE, 1000))));
    }

    @Test
    void testFailedGetOrdersList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/order/orders/876")
                        .contentType("application/json"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.mensagem",
                        is(String.format("%s: %s", Constantes.ORDER_LIST_NOT_FOUND_MESSAGE, 876))));
    }
}
