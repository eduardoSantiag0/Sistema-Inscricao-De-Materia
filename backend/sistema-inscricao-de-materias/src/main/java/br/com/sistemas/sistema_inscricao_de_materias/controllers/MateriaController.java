package br.com.sistemas.sistema_inscricao_de_materias.controllers;

import br.com.sistemas.sistema_inscricao_de_materias.dto.MateriaDTO;
import br.com.sistemas.sistema_inscricao_de_materias.model.Materia;
import br.com.sistemas.sistema_inscricao_de_materias.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/materias")
public class MateriaController {

    @Autowired
    private MateriaService materiaService;

    @GetMapping ("/find")
    public List<MateriaDTO> findMaterias () {
        List<Materia> listaMateria = materiaService.findAll();

        List<MateriaDTO> materiaDTOs = listaMateria.stream()
                .map(m -> new MateriaDTO(m.getNomeMateria(), m.getAlunosInscritos(), m.getCapacidadeMaxima()))
                .collect(Collectors.toList());

        return materiaDTOs;
    }
}
