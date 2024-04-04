/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ija.ija2023.ija_project.simulation;

/**
 *
 * @author Diony
 */
public class Line {
    Point start;
    Point end;
    
    public Line(Point start, Point end)
    {
        this.start = start;
        this.end = end;
    }
    
    Line(double startX, double startY, double endX, double endY)
    {
        this.start.x = startX;
        this.start.y = startY;
        this.end.x = endX;
        this.end.y = endY;
    }
    

    boolean intersectsWith(Line other, Point intersectionPoint)
    {
        return linesIntersects(this, other, intersectionPoint);
    }

    static boolean linesIntersects(Line a, Line b, Point intersectionPoint)
    {
        double denominator = (  (b.end.y - b.start.y) * (a.end.x - a.start.x)
                          - (b.end.x - b.start.x) * (a.end.y - a.start.y)  );

        // Lines are parallel
        if(denominator == 0)
        {
            return false;
        }

        double ua = (   (b.end.x - b.start.x) * (a.start.y - b.start.y)
                     -   (b.end.y - b.start.y) * (a.start.x - b.start.x))
                    / denominator;
        double ub = (   (a.end.x - a.start.x) * (a.start.y - b.start.y)
                     -   (a.end.y - a.start.y) * (a.start.x - b.start.x))
                    / denominator;

        // is the intersection along the segments
        if(ua < 0 || ua > 1 || ub < 0 || ub > 1)
        {
            return false;
        }

        intersectionPoint.x = a.start.x + ua * (a.end.x - a.start.x);
        intersectionPoint.y = a.start.y + ua * (a.end.y - a.start.y);

        return true;
    }

    
    public boolean pointOnLeftSide(Point p)
    {
        return pointOnLeftSide(p, this);
    }

    public boolean pointOnLeftSide(Point p, Line line)
    {
        return Line.pointOnLeftSideStatic(p, line);
    }
    
    public boolean pointOnLeftSide(Point p, Point lineStart, Point lineEnd)
    {
        return Line.pointOnLeftSideStatic(p, new Line(lineStart, lineEnd));
    }

    static public boolean pointOnLeftSideStatic(Point p, Point lineStart, Point lineEnd)
    {
        return Line.pointOnLeftSideStatic(p, new Line(lineStart, lineEnd));
    }
    
    static public boolean pointOnLeftSideStatic(Point p, Line line)
    {
        double res = (line.end.x - line.start.x) * (p.y - line.start.y) - (p.x - line.start.x) * (line.end.y - line.start.y);

        // If res >= 0 point is on the line, or on the left side
        return (res >= 0);
    }
    

}
