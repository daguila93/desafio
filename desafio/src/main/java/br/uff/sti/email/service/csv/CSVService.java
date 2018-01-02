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
public final class CSVService {
    private final Logger LOGGER = LoggerFactory.getLogger(CSVService.class);
    
    public static final String[] HEADER = {
        "nome", "matricula", "telefone", "email", "uffmail", "status"
    };
    
    private final String nomeDoArquivo = "./Arquivo.csv";

    private Reader leitorArquivo;
    private CSVParser parserArquivo;
    private List<CSVRecord> registros;

    public CSVService() {
        try{
            inicializarLeitorArquivo().carregarArquivo();                    
        }catch(FileNotFoundException fnfex){
            LOGGER.error("Erro ao encontrar o arquivo. Não encontrado.", fnfex);
        } catch (IOException ioex) {
            LOGGER.error("Erro ao carregar o arquivo.", ioex);
        }
    }
    
    protected CSVService inicializarLeitorArquivo() throws FileNotFoundException{
        this.leitorArquivo = new FileReader(nomeDoArquivo);
        return this;
    }
    
    protected CSVService carregarArquivo() throws IOException{
       this.parserArquivo = CSVFormat.EXCEL
                                .withFirstRecordAsHeader()
                                .withHeader(HEADER)
                                .parse(leitorArquivo);
       return this;
    }
    
    private List<CSVRecord> lerRegistros() throws IOException{
        this.registros = parserArquivo.getRecords();
        return this.registros;
    }

    public List<CSVRecord> getRegistros() throws IOException {
        return this.registros != null
                ? this.registros
                : lerRegistros();
    }    
}