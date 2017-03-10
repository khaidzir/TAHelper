/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tahelper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import static tahelper.Annotator.readLabeledData;
import static tahelper.Annotator.writeToFile;
import static tahelper.DataSentence.*;

/**
 *
 * @author khaidzir
 */
public class Eksperimen {
    
    public static void main(String [] args) {
//        gabungAll3Label();
//        generateStats3Label();        
        kumpulTranslate();
    }
    
    public static void kumpulTranslate() {
        String oriText = "/home/khaidzir/Desktop/original_text.txt";
        String sk2A = "/home/khaidzir/Desktop/A_result3";
        String sk2B = "/home/khaidzir/Desktop/B_result3";
        String sk2C = "/home/khaidzir/Desktop/C_result7";
        String sk2D = "/home/khaidzir/Desktop/D_result10";
        
        String sk[] = {sk2A, sk2B, sk2C, sk2D};
        
        String outfile = "/home/khaidzir/Desktop/gabung_translate";
        StringBuilder sb = new StringBuilder();
        
        // Baca
        try {
            BufferedReader br = new BufferedReader(new FileReader(oriText));
            String line;
            while ( (line = br.readLine()) != null ) {
                sb.append(line).append("\n");
            }
            br.close();
        } catch (IOException e) {e.printStackTrace();}
        
        sb.append("\n");
        
        // Baca
        for(int i=0; i<sk.length; i++) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(sk[i]));
                String line, split[];
                while ( (line = br.readLine()) != null) {
                    if (line.length() > 0) {
                        split = line.split("\\s+");
                        if (split[split.length-1].equals("0")) {
                            sb.append(split[0]).append(" ");
                        }
                    } else {
                        sb.append("\n");
                    }
                }            
                br.close();
            } catch (IOException e) {}
            sb.append("\n");
        }
        
        // Tulis
        try {
            File file = new File(outfile);

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
    
    public static void generateStats5Label() {
        String root = "/home/khaidzir/TA/ProyekTA/Eksperimen/10Fold/5Label/";
        String [] folders = {"10Fold_all", "10Fold_o", "10Fold_prevlabel", "10Fold_sim"};
        String infile = "result";
        String outfile = "stats";
        for(int i=0; i<folders.length; i++) {
            String folderin = root+folders[i]+"/Results";
            String folderout = root+folders[i]+"/Stats";
            String filegabung = root+folders[i]+"/summary";
            generateStats(folderin, infile, folderout, outfile, filegabung);
        }
    }
    
    public static void generateStats3Label() {
        String root = "/home/khaidzir/TA/ProyekTA/Eksperimen/10Fold/3Label/";
        String [] folders = {"10Fold_all", "10Fold_o", "10Fold_prevlabel", "10Fold_sim"};
        String infile = "result";
        String outfile = "stats";
        for(int i=0; i<folders.length; i++) {
            String folderin = root+folders[i]+"/Combine Rule Based";
            String folderout = root+folders[i]+"/Stats";
            String filegabung = root+folders[i]+"/summary";
            generateStats(folderin, infile, folderout, outfile, filegabung);
        }
    }
    
    public static void generateStats(String folderin, String infile, String folderout, String outfile, String outgabungan) {
        ArrayList<double []> precision = new ArrayList<>();
        ArrayList<double []> recall = new ArrayList<>();
        double [] accuracy = new double[12];
        ArrayList<int[][]> confMatriks = new ArrayList<>();
        
        int idxPredict, idxLabel, hit=0;
        int sumLabel[] = new int[5], sumPredict[] = new int[5];
        int counter=0;
        
        for(int i=0; i<12; i++) {
            // Baca
            try {
                BufferedReader br = new BufferedReader(new FileReader(folderin+"/"+infile+i));
                String line, split[];
                confMatriks.add(new int[5][5]);
                System.out.println(confMatriks.size());
                hit = 0;
                counter = 0;
                while ( (line = br.readLine()) != null ) {
                    if (line.length() > 0) {
                        counter++;
                        split = line.split("\\s+");
                        idxLabel = convertLabelToInt(split[split.length-2]);
                        idxPredict = convertLabelToInt(split[split.length-1]);
                        confMatriks.get(i)[idxLabel][idxPredict]++;
                        if (idxLabel == idxPredict) hit++;
                    }
                }
                br.close();
            } catch (IOException e) {e.printStackTrace();}
            
            // Hitung stats
            for(int j=0; j<5; j++) {
                sumLabel[j] = 0;
                sumPredict[j] = 0;
                for(int k=0; k<5; k++) {
                    sumPredict[j] += confMatriks.get(i)[j][k];
                    sumLabel[j] += confMatriks.get(i)[k][j];
                }
            }
            precision.add(new double[5]);
            recall.add(new double[5]);            
            for(int j=0; j<5; j++) {
                precision.get(i)[j] = (double)confMatriks.get(i)[j][j] / (double)sumLabel[j];
                recall.get(i)[j] = (double)confMatriks.get(i)[j][j] / (double)sumPredict[j];
            }
            accuracy[i] = (double)hit/(double)counter;
            
            // Tulis
            FileOutputStream fos;        
            try {
                File fout = new File(folderout+"/"+outfile+i);
                fos = new FileOutputStream(fout);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
                
                for(int j=0; j<5; j++) {
                    bw.write("Precision ");
                    bw.write(convertIntToLabel(j));
                    bw.write("\t= ");
                    bw.write(String.format("%.4f", precision.get(i)[j]));
                    bw.write("\tRecall ");
                    bw.write(convertIntToLabel(j));
                    bw.write("\t= ");
                    bw.write(String.format("%.4f", recall.get(i)[j]));
                    bw.write("\n");
                }
                bw.write("Akurasi\t= ");
                bw.write(String.format("%.4f", accuracy[i]));

                bw.write("\nConfusion matrix:\n");
                for(int j=0; j<5; j++) {
                    for(int k=0; k<5; k++) {
                        bw.write(String.valueOf(confMatriks.get(i)[j][k]));
                        bw.write("\t");
                    }
                    bw.write("\n");
                }
                
                for(int j=0; j<5; j++) {
                    bw.write("Indeks-"+j);
                    bw.write("\t= "+convertIntToLabel(j)+"\n");
                }

                bw.close();
                fos.close();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        
        // Tulis hasi gabungan
        FileOutputStream fos;        
        try {
            File fout = new File(outgabungan);
            fos = new FileOutputStream(fout);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            for(int i=0; i<12; i++) {
                for(int j=0; j<5; j++) {
                    bw.write("template"+i+"\t");
                    bw.write("Precision ");
                    bw.write(convertIntToLabel(j));
                    bw.write("\t= ");
                    bw.write(String.format("%.4f", precision.get(i)[j]));
                    bw.write("\tRecall ");
                    bw.write(convertIntToLabel(j));
                    bw.write("\t= ");
                    bw.write(String.format("%.4f", recall.get(i)[j]));
                    bw.write("\n");
                }
            }

            bw.close();
            fos.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
        
    }
    
    public static void gabungAll3Label() {
        String grcFile = "/home/khaidzir/TA/ProyekTA/Eksperimen/10Fold/hasil_rule_based";
        String rootfolder = "/home/khaidzir/TA/ProyekTA/Eksperimen/10Fold/3Label/";
        String [] folders = {"10Fold_all", "10Fold_o", "10Fold_prevlabel", "10Fold_sim"};
        String file5label = "/home/khaidzir/TA/ProyekTA/Eksperimen/Dataset/annotated_5label.txt";
        for(int i=0; i<folders.length; i++) {
            for(int j=0; j<12; j++) {
                String fncFile = rootfolder+folders[i]+"/Results/result"+j;
                String outfile = rootfolder+folders[i]+"/Combine Rule Based/result"+j;
                gabungLabel(grcFile, fncFile, file5label, outfile);
            }
        }
    }
    
    public static void gabungLabel(String grcFile, String fncFile, String file5label, String outFile) {
        BufferedReader brGRC, brFNC, br5;
        String lineGRC, lineFNC, line5;
        ArrayList<ArrayList<String>> kalimat = new ArrayList<>();
        ArrayList<ArrayList<String>> label = new ArrayList<>();
        ArrayList<ArrayList<String>> truelabel = new ArrayList<>();
        
        // Baca
        try {
            brGRC = new BufferedReader(new FileReader(grcFile));
            brFNC = new BufferedReader(new FileReader(fncFile));
            br5 = new BufferedReader(new FileReader(file5label));
            
            boolean b = false;
            int idx = -1;
            while ( (lineGRC = brGRC.readLine()) != null 
                 && (lineFNC = brFNC.readLine()) != null
                 && (line5 = br5.readLine()) != null ) {
                if (lineGRC.length() > 0) {
                    if (!b) {
                        kalimat.add(new ArrayList<>());
                        label.add(new ArrayList<>());
                        truelabel.add(new ArrayList<>());
                        idx++;
                        b = true;
                    }
                    
                    String [] splitGRC = lineGRC.split("\\s+");
                    String [] splitFNC = lineFNC.split("\\s+");
                    String [] split5label = line5.split("\\s+");
                    
                    kalimat.get(idx).add(splitGRC[0]);
                    truelabel.get(idx).add(split5label[split5label.length-1]);
                    
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
        } catch (IOException e) {e.printStackTrace();}
        
        // Tulis
        FileOutputStream fos;
        try {
            File fout = new File(outFile);
            fos = new FileOutputStream(fout);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            
            for(int i=0; i<kalimat.size(); i++) {
                for(int j=0; j<kalimat.get(i).size(); j++) {
                    bw.write(kalimat.get(i).get(j));
                    bw.write("\t");
                    bw.write(truelabel.get(i).get(j));
                    bw.write("\t");
                    bw.write(label.get(i).get(j));
                    bw.write("\n");
                }
                
                bw.write("\n");
            }
            
            bw.close();
            fos.close();
        } catch (FileNotFoundException ex) {
        } catch(IOException e) {
        }
        
    }
    
    public static int convertLabelToInt(String label) {
        if (label.equals("0")) return 0;
        if (label.equals("FL")) return 1;
        if (label.equals("RC")) return 2;
        if (label.equals("NC")) return 3;
        if (label.equals("G")) return 4;
        return -1;
    }
    
    public static String convertIntToLabel(int label) {
        if (label == 0) return "0";
        if (label == 1) return "FL";
        if (label == 2) return "RC";
        if (label == 3) return "NC";
        if (label == 4) return "G";
        return null;
    }
    
    public static void gabungSemuaFold() {
        String jenis [] = {"10Fold_all", "10Fold_o", "10Fold_prevlabel", "10Fold_sim"};
        String fold3label = "/home/khaidzir/TA/ProyekTA/Eksperimen/10Fold/3Label/";
        String fold5label = "/home/khaidzir/TA/ProyekTA/Eksperimen/10Fold/5Label/";
        for(String s : jenis) {
            for(int i=0; i<12; i++) {
                gabungFile10Fold(fold3label+s+"/10Fold-", "result"+i, fold3label+s+"/Results/result"+i);
                gabungFile10Fold(fold5label+s+"/10Fold-", "result"+i, fold5label+s+"/Results/result"+i);
            }
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
    
    public static void cekDist() {
        
        String labeledFile = "/home/khaidzir/TA/ProyekTA/Eksperimen/Dataset/labeled_data_5label.txt";
        
        ArrayList<DataSentence> sentences = readLabeledData(labeledFile);
        
        HashMap<Integer, Integer> dataDistKata = new HashMap<>();
        HashMap<Integer, Integer> dataDistPOS = new HashMap<>();
        int tempk, tempp, count = 0;
        
        for(DataSentence d : sentences) {
            for(ArrayList<Object> o : d.sentence) {
                tempk = (int) o.get(DIST_KATA);
                tempp = (int) o.get(DIST_POS);
                if (dataDistKata.containsKey(tempk)) dataDistKata.put(tempk, dataDistKata.get(tempk)+1);
                else dataDistKata.put(tempk, 1);
                if (dataDistPOS.containsKey(tempp)) dataDistPOS.put(tempp, dataDistPOS.get(tempp)+1);
                else dataDistPOS.put(tempp, 1);
                count++;
            }
        }
        
        System.out.println("Total token : " + count);
        System.out.println("Data dist kata : ");
        System.out.println("Jarak\tCount\tPersen");
        for(int a : dataDistKata.keySet()) {
            System.out.println(a + "\t" + dataDistKata.get(a) + "\t" + ((double)dataDistKata.get(a)/(double)count)*100);
        }
        
        System.out.println("\nData dist POS : ");
        System.out.println("Jarak\tCount\tPersen");
        for(int a : dataDistPOS.keySet()) {
            System.out.println(a + "\t" + dataDistPOS.get(a) + "\t" + ((double)dataDistPOS.get(a)/(double)count)*100);
        }
                
    }
    
    public static void cekJumlahKataPerKalimat() {
        
        String labeledFile = "/home/khaidzir/TA/ProyekTA/Eksperimen/Dataset/labeled_data_5label.txt";
        
        ArrayList<DataSentence> sentences = readLabeledData(labeledFile);
        
        HashMap<Integer, Integer> dataJumKata = new HashMap<>();
        int tempk, tempp, count = 0;
        
        for(DataSentence d : sentences) {
            tempk = d.sentence.size();
            if (dataJumKata.containsKey(tempk)) dataJumKata.put(tempk, dataJumKata.get(tempk)+1);
            else dataJumKata.put(tempk, 1);
            count++;
        }
        
        System.out.println("Total kalimat : " + count);
        System.out.println("Data jumlah kata per kalimat : ");
        System.out.println("Jumlah kata\tCount\tPersen");
        for(int a : dataJumKata.keySet()) {
            System.out.println(a + "\t" + dataJumKata.get(a) + "\t" + ((double)dataJumKata.get(a)/(double)count)*100);
        }
                
    }
    
}
