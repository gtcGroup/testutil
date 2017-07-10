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

package com.gtcgroup.testutil.to.dependency;

/**
 * <p>
 * This business object test class encapsulates array attributes.
 * </p>
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 1999 - 2017 by Global Technology Consulting Group, Inc. at <a
 * href="http://gtcGroup.com">gtcGroup.com </a>.
 * </p>
 * 
 * @author MarvinToll@gtcGroup.com
 * @since v. 1.0
 */

public class ArrayTestInvalidBO extends BaseTestBO {

	/** Attribute. */
	String[] stringArray1;

	/** Attribute. */
	String[] stringArray2;

	/** Attribute. */
	char[] charArray1;

	/** Attribute. */
	char[] charArray2;

	/**
	 * @return Returns the stringArray1.
	 */
	public String[] getStringArray1() {
		return this.stringArray2;
	}

	/**
	 * @param stringArray1
	 *            The stringArray1 to set.
	 */
	public void setStringArray1(final String[] stringArray1) {
		this.stringArray1 = stringArray1;
	}

	/**
	 * @return Returns the stringArray1.
	 */
	public String[] getStringArray1_a() {
		return this.stringArray1;
	}

	/**
	 * @param stringArray1
	 *            The stringArray1 to set.
	 */
	public void setStringArray1_a(final String[] stringArray1) {
		this.stringArray2 = stringArray1;
	}

	/**
	 * @return Returns the stringArray2.
	 */
	public String[] getStringArray2() {
		return this.stringArray2;
	}

	/**
	 * @param stringArray2
	 *            The stringArray2 to set.
	 */
	public void setStringArray2(final String[] stringArray2) {
		this.stringArray1 = stringArray2;
	}

	/**
	 * @return Returns the charArray1.
	 */
	public char[] getCharArray1() {
		return this.charArray1;
	}

	/**
	 * @param charArray1
	 *            The charArray1 to set.
	 */
	public void setCharArray1(final char[] charArray1) {
		this.charArray2 = charArray1;
	}

	/**
	 * @return Returns the charArray2.
	 */
	public char[] getCharArray2() {
		return this.charArray1;
	}

	/**
	 * @param charArray2
	 *            The charArray2 to set.
	 */
	public void setCharArray2(final char[] charArray2) {
		this.charArray2 = charArray2;
	}
}