package com.senac.biblioteca.dtos;

import com.senac.biblioteca.model.Categoria;
import com.senac.biblioteca.model.Livro;

public record LivroRequestDto(String nome,
                              String autor,
                              Integer anoPublicacao,
                              Integer isbn,
                              Categoria categoria) {
    public Livro toLivro(Livro livro) {
        livro.setNome(this.nome());
        livro.setAutor(this.autor());
        livro.setAnoPublicacao(this.anoPublicacao());
        livro.setIsbn(this.isbn());
        livro.setCategoria(this.categoria());
        return livro;
    }
}
