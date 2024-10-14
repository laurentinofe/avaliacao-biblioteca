package com.senac.biblioteca.dtos;

import com.senac.biblioteca.model.Emprestimo;

import java.time.LocalDate;

public record EmprestimoResponseDto(ExemplarResponseDto exemplar,
                                    ClienteResponseDto cliente,
                                    LocalDate data) {
    public EmprestimoResponseDto(LocalDate data, ClienteResponseDto dto, Object dto1) {

    }

    public static EmprestimoResponseDto toDto(Emprestimo emprestimo) {
        return new EmprestimoResponseDto(emprestimo.getData(),
                ClienteResponseDto.toDto(emprestimo.getCliente()),
                ExemplarResponseDto.toDto(emprestimo.getExemplar()));
    }
}
