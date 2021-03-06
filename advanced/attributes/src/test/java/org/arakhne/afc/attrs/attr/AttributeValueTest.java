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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import org.junit.Ignore;
import org.junit.Test;

import org.arakhne.afc.math.geometry.d2.Point2D;
import org.arakhne.afc.math.geometry.d2.d.Point2d;
import org.arakhne.afc.math.geometry.d3.Point3D;
import org.arakhne.afc.testtools.AbstractTestCase;
import org.arakhne.afc.ui.vector.Color;
import org.arakhne.afc.ui.vector.Colors;
import org.arakhne.afc.ui.vector.Image;
import org.arakhne.afc.ui.vector.VectorToolkit;

@SuppressWarnings("all")
@Ignore("Invalid 3D primitives are included")
public class AttributeValueTest extends AbstractTestCase {

	protected static void assertAllGetFailed(AttributeValue attr, AttributeType type) {
		try {
			attr.getValue();
			fail("getValue: the exception AttributeNotInitializedException was not thrown for "+type); 
		}
		catch(AttributeException exception) {
			// expected case
		}

		try {
			attr.getBoolean();
			fail("getBoolean: the exception InvalidAttributeTypeException was not thrown for "+type); 
		}
		catch(AttributeException exception) {
			// expected case
		}

		try {
			attr.getColor();
			fail("getColor: the exception InvalidAttributeTypeException was not thrown for "+type); 
		}
		catch(AttributeException exception) {
			// expected case
		}

		try {
			attr.getDate();
			fail("getDate: the exception InvalidAttributeTypeException was not thrown for "+type); 
		}
		catch(AttributeException exception) {
			// expected case
		}

		try {
			attr.getImage();
			fail("getImage: the exception AttributeNotInitializedException was not thrown for "+type); 
		}
		catch(AttributeException exception) {
			// expected case
		}

		try {
			attr.getInteger();
			fail("getInteger: the exception InvalidAttributeTypeException was not thrown for "+type); 
		}
		catch(AttributeException exception) {
			// expected case
		}

		try {
			attr.getJavaObject();
			if (type.isBaseType())
				fail("getJavaObject: the exception AttributeNotInitializedException was not thrown for "+type); 
		}
		catch(AttributeException exception) {
			// expected case
		}

		try {
			attr.getPoint();
			fail("getPoint: the exception InvalidAttributeTypeException was not thrown for "+type); 
		}
		catch(AttributeException exception) {
			// expected case
		}

		try {
			attr.getPoint3D();
			fail("getPoint3D: the exception InvalidAttributeTypeException was not thrown for "+type); 
		}
		catch(AttributeException exception) {
			// expected case
		}

		try {
			attr.getPolyline();
			fail("getPolyline: the exception InvalidAttributeTypeException was not thrown for "+type); 
		}
		catch(AttributeException exception) {
			// expected case
		}

		try {
			attr.getPolyline3D();
			fail("getPolyline3D: the exception InvalidAttributeTypeException was not thrown for "+type); 
		}
		catch(AttributeException exception) {
			// expected case
		}

		try {
			attr.getReal();
			fail("getReal: the exception InvalidAttributeTypeException was not thrown for "+type); 
		}
		catch(AttributeException exception) {
			// expected case
		}

		try {
			attr.getString();
			fail("getString: the exception AttributeNotInitializedException was not thrown for "+type); 
		}
		catch(AttributeNotInitializedException exception) {
			//
		}
		catch(InvalidAttributeTypeException exception) {
			if (!attr.isObjectValue())
				fail("unexpected exception InvalidAttributeTypeException for "+type); 
		}

		try {
			attr.getTimestamp();
			fail("getTimestamp: the exception InvalidAttributeTypeException was not thrown for "+type); 
		}
		catch(AttributeException exception) {
			// expected case
		}

		try {
			attr.getUUID();
			fail("getUUID: the exception InvalidAttributeTypeException was not thrown for "+type); 
		}
		catch(AttributeException exception) {
			// expected case
		}

		try {
			attr.getURI();
			fail("getURI: the exception InvalidAttributeTypeException was not thrown for "+type); 
		}
		catch(AttributeException exception) {
			// expected case
		}

		try {
			attr.getURL();
			fail("getURL: the exception InvalidAttributeTypeException was not thrown for "+type); 
		}
		catch(AttributeException exception) {
			// expected case
		}
	}

	protected static void assertAttributeException(AttributeValue attr, String methodName) throws Exception {
		try {
			Class<? extends AttributeValue> clazz = attr.getClass();
			Method method = clazz.getMethod(methodName);
			method.invoke(attr);
			fail("the exception AttributeException was not thrown"); 
		}
		catch(InvocationTargetException e) {
			Throwable ex = e.getTargetException();
			if (ex instanceof AssertionError) {
				throw (AssertionError)ex;
			}
			if (ex instanceof AttributeException) {
				//
			}
			else {
				fail("the exception AttributeException was not thrown"); 
			}
		}
	}

	@Test
	public void attributeValueImpl() {
		AttributeValue attr = new AttributeValueImpl();
		
		assertFalse(attr.isAssigned());
		assertFalse(attr.isBaseType());
		assertTrue(attr.isObjectValue());
		
		assertEquals(AttributeType.OBJECT, attr.getType());
		
		try {
			attr.getValue();
			fail("the exception AttributeNotInitializedException was not thrown"); 
		}
		catch(AttributeNotInitializedException exception) {
			// expected case
		}
		catch(InvalidAttributeTypeException exception) {
			fail("unexpected exception InvalidAttributeTypeException"); 
		}

		try {
			attr.getBoolean();
			fail("the exception AttributeNotInitializedException was not thrown"); 
		}
		catch(AttributeNotInitializedException exception) {
			// expected case
		}
		catch(InvalidAttributeTypeException exception) {
			fail("unexpected exception InvalidAttributeTypeException"); 
		}
	}

	@Test
	public void attributeValueImplAttributeType() throws Exception {
		AttributeType[] values = AttributeType.values();
		//AttributeType[] values = new AttributeType[] {AttributeType.OBJECT};
		for (AttributeType type : values) {
			AttributeValue attr = new AttributeValueImpl(type);
			
			assertEquals(type, attr.getType());

			assertFalse(attr.isAssigned());
			assertEquals(type.isBaseType(),attr.isBaseType());
			assertEquals("on type "+type, 
					!type.isBaseType(),
					attr.isObjectValue());
			
			if (type.isNullAllowed()) {
				assertAttributeException(attr, "getBoolean"); 
				assertAttributeException(attr, "getColor"); 
				assertAttributeException(attr, "getDate"); 
				assertAttributeException(attr, "getImage"); 
				assertAttributeException(attr, "getInteger"); 
				assertNull(attr.getJavaObject());
				assertAttributeException(attr, "getPoint"); 
				assertAttributeException(attr, "getPoint3D"); 
				assertAttributeException(attr, "getPolyline"); 
				assertAttributeException(attr, "getPolyline3D"); 
				assertAttributeException(attr, "getReal"); 
				assertAttributeException(attr, "getString"); 
				assertAttributeException(attr, "getTimestamp"); 
				assertAttributeException(attr, "getURI"); 
				assertAttributeException(attr, "getURL"); 
				assertAttributeException(attr, "getUUID"); 
				assertAttributeException(attr, "getValue"); 
			}
			else {
				assertAllGetFailed(attr, type);
			}
		}
	}
	
	@Test
	public void attributeValueImplBoolean() throws Exception {
		AttributeValue attr = new AttributeValueImpl(false);
		
		assertEquals(AttributeType.BOOLEAN, attr.getType());

		assertTrue(attr.isAssigned());
		assertTrue(attr.isBaseType());
		assertFalse(attr.isObjectValue());
		
		assertFalse((Boolean)attr.getValue());
		assertFalse(attr.getBoolean());
		assertAttributeException(attr,"getColor"); 
		assertAttributeException(attr,"getDate"); 
		assertAttributeException(attr,"getImage"); 
		assertEquals(0, attr.getInteger());
		assertEquals(0., attr.getReal());
		assertEquals(0, attr.getTimestamp());
		assertEquals(Boolean.toString(false),attr.getString());
		assertAttributeException(attr,"getJavaObject"); 
		assertAttributeException(attr,"getPoint"); 
		assertAttributeException(attr,"getPoint3D"); 
		assertAttributeException(attr,"getPolyline"); 
		assertAttributeException(attr,"getPolyline3D"); 
	}

	@Test
	public void attributeValueImplColor() throws Exception {
		String txt = "255;0;0;255"; 
		AttributeValue attr = new AttributeValueImpl(Colors.RED);
		
		assertEquals(AttributeType.COLOR, attr.getType());

		assertTrue(attr.isAssigned());
		assertFalse(attr.isBaseType());
		assertTrue(attr.isObjectValue());
		
		assertEquals(Colors.RED,attr.getValue());
		assertAttributeException(attr,"getBoolean"); 
		assertEquals(Colors.RED,attr.getColor());
		assertAttributeException(attr,"getDate"); 
		assertAttributeException(attr,"getImage"); 
		assertEquals(Colors.RED.getRGB(), attr.getInteger());
		assertEquals((double)Colors.RED.getRGB(), attr.getReal());
		assertEquals(Colors.RED.getRGB(), attr.getTimestamp());
		assertEquals(txt,attr.getString());
		assertEquals(Colors.RED, attr.getJavaObject());
		assertEquals(new Point2d(255,0),attr.getPoint());
		assertEquals(null /* FIXME: fix code: new Point3fp(255,0,0)*/,attr.getPoint3D());
		assertAttributeException(attr,"getPolyline"); 
		assertAttributeException(attr,"getPolyline3D"); 
	}

