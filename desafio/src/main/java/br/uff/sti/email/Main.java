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

    private static final String[] HEADERS = {"nome", "matricula", "telefone", "email", "uffmail", "status"};

    public static void main(String[] args) throws Exception {

        Reader fileReader = new FileReader("C:\\Users\\D'Aguila\\Desktop\\azszdsz.csv");

        CSVParser csvParser = CSVFormat.EXCEL
                .withFirstRecordAsHeader()
                .withHeader(HEADERS)
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
            System.out.println("Tá de brinks!!!");
        } else {
            if ("Ativo".equalsIgnoreCase(aluno.getStatus()) && aluno.getUffMail().isEmpty()) {
                System.out.println(aluno.getNome().split(" ")[0] + ", por favor escolha uma das opções abaixo para o seu UFFMail.");
            } else {
                System.out.println("Aluno já ativo e com UFF Mail já criado!");
            }
        }
        
    }

}
