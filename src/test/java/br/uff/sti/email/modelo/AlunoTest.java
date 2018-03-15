/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.sti.email.modelo;

import br.uff.sti.email.service.csv.CSVService;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author edil
 */
public class AlunoTest {

    private Aluno aluno;
 
    @Before
    public void setUp() throws IOException {
        CSVRecord record = getFakeRecords().get(0);
        aluno = new Aluno();
        aluno.setNome(record.get("nome"));
        aluno.setMatricula(Long.valueOf(record.get("matricula")));
        aluno.setTelefone(record.get("telefone"));
        aluno.setEmail(record.get("email"));
        aluno.setUffMail(record.get("uffmail"));
        aluno.setStatus(record.get("status"));
    }

    @After
    public void tearDown() {
        aluno = null;
    }

    private List<CSVRecord> getFakeRecords() throws FileNotFoundException, IOException {
        return CSVFormat.EXCEL
                .withFirstRecordAsHeader()
                .withHeader(CSVService.HEADER)
                .parse(new FileReader("./src/test/Test.csv"))
                .getRecords();
    }

    @Test
    public void testIsAtivo() {
        assertThat(aluno.isAtivo(), is(true));
    }

    @Test
    public void testHasUffmail() {
        assertThat(aluno.hasUffmail(), is(false));
    }

    @Test
    public void testToString() {
        assertThat(aluno.toString(), containsString("Edil D'Aguila Rocha,"));
        assertThat(aluno.toString(), containsString("1180000001,"));
        assertThat(aluno.toString(), containsString("99999-9999,"));
        assertThat(aluno.toString(), containsString(","));
        assertThat(aluno.toString(), containsString("email@gmail.com,"));
        assertThat(aluno.toString(), containsString("Ativo,"));
    }    

}