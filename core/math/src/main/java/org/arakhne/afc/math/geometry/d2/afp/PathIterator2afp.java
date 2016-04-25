/* 
 * $Id$
 * 
 * Copyright (C) 2005-09 Stephane GALLAND.
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

import org.arakhne.afc.math.geometry.d2.PathIterator2D;


/** This interface describes an iterator on path elements.
 *
 * @param <T> the types of the path elements.
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 * @since 13.0
 */
public interface PathIterator2afp<T extends PathElement2afp> extends PathIterator2D<T>, Cloneable {

	/** Replies the factory of geometrical elements.
	 *
	 * @return the factory.
	 */
	GeomFactory2afp<T, ?, ?> getGeomFactory();
	
	/** Replies a reset instance of this iterator.
	 *
	 * <p>The reset instance enables to restart iterations with the replied iterator.
	 *
	 * @return the reset iterator.
	 */
	PathIterator2afp<T> restartIterations();

}