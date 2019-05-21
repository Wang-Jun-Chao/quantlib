package wjc.quantlib.optimizer;

/**
 * @author: wangjunchao(王俊超)
 * @time: 2019-05-17 08:28
 **/
public class RosenBrockFunction {
    public double calculate(double x, double y) {
        return (1 - x) * (1 - x) + 100.0 * Math.pow((y - Math.pow(x, 2)), 2);
    }
}
