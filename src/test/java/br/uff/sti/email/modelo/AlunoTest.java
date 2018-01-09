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
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
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
        assertThat(aluno.getUffMail(), isEmptyOrNullString());
    }

    @Test
    public void testToString() {
    }

}
