package wjc.quantlib;

import org.quantlib.ActualActual;
import org.quantlib.BusinessDayConvention;
import org.quantlib.China;
import org.quantlib.Compounding;
import org.quantlib.Date;
import org.quantlib.DateGeneration;
import org.quantlib.DoubleVector;
import org.quantlib.FittedBondDiscountCurve;
import org.quantlib.FixedRateBondHelper;
import org.quantlib.Frequency;
import org.quantlib.Leg;
import org.quantlib.Month;
import org.quantlib.Period;
import org.quantlib.PiecewiseLogCubicDiscount;
import org.quantlib.RateHelper;
import org.quantlib.RateHelperVector;
import org.quantlib.RelinkableQuoteHandle;
import org.quantlib.Schedule;
import org.quantlib.Settings;
import org.quantlib.SimpleQuote;
import org.quantlib.SvenssonFitting;
import org.quantlib.TimeUnit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: wangjunchao(王俊超)
 * @time: 2019-04-25 17:53
 **/
public class ComplexCurveDemo {
    public static void main(String[] args) {
        complexCurve();
    }

    private static void complexCurve() {
        System.out.println("--------------CASE 1--------------");
        int[] maturities = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 15, 20, 30, 50};
        double[] pars = {0.030544, 0.031549, 0.032489, 0.032702, 0.032915, 0.033958, 0.03500,
                0.035050, 0.035100, 0.035150, 0.037765, 0.038163, 0.039568, 0.03972};
        double[] spots = {0.030544, 0.031565, 0.032531, 0.032744, 0.032964, 0.034092, 0.035237,
                0.035264, 0.035298, 0.035337, 0.038517, 0.038884, 0.040943, 0.040720};

        double[] cleanPrice = new double[maturities.length];
        Arrays.fill(cleanPrice, 100.0);

        SimpleQuote[] quote = new SimpleQuote[cleanPrice.length];
        for (int i = 0; i < quote.length; i++) {
            quote[i] = new SimpleQuote(cleanPrice[i]);
        }

        RelinkableQuoteHandle[] quoteHandle = new RelinkableQuoteHandle[quote.length];
        for (int i = 0; i < quoteHandle.length; i++) {
            quoteHandle[i] = new RelinkableQuoteHandle();
            quoteHandle[i].linkTo(quote[i]);
        }

        Frequency frequency = Frequency.Annual;
        ActualActual dc = new ActualActual(ActualActual.Convention.ISMA);
        BusinessDayConvention accrualConvention = BusinessDayConvention.ModifiedFollowing;
        BusinessDayConvention convention = BusinessDayConvention.ModifiedFollowing;
        double redemption = 100.0;
        China calendar = new China(China.Market.IB);

        Date today = calendar.adjust(new Date(23, Month.July, 2018));
        Settings instance = Settings.instance();
        instance.setEvaluationDate(today);

        int bondSettlementDays = 0;
        Date bondSettlementDate = calendar.advance(
                today,
                new Period(bondSettlementDays, TimeUnit.Days));

        RateHelperVector instruments = new RateHelperVector();

        for (int j = 0; j < maturities.length; j++) {
            Date maturity = calendar.advance(
                    bondSettlementDate,
                    new Period(maturities[j], TimeUnit.Years));
            Schedule schedule = new Schedule(
                    bondSettlementDate,
                    maturity,
                    new Period(frequency),
                    calendar,
                    accrualConvention,
                    accrualConvention,
                    DateGeneration.Rule.Backward,
                    false);
            DoubleVector doubleVector = new DoubleVector();
            doubleVector.add(pars[j]);
            FixedRateBondHelper helper = new FixedRateBondHelper(
                    quoteHandle[j],
                    bondSettlementDays,
                    100.0,
                    schedule,
                    doubleVector,
                    dc,
                    convention,
                    redemption);

            instruments.add(helper);
        }

        double tolerance = 1.0e-10;
        int max = 5000;
        SvenssonFitting svensson = new SvenssonFitting();

        PiecewiseLogCubicDiscount ts0 = new PiecewiseLogCubicDiscount(
                bondSettlementDate,
                instruments,
                dc);
        FittedBondDiscountCurve ts1 = new FittedBondDiscountCurve(
                bondSettlementDate,
                instruments,
                dc,
                svensson,
                tolerance,
                max);

        List<Object> spline = new ArrayList<>();
        List<Object> sv = new ArrayList<>();

        System.out.println(String.format("%-9s%-9s%-9s%-9s", "tenor", "spot", "spline", "svensson"));

        for (int i = 0; i < instruments.size(); i++) {
            RateHelper rateHelper =  instruments.get(i);

            // TODO Exception in thread "main" java.lang.ClassCastException: org.quantlib.RateHelper cannot be cast to org.quantlib.FixedRateBondHelper
            Leg cfs = ((FixedRateBondHelper)rateHelper).bond().cashflows();
            int cfSize = (int) cfs.size();

            double tenor = dc.yearFraction(today, cfs.get(cfSize - 1).date());

            System.out.println(
                   String.format(
                           "%-9s%-9s%-9s%-9s",
                            tenor,
                            100.0 * spots[i],
                            100.0 * ts0.zeroRate(cfs.get(cfSize - 1).date(), dc, Compounding.Compounded, frequency).rate(),
                            100.0 * ts1.zeroRate(cfs.get(cfSize - 1).date(), dc, Compounding.Compounded, frequency).rate()));

            spline.add(ts0.zeroRate(cfs.get(cfSize - 1).date(), dc, Compounding.Compounded, frequency).rate());
            sv.add(ts1.zeroRate(cfs.get(cfSize - 1).date(), dc, Compounding.Compounded, frequency).rate());

        }
    }
}
