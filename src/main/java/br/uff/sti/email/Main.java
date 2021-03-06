package br.uff.sti.email;

import br.uff.sti.email.modelo.Aluno;
import br.uff.sti.email.service.AlunoService;
import br.uff.sti.email.service.SugestaoEmailService;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author edil
 */
public class Main {

    private static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private static AlunoService alunoService;
    private static Scanner sc;

    public Main(AlunoService alunoServiceParam, Logger log) {
        LOGGER = log;
        alunoService = alunoServiceParam;
    }

    public static void main(String[] args) throws IOException {
        Main main = new Main(alunoService, sc);
        alunoService = new AlunoService();
        sc = new Scanner(System.in);
        LOGGER.info("Digite sua matrícula: ");

        try {
            long matricula = sc.nextLong();
            Optional<Aluno> optAluno = alunoService.getAluno(matricula);

            if (!optAluno.isPresent()) {
                LOGGER.info("Aluno não encontrado");
            } else {
                Aluno aluno = optAluno.get();

                if (isAlunoValidoParaSugestaoEmail(aluno)) {
                    mostrarSugestoesDeEmail(aluno, sc);
                }
            }
        } catch (InputMismatchException e) {
            LOGGER.warn("Digite uma matrícula válida.");
        } catch (IOException ioex){
            LOGGER.error("Problema ao carregar arquivo de alunos.", ioex);
        }
    }

    public static boolean isAlunoValidoParaSugestaoEmail(Aluno aluno) {
        if (!aluno.isAtivo()) {
            LOGGER.info("Aluno Inativo, entre em contato com o STI");
            return false;
        }

        if (aluno.isAtivo() && aluno.hasUffmail()) {
            LOGGER.info("UFFMAIL já cadastrado: " + aluno.getUffMail());
            return false;
        }
        return true;
    }

    public static void mostrarSugestoesDeEmail(Aluno aluno, Scanner sc) throws IOException {
        LOGGER.info("{} por favor escolha uma das opções abaixo para o seu UFFMail.", aluno.getNome().split(" ")[0]);

        Map<Integer, String> mapa = new SugestaoEmailService().criarMapaDeEmail(aluno.getNome());
        
        //Chamar método de checagem de criação de email aqui
        
        mapa.entrySet().forEach((entry) -> {
            LOGGER.info(entry.getKey() + " - " + entry.getValue());
        });
        String emailEscolhido = mapa.get(sc.nextInt());
        if (emailEscolhido != null) {
            LOGGER.info("A criação de seu e-mail (" + emailEscolhido + ") será feita nos próximos minutos.\n"
                    + "Um SMS foi enviado para " + aluno.getTelefone() + " com a sua senha de acesso. ");
            alunoService.atualizarUffMail(aluno, emailEscolhido);
            
        } else {
            LOGGER.info("Digite uma opção válida.");
        }
    }

    private Main(AlunoService alunoService, Scanner sc) {
        
    }

    Logger getLOGGER() {
       return LOGGER;
    }
}