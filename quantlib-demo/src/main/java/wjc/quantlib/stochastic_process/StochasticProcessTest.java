package wjc.quantlib.stochastic_process;

import org.junit.Test;
import org.quantlib.ActualActual;
import org.quantlib.BlackConstantVol;
import org.quantlib.BlackScholesMertonProcess;
import org.quantlib.BlackVolTermStructureHandle;
import org.quantlib.BoxMullerMersenneTwisterGaussianRng;
import org.quantlib.China;
import org.quantlib.Date;
import org.quantlib.DayCounter;
import org.quantlib.FlatForward;
import org.quantlib.MersenneTwisterUniformRng;
import org.quantlib.Month;
import org.quantlib.QuoteHandle;
import org.quantlib.SimpleQuote;
import org.quantlib.YieldTermStructureHandle;

/**
 * @author: wangjunchao(王俊超)
 * @time: 2019-05-17 11:16
 **/
public class StochasticProcessTest {
    @Test
    public void testingStochasticProcesses1() {
        Date refDate = new Date(27, Month.January, 2019);
        double riskFreeRate = 0.0321;
        double dividendRate = 0.0128;
        double spot = 52.0;
        double vol = 0.2144;
        China cal = new China();
        DayCounter dc = new ActualActual();

        YieldTermStructureHandle rdHandle = new YieldTermStructureHandle(new FlatForward(refDate, riskFreeRate, dc));
        YieldTermStructureHandle rqHandle = new YieldTermStructureHandle(new FlatForward(refDate, dividendRate, dc));

        SimpleQuote spotQuote = new SimpleQuote(spot);
        QuoteHandle spotHandle = new QuoteHandle(new SimpleQuote(spot));

        BlackVolTermStructureHandle volHandle = new BlackVolTermStructureHandle(new BlackConstantVol(refDate, cal, vol, dc));

        BlackScholesMertonProcess bsmProcess = new BlackScholesMertonProcess(spotHandle, rqHandle, rdHandle, volHandle);

        int seed = 1234;
        MersenneTwisterUniformRng unifMt = new MersenneTwisterUniformRng(seed);
        BoxMullerMersenneTwisterGaussianRng bmGauss = new BoxMullerMersenneTwisterGaussianRng(unifMt);

        double dt = 0.004;
        int numVals = 250;

//        bsm = pd.DataFrame()
//
//        for i in range(10):
//        bsmt = pd.DataFrame(
//                dict(
//                        t = np.linspace(0, dt * numVals, numVals + 1),
//                        path = np.nan,
//                        n = 'p' + str(i)))
//
//        bsmt.loc[0, 'path'] =spotQuote.value()
//
//        x = spotQuote.value()
//
//        for j in range(1, numVals + 1):
//        dw = bmGauss.next().value()
//        x = bsmProcess.evolve(bsmt.loc[j, 't'],x, dt, dw)
//        bsmt.loc[j, 'path'] =x
//
//        bsm = pd.concat([bsm, bsmt])
//
//        sn.lineplot(
//                x = 't', y = 'path',
//                data = bsm,
//                hue = 'n', legend = None)
    }
}
