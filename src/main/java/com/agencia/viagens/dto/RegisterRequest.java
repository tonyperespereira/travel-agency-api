package com.agencia.viagens.dto;

import com.agencia.viagens.model.Perfil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String nome;

    @NotBlank
    @Email
    private String email;

    @NotNull
    private Perfil perfil;
}
