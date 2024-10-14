package com.senac.biblioteca.dtos;

import com.senac.biblioteca.model.Cliente;

public record ClienteResponseDto(String nome,
                                 String cpf,
                                 String telefone,
                                 String email,
                                 boolean apto) {

    public static ClienteResponseDto toDto(Cliente cliente) {
        return new ClienteResponseDto(cliente.getNome(),cliente.getCpf(), cliente.getTelefone(), cliente.getEmail(), cliente.isApto());

    }
}
