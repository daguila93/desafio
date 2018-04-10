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
import org.junit.Test;

/**
 *
 * @author edil
 */
public class SugestaoEmailServiceTest {

    private SugestaoEmailService service;

    @Before
    public void setUp() throws IOException {
        service = new SugestaoEmailService();
    }

    @After
    public void tearDown() {
        service = null;
    }

    @Test
    public void quando_aluno_com_um_nome_entao_retorna_uma_sugestao() throws IOException {
        Map<Integer, String> sugestoes
                = service.criarMapaDeEmail("Edila");
        assertThat(sugestoes.get(1), is(equalTo("edila@id.uff.br")));
    }

    @Test
    public void quando_aluno_com_dois_nomes_entao_retorna_quatro_sugestoes() throws IOException {
        Map<Integer, String> sugestoes
                = service.criarMapaDeEmail("Aline Moraes");
        assertThat(sugestoes.get(1), is(equalTo("aline_moraes@id.uff.br")));
        assertThat(sugestoes.get(2), is(equalTo("alinemoraes@id.uff.br")));
        assertThat(sugestoes.get(3), is(equalTo("amoraes@id.uff.br")));
        assertThat(sugestoes.get(4), is(equalTo("aline@id.uff.br")));
    }

    @Test
    public void quando_aluno_com_tres_nomes_entao_retorna_sete_sugestoes() throws IOException {
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

    @Test
    public void quando_aluno_com_quatro_nomes_ou_mais_entao_retorna_sete_sugestoes() throws IOException {
        Map<Integer, String> sugestoes
                = service.criarMapaDeEmail("Guilherme da Silva Alves Gonçalves");
        assertThat(sugestoes.get(1), is(equalTo("guilherme@id.uff.br")));
        assertThat(sugestoes.get(2), is(equalTo("guilherme_da@id.uff.br")));
        assertThat(sugestoes.get(3), is(equalTo("guilhermeda@id.uff.br")));
        assertThat(sugestoes.get(4), is(equalTo("gda@id.uff.br")));
        assertThat(sugestoes.get(5), is(equalTo("guilhermeds@id.uff.br")));
        assertThat(sugestoes.get(6), is(equalTo("gdasilva@id.uff.br")));
        assertThat(sugestoes.get(7), is(equalTo("gdd@id.uff.br")));
    }

    @Test
    public void testverificaSeASugestaoJaExiste() throws IOException {
        service.criarMapaDeEmail("");
    }

}
