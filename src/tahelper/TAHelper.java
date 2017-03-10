/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tahelper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author khaidzir
 */
public class TAHelper {


    
    public static StringBuilder sb;
    
    public static final String ROOT_FOLDER = "D:\\Kuliah\\TA\\Korpus\\Filled Pause Text Corpus\\Processed\\";
    
    public static void main(String[] args) {
        // TODO code application logic here
        prosesTRSFile();
    }
    
    public static void prosesTRSFile() {
        String folder = "D:/Kuliah/TA/Filled Pause Text Corpus/";
        sb = new StringBuilder();
        proses(new File(folder));        
        writeOutput("D:\\Kuliah\\TA\\filledpause.txt");        
    }
    
    public static void proses(File root) {
        if(root.isDirectory()) {
            File[] files = root.listFiles();
            for(File file : files) {
                proses(file);
            }
        } else {
            System.out.println(root);
            String fname = root.getPath();
            if(fname.substring(fname.length()-3).equals("trs")) bacaTRS(fname);
        }
    }
    
    public static void writeOutput(String pathfile) {
        try {
            File file = new File(pathfile);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(sb.toString());
            bw.close();            
        } catch (IOException e) {
                e.printStackTrace();
        }
    }
    
    public static void writeOutput(String data, String pathfile) {
        try {
            File file = new File(pathfile);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            bw.close();            
        } catch (IOException e) {
                e.printStackTrace();
        }
    }
    
    public static void bacaTRS(String pathfile) {
        BufferedReader br = null;
        String sCurrentLine;
        
        try {
            br = new BufferedReader(new FileReader(pathfile));
            String [] temp;

            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.charAt(0) != '<' && sCurrentLine.charAt(0) != 'S') {
                    temp = sCurrentLine.split(" / ");
                    if(temp.length> 1) sb.append(temp[1]).append(" ");
                }
            }
            sb.append("\n");

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
