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

public class GetterParametersBO extends BaseTestBO {

	/** Attribute. */
	private String strTest;

	/** Attribute. */
	private int intTest;

	/** Attribute. */
	private String[] stringArray;

	/** Attribute. */
	private char[] charArray;

	// These attributes exist to avoid warnings.
	/** Attribute. */
	private char charTest;

	/** Attribute. */
	private int intTest2;

	/** Attribute. */
	private String[] stringArrayTest;

	/** Attribute. */
	private String strTest2;

	/** Attribute. */
	private boolean blnTest;

	/**
	 * @param charTest1
	 * @return Returns the charArray.
	 */
	public char[] getCharArray(final char charTest1) {
		this.charTest = charTest1;
		return this.charArray;
	}

	/**
	 * @param charArray
	 *            The charArray to set.
	 */
	public void setCharArray(final char[] charArray) {
		this.charArray = charArray;
	}

	/**
	 * @param intTest1
	 * @return Returns the intTest.
	 */
	public int getIntTest(final int intTest1) {
		this.intTest2 = intTest1;
		return this.intTest;
	}

	/**
	 * @param intTest
	 *            The intTest to set.
	 */
	public void setIntTest(final int intTest) {
		this.intTest = intTest;
	}

	/**
	 * @param strArrayTest
	 * @return Returns the stringArray.
	 */
	public String[] getStringArray(final String[] strArrayTest) {
		this.stringArrayTest = strArrayTest;
		return this.stringArray;
	}

	/**
	 * @param stringArray
	 *            The stringArray to set.
	 */
	public void setStringArray(final String[] stringArray) {
		this.stringArray = stringArray;
	}

	/**
	 * @param strTest1
	 * @param blnTest1
	 * @return Returns the strTest.
	 */
	public String getStrTest(final String strTest1, final boolean blnTest1) {
		this.strTest2 = strTest1;
		this.blnTest = blnTest1;
		return this.strTest;
	}

	/**
	 * @param strTest
	 *            The strTest to set.
	 */
	public void setStrTest(final String strTest) {
		this.strTest = strTest;
	}

	/**
	 * @return Returns the blnTest.
	 */
	public boolean isBlnBogus() {
		return this.blnTest;
	}

	/**
	 * @return Returns the charTest.
	 */
	public char getCharBogus() {
		return this.charTest;
	}

	/**
	 * @return Returns the intTest2.
	 */
	public int getIntBogus() {
		return this.intTest2;
	}

	/**
	 * @return Returns the stringArrayTest.
	 */
	public String[] getStringArrayBogus() {
		return this.stringArrayTest;
	}

	/**
	 * @return Returns the strTest2.
	 */
	public String getStrBogus() {
		return this.strTest2;
	}
}