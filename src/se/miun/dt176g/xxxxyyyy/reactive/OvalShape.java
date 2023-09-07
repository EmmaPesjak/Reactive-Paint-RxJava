package se.miun.dt176g.xxxxyyyy.reactive;

import java.awt.*;

/**
 * <h1>OvalShape</h1> Creates an Oval-object.
 * Concrete class which extends Shape, representing an oval.
 * @author 	Emma Pesjak
 * @version 1.0
 * @since 	2023-09-07
 */
public class OvalShape extends Shape {
    private Point startPoint;
    private Point endPoint;
    private final Color color;
    private final int thickness;

    /**
     * Constructor that sets the color and thickness of the rectangle.
     * @param color is the color.
     * @param thickness is the thickness.
     */
    public OvalShape(Color color, int thickness) {
        this.color = color;
        this.thickness = thickness;
    }

    /**
     * Accessor for setting the start point.
     * @param startPoint is the start point.
     */
    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    /**
     *  Accessor for setting the end point.
     * @param endPoint is the end point.
     */
    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(Graphics g) {
        if (startPoint != null && endPoint != null) {
            // Cast to Graphics2D to be able to set the stroke size.
            Graphics2D g2d = (Graphics2D) g;

            // Set the color and stroke (thickness) for drawing.
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(thickness));

            int x1 = Math.min(startPoint.x, endPoint.x);
            int y1 = Math.min(startPoint.y, endPoint.y);
            int width = Math.abs(startPoint.x - endPoint.x);
            int height = Math.abs(startPoint.y - endPoint.y);

            // Draw the oval based on the calculated dimensions.
            g2d.drawOval(x1, y1, width, height);
        }
    }
}