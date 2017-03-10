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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;
import static tahelper.TAHelper.sb;

/**
 *
 * @author khaidzir
 */
public class WordChecker {
    
    public static StringBuilder sb;
    
    public static void main (String args[]) {
        /* Proses lexical file */
//        String lexfile = "D:\\Kuliah\\TA\\model\\lex.e2f";
//        String output = "D:\\Kuliah\\TA\\model\\daftar kata unik.txt";
//        processUniqueLexicalFile(lexfile, output);
        
        /* Cek kata-kata */
//        String inputfile = "D:\\Kuliah\\TA\\Sementara\\Translation\\subtitle\\clean sub id";
//        String reffile = "D:\\Kuliah\\TA\\model\\daftar kata unik.txt";
//        String output = "D:\\Kuliah\\TA\\Sementara\\daftar oov id.txt";
//        writeOutput(checkWords(inputfile, buildMap(reffile)), output);
        
        /* Cek kata-kata */
//        String inputfile = "D:\\Kuliah\\TA\\Sementara\\itb korpus clean";
//        String reffile = "D:\\Kuliah\\TA\\model\\daftar kata unik.txt";
//        String output = "D:\\Kuliah\\TA\\Sementara\\daftar oov id itb.txt";
//        writeOutput(checkWords(inputfile, buildMap(reffile)), output);

        /* Filter kalimat - kalimat yang tdk mengandung oov */
        String inputfile = "D:\\Kuliah\\TA\\Sementara\\clean itb id";
        String reffile = "D:\\Kuliah\\TA\\model\\daftar kata unik.txt";
        String outputid = "D:\\Kuliah\\TA\\Sementara\\tanpa oov id itb.txt";
        String inputen = "D:\\Kuliah\\TA\\Sementara\\clean itb en";
        String outputen = "D:\\Kuliah\\TA\\Sementara\\tanpa oov en itb.txt";
        LinkedList<Integer> lines = filterSentenceRet(inputfile, reffile, outputid);
        takeSentences(inputen, lines, outputen);
        
        /* Filter kalimat - kalimat yang tdk mengandung oov */
//        String inputfile = "D:\\Kuliah\\TA\\Sementara\\clean sub id";
//        String reffile = "D:\\Kuliah\\TA\\model\\daftar kata unik.txt";
//        String output = "D:\\Kuliah\\TA\\Sementara\\tanpa oov id sub.txt";
//        filterSentence(inputfile, reffile, output);
    }
    
    public static void filterSentence(String inputpath, String refpath, String outpath) {
        BufferedReader br = null;
        String sCurrentLine;
        String [] temp;
        
        sb = new StringBuilder();
        boolean cek = false;
        HashMap<String, Boolean> mapCheck = buildMap(refpath);
        
        try {
            br = new BufferedReader(new FileReader(inputpath));
            while ((sCurrentLine = br.readLine()) != null) {
                temp = sCurrentLine.split(" +");
                cek = true;
                for(String s : temp) {
                    if(!mapCheck.containsKey(s)) {
                        cek = false;
                        break;
                    }
                }
                if(cek) {
                    sb.append(sCurrentLine).append(System.lineSeparator());
                }
            }
            writeOutput(sb.toString(), outpath);
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
    
    public static LinkedList<Integer> filterSentenceRet(String inputpath, String refpath, String outpath) {
        BufferedReader br = null;
        String sCurrentLine;
        String [] temp;
        
        sb = new StringBuilder();
        boolean cek = false;
        HashMap<String, Boolean> mapCheck = buildMap(refpath);
        LinkedList<Integer> retList = new LinkedList<>();
        int line=1;
        
        try {
            br = new BufferedReader(new FileReader(inputpath));
            while ((sCurrentLine = br.readLine()) != null) {
                temp = sCurrentLine.split(" +");
                cek = true;
                for(String s : temp) {
                    if(!mapCheck.containsKey(s)) {
                        cek = false;
                        break;
                    }
                }
                if(cek) {
                    sb.append(sCurrentLine).append(System.lineSeparator());
                    retList.add(line);
                }
                line++;
            }
            writeOutput(sb.toString(), outpath);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return retList;
    }
    
    public static void takeSentences(String inputpath, LinkedList<Integer> lines, String outpath) {
        BufferedReader br = null;
        String sCurrentLine;
        String [] temp;
        
        sb = new StringBuilder();
        int line=1;
        
        try {
            br = new BufferedReader(new FileReader(inputpath));
            while ((sCurrentLine = br.readLine()) != null && !lines.isEmpty()) {
                if(line == lines.getFirst()) {
                    sb.append(sCurrentLine).append(System.lineSeparator());
                    lines.removeFirst();
                }
                line++;
            }
            writeOutput(sb.toString(), outpath);
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
    
    public static void processLexicalFile(String inputPath, String outputPath) {
        BufferedReader br = null;
        String sCurrentLine;
        String [] temp;
        
        sb = new StringBuilder();
        
        try {
            br = new BufferedReader(new FileReader(inputPath));
            while ((sCurrentLine = br.readLine()) != null) {
                temp = sCurrentLine.split(" +");
                sb.append(temp[0]).append(System.lineSeparator());
            }
            writeOutput(sb.toString(), outputPath);
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
    
    public static void processUniqueLexicalFile(String inputPath, String outputPath) {
        BufferedReader br = null;
        String sCurrentLine;
        String [] temp;
        HashMap<String, Boolean> map = new HashMap<>();
        sb = new StringBuilder();
        
        try {
            br = new BufferedReader(new FileReader(inputPath));
            while ((sCurrentLine = br.readLine()) != null) {
                temp = sCurrentLine.split(" ");
                if(!map.containsKey(temp[0])) {
                    sb.append(temp[0]).append(System.lineSeparator());
                    map.put(temp[0], true);
                }
            }
            writeOutput(sb.toString(), outputPath);
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
    
    public static HashMap<String, Boolean> buildMap(String pathfile) {
        HashMap<String, Boolean> retMap = new HashMap<>();
        
        BufferedReader br = null;
        String sCurrentLine;
        
        try {
            br = new BufferedReader(new FileReader(pathfile));
            while ((sCurrentLine = br.readLine()) != null) {
                retMap.put(sCurrentLine, true);
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
        
        return retMap;
    }
    
    public static String checkWords(String inputPath, HashMap<String, Boolean> mapRef) {
        BufferedReader br = null;
        String sCurrentLine;
        String [] temp;
        HashMap<String, Boolean> checkMap = new HashMap<>();
        
        sb = new StringBuilder();
        
        try {
            br = new BufferedReader(new FileReader(inputPath));
            while ((sCurrentLine = br.readLine()) != null) {
                temp = sCurrentLine.split(" ");
                for (String temp1 : temp) {
                    if (temp1.length() > 0) {
                        if (!mapRef.containsKey(temp1) && !checkMap.containsKey(temp1)) {
                            sb.append(temp1).append(System.lineSeparator());
                            checkMap.put(temp1, true);
                        }
                    }
                }
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
        
        return sb.toString();
    }
}