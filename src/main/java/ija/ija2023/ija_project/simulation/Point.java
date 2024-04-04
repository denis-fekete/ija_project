/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ija.ija2023.ija_project.simulation;
/**
 *
 * @author Diony
 */
public class Point{
    protected double x;
    protected double y;
    
    public Point()
    {
        this.x = 0;
        this.y = 0;
    }
    
    public Point(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    
    public double getX()
    {
        return this.x;
    }
    
    public double getY()
    {
        return this.y;
    }

    public Point setY(double y) {
        this.y = y;
        return  this;
    }

    public Point setX(double x)
    {
        this.x = x;
        return this;
    }
    
    public Point SetY(double y)
    {
        this.y = y;
        return this;
    }
    
    public Point getPos()
    {
        return this;
    }
    
    public Point setPos(Point point)
    {
        this.x = point.x;
        this.y = point.y;
        return this;
    }
    
    public Point setPos(double x, double y)
    {
        this.x = x;
        this.y = y;
        return this;
    }
    /**
     * Adds Point from parameter to the this point and returns pointer
     * to this point
     * @param point
     * @return 
     */
    public Point add(Point point)
    {   
        this.x += point.x;
        this.y += point.y;
        
        return this;
    }
    
    /**
     * Adds point A to point B and creates new Point that will be
     * returned
     * @param a Point a
     * @param b Point b
     * @return New Point created from adding a and b points
     */
    public static Point add(Point a, Point b)
    {
        Point point = new Point();
        
        point.x = a.x + b.x;
        point.y = a.y + b.y;
        return point;
    }
    
    
    /**
     * Multiplies Point from parameter to the this point and returns pointer
     * to this point
     * @param point
     * @return 
     */
    public Point mul(Point point)
    {   
        this.x = point.x;
        this.y = point.y;
        
        return this;
    }
    
    /**
     * Multiplies point A with point B and creates new Point that will be
     * returned
     * @param a Point a
     * @param b Point b
     * @return New Point created from multipling a and b points
     */
    public static Point mul(Point a, Point b)
    {
        Point point = new Point();
        
        point.x = a.x * b.x;
        point.y = a.y * b.y;
        return point;
    }

    @Override
    public String toString() {
        return "Point(" +
                "x=" + x +
                ", y=" + y +
                ')';
    }
}
