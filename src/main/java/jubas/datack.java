/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jubas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class datack {
    public static void check() throws Exception
    {
        List<Number> d1 =new ArrayList();
        List<Number> d2 =new ArrayList();
        List<Number> d3 =new ArrayList();
    //PrintWriter trd = new PrintWriter("datck.csv", "UTF-8");
        FileReader frd =new FileReader("train.dat");
        BufferedReader dr =new BufferedReader(frd);
        //String lne =dr.readLine();
        String lne= null;
        String[] split3=null;
        String[] slt=null;
        String[] slt2=null;
        String jh;
        while ((lne = dr.readLine()) != null) {
        split3 =lne.split(",");
        slt=split3 [1].split(":");
        slt2=split3 [3].split(":");
        //jh=slt[1]+","+slt2[1];
        if(slt[1].contains("cur"))
        {
            d1.add(Double.parseDouble(slt2[1]));
        }
        if(slt[1].contains("tmp"))
        {
            d2.add(Double.parseDouble(slt2[1]));
        }
        if(slt[1].contains("light"))
        {
            d3.add(Double.parseDouble(slt2[1]));
        }
        }
        frd.close();
        //trd.close();
        dr.close();
        PrintWriter trd = new PrintWriter("datck.csv", "UTF-8");
        trd.println("Cur,"+d1);
        trd.println("Tmp,"+d2);
        trd.println("Light,"+d3);
        trd.close();
    
    }
    public static void main(String[] args)
    {
        try {
            check();
        } catch (Exception ex) {
            Logger.getLogger(datack.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
}
