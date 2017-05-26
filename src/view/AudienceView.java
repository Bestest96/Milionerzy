package view;

import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 * Created by Luki on 24.05.2017.
 */
public class AudienceView {
    private Stage chartStage;
    private BarChart<String, Number> poll;
    private XYChart.Series<String, Number> series;

    public AudienceView() {
        chartStage = new Stage();
        chartStage.setTitle("Wyniki publiczności");
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Odpowiedzi");
        yAxis.setLabel("Procent publicznosci");
        poll = new BarChart<>(xAxis, yAxis);
        poll.setTitle("Wyniki publiczności");
        series = new XYChart.Series<>();
        series.setName("Procent");
        poll.setCategoryGap(50.0);
        xAxis.setAnimated(false);
        yAxis.setAnimated(false);
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(100);
        poll.setAnimated(false);
        Scene scene = new Scene(poll, 400, 400);
        chartStage.setScene(scene);
        chartStage.setResizable(false);
        chartStage.setAlwaysOnTop(true);
    }

    public BarChart<String, Number> getPoll() {
        return poll;
    }

    public XYChart.Series<String, Number> getSeries() {
        return series;
    }

    public void display() {
        chartStage.showAndWait();
    }
}
