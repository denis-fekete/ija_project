/**
 * Implementation of Line class. Line is class for linking two points into a line
 *
 * @author Denis Fekete (xfeket01@fit.vutbr.cz)
 */

package ija.ija2023.ija_project.SimulationLib2D;

public class Line {
    /**
     * Point where line starts
     */
    Point start;

    /**
     * Point where line ends
     */
    Point end;

    /**
     * Constructor of Line class
     * @param start Start point
     * @param end End point
     */
    Line(Point start, Point end)
    {
        this.start = start;
        this.end = end;
    }

    /**
     * Constructor of Line class
     * @param startX X position of start point
     * @param startY Y position of start point
     * @param endX X position of end point
     * @param endY Y position of end point
     */
    Line(double startX, double startY, double endX, double endY)
    {
        this.start = new Point(startX, startY);
        this.end = new Point(endX, endY);
    }
}
