package wjc.quantlib.integration;

import org.apache.commons.math3.distribution.AbstractRealDistribution;
import org.apache.commons.math3.distribution.LogNormalDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.quantlib.GaussChebyshev2ndIntegration;
import org.quantlib.GaussChebyshevIntegration;
import org.quantlib.GaussHermiteIntegration;
import org.quantlib.GaussKronrodAdaptive;
import org.quantlib.GaussKronrodNonAdaptive;
import org.quantlib.GaussLaguerreIntegration;
import org.quantlib.GaussLobattoIntegral;
import org.quantlib.SimpsonIntegral;
import org.quantlib.TrapezoidIntegralMidPoint;
import org.quantlib.UnaryFunctionDelegate;

/**
 * @author: wangjunchao(王俊超)
 * @time: 2019-05-15 14:14
 **/
public class NumericalIntegration {

    public static void main(String[] args) {
//        testIntegration1();
//        testIntegration2();
        testIntegration3();
//        testIntegration4();
    }


    public static class Function {
        private AbstractRealDistribution dist;
        private double                   t1;
        private double                   t2;

        public Function(AbstractRealDistribution dist, double a, double b) {
            this.dist = dist;
            this.t1 = 0.5 * (b - a);
            this.t2 = 0.5 * (b + a);
        }

        public double calculate(double x) {
            return t1 * dist.density(t1 * x + t2);
        }
    }

    public static void testIntegration3() {
        double a = -1.96;
        double b = 1.96;

        GaussChebyshevIntegration gChebInt = new GaussChebyshevIntegration(64);

        NormalDistribution norm = new NormalDistribution();
        double analytical = norm.cumulativeProbability(b) - norm.cumulativeProbability(a);
        Function f = new Function(norm, a, b);

        System.out.println(String.format("%-30s%.20f", "Analytical:", analytical));
        System.out.println(String.format("%-30s%.20f", "Chebyshev:",
                gChebInt.calculate(new UnaryFunctionDelegate(){
                    @Override
                    public double value(double x) {
                        return f.calculate(x);
                    }
                })));
    }

    public static void testIntegration2() {
        // [0, +infinity]
        GaussLaguerreIntegration gLagInt = new GaussLaguerreIntegration(16);
        // [-infinity, +infinity]
        GaussHermiteIntegration gHerInt = new GaussHermiteIntegration(16);
        // (-1, 1)
        GaussChebyshevIntegration gChebInt = new GaussChebyshevIntegration(64);
        // (-1, 1)
        GaussChebyshev2ndIntegration gChebInt2 = new GaussChebyshev2ndIntegration(64);

        NormalDistribution norm = new NormalDistribution();

        double analytical = norm.cumulativeProbability(1) - norm.cumulativeProbability(-1);

        System.out.println(String.format("%-30s%.20f", "Laguerre:",
                gLagInt.calculate(new UnaryFunctionDelegate() {
                    @Override
                    public double value(double x) {
                        return norm.density(x);
                    }
                })));
        System.out.println(String.format("%-30s%.20f", "Hermite:",
                gHerInt.calculate(new UnaryFunctionDelegate() {
                    @Override
                    public double value(double x) {
                        return norm.density(x);
                    }
                })));
        System.out.println(String.format("%-30s%.20f", "Analytical:", analytical));
        System.out.println(String.format("%-30s%.20f", "Cheb:",
                gChebInt.calculate(new UnaryFunctionDelegate() {
                    @Override
                    public double value(double x) {
                        return norm.density(x);
                    }
                })));
        System.out.println(String.format("%-30s%.20f", "Cheb 2 kind:",
                gChebInt2.calculate(new UnaryFunctionDelegate() {
                    @Override
                    public double value(double x) {
                        return norm.density(x);
                    }
                })));
    }

    public static class CallFunction {

        private double strike;
        private double r;
        private double tau;

        private LogNormalDistribution lnd;

        public CallFunction(double spot, double strike, double r, double vol, double tau) {
            this.strike = strike;
            this.r = r;
            this.tau = tau;

            double mean = Math.log(spot) + (r - 0.5 * Math.pow(vol, 2)) * tau;
            double stdDev = vol * Math.sqrt(tau);

            // TODO 这个类还没有搞懂
            lnd = new LogNormalDistribution(stdDev, Math.exp(mean));
        }

        public double calculate(double x) {
            return (x - strike) * lnd.density(x) * Math.exp((-r) * tau);
        }

    }

    public static void testIntegration4() {
        double spot = 100.0;
        double r = 0.03;
        double tau = 0.5;
        double vol = 0.20;
        double strike = 110.0;

        double a = strike;
        double b = strike * 10.0;

        CallFunction ptrF = new CallFunction(spot, strike, r, vol, tau);

        double absAcc = 0.00001;
        long maxEval = 1000;
        SimpsonIntegral numInt = new SimpsonIntegral(absAcc, maxEval);

        System.out.println("Call Value: " + numInt.calculate(new UnaryFunctionDelegate() {
            @Override
            public double value(double x) {
                return ptrF.calculate(x);
            }
        }, a, b));
    }

    public static void testIntegration1() {
        double absAcc = 0.00001;
        long maxEval = 1000;

        double a = 0.0;
        double b = Math.PI;

        TrapezoidIntegralMidPoint numInt1 = new TrapezoidIntegralMidPoint(absAcc, maxEval);
        SimpsonIntegral numInt2 = new SimpsonIntegral(absAcc, maxEval);
        GaussLobattoIntegral numInt3 = new GaussLobattoIntegral(maxEval, absAcc);
        GaussKronrodAdaptive numInt4 = new GaussKronrodAdaptive(absAcc, maxEval);
        GaussKronrodNonAdaptive numInt5 = new GaussKronrodNonAdaptive(absAcc, maxEval, absAcc);

        NormalDistribution norm = new NormalDistribution();

        double analytical = norm.cumulativeProbability(b) - norm.cumulativeProbability(a);

        System.out.println(String.format("%-30s%.20f", "Analytical:", analytical));
        System.out.println(String.format("%-30s%.20f", "Midpoint Trapezoidal:",
                numInt1.calculate(new UnaryFunctionDelegate() {
                    @Override
                    public double value(double x) {
                        return norm.density(x);
                    }
                }, a, b)));
        System.out.println(String.format("%-30s%.20f", "Simpson:",
                numInt2.calculate(new UnaryFunctionDelegate() {
                    @Override
                    public double value(double x) {
                        return norm.density(x);
                    }
                }, a, b)));
        System.out.println(String.format("%-30s%.20f", "Gauss Lobatto:",
                numInt3.calculate(new UnaryFunctionDelegate() {
                    @Override
                    public double value(double x) {
                        return norm.density(x);
                    }
                }, a, b)));
        System.out.println(String.format("%-30s%.20f", "Gauss Kronrod Adpt:",
                numInt4.calculate(new UnaryFunctionDelegate() {
                    @Override
                    public double value(double x) {
                        return norm.density(x);
                    }
                }, a, b)));
        System.out.println(String.format("%-30s%.20f", "Gauss Kronrod Non Adpt:",
                numInt5.calculate(new UnaryFunctionDelegate() {
                    @Override
                    public double value(double x) {
                        return norm.density(x);
                    }
                }, a, b)));

    }
}
