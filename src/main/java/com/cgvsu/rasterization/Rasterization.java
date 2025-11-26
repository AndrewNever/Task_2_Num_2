package com.cgvsu.rasterization;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class Rasterization {

    public static void drawRectangle(
            final GraphicsContext graphicsContext,
            final int x, final int y,
            final int width, final int height,
            final Color color)
    {
        final PixelWriter pixelWriter = graphicsContext.getPixelWriter();

        for (int row = y; row < y + height; ++row)
            for (int col = x; col < x + width; ++col)
                pixelWriter.setColor(col, row, color);
    }

    public static void fillTriangleBarycentric(
            final GraphicsContext graphicsContext,
            final double x0, final double y0, final Color c0,
            final double x1, final double y1, final Color c1,
            final double x2, final double y2, final Color c2)
    {
        final PixelWriter pixelWriter = graphicsContext.getPixelWriter();
        final int width = (int) Math.ceil(graphicsContext.getCanvas().getWidth());
        final int height = (int) Math.ceil(graphicsContext.getCanvas().getHeight());
        fillTriangleBarycentric(pixelWriter, width, height,
                x0, y0, c0,
                x1, y1, c1,
                x2, y2, c2);
    }

    public static void fillTriangleBarycentric(
            final PixelWriter pixelWriter,
            final int surfaceWidth,
            final int surfaceHeight,
            final double x0, final double y0, final Color c0,
            final double x1, final double y1, final Color c1,
            final double x2, final double y2, final Color c2)
    {

        int minX = (int) Math.floor(Math.min(x0, Math.min(x1, x2)));
        int maxX = (int) Math.ceil(Math.max(x0, Math.max(x1, x2)));
        int minY = (int) Math.floor(Math.min(y0, Math.min(y1, y2)));
        int maxY = (int) Math.ceil(Math.max(y0, Math.max(y1, y2)));


        minX = Math.max(minX, 0);
        minY = Math.max(minY, 0);
        maxX = Math.min(maxX, surfaceWidth - 1);
        maxY = Math.min(maxY, surfaceHeight - 1);


        final double area = edgeFunction(x0, y0, x1, y1, x2, y2);
        if (Math.abs(area) < 1e-8) {
            return;
        }


        for (int y = minY; y <= maxY; y++) {
            final double sampleY = y + 0.5;
            for (int x = minX; x <= maxX; x++) {
                final double sampleX = x + 0.5;

                double w0 = edgeFunction(x1, y1, x2, y2, sampleX, sampleY);
                double w1 = edgeFunction(x2, y2, x0, y0, sampleX, sampleY);
                double w2 = edgeFunction(x0, y0, x1, y1, sampleX, sampleY);

                w0 /= area;
                w1 /= area;
                w2 /= area;


                if (w0 >= -1e-8 && w1 >= -1e-8 && w2 >= -1e-8) {
                    final double r = clamp01(c0.getRed() * w0 + c1.getRed() * w1 + c2.getRed() * w2);
                    final double g = clamp01(c0.getGreen() * w0 + c1.getGreen() * w1 + c2.getGreen() * w2);
                    final double b = clamp01(c0.getBlue() * w0 + c1.getBlue() * w1 + c2.getBlue() * w2);
                    final double a = clamp01(c0.getOpacity() * w0 + c1.getOpacity() * w1 + c2.getOpacity() * w2);
                    pixelWriter.setColor(x, y, new Color(r, g, b, a));
                }
            }
        }
    }


    public static void drawLineBresenham(
            final GraphicsContext graphicsContext,
            final int x0, final int y0,
            final int x1, final int y1,
            final Color color)
    {
        final PixelWriter pixelWriter = graphicsContext.getPixelWriter();
        drawLineBresenham(pixelWriter, x0, y0, x1, y1, color);
    }


    public static void drawLineBresenham(
            final PixelWriter pixelWriter,
            final int x0, final int y0,
            final int x1, final int y1,
            final Color color)
    {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);


        int sx = (x0 < x1) ? 1 : -1;
        int sy = (y0 < y1) ? 1 : -1;

        int err = dx - dy;
        int currentX = x0;
        int currentY = y0;

        while (true) {

            pixelWriter.setColor(currentX, currentY, color);


            if (currentX == x1 && currentY == y1) {
                break;
            }

            int err2 = 2 * err;

            if (err2 > -dy) {
                err -= dy;
                currentX += sx;
            }

            if (err2 < dx) {
                err += dx;
                currentY += sy;
            }
        }
    }

    private static double edgeFunction(double ax, double ay, double bx, double by, double px, double py) {
        return (px - ax) * (by - ay) - (py - ay) * (bx - ax);
    }

    private static double clamp01(double v) {
        if (v < 0.0) return 0.0;
        if (v > 1.0) return 1.0;
        return v;
    }
}