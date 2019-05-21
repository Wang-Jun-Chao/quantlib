package wjc.quantlib.optimizer;

import org.apache.commons.math3.distribution.NormalDistribution;

/**
 * https://www.cnblogs.com/xuruilong100/p/9919091.html
 *
 * @author: wangjunchao(王俊超)
 * @time: 2019-05-17 08:33
 **/
public class CallProblemFunction {
    private final static NormalDistribution NORM = new NormalDistribution();
    private              double             rd;
    private              double             rf;
    private              double             tau;
    private              double             phi;
    private final static int                S    = 4;
    private final        double[]           k;
    private final        double[]           c;


    public CallProblemFunction(double rd, double rf, double tau, double phi,
                               double[] k, double[] c) {
        this.rd = rd;
        this.rf = rf;
        this.tau = tau;
        this.phi = phi;
        this.k = k;
        this.c = c;

    }

    public static double blackScholesPrice(double spot, double strike, double rd, double rf,
                                           double vol, double tau, double phi) {
        double domDf = Math.exp(-rd * tau);
        double forDf = Math.exp(-rf * tau);
        double fwd = spot * forDf / domDf;
        double stdDev = vol * Math.sqrt(tau);

        double dp = (Math.log(fwd / strike) + 0.5 * Math.pow(stdDev, 2)) / stdDev;
        double dm = (Math.log(fwd / strike) - 0.5 * Math.pow(stdDev, 2)) / stdDev;

        return phi * domDf * (fwd * NORM.cumulativeProbability(phi * dp)
                - strike * NORM.cumulativeProbability(phi * dm));
    }

    public double calculate(double x0, double x1) {

        double result = 0;

        for (int i = 0; i < S; i++) {
            result += Math.pow(blackScholesPrice(x0, k[i], rd, rf, x1, tau, phi) - c[i], 2);
        }

        return result;
    }

}
