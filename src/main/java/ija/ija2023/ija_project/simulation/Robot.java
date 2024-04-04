package ija.ija2023.ija_project.simulation;

import ija.ija2023.ija_project.visualization.Obstacle;

import java.util.ArrayList;

/**
 *
 * @author Diony
 */
public class Robot extends Circle {
    // Value of radius for collision detection
    public double detRadius;
    public MyRectangle colliderFwd;
    
    
    private Robot(double x, double y, double radius, double rot, double detRadius)
    {
        super(x, y, radius, rot);
        this.detRadius = detRadius;

        this.colliderFwd = MyRectangle.create(x + detRadius/2, y, detRadius, 2*radius, rot);
    }

    static public Robot create(double x, double y, double radius, double rot, double detRadius)
    {
        Robot newRobot = new Robot(x, y, radius, rot, detRadius);
        // update values by rotating
        newRobot.rotate(0);
        
        return newRobot;
    }
    
    @Override
    public Robot moveForward(double distance)
    {
        this.calculateSinCos();

        // Calcualte delta value for moving in X and Y direction
        double xDelta = this.getCosRad() * distance;
        double yDelta = this.getSinRad() * distance;

        // Apply deltas to the current possition
        x += xDelta;
        y += yDelta;

        // Apply movement to the collider
        final Point p = new Point(xDelta, yDelta);

        colliderFwd.x += xDelta;
        colliderFwd.y += yDelta;

        colliderFwd.LB.add(p);
        colliderFwd.RB.add(p);
        colliderFwd.RT.add(p);
        colliderFwd.LT.add(p);

        return this;
    }

    double dAbs(double val)
    {
        return (val > 0) ? val : -val;
    }

    @Override
    public Robot rotate(double angle)
    {
        this.rot += angle;

        // Calculate whenever rotation is negative or positive number
        final int sign = (this.rot < 0)? -1 : 1;

        // Normalize rotation
        while(dAbs(this.rot) >= 360.0)
        {
            // Subtract 360 from rotation
            this.rot -= sign * 360;
        }

        this.calculateSinCos();

        final double cosRadConst = this.getCosRad();
        final double sinRadConst = this.getSinRad();

        colliderFwd.setRotation(this.getRotation());

        // Calcualte delta value for moving in X and Y direction
        double xDelta = cosRadConst * this.detRadius / 2;
        double yDelta = sinRadConst * this.detRadius / 2;

        colliderFwd.x = this.x + xDelta;
        colliderFwd.y = this.y + yDelta;

        colliderFwd.updatePoints();

        return this;
    }

    public boolean obstacleDetection(ArrayList<Obstacle> validObstacles)
    {
        // Go through list of all other objects
        for(Obstacle obstacle : validObstacles)
        {
            // Store current other object
            MyRectangle other = obstacle.getSim();

            // First check if robot and obstacles radiuses intersect
            if( Circle.intersect(this.getPos(), this.detRadius, 
                    other.getPos(), other.getRadius()) )
            {
                if(this.colliderFwd.intersects(other))
                {   
                    return true;
                }
            }
        }

        return false;
    }

    public double getDetRadius() {
        return detRadius;
    }
}
