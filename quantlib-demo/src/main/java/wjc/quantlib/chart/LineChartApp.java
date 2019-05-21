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

/**
 * @author: wangjunchao(王俊超)
 * @time: 2019-05-17 14:32
 **/
public class LineChartApp extends Application {

    private LineChart  chart;
    private NumberAxis xAxis;
    private NumberAxis yAxis;

    public Parent createContent() {
        xAxis = new NumberAxis("Values for X-AxisParam", 0, 250, 10);
        yAxis = new NumberAxis("Values for Y-AxisParam", 0, 10, 1);

        XYChart.Data[] data1 = new XYChart.Data[250];
        XYChart.Data[] data2 = new XYChart.Data[250];

        for (int i = 0; i < data1.length; i++) {
            data1[i] = new XYChart.Data<>(i, 4 + 2 *Math.random());
            data2[i] = new XYChart.Data<>(i, 10 *Math.random());
        }

        XYChart.Series series1 = new LineChart.Series("Series 1", FXCollections.observableArrayList(data1));
        XYChart.Series series2 = new LineChart.Series("Series 2", FXCollections.observableArrayList(data2));
        ObservableList<XYChart.Series<Double, Double>> lineChartData =
                FXCollections.observableArrayList(series1, series2);
        chart = new LineChart(xAxis, yAxis, lineChartData);


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
    public static void main(String[] args) {
        launch(args);
    }

}
