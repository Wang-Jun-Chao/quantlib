package wjc.quantlib.random;

import org.junit.Test;
import org.quantlib.BoxMullerMersenneTwisterGaussianRng;
import org.quantlib.DoubleVector;
import org.quantlib.HaltonRsg;
import org.quantlib.IncrementalStatistics;
import org.quantlib.KnuthUniformRng;
import org.quantlib.LecuyerUniformRng;
import org.quantlib.MersenneTwisterUniformRng;
import org.quantlib.MoroInverseCumulativeNormal;
import org.quantlib.SobolRsg;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: wangjunchao(王俊超)
 * @time: 2019-05-17 10:56
 **/
public class RandomTest {
    /**
     * 伪随机数
     */
    @Test
    public void testingRandomNumbers1() {
        int seed = 1;
        MersenneTwisterUniformRng unifMt = new MersenneTwisterUniformRng(seed);
        LecuyerUniformRng unifLec = new LecuyerUniformRng(seed);
        KnuthUniformRng unifKnuth = new KnuthUniformRng(seed);

        System.out.println(String.format("%-30s%-30s%-30s", "Mersenne Twister", "Lecuyer", "Knut"));

        for (int i = 0; i < 10; i++) {

            System.out.println(String.format("%1$-30.20G%2$-30.20G%3$-30.20G",
                    unifMt.next().value(), unifLec.next().value(), unifKnuth.next().value()));
        }
    }

    /**
     * 正态分布（伪）随机数
     */
    @Test
    public void testingRandomNumbers2() {
        int seed = 12324;
        MersenneTwisterUniformRng unifMt = new MersenneTwisterUniformRng(seed);
        BoxMullerMersenneTwisterGaussianRng bmGauss = new BoxMullerMersenneTwisterGaussianRng(unifMt);

        for (int i = 0; i < 5; i++) {
            System.out.println(String.format("%1$-30.20G", bmGauss.next().value()));
        }
    }

    /**
     * 拟随机数
     */
    @Test
    public void testingRandomNumbers4() {
        int dim = 5;
        HaltonRsg haltonGen = new HaltonRsg(dim);
        SobolRsg sobolGen = new SobolRsg(dim);

        DoubleVector sampleHalton = haltonGen.nextSequence().value();
        DoubleVector sampleSobol = sobolGen.nextSequence().value();

        System.out.println(String.format("%-30s%-30s", "Halton", "Sobol"));

        for (int i = 0; i < dim; i++) {
            System.out.println(String.format("%1$-30.20G%2$-30.20G",
                    sampleHalton.get(i), sampleSobol.get(i)));
        }
    }

    @Test
    public void testingRandomNumbers5() {
        SobolRsg sobolGen = new SobolRsg(1);

        int seed = 12324;

        MersenneTwisterUniformRng unifMt = new MersenneTwisterUniformRng(seed);
        BoxMullerMersenneTwisterGaussianRng bmGauss = new BoxMullerMersenneTwisterGaussianRng(unifMt);

        IncrementalStatistics boxMullerStat = new IncrementalStatistics();
        IncrementalStatistics sobolStat = new IncrementalStatistics();

        MoroInverseCumulativeNormal invGauss = new MoroInverseCumulativeNormal();

        int numSim = 10000;

        for (int j = 0; j < numSim; j++) {
            boxMullerStat.add(bmGauss.next().value());
            double currSobolNum = sobolGen.nextSequence().value().get(0);
            sobolStat.add(invGauss.getValue(currSobolNum));
        }


        Map<String, Double> stats = new LinkedHashMap<>();

        stats.put("BoxMuller Mean:", boxMullerStat.mean());
        stats.put("Sobol Mean:", sobolStat.mean());
        stats.put("BoxMuller Var:", boxMullerStat.variance());
        stats.put("Sobol Var:", sobolStat.variance());
        stats.put("BoxMuller Skew:", boxMullerStat.skewness());
        stats.put("Sobol Skew:", sobolStat.skewness());
        stats.put("BoxMuller Kurtosis:", boxMullerStat.kurtosis());
        stats.put("Sobol Kurtosis:", sobolStat.kurtosis());

        for (Map.Entry<String, Double> e : stats.entrySet()) {
            System.out.println(String.format("%25s%2$+30.20f", e.getKey(), e.getValue()));
        }
    }
}
