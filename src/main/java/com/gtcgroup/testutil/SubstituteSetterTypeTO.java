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
 * This Transfer Object contains a collection representing a portion of the
 * unmarshalled descriptor document.
 * </p>
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 1999 - 2008 by Global Technology Consulting Group, Inc. at <a
 * href="http://gtcGroup.com">gtcGroup.com </a>.
 * </p>
 * 
 * @author MarvinToll@gtcGroup.com
 * @since v. 1.0
 */

final class SubstituteSetterTypeTO extends TuBaseConfigurationTO {

	/**
	 * Adds a descriptor document element to cache.
	 * 
	 * @param attributeOne
	 *            The first attribute for a descriptor document
	 *            <code>Element</code>.
	 * @param attributeTwo
	 *            The second attribute for a descriptor document
	 *            <code>Element</code>.
	 */
	@Override
	void addElement(final String attributeOne, final String attributeTwo) {

		this.getTuElementTOMap().put(attributeOne,
				new TuElementTO(attributeOne, attributeTwo));
	}

	/**
	 * Indicates if a descriptor document element is cached.
	 * 
	 * @param aTypeClassName
	 *            The first attribute for a descriptor document
	 *            <code>Element</code>.
	 * @param useNullPlease
	 *            The second attribute for a descriptor document
	 *            <code>Element</code>.
	 * @return boolean - Indicates if a <code>TuElementTO</code> is cached.
	 */
	@Override
	boolean containsElement(final String aTypeClassName,
			final String useNullPlease) {

		// Avoid compiler warning.
		if (null == useNullPlease) {
			// Placeholder comment;
		}

		return this.getTuElementTOMap().containsKey(aTypeClassName);
	}
}