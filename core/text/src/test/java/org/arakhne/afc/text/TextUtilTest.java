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

package org.arakhne.afc.text;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import org.arakhne.afc.testtools.AbstractTestCase;
import org.arakhne.afc.vmutil.locale.Locale;

import static org.arakhne.afc.testtools.XbaseInlineTestUtil.*;

@SuppressWarnings("all")
public class TextUtilTest extends AbstractTestCase {

	@Test
	public void cutStringAsArray() throws Exception {
		String src;
		String[] res;
		String[] actual;
		
		src = Locale.getString("A_SOURCE"); 
		res = Locale.getString("A_RESULT").split("\n");  
		actual = TextUtil.cutStringAsArray(src, 80);
		assertNotNull(actual);
		Assert.assertEquals(res.length, actual.length);
		for(int i=0; i<res.length; i++) {
			assertTrue("A:Line Size "+(i+1)+": "+actual[i]+" = "+actual[i].length(), actual[i].length()<=80);   
			Assert.assertEquals("A:Line "+(i+1), res[i], actual[i]); 
		}

		src = Locale.getString("B_SOURCE"); 
		res = Locale.getString("B_RESULT").split("\n");  
		actual = TextUtil.cutStringAsArray(src, 80);
		assertNotNull(actual);
		Assert.assertEquals(res.length, actual.length);
		for(int i=0; i<res.length; i++) {
			assertTrue("B:Line Size "+(i+1)+": "+actual[i]+" = "+actual[i].length(), actual[i].length()<=80);   
			Assert.assertEquals("B:Line "+(i+1), res[i], actual[i]); 
		}

		src = Locale.getString("C_SOURCE"); 
		res = Locale.getString("C_RESULT").split("\n");  
		actual = TextUtil.cutStringAsArray(src, 80);
		assertNotNull(actual);
		Assert.assertEquals(res.length, actual.length);
		for(int i=0; i<res.length; i++) {
			assertTrue("C:Line Size "+(i+1)+": "+actual[i]+" = "+actual[i].length(), actual[i].length()<=80);   
			Assert.assertEquals("C:Line "+(i+1), res[i], actual[i]); 
		}

		src = Locale.getString("D_SOURCE"); 
		res = Locale.getString("D_RESULT").split("\n");  
		actual = TextUtil.cutStringAsArray(src, 80);
		assertNotNull(actual);
		Assert.assertEquals(res.length, actual.length);
		for(int i=0; i<res.length; i++) {
			assertTrue("D:Line Size "+(i+1)+": "+actual[i]+" = "+actual[i].length(), actual[i].length()<=80);   
			Assert.assertEquals("D:Line "+(i+1), res[i], actual[i]); 
		}
	}

	@Test
	public void cutString() throws Exception {
		String src, res, actual;
		
		src = Locale.getString("A_SOURCE"); 
		res = Locale.getString("A_RESULT"); 
		actual = TextUtil.cutString(src, 80);
		assertNotNull(actual);
		Assert.assertEquals("A:", res, actual); 

		src = Locale.getString("B_SOURCE"); 
		res = Locale.getString("B_RESULT"); 
		actual = TextUtil.cutString(src, 80);
		assertNotNull(actual);
		Assert.assertEquals("B:", res, actual); 

		src = Locale.getString("C_SOURCE"); 
		res = Locale.getString("C_RESULT"); 
		actual = TextUtil.cutString(src, 80);
		assertNotNull(actual);
		Assert.assertEquals("C:", res, actual); 

		src = Locale.getString("D_SOURCE"); 
		res = Locale.getString("D_RESULT"); 
		actual = TextUtil.cutString(src, 80);
		assertNotNull(actual);
		Assert.assertEquals("D:", res, actual); 
	}
	
