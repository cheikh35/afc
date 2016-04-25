/* 
 * $Id$
 * 
 * Copyright (c) 2006-10, Multiagent Team, Laboratoire Systemes et Transports, Universite de Technologie de Belfort-Montbeliard.
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
package org.arakhne.afc.math.geometry.d2.afp;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.arakhne.afc.math.AbstractMathTestCase;
import org.arakhne.afc.math.geometry.PathElementType;
import org.arakhne.afc.math.geometry.PathWindingRule;
import org.arakhne.afc.math.geometry.coordinatesystem.CoordinateSystem2DTestRule;
import org.arakhne.afc.math.geometry.d2.Point2D;
import org.arakhne.afc.math.geometry.d2.Vector2D;
import org.junit.After;
import org.junit.Before;
import org.junit.ComparisonFailure;
import org.junit.Rule;
import org.junit.Test;

@SuppressWarnings("all")
public abstract class AbstractShape2afpTest<T extends Shape2afp<?, ?, ?, ?, ?>,
		B extends Rectangle2afp<?, ?, ?, ?, B>> extends AbstractMathTestCase {
	
	@Rule
	public CoordinateSystem2DTestRule csTestRule = new CoordinateSystem2DTestRule();
	
	/** Is the rectangular shape to test.
	 */
	protected T shape;
	
	/** Shape factory.
	 */
	protected TestShapeFactory<? extends Point2D, B> factory;
	
	/**
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.factory = createFactory();
		this.shape = createShape();
	}
	
	protected abstract TestShapeFactory<? extends Point2D, B> createFactory();
	
	/** Create the shape to test.
	 * 
	 * @return the shape to test.
	 */
	protected abstract T createShape();
	
	public final Segment2afp<?, ?, ?, ?, B> createSegment(double x1, double y1, double x2, double y2) {
		return this.factory.createSegment(x1, y1, x2, y2);
	}
	
	public final B createRectangle(double x, double y, double width, double height) {
		return this.factory.createRectangle(x, y, width, height);
	}

	public final Ellipse2afp<?, ?, ?, ?, B> createEllipse(double x, double y, double width, double height) {
		return this.factory.createEllipse(x, y, width, height);
	}

	public final Ellipse2afp<?, ?, ?, ?, B> createEllipseFromCorners(double minx, double miny, double maxx, double maxy) {
		double x, width, y, height;
		if (minx < maxx) {
			x = minx;
			width = maxx - minx;
		} else {
			x = maxx;
			width = minx - maxx;
		}
		if (miny < maxy) {
			y = miny;
			height = maxy - miny;
		} else {
			y = maxy;
			height = miny - maxy;
		}
		return this.factory.createEllipse(x, y, width, height);
	}

	public final RoundRectangle2afp<?, ?, ?, ?, B> createRoundRectangle(double x, double y,
			double width, double height, double arcWidth, double arcHeight) {
		return this.factory.createRoundRectangle(x, y, width, height, arcWidth, arcHeight);
	}

	public final OrientedRectangle2afp<?, ?, ?, ?, B> createOrientedRectangle(
			double centerX, double centerY, double axis1X, double axis1Y, double extent1, double extent2) {
		return this.factory.createOrientedRectangle(centerX, centerY, axis1X, axis1Y, extent1, extent2);
	}

	public final Parallelogram2afp<?, ?, ?, ?, B> createParallelogram(
			double cx, double cy, double ux, double uy, double extent1, double vx, double vy, double extent2) {
		return this.factory.createParallelogram(cx, cy, ux, uy, extent1, vx, vy, extent2);
	}

	public final Triangle2afp<?, ?, ?, ?, B> createTriangle(
			double x1, double y1, double x2, double y2, double x3, double y3) {
		return this.factory.createTriangle(x1, y1, x2, y2, x3, y3);
	}

	public final Circle2afp<?, ?, ?, ?, B> createCircle(double x, double y, double radius) {
		return this.factory.createCircle(x, y, radius);
	}
	
	public final MultiShape2afp<?, ?, ?, ?, ?, B> createMultiShape() {
		return this.factory.createMultiShape();
	}

	public final Point2D createPoint(double x, double y) {
		return this.factory.createPoint(x, y);
	}

	public final Vector2D createVector(double x, double y) {
		return this.factory.createVector(x, y);
	}

	public final Path2afp<?, ?, ?, ?, B> createPath() {
		return this.factory.createPath(null);
	}

	public final Path2afp<?, ?, ?, ?, B> createPath(PathWindingRule rule) {
		return this.factory.createPath(rule);
	}

	public final Path2afp<?, ?, ?, ?, B> createPolyline(double... coordinates) {
		Path2afp<?, ?, ?, ?, B>  path = createPath();
		path.moveTo(coordinates[0], coordinates[1]);
		for (int i = 2; i < coordinates.length; i += 2) {
			path.lineTo(coordinates[i], coordinates[i + 1]);
		}
		return path;
	}

	/**
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		this.shape = null;
	}
	
	/** Assert is the given path iterator has a first element with the
	 * given information.
	 * 
	 * @param pi
	 * @param type
	 * @param coords
	 */
	protected void assertElement(PathIterator2afp<?> pi, PathElementType type, double... coords) {
		if (!pi.hasNext()) {
			fail("expected path element but the iterator is empty"); //$NON-NLS-1$
		}
		PathElement2afp pe = pi.next();
		if (!type.equals(pe.getType())) {
			throw new ComparisonFailure("not same element type.", type.name(), pe.getType().name());
		}
		double[] c = new double[coords.length];
		pe.toArray(c);
		if (!isEpsilonEquals(c, coords)) {
			throw new ComparisonFailure("not same coordinates.", //$NON-NLS-1$ 
					Arrays.toString(coords),
					Arrays.toString(c));
		}
	}
	
	/**
	 * Replies if two arrays have the same values at epsilon.
	 * 
	 * @param a
	 * @param b
	 * @return <code>true</code> if the two arrays are equal, otherwise
	 * <code>false</code>.
	 */
	public boolean isEpsilonEquals(float[] a, float[] b) {
		if (a==b) return true;
		if (a==null && b!=null) return false;
		if (a!=null && b==null) return false;
		assert(a!=null && b!=null);
		if (a.length!=b.length) return false;
		for(int i=0; i<a.length; ++i) {
			if (!isEpsilonEquals(a[i], b[i])) return false;
		}
		return true;
	}
	
	/**
	 * Replies if two arrays have the same values at epsilon.
	 * 
	 * @param a
	 * @param b
	 * @return <code>true</code> if the two arrays are equal, otherwise
	 * <code>false</code>.
	 */
	protected boolean isEquals(int[] a, int[] b) {
		if (a==b) return true;
		if (a==null && b!=null) return false;
		if (a!=null && b==null) return false;
		assert(a!=null && b!=null);
		if (a.length!=b.length) return false;
		for(int i=0; i<a.length; ++i) {
			if (a[i]!=b[i]) return false;
		}
		return true;
	}

	/** Assert is the given path iterator has no element.
	 * 
	 * @param pi
	 */
	protected void assertNoElement(PathIterator2afp<?> pi) {
		if (pi.hasNext()) {
			fail("expected no path element but the iterator is not empty: " //$NON-NLS-1$
					+ pi.next());
		}
	}

	@Test
	public abstract void testClone();

	@Test
	public abstract void equalsObject();

	@Test
	public abstract void equalsObject_withPathIterator();

	@Test
	public abstract void equalsToPathIterator();

	@Test
	public abstract void equalsToShape();

	@Test
	public abstract void isEmpty();

	@Test
	public abstract void clear();

	@Test
	public abstract void containsPoint2D();
	
	@Test
	public abstract void getClosestPointTo();
	
	@Test
	public abstract void getFarthestPointTo();

	@Test
	public abstract void getDistance();

	@Test
	public abstract void getDistanceSquared();

	@Test
	public abstract void getDistanceL1();

	@Test
	public abstract void getDistanceLinf();

	@Test
	public abstract void setIT();

	@Test
	public abstract void getPathIterator();

	@Test
	public abstract void getPathIteratorTransform2D();

	@Test
	public abstract void createTransformedShape();

	@Test
	public abstract void translateVector2D(); 

	@Test
	public abstract void toBoundingBox();
	
	@Test
	public abstract void toBoundingBoxB();

	@Test
	public abstract void containsRectangle2afp();

	@Test
	public abstract void intersectsRectangle2afp();

	@Test
	public abstract void intersectsCircle2afp();

	@Test
	public abstract void intersectsTriangle2afp();

	@Test
	public abstract void intersectsEllipse2afp();

	@Test
	public abstract void intersectsSegment2afp();
	
	@Test
	public abstract void intersectsPath2afp();

	@Test
	public abstract void intersectsPathIterator2afp();

	@Test
	public abstract void intersectsOrientedRectangle2afp();

	@Test
	public abstract void intersectsParallelogram2afp();

	@Test
	public abstract void intersectsRoundRectangle2afp();

	@Test
	public abstract void translateDoubleDouble(); 

	@Test
	public abstract void containsDoubleDouble();

	@Test
	public void getGeomFactory() {
		assertNotNull(this.shape.getGeomFactory());
	}
	
}