/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tahelper;

import java.util.ArrayList;

/**
 *
 * @author khaidzir
 */
public class DataSentence {
    
    public static final int KATA            = 0;
    public static final int POS             = 1;
    public static final int POSISI_TOKEN    = 2;
    public static final int DIST_KATA       = 3;
    public static final int DIST_POS        = 4;
    public static final int PATT_KATA       = 5;
    public static final int PATT_POS        = 6;
    public static final int WORD_SIM_1      = 7;
    public static final int WORD_SIM_2      = 8;
    public static final int WORD_SIM_3      = 9;
    public static final int DIST_WORD_SIM   = 10;
    public static final int PARTIAL_WORD_1  = 11;
    public static final int PARTIAL_WORD_2  = 12;
    public static final int PARTIAL_WORD_3  = 13;
    public static final int DIST_PARTIAL_WORD   = 14;
    public static final int LABEL           = 15;
    
    ArrayList<ArrayList<Object>> sentence;
    
    public DataSentence() {
        sentence = new ArrayList<>();        
    }
    
    public void add(ArrayList<Object> data) {
        sentence.add(data);
    }
    
}
