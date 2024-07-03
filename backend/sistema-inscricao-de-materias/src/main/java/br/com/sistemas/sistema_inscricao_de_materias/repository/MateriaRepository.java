package br.com.sistemas.sistema_inscricao_de_materias.repository;

import br.com.sistemas.sistema_inscricao_de_materias.model.Aluno;
import br.com.sistemas.sistema_inscricao_de_materias.model.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MateriaRepository extends JpaRepository <Materia, Long>
{
    Optional<Materia> findBynomeMateriaContainingIgnoreCase (String materia);
}
