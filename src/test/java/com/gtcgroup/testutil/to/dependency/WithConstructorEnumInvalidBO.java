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
 * @since v. 2.4
 */

public class WithConstructorEnumInvalidBO extends BaseTestBO {

	/** Attribute. */
	ConstructorEnumValidBO objectWithConstructorEnumValidBO;

	/** Attribute. */
	ConstructorEnumValidBO objectWithConstructorEnumValidBO2;

	/**
	 * This method returns the <code>objectWithConstructorEnumValidBO</code>
	 * attribute.
	 * 
	 * @return ConstructorEnumValidBO
	 */
	public ConstructorEnumValidBO getObjectWithConstructorEnumValidBO() {
		return this.objectWithConstructorEnumValidBO;
	}

	/**
	 * This method sets the <code>objectWithConstructorEnumValidBO</code>
	 * attribute.
	 * 
	 * @param objectWithConstructorEnumValidBO
	 */
	public void setObjectWithConstructorEnumValidBO(
			final ConstructorEnumValidBO objectWithConstructorEnumValidBO) {
		this.objectWithConstructorEnumValidBO = objectWithConstructorEnumValidBO;
	}

	/**
	 * This method returns the <code>objectWithConstructorEnumValidBO2</code>
	 * attribute. INVALID METHOD ... should be objectWithConstructorEnumValidBO2
	 * 
	 * @return ConstructorEnumValidBO
	 */
	public ConstructorEnumValidBO getObjectWithConstructorEnumValidBO2() {
		return this.objectWithConstructorEnumValidBO;
	}

	/**
	 * This method sets the <code>objectWithConstructorEnumValidBO2</code>
	 * attribute.
	 * 
	 * @param objectWithConstructorEnumValidBO2
	 */
	public void setObjectWithConstructorEnumValidBO2(
			final ConstructorEnumValidBO objectWithConstructorEnumValidBO2) {
		this.objectWithConstructorEnumValidBO2 = objectWithConstructorEnumValidBO2;
	}

}