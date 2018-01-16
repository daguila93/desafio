/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.sti.email.service;

import static br.uff.sti.email.constantes.Constantes.DOMAIN_EMAIL;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author edil
 */
public class SugestaoEmailService{

    //Mapeando a criação de E-mails e métodos de cada um dos possíveis e-mails.
    public Map<Integer, String> criarMapaDeEmail(String nome) {
        String[] nomes = limparCaracteresEspeciaisEmNome(nome).split(" ");
        Map<Integer, String> mapa = new HashMap<>();
        mapa.put(1, criarEmailSeparadoPorUnderscore(nomes));
        mapa.put(2, criarEmailComPrimeiraLetraSobrenome(nomes));
        mapa.put(3, primeiroNomeMaisSegundoNome(nomes));
        mapa.put(4, primeiraLetraMaisSobrenome(nomes));
        mapa.put(5, primeiraLetraMaisSobrenomes(nomes));
        return mapa;    
//          return mapearSugestoesDeEmail(nome);
    }
    
    private String limparCaracteresEspeciaisEmNome(String nome){
        return nome.replaceAll("[^a-zA-Z_ ]", "");
    }
    
    private Map<Integer, String> mapearSugestoesDeEmail(String nome){
        String[] nomes = nome.split(" ");
        Map<Integer, String> mapa = new HashMap<>();
        int indice = 0;
        switch(nomes.length){
            case 2: {
                mapa.put(++indice, criarEmailSeparadoPorUnderscore(nomes));
                mapa.put(++indice, primeiroNomeMaisSegundoNome(nomes));
                mapa.put(++indice, primeiraLetraMaisSobrenome(nomes));
                break; 
            }
            default: {  
                mapa.put(++indice, criarEmailComPrimeiraLetraSobrenome(nomes));
                mapa.put(++indice, primeiraLetraMaisSobrenomes(nomes));
            }
        }
        return mapa;
    }

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
}
