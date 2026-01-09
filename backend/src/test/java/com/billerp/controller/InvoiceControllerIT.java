package com.billerp.controller;

import com.billerp.dto.request.InvoiceCreateDTO;
import com.billerp.dto.response.InvoiceResponse;
import com.billerp.service.interfaces.InvoiceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@com.tngtech.archunit.junit.ArchTag("integration")
@org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest(controllers = InvoiceController.class, excludeAutoConfiguration = {
                org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration.class
})
@Disabled("Requires a full environment with RSA keys and MongoDB mocked or available")
class InvoiceControllerIT {

        @Autowired
        private MockMvc mockMvc;

        @MockitoBean
        private InvoiceService invoiceService;

        @Autowired
        private ObjectMapper objectMapper;

        @Test
        @DisplayName("Should create invoice via API")
        void shouldCreateInvoice() throws Exception {
                InvoiceCreateDTO dto = new InvoiceCreateDTO("cust1", Collections.emptyList(), null);
                InvoiceResponse response = new InvoiceResponse("inv1", "cust1", Collections.emptyList(),
                                BigDecimal.ZERO, null,
                                null, null, null);

                when(invoiceService.create(any(InvoiceCreateDTO.class))).thenReturn(response);

                mockMvc.perform(post("/api/v1/invoices")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.id").value("inv1"))
                                .andExpect(jsonPath("$.customerId").value("cust1"));
        }

        @Test
        @DisplayName("Should find invoice by ID via API")
        void shouldFindById() throws Exception {
                InvoiceResponse response = new InvoiceResponse("inv1", "cust1", Collections.emptyList(),
                                BigDecimal.ZERO, null,
                                null, null, null);

                when(invoiceService.findById("inv1")).thenReturn(response);

                mockMvc.perform(get("/api/v1/invoices/inv1"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value("inv1"));
        }
}
