package com.billerp.domain.validator;

import com.billerp.domain.exception.InvoiceNotFoundException;
import com.billerp.domain.repository.InvoiceRepository;
import com.billerp.dto.request.InvoiceCreateDTO;
import com.billerp.dto.request.InvoiceItemRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvoiceValidatorTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private ProductValidator productValidator;

    @Mock
    private CustomerValidator customerValidator;

    @InjectMocks
    private InvoiceValidator validator;

    @Test
    @DisplayName("Should validate ID successfully")
    void shouldValidateId() {
        when(invoiceRepository.existsById("123")).thenReturn(true);
        assertThatCode(() -> validator.validateId("123")).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Should throw exception if ID does not exist")
    void shouldThrowExceptionIfIdDoesNotExist() {
        when(invoiceRepository.existsById("456")).thenReturn(false);
        assertThatThrownBy(() -> validator.validateId("456"))
                .isInstanceOf(InvoiceNotFoundException.class);
    }

    @Test
    @DisplayName("Should validate invoice items")
    void shouldValidateInvoiceItems() {
        InvoiceItemRequest item = new InvoiceItemRequest("prod1", 1);
        List<InvoiceItemRequest> items = Collections.singletonList(item);

        assertThatCode(() -> validator.validateInvoiceItems(items)).doesNotThrowAnyException();
        verify(productValidator).validateId("prod1");
    }

    @Test
    @DisplayName("Should validate customer")
    void shouldValidateCustomer() {
        assertThatCode(() -> validator.validateCustomer("cust1")).doesNotThrowAnyException();
        verify(customerValidator).validateId("cust1");
    }

    @Test
    @DisplayName("Should validate Create DTO")
    void shouldValidateCreateDto() {
        InvoiceItemRequest item = new InvoiceItemRequest("prod1", 1);
        InvoiceCreateDTO dto = new InvoiceCreateDTO("cust1", Collections.singletonList(item), null);

        assertThatCode(() -> validator.validateCreate(dto)).doesNotThrowAnyException();
        verify(customerValidator).validateId("cust1");
        verify(productValidator).validateId("prod1");
    }
}
