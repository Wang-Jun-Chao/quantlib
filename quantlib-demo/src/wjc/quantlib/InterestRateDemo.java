package wjc.quantlib;

import org.quantlib.ActualActual;
import org.quantlib.Compounding;
import org.quantlib.Date;
import org.quantlib.Frequency;
import org.quantlib.InterestRate;
import org.quantlib.Month;
import org.quantlib.Period;
import org.quantlib.TimeUnit;

/**
 * @author: wangjunchao(王俊超)
 * @time: 2019-04-23 09:37
 **/
public class InterestRateDemo {
    public static void main(String[] args) {
        case1();
        case2();
    }

    private static void case1() {
        System.out.println("--------------CASE 1--------------");
        // 名义报价的年化利率
        double annualRate = 0.05;
        // 按照 calendar day 计算计息日
        ActualActual dayCount = new ActualActual();
        // 复利计算
        Compounding compoundType = Compounding.Compounded;
        // 每年付息一次
        Frequency frequency = Frequency.Annual;
        InterestRate interestRate = new InterestRate(
                // 浮点数，收益率大小
                annualRate,
                // 对象，配置天数计算规则
                dayCount,
                // 整数，配置计息方式，取值范围是 Compounding 的一些预留变量
                compoundType,
                // 整数，配置付息频率, 取值范围是 Frequency 的一些预留变量
                frequency);

        // InterestRate类的compoundFactor方法返回特定年限后的一元钱的终值，其实就是终值系数。
        double v = interestRate.compoundFactor(2.0);
        System.out.println(v);
    }

    private static void case2() {
        System.out.println("--------------CASE 2--------------");
        ActualActual dc = new ActualActual();
        InterestRate myRate = new InterestRate(0.0341, dc, Compounding.Simple, Frequency.Annual);
        System.out.println(myRate);

        Date d1 = new Date(10, Month.September, 2009);
        Date d2 = d1.add(new Period(3, TimeUnit.Months));

        double compFact = myRate.compoundFactor(d1, d2);
        System.out.println("Compound Factor: " + compFact);
        System.out.println("Discount Factor: " + myRate.discountFactor(d1, d2));

        System.out.println("Equivalent Rate: " + myRate.equivalentRate(
                dc, Compounding.Continuous, Frequency.Semiannual, d1, d2));

        InterestRate implRate = InterestRate.impliedRate(
                compFact, dc, Compounding.Simple, Frequency.Annual, d1, d2);
        System.out.println("Implied Rate from Comp Fact : " + implRate);

    }
}
