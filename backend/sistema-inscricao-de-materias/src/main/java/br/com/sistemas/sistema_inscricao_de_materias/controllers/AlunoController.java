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

//    JSON parse error: Cannot construct instance of `br.com.sistemas.sistema_inscricao_de_materias.model.Materia` (although at least one Creator exists): no String-argument constructor/factory method to deserialize from String value ('Cinema Novo')]

    @PostMapping("/add")
    public String add(@RequestBody AlunoDTO alunoDTO) {
        try {
            System.out.println(alunoDTO);
            Aluno aluno = convertToAlunoEntity(alunoDTO);

            List<Materia> materiasInscritas = alunoDTO.materiasInscritas().stream()
                    .map(m -> materiaService.findByNomeMateria(m.getNomeMateria()).orElseThrow(() -> new IllegalArgumentException("Matéria não encontrada: " + m)))
                    .collect(Collectors.toList());
            aluno.setMateriasInscritas(materiasInscritas);

            alunoService.salvarAluno(aluno);
            return "Novo aluno adicionado";
        } catch (Exception e) {
            return "Erro ao adicionar aluno: " + e.getMessage();
        }
    }


//    @PostMapping("/add")
//    public String add(@RequestBody AlunoDTO alunoDTO) {
//        try {
//            System.out.println("AlunoDTO recebido: " + alunoDTO);
//
//            // Convertendo DTO para entidade Aluno
//            Aluno aluno = convertToAlunoEntity(alunoDTO);
//
//            // Buscando e associando matérias
//            List<Materia> materiasInscritas = alunoDTO.materiasInscritas().stream()
//                    .map(m -> {
//                        Optional<Materia> materia = materiaService.findByNomeMateria(m.getNomeMateria());
//                        if (materia.isPresent()) {
//                            return materia.get();
//                        } else {
//                            throw new IllegalArgumentException("Matéria não encontrada: " + m);
//                        }
//                    })
//                    .collect(Collectors.toList());
//            aluno.setMateriasInscritas(materiasInscritas);
//
//            // Salvando aluno
//            alunoService.salvarAluno(aluno);
//            return "Novo aluno adicionado";
//        } catch (Exception e) {
//            return "Erro ao adicionar aluno: " + e.getMessage();
//        }
//    }


    private Aluno convertToAlunoEntity(AlunoDTO alunoDTO) {
        return new Aluno(alunoDTO.nomeCompleto(), alunoDTO.notaGeral(), alunoDTO.anoIngressao(), alunoDTO.materiasInscritas());
    }


   @GetMapping("/find")
    public List<AlunoDTO> findAlunos () {
       List<Aluno> listaAlunos =  alunoService.findAll();

       List<AlunoDTO> listaDTOs = listaAlunos.stream()
               .map(aluno -> new AlunoDTO(
                       aluno.getNomeCompleto(),
                       aluno.getNotaGeral(),
                       aluno.getAnoIngressao().getYear(),
                       aluno.getMatriasInscritas()
               ))
               .collect(Collectors.toList());
       return listaDTOs;
   }
}
