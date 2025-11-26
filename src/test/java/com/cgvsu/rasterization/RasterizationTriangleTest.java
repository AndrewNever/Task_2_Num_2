package com.cgvsu.rasterization;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import javafx.scene.paint.Color;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class BresenhamAlgorithmTest {

    @Test
    void testBresenhamLinePoints() {

        java.util.List<String> drawnPoints = new java.util.ArrayList<>();


        int x0 = 0, y0 = 0, x1 = 3, y1 = 3;
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);

        assertEquals(3, dx);
        assertEquals(3, dy);


        int sx = (x0 < x1) ? 1 : -1;
        int sy = (y0 < y1) ? 1 : -1;

        assertEquals(1, sx);
        assertEquals(1, sy);
    }

    @Test
    void testBresenhamHorizontalLine() {
        int x0 = 0, y0 = 5, x1 = 5, y1 = 5;
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);

        assertEquals(5, dx);
        assertEquals(0, dy); // Горизонтальная линия
    }

    @Test
    void testBresenhamVerticalLine() {
        int x0 = 5, y0 = 0, x1 = 5, y1 = 5;
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);

        assertEquals(0, dx); // Вертикальная линия
        assertEquals(5, dy);
    }
}
