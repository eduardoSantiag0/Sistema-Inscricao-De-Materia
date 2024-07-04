package br.com.sistemas.sistema_inscricao_de_materias.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name="ALUNO")
public class Aluno {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeCompleto;
    private int notaGeral;
    private static final int anoAtual = LocalDate.now().getYear();

    //! Responsável pela atualização da tabela JOIN
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "aluno_materia",
            joinColumns = @JoinColumn(name = "aluno_id"),
            inverseJoinColumns = @JoinColumn(name = "materia_id")
    )
    private List<Materia> materiasInscritas;

    private LocalDate anoIngressao;
    private boolean prioridade;

    public Aluno() {}

    public Aluno(String nomeCompleto, int notaGeral, int inputAnoIngressao, List<Materia> materiasInscritas) {
        this(nomeCompleto, notaGeral, inputAnoIngressao);
        this.materiasInscritas = materiasInscritas;
    }

    public Aluno (String nomeCompleto, int notaGeral, int inputAnoIngressao) {
        this.nomeCompleto = nomeCompleto;
        
        if (checarNota(notaGeral)) {
            this.notaGeral = notaGeral;
        } else {
            throw  new IllegalArgumentException("Nota inválida");
        }
        
        if (checarAno(inputAnoIngressao)) {
            this.anoIngressao  = LocalDate.of(inputAnoIngressao, 1, 1);
            if ( anoAtual - inputAnoIngressao  >= 3) {
                this.prioridade = true;
            } else {
                this.prioridade = false;
            }
        }
        else {
            throw new IllegalArgumentException("Ano de ingressão inválido ou excedeu o prazo limite para terminar a graduação");
        }

        this.materiasInscritas = new ArrayList<>();
    }

    private boolean checarAno (int anoInput) {
        int anosPermanencia = anoAtual - anoInput;

        if (anosPermanencia > 5) {
            System.out.println("Você já passou do prazo limite para terminar a graduação");
            return false;
        } else if (anosPermanencia < 0) {
            System.out.println("Ano de ingressão inválido");
            return false;
        }
        return true;
    }

    private boolean checarNota (int inputNota) {
        if (inputNota > 10000)
            return false;
        else if (inputNota < 0)
            return false;
        else
            return true;
    }


    public boolean isPrioridade() {
        return prioridade;
    }

    public void setPrioridade(boolean prioridade) {
        this.prioridade = prioridade;
    }

    public LocalDate getAnoIngressao() {
        return anoIngressao;
    }

    public void setAnoIngressao(LocalDate anoIngressao) {
        this.anoIngressao = anoIngressao;
    }

    public List<Materia> getMatriasInscritas() {
        return materiasInscritas;
    }

    public void setMateriasInscritas(List<Materia> materiasInscritas) {
        this.materiasInscritas = materiasInscritas;
    }

    public int getNotaGeral() {
        return notaGeral;
    }

    public void setNotaGeral(int notaGeral) {
        this.notaGeral = notaGeral;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Nome Completo='" + nomeCompleto + '\'' +
                ", Nota Geral =" + notaGeral;
    }

    public void addMateria(Materia materia) {
        this.materiasInscritas.add(materia);
        materia.getAlunosInscritos().add(this);
    }

    public void removeMateria(Materia materia) {
        if (this.materiasInscritas.contains(materia)) {
            this.materiasInscritas.remove(materia);
            materia.getAlunosInscritos().remove(this);
        }
    }
}
