package wjc.quantlib;

import org.quantlib.Euribor;
import org.quantlib.Period;
import org.quantlib.TimeUnit;

/**
 * @author: wangjunchao(王俊超)
 * @time: 2019-04-25 17:08
 **/
public class IndexDemo {
    public static void main(String[] args) {
        case1();
    }

    private static void case1() {
        System.out.println("--------------CASE 1--------------");
        Period tensor = new Period(3, TimeUnit.Months);

        Euribor index = new Euribor(tensor);

        System.out.println(String.format("%-20s: %s", "Name", index.familyName()));
        System.out.println(String.format("%-20s: %s", "BDC", index.businessDayConvention()));
        System.out.println(String.format("%-20s: %s", "End of Month rule ?", index.endOfMonth()));
        System.out.println(String.format("%-20s: %s", "Tenor", index.tenor()));
        System.out.println(String.format("%-20s: %s", "Calendar", index.endOfMonth()));
        System.out.println(String.format("%-20s: %s", "Day Counter", index.dayCounter()));
        System.out.println(String.format("%-20s: %s", "Currency", index.currency()));
    }
}
