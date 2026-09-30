package com.lootchaser.infraestructure.rest.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class StoreRequest {
    @NotBlank(message = "Name is required")
    public String name;

    @NotBlank(message = "Domain is required")
    public String domain;

    @NotBlank(message = "Base URL is required")
    @URL(message = "Base URL must be a valid URL")
    public String baseUrl;
}