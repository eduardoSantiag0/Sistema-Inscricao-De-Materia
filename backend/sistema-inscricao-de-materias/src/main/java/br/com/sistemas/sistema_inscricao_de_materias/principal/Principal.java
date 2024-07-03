package br.com.sistemas.sistema_inscricao_de_materias.principal;

import br.com.sistemas.sistema_inscricao_de_materias.model.Aluno;
import br.com.sistemas.sistema_inscricao_de_materias.model.Materia;
import br.com.sistemas.sistema_inscricao_de_materias.repository.AlunosRepository;
import br.com.sistemas.sistema_inscricao_de_materias.repository.MateriaRepository;
import br.com.sistemas.sistema_inscricao_de_materias.service.AlunoService;
import br.com.sistemas.sistema_inscricao_de_materias.service.InscricaoService;
import br.com.sistemas.sistema_inscricao_de_materias.service.MateriaService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class Principal
{
    private Scanner leitura = new Scanner(System.in);
    private final AlunoService alunoService;
    private final MateriaService materiaService;
    private final InscricaoService inscricaoService;



    public Principal(AlunoService alunoService, MateriaService materiaService, InscricaoService inscricaoService) {
        this.alunoService = alunoService;
        this.materiaService = materiaService;
        this.inscricaoService = inscricaoService;
    }

    public void menu() {

        int escolhaUsuario = -1;
        String menu = "1 - Adicionar Aluno\n" +
                "2 - Criar Matéria\n" +
                "3 - Ver Alunos inscritos na Matéria\n" +
                "4 - Ver Melhores Alunos \n" +
                "5 - Adicionar Aluno Já Matriculado em Matéria\n" +
                "6 - Ver Espaço Disponível nas Matérias" +
                "\n0 - Sair";



        while (escolhaUsuario != 0)
        {
            System.out.println(menu);
            escolhaUsuario = leitura.nextInt();
            leitura.nextLine();
            switch (escolhaUsuario) {
                case 1:
                    adicionarAluno();
                    break;
                case 2:
                    criarMateria();
                    break;
                case 3:
                    verAlunosInscritos();
                    break;
                case 4:
                    verMelhoresAlunos();
                    break;
                case 5:
                    adicionarAlunoJaMatriculado();
                    break;
                case 6:
                    verTamanhodasSalas();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção indisponível");

            }

        }

    }


    private void verAlunosInscritos() {
        Optional<Materia> materia = escolherMateria();
        if (materia.isPresent()) {
            List<Aluno> alunos = materia.get().getAlunosInscritos();
            alunos.forEach(System.out::println);
        } else {
            System.out.println("Matéria não encontrada ou inválida.");
        }
    }

    private void adicionarAlunoJaMatriculado() {
        List<Aluno> alunosCadastrados = alunoService.findAll();
        alunosCadastrados.forEach(System.out::println);

        System.out.println("Qual o nome do aluno: ");
        String name = leitura.nextLine();

        Optional<Aluno> alunoBuscado = alunoService.findByNomeCompleto(name);
        if (alunoBuscado.isPresent()) {
            inscreverAluno(alunoBuscado.get());
        } else {
            System.out.println("Aluno não encontrado!");
        }

    }


    private Optional<Materia> escolherMateria () {
        List<Materia> materiasCadastradas = materiaService.findAll();
        materiasCadastradas.forEach(System.out::println);
        System.out.println("Qual matéria?");

        String nomeMateriaBuscada = leitura.nextLine();
        return materiaService.findByNomeMateria(nomeMateriaBuscada);
    }


    private void adicionarAluno() {

        System.out.println("Nome: ");
        String inNome = leitura.nextLine();
        System.out.println("Nota Geral (Entre 0 e 10.000): ");
        int notaGeral = leitura.nextInt();
        leitura.nextLine();
        System.out.println("Ano de Ingressão: ");
        int inAno = leitura.nextInt();
        leitura.nextLine();

        Aluno aluno = new Aluno(inNome, notaGeral, inAno);
        alunoService.salvarAluno(aluno);


        System.out.println("Deseja inscrever esse aluno em alguma materia?");
        System.out.println("Digite 1 - Aceitar");
        System.out.println("Digite 2 - Recusar");
        int inscreverSim = leitura.nextInt();
        leitura.nextLine();
        if (inscreverSim == 1)
            inscreverAluno(aluno);

    }

    public void criarMateria() {
        System.out.println("Informe o nome da materia");
        String nomeMateria = leitura.nextLine();
        System.out.println("Informe a capacidade maxima da sala");
        int capacidadeMaxima = leitura.nextInt();

        Materia novaMateria = new Materia(nomeMateria, capacidadeMaxima);
        materiaService.salvarMateria(novaMateria);
        System.out.println("Sucesso!");

    }

    private void inscreverAluno(Aluno novoAluno) {
        Optional<Materia> materiaOptional = escolherMateria();

        if (materiaOptional.isPresent()) {
            Materia materia = materiaOptional.get();
            try {
                inscricaoService.inscreverAluno(novoAluno, materia);
                System.out.println("Aluno inscrito com sucesso!");
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Matéria inválida");
        }
    }

    private void verMelhoresAlunos() {
        System.out.println("Os melhores alunos cadastrados: ");
        List<Aluno> alunos =  alunoService.findByOrderByNotaGeralDesc();
        alunos.stream().forEach(System.out::println);
    }


    private void verTamanhodasSalas() {
        List<Materia> todasMaterias = materiaService.findAll();
        todasMaterias.forEach(m -> System.out.println( "\n" + m.getNomeMateria() + ", Alunos Inscritos: " + m.getAlunosInscritos().size() + ", Capacidade Máxima: " + m.getCapacidadeMaxima()  + ", Tem Espaço: " + m.getTemEspaco() + "\n"));
    }


}
