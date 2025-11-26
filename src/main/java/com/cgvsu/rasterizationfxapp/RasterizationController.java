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


        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());


        Rasterization.fillTriangleBarycentric(
                gc,
                300, 100, Color.RED,    // Верхняя вершина по центру
                150, 300, Color.GREEN,  // Левая нижняя вершина
                450, 300, Color.BLUE    // Правая нижняя вершина
        );

    }
}