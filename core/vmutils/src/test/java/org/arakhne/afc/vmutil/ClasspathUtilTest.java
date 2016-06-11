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

package org.arakhne.afc.vmutil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Ignore;
import org.junit.Test;

@SuppressWarnings("all")
public class ClasspathUtilTest {

	@Test
	@Ignore
	public void getStartClasspath() {
		Iterator<URL> urls = ClasspathUtil.getStartClasspath();
		assertNotNull(urls);
		
		String[] paths = System.getProperty("java.class.path").split( 
				Pattern.quote(File.pathSeparator));
		
		for(int i=0; i<paths.length; i++) {
			URL u = FileSystem.convertStringToURL(paths[i], true);
			assertTrue(urls.hasNext());
			assertEquals(u, urls.next());
		}
	}
	
	@Test
	@Ignore
	public void getCurrentClasspath_standardClassLoader() {
		Iterator<URL> urls = ClasspathUtil.getClasspath();
		assertNotNull(urls);
		
		String[] paths = System.getProperty("java.class.path").split( 
				Pattern.quote(File.pathSeparator));
		List<String> list = new ArrayList<>(Arrays.asList(paths));

		while (urls.hasNext()) {
			URL u2 = urls.next();
			assertEquals(list, u2);
		}
	}

}
