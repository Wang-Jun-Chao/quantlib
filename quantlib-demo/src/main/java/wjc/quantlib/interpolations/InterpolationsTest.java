package wjc.quantlib.interpolations;

import org.junit.Test;
import org.quantlib.Array;
import org.quantlib.BicubicSpline;
import org.quantlib.LinearInterpolation;
import org.quantlib.Matrix;

/**
 * @author: wangjunchao(王俊超)
 * @time: 2019-05-16 09:13
 **/
public class InterpolationsTest {
    @Test
    public void testingInterpolations1() {
        double[] xVec = {0.0, 1.0, 2.0, 3.0, 4.0};
        Array xArr = new Array(xVec.length);
        Array yArr = new Array(xVec.length);
        for (int i = 0; i < xVec.length; i++) {
            xArr.set(i, xVec[i]);
            yArr.set(i, Math.exp(xVec[i]));
        }


        LinearInterpolation linInt = new LinearInterpolation(xArr, yArr);

        System.out.println(String.format("Exp at 0.0  %-20.15f", linInt.getValue(0.0)));
        System.out.println(String.format("Exp at 0.5  %-20.15f", linInt.getValue(0.5)));
        System.out.println(String.format("Exp at 1.0  %-20.15f", linInt.getValue(1.0)));
    }

    @Test
    public void testingInterpolations2() {
        final int size = 10;
        Array xVec = new Array(size);
        Array yVec = new Array(size);

        for (int i = 0; i < size; i++) {
            xVec.set(i, i);
            yVec.set(i, i);
        }

        Matrix m = new Matrix(xVec.size(), yVec.size());

        for (int i = 0; i < xVec.size(); i++) {
            for (int j = 0; j < yVec.size(); j++) {
                m.set(i, j, Math.sin(xVec.get(i) + Math.sin(yVec.get(j))));
            }
        }

        BicubicSpline bicubIntp = new BicubicSpline(xVec, yVec, m);

        double x = 0.5;
        double y = 4.5;

        System.out.println(String.format("Analytical Value: %-20.15f", Math.sin(x) + Math.sin(y)));
        System.out.println(String.format("Bicubic Value:    %-20.15f", bicubIntp.getValue(x, y)));
    }
}
