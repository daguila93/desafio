/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.sti.email.service;

import br.uff.sti.email.modelo.Aluno;
import br.uff.sti.email.service.csv.CSVService;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author edil
 */
public class AlunoService {
    private Logger LOGGER;
    
    //Mapeando os objetos (ALUNO) com o HASHMAP
    Map<Long, Aluno> map;
    
    CSVService CSVService;

    public AlunoService() {
        this(new CSVService());
    }

    public AlunoService(CSVService CSVService) {
        this(CSVService, LoggerFactory.getLogger(AlunoService.class));
    } 
    
    public AlunoService(CSVService CSVService, Logger logger) {
        this.CSVService = CSVService;
        this.LOGGER = logger;
        map = new HashMap<>();        
        inicializarMapAlunos();
    }
    
    private void inicializarMapAlunos(){
        try {
            CSVService.inicializarServico();
            for (Aluno aluno : CSVService.getRegistros()) {                
                map.put(aluno.getMatricula(), aluno);       
            }
        }catch (FileNotFoundException fnfex) {
            LOGGER.error("Erro ao encontrar o arquivo de alunos.");
        }catch (IOException ex) {
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

    public void setLOGGER(Logger LOGGER) {
        this.LOGGER = LOGGER;
    }    


    public CSVService getCSVService() {
        return CSVService;
    }  
}
