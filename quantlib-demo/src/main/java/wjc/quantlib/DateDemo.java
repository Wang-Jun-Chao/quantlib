package wjc.quantlib;

import org.quantlib.Date;
import org.quantlib.Month;
import org.quantlib.Period;
import org.quantlib.Settings;
import org.quantlib.TimeUnit;
import org.quantlib.Weekday;

/**
 * @author: wangjunchao(王俊超)
 * @time: 2019-03-14 15:14
 **/
public class DateDemo {
    public static void main(String[] args) {
        case1();
        case2();
        case3();
        case4();
        case5();
    }

    private static void case1() {
        System.out.println("--------------CASE 1--------------");
        Date date = new Date(7, Month.May, 2017);
        System.out.println(date);

        System.out.println(date.dayOfMonth());
        System.out.println(date.dayOfYear());
        System.out.println(date.weekday());

        System.out.println(date.weekday() == Weekday.Saturday);
        System.out.println(date.add(1));
        System.out.println(date.add(-1));

        System.out.println(date.add(new Period(1, TimeUnit.Months)));
        System.out.println(date.add(new Period(1, TimeUnit.Weeks)));

        Date d1 = new Date(31, Month.March, 2015);
        Date d2 = new Date(1, Month.March, 2015);

        // java.lang.RuntimeException: QuantLib was not compiled with intraday support
//        System.out.println(d1.fractionOfSecond() > d2.fractionOfSecond());
    }

    private static void case2() {
        System.out.println("--------------CASE 2--------------");
        Date myDate = new Date(12, Month.August, 2009);
        System.out.println(myDate);

        myDate = myDate.add(1);
        System.out.println(myDate);

        myDate = myDate.add(new Period(12, TimeUnit.Days));
        System.out.println(myDate);

        myDate = myDate.subtract(new Period(2, TimeUnit.Months));
        System.out.println(myDate);

        myDate = myDate.subtract(1);
        System.out.println(myDate);

        myDate = myDate.add(new Period(10, TimeUnit.Weeks));
        System.out.println(myDate);
    }

    private static void case3() {
        System.out.println("--------------CASE 3--------------");

        Date myDate = new Date(12, Month.August, 2017);

        System.out.println("Original Date :" + myDate);
        System.out.println("Weekday :" + myDate.weekday());
        System.out.println("Day of Month :" + myDate.dayOfMonth());
        System.out.println("Day of Year :" + myDate.dayOfYear());
        System.out.println("Month :" + myDate.month());
        System.out.println("Year :" + myDate.year());

        int serialNum = myDate.serialNumber();

        System.out.println("Serial Number :" + serialNum);
    }

    private static void case4() {
        System.out.println("--------------CASE 4--------------");
        System.out.println("Today :" + Date.todaysDate());
        System.out.println("Min Date :" + Date.minDate());
        System.out.println("Max Date :" + Date.maxDate());
        System.out.println("Is Leap :" + Date.isLeap(2011));
        System.out.println("End of Month :" + Date.endOfMonth(new Date(4, Month.August, 2009)));
        System.out.println("Is Month End :" + Date.isEndOfMonth(new Date(29, Month.September, 2009)));
        System.out.println("Is Month End :" + Date.isEndOfMonth(new Date(30, Month.September, 2009)));
        System.out.println("Next WD :" + Date.nextWeekday(new Date(1, Month.September, 2009), Weekday.Friday));
        System.out.println("n-th WD :" + Date.nthWeekday(3, Weekday.Wednesday, Month.September, 2009));
    }

    private static void case5() {
        System.out.println("--------------CASE 5--------------");
        Date d = Settings.instance().getEvaluationDate();
        System.out.println("Eval Date :" + d);

        Settings.instance().setEvaluationDate(new Date(5, Month.January, 1995));
        d = Settings.instance().getEvaluationDate();
        System.out.println("New Eval Date :" + d);
    }
}
