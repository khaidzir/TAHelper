/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tahelper;

import java.util.ArrayList;
import IndonesianNLP.IndonesianPOSTagger;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import static tahelper.DataSentence.*;

/**
 *
 * @author khaidzir
 */
public class Annotator {
    
    public static final String LABELED_FILE = "E:/Kuliah/TA/Korpus/Dataset/labeled_data_5label.txt";
    public static final String OUTPUT_FILE = "E:/Kuliah/TA/Korpus/Dataset/annotated_5label_.txt";
    
    public static void main(String[] args) {
        
//        String tes = "aah ukuran celana juga misalnya saya merasa diri saya sehat kalau misalnya pria dibawah tiga lima";
//        String[] input = tes.split(" +");
//        int[] res = calculateDistSimWords(input);
//        for(int a : res) System.out.print(a + " ");
//        System.out.println();
//        int[] res1 = calculateDistPartialWords(input);
//        for(int a : res1) System.out.print(a + " ");
//        System.out.println();
//        System.out.println(isPartialWord("se", "sebagai"));
        
        /* Annotate labeled file */
        ArrayList<DataSentence> sentences = readLabeledData(LABELED_FILE);
        writeToFileNominalMode(sentences, OUTPUT_FILE, true);
        
        /* Eksperimen */
        // Awal
//        for(int i=1; i<=10; i++) {
//            String infile = "/home/khaidzir/TA/ProyekTA/Others/new label/10Fold/10Fold-"+i+"/test-"+i;
//            String outfile = "/home/khaidzir/TA/ProyekTA/Others/new label/10Fold/10Fold-"+i+"/annotated_flnc";
//            int maxWord = annotateText(infile, outfile);
//            System.out.println("Maksimum kata per kalimat : " + maxWord);
//        }
        
        // Ulangi terus
//        String inoutfile = "/home/khaidzir/TA/ProyekTA/Others/Preprocess/annotated_flnc";
//        String inoutfile = args[0];
//        addPrevLabel(inoutfile);

        // Gabung hasil
//        for(int i=1; i<=10; i++) {            
//            String grcFile = "/home/khaidzir/TA/ProyekTA/Others/new label/10Fold/10Fold-"+i+"/result_grc";
//            String fncFile = "/home/khaidzir/TA/ProyekTA/Others/new label/10Fold/10Fold-"+i+"/result_compare";
//            String outFile = "/home/khaidzir/TA/ProyekTA/Others/new label/10Fold/10Fold-"+i+"/result_compare";
//            String outKalimat = "/home/khaidzir/TA/ProyekTA/Others/new label/10Fold/10Fold-"+i+"/clean_compare";
//            gabungLabel(grcFile, fncFile, outFile, outKalimat);
//        }
//        String folder = "/home/khaidzir/TA/ProyekTA/Others/new label/10Fold/10Fold-";
//        String rfilein = "result";
//        String rfileout = "/home/khaidzir/TA/ProyekTA/Others/new label/10Fold/Result/hasil_preprocess";
//        gabungFile10Fold(folder, rfilein, rfileout);
//        String cfilein = "clean";
//        String cfileout = "/home/khaidzir/TA/ProyekTA/Others/new label/10Fold/Result/clean";
//        gabungFile10Fold(folder, cfilein, cfileout);

//        String rfilein = "result_compare";
//        String rfileout = "/home/khaidzir/TA/ProyekTA/Others/new label/10Fold/Result/result_compare";
//        gabungFile10Fold(folder, rfilein, rfileout);
//        String cfilein = "clean_compare";
//        String cfileout = "/home/khaidzir/TA/ProyekTA/Others/new label/10Fold/Result/clean_compare";
//        gabungFile10Fold(folder, cfilein, cfileout);
    }
    
    /* Eksperimen */
    public static int annotateText(String infile, String outfile) {
        int ret = 0;
        BufferedReader br = null;
        FileOutputStream fos;
        ArrayList<DataSentence> sentences = new ArrayList<>();
        
        try {
            // Baca file infile
            String sCurrentLine;
            br = new BufferedReader(new FileReader(infile));
            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.length() > 0) {
                    sentences.add(annotateSentence(sCurrentLine));
                }
            }
            
