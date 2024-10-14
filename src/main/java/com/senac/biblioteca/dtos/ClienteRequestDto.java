package com.senac.biblioteca.dtos;

import com.senac.biblioteca.model.Cliente;
import com.senac.biblioteca.model.Exemplar;

public record ClienteRequestDto(String nome,
                                String cpf,
                                String telefone,
                                String email,
                                boolean apto) {
    public Cliente toCliente(Cliente cliente) {
        cliente.setNome(this.nome());
        cliente.setCpf(this.cpf());
        cliente.setTelefone(this.telefone());
        cliente.setEmail(this.email());
        cliente.setApto(this.apto());
        return cliente;
    }

    public Exemplar    toExemplar(Exemplar exemplar) {
    }
}
