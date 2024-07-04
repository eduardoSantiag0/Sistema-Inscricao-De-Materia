package br.com.sistemas.sistema_inscricao_de_materias.dto;

import br.com.sistemas.sistema_inscricao_de_materias.model.Materia;

import java.util.List;

//JSON parse error: Cannot construct instance of Materia:
//no String-argument constructor/factory method to deserialize from String value ('Cálculo 1')]

public record AlunoDTO(
        String nomeCompleto,
        int notaGeral,
        int anoIngressao,
        List<String> materiasInscritas
) { }
