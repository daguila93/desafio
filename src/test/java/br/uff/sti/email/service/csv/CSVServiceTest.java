/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.sti.email.service.csv;

import br.uff.sti.email.modelo.Aluno;
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

/**
 *
 * @author edil
 */
public class CSVServiceTest {

    private CSVService service;
    
    @Before
    public void setUp() throws IOException {
        service = new CSVService("./src/test/Test.csv").inicializarServico();
    }

    @After
    public void tearDown() {
        service = null;
    }

    @Test
    public void testLerRegistros() throws Exception {
        List<Aluno> registros = service.lerRegistros();
        assertThat(registros, is(notNullValue()));
        assertThat(registros, hasSize(1));
    }

    @Test
    public void testLerRegistroQuandoRegistrosSaoNulos() throws IOException {
        service.setRegistros(null);
        List<Aluno> registros = service.getRegistros();
        assertThat(registros, is(notNullValue()));
        assertThat(registros, hasSize(1));
    }

    @Test
    public void testConstrutorVazioValorDefault() throws IOException {
        CSVService servico = new CSVService().inicializarServico();
        assertThat(servico.getNomeDoArquivo(), is(not("")));
        assertThat(servico.getNomeDoArquivo(), is("./Arquivo.csv"));
        assertThat(servico.getLOGGER().getName(), is("br.uff.sti.email.service.csv.CSVService"));
    }
    
    @Test
    public void apagarArquivoAntigo() throws IOException{        
        service.salvarMudancaNoCSV((Aluno) service.getRegistros().get(0));
    }

    @Test
    public void testInicializarServico() throws Exception {
    }

    @Test
    public void testGetRegistros() throws Exception {
    }

    @Test
    public void testSetRegistros() {
    }

    @Test
    public void testGetNomeDoArquivo() {
    }

    @Test
    public void testGetLOGGER() {
    }
    
}