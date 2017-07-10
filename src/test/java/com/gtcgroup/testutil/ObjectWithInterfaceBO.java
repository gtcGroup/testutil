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

package com.gtcgroup.testutil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.gtcgroup.testutil.to.dependency.BaseTestBO;
import com.gtcgroup.testutil.to.dependency.IObjectTestValidBO;

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

public class ObjectWithInterfaceBO extends BaseTestBO {

	/** Attribute. */
	private IObjectTestValidBO iObjectTestValidBO;

	/** Attribute. */
	private Iterator<?> iterator;

	/** Attribute. */
	private List<?> list;

	/** Attribute. */
	private ArrayList<?> arrayList;

	/**
	 * @return Returns the iObjectTestValidBO.
	 */
	public IObjectTestValidBO getIObjectTestValidBO() {
		return this.iObjectTestValidBO;
	}

	/**
	 * @param iObjectTestValidBO
	 *            The iObjectTestValidBO to set.
	 */
	public void setIObjectTestValidBO(
			final IObjectTestValidBO iObjectTestValidBO) {
		this.iObjectTestValidBO = iObjectTestValidBO;
	}

	/**
	 * @return Returns the iterator.
	 */
	public Iterator<?> getIterator() {
		return this.iterator;
	}

	/**
	 * @param iterator
	 *            The iterator to set.
	 */
	public void setIterator(final Iterator<?> iterator) {
		this.iterator = iterator;
	}

	/**
	 * @return Returns the list.
	 */
	public LinkedList<?> getList() {
		return (LinkedList<?>) this.list;
	}

	/**
	 * @param list
	 *            The list to set.
	 */
	public void setList(final List<?> list) {
		this.list = list;
	}

	/**
	 * This should fail.
	 * 
	 * @return Returns the list.
	 */
	public List<?> getList2() {
		return this.list;
	}

	/**
	 * This should fail.
	 * 
	 * @param list
	 *            The list to set.
	 */
	public void setList2(final ArrayList<?> list) {
		this.arrayList = list;
	}

	/**
	 * This should fail.
	 * 
	 * @return Returns the list.
	 */
	public List<?> getList3() {
		return this.list;
	}

	/**
	 * This should fail.
	 * 
	 * @param list
	 *            The list to set.
	 */
	public void setList3(final List<?> list) {

		list.getClass();

		final Object objectTemp = TuDynamicProxy.newInstance(List.class);

		this.list = (List<?>) objectTemp;
	}

	/**
	 * This will fail.
	 * 
	 * @return Returns the arrayList.
	 */
	public List<?> getArrayList() {
		return this.arrayList;
	}

	// TODO: Someday - figure out how to handle this one.
	/**
	 * This will fail.
	 * 
	 * @param arrayList
	 *            The arrayList to set.
	 */
	public void setArrayList(final List<?> arrayList) {
		this.arrayList = (ArrayList<?>) arrayList;
	}
}