	@Test
	public void attributeValueImplDate() throws Exception {
		Date currentDate = new Date();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd"); 
		String txt = fmt.format(currentDate);
		AttributeValue attr = new AttributeValueImpl(currentDate);
		
		assertEquals(AttributeType.DATE, attr.getType());

		assertTrue(attr.isAssigned());
		assertFalse(attr.isBaseType());
		assertTrue(attr.isObjectValue());
		
		assertEquals(currentDate,attr.getValue());
		assertAttributeException(attr,"getBoolean"); 
		assertEquals(VectorToolkit.color((int)currentDate.getTime()), attr.getColor());
		assertEquals(currentDate,attr.getDate());
		assertAttributeException(attr,"getImage"); 
		assertEquals(currentDate.getTime(),attr.getInteger());
		assertEquals((double)currentDate.getTime(),attr.getReal());
		assertEquals(currentDate.getTime(),attr.getTimestamp());
		assertEquals(txt,attr.getString());
		assertEquals(currentDate, attr.getJavaObject());
		//TODO: fixcode: assertEquals(new Point2fp(currentDate.getTime(), 0), attr.getPoint());
		//TODO: fixcode: assertEquals(null /* FIXME: fix code : new Point3f(currentDate.getTime(), 0, 0)*/, attr.getPoint3D());
		assertAttributeException(attr,"getPolyline"); 
		assertAttributeException(attr,"getPolyline3D"); 
	}

	@Test
	public void attributeValueImplFloat() throws Exception {
		float nb = (float)Math.random();
		String txt = Double.toString(nb);
		AttributeValue attr = new AttributeValueImpl(nb);
		
		assertEquals(AttributeType.REAL, attr.getType());

		assertTrue(attr.isAssigned());
		assertTrue(attr.isBaseType());
		assertFalse(attr.isObjectValue());
		
		assertEquals(nb,((Number)attr.getValue()).floatValue());
		assertEquals(nb!=0f, attr.getBoolean());
		assertEquals(VectorToolkit.color((int)nb), attr.getColor());
		assertEquals(new Date((long)nb),attr.getDate());
		assertAttributeException(attr,"getImage"); 
		assertEquals((long)nb,attr.getInteger());
		assertEquals((double)nb,attr.getReal());
		assertEquals((long)nb,attr.getTimestamp());
		assertEquals(txt,attr.getString());
		assertAttributeException(attr,"getJavaObject"); 
		assertEquals(new Point2d(nb,0),attr.getPoint());
		//TODO: fix code: assertEquals(new Point3f(nb,0,0),attr.getPoint3D());
		assertAttributeException(attr,"getPolyline"); 
		assertAttributeException(attr,"getPolyline3D"); 
	}

	@Test
	public void attributeValueImplDouble() throws Exception {
		double nb = Math.random();
		String txt = Double.toString(nb);
		AttributeValue attr = new AttributeValueImpl(nb);
		
		assertEquals(AttributeType.REAL, attr.getType());

		assertTrue(attr.isAssigned());
		assertTrue(attr.isBaseType());
		assertFalse(attr.isObjectValue());
		
		assertEquals(nb,((Number)attr.getValue()).doubleValue());
		assertEquals(nb!=0., attr.getBoolean());
		assertEquals(VectorToolkit.color((int)nb), attr.getColor());
		assertEquals(new Date((long)nb),attr.getDate());
		assertAttributeException(attr,"getImage"); 
		assertEquals((long)nb,attr.getInteger());
		assertEquals(nb,attr.getReal());
		assertEquals((long)nb,attr.getTimestamp());
		assertEquals(txt,attr.getString());
		assertAttributeException(attr,"getJavaObject"); 
		assertEquals(new Point2d(nb,0),attr.getPoint());
		//TODO: fix code: assertEquals(new Point3f(nb,0,0),attr.getPoint3D());
		assertAttributeException(attr,"getPolyline"); 
		assertAttributeException(attr,"getPolyline3D"); 
	}

	@Test
	public void attributeValueImplIcon() throws Exception {
		Image ic = VectorToolkit.image(1,1,false);
		AttributeValue attr = new AttributeValueImpl(ic);
		
		assertEquals(AttributeType.IMAGE, attr.getType());

		assertTrue(attr.isAssigned());
		assertFalse(attr.isBaseType());
		assertTrue(attr.isObjectValue());
		
		assertEquals(ic,attr.getValue());
		assertAttributeException(attr,"getBoolean"); 
		assertAttributeException(attr,"getColor"); 
		assertAttributeException(attr,"getDate"); 
		assertEquals(ic,attr.getImage());
		assertAttributeException(attr,"getInteger"); 
		assertAttributeException(attr,"getReal"); 
		assertAttributeException(attr,"getTimestamp"); 
		assertEquals(ic.toString(), attr.getString());
		assertEquals(ic,attr.getJavaObject());
		assertAttributeException(attr,"getPoint"); 
		//TODO: fixcode: assertAttributeException(attr,"getPoint3D"); 
		assertAttributeException(attr,"getPolyline"); 
		assertAttributeException(attr,"getPolyline3D"); 
	}

	@Test
	public void attributeValueImplInt() throws Exception {
		int nb = new Random().nextInt();
		String txt = Long.toString(nb);
		AttributeValue attr = new AttributeValueImpl(nb);
		
		assertEquals(AttributeType.INTEGER, attr.getType());

		assertTrue(attr.isAssigned());
		assertTrue(attr.isBaseType());
		assertFalse(attr.isObjectValue());
		
		assertEquals(nb,((Number)attr.getValue()).intValue());
		assertEquals(nb!=0, attr.getBoolean());
		assertEquals(VectorToolkit.color(nb), attr.getColor());
		assertEquals(new Date(nb),attr.getDate());
		assertAttributeException(attr,"getImage"); 
		assertEquals(nb,attr.getInteger());
		assertEquals(nb,(int)attr.getReal());
		assertEquals(nb,attr.getTimestamp());
		assertEquals(txt,attr.getString());
		assertAttributeException(attr,"getJavaObject"); 
		assertEquals(new Point2d(nb,0),attr.getPoint());
		//TODO: fix code: assertEquals(new Point3f(nb,0,0),attr.getPoint3D());
		assertAttributeException(attr,"getPolyline"); 
		assertAttributeException(attr,"getPolyline3D"); 
	}

	@Test
	public void attributeValueImplLong() throws Exception {
		long nb = new Random().nextLong();
		String txt = Long.toString(nb);
		AttributeValue attr = new AttributeValueImpl(nb);
		
		assertEquals(AttributeType.INTEGER, attr.getType());

		assertTrue(attr.isAssigned());
		assertTrue(attr.isBaseType());
		assertFalse(attr.isObjectValue());
		
		assertEquals(nb,((Number)attr.getValue()).longValue());
		assertEquals(nb!=0, attr.getBoolean());
		assertEquals(VectorToolkit.color((int)nb), attr.getColor());
		assertEquals(new Date(nb),attr.getDate());
		assertAttributeException(attr,"getImage"); 
		assertEquals(nb,attr.getInteger());
		assertEquals((double)nb,attr.getReal());
		assertEquals(nb,attr.getTimestamp());
		assertEquals(txt,attr.getString());
		assertAttributeException(attr,"getJavaObject"); 
		//TODO: fixcode: assertEquals(new Point2fp(nb,0),attr.getPoint());
		//TODO: fix code: assertEquals(new Point3f(nb,0,0),attr.getPoint3D());
		assertAttributeException(attr,"getPolyline"); 
		assertAttributeException(attr,"getPolyline3D"); 
	}

	@Test
	public void attributeValueImplPoint2d() throws Exception {
		Point2D pt = new Point2d((float)Math.random(),(float)Math.random());
		Point3D pt3d = null; // FIXME: fix code: new Point3f(pt.getX(),pt.getY(),0);
		String str = pt.getX()+";"+pt.getY(); 
		AttributeValue attr = new AttributeValueImpl(pt);
		
		assertEquals(AttributeType.POINT, attr.getType());

		assertTrue(attr.isAssigned());
		assertFalse(attr.isBaseType());
		assertTrue(attr.isObjectValue());
		
		assertEquals(pt,attr.getValue());
		assertAttributeException(attr,"getBoolean"); 
		//TODO: fix code: assertEquals(VectorToolkit.color(pt.getX(),pt.getY(),0f),attr.getColor());
		assertAttributeException(attr,"getDate"); 
		assertAttributeException(attr,"getImage"); 
		assertAttributeException(attr,"getInteger"); 
		assertAttributeException(attr,"getReal"); 
		assertAttributeException(attr,"getTimestamp"); 
		assertEquals(str,attr.getString());
		assertEquals(pt, attr.getJavaObject());
		assertEquals(pt,attr.getPoint());
		assertEquals(pt3d,attr.getPoint3D());
		assertEquals(new Point2D[]{pt},attr.getPolyline());
		assertEquals(new Point3D[]{pt3d},attr.getPolyline3D());
	}
	
	@Test
	public void attributeValueImplDoubleDouble() throws Exception {
		float x = (float)Math.random();
		float y = (float)Math.random();
		Point2D pt = new Point2d(x,y);
		Point3D pt3d = null; // FIXME: fix code: new Point3f(x,y,0);
		String str = x+";"+y; 
		AttributeValue attr = new AttributeValueImpl(x,y);
		
		assertEquals(AttributeType.POINT, attr.getType());

		assertTrue(attr.isAssigned());
		assertFalse(attr.isBaseType());
		assertTrue(attr.isObjectValue());
		
		assertEquals(pt,attr.getValue());
		assertAttributeException(attr,"getBoolean"); 
		//TODO: fix code: assertEquals(VectorToolkit.color(pt.getX(),pt.getY(),0f),attr.getColor());
		assertAttributeException(attr,"getDate"); 
		assertAttributeException(attr,"getImage"); 
		assertAttributeException(attr,"getInteger"); 
		assertAttributeException(attr,"getReal"); 
		assertAttributeException(attr,"getTimestamp"); 
		assertEquals(str,attr.getString());
		assertEquals(pt, attr.getJavaObject());
		assertEquals(pt,attr.getPoint());
		assertEquals(pt3d,attr.getPoint3D());
		assertEquals(new Point2D[]{pt},attr.getPolyline());
		assertEquals(new Point3D[]{pt3d},attr.getPolyline3D());
	}
	
