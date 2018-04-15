/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.sti.email.service.csv;

import br.uff.sti.email.modelo.Aluno;
import br.uff.sti.email.service.SugestaoEmailService;
import java.io.IOException;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import org.junit.After;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import static org.mockito.BDDMockito.given;
import org.mockito.Matchers;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;

/**
 *
 * @author edil
 */
@RunWith(MockitoJUnitRunner.class)
public class CSVServiceTest {

    private CSVService service;
    private SugestaoEmailService sugestaoEmailService;
    
    @Before
    public void setUp() throws IOException {
        service = new CSVService("./src/test/Test.csv");
    }

    @After
    public void tearDown() {
        service = null;
    }

    @Test
    public void testLerRegistros() throws Exception {
        List<Aluno> registros = service.lerRegistros();
        assertThat(registros, is(notNullValue()));
        assertThat(registros, hasSize(2));
    }

    @Test
    public void testLerRegistroQuandoRegistrosSaoNulos() throws IOException {
        service.setRegistros(null);
        List<Aluno> registros = service.getRegistros();
        assertThat(registros, is(notNullValue()));
        assertThat(registros, hasSize(2));
    }

    @Test
    public void testConstrutorVazioValorDefault() throws IOException {
        CSVService servico = new CSVService();
        assertThat(servico.getNomeDoArquivo(), is(not("")));
        assertThat(servico.getNomeDoArquivo(), is("./Arquivo.csv"));
        assertThat(servico.getLOGGER().getName(), is("br.uff.sti.email.service.csv.CSVService"));
    }
    
    @Test
    public void apagarArquivoAntigo() throws IOException{
        service.apagarArquivoAntigo();
        service.salvarMudancaNoCSV((Aluno) service.getRegistros().get(0));
    }

    @Test
    public void testConstrutorSemParametro() throws IOException {
        sugestaoEmailService = new SugestaoEmailService();
        assertThat(sugestaoEmailService, is(notNullValue()));
    }

    @Test
    public void testCatchIOException() throws Exception {
        CSVService service2 = new CSVService("./src/test/Test.csv", mock(Logger.class));
        service2.lerRegistros();
        Aluno mockDeAluno = mock(Aluno.class);
        given(mockDeAluno.getMatricula()).willThrow(IOException.class);
        service2.salvarMudancaNoCSV(mockDeAluno);
        verify(service2.getLOGGER(), times(1)).error(anyString());
    }    
}
