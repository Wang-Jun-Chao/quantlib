package wjc.quantlib;

import org.quantlib.BusinessDayConvention;
import org.quantlib.Calendar;
import org.quantlib.China;
import org.quantlib.Date;
import org.quantlib.DateGeneration;
import org.quantlib.Month;
import org.quantlib.Period;
import org.quantlib.Schedule;
import org.quantlib.TimeUnit;

/**
 * @author: wangjunchao(王俊超)
 * @time: 2019-04-23 22:38
 **/
public class ScheduleDemo {
    public static void main(String[] args) {
        case1();
        case2();
    }

    private static void case1() {
        System.out.println("--------------CASE 1--------------");
        Date effectiveDate = new Date(1, Month.January, 2017);
        Date terminationDate = new Date(1, Month.December, 2017);
        Period tenor = new Period(1, TimeUnit.Months);
        Calendar calendar = new China(China.Market.IB);
        BusinessDayConvention convention = BusinessDayConvention.Following;
        BusinessDayConvention terminationDateConvention = BusinessDayConvention.Following;
        DateGeneration.Rule rule = DateGeneration.Rule.Forward;
        boolean endOfMonth = false;

        Schedule mySched = new Schedule(
                // 日期，日历列表的起始日期，例如债券的起息日
                effectiveDate,
                // 日期，日历列表的结束日期，例如债券的到期日
                terminationDate,
                // 对象，相邻两个日期的间隔，例如债券的付息频率（1 年或 6 个月）或利率互换的利息重置频率（3 个月）
                tenor,
                // 日历表，生成日期所遵循的特定日历表
                calendar,
                // 对象，如何调整非工作日（除最后一个日期外），取值范围是 BusinessDayConvention 的一些预留变量。
                convention,
                // 对象，如果最后一个日期是非工作日，该如何调整，取值范围是 BusinessDayConvention 的一些预留变量。
                terminationDateConvention,
                // DateGeneration 的对象象，生成日期的规则。
                rule,
                // 如果起始日期在月末，是否要求其他日期也要安排在月末（除最后一个日期外）
                endOfMonth);
        // firstDate, nextToLastDate（可选）：日期，专门为生成方法 rule 提供的起始、结束日期（不常用）


        for (int i = 0; i < mySched.size(); i++) {
            System.out.println(mySched.date(i));
        }
    }

    private static void case2() {
        System.out.println("--------------CASE 2--------------");
        Date effectiveDate = new Date(1, Month.January, 2017);
        Date terminationDate = new Date(1, Month.December, 2017);
        Period tenor = new Period(1, TimeUnit.Months);
        Calendar calendar = new China(China.Market.IB);
        BusinessDayConvention convention = BusinessDayConvention.Following;
        BusinessDayConvention terminationDateConvention = BusinessDayConvention.Following;
        DateGeneration.Rule rule = DateGeneration.Rule.Forward;
        boolean endOfMonth = false;

        Schedule mySched = new Schedule(
                // 日期，日历列表的起始日期，例如债券的起息日
                effectiveDate,
                // 日期，日历列表的结束日期，例如债券的到期日
                terminationDate,
                // 对象，相邻两个日期的间隔，例如债券的付息频率（1 年或 6 个月）或利率互换的利息重置频率（3 个月）
                tenor,
                // 日历表，生成日期所遵循的特定日历表
                calendar,
                // 对象，如何调整非工作日（除最后一个日期外），取值范围是 BusinessDayConvention 的一些预留变量。
                convention,
                // 对象，如果最后一个日期是非工作日，该如何调整，取值范围是 BusinessDayConvention 的一些预留变量。
                terminationDateConvention,
                // DateGeneration 的对象象，生成日期的规则。
                rule,
                // 如果起始日期在月末，是否要求其他日期也要安排在月末（除最后一个日期外）
                endOfMonth);
        // firstDate, nextToLastDate（可选）：日期，专门为生成方法 rule 提供的起始、结束日期（不常用）


        mySched = mySched.until(new Date(15, Month.June, 2017));

        for (int i = 0; i < mySched.size(); i++) {
            System.out.println(mySched.date(i));
        }

        for (int i = 1; i < mySched.size(); i++) {
            System.out.println(String.format("%d-th internal is regular? %s", i, mySched.isRegular(i)));
        }
    }
}