	@Test
	public void attributeValueImplPoint3d() throws Exception {
		float x = (float)Math.random();
		float y = (float)Math.random();
		float z = (float)Math.random();
		Point3D pt = null; // FIXME: fix code: new Point3f(x,y,z);
		Point2D pt2d = new Point2d(x,y);
		String str = x+";"+y+";"+z;  
		AttributeValue attr = new AttributeValueImpl(pt);
		
		assertEquals(AttributeType.POINT3D, attr.getType());

		assertTrue(attr.isAssigned());
		assertFalse(attr.isBaseType());
		assertTrue(attr.isObjectValue());
		
		assertEquals(pt,attr.getValue());
		assertAttributeException(attr,"getBoolean"); 
		//TODO: fix code: assertEquals(VectorToolkit.color(pt.getX(),pt.getY(),pt.getZ()),attr.getColor());
		assertAttributeException(attr,"getDate"); 
		assertAttributeException(attr,"getImage"); 
		assertAttributeException(attr,"getInteger"); 
		assertAttributeException(attr,"getReal"); 
		assertAttributeException(attr,"getTimestamp"); 
		assertEquals(str,attr.getString());
		assertEquals(pt, attr.getJavaObject());
		assertEquals(pt2d,attr.getPoint());
		assertEquals(pt,attr.getPoint3D());
		assertEquals(new Point2D[]{pt2d},attr.getPolyline());
		assertEquals(new Point3D[]{pt},attr.getPolyline3D());
	}

	@Test
	public void attributeValueImplDoubleDoubleDouble() throws Exception {
		double x = Math.random();
		double y = Math.random();
		double z = Math.random();
		Point3D pt = null; // FIXME: fix code: new Point3f(x,y,z);
		Point2D pt2d = new Point2d(x,y);
		String str = ((float)x)+";"+((float)y)+";"+((float)z);  
		AttributeValue attr = new AttributeValueImpl(x,y,z);
		
		assertEquals(AttributeType.POINT3D, attr.getType());

		assertTrue(attr.isAssigned());
		assertFalse(attr.isBaseType());
		assertTrue(attr.isObjectValue());
		
		assertEquals(pt,attr.getValue());
		assertAttributeException(attr,"getBoolean"); 
		//TODO: fix code: assertEquals(VectorToolkit.color(pt.getX(),pt.getY(),pt.getZ()),attr.getColor());
		assertAttributeException(attr,"getDate"); 
		assertAttributeException(attr,"getImage"); 
		assertAttributeException(attr,"getInteger"); 
		assertAttributeException(attr,"getReal"); 
		assertAttributeException(attr,"getTimestamp"); 
		assertEquals(str,attr.getString());
		assertEquals(pt, attr.getJavaObject());
		assertEquals(pt2d,attr.getPoint());
		assertEquals(pt,attr.getPoint3D());
		assertEquals(new Point2D[]{pt2d},attr.getPolyline());
		assertEquals(new Point3D[]{pt},attr.getPolyline3D());
	}

	@Test
	public void attributeValueImplString_random() throws Exception {
		double x = Math.random();
		Point2D pt2d = new Point2d(x,0);
		Point3D pt3d = null; // FIXME: fix code: new Point3f(x,0,0);
		String str = Double.toHexString(x);
		AttributeValue attr = new AttributeValueImpl(str);
		
		assertEquals(AttributeType.STRING, attr.getType());

		assertTrue(attr.isAssigned());
		assertTrue(attr.isBaseType());
		assertFalse(attr.isObjectValue());
		
		assertEquals(str,attr.getValue());
		assertAttributeException(attr,"getBoolean"); 
		assertAttributeException(attr,"getColor"); 
		assertAttributeException(attr,"getDate"); 
		assertAttributeException(attr,"getImage"); 
		assertAttributeException(attr,"getInteger"); 
		assertEquals(x,attr.getReal());
		assertAttributeException(attr,"getTimestamp"); 
		assertEquals(str,attr.getString());
		assertAttributeException(attr,"getJavaObject"); 
		assertEquals(pt2d,attr.getPoint());
		assertEquals(pt3d,attr.getPoint3D());
		assertAttributeException(attr,"getJavaObject"); 
		assertEquals(new Point2D[]{pt2d},attr.getPolyline());
		assertEquals(new Point3D[]{pt3d},attr.getPolyline3D());
	}

	@Test
	public void attributeValueImplString_boolean() throws Exception {
		String str = Boolean.toString(true);
		AttributeValue attr = new AttributeValueImpl(str);
		
		assertEquals(AttributeType.STRING, attr.getType());

		assertTrue(attr.isAssigned());
		assertTrue(attr.isBaseType());
		assertFalse(attr.isObjectValue());
		
		assertEquals(str,attr.getValue());
		assertTrue(attr.getBoolean());
		assertAttributeException(attr,"getColor"); 
		assertAttributeException(attr,"getDate"); 
		assertAttributeException(attr,"getImage"); 
		assertAttributeException(attr,"getInteger"); 
		assertAttributeException(attr,"getReal"); 
		assertAttributeException(attr,"getTimestamp"); 
		assertEquals(str,attr.getString());
		assertAttributeException(attr,"getJavaObject"); 
		assertAttributeException(attr,"getPoint"); 
		//TODO: fixcode: assertAttributeException(attr,"getPoint3D"); 
		//TODO: fixcode: assertAttributeException(attr,"getPolyline"); 
		//TODO: fixcode: assertAttributeException(attr,"getPolyline3D"); 
	}

	@Test
	public void attributeValueImplString_Color() throws Exception {
		Color c = Colors.RED;
		Point2D pt2d = new Point2d(c.getRed(),c.getGreen());
		Point2D pt2d2 = new Point2d(c.getBlue(),0);
		Point3D pt3d = null; // FIXME: fix code: new Point3f(c.getRed(),c.getGreen(),c.getBlue());
		String str = c.getRed()+";"+c.getGreen()+";"+c.getBlue();  
		AttributeValue attr = new AttributeValueImpl(str);
		
		assertEquals(AttributeType.STRING, attr.getType());

		assertTrue(attr.isAssigned());
		assertTrue(attr.isBaseType());
		assertFalse(attr.isObjectValue());
		
		assertEquals(str,attr.getValue());
		assertAttributeException(attr,"getBoolean"); 
		assertEquals(c,attr.getColor());
		assertAttributeException(attr,"getDate"); 
		assertAttributeException(attr,"getImage"); 
		assertAttributeException(attr,"getInteger"); 
		assertAttributeException(attr,"getReal"); 
		assertAttributeException(attr,"getTimestamp"); 
		assertEquals(str,attr.getString());
		assertAttributeException(attr,"getJavaObject"); 
		assertEquals(pt2d,attr.getPoint());
		assertEquals(pt3d,attr.getPoint3D());
		assertEquals(new Point2D[]{pt2d,pt2d2},attr.getPolyline());
		assertEquals(new Point3D[]{pt3d},attr.getPolyline3D());
	}

	@Test
	public void attributeValueImplString_Date() throws Exception {
		Date currentDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		String str = format.format(currentDate); 
		AttributeValue attr = new AttributeValueImpl(str);
		
		assertEquals(AttributeType.STRING, attr.getType());

		assertTrue(attr.isAssigned());
		assertTrue(attr.isBaseType());
		assertFalse(attr.isObjectValue());
		
		assertEquals(str,attr.getValue());
		assertAttributeException(attr,"getBoolean"); 
		assertAttributeException(attr,"getColor"); 
		assertEpsilonEquals(currentDate,attr.getDate());
		assertAttributeException(attr,"getImage"); 
		assertAttributeException(attr,"getInteger"); 
		assertAttributeException(attr,"getReal"); 
		assertAttributeException(attr,"getTimestamp"); 
		assertEquals(str,attr.getString());
		assertAttributeException(attr,"getJavaObject"); 
		assertAttributeException(attr,"getPoint"); 
		assertAttributeException(attr,"getPoint3D"); 
		assertAttributeException(attr,"getPolyline"); 
		assertAttributeException(attr,"getPolyline3D"); 
	}

	@Test
	public void attributeValueImplString_JDate() throws Exception {
		Date currentDate = new Date();
		String str = DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.FULL).format(currentDate); 
		AttributeValue attr = new AttributeValueImpl(str);
		
		assertEquals(AttributeType.STRING, attr.getType());

		assertTrue(attr.isAssigned());
		assertTrue(attr.isBaseType());
		assertFalse(attr.isObjectValue());
		
