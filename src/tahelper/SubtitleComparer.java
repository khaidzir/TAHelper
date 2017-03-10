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
public class SubtitleComparer {
    
    public static void main (String args[]) {
        String path1 = "D:\\Kuliah\\TA\\Korpus\\ITB\\Processed\\itb.proc.en";
        String path2 = "D:\\Kuliah\\TA\\Korpus\\ITB\\Processed\\itb.proc.id";
        String pathout = "D:\\Kuliah\\TA\\Korpus\\ITB\\Processed\\hasil";
        int limit = 5;
        compare(path1, path2, limit, pathout);
    }
    
    public static void compare(String path1, String path2, int limit, String pathout) {
        BufferedReader br1 = null, br2 = null;
        String currLine1, currLine2;
        String[] temp1, temp2;
        StringBuilder sb = new StringBuilder();
        int counter = 1;
        
        try {
            br1 = new BufferedReader(new FileReader(path1));
            br2 = new BufferedReader(new FileReader(path2));            
            
            while ( (currLine1=br1.readLine()) != null && 
                    (currLine2=br2.readLine()) != null ) {
                temp1 = currLine1.split(" +");
                temp2 = currLine2.split(" +");
                if (Math.abs(temp1.length-temp2.length) > limit)  {
                    sb.append(counter).append(".\t").append(currLine1).append(System.lineSeparator()).
                            append("\t").append(currLine2).append(System.lineSeparator());
                }
                counter++;
            }
            
            File file = new File(pathout);

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
        } finally {
            try {
                if (br1 != null)br1.close();
                if (br2 != null)br2.close();
            } catch (IOException ex) {
                    ex.printStackTrace();
            }
        }
    }
    
}
