package com.wonderDev.mapper;

import com.wonderDev.dto.CreateCustomerDto;
import com.wonderDev.dto.CustomerDto;
import com.wonderDev.dto.UpdateCustomerDto;
import com.wonderDev.model.Customer;

public class CustomerMapper {

    public static CustomerDto toDTO(Customer customer) {
        CustomerDto dto = new CustomerDto();
        dto.id = customer.id;
        dto.firstName = customer.firstName;
        dto.middleName = customer.middleName;
        dto.lastName = customer.lastName;
        dto.secondLastName = customer.secondLastName;
        dto.email = customer.email;
        dto.address = customer.address;
        dto.phone = customer.phone;
        dto.country = customer.country;
        dto.demonym = customer.demonym;
        return dto;
    }

    public static Customer fromCreateDTO(CreateCustomerDto dto) {
        Customer customer = new Customer();
        customer.firstName = dto.firstName;
        customer.middleName = dto.middleName;
        customer.lastName = dto.lastName;
        customer.secondLastName = dto.secondLastName;
        customer.email = dto.email;
        customer.address = dto.address;
        customer.phone = dto.phone;
        customer.country = dto.country;
        return customer;
    }

    public static void updateEntity(Customer customer, UpdateCustomerDto dto) {
        customer.email = dto.email;
        customer.address = dto.address;
        customer.phone = dto.phone;
        customer.country = dto.country;
    }
}
