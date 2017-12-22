/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.sti.email;

import java.util.Scanner;

/**
 *
 * @author edil
 */
public class Email {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner entrada = new Scanner (System.in);
        Long matricula;
        
        System.out.println("Digite sua Matrícula: ");
        matricula = entrada.nextLong();
        
        
        
        System.out.println("Sua Matrícula é: " + matricula);
        
    }
    
}
