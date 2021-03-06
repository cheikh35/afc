/*
 * $Id$
 * This file is a part of the Arakhne Foundation Classes, http://www.arakhne.org/afc
 *
 * Copyright (c) 2000-2012 Stephane GALLAND.
 * Copyright (c) 2005-10, Multiagent Team, Laboratoire Systemes et Transports,
 *                        Universite de Technologie de Belfort-Montbeliard.
 * Copyright (c) 2013-2016 The original authors, and other authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.arakhne.afc.math.geometry.d2.dfx;

import static org.junit.Assert.assertNotNull;

import org.arakhne.afc.math.geometry.d2.afp.AbstractTriangle2afpTest;
import org.arakhne.afc.math.geometry.d2.afp.TestShapeFactory;
import org.arakhne.afc.math.geometry.d2.dfx.Rectangle2dfx;
import org.arakhne.afc.math.geometry.d2.dfx.Triangle2dfx;
import org.junit.Test;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;

@SuppressWarnings("all")
public class Triangle2dfxTest extends AbstractTriangle2afpTest<Triangle2dfx, Rectangle2dfx> {

	@Override
	protected TestShapeFactory2dfx createFactory() {
		return TestShapeFactory2dfx.SINGLETON;
	}

	@Test
	public void x1Property() {
		assertEpsilonEquals(5, this.shape.getX1());
		
		DoubleProperty property = this.shape.x1Property();
		assertNotNull(property);
		assertEpsilonEquals(5, property.get());
		
		this.shape.setX1(456.159);
		assertEpsilonEquals(456.159, property.get());
		
		assertEpsilonEquals(456.159, this.shape.getX1());
	}

	@Test
	public void y1Property() {
		assertEpsilonEquals(8, this.shape.getY1());
		
		DoubleProperty property = this.shape.y1Property();
		assertNotNull(property);
		assertEpsilonEquals(8, property.get());
		
		this.shape.setY1(456.159);
		assertEpsilonEquals(456.159, property.get());
		
		assertEpsilonEquals(456.159, this.shape.getY1());
	}

	@Test
	public void x2Property() {
		assertEpsilonEquals(-10, this.shape.getX2());
		
		DoubleProperty property = this.shape.x2Property();
		assertNotNull(property);
		assertEpsilonEquals(-10, property.get());
		
		this.shape.setX2(456.159);
		assertEpsilonEquals(456.159, property.get());
		
		assertEpsilonEquals(456.159, this.shape.getX2());
	}

	@Test
	public void y2Property() {
		assertEpsilonEquals(1, this.shape.getY2());
		
		DoubleProperty property = this.shape.y2Property();
		assertNotNull(property);
		assertEpsilonEquals(1, property.get());
		
		this.shape.setY2(456.159);
		assertEpsilonEquals(456.159, property.get());
		
		assertEpsilonEquals(456.159, this.shape.getY2());
	}

	@Test
	public void x3Property() {
		assertEpsilonEquals(-1, this.shape.getX3());
		
		DoubleProperty property = this.shape.x3Property();
		assertNotNull(property);
		assertEpsilonEquals(-1, property.get());
		
		this.shape.setX3(456.159);
		assertEpsilonEquals(456.159, property.get());
		
		assertEpsilonEquals(456.159, this.shape.getX3());
	}

	@Test
	public void y3Property() {
		assertEpsilonEquals(-2, this.shape.getY3());
		
		DoubleProperty property = this.shape.y3Property();
		assertNotNull(property);
		assertEpsilonEquals(-2, property.get());
		
		this.shape.setY3(456.159);
		assertEpsilonEquals(456.159, property.get());
		
		assertEpsilonEquals(456.159, this.shape.getY3());
	}

	@Test
	public void boundingBoxProperty() {
		ObjectProperty<Rectangle2dfx> property = this.shape.boundingBoxProperty();
		assertNotNull(property);
		Rectangle2dfx box = property.get();
		assertNotNull(box);
		assertEpsilonEquals(-10, box.getMinX());
		assertEpsilonEquals(-2, box.getMinY());
		assertEpsilonEquals(5, box.getMaxX());
		assertEpsilonEquals(8, box.getMaxY());
	}

}