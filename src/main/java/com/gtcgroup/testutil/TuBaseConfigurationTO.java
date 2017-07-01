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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This Transfer Object base class contains a collection representing an
 * unmarshalled portion of the descriptor document(s).
 * </p>
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 1999 - 2008 by Global Technology Consulting Group, Inc. at <a
 * href="http://gtcGroup.com">gtcGroup.com </a>.
 * </p>
 * 
 * @author MarvinToll@gtcGroup.com
 * @since v. 1.0
 */

abstract class TuBaseConfigurationTO extends TuBaseTO {

	/**
	 * Synchronized collection of descriptor document elements represented as
	 * <code>TuElementTO</code>s.
	 */
	private final Map<String, Object> tuElementTOMap = Collections
			.synchronizedMap(new HashMap<String, Object>());

	/**
	 * Constructor
	 */
	TuBaseConfigurationTO() {
		super();
	}

	/**
	 * Returns the <code>ElementTO</code> collection.
	 * 
	 * @return Map - The collection of <code>TuElementTO</code>s.
	 */
	Map<String, Object> getTuElementTOMap() {
		return this.tuElementTOMap;
	}

	/**
	 * Returns a transfer object from the collection.
	 * 
	 * @param attributeOne
	 *            The first attribute for a descriptor document
	 *            <code>Element</code>.
	 * @return TuElementTO - An element from the collection.
	 * @since v. 1.1
	 */
	public TuElementTO getElementTO(final String attributeOne) {

		return (TuElementTO) this.getTuElementTOMap().get(attributeOne);
	}

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
	abstract void addElement(String attributeOne, String attributeTwo);

	/**
	 * Indicates if a descriptor document element is cached and active.
	 * 
	 * @param attributeOne
	 *            The first attribute for a descriptor document
	 *            <code>Element</code>.
	 * @param attributeTwo
	 *            The second attribute for a descriptor document
	 *            <code>Element</code>.
	 * @return boolean - Indicates if a <code>TuElementTO</code> is cached.
	 */
	abstract boolean containsElement(String attributeOne, String attributeTwo);
}