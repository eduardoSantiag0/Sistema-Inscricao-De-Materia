package br.com.sistemas.sistema_inscricao_de_materias.service;

import br.com.sistemas.sistema_inscricao_de_materias.model.Aluno;
import br.com.sistemas.sistema_inscricao_de_materias.repository.AlunosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    private final AlunosRepository alunoRepository;

    @Autowired
    public AlunoService(AlunosRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public Aluno salvarAluno(Aluno aluno) {
        int ano = aluno.getAnoIngressao().getYear();
//        Aluno novoAluno = new Aluno(aluno.getNomeCompleto(), aluno.getNotaGeral(), ano);
        Aluno novoAluno = new Aluno(aluno.getNomeCompleto(), aluno.getNotaGeral(), ano, aluno.getMatriasInscritas());
        return alunoRepository.save(novoAluno);
    }

    public List<Aluno> findAll() {

        return alunoRepository.findAll();
    }

    public Optional<Aluno> findByNomeCompleto(String nome) {
        return alunoRepository.findBynomeCompletoContainingIgnoreCase(nome);
    }

    public List<Aluno> findByOrderByNotaGeralDesc() {
        return alunoRepository.findByOrderByNotaGeralDesc();
    }

}
