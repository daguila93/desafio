/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.sti.email.service.csv;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author edil
 */
public class CSVServiceTest {
    
    private CSVService cSVService;
    
    public CSVServiceTest() {
    }
    
    @Before
    public void setUp() {
        
        cSVService = new CSVService();
        
    }
    
    @After
    public void tearDown() {
        cSVService = null;
        
    }

    @Test
    public void testGetRegistros() throws Exception {
        
        
        
    }
    
}
