package wjc.quantlib;

import org.quantlib.Actual365Fixed;
import org.quantlib.AnalyticEuropeanEngine;
import org.quantlib.BlackConstantVol;
import org.quantlib.BlackScholesMertonProcess;
import org.quantlib.BlackVolTermStructureHandle;
import org.quantlib.Calendar;
import org.quantlib.China;
import org.quantlib.Date;
import org.quantlib.EuropeanExercise;
import org.quantlib.FlatForward;
import org.quantlib.Month;
import org.quantlib.Option;
import org.quantlib.PlainVanillaPayoff;
import org.quantlib.QuoteHandle;
import org.quantlib.Settings;
import org.quantlib.SimpleQuote;
import org.quantlib.VanillaOption;
import org.quantlib.YieldTermStructureHandle;

/**
 * @author: wangjunchao(王俊超)
 * @time: 2019-04-23 11:08
 **/
public class EuropeanOption {
    public static void main(String[] args) {
        //  1. 设置期权的五要素以及分红率和期权类型
        //  1.1 五要素
        //  1.1.1 到期日，不变要素
        Date maturityDate = new Date(31, Month.December, 2019);
        //  即期价格，主是现价，变化因素
        double spotPrice = 9.00;
        //  行权价，不变要素
        double strikePrice = 10.00;
        //  波动率，一年的水平波动率
        double volatility = 0.20;
        //  无风险利率
        double riskFreeRate = 0.001;
        //  1.2 分红率
        double dividendRate = 0.01;
        //  1.3 期权类型
        Option.Type optionType = Option.Type.Call;

        //  1.4 设置日期计算方式与使用地区
        Actual365Fixed dayCount = new Actual365Fixed();
//        var calendar = new UnitedStates();
        Calendar calendar = new China();
        //  1.5 计算期权价格的日期，也就是估值日，我们设为今天
        Date calculationDate = new Date(23, Month.April, 2019);
        Settings settings = Settings.instance();
        settings.setEvaluationDate(calculationDate);
        //  2. 利用上的设置配置一个欧式期权
        PlainVanillaPayoff payoff = new PlainVanillaPayoff(optionType, strikePrice);
        EuropeanExercise exercise = new EuropeanExercise(maturityDate);
        //  2.1 根据payoff与exercise完成欧式期权的构建
        VanillaOption europeanOption = new VanillaOption(payoff, exercise);

        //  3. 构造我们的BSM定价引擎
        //  3.1 处理股票当前价格
        QuoteHandle spotHandle = new QuoteHandle(new SimpleQuote(spotPrice));
        //  3.2 根据之前的无风险利率和日期计算方式，构建利率期限结构
        YieldTermStructureHandle flatTs = new YieldTermStructureHandle(new FlatForward(
                calculationDate, riskFreeRate, dayCount));
        //  3.3 设置分红率期限结构
        YieldTermStructureHandle dividendYield = new YieldTermStructureHandle(new FlatForward(
                calculationDate, dividendRate, dayCount));
        //  3.4 设置波动率结构
        BlackVolTermStructureHandle flatVolTs = new BlackVolTermStructureHandle(new BlackConstantVol(
                calculationDate, calendar, volatility, dayCount));
        //  3.5 构造BSM定价引擎
        BlackScholesMertonProcess bsmProcess = new BlackScholesMertonProcess(
                spotHandle,
                dividendYield,
                flatTs,
                flatVolTs);

        //  4. 使用BSM定价引擎计算
        europeanOption.setPricingEngine(new AnalyticEuropeanEngine(bsmProcess));
        double bsPrice = europeanOption.NPV();
        double delta = europeanOption.delta();
        double theta = europeanOption.theta();
        double gamma = europeanOption.gamma();
        double vega = europeanOption.vega();

        String format = String.format("%-20s%-20s%-20s%-20s%-20s", "price", "delta", "gamma", "theta", "vega");
        String format2 = String.format("%-20.10f%-20.10f%-20.10f%-20.10f%-20.10f", bsPrice, delta, gamma, theta, vega);
        System.out.println(format);
        System.out.println(format2);
        System.out.println("The theoretical price is " + bsPrice);

    }
}
