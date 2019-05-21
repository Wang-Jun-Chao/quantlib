package wjc.quantlib.stochastic_process;

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
        xAxis = new NumberAxis("Values for X-Axis", 0, 250, 0.001);
        yAxis = new NumberAxis("Values for Y-Axis", 0, 10, 1);

        XYChart.Data[] data1 = new XYChart.Data[250] ;
        XYChart.Data[] data2 = new XYChart.Data[250];

        for (int i = 0; i < data1.length; i++) {
            data1[i] = new XYChart.Data<>(i, 1+ Math.random());
            data2[i] = new XYChart.Data<>(i, 5 + Math.random());
        }

        ObservableList<XYChart.Series<Double, Double>> lineChartData =
                FXCollections.observableArrayList(
                        new LineChart.Series("Series 1", FXCollections.observableArrayList(data1)),
                        new LineChart.Series("Series 2", FXCollections.observableArrayList(data2))
                );
        chart = new LineChart(xAxis, yAxis, lineChartData);


        final String stockLineChartCss =
                getClass().getResource("StockLineChart.css").toExternalForm();
        String style = ".chart-series-line {    \n" +
                "    -fx-stroke-width: 2px;\n" +
                "    -fx-effect: null;\n" +
                "}";
        chart.getStylesheets().add(style);

        return chart;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    /**
     * Java main for when running without JavaFX launcher
     */
    public static void main(String[] args) {
        launch(args);
    }

}
