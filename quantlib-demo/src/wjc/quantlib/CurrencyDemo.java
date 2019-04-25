package wjc.quantlib;

import org.quantlib.CNYCurrency;
import org.quantlib.Currency;
import org.quantlib.EURCurrency;
import org.quantlib.USDCurrency;

/**
 * @author: wangjunchao(王俊超)
 * @time: 2019-04-25 17:41
 **/
public class CurrencyDemo {
    public static void main(String[] args) {
        case1();
    }

    private static void case1() {
        System.out.println("--------------CASE 1--------------");
        USDCurrency usd = new USDCurrency();
        CNYCurrency cny = new CNYCurrency();

        System.out.println(String.format("%-20s%s", "USDCurrency", "CNYCurrency"));

        System.out.println(String.format("%-20s%s", usd.name(), cny.name()));
        System.out.println(String.format("%-20s%s", usd.code(), cny.code()));
        System.out.println(String.format("%-20s%s", usd.numericCode(), cny.numericCode()));
        System.out.println(String.format("%-20s%s", usd.symbol(), cny.symbol()));
        System.out.println(String.format("%-20s%s", usd.fractionSymbol(), cny.fractionSymbol()));
        System.out.println(String.format("%-20s%s", usd.fractionsPerUnit(), cny.fractionsPerUnit()));
        System.out.println(String.format("%-20s%s", usd.format(), cny.format()));

        Currency c1 = new Currency();
        Currency c2 = new EURCurrency();
        Currency c3 = new USDCurrency();
        ;
        Currency c4 = c2;

        boolean e1 = c1.empty();
        boolean e2 = c2.empty();
        boolean e3 = c3.empty();
        boolean e4 = c4.empty();

        System.out.println(String.format("%s, %s, %s, %s", e1, e2, e3, e4));
    }
}
