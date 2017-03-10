/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tahelper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import static tahelper.Annotator.annotateLabeledSentence;

/**
 *
 * @author khaidzir
 */
public class OOV {
    
    public static void main (String args[]) {
        String korpusMTPath = "E:/Kuliah/TA/Korpus/Fix/korpus_gabungan.id";
        String datasetPath = "E:/Kuliah/TA/Korpus/Dataset/daftar_kalimat.txt";
        HashMap<String, Boolean> mapKorpus = loadWords(korpusMTPath);
        HashMap<String, Boolean> mapDataset = loadWords(datasetPath);
        HashMap<String, Boolean> result = showOOVList(mapKorpus, mapDataset);
        result.keySet().stream().forEach((s) -> {
            System.out.println(s);
        });
    }
    
    public static HashMap<String, Boolean> showOOVList(HashMap<String, Boolean> dict, HashMap<String, Boolean> input) {
        HashMap<String, Boolean> ret = new HashMap<>();
        
        input.keySet().stream().filter((kata) -> (!dict.containsKey(kata) && !ret.containsKey(kata))).forEach((kata) -> {
            ret.put(kata, true);
        });
        
        return ret;
    }
    
    public static HashMap<String, Boolean> loadWords(String path) {
        HashMap<String, Boolean> ret = new HashMap<>();
        
        // Baca file line per line
        BufferedReader br;
        String sentence;
        
        try {
            br = new BufferedReader(new FileReader(path));
            
            while ((sentence = br.readLine()) != null) {
                if (sentence.length() > 0) {
                    String [] split = sentence.split(" +");
                    for(String s : split) {
                        if (!ret.containsKey(s)) {
                            ret.put(s, true);
                        }
                    }
                }
            }
        } catch (IOException e) {}
        
        return ret;
    }
    
}
