/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.sti.email.service.csv;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author edil
 */
public class CSVService {

    private Logger LOGGER;

    public static final String[] HEADER = {
        "nome", "matricula", "telefone", "email", "uffmail", "status"
    };

    private String nomeDoArquivo;

    private Reader leitorArquivo;
    private CSVParser parserArquivo;
    private List<CSVRecord> registros;

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
    
    public CSVService inicializarServico() throws FileNotFoundException, IOException{
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

    protected List<CSVRecord> lerRegistros() throws IOException {
        this.registros = parserArquivo.getRecords();
        return this.registros;
    }

    /**
     * Retorna os registros dos alunos que estão no csv.
     *
     * @return
     * @throws IOException
     */
    public List<CSVRecord> getRegistros() throws IOException {
        return this.registros != null
                ? this.registros
                : lerRegistros();
    }

    public void setRegistros(List<CSVRecord> registros) {
        this.registros = registros;
    }

    public String getNomeDoArquivo() {
        return nomeDoArquivo;
    }

    public Logger getLOGGER() {
        return LOGGER;
    }
}
