/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.sti.email;

import br.uff.sti.email.service.AlunoService;
import br.uff.sti.email.service.csv.CSVService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;

/**
 *
 * @author edil
 */
@RunWith(MockitoJUnitRunner.class)
public class MainTest {
    
    public MainTest() {
    }
    
    @Mock
    private CSVService arquivoService;
    
    @Mock
    private Logger logger;

    private AlunoService alunoService;
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
               
    }

    @Test
    public void testMain() {
        
    }

    @Test
    public void testIsAlunoValidoParaSugestaoEmail() {
        
    }

    @Test
    public void testMostrarSugestoesDeEmail() {
    }
    
}
