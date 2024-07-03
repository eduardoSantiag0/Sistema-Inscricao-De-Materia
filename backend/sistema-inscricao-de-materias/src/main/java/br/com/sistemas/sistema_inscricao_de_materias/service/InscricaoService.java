package br.com.sistemas.sistema_inscricao_de_materias.service;
//import br.com.sistemas.sistema_inscricao_de_materias.repository.InscricaoRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.sistemas.sistema_inscricao_de_materias.model.Aluno;
import br.com.sistemas.sistema_inscricao_de_materias.model.Materia;
import br.com.sistemas.sistema_inscricao_de_materias.repository.AlunosRepository;
import br.com.sistemas.sistema_inscricao_de_materias.repository.MateriaRepository;
import org.springframework.stereotype.Service;

@Service
public class InscricaoService {
    private final AlunosRepository alunosRepository;
    private final MateriaRepository materiaRepository;
//    private final InscricaoRepository inscricaoRepository;


    public InscricaoService(AlunosRepository alunosRepository, MateriaRepository materiaRepository) {
        this.alunosRepository = alunosRepository;
        this.materiaRepository = materiaRepository;
    }

    @Transactional
    public void inscreverAluno(Aluno novoAluno, Materia materia) {
        Aluno alunoOperacao = materia.adicionarAluno(novoAluno, this);
        if (alunoOperacao != null) {
            alunosRepository.save(alunoOperacao);
            alunosRepository.save(novoAluno);
            materiaRepository.save(materia);
        } else {
            throw new IllegalStateException("Não foi possível inscrever o aluno na matéria");
        }
    }

}
