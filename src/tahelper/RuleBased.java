/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tahelper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author khaidzir
 */
public class RuleBased {
    
    public static final String G_LABEL = "G";
    public static final String RC_LABEL = "RC";
    public static final String NO_LABEL = "-";
    public static int MAX_DIST = 3;
    public static double EPSILON = 0.000001;
    
    public static final String WORDS_DATABASE_PATH = "/home/khaidzir/TA/ProyekTA/Others/Korpus/daftarkata_kbbi";    
    public static final String REPEATABLE_WORD_LIST = "/home/khaidzir/TA/ProyekTA/Others/Korpus/kata_ulang";
    public static final String NOUN_LIST = "/home/khaidzir/TA/ProyekTA/Others/Korpus/kata_benda";
    
    public static HashMap<String, Boolean> daftarKata = null, kataBenda = null, kataUlang = null;
    
    public static void main(String args[]) {
        daftarKata = new HashMap<>();
        kataUlang = new HashMap<>();
        kataBenda = new HashMap<>();
        loadWordDatabase(daftarKata, WORDS_DATABASE_PATH);
        loadWordDatabase(kataUlang, REPEATABLE_WORD_LIST);
        loadWordDatabase(kataBenda, NOUN_LIST);
        
//        System.out.println("Kata benda : ");
//        for(String s : kataBenda.keySet()) {
//            System.out.println(s);
//        }
//        System.out.println("\nKata ulang : ");
//        for(String s : kataUlang.keySet()) {
//            System.out.println(s);
//        }
        
        
        String filein = "/home/khaidzir/TA/ProyekTA/Eksperimen/Dataset/daftar_kalimat.txt";
        String fileout = "/home/khaidzir/TA/ProyekTA/Eksperimen/Dataset/hasil_rule_based";
        annotateText(filein, fileout);        
    }
    
    /* Eksperimen */
    
    public static void annotateText(String infile, String outfile) {
        BufferedReader br = null;
        FileOutputStream fos;
        ArrayList<String[]> kalimat = new ArrayList<>();
        ArrayList<String[]>  label = new ArrayList<>();
        try {
            // Baca file infile
            String sCurrentLine;
            br = new BufferedReader(new FileReader(infile));
            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.length() > 0) {
                    String [] split = sCurrentLine.split(" +");
                    kalimat.add(split);
                    label.add(annotate(split));
                }
            }
            
            // Tulis ke outfile
            File fout = new File(outfile);
            fos = new FileOutputStream(fout);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));  
            
            for(int i=0; i<kalimat.size(); i++) {
                for(int j=0; j<kalimat.get(i).length; j++) {
                    bw.write(kalimat.get(i)[j]);
                    bw.write(" ");
                    bw.write(label.get(i)[j]);
                    bw.write("\n");
                }
                bw.write("\n");
            }
            
            bw.close();
            fos.close();            
        } catch (IOException e) {
        } finally {
            try {
                if (br != null)br.close();
                
            } catch (IOException ex) {
            }
        }
    }
    
    /////////////////
    
    public static String[] annotate(String[] input) {
        String[] ret = new String[input.length];
        boolean b;
        for(int i=0; i<input.length-1; i++) {
            b = false;
            // Cek G
            for(int j=i+1; j<=i+MAX_DIST && j<input.length && !b; j++) {
                b = eqFloat(similarityBetween2Words(input[i], input[j]), (float)1.0) && 
                        (calculateVocal(input[i]) >= 1) && !daftarKata.containsKey(input[i]) 
                        && !input[i].equals(input[j]);
                    
            }
            if (b) {
                ret[i] = G_LABEL;
                continue;
            }
            
            // Cek RC
            b = false;
            for(int j=i+1; j<=i+MAX_DIST && j<input.length && !b; j++) {
               b = input[i].equals(input[j]) && !kataUlang.containsKey(input[i])
                       && !kataBenda.containsKey(input[i]);
            }
            ret[i] = b ? RC_LABEL : NO_LABEL;
        }
        
        ret[ret.length-1] = NO_LABEL;
        
        return ret;
    }
    
    public static float similarityBetween2Words(String word1, String word2) {
        
        int c = 0;
        int l1 = word1.length();
        int l2 = word2.length();
        
        if (l1 > l2) return (float)0.0;
        
        boolean b = true;
        while(b && c < l1 && c < l2) {
            if (word1.charAt(c) == word2.charAt(c)) c++;
            else b = false;
        }
        
        return ((float)c)/((float)word1.length());
    }
    
    public static boolean eqFloat(float f1, float f2) {
        return Math.abs(f1-f2) <= EPSILON;            
    }
    
    public static void loadKBBI() {
        BufferedReader br = null;
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(WORDS_DATABASE_PATH));
            daftarKata = new HashMap<>();
            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.length() > 0) {
                    daftarKata.put(sCurrentLine, Boolean.TRUE);
                }
            }
        } catch (IOException e) {
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
            }
        }
    }
    
    public static void loadWordDatabase(HashMap<String, Boolean> map, String filepath) {
        BufferedReader br = null;
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(filepath));
            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.length() > 0) {
                    map.put(sCurrentLine, Boolean.TRUE);
                }
            }
        } catch (IOException e) {
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
            }
        }
    }
    
    public static int calculateVocal(String word) {
        int ret = 0;
        
        boolean sign = false;
        for(int i=0; i<word.length(); i++) {
            if ( (word.charAt(i) == 'a' || word.charAt(i) == 'i' || word.charAt(i) == 'u'
                    || word.charAt(i) == 'e' || word.charAt(i) == 'o') && !sign ) {
                ret++;
                sign = true;
            } else sign = false;
        }
        
        return ret;
    }
    
}
