package com.lootchaser.infraestructure.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class GameRequest {

    @NotBlank(message = "O título do jogo é obrigatório.")
    public String title;

    @NotBlank(message = "O gênero do jogo é obrigatório.")
    public String genre;

    @NotNull(message = "O preço base é obrigatório.")
    @Positive(message = "O preço base deve ser maior que zero.")
    public Double basePrice;

    @NotNull(message = "O ID da loja é obrigatório.")
    public Long storeId;
}