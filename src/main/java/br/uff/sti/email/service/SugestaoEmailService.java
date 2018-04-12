/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.sti.email.service;

import static br.uff.sti.email.constantes.Constantes.DOMAIN_EMAIL;
import br.uff.sti.email.modelo.Aluno;
import br.uff.sti.email.service.csv.CSVService;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author edil
 */
public class SugestaoEmailService {

    private final CSVService arquivoService;
    
    private Integer indiceMapa;
    
    public SugestaoEmailService() throws IOException{
        this(new CSVService());
    }
    
    public SugestaoEmailService(CSVService service) throws IOException{
        arquivoService = service.inicializarServico();
        indiceMapa = 0;
    } 
    
    //Mapeando a criação de E-mails e métodos de cada um dos possíveis e-mails.
    public Map<Integer, String> criarMapaDeEmail(String nome) throws IOException {
        String[] nomes = limparCaracteresEspeciaisEmNome(nome).split(" ");
        indiceMapa = 0;
        return mapearSugestoesDeEmail(nomes);
    }
    
    private Integer incrementarIndice(){
        return this.indiceMapa+=1;
    }

    private String limparCaracteresEspeciaisEmNome(String nome) {
        return nome.replaceAll("[^a-zA-Z_ ]", "");
    }

    private Map<Integer, String> mapearSugestoesDeEmail(String[] nomes) throws IOException {
        Map<Integer, String> mapa = new HashMap<>();
        switch (nomes.length) {
            case 3: sugestoesEmailAlunoComTresNomes(mapa, nomes);
            case 2: sugestoesEmailAlunoComDoisNomes(mapa, nomes);
            case 1: sugestoesEmailAlunoComUmNome(mapa, nomes); break;
            default: sugestoesEmailAlunoComMaisTresNomes(mapa, nomes);
        }
        return mapa;
    }
    
    private Boolean isValidoParaSugestao(Map <Integer, String> mapa, String sugestaoDeEmail) throws IOException{
        for(Aluno aluno : arquivoService.getRegistros()){
            if(aluno.getUffMail().equals(sugestaoDeEmail)){
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }
   
    // Métodos agregadores de sugestão de nome de email
    private void sugestoesEmailAlunoComMaisTresNomes(Map<Integer, String> mapa, String[] nomes) throws IOException {        
            sugestoesEmailAlunoComUmNome(mapa, nomes);
            sugestoesEmailAlunoComDoisNomes(mapa, nomes);
            sugestoesEmailAlunoComTresNomes(mapa, nomes);
        
    }

    private void sugestoesEmailAlunoComTresNomes(Map<Integer, String> mapa, String[] nomes) throws IOException {
        adicionaSugestaoValidaNoMapa(mapa, criarEmailComPrimeiraLetraSobrenome(nomes));        
        adicionaSugestaoValidaNoMapa(mapa, primeiraLetraMaisSobrenomes(nomes));        
        adicionaSugestaoValidaNoMapa(mapa, primeiraLetraDoNomeEPrimeiraLetraDoSobrenomeDobrada(nomes));
    }
    
    private void sugestoesEmailAlunoComDoisNomes(Map<Integer, String> mapa, String[] nomes) throws IOException{
        adicionaSugestaoValidaNoMapa(mapa, criarEmailSeparadoPorUnderscore(nomes));   
        adicionaSugestaoValidaNoMapa(mapa, primeiroNomeMaisSegundoNome(nomes));
        adicionaSugestaoValidaNoMapa(mapa, primeiraLetraMaisSobrenome(nomes));
    }
    
    private void sugestoesEmailAlunoComUmNome(Map<Integer, String> mapa, String[] nomes) throws IOException{
        adicionaSugestaoValidaNoMapa(mapa, primeiroNomeSomente(nomes));
    }
    
    private void adicionaSugestaoValidaNoMapa(Map<Integer, String> mapa, String sugestao) throws IOException{
        if(isValidoParaSugestao(mapa, sugestao)){
            mapa.put(incrementarIndice(), sugestao);
        }
    }
    
    // Métodos de sugestão de nome de email
    private String criarEmailSeparadoPorUnderscore(String[] arrayNome) {
        String email = arrayNome[0] + "_" + arrayNome[1] + DOMAIN_EMAIL;
        return email.toLowerCase();
    }

    private String criarEmailComPrimeiraLetraSobrenome(String[] arrayNome) {
        String email = arrayNome[0] + arrayNome[1].charAt(0) + arrayNome[2].charAt(0) + DOMAIN_EMAIL;
        return email.toLowerCase();
    }

    private String primeiroNomeMaisSegundoNome(String[] arrayNome) {
        String email = arrayNome[0] + arrayNome[1] + DOMAIN_EMAIL;
        return email.toLowerCase();
    }

    private String primeiraLetraMaisSobrenome(String[] arrayNome) {
        String email = arrayNome[0].charAt(0) + arrayNome[1] + DOMAIN_EMAIL;
        return email.toLowerCase();
    }

    private String primeiraLetraMaisSobrenomes(String[] arrayNome) {
        String email = arrayNome[0].charAt(0) + arrayNome[1] + arrayNome[2] + DOMAIN_EMAIL;
        return email.toLowerCase();
    }

    private String primeiraLetraDoNomeEPrimeiraLetraDoSobrenomeDobrada(String[] arrayNome) {
        return new StringBuilder()
                .append(arrayNome[0].charAt(0))
                .append(arrayNome[1].charAt(0))
                .append(arrayNome[1].charAt(0))
                .append(DOMAIN_EMAIL)
                .toString().toLowerCase();
    }
    
    private String primeiroNomeSomente(String[] arrayNome) {
        return (arrayNome[0] + DOMAIN_EMAIL).toLowerCase();
    }
}
