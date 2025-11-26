package com.cgvsu.rasterizationfxapp;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;

import com.cgvsu.rasterization.*;
import javafx.scene.paint.Color;

public class RasterizationController {

    @FXML
    AnchorPane anchorPane;
    @FXML
    private Canvas canvas;

    @FXML
    private void initialize() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Очищаем canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Рисуем РОВНЫЙ треугольник (равнобедренный)
        Rasterization.fillTriangleBarycentric(
                gc,
                300, 100, Color.RED,    // Верхняя вершина по центру
                150, 300, Color.GREEN,  // Левая нижняя вершина
                450, 300, Color.BLUE    // Правая нижняя вершина
        );

        // Рисуем линии алгоритмом Брезенхэма (один цвет для всей линии)
//        Rasterization.drawLineBresenham(gc, 50, 50, 300, 100, Color.YELLOW);
//        Rasterization.drawLineBresenham(gc, 300, 100, 200, 300, Color.PURPLE);
//        Rasterization.drawLineBresenham(gc, 200, 300, 50, 50, Color.CYAN);
    }
}