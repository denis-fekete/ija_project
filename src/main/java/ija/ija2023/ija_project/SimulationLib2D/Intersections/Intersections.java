/**
 * Implementation intersection algorithms for collision detection.
 *
 * @authors Denis Fekete (xfeket01@fit.vutbr.cz), Javidx9 OneLoneCoder (https://github.com/OneLoneCoder), GeeksForGeeks (https://www.geeksforgeeks.org)
 *
 * This file and its contents are under OLC-3 License:
 *
 * # License (OLC-3)
 *
 * Copyright 2018-2022 OneLoneCoder.com
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions or derivations of source code must retain the above
 *    copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions or derivative works in binary form must reproduce
 *    the above copyright notice. This list of conditions and the following
 *    disclaimer must be reproduced in the documentation and/or other
 *    materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package ija.ija2023.ija_project.SimulationLib2D.Intersections;

import ija.ija2023.ija_project.SimulationLib2D.Point;
import ija.ija2023.ija_project.SimulationLib2D.Robot;

public class Intersections {
    /**
     * Checks if line intersects with this robot
     * @param lineStart Point where line starts
     * @param lineEnd Point where line ends
     * @return True if line intersects with robot
     *
     * Source:  https://github.com/OneLoneCoder/Javidx9/tree/master/ConsoleGameEngine/BiggerProjects/Balls
     *          https://www.youtube.com/watch?v=ebq7L2Wtbl4
     * Author: Javidx9 OneLoneCoder (https://github.com/OneLoneCoder)
     */
    public static boolean lineCircleIntersect(Robot robot, Point lineStart, Point lineEnd)
    {

        double lineX1 = lineEnd.x - lineStart.x;
        double lineY1 = lineEnd.y - lineStart.y;

        double lineX2 = robot.x - lineStart.x;
        double lineY2 = robot.y - lineStart.y;

        double lineLength = lineX1 * lineX1 + lineY1 * lineY1;

        double t = Math.max(0.0, Math.min(lineLength, (lineX1 * lineX2 + lineY1 * lineY2))) / lineLength;

        double closestX = lineStart.x + t * lineX1;
        double closestY = lineStart.y + t * lineY1;

        double distance = Math.sqrt((robot.x - closestX) * (robot.x - closestX) + (robot.y - closestY) * (robot.y - closestY));

        return (distance <= robot.getRadius());
    }


    /**
     * Returns orientation of points
     * @param a Start of first line
     * @param b End of first line
     * @param c Point that is checked
     * @return Returns: 0 if c is collinear with a,b line, 1 if on right side, 2 if on left side
     *
     * Source: https://www.geeksforgeeks.org/check-if-two-given-line-segments-intersect/
     * Author: GeeksForGeeks
     */
    public static int orientation(Point a, Point b, Point c)
    {
        double val = (b.y - a.y) * (c.x - b.x) -
                (b.x - a.x) * (c.y - b.y);

        if (val == 0) return 0;

        return (val > 0)? 1: 2;
    }

    /**
     * Checks if Point c lies on line segment
     * @param a Start of line segment
     * @param b End of line segment
     * @param c Point that is checked
     * @return True if point lies on segment
     */
    public static boolean onSegment(Point a, Point b, Point c)
    {
        return b.x <= Math.max(a.x, c.x) && b.x >= Math.max(a.x, c.x) &&
                b.y <= Math.max(a.y, c.y) && b.y >= Math.max(a.y, c.y);
    }


    /**
     * Checks if two line intersect
     * @param a Start point of first line
     * @param b End point of first line
     * @param c Start point of second line
     * @param d End point of second
     * @return True if lines intersect
     *
     * Source: https://www.geeksforgeeks.org/check-if-two-given-line-segments-intersect/
     * Author: GeeksForGeeks
     */
    public static boolean linesIntersect(Point a, Point b, Point c, Point d)
    {
        int o1 = orientation(a, b, c);
        int o2 = orientation(a, b, d);
        int o3 = orientation(c, d, a);
        int o4 = orientation(c, d, b);

        if (o1 != o2 && o3 != o4)
            return true;

        // Special Cases
        // p1, q1 and p2 are collinear and p2 lies on segment p1q1
        if (o1 == 0 && onSegment(a, d, b)) return true;

        // p1, q1 and q2 are collinear and q2 lies on segment p1q1
        if (o2 == 0 && onSegment(a, d, b)) return true;

        // p2, q2 and p1 are collinear and p1 lies on segment p2q2
        if (o3 == 0 && onSegment(c, a, d)) return true;

        // p2, q2 and q1 are collinear and q1 lies on segment p2q2
        if (o4 == 0 && onSegment(c, a, d)) return true;

        return false;
    }
}
