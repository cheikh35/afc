/* 
 * $Id$
 * 
 * Copyright (C) 2011 Janus Core Developers
 * Copyright (C) 2012-13 Stephane GALLAND.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * This program is free software; you can redistribute it and/or modify
 */
package org.arakhne.afc.math.geometry.d2.afp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.arakhne.afc.math.geometry.PathElementType;
import org.arakhne.afc.math.geometry.d2.Point2D;
import org.arakhne.afc.math.geometry.d2.Transform2D;
import org.arakhne.afc.math.geometry.d2.ai.PathIterator2ai;
import org.junit.Test;

@SuppressWarnings("all")
public abstract class AbstractEllipse2afpTest<T extends Ellipse2afp<?, T, ?, ?, B>,
		B extends Rectangle2afp<?, ?, ?, ?, B>> extends AbstractShape2afpTest<T, B> {

	@Override
	protected final T createShape() {
		return (T) createEllipse(5, 8, 5, 10);
	}
	
	@Test
	@Override
	public void testClone() {
		T clone = this.shape.clone();
		assertNotNull(clone);
		assertNotSame(this.shape, clone);
		assertEquals(this.shape.getClass(), clone.getClass());
		assertEpsilonEquals(5, clone.getMinX());
		assertEpsilonEquals(8, clone.getMinY());
		assertEpsilonEquals(10, clone.getMaxX());
		assertEpsilonEquals(18, clone.getMaxY());
	}

	@Test
	@Override
	public void equalsObject() {
		assertFalse(this.shape.equals(null));
		assertFalse(this.shape.equals(new Object()));
		assertFalse(this.shape.equals(createEllipse(0, 0, 5, 10)));
		assertFalse(this.shape.equals(createEllipse(5, 8, 6, 10)));
		assertFalse(this.shape.equals(createSegment(5, 8, 6, 10)));
		assertTrue(this.shape.equals(this.shape));
		assertTrue(this.shape.equals(createEllipse(5, 8, 5, 10)));
	}

	@Test
	@Override
	public void equalsObject_withPathIterator() {
		assertFalse(this.shape.equals(createEllipse(0, 0, 5, 10).getPathIterator()));
		assertFalse(this.shape.equals(createEllipse(5, 8, 6, 10).getPathIterator()));
		assertFalse(this.shape.equals(createSegment(5, 8, 6, 10).getPathIterator()));
		assertTrue(this.shape.equals(this.shape.getPathIterator()));
		assertTrue(this.shape.equals(createEllipse(5, 8, 5, 10).getPathIterator()));
	}

	@Test
	@Override
	public void equalsToPathIterator() {
		assertFalse(this.shape.equalsToPathIterator((PathIterator2ai) null));
		assertFalse(this.shape.equalsToPathIterator(createEllipse(0, 0, 5, 10).getPathIterator()));
		assertFalse(this.shape.equalsToPathIterator(createEllipse(5, 8, 6, 10).getPathIterator()));
		assertFalse(this.shape.equalsToPathIterator(createSegment(5, 8, 6, 10).getPathIterator()));
		assertTrue(this.shape.equalsToPathIterator(this.shape.getPathIterator()));
		assertTrue(this.shape.equalsToPathIterator(createEllipse(5, 8, 5, 10).getPathIterator()));
	}

	@Test
	@Override
	public void equalsToShape() {
		assertFalse(this.shape.equalsToShape(null));
		assertFalse(this.shape.equalsToShape((T) createEllipse(0, 0, 5, 10)));
		assertFalse(this.shape.equalsToShape((T) createEllipse(5, 8, 6, 10)));
		assertTrue(this.shape.equalsToShape(this.shape));
		assertTrue(this.shape.equalsToShape((T) createEllipse(5, 8, 5, 10)));
	}

	@Override
	public void isEmpty() {
		assertFalse(this.shape.isEmpty());
		this.shape.clear();
		assertTrue(this.shape.isEmpty());
	}

	@Override
	public void clear() {
		this.shape.clear();
		assertEpsilonEquals(0, this.shape.getMinX());
		assertEpsilonEquals(0, this.shape.getMinY());
		assertEpsilonEquals(0, this.shape.getMaxX());
		assertEpsilonEquals(0, this.shape.getMaxY());
	}

	@Override
	public void containsDoubleDouble() {
		assertFalse(this.shape.contains(0,0));
		assertFalse(this.shape.contains(11,10));
		assertFalse(this.shape.contains(11,50));
		assertTrue(this.shape.contains(9,12));
		assertTrue(this.shape.contains(9,11));
		assertTrue(this.shape.contains(8,12));
		assertFalse(this.shape.contains(3,7));
		assertFalse(this.shape.contains(10,12));
		assertFalse(this.shape.contains(10,11));
		assertTrue(this.shape.contains(9,10));
		assertFalse(this.shape.contains(9.5,9.5));
	}

	@Override
	public void containsPoint2D() {
		assertFalse(this.shape.contains(createPoint(0,0)));
		assertFalse(this.shape.contains(createPoint(11,10)));
		assertFalse(this.shape.contains(createPoint(11,50)));
		assertTrue(this.shape.contains(createPoint(9,12)));
		assertTrue(this.shape.contains(createPoint(9,11)));
		assertTrue(this.shape.contains(createPoint(8,12)));
		assertFalse(this.shape.contains(createPoint(3,7)));
		assertFalse(this.shape.contains(createPoint(10,12)));
		assertFalse(this.shape.contains(createPoint(10,11)));
		assertTrue(this.shape.contains(createPoint(9,10)));
		assertFalse(this.shape.contains(createPoint(9.5,9.5)));
	}

	@Override
	public void getClosestPointTo() {
		// Values computed with GeoGebra tool
		Point2D result;
		
		// Lower / Lower
		result = this.shape.getClosestPointTo(createPoint(0, 0));
		assertEpsilonEquals(6.58303, result.getX());
		assertEpsilonEquals(8.34848, result.getY());

		// Lower / Upper
		result = this.shape.getClosestPointTo(createPoint(0, 24));
		assertEpsilonEquals(6.39777, result.getX());
		assertEpsilonEquals(17.4878, result.getY());

		// Upper / Lower
		result = this.shape.getClosestPointTo(createPoint(24, 0));
		assertEpsilonEquals(9.08189, result.getX());
		assertEpsilonEquals(9.12824, result.getY());

		// Upper / Upper
		result = this.shape.getClosestPointTo(createPoint(24, 24));
		assertEpsilonEquals(9.2587, result.getX());
		assertEpsilonEquals(16.55357, result.getY());

		// On x axis (positive)
		result = this.shape.getClosestPointTo(createPoint(18, 13));
		assertEpsilonEquals(10, result.getX());
		assertEpsilonEquals(13, result.getY());

		// On x axis (negative)
		result = this.shape.getClosestPointTo(createPoint(0, 13));
		assertEpsilonEquals(5, result.getX());
		assertEpsilonEquals(13, result.getY());

		// On y axis (positive)
		result = this.shape.getClosestPointTo(createPoint(7.5, 24));
		assertEpsilonEquals(7.5, result.getX());
		assertEpsilonEquals(18, result.getY());

		// On y axis (negative)
		result = this.shape.getClosestPointTo(createPoint(7.5, 0));
		assertEpsilonEquals(7.5, result.getX());
		assertEpsilonEquals(8, result.getY());

		// Inside
		result = this.shape.getClosestPointTo(createPoint(6, 11));
		assertEpsilonEquals(6, result.getX());
		assertEpsilonEquals(11, result.getY());
	}

	@Override
	public void getFarthestPointTo() {
		// Values computed with GeoGebra tool
		Point2D result;
		
		// Lower / Lower
		result = this.shape.getFarthestPointTo(createPoint(0, 0));
		assertEpsilonEquals(8.05329, result.getX());
		assertEpsilonEquals(17.92645, result.getY());

		// Lower / Upper
		result = this.shape.getFarthestPointTo(createPoint(0, 24));
		assertEpsilonEquals(8.12711, result.getX());
		assertEpsilonEquals(8.08913, result.getY());

		// Upper / Lower
		result = this.shape.getFarthestPointTo(createPoint(24, 0));
		assertEpsilonEquals(6.31519, result.getX());
		assertEpsilonEquals(17.75919, result.getY());

		// Upper / Upper
		result = this.shape.getFarthestPointTo(createPoint(24, 24));
		assertEpsilonEquals(6.16141, result.getX());
		assertEpsilonEquals(8.28223, result.getY());

		// On x axis (positive)
		result = this.shape.getFarthestPointTo(createPoint(18, 13));
		assertEpsilonEquals(5, result.getX());
		assertEpsilonEquals(13, result.getY());

		// On x axis (negative)
		result = this.shape.getFarthestPointTo(createPoint(0, 13));
		assertEpsilonEquals(10, result.getX());
		assertEpsilonEquals(13, result.getY());

		// On y axis (positive)
		result = this.shape.getFarthestPointTo(createPoint(7.5, 24));
		assertEpsilonEquals(7.5, result.getX());
		assertEpsilonEquals(8, result.getY());

		// On y axis (negative)
		result = this.shape.getFarthestPointTo(createPoint(7.5, 0));
		assertEpsilonEquals(7.5, result.getX());
		assertEpsilonEquals(18, result.getY());

		// Inside
		result = this.shape.getFarthestPointTo(createPoint(6, 11));
		assertEpsilonEquals(7.82555, result.getX());
		assertEpsilonEquals(17.97659, result.getY());
	}

	@Override
	public void getDistance() {
		// Values computed with GeoGebra tool

		// Lower / Lower
		assertEpsilonEquals(10.63171, this.shape.getDistance(createPoint(0, 0)));

		// Lower / Upper
		assertEpsilonEquals(9.12909, this.shape.getDistance(createPoint(0, 24)));

		// Upper / Lower
		assertEpsilonEquals(17.48928, this.shape.getDistance(createPoint(24, 0)));

		// Upper / Upper
		assertEpsilonEquals(16.5153, this.shape.getDistance(createPoint(24, 24)));

		// On x axis (positive)
		assertEpsilonEquals(8, this.shape.getDistance(createPoint(18, 13)));

		// On x axis (negative)
		assertEpsilonEquals(5, this.shape.getDistance(createPoint(0, 13)));

		// On y axis (positive)
		assertEpsilonEquals(6, this.shape.getDistance(createPoint(7.5, 24)));

		// On y axis (negative)
		assertEpsilonEquals(8, this.shape.getDistance(createPoint(7.5, 0)));

		// Inside
		assertEpsilonEquals(0, this.shape.getDistance(createPoint(6, 11)));
	}

	@Override
	public void getDistanceSquared() {
		// Values computed with GeoGebra tool

		// Lower / Lower
		assertEpsilonEquals(113.03335, this.shape.getDistanceSquared(createPoint(0, 0)));

		// Lower / Upper
		assertEpsilonEquals(83.34011, this.shape.getDistanceSquared(createPoint(0, 24)));

		// Upper / Lower
		assertEpsilonEquals(305.87484, this.shape.getDistanceSquared(createPoint(24, 0)));

		// Upper / Upper
		assertEpsilonEquals(272.75517, this.shape.getDistanceSquared(createPoint(24, 24)));

		// On x axis (positive)
		assertEpsilonEquals(64, this.shape.getDistanceSquared(createPoint(18, 13)));

		// On x axis (negative)
		assertEpsilonEquals(25, this.shape.getDistanceSquared(createPoint(0, 13)));

		// On y axis (positive)
		assertEpsilonEquals(36, this.shape.getDistanceSquared(createPoint(7.5, 24)));

		// On y axis (negative)
		assertEpsilonEquals(64, this.shape.getDistanceSquared(createPoint(7.5, 0)));

		// Inside
		assertEpsilonEquals(0, this.shape.getDistanceSquared(createPoint(6, 11)));
		
	}

	@Override
	public void getDistanceL1() {
		// Values computed with GeoGebra tool

		// Lower / Lower
		assertEpsilonEquals(14.93151, this.shape.getDistanceL1(createPoint(0, 0)));

		// Lower / Upper
		assertEpsilonEquals(12.90997, this.shape.getDistanceL1(createPoint(0, 24)));

		// Upper / Lower
		assertEpsilonEquals(24.04635, this.shape.getDistanceL1(createPoint(24, 0)));

		// Upper / Upper
		assertEpsilonEquals(22.18773, this.shape.getDistanceL1(createPoint(24, 24)));

		// On x axis (positive)
		assertEpsilonEquals(8, this.shape.getDistanceL1(createPoint(18, 13)));

		// On x axis (negative)
		assertEpsilonEquals(5, this.shape.getDistanceL1(createPoint(0, 13)));

		// On y axis (positive)
		assertEpsilonEquals(6, this.shape.getDistanceL1(createPoint(7.5, 24)));

		// On y axis (negative)
		assertEpsilonEquals(8, this.shape.getDistanceL1(createPoint(7.5, 0)));

		// Inside
		assertEpsilonEquals(0, this.shape.getDistanceL1(createPoint(6, 11)));
	}

	@Override
	public void getDistanceLinf() {
		// Values computed with GeoGebra tool

		// Lower / Lower
		assertEpsilonEquals(8.34848, this.shape.getDistanceLinf(createPoint(0, 0)));

		// Lower / Upper
		assertEpsilonEquals(6.5122, this.shape.getDistanceLinf(createPoint(0, 24)));

		// Upper / Lower
		assertEpsilonEquals(14.91811, this.shape.getDistanceLinf(createPoint(24, 0)));

		// Upper / Upper
		assertEpsilonEquals(14.7413, this.shape.getDistanceLinf(createPoint(24, 24)));

		// On x axis (positive)
		assertEpsilonEquals(8, this.shape.getDistanceLinf(createPoint(18, 13)));

		// On x axis (negative)
		assertEpsilonEquals(5, this.shape.getDistanceLinf(createPoint(0, 13)));

		// On y axis (positive)
		assertEpsilonEquals(6, this.shape.getDistanceLinf(createPoint(7.5, 24)));

		// On y axis (negative)
		assertEpsilonEquals(8, this.shape.getDistanceLinf(createPoint(7.5, 0)));

		// Inside
		assertEpsilonEquals(0, this.shape.getDistanceLinf(createPoint(6, 11)));
	}

	@Override
	public void setIT() {
		this.shape.set((T) createEllipse(17, 20, 7, 45));
		assertEpsilonEquals(17, this.shape.getMinX());
		assertEpsilonEquals(20, this.shape.getMinY());
		assertEpsilonEquals(24, this.shape.getMaxX());
		assertEpsilonEquals(65, this.shape.getMaxY());
	}

	@Override
	public void getPathIterator() {
		PathIterator2afp pi = this.shape.getPathIterator();
		assertElement(pi, PathElementType.MOVE_TO, 10, 13);
		assertElement(pi, PathElementType.CURVE_TO, 10, 15.76142374915397, 8.880711874576983, 18, 7.5, 18);
		assertElement(pi, PathElementType.CURVE_TO, 6.119288125423017, 18, 5, 15.76142374915397, 5, 13);
		assertElement(pi, PathElementType.CURVE_TO, 5, 10.23857625084603, 6.119288125423017, 8, 7.5, 8);
		assertElement(pi, PathElementType.CURVE_TO, 8.880711874576983, 8, 10, 10.23857625084603, 10, 13);
		assertElement(pi, PathElementType.CLOSE, 10, 13);
		assertNoElement(pi);
	}

	@Override
	public void getPathIteratorTransform2D() {
		PathIterator2afp pi = this.shape.getPathIterator(null);
		assertElement(pi, PathElementType.MOVE_TO, 10, 13);
		assertElement(pi, PathElementType.CURVE_TO, 10, 15.76142374915397, 8.880711874576983, 18, 7.5, 18);
		assertElement(pi, PathElementType.CURVE_TO, 6.119288125423017, 18, 5, 15.76142374915397, 5, 13);
		assertElement(pi, PathElementType.CURVE_TO, 5, 10.23857625084603, 6.119288125423017, 8, 7.5, 8);
		assertElement(pi, PathElementType.CURVE_TO, 8.880711874576983, 8, 10, 10.23857625084603, 10, 13);
		assertElement(pi, PathElementType.CLOSE, 10, 13);
		assertNoElement(pi);

		Transform2D tr;
		
		tr = new Transform2D();
		pi = this.shape.getPathIterator(tr);
		assertElement(pi, PathElementType.MOVE_TO, 10, 13);
		assertElement(pi, PathElementType.CURVE_TO, 10, 15.76142374915397, 8.880711874576983, 18, 7.5, 18);
		assertElement(pi, PathElementType.CURVE_TO, 6.119288125423017, 18, 5, 15.76142374915397, 5, 13);
		assertElement(pi, PathElementType.CURVE_TO, 5, 10.23857625084603, 6.119288125423017, 8, 7.5, 8);
		assertElement(pi, PathElementType.CURVE_TO, 8.880711874576983, 8, 10, 10.23857625084603, 10, 13);
		assertElement(pi, PathElementType.CLOSE, 10, 13);
		assertNoElement(pi);

		tr = new Transform2D();
		tr.makeTranslationMatrix(10, -10);
		pi = this.shape.getPathIterator(tr);
		assertElement(pi, PathElementType.MOVE_TO, 20, 3);
		assertElement(pi, PathElementType.CURVE_TO, 20, 5.76142374915397, 18.880711874576983, 8, 17.5, 8);
		assertElement(pi, PathElementType.CURVE_TO, 16.119288125423017, 8, 15, 5.76142374915397, 15, 3);
		assertElement(pi, PathElementType.CURVE_TO, 15, 0.23857625084603, 16.119288125423017, -2, 17.5, -2);
		assertElement(pi, PathElementType.CURVE_TO, 18.880711874576983, -2, 20, 0.23857625084603, 20, 3);
		assertElement(pi, PathElementType.CLOSE, 20, 3);
		assertNoElement(pi);
	}

	@Override
	public void createTransformedShape() {
		Transform2D tr;
		Shape2afp newShape;
		
		newShape = this.shape.createTransformedShape(null);
		assertNotNull(newShape);
		assertNotSame(this.shape, newShape);
		assertEquals(this.shape, newShape);

		tr = new Transform2D();
		newShape = this.shape.createTransformedShape(tr);
		assertNotNull(newShape);
		assertNotSame(this.shape, newShape);
		assertEquals(this.shape, newShape);

		tr = new Transform2D();
		tr.makeTranslationMatrix(10, -10);
		newShape = this.shape.createTransformedShape(tr);
		assertNotNull(newShape);
		assertNotSame(this.shape, newShape);
		assertTrue(newShape instanceof Path2afp);
		PathIterator2afp pi = this.shape.getPathIterator(tr);
		assertElement(pi, PathElementType.MOVE_TO, 20, 3);
		assertElement(pi, PathElementType.CURVE_TO, 20, 5.76142374915397, 18.880711874576983, 8, 17.5, 8);
		assertElement(pi, PathElementType.CURVE_TO, 16.119288125423017, 8, 15, 5.76142374915397, 15, 3);
		assertElement(pi, PathElementType.CURVE_TO, 15, 0.23857625084603, 16.119288125423017, -2, 17.5, -2);
		assertElement(pi, PathElementType.CURVE_TO, 18.880711874576983, -2, 20, 0.23857625084603, 20, 3);
		assertElement(pi, PathElementType.CLOSE, 20, 3);
		assertNoElement(pi);
	}

	@Override
	public void translateDoubleDouble() {
		this.shape.translate(123.456, -789.123);
		assertEpsilonEquals(128.456, this.shape.getMinX());
		assertEpsilonEquals(-781.123, this.shape.getMinY());
		assertEpsilonEquals(133.456, this.shape.getMaxX());
		assertEpsilonEquals(-771.123, this.shape.getMaxY());
	}

	@Override
	public void translateVector2D() {
		this.shape.translate(createVector(123.456, -789.123));
		assertEpsilonEquals(128.456, this.shape.getMinX());
		assertEpsilonEquals(-781.123, this.shape.getMinY());
		assertEpsilonEquals(133.456, this.shape.getMaxX());
		assertEpsilonEquals(-771.123, this.shape.getMaxY());
	}

	@Override
	public void toBoundingBox() {
		B box = this.shape.toBoundingBox();
		assertEpsilonEquals(5, box.getMinX());
		assertEpsilonEquals(8, box.getMinY());
		assertEpsilonEquals(10, box.getMaxX());
		assertEpsilonEquals(18, box.getMaxY());
	}

	@Override
	public void toBoundingBoxB() {
		B box = createRectangle(0, 0, 0, 0);
		this.shape.toBoundingBox(box);
		assertEpsilonEquals(5, box.getMinX());
		assertEpsilonEquals(8, box.getMinY());
		assertEpsilonEquals(10, box.getMaxX());
		assertEpsilonEquals(18, box.getMaxY());
	}

	@Override
	public void containsRectangle2afp() {
		assertFalse(this.shape.contains(createRectangle(0, 0, 1, 1)));
		assertFalse(this.shape.contains(createRectangle(4, 6, 100, 100)));
		assertFalse(this.shape.contains(createRectangle(4, 6, 4, 6)));
		assertFalse(this.shape.contains(createRectangle(5, 8, 2.5, 5)));
		assertTrue(this.shape.contains(createRectangle(6, 10, 2, 6)));
	}

	@Override
	public void intersectsRectangle2afp() {
		assertFalse(this.shape.intersects(createRectangle(0, 0, 1, 1)));
		assertTrue(this.shape.intersects(createRectangle(4, 6, 100, 100)));
		assertTrue(this.shape.intersects(createRectangle(4, 6, 4, 6)));
		assertTrue(this.shape.intersects(createRectangle(5, 8, 2.5, 5)));
		assertTrue(this.shape.intersects(createRectangle(6, 10, 2, 6)));
		assertFalse(this.shape.intersects(createRectangle(5, 8, .1, .1)));
	}

	@Override
	public void intersectsCircle2afp() {
		assertFalse(this.shape.intersects(createCircle(0, 0, 1)));
		assertTrue(this.shape.intersects(createCircle(7.5, 7, 2)));
		assertFalse(this.shape.intersects(createCircle(3, 8, 2)));
		assertFalse(this.shape.intersects(createCircle(4, 8, 2)));
	}

	@Override
	public void intersectsEllipse2afp() {
		assertFalse(this.shape.intersects(createEllipse(0, 0, 1, 2)));
		assertFalse(this.shape.intersects(createEllipse(100, 0, 1, 2)));
		assertFalse(this.shape.intersects(createEllipse(100, 100, 1, 2)));
		assertFalse(this.shape.intersects(createEllipse(0, 100, 1, 2)));
		assertFalse(this.shape.intersects(createEllipse(0, 8, 5, 10)));
		assertTrue(this.shape.intersects(createEllipse(0.1, 8, 5, 10)));
		assertTrue(this.shape.intersects(createEllipse(8, 10, .5, .2)));
		assertFalse(this.shape.intersects(createEllipse(2, 6, 4, 2)));
		assertTrue(this.shape.intersects(createEllipse(2.5, 3, 5, 10)));
		assertFalse(this.shape.intersects(createEllipse(1, -1, 5, 10)));
	}

	@Override
	public void intersectsSegment2afp() {
		assertFalse(this.shape.intersects(createSegment(5, -4, -1, -5)));
		assertFalse(this.shape.intersects(createSegment(5, -4, 7, 6)));
		assertFalse(this.shape.intersects(createSegment(5, -4, 14, 13)));
		assertFalse(this.shape.intersects(createSegment(5, -4, 11, 13)));
		assertTrue(this.shape.intersects(createSegment(5, -4, 11, 18)));
		assertFalse(this.shape.intersects(createSegment(0, 8, 50, 8)));
		assertFalse(this.shape.intersects(createSegment(5, 0, 5, 50)));
		assertTrue(this.shape.intersects(createSegment(5, -4, 6, 12)));
	}

	@Override
	public void intersectsTriangle2afp() {
		Triangle2afp triangle = createTriangle(5, 8, -10, 1, -1, -2);
		assertFalse(createEllipse(5, 8, 2, 1).intersects(triangle));
		assertTrue(createEllipse(-10, 1, 2, 1).intersects(triangle));
		assertTrue(createEllipse(-1, -2, 2, 1).intersects(triangle));
		
		assertFalse(createEllipse(1, 0, 2, 1).intersects(triangle));
		assertFalse(createEllipse(0.9, 0, 2, 1).intersects(triangle));
		assertFalse(createEllipse(0.8, 0, 2, 1).intersects(triangle));
		assertFalse(createEllipse(0.7, 0, 2, 1).intersects(triangle));
		assertFalse(createEllipse(0.6, 0, 2, 1).intersects(triangle));
		assertTrue(createEllipse(0.5, 0, 2, 1).intersects(triangle));

		assertFalse(createEllipse(-1.12464, -2.86312, 2, 1).intersects(triangle));
	}

	@Override
	public void intersectsPath2afp() {
		Path2afp p;

		p = createPath();
		p.moveTo(-20, -20);
		p.lineTo(-20, 20);
		p.lineTo(20, 20);
		p.lineTo(20, -20);
		assertFalse(this.shape.intersects(p));
		p.closePath();
		assertTrue(this.shape.intersects(p));
		
		p = createPath();
		p.moveTo(-20, -20);
		p.lineTo(5, 8);
		p.lineTo(-20, 20);
		assertFalse(this.shape.intersects(p));
		p.closePath();
		assertFalse(this.shape.intersects(p));

		p = createPath();
		p.moveTo(-20, -20);
		p.lineTo(20, 20);
		p.lineTo(-20, 20);
		assertTrue(this.shape.intersects(p));
		p.closePath();
		assertTrue(this.shape.intersects(p));

		p = createPath();
		p.moveTo(-20, -20);
		p.lineTo(-20, 20);
		p.lineTo(20, -20);
		assertFalse(this.shape.intersects(p));
		p.closePath();
		assertFalse(this.shape.intersects(p));

		p = createPath();
		p.moveTo(-20, 20);
		p.lineTo(10, 8);
		p.lineTo(20, 8);
		assertTrue(this.shape.intersects(p));
		p.closePath();
		assertTrue(this.shape.intersects(p));

		p = createPath();
		p.moveTo(-20, 20);
		p.lineTo(20, 18);
		p.lineTo(10, 8);
		assertFalse(this.shape.intersects(p));
		p.closePath();
		assertTrue(this.shape.intersects(p));
	}

	@Override
	public void intersectsPathIterator2afp() {
		Path2afp<?, ?, ?, ?, B> p;

		p = createPath();
		p.moveTo(-20, -20);
		p.lineTo(-20, 20);
		p.lineTo(20, 20);
		p.lineTo(20, -20);
		assertFalse(this.shape.intersects(p.getPathIterator()));
		p.closePath();
		assertTrue(this.shape.intersects(p.getPathIterator()));
		
		p = createPath();
		p.moveTo(-20, -20);
		p.lineTo(5, 8);
		p.lineTo(-20, 20);
		assertFalse(this.shape.intersects(p.getPathIterator()));
		p.closePath();
		assertFalse(this.shape.intersects(p.getPathIterator()));

		p = createPath();
		p.moveTo(-20, -20);
		p.lineTo(20, 20);
		p.lineTo(-20, 20);
		assertTrue(this.shape.intersects(p.getPathIterator()));
		p.closePath();
		assertTrue(this.shape.intersects(p.getPathIterator()));

		p = createPath();
		p.moveTo(-20, -20);
		p.lineTo(-20, 20);
		p.lineTo(20, -20);
		assertFalse(this.shape.intersects(p.getPathIterator()));
		p.closePath();
		assertFalse(this.shape.intersects(p.getPathIterator()));

		p = createPath();
		p.moveTo(-20, 20);
		p.lineTo(10, 8);
		p.lineTo(20, 8);
		assertTrue(this.shape.intersects(p.getPathIterator()));
		p.closePath();
		assertTrue(this.shape.intersects(p.getPathIterator()));

		p = createPath();
		p.moveTo(-20, 20);
		p.lineTo(20, 18);
		p.lineTo(10, 8);
		assertFalse(this.shape.intersects(p.getPathIterator()));
		p.closePath();
		assertTrue(this.shape.intersects(p.getPathIterator()));
	}

	@Override
	public void intersectsOrientedRectangle2afp() {
		OrientedRectangle2afp rectangle = createOrientedRectangle(
				6, 9,
				0.894427190999916, -0.447213595499958, 13.999990000000002,
				12.999989999999997);
		assertFalse(createEllipse(0, -5, 2, 1).intersects(rectangle));
		assertFalse(createEllipse(0, -4.5, 2, 1).intersects(rectangle));
		assertTrue(createEllipse(0, -4, 2, 1).intersects(rectangle));
		assertTrue(createEllipse(4, 4, 2, 1).intersects(rectangle));
		assertFalse(createEllipse(20, -2, 2, 1).intersects(rectangle));
		assertTrue(createEllipse(-15, -10, 50, 50).intersects(rectangle));
	}

	@Test
	@Override
	public void intersectsParallelogram2afp() {
		Parallelogram2afp para = createParallelogram(
				6, 9,
				2.425356250363330e-01, 9.701425001453320e-01, 9.219544457292887,
				-7.071067811865475e-01, 7.071067811865475e-01, 1.264911064067352e+01);
		assertFalse(createEllipse(0, 0, 2, 1).intersects(para));
		assertFalse(createEllipse(0, 1, 2, 1).intersects(para));
		assertTrue(createEllipse(0, 2, 2, 1).intersects(para));
		assertTrue(createEllipse(0, 3, 2, 1).intersects(para));
		assertTrue(createEllipse(0, 4, 2, 1).intersects(para));
		assertTrue(createEllipse(1, 3, 2, 1).intersects(para));
		assertTrue(createEllipse(5, 5, 2, 1).intersects(para));
		assertFalse(createEllipse(0.1, 1, 2, 1).intersects(para));
		assertFalse(createEllipse(0.2, 1, 2, 1).intersects(para));
		assertTrue(createEllipse(0.3, 1, 2, 1).intersects(para));
		assertTrue(createEllipse(0.4, 1, 2, 1).intersects(para));
		assertFalse(createEllipse(-7, 7.5, 2, 1).intersects(para));
	}

	@Override
	public void intersectsRoundRectangle2afp() {
		assertFalse(this.shape.intersects(createRoundRectangle(0, 0, 2, 2, .1, .2)));
		assertFalse(this.shape.intersects(createRoundRectangle(0, 20, 2, 2, .1, .2)));
		assertFalse(this.shape.intersects(createRoundRectangle(20, 20, 2, 2, .1, .2)));
		assertFalse(this.shape.intersects(createRoundRectangle(20, 0, 2, 2, .1, .2)));

		assertFalse(this.shape.intersects(createRoundRectangle(0, 12, 2, 2, .1, .2)));
		assertFalse(this.shape.intersects(createRoundRectangle(20, 12, 2, 2, .1, .2)));
		assertFalse(this.shape.intersects(createRoundRectangle(6, 0, 2, 2, .1, .2)));
		assertFalse(this.shape.intersects(createRoundRectangle(6, 20, 2, 2, .1, .2)));

		assertTrue(this.shape.intersects(createRoundRectangle(6, 10, 2, 2, .1, .2)));
		assertTrue(this.shape.intersects(createRoundRectangle(4, 12, 2, 2, .1, .2)));
		assertTrue(this.shape.intersects(createRoundRectangle(9, 12, 2, 2, .1, .2)));
		assertTrue(this.shape.intersects(createRoundRectangle(6, 7, 2, 2, .1, .2)));
		assertTrue(this.shape.intersects(createRoundRectangle(6, 17, 2, 2, .1, .2)));

		assertTrue(this.shape.intersects(createRoundRectangle(4, 7, 2, 2, .1, .2)));
		assertTrue(this.shape.intersects(createRoundRectangle(4, 17, 2, 2, .1, .2)));
		assertTrue(this.shape.intersects(createRoundRectangle(9, 7, 2, 2, .1, .2)));
		assertTrue(this.shape.intersects(createRoundRectangle(9, 17, 2, 2, .1, .2)));

		assertFalse(this.shape.intersects(createRoundRectangle(3, 6, 2, 2, .1, .2)));
		assertFalse(this.shape.intersects(createRoundRectangle(3.1, 6.1, 2, 2, .1, .2)));
		assertFalse(this.shape.intersects(createRoundRectangle(3.2, 6.2, 2, 2, .1, .2)));
		assertTrue(this.shape.intersects(createRoundRectangle(3.2, 6.3, 2, 2, .1, .2)));
	}

	@Test
	public void staticComputeClosestPointToShallowEllipse_horizontalAxisGreater() {
		// Values computed with GeoGebra tool
		Point2D result;
		
		// Lower / Lower
		result = createPoint(0, 0);
		Ellipse2afp.computeClosestPointToShallowEllipse(0, 0, 5, 8, 10, 5, result);
		assertEpsilonEquals(6, result.getX());
		assertEpsilonEquals(9, result.getY());

		// Lower / Upper
		result = createPoint(0, 0);
		Ellipse2afp.computeClosestPointToShallowEllipse(0, 24, 5, 8, 10, 5, result);
		assertEpsilonEquals(6.33945, result.getX());
		assertEpsilonEquals(12.20297, result.getY());

		// Upper / Lower
		result = createPoint(0, 0);
		Ellipse2afp.computeClosestPointToShallowEllipse(24, 0, 5, 8, 10, 5, result);
		assertEpsilonEquals(14.48365, result.getX());
		assertEpsilonEquals(9.39355, result.getY());

		// Upper / Upper
		result = createPoint(0, 0);
		Ellipse2afp.computeClosestPointToShallowEllipse(24, 24, 5, 8, 10, 5, result);
		assertEpsilonEquals(14.24203, result.getX());
		assertEpsilonEquals(11.82337, result.getY());

		// On x axis (positive)
		result = createPoint(0, 0);
		Ellipse2afp.computeClosestPointToShallowEllipse(18, 10.5, 5, 8, 10, 5, result);
		assertEpsilonEquals(15, result.getX());
		assertEpsilonEquals(10.5, result.getY());

		// On x axis (negative)
		result = createPoint(0, 0);
		Ellipse2afp.computeClosestPointToShallowEllipse(0, 10.5, 5, 8, 10, 5, result);
		assertEpsilonEquals(5, result.getX());
		assertEpsilonEquals(10.5, result.getY());

		// On y axis (positive)
		result = createPoint(0, 0);
		Ellipse2afp.computeClosestPointToShallowEllipse(10, 24, 5, 8, 10, 5, result);
		assertEpsilonEquals(10, result.getX());
		assertEpsilonEquals(13, result.getY());

		// On y axis (negative)
		result = createPoint(0, 0);
		Ellipse2afp.computeClosestPointToShallowEllipse(10, 0, 5, 8, 10, 5, result);
		assertEpsilonEquals(10, result.getX());
		assertEpsilonEquals(8, result.getY());

		// Inside
		result = createPoint(0, 0);
		Ellipse2afp.computeClosestPointToShallowEllipse(6, 11, 5, 8, 10, 5, result);
		assertEpsilonEquals(5.42383, result.getX());
		assertEpsilonEquals(11.50731, result.getY());
	}
	
	@Test
	public void staticComputeClosestPointToShallowEllipse_verticalAxisGreater() {
		// Values computed with GeoGebra tool
		Point2D result;
		
		// Lower / Lower
		result = createPoint(0, 0);
		Ellipse2afp.computeClosestPointToShallowEllipse(0, 0, 5, 8, 5, 10, result);
		assertEpsilonEquals(6.58303, result.getX());
		assertEpsilonEquals(8.34848, result.getY());

		// Lower / Upper
		result = createPoint(0, 0);
		Ellipse2afp.computeClosestPointToShallowEllipse(0, 24, 5, 8, 5, 10, result);
		assertEpsilonEquals(6.39777, result.getX());
		assertEpsilonEquals(17.4878, result.getY());

		// Upper / Lower
		result = createPoint(0, 0);
		Ellipse2afp.computeClosestPointToShallowEllipse(24, 0, 5, 8, 5, 10, result);
		assertEpsilonEquals(9.08189, result.getX());
		assertEpsilonEquals(9.12824, result.getY());

		// Upper / Upper
		result = createPoint(0, 0);
		Ellipse2afp.computeClosestPointToShallowEllipse(24, 24, 5, 8, 5, 10, result);
		assertEpsilonEquals(9.2587, result.getX());
		assertEpsilonEquals(16.55357, result.getY());

		// On x axis (positive)
		result = createPoint(0, 0);
		Ellipse2afp.computeClosestPointToShallowEllipse(18, 13, 5, 8, 5, 10, result);
		assertEpsilonEquals(10, result.getX());
		assertEpsilonEquals(13, result.getY());

		// On x axis (negative)
		result = createPoint(0, 0);
		Ellipse2afp.computeClosestPointToShallowEllipse(0, 13, 5, 8, 5, 10, result);
		assertEpsilonEquals(5, result.getX());
		assertEpsilonEquals(13, result.getY());

		// On y axis (positive)
		result = createPoint(0, 0);
		Ellipse2afp.computeClosestPointToShallowEllipse(7.5, 24, 5, 8, 5, 10, result);
		assertEpsilonEquals(7.5, result.getX());
		assertEpsilonEquals(18, result.getY());

		// On y axis (negative)
		result = createPoint(0, 0);
		Ellipse2afp.computeClosestPointToShallowEllipse(7.5, 0, 5, 8, 5, 10, result);
		assertEpsilonEquals(7.5, result.getX());
		assertEpsilonEquals(8, result.getY());

		// Inside
		result = createPoint(0, 0);
		Ellipse2afp.computeClosestPointToShallowEllipse(6, 11, 5, 8, 5, 10, result);
		assertEpsilonEquals(5.25055, result.getX());
		assertEpsilonEquals(10.81828, result.getY());
	}

	@Test
	public void staticComputeClosestPointToSolidEllipse_horizontalAxisGreater() {
		// Values computed with GeoGebra tool
		Point2D result;
		
		// Lower / Lower
		result = createPoint(0, 0);
		Ellipse2afp.computeClosestPointToSolidEllipse(0, 0, 5, 8, 10, 5, result);
		assertEpsilonEquals(6, result.getX());
		assertEpsilonEquals(9, result.getY());

		// Lower / Upper
		result = createPoint(0, 0);
		Ellipse2afp.computeClosestPointToSolidEllipse(0, 24, 5, 8, 10, 5, result);
		assertEpsilonEquals(6.33945, result.getX());
		assertEpsilonEquals(12.20297, result.getY());

		// Upper / Lower
		result = createPoint(0, 0);
		Ellipse2afp.computeClosestPointToSolidEllipse(24, 0, 5, 8, 10, 5, result);
		assertEpsilonEquals(14.48365, result.getX());
		assertEpsilonEquals(9.39355, result.getY());

		// Upper / Upper
		result = createPoint(0, 0);
		Ellipse2afp.computeClosestPointToSolidEllipse(24, 24, 5, 8, 10, 5, result);
		assertEpsilonEquals(14.24203, result.getX());
		assertEpsilonEquals(11.82337, result.getY());

		// On x axis (positive)
		result = createPoint(0, 0);
		Ellipse2afp.computeClosestPointToSolidEllipse(18, 10.5, 5, 8, 10, 5, result);
		assertEpsilonEquals(15, result.getX());
		assertEpsilonEquals(10.5, result.getY());

		// On x axis (negative)
		result = createPoint(0, 0);
		Ellipse2afp.computeClosestPointToSolidEllipse(0, 10.5, 5, 8, 10, 5, result);
		assertEpsilonEquals(5, result.getX());
		assertEpsilonEquals(10.5, result.getY());

		// On y axis (positive)
		result = createPoint(0, 0);
		Ellipse2afp.computeClosestPointToSolidEllipse(10, 24, 5, 8, 10, 5, result);
		assertEpsilonEquals(10, result.getX());
		assertEpsilonEquals(13, result.getY());

		// On y axis (negative)
		result = createPoint(0, 0);
		Ellipse2afp.computeClosestPointToSolidEllipse(10, 0, 5, 8, 10, 5, result);
		assertEpsilonEquals(10, result.getX());
		assertEpsilonEquals(8, result.getY());

		// Inside
		result = createPoint(0, 0);
		Ellipse2afp.computeClosestPointToSolidEllipse(6, 11, 5, 8, 10, 5, result);
		assertEpsilonEquals(6, result.getX());
		assertEpsilonEquals(11, result.getY());
	}
	
	@Test
	public void staticComputeClosestPointToSolidEllipse_verticalAxisGreater() {
		// Values computed with GeoGebra tool
		Point2D result;
		
		// Lower / Lower
		result = createPoint(0, 0);
		Ellipse2afp.computeClosestPointToSolidEllipse(0, 0, 5, 8, 5, 10, result);
		assertEpsilonEquals(6.58303, result.getX());
		assertEpsilonEquals(8.34848, result.getY());

		// Lower / Upper
		result = createPoint(0, 0);
		Ellipse2afp.computeClosestPointToSolidEllipse(0, 24, 5, 8, 5, 10, result);
		assertEpsilonEquals(6.39777, result.getX());
		assertEpsilonEquals(17.4878, result.getY());

		// Upper / Lower
		result = createPoint(0, 0);
		Ellipse2afp.computeClosestPointToSolidEllipse(24, 0, 5, 8, 5, 10, result);
		assertEpsilonEquals(9.08189, result.getX());
		assertEpsilonEquals(9.12824, result.getY());

		// Upper / Upper
		result = createPoint(0, 0);
		Ellipse2afp.computeClosestPointToSolidEllipse(24, 24, 5, 8, 5, 10, result);
		assertEpsilonEquals(9.2587, result.getX());
		assertEpsilonEquals(16.55357, result.getY());

		// On x axis (positive)
		result = createPoint(0, 0);
		Ellipse2afp.computeClosestPointToSolidEllipse(18, 13, 5, 8, 5, 10, result);
		assertEpsilonEquals(10, result.getX());
		assertEpsilonEquals(13, result.getY());

		// On x axis (negative)
		result = createPoint(0, 0);
		Ellipse2afp.computeClosestPointToSolidEllipse(0, 13, 5, 8, 5, 10, result);
		assertEpsilonEquals(5, result.getX());
		assertEpsilonEquals(13, result.getY());

		// On y axis (positive)
		result = createPoint(0, 0);
		Ellipse2afp.computeClosestPointToSolidEllipse(7.5, 24, 5, 8, 5, 10, result);
		assertEpsilonEquals(7.5, result.getX());
		assertEpsilonEquals(18, result.getY());

		// On y axis (negative)
		result = createPoint(0, 0);
		Ellipse2afp.computeClosestPointToSolidEllipse(7.5, 0, 5, 8, 5, 10, result);
		assertEpsilonEquals(7.5, result.getX());
		assertEpsilonEquals(8, result.getY());

		result = createPoint(0, 0);
		Ellipse2afp.computeClosestPointToSolidEllipse(7.5, 7, 5, 8, 5, 10, result);
		assertEpsilonEquals(7.5, result.getX());
		assertEpsilonEquals(8, result.getY());

		// Inside
		result = createPoint(0, 0);
		Ellipse2afp.computeClosestPointToSolidEllipse(6, 11, 5, 8, 5, 10, result);
		assertEpsilonEquals(6, result.getX());
		assertEpsilonEquals(11, result.getY());

		// Outside / touching the bounding box of the ellipse
		result = createPoint(0, 0);
		Ellipse2afp.computeClosestPointToSolidEllipse(3, 8, 5, 8, 5, 10, result);
		assertEpsilonEquals(5.75656, result.getX());
		assertEpsilonEquals(9.41648, result.getY());
	}
	
	@Test
	public void staticComputeFarthestPointToShallowEllipse_verticalAxisGreater() {
		// Values computed with GeoGebra tool
		Point2D result;
		
		// Lower / Lower
		result = createPoint(0, 0);
		Ellipse2afp.computeFarthestPointToShallowEllipse(0, 0, 5, 8, 5, 10, result);
		assertEpsilonEquals(8.05329, result.getX());
		assertEpsilonEquals(17.92645, result.getY());

		// Lower / Upper
		result = createPoint(0, 0);
		Ellipse2afp.computeFarthestPointToShallowEllipse(0, 24, 5, 8, 5, 10, result);
		assertEpsilonEquals(8.12711, result.getX());
		assertEpsilonEquals(8.08913, result.getY());

		// Upper / Lower
		result = createPoint(0, 0);
		Ellipse2afp.computeFarthestPointToShallowEllipse(24, 0, 5, 8, 5, 10, result);
		assertEpsilonEquals(6.31519, result.getX());
		assertEpsilonEquals(17.75919, result.getY());

		// Upper / Upper
		result = createPoint(0, 0);
		Ellipse2afp.computeFarthestPointToShallowEllipse(24, 24, 5, 8, 5, 10, result);
		assertEpsilonEquals(6.16141, result.getX());
		assertEpsilonEquals(8.28223, result.getY());

		// On x axis (positive)
		result = createPoint(0, 0);
		Ellipse2afp.computeFarthestPointToShallowEllipse(18, 13, 5, 8, 5, 10, result);
		assertEpsilonEquals(5, result.getX());
		assertEpsilonEquals(13, result.getY());

		// On x axis (negative)
		result = createPoint(0, 0);
		Ellipse2afp.computeFarthestPointToShallowEllipse(0, 13, 5, 8, 5, 10, result);
		assertEpsilonEquals(10, result.getX());
		assertEpsilonEquals(13, result.getY());

		// On y axis (positive)
		result = createPoint(0, 0);
		Ellipse2afp.computeFarthestPointToShallowEllipse(7.5, 24, 5, 8, 5, 10, result);
		assertEpsilonEquals(7.5, result.getX());
		assertEpsilonEquals(8, result.getY());

		// On y axis (negative)
		result = createPoint(0, 0);
		Ellipse2afp.computeFarthestPointToShallowEllipse(7.5, 0, 5, 8, 5, 10, result);
		assertEpsilonEquals(7.5, result.getX());
		assertEpsilonEquals(18, result.getY());

		// Inside
		result = createPoint(0, 0);
		Ellipse2afp.computeFarthestPointToShallowEllipse(6, 11, 5, 8, 5, 10, result);
		assertEpsilonEquals(7.82555, result.getX());
		assertEpsilonEquals(17.97659, result.getY());
	}
	
	@Test
	public void staticContainsEllipsePoint() {
		assertTrue(Ellipse2afp.containsEllipsePoint(0, 0, 2, 1, 1, 0.5));
		assertTrue(Ellipse2afp.containsEllipsePoint(0, 0, 2, 1, 1.5, 0.7));
		assertTrue(Ellipse2afp.containsEllipsePoint(0, 0, 2, 1, 0.3, 0.3));
		assertTrue(Ellipse2afp.containsEllipsePoint(0, 0, 2, 1, 2, 0.5));
		assertTrue(Ellipse2afp.containsEllipsePoint(0, 0, 2, 1, 1, 1));
		assertFalse(Ellipse2afp.containsEllipsePoint(0, 0, 2, 1, 0, 0));
		assertFalse(Ellipse2afp.containsEllipsePoint(0, 0, 2, 1, 0, 1));
		assertFalse(Ellipse2afp.containsEllipsePoint(0, 0, 2, 1, 1, 10));
	}
	
	@Test
	public void staticContainsEllipseRectangle() {
		assertFalse(Ellipse2afp.containsEllipseRectangle(0, 0, 1, 1,
				0, 0, 1, 1));
		assertFalse(Ellipse2afp.containsEllipseRectangle(0, 0, 1, 1,
				-5, -5, -4, -4));
		assertFalse(Ellipse2afp.containsEllipseRectangle(0, 0, 1, 1,
				.5, .5, 5.5, 5.5));
		assertFalse(Ellipse2afp.containsEllipseRectangle(0, 0, 1, 1,
				.5, .5, 5.5, 1.1));
		assertFalse(Ellipse2afp.containsEllipseRectangle(0, 0, 1, 1,
				-5, -5, 0, 0));
		assertFalse(Ellipse2afp.containsEllipseRectangle(0, 0, 1, 1,
				-9, -9, -5, -5));
		assertFalse(Ellipse2afp.containsEllipseRectangle(0, 0, 1, 1,
				-5, -9, -1, -5));
		assertFalse(Ellipse2afp.containsEllipseRectangle(0, 0, 1, 1,
				5, -5, 11, 0));
		assertFalse(Ellipse2afp.containsEllipseRectangle(0, 0, 1, 1,
				-5, -5, -5, -5));
		assertFalse(Ellipse2afp.containsEllipseRectangle(0, 0, 1, 1,
				-5, -5, -4.9, -4.9));
		assertTrue(Ellipse2afp.containsEllipseRectangle(0, 0, 1, 1,
				.25, .25, .75, .75));
	}
	
	@Test
	public void staticIntersectsEllipseEllipse() {
		assertTrue(Ellipse2afp.intersectsEllipseEllipse(0, 0, 1, 1,
				0, 0, 1, 1));
		assertTrue(Ellipse2afp.intersectsEllipseEllipse(0, 0, 1, 1,
				-5, -5, 6, 6));
		assertTrue(Ellipse2afp.intersectsEllipseEllipse(0, 0, 1, 1,
				.5, .5, 4.5, 4.5));
		assertTrue(Ellipse2afp.intersectsEllipseEllipse(0, 0, 1, 1,
				.5, .5, 4.5, .1));
		assertTrue(Ellipse2afp.intersectsEllipseEllipse(0, 0, 1, 1,
				-5, -5, 10, 10));
		assertFalse(Ellipse2afp.intersectsEllipseEllipse(0, 0, 1, 1,
				-5, -5, 1, 1));
		assertFalse(Ellipse2afp.intersectsEllipseEllipse(0, 0, 1, 1,
				-5, -5, 9, 1));
		assertFalse(Ellipse2afp.intersectsEllipseEllipse(0, 0, 1, 1,
				5, -5, 1, 10));
		assertFalse(Ellipse2afp.intersectsEllipseEllipse(0, 0, 1, 1,
				-5, -5, 5, 5));
		assertFalse(Ellipse2afp.intersectsEllipseEllipse(0, 0, 1, 1,
				-5, -5, 5.1, 5.1));
		assertFalse(Ellipse2afp.intersectsEllipseEllipse(5, 8, 5, 10,
				1, -1, 5, 10));
	}
	
	@Test
	public void staticIntersectsEllipseLine() {
		assertTrue(Ellipse2afp.intersectsEllipseLine(0, 0, 1, 1,
				0, 0, 1, 1));
		assertTrue(Ellipse2afp.intersectsEllipseLine(0, 0, 1, 1,
				-5, -5, 1, 1));
		assertTrue(Ellipse2afp.intersectsEllipseLine(0, 0, 1, 1,
				.5, .5, 5, 5));
		assertTrue(Ellipse2afp.intersectsEllipseLine(0, 0, 1, 1,
				.5, .5, 5, .6));
		assertTrue(Ellipse2afp.intersectsEllipseLine(0, 0, 1, 1,
				-5, -5, 5, 5));
		assertTrue(Ellipse2afp.intersectsEllipseLine(0, 0, 1, 1,
				-5, -5, -4, -4));
		assertFalse(Ellipse2afp.intersectsEllipseLine(0, 0, 1, 1,
				-5, -5, 4, -4));
		assertFalse(Ellipse2afp.intersectsEllipseLine(0, 0, 1, 1,
				5, -5, 6, 5));
		assertTrue(Ellipse2afp.intersectsEllipseLine(0, 0, 1, 1,
				-5, -5, .0, .0));
		assertTrue(Ellipse2afp.intersectsEllipseLine(0, 0, 1, 1,
				-5, -5, .1, .1));
		assertTrue(Ellipse2afp.intersectsEllipseLine(0, 0, 1, 1,
				-5, -5, .4, .3));

		assertTrue(Ellipse2afp.intersectsEllipseLine(
				6, -5, 1, 2, 6.7773438, -3.0272121, 6.7890625, -3.1188917));
		assertTrue(Ellipse2afp.intersectsEllipseLine(
				6, -5, 1, 2, 6.7890625, -3.1188917, 6.8007812, -3.2118688));
		assertTrue(Ellipse2afp.intersectsEllipseLine(
				6, -5, 1, 2, 6.8007812, -3.2118688, 6.8125, -3.3061523));
		assertTrue(Ellipse2afp.intersectsEllipseLine(
				6, -5, 1, 2, 6.8125, -3.3061523, 6.8242188, -3.401752));
		assertTrue(Ellipse2afp.intersectsEllipseLine(
				6, -5, 1, 2, 6.8242188, -3.401752, 6.8359375, -3.4986773));
		assertTrue(Ellipse2afp.intersectsEllipseLine(
				6, -5, 1, 2, 6.8359375, -3.4986773, 6.8476562, -3.5969372));
		assertTrue(Ellipse2afp.intersectsEllipseLine(
				6, -5, 1, 2, 6.8476562, -3.5969372, 6.859375, -3.6965408));
		assertTrue(Ellipse2afp.intersectsEllipseLine(
				6, -5, 1, 2, 6.859375, -3.6965408, 6.8710938, -3.7974977));
		assertTrue(Ellipse2afp.intersectsEllipseLine(
				6, -5, 1, 2, 6.8710938, -3.7974977, 6.8828125, -3.8998175));
		assertTrue(Ellipse2afp.intersectsEllipseLine(
				6, -5, 1, 2, 6.8828125, -3.8998175, 6.8945312, -4.003509));
		assertTrue(Ellipse2afp.intersectsEllipseLine(
				6, -5, 1, 2, 6.8945312, -4.003509, 6.90625, -4.1085815));
		assertTrue(Ellipse2afp.intersectsEllipseLine(
				6, -5, 1, 2, 6.90625, -4.1085815, 6.9179688, -4.2150445));
		assertTrue(Ellipse2afp.intersectsEllipseLine(
				6, -5, 1, 2, 6.9179688, -4.2150445, 6.9296875, -4.3229074));
		assertTrue(Ellipse2afp.intersectsEllipseLine(
				6, -5, 1, 2, 6.9296875, -4.3229074, 6.9414062, -4.4321795));
		assertTrue(Ellipse2afp.intersectsEllipseLine(
				6, -5, 1, 2, 6.9414062, -4.4321795, 6.953125, -4.5428696));
		assertTrue(Ellipse2afp.intersectsEllipseLine(
				6, -5, 1, 2, 6.953125, -4.5428696, 6.9648438, -4.6549873));
		assertTrue(Ellipse2afp.intersectsEllipseLine(
				6, -5, 1, 2, 6.9648438, -4.6549873, 6.9765625, -4.7685423));
		assertTrue(Ellipse2afp.intersectsEllipseLine(
				6, -5, 1, 2, 6.9765625, -4.7685423, 6.9882812, -4.8835435));
		assertTrue(Ellipse2afp.intersectsEllipseLine(
				6, -5, 1, 2, 6.9882812, -4.8835435, 7, -5));

		assertTrue(Ellipse2afp.intersectsEllipseLine(
				0, 0, 1, 2, .5, -1, .5, 2));
		assertTrue(Ellipse2afp.intersectsEllipseLine(
				0, 0, 1, 2, .5, -1, .5, -.5));
	}
	
	@Test
	public void staticIntersectsEllipseRectangle() {
		assertTrue(Ellipse2afp.intersectsEllipseRectangle(0, 0, 1, 1,
				0, 0, 1, 1));
		assertTrue(Ellipse2afp.intersectsEllipseRectangle(0, 0, 1, 1,
				-5, -5, 1, 1));
		assertTrue(Ellipse2afp.intersectsEllipseRectangle(0, 0, 1, 1,
				.5, .5, 5, 5));
		assertTrue(Ellipse2afp.intersectsEllipseRectangle(0, 0, 1, 1,
				.5, .5, 5, .6));
		assertTrue(Ellipse2afp.intersectsEllipseRectangle(0, 0, 1, 1,
				-5, -5, 5, 5));
		assertFalse(Ellipse2afp.intersectsEllipseRectangle(0, 0, 1, 1,
				-5, -5, -4, -4));
		assertFalse(Ellipse2afp.intersectsEllipseRectangle(0, 0, 1, 1,
				-5, -5, 4, -4));
		assertFalse(Ellipse2afp.intersectsEllipseRectangle(0, 0, 1, 1,
				5, -5, 6, 5));
		assertFalse(Ellipse2afp.intersectsEllipseRectangle(0, 0, 1, 1,
				-5, -5, .0, .0));
		assertFalse(Ellipse2afp.intersectsEllipseRectangle(0, 0, 1, 1,
				-5, -5, .1, .1));
		assertTrue(Ellipse2afp.intersectsEllipseRectangle(5, 8, .2, .4,
				5, 8, 5.05, 8.1));
	}
	
	@Test
	public void staticIntersectsEllipseSegment_noIntersectionAtTouching() {
		assertTrue(Ellipse2afp.intersectsEllipseSegment(0, 0, 1, 1,
				0, 0, 1, 1,
				false));
		assertTrue(Ellipse2afp.intersectsEllipseSegment(0, 0, 1, 1,
				-5, -5, 1, 1,
				false));
		assertTrue(Ellipse2afp.intersectsEllipseSegment(0, 0, 1, 1,
				.5, .5, 5, 5,
				false));
		assertTrue(Ellipse2afp.intersectsEllipseSegment(0, 0, 1, 1,
				.5, .5, 5, .6,
				false));
		assertTrue(Ellipse2afp.intersectsEllipseSegment(0, 0, 1, 1,
				-5, -5, 5, 5,
				false));
		assertFalse(Ellipse2afp.intersectsEllipseSegment(0, 0, 1, 1,
				-5, -5, -4, -4,
				false));
		assertFalse(Ellipse2afp.intersectsEllipseSegment(0, 0, 1, 1,
				-5, -5, 4, -4,
				false));
		assertFalse(Ellipse2afp.intersectsEllipseSegment(0, 0, 1, 1,
				5, -5, 6, 5,
				false));
		assertFalse(Ellipse2afp.intersectsEllipseSegment(0, 0, 1, 1,
				-5, -5, .0, .0,
				false));
		assertFalse(Ellipse2afp.intersectsEllipseSegment(0, 0, 1, 1,
				-5, -5, .1, .1,
				false));
		assertTrue(Ellipse2afp.intersectsEllipseSegment(0, 0, 1, 1,
				-5, -5, .4, .3,
				false));
		
		assertFalse(Ellipse2afp.intersectsEllipseSegment(
				6, -5, 1, 2, 6.7773438, -3.0272121, 6.7890625, -3.1188917,
				false));
		assertTrue(Ellipse2afp.intersectsEllipseSegment(
				6, -5, 1, 2, 6.7890625, -3.1188917, 6.8007812, -3.2118688,
				false));
		assertTrue(Ellipse2afp.intersectsEllipseSegment(
				6, -5, 1, 2, 6.8007812, -3.2118688, 6.8125, -3.3061523,
				false));
		assertTrue(Ellipse2afp.intersectsEllipseSegment(
				6, -5, 1, 2, 6.8125, -3.3061523, 6.8242188, -3.401752,
				false));
		assertTrue(Ellipse2afp.intersectsEllipseSegment(
				6, -5, 1, 2, 6.8242188, -3.401752, 6.8359375, -3.4986773,
				false));
		assertTrue(Ellipse2afp.intersectsEllipseSegment(
				6, -5, 1, 2, 6.8359375, -3.4986773, 6.8476562, -3.5969372,
				false));
		assertTrue(Ellipse2afp.intersectsEllipseSegment(
				6, -5, 1, 2, 6.8476562, -3.5969372, 6.859375, -3.6965408,
				false));
		assertTrue(Ellipse2afp.intersectsEllipseSegment(
				6, -5, 1, 2, 6.859375, -3.6965408, 6.8710938, -3.7974977,
				false));
		assertTrue(Ellipse2afp.intersectsEllipseSegment(
				6, -5, 1, 2, 6.8710938, -3.7974977, 6.8828125, -3.8998175,
				false));
		assertTrue(Ellipse2afp.intersectsEllipseSegment(
				6, -5, 1, 2, 6.8828125, -3.8998175, 6.8945312, -4.003509,
				false));
		assertTrue(Ellipse2afp.intersectsEllipseSegment(
				6, -5, 1, 2, 6.8945312, -4.003509, 6.90625, -4.1085815,
				false));
		assertTrue(Ellipse2afp.intersectsEllipseSegment(
				6, -5, 1, 2, 6.90625, -4.1085815, 6.9179688, -4.2150445,
				false));
		assertTrue(Ellipse2afp.intersectsEllipseSegment(
				6, -5, 1, 2, 6.9179688, -4.2150445, 6.9296875, -4.3229074,
				false));
		assertTrue(Ellipse2afp.intersectsEllipseSegment(
				6, -5, 1, 2, 6.9296875, -4.3229074, 6.9414062, -4.4321795,
				false));
		assertTrue(Ellipse2afp.intersectsEllipseSegment(
				6, -5, 1, 2, 6.9414062, -4.4321795, 6.953125, -4.5428696,
				false));
		assertFalse(Ellipse2afp.intersectsEllipseSegment(
				6, -5, 1, 2, 6.953125, -4.5428696, 6.9648438, -4.6549873,
				false));
		assertFalse(Ellipse2afp.intersectsEllipseSegment(
				6, -5, 1, 2, 6.9648438, -4.6549873, 6.9765625, -4.7685423,
				false));
		assertFalse(Ellipse2afp.intersectsEllipseSegment(
				6, -5, 1, 2, 6.9765625, -4.7685423, 6.9882812, -4.8835435,
				false));
		assertFalse(Ellipse2afp.intersectsEllipseSegment(
				6, -5, 1, 2, 6.9882812, -4.8835435, 7, -5,
				false));

		assertTrue(Ellipse2afp.intersectsEllipseSegment(
				0, 0, 1, 2, .5, -1, .5, 2,
				false));
		assertFalse(Ellipse2afp.intersectsEllipseSegment(
				0, 0, 1, 2, .5, -1, .5, -.5,
				false));

		assertFalse(Ellipse2afp.intersectsEllipseSegment(5, 8, 5, 10, 0, 8, 50, 8, false));
		assertFalse(Ellipse2afp.intersectsEllipseSegment(5, 8, 5, 10, 5, 0, 5, 50, false));
	}
	
	@Test
	public void staticIntersectsEllipseSegment_intersectionAtTouching() {
		assertTrue(Ellipse2afp.intersectsEllipseSegment(0, 0, 1, 1,
				0, 0, 1, 1,
				true));
		assertTrue(Ellipse2afp.intersectsEllipseSegment(0, 0, 1, 1,
				-5, -5, 1, 1,
				true));
		assertTrue(Ellipse2afp.intersectsEllipseSegment(0, 0, 1, 1,
				.5, .5, 5, 5,
				true));
		assertTrue(Ellipse2afp.intersectsEllipseSegment(0, 0, 1, 1,
				.5, .5, 5, .6,
				true));
		assertTrue(Ellipse2afp.intersectsEllipseSegment(0, 0, 1, 1,
				-5, -5, 5, 5,
				true));
		assertFalse(Ellipse2afp.intersectsEllipseSegment(0, 0, 1, 1,
				-5, -5, -4, -4,
				true));
		assertFalse(Ellipse2afp.intersectsEllipseSegment(0, 0, 1, 1,
				-5, -5, 4, -4,
				true));
		assertFalse(Ellipse2afp.intersectsEllipseSegment(0, 0, 1, 1,
				5, -5, 6, 5,
				true));
		assertFalse(Ellipse2afp.intersectsEllipseSegment(0, 0, 1, 1,
				-5, -5, .0, .0,
				true));
		assertFalse(Ellipse2afp.intersectsEllipseSegment(0, 0, 1, 1,
				-5, -5, .1, .1,
				true));
		assertTrue(Ellipse2afp.intersectsEllipseSegment(0, 0, 1, 1,
				-5, -5, .4, .3,
				true));
		
		assertFalse(Ellipse2afp.intersectsEllipseSegment(
				6, -5, 1, 2, 6.7773438, -3.0272121, 6.7890625, -3.1188917,
				true));
		assertTrue(Ellipse2afp.intersectsEllipseSegment(
				6, -5, 1, 2, 6.7890625, -3.1188917, 6.8007812, -3.2118688,
				true));
		assertTrue(Ellipse2afp.intersectsEllipseSegment(
				6, -5, 1, 2, 6.8007812, -3.2118688, 6.8125, -3.3061523,
				true));
		assertTrue(Ellipse2afp.intersectsEllipseSegment(
				6, -5, 1, 2, 6.8125, -3.3061523, 6.8242188, -3.401752,
				true));
		assertTrue(Ellipse2afp.intersectsEllipseSegment(
				6, -5, 1, 2, 6.8242188, -3.401752, 6.8359375, -3.4986773,
				true));
		assertTrue(Ellipse2afp.intersectsEllipseSegment(
				6, -5, 1, 2, 6.8359375, -3.4986773, 6.8476562, -3.5969372,
				true));
		assertTrue(Ellipse2afp.intersectsEllipseSegment(
				6, -5, 1, 2, 6.8476562, -3.5969372, 6.859375, -3.6965408,
				true));
		assertTrue(Ellipse2afp.intersectsEllipseSegment(
				6, -5, 1, 2, 6.859375, -3.6965408, 6.8710938, -3.7974977,
				true));
		assertTrue(Ellipse2afp.intersectsEllipseSegment(
				6, -5, 1, 2, 6.8710938, -3.7974977, 6.8828125, -3.8998175,
				true));
		assertTrue(Ellipse2afp.intersectsEllipseSegment(
				6, -5, 1, 2, 6.8828125, -3.8998175, 6.8945312, -4.003509,
				true));
		assertTrue(Ellipse2afp.intersectsEllipseSegment(
				6, -5, 1, 2, 6.8945312, -4.003509, 6.90625, -4.1085815,
				true));
		assertTrue(Ellipse2afp.intersectsEllipseSegment(
				6, -5, 1, 2, 6.90625, -4.1085815, 6.9179688, -4.2150445,
				true));
		assertTrue(Ellipse2afp.intersectsEllipseSegment(
				6, -5, 1, 2, 6.9179688, -4.2150445, 6.9296875, -4.3229074,
				true));
		assertTrue(Ellipse2afp.intersectsEllipseSegment(
				6, -5, 1, 2, 6.9296875, -4.3229074, 6.9414062, -4.4321795,
				true));
		assertTrue(Ellipse2afp.intersectsEllipseSegment(
				6, -5, 1, 2, 6.9414062, -4.4321795, 6.953125, -4.5428696,
				true));
		assertFalse(Ellipse2afp.intersectsEllipseSegment(
				6, -5, 1, 2, 6.953125, -4.5428696, 6.9648438, -4.6549873,
				true));
		assertFalse(Ellipse2afp.intersectsEllipseSegment(
				6, -5, 1, 2, 6.9648438, -4.6549873, 6.9765625, -4.7685423,
				true));
		assertFalse(Ellipse2afp.intersectsEllipseSegment(
				6, -5, 1, 2, 6.9765625, -4.7685423, 6.9882812, -4.8835435,
				true));
		assertFalse(Ellipse2afp.intersectsEllipseSegment(
				6, -5, 1, 2, 6.9882812, -4.8835435, 7, -5,
				true));

		assertTrue(Ellipse2afp.intersectsEllipseSegment(
				0, 0, 1, 2, .5, -1, .5, 2,
				true));
		assertFalse(Ellipse2afp.intersectsEllipseSegment(
				0, 0, 1, 2, .5, -1, .5, -.5,
				true));

		assertTrue(Ellipse2afp.intersectsEllipseSegment(5, 8, 5, 10, 0, 8, 50, 8, true));
		assertTrue(Ellipse2afp.intersectsEllipseSegment(5, 8, 5, 10, 5, 0, 5, 50, true));
	}

	@Test
	public void staticIntersectsEllipseCircle() {
		assertFalse(Ellipse2afp.intersectsEllipseCircle(5, 8, 5, 10, 0, 0, 1));
		assertTrue(Ellipse2afp.intersectsEllipseCircle(5, 8, 5, 10, 7.5, 7, 2));
		assertFalse(Ellipse2afp.intersectsEllipseCircle(5, 8, 5, 10, 3, 8, 2));
		assertFalse(Ellipse2afp.intersectsEllipseCircle(5, 8, 5, 10, 4, 8, 2));
	}

	@Test
	public void setDoubleDoubleDoubleDouble() {
		this.shape.set(123.456, 789.123, 456.789, 123.456);
		assertEpsilonEquals(123.456, this.shape.getMinX());
		assertEpsilonEquals(789.123, this.shape.getMinY());
		assertEpsilonEquals(580.245, this.shape.getMaxX());
		assertEpsilonEquals(912.579, this.shape.getMaxY());
	}
	
	@Test
	public void setPoint2DPoint2D() {
		this.shape.set(createPoint(123.456, 789.123), createPoint(456.789, 123.456));
		assertEpsilonEquals(123.456, this.shape.getMinX());
		assertEpsilonEquals(123.456, this.shape.getMinY());
		assertEpsilonEquals(456.789, this.shape.getMaxX());
		assertEpsilonEquals(789.123, this.shape.getMaxY());
	}
	
	@Test
	public void setWidth() {
		this.shape.setWidth(123.456);
		assertEpsilonEquals(5, this.shape.getMinX());
		assertEpsilonEquals(8, this.shape.getMinY());
		assertEpsilonEquals(128.456, this.shape.getMaxX());
		assertEpsilonEquals(18, this.shape.getMaxY());
	}

	@Test
	public void setHeight() {
		this.shape.setHeight(123.456);
		assertEpsilonEquals(5, this.shape.getMinX());
		assertEpsilonEquals(8, this.shape.getMinY());
		assertEpsilonEquals(10, this.shape.getMaxX());
		assertEpsilonEquals(131.456, this.shape.getMaxY());
	}
	
	@Test 
	public void setFromCornersDoubleDoubleDoubleDouble() {
		this.shape.setFromCorners(123.456, 789.123, 456.789, 159.357);
		assertEpsilonEquals(123.456, this.shape.getMinX());
		assertEpsilonEquals(159.357, this.shape.getMinY());
		assertEpsilonEquals(456.789, this.shape.getMaxX());
		assertEpsilonEquals(789.123, this.shape.getMaxY());
	}

	@Test
	public void setFromCornersPoint2DPoint2D() {
		this.shape.setFromCorners(createPoint(123.456, 789.123), createPoint(456.789, 159.357));
		assertEpsilonEquals(123.456, this.shape.getMinX());
		assertEpsilonEquals(159.357, this.shape.getMinY());
		assertEpsilonEquals(456.789, this.shape.getMaxX());
		assertEpsilonEquals(789.123, this.shape.getMaxY());
	}

	@Test
	public void setFromCenterDoubleDoubleDoubleDouble() {
		this.shape.setFromCenter(123.456, 789.123, 456.789, 159.357);
		assertEpsilonEquals(-209.877, this.shape.getMinX());
		assertEpsilonEquals(159.357, this.shape.getMinY());
		assertEpsilonEquals(456.789, this.shape.getMaxX());
		assertEpsilonEquals(1418.889, this.shape.getMaxY());
	}

	@Test
	public void setFromCenterPoint2DPoint2D() {
		this.shape.setFromCenter(createPoint(123.456, 789.123), createPoint(456.789, 159.357));
		assertEpsilonEquals(-209.877, this.shape.getMinX());
		assertEpsilonEquals(159.357, this.shape.getMinY());
		assertEpsilonEquals(456.789, this.shape.getMaxX());
		assertEpsilonEquals(1418.889, this.shape.getMaxY());
	}

	@Test
	public void getMinX() {
		assertEpsilonEquals(5, this.shape.getMinX());
	}

	@Test
	public void setMinX() {
		this.shape.setMinX(0);
		assertEpsilonEquals(0, this.shape.getMinX());
		assertEpsilonEquals(8, this.shape.getMinY());
		assertEpsilonEquals(10, this.shape.getMaxX());
		assertEpsilonEquals(18, this.shape.getMaxY());
		this.shape.setMinX(456);
		assertEpsilonEquals(456, this.shape.getMinX());
		assertEpsilonEquals(8, this.shape.getMinY());
		assertEpsilonEquals(456, this.shape.getMaxX());
		assertEpsilonEquals(18, this.shape.getMaxY());
	}

	@Test
	public void getCenterX() {
		assertEpsilonEquals(7.5, this.shape.getCenterX());
	}

	@Test
	public void getMaxX() {
		assertEpsilonEquals(10, this.shape.getMaxX());
	}

	@Test
	public void setMaxX() {
		this.shape.setMaxX(1456);
		assertEpsilonEquals(5, this.shape.getMinX());
		assertEpsilonEquals(8, this.shape.getMinY());
		assertEpsilonEquals(1456, this.shape.getMaxX());
		assertEpsilonEquals(18, this.shape.getMaxY());
		this.shape.setMaxX(0);
		assertEpsilonEquals(0, this.shape.getMinX());
		assertEpsilonEquals(8, this.shape.getMinY());
		assertEpsilonEquals(0, this.shape.getMaxX());
		assertEpsilonEquals(18, this.shape.getMaxY());
	}

	@Test
	public void getMinY() {
		assertEpsilonEquals(8, this.shape.getMinY());
	}

	@Test
	public void setMinY() {
		this.shape.setMinY(0);
		assertEpsilonEquals(5, this.shape.getMinX());
		assertEpsilonEquals(0, this.shape.getMinY());
		assertEpsilonEquals(10, this.shape.getMaxX());
		assertEpsilonEquals(18, this.shape.getMaxY());
		this.shape.setMinY(456);
		assertEpsilonEquals(5, this.shape.getMinX());
		assertEpsilonEquals(456, this.shape.getMinY());
		assertEpsilonEquals(10, this.shape.getMaxX());
		assertEpsilonEquals(456, this.shape.getMaxY());
	}

	@Test
	public void getCenterY() {
		assertEpsilonEquals(13, this.shape.getCenterY());
	}

	@Test
	public void getMaxY() {
		assertEpsilonEquals(18, this.shape.getMaxY());
	}
	
	@Test
	public void setMaxY() {
		this.shape.setMaxY(1456);
		assertEpsilonEquals(5, this.shape.getMinX());
		assertEpsilonEquals(8, this.shape.getMinY());
		assertEpsilonEquals(10, this.shape.getMaxX());
		assertEpsilonEquals(1456, this.shape.getMaxY());
		this.shape.setMaxY(0);
		assertEpsilonEquals(5, this.shape.getMinX());
		assertEpsilonEquals(0, this.shape.getMinY());
		assertEpsilonEquals(10, this.shape.getMaxX());
		assertEpsilonEquals(0, this.shape.getMaxY());
	}

	@Test
	public void getWidth() {
		assertEpsilonEquals(5, this.shape.getWidth());
	}

	@Test
	public void getHeight() {
		assertEpsilonEquals(10, this.shape.getHeight());
	}
	
	@Test
	public void inflateDoubleDoubleDoubleDouble() {
		this.shape.inflate(15, -10, -20, 20);
		assertEpsilonEquals(-10, this.shape.getMinX());
		assertEpsilonEquals(18, this.shape.getMinY());
		assertEpsilonEquals(-10, this.shape.getMaxX());
		assertEpsilonEquals(38, this.shape.getMaxY());
	}

	@Test
	public void getHorizontalRadius_verticalAxisGreater() {
		assertEpsilonEquals(2.5, this.shape.getHorizontalRadius());
	}
	
	@Test
	public void getVerticalRadius_verticalAxisGreater() {
		assertEpsilonEquals(5, this.shape.getVerticalRadius());
	}

	@Test
	public void getMinFocusPoint_verticalAxisGreater() {
		Point2D p = this.shape.getMinFocusPoint();
		assertNotNull(p);
		assertEpsilonEquals(7.5, p.getX());
		assertEpsilonEquals(8.66987, p.getY());
	}
	
	@Test
	public void getMaxFocusPoint_verticalAxisGreater() {
		Point2D p = this.shape.getMaxFocusPoint();
		assertNotNull(p);
		assertEpsilonEquals(7.5, p.getX());
		assertEpsilonEquals(17.33013, p.getY());
	}

	@Test
	public void getHorizontalRadius_horizontalAxisGreater() {
		this.shape.set(5, 8, 10, 5);
		assertEpsilonEquals(5, this.shape.getHorizontalRadius());
	}
	
	@Test
	public void getVerticalRadius_horizontalAxisGreater() {
		this.shape.set(5, 8, 10, 5);
		assertEpsilonEquals(2.5, this.shape.getVerticalRadius());
	}

	@Test
	public void getMinFocusPoint_horizontalAxisGreater() {
		this.shape.set(5, 8, 10, 5);
		Point2D p = this.shape.getMinFocusPoint();
		assertNotNull(p);
		assertEpsilonEquals(5.66987, p.getX());
		assertEpsilonEquals(10.5, p.getY());
	}
	
	@Test
	public void getMaxFocusPoint_horizontalAxisGreater() {
		this.shape.set(5, 8, 10, 5);
		Point2D p = this.shape.getMaxFocusPoint();
		assertNotNull(p);
		assertEpsilonEquals(14.3301, p.getX());
		assertEpsilonEquals(10.5, p.getY());
	}

}