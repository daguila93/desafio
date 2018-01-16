/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.sti.email;

import br.uff.sti.email.modelo.Aluno;
import br.uff.sti.email.service.AlunoService;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Optional;
import java.util.Scanner;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import org.junit.After;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import org.mockito.Matchers;
import static org.mockito.Matchers.anyString;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;

/**
 *
 * @author edil
 */
@RunWith(MockitoJUnitRunner.class)
public class MainTest {
        
    @Mock
    private Logger logger;
    
    @Mock
    private AlunoService alunoService;
    
    @Rule
    public TextFromStandardInputStream systemMock;
    
    private Main principal;
    
    @Before
    public void setUp() {
        given(this.alunoService.getAluno(Matchers.anyLong()))
            .willReturn(Optional.ofNullable(new Aluno()));
        systemMock = TextFromStandardInputStream.emptyStandardInputStream();
        principal = new Main(alunoService, logger);
    }
    
    @After
    public void tearDown() {
        principal = null;
    }

    @Test
    public void testMain() {
        //Main.main(null);
    }

    @Test
    public void quandoAlunoAtivoESemUffMailEntaoAlunoValidoParaSugestao() {
        Aluno alunoAtivo = new Aluno();
        alunoAtivo.setStatus("Ativo");
        alunoAtivo.setUffMail("");
        Boolean resultado = Main.isAlunoValidoParaSugestaoEmail(alunoAtivo);
        assertThat(resultado, is(true));
    }
    
    @Test
    public void quandoAlunoInativoEntaoNaoValidoParaSugestao() {
        Aluno alunoInativo = new Aluno();
        alunoInativo.setStatus("Inativo");
        Boolean resultado = Main.isAlunoValidoParaSugestaoEmail(alunoInativo);
        assertThat(resultado, is(false));
    }
    
    @Test
    public void quandoAlunoAtivoComUffMailEntaoNaoValidoParaSugestao() {
        Aluno alunoAtivo = new Aluno();
        alunoAtivo.setStatus("Ativo");
        alunoAtivo.setUffMail("edil.rocha@id.uff.br");
        Boolean resultado = Main.isAlunoValidoParaSugestaoEmail(alunoAtivo);
        assertThat(resultado, is(false));
    }
    
    @Test
    public void testReturnLogger(){
        Logger log = principal.getLOGGER(); 
        assertThat(log.getName(), is(not("")));
        assertThat(log.getName(), is(nullValue())); // pq é um mock  
    }
    
    @Test
    @Ignore
    public void quandoAlunoEValidoRegistraLogParaSugestoesDeEmail(){   
        Aluno aluno = new Aluno();
        aluno.setNome("Guilherme Alves Gonçalves");
        aluno.setStatus("Ativo");
        aluno.setUffMail("");
        systemMock.provideLines("1");
        Main.mostrarSugestoesDeEmail(aluno, new Scanner(System.in));       
        verify(principal.getLOGGER(), times(7)).info(anyString());        
    }
}
