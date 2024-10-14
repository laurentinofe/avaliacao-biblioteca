package com.senac.biblioteca.dtos;

import com.senac.biblioteca.model.Exemplar;

public record ExemplarResponseDto(com.senac.biblioteca.model.Livro livro,
                                  Integer referencia,
                                  boolean disponivel) {
    public static ExemplarResponseDto toDto(Exemplar exemplar) {
        return new ExemplarResponseDto(exemplar.getLivro(),
                exemplar.getReferencia(),
                exemplar.isDisponivel());
    }
}
