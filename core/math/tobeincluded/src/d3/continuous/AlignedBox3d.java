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
import org.eclipse.xtext.xbase.lib.Pure;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/** 3D axis-aligned box with DoubleProperty points.
 * 
 * @author $Author: sgalland$
 * @author $Author: hjaffali$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class AlignedBox3d extends AbstractBoxedShape3F<AlignedBox3d> {


	/**
	 * 
	 */
	private static final long serialVersionUID = -2389846711674422629L;

	/** Replies the point on the shape that is closest to the given point.
	 * 
	 * @param minx x coordinate of the lower point of the box.
	 * @param miny y coordinate of the lower point of the box.
	 * @param minz z coordinate of the lower point of the box.
	 * @param maxx x coordinate of the upper point of the box.
	 * @param maxy y coordinate of the upper point of the box.
	 * @param maxz z coordinate of the upper point of the box.
	 * @param x x coordinate of the point.
	 * @param y y coordinate of the point.
	 * @param z z coordinate of the point.
	 * @return the closest point on the shape; or the point itself
	 * if it is inside the shape.
	 */
	@Pure
	public static Point3d computeClosestPoint(
			double minx, double miny, double minz,
			double maxx, double maxy, double maxz,
			double x, double y, double z) {
		Point3d closest = new Point3d();
		if (x < minx) {
			closest.setX(minx);
		}
		else if (x > maxx) {
			closest.setX(maxx);
		}
		else {
			closest.setX(x);
		}
		if (y < miny) {
			closest.setY(miny);
		}
		else if (y > maxy) {
			closest.setY(maxy);
		}
		else {
			closest.setY(y);
		}
		if (z < minz) {
			closest.setZ(minz);
		}
		else if (z > maxz) {
			closest.setZ(maxz);
		}
		else {
			closest.setZ(z);
		}
		return closest;
	}

	/**
	 * Tests if the two 3D axis-aligned boxes are intersecting.
	 * <p>
	 * This function is assuming that <var>lx1</var> is lower
	 * or equal to <var>ux1</var>, <var>ly1</var> is lower
	 * or equal to <var>uy1</var>, and so on.
	 *
	 * @param lower1x coordinates of the lowest point of the first box.
	 * @param lower1y coordinates of the lowest point of the first box.
	 * @param lower1z coordinates of the lowest point of the first box.
	 * @param upper1x coordinates of the uppermost point of the first box.
	 * @param upper1y coordinates of the uppermost point of the first box.
	 * @param upper1z coordinates of the uppermost point of the first box.
	 * @param lower2x coordinates of the lowest point of the second box.
	 * @param lower2y coordinates of the lowest point of the second box.
	 * @param lower2z coordinates of the lowest point of the second box.
	 * @param upper2x coordinates of the uppermost point of the second box.
	 * @param upper2y coordinates of the uppermost point of the second box.
	 * @param upper2z coordinates of the uppermost point of the second box.
	 * @return <code>true</code> if the two 3D boxes intersect each 
	 * other; <code>false</code> otherwise.
	 */
	@Pure
	public static boolean intersectsAlignedBoxAlignedBox(
			double lower1x, double lower1y, double lower1z, double upper1x, double upper1y, double upper1z,
			double lower2x, double lower2y, double lower2z, double upper2x, double upper2y, double upper2z) {
		assert(lower1x<=upper1x);
		assert(lower1y<=upper1y);
		assert(lower1z<=upper1z);
		assert(lower2x<=upper2x);
		assert(lower2y<=upper2y);
		assert(lower2z<=upper2z);

		boolean intersects;
		if (lower1x<lower2x) intersects = upper1x>lower2x;
		else intersects = upper2x>lower1x;

		if (intersects) {
			if (lower1y<lower2y) intersects = upper1y>lower2y;
			else intersects = upper2y>lower1y;

			if (intersects) {
				if (lower1z<lower2z) intersects = upper1z>lower2z;
				else intersects = upper2z>lower1z;
			}
		}

		return intersects;
	}

	/** Replies if the given point is inside this shape.
	 * 
	 * @param minx coordinates of the lowest point of the box.
	 * @param miny coordinates of the lowest point of the box.
	 * @param minz coordinates of the lowest point of the box.
	 * @param maxx coordinates of the uppermost point of the box.
	 * @param maxy coordinates of the uppermost point of the box.
	 * @param maxz coordinates of the uppermost point of the box.
	 * @param px x coordinate of the point.
	 * @param py y coordinate of the point.
	 * @param pz z coordinate of the point.
	 * @return <code>true</code> if the given point is inside this
	 * shape, otherwise <code>false</code>.
	 */
	@Pure
	public static boolean containsAlignedBoxPoint(
			double minx, double miny, double minz, double maxx, double maxy, double maxz,
			double px, double py, double pz) {
		return (minx<=px && px<=maxx
				&&
				miny<=py && py<=maxy
				&&
				minz<=pz && pz<=maxz);
	}

	
	/** Lowest x-coordinate covered by this rectangular shape. */
	protected DoubleProperty minxProperty;
	/** Lowest y-coordinate covered by this rectangular shape. */
	protected DoubleProperty minyProperty;
	/** Lowest z-coordinate covered by this rectangular shape. */
	protected DoubleProperty minzProperty;
	/** Highest x-coordinate covered by this rectangular shape. */
	protected DoubleProperty maxxProperty;
	/** Highest y-coordinate covered by this rectangular shape. */
	protected DoubleProperty maxyProperty;
	/** Highest z-coordinate covered by this rectangular shape. */
	protected DoubleProperty maxzProperty;
	
	/**
	 */
	public AlignedBox3d() {
		this.minxProperty = new SimpleDoubleProperty(0f);
		this.minyProperty = new SimpleDoubleProperty(0f);
		this.minzProperty = new SimpleDoubleProperty(0f);
		this.maxxProperty = new SimpleDoubleProperty(0f);
		this.maxyProperty = new SimpleDoubleProperty(0f);
		this.maxzProperty = new SimpleDoubleProperty(0f);
	}
	
	/**
	 * @param min is the min corner of the box.
	 * @param max is the max corner of the box.
	 */
	public AlignedBox3d(Point3f min, Point3f max) {
		this();
		setFromCorners(
				min.getX(), min.getY(), min.getZ(),
				max.getX(), max.getY(), max.getZ());
	}
	
	/**
	 * @param min is the min corner of the box.
	 * @param max is the max corner of the box.
	 */
	public AlignedBox3d(Point3d min, Point3d max) {
		this();
		setFromCornersProperties(min, max);
	}
	
	/**
	 * @param x
	 * @param y
	 * @param z
	 * @param sizex
	 * @param sizey
	 * @param sizez
	 */
	public AlignedBox3d(double x, double y, double z, double sizex, double sizey, double sizez) {
		this();
		setFromCorners(x, y, z, x+sizex, y+sizey, z+sizez);
	}
	
	/**
	 * @param s
	 */
	public AlignedBox3d(AbstractBoxedShape3F<?> s) {
		this();
		this.minxProperty.set(s.getMinX());
		this.minyProperty.set(s.getMinY());
		this.minzProperty.set(s.getMinZ());
		this.maxxProperty.set(s.getMaxX());
		this.maxyProperty.set(s.getMaxY());
		this.maxzProperty.set(s.getMaxZ());
	}

	
	/**
	 * @param s
	 */
	public AlignedBox3d(AlignedBox3d s) {
		this();
		this.setFromCornersProperties(new Point3d(s.minxProperty,s.minyProperty,s.minzProperty),new Point3d(s.maxxProperty,s.maxyProperty,s.maxzProperty));
	}
	
	/** Change the frame of the box.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param sizex
	 * @param sizey
	 * @param sizez
	 */
	@Override
	public void set(double x, double y, double z, double sizex, double sizey, double sizez) {
		setFromCorners(x, y, z, x+sizex, y+sizey, z+sizez);
	}
	
	/** Change the frame of the box.
	 * 
	 * @param min is the min corner of the box.
	 * @param max is the max corner of the box.
	 */
	@Override
	public void set(Point3D min, Point3D max) {
		setFromCorners(
				min.getX(), min.getY(), min.getZ(), 
				max.getX(), max.getY(), max.getZ());
	}
	
	/** Change the frame of the box with Point3d points.
	 * 
	 * When the points are changed, the AlignedBox3d will also be changed
	 * 
	 * @param min is the min corner of the box.
	 * @param max is the max corner of the box.
	 */
	public void setProperties(Point3d min, Point3d max) {
		setFromCornersProperties(min, max);
	}
	
	/** Change the X-size of the box, not the min corner.
	 * 
	 * @param size
	 */
	@Override
	public void setSizeX(double size) {
		this.maxxProperty.set(this.getMinX() + Math.max(0f, size));
	}

	/** Change the Y-size of the box, not the min corner.
	 * 
	 * @param size
	 */
	@Override
	public void setSizeY(double size) {
		this.maxyProperty.set(this.getMinY() + Math.max(0f, size));
	}

	/** Change the Z-size of the box, not the min corner.
	 * 
	 * @param size
	 */
	@Override
	public void setSizeZ(double size) {
		this.maxzProperty.set(this.getMinZ() + Math.max(0f, size));
	}

	/** Change the frame of the box.
	 * 
	 * @param p1 is the coordinate of the first corner.
	 * @param p2 is the coordinate of the second corner.
	 */
	@Override
	public void setFromCorners(Point3D p1, Point3D p2) {
		setFromCorners(
				p1.getX(), p1.getY(), p1.getZ(),
				p2.getX(), p2.getY(), p2.getZ());
	}
	
	/** Change the frame of the box.
	 * 
	 * @param p1 is the coordinate of the first corner.
	 * @param p2 is the coordinate of the second corner.
	 */
	public void setFromCornersProperties(Point3d p1, Point3d p2) {
		if (p1.getX()<p2.getX()) {
			this.minxProperty = p1.xProperty;
			this.maxxProperty = p2.xProperty;
		}
		else {
			this.minxProperty = p2.xProperty;
			this.maxxProperty = p1.xProperty;
		}
		if (p1.getY()<p2.getY()) {
			this.minyProperty = p1.yProperty;
			this.maxyProperty = p2.yProperty;
		}
		else {
			this.minyProperty = p2.yProperty;
			this.maxyProperty = p1.yProperty;
		}
		if (p1.getZ()<p2.getZ()) {
			this.minzProperty = p1.zProperty;
			this.maxzProperty = p2.zProperty;
		}
		else {
			this.minzProperty = p2.zProperty;
			this.maxzProperty = p1.zProperty;
		}
	}

	/** Change the frame of the box.
	 * 
	 * @param x1 is the coordinate of the first corner.
	 * @param y1 is the coordinate of the first corner.
	 * @param z1 is the coordinate of the first corner.
	 * @param x2 is the coordinate of the second corner.
	 * @param y2 is the coordinate of the second corner.
	 * @param z2 is the coordinate of the second corner.
	 */
	@Override
	public void setFromCorners(double x1, double y1, double z1, double x2, double y2, double z2) {
		if (x1<x2) {
			this.minxProperty.set(x1);
			this.maxxProperty.set(x2);
		}
		else {
			this.minxProperty.set(x2);
			this.maxxProperty.set(x1);
		}
		if (y1<y2) {
			this.minyProperty.set(y1);
			this.maxyProperty.set(y2);
		}
		else {
			this.minyProperty.set(y2);
			this.maxyProperty.set(y1);
		}
		if (z1<z2) {
			this.minzProperty.set(z1);
			this.maxzProperty.set(z2);
		}
		else {
			this.minzProperty.set(z2);
			this.maxzProperty.set(z1);
		}
	}
	
	/**
     * Sets the framing box of this <code>Shape</code>
     * based on the specified center point coordinates and corner point
     * coordinates.  The framing box is used by the subclasses of
     * <code>BoxedShape</code> to define their geometry.
     *
     * @param centerX the X coordinate of the specified center point
     * @param centerY the Y coordinate of the specified center point
     * @param centerZ the Z coordinate of the specified center point
     * @param cornerX the X coordinate of the specified corner point
     * @param cornerY the Y coordinate of the specified corner point
     * @param cornerZ the Z coordinate of the specified corner point
     */
	@Override
	public void setFromCenter(double centerX, double centerY, double centerZ, double cornerX, double cornerY, double cornerZ) {
		double dx = centerX - cornerX;
		double dy = centerY - cornerY;
		double dz = centerZ - cornerZ;
		setFromCorners(cornerX, cornerY, cornerZ, centerX + dx, centerY + dy, centerZ + dz);
	}
	
	/** Replies the min point.
	 * 
	 * @return the min point.
	 */
	@Pure
	@Override
	public Point3d getMin() {
		return new Point3d(this.minxProperty, this.minyProperty, this.minzProperty);
	}

	/** Replies the min point.
	 * 
	 * @return the min point.
	 */
	@Pure
	@Override
	public Point3d getMax() {
		return new Point3d(this.maxxProperty, this.maxyProperty, this.maxzProperty);
	}

	/** Replies the center point.
	 * 
	 * @return the center point.
	 */
	@Pure
	@Override
	public Point3d getCenter() {
		return new Point3d(
				(this.minxProperty.doubleValue() + this.maxxProperty.doubleValue()) / 2.,
				(this.minyProperty.doubleValue() + this.getMaxY()) / 2.,
				(this.minzProperty.doubleValue() + this.maxzProperty.doubleValue()) / 2.);
	}

	/** Replies the min X.
	 * 
	 * @return the min x.
	 */
	@Pure
	@Override
	public double getMinX() {
		return this.minxProperty.doubleValue();
	}

	/** Set the min X.
	 * 
	 * @param x the min x.
	 */
	@Override
	public void setMinX(double x) {
		double o = this.maxxProperty.doubleValue();
		if (o<x) {
			this.minxProperty.set(o);
			this.maxxProperty.set(x);
		}
		else {
			this.minxProperty.set(x);
		}
	}

	/** Replies the center x.
	 * 
	 * @return the center x.
	 */
	@Pure
	@Override
	public double getCenterX() {
		return (this.minxProperty.doubleValue() + this.maxxProperty.doubleValue()) / 2f;
	}

	/** Replies the max x.
	 * 
	 * @return the max x.
	 */
	@Pure
	@Override
	public double getMaxX() {
		return this.maxxProperty.doubleValue();
	}

	/** Set the max X.
	 * 
	 * @param x the max x.
	 */
	@Override
	public void setMaxX(double x) {
		double o = this.minxProperty.doubleValue();
		if (o>x) {
			this.maxxProperty.set(o);
			this.minxProperty.set(x);
		}
		else {
			this.maxxProperty.set(x);
		}
	}

	/** Replies the min y.
	 * 
	 * @return the min y.
	 */
	@Pure
	@Override
	public double getMinY() {
		return this.minyProperty.doubleValue();
	}

	/** Set the min Y.
	 * 
	 * @param y the min y.
	 */
	@Override
	public void setMinY(double y) {
		double o = this.maxyProperty.doubleValue();
		if (o<y) {
			this.minyProperty.set(o);
			this.maxyProperty.set(y);
		}
		else {
			this.minyProperty.set(y);
		}
	}

	/** Replies the center y.
	 * 
	 * @return the center y.
	 */
	@Pure
	@Override
	public double getCenterY() {
		return (this.minyProperty.doubleValue() + this.maxyProperty.doubleValue() ) / 2f;
	}

	/** Replies the max y.
	 * 
	 * @return the max y.
	 */
	@Pure
	@Override
	public double getMaxY() {
		return this.maxyProperty.doubleValue() ;
	}
	
	/** Set the max Y.
	 * 
	 * @param y the max y.
	 */
	@Override
	public void setMaxY(double y) {
		double o = this.minyProperty.doubleValue() ;
		if (o>y) {
			this.maxyProperty.set(  o);
			this.minyProperty.set(y);
		}
		else {
			this.maxyProperty.set(y);
		}
	}

	/** Replies the min z.
	 * 
	 * @return the min z.
	 */
	@Pure
	@Override
	public double getMinZ() {
		return this.minzProperty.doubleValue() ;
	}

	/** Set the min Z.
	 * 
	 * @param z the min z.
	 */
	@Override
	public void setMinZ(double z) {
		double o = this.maxzProperty.doubleValue() ;
		if (o<z) {
			this.minzProperty.set(o);
			this.maxzProperty.set(z);
		}
		else {
			this.minzProperty.set(z);
		}
	}

	/** Replies the center z.
	 * 
	 * @return the center z.
	 */
	@Pure
	@Override
	public double getCenterZ() {
		return (this.minzProperty.doubleValue()  + this.maxzProperty.doubleValue() ) / 2f;
	}

	/** Replies the max z.
	 * 
	 * @return the max z.
	 */
	@Pure
	@Override
	public double getMaxZ() {
		return this.maxzProperty.doubleValue() ;
	}
	
	/** Set the max Z.
	 * 
	 * @param z the max z.
	 */
	@Override
	public void setMaxZ(double z) {
		double o = this.minzProperty.doubleValue() ;
		if (o>z) {
			this.maxzProperty.set(o);
			this.minzProperty.set(z);
		}
		else {
			this.maxzProperty.set(z);
		}
	}

	/** Replies the x-size.
	 * 
	 * @return the x-size.
	 */
	@Pure
	@Override
	public double getSizeX() {
		return this.maxxProperty.doubleValue()  - this.minxProperty.doubleValue() ;
	}

	/** Replies the y-size.
	 * 
	 * @return the y-size.
	 */
	@Pure
	@Override
	public double getSizeY() {
		return this.maxyProperty.doubleValue()  - this.minyProperty.doubleValue() ;
	}
	
	/** Replies the z-size.
	 * 
	 * @return the z-size.
	 */
	@Pure
	@Override
	public double getSizeZ() {
		return this.maxzProperty.doubleValue()  - this.minzProperty.doubleValue() ;
	}
	
	/** Set the x bounds of the box.
	 * 
	 * @param min the min value for the x axis.
	 * @param max the max value for the x axis.
	 */
	@Override
	public void setX(double min, double max) {
		if (min <= max) {
			this.minxProperty.set(min);
			this.maxxProperty.set(max);
		} else {
			this.minxProperty.set(max);
			this.maxxProperty.set(min);
		}
	}


	/** Set the y bounds of the box.
	 * 
	 * @param min the min value for the y axis.
	 * @param max the max value for the y axis.
	 */
	@Override
	public void setY(double min, double max) {
		if (min <= max) {
			this.minyProperty.set(min);
			this.maxyProperty.set(max);
		} else {
			this.minyProperty.set(max);
			this.maxyProperty.set(min);
		}
	}


	/** Set the z bounds of the box.
	 * 
	 * @param min the min value for the z axis.
	 * @param max the max value for the z axis.
	 */
	@Override
	public void setZ(double min, double max) {
		if (min <= max) {
			this.minzProperty.set(min);
			this.maxzProperty.set(max);
		} else {
			this.minzProperty.set(max);
			this.maxzProperty.set(min);
		}
	}
	
	
	/** {@inheritDoc}
	 */
	@Pure
	@Override
	public AlignedBox3d toBoundingBox() {
		return this;
	}

	@Pure
	@Override
	public double distanceSquared(Point3D p) {
		double d1 = 0;
		if (p.getX()<this.getMinX()) {
			d1 = this.getMinX() - p.getX();
			d1 = d1*d1;
		}
		else if (p.getX()>this.getMaxX()) {
			d1 = p.getX() - this.getMaxX();
			d1 = d1*d1;
		}
		double d2 = 0;
		if (p.getY()<this.getMinY()) {
			d2 = this.getMinY() - p.getY();
			d2 = d2*d2;
		}
		else if (p.getY()>this.getMaxY()) {
			d2 = p.getY() - this.getMaxY();
			d2 = d2*d2;
		}
		double d3 = 0;
		if (p.getZ()<this.getMinZ()) {
			d3 = this.getMinZ() - p.getZ();
			d3 = d3*d3;
		}
		else if (p.getZ()>this.getMaxZ()) {
			d3 = p.getZ() - this.getMaxZ();
			d3 = d3*d3;
		}
		return d1+d2+d3;
	}

	@Pure
	@Override
	public double distanceL1(Point3D p) {
		double d1 = 0;
		if (p.getX()<this.getMinX()) {
			d1 = this.getMinX() - p.getX();
		}
		else if (p.getX()>this.getMaxX()) {
			d1 = p.getX() - this.getMaxX();
		}
		double d2 = 0;
		if (p.getY()<this.getMinY()) {
			d2 = this.getMinY() - p.getY();
		}
		else if (p.getY()>this.getMaxY()) {
			d2 = p.getY() - this.getMaxY();
		}
		double d3 = 0;
		if (p.getZ()<this.getMinZ()) {
			d3 = this.getMinZ() - p.getZ();
		}
		else if (p.getZ()>this.getMaxZ()) {
			d3 = p.getZ() - this.getMaxZ();
		}
		return d1+d2+d3;
	}

	@Pure
	@Override
	public double distanceLinf(Point3D p) {
		double d1 = 0;
		if (p.getX()<this.getMinX()) {
			d1 = this.getMinX() - p.getX();
		}
		else if (p.getX()>this.getMaxX()) {
			d1 = p.getX() - this.getMaxX();
		}
		double d2 = 0;
		if (p.getY()<this.getMinY()) {
			d2 = this.getMinY() - p.getY();
		}
		else if (p.getY()>this.getMaxY()) {
			d2 = p.getY() - this.getMaxY();
		}
		double d3 = 0;
		if (p.getZ()<this.getMinZ()) {
			d3 = this.getMinZ() - p.getZ();
		}
		else if (p.getZ()>this.getMaxZ()) {
			d3 = p.getZ() - this.getMaxZ();
		}
		return MathUtil.max(d1, d2, d3);
	}

	@Pure
	@Override
	public boolean contains(double x, double y, double z) {
		return containsAlignedBoxPoint(
				this.getMinX(), this.getMinY(), this.getMinZ(), this.getMaxX(), this.getMaxY(), this.getMaxZ(),
				x, y, z);
	}

	@Pure
	@Override
	public boolean intersects(AbstractBoxedShape3F<?> s) {
		return intersectsAlignedBoxAlignedBox(
				getMinX(), getMinY(), getMinZ(),
				getMaxX(), getMaxY(), getMaxZ(),
				s.getMinX(), s.getMinY(), s.getMinZ(),
				s.getMaxX(), s.getMaxY(), s.getMaxZ());
	}

	@Pure
	@Override
	public boolean intersects(AbstractSphere3F s) {
		return AbstractSphere3F.intersectsSolidSphereSolidAlignedBox(
				s.getX(), s.getY(), s.getZ(), s.getRadius(),
				getMinX(), getMinY(), getMinZ(),
				getMaxX(), getMaxY(), getMaxZ());
	}

	@Pure
	@Override
	public boolean intersects(AbstractSegment3F s) {
		return AbstractSegment3F.intersectsSegmentAlignedBox(
				s.getX1(), s.getY1(), s.getZ1(),
				s.getX2(), s.getY2(), s.getZ2(),
				getMinX(), getMinY(), getMinZ(),
				getMaxX(), getMaxY(), getMaxZ());
	}

	@Pure
	@Override
	public boolean intersects(AbstractTriangle3F s) {
		return AbstractTriangle3F.intersectsTriangleAlignedBox(
				s.getX1(), s.getY1(), s.getZ1(),
				s.getX2(), s.getY2(), s.getZ2(),
				s.getX3(), s.getY3(), s.getZ3(),
				getMinX(), getMinY(), getMinZ(),
				getMaxX(), getMaxY(), getMaxZ());
	}

	@Pure
	@Override
	public boolean intersects(AbstractCapsule3F s) {
		return AbstractCapsule3F.intersectsCapsuleAlignedBox(
				s.getMedialX1(), s.getMedialY1(), s.getMedialY1(),
				s.getMedialX2(), s.getMedialY2(), s.getMedialY2(),
				s.getRadius(),
				getMinX(), getMinY(), getMinZ(),
				getMaxX(), getMaxY(), getMaxZ());
	}

	@Pure
	@Override
	public boolean intersects(AbstractOrientedBox3F s) {
		return AbstractOrientedBox3F.intersectsOrientedBoxAlignedBox(
				s.getCenterX(), s.getCenterY(), s.getCenterZ(),
				s.getFirstAxisX(), s.getFirstAxisY(), s.getFirstAxisZ(),
				s.getSecondAxisX(), s.getSecondAxisY(), s.getSecondAxisZ(),
				s.getThirdAxisX(), s.getThirdAxisY(), s.getThirdAxisZ(),
				s.getFirstAxisExtent(), s.getSecondAxisExtent(), s.getThirdAxisExtent(),
				getMinX(), getMinY(), getMinZ(),
				getMaxX(), getMaxY(), getMaxZ());
	}

	@Pure
	@Override
	public boolean intersects(Plane3D<?> p) {
		return p.intersects(this);
	}

	@Pure
	@Override
	public boolean intersects(Path3f s) {
		return s.intersects(this);
	}

	@Pure
	@Override
	public boolean intersects(Path3d s) {
		return s.intersects(this);
	}
	
	@Pure
	@Override
	public Point3D getClosestPointTo(Point3D p) {
		return computeClosestPoint(
				getMinX(), getMinY(), getMinZ(),
				getMaxX(), getMaxY(), getMaxZ(),
				p.getX(), p.getY(), p.getZ());
	}

	@Pure
	@Override
	public Point3D getFarthestPointTo(Point3D p) {
		Point3d farthest = new Point3d();
		if (p.getX()<this.getMinX()) {
			farthest.setX(this.getMaxX());
		}
		else if (p.getX()>this.getMaxX()) {
			farthest.setX(this.getMinX());
		}
		else {
			double dl = Math.abs(p.getX()-this.getMinX());
			double du = Math.abs(p.getX()-this.getMaxX());
			if (dl>du) {
				farthest.setX(this.getMinX());
			}
			else {
				farthest.setX(this.getMaxX());
			}
		}
		if (p.getY()<this.getMinY()) {
			farthest.setY(this.getMaxY());
		}
		else if (p.getY()>this.getMaxY()) {
			farthest.setY(this.getMinY());
		}
		else {
			double dl = Math.abs(p.getY()-this.getMinY());
			double du = Math.abs(p.getY()-this.getMaxY());
			if (dl>du) {
				farthest.setY(this.getMinY());
			}
			else {
				farthest.setY(this.getMaxY());
			}
		}
		if (p.getZ()<this.getMinZ()) {
			farthest.setZ(this.getMaxZ());
		}
		else if (p.getZ()>this.getMaxZ()) {
			farthest.setZ(this.getMinZ());
		}
		else {
			double dl = Math.abs(p.getZ()-this.getMinZ());
			double du = Math.abs(p.getZ()-this.getMaxZ());
			if (dl>du) {
				farthest.setZ(this.getMinZ());
			}
			else {
				farthest.setZ(this.getMaxZ());
			}
		}
		return farthest;
	}

	@Override
	public void set(Shape3F s) {
		s.toBoundingBox(this);
	}

	@Pure
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof AbstractBoxedShape3F) {
			AbstractBoxedShape3F<?> ab3f = (AbstractBoxedShape3F<?>) obj;
			return ((getMinX() == ab3f.getMinX()) &&
					(getMinY() == ab3f.getMinY()) &&
					(getMinZ() == ab3f.getMinZ()) &&
					(getMaxX() == ab3f.getMaxX()) &&
					(getMaxY() == ab3f.getMaxY()) &&
					(getMaxZ() == ab3f.getMaxZ()));
		}
		return false;
	}

	@Pure
	@Override
	public int hashCode() {
		long bits = 1L;
		bits = 31L * bits + doubleToLongBits(getMinX());
		bits = 31L * bits + doubleToLongBits(getMinY());
		bits = 31L * bits + doubleToLongBits(getMinZ());
		bits = 31L * bits + doubleToLongBits(getMaxX());
		bits = 31L * bits + doubleToLongBits(getMaxY());
		bits = 31L * bits + doubleToLongBits(getMaxZ());
		return (int) (bits ^ (bits >> 32));
	}

	@Override
	public PathIterator3f getPathIterator(Transform3D transform) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PathIterator3d getPathIteratorProperty(Transform3D transform) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
