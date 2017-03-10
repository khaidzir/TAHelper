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
import java.util.ArrayList;
import static tahelper.DataSentence.DIST_KATA;
import static tahelper.DataSentence.DIST_POS;
import static tahelper.DataSentence.KATA;
import static tahelper.DataSentence.LABEL;
import static tahelper.DataSentence.PATT_KATA;
import static tahelper.DataSentence.PATT_POS;
import static tahelper.DataSentence.POS;
import static tahelper.DataSentence.POSISI_TOKEN;
import static tahelper.DataSentence.WORD_SIM_1;

/**
 *
 * @author khaidzir
 */
public class DataSentenceProcess {    
    
    public static final String listPOS[] = {"NN","JJ","CC","CDC","IN","NNG","VBT","CDI","PRL","PRN","CDP","PRP",
        "CDO","SC","RB","DT","NEG","NNP","FW","UH","MD","WP","VBI","RP"};
    
    public static ArrayList<DataSentence> readFile(String filename) {
        ArrayList<DataSentence> ret = new ArrayList<>();
        
        BufferedReader br = null;
        String sCurrentLine;
        String [] split;
        
//        HashMap<String, Boolean> map = new HashMap<>();
        
        ret.add(new DataSentence());
        int currIdx = 0;
        try {
            br = new BufferedReader(new FileReader(filename));
            while ((sCurrentLine = br.readLine()) != null) {
                if(sCurrentLine.length() > 0) {
                    split = sCurrentLine.split(" +");
                    ArrayList<Object> attr = new ArrayList<>();
                    attr.add((String)split[KATA]);
                    attr.add((String)split[POS]);
                    attr.add(Float.parseFloat(split[POSISI_TOKEN]));
                    attr.add(Integer.parseInt(split[DIST_KATA]));
                    attr.add(Integer.parseInt(split[DIST_POS]));
                    attr.add(Integer.parseInt(split[PATT_KATA]));
                    attr.add(Integer.parseInt(split[PATT_POS]));
                    attr.add(Float.parseFloat(split[WORD_SIM_1]));
                    attr.add((String)split[LABEL]);
                    ret.get(currIdx).add(attr);
                    
//                    if (!map.containsKey((String)split[POS])) {
//                        map.put((String)split[POS], Boolean.TRUE);
//                    }
                } else {
                    ret.add(new DataSentence());
                    currIdx++;
                }
            }
            
//            for(String s : map.keySet()) {
//                System.out.print(s+",");                
//            }
//            System.out.println();
            if (ret.get(ret.size()-1).sentence.isEmpty()) {
                ret.remove(ret.size()-1);
            }
            
            
        } catch (IOException e) {
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
            }
        }
        
