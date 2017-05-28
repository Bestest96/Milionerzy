package view;

import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 * The {@link view.AudienceView} class represents the audience poll look after voting.
 */
public final class AudienceView {
    /**
     * A stage to display the chart.
     */
    private final Stage chartStage;
    /**
     * A bar chart which represents the whole look of the poll.
     */
    private final BarChart<String, Number> poll;
    /**
     * The series of data to represent percents gathered by each answer.
     */
    private final XYChart.Series<String, Number> series;

    /**
     * An {@link view.AudienceView} class constructor.
     */
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
    }

    /**
     * A getter for the poll.
     * @return the poll.
     */
    public BarChart<String, Number> getPoll() {
        return poll;
    }

    /**
     * A getter for the series.
     * @return the series of the chart.
     */
    public XYChart.Series<String, Number> getSeries() {
        return series;
    }

    /**
     * A getter for the audience view stage,
     * @return the audience view stage.
     */
    public Stage getChartStage() {
        return chartStage;
    }

    /**
     * The method that displays the chart.
     */
    public void display() {
        chartStage.setAlwaysOnTop(true);
        chartStage.show();
    }
}
