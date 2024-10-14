package com.senac.biblioteca.dtos;

import com.senac.biblioteca.model.Exemplar;
import com.senac.biblioteca.model.Livro;
import com.senac.biblioteca.repositories.EmprestimoRepository;
import com.senac.biblioteca.repositories.LivroRepository;

public record ExemplarRequestDto(Livro livro,
                                 Integer referencia,
                                 boolean disponivel,
                                 Integer idLivro) {

    public Exemplar toExemplar(Exemplar exemplar,
                               LivroRepository livroRepository) {

        exemplar.setReferencia(this.referencia());
        exemplar.setDisponivel(this.disponivel());
        exemplar.setLivro(livroRepository.getReferenceById(this.idLivro()));
        return exemplar;
    }
}