	@Test
	public void splitBrackets() throws Exception {
		String[] tab;
		
		tab = TextUtil.splitBrackets("{a}{b}{c}{d}"); 
		assertArrayEquals(new String[]{
				"a", 
				"b", 
				"c", 
				"d", 
			}, tab);

		tab = TextUtil.splitBrackets("start {a}bbb {b eee}{c}{d}zzz end"); 
		assertArrayEquals(new String[]{
				"start", 
				"a", 
				"bbb", 
				"b eee", 
				"c", 
				"d", 
				"zzz end", 
			}, tab);

		tab = TextUtil.splitBrackets("start {a}bbb {b {eee}}{c}{d}zzz end"); 
		assertArrayEquals(new String[]{
				"start", 
				"a", 
				"bbb", 
				"b {eee}", 
				"c", 
				"d", 
				"zzz end", 
			}, tab);

		tab = TextUtil.splitBrackets("start {a}bbb {b {e{e{e}f}}}{c}{d}zzz end"); 
		assertArrayEquals(new String[]{
				"start", 
				"a", 
				"bbb", 
				"b {e{e{e}f}}", 
				"c", 
				"d", 
				"zzz end", 
			}, tab);

		tab = TextUtil.splitBrackets("start {a}bbb {b {e{e{e}f}}}{}{d}zzz end"); 
		assertArrayEquals(new String[]{
				"start", 
				"a", 
				"bbb", 
				"b {e{e{e}f}}", 
				"", 
				"d", 
				"zzz end", 
			}, tab);
	}
	
	@Test
	public void splitCharCharStr() throws Exception {
		String[] tab;
		
		tab = TextUtil.split('(',']',"(a](b](c](d]"); 
		assertArrayEquals(new String[]{
				"a", 
				"b", 
				"c", 
				"d", 
			}, tab);

		tab = TextUtil.split('(',']',"start (a]bbb (b eee](c](d]zzz end"); 
		assertArrayEquals(new String[]{
				"start", 
				"a", 
				"bbb", 
				"b eee", 
				"c", 
				"d", 
				"zzz end", 
			}, tab);

		tab = TextUtil.split('(',']',"start (a]bbb (b (eee]](c](d]zzz end"); 
		assertArrayEquals(new String[]{
				"start", 
				"a", 
				"bbb", 
				"b (eee]", 
				"c", 
				"d", 
				"zzz end", 
			}, tab);

		tab = TextUtil.split('(',']',"start (a]bbb (b (e(e(e]f]]](c](d]zzz end"); 
		assertArrayEquals(new String[]{
				"start", 
				"a", 
				"bbb", 
				"b (e(e(e]f]]", 
				"c", 
				"d", 
				"zzz end", 
			}, tab);

		tab = TextUtil.split('(',']',"start (a]bbb (b (e(e(e]f]]](](d]zzz end"); 
		assertArrayEquals(new String[]{
				"start", 
				"a", 
				"bbb", 
				"b (e(e(e]f]]", 
				"", 
				"d", 
				"zzz end", 
			}, tab);
	}

	@Test
	public void splitBracketsAsList() throws Exception {
		List<String> tab;
		
		tab = TextUtil.splitBracketsAsList("{a}{b}{c}{d}"); 
		assertCollectionEquals(Arrays.asList(
				"a", 
				"b", 
				"c", 
				"d" 
			), tab);

		tab = TextUtil.splitBracketsAsList("start {a}bbb {b eee}{c}{d}zzz end"); 
		assertCollectionEquals(Arrays.asList(
				"start", 
				"a", 
				"bbb", 
				"b eee", 
				"c", 
				"d", 
				"zzz end" 
			), tab);

		tab = TextUtil.splitBracketsAsList("start {a}bbb {b {eee}}{c}{d}zzz end"); 
		assertCollectionEquals(Arrays.asList(
				"start", 
				"a", 
				"bbb", 
				"b {eee}", 
				"c", 
				"d", 
				"zzz end" 
			), tab);

		tab = TextUtil.splitBracketsAsList("start {a}bbb {b {e{e{e}f}}}{c}{d}zzz end"); 
		assertCollectionEquals(Arrays.asList(
				"start", 
				"a", 
				"bbb", 
				"b {e{e{e}f}}", 
				"c", 
				"d", 
				"zzz end" 
			), tab);

		tab = TextUtil.splitBracketsAsList("start {a}bbb {b {e{e{e}f}}}{}{d}zzz end"); 
		assertCollectionEquals(Arrays.asList(
				"start", 
				"a", 
				"bbb", 
				"b {e{e{e}f}}", 
				"", 
				"d", 
				"zzz end" 
			), tab);
	}

