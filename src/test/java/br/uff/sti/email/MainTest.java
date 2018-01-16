/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.sti.email;

import br.uff.sti.email.modelo.Aluno;
import br.uff.sti.email.service.AlunoService;
import java.io.FileNotFoundException;
import java.util.Optional;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import org.junit.After;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import org.mockito.Matchers;
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
    
    //Testar se o Logger foi chamado 7 vezes.
    @Test
    public void testReturnLogger(){
        Logger log = principal.getLOGGER();        
        assertThat(log.getName(), is(not("")));
        verify(principal.getLOGGER(), times(7)).info(anyString());        
    }    
}
