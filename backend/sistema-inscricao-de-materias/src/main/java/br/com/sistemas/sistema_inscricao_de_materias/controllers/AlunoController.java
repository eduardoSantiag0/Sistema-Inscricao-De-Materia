package br.com.sistemas.sistema_inscricao_de_materias.controllers;

import br.com.sistemas.sistema_inscricao_de_materias.dto.AlunoDTO;
import br.com.sistemas.sistema_inscricao_de_materias.model.Aluno;
import br.com.sistemas.sistema_inscricao_de_materias.model.Materia;
import br.com.sistemas.sistema_inscricao_de_materias.service.AlunoService;
import br.com.sistemas.sistema_inscricao_de_materias.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> add(@RequestBody AlunoDTO alunoDTO) {
        try {
            System.out.println(alunoDTO);

            //todo Adicionar verificação para caso matéria esteja cheia
            //* filter para materia.isTemEspaco() == true
            List<Materia> materiasInscritas = alunoDTO.materiasInscritas().stream()
                    .map(nomeMateria -> materiaService.findByNomeMateria(nomeMateria)
                            .orElseThrow(() -> new IllegalArgumentException("Matéria não encontrada: " + nomeMateria)))
                    .filter(Materia::getTemEspaco)
                    .collect(Collectors.toList());

            Aluno aluno = converterAluno(alunoDTO, materiasInscritas);

            alunoService.salvarAluno(aluno);
            return ResponseEntity.status(HttpStatus.CREATED).body("Novo aluno adicionado");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao adicionar aluno: " + e.getMessage());
        }
    }



    private Aluno converterAluno(AlunoDTO alunoDTO, List <Materia> materias) {
        return new Aluno(alunoDTO.nomeCompleto(), alunoDTO.notaGeral(), alunoDTO.anoIngressao(), materias);
    }

   @GetMapping("/find")
    public ResponseEntity<List<AlunoDTO>> findAlunos () {
        try {
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

            return ResponseEntity.ok(listaDTOs);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

   }

}
