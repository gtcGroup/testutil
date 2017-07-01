/*
 * [Licensed per the Open Source "MIT License".]
 * 
 * Copyright (c) 1999 - 2008 by
 * Global Technology Consulting Group, Inc. at
 * http://gtcGroup.com
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.gtcgroup.testutil.bo.dependency;

/**
 * <p>
 * This business object test class encapsulates object attributes.
 * </p>
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 1999 - 2008 by Global Technology Consulting Group, Inc. at <a
 * href="http://gtcGroup.com">gtcGroup.com </a>.
 * </p>
 * 
 * @author MarvinToll@gtcGroup.com
 * @since v. 1.0
 */

public class IncludePrimitiveReturnTypeBO extends BaseTestBO {

	/** Attribute. */
	Boolean blnTestA;

	/** Attribute. */
	boolean blnTestB;

	/** Attribute. */
	Byte byteA;

	/** Attribute. */
	byte byteB;

	/** Attribute. */
	Character charA;

	/** Attribute. */
	char charB;

	/** Attribute. */
	Double doubleA;

	/** Attribute. */
	double doubleB;

	/** Attribute. */
	Float floatA;

	/** Attribute. */
	float floatB;

	/** Attribute. */
	Integer intA;

	/** Attribute. */
	int intB;

	/** Attribute. */
	Long longA;

	/** Attribute. */
	long longB;

	/** Attribute. */
	Short shortA;

	/** Attribute. */
	short shortB;

	/**
	 * @return Returns the blnTestA.
	 */
	public boolean isBlnTest1() {
		return this.blnTestA.booleanValue();
	}

	/**
	 * @param blnTestA
	 *            The blnTestA to set.
	 */
	public void setBlnTest1(final Boolean blnTestA) {
		this.blnTestA = blnTestA;
	}

	/**
	 * @return Returns the blnTestA.
	 */
	public Boolean isBlnTest2() {
		return this.blnTestA;
	}

	/**
	 * @param blnTestA
	 *            The blnTestA to set.
	 */
	public void setBlnTest2(final boolean blnTestA) {
		this.blnTestA = new Boolean(blnTestA);
	}

	/**
	 * @return Returns the blnTestB.
	 */
	public boolean isBlnTest3() {
		return this.blnTestB;
	}

	/**
	 * @param blnTestB
	 *            The blnTestB to set.
	 */
	public void setBlnTest3(final Boolean blnTestB) {
		this.blnTestB = blnTestB.booleanValue();
	}

	/**
	 * @return Returns the blnTestB.
	 */
	public Boolean isBlnTest4() {
		return new Boolean(this.blnTestB);
	}

	/**
	 * @param blnTestB
	 *            The blnTestB to set.
	 */
	public void setBlnTest4(final boolean blnTestB) {
		this.blnTestB = blnTestB;
	}

	/**
	 * @return Returns the byteA.
	 */
	public byte getByte1() {
		return this.byteA.byteValue();
	}

	/**
	 * @param byteA
	 *            The byteA to set.
	 */
	public void setByte1(final Byte byteA) {
		this.byteA = byteA;
	}

	/**
	 * @return Returns the byte1.
	 */
	public Byte getByte2() {
		return this.byteA;
	}

	/**
	 * @param byteA
	 *            The byteA to set.
	 */
	public void setByte2(final byte byteA) {
		this.byteA = new Byte(byteA);
	}

	/**
	 * @return Returns the byteB.
	 */
	public byte getByte3() {
		return this.byteB;
	}

	/**
	 * @param byteB
	 *            The byteB to set.
	 */
	public void setByte3(final Byte byteB) {
		this.byteB = byteB.byteValue();
	}

	/**
	 * @return Returns the byteB.
	 */
	public Byte getByte4() {
		return new Byte(this.byteB);
	}

	/**
	 * @param byteB
	 *            The byteB to set.
	 */
	public void setByte4(final byte byteB) {
		this.byteB = byteB;
	}

	/**
	 * @return Returns the charA.
	 */
	public char getChar1() {
		return this.charA.charValue();
	}

	/**
	 * @param charA
	 *            The charA to set.
	 */
	public void setChar1(final Character charA) {
		this.charA = charA;
	}

	/**
	 * @return Returns the charA.
	 */
	public Character getChar2() {
		return this.charA;
	}

	/**
	 * @param charA
	 *            The charA to set.
	 */
	public void setChar2(final char charA) {
		this.charA = new Character(charA);
	}

	/**
	 * @return Returns the charB.
	 */
	public char getChar3() {
		return this.charB;
	}

	/**
	 * @param charB
	 *            The charB to set.
	 */
	public void setChar3(final Character charB) {
		this.charB = charB.charValue();
	}

	/**
	 * @return Returns the charB.
	 */
	public Character getChar4() {
		return new Character(this.charB);
	}

	/**
	 * @param charB
	 *            The charB to set.
	 */
	public void setChar4(final char charB) {
		this.charB = charB;
	}

	/**
	 * @return Returns the doubleA.
	 */
	public double getDouble1() {
		return this.doubleA.doubleValue();
	}

	/**
	 * @param doubleA
	 *            The doubleA to set.
	 */
	public void setDouble1(final Double doubleA) {
		this.doubleA = doubleA;
	}

