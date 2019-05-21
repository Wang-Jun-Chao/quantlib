package wjc.quantlib.optimizer;

import org.junit.Test;
import org.quantlib.Array;
import org.quantlib.BFGS;
import org.quantlib.ConjugateGradient;
import org.quantlib.CostFunctionDelegate;
import org.quantlib.EndCriteria;
import org.quantlib.NoConstraint;
import org.quantlib.Optimizer;
import org.quantlib.Simplex;

/**
 * @author: wangjunchao(王俊超)
 * @time: 2019-05-16 09:30
 **/
public class OptimizerTest {

    @Test
    public void testOptimizer1() {
        int maxIterations = 1000;
        int minStatIterations = 100;
        double rootEpsilon = 1e-8;
        double functionEpsilon = 1e-9;
        double gradientNormEpsilon = 1e-5;

        EndCriteria myEndCrit = new EndCriteria(
                maxIterations,
                minStatIterations,
                rootEpsilon,
                functionEpsilon,
                gradientNormEpsilon);

        NoConstraint constraint = new NoConstraint();

        Simplex solver1 = new Simplex(0.1);
        ConjugateGradient solver2 = new ConjugateGradient();

        Optimizer minimize = new Optimizer();
        RosenBrockFunction rosenBrockFunction = new RosenBrockFunction();
        Array array = new Array(2, 0.1);
        Array min1 = minimize.solve(new CostFunctionDelegate() {
            @Override
            public double value(Array x) {
                return rosenBrockFunction.calculate(x.get(0), x.get(1));
            }
        }, constraint, solver1, myEndCrit, array);

        Array min2 = minimize.solve(new CostFunctionDelegate() {
            @Override
            public double value(Array x) {
                return rosenBrockFunction.calculate(x.get(0), x.get(1));
            }
        }, constraint, solver2, myEndCrit, array);

        System.out.println(String.format("%-30s%-30.20f%-30.20f", "Root Simplex", min1.get(0), min1.get(1)));
        System.out.println(String.format("%-30s%-30.20f%-30.20f", "Root ConjugateGradient", min2.get(0), min2.get(1)));
        System.out.println(String.format("%-30s%2$-30.20G", "Min F Value Simplex", rosenBrockFunction.calculate(min1.get(0), min1.get(1))));
        System.out.println(String.format("%-30s%2$-30.20G", "Min F Value ConjugateGradient", rosenBrockFunction.calculate(min2.get(0), min2.get(1))));
    }

    @Test
    public void testOptimizer2() {
        double spot = 98.51;
        double vol = 0.134;
        double[] k = {87.0, 96.0, 103.0, 110.0};
        double rd = 0.002;
        double rf = 0.01;
        double phi = 1;
        double tau = 0.6;

        double[] c = new double[4];

        for (int i = 0; i < k.length; i++) {
            c[i] = CallProblemFunction.blackScholesPrice(spot, k[i], rd, rf, vol, tau, phi);
        }

        CallProblemFunction callProblemFunction = new CallProblemFunction(rd, rf, tau, phi, k, c);

        long maxIterations = 1000;
        long minStatIterations = 100;
        double rootEpsilon = 1e-5;
        double functionEpsilon = 1e-5;
        double gradientNormEpsilon = 1e-5;

        EndCriteria myEndCrit = new EndCriteria(maxIterations, minStatIterations,
                rootEpsilon, functionEpsilon, gradientNormEpsilon);

        Array startVal = new Array(2);
        startVal.set(0, 80.0);
        startVal.set(1, 0.20);

        NoConstraint constraint = new NoConstraint();
        BFGS solver = new BFGS();

        Optimizer minimize = new Optimizer();

        Array min1 = minimize.solve(new CostFunctionDelegate() {
            @Override
            public double value(Array x) {
                return callProblemFunction.calculate(x.get(0), x.get(1));
            }

        }, constraint, solver, myEndCrit, startVal);

        System.out.println(String.format("%-30s%-30.20f%-30.20f", "Root", min1.get(0), min1.get(1)));
        System.out.println(String.format("%-30s%2$-30.20G", "Min Function Value",
                callProblemFunction.calculate(min1.get(0), min1.get(1))));
    }
}
