package wjc.quantlib.chart;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: wangjunchao(王俊超)
 * @time: 2019-05-17 14:32
 **/
public class LineChartApp2 extends Application {

    public static class AxisParam {
        private String axisLabel;
        private double lowerBound;
        private double upperBound;
        private double tickUnit;

        public AxisParam(String axisLabel, double lowerBound, double upperBound, double tickUnit) {
            this.axisLabel = axisLabel;
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
            this.tickUnit = tickUnit;
        }
    }

    private static AxisParam          xAxisParam;
    private static AxisParam          yAxisParam;
    private static List<String>       nameList = new ArrayList<>();
    private static List<List<Number>> dataList = new ArrayList<>();

    private Parent createContent() {
        NumberAxis xAxis = new NumberAxis(xAxisParam.axisLabel, xAxisParam.lowerBound, xAxisParam.upperBound, xAxisParam.tickUnit);
        NumberAxis yAxis = new NumberAxis(yAxisParam.axisLabel, yAxisParam.lowerBound, yAxisParam.upperBound, yAxisParam.tickUnit);


        List<XYChart.Series> seriesList = new ArrayList<>(nameList.size());

        for (int i = 0; i < dataList.size(); i++) {
            XYChart.Data[] array = new XYChart.Data[dataList.get(i).size()];

            for (int j = 0; j < array.length; j++) {
                array[j] = new XYChart.Data<>(j, dataList.get(i).get(j));
            }

            XYChart.Series series = new LineChart.Series(nameList.get(i), FXCollections.observableArrayList(array));
            seriesList.add(series);
        }


        ObservableList<XYChart.Series> lineChartData = FXCollections.observableArrayList(seriesList);
        LineChart chart = new LineChart(xAxis, yAxis, lineChartData);


        final String stockLineChartCss = getClass().getResource("/StockLineChart.css").toExternalForm();
        chart.setCreateSymbols(false);
        chart.getStylesheets().add(stockLineChartCss);

        return chart;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent(), 1000, 800));
        primaryStage.show();
    }

    /**
     * Java main for when running without JavaFX launcher
     */
    public static void draw(AxisParam xAxisParam, AxisParam yAxisParam, List<String> nameList, List dataList) {
        LineChartApp2.xAxisParam = xAxisParam;
        LineChartApp2.yAxisParam = yAxisParam;
        LineChartApp2.nameList = nameList;
        LineChartApp2.dataList = dataList;
        launch();
    }

}
