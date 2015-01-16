/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jubas;

import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import static jubas.coreback.clasy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import us.jubat.classifier.EstimateResult;


/**
 *
 * @author root
 */
@RestController
public class controler {
 
    private static final String template1 = "light:, %s";
    private static final String template2 = "tmp:, %s";
    private static final String template3 = "cur:, %s";
    private final AtomicLong counter = new AtomicLong();
    private List<List<EstimateResult>> results;
    @RequestMapping("/jubaform")
    public coreback jubaform(@RequestParam(value="value", defaultValue="0") double value) throws UnknownHostException {
        double [] value1=new double[3];
        int k=0;
        results=clasy(value);
        for (List<EstimateResult> result : results) {
            for (EstimateResult r : result) {
                System.out.printf("%s %f\n", r.label, r.score);
                value1[k]= r.score;
                k++;
            }
             
        }
        return new coreback(counter.incrementAndGet(),
                            String.format(template1, value1[0]),String.format(template2, value1[1]),String.format(template3, value1[2]));
    }
 
    
}
