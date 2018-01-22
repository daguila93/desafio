/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.sti.email.service;

import java.io.IOException;
import java.util.Map;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author edil
 */
public class SugestaoEmailServiceTest {
    
    private SugestaoEmailService service;
    
    @Before
    public void setUp() {
        service = new SugestaoEmailService();
    }
    
    @After
    public void tearDown() {
        service = null;
    }
    
    @Ignore
    @Test
    public void testCriarMapaDeEmail() throws IOException {
        Map<Integer, String> sugestoes
                = service.criarMapaDeEmail("Edil D'Aguila Rocha");
        assertThat(sugestoes.get(1), is(equalTo("edildr@id.uff.br")));
        assertThat(sugestoes.get(2), is(equalTo("edaguilarocha@id.uff.br")));
        assertThat(sugestoes.get(3), is(equalTo("edd@id.uff.br")));
        assertThat(sugestoes.get(4), is(equalTo("edil_daguila@id.uff.br")));
        assertThat(sugestoes.get(5), is(equalTo("edildaguila@id.uff.br")));
        assertThat(sugestoes.get(6), is(equalTo("edaguila@id.uff.br")));
        assertThat(sugestoes.get(7), is(equalTo("edil@id.uff.br")));
    }
    
}
