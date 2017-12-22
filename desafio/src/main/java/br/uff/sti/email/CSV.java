/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.sti.email;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author edil
 */
public class CSV {

    CSV obj = new CSV();
    
    public void run() {

        String arquivoCSV = "/home/edil/Área de Trabalho/CSV.CSV";
        BufferedReader br = null;
        String linha = "";
        String csvDivisor = ",";
        try {

            br = new BufferedReader(new FileReader(arquivoCSV));
            while ((linha = br.readLine()) != null) {

                String[] pais = linha.split(csvDivisor);

                System.out.println(pais);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
