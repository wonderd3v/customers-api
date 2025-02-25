package com.wonderDev.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateCustomerDto {

    @NotBlank
    public String firstName;

    public String middleName;

    @NotBlank
    public String lastName;

    public String secondLastName;

    @Email
    @NotBlank
    public String email;

    @NotBlank
    public String address;

    @NotBlank
    @Size(min = 10, max = 15)
    public String phone;

    @NotBlank
    public String country;
}
