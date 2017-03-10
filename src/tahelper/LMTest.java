/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tahelper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author khaidzir
 */
public class LMTest {
    
    public static void main(String[] args) {        
        BufferedReader br = null;
        String sCurrentLine;
        String filein = "/home/khaidzir/TA/ProyekTA/MachineTranslation/Korpus/Semua/combine.en";
        int min=1000,lmin=0;
        int nl = 0;
        // Baca file
        try {
            br = new BufferedReader(new FileReader(filein));
            while ((sCurrentLine = br.readLine()) != null) {
                nl++;
                String [] split = sCurrentLine.split("\\s+");
                if (split.length < min) {
                    min = split.length;
                    lmin = nl;
                }
            }
            System.out.println("Minimal kata = " + min + "di baris = " + lmin);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
}
