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
import static tahelper.TAHelper.sb;

/**
 *
 * @author khaidzir
 */
public class SubtitleProcess {
    
    public static void main(String[] args) {
        sb = new StringBuilder();
        String pathSub = "D:\\Kuliah\\TA\\Korpus\\Subtitle\\The Kings.Speech.2010.DVDSCR.XviD.AC3-NYDIC.srt";
        String pathOutput = "D:\\Kuliah\\TA\\hasil sub en.txt";
        bacaSub(pathSub);
        writeOutput(pathOutput);
    }
    
    public static void bacaSub(String pathfile) {
        BufferedReader br = null;
        String sCurrentLine;
        
        try {
            br = new BufferedReader(new FileReader(pathfile));            
            boolean nyambung = true;
            
            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.length() > 0) {
                    if (sCurrentLine.charAt(0) < '0' || sCurrentLine.charAt(0) > '9') {
                        if (sCurrentLine.charAt(0) == '-') nyambung = false;
                        if (nyambung) sb.append(sCurrentLine).append(" ");
                        else sb.append(sCurrentLine).append(System.lineSeparator());
                    } else nyambung = true;
                } else sb.append(System.lineSeparator());
            }
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
    
}
