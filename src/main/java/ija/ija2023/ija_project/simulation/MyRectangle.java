/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ija.ija2023.ija_project.simulation;

import java.text.DateFormatSymbols;

/**
 *
 * @author Diony
 */
public class MyRectangle extends Circle {
    double width;
    double height;
    
    Point LB; // left bottom point of rectangle
    Point LT; // left top point of rectangle
    Point RB; // right bottom point of rectangle
    Point RT; // right top point of rectangle
    
    private MyRectangle(double x, double y, double w, double h, double rot)
    {
        super(x, y, Math.sqrt( (w * w + h * h) / 4 ), rot);

        this.width = w;
        this.height = h;

        LB = new Point();
        LT = new Point();
        RB = new Point();
        RT = new Point();

    }
    
    public static MyRectangle create(double x, double y, double w, double h, double rot)
    {
        // create rect object
        MyRectangle rect = new MyRectangle(x, y, w, h, rot);
        // update points

        rect.updatePoints();
        // return created object
        return rect;
    }

    @Override
    public String toString() {
        return "Rectangle Center(x=" + x + ", y=" + y + ")\n" +
                "LT: " + LT.toString() +
                "\tRT: " + RT.toString() +
                "\nLB: " + LB.toString() +
                "\tRB: " + RB.toString();
    }

    @Override
    public MyRectangle moveTo(Point point)
    {
        super.moveTo(point);
        this.updatePoints();
        return this;
    }
    
    @Override
    public MyRectangle moveForward(double distance)
    {
        // Calcualte delta value for moving in X and Y direction
        double xDelta = cosRad * distance;
        double yDelta = sinRad * distance;

        // Apply deltas to the current possition
        x += xDelta;
        y += yDelta;

        Point p = new Point(xDelta, yDelta);
        LB.add(p);
        RB.add(p);
        RT.add(p);
        LT.add(p);
        
        return this;
    }
    
    @Override
    public MyRectangle rotate(double angle)
    {
        super.rotate(angle);
        this.updatePoints();
        return this;
    }
    
    protected Point updateSinglePoint(Point center, Point p)
    {
        // Calculate translated position
        double tmpX = p.x - center.x;
        double tmpY = p.y - center.y;
        // Calculate rotated position
        double rotatedX = (tmpX * cosRad) - (tmpY * sinRad);
        double rotatedY = (tmpX * sinRad) + (tmpY * cosRad);
        // Transle back to original position with rotated position
        p.setPos(rotatedX + center.x, rotatedY + center.y);
        
        return p;
    }
    
    protected void calculateCornersWithNoRotation()
    {
        // Calculate constants width/2 and heigth/2
        final double w2 = width / 2;
        final double h2 = height / 2;

        // Calculate corner points with no rotation
        LB.setPos(x - w2, y - h2);
        LT.setPos(x - w2, y + h2);
        RB.setPos(x + w2, y - h2);
        RT.setPos(x + w2, y + h2);
    }
    
    protected MyRectangle updatePoints(double cosRad, double sinRad)
    {
        calculateCornersWithNoRotation();

        Point center = new Point(this.x, this.y);

        // Update stored sinus and cosinus values
        calculateSinCos();

        // Calculate corners with rotation
        LB.setPos(updateSinglePoint(center, LB));
        LT.setPos(updateSinglePoint(center, LT));
        RB.setPos(updateSinglePoint(center, RB));
        RT.setPos(updateSinglePoint(center, RT));

        return this;
    }
    
    protected MyRectangle updatePoints()
    {
        return updatePoints(this.cosRad, this.sinRad);
    }
    
    /**
     * Break rectangle into lines, based on edge returns Line made from 
     * rectangle points
     * edge = 0 : bottom line
     * edge = 1 : right line
     * edge = 2 : top line
     * edge = 3 : left line
     * @param edge parameter that decides which Line should be returned
     * @return 
     */
    protected Line breakIntoEdges(int edge)
    {
        switch(edge % 4)
        {
            // return bottom line
            case 0: return new Line(this.LB, this.RB);
            // return right line
            case 1: return new Line(this.RB, this.RT);
            // return top line
            case 2: return new Line(this.RT, this.LT);
            // return left line
            case 3: return new Line(this.LT, this.LB);
            default: return null;
        }
    }
    
    public boolean pointInRectangle(Point point)
    {
        return MyRectangle.pointInRectangle(point, this);
    }
    
    public static boolean pointInRectangle(Point point, MyRectangle rect)
    {
        if(Line.pointOnLeftSideStatic(point, rect.LB, rect.RB))
        {
            if(Line.pointOnLeftSideStatic(point, rect.RB, rect.RT))
            {
                if(Line.pointOnLeftSideStatic(point, rect.RT, rect.LT))
                {
                    if(Line.pointOnLeftSideStatic(point, rect.LT, rect.LB))
                    {
                        return true;
                    }
                }
            }
        }

    return false;
    }
    
    boolean intersects(MyRectangle other)
    {
        // Check points of rectangle
        if(MyRectangle.pointInRectangle(LB, other)) {return true; }
        if(MyRectangle.pointInRectangle(RB, other)) {return true; }
        if(MyRectangle.pointInRectangle(RT, other)) {return true; }
        if(MyRectangle.pointInRectangle(LT, other)) {return true; }

        // Check whenever edges of rectangle intesect
        for(int i = 0; i < 4; i++)
        {
            Line lineA = breakIntoEdges(i);

            for (int j = 0; j < 4; j++)
            {
                Line lineB = breakIntoEdges(j);

                Point intersectionPoint = new Point();
                // Check if lines intersect
                if(Line.linesIntersects(lineA, lineB, intersectionPoint))
                {
                    // Check if found point is in both rectangles
                    if(MyRectangle.pointInRectangle(intersectionPoint, this))
                    {
                        if(MyRectangle.pointInRectangle(intersectionPoint, other))
                        {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Point getLB() {
        return LB;
    }

    public Point getLT() {
        return LT;
    }

    public Point getRT() {
        return RT;
    }

    public Point getRB() {
        return RB;
    }

    public double[] simulationColliderXPoints()
    {

        return new double[]{
                this.getLB().getX(),
                this.getRB().getX(),
                this.getRT().getX(),
                this.getLT().getX() };
    }

    public double[] simulationColliderYPoints()
    {

        return new double[]{
                this.getLB().getY(),
                this.getRB().getY(),
                this.getRT().getY(),
                this.getLT().getY() };
    }
}