	/**
	 * @return Returns the doubleA.
	 */
	public Double getDouble2() {
		return this.doubleA;
	}

	/**
	 * @param doubleA
	 *            The doubleA to set.
	 */
	public void setDouble2(final double doubleA) {
		this.doubleA = new Double(doubleA);
	}

	/**
	 * @return Returns the doubleB.
	 */
	public double getDouble3() {
		return this.doubleB;
	}

	/**
	 * @param doubleB
	 *            The doubleB to set.
	 */
	public void setDouble3(final Double doubleB) {
		this.doubleB = doubleB.doubleValue();
	}

	/**
	 * @return Returns the doubleB.
	 */
	public Double getDouble4() {
		return new Double(this.doubleB);
	}

	/**
	 * @param doubleB
	 *            The doubleB to set.
	 */
	public void setDouble4(final double doubleB) {
		this.doubleB = doubleB;
	}

	/**
	 * @return Returns the floatA.
	 */
	public float getFloat1() {
		return this.floatA.floatValue();
	}

	/**
	 * @param floatA
	 *            The floatA to set.
	 */
	public void setFloat1(final Float floatA) {
		this.floatA = floatA;
	}

	/**
	 * @return Returns the floatA.
	 */
	public Float getFloat2() {
		return this.floatA;
	}

	/**
	 * @param floatA
	 *            The floatA to set.
	 */
	public void setFloat2(final float floatA) {
		this.floatA = new Float(floatA);
	}

	/**
	 * @return Returns the floatB.
	 */
	public float getFloat3() {
		return this.floatB;
	}

	/**
	 * @param floatB
	 *            The floatB to set.
	 */
	public void setFloat3(final Float floatB) {
		this.floatB = floatB.floatValue();
	}

	/**
	 * @return Returns the floatB.
	 */
	public Float getFloat4() {
		return new Float(this.floatB);
	}

	/**
	 * @param floatB
	 *            The floatB to set.
	 */
	public void setFloat4(final float floatB) {
		this.floatB = floatB;
	}

	/**
	 * @return Returns the intA.
	 */
	public int getInt1() {
		return this.intA.intValue();
	}

	/**
	 * @param intA
	 *            The intA to set.
	 */
	public void setInt1(final Integer intA) {
		this.intA = intA;
	}

	/**
	 * @return Returns the int1.
	 */
	public Integer getInt2() {
		return this.intA;
	}

	/**
	 * @param intA
	 *            The intA to set.
	 */
	public void setInt2(final int intA) {
		this.intA = new Integer(intA);
	}

	/**
	 * @return Returns the intB.
	 */
	public int getInt3() {
		return this.intB;
	}

	/**
	 * @param intB
	 *            The intB to set.
	 */
	public void setInt3(final Integer intB) {
		this.intB = intB.intValue();
	}

	/**
	 * @return Returns the intB.
	 */
	public Integer getInt4() {
		return new Integer(this.intB);
	}

	/**
	 * @param intB
	 *            The intB to set.
	 */
	public void setInt4(final int intB) {
		this.intB = intB;
	}

	/**
	 * @return Returns the longA.
	 */
	public long getLong1() {
		return this.longA.longValue();
	}

	/**
	 * @param longA
	 *            The longA to set.
	 */
	public void setLong1(final Long longA) {
		this.longA = longA;
	}

	/**
	 * @return Returns the longA.
	 */
	public Long getLong2() {
		return this.longA;
	}

	/**
	 * @param longA
	 *            The longA to set.
	 */
	public void setLong2(final long longA) {
		this.longA = new Long(longA);
	}

	/**
	 * @return Returns the longB.
	 */
	public long getLong3() {
		return this.longB;
	}

	/**
	 * @param longB
	 *            The longB to set.
	 */
	public void setLong3(final Long longB) {
		this.longB = longB.longValue();
	}

	/**
	 * @return Returns the longB.
	 */
	public Long getLong4() {
		return new Long(this.longB);
	}

	/**
	 * @param longB
	 *            The longB to set.
	 */
	public void setLong4(final long longB) {
		this.longB = longB;
	}

	/**
	 * @return Returns the shortA.
	 */
	public short getShort1() {
		return this.shortA.shortValue();
	}

	/**
	 * @param shortA
	 *            The shortA to set.
	 */
	public void setShort1(final Short shortA) {
		this.shortA = shortA;
	}

	/**
	 * @return Returns the shortA.
	 */
	public Short getShort2() {
		return this.shortA;
	}

	/**
	 * @param shortA
	 *            The shortA to set.
	 */
	public void setShort2(final short shortA) {
		this.shortA = new Short(shortA);
	}

	/**
	 * @return Returns the shortB.
	 */
	public short getShort3() {
		return this.shortB;
	}

	/**
	 * @param shortB
	 *            The shortB to set.
	 */
	public void setShort3(final Short shortB) {
		this.shortB = shortB.shortValue();
	}

	/**
	 * @return Returns the shortB.
	 */
	public Short getShort4() {
		return new Short(this.shortB);
	}

	/**
	 * @param shortB
	 *            The shortB to set.
	 */
	public void setShort4(final short shortB) {
		this.shortB = shortB;
	}
}