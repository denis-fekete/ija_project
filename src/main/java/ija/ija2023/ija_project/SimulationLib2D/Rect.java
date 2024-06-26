/**
 * Implementation of Rectangle class. Rectangle is a class for representing
 * rectangular or polygonal convex shapes with four corners. It contains
 * definition for moving and rotating object with respect to its center.
 * Also holds implementation of collision detection between other rectangles
 *
 * @author Denis Fekete (xfeket01@fit.vutbr.cz)
 */

package ija.ija2023.ija_project.SimulationLib2D;

import ija.ija2023.ija_project.SimulationLib2D.Intersections.Intersections;

public class Rect extends Circle {
    /**
     * Width of this Rectangle
     */
    double width;

    /**
     * Height of this rectangle
     */
    double height;
    
    Point LB; // left bottom point of rectangle
    Point LT; // left top point of rectangle
    Point RB; // right bottom point of rectangle
    Point RT; // right top point of rectangle
    
    private Rect(double x, double y, double w, double h, double rot)
    {
        super(x, y, Math.sqrt( (w * w + h * h) / 4 ), rot);

        this.width = w;
        this.height = h;

        LB = new Point();
        LT = new Point();
        RB = new Point();
        RT = new Point();
    }
    
    public static Rect create(double x, double y, double w, double h, double rot)
    {
        // create rect object
        Rect rect = new Rect(x, y, w, h, rot);
        // update points

        rect.rotate(0);
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
    public Rect moveTo(Point point)
    {
        super.moveTo(point);
        this.updatePoints();
        return this;
    }
    
    @Override
    public Rect moveForward(double distance)
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

    /**
     * Moves all points in rectangle in given deltas
     * @param deltaX Value to move in x axis
     * @param deltaY Value to move in y axis
     */
    public Rect moveInDirection(double deltaX, double deltaY)
    {
        x += deltaX; y += deltaY;
        LB.x += deltaX; LB.y += deltaY;
        RB.x += deltaX; RB.y += deltaY;
        RT.x += deltaX; RT.y += deltaY;
        LT.x += deltaX; LT.y += deltaY;

        return this;
    }

    /**
     * Add rotation to this Rectangle, and update all points
     * @param angle Angle to be added to the Rectangle
     */
    @Override
    public Rect rotate(double angle)
    {
        super.rotate(angle);

        this.rot = angle;

        this.updatePoints();
        return this;
    }

    /**
     * Updates single point based on `center` Point
     * @param center Point based on which position will be calculated
     * @param p Pointer to the Point that will be set
     */
    protected Point updateSinglePoint(Point center, Point p)
    {
        // Calculate translated position
        double tmpX = p.x - center.x;
        double tmpY = p.y - center.y;
        // Calculate rotated position
        double rotatedX = (tmpX * cosRad) - (tmpY * sinRad);
        double rotatedY = (tmpX * sinRad) + (tmpY * cosRad);
        // Translate back to original position with rotated position
        p.setPos(rotatedX + center.x, rotatedY + center.y);
        
        return p;
    }

    /**
     * Calculates corners of `rect` Rectangle with no rotation
     */
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

    /**
     * Updates all points in rectangle based on rotation, offsets will
     * be respected
     * @param cosRad Calculates points based on `cosRad` value, if it was
     * already calculated
     * @param sinRad Calculates points based on `sinRad` value, if it was
     * already calculated
     */
    protected Rect updatePoints(double cosRad, double sinRad)
    {
        calculateCornersWithNoRotation();

        Point center = new Point(this.x, this.y);

        // Update stored sinus and cosine values
        calculateSinCos();

        // Calculate corners with rotation
        LB.setPos(updateSinglePoint(center, LB));
        LT.setPos(updateSinglePoint(center, LT));
        RB.setPos(updateSinglePoint(center, RB));
        RT.setPos(updateSinglePoint(center, RT));

        return this;
    }

    /**
     * Updates all points in rectangle based on rotation, offsets will
     * be respected
     */
    protected Rect updatePoints()
    {
        return updatePoints(this.cosRad, this.sinRad);
    }

    public boolean pointInRectangle(Point p)
    {
        return (
                Intersections.orientation(LB, RB, p) == 2 &&
                Intersections.orientation(RB, RT, p) == 2 &&
                Intersections.orientation(RT, LT, p) == 2 &&
                Intersections.orientation(LT, LB, p) == 2);
    }

    /**
     * Break rectangle into lines, based on edge returns Line made from 
     * rectangle points
     * edge = 0 : bottom line
     * edge = 1 : right line
     * edge = 2 : top line
     * edge = 3 : left line
     * @param edge parameter that decides which Line should be returned
     */
    protected Line breakIntoEdges(int edge)
    {
        switch (edge % 4)
        {
            case 0:
                return new Line(this.LB, this.RB);
            case 1:
                return new Line(this.RB, this.RT);
            case 2:
                return new Line(this.RT, this.LT);
            case 3:
                return new Line(this.LT, this.LB);
        }

        return null;
    }

    /**
     * Checks if this Rectangle intersects with `other` rectangle
     * @param other Reference to the other Rectangle
     * @return True if Rectangles intersect
     */
    boolean intersects(Rect other)
    {
        Line lineA;
        Line lineB;
        // Check whenever edges of rectangle intersects, 4 = EDGE COUNT
        for(int i = 0; i < 4; i++)
        {
            lineA = breakIntoEdges(i);

            for (int j = 0; j < 4; j++) // 4 = EDGE COUNT
            {
                lineB = other.breakIntoEdges(j);

                // Check if lines intersect
                if(Intersections.linesIntersect(lineA.start, lineA.end, lineB.start, lineB.end))
                {
                    return true;
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

    /**
     * @return Returns an array of X value from this Rectangle corners
     */
    public double[] simulationColliderXPoints()
    {
        return new double[]{
                this.getLB().getX(),
                this.getRB().getX(),
                this.getRT().getX(),
                this.getLT().getX() };
    }

    /**
     * @return Returns an array of Y value from this Rectangle corners
     */
    public double[] simulationColliderYPoints()
    {
        return new double[]{
                this.getLB().getY(),
                this.getRB().getY(),
                this.getRT().getY(),
                this.getLT().getY() };
    }
}
