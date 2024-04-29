package ija.ija2023.ija_project.SimulationLib2D;

public class Line {
    Point start;
    Point end;

    Line(Point start, Point end)
    {
        this.start = start;
        this.end = end;
    }

    Line(double startX, double startY, double endX, double endY)
    {
        this.start = new Point(startX, startY);
        this.end = new Point(endX, endY);
    }
}