            // Tulis ke outfile
            File fout = new File(outfile);
            fos = new FileOutputStream(fout);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));  
            
            for (DataSentence d : sentences) {
                if (d.sentence.size() > ret) ret = d.sentence.size();
                for(ArrayList<Object> o : d.sentence) {
                    bw.write((String) o.get(KATA)); bw.write(" ");                    
                    bw.write((String) o.get(POS)); bw.write(" ");
                    bw.write(String.valueOf(o.get(POSISI_TOKEN))); bw.write(" ");
                    bw.write(String.valueOf(o.get(DIST_KATA))); bw.write(" ");
                    bw.write(String.valueOf(o.get(DIST_POS))); bw.write(" ");
                    bw.write(String.valueOf(o.get(PATT_KATA))); bw.write(" ");
                    bw.write(String.valueOf(o.get(PATT_POS))); bw.write(" ");
                    bw.write(String.valueOf(o.get(WORD_SIM_1))); bw.write(" ");
                    bw.write(String.valueOf(o.get(WORD_SIM_2))); bw.write(" ");
                    bw.write(String.valueOf(o.get(WORD_SIM_3))); bw.write(" \n");
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
        
        return ret;
    }
    
    public static void addPrevLabel(String inoutfile) {
        ArrayList<DataSentence> sentences = new ArrayList<>();
        BufferedReader br;
        String line;
        
        // Baca
        try {
            br = new BufferedReader(new FileReader(inoutfile));
            int idx = -1;
            // b buat nandain kalimat baru, d buat nandain <s>
            boolean b = false, d = true;
            String[] split = null;
            String prevlabel;
            while ((line = br.readLine()) != null) {
                if (line.length() > 0) {
                    if (!b) {                        
                        sentences.add(new DataSentence());
                        idx++;
                        b = true;
                        d = true;
                        split = line.split("\\s+");
                    } else {
                        prevlabel = split[LABEL];
                        split = line.split("\\s+");                        
//                        if (split[PREV_LABEL].equals("<s>") && d) {                           
//                            split[PREV_LABEL] = prevlabel;
//                            d = false;                            
//                        }
                    }
                    
                    ArrayList<Object> objs = new ArrayList<>();                    
                    objs.addAll(Arrays.asList(split));
                    sentences.get(idx).add(objs);                    
                } else {
                    b = false;
                    d = false;
                }
            }            
            br.close();
        } catch (IOException e) {}
        
        // Tulis
        writeToFile(sentences, inoutfile, false);        
    }
    
    public static void gabungLabel(String grcFile, String fncFile, String outFile, String outKalimat) {
        BufferedReader brGRC, brFNC;
        String lineGRC, lineFNC;
        ArrayList<ArrayList<String>> kalimat = new ArrayList<>();
        ArrayList<ArrayList<String>> label = new ArrayList<>();
        
        // Baca
        try {
            brGRC = new BufferedReader(new FileReader(grcFile));
            brFNC = new BufferedReader(new FileReader(fncFile));            
            
            boolean b = false;
            int idx = -1;
            while ( (lineGRC = brGRC.readLine()) != null 
                 && (lineFNC = brFNC.readLine()) != null) {
                if (lineGRC.length() > 0) {
                    if (!b) {
                        kalimat.add(new ArrayList<>());
                        label.add(new ArrayList<>());
                        idx++;
                        b = true;
                    }
                    
                    String [] splitGRC = lineGRC.split("\\s+");
                    String [] splitFNC = lineFNC.split("\\s+");
                    
                    kalimat.get(idx).add(splitGRC[0]);
                    
                    if (splitFNC[splitFNC.length-1].equals("0")) {
                        if (!splitGRC[splitGRC.length-1].equals("-")) {
                            label.get(idx).add(splitGRC[splitGRC.length-1]);
                        } else {
                            label.get(idx).add("0");
                        }
                    } else {
                        label.get(idx).add(splitFNC[splitFNC.length-1]);
                    }
                    
                } else {
                    b = false;
                }
            }            
            brGRC.close();
            brFNC.close();
        } catch (IOException e) {}
        
        // Tulis
        FileOutputStream fos;
        FileOutputStream fos2;
        try {
            File fout = new File(outFile);
            fos = new FileOutputStream(fout);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));  
            
            File fout2 = new File(outKalimat);
            fos2 = new FileOutputStream(fout2);
            BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(fos2));  
            
            for(int i=0; i<kalimat.size(); i++) {
                for(int j=0; j<kalimat.get(i).size(); j++) {
                    bw.write(kalimat.get(i).get(j));
                    bw.write(" ");
                    bw.write(label.get(i).get(j));
                    bw.write("\n");
                    if (label.get(i).get(j).equals("0")) {
                        bw2.write(kalimat.get(i).get(j));
                        bw2.write(" ");
                    }
                }
                
                bw.write("\n");
                bw2.write("\n");
            }
            
            bw.close();
            fos.close();
            bw2.close();
            fos2.close();
        } catch (FileNotFoundException ex) {
        } catch(IOException e) {
        }
        
    }
    
    public static void gabungFile10Fold(String folder, String filein, String fileout) {        
        ArrayList<String> words = new ArrayList<>();
        
        // Baca
        for(int i=1; i<=10; i++) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(folder+i+"/"+filein));
                String line;
                boolean b = false;
                int idx = -1;
                while ( (line = br.readLine()) != null ) {                     
                    words.add(line);
                }
                br.close();
            } catch (IOException e) {}
        }
        
        
        // Tulis
        FileOutputStream fos;        
        try {
            File fout = new File(fileout);
            fos = new FileOutputStream(fout);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));  
            
            for(String s : words) {
                bw.write(s);
                bw.write("\n");
            }
            
            bw.close();
            fos.close();
        } catch (FileNotFoundException ex) {
        } catch(IOException e) {
        }
    }
    
    ////////////////////
    
    public static ArrayList<DataSentence> readLabeledData(String source) {
        ArrayList<DataSentence> ret = new ArrayList<>();
        BufferedReader br;
        String sentence, label;
        
        try {
            br = new BufferedReader(new FileReader(source));
            
            while ((sentence = br.readLine()) != null) {
                if (sentence.length() > 0) {
                    label = br.readLine();
                    ret.add(annotateLabeledSentence(sentence, label));
                }
            }
        } catch (IOException e) {}
        
        return ret;
    }
    
    public static void writeToFile(ArrayList<DataSentence> sentences, String outpath, boolean labeled) {
        FileOutputStream fos;
        try {
            File fout = new File(outpath);
            fos = new FileOutputStream(fout);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));  
            
            for (DataSentence d : sentences) {
                for(ArrayList<Object> o : d.sentence) {
                    bw.write((String) o.get(KATA)); bw.write(" ");                    
                    bw.write((String) o.get(POS)); bw.write(" ");
                    bw.write(String.valueOf(o.get(POSISI_TOKEN))); bw.write(" ");
                    bw.write(String.valueOf(o.get(DIST_KATA))); bw.write(" ");
                    bw.write(String.valueOf(o.get(DIST_POS))); bw.write(" ");
                    bw.write(String.valueOf(o.get(PATT_KATA))); bw.write(" ");
                    bw.write(String.valueOf(o.get(PATT_POS))); bw.write(" ");
                    bw.write(String.valueOf(o.get(WORD_SIM_1)));                    
                    if (labeled) {
                        bw.write(" ");
                        bw.write((String) o.get(LABEL)); bw.write("\n");
                    } else {
                        bw.write("\n");
                    }
                    
                }
                bw.write("\n");
            }
            
            bw.close();
            fos.close();
        } catch (FileNotFoundException ex) {
        } catch(IOException e) {
        }
    }
    
    public static void writeToFileNominalMode(ArrayList<DataSentence> sentences, String outpath, boolean labeled) {
        FileOutputStream fos;
        try {
            File fout = new File(outpath);
            fos = new FileOutputStream(fout);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));  
            
            for (DataSentence d : sentences) {
                for(ArrayList<Object> o : d.sentence) {
                    bw.write((String) o.get(KATA)); bw.write(" ");                    
                    bw.write((String) o.get(POS)); bw.write(" ");
                    bw.write(getNomPos((float)o.get(POSISI_TOKEN))); bw.write(" ");
                    bw.write(getNomDist((int)o.get(DIST_KATA))); bw.write(" ");
                    bw.write(getNomDist((int)o.get(DIST_POS))); bw.write(" ");
                    bw.write(String.valueOf(o.get(PATT_KATA))); bw.write(" ");
                    bw.write(String.valueOf(o.get(PATT_POS))); bw.write(" ");
                    bw.write(getNomSim((float)o.get(WORD_SIM_1))); bw.write(" ");
                    bw.write(getNomSim((float)o.get(WORD_SIM_2))); bw.write(" ");
                    bw.write(getNomSim((float)o.get(WORD_SIM_3))); bw.write(" ");
                    bw.write(String.valueOf((int)o.get(DIST_WORD_SIM))); bw.write(" ");
                    bw.write(String.valueOf((int)o.get(PARTIAL_WORD_1))); bw.write(" ");
                    bw.write(String.valueOf((int)o.get(PARTIAL_WORD_2))); bw.write(" ");
                    bw.write(String.valueOf((int)o.get(PARTIAL_WORD_3))); bw.write(" ");
                    bw.write(String.valueOf((int)o.get(DIST_PARTIAL_WORD)));
                    if (labeled) {
                        bw.write(" ");
                        bw.write((String) o.get(LABEL)); bw.write("\n");
                    } else {
                        bw.write("\n");
                    }
                    
                }
                bw.write("\n");
            }
            
            bw.close();
            fos.close();
        } catch (FileNotFoundException ex) {
        } catch(IOException e) {
        }
    }
    
    public static DataSentence annotateLabeledSentence(String sentence, String label) {
        DataSentence ret = new DataSentence();
        
        // POS
        ArrayList<String[]> arrRes = null;
        arrRes = IndonesianPOSTagger.doPOSTag(sentence);
        String[] splitPOS = new String[arrRes.size()];
        String[] splitKata = new String[arrRes.size()];
        for(int i=0; i<arrRes.size(); i++) {
            splitKata[i] = arrRes.get(i)[0];
            splitPOS[i] = arrRes.get(i)[1];
        }
        
        // Posisi token
        float tokenPos[] = new float[splitKata.length];
        for(int i=0; i<tokenPos.length; i++) {
            tokenPos[i] = (float)(i+1) / (float)tokenPos.length;
        }
        
        // Dist kata
        int[] distKata = calculateDist(splitKata);
        
        // Dist POS
        int[] distPOS = calculateDist(splitPOS);
        
        // Patt kata
        int[] pattKata = calculatePatt(splitKata);
        
        // Patt POS
        int[] pattPOS = calculatePatt(splitPOS);
        
        // Word sim 1 
        float[] wordSim1 = calculateWordSim(splitKata);
        
        // Word sim 2
        float[] wordSim2 = calculateNWordSim(splitKata,2);
        
        // Word sim 3
        float[] wordSim3 = calculateNWordSim(splitKata,3);
        
        // Dist word sim
        int[] distWordSim = calculateDistSimWords(splitKata);
        
        // PartialWord 1
        int[] partWord1 = calculateNPartialWords(splitKata, 1);
        
        // PartialWord 2
        int[] partWord2 = calculateNPartialWords(splitKata, 2);
        
        // PartialWord 3
        int[] partWord3 = calculateNPartialWords(splitKata, 3);
        
        // Dist part word
        int[] distPartWord = calculateDistPartialWords(splitKata);
        
        // Label
        String[] arrLabel = label.split(" +");
        
        // Kumpulkeun Mang!
        for(int i=0; i<splitKata.length; i++) {
            ArrayList<Object> data = new ArrayList<>();
            data.add(splitKata[i]);
            data.add(splitPOS[i]);
            data.add(tokenPos[i]);
            data.add(distKata[i]);
            data.add(distPOS[i]);
            data.add(pattKata[i]);
            data.add(pattPOS[i]);
            data.add(wordSim1[i]);            
            data.add(wordSim2[i]);
            data.add(wordSim3[i]);
            data.add(distWordSim[i]);
            data.add(partWord1[i]);
            data.add(partWord2[i]);
            data.add(partWord3[i]);
            data.add(distPartWord[i]);
            data.add(arrLabel[i]);
            ret.add(data);
        }
        
        return ret;
    }
    
    public static String getNomDist(int dist) {
        if (dist > 11) return "E";
        else return String.valueOf(dist);
    }
    
    public static String getNomPos(float pos) {
        float s = (float)1/(float)3;
        float m = (float)2/(float)3;
        if (pos <= s) return "S";
        if (pos <= m) return "M";
        return "F";
    }
    
    public static String getNomSim(float sim) {
        if (eqFloat(sim, (float)1)) return "1";
        else return "0";
    }
    
    public static DataSentence annotateSentence(String sentence) {
        DataSentence ret = new DataSentence();
        
        // POS
        ArrayList<String[]> arrRes = null;
        arrRes = IndonesianPOSTagger.doPOSTag(sentence);
        String[] splitPOS = new String[arrRes.size()];
        String[] splitKata = new String[arrRes.size()];
        for(int i=0; i<arrRes.size(); i++) {
            splitKata[i] = arrRes.get(i)[0];
            splitPOS[i] = arrRes.get(i)[1];
        }
        
        // Posisi token
        float tokenPos[] = new float[splitKata.length];
        for(int i=0; i<tokenPos.length; i++) {
            tokenPos[i] = (float)(i+1) / (float)tokenPos.length;
        }
        
        // Dist kata
        int[] distKata = calculateDist(splitKata);
        
        // Dist POS
        int[] distPOS = calculateDist(splitPOS);
        
        // Patt kata
        int[] pattKata = calculatePatt(splitKata);
        
        // Patt POS
        int[] pattPOS = calculatePatt(splitPOS);
        
        // Word sim
        float[] wordSim = calculateWordSim(splitKata);
        
        // Word 2 sim
        float[] word2Sim = calculateNWordSim(splitKata, 2);
        
        // Word 3 sim
        float[] word3Sim = calculateNWordSim(splitKata, 3);
        
        // Dist word sim
        int[] distWordSim = calculateDistSimWords(splitKata);
        
        // PartialWord 1
        int[] partWord1 = calculateNPartialWords(splitKata, 1);
        
        // PartialWord 2
        int[] partWord2 = calculateNPartialWords(splitKata, 3);
        
        // PartialWord 3
        int[] partWord3 = calculateNPartialWords(splitKata, 3);
        
        // Dist part word
        int[] distPartWord = calculateDistPartialWords(splitKata);
        
        // Kumpulkeun Mang!
        for(int i=0; i<splitKata.length; i++) {
            ArrayList<Object> data = new ArrayList<>();
            data.add(splitKata[i]);
            data.add(splitPOS[i]);
            data.add(tokenPos[i]);
            data.add(distKata[i]);
            data.add(distPOS[i]);
            data.add(pattKata[i]);
            data.add(pattPOS[i]);
            data.add(wordSim[i]);
            data.add(word2Sim[i]);
            data.add(word3Sim[i]);
            data.add(distWordSim[i]);
            data.add(partWord1[i]);
            data.add(partWord2[i]);
            data.add(partWord3[i]);
            data.add(distPartWord[i]);
            ret.add(data);
        }
        
        return ret;
    }
    
    public static int[] calculateDist(String[] input) {
        int [] ret = new int[input.length];
        
        for(int i=0; i<ret.length; i++) {
            ret[i] = 0;
        }
        
        for(int i=0; i<input.length-1; i++) {
            for(int j=i+1; j<input.length; j++) {
                if (input[i].equals(input[j])) {
                    ret[i] = j-i;
                    break;
                }
            }
        }
        
        return ret;
    }
    
    public static int[] calculatePatt(String[] input) {
        int[] ret = new int[input.length];
        for(int i=0; i<ret.length; i++) ret[i] = 0;
        
        int[] dist = calculateDist(input);
        
        int max;
        for(int i=0; i<ret.length-1; i++) {
            max = i;
            if (dist[i] > 0) {
                for(int j=i+1; j<ret.length; j++) {
                    if(dist[j] != dist[i]) break;
                    else max=j;
                }
            }
            if (max > i) {
                for(int j=i; j<=max; j++) {
                    ret[j] = 1;
                }
            }
            i = max;
        }
        
        return ret;
    }
    
    
    // Word sim
    public static float calculateSimilarityBetween2Words(String word1, String word2) {
        int c = 0;
        int l1 = word1.length();
        int l2 = word2.length();
        boolean b = true;
        while(b && c < l1 && c < l2) {
            if (word1.charAt(c) == word2.charAt(c)) c++;
            else b = false;
        }
        
        return ((float)c)/((float)word1.length());
    }
    
    public static float[] calculateWordSim(String[] input) {
        float[] ret = new float[input.length];
        
        for(int i=0; i<input.length-1; i++) {
            ret[i] = calculateSimilarityBetween2Words(input[i], input[i+1]);
        }
        ret[ret.length-1] = (float)0.0;
        
        return ret;
    }
    
    public static float[] calculateNWordSim(String[] input, int n) {
        float[] ret = new float[input.length];
        
        int i = 0;
        for(; i<input.length-n; i++) {
            ret[i] = calculateSimilarityBetween2Words(input[i], input[i+n]);
        }
        
        for(; i<input.length; i++) {
            ret[i] = (float)0.0;
        }
        
        return ret;
    }
    
    public static int[] calculateDistSimWords(String [] input) {
        int [] ret = new int[input.length];
        boolean b;
        for(int i=0; i<input.length; i++) {
            b = false;
            for(int j=i+1; j<input.length&&!b; j++) {
                if (eqFloat((float)1.0, calculateSimilarityBetween2Words(input[i], input[j]))) {
                    ret[i] = j-i;
                    b = true;
                }
            }
            if (!b) ret[i] = 0;
        }
        
        return ret;
    }
    
    // Partial word
    public static boolean isPartialWord(String word1, String word2) {
        int l1 = word1.length(), l2 = word2.length();
        if (l1 > l2) return false;
        int j=0;
        for(int i=0; i<l2; i++) {
            if (word1.charAt(j) == word2.charAt(i)) {
                j++;
                if (j == l1) i=l2;
            }
            else j=0;
        }
        return j == l1;
    }
    
    public static int[] calculateNPartialWords(String[] input, int n) {
        int[] ret = new int[input.length];
        
        int i = 0;
        for(; i<input.length-n; i++) {
            ret[i] = isPartialWord(input[i], input[i+n])? 1 : 0;
        }
        
        for(; i<input.length; i++) {
            ret[i] = 0;
        }
        
        return ret;
    }
    
    public static int[] calculateDistPartialWords(String[] input) {        
        int [] ret = new int[input.length];
        boolean b;
        for(int i=0; i<input.length; i++) {
            b = false;
            for(int j=i+1; j<input.length&&!b; j++) {
                if (isPartialWord(input[i], input[j])) {
                    ret[i] = j-i;
                    b = true;
                }
            }
            if (!b) ret[i] = 0;
        }
        
        return ret;
    }
    
    
    
    public static boolean eqFloat(float f1, float f2) {
        float eps = (float) 0.0000001;
        return Math.abs(f1-f2) <= eps;
    }
    
}
