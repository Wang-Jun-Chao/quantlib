package wjc.quantlib;

import org.quantlib.BusinessDayConvention;
import org.quantlib.Calendar;
import org.quantlib.China;
import org.quantlib.Date;
import org.quantlib.Month;
import org.quantlib.Period;
import org.quantlib.SaudiArabia;
import org.quantlib.TimeUnit;
import org.quantlib.Weekday;

/**
 * @author: wangjunchao(王俊超)
 * @time: 2019-04-23 19:05
 **/
public class CalendarDemo {
    public static void main(String[] args) {
        case1();
        case2();
        case3();
    }

    private static void case1() {
        System.out.println("--------------CASE 1--------------");
        Calendar chinaCal = new China(China.Market.IB);
        SaudiArabia saudiArabCal = new SaudiArabia();
        Date nyEve = new Date(3, Month.April, 2017);

        System.out.println("Is BD :" + chinaCal.isBusinessDay(nyEve));
        System.out.println("Is Holiday :" + chinaCal.isHoliday(nyEve));
        System.out.println("Is Weekend in SA :" + saudiArabCal.isWeekend(Weekday.Friday));
        System.out.println("Is Weekend in CN :" + chinaCal.isWeekend(Weekday.Friday));
        System.out.println("Is Last BD :" + chinaCal.isEndOfMonth(new Date(5, Month.April, 2018)));
        System.out.println("Last BD :" + chinaCal.endOfMonth(nyEve));
    }

    private static void case2() {
        System.out.println("--------------CASE 2--------------");
        Calendar chinaCal = new China(China.Market.IB);

        Date d1 = new Date(5, Month.April, 2018);
        Date d2 = new Date(6, Month.April, 2018);
        Date d3 = new Date(8, Month.April, 2018);

        System.out.println("Is Business Day : " + chinaCal.isBusinessDay(d1));
        System.out.println("Is Business Day : " + chinaCal.isBusinessDay(d2));
        System.out.println("Is Business Day : " + chinaCal.isBusinessDay(d3));

        chinaCal.addHoliday(d1);
        chinaCal.addHoliday(d2);
        chinaCal.removeHoliday(d3);

        System.out.println("Is Business Day : " + chinaCal.isBusinessDay(d1));
        System.out.println("Is Business Day : " + chinaCal.isBusinessDay(d2));
        System.out.println("Is Business Day : " + chinaCal.isBusinessDay(d3));
    }

    private static void case3() {
        System.out.println("--------------CASE 3--------------");
        Calendar chinaCal = new China(China.Market.IB);

        Date firstDate = new Date(31, Month.January, 2018);
        Date secondDate = new Date(1, Month.April, 2018);

        System.out.println("Date 2 Adj :" + chinaCal.adjust(secondDate, BusinessDayConvention.Preceding));
        System.out.println("Date 2 Adj :" + chinaCal.adjust(secondDate, BusinessDayConvention.ModifiedPreceding));

        Period mat = new Period(2, TimeUnit.Months);

        System.out.println("Date 1 Month Adv :" + chinaCal.advance(
                firstDate, mat, BusinessDayConvention.Following, false));
        System.out.println("Date 1 Month Adv :" + chinaCal.advance(
                firstDate, mat, BusinessDayConvention.ModifiedFollowing, false));
        System.out.println("Business Days Between :" + chinaCal.businessDaysBetween(
                new Date(5, Month.March, 2018), new Date(30, Month.March, 2018),
                true, true));
    }
}
