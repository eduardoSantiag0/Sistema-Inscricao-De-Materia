package br.com.sistemas.sistema_inscricao_de_materias.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;

@Entity
@Table (name="aluno_materia")
public class AlunoMateria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public AlunoMateria() {}

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "materia_id")
    private Materia materia;

    public AlunoMateria(Aluno aluno, Materia materia) {
        this.aluno = aluno;
        this.materia = materia;
    }

}
