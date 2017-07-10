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

public class ObjectTestInvalidBO extends BaseTestBO {

	/** Attribute. */
	String strTest1;

	/** Attribute. */
	String strTest2;

	/** Attribute. */
	String strWithMultipleArgs;

	// Used to generate a warning.
	/** Attribute. */
	boolean blnTest0;

	/**
	 * @return Returns the strTest1.
	 */
	public String getStrTest1() {
		return this.strTest1;
	}

	/**
	 * @param strTest1
	 *            The strTest1 to set.
	 */
	public void setStrTest1(final String strTest1) {
		this.strTest2 = strTest1;
	}

	/**
	 * @return Returns the strTest2.
	 */
	public String getStrTest2() {
		return this.strTest2;
	}

	/**
	 * @param strTest2
	 *            The strTest2 to set.
	 */
	public void setStrTest2(final String strTest2) {
		this.strTest2 = strTest2;
	}

	/**
	 * @return Returns the strWithMultipleArgs.
	 */
	public String getStrWithMultipleArgs() {
		return this.strWithMultipleArgs;
	}

	/**
	 * @param strWithMultipleArgs
	 *            The strWithMultipleArgs to set.
	 * @param blnBogus
	 */
	public void setStrWithMultipleArgs(final String strWithMultipleArgs,
			final boolean blnBogus) {
		this.strWithMultipleArgs = strWithMultipleArgs;

		// This line enalbles a warning.
		this.blnTest0 = blnBogus;
	}

}