/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jubas;
import java.io.*;
import java.net.UnknownHostException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.msgpack.rpc.*;
import org.msgpack.rpc.error.TransportError;
import us.jubat.classifier.ClassifierClient;
import us.jubat.classifier.EstimateResult;
import us.jubat.classifier.LabeledDatum;
import us.jubat.common.Datum;
/**
 *
 * @author root
 */
public class jubaclnt {
    private static Datum makeDatum(String key,double value) {
        //Datum().addNumber();
        return new Datum().addNumber(key, value);
    }
    private static ClassifierClient openClient() throws UnknownHostException {
        String host = "192.168.2.8";
        int port = 9199;
        String name = "test";
        int k=1;
        ClassifierClient client = new ClassifierClient(host, port, name, 10);
        return client;
    }
    public static String getMaxScore( List<EstimateResult> res)
    {
        double max = 0;
        String lb = null;
        for (EstimateResult r : res) {
            System.out.printf("%s %f\n", r.label, r.score);
            if(r.score>max)
            {
                max=r.score;
                lb=r.label;
            }
        }
        return lb;
    }
    public static void prtFile(String type,double  value,PrintWriter trd)
    {
        
    
    }
    public static List<Datum> clfy(String filename) throws FileNotFoundException, IOException
    {
        List<Datum> clData;
        clData = new ArrayList();
        //
        FileReader frdc =new FileReader("class.dat");
        BufferedReader drc =new BufferedReader(frdc);
        String lne;
        String[] elem;
        String[] elemval1 = null;
        String[] elemval2 = null;
        List<Datum> testData2= new ArrayList();
        List<String> keppData2=new ArrayList();
        PrintWriter trd = new PrintWriter("datckBf.csv", "UTF-8");
        while ((lne = drc.readLine()) != null) {
        //lne = drc.readLine();
        elem=lne.split(",");
        //System.out.println(elem[0]);
        elemval1=elem[1].split(":");
        elemval2=elem[3].split(":");
        
        //elemval[1] [0].replaceAll("\"", ""), elemval[1] [1].replaceAll("\"", "")
        //System.out.println(k);
        //System.out.println(elemval2 [0].replaceAll("\"", ""));
        
        if(Double.parseDouble(elemval2 [1])!=0)
                //&&(!elemval1 [1].contains("light")))
        {
            //System.out.println(elemval2 [0]);
            keppData2.add(elemval1 [1]);
            testData2.add(makeDatum(elemval2 [0].replaceAll("\"", ""), Double.parseDouble(elemval2 [1])));
        ///trainData.add(makeTrainDatum(elemval1 [1],elemval2 [0].replaceAll("\"", ""), Double.parseDouble(elemval2 [1])));
        clData.add(makeDatum("value", Double.parseDouble(elemval2 [1])));
        if(elemval1 [1].contentEquals("\"\""))
                {trd.println("\"tmp\"");}
        else
                {trd.println(elemval1 [1]);}
        }
        //k++;
        }
        drc.close();
        frdc.close();
        trd.close();
        //
        return clData;
    }
    public static void main(String[] args) throws UnknownHostException, IOException
    {
    //List<?> clData;
    //clData = new ArrayList();
    Datum[] testData = { //
        makeDatum("value",230)};
    List<Datum> dats = clfy("class.dat");
    ClassifierClient client= openClient();
 
        //List<List<EstimateResult>> results = client.classify(Arrays.asList(testData));
        List<List<EstimateResult>> results = client.classify(dats);
        PrintWriter trd = new PrintWriter("datckAft.csv", "UTF-8");
        for (List<EstimateResult> result : results) {
            for (EstimateResult r : result) {
                System.out.println(r.label);
                System.out.println(r.score);
                System.out.printf("%s %f\n", r.label, r.score);
            }
            System.out.println(result);
            //System.out.println(getMaxScore(result));
            trd.println(getMaxScore(result));
            //trd.println();
            System.out.println();
        }
        trd.close();
        List<String> labels = client.getLabels();
        for (String label  : labels ) {
            
                System.out.printf(label);
           
            System.out.println();
        }
        Map<String, Map<String, String>> tsf = client.getStatus();
        Collection<Map<String, String>> gf = tsf.values();
        System.out.println(tsf);
        System.out.println(gf);
        System.exit(0);
    }
    
}