	@Test
	public void splitAsListCharCharStr() throws Exception {
		List<String> tab;
		
		tab = TextUtil.splitAsList('|','=',"|a=|b=|c=|d="); 
		assertCollectionEquals(Arrays.asList(
				"a", 
				"b", 
				"c", 
				"d" 
			), tab);

		tab = TextUtil.splitAsList('|','=',"start |a=bbb |b eee=|c=|d=zzz end"); 
		assertCollectionEquals(Arrays.asList(
				"start", 
				"a", 
				"bbb", 
				"b eee", 
				"c", 
				"d", 
				"zzz end" 
			), tab);

		tab = TextUtil.splitAsList('|','=',"start |a=bbb |b |eee==|c=|d=zzz end"); 
		assertCollectionEquals(Arrays.asList(
				"start", 
				"a", 
				"bbb", 
				"b |eee=", 
				"c", 
				"d", 
				"zzz end" 
			), tab);

		tab = TextUtil.splitAsList('|','=',"start |a=bbb |b |e|e|e=f===|c=|d=zzz end"); 
		assertCollectionEquals(Arrays.asList(
				"start", 
				"a", 
				"bbb", 
				"b |e|e|e=f==", 
				"c", 
				"d", 
				"zzz end" 
			), tab);

		tab = TextUtil.splitAsList('|','=',"start |a=bbb |b |e|e|e=f===|=|d=zzz end"); 
		assertCollectionEquals(Arrays.asList(
				"start", 
				"a", 
				"bbb", 
				"b |e|e|e=f==", 
				"", 
				"d", 
				"zzz end" 
			), tab);
	}
	
	@Test
	public void parseHTML() {
		String source, expected, actual;
		
		source = Locale.getString("HTML_JAVA_SOURCE"); 
		expected = Locale.getString("HTML_JAVA_EXPECTED"); 
		
		assertNull(TextUtil.parseHTML(null));
		
		actual = TextUtil.parseHTML(source);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void toHTML() {
		String source, expected, actual;
		
		source = Locale.getString("JAVA_HTML_SOURCE"); 
		expected = Locale.getString("JAVA_HTML_EXPECTED"); 
		
		assertNull(TextUtil.toHTML(null));
		
		actual = TextUtil.toHTML(source);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void equalsIgnoreAccentsStringStringMap() {
		assertInlineParameterUsage(TextUtil.class, "equalsIgnoreAccents", String.class, String.class, Map.class);
	}

	@Test
	public void equalsIgnoreCaseAccentsStringStringMap() {
		assertInlineParameterUsage(TextUtil.class, "equalsIgnoreCaseAccents", String.class, String.class, Map.class);
	}

	@Test
	public void joinStringBooleanArray() {
		assertInlineParameterUsage(TextUtil.class, "join", String.class, boolean[].class);
	}

	@Test
	public void joinStringByteArray() {
		assertInlineParameterUsage(TextUtil.class, "join", String.class, byte[].class);
	}

	@Test
	public void joinStringCharArray() {
		assertInlineParameterUsage(TextUtil.class, "join", String.class, char[].class);
	}

	@Test
	public void joinStringShortArray() {
		assertInlineParameterUsage(TextUtil.class, "join", String.class, short[].class);
	}

	@Test
	public void joinStringDoubleArray() {
		assertInlineParameterUsage(TextUtil.class, "join", String.class, double[].class);
	}

	@Test
	public void joinStringFloatArray() {
		assertInlineParameterUsage(TextUtil.class, "join", String.class, float[].class);
	}

	@Test
	public void joinStringIntArray() {
		assertInlineParameterUsage(TextUtil.class, "join", String.class, int[].class);
	}

	@Test
	public void joinStringLongArray() {
		assertInlineParameterUsage(TextUtil.class, "join", String.class, long[].class);
	}

	@Test
	public void joinStringIterable() {
		assertInlineParameterUsage(TextUtil.class, "join", String.class, Iterable.class);
	}
	
	@Test
	public void joinStringStringStringObjectArray() {
		assertInlineParameterUsage(TextUtil.class, "join", String.class, String.class, String.class, Object[].class);
	}

	@Test
	public void joinStringObjectArray() {
		assertInlineParameterUsage(TextUtil.class, "join", String.class, Object[].class);
	}
	
	@Test
	public void mergeBracketsIterable() {
		assertInlineParameterUsage(TextUtil.class, "mergeBrackets", Iterable.class);
	}

	@Test
	public void mergeBracketsObjectArray() {
		assertInlineParameterUsage(TextUtil.class, "mergeBrackets", Object[].class);
	}

	@Test
	public void splitBracketsString() {
		assertInlineParameterUsage(TextUtil.class, "splitBrackets", String.class);
	}

	@Test
	public void splitBracketsAsListString() {
		assertInlineParameterUsage(TextUtil.class, "splitBracketsAsList", String.class);
	}

	@Test
	public void splitBracketsAsUUIDsString() {
		assertInlineParameterUsage(TextUtil.class, "splitBracketsAsUUIDs", String.class);
	}

}
