/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.sti.email.service.csv;

import br.uff.sti.email.modelo.Aluno;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author edil
 */
public class CSVService {

    Map<Long, Aluno> map;
    private Logger LOGGER;

    public static final String[] HEADER = {
        "nome", "matricula", "telefone", "email", "uffmail", "status"
    };

    private String nomeDoArquivo;
    private Reader leitorArquivo;
    private CSVParser parserArquivo;
    private List<Aluno> registros;

    public CSVService() {
        this("./Arquivo.csv");
    }

    public CSVService(String nomeDoArquivo) {
        this(nomeDoArquivo, LoggerFactory.getLogger(CSVService.class));
    }

    public CSVService(String nomeDoArquivo, Logger log) {
        this.nomeDoArquivo = nomeDoArquivo;
        this.LOGGER = log;
    }

    public CSVService inicializarServico() throws FileNotFoundException, IOException {
        inicializarLeitorArquivo().carregarArquivo();
        return this;
    }

    private CSVService inicializarLeitorArquivo() throws FileNotFoundException {
        this.leitorArquivo = new FileReader(nomeDoArquivo);
        return this;
    }

    private CSVService carregarArquivo() throws IOException {
        this.parserArquivo = CSVFormat.EXCEL
                .withFirstRecordAsHeader()
                .withHeader(HEADER)
                .parse(leitorArquivo);
        return this;
    }

    protected List<Aluno> lerRegistros() throws IOException {
        this.registros = new ArrayList<>();        
        for(CSVRecord registro : parserArquivo.getRecords()){
            Aluno alunoNovo = new Aluno();
            alunoNovo.setMatricula(Long.parseLong(registro.get("matricula")));
            alunoNovo.setNome(registro.get("nome"));
            alunoNovo.setEmail(registro.get("email"));
            alunoNovo.setTelefone(registro.get("telefone"));
            alunoNovo.setStatus(registro.get("status"));
            alunoNovo.setUffMail(registro.get("uffmail"));
            registros.add(alunoNovo);
        }
        return this.registros;
    }
    
    public String salvarMudancaNoCSV(String email) throws IOException {
        Aluno aluno = new Aluno();
        FileWriter fw = new FileWriter(aluno.toString());
        try(CSVPrinter printer = new CSVPrinter(fw, CSVFormat.EXCEL) ) {
            printer.printRecords(registros);       
        } catch (Exception e) {
            
        }
        return null;
    }

//    public void realizarAlteracoesEmAluno(Aluno aluno, String emailEscolhido){
//        System.out.println("entrou aqui");
//        try {
//            Aluno alunoEncontrado = encontrarAlunoNoArquivoCSV(aluno);
//            alteraRegistroAlunoNoArquivoCSV(alunoEncontrado, emailEscolhido);
//            LOGGER.info(registros.get(registros.indexOf(alunoEncontrado)).toString());
//            this.gravarUffMailNoArquivoCSV();
//        } catch (IOException | IllegalStateException ex) {
//            LOGGER.error("Erro ao realizar alterações no aluno {} - {}",
//                    aluno.getMatricula(), ex.getMessage());
//        }       
//    } 
//    
//   public Aluno encontrarAlunoNoArquivoCSV(Aluno aluno) throws IOException{
//       for(Aluno alunoAtual : getRegistros()){
//           if(alunoAtual.getMatricula().equals(aluno.getMatricula())){
//               return alunoAtual;
//           }
//       }
//       throw new IllegalStateException("Não encontrou aluno no arquivo, mas deveria.");
//    }
//    
//    public void alteraRegistroAlunoNoArquivoCSV(Aluno aluno, String emailEscolhido) throws IOException{
//       aluno.setUffMail(emailEscolhido);
//    }
//    
//    public CSVService gravarUffMailNoArquivoCSV() throws IOException{        
//        return this;
//    }
    /**
     * Retorna os registros dos alunos que estão no csv.
     *
     * @return
     * @throws IOException
     */
    public List<Aluno> getRegistros() throws IOException {
        return this.registros != null
                ? this.registros
                : lerRegistros();
    }

    public void setRegistros(List<Aluno> registros) {
        this.registros = registros;
    }

    public String getNomeDoArquivo() {
        return nomeDoArquivo;
    }

    public Logger getLOGGER() {
        return LOGGER;
    }
}