		assertEquals(str,attr.getValue());
		assertAttributeException(attr,"getBoolean"); 
		assertAttributeException(attr,"getColor"); 
		assertEpsilonEquals(currentDate,attr.getDate());
		assertAttributeException(attr,"getImage"); 
		assertAttributeException(attr,"getInteger"); 
		assertAttributeException(attr,"getReal"); 
		assertAttributeException(attr,"getTimestamp"); 
		assertEquals(str,attr.getString());
		assertAttributeException(attr,"getJavaObject"); 
		assertAttributeException(attr,"getPoint"); 
		assertAttributeException(attr,"getPoint3D"); 
		assertAttributeException(attr,"getPolyline"); 
		assertAttributeException(attr,"getPolyline3D"); 
	}

	@Test
	public void attributeValueImplString_Integer() throws Exception {
		int nb = new Random().nextInt(20000)+256;
		Point2D pt2d = new Point2d(nb,0);
		Point3D pt3d = null; // FIXME: fix code: new Point3f(nb,0,0);
		String str = Integer.toString(nb);
		AttributeValue attr = new AttributeValueImpl(str);
		
		assertEquals(AttributeType.STRING, attr.getType());

		assertTrue(attr.isAssigned());
		assertTrue(attr.isBaseType());
		assertFalse(attr.isObjectValue());
		
		assertEquals(str,attr.getValue());
		assertAttributeException(attr,"getBoolean"); 
		assertAttributeException(attr,"getColor"); 
		assertAttributeException(attr,"getDate"); 
		assertAttributeException(attr,"getImage"); 
		assertEquals(nb,attr.getInteger());
		assertEquals((double)nb,attr.getReal());
		assertEquals(nb,attr.getTimestamp());
		assertEquals(str,attr.getString());
		assertAttributeException(attr,"getJavaObject"); 
		assertEquals(pt2d,attr.getPoint());
		assertEquals(pt3d,attr.getPoint3D());
		assertEquals(new Point2D[]{pt2d},attr.getPolyline());
		assertEquals(new Point3D[]{pt3d},attr.getPolyline3D());
	}

	@Test
	public void attributeValueImplString_Long() throws Exception {
		long nb = new Random().nextInt(20000)+256;
		Point2D pt2d = new Point2d(nb,0);
		Point3D pt3d = null; // FIXME: fix code: new Point3f(nb,0,0);
		String str = Long.toString(nb);
		AttributeValue attr = new AttributeValueImpl(str);
		
		assertEquals(AttributeType.STRING, attr.getType());

		assertTrue(attr.isAssigned());
		assertTrue(attr.isBaseType());
		assertFalse(attr.isObjectValue());
		
		assertEquals(str,attr.getValue());
		assertAttributeException(attr,"getBoolean"); 
		assertAttributeException(attr,"getColor"); 
		assertAttributeException(attr,"getDate"); 
		assertAttributeException(attr,"getImage"); 
		assertEquals(nb,attr.getInteger());
		assertEquals((double)nb,attr.getReal());
		assertEquals(nb,attr.getTimestamp());
		assertEquals(str,attr.getString());
		assertAttributeException(attr,"getJavaObject"); 
		assertEquals(pt2d,attr.getPoint());
		assertEquals(pt3d,attr.getPoint3D());
		assertEquals(new Point2D[]{pt2d},attr.getPolyline());
		assertEquals(new Point3D[]{pt3d},attr.getPolyline3D());
	}

	@Test
	public void attributeValueImplString_Double() throws Exception {
		double nb = Math.random()+256;
		Point2D pt2d = new Point2d(nb,0);
		Point3D pt3d = null; // FIXME: fix code: new Point3f(nb,0,0);
		String str = Double.toString(nb);
		AttributeValue attr = new AttributeValueImpl(str);
		
		assertEquals(AttributeType.STRING, attr.getType());

		assertTrue(attr.isAssigned());
		assertTrue(attr.isBaseType());
		assertFalse(attr.isObjectValue());
		
		assertEquals(str,attr.getValue());
		assertAttributeException(attr,"getBoolean"); 
		assertAttributeException(attr,"getColor"); 
		assertAttributeException(attr,"getDate"); 
		assertAttributeException(attr,"getImage"); 
		assertAttributeException(attr,"getInteger"); 
		assertEquals(nb,attr.getReal());
		assertAttributeException(attr,"getTimestamp"); 
		assertEquals(str,attr.getString());
		assertAttributeException(attr,"getJavaObject"); 
		assertEquals(pt2d,attr.getPoint());
		assertEquals(pt3d,attr.getPoint3D());
		assertEquals(new Point2D[]{pt2d},attr.getPolyline());
		assertEquals(new Point3D[]{pt3d},attr.getPolyline3D());
	}

	@Test
	public void attributeValueImplString_Point2D() throws Exception {
		double x = Math.random()+256;
		double y = Math.random()+256;
		Point2D pt2d = new Point2d(x,y);
		Point3D pt3d = null; // FIXME: fix code: new Point3f(x,y,0);
		String str = x+";"+y; 
		AttributeValue attr = new AttributeValueImpl(str);
		
		assertEquals(AttributeType.STRING, attr.getType());

		assertTrue(attr.isAssigned());
		assertTrue(attr.isBaseType());
		assertFalse(attr.isObjectValue());
		
		assertEquals(str,attr.getValue());
		assertAttributeException(attr,"getBoolean"); 
		assertAttributeException(attr,"getColor"); 
		assertAttributeException(attr,"getDate"); 
		assertAttributeException(attr,"getImage"); 
		assertAttributeException(attr,"getInteger"); 
		assertAttributeException(attr,"getReal"); 
		assertAttributeException(attr,"getTimestamp"); 
		assertEquals(str,attr.getString());
		assertAttributeException(attr,"getJavaObject"); 
		assertEquals(pt2d,attr.getPoint());
		assertEquals(pt3d,attr.getPoint3D());
		assertEquals(new Point2D[]{pt2d},attr.getPolyline());
		assertEquals(new Point3D[]{pt3d},attr.getPolyline3D());
	}

	@Test
	public void attributeValueImplString_Point3D() throws Exception {
		double x = Math.random()+256;
		double y = Math.random()+256;
		double z = Math.random()+256;
		Point2D pt2d = new Point2d(x,y);
		Point2D pt2d2 = new Point2d(z,0);
		Point3D pt3d = null; // FIXME: fix code: new Point3f(x,y,z);
		String str = x+";"+y+";"+z;  
		AttributeValue attr = new AttributeValueImpl(str);
		
		assertEquals(AttributeType.STRING, attr.getType());

		assertTrue(attr.isAssigned());
		assertTrue(attr.isBaseType());
		assertFalse(attr.isObjectValue());
		
		assertEquals(str,attr.getValue());
		assertAttributeException(attr,"getBoolean"); 
		assertAttributeException(attr,"getColor"); 
		assertAttributeException(attr,"getDate"); 
		assertAttributeException(attr,"getImage"); 
		assertAttributeException(attr,"getInteger"); 
		assertAttributeException(attr,"getReal"); 
		assertAttributeException(attr,"getTimestamp"); 
		assertEquals(str,attr.getString());
		assertAttributeException(attr,"getJavaObject"); 
		assertEquals(pt2d,attr.getPoint());
		assertEquals(pt3d,attr.getPoint3D());
		assertEquals(new Point2D[]{pt2d,pt2d2},attr.getPolyline());
		assertEquals(new Point3D[]{pt3d},attr.getPolyline3D());
	}

	@Test
	public void attributeValueImplPoint2DArray() throws Exception {
		double x1 = Math.random();
		double y1 = Math.random();
		double x2 = Math.random();
		double y2 = Math.random();

		Point2D pt1 = new Point2d(x1,y1);
		Point2D pt2 = new Point2d(x2,y2);
		
		Point2D[] list = new Point2D[]{ pt1, pt2 };
		Point3D[] list2 = new Point3D[]{ /* FIXME: fix code: new Point3f(x1,y1,0), new Point3f(x2,y2,0)*/ };

		String str = ((float)x1)+";"+((float)y1)+";"+((float)x2)+";"+((float)y2);   

		AttributeValue attr = new AttributeValueImpl(list);
		
		assertEquals(AttributeType.POLYLINE, attr.getType());

		assertTrue(attr.isAssigned());
		assertFalse(attr.isBaseType());
		assertTrue(attr.isObjectValue());
		
		assertEquals(list,(Point2D[])attr.getValue());
		assertAttributeException(attr,"getBoolean"); 
		assertAttributeException(attr,"getColor"); 
		assertAttributeException(attr,"getDate"); 
		assertAttributeException(attr,"getImage"); 
		assertAttributeException(attr,"getInteger"); 
		assertAttributeException(attr,"getReal"); 
		assertAttributeException(attr,"getTimestamp"); 
		assertEquals(str,attr.getString());
		assertTrue(Arrays.equals(list, (Point2D[])attr.getJavaObject()));
		assertAttributeException(attr,"getPoint"); 
		assertAttributeException(attr,"getPoint3D"); 
		assertEquals(list,attr.getPolyline());
		assertEquals(list2,attr.getPolyline3D());
	}

	@Test
	public void attributeValueImplPoint3DArray() throws Exception {
		double x1 = Math.random();
		double y1 = Math.random();
		double z1 = Math.random();
		double x2 = Math.random();
		double y2 = Math.random();
		double z2 = Math.random();

		Point3D pt1 = null; // FIXME: fix code: new Point3f(x1,y1,z1);
		Point3D pt2 = null; // FIXME: fix code: new Point3f(x2,y2,z2);
		
		Point3D[] list = new Point3D[]{ pt1, pt2 };
		Point2D[] list2 = new Point2D[]{ new Point2d(x1,y1), new Point2d(x2,y2) };

		String str = ((float)x1)+";"+((float)y1)+";"+((float)z1)+";"+((float)x2)+";"+((float)y2)+";"+((float)z2);     

		AttributeValue attr = new AttributeValueImpl(list);
		
		assertEquals(AttributeType.POLYLINE3D, attr.getType());

		assertTrue(attr.isAssigned());
		assertFalse(attr.isBaseType());
		assertTrue(attr.isObjectValue());
		
		assertEquals(list,(Point3D[])attr.getValue());
		assertAttributeException(attr,"getBoolean"); 
		assertAttributeException(attr,"getColor"); 
		assertAttributeException(attr,"getDate"); 
		assertAttributeException(attr,"getImage"); 
		assertAttributeException(attr,"getInteger"); 
		assertAttributeException(attr,"getReal"); 
		assertAttributeException(attr,"getTimestamp"); 
		assertEquals(str,attr.getString());
		assertTrue(Arrays.equals(list, (Point3D[])attr.getJavaObject()));
		assertAttributeException(attr,"getPoint"); 
		assertAttributeException(attr,"getPoint3D"); 
		assertEquals(list2,attr.getPolyline());
		assertEquals(list,attr.getPolyline3D());
	}

	@Test
	public void cast() throws AttributeException {
		AttributeValue attr1, attr2;
		String msg, str;
		AttributeType source, target;
		long time;
		Color col;
		Date dt;
		DateFormat format;
		Point2D pt2d;
		Point3D pt3d;
		
		//
		// SOURCE: BOOLEAN
		//
		
		source = AttributeType.BOOLEAN;
		target = AttributeType.BOOLEAN;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getBoolean());

		source = AttributeType.BOOLEAN;
		target = AttributeType.COLOR;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getColor());

		source = AttributeType.BOOLEAN;
		target = AttributeType.DATE;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		time = System.currentTimeMillis();
		assertTrue(msg,((Date)attr2.getValue()).getTime()<=time);
		assertTrue(msg,attr2.getDate().getTime()<=time);

		source = AttributeType.BOOLEAN;
		target = AttributeType.IMAGE;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertException(msg, AttributeNotInitializedException.class, attr2, "getImage"); 

		source = AttributeType.BOOLEAN;
		target = AttributeType.INTEGER;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getInteger());

		source = AttributeType.BOOLEAN;
		target = AttributeType.OBJECT;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getJavaObject());

		source = AttributeType.BOOLEAN;
		target = AttributeType.POINT;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getPoint());

		source = AttributeType.BOOLEAN;
		target = AttributeType.POINT3D;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getPoint3D());

		source = AttributeType.BOOLEAN;
		target = AttributeType.POLYLINE;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,(Point2D[])target.getDefaultValue(), (Point2D[])attr2.getValue());
		assertEquals(msg,(Point2D[])target.getDefaultValue(), attr2.getPolyline());

		source = AttributeType.BOOLEAN;
		target = AttributeType.POLYLINE3D;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,(Point3D[])target.getDefaultValue(), (Point3D[])attr2.getValue());
		assertEquals(msg,(Point3D[])target.getDefaultValue(), attr2.getPolyline3D());

		source = AttributeType.BOOLEAN;
		target = AttributeType.REAL;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getReal());

		source = AttributeType.BOOLEAN;
		target = AttributeType.STRING;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,Boolean.toString((Boolean)AttributeType.BOOLEAN.getDefaultValue()), attr2.getValue());
		assertEquals(msg,Boolean.toString((Boolean)AttributeType.BOOLEAN.getDefaultValue()), attr2.getString());

		source = AttributeType.BOOLEAN;
		target = AttributeType.TIMESTAMP;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertTrue(msg,attr2.getValue() instanceof Timestamp);
		time = System.currentTimeMillis();
		assertTrue(msg,((Number)attr2.getValue()).longValue()<=time);
		assertTrue(msg,attr2.getTimestamp()<=time);

		//
		// SOURCE: COLOR
		//
		
		source = AttributeType.COLOR;
		target = AttributeType.BOOLEAN;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getBoolean());

		source = AttributeType.COLOR;
		target = AttributeType.COLOR;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getColor());

		source = AttributeType.COLOR;
		target = AttributeType.DATE;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		time = System.currentTimeMillis();
		assertTrue(msg,((Date)attr2.getValue()).getTime()<=time);
		assertTrue(msg,attr2.getDate().getTime()<=time);

		source = AttributeType.COLOR;
		target = AttributeType.IMAGE;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertException(msg, AttributeNotInitializedException.class, attr2, "getImage"); 

		source = AttributeType.COLOR;
		target = AttributeType.INTEGER;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,((Color)source.getDefaultValue()).getRGB(), ((Long)attr2.getValue()).intValue());
		assertEquals(msg,((Color)source.getDefaultValue()).getRGB(), (int)attr2.getInteger());

		source = AttributeType.COLOR;
		target = AttributeType.OBJECT;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,VectorToolkit.color(0,0,0), attr2.getValue());
		assertEquals(msg,VectorToolkit.color(0,0,0), attr2.getJavaObject());

		source = AttributeType.COLOR;
		target = AttributeType.POINT;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getPoint());

		source = AttributeType.COLOR;
		target = AttributeType.POINT3D;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getPoint3D());

		source = AttributeType.COLOR;
		target = AttributeType.POLYLINE;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,(Point2D[])target.getDefaultValue(), (Point2D[])attr2.getValue());
		assertEquals(msg,(Point2D[])target.getDefaultValue(), attr2.getPolyline());

		source = AttributeType.COLOR;
		target = AttributeType.POLYLINE3D;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,(Point3D[])target.getDefaultValue(), (Point3D[])attr2.getValue());
		assertEquals(msg,(Point3D[])target.getDefaultValue(), attr2.getPolyline3D());

		source = AttributeType.COLOR;
		target = AttributeType.REAL;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,((Color)source.getDefaultValue()).getRGB(), ((Number)attr2.getValue()).intValue());
		assertEquals(msg,((Color)source.getDefaultValue()).getRGB(), (int)attr2.getReal());

		source = AttributeType.COLOR;
		target = AttributeType.STRING;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		col = (Color)source.getDefaultValue();
		str = col.getRed()+";"+col.getGreen()+";"+col.getBlue()+";"+col.getAlpha();   
		assertEquals(msg,str, attr2.getValue());
		assertEquals(msg,str, attr2.getString());

		source = AttributeType.COLOR;
		target = AttributeType.TIMESTAMP;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertTrue(msg,attr2.getValue() instanceof Timestamp);
		time = System.currentTimeMillis();
		assertTrue(msg,((Number)attr2.getValue()).longValue()<=time);
		assertTrue(msg,attr2.getTimestamp()<=time);

		//
		// SOURCE: DATE
		//
		
		source = AttributeType.DATE;
		target = AttributeType.BOOLEAN;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getBoolean());

		source = AttributeType.DATE;
		target = AttributeType.DATE;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		time = System.currentTimeMillis();
		assertTrue(msg,((Date)attr2.getValue()).getTime()<=time);
		assertTrue(msg,attr2.getDate().getTime()<=time);

		source = AttributeType.DATE;
		target = AttributeType.IMAGE;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertException(msg, AttributeNotInitializedException.class, attr2, "getImage"); 

		source = AttributeType.DATE;
		target = AttributeType.INTEGER;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();
		dt = attr2.getDate();
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,dt.getTime(), attr2.getValue());
		assertEquals(msg,dt.getTime(), attr2.getInteger());

		source = AttributeType.DATE;
		target = AttributeType.OBJECT;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertNotNull(msg, attr2.getValue());
		assertNotNull(msg,attr2.getJavaObject());

		source = AttributeType.DATE;
		target = AttributeType.POINT;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		Point2D pt = attr2.getPoint();
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,pt, attr2.getValue());
		assertEquals(msg,pt, attr2.getPoint());

		source = AttributeType.DATE;
		target = AttributeType.POINT3D;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		Point3D pt3 = attr2.getPoint3D();
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,pt3, attr2.getValue());
		assertEquals(msg,pt3, attr2.getPoint3D());

		source = AttributeType.DATE;
		target = AttributeType.POLYLINE;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,(Point2D[])target.getDefaultValue(), (Point2D[])attr2.getValue());
		assertEquals(msg,(Point2D[])target.getDefaultValue(), attr2.getPolyline());

		source = AttributeType.DATE;
		target = AttributeType.POLYLINE3D;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,(Point3D[])target.getDefaultValue(), (Point3D[])attr2.getValue());
		assertEquals(msg,(Point3D[])target.getDefaultValue(), attr2.getPolyline3D());

		source = AttributeType.DATE;
		target = AttributeType.REAL;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();
		dt = attr2.getDate();
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,dt.getTime(), ((Double)attr2.getValue()).longValue());
		assertEquals(msg,dt.getTime(), (long)attr2.getReal());

		source = AttributeType.DATE;
		target = AttributeType.STRING;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		dt = (Date)source.getDefaultValue();
		format = new SimpleDateFormat("yyyy-MM-dd"); 
		str = format.format(dt);
		assertEquals(msg,str, attr2.getValue());
		assertEquals(msg,str, attr2.getString());

		source = AttributeType.DATE;
		target = AttributeType.TIMESTAMP;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertTrue(msg,attr2.getValue() instanceof Timestamp);
		time = System.currentTimeMillis();
		assertTrue(msg,((Number)attr2.getValue()).longValue()<=time);
		assertTrue(msg,attr2.getTimestamp()<=time);

		//
		// SOURCE: ICON
		//
		
		source = AttributeType.IMAGE;
		target = AttributeType.BOOLEAN;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getBoolean());

		source = AttributeType.IMAGE;
		target = AttributeType.COLOR;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getColor());

		source = AttributeType.IMAGE;
		target = AttributeType.DATE;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		time = System.currentTimeMillis();
		assertTrue(msg,((Date)attr2.getValue()).getTime()<=time);
		assertTrue(msg,attr2.getDate().getTime()<=time);

		source = AttributeType.IMAGE;
		target = AttributeType.IMAGE;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertException(msg, AttributeNotInitializedException.class, attr2, "getImage"); 

		source = AttributeType.IMAGE;
		target = AttributeType.INTEGER;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(),attr2.getValue());
		assertEquals(msg,target.getDefaultValue(),attr2.getInteger());

		source = AttributeType.IMAGE;
		target = AttributeType.OBJECT;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertNull(msg,attr2.getValue());
		assertNull(msg,attr2.getJavaObject());

		source = AttributeType.IMAGE;
		target = AttributeType.POINT;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getPoint());

		source = AttributeType.IMAGE;
		target = AttributeType.POINT3D;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getPoint3D());

		source = AttributeType.IMAGE;
		target = AttributeType.POLYLINE;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,(Point2D[])target.getDefaultValue(), (Point2D[])attr2.getValue());
		assertEquals(msg,(Point2D[])target.getDefaultValue(), attr2.getPolyline());

		source = AttributeType.IMAGE;
		target = AttributeType.POLYLINE3D;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,(Point3D[])target.getDefaultValue(), (Point3D[])attr2.getValue());
		assertEquals(msg,(Point3D[])target.getDefaultValue(), attr2.getPolyline3D());

		source = AttributeType.IMAGE;
		target = AttributeType.REAL;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getReal());

		source = AttributeType.IMAGE;
		target = AttributeType.STRING;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue().toString(), attr2.getString());

		source = AttributeType.IMAGE;
		target = AttributeType.TIMESTAMP;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertTrue(msg,attr2.getValue() instanceof Timestamp);
		time = System.currentTimeMillis();
		assertTrue(msg,((Number)attr2.getValue()).longValue()<=time);
		assertTrue(msg,attr2.getTimestamp()<=time);

		//
		// SOURCE: INTEGER
		//
		
		source = AttributeType.INTEGER;
		target = AttributeType.BOOLEAN;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getBoolean());

		source = AttributeType.INTEGER;
		target = AttributeType.COLOR;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getColor());

		source = AttributeType.INTEGER;
		target = AttributeType.DATE;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		time = System.currentTimeMillis();
		assertTrue(msg,((Date)attr2.getValue()).getTime()<=time);
		assertTrue(msg,attr2.getDate().getTime()<=time);

		source = AttributeType.INTEGER;
		target = AttributeType.IMAGE;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertException(msg, AttributeNotInitializedException.class, attr2, "getImage"); 

		source = AttributeType.INTEGER;
		target = AttributeType.INTEGER;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(),attr2.getValue());
		assertEquals(msg,target.getDefaultValue(),attr2.getInteger());

		source = AttributeType.INTEGER;
		target = AttributeType.OBJECT;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertNull(msg,attr2.getValue());
		assertNull(msg,attr2.getJavaObject());

		source = AttributeType.INTEGER;
		target = AttributeType.POINT;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getPoint());

		source = AttributeType.INTEGER;
		target = AttributeType.POINT3D;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getPoint3D());

		source = AttributeType.INTEGER;
		target = AttributeType.POLYLINE;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,(Point2D[])target.getDefaultValue(), (Point2D[])attr2.getValue());
		assertEquals(msg,(Point2D[])target.getDefaultValue(), attr2.getPolyline());

		source = AttributeType.INTEGER;
		target = AttributeType.POLYLINE3D;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,(Point3D[])target.getDefaultValue(), (Point3D[])attr2.getValue());
		assertEquals(msg,(Point3D[])target.getDefaultValue(), attr2.getPolyline3D());

		source = AttributeType.INTEGER;
		target = AttributeType.REAL;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getReal());

		source = AttributeType.INTEGER;
		target = AttributeType.STRING;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,"0", attr2.getValue()); 
		assertEquals(msg,"0", attr2.getString()); 

		source = AttributeType.INTEGER;
		target = AttributeType.TIMESTAMP;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertTrue(msg,attr2.getValue() instanceof Timestamp);
		time = System.currentTimeMillis();
		assertTrue(msg,((Number)attr2.getValue()).longValue()<=time);
		assertTrue(msg,attr2.getTimestamp()<=time);

		//
		// SOURCE: OBJECT
		//
		
		source = AttributeType.OBJECT;
		target = AttributeType.BOOLEAN;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getBoolean());

		source = AttributeType.OBJECT;
		target = AttributeType.COLOR;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getColor());

		source = AttributeType.OBJECT;
		target = AttributeType.DATE;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		time = System.currentTimeMillis();
		assertTrue(msg,((Date)attr2.getValue()).getTime()<=time);
		assertTrue(msg,attr2.getDate().getTime()<=time);

		source = AttributeType.OBJECT;
		target = AttributeType.IMAGE;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertException(msg, AttributeNotInitializedException.class, attr2, "getImage"); 

		source = AttributeType.OBJECT;
		target = AttributeType.INTEGER;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(),attr2.getValue());
		assertException(msg,InvalidAttributeTypeException.class, attr2,"getImage"); 

		source = AttributeType.OBJECT;
		target = AttributeType.OBJECT;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertNull(msg,attr2.getValue());
		assertNull(msg,attr2.getJavaObject());

		source = AttributeType.OBJECT;
		target = AttributeType.POINT;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getPoint());

		source = AttributeType.OBJECT;
		target = AttributeType.POINT3D;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getPoint3D());

		source = AttributeType.OBJECT;
		target = AttributeType.POLYLINE;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,(Point2D[])target.getDefaultValue(), (Point2D[])attr2.getValue());
		assertEquals(msg,(Point2D[])target.getDefaultValue(), attr2.getPolyline());

		source = AttributeType.OBJECT;
		target = AttributeType.POLYLINE3D;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,(Point3D[])target.getDefaultValue(), (Point3D[])attr2.getValue());
		assertEquals(msg,(Point3D[])target.getDefaultValue(), attr2.getPolyline3D());

		source = AttributeType.OBJECT;
		target = AttributeType.REAL;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getReal());

		source = AttributeType.OBJECT;
		target = AttributeType.STRING;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getString());

		source = AttributeType.OBJECT;
		target = AttributeType.TIMESTAMP;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertTrue(msg,attr2.getValue() instanceof Timestamp);
		time = System.currentTimeMillis();
		assertTrue(msg,((Number)attr2.getValue()).longValue()<=time);
		assertTrue(msg,attr2.getTimestamp()<=time);

		//
		// SOURCE: POINT
		//
		
		source = AttributeType.POINT;
		target = AttributeType.BOOLEAN;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getBoolean());

		source = AttributeType.POINT;
		target = AttributeType.COLOR;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getColor());

		source = AttributeType.POINT;
		target = AttributeType.DATE;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		time = System.currentTimeMillis();
		assertTrue(msg,((Date)attr2.getValue()).getTime()<=time);
		assertTrue(msg,attr2.getDate().getTime()<=time);

		source = AttributeType.POINT;
		target = AttributeType.IMAGE;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertException(msg, AttributeNotInitializedException.class, attr2, "getImage"); 

		source = AttributeType.POINT;
		target = AttributeType.INTEGER;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(),attr2.getValue());
		assertEquals(msg,target.getDefaultValue(),attr2.getInteger());

		source = AttributeType.POINT;
		target = AttributeType.OBJECT;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,new Point2d(), attr2.getValue());
		assertEquals(msg,new Point2d(), attr2.getJavaObject());

		source = AttributeType.POINT;
		target = AttributeType.POINT;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getPoint());

		source = AttributeType.POINT;
		target = AttributeType.POINT3D;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getPoint3D());

		source = AttributeType.POINT;
		target = AttributeType.POLYLINE;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,new Point2D[] {(Point2D)source.getDefaultValue()}, (Point2D[])attr2.getValue());
		assertEquals(msg,new Point2D[] {(Point2D)source.getDefaultValue()}, attr2.getPolyline());

		source = AttributeType.POINT;
		target = AttributeType.POLYLINE3D;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		//TODO: fixcode: assertEquals(msg,new Point3D[] {(Point3D)AttributeType.POINT3D.getDefaultValue()}, (Point3D[])attr2.getValue());
		//TODO: fixcode: assertEquals(msg,new Point3D[] {(Point3D)AttributeType.POINT3D.getDefaultValue()}, attr2.getPolyline3D());

		source = AttributeType.POINT;
		target = AttributeType.REAL;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getReal());

		source = AttributeType.POINT;
		target = AttributeType.STRING;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		pt2d = (Point2D)source.getDefaultValue();
		str = pt2d.getX()+";"+pt2d.getY(); 
		assertEquals(msg,str, attr2.getValue());
		assertEquals(msg,str, attr2.getString());

		source = AttributeType.POINT;
		target = AttributeType.TIMESTAMP;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertTrue(msg,attr2.getValue() instanceof Timestamp);
		time = System.currentTimeMillis();
		assertTrue(msg,((Number)attr2.getValue()).longValue()<=time);
		assertTrue(msg,attr2.getTimestamp()<=time);

		//
		// SOURCE: POINT3D
		//
		
		source = AttributeType.POINT3D;
		target = AttributeType.BOOLEAN;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getBoolean());

		source = AttributeType.POINT3D;
		target = AttributeType.COLOR;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getColor());

		source = AttributeType.POINT3D;
		target = AttributeType.DATE;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		time = System.currentTimeMillis();
		assertTrue(msg,((Date)attr2.getValue()).getTime()<=time);
		assertTrue(msg,attr2.getDate().getTime()<=time);

		source = AttributeType.POINT3D;
		target = AttributeType.IMAGE;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertException(msg, AttributeNotInitializedException.class, attr2, "getImage"); 

		source = AttributeType.POINT3D;
		target = AttributeType.INTEGER;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(),attr2.getValue());
		assertEquals(msg,target.getDefaultValue(),attr2.getInteger());

		source = AttributeType.POINT3D;
		target = AttributeType.OBJECT;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,null /*FIXME: fix code: new Point3f()*/, attr2.getValue());
		assertEquals(msg,(Object[]) null /*FIXME: fix code: new Point3f()*/, attr2.getJavaObject());

		source = AttributeType.POINT3D;
		target = AttributeType.POINT;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getPoint());

		source = AttributeType.POINT3D;
		target = AttributeType.POINT3D;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getPoint3D());

		source = AttributeType.POINT3D;
		target = AttributeType.POLYLINE;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		//TODO: fixcode: assertEquals(msg,new Point2D[] {(Point2D)AttributeType.POINT.getDefaultValue()}, (Point2D[])attr2.getValue());
		//TODO: fixcode: assertEquals(msg,new Point2D[] {(Point2D)AttributeType.POINT.getDefaultValue()}, attr2.getPolyline());

		source = AttributeType.POINT3D;
		target = AttributeType.POLYLINE3D;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		//TODO: fixcode: assertEquals(msg,new Point3D[] {(Point3D)AttributeType.POINT3D.getDefaultValue()}, (Point3D[])attr2.getValue());
		//TODO: fixcode: assertEquals(msg,new Point3D[] {(Point3D)AttributeType.POINT3D.getDefaultValue()}, attr2.getPolyline3D());

		source = AttributeType.POINT3D;
		target = AttributeType.REAL;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getReal());

		source = AttributeType.POINT3D;
		target = AttributeType.STRING;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		pt3d = (Point3D)source.getDefaultValue();
		//TODO: fixcode: str = pt2d.getX()+";"+pt2d.getY()+";"+pt3d.getZ();  
		//TODO: fixcode: assertEquals(msg,str, attr2.getValue());
		//TODO: fixcode: assertEquals(msg,str, attr2.getString());

		source = AttributeType.POINT3D;
		target = AttributeType.TIMESTAMP;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertTrue(msg,attr2.getValue() instanceof Timestamp);
		time = System.currentTimeMillis();
		assertTrue(msg,((Number)attr2.getValue()).longValue()<=time);
		assertTrue(msg,attr2.getTimestamp()<=time);

		//
		// SOURCE: POLYLINE
		//
		
		source = AttributeType.POLYLINE;
		target = AttributeType.BOOLEAN;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getBoolean());

		source = AttributeType.POLYLINE;
		target = AttributeType.COLOR;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getColor());

		source = AttributeType.POLYLINE;
		target = AttributeType.DATE;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		time = System.currentTimeMillis();
		assertTrue(msg,((Date)attr2.getValue()).getTime()<=time);
		assertTrue(msg,attr2.getDate().getTime()<=time);

		source = AttributeType.POLYLINE;
		target = AttributeType.IMAGE;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertException(msg, AttributeNotInitializedException.class, attr2, "getImage"); 

		source = AttributeType.POLYLINE;
		target = AttributeType.INTEGER;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(),attr2.getValue());
		assertEquals(msg,target.getDefaultValue(),attr2.getInteger());

		source = AttributeType.POLYLINE;
		target = AttributeType.OBJECT;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertTrue(msg,Arrays.equals(new Point2d[0], (Point2D[])attr2.getValue()));
		assertTrue(msg,Arrays.equals(new Point2d[0], (Point2D[])attr2.getJavaObject()));

		source = AttributeType.POLYLINE;
		target = AttributeType.POINT;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getPoint());

		source = AttributeType.POLYLINE;
		target = AttributeType.POINT3D;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getPoint3D());

		source = AttributeType.POLYLINE;
		target = AttributeType.POLYLINE;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,(Point2D[])source.getDefaultValue(), (Point2D[])attr2.getValue());
		assertEquals(msg,(Point2D[])source.getDefaultValue(), attr2.getPolyline());
		
		source = AttributeType.POLYLINE;
		target = AttributeType.POLYLINE3D;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,(Point3D[])AttributeType.POLYLINE3D.getDefaultValue(), (Point3D[])attr2.getValue());
		assertEquals(msg,(Point3D[])AttributeType.POLYLINE3D.getDefaultValue(), attr2.getPolyline3D());

		source = AttributeType.POLYLINE;
		target = AttributeType.REAL;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getReal());

		source = AttributeType.POLYLINE;
		target = AttributeType.STRING;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		str = ""; 
		assertEquals(msg,str, attr2.getValue());
		assertEquals(msg,str, attr2.getString());

		source = AttributeType.POLYLINE;
		target = AttributeType.TIMESTAMP;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertTrue(msg,attr2.getValue() instanceof Timestamp);
		time = System.currentTimeMillis();
		assertTrue(msg,((Number)attr2.getValue()).longValue()<=time);
		assertTrue(msg,attr2.getTimestamp()<=time);

		//
		// SOURCE: POLYLINE3D
		//
		
		source = AttributeType.POLYLINE3D;
		target = AttributeType.BOOLEAN;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getBoolean());

		source = AttributeType.POLYLINE3D;
		target = AttributeType.COLOR;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getColor());

		source = AttributeType.POLYLINE3D;
		target = AttributeType.DATE;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		time = System.currentTimeMillis();
		assertTrue(msg,((Date)attr2.getValue()).getTime()<=time);
		assertTrue(msg,attr2.getDate().getTime()<=time);

		source = AttributeType.POLYLINE3D;
		target = AttributeType.IMAGE;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertException(msg, AttributeNotInitializedException.class, attr2, "getImage"); 

		source = AttributeType.POLYLINE3D;
		target = AttributeType.INTEGER;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(),attr2.getValue());
		assertEquals(msg,target.getDefaultValue(),attr2.getInteger());

		source = AttributeType.POLYLINE3D;
		target = AttributeType.OBJECT;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertTrue(msg,Arrays.equals(null /* FIXME: fix code new Point3f[0]*/, (Point3D[])attr2.getValue()));
		assertTrue(msg,Arrays.equals(null /* FIXME: fix code new Point3f[0]*/, (Point3D[])attr2.getJavaObject()));

		source = AttributeType.POLYLINE3D;
		target = AttributeType.POINT;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getPoint());

		source = AttributeType.POLYLINE3D;
		target = AttributeType.POINT3D;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getPoint3D());

		source = AttributeType.POLYLINE3D;
		target = AttributeType.POLYLINE;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,(Point2D[])AttributeType.POLYLINE.getDefaultValue(), (Point2D[])attr2.getValue());
		assertEquals(msg,(Point2D[])AttributeType.POLYLINE.getDefaultValue(), attr2.getPolyline());
		
		source = AttributeType.POLYLINE3D;
		target = AttributeType.POLYLINE3D;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,(Point3D[])AttributeType.POLYLINE3D.getDefaultValue(), (Point3D[])attr2.getValue());
		assertEquals(msg,(Point3D[])AttributeType.POLYLINE3D.getDefaultValue(), attr2.getPolyline3D());

		source = AttributeType.POLYLINE3D;
		target = AttributeType.REAL;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getReal());

		source = AttributeType.POLYLINE3D;
		target = AttributeType.STRING;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		str = ""; 
		assertEquals(msg,str, attr2.getValue());
		assertEquals(msg,str, attr2.getString());

		source = AttributeType.POLYLINE3D;
		target = AttributeType.TIMESTAMP;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertTrue(msg,attr2.getValue() instanceof Timestamp);
		time = System.currentTimeMillis();
		assertTrue(msg,((Number)attr2.getValue()).longValue()<=time);
		assertTrue(msg,attr2.getTimestamp()<=time);

		//
		// SOURCE: REAL
		//
		
		source = AttributeType.REAL;
		target = AttributeType.BOOLEAN;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getBoolean());

		source = AttributeType.REAL;
		target = AttributeType.COLOR;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getColor());

		source = AttributeType.REAL;
		target = AttributeType.DATE;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		time = System.currentTimeMillis();
		assertTrue(msg,((Date)attr2.getValue()).getTime()<=time);
		assertTrue(msg,attr2.getDate().getTime()<=time);

		source = AttributeType.REAL;
		target = AttributeType.IMAGE;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertException(msg, AttributeNotInitializedException.class, attr2, "getImage"); 

		source = AttributeType.REAL;
		target = AttributeType.INTEGER;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(),attr2.getValue());
		assertEquals(msg,target.getDefaultValue(),attr2.getInteger());

		source = AttributeType.REAL;
		target = AttributeType.OBJECT;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertNull(msg,attr2.getValue());
		assertNull(msg,attr2.getJavaObject());

		source = AttributeType.REAL;
		target = AttributeType.POINT;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getPoint());

		source = AttributeType.REAL;
		target = AttributeType.POINT3D;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getPoint3D());

		source = AttributeType.REAL;
		target = AttributeType.POLYLINE;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,(Point2D[])target.getDefaultValue(), (Point2D[])attr2.getValue());
		assertEquals(msg,(Point2D[])target.getDefaultValue(), attr2.getPolyline());

		source = AttributeType.REAL;
		target = AttributeType.POLYLINE3D;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,(Point3D[])target.getDefaultValue(), (Point3D[])attr2.getValue());
		assertEquals(msg,(Point3D[])target.getDefaultValue(), attr2.getPolyline3D());

		source = AttributeType.REAL;
		target = AttributeType.REAL;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getReal());

		source = AttributeType.REAL;
		target = AttributeType.STRING;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,Double.toString(0), attr2.getValue());
		assertEquals(msg,Double.toString(0), attr2.getString());

		source = AttributeType.REAL;
		target = AttributeType.TIMESTAMP;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertTrue(msg,attr2.getValue() instanceof Timestamp);
		time = System.currentTimeMillis();
		assertTrue(msg,((Number)attr2.getValue()).longValue()<=time);
		assertTrue(msg,attr2.getTimestamp()<=time);

		//
		// SOURCE: STRING
		//
		
		source = AttributeType.STRING;
		target = AttributeType.BOOLEAN;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getBoolean());

		source = AttributeType.STRING;
		target = AttributeType.COLOR;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getColor());

		source = AttributeType.STRING;
		target = AttributeType.DATE;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		time = System.currentTimeMillis();
		assertTrue(msg,((Date)attr2.getValue()).getTime()<=time);
		assertTrue(msg,attr2.getDate().getTime()<=time);

		source = AttributeType.STRING;
		target = AttributeType.IMAGE;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertException(msg, AttributeNotInitializedException.class, attr2, "getImage"); 

		source = AttributeType.STRING;
		target = AttributeType.INTEGER;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(),attr2.getValue());
		assertEquals(msg,target.getDefaultValue(),attr2.getInteger());

		source = AttributeType.STRING;
		target = AttributeType.OBJECT;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertNull(msg,attr2.getValue());
		assertNull(msg,attr2.getJavaObject());

		source = AttributeType.STRING;
		target = AttributeType.POINT;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getPoint());

		source = AttributeType.STRING;
		target = AttributeType.POINT3D;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getPoint3D());

		source = AttributeType.STRING;
		target = AttributeType.POLYLINE;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,(Point2D[])target.getDefaultValue(), (Point2D[])attr2.getValue());
		assertEquals(msg,(Point2D[])target.getDefaultValue(), attr2.getPolyline());

		source = AttributeType.STRING;
		target = AttributeType.POLYLINE3D;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,(Point3D[])target.getDefaultValue(), (Point3D[])attr2.getValue());
		assertEquals(msg,(Point3D[])target.getDefaultValue(), attr2.getPolyline3D());

		source = AttributeType.STRING;
		target = AttributeType.REAL;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertEquals(msg,target.getDefaultValue(), attr2.getReal());

		source = AttributeType.STRING;
		target = AttributeType.STRING;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		str = ""; 
		assertEquals(msg,str, attr2.getValue());
		assertEquals(msg,str, attr2.getString());

		source = AttributeType.STRING;
		target = AttributeType.TIMESTAMP;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertTrue(msg,attr2.getValue() instanceof Timestamp);
		time = System.currentTimeMillis();
		assertTrue(msg,((Number)attr2.getValue()).longValue()<=time);
		assertTrue(msg,attr2.getTimestamp()<=time);

		//
		// SOURCE: TIMESTAMP
		//
		
		source = AttributeType.TIMESTAMP;
		target = AttributeType.BOOLEAN;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,true, attr2.getValue());
		assertEquals(msg,true, attr2.getBoolean());

		source = AttributeType.TIMESTAMP;
		target = AttributeType.DATE;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		time = System.currentTimeMillis();
		assertTrue(msg,((Date)attr2.getValue()).getTime()<=time);
		assertTrue(msg,attr2.getDate().getTime()<=time);

		source = AttributeType.TIMESTAMP;
		target = AttributeType.IMAGE;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,target.getDefaultValue(), attr2.getValue());
		assertException(msg, AttributeNotInitializedException.class, attr2, "getImage"); 

		source = AttributeType.TIMESTAMP;
		target = AttributeType.INTEGER;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		time = System.currentTimeMillis();
		assertTrue(msg,(Long)attr2.getValue()<=time);
		assertTrue(msg,attr2.getTimestamp()<=time);

		source = AttributeType.TIMESTAMP;
		target = AttributeType.OBJECT;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertNull(msg,attr2.getValue());
		assertNull(msg,attr2.getJavaObject());

		source = AttributeType.TIMESTAMP;
		target = AttributeType.POINT;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();
		time = attr2.getTimestamp();
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,new Point2d(time,0), attr2.getValue());
		assertEquals(msg,new Point2d(time,0), attr2.getPoint());

		source = AttributeType.TIMESTAMP;
		target = AttributeType.POINT3D;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();
		time = attr2.getTimestamp();
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,null /* FIXME: fix code new Point3f(time,0,0)*/, attr2.getValue());
		assertEquals(msg,null /* FIXME: fix code new Point3f(time,0,0)*/, attr2.getPoint3D());

		source = AttributeType.TIMESTAMP;
		target = AttributeType.POLYLINE;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,(Point2D[])target.getDefaultValue(), (Point2D[])attr2.getValue());
		assertEquals(msg,(Point2D[])target.getDefaultValue(), attr2.getPolyline());

		source = AttributeType.TIMESTAMP;
		target = AttributeType.POLYLINE3D;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,(Point3D[])target.getDefaultValue(), (Point3D[])attr2.getValue());
		assertEquals(msg,(Point3D[])target.getDefaultValue(), attr2.getPolyline3D());

		source = AttributeType.TIMESTAMP;
		target = AttributeType.REAL;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();
		time = attr2.getTimestamp();
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertEquals(msg,Double.valueOf(time), attr2.getValue());
		assertEquals(msg,Long.valueOf(time).doubleValue(), attr2.getReal());

		source = AttributeType.TIMESTAMP;
		target = AttributeType.STRING;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();
		time = attr2.getTimestamp();
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		str = format.format(new Date(time));
		assertEquals(msg,str, attr2.getValue());
		assertEquals(msg,str, attr2.getString());

		source = AttributeType.TIMESTAMP;
		target = AttributeType.TIMESTAMP;
		msg = "from '"+source.toString()+"' to '"+target.toString()+"'";   
		attr1 = new AttributeValueImpl(source);
		attr2 = new AttributeValueImpl(source);
		attr2.setToDefault();		
		attr1.cast(target);
		assertFalse(msg,attr1.isAssigned());
		attr2.cast(target);
		assertTrue(msg,attr2.isAssigned());
		assertTrue(msg,attr2.getValue() instanceof Timestamp);
		time = System.currentTimeMillis();
		assertTrue(msg,((Number)attr2.getValue()).longValue()<=time);
		assertTrue(msg,attr2.getTimestamp()<=time);

	}
	
	@Test
	public void testEquals() {
		AttributeValueImpl attr = new AttributeValueImpl();
		
		attr.setBoolean(true);
		assertTrue(attr.equals(new AttributeValueImpl(true)));
		assertFalse(attr.equals(new AttributeValueImpl(false)));
		assertTrue(attr.equals(true));
		assertFalse(attr.equals(false));
		assertTrue(attr.equals(new AttributeValueImpl("true"))); 
		assertFalse(attr.equals(new AttributeValueImpl("false"))); 
		assertTrue(attr.equals("true")); 
		assertFalse(attr.equals("false")); 
		assertFalse(attr.equals(new AttributeValueImpl(1.)));
		assertFalse(attr.equals(new AttributeValueImpl("1."))); 
		assertFalse(attr.equals(1.));
		assertFalse(attr.equals("toto")); 

		attr.setBoolean(false);
		assertFalse(attr.equals(new AttributeValueImpl(true)));
		assertTrue(attr.equals(new AttributeValueImpl(false)));
		assertFalse(attr.equals(true));
		assertTrue(attr.equals(false));
		assertFalse(attr.equals(new AttributeValueImpl("true"))); 
		assertTrue(attr.equals(new AttributeValueImpl("false"))); 
		assertFalse(attr.equals("true")); 
		assertTrue(attr.equals("false")); 
		assertFalse(attr.equals(new AttributeValueImpl(1.)));
		assertFalse(attr.equals(new AttributeValueImpl("1."))); 
		assertFalse(attr.equals(1.));
		assertFalse(attr.equals("toto")); 
	}
	
	@Test
	public void parse() {
		AttributeValueImpl v;
		
		v = AttributeValueImpl.parse("127.0.0.1"); 
		assertSame(AttributeType.INET_ADDRESS, v.getType());

		v = AttributeValueImpl.parse("localhost"); 
		assertSame(AttributeType.INET_ADDRESS, v.getType());

		v = AttributeValueImpl.parse("java.lang.String"); 
		assertSame(AttributeType.TYPE, v.getType());

		v = AttributeValueImpl.parse(AttributeType.class.getName()+"."+AttributeType.ENUMERATION.toString()); 
		assertSame(AttributeType.ENUMERATION, v.getType());

		v = AttributeValueImpl.parse("3eade434-b267-4ffa-a574-2e2cbff0151a"); 
		assertSame(AttributeType.UUID, v.getType());

		v = AttributeValueImpl.parse("134"); 
		assertSame(AttributeType.INTEGER, v.getType());

		v = AttributeValueImpl.parse("-134"); 
		assertSame(AttributeType.INTEGER, v.getType());

		v = AttributeValueImpl.parse("134e34"); 
		assertSame(AttributeType.REAL, v.getType());

		v = AttributeValueImpl.parse("-134.5"); 
		assertSame(AttributeType.REAL, v.getType());

		v = AttributeValueImpl.parse("2012-11-30 18:22:34"); 
		assertSame(AttributeType.DATE, v.getType());

		v = AttributeValueImpl.parse("Fri, 30 Nov 2012 18:22:42 +0100"); 
		assertSame(AttributeType.DATE, v.getType());

		v = AttributeValueImpl.parse("True"); 
		assertSame(AttributeType.BOOLEAN, v.getType());
		
		v = AttributeValueImpl.parse("False"); 
		assertSame(AttributeType.BOOLEAN, v.getType());

		v = AttributeValueImpl.parse("TrUe"); 
		assertSame(AttributeType.BOOLEAN, v.getType());

		v = AttributeValueImpl.parse("http://www.multiagent.fr"); 
		assertSame(AttributeType.URL, v.getType());

		v = AttributeValueImpl.parse("mailto:stephane.galland@utbm.fr"); 
		assertSame(AttributeType.URL, v.getType());

		v = AttributeValueImpl.parse("urn:isbn:096139210x"); 
		assertSame(AttributeType.URI, v.getType());

		v = AttributeValueImpl.parse("1;2;3;4;5;6;7;8;9"); 
		assertSame(AttributeType.POLYLINE3D, v.getType());

		v = AttributeValueImpl.parse("1;2;3;4;5;6;7;8"); 
		assertSame(AttributeType.POLYLINE, v.getType());

		v = AttributeValueImpl.parse("1;2;3;4"); 
		assertSame(AttributeType.COLOR, v.getType());

		v = AttributeValueImpl.parse("1;2;3"); 
		assertSame(AttributeType.COLOR, v.getType());

		v = AttributeValueImpl.parse("1;2;300"); 
		//TODO: fixcode: assertSame(AttributeType.POINT3D, v.getType());

		v = AttributeValueImpl.parse("1;2"); 
		//TODO: fixcode: assertSame(AttributeType.POINT, v.getType());

		v = AttributeValueImpl.parse("blablabla"); 
		assertSame(AttributeType.STRING, v.getType());
	}

}
