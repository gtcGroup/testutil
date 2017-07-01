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

package com.gtcgroup.testutil;

/**
 * <p>
 * This immutable Transfer Object contains attributes representing an
 * unmarshalled descriptor document Element.
 * </p>
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 1999 - 2008 by Global Technology Consulting Group, Inc. at <a
 * href="http://gtcGroup.com">gtcGroup.com </a>.
 * </p>
 * 
 * @author MarvinToll@gtcGroup.com 
 * @since v. 1.0
 */

final class TuElementTO extends TuBaseTO {

	/** The first attribute for a descriptor document <code>Element</code>. */
	private final String attributeOne;

	/** The second attribute for a descriptor document <code>Element</code>. */
	private final String attributeTwo;

	/**
	 * Constructor
	 * 
	 * @param attributeOne
	 *            The first attribute for a descriptor document
	 *            <code>Element</code>.
	 * @param attributeTwo
	 *            The second attribute for a descriptor document
	 *            <code>Element</code>.
	 */
	TuElementTO(final String attributeOne, final String attributeTwo) {

		super();

		this.attributeOne = attributeOne;
		this.attributeTwo = attributeTwo;
	}

	/**
	 * Returns the string attributeOne.
	 * 
	 * @return String - The first attribute for a descriptor document
	 *         <code>Element</code>.
	 */
	final String getAttributeOne() {
		return this.attributeOne;
	}

	/**
	 * Returns the string attributeTwo.
	 * 
	 * @return String - The second attribute for a descriptor document
	 *         <code>Element</code>.
	 */
	final String getAttributeTwo() {
		return this.attributeTwo;
	}
}