package com.wonderDev;

import com.wonderDev.dto.CreateCustomerDto;
import com.wonderDev.dto.CustomerDto;
import com.wonderDev.dto.UpdateCustomerDto;
import com.wonderDev.exception.CustomerNotfoundException;
import com.wonderDev.model.Customer;
import com.wonderDev.repository.CustomerRepository;
import com.wonderDev.service.CustomerService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class CustomerServiceTest {

    @InjectMock
    CustomerRepository customerRepository;

    @Inject
    CustomerService customerService;

    @Test
    public void shouldCreateCustomer() {
        CreateCustomerDto dto = new CreateCustomerDto();
        dto.firstName = "John";
        dto.lastName = "Doe";
        dto.email = "john.doe@example.com";
        dto.address = "123 Main St";
        dto.phone = "123456789";
        dto.country = "US";

        when(customerRepository.findByEmail(dto.email)).thenReturn(null);

        CustomerDto createdCustomer = customerService.createCustomer(dto);

        assertNotNull(createdCustomer);
        assertEquals("John", createdCustomer.firstName);
        assertEquals("Doe", createdCustomer.lastName);
        assertEquals("john.doe@example.com", createdCustomer.email);

        verify(customerRepository).persist(any(Customer.class));
    }

    @Test
    public void shoutGetACustomerById() {
        Long customerId = 1L;
        Customer customer = new Customer();
        customer.id = customerId;
        customer.firstName = "John";
        customer.lastName = "Doe";
        customer.email = "john.doe@example.com";
        customer.address = "123 Main St";
        customer.phone = "123456789";
        customer.country = "US";

        when(customerRepository.findById(customerId)).thenReturn(customer);

        CustomerDto result = customerService.getCustomerById(customerId);

        assertNotNull(result);
        assertEquals("John", result.firstName);
        assertEquals("Doe", result.lastName);

        verify(customerRepository).findById(customerId);
    }

    @Test
    public void shouldGetNotExistingCustomerException() {
        Long customerId = 999L;

        when(customerRepository.findById(customerId)).thenReturn(null);

        assertThrows(CustomerNotfoundException.class, () -> {
            customerService.getCustomerById(customerId);
        });

        verify(customerRepository).findById(customerId);
    }

    @Test
    public void shouldUpdateCustomer() {
        Long customerId = 1L;
        UpdateCustomerDto updateDto = new UpdateCustomerDto();
        updateDto.email = "new.email@example.com";
        updateDto.address = "New Address 123";
        updateDto.phone = "987654321";
        updateDto.country = "CA";

        Customer existingCustomer = new Customer();
        existingCustomer.id = customerId;
        existingCustomer.firstName = "John";
        existingCustomer.lastName = "Doe";
        existingCustomer.email = "old.email@example.com";
        existingCustomer.address = "Old Address 456";
        existingCustomer.phone = "123456789";
        existingCustomer.country = "US";

        when(customerRepository.findById(customerId)).thenReturn(existingCustomer);

        CustomerDto updatedCustomer = customerService.updateCustomer(customerId, updateDto);

        assertNotNull(updatedCustomer);
        assertEquals(updateDto.email, updatedCustomer.email);
        assertEquals(updateDto.address, updatedCustomer.address);
        assertEquals(updateDto.phone, updatedCustomer.phone);
        assertEquals(updateDto.country, updatedCustomer.country);

        verify(customerRepository).findById(customerId);
    }

    @Test
    public void shouldNotFindCustomerByIdOnUpdate() {
        Long customerId = 999L;
        UpdateCustomerDto updateDto = new UpdateCustomerDto();
        updateDto.email = "notfound@example.com";

        when(customerRepository.findById(customerId)).thenReturn(null);

        assertThrows(CustomerNotfoundException.class, () -> {
            customerService.updateCustomer(customerId, updateDto);
        });

        verify(customerRepository).findById(customerId);
    }

    @Test
    public void shouldDeleteCustomer() {
        Long customerId = 1L;
        when(customerRepository.deleteById(customerId)).thenReturn(true);

        assertDoesNotThrow(() -> customerService.deleteCustomer(customerId));

        verify(customerRepository).deleteById(customerId);
    }

    @Test
    public void shouldNotFindCustomerByIdOnDelete() {
        Long customerId = 999L;

        when(customerRepository.deleteById(customerId)).thenReturn(false);

        assertThrows(CustomerNotfoundException.class, () -> {
            customerService.deleteCustomer(customerId);
        });

        verify(customerRepository).deleteById(customerId);
    }
}
