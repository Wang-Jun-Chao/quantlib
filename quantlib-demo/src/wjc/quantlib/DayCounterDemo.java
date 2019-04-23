package wjc.quantlib;

import org.quantlib.Date;
import org.quantlib.Month;
import org.quantlib.Period;
import org.quantlib.Thirty360;
import org.quantlib.TimeUnit;

/**
 * @author: wangjunchao(王俊超)
 * @time: 2019-04-23 22:21
 **/
public class DayCounterDemo {
    public static void main(String[] args) {
        case1();
    }

    private static void case1() {
        System.out.println("--------------CASE 1--------------");
        Thirty360 dc = new Thirty360();

        Date d1 = new Date(1, Month.March, 2018);
        Date d2 = d1.add(new Period(2, TimeUnit.Months));

        System.out.println("Days Between d1 / d2:" +dc.dayCount(d1, d2));
        System.out.println("Year Fraction d1 / d2:" +dc.yearFraction(d1, d2));
        System.out.println("Actual Days Between d1 / d2:" +(d2.dayOfYear() - d1.dayOfYear()));
        System.out.println("d1:" +d1);
        System.out.println("d2:" +d2);

    }
}
