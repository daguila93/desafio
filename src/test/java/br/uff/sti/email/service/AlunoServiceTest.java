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
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
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
    
    public AlunoServiceTest() {
    }

    @Before
    public void setUp() throws IOException {
        given(this.arquivoService.getRegistros()).willReturn(getFakeRecords());
        alunoService = new AlunoService(arquivoService, logger);
    }

    @After
    public void tearDown() {
        arquivoService = null;
    }

    private List<CSVRecord> getFakeRecords() throws FileNotFoundException, IOException {
        return CSVFormat.EXCEL
                .withFirstRecordAsHeader()
                .withHeader(CSVService.HEADER)
                .parse(new FileReader("./src/test/Test.csv"))
                .getRecords();
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
    public void testGetAluno() throws IOException {
        Aluno aluno = new Aluno();
        aluno.getNome();
        aluno.getEmail();
        assert false;
    }

    @Test
    public void testCatchException() throws Exception {
        given(arquivoService.getRegistros()).willThrow(new IOException());        
        alunoService = new AlunoService(arquivoService, logger);
        verify(alunoService.getLOGGER(), times(1)).error(anyString());
    }

    @Test
    public void testReturnLogger() {
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
