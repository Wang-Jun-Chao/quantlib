package wjc.quantlib;

import org.quantlib.ActualActual;
import org.quantlib.China;
import org.quantlib.Compounding;
import org.quantlib.Date;
import org.quantlib.DateVector;
import org.quantlib.DayCounter;
import org.quantlib.DoubleVector;
import org.quantlib.Frequency;
import org.quantlib.Linear;
import org.quantlib.Month;
import org.quantlib.Period;
import org.quantlib.TimeUnit;
import org.quantlib.ZeroCurve;

/**
 * @author: wangjunchao(王俊超)
 * @time: 2019-04-25 17:53
 **/
public class ZeroCurveDemo {
    public static void main(String[] args) {
        zeroCurve();
    }

    private static void zeroCurve() {
        System.out.println("--------------CASE 1--------------");
        DateVector dates = new DateVector();
        DoubleVector dfs = new DoubleVector();

        China cal = new China(China.Market.IB);
        Date today = new Date(23, Month.July, 2018);
        DayCounter dc = new ActualActual(ActualActual.Convention.ISMA);

        int settlementDays = 0;
        Date settlement = cal.advance(today, settlementDays, TimeUnit.Days);

        dates.add(settlement);
        dates.add(settlement.add(new Period(1, TimeUnit.Years)));
        dates.add(settlement.add(new Period(2, TimeUnit.Years)));
        dates.add(settlement.add(new Period(3, TimeUnit.Years)));
        dates.add(settlement.add(new Period(4, TimeUnit.Years)));
        dates.add(settlement.add(new Period(5, TimeUnit.Years)));
        dates.add(settlement.add(new Period(6, TimeUnit.Years)));
        dates.add(settlement.add(new Period(7, TimeUnit.Years)));
        dates.add(settlement.add(new Period(8, TimeUnit.Years)));
        dates.add(settlement.add(new Period(9, TimeUnit.Years)));
        dates.add(settlement.add(new Period(10, TimeUnit.Years)));
        dates.add(settlement.add(new Period(15, TimeUnit.Years)));
        dates.add(settlement.add(new Period(20, TimeUnit.Years)));
        dates.add(settlement.add(new Period(30, TimeUnit.Years)));

        dfs.add(0.0000 / 100.0);
        dfs.add(3.0544 / 100.0);
        dfs.add(3.1565 / 100.0);
        dfs.add(3.2531 / 100.0);
        dfs.add(3.2744 / 100.0);
        dfs.add(3.2964 / 100.0);
        dfs.add(3.4092 / 100.0);
        dfs.add(3.5237 / 100.0);
        dfs.add(3.5264 / 100.0);
        dfs.add(3.5298 / 100.0);
        dfs.add(3.5337 / 100.0);
        dfs.add(3.8517 / 100.0);
        dfs.add(3.8884 / 100.0);
        dfs.add(4.0943 / 100.0);

        Date tmpDate1 = settlement.add(new Period(7, TimeUnit.Years));
        Date tmpDate2 = settlement.add(new Period(8, TimeUnit.Years));
        ZeroCurve curve = new ZeroCurve(dates, dfs, dc, cal, new Linear(),
                Compounding.Compounded, Frequency.Annual);


        System.out.println("零息收益率：" + curve.zeroRate(
                tmpDate2, dc, Compounding.Compounded, Frequency.Annual));
        System.out.println("贴现因子：" + curve.discount(tmpDate2));

        System.out.println("7Y - 8Y 远期收益率：" + curve.forwardRate(
                tmpDate1, tmpDate2, dc, Compounding.Compounded, Frequency.Annual));

    }
}
