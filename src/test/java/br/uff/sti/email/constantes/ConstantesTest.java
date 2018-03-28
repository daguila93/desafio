/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.sti.email.constantes;

import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author edil
 */
public class ConstantesTest {
    
    private Constantes constantes;
    
    @Before
    public void before(){
        constantes = new Constantes();
    }
    
    @After
    public void after(){
        constantes = null;
    }
        
    @Test
    public void testSomeMethod() {
        assertThat(Constantes.DOMAIN_EMAIL, is("@id.uff.br"));
    }    
}