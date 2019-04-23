package wjc.quantlib;

import org.quantlib.ActualActual;
import org.quantlib.Compounding;
import org.quantlib.Frequency;
import org.quantlib.InterestRate;

/**
 * @author: wangjunchao(王俊超)
 * @time: 2019-04-23 09:37
 **/
public class InterestRateDemo {
    public static void main(String[] args) {
        // 名义报价的年化利率
        double annualRate = 0.05;
        // 按照 calendar day 计算计息日
        ActualActual dayCount = new ActualActual();
        // 复利计算
        Compounding compoundType = Compounding.Compounded;
        // 每年付息一次
        Frequency frequency = Frequency.Annual;
        InterestRate interestRate = new InterestRate(annualRate, dayCount, compoundType, frequency);

        // InterestRate类的compoundFactor方法返回特定年限后的一元钱的终值，其实就是终值系数。
        double v = interestRate.compoundFactor(2.0);
        System.out.println(v);
    }
}
