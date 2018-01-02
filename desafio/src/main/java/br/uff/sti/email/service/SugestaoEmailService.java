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
public class SugestaoEmailService {
    //Mapeando a criação de E-mails e métodos de cada um dos possíveis e-mails.
    public static Map<Integer, String> criarMapaDeEmail(String nome) {
        Map<Integer, String> mapa = new HashMap<>();
        mapa.put(1, criarEmailSeparadoPorUnderscore(nome));
        mapa.put(2, criarEmailComPrimeiraLetraSobrenome(nome));
        mapa.put(3, primeiroNomeMaisSegundoNome(nome));
        mapa.put(4, primeiraLetraMaisSobrenome(nome));
        mapa.put(5, primeiraLetraMaisSobrenomes(nome));
        return mapa;
    }

    private static String criarEmailSeparadoPorUnderscore(String nome) {
        String arrayNome[] = nome.split(" ");
        String email = arrayNome[0] + "_" + arrayNome[1] + DOMAIN_EMAIL;
        return email.toLowerCase();
    }

    private static String criarEmailComPrimeiraLetraSobrenome(String nome) {
        String arrayNome[] = nome.split(" ");
        String email = arrayNome[0] + arrayNome[1].charAt(0) + arrayNome[2].charAt(0) + DOMAIN_EMAIL;
        return email.toLowerCase();
    }

    private static String primeiroNomeMaisSegundoNome(String nome) {
        String arrayNome[] = nome.split(" ");
        String email = arrayNome[0] + arrayNome[1] + DOMAIN_EMAIL;
        return email.toLowerCase();
    }

    private static String primeiraLetraMaisSobrenome(String nome) {
        String arrayNome[] = nome.split(" ");
        String email = arrayNome[0].charAt(0) + arrayNome[1] + DOMAIN_EMAIL;
        return email.toLowerCase();
    }

    private static String primeiraLetraMaisSobrenomes(String nome) {
        String arrayNome[] = nome.split(" ");
        String email = arrayNome[0].charAt(0) + arrayNome[1] + arrayNome[2] + DOMAIN_EMAIL;
        return email.toLowerCase();
    }
}
