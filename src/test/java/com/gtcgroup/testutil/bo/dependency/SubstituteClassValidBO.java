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

package com.gtcgroup.testutil.bo.dependency;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * This business object test class encapsulates object attributes.
 * </p>
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 1999 - 2008 by Global Technology Consulting Group, Inc. at <a
 * href="http://gtcGroup.com">gtcGroup.com </a>.
 * </p>
 * 
 * @author MarvinToll@gtcGroup.com
 * @since v. 1.0
 */

public class SubstituteClassValidBO extends BaseTestBO {

	/** Attribute. */
	private Date date;

	/** Attribute. */
	private Timestamp timestamp;

	/** Attribute. */
	private Set<?> set;

	/** Attribute. */
	private List<?> list;

	/** Attribute. */
	private ArrayList<?> arrayList;

	/** Attribute. */
	private Map<?, ?> map;

	/** Attribute. */
	private BaseAbstractBO baseAbstractBO;

	/**
	 * @return Returns the date.
	 */
	public Date getDate() {
		return this.date;
	}

	/**
	 * @param date
	 *            The date to set.
	 */
	public void setDate(final Date date) {
		this.date = date;
	}

	/**
	 * @return Returns the set.
	 */
	public Set<?> getSet() {
		return this.set;
	}

	/**
	 * @param set
	 *            The set to set.
	 */
	public void setSet(final Set<?> set) {
		this.set = set;
	}

	/**
	 * @return Returns the list.
	 */
	public List<?> getList() {
		return this.list;
	}

	/**
	 * @param list
	 *            The list to set.
	 */
	public void setList(final List<?> list) {
		this.list = list;
	}

	/**
	 * @return Returns the map.
	 */
	public Map<?, ?> getMap() {
		return this.map;
	}

	/**
	 * @param map
	 *            The map to set.
	 */
	public void setMap(final Map<?, ?> map) {
		this.map = map;
	}

	/**
	 * @return Returns the timestamp.
	 */
	public Timestamp getTimestamp() {
		return this.timestamp;
	}

	/**
	 * @param timestamp
	 *            The timestamp to set.
	 */
	public void setTimestamp(final Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return Returns the baseAbstractBO.
	 */
	public BaseAbstractBO getBaseAbstractBO() {
		return this.baseAbstractBO;
	}

	/**
	 * @param baseAbstractBO
	 *            The baseAbstractBO to set.
	 */
	public void setBaseAbstractBO(final BaseAbstractBO baseAbstractBO) {
		this.baseAbstractBO = baseAbstractBO;
	}

	/**
	 * @return Returns the List.
	 */
	public List<?> getArrayList1() {
		return this.arrayList;
	}

	/**
	 * @param arrayList
	 *            The arrayList to set.
	 */
	public void setArrayList1(final ArrayList<?> arrayList) {
		this.arrayList = arrayList;
	}

	/**
	 * @return Returns the List.
	 */
	public ArrayList<?> getArrayList2() {
		return this.arrayList;
	}

	/**
	 * @param list
	 *            The arrayList to set.
	 */
	public void setArrayList2(final List<?> list) {
		this.arrayList = (ArrayList<?>) list;
	}

	/**
	 * @return Returns the List.
	 */
	public List<?> getList1() {
		return this.list;
	}

	/**
	 * @param arrayList
	 *            The arrayList to set.
	 */
	public void setList1(final ArrayList<?> arrayList) {
		this.list = arrayList;
	}

	/**
	 * @return Returns the ArrayList.
	 */
	public ArrayList<?> getList2() {
		return (ArrayList<?>) this.list;
	}

	/**
	 * @param list
	 *            The list to set.
	 */
	public void setList2(final List<?> list) {
		this.list = list;
	}
}