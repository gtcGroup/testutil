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
 * This business object test class encapsulates attributes.
 * </p>
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 1999 - 2017 by Global Technology Consulting Group, Inc. at <a
 * href="http://gtcGroup.com">gtcGroup.com </a>.
 * </p>
 * 
 * @author MarvinToll@gtcGroup.com
 * @since v. 2.4
 */

public class EnumTestInvalidBO extends BaseTestBO {

	/** Attribute. */
	Enum<?> mockEnums1;

	/** Attribute. */
	Enum<?> mockEnums2;

	/**
	 * This method returns the <code>mockEnums1</code> attribute.
	 * 
	 * @return Enum<?>
	 */
	public Enum<?> getMockEnums1() {
		return this.mockEnums1;
	}

	/**
	 * This method sets the <code>mockEnums1</code> attribute. INVALID -
	 * SHOULD SET TO mockEnums1
	 * 
	 * @param mockEnums1
	 */
	public void setMockEnums1(final Enum<?> mockEnums1) {
		this.mockEnums2 = mockEnums1;
	}

	/**
	 * This method returns the <code>mockEnums2</code> attribute.
	 * 
	 * @return Enum<?>
	 */
	public Enum<?> getMockEnums2() {
		return this.mockEnums2;
	}

	/**
	 * This method sets the <code>mockEnums2</code> attribute.
	 * 
	 * @param mockEnums2
	 */
	public void setMockEnums2(final Enum<?> mockEnums2) {
		this.mockEnums2 = mockEnums2;
	}

}