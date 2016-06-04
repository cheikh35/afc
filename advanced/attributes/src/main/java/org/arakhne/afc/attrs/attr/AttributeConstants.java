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

package org.arakhne.afc.attrs.attr;

import org.arakhne.afc.vmutil.URISchemeType;

/**
 * This interface contains several constant names.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public final class AttributeConstants {

	/** Default URI and URL scheme used to build URLs and URIs.
	 */
	public static final URISchemeType DEFAULT_SCHEME = URISchemeType.HTTP;

	/** String constant for the <code>true</code> boolean constant.
	 */
	public static final String TRUE_CONSTANT = "true"; 

	/** String constant for the <code>true</code> boolean constant.
	 */
	public static final String YES_CONSTANT = "yes"; 

	/** String constant for the <code>true</code> boolean constant.
	 */
	public static final String OUI_CONSTANT = "oui"; 

	/** String constant for the <code>true</code> boolean constant.
	 */
	public static final String T_CONSTANT = "t"; 

	/** String constant for the <code>true</code> boolean constant.
	 */
	public static final String O_CONSTANT = "o"; 

	/** String constant for the <code>true</code> boolean constant.
	 */
	public static final String Y_CONSTANT = "y"; 

	/** String constant for the <code>false</code> boolean constant.
	 */
	public static final String FALSE_CONSTANT = "false"; 

	/** String constant for the <code>true</code> boolean constant.
	 */
	public static final String NO_CONSTANT = "no"; 

	/** String constant for the <code>true</code> boolean constant.
	 */
	public static final String NON_CONSTANT = "non"; 

	/** String constant for the <code>true</code> boolean constant.
	 */
	public static final String F_CONSTANT = "f"; 

	/** String constant for the <code>true</code> boolean constant.
	 */
	public static final String N_CONSTANT = "n"; 

	/** Attribute "name".
	 */
	public static final String ATTR_NAME = "name"; 

	/** Attirbute "id".
	 */
	public static final String ATTR_IDENTIFIER = "id"; 

	private AttributeConstants() {
		//
	}

}
