/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.sti.email.service.csv;

import java.io.IOException;
import java.util.List;
import org.apache.commons.csv.CSVRecord;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.hasSize;
import org.junit.After;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author edil
 */
public class CSVServiceTest {

    private CSVService service;

    public CSVServiceTest() {
    }

    @Before
    public void setUp() {
        service = new CSVService("./src/test/Test.csv");
    }

    @After
    public void tearDown() {
        service = null;
    }

    @Test
    public void testLerRegistros() throws Exception {
        List<CSVRecord> registros = service.lerRegistros();
        assertThat(registros, is(notNullValue()));
        assertThat(registros, hasSize(1));        
    }
    
    @Test
    public void testLerRegistroQuandoRegistrosSaoNulos() throws IOException{
        service.setRegistros(null);
        List<CSVRecord> registros = service.getRegistros();
        assertThat(registros, is(notNullValue()));
        assertThat(registros, hasSize(1));
    }
    
    @Test
    public void testConstrutorVazioValorDefault(){
        CSVService servico = new CSVService();
        assertThat(servico.getNomeDoArquivo(), is(not("")));
        assertThat(servico.getNomeDoArquivo(), is("./Arquivo.csv"));

    }

}
