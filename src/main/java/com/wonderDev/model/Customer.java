package com.wonderDev.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Customer extends PanacheEntity {
    @NotBlank
    public String firstName;

    public String middleName;

    @NotBlank
    public String lastName;

    public String secondLastName;

    @Email
    @NotBlank
    @Column(unique = true)
    public String email;

    @NotBlank
    public String address;

    @NotBlank
    @Size(min = 10, max = 15)
    public String phone;

    @NotBlank
    public String country;

    public String demonym; // Will be retrieved from external API
}
