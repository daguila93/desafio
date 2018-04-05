/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.sti.email.service.csv;

import br.uff.sti.email.modelo.Aluno;
import java.io.File;
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
    private final String CAMINHODOARQUIVOANTIGO = "." + File.separator +"ArquivoQueVaiSerApagado.csv";

    public CSVService() {
        this("." + File.separator + "Arquivo.csv");
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
        this.parserArquivo = getCSVFormat().parse(leitorArquivo);
        return this;
    }

    public List<Aluno> lerRegistros() throws IOException {
        this.registros = new ArrayList<>();
        for (CSVRecord registro : parserArquivo.getRecords()) {
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

    public void salvarMudancaNoCSV(Aluno aluno) {
        try {
            FileWriter fileWriter = new FileWriter("." + File.separator + "novo.csv");
            CSVPrinter printer = new CSVPrinter(fileWriter, getCSVFormat().withQuote(null));

            printarCabecalho(printer);

            for (Aluno registro : registros) {
                if (registro.getMatricula().equals(aluno.getMatricula())) {
                    registro = aluno;
                }
                printer.printRecord(registro);
            }
            alteraNomeDoArquivo();
            apagarArquivoAntigo();
            printer.flush();
            printer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void alteraNomeDoArquivo() {
        new File(nomeDoArquivo).renameTo(new File("ArquivoQueVaiSerApagado.csv"));
        new File("novo.csv").renameTo(new File(nomeDoArquivo));
    }

    public void apagarArquivoAntigo(){
        File f = new File(CAMINHODOARQUIVOANTIGO);
         f.delete();
            
    }
    
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

    private CSVFormat getCSVFormat() {
        return CSVFormat.EXCEL.withFirstRecordAsHeader().withHeader(HEADER);
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

    private void printarCabecalho(CSVPrinter printer) throws IOException {
        //Printar o Cabeçalho
        for (String HEADER1 : HEADER) {
            printer.print(HEADER1);
        }        
        printer.println();
    }
}
