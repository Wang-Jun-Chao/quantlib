package wjc.quantlib.solver;

import org.apache.commons.math3.analysis.FunctionUtils;
import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math3.analysis.function.Gaussian;
import org.apache.commons.math3.analysis.function.Sin;
import org.apache.commons.math3.analysis.solvers.NewtonRaphsonSolver;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.util.FastMath;

/**
 * TODO
 * @author: wangjunchao(王俊超)
 * @time: 2019-05-15 16:35
 **/
public class NewtonSolverDemo {

    public static void main(String[] args) {
        testSolver2();
    }

    public static void testSolver2() {

        // setup of  market parameters
        double spot = 100.0;
        double strike = 110.0;
        double rd = 0.002;
        double rf = 0.01;
        double tau = 0.5;
        double phi = 1;
        double vol = 0.1423;

        // calculate corresponding Black Scholes price
        double price = SolverDemo.blackScholesPrice(spot, strike, rd, rf, vol, tau, phi);
        BlackScholesClass solvProblem = new BlackScholesClass(spot, strike, rd, rf, tau, phi, price);


        NewtonRaphsonSolver mySolv = new NewtonRaphsonSolver();
//        double solve = mySolv.solve(3, new Gaussian(), 1.0, 4.0, 1);
//        System.out.println(solve);

        Gaussian f = new Gaussian();
        double result;

        NewtonRaphsonSolver solver = new NewtonRaphsonSolver();
        result = solver.solve(0, f, 3.0, 4.0);
        System.out.println(result);
        result = solver.solve(3, f, 1, 4);
        System.out.println(result);


//        double accuracy = 0.00001;
//        double guess = 0.10;
//        double step = 0.001;
//
//        mySolv.
//
//                res = mySolv.solve(solvProblem, accuracy, guess, step);
//
//        print('{0:<20}{1}'.format('Input Volatility:', vol))
//
//        print('{0:<20}{1}'.format('Implied Volatility:', res))
    }

    public static class BlackScholesClass {
        private double             spot;
        private double             strike;
        private double             rd;
        private double             rf;
        private double             tau;
        private double             phi;
        private double             price;
        private double             sqrtTau;
        private NormalDistribution norm = new NormalDistribution();
        private double             domDf;
        private double             forDf;
        private double             fwd;
        private double             logFwd;

        public BlackScholesClass(double spot, double strike, double rd, double rf,
                                 double tau, double phi, double price) {
            this.spot = spot;
            this.strike = strike;
            this.rd = rd;
            this.rf = rf;
            this.phi = phi;
            this.tau = tau;
            this.price = price;
            this.sqrtTau = Math.sqrt(tau);
            this.domDf = Math.exp(-this.rd * this.tau);
            this.forDf = Math.exp(-this.rf * this.tau);
            this.fwd = this.spot * this.forDf / this.domDf;
            this.logFwd = Math.log(this.fwd / this.strike);
        }

        public double blackScholesPrice(double spot, double strike, double rd, double rf,
                                        double vol, double tau, double phi) {
            domDf = Math.exp(-rd * tau);
            forDf = Math.exp(-rf * tau);
            fwd = spot * forDf / domDf;
            double stdDev = vol * Math.sqrt(tau);

            double dp = (Math.log(fwd / strike) + 0.5 * stdDev * stdDev) / stdDev;
            double dm = (Math.log(fwd / strike) - 0.5 * stdDev * stdDev) / stdDev;

            return phi * domDf * (fwd * norm.cumulativeProbability(phi * dp)
                    - strike * norm.cumulativeProbability(phi * dm));
        }

        public double impliedVolProblem(double spot, double strike, double rd, double rf,
                                        double vol, double tau, double phi, double price) {
            return this.blackScholesPrice(spot, strike, rd, rf, vol, tau, phi) - price;
        }

        public double call(double x) {
            return this.impliedVolProblem(spot, strike, rd, rf, x, tau, phi, price);
        }

        public double derivative(double x) {
            // vega
            double stdDev = x * this.sqrtTau;
            double dp = (this.logFwd + 0.5 * stdDev * stdDev) / stdDev;
            return this.spot * this.forDf * norm.density(dp) * this.sqrtTau;
        }
    }

}
