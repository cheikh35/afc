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
package org.arakhne.afc.math.geometry.d3.continuous;

import org.arakhne.afc.math.MathUtil;
import org.arakhne.afc.math.geometry.d3.Point3D;



/** 3D sphere with floating-point points.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class Sphere3f extends AbstractShape3f<Sphere3f> {

	private static final long serialVersionUID = -3227474955850738842L;

	/** Replies if the specified box intersects the specified sphere.
	 *
	 * @param sphereCenterx x coordinate of the sphere center.
	 * @param sphereCentery y coordinate of the sphere center.
	 * @param sphereCenterz z coordinate of the sphere center.
	 * @param sphereRadius is the radius of the sphere.
	 * @param boxCenterx x coordinate of the center point of the oriented box.
	 * @param boxCentery y coordinate of the center point of the oriented box.
	 * @param boxCenterz z coordinate of the center point of the oriented box.
	 * @param boxAxis1x x coordinate of the first axis of the oriented box axis.
	 * @param boxAxis1y y coordinate of the first axis of the oriented box axis.
	 * @param boxAxis1z z coordinate of the first axis of the oriented box axis.
	 * @param boxAxis2x x coordinate of the second axis of the oriented box axis.
	 * @param boxAxis2y y coordinate of the second axis of the oriented box axis.
	 * @param boxAxis2z z coordinate of the second axis of the oriented box axis.
	 * @param boxAxis3x x coordinate of the third axis of the oriented box axis.
	 * @param boxAxis3y y coordinate of the third axis of the oriented box axis.
	 * @param boxAxis3z z coordinate of the third axis of the oriented box axis.
	 * @param boxExtentAxis1 extent of the first axis of the oriented box.
	 * @param boxExtentAxis2 extent of the second axis of the oriented box.
	 * @param boxExtentAxis3 extent of the third axis of the oriented box.
	 * @return <code>true</code> if intersecting, otherwise <code>false</code>
	 */
	public static boolean intersectsSolidSphereOrientedBox(
			double sphereCenterx, double sphereCentery, double sphereCenterz, double sphereRadius,
			double boxCenterx, double boxCentery, double boxCenterz,
			double boxAxis1x, double boxAxis1y, double boxAxis1z,
			double boxAxis2x, double boxAxis2y, double boxAxis2z,
			double boxAxis3x, double boxAxis3y, double boxAxis3z,
			double boxExtentAxis1, double boxExtentAxis2, double boxExtentAxis3) {
		// Find points on OBB closest and farest to sphere center
		Point3f closest = new Point3f();
		Point3f farest = new Point3f();

		OrientedBox3f.computeClosestFarestOBBPoints(
				sphereCenterx,  sphereCentery,  sphereCenterz,
				boxCenterx, boxCentery, boxCenterz,
				boxAxis1x, boxAxis1y, boxAxis1z,
				boxAxis2x, boxAxis2y, boxAxis2z,
				boxAxis3x, boxAxis3y, boxAxis3z,
				boxExtentAxis1, boxExtentAxis2, boxExtentAxis3,
				closest,
				farest);

		// Sphere and OBB intersect if the (squared) distance from sphere
		// center to point p is less than the (squared) sphere radius
		double squaredRadius = sphereRadius * sphereRadius;

		return (Point3f.distanceSquaredPointPoint(
				sphereCenterx, sphereCentery, sphereCenterz,
				closest.getX(), closest.getY(), closest.getZ()) < squaredRadius);
	}

	/**
	 * Replies if the specified sphere intersects the specified capsule.
	 * @param sphereCenterx - center of the sphere
	 * @param sphereCentery - center of the sphere
	 * @param sphereCenterz - center of the sphere
	 * @param sphereRadius - radius of the sphere
	 * @param capsuleAx - Medial line segment start point of the capsule
	 * @param capsuleAy - Medial line segment start point of the capsule
	 * @param capsuleAz - Medial line segment start point of the capsule
	 * @param capsuleBx - Medial line segment end point of the capsule
	 * @param capsuleBy - Medial line segment end point of the capsule
	 * @param capsuleBz - Medial line segment end point of the capsule
	 * @param capsuleRadius - radius of the capsule
	 * @return <code>true</code> if intersecting, otherwise <code>false</code>
	 */
	public static boolean intersectsSphereCapsule(
			double sphereCenterx, double sphereCentery, double sphereCenterz, double sphereRadius,
			double capsuleAx, double capsuleAy, double capsuleAz,
			double capsuleBx, double capsuleBy, double capsuleBz,
			double capsuleRadius) {
		// Compute (squared) distance between sphere center and capsule line segment
		double dist2 = Segment3f.distanceSquaredSegmentPoint(
				capsuleAx, capsuleAy, capsuleAz, capsuleBx, capsuleBy, capsuleBz,
				sphereCenterx, sphereCentery, sphereCenterz);
		// If (squared) distance smaller than (squared) sum of radii, they collide
		double radius = sphereRadius + capsuleRadius;
		return dist2 < radius * radius;
	}

	/** Replies if the specified box intersects the specified sphere.
     * <p>
     * A Simple Method for Box-Sphere Intersection Testing by Jim Arvo
     * from "Graphics Gems", Academic Press, 1990
     * <p>
     * This routine tests for intersection between an 3-dimensional             
     * axis-aligned box and an 3-dimensional sphere.  The algorithm type
     * argument indicates whether the objects are to be regarded as
     * surfaces or solids.                                                     
     * 
     * @param sphereCenterx are the coordinates of the sphere center.
     * @param sphereCentery are the coordinates of the sphere center.
     * @param sphereCenterz are the coordinates of the sphere center.
     * @param radius is the radius of the sphere.
     * @param lowerx coordinates of the lowest point of the box.
     * @param lowery coordinates of the lowest point of the box.
     * @param lowerz coordinates of the lowest point of the box.
     * @param upperx coordinates of the uppermost point of the box.
     * @param uppery coordinates of the uppermost point of the box.
     * @param upperz coordinates of the uppermost point of the box.
     * @return <code>true</code> if intersecting, otherwise <code>false</code>
     */
    public static boolean intersectsSolidSphereHollowAlignedBox(
    		double sphereCenterx, double sphereCentery, double sphereCenterz, double radius,
			double lowerx, double lowery, double lowerz,
			double upperx, double uppery, double upperz) {
    	double r2 = radius*radius;
    	double a, dmin;
    	boolean face;

		dmin = 0;
		
		face = false;

		// X
		if ( sphereCenterx < lowerx ) {
			face = true;
			a = sphereCenterx - lowerx;
			dmin += a*a;
		} else if ( sphereCenterx > upperx ) {
			face = true;
			a = sphereCenterx - upperx;
			dmin += a*a;     
		} else if ( sphereCenterx - lowerx <= radius ) {
			face = true;
		} else if ( upperx - sphereCenterx <= radius ) {
			face = true;
		}
		
		// Y
		if ( sphereCentery < lowery ) {
			face = true;
			a = sphereCentery - lowery;
			dmin += a*a;
		}
		else if ( sphereCentery > uppery ) {
			face = true;
			a = sphereCentery - uppery;
			dmin += a*a;     
		}
		else if ( sphereCentery - lowery <= radius ) face = true;
		else if ( uppery - sphereCentery <= radius ) face = true;
		
		// Z
		if ( sphereCenterz < lowerz ) {
			face = true;
			a = sphereCenterz - lowerz;
			dmin += a*a;
		}
		else if ( sphereCenterz > upperz ) {
			face = true;
			a = sphereCenterz - upperz;
			dmin += a*a;     
		}
		else if ( sphereCenterz - lowerz <= radius ) face = true;
		else if ( upperz - sphereCenterz <= radius ) face = true;
		
		return ( face && ( dmin <= r2 ) );
    }

    /** Replies if the specified box intersects the specified sphere.
     * <p>
     * A Simple Method for Box-Sphere Intersection Testing by Jim Arvo
     * from "Graphics Gems", Academic Press, 1990
     * <p>
     * This routine tests for intersection between an 3-dimensional             
     * axis-aligned box and an 3-dimensional sphere.  The algorithm type
     * argument indicates whether the objects are to be regarded as
     * surfaces or solids.                                                     
     * 
     * @param sphereCenterx are the coordinates of the sphere center.
     * @param sphereCentery are the coordinates of the sphere center.
     * @param sphereCenterz are the coordinates of the sphere center.
     * @param radius is the radius of the sphere.
     * @param lowerx coordinates of the lowest point of the box.
     * @param lowery coordinates of the lowest point of the box.
     * @param lowerz coordinates of the lowest point of the box.
     * @param upperx coordinates of the uppermost point of the box.
     * @param uppery coordinates of the uppermost point of the box.
     * @param upperz coordinates of the uppermost point of the box.
     * @return <code>true</code> if intersecting, otherwise <code>false</code>
     */
    public static boolean intersectsHollowSphereSolidAlignedBox(
    		double sphereCenterx, double sphereCentery, double sphereCenterz, double radius,
			double lowerx, double lowery, double lowerz,
			double upperx, double uppery, double upperz) {
    	double r2 = radius*radius;
    	double a, b, dmin, dmax;
    	
    	dmax = 0;
    	dmin = 0;

    	// X
    	a = sphereCenterx - lowerx;
    	a = a*a;
    	
    	b = sphereCenterx - upperx;
    	b = b*b;
    	
    	dmax += Math.max(a, b);
    	if( sphereCenterx < lowerx ) dmin += a;
    	else if( sphereCenterx > upperx ) dmin += b;

    	// Y
    	a = sphereCentery - lowery;
    	a = a*a;
    	
    	b = sphereCentery - uppery;
    	b = b*b;
    	
    	dmax += Math.max(a, b);
    	if( sphereCentery < lowery ) dmin += a;
    	else if( sphereCentery > uppery ) dmin += b;

    	// Z
    	a = sphereCenterz - lowerz;
    	a = a*a;
    	
    	b = sphereCenterz - upperz;
    	b = b*b;
    	
    	dmax += Math.max(a, b);
    	if( sphereCenterz < lowerz ) dmin += a;
    	else if( sphereCenterz > upperz ) dmin += b;

    	return ( dmin <= r2 && r2 <= dmax );
    }

    /** Replies if the specified box intersects the specified sphere.
     * <p>
     * A Simple Method for Box-Sphere Intersection Testing by Jim Arvo
     * from "Graphics Gems", Academic Press, 1990
     * <p>
     * This routine tests for intersection between an 3-dimensional             
     * axis-aligned box and an 3-dimensional sphere.  The algorithm type
     * argument indicates whether the objects are to be regarded as
     * surfaces or solids.                                                     
     * 
     * @param sphereCenterx are the coordinates of the sphere center.
     * @param sphereCentery are the coordinates of the sphere center.
     * @param sphereCenterz are the coordinates of the sphere center.
     * @param radius is the radius of the sphere.
     * @param lowerx coordinates of the lowest point of the box.
     * @param lowery coordinates of the lowest point of the box.
     * @param lowerz coordinates of the lowest point of the box.
     * @param upperx coordinates of the uppermost point of the box.
     * @param uppery coordinates of the uppermost point of the box.
     * @param upperz coordinates of the uppermost point of the box.
     * @return <code>true</code> if intersecting, otherwise <code>false</code>
     */
    public static boolean intersectsHollowSphereHollowAlignedBox(
    		double sphereCenterx, double sphereCentery, double sphereCenterz, double radius,
    		double lowerx, double lowery, double lowerz,
    		double upperx, double uppery, double upperz) {
    	double r2 = radius*radius;
    	double a, b, dmin, dmax;
    	boolean face;

		dmin = 0;
		dmax = 0;
		
		face = false;

		// X
		a = sphereCenterx - lowerx;
		a = a*a;

		b = sphereCenterx - upperx;
		b = b*b;
		
		dmax += Math.max(a, b);
		
		if( sphereCenterx < lowerx ) {
			face = true;
			dmin += a;
		}
		else if( sphereCenterx > upperx ) {
			face = true;
			dmin += b;
		}
		else if( Math.min(a, b) <= r2 ) {
			face = true;
		}
		
		// Y
		a = sphereCentery - lowery;
		a = a*a;

		b = sphereCentery - uppery;
		b = b*b;
		
		dmax += Math.max(a, b);
		
		if( sphereCentery < lowery ) {
			face = true;
			dmin += a;
		}
		else if( sphereCentery > uppery ) {
			face = true;
			dmin += b;
		}
		else if( Math.min(a, b) <= r2 ) {
			face = true;
		}
		
		// Z
		a = sphereCenterz - lowerz;
		a = a*a;

		b = sphereCenterz - upperz;
		b = b*b;
		
		dmax += Math.max(a, b);
		
		if( sphereCenterz < lowerz ) {
			face = true;
			dmin += a;
		}
		else if( sphereCenterz > upperz ) {
			face = true;
			dmin += b;
		}
		else if( Math.min(a, b) <= r2 ) {
			face = true;
		}
		
		return (face && ( dmin <= r2 ) && ( r2 <= dmax));
    }

    /**
	 * Replies if the given point is inside the given sphere.
	 *
	 * @param cx is the center of the sphere.
	 * @param cy is the center of the sphere.
	 * @param cz is the center of the sphere.
	 * @param radius is the radius of the sphere.
	 * @param px is the point to test.
	 * @param py is the point to test.
	 * @param pz is the point to test.
	 * @return <code>true</code> if the point is inside the circle;
	 * <code>false</code> if not.
	 */
	public static boolean containsSpherePoint(double cx, double cy, double cz, double radius,
			double px, double py, double pz) {
		return Point3f.distanceSquaredPointPoint(
				px, py, pz,
				cx, cy, cz) <= (radius * radius);
	}

	/** Replies if an aligned box is inside in the sphere.
	 * 
	 * @param cx is the center of the sphere.
	 * @param cy is the center of the sphere.
	 * @param cz is the center of the sphere.
	 * @param radius is the radius of the sphere.
	 * @param bx is the lowest corner of the box.
	 * @param by is the lowest corner of the box.
	 * @param bz is the lowest corner of the box.
	 * @param bsx is the X size of the box.
	 * @param bsy is the Y size of the box.
	 * @param bsz is the Z size of the box.
	 * @return <code>true</code> if the given box is inside the sphere;
	 * otherwise <code>false</code>.
	 */
	public static boolean containsSphereAlignedBox(
			double cx, double cy, double cz, double radius,
			double bx, double by, double bz, double bsx, double bsy, double bsz) {
		double rcx = (bx + bsx/2f);
		double rcy = (by + bsy/2f);
		double rcz = (bz + bsz/2f);
		double farX;
		if (cx<=rcx) farX = bx + bsx;
		else farX = bx;
		double farY;
		if (cy<=rcy) farY = by + bsy;
		else farY = by;
		double farZ;
		if (cz<=rcz) farZ = bz + bsz;
		else farZ = bz;
		return containsSpherePoint(cx, cy, cz, radius, farX, farY, farZ);
	}

	/** Replies if two spheres are intersecting.
	 * 
	 * @param x1 is the center of the first sphere
	 * @param y1 is the center of the first sphere
	 * @param z1 is the center of the first sphere
	 * @param radius1 is the radius of the first sphere
	 * @param x2 is the center of the second sphere
	 * @param y2 is the center of the second sphere
	 * @param z2 is the center of the second sphere
	 * @param radius2 is the radius of the second sphere
	 * @return <code>true</code> if the two shapes are intersecting; otherwise
	 * <code>false</code>
	 */
	public static boolean intersectsSphereSphere(
			double x1, double y1, double z1, double radius1,
			double x2, double y2, double z2, double radius2) {
		double r = radius1+radius2;
		return Point3f.distanceSquaredPointPoint(x1, y1, z1, x2, y2, z2) < (r*r);
	}

	/** Replies if a sphere and a aligned box are intersecting.
     * <p>
     * A Simple Method for Box-Sphere Intersection Testing by Jim Arvo
     * from "Graphics Gems", Academic Press, 1990
     * <p>
     * This routine tests for intersection between an 3-dimensional             
     * axis-aligned box and an 3-dimensional sphere.  The algorithm type
     * argument indicates whether the objects are to be regarded as
     * surfaces or solids.                                                     
	 * 
	 * @param x1 is the center of the sphere
	 * @param y1 is the center of the sphere
	 * @param z1 is the center of the sphere
	 * @param radius is the radius of the sphere
	 * @param bx is the lowest corner of the box.
	 * @param by is the lowest corner of the box.
	 * @param bz is the lowest corner of the box.
	 * @param bsx is the X size of the box.
	 * @param bsy is the Y size of the box.
	 * @param bsz is the Z size of the box.
	 * @return <code>true</code> if the two shapes are intersecting; otherwise
	 * <code>false</code>
	 */
	public static boolean intersectsSolidSphereSolidAlignedBox(
			double x1, double y1, double z1, double radius,
			double bx, double by, double bz, double bsx, double bsy, double bsz) {
		double dx;
		if (x1<bx) {
			dx = bx - x1;
		}
		else if (x1>(bx+bsx)) {
			dx = x1 - (bx+bsx);
		}
		else {
			dx = 0f;
		}
		double dy;
		if (y1<by) {
			dy = by - y1;
		}
		else if (y1>(by+bsy)) {
			dy = y1 - (by+bsy);
		}
		else {
			dy = 0f;
		}
		double dz;
		if (z1<bz) {
			dz = bz - z1;
		}
		else if (z1>(bz+bsz)) {
			dz = z1 - (bz+bsz);
		}
		else {
			dz = 0f;
		}
		return (dx*dx+dy*dy+dz*dz) < (radius*radius*radius);
	}

	/** Replies if a sphere and a line are intersecting.
	 * 
	 * @param x1 is the center of the sphere
	 * @param y1 is the center of the sphere
	 * @param z1 is the center of the sphere
	 * @param radius is the radius of the sphere
	 * @param x2 is the first point of the line.
	 * @param y2 is the first point of the line.
	 * @param z2 is the first point of the line.
	 * @param x3 is the second point of the line.
	 * @param y3 is the second point of the line.
	 * @param z3 is the second point of the line.
	 * @return <code>true</code> if the two shapes are intersecting; otherwise
	 * <code>false</code>
	 */
	public static boolean intersectsSphereLine(
			double x1, double y1, double z1, double radius,
			double x2, double y2, double z2,
			double x3, double y3, double z3) {
		double d = Segment3f.distanceSquaredLinePoint(x2, y2, z2, x3, y3, z3, x1, y1, z1);
		return d<(radius*radius);
	}

	/** Replies if a sphere and a segment are intersecting.
	 * 
	 * @param x1 is the center of the sphere
	 * @param y1 is the center of the sphere
	 * @param z1 is the center of the sphere
	 * @param radius is the radius of the sphere
	 * @param x2 is the first point of the line.
	 * @param y2 is the first point of the line.
	 * @param z2 is the first point of the line.
	 * @param x3 is the second point of the line.
	 * @param y3 is the second point of the line.
	 * @param z3 is the second point of the line.
	 * @return <code>true</code> if the two shapes are intersecting; otherwise
	 * <code>false</code>
	 */
	public static boolean intersectsSphereSegment(
			double x1, double y1, double z1, double radius,
			double x2, double y2, double z2,
			double x3, double y3, double z3) {
		double d = Segment3f.distanceSquaredSegmentPoint(x2, y2, z2, x3, y3, z3, x1, y1, z1);
		return d<(radius*radius);
	}

    /** X-coordinate of the sphere center. */
	protected double cx = 0f;

	/** Y-coordinate of the sphere center. */
	protected double cy = 0f;

	/** Z-coordinate of the sphere center. */
	protected double cz = 0f;

	/** Radius of the sphere center (must be always positive). */
	protected double radius = 0f;

	/**
	 */
	public Sphere3f() {
		//
	}

	/**
	 * @param center
	 * @param radius
	 */
	public Sphere3f(Point3D center, double radius) {
		set(center, radius);
	}

	/**
	 * @param x
	 * @param y
	 * @param z
	 * @param radius
	 */
	public Sphere3f(double x, double y, double z, double radius) {
		set(x, y, z, radius);
	}
	
	/** Construct a sphere from a sphere.
	 * @param c
	 */
	public Sphere3f(Sphere3f c) {
		this.cx = c.cx;
		this.cy = c.cy;
		this.cz = c.cz;
		this.radius = c.radius;
	}

	@Override
	public void clear() {
		this.cx = this.cy = this.cz = 0f;
		this.radius = 0f;
	}
	
	/** Replies if the sphere is empty.
	 * The sphere is empty when the radius is nul.
	 * 
	 * @return <code>true</code> if the radius is nul;
	 * otherwise <code>false</code>.
	 */
	@Override
	public boolean isEmpty() {
		return this.radius<=+0f;
	}

	/** Change the frame of the sphere.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param radius
	 */
	public void set(double x, double y, double z, double radius) {
		this.cx = x;
		this.cy = y;
		this.cz = z;
		this.radius = Math.abs(radius);
	}

	/** Change the frame of te sphere.
	 * 
	 * @param center
	 * @param radius
	 */
	public void set(Point3D center, double radius) {
		this.cx = center.getX();
		this.cy = center.getY();
		this.cz = center.getZ();
		this.radius = Math.abs(radius);
	}

	@Override
	public void set(Shape3f s) {
		if (s instanceof Sphere3f) {
			Sphere3f c = (Sphere3f) s;
			set(c.getX(), c.getY(), c.getZ(), c.getRadius());
		} else {
			AlignedBox3f r = s.toBoundingBox();
			set(r.getCenterX(), r.getCenterY(), r.getCenterZ(),
				MathUtil.min(r.getSizeX(), r.getSizeY(), r.getSizeZ()) / 2.);
		}
	}

	/** Replies the center X.
	 * 
	 * @return the center x.
	 */
	public double getX() {
		return this.cx;
	}

	/** Replies the center y.
	 * 
	 * @return the center y.
	 */
	public double getY() {
		return this.cy;
	}

	/** Replies the center z.
	 * 
	 * @return the center z.
	 */
	public double getZ() {
		return this.cz;
	}

	/** Replies the center.
	 * 
	 * @return a copy of the center.
	 */
	public Point3f getCenter() {
		return new Point3f(this.cx, this.cy, this.cz);
	}

	/** Change the center.
	 * 
	 * @param center
	 */
	public void setCenter(Point3D center) {
		this.cx = center.getX();
		this.cy = center.getY();
		this.cz = center.getZ();
	}

	/** Change the center.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setCenter(double x, double y, double z) {
		this.cx = x;
		this.cy = y;
		this.cz = z;
	}

	/** Replies the radius.
	 * 
	 * @return the radius.
	 */
	public double getRadius() {
		return this.radius;
	}

	/** Set the radius.
	 * 
	 * @param radius is the radius.
	 */
	public void setRadius(double radius) {
		this.radius = Math.abs(radius);
	}

	/** {@inheritDoc}
	 */
	@Override
	public AlignedBox3f toBoundingBox() {
		AlignedBox3f r = new AlignedBox3f();
		r.setFromCorners(
				this.cx-this.radius,
				this.cy-this.radius,
				this.cz-this.radius,
				this.cx+this.radius,
				this.cy+this.radius,
				this.cz+this.radius);
		return r;
	}

	/** {@inheritDoc}
	 */
	@Override
	public void toBoundingBox(AlignedBox3f box) {
		box.setFromCorners(
				this.cx-this.radius,
				this.cy-this.radius,
				this.cz-this.radius,
				this.cx+this.radius,
				this.cy+this.radius,
				this.cz+this.radius);
	}

	/** {@inheritDoc}
	 */
	@Override
	public double distance(Point3D p) {
		double d = Point3f.distancePointPoint(getX(), getY(), getZ(), p.getX(), p.getY(), p.getZ()) - getRadius();
		return MathUtil.max(0., d);
	}
	
	/** {@inheritDoc}
	 */
	@Override
	public double distanceSquared(Point3D p) {
		double d = Point3f.distanceSquaredPointPoint(getX(), getY(), getZ(), p.getX(), p.getY(), p.getZ()) - getRadius();
		return MathUtil.max(0., d);
	}

	/** {@inheritDoc}
	 */
	@Override
	public double distanceL1(Point3D p) {
		Point3D r = getClosestPointTo(p);
		return r.distanceL1(p);
	}

	/** {@inheritDoc}
	 */
	@Override
	public double distanceLinf(Point3D p) {
		Point3D r = getClosestPointTo(p);
		return r.distanceLinf(p);
	}

	/** {@inheritDoc}
	 */
	@Override
	public boolean contains(double x, double y, double z) {
		return containsSpherePoint(getX(), getY(), getZ(), getRadius(), x, y, z);
	}

	/** {@inheritDoc}
	 */
	@Override
	public Point3f getClosestPointTo(Point3D p) {
		Vector3f v = new Vector3f(p);
		v.sub(this.cx, this.cy, this.cz);
		double l = v.lengthSquared();
		if (l<=(this.radius*this.radius)) {
			if (p instanceof Point3f) return (Point3f)p;
			return new Point3f(p);
		}
		double s = this.radius/Math.sqrt(l);
		v.scale(s);
		return new Point3f(this.cx + v.getX(), this.cy + v.getY(), this.cz + v.getZ());
	}

	/** {@inheritDoc}
	 */
	@Override
	public Point3f getFarthestPointTo(Point3D p) {
		Vector3f v = new Vector3f(
				this.cx - p.getX(),
				this.cy - p.getY(),
				this.cz - p.getZ());
		v.setLength(this.radius);
		return new Point3f(this.cx + v.getX(), this.cy + v.getY(), this.cz + v.getZ());
	}

	@Override
	public void translate(double dx, double dy, double dz) {
		this.cx += dx;
		this.cy += dy;
		this.cz += dz;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof Sphere3f) {
			Sphere3f rr2d = (Sphere3f) obj;
			return ((getX() == rr2d.getX()) &&
					(getY() == rr2d.getY()) &&
					(getZ() == rr2d.getZ()) &&
					(getRadius() == rr2d.getRadius()));
		}
		return false;
	}

	@Override
	public int hashCode() {
		long bits = 1L;
		bits = 31L * bits + doubleToLongBits(getX());
		bits = 31L * bits + doubleToLongBits(getY());
		bits = 31L * bits + doubleToLongBits(getZ());
		bits = 31L * bits + doubleToLongBits(getRadius());
		return (int) (bits ^ (bits >> 32));
	}

	@Override
	public boolean intersects(AlignedBox3f s) {
		return intersectsSolidSphereSolidAlignedBox(
				getX(), getY(), getZ(), getRadius(),
				s.getMinX(), s.getMinY(), s.getMinZ(),
				s.getSizeX(), s.getSizeY(), s.getSizeZ());
	}

	@Override
	public boolean intersects(Sphere3f s) {
		return intersectsSphereSphere(
				getX(), getY(), getZ(), getRadius(),
				s.getX(), s.getY(), s.getZ(), s.getRadius());
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("["); //$NON-NLS-1$
		b.append(getX());
		b.append(";"); //$NON-NLS-1$
		b.append(getY());
		b.append(";"); //$NON-NLS-1$
		b.append(getZ());
		b.append(";"); //$NON-NLS-1$
		b.append(getRadius());
		b.append("]"); //$NON-NLS-1$
		return b.toString();
	}

	@Override
	public boolean intersects(Segment3f s) {
		return intersectsSphereSegment(
				getX(), getY(), getZ(), getRadius(),
				s.getX1(), s.getY1(), s.getZ1(),
				s.getX2(), s.getY2(), s.getZ2());
	}

	@Override
	public boolean intersects(Triangle3f s) {
		return Triangle3f.intersectsTriangleSphere(
				s.getX1(), s.getY1(), s.getZ1(),
				s.getX2(), s.getY2(), s.getZ2(),
				s.getX3(), s.getY3(), s.getZ3(),
				getX(), getY(), getZ(), getRadius());
	}

	@Override
	public boolean intersects(Capsule3f s) {
		return intersectsSphereCapsule(
				getX(), getY(), getZ(), getRadius(),
				s.getMedialX1(), s.getMedialY1(), s.getMedialZ1(),
				s.getMedialX2(), s.getMedialY2(), s.getMedialZ2(),
				s.getRadius());
	}

	@Override
	public boolean intersects(OrientedBox3f s) {
		return intersectsSolidSphereOrientedBox(
				getX(), getY(), getZ(), getRadius(),
				s.getCenterX(), s.getCenterY(), s.getCenterZ(),
				s.getFirstAxisX(), s.getFirstAxisY(), s.getFirstAxisZ(),
				s.getSecondAxisX(), s.getSecondAxisY(), s.getSecondAxisZ(),
				s.getThirdAxisX(), s.getThirdAxisY(), s.getThirdAxisZ(),
				s.getFirstAxisExtent(), s.getSecondAxisExtent(), s.getThirdAxisExtent());
	}

	@Override
	public boolean intersects(Plane3D<?> p) {
		return p.intersects(this);
	}

	@Override
	public void transform(Transform3D transformationMatrix) {
		Point3f c = getCenter();
		transformationMatrix.transform(c);
		Vector3f v = new Vector3f(getRadius(), getRadius(), getRadius());
		transformationMatrix.transform(v);
		set(c, MathUtil.max(v.getX(), v.getY(), v.getZ()));
	}

}