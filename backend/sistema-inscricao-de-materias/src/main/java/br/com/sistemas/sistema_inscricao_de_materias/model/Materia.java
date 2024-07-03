package br.com.sistemas.sistema_inscricao_de_materias.model;

import br.com.sistemas.sistema_inscricao_de_materias.service.InscricaoService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
@Table (name="MATERIA")
public class Materia {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToMany(mappedBy = "materiasInscritas", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private List<Aluno> alunosInscritos = new ArrayList<>();

    private String nomeMateria;
    private int capacidadeMaxima;
    private boolean temEspaco;


    public Materia() {}

    public Materia(String nomeMateria, int capacidadeMaxima) {
        this.nomeMateria = nomeMateria;
        this.capacidadeMaxima = capacidadeMaxima;
        this.temEspaco = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeMateria() {
        return nomeMateria;
    }

    public void setNomeMateria(String nomeMateria) {
        this.nomeMateria = nomeMateria;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public boolean getTemEspaco () { return temEspaco; };

    public void setCapacidadeMaxima(int capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public List<Aluno> getAlunosInscritos() {
        return alunosInscritos;
    }

    public void setAlunosInscritos(List<Aluno> alunosInscritos) {
        this.alunosInscritos = alunosInscritos;
    }


    public Aluno adicionarAluno (Aluno novoAluno, InscricaoService inscricaoService) {
        try {
            if (this.calcularEspaco()) {
                alunosInscritos.add(novoAluno);
                novoAluno.addMateria(this);
                this.temEspaco = calcularEspaco();
                return novoAluno;
            } else {
                Aluno alunoMenorNota = alunosInscritos.stream()
                        .min(Comparator.comparingInt(Aluno::getNotaGeral))
                        .orElse(null);

                // Verifica se o novo aluno tem prioridade ou uma nota maior que o aluno com a menor nota
                if (alunoMenorNota != null && (novoAluno.isPrioridade() || novoAluno.getNotaGeral() > alunoMenorNota.getNotaGeral())) {
                    //* Remove o aluno com a menor nota
                    alunosInscritos.remove(alunoMenorNota);
                    alunoMenorNota.removeMateria(this);

                    //* Adiciona novo aluno
                    novoAluno.addMateria(this);
                    alunosInscritos.add(novoAluno);


                    return alunoMenorNota;
                } else {
                    // Se não há espaço e o novo aluno não tem prioridade nem uma nota maior, rejeita a adição
                    System.out.println("Está cheia a sala e o novo aluno não tem prioridade nem uma nota maior.");
                    return null;
                }
            }
        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao adicionar o aluno: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

    }

    private boolean calcularEspaco() {
        return alunosInscritos.size() < this.capacidadeMaxima;
    }


    @Override
    public String toString() {
        return "Materia{" +
                "id=" + id +
                ", nomeMateria='" + nomeMateria + '\'' +
                ", capacidadeMaxima=" + capacidadeMaxima +
                '}';
    }

}
