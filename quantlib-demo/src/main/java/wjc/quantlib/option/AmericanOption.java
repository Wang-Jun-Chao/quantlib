package wjc.quantlib.option;

import org.junit.Test;
import org.quantlib.Actual365Fixed;
import org.quantlib.AmericanExercise;
import org.quantlib.BlackConstantVol;
import org.quantlib.BlackScholesProcess;
import org.quantlib.BlackVolTermStructureHandle;
import org.quantlib.Calendar;
import org.quantlib.Date;
import org.quantlib.DateVector;
import org.quantlib.DayCounter;
import org.quantlib.DividendVanillaOption;
import org.quantlib.DoubleVector;
import org.quantlib.FDDividendAmericanEngine;
import org.quantlib.FlatForward;
import org.quantlib.Month;
import org.quantlib.Option;
import org.quantlib.PlainVanillaPayoff;
import org.quantlib.PricingEngine;
import org.quantlib.QuoteHandle;
import org.quantlib.Settings;
import org.quantlib.SimpleQuote;
import org.quantlib.TARGET;
import org.quantlib.YieldTermStructureHandle;

/**
 * @author: wangjunchao(王俊超)
 * @time: 2019-05-21 14:22
 **/
public class AmericanOption {
    public static class Inputs {
        long         s;
        long         k;
        double       r;
        double       vol;
        /**
         * Dividend dates
         */
        DateVector   divdates;
        /**
         * Dividend ammounts
         */
        DoubleVector divamounts;
        Date         maturity;
        Date         settlement;
    }

    private void setupExample(Inputs i) {
        i.s = 100;
        i.k = 100;
        i.r = 0.05;
        i.vol = 0.2;

        i.divdates = new DateVector(4);
        i.divdates.add(new Date(1, Month.January, 2019));
        i.divdates.add(new Date(1, Month.April, 2019));
        i.divdates.add(new Date(1, Month.July, 2019));
        i.divdates.add(new Date(1, Month.October, 2019));
        ;

        i.divamounts = new DoubleVector(4);
        i.divamounts.add(1.0);
        i.divamounts.add(1.0);
        i.divamounts.add(1.0);
        i.divamounts.add(1.0);


        i.maturity = new Date(1, Month.December, 2019);
        i.settlement = new Date(1, Month.December, 2018);

    }

    private void price(Inputs i) {
        Calendar calendar = new TARGET();
        Settings.instance().setEvaluationDate(new Date(1, Month.December, 2018));
        DayCounter dayCounter = new Actual365Fixed();
        AmericanExercise americanExercise = new AmericanExercise(i.settlement, i.maturity);
        PlainVanillaPayoff payoff = new PlainVanillaPayoff(Option.Type.Call, i.k);

        DividendVanillaOption opt = new DividendVanillaOption(
                payoff, americanExercise, i.divdates, i.divamounts);

        SimpleQuote simpleQuote = new SimpleQuote(i.s);
        QuoteHandle underlyingH = new QuoteHandle(simpleQuote);

        YieldTermStructureHandle rTS = new YieldTermStructureHandle(
                new FlatForward(i.settlement, i.r, dayCounter));

        BlackVolTermStructureHandle flatVolTS = new BlackVolTermStructureHandle(
                new BlackConstantVol(i.settlement, calendar, i.vol, dayCounter));

        BlackScholesProcess process = new BlackScholesProcess(
                underlyingH, rTS, flatVolTS);

        PricingEngine pe = new FDDividendAmericanEngine(process);

        opt.setPricingEngine(pe);

        System.out.println("NPV: " + opt.NPV());
    }

    /**
     * TODO java.lang.RuntimeException: first date (-119) cannot be negative
     */
    @Test
    public void testAmericanOption() {
        Inputs i = new Inputs();
        setupExample(i);
        price(i);
    }
}
