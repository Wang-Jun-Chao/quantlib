package wjc.quantlib.solver;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.quantlib.Bisection;
import org.quantlib.Brent;
import org.quantlib.Ridder;
import org.quantlib.UnaryFunctionDelegate;

/**
 * @author: wangjunchao(王俊超)
 * @time: 2019-05-15 16:13
 **/
public class SolverDemo {
    public static void main(String[] args) {
        testSolver1();
    }

    // 非 Newton 算法（不需要导数）
    private static void testSolver1() {
        // setup of market parameters
        double spot = 100.0;
        double strike = 110.0;
        double rd = 0.002;
        double rf = 0.01;
        double tau = 0.5;
        double phi = 1;
        double vol = 0.1423;

        // calculate corresponding Black Scholes price
        double price = blackScholesPrice(spot, strike, rd, rf, vol, tau, phi);

        // setup a solver
        Bisection mySolv1 = new Bisection();
        Brent mySolv2 = new Brent();
        Ridder mySolv3 = new Ridder();

        double  accuracy = 0.00001;
        double  guess = 0.25;

        double  min = 0.0;
        double  max = 1.0;


        double res1 = mySolv1.solve(new UnaryFunctionDelegate(){
            @Override
            public double value(double x) {
                return impliedVolProblem(spot, strike, rd, rf, x, tau, phi, price);
            }
        }, accuracy, guess, min, max);
        double res2 = mySolv2.solve(new UnaryFunctionDelegate(){
            @Override
            public double value(double x) {
                return impliedVolProblem(spot, strike, rd, rf, x, tau, phi, price);
            }
        }, accuracy, guess, min, max);
        double res3 = mySolv3.solve(new UnaryFunctionDelegate(){
            @Override
            public double value(double x) {
                return impliedVolProblem(spot, strike, rd, rf, x, tau, phi, price);
            }
        }, accuracy, guess, min, max);

        System.out.println(String.format("%-30s%.20f", "Input Volatility:", vol));
        System.out.println(String.format("%-30s%.20f", "Implied Volatility Bisection:", res1));
        System.out.println(String.format("%-30s%.20f", "Implied Volatility Brent:", res2));
        System.out.println(String.format("%-30s%.20f", "Implied Volatility Ridder:", res3));
    }

    public static double blackScholesPrice(double spot, double strike, double rd,
                                           double rf, double vol, double tau, double phi) {
        double domDf = Math.exp(-rd * tau);
        double forDf = Math.exp(-rf * tau);
        double fwd = spot * forDf / domDf;
        double stdDev = vol * Math.sqrt(tau);

        double dp = (Math.log(fwd / strike) + 0.5 * Math.pow(stdDev, 2)) / stdDev;
        double dm = (Math.log(fwd / strike) - 0.5 * Math.pow(stdDev, 2)) / stdDev;

        NormalDistribution norm = new NormalDistribution();

        return phi * domDf * (fwd * norm.cumulativeProbability(phi * dp) -
                strike * norm.cumulativeProbability(phi * dm));
    }

    public static double impliedVolProblem(double spot, double strike, double rd, double rf,
                                           double vol, double tau, double phi, double price) {
        return blackScholesPrice(spot, strike, rd, rf, vol, tau, phi) - price;
    }
}
