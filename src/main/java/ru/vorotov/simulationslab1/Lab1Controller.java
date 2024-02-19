package ru.vorotov.simulationslab1;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;

import java.util.Timer;
import java.util.TimerTask;

public class Lab1Controller {
    @FXML
    private TextField stepField;
    @FXML
    private LineChart<Double, Double> lineChart;
    @FXML
    private TextField heightField;
    @FXML
    private TextField speedField;
    @FXML
    private TextField angleField;
    @FXML
    private TextField sizeField;
    @FXML
    private TextField weightField;
    @FXML
    private TextField distanceField;
    @FXML
    private TextField maxHeightField;
    @FXML
    private TextField vxField;
    @FXML
    private TextField vyField;
    @FXML
    private TextField timeField;


    private final double g = 9.81;
    private final double C = 0.15;
    private final double rho = 1.29;
    double height, angle, speed, S, m;
    double sina, cosa, beta, k;
    double t, x, y, dt, vx, vy, maxHeight;

    public void onStartButtonClick(ActionEvent actionEvent) {
        // инициализация графика
        XYChart.Series<Double, Double> series = new XYChart.Series<>();
        lineChart.getData().add(series);
        lineChart.getXAxis().setAutoRanging(true);
        lineChart.getYAxis().setAutoRanging(true);
        // модель
        height = Double.parseDouble(heightField.getText());
        angle = Double.parseDouble(angleField.getText());
        speed = Double.parseDouble(speedField.getText());
        S = Double.parseDouble(sizeField.getText());
        m = Double.parseDouble(weightField.getText());
        dt = Double.parseDouble(stepField.getText());
        series.setName("Step " + dt);

        cosa = Math.cos(angle * Math.PI / 180);
        sina = Math.sin(angle * Math.PI / 180);

        beta = 0.5 * C * S * rho;
        k = beta / m;

        t = 0;
        x = 0;
        y = height;
        maxHeight = 0;
        vx = speed * cosa;
        vy = speed * sina;

        series.getData().add(new XYChart.Data<>(x, y));

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                double vx_old = vx;
                double vy_old = vy;

                double root = Math.sqrt(vx * vx + vy * vy);
                t = t + dt;

                vx = vx_old - k * vx_old * root * dt;
                vy = vy_old - (g + k * vy_old * root) * dt;
                x = x + vx * dt;
                y = y + vy * dt;
                if (y > maxHeight) {
                    maxHeight = y;
                }

                maxHeightField.setText(String.valueOf(maxHeight));
                distanceField.setText(String.valueOf(x));
                vxField.setText(String.valueOf(vx));
                vyField.setText(String.valueOf(vy));
                timeField.setText(String.valueOf(t));

                Platform.runLater(() -> series.getData().add(new XYChart.Data<>(x, y)));

                if (y <= 0) {
                    timer.cancel();
                }
            }
        };


        timer.scheduleAtFixedRate(task, 0, 100);

    }

    public void onClearButtonClick(ActionEvent actionEvent) {
        lineChart.getData().clear();
    }
}