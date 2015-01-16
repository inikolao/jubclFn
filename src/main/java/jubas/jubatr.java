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
public class jubatr {
    private static Datum makeDatum(String key,double value) {
        return new Datum().addNumber(key, value);
    }
    private static LabeledDatum makeTrainDatum(String label, String key, double value) {
        return new LabeledDatum(label, makeDatum(key,value));
    }
    private static ClassifierClient openClient() throws UnknownHostException {
        String host = "192.168.2.8";
        int port = 9199;
        String name = "test";
        int k=1;
        ClassifierClient client = new ClassifierClient(host, port, name, 10);
        return client;
    }
    public static List<LabeledDatum> train(String filename) throws FileNotFoundException, IOException
    {
        List<LabeledDatum> trainData;
        trainData = new ArrayList();
        //
        FileReader frdc =new FileReader("train.dat");
        BufferedReader drc =new BufferedReader(frdc);
        String lne;
        String[] elem;
        String[] elemval1 = null;
        String[] elemval2 = null;
        List<Datum> testData2= new ArrayList();
        List<String> keppData2=new ArrayList();
        while ((lne = drc.readLine()) != null) {
        //lne = drc.readLine();
        elem=lne.split(",");
        //System.out.println(elem[0]);
        elemval1=elem[1].split(":");
        elemval2=elem[3].split(":");
        
        //elemval[1] [0].replaceAll("\"", ""), elemval[1] [1].replaceAll("\"", "")
        //System.out.println(k);
        System.out.println(elemval2 [0].replaceAll("\"", ""));
        if(Double.parseDouble(elemval2 [1])!=0)
                //&&(!elemval1 [1].contains("light")))
        {
            //System.out.println(elemval2 [0]);
            keppData2.add(elemval1 [1]);
            testData2.add(makeDatum(elemval2 [0].replaceAll("\"", ""), Double.parseDouble(elemval2 [1])));
        ///trainData.add(makeTrainDatum(elemval1 [1],elemval2 [0].replaceAll("\"", ""), Double.parseDouble(elemval2 [1])));
        trainData.add(makeTrainDatum(elemval1 [1],"value", Double.parseDouble(elemval2 [1])));
        }
        //k++;
        }
        drc.close();
        frdc.close();
        //
        return trainData;
    }
    public static void main(String[] args)
    {
        try {
            ClassifierClient clinet = openClient();
            List<LabeledDatum> dats = train("train.dat");
            //LabeledDatum makeTrainDatum(String label, String key, double value);
             LabeledDatum[] dat2= { //
                //makeTrainDatum("cur", "value", 872),
                //makeTrainDatum( "light", "value", 2.097575),
                //makeTrainDatum("tmp", "value", 25),
                //makeTrainDatum( "cur", "value", 654),
                //makeTrainDatum("light", "value", 1.0842),
                //makeTrainDatum("tmp", "value", 20),
                // makeTrainDatum("male", "short", "jacket", "jeans", 1.76),
                // makeTrainDatum("female", "long", "sweater", "skirt", 1.52),
                };
//List<LabeledDatum> dats2=
            clinet.train(dats);
             //clinet.train(Arrays.asList(dat2));
        } catch (UnknownHostException ex) {
            Logger.getLogger(jubatr.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(jubatr.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }
}