        return ret;
    }
    
    public static ArrayList<DataSentence> readFileNominalMode(String filename) {
        ArrayList<DataSentence> ret = new ArrayList<>();
        
        BufferedReader br = null;
        String sCurrentLine;
        String [] split;
        
//        HashMap<String, Boolean> map = new HashMap<>();
        
        ret.add(new DataSentence());
        int currIdx = 0;
        try {
            br = new BufferedReader(new FileReader(filename));
            while ((sCurrentLine = br.readLine()) != null) {
                if(sCurrentLine.length() > 0) {
                    split = sCurrentLine.split(" +");
                    ArrayList<Object> attr = new ArrayList<>();
                    attr.add((String)split[KATA]);
                    attr.add((String)split[POS]);
                    attr.add((String)(split[POSISI_TOKEN]));
                    attr.add((String)(split[DIST_KATA]));
                    attr.add((String)(split[DIST_POS]));
                    attr.add((String)(split[PATT_KATA]));
                    attr.add((String)(split[PATT_POS]));
                    attr.add((String)(split[WORD_SIM_1]));
                    attr.add((String)split[LABEL]);
                    ret.get(currIdx).add(attr);
                    
//                    if (!map.containsKey((String)split[POS])) {
//                        map.put((String)split[POS], Boolean.TRUE);
//                    }
                } else {
                    ret.add(new DataSentence());
                    currIdx++;
                }
            }
            
//            for(String s : map.keySet()) {
//                System.out.print(s+",");                
//            }
//            System.out.println();
            if (ret.get(ret.size()-1).sentence.isEmpty()) {
                ret.remove(ret.size()-1);
            }
            
            
        } catch (IOException e) {
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
            }
        }
        
        return ret;
    }
    
    public static void writeArff(ArrayList<DataSentence> sentences, String fileout) {
        StringBuilder sb = new StringBuilder();
        sb.append("@relation disfluencies_set").append(System.lineSeparator())
                                            .append(System.lineSeparator());
        sb.append("@attribute kata string").append(System.lineSeparator());
        sb.append("@attribute pos {NN,JJ,CC,CDC,IN,NNG,VBT,CDI,PRL,PRN,CDP,PRP,CDO,SC,RB,DT,NEG,NNP,FW,UH,MD,WP,VBI,RP}").append(System.lineSeparator());
        sb.append("@attribute posisi_token_norm numeric").append(System.lineSeparator());
        sb.append("@attribute dist_kata numeric").append(System.lineSeparator());
        sb.append("@attribute dist_pos numeric").append(System.lineSeparator());
        sb.append("@attribute patt_kata numeric").append(System.lineSeparator());
        sb.append("@attribute patt_pos numeric").append(System.lineSeparator());
        
        sb.append("@attribute word_next_sim numeric").append(System.lineSeparator());
        
        sb.append("@attribute prev_kata string").append(System.lineSeparator());
        sb.append("@attribute prev_pos {<s>,NN,JJ,CC,CDC,IN,NNG,VBT,CDI,PRL,PRN,CDP,PRP,CDO,SC,RB,DT,NEG,NNP,FW,UH,MD,WP,VBI,RP}").append(System.lineSeparator());        
        sb.append("@attribute prev_kata2 string").append(System.lineSeparator());
        sb.append("@attribute prev_pos2 {<s>,NN,JJ,CC,CDC,IN,NNG,VBT,CDI,PRL,PRN,CDP,PRP,CDO,SC,RB,DT,NEG,NNP,FW,UH,MD,WP,VBI,RP}").append(System.lineSeparator());
        sb.append("@attribute prev2_kata string").append(System.lineSeparator());
        sb.append("@attribute prev2_pos string").append(makePrevPairPOS()).append(System.lineSeparator());        
        sb.append("@attribute next_kata string").append(System.lineSeparator());
        sb.append("@attribute next_pos {NN,JJ,CC,CDC,IN,NNG,VBT,CDI,PRL,PRN,CDP,PRP,CDO,SC,RB,DT,NEG,NNP,FW,UH,MD,WP,VBI,RP,</s>}").append(System.lineSeparator());
        sb.append("@attribute next_kata2 string").append(System.lineSeparator());
        sb.append("@attribute next_pos2 {NN,JJ,CC,CDC,IN,NNG,VBT,CDI,PRL,PRN,CDP,PRP,CDO,SC,RB,DT,NEG,NNP,FW,UH,MD,WP,VBI,RP,</s>}").append(System.lineSeparator());
        sb.append("@attribute next2_kata string").append(System.lineSeparator());
        sb.append("@attribute next2_pos string").append(makeNextPairPOS()).append(System.lineSeparator());       
        
        sb.append("@attribute prev_label {<s>,0,FL,RC,NC,G}").append(System.lineSeparator());
        
        sb.append("@attribute label {0,FL,RC,NC,G}").append(System.lineSeparator())
                                                .append(System.lineSeparator());
        sb.append("@data").append(System.lineSeparator());
        
        for(DataSentence d : sentences) {
            for(int i=0; i<d.sentence.size(); i++) {
            
                sb.append((String)d.sentence.get(i).get(KATA)).append(",");
                sb.append((String)d.sentence.get(i).get(POS)).append(",");
                sb.append((Float)d.sentence.get(i).get(POSISI_TOKEN)).append(",");
                sb.append((Integer)d.sentence.get(i).get(DIST_KATA)).append(",");
                sb.append((Integer)d.sentence.get(i).get(DIST_POS)).append(",");
                sb.append((Integer)d.sentence.get(i).get(PATT_KATA)).append(",");
                sb.append((Integer)d.sentence.get(i).get(PATT_POS)).append(",");
                
                
                sb.append(i<d.sentence.size()-1?
                        calculateSimilarityBetween2Words((String)d.sentence.get(i).get(KATA), (String)d.sentence.get(i+1).get(KATA))
                        : 0).append(",");
                
                // Atribut - atribut prev token
                sb.append(i>0? d.sentence.get(i-1).get(KATA) : "<s>").append(",");
                sb.append(i>0? d.sentence.get(i-1).get(POS) : "<s>").append(",");
                sb.append(i>1? d.sentence.get(i-2).get(KATA) : "<s>").append(",");
                sb.append(i>1? d.sentence.get(i-2).get(POS) : "<s>").append(",");                
                sb.append("'").append(i>0? d.sentence.get(i-1).get(KATA) : "<s>").append(" ").
                           append(d.sentence.get(i).get(KATA)).append("',");
                sb.append("'").append(i>0? d.sentence.get(i-1).get(POS) : "<s>").append(" ").
                   append(d.sentence.get(i).get(POS)).append("',");
                
                
                // Atribut - atribut next token
                sb.append(i<d.sentence.size()-1? d.sentence.get(i+1).get(KATA) : "</s>").append(",");
                sb.append(i<d.sentence.size()-1? d.sentence.get(i+1).get(POS) : "</s>").append(",");
                sb.append(i<d.sentence.size()-2? d.sentence.get(i+2).get(KATA) : "</s>").append(",");
                sb.append(i<d.sentence.size()-2? d.sentence.get(i+2).get(POS) : "</s>").append(",");                
                sb.append("'").append(d.sentence.get(i).get(KATA)).append(" ").
                           append(i<d.sentence.size()-1? d.sentence.get(i+1).get(KATA) : "</s>").append("',");
                sb.append("'").append(d.sentence.get(i).get(POS)).append(" ").
                           append(i<d.sentence.size()-1? d.sentence.get(i+1).get(POS) : "</s>").append("',");
                
                // Previous label
                sb.append(i>0? d.sentence.get(i-1).get(LABEL) : "<s>").append(",");
                
                // Label
                sb.append(d.sentence.get(i).get(LABEL)).append(System.lineSeparator());
            }            
        }
        
        try {
            File file = new File(fileout);

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
    
    public static void writeArff3Label(ArrayList<DataSentence> sentences, String fileout) {
        StringBuilder sb = new StringBuilder();
        sb.append("@relation disfluencies_set").append(System.lineSeparator())
                                            .append(System.lineSeparator());
        sb.append("@attribute kata string").append(System.lineSeparator());
        sb.append("@attribute pos {NN,JJ,CC,CDC,IN,NNG,VBT,CDI,PRL,PRN,CDP,PRP,CDO,SC,RB,DT,NEG,NNP,FW,UH,MD,WP,VBI,RP}").append(System.lineSeparator());
        sb.append("@attribute posisi_token_norm numeric").append(System.lineSeparator());
        sb.append("@attribute dist_kata numeric").append(System.lineSeparator());
        sb.append("@attribute dist_pos numeric").append(System.lineSeparator());
        sb.append("@attribute patt_kata numeric").append(System.lineSeparator());
        sb.append("@attribute patt_pos numeric").append(System.lineSeparator());
        
        sb.append("@attribute word_next_sim numeric").append(System.lineSeparator());
        
        sb.append("@attribute prev_kata string").append(System.lineSeparator());
        sb.append("@attribute prev_pos {<s>,NN,JJ,CC,CDC,IN,NNG,VBT,CDI,PRL,PRN,CDP,PRP,CDO,SC,RB,DT,NEG,NNP,FW,UH,MD,WP,VBI,RP}").append(System.lineSeparator());        
        sb.append("@attribute prev_kata2 string").append(System.lineSeparator());
        sb.append("@attribute prev_pos2 {<s>,NN,JJ,CC,CDC,IN,NNG,VBT,CDI,PRL,PRN,CDP,PRP,CDO,SC,RB,DT,NEG,NNP,FW,UH,MD,WP,VBI,RP}").append(System.lineSeparator());
        sb.append("@attribute prev2_kata string").append(System.lineSeparator());
        sb.append("@attribute prev2_pos string").append(makePrevPairPOS()).append(System.lineSeparator());        
        sb.append("@attribute next_kata string").append(System.lineSeparator());
        sb.append("@attribute next_pos {NN,JJ,CC,CDC,IN,NNG,VBT,CDI,PRL,PRN,CDP,PRP,CDO,SC,RB,DT,NEG,NNP,FW,UH,MD,WP,VBI,RP,</s>}").append(System.lineSeparator());
        sb.append("@attribute next_kata2 string").append(System.lineSeparator());
        sb.append("@attribute next_pos2 {NN,JJ,CC,CDC,IN,NNG,VBT,CDI,PRL,PRN,CDP,PRP,CDO,SC,RB,DT,NEG,NNP,FW,UH,MD,WP,VBI,RP,</s>}").append(System.lineSeparator());
        sb.append("@attribute next2_kata string").append(System.lineSeparator());
        sb.append("@attribute next2_pos string").append(makeNextPairPOS()).append(System.lineSeparator());       
        
        sb.append("@attribute prev_label {<s>,0,FL,NC}").append(System.lineSeparator());
        
        sb.append("@attribute label {0,FL,NC}").append(System.lineSeparator())
                                                .append(System.lineSeparator());
        sb.append("@data").append(System.lineSeparator());
        
        for(DataSentence d : sentences) {
            for(int i=0; i<d.sentence.size(); i++) {
            
                sb.append((String)d.sentence.get(i).get(KATA)).append(",");
                sb.append((String)d.sentence.get(i).get(POS)).append(",");
                sb.append((Float)d.sentence.get(i).get(POSISI_TOKEN)).append(",");
                sb.append((Integer)d.sentence.get(i).get(DIST_KATA)).append(",");
                sb.append((Integer)d.sentence.get(i).get(DIST_POS)).append(",");
                sb.append((Integer)d.sentence.get(i).get(PATT_KATA)).append(",");
                sb.append((Integer)d.sentence.get(i).get(PATT_POS)).append(",");
                
                
                sb.append(i<d.sentence.size()-1?
                        calculateSimilarityBetween2Words((String)d.sentence.get(i).get(KATA), (String)d.sentence.get(i+1).get(KATA))
                        : 0).append(",");
                
                // Atribut - atribut prev token
                sb.append(i>0? d.sentence.get(i-1).get(KATA) : "<s>").append(",");
                sb.append(i>0? d.sentence.get(i-1).get(POS) : "<s>").append(",");
                sb.append(i>1? d.sentence.get(i-2).get(KATA) : "<s>").append(",");
                sb.append(i>1? d.sentence.get(i-2).get(POS) : "<s>").append(",");                
                sb.append("'").append(i>0? d.sentence.get(i-1).get(KATA) : "<s>").append(" ").
                           append(d.sentence.get(i).get(KATA)).append("',");
                sb.append("'").append(i>0? d.sentence.get(i-1).get(POS) : "<s>").append(" ").
                   append(d.sentence.get(i).get(POS)).append("',");
                
                
                // Atribut - atribut next token
                sb.append(i<d.sentence.size()-1? d.sentence.get(i+1).get(KATA) : "</s>").append(",");
                sb.append(i<d.sentence.size()-1? d.sentence.get(i+1).get(POS) : "</s>").append(",");
                sb.append(i<d.sentence.size()-2? d.sentence.get(i+2).get(KATA) : "</s>").append(",");
                sb.append(i<d.sentence.size()-2? d.sentence.get(i+2).get(POS) : "</s>").append(",");                
                sb.append("'").append(d.sentence.get(i).get(KATA)).append(" ").
                           append(i<d.sentence.size()-1? d.sentence.get(i+1).get(KATA) : "</s>").append("',");
                sb.append("'").append(d.sentence.get(i).get(POS)).append(" ").
                           append(i<d.sentence.size()-1? d.sentence.get(i+1).get(POS) : "</s>").append("',");
                
                // Previous label
                sb.append(i>0? d.sentence.get(i-1).get(LABEL) : "<s>").append(",");
                
                // Label
                sb.append(d.sentence.get(i).get(LABEL)).append(System.lineSeparator());
            }            
        }
        
        try {
            File file = new File(fileout);

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
    
    public static void write10FoldFiles(ArrayList<DataSentence> sentences, String rootFolder) {
        StringBuilder sb1, sb2;
        FileWriter fw1,fw2;
        BufferedWriter bw1, bw2;
        int first, numInst, offset;
        
        for (int i=0; i<10; i++) {
            File folder = new File(rootFolder+"/10Fold-"+(i+1));
            if (!folder.exists()) {
                folder.mkdir();
            }
            sb1 = new StringBuilder();
            sb2 = new StringBuilder();
            
            numInst = sentences.size() / 10;
            if (i < sentences.size() % 10) {
                numInst++;
                offset = i;
            } else {
                offset = sentences.size() % 10;
            }
            first = i * (sentences.size() / 10) + offset;
            
            for(int j=0; j<sentences.size(); j++) {                
                for(ArrayList<Object> o : sentences.get(j).sentence) {
                    if (j >= first && j<first+numInst) {
                        sb2.append((String)o.get(KATA)).append(" ");
                        sb2.append((String)o.get(POS)).append(" ");
                        sb2.append((Float)o.get(POSISI_TOKEN)).append(" ");
                        sb2.append((Integer)o.get(DIST_KATA)).append(" ");
                        sb2.append((Integer)o.get(DIST_POS)).append(" ");
                        sb2.append((Integer)o.get(PATT_KATA)).append(" ");
                        sb2.append((Integer)o.get(PATT_POS)).append(" ");
                        sb2.append((Float)o.get(WORD_SIM_1)).append(" ");
                        sb2.append((String)o.get(LABEL)).append("\n");
                    } else {
                        sb1.append((String)o.get(KATA)).append(" ");
                        sb1.append((String)o.get(POS)).append(" ");
                        sb1.append((Float)o.get(POSISI_TOKEN)).append(" ");
                        sb1.append((Integer)o.get(DIST_KATA)).append(" ");
                        sb1.append((Integer)o.get(DIST_POS)).append(" ");
                        sb1.append((Integer)o.get(PATT_KATA)).append(" ");
                        sb1.append((Integer)o.get(PATT_POS)).append(" ");
                        sb1.append((Float)o.get(WORD_SIM_1)).append(" ");
                        sb1.append((String)o.get(LABEL)).append("\n");
                    }
                }
                
                if (j >= first && j<first+numInst) {
                    sb2.append("\n");
                } else {
                    sb1.append("\n");
                }
            }
            
            try {
                File train = new File(rootFolder+"/10Fold-"+(i+1)+"/train");
                File test = new File(rootFolder+"/10Fold-"+(i+1)+"/test");

                // if file doesnt exists, then create it
                if (!train.exists()) {
                    train.createNewFile();
                }
                if (!test.exists()) {
                    test.createNewFile();
                }

                fw1 = new FileWriter(train.getAbsoluteFile());
                fw2 = new FileWriter(test.getAbsoluteFile());
                
                bw1 = new BufferedWriter(fw1);
                bw1.write(sb1.toString());
                bw1.close(); 
                
                bw2 = new BufferedWriter(fw2);
                bw2.write(sb2.toString());
                bw2.close();            
            } catch (IOException e) {
                    e.printStackTrace();
            }
        }
    }
    
    public static void write10FoldFilesNominalMode(ArrayList<DataSentence> sentences, String rootFolder) {
        StringBuilder sb1, sb2;
        FileWriter fw1,fw2;
        BufferedWriter bw1, bw2;
        int first, numInst, offset;
        
        for (int i=0; i<10; i++) {
            File folder = new File(rootFolder+"/10Fold-"+(i+1));
            if (!folder.exists()) {
                folder.mkdir();
            }
            sb1 = new StringBuilder();
            sb2 = new StringBuilder();
            
            numInst = sentences.size() / 10;
            if (i < sentences.size() % 10) {
                numInst++;
                offset = i;
            } else {
                offset = sentences.size() % 10;
            }
            first = i * (sentences.size() / 10) + offset;
            
            for(int j=0; j<sentences.size(); j++) {                
                for(ArrayList<Object> o : sentences.get(j).sentence) {
                    if (j >= first && j<first+numInst) {
                        sb2.append((String)o.get(KATA)).append(" ");
                        sb2.append((String)o.get(POS)).append(" ");
                        sb2.append((String)o.get(POSISI_TOKEN)).append(" ");
                        sb2.append((String)o.get(DIST_KATA)).append(" ");
                        sb2.append((String)o.get(DIST_POS)).append(" ");
                        sb2.append((String)o.get(PATT_KATA)).append(" ");
                        sb2.append((String)o.get(PATT_POS)).append(" ");
                        sb2.append((String)o.get(WORD_SIM_1)).append(" ");
                        sb2.append((String)o.get(LABEL)).append("\n");
                    } else {
                        sb1.append((String)o.get(KATA)).append(" ");
                        sb1.append((String)o.get(POS)).append(" ");
                        sb1.append((String)o.get(POSISI_TOKEN)).append(" ");
                        sb1.append((String)o.get(DIST_KATA)).append(" ");
                        sb1.append((String)o.get(DIST_POS)).append(" ");
                        sb1.append((String)o.get(PATT_KATA)).append(" ");
                        sb1.append((String)o.get(PATT_POS)).append(" ");
                        sb1.append((String)o.get(WORD_SIM_1)).append(" ");
                        sb1.append((String)o.get(LABEL)).append("\n");
                    }
                }
                
                if (j >= first && j<first+numInst) {
                    sb2.append("\n");
                } else {
                    sb1.append("\n");
                }
            }
            
            try {
                File train = new File(rootFolder+"/10Fold-"+(i+1)+"/train");
                File test = new File(rootFolder+"/10Fold-"+(i+1)+"/test");

                // if file doesnt exists, then create it
                if (!train.exists()) {
                    train.createNewFile();
                }
                if (!test.exists()) {
                    test.createNewFile();
                }

                fw1 = new FileWriter(train.getAbsoluteFile());
                fw2 = new FileWriter(test.getAbsoluteFile());
                
                bw1 = new BufferedWriter(fw1);
                bw1.write(sb1.toString());
                bw1.close(); 
                
                bw2 = new BufferedWriter(fw2);
                bw2.write(sb2.toString());
                bw2.close();            
            } catch (IOException e) {
                    e.printStackTrace();
            }
        }
    }
    
    public static String makePrevPairPOS() {        
        StringBuilder sb = new StringBuilder();
        
        sb.append("{");
        
        for (int i=0; i<listPOS.length; i++) {
            sb.append("'<s> ").append(listPOS[i]).append("',");
            for (int j=0; j<listPOS.length; j++) {
                sb.append("'").append(listPOS[i]).append(" ").append(listPOS[j]).append("'");
                if (i<listPOS.length-1 || j < listPOS.length-1) sb.append(",");
            }
        }
        
        sb.append("}");
        
        return sb.toString();
    }
    
    public static String makeNextPairPOS() {        
        StringBuilder sb = new StringBuilder();
        
        sb.append("{");
        
        for (int i=0; i<listPOS.length; i++) {            
            for (String s : listPOS) {
                sb.append("'").append(listPOS[i]).append(" ").append(s).append("'").append(",");
            }
            sb.append("'").append(listPOS[i]).append(" </s>'");
            if (i < listPOS.length-1) sb.append(",");
        }
        
        sb.append("}");
        
        return sb.toString();
    }
   
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
    
    public static void split10Fold(String filein, String filename, String rootFolder) {
        StringBuilder sb;
        FileWriter fw;
        BufferedWriter bw;
        BufferedReader br = null;
        String sCurrentLine;
        int first, numInst, offset;
        ArrayList<String> sentences = new ArrayList<>();
        
        // Baca file
        try {
            br = new BufferedReader(new FileReader(filein));
            while ((sCurrentLine = br.readLine()) != null) {                
                sentences.add(sCurrentLine);                
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
        System.out.println("Banyak kalimat : " + sentences.size());
        
        // Tulis 10 Fold
        for (int i=0; i<10; i++) {
            File folder = new File(rootFolder+"/10Fold-"+(i+1));
            if (!folder.exists()) {
                folder.mkdir();
            }
            sb = new StringBuilder();
            
            numInst = sentences.size() / 10;
            if (i < sentences.size() % 10) {
                numInst++;
                offset = i;
            } else {
                offset = sentences.size() % 10;
            }
            first = i * (sentences.size() / 10) + offset;
            
            for(int j=first; j<first+numInst; j++) {
                sb.append(sentences.get(j)).append("\n");
            }
            
            try {
                File foldfile = new File(rootFolder+"/10Fold-"+(i+1)+"/"+filename+"-"+(i+1));

                // if file doesnt exists, then create it
                if (!foldfile.exists()) {
                    foldfile.createNewFile();
                }
                fw = new FileWriter(foldfile.getAbsoluteFile());
                bw = new BufferedWriter(fw);
                bw.write(sb.toString());
                bw.close();
            } catch (IOException e) {
                    e.printStackTrace();
            }
        }
    }
    
    
    
    public static void main(String args[]) {
        String filein = "/home/khaidzir/TA/ProyekTA/Eksperimen/Dataset/annotated_3label.txt";        
        ArrayList<DataSentence> sentences = readFileNominalMode(filein);
        String fold = "/home/khaidzir/TA/ProyekTA/Eksperimen/10Fold/3Label/10Fold_o";
        write10FoldFilesNominalMode(sentences, fold);
        System.out.println("Banyak kalimat = " + sentences.size());
        

//        for(int i=1; i<=10; i++) {
//            String filein = "/home/khaidzir/TA/ProyekTA/Others/new label/10Fold_new/10Fold-"+i+"/test";
//            String fileout = "/home/khaidzir/TA/ProyekTA/Others/new label/10Fold_new/10Fold-"+i+"/annotated_flnc";
//            ArrayList<DataSentence> sentences = readFile(filein);
//            StringBuilder sb;
//            FileWriter fw;
//            BufferedWriter bw;
//            sb = new StringBuilder();
//
//            for(int j=0; j<sentences.size(); j++) {                
//                for(ArrayList<Object> o : sentences.get(j).sentence) {                    
//                    sb.append((String)o.get(KATA)).append(" ");
//                    sb.append((String)o.get(POS)).append(" ");
//                    sb.append((Float)o.get(POSISI_TOKEN)).append(" ");
//                    sb.append((Integer)o.get(DIST_KATA)).append(" ");
//                    sb.append((Integer)o.get(DIST_POS)).append(" ");
//                    sb.append((Integer)o.get(PATT_KATA)).append(" ");
//                    sb.append((Integer)o.get(PATT_POS)).append(" ");
//                    sb.append((Float)o.get(WORD_SIM)).append(" ");
//                    sb.append("<s>").append(" ");
//                    sb.append((String)o.get(LABEL)).append("\n");
//                }
//                
//                sb.append("\n");
//            }
//
//            try {
//                File flnc = new File(fileout);
//
//                // if file doesnt exists, then create it
//                if (!flnc.exists()) {
//                    flnc.createNewFile();
//                }
//
//                fw = new FileWriter(flnc.getAbsoluteFile());
//
//                bw = new BufferedWriter(fw);
//                bw.write(sb.toString());
//                bw.close(); 
//            } catch (IOException e) {
//            }
//            
//        }


//        String filein = "/home/khaidzir/TA/ProyekTA/Others/Korpus/Newest/daftar_kalimat_newest.txt";
//        String fold = "/home/khaidzir/TA/ProyekTA/Others/new label/10Fold";
//        split10Fold(filein, "test", fold);
    }
    
    
}
