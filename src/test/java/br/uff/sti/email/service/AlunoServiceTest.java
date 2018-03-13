/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.sti.email.service;

import br.uff.sti.email.modelo.Aluno;
import br.uff.sti.email.service.csv.CSVService;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import org.hamcrest.Matchers;
import org.junit.After;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author edil
 */
@RunWith(MockitoJUnitRunner.class)
public class AlunoServiceTest {
    
    @Mock
    private CSVService arquivoService;
    
    @Mock
    private Logger logger;

    private AlunoService alunoService;
    
    @Before
    public void setUp() throws IOException {
        given(this.arquivoService.getRegistros()).willReturn(lerRegistros());
        alunoService = new AlunoService(arquivoService, logger);
    }

    @After
    public void tearDown() {
        alunoService = null;
        arquivoService = null;
    }

    private List<CSVRecord> getFakeRecords() throws FileNotFoundException, IOException {
        return CSVFormat.EXCEL
                .withFirstRecordAsHeader()
                .withHeader(CSVService.HEADER)
                .parse(new FileReader("./src/test/Test.csv"))
                .getRecords();
    }
    
    private List<Aluno> lerRegistros() throws IOException {
        List<Aluno> registros = new ArrayList<>();        
        for(CSVRecord registro : getFakeRecords()){
            Aluno alunoNovo = new Aluno();
            alunoNovo.setMatricula(Long.parseLong(registro.get("matricula")));
            alunoNovo.setNome(registro.get("nome"));
            alunoNovo.setEmail(registro.get("email"));
            alunoNovo.setTelefone(registro.get("telefone"));
            alunoNovo.setStatus(registro.get("status"));
            alunoNovo.setUffMail(registro.get("uffmail"));
            registros.add(alunoNovo);
        }
        return registros;
    }

    @Test
    public void testInicializarMapAlunosComSucesso() {
        Aluno aluno = alunoService.getMap().get(1180000001L);
        assertThat(aluno.getMatricula(), is(equalTo(1180000001L)));
        assertThat(aluno.getNome(), is(equalTo("Edil D'Aguila Rocha")));
        assertThat(aluno.getEmail(), is(equalTo("email@gmail.com")));
        assertThat(aluno.getUffMail(), is(""));
        assertThat(aluno.getStatus(), is(equalTo("Ativo")));
        assertThat(aluno.getTelefone(), is(equalTo("99999-9999")));
        assertThat(alunoService.getMap().size(), is(1));
    }

    @Test
    public void testCatchIOException() throws Exception {
        given(arquivoService.getRegistros()).willThrow(new IOException());        
        alunoService = new AlunoService(arquivoService, logger);
        verify(alunoService.getLOGGER(), times(1)).error(anyString());
    }
    
    @Test
    public void testCatchFileNotFoundException() throws Exception {
        given(arquivoService.inicializarServico()).willThrow(FileNotFoundException.class);       
        alunoService = new AlunoService(arquivoService, logger);
        verify(alunoService.getLOGGER(), times(1)).error(anyString());
    }

    @Test
    public void testReturnLogger() {
        alunoService.setLOGGER(LoggerFactory.getLogger(AlunoService.class));
        Logger log = alunoService.getLOGGER();        
        assertThat(log.getName(), is(not("")));
        assertThat(log.getName(), is("br.uff.sti.email.service.AlunoService"));
    }

    @Test
    public void testConstrutorVazioExisteInstancia() {
        AlunoService servico = new AlunoService();
        assertThat(servico.getCSVService(), is(Matchers.notNullValue()));
    }
    
    @Test
    public void testMatriculaNaoNula(){
        assertThat(alunoService.getAluno(1180000001L).isPresent(), is(true)); 
    }
    
    @Test
    public void testMatriculaNula(){
        assertThat(alunoService.getAluno(1L).isPresent(), is(false)); 
    }
    
}