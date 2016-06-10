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

package org.arakhne.afc.inputoutput.filefilter;

import org.arakhne.afc.vmutil.locale.Locale;

/** File filter for the Graphviz DOT files.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class DOTFileFilter extends AbstractFileFilter {

	/** Default extension for the Graphviz DOT files.
	 */
	public static final String EXTENSION_DOT = "dot"; //$NON-NLS-1$

	/** Default extension for the Graphviz DOT files.
	 *
	 * @deprecated since 13.0, see {@link #EXTENSION_DOT}
	 */
	@Deprecated
	public static final String EXTENSION = EXTENSION_DOT;

	/** Construct.
	 */
	public DOTFileFilter() {
		this(true);
	}

	/**
	 * @param acceptDirectories is <code>true</code> to
	 *     permit to this file filter to accept directories;
	 *     <code>false</code> if the directories should not
	 *     match.
	 */
	public DOTFileFilter(boolean acceptDirectories) {
		super(
				acceptDirectories,
				Locale.getString(DOTFileFilter.class, "FILE_FILTER_NAME"), //$NON-NLS-1$
				EXTENSION_DOT);
	}

}
