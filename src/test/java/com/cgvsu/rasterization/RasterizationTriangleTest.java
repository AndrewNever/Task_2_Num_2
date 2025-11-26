package com.cgvsu.rasterization;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RasterizationTriangleTest {

    @Test
    void testFillTriangleBarycentric_ColorInterpolationCenter() {
        int w = 100, h = 100;
        WritableImage img = new WritableImage(w, h);
        PixelWriter pw = img.getPixelWriter();

        // Треугольник почти равносторонний внутри холста
        double x0 = 10, y0 = 10; Color c0 = Color.RED;
        double x1 = 80, y1 = 10; Color c1 = Color.GREEN;
        double x2 = 45, y2 = 80; Color c2 = Color.BLUE;

        Rasterization.fillTriangleBarycentric(
                pw, w, h,
                x0, y0, c0,
                x1, y1, c1,
                x2, y2, c2
        );

        PixelReader pr = img.getPixelReader();

        // Семпл близко к центру треугольника
        int sx = 45, sy = 33;
        Color sampled = pr.getColor(sx, sy);

        // Проверяем, что цвет не равен ни одной вершине и является ненулевой смесью
        assertNotEquals(0.0, sampled.getRed(), 1e-6);
        assertNotEquals(0.0, sampled.getGreen(), 1e-6);
        assertNotEquals(0.0, sampled.getBlue(), 1e-6);

        // И лежит в допустимых границах [0,1]
        assertTrue(sampled.getRed() >= 0 && sampled.getRed() <= 1);
        assertTrue(sampled.getGreen() >= 0 && sampled.getGreen() <= 1);
        assertTrue(sampled.getBlue() >= 0 && sampled.getBlue() <= 1);
    }

    @Test
    void testFillTriangleBarycentric_ClippingAndEdges() {
        int w = 20, h = 20;
        WritableImage img = new WritableImage(w, h);
        PixelWriter pw = img.getPixelWriter();

        // Треугольник частично вне холста
        double x0 = -10, y0 = 5; Color c0 = Color.WHITE;
        double x1 = 10, y1 = -10; Color c1 = Color.BLACK;
        double x2 = 30, y2 = 30; Color c2 = Color.RED;

        Rasterization.fillTriangleBarycentric(
                pw, w, h,
                x0, y0, c0,
                x1, y1, c1,
                x2, y2, c2
        );

        PixelReader pr = img.getPixelReader();

        // Точки в пределах холста должны быть закрашены где-то внутри
        boolean anyColored = false;
        for (int y = 0; y < 20 && !anyColored; y++) {
            for (int x = 0; x < 20 && !anyColored; x++) {
                Color col = pr.getColor(x, y);
                if (col.getOpacity() > 0) {
                    anyColored = true;
                }
            }
        }
        assertTrue(anyColored, "Ожидалась хотя бы одна закрашенная точка после клиппинга");
    }
}


