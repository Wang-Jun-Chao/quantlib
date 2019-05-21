package wjc.quantlib.curve;

import org.junit.Test;
import org.quantlib.Compounding;
import org.quantlib.Date;
import org.quantlib.DateVector;
import org.quantlib.DayCounter;
import org.quantlib.DiscountCurve;
import org.quantlib.DoubleVector;
import org.quantlib.EURLibor1M;
import org.quantlib.Frequency;
import org.quantlib.InterestRate;
import org.quantlib.Month;
import org.quantlib.Period;
import org.quantlib.TimeUnit;
import org.quantlib.UnitedStates;

/**
 * @author: wangjunchao(王俊超)
 * @time: 2019-04-25 17:53
 **/
public class DiscountCurveTest {

    @Test
    public void testDiscountCurve() {
        DateVector dates = new DateVector();
        DoubleVector dfs = new DoubleVector();

        UnitedStates cal = new UnitedStates();
        Date today = new Date(11, Month.September, 2009);
        EURLibor1M libor = new EURLibor1M();
        DayCounter dc = libor.dayCounter();

        int settlementDays = 2;
        Date settlement = cal.advance(today, settlementDays, TimeUnit.Days);

        dates.add(settlement);
        dates.add(settlement.add(new Period(1, TimeUnit.Days)));
        dates.add(settlement.add(new Period(1, TimeUnit.Weeks)));
        dates.add(settlement.add(new Period(1, TimeUnit.Months)));
        dates.add(settlement.add(new Period(2, TimeUnit.Months)));
        dates.add(settlement.add(new Period(3, TimeUnit.Months)));
        dates.add(settlement.add(new Period(6, TimeUnit.Months)));
        dates.add(settlement.add(new Period(9, TimeUnit.Months)));
        dates.add(settlement.add(new Period(1, TimeUnit.Years)));
        dates.add(settlement.add(new Period(1, TimeUnit.Years)).add(new Period(3, TimeUnit.Months)));
        dates.add(settlement.add(new Period(1, TimeUnit.Years)).add(new Period(6, TimeUnit.Months)));
        dates.add(settlement.add(new Period(1, TimeUnit.Years)).add(new Period(9, TimeUnit.Months)));
        dates.add(settlement.add(new Period(2, TimeUnit.Years)));

        dfs.add(1.0);
        dfs.add(0.9999656);
        dfs.add(0.9999072);
        dfs.add(0.9996074);
        dfs.add(0.9990040);
        dfs.add(0.9981237);
        dfs.add(0.9951358);
        dfs.add(0.9929456);
        dfs.add(0.9899849);
        dfs.add(0.9861596);
        dfs.add(0.9815178);
        dfs.add(0.9752363);
        dfs.add(0.9680804);

        Date tmpDate1 = settlement.add(new Period(1, TimeUnit.Years)).add(new Period(3, TimeUnit.Months));
        Date tmpDate2 = tmpDate1.add(new Period(3, TimeUnit.Months));
        DiscountCurve curve = new DiscountCurve(dates, dfs, dc, cal);

        InterestRate equZero = curve.zeroRate(tmpDate1, dc, Compounding.Simple, Frequency.Annual);

        System.out.println(String.format("%-40s%s", "等价 Zero Rate :", equZero));
        System.out.println(String.format("%-40s%s", "等价 Zero Rate 计算的贴现因子 :", equZero.discountFactor(settlement, tmpDate1)));
        System.out.println(String.format("%-40s%s", "真实 Discount Factor :", curve.discount(tmpDate1)));
        System.out.println(String.format("%-40s%s", "1Y3M-1Y6M 间的远期收益率 Fwd Rate :", curve.forwardRate(tmpDate1, tmpDate2, dc, Compounding.Continuous)));
    }
}
