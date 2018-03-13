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

    public SugestaoEmailService() throws IOException{
        arquivoService = new CSVService().inicializarServico();
    }    
    
    //Mapeando a criação de E-mails e métodos de cada um dos possíveis e-mails.
    public Map<Integer, String> criarMapaDeEmail(String nome) throws IOException {
        String[] nomes = limparCaracteresEspeciaisEmNome(nome).split(" ");
        return mapearSugestoesDeEmail(nomes);
    }

    private String limparCaracteresEspeciaisEmNome(String nome) {
        return nome.replaceAll("[^a-zA-Z_ ]", "");
    }

    private Map<Integer, String> mapearSugestoesDeEmail(String[] nomes) throws IOException {
        Map<Integer, String> mapa = new HashMap<>();
        int indice = 0;
        switch (nomes.length) {
            case 3: indice = sugestoesEmailAlunoComTresNomes(mapa, indice, nomes);
            case 2: indice = sugestoesEmailAlunoComDoisNomes(mapa, indice, nomes);
            case 1: indice = sugestoesEmailAlunoComUmNome(mapa, indice, nomes); break;
            default: sugestoesEmailAlunoComMaisTresNomes(mapa, indice, nomes);
        }
        return mapa;
    }
    
    private int verificaSeASugestaoJaExiste(Map <Integer, String> mapa, int indice, String sugestaoDeEmail) throws IOException{
        
        for(Aluno aluno : arquivoService.getRegistros()){
            if(aluno.getUffMail().equals(sugestaoDeEmail)){
                return indice;
            }
        }
        mapa.put(++indice, sugestaoDeEmail);
        return indice;
    }
    
    // Métodos agregadores de sugestão de nome de email
    
     private void sugestoesEmailAlunoComMaisTresNomes(Map<Integer, String> mapa, int indice, String[] nomes) throws IOException {
        indice = sugestoesEmailAlunoComUmNome(mapa, indice, nomes);
        indice = sugestoesEmailAlunoComDoisNomes(mapa, indice, nomes);
        indice = sugestoesEmailAlunoComTresNomes(mapa, indice, nomes);
    }
    
    private int sugestoesEmailAlunoComTresNomes(Map<Integer, String> mapa, int indice, String[] nomes) throws IOException{
        verificaSeASugestaoJaExiste(mapa, indice, criarEmailComPrimeiraLetraSobrenome(nomes));
        mapa.put(++indice, criarEmailComPrimeiraLetraSobrenome(nomes));
        
        verificaSeASugestaoJaExiste(mapa, indice, primeiraLetraMaisSobrenomes(nomes));
        mapa.put(++indice, primeiraLetraMaisSobrenomes(nomes));
        
        verificaSeASugestaoJaExiste(mapa, indice, primeiraLetraDoNomeEPrimeiraLetraDoSobrenomeDobrada(nomes));
        mapa.put(++indice, primeiraLetraDoNomeEPrimeiraLetraDoSobrenomeDobrada(nomes));
        return indice;
    }
    
    private int sugestoesEmailAlunoComDoisNomes(Map<Integer, String> mapa, int indice, String[] nomes){
        mapa.put(++indice, criarEmailSeparadoPorUnderscore(nomes));
        mapa.put(++indice, primeiroNomeMaisSegundoNome(nomes));
        mapa.put(++indice, primeiraLetraMaisSobrenome(nomes));  
        return indice;
    }
    
    private int sugestoesEmailAlunoComUmNome(Map<Integer, String> mapa, int indice, String[] nomes){
        mapa.put(++indice, primeiroNomeSomente(nomes));
        return indice;
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
