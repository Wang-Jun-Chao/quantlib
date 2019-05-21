package wjc.quantlib.curve;

import org.quantlib.Actual360;
import org.quantlib.ActualActual;
import org.quantlib.BusinessDayConvention;
import org.quantlib.China;
import org.quantlib.Compounding;
import org.quantlib.Date;
import org.quantlib.DateVector;
import org.quantlib.DayCounter;
import org.quantlib.DoubleVector;
import org.quantlib.Euribor6M;
import org.quantlib.ForwardCurve;
import org.quantlib.Frequency;
import org.quantlib.IborIndex;
import org.quantlib.Month;
import org.quantlib.Period;
import org.quantlib.PiecewiseFlatForward;
import org.quantlib.QuoteHandle;
import org.quantlib.QuoteHandleVector;
import org.quantlib.RateHelperVector;
import org.quantlib.Settings;
import org.quantlib.SimpleQuote;
import org.quantlib.SwapRateHelper;
import org.quantlib.Thirty360;
import org.quantlib.TimeUnit;


/**
 * TODO
 * @author: wangjunchao(王俊超)
 * @time: 2019-04-25 17:53
 **/
public class ForwardCurveDemo {
    public static void main(String[] args) {
        zeroCurve();
    }

    private static void zeroCurve() {
        System.out.println("--------------CASE 1--------------");

        Settings.instance().setEvaluationDate(new Date(3, Month.October, 2014));




        DateVector dates = new DateVector();
        DoubleVector dfs = new DoubleVector();

        China cal = new China(China.Market.IB);
        Date today = new Date(23, Month.July, 2018);
        DayCounter dc = new Thirty360();
        IborIndex iborIndex = new Euribor6M();

        SwapRateHelper swapRateHelper = new SwapRateHelper(
                new QuoteHandle(new SimpleQuote(1)),
                new Period(1, TimeUnit.Years),
                cal,
                Frequency.Monthly,
                BusinessDayConvention.Following,
                dc,
                iborIndex
        );

        RateHelperVector rateHelperVector = new RateHelperVector();
        rateHelperVector.add(swapRateHelper);

        int settlementDays = 0;
        Date settlement = cal.advance(today, settlementDays, TimeUnit.Days);

        dates.add(settlement);
        dates.add(settlement.add(new Period(2, TimeUnit.Years)));
        dates.add(settlement.add(new Period(3, TimeUnit.Years)));
        dates.add(settlement.add(new Period(5, TimeUnit.Years)));
        dates.add(settlement.add(new Period(10, TimeUnit.Years)));
        dates.add(settlement.add(new Period(15, TimeUnit.Years)));

        dfs.add(0.000);
        dfs.add(0.201);
        dfs.add(0.258);
        dfs.add(0.464);
        dfs.add(1.151);
        dfs.add(1.588);

        PiecewiseFlatForward curve1 = new PiecewiseFlatForward(0, cal, rateHelperVector, new Actual360());
        ForwardCurve curve2 = new ForwardCurve(dates, dfs, new Actual360());


        System.out.println(curve1.referenceDate() + " to " + curve1.maxDate());
        System.out.println(curve2.referenceDate() + " to " + curve2.maxDate());


//        System.out.println(curve1.zeroRate(5.0 , Compounding.Continuous));
//        System.out.println(curve2.zeroRate(5.0 , Compounding.Continuous));

        System.out.println(curve1.zeroRate(new Date(7, Month.September, 2019), new Actual360(), Compounding.Continuous));
        System.out.println(curve2.zeroRate(new Date(7, Month.September, 2019), new Actual360(), Compounding.Continuous));




//        Date tmpDate1 = settlement.add(new Period(7, TimeUnit.Years));
//        Date tmpDate2 = settlement.add(new Period(8, TimeUnit.Years));
//        ForwardCurve curve = new ForwardCurve(dates, dfs, dc, cal, new Linear(),
//                Compounding.Compounded, Frequency.Annual);
//
//
//        System.out.println("零息收益率：" + curve.zeroRate(
//                tmpDate2, dc, Compounding.Compounded, Frequency.Annual));
//        System.out.println("贴现因子：" + curve.discount(tmpDate2));
//
//        System.out.println("7Y - 8Y 远期收益率：" + curve.forwardRate(
//                tmpDate1, tmpDate2, dc, Compounding.Compounded, Frequency.Annual));

    }
}
