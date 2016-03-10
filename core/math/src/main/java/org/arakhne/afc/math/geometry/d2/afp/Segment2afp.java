/* 
 * $Id$
 * 
 * Copyright (C) 2010-2013 Stephane GALLAND.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * This program is free software; you can redistribute it and/or modify
 */

package org.arakhne.afc.math.geometry.d2.afp;

import java.util.NoSuchElementException;

import org.arakhne.afc.math.MathConstants;
import org.arakhne.afc.math.MathUtil;
import org.arakhne.afc.math.geometry.PathWindingRule;
import org.arakhne.afc.math.geometry.d2.Point2D;
import org.arakhne.afc.math.geometry.d2.Transform2D;
import org.arakhne.afc.math.geometry.d2.Vector2D;
import org.eclipse.xtext.xbase.lib.Pure;

/** Fonctional interface that represented a 2D segment/line on a plane.
 *
 * @param <ST> is the type of the general implementation.
 * @param <IT> is the type of the implementation of this shape.
 * @param <IE> is the type of the path elements.
 * @param <P> is the type of the points.
 * @param <B> is the type of the bounding boxes.
 * @author $Author: sgalland$
 * @author $Author: hjaffali$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public interface Segment2afp<
		ST extends Shape2afp<?, ?, IE, P, B>,
		IT extends Segment2afp<?, ?, IE, P, B>,
		IE extends PathElement2afp,
		P extends Point2D,
		B extends Rectangle2afp<?, ?, IE, P, B>>
		extends Shape2afp<ST, IT, IE, P, B> {

	/**
	 * Replies if two lines are colinear.
	 * <p>
	 * The given two lines are described respectivaly by two points, i.e. {@code (x1,y1)} and {@code (x2,y2)} for the first line, and {@code (x3,y3)} and {@code (x4,y4)} for the second line.
	 * <p>
	 * If you are interested to test if the two lines are parallel, see {@link #isParallelLines(double, double, double, double, double, double, double, double)}.
	 * 
	 * @param x1
	 *            is the X coordinate of the first point of the first line.
	 * @param y1
	 *            is the Y coordinate of the first point of the first line.
	 * @param x2
	 *            is the X coordinate of the second point of the first line.
	 * @param y2
	 *            is the Y coordinate of the second point of the first line.
	 * @param x3
	 *            is the X coordinate of the first point of the second line.
	 * @param y3
	 *            is the Y coordinate of the first point of the second line.
	 * @param x4
	 *            is the X coordinate of the second point of the second line.
	 * @param y4
	 *            is the Y coordinate of the second point of the second line.
	 * @return <code>true</code> if the two given lines are collinear.
	 * @see #isParallelLines(double, double, double, double, double, double, double, double)
	 * @see Point2D#isCollinearPoints(double, double, double, double, double, double)
	 */
	@Pure
	static boolean isCollinearLines(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
		return (isParallelLines(x1, y1, x2, y2, x3, y3, x4, y4)
				&& Point2D.isCollinearPoints(x1, y1, x2, y2, x3, y3));
	}

	/**
	 * Replies if two lines are parallel.
	 * <p>
	 * The given two lines are described respectivaly by two points, i.e. {@code (x1,y1)} and {@code (x2,y2)} for the first line, and {@code (x3,y3)} and {@code (x4,y4)} for the second line.
	 * <p>
	 * If you are interested to test if the two lines are colinear, see {@link #isCollinearLines(double, double, double, double, double, double, double, double)}.
	 * 
	 * @param x1
	 *            is the X coordinate of the first point of the first line.
	 * @param y1
	 *            is the Y coordinate of the first point of the first line.
	 * @param x2
	 *            is the X coordinate of the second point of the first line.
	 * @param y2
	 *            is the Y coordinate of the second point of the first line.
	 * @param x3
	 *            is the X coordinate of the first point of the second line.
	 * @param y3
	 *            is the Y coordinate of the first point of the second line.
	 * @param x4
	 *            is the X coordinate of the second point of the second line.
	 * @param y4
	 *            is the Y coordinate of the second point of the second line.
	 * @return <code>true</code> if the two given lines are parallel.
	 * @see #isCollinearLines(double, double, double, double, double, double, double, double)
	 */
	@Pure
	static boolean isParallelLines(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
		return Vector2D.isCollinearVectors(x2 - x1, y2 - y1, x4 - x3, y4 - y3);
	}

	/**
	 * Replies the intersection point between two segments.
	 * 
	 * @param x1
	 *            is the X coordinate of the first point of the first segment.
	 * @param y1
	 *            is the Y coordinate of the first point of the first segment.
	 * @param x2
	 *            is the X coordinate of the second point of the first segment.
	 * @param y2
	 *            is the Y coordinate of the second point of the first segment.
	 * @param x3
	 *            is the X coordinate of the first point of the second segment.
	 * @param y3
	 *            is the Y coordinate of the first point of the second segment.
	 * @param x4
	 *            is the X coordinate of the second point of the second segment.
	 * @param y4
	 *            is the Y coordinate of the second point of the second segment.
	 * @param result the intersection point.
	 * @return <code>true</code> if an intersection exists.
	 */
	@Pure
	static boolean computeSegmentSegmentIntersection(double x1, double y1, double x2, double y2,
			double x3, double y3, double x4, double y4,
			Point2D result) {
		double m = computeSegmentSegmentIntersectionFactor(x1, y1, x2, y2, x3, y3, x4, y4);
		if (Double.isNaN(m))
			return false;
		result.set(x1 + m * (x2 - x1), y1 + m * (y2 - y1));
		return true;
	}

	/**
	 * Replies one position factor for the intersection point between two lines.
	 * <p>
	 * Let line equations for L1 and L2:<br>
	 * <code>L1: P1 + factor1 * (P2-P1)</code><br>
	 * <code>L2: P3 + factor2 * (P4-P3)</code><br>
	 * If lines are intersecting, then<br>
	 * <code>P1 + factor1 * (P2-P1) = P3 + factor2 * (P4-P3)</code>
	 * <p>
	 * This function computes and replies <code>factor1</code>.
	 * 
	 * @param x1
	 *            is the X coordinate of the first point of the first segment.
	 * @param y1
	 *            is the Y coordinate of the first point of the first segment.
	 * @param x2
	 *            is the X coordinate of the second point of the first segment.
	 * @param y2
	 *            is the Y coordinate of the second point of the first segment.
	 * @param x3
	 *            is the X coordinate of the first point of the second segment.
	 * @param y3
	 *            is the Y coordinate of the first point of the second segment.
	 * @param x4
	 *            is the X coordinate of the second point of the second segment.
	 * @param y4
	 *            is the Y coordinate of the second point of the second segment.
	 * @return <code>factor1</code> or {@link Double#NaN} if no intersection.
	 */
	@Pure
	public static double computeSegmentSegmentIntersectionFactor(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
		double X1 = x2 - x1;
		double Y1 = y2 - y1;
		double X2 = x4 - x3;
		double Y2 = y4 - y3;

		// determinant is zero when parallel = det(L1,L2)
		double det = Vector2D.perpProduct(X1, Y1, X2, Y2);
		if (det == 0.)
			return Double.NaN;

		// Given line equations:
		// Pa = P1 + ua (P2-P1), and
		// Pb = P3 + ub (P4-P3)
		// and
		// V = (P1-P3)
		// then
		// ua = det(L2,V) / det(L1,L2)
		// ub = det(L1,V) / det(L1,L2)
		double u = Vector2D.perpProduct(X1, Y1, x1 - x3, y1 - y3) / det;
		if (u < 0. || u > 1.)
			return Double.NaN;
		u = Vector2D.perpProduct(X2, Y2, x1 - x3, y1 - y3) / det;
		return (u < 0. || u > 1.) ? Double.NaN : u;
	}

	/**
	 * Replies the position factory for the intersection point between two lines.
	 * <p>
	 * Let line equations for L1 and L2:<br>
	 * <code>L1: P1 + factor1 * (P2-P1)</code><br>
	 * <code>L2: P3 + factor2 * (P4-P3)</code><br>
	 * If lines are intersecting, then<br>
	 * <code>P1 + factor1 * (P2-P1) = P3 + factor2 * (P4-P3)</code>
	 * <p>
	 * This function computes and replies <code>factor1</code>.
	 * 
	 * @param x1
	 *            is the X coordinate of the first point of the first line.
	 * @param y1
	 *            is the Y coordinate of the first point of the first line.
	 * @param x2
	 *            is the X coordinate of the second point of the first line.
	 * @param y2
	 *            is the Y coordinate of the second point of the first line.
	 * @param x3
	 *            is the X coordinate of the first point of the second line.
	 * @param y3
	 *            is the Y coordinate of the first point of the second line.
	 * @param x4
	 *            is the X coordinate of the second point of the second line.
	 * @param y4
	 *            is the Y coordinate of the second point of the second line.
	 * @return <code>factor1</code> or {@link Double#NaN} if no intersection.
	 */
	@Pure
	static double computeLineLineIntersectionFactor(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
		double X1 = x2 - x1;
		double Y1 = y2 - y1;
		double X2 = x4 - x3;
		double Y2 = y4 - y3;

		// determinant is zero when parallel = det(L1,L2)
		double det = Vector2D.perpProduct(X1, Y1, X2, Y2);
		if (det == 0.)
			return Double.NaN;

		// Given line equations:
		// Pa = P1 + ua (P2-P1), and
		// Pb = P3 + ub (P4-P3)
		// and
		// V = (P1-P3)
		// then
		// ua = det(L2,V) / det(L1,L2)
		// ub = det(L1,V) / det(L1,L2)
		return Vector2D.perpProduct(X2, Y2, x1 - x3, y1 - y3) / det;
	}

	/** Compute the intersection of two lines specified
	 * by the specified points and vectors.
	 * 
	 * @param x1 horizontal position of the first point of the line.
	 * @param y1 vertical position of the first point of the line.
	 * @param x2 horizontal position of the second point of the line.
	 * @param y2 vertical position of the second point of the line.
	 * @param x3 horizontal position of the first point of the line.
	 * @param y3 vertical position of the first point of the line.
	 * @param x4 horizontal position of the second point of the line.
	 * @param y4 vertical position of the second point of the line.
	 * @param result the intersection point.
	 * @return <code>true</code> if there is an intersection.
	 */
	@Pure
	static boolean computeLineLineIntersection(double x1, double y1, double x2, double y2,
			double x3, double y3, double x4, double y4,
			Point2D result) {
		double denom = (y4-y3)*(x2-x1) - (x4-x3)*(y2-y1);
		if (denom==0.) return false;
		double ua = ((x4-x3)*(y1-y3) - (y4-y3)*(x1-x3));
		double ub = ((x2-x1)*(y1-y3) - (y2-y1)*(x1-x3));
		if (ua==ub) return false;
		ua = ua / denom;
		result.set(
				x1 + ua * (x2 - x1),
				y1 + ua * (y2 - y1));
		return true;
	}

	/** Compute the distance between a point and a line.
	 *
	 * @param x1 horizontal position of the first point of the line.
	 * @param y1 vertical position of the first point of the line.
	 * @param x2 horizontal position of the second point of the line.
	 * @param y2 vertical position of the second point of the line.
	 * @param px horizontal position of the point.
	 * @param py vertical position of the point.
	 * @return the distance beetween the point and the line.
	 * @see #getDistanceLinePoint(double, double, double, double, double, double)
	 */
	@Pure
	public static double getDistanceSquaredLinePoint(double x1, double y1, double x2, double y2, double px, double py) {
		double r_denomenator = (x2-x1)*(x2-x1) + (y2-y1)*(y2-y1);
		if (r_denomenator==0.) return Point2D.getDistanceSquaredPointPoint(px, py, x1, y1);
		double s = ((y1-py)*(x2-x1)-(x1-px)*(y2-y1) ) / r_denomenator;
		return (s * s) * Math.abs(r_denomenator);
	}

	/** Compute the square distance between a point and a segment.
	 *
	 * @param x1 horizontal position of the first point of the segment.
	 * @param y1 vertical position of the first point of the segment.
	 * @param x2 horizontal position of the second point of the segment.
	 * @param y2 vertical position of the second point of the segment.
	 * @param px horizontal position of the point.
	 * @param py vertical position of the point.
	 * @param pts is the point that will be set with the coordinates of the nearest point,
	 * if not <code>null</code>.
	 * @return the distance beetween the point and the segment.
	 */
	static double getDistanceSquaredSegmentPoint(double x1, double y1, double x2, double y2, double px, double py, Point2D pts) {
		double r_denomenator = (x2-x1)*(x2-x1) + (y2-y1)*(y2-y1);
		if (r_denomenator==0f) return Point2D.getDistanceSquaredPointPoint(px, py, x1, y1);
		double r_numerator = (px-x1)*(x2-x1) + (py-y1)*(y2-y1);
		double ratio = r_numerator / r_denomenator;

		if (ratio<=0f) {
			if (pts!=null) pts.set(x1, y1);
			return Math.abs((px-x1)*(px-x1) + (py-y1)*(py-y1));
		}

		if (ratio>=1f) {
			if (pts!=null) pts.set(x2, y2);
			return Math.abs((px-x2)*(px-x2) + (py-y2)*(py-y2));
		}

		if (pts!=null) pts.set(
				ratio * (x2-x1),
				ratio * (y2-y1));

		double s =  ((y1-py)*(x2-x1)-(x1-px)*(y2-y1) ) / r_denomenator;
		return (s * s) * Math.abs(r_denomenator);
	}

	/** Compute the distance between a point and a segment.
	 *
	 * @param x1 horizontal position of the first point of the segment.
	 * @param y1 vertical position of the first point of the segment.
	 * @param x2 horizontal position of the second point of the segment.
	 * @param y2 vertical position of the second point of the segment.
	 * @param px horizontal position of the point.
	 * @param py vertical position of the point.
	 * @param pts is the point that will be set with the coordinates of the nearest point,
	 * if not <code>null</code>.
	 * @return the distance beetween the point and the segment.
	 */
	static double getDistanceSegmentPoint(double x1, double y1, double x2, double y2, double px, double py, Point2D pts) {
		double r_denomenator = (x2-x1)*(x2-x1) + (y2-y1)*(y2-y1);
		if (r_denomenator==0.) return Point2D.getDistancePointPoint(px, py, x1, y1);
		double r_numerator = (px-x1)*(x2-x1) + (py-y1)*(y2-y1);
		double ratio = r_numerator / r_denomenator;

		if (ratio<=0.) {
			if (pts!=null) pts.set(x1, y1);
			return Math.sqrt((px-x1)*(px-x1) + (py-y1)*(py-y1));
		}

		if (ratio>=1.) {
			if (pts!=null) pts.set(x2, y2);
			return Math.sqrt((px-x2)*(px-x2) + (py-y2)*(py-y2));
		}

		if (pts!=null) pts.set(
				ratio * (x2-x1),
				ratio * (y2-y1));

		double s =  ((y1-py)*(x2-x1)-(x1-px)*(y2-y1) ) / r_denomenator;
		return Math.abs(s) * Math.sqrt(r_denomenator);
	}

	/** Compute the distance between a point and a line.
	 *
	 * @param x1 horizontal position of the first point of the line.
	 * @param y1 vertical position of the first point of the line.
	 * @param x2 horizontal position of the second point of the line.
	 * @param y2 vertical position of the second point of the line.
	 * @param px horizontal position of the point.
	 * @param py vertical position of the point.
	 * @return the distance beetween the point and the line.
	 * @see #getDistanceSquaredLinePoint(double, double, double, double, double, double)
	 * @see #computeRelativeDistanceLinePoint(double, double, double, double, double, double)
	 */
	@Pure
	static double getDistanceLinePoint(double x1, double y1, double x2, double y2, double px, double py) {
		double r_denomenator = (x2-x1)*(x2-x1) + (y2-y1)*(y2-y1);
		if (r_denomenator==0.) return Point2D.getDistancePointPoint(px, py, x1, y1);
		double s = ((y1-py)*(x2-x1)-(x1-px)*(y2-y1) ) / r_denomenator;
		return Math.abs(s) * Math.sqrt(r_denomenator);
	}

	/** Replies if a point is closed to a segment.
	 *
	 * @param x1 horizontal location of the first-segment begining.
	 * @param y1 vertical location of the first-segment ending.
	 * @param x2 horizontal location of the second-segment begining.
	 * @param y2 vertical location of the second-segment ending.
	 * @param x horizontal location of the point.
	 * @param y vertical location of the point.
	 * @param hitDistance is the maximal hitting distance.
	 * @return <code>true</code> if the point and the
	 *         line have closed locations.
	 */
	@Pure
	static boolean isPointClosedToSegment( double x1, double y1, 
			double x2, double y2, 
			double x, double y, double hitDistance ) {
		return ( getDistanceSegmentPoint(x1, y1, x2, y2, x, y, null) < hitDistance ) ;
	}

	/** Replies if a point is closed to a line.
	 *
	 * @param x1 horizontal location of the first-line begining.
	 * @param y1 vertical location of the first-line ending.
	 * @param x2 horizontal location of the second-line begining.
	 * @param y2 vertical location of the second-line ending.
	 * @param x horizontal location of the point.
	 * @param y vertical location of the point.
	 * @param hitDistance is the maximal hitting distance.
	 * @return <code>true</code> if the point and the
	 *         line have closed locations.
	 */
	@Pure
	static boolean isPointClosedToLine( double x1, double y1, 
			double x2, double y2, 
			double x, double y, double hitDistance ) {
		return ( getDistanceLinePoint(x1, y1, x2, y2, x, y) < hitDistance ) ;
	}

	/**
	 * Replies the projection a point on a segment.
	 * 
	 * @param px
	 *            is the coordiante of the point to project
	 * @param py
	 *            is the coordiante of the point to project
	 * @param s1x
	 *            is the x-coordinate of the first line point.
	 * @param s1y
	 *            is the y-coordinate of the first line point.
	 * @param s2x
	 *            is the x-coordinate of the second line point.
	 * @param s2y
	 *            is the y-coordinate of the second line point.
	 * @return the projection of the specified point on the line. If 
	 * equal to {@code 0}, the projection is equal to the first segment point. 
	 * If equal to {@code 1}, the projection is equal to the second segment point. 
	 * If inside {@code ]0;1[}, the projection is between the two segment points. 
	 * If inside {@code ]-inf;0[}, the projection is outside on the side of the 
	 * first segment point. If inside {@code ]1;+inf[}, the projection is 
	 * outside on the side of the second segment point.
	 */
	@Pure
	static double computeProjectedPointOnLine(double px, double py, double s1x, double s1y, double s2x, double s2y) {
		double r_numerator = (px-s1x)*(s2x-s1x) + (py-s1y)*(s2y-s1y);
		double r_denomenator = (s2x-s1x)*(s2x-s1x) + (s2y-s1y)*(s2y-s1y);
		return r_numerator / r_denomenator;
	}

	/**
	 * Replies the relative distance from the given point to the given line.
	 * The replied distance may be negative, depending on which side of 
	 * the line the point is.
	 * 
	 * @param x1
	 *            the X coordinate of the start point of the specified line segment
	 * @param y1
	 *            the Y coordinate of the start point of the specified line segment
	 * @param x2
	 *            the X coordinate of the end point of the specified line segment
	 * @param y2
	 *            the Y coordinate of the end point of the specified line segment
	 * @param px
	 *            the X coordinate of the specified point to be compared with the specified line segment
	 * @param py
	 *            the Y coordinate of the specified point to be compared with the specified line segment
	 * @return the positive or negative distance from the point to the line
	 * @see #ccw(double, double, double, double, double, double, double)
	 * @see #computeSideLinePoint(double, double, double, double, double, double, double)
	 */
	@Pure
	static double computeRelativeDistanceLinePoint(double x1, double y1, double x2, double y2, double px, double py) {
		double r_denomenator = (x2-x1)*(x2-x1) + (y2-y1)*(y2-y1);
		if (r_denomenator==0.) return Point2D.getDistancePointPoint(px, py, x1, y1);
		double s = ((y1-py)*(x2-x1)-(x1-px)*(y2-y1) ) / r_denomenator;
		return s * Math.sqrt(r_denomenator);
	}

	/**
	 * Replies on which side of a line the given point is located.
	 * <p>
	 * A return value of 1 indicates that the line segment must turn in the direction
	 * that takes the positive X axis towards the negative Y axis. In the default
	 * coordinate system used by Java 2D, this direction is counterclockwise.
	 * <p>
	 * A return value of -1 indicates that the line segment must turn in the direction that takes the positive X axis towards the positive Y axis. In the default coordinate system, this direction is clockwise.
	 * <p>
	 * A return value of 0 indicates that the point lies exactly on the line segment. Note that an indicator value of 0 is rare and not useful for determining colinearity because of floating point rounding issues.
	 * <p>
	 * This function uses the equal-to-zero test with the error {@link Math#ulp(double)}.
	 * <p>
	 * In opposite of {@link #ccw(double, double, double, double, double, double, double)},
	 * this function does not try to classify the point if it is colinear
	 * to the segment. If the point is colinear, O is always returns. 
	 * 
	 * @param x1
	 *            the X coordinate of the start point of the specified line segment
	 * @param y1
	 *            the Y coordinate of the start point of the specified line segment
	 * @param x2
	 *            the X coordinate of the end point of the specified line segment
	 * @param y2
	 *            the Y coordinate of the end point of the specified line segment
	 * @param px
	 *            the X coordinate of the specified point to be compared with the specified line segment
	 * @param py
	 *            the Y coordinate of the specified point to be compared with the specified line segment
	 * @param epsilon approximate epsilon.
	 * @return an integer that indicates the position of the third specified coordinates with respect to the line segment formed by the first two specified coordinates.
	 * @see #computeRelativeDistanceLinePoint(double, double, double, double, double, double)
	 * @see MathUtil#isEpsilonZero(double)
	 * @see #ccw(double, double, double, double, double, double, double)
	 */
	@Pure
	static int computeSideLinePoint(double x1, double y1, double x2, double y2, double px, double py, double epsilon) {
		double cx2 = x2 - x1;
		double cy2 = y2 - y1;
		double cpx = px - x1;
		double cpy = py - y1;
		double side = cpx * cy2 - cpy * cx2;
		if (side != 0f && MathUtil.isEpsilonZero(side, epsilon)) {
			side = 0f;
		}
		return (side < 0f) ? -1 : ((side > 0f) ? 1 : 0);
	}

	/**
	 * Replies the relative counterclockwise (CCW) of a segment against a point. Returns an indicator of where 
	 * the specified point {@code (px,py)} lies with respect to the line segment from {@code (x1,y1)}
	 *  to {@code (x2,y2)}. The return value can be either 1, -1, or 0 and indicates in which 
	 *  direction the specified line must pivot around its first end point, {@code (x1,y1)}, in 
	 *  order to point at the specified point {@code (px,py)}.
	 * In other words, given three point P1, P2, and P, is the segments (P1-P2-P) a counterclockwise turn?
	 * <p>
	 * In opposite to {@link #computeSideLinePoint(double, double, double, double, double, double, double)},
	 * this function tries to classifies the point if it is colinear to the segment.
	 * The classification is explained below.
	 * <p>
	 * A return value of 1 indicates that the line segment must turn in the direction that takes the 
	 * positive X axis towards the negative Y axis. In the default coordinate system used by Java 2D, 
	 * this direction is counterclockwise.
	 * <p>
	 * A return value of -1 indicates that the line segment must turn in the direction that takes the 
	 * positive X axis towards the positive Y axis. In the default coordinate system, this 
	 * direction is clockwise.
	 * <p>
	 * A return value of 0 indicates that the point lies exactly on the line segment. 
	 * Note that an indicator value of 0 is rare and not useful for determining colinearity 
	 * because of floating point rounding issues.
	 * <p>
	 * If the point is colinear with the line segment, but not between the end points, then the value will be 
	 * -1 if the point lies "beyond {@code (x1,y1)}" or 1 if the point lies "beyond {@code (x2,y2)}".
	 * 
	 * @param x1
	 *            the X coordinate of the start point of the specified line segment
	 * @param y1
	 *            the Y coordinate of the start point of the specified line segment
	 * @param x2
	 *            the X coordinate of the end point of the specified line segment
	 * @param y2
	 *            the Y coordinate of the end point of the specified line segment
	 * @param px
	 *            the X coordinate of the specified point to be compared with the specified line segment
	 * @param py
	 *            the Y coordinate of the specified point to be compared with the specified line segment
	 * @param epsilon approximation of the tests for equality to zero.
	 * @return an integer that indicates the position of the third specified coordinates with respect to the line segment formed by the first two specified coordinates.
	 * @see #computeRelativeDistanceLinePoint(double, double, double, double, double, double)
	 * @see #computeSideLinePoint(double, double, double, double, double, double, double)
	 */
	@Pure
	static int ccw(double x1, double y1, double x2, double y2, double px, double py, double epsilon) {
		double cx2 = x2 - x1;
		double cy2 = y2 - y1;
		double cpx = px - x1;
		double cpy = py - y1;
		double ccw = cpx * cy2 - cpy * cx2;
		if (MathUtil.isEpsilonZero(ccw, epsilon)) {
			// The point is colinear, classify based on which side of
			// the segment the point falls on. We can calculate a
			// relative value using the projection of px,py onto the
			// segment - a negative value indicates the point projects
			// outside of the segment in the direction of the particular
			// endpoint used as the origin for the projection.
			ccw = cpx * cx2 + cpy * cy2;
			if (ccw > 0.) {
				// Reverse the projection to be relative to the original x2,y2
				// x2 and y2 are simply negated.
				// px and py need to have (x2 - x1) or (y2 - y1) subtracted
				// from them (based on the original values)
				// Since we really want to get a positive answer when the
				// point is "beyond (x2,y2)", then we want to calculate
				// the inverse anyway - thus we leave x2 & y2 negated.
				cpx -= cx2;
				cpy -= cy2;
				ccw = cpx * cx2 + cpy * cy2;
				if (ccw < 0f) {
					ccw = 0f;
				}
			}
		}
		return (ccw < 0f) ? -1 : ((ccw > 0f) ? 1 : 0);
	}

	/** Compute the interpolate point between the two points.
	 * 
	 * @param p1x
	 * @param p1y
	 * @param p2x
	 * @param p2y
	 * @param factor is between 0 and 1; 0 for p1, and 1 for p2.
	 * @param result the interpolate point.
	 */
	@Pure
	static void interpolate(double p1x, double p1y, double p2x, double p2y, double factor, Point2D result) {
		double f = (factor<0f) ? 0f : factor;
		if (f>1f) f = 1f;
		double vx = p2x - p1x;
		double vy = p2y - p1y;
		result.set(
				p1x + factor * vx,
				p1y + factor * vy);
	}

	/** Replies the point on the segment that is farthest to the given point.
	 * 
	 * @param ax is the x coordinate of the first point of the segment.
	 * @param ay is the y coordinate of the first point of the segment.
	 * @param bx is the x coordinate of the second point of the segment.
	 * @param by is the y coordinate of the second point of the segment.
	 * @param px is the x coordinate of the point.
	 * @param py is the y coordinate of the point.
	 * @param result the farthest point on the shape.
	 */
	@Pure
	static void computeFarthestPointTo(
			double ax, double ay, double bx, double by, double px, double py, Point2D result) {
		double v1x = px - ax;
		double v1y = py - ay;
		double v2x = px - bx;
		double v2y = py - by;
		if ((v1x*v1x+v1y*v1y) >= (v2x*v2x+v2y*v2y)) {
			result.set(ax, ay);
		} else {
			result.set(bx, by);
		}
	}

	/** Replies the point on the segment that is closest to the given point.
	 * 
	 * @param ax is the x coordinate of the first point of the segment.
	 * @param ay is the y coordinate of the first point of the segment.
	 * @param bx is the x coordinate of the second point of the segment.
	 * @param by is the y coordinate of the second point of the segment.
	 * @param px is the x coordinate of the point.
	 * @param py is the y coordinate of the point.
	 * @param result the is point on the shape.
	 */
	@Pure
	static void computeClosestPointTo(
			double ax, double ay, double bx, double by, double px, double py,
			Point2D result) {
		double ratio = Segment2afp.computeProjectedPointOnLine(px, py,
				ax, ay,
				bx, by);
		if (ratio<=0f) result.set(ax, ay);
		else if (ratio>=1f) result.set(bx, by);
		else result.set(
				ax + (bx - ax) * ratio,
				ay + (by - ay) * ratio); 
	}

	/**
	 * Calculates the number of times the line from (x0,y0) to (x1,y1)
	 * crosses the ray extending to the right from (px,py).
	 * If the point lies on the line, then no crossings are recorded.
	 * +1 is returned for a crossing where the Y coordinate is increasing
	 * -1 is returned for a crossing where the Y coordinate is decreasing
	 * 
	 * @param px is the reference point to test.
	 * @param py is the reference point to test.
	 * @param x0 is the first point of the line.
	 * @param y0 is the first point of the line.
	 * @param x1 is the second point of the line.
	 * @param y1 is the secondpoint of the line.
	 * @return the crossing.
	 */
	@Pure
	static int computeCrossingsFromPoint(
			double px, double py,
			double x0, double y0,
			double x1, double y1) {
		// Copied from AWT API
		if (py <  y0 && py <  y1) return 0;
		if (py >= y0 && py >= y1) return 0;
		// assert(y0 != y1);
		if (px >= x0 && px >= x1) return 0;
		if (px <  x0 && px <  x1) return (y0 < y1) ? 1 : -1;
		double xintercept = x0 + (py - y0) * (x1 - x0) / (y1 - y0);
		if (px >= xintercept) return 0;
		return (y0 < y1) ? 1 : -1;
	}

	/**
	 * Calculates the number of times the line from (x0,y0) to (x1,y1)
	 * crosses the ray extending to the right from (px,py).
	 * If the point lies on the line, then no crossings are recorded.
	 * +1 is returned for a crossing where the Y coordinate is increasing
	 * -1 is returned for a crossing where the Y coordinate is decreasing
	 * <p>
	 * This function differs from {@link #computeCrossingsFromPoint(double, double, double, double, double, double)}.
	 * The equality test is not used in this function.
	 * 
	 * @param px is the reference point to test.
	 * @param py is the reference point to test.
	 * @param x0 is the first point of the line.
	 * @param y0 is the first point of the line.
	 * @param x1 is the second point of the line.
	 * @param y1 is the secondpoint of the line.
	 * @return the crossing.
	 */
	@Pure
	static int computeCrossingsFromPoint1(
			double px, double py,
			double x0, double y0,
			double x1, double y1) {
		// Copied from AWT API
		if (py <  y0 && py <  y1) return 0;
		if (py > y0 && py > y1) return 0;
		// assert(y0 != y1);
		if (px > x0 && px > x1) return 0;
		if (px <  x0 && px <  x1) return (y0 < y1) ? 1 : -1;
		double xintercept = x0 + (py - y0) * (x1 - x0) / (y1 - y0);
		if (px > xintercept) return 0;
		return (y0 < y1) ? 1 : -1;
	}

	/**
	 * Calculates the number of times the line from (x0,y0) to (x1,y1)
	 * crosses the segment (sx0,sy0) to (sx1,sy1) extending to the right.
	 * 
	 * @param crossings is the initial value for the number of crossings.
	 * @param sx1 is the first point of the segment to extend.
	 * @param sy1 is the first point of the segment to extend.
	 * @param sx2 is the second point of the segment to extend.
	 * @param sy2 is the second point of the segment to extend.
	 * @param x0 is the first point of the line.
	 * @param y0 is the first point of the line.
	 * @param x1 is the second point of the line.
	 * @param y1 is the secondpoint of the line.
	 * @return the crossing, or {@link MathConstants#SHAPE_INTERSECTS}
	 */
	@Pure
	static int computeCrossingsFromSegment(
			int crossings,
			double sx1, double sy1,
			double sx2, double sy2,
			double x0, double y0,
			double x1, double y1) {
		int numCrosses = crossings;

		double xmin = Math.min(sx1, sx2);
		double xmax = Math.max(sx1, sx2);
		double ymin = Math.min(sy1, sy2);
		double ymax = Math.max(sy1, sy2);

		if (y0<=ymin && y1<=ymin) return numCrosses;
		if (y0>=ymax && y1>=ymax) return numCrosses;
		if (x0<=xmin && x1<=xmin) return numCrosses;
		if (x0>=xmax && x1>=xmax) {
			// The line is entirely at the right of the shadow
			if (y0<y1) {
				if (y0<=ymin) ++numCrosses;
				if (y1>=ymax) ++numCrosses;
			}
			else {
				if (y1<=ymin) --numCrosses;
				if (y0>=ymax) --numCrosses;
			}
		}
		else if (intersectsSegmentSegmentWithEnds(x0, y0, x1, y1, sx1, sy1, sx2, sy2)) {
			return MathConstants.SHAPE_INTERSECTS;
		}
		else {
			int side1, side2;
			if (sy1<=sy2) {
				side1 = computeSideLinePoint(sx1, sy1, sx2, sy2, x0, y0, 0.);
				side2 = computeSideLinePoint(sx1, sy1, sx2, sy2, x1, y1, 0.);
			}
			else {
				side1 = computeSideLinePoint(sx2, sy2, sx1, sy1, x0, y0, 0.);
				side2 = computeSideLinePoint(sx2, sy2, sx1, sy1, x1, y1, 0.);
			}
			if (side1>0 || side2>0) {
				int n1 = computeCrossingsFromPoint(sx1, sy1, x0, y0, x1, y1);
				int n2;
				if (n1!=0) {
					n2 = computeCrossingsFromPoint1(sx2, sy2, x0, y0, x1, y1);
				}
				else {
					n2 = computeCrossingsFromPoint(sx2, sy2, x0, y0, x1, y1);
				}
				numCrosses += n1;
				numCrosses += n2;
			}
		}

		return numCrosses;
	}

	/**
	 * Calculates the number of times the line from (x0,y0) to (x1,y1)
	 * crosses the ellipse (ex0,ey0) to (ex1,ey1) extending to the right.
	 * 
	 * @param crossings is the initial value for the number of crossings.
	 * @param ex is the first corner of the ellipse to extend.
	 * @param ey is the first corner of the ellipse to extend.
	 * @param ew is the width of the ellipse to extend.
	 * @param eh is the height of the ellipse  to extend.
	 * @param x0 is the first point of the line.
	 * @param y0 is the first point of the line.
	 * @param x1 is the second point of the line.
	 * @param y1 is the secondpoint of the line.
	 * @return the crossing, or {@link MathConstants#SHAPE_INTERSECTS}.
	 */
	@Pure
	static int computeCrossingsFromEllipse(
			int crossings,
			double ex, double ey,
			double ew, double eh,
			double x0, double y0,
			double x1, double y1) {
		int numCrosses = crossings;

		double xmin = ex;
		double ymin = ey;
		double xmax = ex + ew;
		double ymax = ey + eh;

		if (y0<=ymin && y1<=ymin) return numCrosses;
		if (y0>=ymax && y1>=ymax) return numCrosses;
		if (x0<=xmin && x1<=xmin) return numCrosses;

		if (x0>=xmax && x1>=xmax) {
			// The line is entirely at the right of the shadow
			if (y0<y1) {
				if (y0<=ymin) ++numCrosses;
				if (y1>=ymax) ++numCrosses;
			}
			else {
				if (y1<=ymin) --numCrosses;
				if (y0>=ymax) --numCrosses;
			}
		}
		else if (Ellipse2afp.intersectsEllipseSegment(
				xmin, ymin, xmax-xmin, ymax-ymin,
				x0, y0, x1, y1)) {
			return MathConstants.SHAPE_INTERSECTS;
		}
		else {
			double xcenter = (xmin+xmax)/2f;
			numCrosses += computeCrossingsFromPoint(xcenter, ymin, x0, y0, x1, y1);
			numCrosses += computeCrossingsFromPoint(xcenter, ymax, x0, y0, x1, y1);
		}

		return numCrosses;
	}

	/**
	 * Calculates the number of times the line from (x0,y0) to (x1,y1)
	 * crosses the ellipse (ex0,ey0) to (ex1,ey1) extending to the right.
	 * 
	 * @param crossings is the initial value for the number of crossings.
	 * @param cx is the center of the circle to extend.
	 * @param cy is the center of the circle to extend.
	 * @param radius is the radius of the circle to extend.
	 * @param x0 is the first point of the line.
	 * @param y0 is the first point of the line.
	 * @param x1 is the second point of the line.
	 * @param y1 is the secondpoint of the line.
	 * @return the crossing, or {@link MathConstants#SHAPE_INTERSECTS}.
	 */
	@Pure
	static int computeCrossingsFromCircle(
			int crossings,
			double cx, double cy,
			double radius,
			double x0, double y0,
			double x1, double y1) {
		int numCrosses = crossings;

		double xmin = cx - Math.abs(radius);
		double ymin = cy - Math.abs(radius);
		double ymax = cy + Math.abs(radius);

		if (y0<=ymin && y1<=ymin) return numCrosses;
		if (y0>=ymax && y1>=ymax) return numCrosses;
		if (x0<=xmin && x1<=xmin) return numCrosses;

		if (x0>=cx+radius && x1>=cx+radius) {
			// The line is entirely at the right of the shadow
			if (y0<y1) {
				if (y0<=ymin) ++numCrosses;
				if (y1>=ymax) ++numCrosses;
			}
			else {
				if (y1<=ymin) --numCrosses;
				if (y0>=ymax) --numCrosses;
			}
		}
		else if (Circle2afp.intersectsCircleSegment(
				cx, cy, radius,
				x0, y0, x1, y1)) {
			return MathConstants.SHAPE_INTERSECTS;
		}
		else {
			numCrosses += computeCrossingsFromPoint(cx, ymin, x0, y0, x1, y1);
			numCrosses += computeCrossingsFromPoint(cx, ymax, x0, y0, x1, y1);
		}

		return numCrosses;
	}

	/**
	 * Accumulate the number of times the line crosses the shadow
	 * extending to the right of the rectangle.  See the comment
	 * for the {@link MathConstants#SHAPE_INTERSECTS} constant for more complete details.
	 * 
	 * @param crossings is the initial value for the number of crossings.
	 * @param rxmin is the first corner of the rectangle.
	 * @param rymin is the first corner of the rectangle.
	 * @param rxmax is the second corner of the rectangle.
	 * @param rymax is the second corner of the rectangle.
	 * @param x0 is the first point of the line.
	 * @param y0 is the first point of the line.
	 * @param x1 is the second point of the line.
	 * @param y1 is the secondpoint of the line.
	 * @return the crossing, or {@link MathConstants#SHAPE_INTERSECTS}.
	 */
	@Pure
	static int computeCrossingsFromRect(
			int crossings,
			double rxmin, double rymin,
			double rxmax, double rymax,
			double x0, double y0,
			double x1, double y1) {
		int numCrosses = crossings;

		if (y0 >= rymax && y1 >= rymax) return numCrosses;
		if (y0 <= rymin && y1 <= rymin) return numCrosses;
		if (x0 <= rxmin && x1 <= rxmin) return numCrosses;
		if (x0 >= rxmax && x1 >= rxmax) {
			// Line is entirely to the right of the rect
			// and the vertical ranges of the two overlap by a non-empty amount
			// Thus, this line segment is partially in the "right-shadow"
			// Path may have done a complete crossing
			// Or path may have entered or exited the right-shadow
			if (y0 < y1) {
				// y-increasing line segment...
				// We know that y0 < rymax and y1 > rymin
				if (y0 <= rymin) ++numCrosses;
				if (y1 >= rymax) ++numCrosses;
			}
			else if (y1 < y0) {
				// y-decreasing line segment...
				// We know that y1 < rymax and y0 > rymin
				if (y1 <= rymin) --numCrosses;
				if (y0 >= rymax) --numCrosses;
			}
			return numCrosses;
		}
		// Remaining case:
		// Both x and y ranges overlap by a non-empty amount
		// First do trivial INTERSECTS rejection of the cases
		// where one of the endpoints is inside the rectangle.
		if ((x0 > rxmin && x0 < rxmax && y0 > rymin && y0 < rymax) ||
				(x1 > rxmin && x1 < rxmax && y1 > rymin && y1 < rymax)) {
			return MathConstants.SHAPE_INTERSECTS;
		}
		// Otherwise calculate the y intercepts and see where
		// they fall with respect to the rectangle
		double xi0 = x0;
		if (y0 < rymin) {
			xi0 += ((rymin - y0) * (x1 - x0) / (y1 - y0));
		}
		else if (y0 > rymax) {
			xi0 += ((rymax - y0) * (x1 - x0) / (y1 - y0));
		}
		double xi1 = x1;
		if (y1 < rymin) {
			xi1 += ((rymin - y1) * (x0 - x1) / (y0 - y1));
		}
		else if (y1 > rymax) {
			xi1 += ((rymax - y1) * (x0 - x1) / (y0 - y1));
		}
		if (xi0 <= rxmin && xi1 <= rxmin) return numCrosses;
		if (xi0 >= rxmax && xi1 >= rxmax) {
			if (y0 < y1) {
				// y-increasing line segment...
				// We know that y0 < rymax and y1 > rymin
				if (y0 <= rymin) ++numCrosses;
				if (y1 >= rymax) ++numCrosses;
			}
			else if (y1 < y0) {
				// y-decreasing line segment...
				// We know that y1 < rymax and y0 > rymin
				if (y1 <= rymin) --numCrosses;
				if (y0 >= rymax) --numCrosses;
			}
			return numCrosses;
		}
		return MathConstants.SHAPE_INTERSECTS;
	}

	/** Replies if two lines are intersecting.
	 * 
	 * @param x1 is the first point of the first line.
	 * @param y1 is the first point of the first line.
	 * @param x2 is the second point of the first line.
	 * @param y2 is the second point of the first line.
	 * @param x3 is the first point of the second line.
	 * @param y3 is the first point of the second line.
	 * @param x4 is the second point of the second line.
	 * @param y4 is the second point of the second line.
	 * @return <code>true</code> if the two shapes are intersecting; otherwise
	 * <code>false</code>
	 */
	@Pure
	static boolean intersectsLineLine(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
		if (isParallelLines(x1, y1, x2, y2, x3, y3, x4, y4)) {
			return Point2D.isCollinearPoints(x1, y1, x2, y2, x3, y3);
		}
		return true;
	}

	/** Replies if a segment and a line are intersecting.
	 * 
	 * @param x1 is the first point of the first segment.
	 * @param y1 is the first point of the first segment.
	 * @param x2 is the second point of the first segment.
	 * @param y2 is the second point of the first segment.
	 * @param x3 is the first point of the second line.
	 * @param y3 is the first point of the second line.
	 * @param x4 is the second point of the second line.
	 * @param y4 is the second point of the second line.
	 * @return <code>true</code> if the two shapes are intersecting; otherwise
	 * <code>false</code>
	 */
	@Pure
	static boolean intersectsSegmentLine(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
		return (computeSideLinePoint(x3, y3, x4, y4, x1, y1, Double.NaN) *
				computeSideLinePoint(x3, y3, x4, y4, x2, y2, Double.NaN)) <= 0;
	}

	/** Do an intersection test of two segments for ensuring that the answer of "no intersect" is safe.
	 * If the function replies <code>true</code>, it may
	 * This function considers that the ends of
	 * the segments are intersecting.
	 * 
	 * @param x1 is the first point of the first segment.
	 * @param y1 is the first point of the first segment.
	 * @param x2 is the second point of the first segment.
	 * @param y2 is the second point of the first segment.
	 * @param x3 is the first point of the second segment.
	 * @param y3 is the first point of the second segment.
	 * @param x4 is the second point of the second segment.
	 * @param y4 is the second point of the second segment.
	 * @return the type of intersection. 
	 * @see #intersectsSegmentSegmentWithEnds(double, double, double, double, double, double, double, double)
	 */
	@Pure
	static UncertainIntersection ensureNoSegmentSegmentWithEndsIntersection(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
		double vx1, vy1, vx2a, vy2a, vx2b, vy2b, f1, f2, sign;

		vx1 = x2 - x1;
		vy1 = y2 - y1;

		// Based on CCW. It is different than MathUtil.ccw(); because
		// this small algorithm is computing a ccw of 0 for colinear points.
		vx2a = x3 - x1;
		vy2a = y3 - y1;
		f1 = vx2a * vy1 - vy2a * vx1;

		vx2b = x4 - x1;
		vy2b = y4 - y1;
		f2 = vx2b * vy1 - vy2b * vx1;

		// s = f1 * f2
		//
		// f1  f2  s   intersect
		// -1  -1   1  F
		// -1   0   0  ON SEGMENT?
		// -1   1  -1  T
		//  0  -1   0  ON SEGMENT?
		//  0   0   0  SEGMENT INTERSECTION?
		//  0   1   1  ON SEGMENT?
		//  1  -1  -1  T
		//  1   0   0  ON SEGMENT?
		//  1   1   1  F
		sign = f1 * f2;

		if (sign<0f) return UncertainIntersection.PERHAPS;
		if (sign>0f) return UncertainIntersection.NO;

		double squaredLength = vx1*vx1 + vy1*vy1;

		if (f1==0f && f2==0f) {
			// Projection of the point on the segment line:
			// <0 -> means before first point
			// >1 -> means after second point
			// otherwhise on the segment.

			f1 = (vx2a * vx1 + vy2a * vy1) / squaredLength;
			f2 = (vx2b * vx1 + vy2b * vy1) / squaredLength;

			// No intersection when:
			// f1<0 && f2<0 or
			// f1>1 && f2>1

			return UncertainIntersection.fromBoolean((f1>=0f || f2>=0) && (f1<=1f || f2<=1f));
		}

		if (f1==0f) {
			// Projection of the point on the segment line:
			// <0 -> means before first point
			// >1 -> means after second point
			// otherwhise on the segment.

			f1 = (vx2a * vx1 + vy2a * vy1) / squaredLength;

			// No intersection when:
			// f1<=0 && f2<=0 or
			// f1>=1 && f2>=1

			return UncertainIntersection.fromBoolean(f1>=0f && f1<=1f);
		}

		if (f2==0f) {
			// Projection of the point on the segment line:
			// <0 -> means before first point
			// >1 -> means after second point
			// otherwhise on the segment.

			f2 = (vx2b * vx1 + vy2b * vy1) / squaredLength;

			// No intersection when:
			// f1<0 && f2<0 or
			// f1>1 && f2>1

			return UncertainIntersection.fromBoolean(f2>=0 && f2<=1f);
		}

		return UncertainIntersection.NO;
	}

	/** Do an intersection test of two segments for ensuring that the answer of "no intersect" is safe.
	 * If the function replies <code>true</code>, it may
	 * This function does not consider that the ends of
	 * the segments are intersecting.
	 * 
	 * @param x1 is the first point of the first segment.
	 * @param y1 is the first point of the first segment.
	 * @param x2 is the second point of the first segment.
	 * @param y2 is the second point of the first segment.
	 * @param x3 is the first point of the second segment.
	 * @param y3 is the first point of the second segment.
	 * @param x4 is the second point of the second segment.
	 * @param y4 is the second point of the second segment.
	 * @return the type of intersection. 
	 * @see #intersectsSegmentSegmentWithoutEnds(double, double, double, double, double, double, double, double)
	 */
	@Pure
	static UncertainIntersection ensureNoSegmentSegmentWithoutEndsIntersection(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
		double vx1, vy1, vx2a, vy2a, vx2b, vy2b, f1, f2, sign;

		vx1 = x2 - x1;
		vy1 = y2 - y1;

		vx2a = x3 - x1;
		vy2a = y3 - y1;
		f1 = vx2a * vy1 - vy2a * vx1;

		vx2b = x4 - x1;
		vy2b = y4 - y1;
		f2 = vx2b * vy1 - vy2b * vx1;

		// s = f1 * f2
		//
		// f1  f2  s   intersect
		// -1  -1   1  F
		// -1   0   0  F
		// -1   1  -1  T
		//  0  -1   0  F
		//  0   0   0  SEGMENT INTERSECTION?
		//  0   1   1  F
		//  1  -1  -1  T
		//  1   0   0  F
		//  1   1   1  F

		sign = f1 * f2;

		if (sign<0f) return UncertainIntersection.PERHAPS;
		if (sign>0f) return UncertainIntersection.NO;

		if (f1==0f && f2==0f) {
			// Projection of the point on the segment line:
			// <0 -> means before first point
			// >1 -> means after second point
			// otherwhise on the segment.

			double squaredLength = vx1*vx1 + vy1*vy1;

			f1 = (vx2a * vx1 + vy2a * vy1) / squaredLength;
			f2 = (vx2b * vx1 + vy2b * vy1) / squaredLength;

			// No intersection when:
			// f1<=0 && f2<=0 or
			// f1>=1 && f2>=1

			return UncertainIntersection.fromBoolean((f1>0f || f2>0) && (f1<1f || f2<1f));
		}

		return UncertainIntersection.NO;
	}

	/** Replies if two segments are intersecting.
	 * This function considers that the ends of
	 * the segments are not intersecting.
	 * To include the ends of the segments in the intersection ranges, see
	 * {@link #intersectsSegmentSegmentWithEnds(double, double, double, double, double, double, double, double)}.
	 * 
	 * @param x1 is the first point of the first segment.
	 * @param y1 is the first point of the first segment.
	 * @param x2 is the second point of the first segment.
	 * @param y2 is the second point of the first segment.
	 * @param x3 is the first point of the second segment.
	 * @param y3 is the first point of the second segment.
	 * @param x4 is the second point of the second segment.
	 * @param y4 is the second point of the second segment.
	 * @return <code>true</code> if the two shapes are intersecting; otherwise
	 * <code>false</code>
	 * @see #intersectsSegmentSegmentWithEnds(double, double, double, double, double, double, double, double)
	 */
	@Pure
	static boolean intersectsSegmentSegmentWithoutEnds(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
		UncertainIntersection r;
		r = ensureNoSegmentSegmentWithoutEndsIntersection(x1, y1, x2, y2, x3, y3, x4, y4);
		if (!r.booleanValue()) return r.booleanValue();
		return ensureNoSegmentSegmentWithoutEndsIntersection(x3, y3, x4, y4, x1, y1, x2, y2).booleanValue();
	}

	/** Replies if two segments are intersecting.
	 * This function considers that the ends of
	 * the segments are intersecting.
	 * To ignore the ends of the segments, see
	 * {@link #intersectsSegmentSegmentWithoutEnds(double, double, double, double, double, double, double, double)}.
	 * 
	 * @param x1 is the first point of the first segment.
	 * @param y1 is the first point of the first segment.
	 * @param x2 is the second point of the first segment.
	 * @param y2 is the second point of the first segment.
	 * @param x3 is the first point of the second segment.
	 * @param y3 is the first point of the second segment.
	 * @param x4 is the second point of the second segment.
	 * @param y4 is the second point of the second segment.
	 * @return <code>true</code> if the two shapes are intersecting; otherwise
	 * <code>false</code>
	 * @see #intersectsSegmentSegmentWithoutEnds(double, double, double, double, double, double, double, double)
	 */
	@Pure
	static boolean intersectsSegmentSegmentWithEnds(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
		UncertainIntersection r;
		r = ensureNoSegmentSegmentWithEndsIntersection(x1, y1, x2, y2, x3, y3, x4, y4);
		if (!r.booleanValue()) return r.booleanValue();
		return ensureNoSegmentSegmentWithEndsIntersection(x3, y3, x4, y4, x1, y1, x2, y2).booleanValue();
	}

	@Override
	default boolean isEmpty() {
		return getX1()==getX2() && getY1()==getY2();
	}

	/** Change the line.
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	// No default implementation for ensuring atomic change.
	public void set(double x1, double y1, double x2, double y2);

	/** Change the line.
	 * 
	 * @param a
	 * @param b
	 */
	default void set(Point2D a, Point2D b) {
		set(a.getX(), a.getY(), b.getX(), b.getY());
	}
	
	@Override
	default void set(IT s) {
		Rectangle2afp<?, ?, ?, ?, ?> box = s.toBoundingBox();
		set(box.getMinX(), box.getMinY(), box.getMaxX(), box.getMaxY());
	}

	/** Sets a new value in the X of the first point.
	 * 
	 * @param x the new value double x
	 */
	void setX1(double x);

	/**Sets a new value in the Y of the first point.
	 * 
	 * @param y the new value double y
	 */
	void setY1(double y);

	/**Sets a new value in the X of the second point.
	 * 
	 * @param x the new value double x
	 */
	void setX2(double x);

	/**Sets a new value in the Y of the second point.
	 * 
	 * @param y the new value double y
	 */
	void setY2(double y);

	/** Replies the X of the first point.
	 * 
	 * @return the x of the first point.
	 */
	@Pure
	double getX1();

	/** Replies the Y of the first point.
	 * 
	 * @return the y of the first point.
	 */
	@Pure
	double getY1();

	/** Replies the X of the second point.
	 * 
	 * @return the x of the second point.
	 */
	@Pure
	double getX2();

	/** Replies the Y of the second point.
	 * 
	 * @return the y of the second point.
	 */
	@Pure
	double getY2();

	/** Replies the first point.
	 * 
	 * @return the first point.
	 */
	@Pure
	Point2D getP1();

	/** Replies the second point.
	 * 
	 * @return the second point.
	 */
	@Pure
	Point2D getP2();

	/** Replies the length of the segment.
	 *
	 * @return the length.
	 */
	@Pure
	default double length() {
		return Point2D.getDistancePointPoint(getX1(), getY1(), getX2(), getY2());
	}

	/** Replies the squared length of the segment.
	 *
	 * @return the squared length.
	 */
	@Pure
	default double lengthSquared() {
		return Point2D.getDistanceSquaredPointPoint(getX1(), getY1(), getX2(), getY2());
	}

	@Pure
	@Override
	default void toBoundingBox(B box) {
		box.setFromCorners(
				this.getX1(),
				this.getY1(),
				this.getX2(),
				this.getY2());
	}

	@Pure
	@Override
	default double getDistanceSquared(Point2D p) {
		return getDistanceSquaredSegmentPoint(
				getX1(), getY1(),
				getX2(), getY2(),
				p.getX(), p.getY(),
				null);
	}

	@Pure
	@Override
	default double getDistanceL1(Point2D p) {
		double ratio = computeProjectedPointOnLine(p.getX(), p.getY(), getX1(), getY1(), getX2(), getY2());
		ratio = MathUtil.clamp(ratio, 0f, 1f);
		double vx = (getX2() - getX1()) * ratio;
		double vy = (getY2() - getY1()) * ratio;
		return Math.abs(getX1() + vx - p.getX())
				+ Math.abs(getY1() + vy - p.getY());
	}

	@Pure
	@Override
	default double getDistanceLinf(Point2D p) {
		double ratio = computeProjectedPointOnLine(p.getX(), p.getY(), getX1(), getY1(), getX2(), getY2());
		ratio = MathUtil.clamp(ratio, 0f, 1f);
		double vx = (getX2() - getX1()) * ratio;
		double vy = (getY2() - getY1()) * ratio;
		return Math.max(
				Math.abs(this.getX1() + vx - p.getX()),
				Math.abs(this.getY1() + vy - p.getY()));
	}
	
	/** {@inheritDoc}
	 * <p>
	 * This function uses the equal-to-zero test with the error {@link Math#ulp(double)}.
	 * 
	 * @see MathUtil#isEpsilonZero(double)
	 */
	@Override
	default boolean contains(double x, double y) {
		return MathUtil.isEpsilonZero(
				getDistanceSquaredSegmentPoint(
						getX1(), getY1(),
						getX2(), getY2(),
						x, y,
						null));
	}
	
	@Override
	default boolean contains(Rectangle2afp<?, ?, ?, ?, ?> r) {
		return contains(r.getMinX(), r.getMinY())
				&& contains(r.getMaxX(), r.getMaxY());
	}
	
	@Override
	default void translate(double dx, double dy) {
		set(getX1() + dx, getY1() + dy, getX2() + dx, getY2() + dy);
	}
	
	/** Transform the current segment.
	 * This function changes the current segment.
	 * 
	 * @param transform is the affine transformation to apply.
	 * @see #createTransformedShape
	 */
	default void transform(Transform2D transform) {
		Point2D p = new FakePoint(getX1(),  getY1());
		transform.transform(p);
		double x1 = p.getX();
		double y1 = p.getY();
		p.set(getX2(), getY2());
		transform.transform(p);
		set(x1, y1, p.getX(), p.getY());
	}
	
	@Override
	default void clear() {
		set(0, 0, 0, 0);
	}

	/** Clip the segment against the clipping rectangle
	 * according to the <a href="http://en.wikipedia.org/wiki/Cohen%E2%80%93Sutherland_algorithm">Cohen-Sutherland algorithm</a>.
	 * 
	 * @param rxmin is the min of the coordinates of the rectangle.
	 * @param rymin is the min of the coordinates of the rectangle.
	 * @param rxmax is the max of the coordinates of the rectangle.
	 * @param rymax is the max of the coordinates of the rectangle.
	 * @return <code>true</code> if the segment has an intersection with the
	 * rectangle and the segment was clipped; <code>false</code> if the segment
	 * does not intersect the rectangle.
	 */
	@Pure
	default boolean clipToRectangle(double rxmin, double rymin, double rxmax, double rymax) {
		double x0 = this.getX1();
		double y0 = this.getY1();
		double x1 = this.getX2();
		double y1 = this.getY2();
		int code1 = MathUtil.getCohenSutherlandCode(x0, y0, rxmin, rymin, rxmax, rymax);
		int code2 = MathUtil.getCohenSutherlandCode(x1, y1, rxmin, rymin, rxmax, rymax);
		boolean accept = false;
		boolean cont = true;
		double x, y;
		x = y = 0;

		while (cont) {
			if ((code1 | code2)==0) {
				// Bitwise OR is 0. Trivially accept and get out of loop
				accept = true;
				cont = false;
			}
			else if ((code1 & code2)!=0) {
				// Bitwise AND is not 0. Trivially reject and get out of loop
				cont = false;
			}
			else {
				// failed both tests, so calculate the line segment to clip
				// from an outside point to an intersection with clip edge

				// At least one endpoint is outside the clip rectangle; pick it.
				int code3 = code1!=0 ? code1 : code2;

				// Now find the intersection point;
				// use formulas y = y0 + slope * (x - x0), x = x0 + (1 / slope) * (y - y0)
				if ((code3 & MathConstants.COHEN_SUTHERLAND_TOP)!=0) {
					// point is above the clip rectangle
					x = x0 + (x1 - x0) * (rymax - y0) / (y1 - y0);
					y = rymax;
				}
				else if ((code3 & MathConstants.COHEN_SUTHERLAND_BOTTOM)!=0) {
					// point is below the clip rectangle
					x = x0 + (x1 - x0) * (rymin - y0) / (y1 - y0);
					y = rymin;
				}
				else if ((code3 & MathConstants.COHEN_SUTHERLAND_RIGHT)!=0) { 
					// point is to the right of clip rectangle
					y = y0 + (y1 - y0) * (rxmax - x0) / (x1 - x0);
					x = rxmax;
				}
				else if ((code3 & MathConstants.COHEN_SUTHERLAND_LEFT)!=0) {
					// point is to the left of clip rectangle
					y = y0 + (y1 - y0) * (rxmin - x0) / (x1 - x0);
					x = rxmin;
				}
				else {
					code3 = 0;
				}

				if (code3!=0) {
					// Now we move outside point to intersection point to clip
					// and get ready for next pass.
					if (code3 == code1) {
						x0 = x;
						y0 = y;
						code1 = MathUtil.getCohenSutherlandCode(x0, y0, rxmin, rymin, rxmax, rymax);
					}
					else {
						x1 = x;
						y1 = y;
						code2 = MathUtil.getCohenSutherlandCode(x1, y1, rxmin, rymin, rxmax, rymax);
					}
				}
			}
		}
		if (accept) {
			set(x0, y0, x1, y1);
		}
		return accept;
	}
	
	@Override
	default boolean intersects(Circle2afp<?, ?, ?, ?, ?> s) {
		return Circle2afp.intersectsCircleSegment(
				s.getX(), s.getY(),
				s.getRadius(),
				getX1(), getY1(),
				getX2(), getY2());
	}
	
	@Override
	default boolean intersects(Ellipse2afp<?, ?, ?, ?, ?> s) {
		return Ellipse2afp.intersectsEllipseSegment(
				s.getMinX(), s.getMinY(),
				s.getWidth(), s.getHeight(),
				getX1(), getY1(),
				getX2(), getY2());
	}
	
	@Override
	default boolean intersects(OrientedRectangle2afp<?, ?, ?, ?, ?> s) {
		return OrientedRectangle2afp.intersectsOrientedRectangleSegment(
				s.getCenterX(), s.getCenterY(), 
				s.getFirstAxisX(), s.getFirstAxisY(), s.getFirstAxisExtent(),
				s.getSecondAxisX(), s.getSecondAxisY(), s.getSecondAxisExtent(),
				getX1(), getY1(), getX2(), getY2());
	}
	
	@Override
	default boolean intersects(Rectangle2afp<?, ?, ?, ?, ?> s) {
		return Rectangle2afp.intersectsRectangleSegment(
				s.getMinX(), s.getMinY(),
				s.getMaxX(), s.getMaxY(),
				getX1(), getY1(),
				getX2(), getY2());
	}
	
	@Override
	default boolean intersects(RoundRectangle2afp<?, ?, ?, ?, ?> s) {
		return s.intersects(this);
	}
	
	@Override
	default boolean intersects(Segment2afp<?, ?, ?, ?, ?> s) {
		return intersectsSegmentSegmentWithEnds(
				getX1(), getY1(),
				getX2(), getY2(),
				s.getX1(), s.getY1(),
				s.getX2(), s.getY2());
	}
	
	@Override
	default boolean intersects(PathIterator2afp<?> iterator) {
		int mask = (iterator.getWindingRule() == PathWindingRule.NON_ZERO ? -1 : 2);
		int crossings = Path2afp.computeCrossingsFromSegment(
				0,
				iterator,
				getX1(), getY1(), getX2(), getY2(),
				false);
		return (crossings == MathConstants.SHAPE_INTERSECTS ||
				(crossings & mask) != 0);

	}

	/** Fonctional interface that represented a 2D segment/line on a plane.
	 *
	 * @author $Author: sgalland$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 */
	enum UncertainIntersection {
		/** Intersection, uncertainly.
		 */
		PERHAPS {
			@Override
			public boolean booleanValue() {
				return true;
			}
		},
		/** No intersection, certainly.
		 */
		NO {
			@Override
			public boolean booleanValue() {
				return false;
			}
		};
		
		/** Replies the boolean representation of the constant.
		 *
		 * @return the boolean representation.
		 */
		public abstract boolean booleanValue();

		/** Replies the UncertainIntersection for the given boolean.
		 *
		 * @param value the boolean value.
		 * @return the UncertainIntersection.
		 */
		public static UncertainIntersection fromBoolean(boolean value) {
			if (value) {
				return UncertainIntersection.PERHAPS;
			}
			return UncertainIntersection.NO;
		}
		
	}

	@Pure
	@Override
	default PathIterator2afp<IE> getPathIterator(Transform2D transform) {
		return new SegmentPathIterator<>(this, transform);
	}

	@Pure
	@Override
	default P getClosestPointTo(Point2D p) {
		P point = getGeomFactory().newPoint();
		Segment2afp.computeClosestPointTo(
				getX1(), getY1(),
				getX2(), getY2(),
				p.getX(), p.getY(),
				point);
		return point;
	}

	@Pure
	@Override
	default P getFarthestPointTo(Point2D p) {
		P point = getGeomFactory().newPoint();
		Segment2afp.computeFarthestPointTo(
				getX1(), getY1(),
				getX2(), getY2(),
				p.getX(), p.getY(),
				point);
		return point;
	}

	/** Iterator on the path elements of the segment.
	 * 
	 * @param <T> the type of the path elements.
	 * @author $Author: sgalland$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 * @since 13.0
	 */
	static class SegmentPathIterator<T extends PathElement2afp> implements PathIterator2afp<T> {

		private final GeomFactory2afp<T, ?, ?> factory;

		private final Point2D p1;

		private final Point2D p2;

		private final Transform2D transform;

		private final double x1;

		private final double y1;

		private final double x2;

		private final double y2;

		private int index = 0;

		/**
		 * @param segment the iterated segment.
		 * @param transform the transformation, or <code>null</code>.
		 */
		public SegmentPathIterator(Segment2afp<?, ?, T, ?, ?> segment, Transform2D transform) {
			this.factory = segment.getGeomFactory();
			this.p1 = this.factory.newPoint();
			this.p2 = this.factory.newPoint();
			this.transform = (transform == null || transform.isIdentity()) ? null : transform;
			this.x1 = segment.getX1();
			this.y1 = segment.getY1();
			this.x2 = segment.getX2();
			this.y2 = segment.getY2();
			if (this.x1 == this.x2 && this.y1 == this.y2) {
				this.index = 2;
			}
		}

		@Pure
		@Override
		public boolean hasNext() {
			return this.index <= 1;
		}

		@Override
		public T next() {
			if (this.index>1) throw new NoSuchElementException();
			int idx = this.index;
			++this.index;
			switch(idx) {
			case 0:
				this.p2.set(this.x1, this.y1);
				if (this.transform!=null) {
					this.transform.transform(this.p2);
				}
				return this.factory.newMovePathElement(
						this.p2.getX(), this.p2.getY());
			case 1:
				this.p1.set(this.p2);
				this.p2.set(this.x2, this.y2);
				if (this.transform!=null) {
					this.transform.transform(this.p2);
				}
				return this.factory.newLinePathElement(
						this.p1.getX(), this.p1.getY(),
						this.p2.getX(), this.p2.getY());
			default:
				throw new NoSuchElementException();
			}
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

		@Pure
		@Override
		public PathWindingRule getWindingRule() {
			return PathWindingRule.NON_ZERO;
		}

		@Pure
		@Override
		public boolean isPolyline() {
			return true;
		}

		@Pure
		@Override
		public boolean isCurved() {
			return false;
		}
		
		@Pure
		@Override
		public boolean isPolygon() {
			return false;
		}

		@Pure
		@Override
		public boolean isMultiParts() {
			return false;
		}

		@Override
		public GeomFactory2afp<T, ?, ?> getGeomFactory() {
			return this.factory;
		}

	}

}
