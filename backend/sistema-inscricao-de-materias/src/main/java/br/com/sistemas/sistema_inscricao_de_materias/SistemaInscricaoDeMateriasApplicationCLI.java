package br.com.sistemas.sistema_inscricao_de_materias;

import br.com.sistemas.sistema_inscricao_de_materias.principal.Principal;
import br.com.sistemas.sistema_inscricao_de_materias.repository.AlunosRepository;
import br.com.sistemas.sistema_inscricao_de_materias.repository.MateriaRepository;
import br.com.sistemas.sistema_inscricao_de_materias.service.AlunoService;
import br.com.sistemas.sistema_inscricao_de_materias.service.InscricaoService;
import br.com.sistemas.sistema_inscricao_de_materias.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
//@PropertySource("classpath:application-secrets.properties")
@PropertySource("classpath:application.properties")
public class SistemaInscricaoDeMateriasApplicationCLI implements CommandLineRunner {

	@Autowired
	private  AlunosRepository repositorioAluno;

	@Autowired
	private MateriaRepository repositorioMaterias;

	@Autowired
	private InscricaoService inscricaoService;

	@Autowired
	private AlunoService alunoService;

	@Autowired
	private MateriaService materiaService;

	public static void main(String[] args) {
		SpringApplication.run(SistemaInscricaoDeMateriasApplicationCLI.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Hello mom");
		Principal main = new Principal(alunoService, materiaService, inscricaoService);
		main.menu();
		System.exit(0);
	}
}
