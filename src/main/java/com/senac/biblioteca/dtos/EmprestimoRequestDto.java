package com.senac.biblioteca.dtos;

import com.senac.biblioteca.model.Cliente;
import com.senac.biblioteca.model.Emprestimo;
import com.senac.biblioteca.model.Exemplar;
import com.senac.biblioteca.repositories.ClienteRepository;


import java.time.LocalDate;

public record EmprestimoRequestDto(Exemplar exemplar,
                                   Cliente cliente,
                                   LocalDate data,
                                   Integer idCliente) {
    public Emprestimo toEmprestimo(Emprestimo emprestimo,
                                   ClienteRepository clienteRepository) {
        emprestimo.setExemplar(this.exemplar());
        emprestimo.setData(this.data());
        emprestimo.setCliente(clienteRepository.getReferenceById(this.idCliente()));
        return emprestimo;
    }

    public Integer exemplarId() {
        return null;
    }

    public Integer clienteId() {
        return null;
    }
}
