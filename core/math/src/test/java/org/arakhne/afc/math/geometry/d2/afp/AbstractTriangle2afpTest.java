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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * This program is free software; you can redistribute it and/or modify
 */
package org.arakhne.afc.math.geometry.d2.afp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.arakhne.afc.math.geometry.PathElementType;
import org.arakhne.afc.math.geometry.PathWindingRule;
import org.arakhne.afc.math.geometry.d2.Point2D;
import org.arakhne.afc.math.geometry.d2.Shape2D;
import org.arakhne.afc.math.geometry.d2.Transform2D;
import org.arakhne.afc.math.geometry.d2.ai.PathIterator2ai;
import org.junit.Test;

@SuppressWarnings("all")
public abstract class AbstractTriangle2afpTest<T extends Triangle2afp<?, T, ?, ?, ?, B>,
		B extends Rectangle2afp<?, ?, ?, ?, ?, B>> extends AbstractShape2afpTest<T, B> {

	@Override
	protected final T createShape() {
		return (T) createTriangle(5, 8, -10, 1, -1, -2);
	}
	
	@Test
	public void staticIsCCWOrderDefinition() {
		assertTrue(Triangle2afp.isCCWOrderDefinition(5, 8, -10, 1, -1, -2));
		assertTrue(Triangle2afp.isCCWOrderDefinition(-10, 1, -1, -2, 5, 8));
		assertTrue(Triangle2afp.isCCWOrderDefinition(-1, -2, 5, 8, -10, 1));
		assertFalse(Triangle2afp.isCCWOrderDefinition(5, 8, -1, -2, -10, 1));
		assertFalse(Triangle2afp.isCCWOrderDefinition(-1, -2, -10, 1, 5, 8));
		assertFalse(Triangle2afp.isCCWOrderDefinition(-10, 1, 5, 8, -1, -2));
		
		assertFalse(Triangle2afp.isCCWOrderDefinition(-6, 8, -4, 2, -6, 0));
		assertTrue(Triangle2afp.isCCWOrderDefinition(-6, 8, -6, 0, -4, 2));
	}

	@Test
	public void staticContainsTrianglePoint() {
		assertTrue(Triangle2afp.containsTrianglePoint(5, 8, -10, 1, -1, -2, 0, 0));
		assertFalse(Triangle2afp.containsTrianglePoint(5, 8, -10, 1, -1, -2, 11, 10));
		assertFalse(Triangle2afp.containsTrianglePoint(5, 8, -10, 1, -1, -2, 11, 50));
		assertFalse(Triangle2afp.containsTrianglePoint(5, 8, -10, 1, -1, -2, 9, 12));
		assertFalse(Triangle2afp.containsTrianglePoint(5, 8, -10, 1, -1, -2, 9, 11));
		assertFalse(Triangle2afp.containsTrianglePoint(5, 8, -10, 1, -1, -2, 0, 6));
		assertFalse(Triangle2afp.containsTrianglePoint(5, 8, -10, 1, -1, -2, 8, 12));
		assertTrue(Triangle2afp.containsTrianglePoint(5, 8, -10, 1, -1, -2, 3, 7));
		assertFalse(Triangle2afp.containsTrianglePoint(5, 8, -10, 1, -1, -2, 10, 11));
		assertFalse(Triangle2afp.containsTrianglePoint(5, 8, -10, 1, -1, -2, 9, 10));
		assertTrue(Triangle2afp.containsTrianglePoint(5, 8, -10, 1, -1, -2, -4, 2));
		assertFalse(Triangle2afp.containsTrianglePoint(5, 8, -10, 1, -1, -2, -8, -5));
	}
	
	@Test
	public void staticContainsTriangleRectangle() {
		assertFalse(Triangle2afp.containsTriangleRectangle(5, 8, -10, 1, -1, -2, 0, 0, 1, 1));
		assertTrue(Triangle2afp.containsTriangleRectangle(5, 8, -10, 1, -1, -2, -2, 1, 1, 1));
		assertFalse(Triangle2afp.containsTriangleRectangle(5, 8, -10, 1, -1, -2, -30, 20, 1, 1));
		assertFalse(Triangle2afp.containsTriangleRectangle(5, 8, -10, 1, -1, -2, -10.5, 0.5, 1, 1));
	}

	@Test
	public void staticComputeClosestFarthestPoints() {
		Point2D closest, farthest;
		
		closest = createPoint(Double.NaN, Double.NaN);
		farthest = createPoint(Double.NaN, Double.NaN);
		Triangle2afp.computeClosestFarthestPoints(5, 8, -10, 1, -1, -2, 0, 0, closest, farthest);
		assertEpsilonEquals(0, closest.getX());
		assertEpsilonEquals(0, closest.getY());
		assertEpsilonEquals(-10, farthest.getX());
		assertEpsilonEquals(1, farthest.getY());
		
		closest = createPoint(Double.NaN, Double.NaN);
		farthest = createPoint(Double.NaN, Double.NaN);
		Triangle2afp.computeClosestFarthestPoints(5, 8, -10, 1, -1, -2, 9, 12, closest, farthest);
		assertEpsilonEquals(5, closest.getX());
		assertEpsilonEquals(8, closest.getY());
		assertEpsilonEquals(-10, farthest.getX());
		assertEpsilonEquals(1, farthest.getY());
	
		closest = createPoint(Double.NaN, Double.NaN);
		farthest = createPoint(Double.NaN, Double.NaN);
		Triangle2afp.computeClosestFarthestPoints(5, 8, -10, 1, -1, -2, 0, 6, closest, farthest);
		assertEpsilonEquals(0.12774, closest.getX());
		assertEpsilonEquals(5.72628, closest.getY());
		assertEpsilonEquals(-10, farthest.getX());
		assertEpsilonEquals(1, farthest.getY());
		
		closest = createPoint(Double.NaN, Double.NaN);
		farthest = createPoint(Double.NaN, Double.NaN);
		Triangle2afp.computeClosestFarthestPoints(5, 8, -10, 1, -1, -2, -20, 1, closest, farthest);
		assertEpsilonEquals(-10, closest.getX());
		assertEpsilonEquals(1, closest.getY());
		assertEpsilonEquals(5, farthest.getX());
		assertEpsilonEquals(8, farthest.getY());

		closest = createPoint(Double.NaN, Double.NaN);
		farthest = createPoint(Double.NaN, Double.NaN);
		Triangle2afp.computeClosestFarthestPoints(5, 8, -10, 1, -1, -2, -6, -1, closest, farthest);
		assertEpsilonEquals(-5.8, closest.getX());
		assertEpsilonEquals(-0.4, closest.getY());
		assertEpsilonEquals(5, farthest.getX());
		assertEpsilonEquals(8, farthest.getY());

		closest = createPoint(Double.NaN, Double.NaN);
		farthest = createPoint(Double.NaN, Double.NaN);
		Triangle2afp.computeClosestFarthestPoints(5, 8, -10, 1, -1, -2, -1, -6, closest, farthest);
		assertEpsilonEquals(-1, closest.getX());
		assertEpsilonEquals(-2, closest.getY());
		assertEpsilonEquals(5, farthest.getX());
		assertEpsilonEquals(8, farthest.getY());

		closest = createPoint(Double.NaN, Double.NaN);
		farthest = createPoint(Double.NaN, Double.NaN);
		Triangle2afp.computeClosestFarthestPoints(5, 8, -10, 1, -1, -2, 6, 2, closest, farthest);
		assertEpsilonEquals(2.61765, closest.getX());
		assertEpsilonEquals(4.02941, closest.getY());
		assertEpsilonEquals(-10, farthest.getX());
		assertEpsilonEquals(1, farthest.getY());

		closest = createPoint(Double.NaN, Double.NaN);
		farthest = createPoint(Double.NaN, Double.NaN);
		Triangle2afp.computeClosestFarthestPoints(5, 8, -10, 1, -1, -2, 5, 9, closest, farthest);
		assertEpsilonEquals(5, closest.getX());
		assertEpsilonEquals(8, closest.getY());
		assertEpsilonEquals(-10, farthest.getX());
		assertEpsilonEquals(1, farthest.getY());
	}

	@Test
	public void staticComputeSquaredDistanceTrianglePoint() {
		assertEpsilonEquals(0, Triangle2afp.computeSquaredDistanceTrianglePoint(5, 8, -10, 1, -1, -2, 0, 0));
		assertEpsilonEquals(32, Triangle2afp.computeSquaredDistanceTrianglePoint(5, 8, -10, 1, -1, -2, 9, 12));
		assertEpsilonEquals(.09124, Triangle2afp.computeSquaredDistanceTrianglePoint(5, 8, -10, 1, -1, -2, 0, 6));
		assertEpsilonEquals(100, Triangle2afp.computeSquaredDistanceTrianglePoint(5, 8, -10, 1, -1, -2, -20, 1));
		assertEpsilonEquals(0.40001, Triangle2afp.computeSquaredDistanceTrianglePoint(5, 8, -10, 1, -1, -2, -6, -1));
		assertEpsilonEquals(16, Triangle2afp.computeSquaredDistanceTrianglePoint(5, 8, -10, 1, -1, -2, -1, -6));
		assertEpsilonEquals(15.55876, Triangle2afp.computeSquaredDistanceTrianglePoint(5, 8, -10, 1, -1, -2, 6, 2));
		assertEpsilonEquals(1, Triangle2afp.computeSquaredDistanceTrianglePoint(5, 8, -10, 1, -1, -2, 5, 9));
	}

	@Test
	public void staticIntersectsTriangleCircle() {
		assertTrue(Triangle2afp.intersectsTriangleCircle(5, 8, -10, 1, -1, -2, 5, 8, 1));
		assertTrue(Triangle2afp.intersectsTriangleCircle(5, 8, -10, 1, -1, -2, -10, 1, 1));
		assertTrue(Triangle2afp.intersectsTriangleCircle(5, 8, -10, 1, -1, -2, -1, -2, 1));
		
		assertFalse(Triangle2afp.intersectsTriangleCircle(5, 8, -10, 1, -1, -2, 2, 0, 1));
		assertFalse(Triangle2afp.intersectsTriangleCircle(5, 8, -10, 1, -1, -2, 1.9, 0, 1));
		assertFalse(Triangle2afp.intersectsTriangleCircle(5, 8, -10, 1, -1, -2, 1.8, 0, 1));
		assertFalse(Triangle2afp.intersectsTriangleCircle(5, 8, -10, 1, -1, -2, 1.7, 0, 1));
		assertFalse(Triangle2afp.intersectsTriangleCircle(5, 8, -10, 1, -1, -2, 1.6, 0, 1));
		assertFalse(Triangle2afp.intersectsTriangleCircle(5, 8, -10, 1, -1, -2, 1.5, 0, 1));
		assertFalse(Triangle2afp.intersectsTriangleCircle(5, 8, -10, 1, -1, -2, 1.4, 0, 1));
		assertTrue(Triangle2afp.intersectsTriangleCircle(5, 8, -10, 1, -1, -2, 1.3, 0, 1));

		assertFalse(Triangle2afp.intersectsTriangleCircle(5, 8, -10, 1, -1, -2, 5, 9, 1));
		assertTrue(Triangle2afp.intersectsTriangleCircle(5, 8, -10, 1, -1, -2, 5, 8.9, 1));

		assertTrue(Triangle2afp.intersectsTriangleCircle(5, 8, -10, 1, -1, -2, -4, 1, 1));
	}

	@Test
	public void staticIntersectsTriangleEllipse() {
		assertFalse(Triangle2afp.intersectsTriangleEllipse(5, 8, -10, 1, -1, -2, 5, 8, 2, 1));
		assertTrue(Triangle2afp.intersectsTriangleEllipse(5, 8, -10, 1, -1, -2, -10, 1, 2, 1));
		assertTrue(Triangle2afp.intersectsTriangleEllipse(5, 8, -10, 1, -1, -2, -1, -2, 2, 1));
		
		assertFalse(Triangle2afp.intersectsTriangleEllipse(5, 8, -10, 1, -1, -2, 1, 0, 2, 1));
		assertFalse(Triangle2afp.intersectsTriangleEllipse(5, 8, -10, 1, -1, -2, 0.9, 0, 2, 1));
		assertFalse(Triangle2afp.intersectsTriangleEllipse(5, 8, -10, 1, -1, -2, 0.8, 0, 2, 1));
		assertFalse(Triangle2afp.intersectsTriangleEllipse(5, 8, -10, 1, -1, -2, 0.7, 0, 2, 1));
		assertFalse(Triangle2afp.intersectsTriangleEllipse(5, 8, -10, 1, -1, -2, 0.6, 0, 2, 1));
		assertTrue(Triangle2afp.intersectsTriangleEllipse(5, 8, -10, 1, -1, -2, 0.5, 0, 2, 1));

		assertFalse(Triangle2afp.intersectsTriangleEllipse(5, 8, -10, 1, -1, -2, -1.12464, -2.86312, 2, 1));
	}

	@Test
	public void staticIntersectsTriangleSegment() {
		assertTrue(Triangle2afp.intersectsTriangleSegment(5, 8, -10, 1, -1, -2, -6, -2, -4, 0));
		assertTrue(Triangle2afp.intersectsTriangleSegment(5, 8, -10, 1, -1, -2, -6, -2, 2, 0));
		assertFalse(Triangle2afp.intersectsTriangleSegment(5, 8, -10, 1, -1, -2, -6, -2, 14, -4));
		assertTrue(Triangle2afp.intersectsTriangleSegment(5, 8, -10, 1, -1, -2, -2, 2, 4, 0));
		assertTrue(Triangle2afp.intersectsTriangleSegment(5, 8, -10, 1, -1, -2, -2, 2, 0, 0));
		assertTrue(Triangle2afp.intersectsTriangleSegment(5, 8, -10, 1, -1, -2, -4, -2, -6, 6));
		assertTrue(Triangle2afp.intersectsTriangleSegment(5, 8, -10, 1, -1, -2, 6, 7, -6, 6));
		assertTrue(Triangle2afp.intersectsTriangleSegment(5, 8, -10, 1, -1, -2, 0, 5, -6, 6));
		assertFalse(Triangle2afp.intersectsTriangleSegment(5, 8, -10, 1, -1, -2, -5, 5, -6, 6));

		assertFalse(Triangle2afp.intersectsTriangleSegment(5, 8, -10, 1, -1, -2, -4, -2, 2, -2));
		assertFalse(Triangle2afp.intersectsTriangleSegment(5, 8, -10, 1, -1, -2, -1, -2, 5, 8));
	}

	@Test
	public void staticIntersectsTriangleTriangle_ab() {
		assertFalse(Triangle2afp.intersectsTriangleTriangle(5, 8, -10, 1, -1, -2,
				-8, 6.5, -4, 6, -7, 11));
		assertFalse(Triangle2afp.intersectsTriangleTriangle(5, 8, -10, 1, -1, -2,
				-8, -2, -10, -4, -6, -6));
		assertFalse(Triangle2afp.intersectsTriangleTriangle(5, 8, -10, 1, -1, -2,
				6, 2, 8, -2, 16, 0));
		assertFalse(Triangle2afp.intersectsTriangleTriangle(5, 8, -10, 1, -1, -2,
				0, -4, -2, -6, 2, -8));
		assertFalse(Triangle2afp.intersectsTriangleTriangle(5, 8, -10, 1, -1, -2,
				8, 14, 8, 12, 12, 12));
		assertFalse(Triangle2afp.intersectsTriangleTriangle(5, 8, -10, 1, -1, -2,
				-16, 2, -16, 0, -14, 2));
		assertFalse(Triangle2afp.intersectsTriangleTriangle(5, 8, -10, 1, -1, -2,
				-16, 2, -12, 6, -12, 8));
		assertTrue(Triangle2afp.intersectsTriangleTriangle(5, 8, -10, 1, -1, -2,
				-6, 8, -6, 0, -4, 2));
		assertTrue(Triangle2afp.intersectsTriangleTriangle(5, 8, -10, 1, -1, -2,
				-6, 8, -8, 6, -4, 2));
		assertTrue(Triangle2afp.intersectsTriangleTriangle(5, 8, -10, 1, -1, -2,
				-6, 8, -8, 6, -4, -4));
		assertTrue(Triangle2afp.intersectsTriangleTriangle(5, 8, -10, 1, -1, -2,
				-6, 8, -8, 6, 4, 2));
		assertTrue(Triangle2afp.intersectsTriangleTriangle(5, 8, -10, 1, -1, -2,
				0, 4, -6, 0, 2, -2));
		assertFalse(Triangle2afp.intersectsTriangleTriangle(5, 8, -10, 1, -1, -2,
				-16, 2, -12, -6, -12, 8));
		assertFalse(Triangle2afp.intersectsTriangleTriangle(5, 8, -10, 1, -1, -2,
				-16, 0, -10, 1, -14, 2));
		assertFalse(Triangle2afp.intersectsTriangleTriangle(5, 8, -10, 1, -1, -2,
				-1, -2, -10, 1, -14, 2));
	}

	@Test
	public void staticIntersectsTriangleTriangle_ba() {
		assertFalse(Triangle2afp.intersectsTriangleTriangle(
				-8, 6.5, -4, 6, -7, 11, 5, 8, -10, 1, -1, -2));
		assertFalse(Triangle2afp.intersectsTriangleTriangle(
				-8, -2, -10, -4, -6, -6, 5, 8, -10, 1, -1, -2));
		assertFalse(Triangle2afp.intersectsTriangleTriangle(
				6, 2, 8, -2, 16, 0, 5, 8, -10, 1, -1, -2));
		assertFalse(Triangle2afp.intersectsTriangleTriangle(
				0, -4, -2, -6, 2, -8, 5, 8, -10, 1, -1, -2));
		assertFalse(Triangle2afp.intersectsTriangleTriangle(
				8, 14, 8, 12, 12, 12, 5, 8, -10, 1, -1, -2));
		assertFalse(Triangle2afp.intersectsTriangleTriangle(
				-16, 2, -16, 0, -14, 2, 5, 8, -10, 1, -1, -2));
		assertFalse(Triangle2afp.intersectsTriangleTriangle(
				-16, 2, -12, 6, -12, 8, 5, 8, -10, 1, -1, -2));
		assertTrue(Triangle2afp.intersectsTriangleTriangle(
				-6, 8, -6, 0, -4, 2, 5, 8, -10, 1, -1, -2));
		assertTrue(Triangle2afp.intersectsTriangleTriangle(
				-6, 8, -8, 6, -4, 2, 5, 8, -10, 1, -1, -2));
		assertTrue(Triangle2afp.intersectsTriangleTriangle(
				-6, 8, -8, 6, -4, -4, 5, 8, -10, 1, -1, -2));
		assertTrue(Triangle2afp.intersectsTriangleTriangle(
				-6, 8, -8, 6, 4, 2, 5, 8, -10, 1, -1, -2));
		assertTrue(Triangle2afp.intersectsTriangleTriangle(
				0, 4, -6, 0, 2, -2, 5, 8, -10, 1, -1, -2));
		assertFalse(Triangle2afp.intersectsTriangleTriangle(
				-16, 2, -12, -6, -12, 8, 5, 8, -10, 1, -1, -2));
		assertFalse(Triangle2afp.intersectsTriangleTriangle(
				-16, 0, -10, 1, -14, 2, 5, 8, -10, 1, -1, -2));
		assertFalse(Triangle2afp.intersectsTriangleTriangle(
				-1, -2, -10, 1, -14, 2, 5, 8, -10, 1, -1, -2));
	}

	@Test
	public void staticIntersectsTriangleRectangle() {
		assertFalse(Triangle2afp.intersectsTriangleRectangle(5, 8, -10, 1, -1, -2, -6, -2, 1, 1));
		assertFalse(Triangle2afp.intersectsTriangleRectangle(5, 8, -10, 1, -1, -2, -6, 6, 1, 1));
		assertFalse(Triangle2afp.intersectsTriangleRectangle(5, 8, -10, 1, -1, -2, 6, 6, 1, 1));
		assertFalse(Triangle2afp.intersectsTriangleRectangle(5, 8, -10, 1, -1, -2, -16, 0, 1, 1));
		assertFalse(Triangle2afp.intersectsTriangleRectangle(5, 8, -10, 1, -1, -2, 12, 12, 1, 1));
		assertFalse(Triangle2afp.intersectsTriangleRectangle(5, 8, -10, 1, -1, -2, 0, -6, 1, 1));
		assertTrue(Triangle2afp.intersectsTriangleRectangle(5, 8, -10, 1, -1, -2, -4, 2, 1, 1));
		assertTrue(Triangle2afp.intersectsTriangleRectangle(5, 8, -10, 1, -1, -2, -4, 4, 1, 1));
		assertTrue(Triangle2afp.intersectsTriangleRectangle(5, 8, -10, 1, -1, -2, 0, 6, 1, 1));
		assertTrue(Triangle2afp.intersectsTriangleRectangle(5, 8, -10, 1, -1, -2, 2, 4, 1, 1));
		assertFalse(Triangle2afp.intersectsTriangleRectangle(5, 8, -10, 1, -1, -2, 5, 8, 1, 1));
	}

	@Test
	public void getX1() {
		assertEpsilonEquals(5, this.shape.getX1());
	}

	@Test
	public void getY1() {
		assertEpsilonEquals(8, this.shape.getY1());
	}

	@Test
	public void getX2() {
		assertEpsilonEquals(-10, this.shape.getX2());
	}

	@Test
	public void getY2() {
		assertEpsilonEquals(1, this.shape.getY2());
	}

	@Test
	public void getX3() {
		assertEpsilonEquals(-1, this.shape.getX3());
	}

	@Test
	public void getY3() {
		assertEpsilonEquals(-2, this.shape.getY3());
	}

	@Test
	public void getP1() {
		Point2D p = this.shape.getP1();
		assertNotNull(p);
		assertEpsilonEquals(5, p.getX());
		assertEpsilonEquals(8, p.getY());
	}

	@Test
	public void getP2() {
		Point2D p = this.shape.getP2();
		assertNotNull(p);
		assertEpsilonEquals(-10, p.getX());
		assertEpsilonEquals(1, p.getY());
	}

	@Test
	public void getP3() {
		Point2D p = this.shape.getP3();
		assertNotNull(p);
		assertEpsilonEquals(-1, p.getX());
		assertEpsilonEquals(-2, p.getY());
	}

	@Test
	public void setP1Point2D() {
		this.shape.setP1(createPoint(123.456, 456.789));
		assertEpsilonEquals(123.456, this.shape.getX1());
		assertEpsilonEquals(456.789, this.shape.getY1());
		assertEpsilonEquals(-10, this.shape.getX2());
		assertEpsilonEquals(1, this.shape.getY2());
		assertEpsilonEquals(-1, this.shape.getX3());
		assertEpsilonEquals(-2, this.shape.getY3());
	}

	@Test
	public void setP1DoubleDouble() {
		this.shape.setP1(123.456, 456.789);
		assertEpsilonEquals(123.456, this.shape.getX1());
		assertEpsilonEquals(456.789, this.shape.getY1());
		assertEpsilonEquals(-10, this.shape.getX2());
		assertEpsilonEquals(1, this.shape.getY2());
		assertEpsilonEquals(-1, this.shape.getX3());
		assertEpsilonEquals(-2, this.shape.getY3());
	}

	@Test
	public void setP2Point2D() {
		this.shape.setP2(createPoint(123.456, 456.789));
		assertEpsilonEquals(5, this.shape.getX1());
		assertEpsilonEquals(8, this.shape.getY1());
		assertEpsilonEquals(123.456, this.shape.getX2());
		assertEpsilonEquals(456.789, this.shape.getY2());
		assertEpsilonEquals(-1, this.shape.getX3());
		assertEpsilonEquals(-2, this.shape.getY3());
	}

	@Test
	public void setP2DoubleDouble() {
		this.shape.setP2(123.456, 456.789);
		assertEpsilonEquals(5, this.shape.getX1());
		assertEpsilonEquals(8, this.shape.getY1());
		assertEpsilonEquals(123.456, this.shape.getX2());
		assertEpsilonEquals(456.789, this.shape.getY2());
		assertEpsilonEquals(-1, this.shape.getX3());
		assertEpsilonEquals(-2, this.shape.getY3());
	}

	@Test
	public void setP3Point2D() {
		this.shape.setP3(createPoint(123.456, 456.789));
		assertEpsilonEquals(5, this.shape.getX1());
		assertEpsilonEquals(8, this.shape.getY1());
		assertEpsilonEquals(-10, this.shape.getX2());
		assertEpsilonEquals(1, this.shape.getY2());
		assertEpsilonEquals(123.456, this.shape.getX3());
		assertEpsilonEquals(456.789, this.shape.getY3());
	}

	@Test
	public void setP3DoubleDouble() {
		this.shape.setP3(123.456, 456.789);
		assertEpsilonEquals(5, this.shape.getX1());
		assertEpsilonEquals(8, this.shape.getY1());
		assertEpsilonEquals(-10, this.shape.getX2());
		assertEpsilonEquals(1, this.shape.getY2());
		assertEpsilonEquals(123.456, this.shape.getX3());
		assertEpsilonEquals(456.789, this.shape.getY3());
	}

	@Test
	public void setX1() {
		this.shape.setX1(123.456);
		assertEpsilonEquals(123.456, this.shape.getX1());
		assertEpsilonEquals(8, this.shape.getY1());
		assertEpsilonEquals(-10, this.shape.getX2());
		assertEpsilonEquals(1, this.shape.getY2());
		assertEpsilonEquals(-1, this.shape.getX3());
		assertEpsilonEquals(-2, this.shape.getY3());
	}

	@Test
	public void setY1() {
		this.shape.setY1(123.456);
		assertEpsilonEquals(5, this.shape.getX1());
		assertEpsilonEquals(123.456, this.shape.getY1());
		assertEpsilonEquals(-10, this.shape.getX2());
		assertEpsilonEquals(1, this.shape.getY2());
		assertEpsilonEquals(-1, this.shape.getX3());
		assertEpsilonEquals(-2, this.shape.getY3());
	}

	@Test
	public void setX2() {
		this.shape.setX2(123.456);
		assertEpsilonEquals(5, this.shape.getX1());
		assertEpsilonEquals(8, this.shape.getY1());
		assertEpsilonEquals(123.456, this.shape.getX2());
		assertEpsilonEquals(1, this.shape.getY2());
		assertEpsilonEquals(-1, this.shape.getX3());
		assertEpsilonEquals(-2, this.shape.getY3());
	}

	@Test
	public void setY2() {
		this.shape.setY2(123.456);
		assertEpsilonEquals(5, this.shape.getX1());
		assertEpsilonEquals(8, this.shape.getY1());
		assertEpsilonEquals(-10, this.shape.getX2());
		assertEpsilonEquals(123.456, this.shape.getY2());
		assertEpsilonEquals(-1, this.shape.getX3());
		assertEpsilonEquals(-2, this.shape.getY3());
	}

	@Test
	public void setX3() {
		this.shape.setX3(123.456);
		assertEpsilonEquals(5, this.shape.getX1());
		assertEpsilonEquals(8, this.shape.getY1());
		assertEpsilonEquals(-10, this.shape.getX2());
		assertEpsilonEquals(1, this.shape.getY2());
		assertEpsilonEquals(123.456, this.shape.getX3());
		assertEpsilonEquals(-2, this.shape.getY3());
	}

	@Test
	public void setY3() {
		this.shape.setY3(123.456);
		assertEpsilonEquals(5, this.shape.getX1());
		assertEpsilonEquals(8, this.shape.getY1());
		assertEpsilonEquals(-10, this.shape.getX2());
		assertEpsilonEquals(1, this.shape.getY2());
		assertEpsilonEquals(-1, this.shape.getX3());
		assertEpsilonEquals(123.456, this.shape.getY3());
	}

	@Test
	public void setDoubleDoubleDoubleDoubleDoubleDouble() {
		this.shape.set(1.2, 3.4, 5.6, 7.8, 9.1, 3.2);
		assertEpsilonEquals(1.2, this.shape.getX1());
		assertEpsilonEquals(3.4, this.shape.getY1());
		assertEpsilonEquals(5.6, this.shape.getX2());
		assertEpsilonEquals(7.8, this.shape.getY2());
		assertEpsilonEquals(9.1, this.shape.getX3());
		assertEpsilonEquals(3.2, this.shape.getY3());
	}

	@Test
	public void isCCW() {
		assertTrue(this.shape.isCCW());
		this.shape.set(5, 8, -1, -2, -10, 1);
		assertFalse(this.shape.isCCW());
	}

	@Test
	@Override
	public void testClone() {
		T clone = this.shape.clone();
		assertNotNull(clone);
		assertNotSame(this.shape, clone);
		assertEquals(this.shape.getClass(), clone.getClass());
		assertEpsilonEquals(5, clone.getX1());
		assertEpsilonEquals(8, clone.getY1());
		assertEpsilonEquals(-10, clone.getX2());
		assertEpsilonEquals(1, clone.getY2());
		assertEpsilonEquals(-1, clone.getX3());
		assertEpsilonEquals(-2, clone.getY3());
	}

	@Test
	@Override
	public void equalsObject() {
		assertFalse(this.shape.equals(null));
		assertFalse(this.shape.equals(new Object()));
		assertFalse(this.shape.equals(createTriangle(5, 8, -10, 1, -1, -3)));
		assertFalse(this.shape.equals(createTriangle(-1, -2, 5, 8, -10, 1)));
		assertFalse(this.shape.equals(createSegment(5, 8, 6, 10)));
		assertTrue(this.shape.equals(this.shape));
		assertTrue(this.shape.equals(createTriangle(5, 8, -10, 1, -1, -2)));
	}

	@Test
	@Override
	public void equalsObject_withPathIterator() {
		assertFalse(this.shape.equals(createTriangle(5, 8, -10, 1, -1, -3).getPathIterator()));
		assertFalse(this.shape.equals(createTriangle(-1, -2, 5, 8, -10, 1).getPathIterator()));
		assertFalse(this.shape.equals(createSegment(5, 8, 6, 10).getPathIterator()));
		assertTrue(this.shape.equals(this.shape.getPathIterator()));
		assertTrue(this.shape.equals(createTriangle(5, 8, -10, 1, -1, -2).getPathIterator()));
	}

	@Test
	@Override
	public void equalsToPathIterator() {
		assertFalse(this.shape.equalsToPathIterator((PathIterator2ai) null));
		assertFalse(this.shape.equalsToPathIterator(createTriangle(5, 8, -10, 1, -1, -3).getPathIterator()));
		assertFalse(this.shape.equalsToPathIterator(createTriangle(-1, -2, 5, 8, -10, 1).getPathIterator()));
		assertFalse(this.shape.equalsToPathIterator(createSegment(5, 8, 6, 10).getPathIterator()));
		assertTrue(this.shape.equalsToPathIterator(this.shape.getPathIterator()));
		assertTrue(this.shape.equalsToPathIterator(createTriangle(5, 8, -10, 1, -1, -2).getPathIterator()));
	}

	@Test
	@Override
	public void equalsToShape() {
		assertFalse(this.shape.equalsToShape(null));
		assertFalse(this.shape.equalsToShape((T) createTriangle(5, 8, -10, 1, -1, -3)));
		assertFalse(this.shape.equalsToShape((T) createTriangle(-1, -2, 5, 8, -10, 1)));
		assertTrue(this.shape.equalsToShape(this.shape));
		assertTrue(this.shape.equalsToShape((T) createTriangle(5, 8, -10, 1, -1, -2)));
	}

	@Test
	@Override
	public void isEmpty() {
		assertFalse(this.shape.isEmpty());
		this.shape.clear();
		assertTrue(this.shape.isEmpty());
	}

	@Test
	@Override
	public void clear() {
		this.shape.clear();
		assertEpsilonEquals(0, this.shape.getX1());
		assertEpsilonEquals(0, this.shape.getY1());
		assertEpsilonEquals(0, this.shape.getX2());
		assertEpsilonEquals(0, this.shape.getY2());
		assertEpsilonEquals(0, this.shape.getX3());
		assertEpsilonEquals(0, this.shape.getY3());
	}

	@Test
	@Override
	public void containsDoubleDouble() {
		assertTrue(this.shape.contains(0,0));
		assertFalse(this.shape.contains(11,10));
		assertFalse(this.shape.contains(11,50));
		assertFalse(this.shape.contains(9,12));
		assertFalse(this.shape.contains(9,11));
		assertFalse(this.shape.contains(0,6));
		assertFalse(this.shape.contains(8,12));
		assertTrue(this.shape.contains(3,7));
		assertFalse(this.shape.contains(10,11));
		assertFalse(this.shape.contains(9,10));
		assertTrue(this.shape.contains(-4,2));
		assertFalse(this.shape.contains(-8,-5));
	}

	@Test
	@Override
	public void containsPoint2D() {
		assertTrue(this.shape.contains(createPoint(0,0)));
		assertFalse(this.shape.contains(createPoint(11,10)));
		assertFalse(this.shape.contains(createPoint(11,50)));
		assertFalse(this.shape.contains(createPoint(9,12)));
		assertFalse(this.shape.contains(createPoint(9,11)));
		assertFalse(this.shape.contains(createPoint(0,6)));
		assertFalse(this.shape.contains(createPoint(8,12)));
		assertTrue(this.shape.contains(createPoint(3,7)));
		assertFalse(this.shape.contains(createPoint(10,11)));
		assertFalse(this.shape.contains(createPoint(9,10)));
		assertTrue(this.shape.contains(createPoint(-4,2)));
		assertFalse(this.shape.contains(createPoint(-8,-5)));
	}

	@Test
	@Override
	public void getClosestPointTo() {
		Point2D closest;
		
		closest = this.shape.getClosestPointTo(createPoint(0, 0));
		assertEpsilonEquals(0, closest.getX());
		assertEpsilonEquals(0, closest.getY());
		
		closest = this.shape.getClosestPointTo(createPoint(9, 12));
		assertEpsilonEquals(5, closest.getX());
		assertEpsilonEquals(8, closest.getY());
	
		closest = this.shape.getClosestPointTo(createPoint(0, 6));
		assertEpsilonEquals(0.12774, closest.getX());
		assertEpsilonEquals(5.72628, closest.getY());
		
		closest = this.shape.getClosestPointTo(createPoint(-20, 1));
		assertEpsilonEquals(-10, closest.getX());
		assertEpsilonEquals(1, closest.getY());

		closest = this.shape.getClosestPointTo(createPoint(-6, -1));
		assertEpsilonEquals(-5.8, closest.getX());
		assertEpsilonEquals(-0.4, closest.getY());

		closest = this.shape.getClosestPointTo(createPoint(-1, -6));
		assertEpsilonEquals(-1, closest.getX());
		assertEpsilonEquals(-2, closest.getY());

		closest = this.shape.getClosestPointTo(createPoint(6, 2));
		assertEpsilonEquals(2.61765, closest.getX());
		assertEpsilonEquals(4.02941, closest.getY());
	}

	@Override
	public void getFarthestPointTo() {
		Point2D farthest;
		
		farthest = this.shape.getFarthestPointTo(createPoint(0, 0));
		assertEpsilonEquals(-10, farthest.getX());
		assertEpsilonEquals(1, farthest.getY());
		
		farthest = this.shape.getFarthestPointTo(createPoint(9, 12));
		assertEpsilonEquals(-10, farthest.getX());
		assertEpsilonEquals(1, farthest.getY());
	
		farthest = this.shape.getFarthestPointTo(createPoint(0, 6));
		assertEpsilonEquals(-10, farthest.getX());
		assertEpsilonEquals(1, farthest.getY());
		
		farthest = this.shape.getFarthestPointTo(createPoint(-20, 1));
		assertEpsilonEquals(5, farthest.getX());
		assertEpsilonEquals(8, farthest.getY());

		farthest = this.shape.getFarthestPointTo(createPoint(-6, -1));
		assertEpsilonEquals(5, farthest.getX());
		assertEpsilonEquals(8, farthest.getY());

		farthest = this.shape.getFarthestPointTo(createPoint(-1, -6));
		assertEpsilonEquals(5, farthest.getX());
		assertEpsilonEquals(8, farthest.getY());

		farthest = this.shape.getFarthestPointTo(createPoint(6, 2));
		assertEpsilonEquals(-10, farthest.getX());
		assertEpsilonEquals(1, farthest.getY());
	}

	@Override
	public void getDistance() {
		assertEpsilonEquals(0, this.shape.getDistance(createPoint(0, 0)));
		assertEpsilonEquals(5.65685, this.shape.getDistance(createPoint(9, 12)));
		assertEpsilonEquals(0.30206, this.shape.getDistance(createPoint(0, 6)));
		assertEpsilonEquals(10, this.shape.getDistance(createPoint(-20, 1)));
		assertEpsilonEquals(0.63246, this.shape.getDistance(createPoint(-6, -1)));
		assertEpsilonEquals(4, this.shape.getDistance(createPoint(-1, -6)));
		assertEpsilonEquals(3.94446, this.shape.getDistance(createPoint(6, 2)));
	}

	@Override
	public void getDistanceSquared() {
		assertEpsilonEquals(0, this.shape.getDistanceSquared(createPoint(0, 0)));
		assertEpsilonEquals(32, this.shape.getDistanceSquared(createPoint(9, 12)));
		assertEpsilonEquals(.09124, this.shape.getDistanceSquared(createPoint(0, 6)));
		assertEpsilonEquals(100, this.shape.getDistanceSquared(createPoint(-20, 1)));
		assertEpsilonEquals(0.40001, this.shape.getDistanceSquared(createPoint(-6, -1)));
		assertEpsilonEquals(16, this.shape.getDistanceSquared(createPoint(-1, -6)));
		assertEpsilonEquals(15.55876, this.shape.getDistanceSquared(createPoint(6, 2)));
	}

	@Override
	public void getDistanceL1() {
		assertEpsilonEquals(0, this.shape.getDistanceL1(createPoint(0, 0)));
		assertEpsilonEquals(8, this.shape.getDistanceL1(createPoint(9, 12)));
		assertEpsilonEquals(0.40146, this.shape.getDistanceL1(createPoint(0, 6)));
		assertEpsilonEquals(10, this.shape.getDistanceL1(createPoint(-20, 1)));
		assertEpsilonEquals(.8, this.shape.getDistanceL1(createPoint(-6, -1)));
		assertEpsilonEquals(4, this.shape.getDistanceL1(createPoint(-1, -6)));
		assertEpsilonEquals(5.41176, this.shape.getDistanceL1(createPoint(6, 2)));
	}

	@Override
	public void getDistanceLinf() {
		assertEpsilonEquals(0, this.shape.getDistanceLinf(createPoint(0, 0)));
		assertEpsilonEquals(4, this.shape.getDistanceLinf(createPoint(9, 12)));
		assertEpsilonEquals(.27372, this.shape.getDistanceLinf(createPoint(0, 6)));
		assertEpsilonEquals(10, this.shape.getDistanceLinf(createPoint(-20, 1)));
		assertEpsilonEquals(.6, this.shape.getDistanceLinf(createPoint(-6, -1)));
		assertEpsilonEquals(4, this.shape.getDistanceLinf(createPoint(-1, -6)));
		assertEpsilonEquals(3.38235, this.shape.getDistanceLinf(createPoint(6, 2)));
	}

	@Override
	public void setIT() {
		this.shape.set((T) createTriangle(17, 20, 7, 45, 7, -4));
		assertEpsilonEquals(17, this.shape.getX1());
		assertEpsilonEquals(20, this.shape.getY1());
		assertEpsilonEquals(7, this.shape.getX2());
		assertEpsilonEquals(45, this.shape.getY2());
		assertEpsilonEquals(7, this.shape.getX3());
		assertEpsilonEquals(-4, this.shape.getY3());
	}

	@Override
	public void translateDoubleDouble() {
		this.shape.translate(2, -3);
		assertEpsilonEquals(7, this.shape.getX1());
		assertEpsilonEquals(5, this.shape.getY1());
		assertEpsilonEquals(-8, this.shape.getX2());
		assertEpsilonEquals(-2, this.shape.getY2());
		assertEpsilonEquals(1, this.shape.getX3());
		assertEpsilonEquals(-5, this.shape.getY3());
	}

	@Override
	public void translateVector2D() {
		this.shape.translate(createVector(2, -3));
		assertEpsilonEquals(7, this.shape.getX1());
		assertEpsilonEquals(5, this.shape.getY1());
		assertEpsilonEquals(-8, this.shape.getX2());
		assertEpsilonEquals(-2, this.shape.getY2());
		assertEpsilonEquals(1, this.shape.getX3());
		assertEpsilonEquals(-5, this.shape.getY3());
	}

	@Override
	public void toBoundingBox() {
		B box = this.shape.toBoundingBox();
		assertEpsilonEquals(-10, box.getMinX());
		assertEpsilonEquals(-2, box.getMinY());
		assertEpsilonEquals(5, box.getMaxX());
		assertEpsilonEquals(8, box.getMaxY());
	}

	@Override
	public void toBoundingBoxB() {
		B box = createRectangle(0, 0, 0, 0);
		this.shape.toBoundingBox(box);
		assertEpsilonEquals(-10, box.getMinX());
		assertEpsilonEquals(-2, box.getMinY());
		assertEpsilonEquals(5, box.getMaxX());
		assertEpsilonEquals(8, box.getMaxY());
	}

	@Override
	public void getPathIteratorTransform2D() {
		PathIterator2afp pi = this.shape.getPathIterator(null);
		assertElement(pi, PathElementType.MOVE_TO, 5, 8);
		assertElement(pi, PathElementType.LINE_TO, -10, 1);
		assertElement(pi, PathElementType.LINE_TO, -1, -2);
		assertElement(pi, PathElementType.CLOSE, 5, 8);
		assertNoElement(pi);

		Transform2D tr;
		
		tr = new Transform2D();
		pi = this.shape.getPathIterator(tr);
		assertElement(pi, PathElementType.MOVE_TO, 5, 8);
		assertElement(pi, PathElementType.LINE_TO, -10, 1);
		assertElement(pi, PathElementType.LINE_TO, -1, -2);
		assertElement(pi, PathElementType.CLOSE, 5, 8);
		assertNoElement(pi);

		tr = new Transform2D();
		tr.makeTranslationMatrix(10, -10);
		pi = this.shape.getPathIterator(tr);
		assertElement(pi, PathElementType.MOVE_TO, 15, -2);
		assertElement(pi, PathElementType.LINE_TO, 0, -9);
		assertElement(pi, PathElementType.LINE_TO, 9, -12);
		assertElement(pi, PathElementType.CLOSE, 15, -2);
		assertNoElement(pi);
	}

	@Override
	public void getPathIterator() {
		PathIterator2afp pi = this.shape.getPathIterator();
		assertElement(pi, PathElementType.MOVE_TO, 5, 8);
		assertElement(pi, PathElementType.LINE_TO, -10, 1);
		assertElement(pi, PathElementType.LINE_TO, -1, -2);
		assertElement(pi, PathElementType.CLOSE, 5, 8);
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
		assertTrue(newShape instanceof Triangle2afp);
		PathIterator2afp pi = this.shape.getPathIterator(tr);
		assertElement(pi, PathElementType.MOVE_TO, 15, -2);
		assertElement(pi, PathElementType.LINE_TO, 0, -9);
		assertElement(pi, PathElementType.LINE_TO, 9, -12);
		assertElement(pi, PathElementType.CLOSE, 15, -2);
		assertNoElement(pi);
	}

	@Override
	public void containsRectangle2afp() {
		assertFalse(this.shape.contains(createRectangle(0, 0, 1, 1)));
		assertTrue(this.shape.contains(createRectangle(-2, 1, 1, 1)));
		assertFalse(this.shape.contains(createRectangle(-30, 20, 1, 1)));
		assertFalse(this.shape.contains(createRectangle(-10.5, 0.5, 1, 1)));
	}

	@Override
	public void intersectsRectangle2afp() {
		assertFalse(this.shape.intersects(createRectangle(-6, -2, 1, 1)));
		assertFalse(this.shape.intersects(createRectangle(-6, 6, 1, 1)));
		assertFalse(this.shape.intersects(createRectangle(6, 6, 1, 1)));
		assertFalse(this.shape.intersects(createRectangle(-16, 0, 1, 1)));
		assertFalse(this.shape.intersects(createRectangle(12, 12, 1, 1)));
		assertFalse(this.shape.intersects(createRectangle(0, -6, 1, 1)));
		assertTrue(this.shape.intersects(createRectangle(-4, 2, 1, 1)));
		assertTrue(this.shape.intersects(createRectangle(-4, 4, 1, 1)));
		assertTrue(this.shape.intersects(createRectangle(0, 6, 1, 1)));
		assertTrue(this.shape.intersects(createRectangle(2, 4, 1, 1)));
		assertFalse(this.shape.intersects(createRectangle(5, 8, 1, 1)));
	}

	@Test
	@Override
	public void intersectsTriangle2afp() {
		assertFalse(this.shape.intersects(createTriangle(
				-8, 6.5, -4, 6, -7, 11)));
		assertFalse(this.shape.intersects(createTriangle(
				-8, -2, -10, -4, -6, -6)));
		assertFalse(this.shape.intersects(createTriangle(
				6, 2, 8, -2, 16, 0)));
		assertFalse(this.shape.intersects(createTriangle(
				0, -4, -2, -6, 2, -8)));
		assertFalse(this.shape.intersects(createTriangle(
				8, 14, 8, 12, 12, 12)));
		assertFalse(this.shape.intersects(createTriangle(
				-16, 2, -16, 0, -14, 2)));
		assertFalse(this.shape.intersects(createTriangle(
				-16, 2, -12, 6, -12, 8)));
		assertTrue(this.shape.intersects(createTriangle(
				-6, 8, -6, 0, -4, 2)));
		assertTrue(this.shape.intersects(createTriangle(
				-6, 8, -8, 6, -4, 2)));
		assertTrue(this.shape.intersects(createTriangle(
				-6, 8, -8, 6, -4, -4)));
		assertTrue(this.shape.intersects(createTriangle(
				-6, 8, -8, 6, 4, 2)));
		assertTrue(this.shape.intersects(createTriangle(
				0, 4, -6, 0, 2, -2)));
		assertFalse(this.shape.intersects(createTriangle(
				-16, 2, -12, -6, -12, 8)));
		assertFalse(this.shape.intersects(createTriangle(
				-16, 0, -10, 1, -14, 2)));
		assertFalse(this.shape.intersects(createTriangle(
				-1, -2, -10, 1, -14, 2)));
	}
	
	@Test
	@Override
	public void intersectsOrientedRectangle2afp() {
		OrientedRectangle2afp rectangle = createOrientedRectangle(
				6, 9,
				0.894427190999916, -0.447213595499958, 13.999990000000002,
				12.999989999999997);
		assertFalse(createTriangle(-10, 15, -8, 16, -13, 19).intersects(rectangle));
		assertFalse(createTriangle(-5, 30, -3, 31, -8, 34).intersects(rectangle));
		assertFalse(createTriangle(15, 25, 17, 26, 12, 29).intersects(rectangle));
		assertFalse(createTriangle(40, 15, 42, 16, 37, 19).intersects(rectangle));
		assertFalse(createTriangle(35, 0, 37, 1, 32, 4).intersects(rectangle));
		assertFalse(createTriangle(15, -20, 17, -19, 12, -16).intersects(rectangle));
		assertFalse(createTriangle(-5, -10, -3, -9, -8, -6).intersects(rectangle));
		assertFalse(createTriangle(-25, -5, -23, -4, -28, -1).intersects(rectangle));
		assertTrue(createTriangle(-4, -2, -2, -1, -7, -2).intersects(rectangle));
		assertTrue(createTriangle(-2, 4, 0, 5, -5, 8).intersects(rectangle));
		assertTrue(createTriangle(20, 5, 22, 6, 17, 9).intersects(rectangle));
		assertTrue(createTriangle(20, 5, 22, 6, -10, 15).intersects(rectangle));
		assertTrue(createTriangle(50, 30, 0, -50, -30, 31).intersects(rectangle));

	}

	@Test
	@Override
	public void intersectsParallelogram2afp() {
		Parallelogram2afp para = createParallelogram(6, 9, 2.425356250363330e-01, 9.701425001453320e-01, 9.219544457292887,
				-7.071067811865475e-01, 7.071067811865475e-01, 1.264911064067352e+01);
		assertTrue(createTriangle(-5, 15, -3, 16, -8, 19).intersects(para));
		assertTrue(createTriangle(-5, 15, -8, 19, -3, 16).intersects(para));
		assertFalse(createTriangle(0, -5, 2, -4, -3, -1).intersects(para));
		assertFalse(createTriangle(0, -5, -3, -1, 2, -4).intersects(para));
		assertFalse(createTriangle(20, 0, 22, 1, 17, 4).intersects(para));
		assertFalse(createTriangle(20, 0, 17, 4, 22, 1).intersects(para));
		assertFalse(createTriangle(17.18034, 9, 19.18034, 10, 14.18034, 13).intersects(para));
		assertFalse(createTriangle(17.18034, 9, 14.18034, 13, 19.18034, 10).intersects(para));
		assertTrue(createTriangle(0, 10, 2, 11, -3, 14).intersects(para));
		assertTrue(createTriangle(0, 10, -3, 14, 2, 11).intersects(para));
		assertTrue(createTriangle(0, 20, 2, 21, -3, 24).intersects(para));
	}
	
	@Test
	@Override
	public void intersectsRoundRectangle2afp() {
		assertTrue(this.shape.intersects(createRoundRectangle(0, 0, 1, 1, .2, .4)));
		assertTrue(this.shape.intersects(createRoundRectangle(0, 2, 1, 1, .2, .4)));
		assertTrue(this.shape.intersects(createRoundRectangle(0, 3, 1, 1, .2, .4)));
		assertTrue(this.shape.intersects(createRoundRectangle(0, 4, 1, 1, .2, .4)));
		assertTrue(this.shape.intersects(createRoundRectangle(0, 5, 1, 1, .2, .4)));
		assertTrue(this.shape.intersects(createRoundRectangle(0, 6, 1, 1, .2, .4)));
		assertTrue(this.shape.intersects(createRoundRectangle(0, 6.05, 1, 1, .2, .4)));
		assertFalse(this.shape.intersects(createRoundRectangle(0, 6.06, 1, 1, .2, .4)));
		assertFalse(this.shape.intersects(createRoundRectangle(4.5, 8, 1, 1, .2, .4)));
	}

	@Override
	public void intersectsCircle2afp() {
		assertTrue(this.shape.intersects(createCircle(5, 8, 1)));
		assertTrue(this.shape.intersects(createCircle(-10, 1, 1)));
		assertTrue(this.shape.intersects(createCircle(-1, -2, 1)));
		
		assertFalse(this.shape.intersects(createCircle(2, 0, 1)));
		assertFalse(this.shape.intersects(createCircle(1.9, 0, 1)));
		assertFalse(this.shape.intersects(createCircle(1.8, 0, 1)));
		assertFalse(this.shape.intersects(createCircle(1.7, 0, 1)));
		assertFalse(this.shape.intersects(createCircle(1.6, 0, 1)));
		assertFalse(this.shape.intersects(createCircle(1.5, 0, 1)));
		assertFalse(this.shape.intersects(createCircle(1.4, 0, 1)));
		assertTrue(this.shape.intersects(createCircle(1.3, 0, 1)));

		assertFalse(this.shape.intersects(createCircle(5, 9, 1)));
		assertTrue(this.shape.intersects(createCircle(5, 8.9, 1)));

		assertTrue(this.shape.intersects(createCircle(-4, 1, 1)));
	}

	@Override
	public void intersectsEllipse2afp() {
		assertFalse(this.shape.intersects(createEllipse(5, 8, 2, 1)));
		assertTrue(this.shape.intersects(createEllipse(-10, 1, 2, 1)));
		assertTrue(this.shape.intersects(createEllipse(-1, -2, 2, 1)));
		
		assertFalse(this.shape.intersects(createEllipse(1, 0, 2, 1)));
		assertFalse(this.shape.intersects(createEllipse(0.9, 0, 2, 1)));
		assertFalse(this.shape.intersects(createEllipse(0.8, 0, 2, 1)));
		assertFalse(this.shape.intersects(createEllipse(0.7, 0, 2, 1)));
		assertFalse(this.shape.intersects(createEllipse(0.6, 0, 2, 1)));
		assertTrue(this.shape.intersects(createEllipse(0.5, 0, 2, 1)));

		assertFalse(this.shape.intersects(createEllipse(-1.12464, -2.86312, 2, 1)));
	}

	@Override
	public void intersectsSegment2afp() {
		assertTrue(this.shape.intersects(createSegment(-6, -2, -4, 0)));
		assertTrue(this.shape.intersects(createSegment(-6, -2, 2, 0)));
		assertFalse(this.shape.intersects(createSegment(-6, -2, 14, -4)));
		assertTrue(this.shape.intersects(createSegment(-2, 2, 4, 0)));
		assertTrue(this.shape.intersects(createSegment(-2, 2, 0, 0)));
		assertTrue(this.shape.intersects(createSegment(-4, -2, -6, 6)));
		assertTrue(this.shape.intersects(createSegment(6, 7, -6, 6)));
		assertTrue(this.shape.intersects(createSegment(0, 5, -6, 6)));
		assertFalse(this.shape.intersects(createSegment(-5, 5, -6, 6)));

		assertFalse(this.shape.intersects(createSegment(-4, -2, 2, -2)));
		assertFalse(this.shape.intersects(createSegment(-1, -2, 5, 8)));
	}

	@Override
	public void intersectsPath2afp() {
		// NON_ZERO
		
		Path2afp path = createPath(PathWindingRule.NON_ZERO);
		path.moveTo(-8, 0);
		path.lineTo(-2, -2);
		path.lineTo(2, -2);
		path.lineTo(2, 2);
		assertFalse(this.shape.intersects(path));
		path.closePath();
		assertTrue(this.shape.intersects(path));

		path = createPath(PathWindingRule.NON_ZERO);
		path.moveTo(-8, 0);
		path.lineTo(-2, -2);
		path.lineTo(2, -2);
		path.lineTo(0, 8);
		assertTrue(this.shape.intersects(path));
		path.closePath();
		assertTrue(this.shape.intersects(path));

		path = createPath(PathWindingRule.NON_ZERO);
		path.moveTo(-18, 2);
		path.lineTo(-2, -2);
		path.lineTo(2, -2);
		path.lineTo(6, 10);
		assertFalse(this.shape.intersects((PathIterator2afp) path.getPathIterator()));
		path.closePath();
		assertTrue(this.shape.intersects((PathIterator2afp) path.getPathIterator()));

		// EVEN_ODD
		
		path = createPath(PathWindingRule.EVEN_ODD);
		path.moveTo(-8, 0);
		path.lineTo(-2, -2);
		path.lineTo(2, -2);
		path.lineTo(2, 2);
		assertFalse(this.shape.intersects(path));
		path.closePath();
		assertTrue(this.shape.intersects(path));

		path = createPath(PathWindingRule.EVEN_ODD);
		path.moveTo(-8, 0);
		path.lineTo(-2, -2);
		path.lineTo(2, -2);
		path.lineTo(0, 8);
		assertTrue(this.shape.intersects(path));
		path.closePath();
		assertTrue(this.shape.intersects(path));

		path = createPath(PathWindingRule.EVEN_ODD);
		path.moveTo(-18, 2);
		path.lineTo(-2, -2);
		path.lineTo(2, -2);
		path.lineTo(6, 10);
		assertFalse(this.shape.intersects(path));
		path.closePath();
		assertTrue(this.shape.intersects(path));
	}

	@Override
	public void intersectsPathIterator2afp() {
		// NON_ZERO
		
		Path2afp path = createPath(PathWindingRule.NON_ZERO);
		path.moveTo(-8, 0);
		path.lineTo(-2, -2);
		path.lineTo(2, -2);
		path.lineTo(2, 2);
		assertFalse(this.shape.intersects((PathIterator2afp) path.getPathIterator()));
		path.closePath();
		assertTrue(this.shape.intersects((PathIterator2afp) path.getPathIterator()));

		path = createPath(PathWindingRule.NON_ZERO);
		path.moveTo(-8, 0);
		path.lineTo(-2, -2);
		path.lineTo(2, -2);
		path.lineTo(0, 8);
		assertTrue(this.shape.intersects((PathIterator2afp) path.getPathIterator()));
		path.closePath();
		assertTrue(this.shape.intersects((PathIterator2afp) path.getPathIterator()));

		path = createPath(PathWindingRule.NON_ZERO);
		path.moveTo(-18, 2);
		path.lineTo(-2, -2);
		path.lineTo(2, -2);
		path.lineTo(6, 10);
		assertFalse(this.shape.intersects((PathIterator2afp) path.getPathIterator()));
		path.closePath();
		assertTrue(this.shape.intersects((PathIterator2afp) path.getPathIterator()));

		// EVEN_ODD
		
		path = createPath(PathWindingRule.EVEN_ODD);
		path.moveTo(-8, 0);
		path.lineTo(-2, -2);
		path.lineTo(2, -2);
		path.lineTo(2, 2);
		assertFalse(this.shape.intersects((PathIterator2afp) path.getPathIterator()));
		path.closePath();
		assertTrue(this.shape.intersects((PathIterator2afp) path.getPathIterator()));

		path = createPath(PathWindingRule.EVEN_ODD);
		path.moveTo(-8, 0);
		path.lineTo(-2, -2);
		path.lineTo(2, -2);
		path.lineTo(0, 8);
		assertTrue(this.shape.intersects((PathIterator2afp) path.getPathIterator()));
		path.closePath();
		assertTrue(this.shape.intersects((PathIterator2afp) path.getPathIterator()));

		path = createPath(PathWindingRule.EVEN_ODD);
		path.moveTo(-18, 2);
		path.lineTo(-2, -2);
		path.lineTo(2, -2);
		path.lineTo(6, 10);
		assertFalse(this.shape.intersects((PathIterator2afp) path.getPathIterator()));
		path.closePath();
		assertTrue(this.shape.intersects((PathIterator2afp) path.getPathIterator()));
	}

	@Override
	public void intersectsShape2D() {
		assertTrue(this.shape.intersects((Shape2D) createCircle(5, 8, 1)));
		assertTrue(this.shape.intersects((Shape2D) createEllipse(-10, 1, 2, 1)));
	}

	@Override
	public void operator_addVector2D() {
		this.shape.operator_add(createVector(2, -3));
		assertEpsilonEquals(7, this.shape.getX1());
		assertEpsilonEquals(5, this.shape.getY1());
		assertEpsilonEquals(-8, this.shape.getX2());
		assertEpsilonEquals(-2, this.shape.getY2());
		assertEpsilonEquals(1, this.shape.getX3());
		assertEpsilonEquals(-5, this.shape.getY3());
	}

	@Override
	public void operator_plusVector2D() {
		T shape = this.shape.operator_plus(createVector(2, -3));
		assertEpsilonEquals(7, shape.getX1());
		assertEpsilonEquals(5, shape.getY1());
		assertEpsilonEquals(-8, shape.getX2());
		assertEpsilonEquals(-2, shape.getY2());
		assertEpsilonEquals(1, shape.getX3());
		assertEpsilonEquals(-5, shape.getY3());
	}

	@Override
	public void operator_removeVector2D() {
		this.shape.operator_remove(createVector(2, -3));
		assertEpsilonEquals(3, this.shape.getX1());
		assertEpsilonEquals(11, this.shape.getY1());
		assertEpsilonEquals(-12, this.shape.getX2());
		assertEpsilonEquals(4, this.shape.getY2());
		assertEpsilonEquals(-3, this.shape.getX3());
		assertEpsilonEquals(1, this.shape.getY3());
	}

	@Override
	public void operator_minusVector2D() {
		T shape = this.shape.operator_minus(createVector(2, -3));
		assertEpsilonEquals(3, shape.getX1());
		assertEpsilonEquals(11, shape.getY1());
		assertEpsilonEquals(-12, shape.getX2());
		assertEpsilonEquals(4, shape.getY2());
		assertEpsilonEquals(-3, shape.getX3());
		assertEpsilonEquals(1, shape.getY3());
	}

	@Override
	public void operator_multiplyTransform2D() {
		Transform2D tr;
		Shape2afp newShape;
		
		newShape = this.shape.operator_multiply(null);
		assertNotNull(newShape);
		assertNotSame(this.shape, newShape);
		assertEquals(this.shape, newShape);

		tr = new Transform2D();
		newShape = this.shape.operator_multiply(tr);
		assertNotNull(newShape);
		assertNotSame(this.shape, newShape);
		assertEquals(this.shape, newShape);

		tr = new Transform2D();
		tr.makeTranslationMatrix(10, -10);
		newShape = this.shape.operator_multiply(tr);
		assertNotNull(newShape);
		assertNotSame(this.shape, newShape);
		assertTrue(newShape instanceof Triangle2afp);
		PathIterator2afp pi = this.shape.getPathIterator(tr);
		assertElement(pi, PathElementType.MOVE_TO, 15, -2);
		assertElement(pi, PathElementType.LINE_TO, 0, -9);
		assertElement(pi, PathElementType.LINE_TO, 9, -12);
		assertElement(pi, PathElementType.CLOSE, 15, -2);
		assertNoElement(pi);
	}

	@Override
	public void operator_andPoint2D() {
		assertTrue(this.shape.operator_and(createPoint(0,0)));
		assertFalse(this.shape.operator_and(createPoint(11,10)));
		assertFalse(this.shape.operator_and(createPoint(11,50)));
		assertFalse(this.shape.operator_and(createPoint(9,12)));
		assertFalse(this.shape.operator_and(createPoint(9,11)));
		assertFalse(this.shape.operator_and(createPoint(0,6)));
		assertFalse(this.shape.operator_and(createPoint(8,12)));
		assertTrue(this.shape.operator_and(createPoint(3,7)));
		assertFalse(this.shape.operator_and(createPoint(10,11)));
		assertFalse(this.shape.operator_and(createPoint(9,10)));
		assertTrue(this.shape.operator_and(createPoint(-4,2)));
		assertFalse(this.shape.operator_and(createPoint(-8,-5)));
	}

	@Override
	public void operator_andShape2D() {
		assertTrue(this.shape.operator_and(createCircle(5, 8, 1)));
		assertTrue(this.shape.operator_and(createEllipse(-10, 1, 2, 1)));
	}

	@Override
	public void operator_upToPoint2D() {
		assertEpsilonEquals(0, this.shape.operator_upTo(createPoint(0, 0)));
		assertEpsilonEquals(5.65685, this.shape.operator_upTo(createPoint(9, 12)));
		assertEpsilonEquals(0.30206, this.shape.operator_upTo(createPoint(0, 6)));
		assertEpsilonEquals(10, this.shape.operator_upTo(createPoint(-20, 1)));
		assertEpsilonEquals(0.63246, this.shape.operator_upTo(createPoint(-6, -1)));
		assertEpsilonEquals(4, this.shape.operator_upTo(createPoint(-1, -6)));
		assertEpsilonEquals(3.94446, this.shape.operator_upTo(createPoint(6, 2)));
	}

}