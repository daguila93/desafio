/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.sti.email.service;

import br.uff.sti.email.modelo.Aluno;
import br.uff.sti.email.service.csv.CSVService;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author edil
 */
public class AlunoService {
    private Logger LOGGER = LoggerFactory.getLogger(AlunoService.class);
    
    //Mapeando os objetos (ALUNO) com o HASHMAP
    Map<Long, Aluno> map;
    
    CSVService CSVService;

    public AlunoService() {
        this(new CSVService());        
    }

    public AlunoService(CSVService CSVService) {
        this(CSVService, null);
    } 
    
    public AlunoService(CSVService CSVService, Logger logger) {
        this.CSVService = CSVService;
        overrideLogger(logger);
        map = new HashMap<>();        
        inicializarMapAlunos();
    }
    
    private void inicializarMapAlunos(){
        try {
            for (CSVRecord record : CSVService.getRegistros()) {
                Aluno aluno = new Aluno();
                aluno.setNome(record.get("nome"));
                aluno.setMatricula(Long.valueOf(record.get("matricula")));
                aluno.setTelefone(record.get("telefone"));
                aluno.setEmail(record.get("email"));
                aluno.setUffMail(record.get("uffmail"));
                aluno.setStatus(record.get("status"));
                map.put(aluno.getMatricula(), aluno);       
            }
        } catch (IOException ex) {
            LOGGER.error("Erro ao inicializar mapa de alunos.");
        }
    }

    public Optional<Aluno> getAluno(Long matricula) {
        return Optional.ofNullable(map.get(matricula));
    }

    public Map<Long, Aluno> getMap() {
        return map;
    }

    public Logger getLOGGER() {
        return LOGGER;
    } 
    
    private void overrideLogger(Logger logger){
        if(logger != null){
            this.LOGGER = logger;
        }
    }

    public CSVService getCSVService() {
        return CSVService;
    }  
}
