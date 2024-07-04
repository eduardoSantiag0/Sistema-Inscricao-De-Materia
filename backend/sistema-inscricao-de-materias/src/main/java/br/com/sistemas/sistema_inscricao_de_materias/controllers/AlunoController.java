package br.com.sistemas.sistema_inscricao_de_materias.controllers;

import br.com.sistemas.sistema_inscricao_de_materias.dto.AlunoDTO;
import br.com.sistemas.sistema_inscricao_de_materias.model.Aluno;
import br.com.sistemas.sistema_inscricao_de_materias.model.Materia;
import br.com.sistemas.sistema_inscricao_de_materias.service.AlunoService;
import br.com.sistemas.sistema_inscricao_de_materias.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/alunos")
public class AlunoController {
    @Autowired
    private AlunoService alunoService;

    @Autowired
    private MateriaService materiaService;


    @PostMapping("/add")
    public String add(@RequestBody AlunoDTO alunoDTO) {
        try {
            System.out.println(alunoDTO);

            List<Materia> materiasInscritas = alunoDTO.materiasInscritas().stream()
                    .map(nomeMateria -> materiaService.findByNomeMateria(nomeMateria)
                            .orElseThrow(() -> new IllegalArgumentException("Matéria não encontrada: " + nomeMateria)))
                    .collect(Collectors.toList());

            Aluno aluno = converterAluno(alunoDTO, materiasInscritas);

            alunoService.salvarAluno(aluno);
            return "Novo aluno adicionado";
        } catch (Exception e) {
            return "Erro ao adicionar aluno: " + e.getMessage();
        }
    }



    private Aluno converterAluno(AlunoDTO alunoDTO, List <Materia> materias) {
        return new Aluno(alunoDTO.nomeCompleto(), alunoDTO.notaGeral(), alunoDTO.anoIngressao(), materias);
    }

   @GetMapping("/find")
    public List<AlunoDTO> findAlunos () {
       List<Aluno> listaAlunos =  alunoService.findAll();


       List<String> listaMaterias = listaAlunos.stream()
               .flatMap(a -> a.getMatriasInscritas().stream())
               .map(Materia::getNomeMateria)
               .collect(Collectors.toList());

       List<AlunoDTO> listaDTOs = listaAlunos.stream()
               .map(aluno -> new AlunoDTO(
                       aluno.getNomeCompleto(),
                       aluno.getNotaGeral(),
                       aluno.getAnoIngressao().getYear(),
                       listaMaterias
               ))
               .collect(Collectors.toList());
       return listaDTOs;
   }

}
