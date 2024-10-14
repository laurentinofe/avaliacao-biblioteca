package com.senac.biblioteca.dtos;

import com.senac.biblioteca.model.Categoria;
import com.senac.biblioteca.model.Livro;

public record LivroResponseDto(String nome,
                               String autor,
                               Integer anoPublicacao,
                               Integer isbn,
                               Categoria categoria) {
    public static LivroResponseDto toDto(Livro livro) {
        return new LivroResponseDto(livro.getNome(),
                livro.getAutor(),
                livro.getAnoPublicacao(),
                livro.getIsbn(),
                livro.getCategoria());
    }
}
