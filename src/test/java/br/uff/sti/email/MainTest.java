/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.sti.email;

import br.uff.sti.email.modelo.Aluno;
import br.uff.sti.email.service.AlunoService;
import br.uff.sti.email.service.csv.CSVService;
import java.util.Optional;
import java.util.OptionalLong;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import static org.mockito.BDDMockito.given;
import org.mockito.Matchers;
import org.mockito.Mock;
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
    
    private Main principal;
    
    @Before
    public void setUp() {
        given(this.alunoService.getAluno(Matchers.anyLong()))
            .willReturn(Optional.ofNullable(new Aluno()));
        principal = new Main(alunoService, logger);
    }
    
    @After
    public void tearDown() {
        principal = null;
    }

    @Test
    public void testMain() {
        Main.main(null);
    }

    @Test
    public void testIsAlunoValidoParaSugestaoEmail() {
        alunoService.getAluno(Long.MAX_VALUE);
    }
    
    @Test
    public void testIsAlunoAtivo(){
       
    }
    
    @Test
    public void testAlunoAtivoEComUffMail(){
        
    }
    @Test

    @Test
    public void testMostrarSugestoesDeEmail() {
    }
    
}
