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
import org.junit.After;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.BDDMockito.given;
import org.mockito.Mockito;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import org.slf4j.Logger;

/**
 *
 * @author edil
 */
public class AlunoServiceTest{
    
    private AlunoService alunoService;
    private CSVService arquivoService;
    private Logger logger;

    public AlunoServiceTest() {
    }

    @Before
    public void setUp() throws IOException {
        logger = Mockito.mock(Logger.class);
        arquivoService = Mockito.mock(CSVService.class);
        given(this.arquivoService.getRegistros()).willReturn(getFakeRecords());
        alunoService = spy(new AlunoService(arquivoService));
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
    }

    public void testCatch() throws Exception {
        given(AlunoService.getLOGGER()).willReturn(logger);
        alunoService = spy(new AlunoService(arquivoService));
        Mockito.verify(logger, times(1));
    }

}
