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

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.eclipse.xtext.xbase.lib.Pure;

import org.arakhne.afc.math.geometry.d2.MathFXAttributeNames;
import org.arakhne.afc.math.geometry.d2.Point2D;
import org.arakhne.afc.math.geometry.d2.afp.Circle2afp;
import org.arakhne.afc.vmutil.asserts.AssertMessages;

/** Circle with 2 double precision floating-point FX properties.
 *
 * @author $Author: sgalland$
 * @author $Author: hjaffali$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 * @since 13.0
 */
public class Circle2dfx
		extends AbstractShape2dfx<Circle2dfx>
		implements Circle2afp<Shape2dfx<?>, Circle2dfx, PathElement2dfx, Point2dfx, Vector2dfx, Rectangle2dfx> {

	private static final long serialVersionUID = 837592010117981823L;

	private DoubleProperty centerX;

	private DoubleProperty centerY;

	private DoubleProperty radius;

	/** Construct an empty circle.
	 */
	public Circle2dfx() {
		//
	}

	/** Construct a circle with the given position and radius.
	 * @param center the center of the circle.
	 * @param radius the radius of the circle.
	 */
	public Circle2dfx(Point2D<?, ?> center, double radius) {
		assert center != null : AssertMessages.notNullParameter(0);
		set(center.getX(), center.getY(), radius);
	}

	/** Construct a circle with the given position and radius.
	 * @param x x coordinate of the center of the circle.
	 * @param y y coordinate of the center of the circle.
	 * @param radius the radius of the circle.
	 */
	public Circle2dfx(double x, double y, double radius) {
		set(x, y, radius);
	}

	/** Construct a circle from a circle.
	 * @param circle the circle to copy.
	 */
	public Circle2dfx(Circle2afp<?, ?, ?, ?, ?, ?> circle) {
		assert circle != null : AssertMessages.notNullParameter();
		set(circle.getX(), circle.getY(), circle.getRadius());
	}

	@Pure
	@Override
	public Circle2dfx clone() {
		final Circle2dfx clone = super.clone();
		if (clone.centerX != null) {
			clone.centerX = null;
			clone.xProperty().set(getX());
		}
		if (clone.centerY != null) {
			clone.centerY = null;
			clone.yProperty().set(getY());
		}
		if (clone.radius != null) {
			clone.radius = null;
			clone.radiusProperty().set(getRadius());
		}
		return clone;
	}

	@Pure
	@Override
	public int hashCode() {
		long bits = 1;
		bits = 31 * bits + Double.hashCode(getX());
		bits = 31 * bits + Double.hashCode(getY());
		bits = 31 * bits + Double.hashCode(getRadius());
		final int b = (int) bits;
		return b ^ (b >> 31);
	}

	@Pure
	@Override
	public String toString() {
		final StringBuilder b = new StringBuilder();
		b.append("center: ("); //$NON-NLS-1$
		b.append(getX());
		b.append(", "); //$NON-NLS-1$
		b.append(getY());
		b.append(")\nradius: "); //$NON-NLS-1$
		b.append(getRadius());
		return b.toString();
	}

	@Pure
	@Override
	public double getX() {
		return this.centerX == null ? 0 : this.centerX.get();
	}

	@Pure
	@Override
	public double getY() {
		return this.centerY == null ? 0 : this.centerY.get();
	}

	@Pure
	@Override
	public Point2dfx getCenter() {
		return getGeomFactory().newPoint(xProperty(), yProperty());
	}

	@Override
	public void setX(double x) {
		xProperty().set(x);
	}

	@Override
	public void setY(double y) {
		yProperty().set(y);
	}

	/** Replies the property that is the x coordinate of the circle center.
	 *
	 * @return the x property.
	 */
	@Pure
	public DoubleProperty xProperty() {
		if (this.centerX == null) {
			this.centerX = new SimpleDoubleProperty(this, MathFXAttributeNames.X);
		}
		return this.centerX;
	}

	/** Replies the property that is the y coordinate of the circle center.
	 *
	 * @return the y property.
	 */
	@Pure
	public DoubleProperty yProperty() {
		if (this.centerY == null) {
			this.centerY = new SimpleDoubleProperty(this, MathFXAttributeNames.Y);
		}
		return this.centerY;
	}

	@Pure
	@Override
	public double getRadius() {
		return this.radius == null ? 0 : this.radius.get();
	}

	@Override
	public void setRadius(double radius) {
		assert radius >= 0 : AssertMessages.positiveOrZeroParameter();
		radiusProperty().set(radius);
	}

	/** Replies the property that is the radius of the circle.
	 *
	 * @return the radius property.
	 */
	@Pure
	public DoubleProperty radiusProperty() {
		if (this.radius == null) {
			this.radius = new SimpleDoubleProperty(this, MathFXAttributeNames.RADIUS) {
				@Override
				protected void invalidated() {
					if (get() < 0.) {
						set(0.);
					}
				}
			};
		}
		return this.radius;
	}

	@Override
	public void set(double x, double y, double radius) {
		assert radius >= 0 : AssertMessages.positiveOrZeroParameter(2);
		xProperty().set(x);
		yProperty().set(y);
		radiusProperty().set(radius);
	}

	@Override
	public ObjectProperty<Rectangle2dfx> boundingBoxProperty() {
		if (this.boundingBox == null) {
			this.boundingBox = new SimpleObjectProperty<>(this, MathFXAttributeNames.BOUNDING_BOX);
			this.boundingBox.bind(Bindings.createObjectBinding(() -> {
				return toBoundingBox();
			},
					xProperty(), yProperty(), radiusProperty()));
		}
		return this.boundingBox;
	}

}
