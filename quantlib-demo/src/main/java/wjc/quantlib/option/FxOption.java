package wjc.quantlib.option;

import org.junit.Test;
import org.quantlib.Actual365Fixed;
import org.quantlib.AmericanExercise;
import org.quantlib.BinomialVanillaEngine;
import org.quantlib.BlackConstantVol;
import org.quantlib.BlackVolTermStructureHandle;
import org.quantlib.Calendar;
import org.quantlib.Date;
import org.quantlib.DayCounter;
import org.quantlib.FlatForward;
import org.quantlib.GarmanKohlagenProcess;
import org.quantlib.Month;
import org.quantlib.Option;
import org.quantlib.PlainVanillaPayoff;
import org.quantlib.QuoteHandle;
import org.quantlib.Settings;
import org.quantlib.SimpleQuote;
import org.quantlib.TARGET;
import org.quantlib.VanillaOption;
import org.quantlib.YieldTermStructureHandle;

/**
 * http://www.bnikolic.co.uk/blog/ql-fx-option-simple.html
 *
 * @author: wangjunchao(王俊超)
 * @time: 2019-05-21 09:50
 **/
public class FxOption {
    class OptionInputs {
        long       s;
        long       k;
        /**
         * Foreign rate
         */
        double     f;
        /**
         * Domestic rate
         */
        double     r;
        double     vol;
        Date       maturity;
        DayCounter dayCounter;
    }


    private double crrPricing(VanillaOption o, GarmanKohlagenProcess process, long timeSteps) {

        BinomialVanillaEngine engine = new BinomialVanillaEngine(process, "CoxRossRubinstein", timeSteps);
        o.setPricingEngine(engine);

        return o.NPV();
    }

    private void fxOptEx(OptionInputs in, Date todaysDate, Date settlementDate) {

        // set up dates
        Calendar calendar = new TARGET();
        Settings.instance().setEvaluationDate(todaysDate);

        AmericanExercise americanExercise = new AmericanExercise(settlementDate, in.maturity);

        SimpleQuote underlyingH = new SimpleQuote(in.s);
        QuoteHandle quoteHandle = new QuoteHandle(underlyingH);

        FlatForward rTS = new FlatForward(settlementDate, in.r, in.dayCounter);
        YieldTermStructureHandle rtsHandle = new YieldTermStructureHandle(rTS);
        FlatForward fTS = new FlatForward(settlementDate, in.f, in.dayCounter);
        YieldTermStructureHandle fTSHandle = new YieldTermStructureHandle(fTS);

        BlackConstantVol flatVolTS = new BlackConstantVol(settlementDate, calendar, in.vol, in.dayCounter);
        BlackVolTermStructureHandle flatVolTSHandler = new BlackVolTermStructureHandle(flatVolTS);
        PlainVanillaPayoff payoff = new PlainVanillaPayoff(Option.Type.Call, in.k);

        GarmanKohlagenProcess process = new GarmanKohlagenProcess(quoteHandle, fTSHandle, rtsHandle, flatVolTSHandler);

        VanillaOption amerOpt = new VanillaOption(payoff, americanExercise);

        double npv = crrPricing(amerOpt, process, 100);

        System.out.println("Option value: " + npv);
    }


    @Test
    public void test() {

        OptionInputs in = new OptionInputs();
        in.s = 100;
        in.k = 100;
        in.f = 0.05;
        in.r = 0.02;
        in.vol = 0.20;
        in.maturity = new Date(17, Month.May, 1999);
        in.dayCounter = new Actual365Fixed();

        fxOptEx(in, new Date(15, Month.May, 1998), new Date(17, Month.May, 1998));
    }

}
