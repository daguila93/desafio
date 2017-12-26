package br.uff.sti.email;

import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author edil
 */
public class Main {

    private static final String[] HEADER = {"nome", "matricula", "telefone", "email", "uffmail", "status"};
    private static final String DOMAIN_EMAIL = "@id.uff.br";

    public static void main(String[] args) throws Exception {

        Reader fileReader = new FileReader("./Arquivo.csv");

        CSVParser csvParser = CSVFormat.EXCEL
                .withFirstRecordAsHeader()
                .withHeader(HEADER)
                .parse(fileReader);

        List<CSVRecord> records = csvParser.getRecords();

        Map<Long, Aluno> map = new HashMap<Long, Aluno>();

        for (CSVRecord record : records) {
            Aluno aluno = new Aluno();
            aluno.setNome(record.get("nome"));
            aluno.setMatricula(Long.valueOf(record.get("matricula")));
            aluno.setTelefone(record.get("telefone"));
            aluno.setEmail(record.get("email"));
            aluno.setUffMail(record.get("uffmail"));
            aluno.setStatus(record.get("status"));
            map.put(aluno.getMatricula(), aluno);
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("Digite sua matrícula: ");
        long matricula = sc.nextLong();
        Aluno aluno = map.get(matricula);

        if (aluno == null) {
            System.out.println("Digite uma matrícula válida!");
        } else {
            if ("Ativo".equalsIgnoreCase(aluno.getStatus()) && aluno.getUffMail().isEmpty()) {
                System.out.println(aluno.getNome().split(" ")[0] + ", por favor escolha uma das opções abaixo para o seu UFFMail.");

                Map<Integer, String> mapa = criarMapaDeEmail(aluno.getNome());

                for (Map.Entry entry : mapa.entrySet()) {
                    System.out.println(entry.getKey() + " - " + entry.getValue());
                }

                String emailEscolhido = mapa.get(sc.nextInt());
                if (emailEscolhido != null) {
                    System.out.println("A criação de seu e-mail (" + emailEscolhido + ") será feita nos próximos minutos.\n"
                            + "Um SMS foi enviado para " + aluno.getTelefone() + " com a sua senha de acesso.");
                } else {
                    System.out.println("Digite uma opção válida.");
                }

            }

        }

    }

    private static Map<Integer, String> criarMapaDeEmail(String nome) {
        Map<Integer, String> mapa = new HashMap<Integer, String>();
        mapa.put(1, criarEmailSeparadoPorUnderscore(nome));
        mapa.put(2, criarEmailComPrimeiraLetraSobrenome(nome));
        mapa.put(3, primeiroNomeMaisSegundoNome(nome));
        mapa.put(4, primeiraLetraMaisSobrenome(nome));
        mapa.put(5, primeiraLetraMaisSobrenomes(nome));
        return mapa;
    }

    private static String criarEmailSeparadoPorUnderscore(String nome) {
        String arrayNome[] = nome.split(" ");
        String email = arrayNome[0] + "_" + arrayNome[1] + DOMAIN_EMAIL;
        return email.toLowerCase();
    }

    private static String criarEmailComPrimeiraLetraSobrenome(String nome) {
        String arrayNome[] = nome.split(" ");
        String email = arrayNome[0] + arrayNome[1].charAt(0) + arrayNome[2].charAt(0) + DOMAIN_EMAIL;
        return email.toLowerCase();
    }
    
    private static String primeiroNomeMaisSegundoNome(String nome){
        String arrayNome[] = nome.split(" ");
        String email = arrayNome[0] + arrayNome[1]+ DOMAIN_EMAIL;
        return email.toLowerCase();
    }
    
    private static String primeiraLetraMaisSobrenome(String nome){
        String arrayNome[] = nome.split(" ");
        String email = arrayNome[0].charAt(0)+ arrayNome[1] + DOMAIN_EMAIL;
        return email.toLowerCase();
    }
    
    private static String primeiraLetraMaisSobrenomes(String nome){
        String arrayNome[] = nome.split(" ");
        String email = arrayNome[0].charAt(0) + arrayNome[1] + arrayNome[2] + DOMAIN_EMAIL;
        return email.toLowerCase();
    }

}
