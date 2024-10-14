package com.senac.biblioteca.controllers;

import com.senac.biblioteca.dtos.EmprestimoRequestDto;
import com.senac.biblioteca.dtos.EmprestimoResponseDto;
import com.senac.biblioteca.model.Cliente;
import com.senac.biblioteca.model.Emprestimo;
import com.senac.biblioteca.model.Exemplar;
import com.senac.biblioteca.repositories.ClienteRepository;
import com.senac.biblioteca.repositories.EmprestimoRepository;
import com.senac.biblioteca.repositories.ExemplarRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("{emprestimos}")
public class EmprestimoController {
    private final EmprestimoRepository emprestimoRepository;
    private final ExemplarRepository exemplarRepository;
    private final ClienteRepository clienteRepository;

    public EmprestimoController(EmprestimoRepository emprestimoRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.exemplarRepository = exemplarRepository;
        this.clienteRepository = clienteRepository;

    }

    public ResponseEntity<Page<EmprestimoResponseDto>> findAll(@RequestParam(name = "numeroPagina", required = false, defaultValue = "0") int numeroPagina,
                                                               @RequestParam(name = "quantidade", required = false, defaultValue = "5") int quantidade) {
        PageRequest pageRequest = PageRequest.of(numeroPagina, quantidade);
        return ResponseEntity.ok(emprestimoRepository.findAll(pageRequest)
                .map(emprestimo -> EmprestimoResponseDto.toDto(emprestimo)));
    }

    @GetMapping("{id}")
    public ResponseEntity<EmprestimoResponseDto> findById(@PathVariable("id") Integer id) {
        Optional<Emprestimo> emprestimo = emprestimoRepository.findById(id);

        if (emprestimo.isPresent()) {
            return ResponseEntity.ok(EmprestimoResponseDto.toDto(emprestimo.get()));
        } else {
            throw new EntityNotFoundException("Empréstimo não encontrado");
        }
    }

    @PostMapping
    public ResponseEntity<EmprestimoResponseDto> save(@RequestBody EmprestimoRequestDto dto) {
        Optional<Exemplar> exemplarOpt = exemplarRepository.findById(dto.exemplarId());
        if (exemplarOpt.isEmpty()) {
            throw new EntityNotFoundException("Exemplar não encontrado.");
        }
        Exemplar exemplar = exemplarOpt.get();

        if (!exemplar.isDisponivel()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Optional<Cliente> clienteOpt = clienteRepository.findById(dto.clienteId());
        if (clienteOpt.isEmpty()) {
            throw new EntityNotFoundException("Cliente não encontrado.");
        }
        Cliente cliente = clienteOpt.get();

        if (!cliente.isApto()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Emprestimo emprestimo = dto.toEmprestimo(new Emprestimo(), exemplarRepository, clienteRepository);

        exemplar.setDisponivel(false);
        exemplarRepository.save(exemplar);

        // Salvar o empréstimo
        emprestimoRepository.save(emprestimo);

        return ResponseEntity
                .created(URI.create("/emprestimos/" + emprestimo.getId()))
                .body(EmprestimoResponseDto.toDto(emprestimo));
    }

    @PutMapping("{id}")
    public ResponseEntity<EmprestimoResponseDto> update(@PathVariable("id") Integer id, @RequestBody EmprestimoRequestDto dto) {
        Optional<Emprestimo> emprestimoOpt = emprestimoRepository.findById(id);

        if (emprestimoOpt.isPresent()) {
            Emprestimo emprestimoSalvo = dto.toEmprestimo(emprestimoOpt.get());
            return ResponseEntity.ok(EmprestimoResponseDto.toDto(emprestimoRepository.save(emprestimoSalvo)));
        } else {
            throw new EntityNotFoundException("Emprestimo não encontrado");
        }
    }

    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        emprestimoRepository.deleteById(id);
    }


}
