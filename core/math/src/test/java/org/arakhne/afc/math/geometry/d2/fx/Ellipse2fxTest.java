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
package org.arakhne.afc.math.geometry.d2.fx;

import static org.junit.Assert.assertNotNull;

import org.arakhne.afc.math.geometry.d2.afp.AbstractEllipse2afpTest;
import org.arakhne.afc.math.geometry.d2.afp.TestShapeFactory;
import org.arakhne.afc.math.geometry.d2.fx.Ellipse2fx;
import org.arakhne.afc.math.geometry.d2.fx.Rectangle2fx;
import org.junit.Test;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;

@SuppressWarnings("all")
public class Ellipse2fxTest extends AbstractEllipse2afpTest<Ellipse2fx, Rectangle2fx> {

	@Override
	protected TestShapeFactory2fx createFactory() {
		return TestShapeFactory2fx.SINGLETON;
	}
	
	@Test
	public void minXProperty() {
		assertEpsilonEquals(5, this.shape.getMinX());
		
		DoubleProperty property = this.shape.minXProperty();
		assertNotNull(property);
		assertEpsilonEquals(5, property.get());
		
		this.shape.setMinX(456.159);
		assertEpsilonEquals(456.159, property.get());
		
		assertEpsilonEquals(456.159, this.shape.getMaxX());
	}

	@Test
	public void maxXProperty() {
		assertEpsilonEquals(10, this.shape.getMaxX());
		
		DoubleProperty property = this.shape.maxXProperty();
		assertNotNull(property);
		assertEpsilonEquals(10, property.get());
		
		this.shape.setMaxX(456.159);
		assertEpsilonEquals(456.159, property.get());
		
		assertEpsilonEquals(5, this.shape.getMinX());
	}

	@Test
	public void minYProperty() {
		assertEpsilonEquals(8, this.shape.getMinY());
		
		DoubleProperty property = this.shape.minYProperty();
		assertNotNull(property);
		assertEpsilonEquals(8, property.get());
		
		this.shape.setMinY(456.159);
		assertEpsilonEquals(456.159, property.get());
		
		assertEpsilonEquals(456.159, this.shape.getMaxY());
	}

	@Test
	public void maxYProperty() {
		assertEpsilonEquals(18, this.shape.getMaxY());
		
		DoubleProperty property = this.shape.maxYProperty();
		assertNotNull(property);
		assertEpsilonEquals(18, property.get());
		
		this.shape.setMaxY(456.159);
		assertEpsilonEquals(456.159, property.get());
		
		assertEpsilonEquals(8, this.shape.getMinY());
	}

	@Test
	public void widthProperty() {
		assertEpsilonEquals(5, this.shape.getWidth());

		ReadOnlyDoubleProperty property = this.shape.widthProperty();
		assertNotNull(property);
		assertEpsilonEquals(5, property.get());
		
		this.shape.setMinX(7);
		assertEpsilonEquals(3, property.get());

		this.shape.setMinX(-5);
		assertEpsilonEquals(15, property.get());

		this.shape.setMaxX(0);
		assertEpsilonEquals(5, property.get());
	}

	@Test
	public void heightProperty() {
		assertEpsilonEquals(10, this.shape.getHeight());

		ReadOnlyDoubleProperty property = this.shape.heightProperty();
		assertNotNull(property);
		assertEpsilonEquals(10, property.get());
		
		this.shape.setMinY(9);
		assertEpsilonEquals(9, property.get());

		this.shape.setMinY(-5);
		assertEpsilonEquals(23, property.get());

		this.shape.setMaxY(0);
		assertEpsilonEquals(5, property.get());
	}

	@Test
	public void boundingBoxProperty() {
		ObjectProperty<Rectangle2fx> property = this.shape.boundingBoxProperty();
		assertNotNull(property);
		Rectangle2fx box = property.get();
		assertNotNull(box);
		assertEpsilonEquals(5, box.getMinX());
		assertEpsilonEquals(8, box.getMinY());
		assertEpsilonEquals(10, box.getMaxX());
		assertEpsilonEquals(18, box.getMaxY());
	}

}