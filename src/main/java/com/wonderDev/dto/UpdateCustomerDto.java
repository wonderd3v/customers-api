package com.wonderDev.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateCustomerDto {

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
