/*
 * [Licensed per the Open Source "MIT License".]
 * 
 * Copyright (c) 1999 - 2017 by
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
 * Copyright (c) 1999 - 2017 by Global Technology Consulting Group, Inc. at <a
 * href="http://gtcGroup.com">gtcGroup.com </a>.
 * </p>
 * 
 * @author MarvinToll@gtcGroup.com
 * @since v. 1.0
 */

public class PrimitiveInvalidBO extends BaseTestBO {

	/** Attribute. */
	boolean blnTest0;

	/** Attribute. */
	boolean blnTest1;

	/** Attribute. */
	boolean blnTest2;

	/** Attribute. */
	byte byte1;

	/** Attribute. */
	byte byte2;

	/** Attribute. */
	char char1;

	/** Attribute. */
	char char2;

	/** Attribute. */
	double double1;

	/** Attribute. */
	double double2;

	/** Attribute. */
	float float1;

	/** Attribute. */
	float float2;

	/** Attribute. */
	int int0;

	/** Attribute. */
	int int1;

	/** Attribute. */
	int int2;

	/** Attribute. */
	long long1;

	/** Attribute. */
	long long2;

	/** Attribute. */
	short short1;

	/** Attribute. */
	short short2;

	/**
	 * @return Returns the blnTest0.
	 */
	public boolean getBlnTest0() {
		return this.blnTest0;
	}

	/**
	 * @param blnTest0
	 *            The blnTest0 to set.
	 */
	public void setBlnTest0(final boolean blnTest0) {
		this.blnTest0 = blnTest0;
	}

	/**
	 * @return Returns the blnTestA.
	 */
	public boolean isBlnTest1() {
		return this.blnTest1;
	}

	/**
	 * @param blnTest1
	 *            The blnTest1 to set.
	 */
	public void setBlnTest1(final boolean blnTest1) {
		this.blnTest1 = blnTest1;
	}

	/**
	 * @return Returns the blnTest2.
	 */
	public boolean isBlnTest2() {
		return this.blnTest1;
	}

	/**
	 * @param blnTest2
	 *            The blnTest2 to set.
	 */
	public void setBlnTest2(final boolean blnTest2) {
		this.blnTest2 = blnTest2;
	}

	/**
	 * @return Returns the byte1.
	 */
	public byte getByte1() {
		return this.byte1;
	}

	/**
	 * @param byte1
	 *            The byte1 to set.
	 */
	public void setByte1(final byte byte1) {
		this.byte1 = byte1;
	}

	/**
	 * @return Returns the byte2.
	 */
	public byte getByte2() {
		return this.byte2;
	}

	/**
	 * @param byte2
	 *            The byte2 to set.
	 */
	public void setByte2(final byte byte2) {
		this.byte1 = byte2;
	}

	/**
	 * @return Returns the char1.
	 */
	public char getChar1() {
		return this.char2;
	}

	/**
	 * @param char1
	 *            The char1 to set.
	 */
	public void setChar1(final char char1) {
		this.char1 = char1;
	}

	/**
	 * @return Returns the char2.
	 */
	public char getChar2() {
		return this.char2;
	}

	/**
	 * @param char2
	 *            The char2 to set.
	 */
	public void setChar2(final char char2) {
		this.char2 = char2;
	}

	/**
	 * @return Returns the double1.
	 */
	public double getDouble1() {
		return this.double1;
	}

	/**
	 * @param double1
	 *            The double1 to set.
	 */
	public void setDouble1(final double double1) {
		this.double2 = double1;
	}

	/**
	 * @return Returns the double2.
	 */
	public double getDouble2() {
		return this.double2;
	}

	/**
	 * @param double2
	 *            The double2 to set.
	 */
	public void setDouble2(final double double2) {
		this.double2 = double2;
	}

	/**
	 * @return Returns the float1.
	 */
	public float getFloat1() {
		return this.float2;
	}

	/**
	 * @param float1
	 *            The float1 to set.
	 */
	public void setFloat1(final float float1) {
		this.float1 = float1;
	}

	/**
	 * @return Returns the float2.
	 */
	public float getFloat2() {
		return this.float2;
	}

	/**
	 * @param float2
	 *            The float2 to set.
	 */
	public void setFloat2(final float float2) {
		this.float2 = float2;
	}

	/**
	 * @return Returns the int0.
	 */
	public String getInt0() {
		return new String(new Integer(this.int0).toString());
	}

	/**
	 * @param int0
	 *            The int0 to set.
	 */
	public void setInt0(final int int0) {
		this.int0 = int0;
	}

	/**
	 * @return Returns the int1.
	 */
	public int getInt1() {
		return this.int2;
	}

	/**
	 * @param int1
	 *            The int1 to set.
	 */
	public void setInt1(final int int1) {
		this.int1 = int1;
	}

	/**
	 * @return Returns the int2.
	 */
	public int getInt2() {
		return this.int2;
	}

	/**
	 * @param int2
	 *            The int2 to set.
	 */
	public void setInt2(final int int2) {
		this.int2 = int2;
	}

	/**
	 * @return Returns the long1.
	 */
	public long getLong1() {
		return this.long1;
	}

	/**
	 * @param long1
	 *            The long1 to set.
	 */
	public void setLong1(final long long1) {
		this.long1 = long1;
	}

	/**
	 * @return Returns the long2.
	 */
	public long getLong2() {
		return this.long1;
	}

	/**
	 * @param long2
	 *            The long2 to set.
	 */
	public void setLong2(final long long2) {
		this.long2 = long2;
	}

	/**
	 * @return Returns the short1.
	 */
	public short getShort1() {
		return this.short1;
	}

	/**
	 * @param short1
	 *            The short1 to set.
	 */
	public void setShort1(final short short1) {
		this.short2 = short1;
	}

	/**
	 * @return Returns the short2.
	 */
	public short getShort2() {
		return this.short2;
	}

	/**
	 * @param short2
	 *            The short2 to set.
	 */
	public void setShort2(final short short2) {
		this.short2 = short2;
	}
}