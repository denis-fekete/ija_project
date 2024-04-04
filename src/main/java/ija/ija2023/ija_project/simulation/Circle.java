/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ija.ija2023.ija_project.simulation;

/**
 *
 * @author Diony
 */
public class Circle extends Point{
    protected double rot;
    protected double radius;
    
    protected double cosRad;
    protected double sinRad;
    
    
    public Circle()
    {
        super(0, 0);
        this.radius = 0;
        this.rot = 0;
    }
    
    public Circle(double x, double y, double radius, double rot)
    {
        super(x, y);
        this.radius = radius;
        this.rot = rot;
    }
    
    /**
    * Changes circle center point at position specified by Point
    * @param point 
    */
    public Point moveTo(Point point)
    {
        this.x = point.x;
        this.y = point.y;
        
        return this;
    }
    
    /**
     * Changes circle rotation and calculates cosine and sinus values
     * @param angle
     * @return 
     */
    public Point rotate(double angle)
    {
        rot += angle;
        
        final int sign = (rot< 0)? -1 : 1;
        
        while(Math.abs(rot) >= 360.0)
        {
            rot -= sign * 360;
        }
        
        this.calculateSinCos();
        
        return this;
    }
    
    /**
     * Moves point in direction it is facing by distance, direction is calculated based 
     * on rotation
     * @param distance Units that circle will be moved by
     * @return 
     */
    public Point moveForward(double distance)
    {
        double xDelta = cosRad * distance;
        double yDelta = sinRad * distance;
        
        this.x += xDelta;
        this.y += yDelta;
        
        return this;
    }
    
    /**
     * Updates stored cosRad and sinRad values
     * @return 
     */
    protected void calculateSinCos()
    {
        double radians = rot * (Math.PI / 180.0);
        
        cosRad = Math.cos(radians);
        sinRad = Math.sin(radians);
    }
    
    /**
     * Returns Circles cosine value based on rotation
     * @return 
     */
    public double getCosRad()
    {
        return this.cosRad;
    }
    
    /**
    * Returns Circles sine value based on rotation
    * @return 
    */
    public double getSinRad()
    {
        return this.sinRad;
    }
    
    /**
     * Returns radius of circle
     * @return 
     */
    public double getRadius()
    {
        return this.radius;
    }
    
    /**
     * Returns rotation of circle in degrees
     * @return 
     */
    public double getRotation()
    {
        return this.rot;
    }
    
    /**
     * Sets value of circle radius
     * @param radius new value to be set
     * @return 
     */
    public Point setRadius(double radius)
    {
        this.radius = radius;
        return this;
    }
    
    /**
     * Sets  value of circle rotation
     * @param rot new value to be set
     * @return 
     */
    public Point setRotation(double rot)
    {
        this.rot = rot;
        return this;
    }

    static boolean intersect(Circle a, Circle b)
    {
        return Circle.intersect(a.getPos(), a.getRadius(), b.getPos(), b.getRadius());
    }
    
    
    static boolean intersect(Point aCenter, double aRadius, 
            Point bCenter, double bRadius)
    {
        final double radius = aRadius + bRadius;

        // Check if delta on X-axis between objects is less than detection 
        // radius + radius of other object
        if((aCenter.x - bCenter.x) * (aCenter.x - bCenter.x) <= radius * radius)
        {
            // Check if delta on Y-axis between objects is less than detection 
            // radius + radius of other object
            if((aCenter.y - bCenter.y) * (aCenter.y - bCenter.y) <= radius * radius)
            {
                return true;
            }
        }

        return false;
    }
}
