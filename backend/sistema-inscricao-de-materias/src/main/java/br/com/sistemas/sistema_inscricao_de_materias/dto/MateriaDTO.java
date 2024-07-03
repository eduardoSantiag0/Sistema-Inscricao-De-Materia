package br.com.sistemas.sistema_inscricao_de_materias.dto;

import br.com.sistemas.sistema_inscricao_de_materias.model.Aluno;

import java.util.List;

public record MateriaDTO (
        String nomeMateria,
        List<Aluno> alunosInscritos,
        int capacidadeMaxima) { }
