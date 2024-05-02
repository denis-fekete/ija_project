/**
 * Implementation of Circle class.  Circle class is for simulating circle
 * objects in simulation space
 *
 * @author Denis Fekete (xfeket01@fit.vutbr.cz)
 */

package ija.ija2023.ija_project.SimulationLib2D;

public class Circle extends Point{
    /**
     * Rotation of this Circle
     */
    protected double rot;

    /**
     * Radius of this Circle
     */
    protected double radius;

    /**
     * Stored cosine value for better performance (less cos() function calls)
     */
    protected double cosRad;

    /**
     * Stored sine value for better performance (less cos() function calls)
     */
    protected double sinRad;
    

    /**
     * Circle constructor
     * @param x Center x position of the Circle
     * @param y Center y position of the Circle
     * @param radius Radius of the Circle
     * @param rot Rotation angle of the circle
     */
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
    * Changes circle center point to the position specified by Point
    * @param point New center of this circle
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

        this.setPos(xDelta, yDelta);

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
}
