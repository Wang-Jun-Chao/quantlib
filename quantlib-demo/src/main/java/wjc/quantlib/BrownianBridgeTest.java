package wjc.quantlib;

import com.google.common.collect.Lists;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.junit.Test;
import org.quantlib.BrownianBridge;
import org.quantlib.DoubleVector;
import wjc.quantlib.chart.LineChartApp2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: wangjunchao(王俊超)
 * @time: 2019-05-21 11:15
 **/
public class BrownianBridgeTest {
    private void printBridge(DoubleVector list) {
        double cumsum = 0;
        for (int i = 0; i < list.size(); i += 2) {
            cumsum += list.get(i);
            System.out.println(String.format("%-5d%-+30.20f%-+30.20f", (i + 1), list.get(i), cumsum));
        }
    }

    @Test
    public void brownianBridgeTest() {
        final long n = 500;


        DoubleVector input = new DoubleVector(n);

        // This is the key fact: the first variate in the input sequency is
        // used to construct a global step. If you want the usual Brownian
        // bridge, you need to set this to zero.
        input.add(0.0);

        // Generate the rest of the variates as standard Gaussian random
        // values
        NormalDistribution generator = new NormalDistribution();
        for (int i = 1; i < n; i++) {
            input.add(generator.sample());
        }

        BrownianBridge bridge = new BrownianBridge(input.size());
        DoubleVector output = bridge.transform(input);

        printBridge(output);

        List<Double> list = new ArrayList<>((int) output.size());
        List<Double> list2 = new ArrayList<>(list.size());
        double sum = 0;
        double min = 0;
        double max = 0;

        for (int i = 0; i < output.size(); i += 2) {
            list.add(output.get(i));
            sum += output.get(i);
            list2.add(sum);

            if (sum < min) {
                min = sum - 1;
            }
            if (sum > max) {
                max = sum + 1;
            }
        }

        LineChartApp2.AxisParam xAxisParam = new LineChartApp2.AxisParam("t", 0, list.size(), list.size() / 20);
        LineChartApp2.AxisParam yAxisParam = new LineChartApp2.AxisParam("B(t)", min, max, (max - min) / 20);

        LineChartApp2.draw(xAxisParam, yAxisParam,
                Lists.newArrayList("Brownian Bridge", "Gaussian Random"),
                Lists.newArrayList(list, list2));
    }
}
