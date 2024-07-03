package br.com.sistemas.sistema_inscricao_de_materias.service;

import br.com.sistemas.sistema_inscricao_de_materias.model.Materia;
import br.com.sistemas.sistema_inscricao_de_materias.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MateriaService {
    private final MateriaRepository materiaRepository;

    public MateriaService(MateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;
    }

    public Materia salvarMateria(Materia materia) {
        return materiaRepository.save(materia);
    }

    public List<Materia> findAll() {
        return materiaRepository.findAll();
    }

    public Optional<Materia> findByNomeMateria(String nomeMateria) {
        return materiaRepository.findBynomeMateriaContainingIgnoreCase(nomeMateria);
    }
}
