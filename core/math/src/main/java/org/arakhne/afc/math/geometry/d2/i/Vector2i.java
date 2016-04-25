/* 
 * $Id$
 * 
 * Copyright (C) 2011 Janus Core Developers
 * Copyright (C) 2012 Stephane GALLAND.
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
package org.arakhne.afc.math.geometry.d2.i;

import org.arakhne.afc.math.geometry.d2.Point2D;
import org.arakhne.afc.math.geometry.d2.Tuple2D;
import org.arakhne.afc.math.geometry.d2.UnmodifiableVector2D;
import org.arakhne.afc.math.geometry.d2.Vector2D;
import org.eclipse.xtext.xbase.lib.Pure;

/** 2D Vector with 2 integer numbers.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 * @since 13.0
 */
public class Vector2i extends Tuple2i<Vector2D, Vector2i> implements Vector2D {

	private static final long serialVersionUID = -7228108517874845303L;

	/**
	 */
	public Vector2i() {
		//
	}

	/**
	 * @param tuple is the tuple to copy.
	 */
	public Vector2i(Tuple2D<?> tuple) {
		super(tuple);
	}

	/**
	 * @param tuple is the tuple to copy.
	 */
	public Vector2i(int[] tuple) {
		super(tuple);
	}

	/**
	 * @param tuple is the tuple to copy.
	 */
	public Vector2i(double[] tuple) {
		super(tuple);
	}

	/**
	 * @param x
	 * @param y
	 */
	public Vector2i(int x, int y) {
		super(x,y);
	}

	/**
	 * @param x
	 * @param y
	 */
	public Vector2i(float x, float y) {
		super(x,y);
	}

	/**
	 * @param x
	 * @param y
	 */
	public Vector2i(double x, double y) {
		super(x,y);
	}

	/**
	 * @param x
	 * @param y
	 */
	public Vector2i(long x, long y) {
		super(x,y);
	}

	@Pure
	@Override
	public double dot(Vector2D vector) {
		assert (vector != null) : "Vector must not be null"; //$NON-NLS-1$
		return (this.x * vector.getX() + this.y * vector.getY());
	}

	@Pure
	@Override
	public double perp(Vector2D vector) {
		assert (vector != null) : "Vector must not be null"; //$NON-NLS-1$
		return this.x * vector.getY() - vector.getX() * this.y;
	}

	@Pure
	@Override
	public double getLength() {
		return Math.sqrt(this.x * this.x + this.y * this.y);
	}

	@Pure
	@Override
	public double getLengthSquared() {
		return this.x * this.x + this.y * this.y;
	}

	@Override
	public void add(Vector2D vector1, Vector2D vector2) {
		assert (vector1 != null) : "First vector must not be null"; //$NON-NLS-1$
		assert (vector2 != null) : "Second vector must not be null"; //$NON-NLS-1$
		this.x = (int) Math.round(vector1.getX() + vector2.getX());
		this.y = (int) Math.round(vector1.getY() + vector2.getY());
	}

	@Override
	public void add(Vector2D vector) {
		assert (vector != null) : "Vector must not be null"; //$NON-NLS-1$
		this.x = (int) Math.round(this.x + vector.getX());
		this.y = (int) Math.round(this.y + vector.getY());
	}

	@Override
	public void scaleAdd(int scale, Vector2D vector1, Vector2D vector2) {
		assert (vector1 != null) : "First vector must not be null"; //$NON-NLS-1$
		assert (vector2 != null) : "Second vector must not be null"; //$NON-NLS-1$
		this.x = (int) Math.round(scale * vector1.getX() + vector2.getX());
		this.y = (int) Math.round(scale * vector1.getY() + vector2.getY());
	}

	@Override
	public void scaleAdd(double scale, Vector2D vector1, Vector2D vector2) {
		assert (vector1 != null) : "First vector must not be null"; //$NON-NLS-1$
		assert (vector2 != null) : "Second vector must not be null"; //$NON-NLS-1$
		this.x = (int) Math.round(scale * vector1.getX() + vector2.getX());
		this.y = (int) Math.round(scale * vector1.getY() + vector2.getY());
	}

	@Override
	public void scaleAdd(int scale, Vector2D vector) {
		assert (vector != null) : "Vector must not be null"; //$NON-NLS-1$
		this.x = (int) Math.round(scale * this.x + vector.getX());
		this.y = (int) Math.round(scale * this.y + vector.getY());
	}

	@Override
	public void scaleAdd(double scale, Vector2D vector) {
		assert (vector != null) : "Vector must not be null"; //$NON-NLS-1$
		this.x = (int) Math.round(scale * this.x + vector.getX());
		this.y = (int) Math.round(scale * this.y + vector.getY());
	}

	@Override
	public void sub(Vector2D vector1, Vector2D vector2) {
		assert (vector1 != null) : "First vector must not be null"; //$NON-NLS-1$
		assert (vector2 != null) : "Second vector must not be null"; //$NON-NLS-1$
		this.x = (int) Math.round(vector1.getX() - vector2.getX());
		this.y = (int) Math.round(vector1.getY() - vector2.getY());
	}

	@Override
	public void sub(Point2D point1, Point2D point2) {
		assert (point1 != null) : "First point must not be null"; //$NON-NLS-1$
		assert (point2 != null) : "Second point must not be null"; //$NON-NLS-1$
		this.x = (int) Math.round(point1.getX() - point2.getX());
		this.y = (int) Math.round(point1.getY() - point2.getY());
	}

	@Override
	public void sub(Vector2D vector) {
		assert (vector != null) : "Vector must not be null"; //$NON-NLS-1$
		this.x = (int) Math.round(this.x - vector.getX());
		this.y = (int) Math.round(this.y - vector.getY());
	}

	@Override
	public void setLength(double newLength) {
		assert (newLength >= 0) : "New length must be positive or zero";  //$NON-NLS-1$
		double l = getLength();
		if (l != 0) {
			double f = newLength / l;
			this.x = (int) Math.round(this.x * f);
			this.y = (int) Math.round(this.y * f);
		} else {
			this.x = (int) Math.round(newLength);
			this.y = 0;
		}
	}

	@Override
	public Vector2D toUnitVector() {
		double length = getLength();
		if (length == 0.) {
			return new Vector2i(0, 0);
		}
		return new Vector2i(getX() / length, getY() / length);
	}
	
	@Override
	public Vector2D toOrthogonalVector() {
		return new Vector2i(-iy(), ix());
	}

	@Pure
	@Override
	public Vector2D toUnmodifiable() {
		return new UnmodifiableVector2D() {

			private static final long serialVersionUID = 7684988962796497763L;

			@Override
			public Vector2D toUnitVector() {
				return Vector2i.this.toUnitVector();
			}
			
			@Override
			public Vector2D toOrthogonalVector() {
				return Vector2i.this.toOrthogonalVector();
			}
			
			@Override
			public Vector2D clone() {
				return Vector2i.this.toUnmodifiable();
			}

			@Override
			public double getX() {
				return Vector2i.this.getX();
			}

			@Override
			public int ix() {
				return Vector2i.this.ix();
			}

			@Override
			public double getY() {
				return Vector2i.this.getY();
			}

			@Override
			public int iy() {
				return Vector2i.this.iy();
			}

		};
	}

}