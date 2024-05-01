package ija.ija2023.ija_project.SimulationLib2D;

import java.util.ArrayList;

/**
 *
 * @author Diony
 */
public class Robot extends Circle {
    // Value of radius for collision detection
    public double detRadius;
    public Rect colliderFwd;

    /**
     * Constructor of Robot object
     * @param x Center X position to be set
     * @param y Center Y position to be set
     * @param radius Radius if this Robot
     * @param rot Rotation of this robot
     * @param detRadius Detection radius of this robot
     */
    private Robot(double x, double y, double radius, double rot, double detRadius)
    {
        super(x, y, radius, rot);
        this.detRadius = detRadius;

        this.colliderFwd = Rect.create(x + detRadius/2, y, detRadius, 2 * radius, rot);
    }

    /**
     * Creates Robot object, initializes and returns it
     * @param x Center X position to be set
     * @param y Center Y position to be set
     * @param radius Radius if this Robot
     * @param rot Rotation of this robot
     * @param detRadius Detection radius of this robot
     * @return Created and initialized Robot object
     */
    static public Robot create(double x, double y, double radius, double rot, double detRadius)
    {
        Robot newRobot = new Robot(x, y, radius, rot, detRadius);
        // update values by rotating
        newRobot.rotate(0);
        
        return newRobot;
    }

    /**
     * Moves this Robot forward in direction it is facing (based on `rotation`)
     * @param distance Value to move in forward facing direction
     */
    @Override
    public Robot moveForward(double distance)
    {
        this.calculateSinCos();

        // Calculate delta value for moving in X and Y direction
        double xDelta = this.getCosRad() * distance;
        double yDelta = this.getSinRad() * distance;

        // Apply deltas to the current position
        x += xDelta;
        y += yDelta;

        colliderFwd.moveInDirection(xDelta, yDelta);

        return this;
    }

    /**
     * Moves Robot to Point `p`
     * @param p New center position of Robot
     */
    public Robot moveTo(Point p)
    {
        this.x = p.x;
        this.y = p.y;

        rotate(0);

        return this;
    }

    double dAbs(double val)
    {
        return (val > 0) ? val : -val;
    }

    /**
     * Add rotation to this Rectangle, and update all points
     * @param angle Angle to be added to the Rectangle
     */
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

        // Fix colliderFwd position, rotate point around point
        this.calculateSinCos();

        colliderFwd.setRotation(this.getRotation());

        // Reset collider position as if it was not rotated
        colliderFwd.x = x + detRadius / 2;
        colliderFwd.y = y;
        // Translate back to origin
        colliderFwd.x -= this.x;
        colliderFwd.y -= this.y;
        // rotate it
        double xDelta = colliderFwd.x * cosRad - colliderFwd.y * sinRad;
        double yDelta = colliderFwd.x * sinRad + colliderFwd.y * cosRad;
        // translate point back
        colliderFwd.x = this.x + xDelta;
        colliderFwd.y = this.y + yDelta;

        colliderFwd.updatePoints(cosRad, sinRad);

        return this;
    }

    /**
     * Checks if Robots collides with any Obstacle from vector of obstacles
     * @param validObstacles Vector of obstacles that Robot can collide with
     * @return True if Robot collides with one of the Obstacles
     */
    public boolean obstacleDetection(ArrayList<Rect> validObstacles)
    {
        // Go through list of all other objects
        for(Rect other : validObstacles)
        {
            // First check if robot and obstacles radius intersect as if they were circles
            if(this.intersect(other))
            {
                if(colliderFwd.intersects(other))
                {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Checks if this Robot intersects with other Circle
     * @param other Pointer to the other Circle
     * @return True if this Robot collides with Circle
     */
    boolean intersect(Circle other)
    {
        double left = (this.x - other.getX()) * (this.x - other.getX()) +
                    (this.y - other.getY()) * (this.y - other.getY());

        return (left <= (this.detRadius + other.getRadius()) * (this.detRadius + other.getRadius()));
    }

    /**
     * Checks if line intersects with this robot
     * @param lineStart Point where line starts
     * @param lineEnd Point where line ends
     * @return True if line intersects with robot
     */
    boolean lineCircleIntersect(Point lineStart, Point lineEnd)
    {

        double lineX1 = lineEnd.x - lineStart.x;
        double lineY1 = lineEnd.y - lineStart.y;

        double lineX2 = this.x - lineStart.x;
        double lineY2 = this.y - lineStart.y;

        double lineLength = lineX1 * lineX1 + lineY1 * lineY1;

        double t = Math.max(0.0, Math.min(lineLength, (lineX1 * lineX2 + lineY1 * lineY2))) / lineLength;

        double closestX = lineStart.x + t * lineX1;
        double closestY = lineStart.y + t * lineY1;

        double distance = Math.sqrt((this.x - closestX) * (this.x - closestX) + (this.y - closestY) * (this.y - closestY));

        return (distance <= this.getRadius());
    }


    /**
     * Check is this robot collides with another robot
     * @param robots Pointer to the vector of robots
     * @return True if Robot collides with other Robots
     */
    public boolean robotDetection(ArrayList<Robot> robots)
    {
        for(Robot other : robots)
        {
            if(other == this)
                continue;

            if(this.intersect(other))
            {
                Line line;
                for(int i = 0; i < 4; i++)
                {
                    line = this.colliderFwd.breakIntoEdges(i);

                    if(other.lineCircleIntersect(line.start, line.end))
                    {
                        return true;
                    }

                }

                // check if other robot is not inside robot collider
                if(this.colliderFwd.pointInRectangle(other.getPos()))
                    return  true;
            }
        }

        return false;
    }

    public double getDetRadius() {
        return detRadius;
    }
}
