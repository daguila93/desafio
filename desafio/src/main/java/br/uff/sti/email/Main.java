package br.uff.sti.email;

import br.uff.sti.email.modelo.Aluno;
import br.uff.sti.email.service.AlunoService;
import br.uff.sti.email.service.SugestaoEmailService;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    
    public static void main(String[] args) {

        AlunoService alunoService = new AlunoService();

     

//Pedindo a matrícula do Aluno
        Scanner sc = new Scanner(System.in);
        LOGGER.info("Digite sua matrícula: ");

        //Teste de tipo da matrícula
        try {
            long matricula = sc.nextLong();
            Optional<Aluno> optAluno = alunoService.getAluno(matricula);

            //Testes Condicionais da Matrícula para a criação de UFFmail se necessário.
            if (!optAluno.isPresent()) {
                LOGGER.info("Aluno não encontrado");
            } else {
                Aluno aluno = optAluno.get();
                if (aluno.isAtivo() && !aluno.hasUffmail()) {
//                    System.out.println(aluno.getNome().split(" ")[0] + ", por favor escolha uma das opções abaixo para o seu UFFMail. ");
                    LOGGER.info("{} por favor escolha uma das opções abaixo para o seu UFFMail.", aluno.getNome().split(" ")[0] );

                    Map<Integer, String> mapa = SugestaoEmailService.criarMapaDeEmail(aluno.getNome());

                    for (Map.Entry entry : mapa.entrySet()) {
                        LOGGER.info(entry.getKey() + " - " + entry.getValue());//                        
                    }
                    String emailEscolhido = mapa.get(sc.nextInt());
                    if (emailEscolhido != null) {
                        LOGGER.info("A criação de seu e-mail (" + emailEscolhido + ") será feita nos próximos minutos.\n"
                                + "Um SMS foi enviado para " + aluno.getTelefone() + " com a sua senha de acesso. ");
                    } else {
                        LOGGER.info("Digite uma opção válida.");
                    }

                } else {
                    if (aluno.isAtivo() && aluno.hasUffmail()) {
                        LOGGER.info("UFFMAIL já cadastrado: " + aluno.getUffMail());
                    }
                }
                if (!aluno.isAtivo()) {
                    LOGGER.info("Aluno Inativo, entre em contato com o STI");
                }
            }

        } catch (InputMismatchException e) {
            LOGGER.info("Digite uma matrícula válida.");
        }

    }

}
