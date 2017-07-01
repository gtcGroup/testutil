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
 * @since v. 2.4
 */

public class ConstructorEnumValidBO extends BaseTestBO {

	/** Attribute. */
	Enum<?> enumTest1;

	/** Attribute. */
	Enum<?> enumTest2;

	/** Attribute. */
	Enum<?> enumWithMultipleArgs;

	/**
	 * Constructor
	 * 
	 * @param enumTest1
	 * @param enumTest2
	 */
	public ConstructorEnumValidBO(final Enum<?> enumTest1,
			final Enum<?> enumTest2) {
		super();
		this.enumTest1 = enumTest1;
		this.enumTest2 = enumTest2;
	}

	/**
	 * @return the enumTest1
	 */
	public Enum<?> getEnumTest1() {
		return this.enumTest1;
	}

	/**
	 * @param enumTest1
	 *            the enumTest1 to set
	 */
	public void setEnumTest1(final Enum<?> enumTest1) {
		this.enumTest1 = enumTest1;
	}

	/**
	 * @return the enumTest2
	 */
	public Enum<?> getEnumTest2() {
		return this.enumTest2;
	}

	/**
	 * @param enumTest2
	 *            the enumTest2 to set
	 */
	public void setEnumTest2(final Enum<?> enumTest2) {
		this.enumTest2 = enumTest2;
	}

}