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

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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

public final class ConcreteBO extends BaseAbstractBO {

	/** Attribute. */
	private final Timestamp timestamp;

	/** Attribute. */
	private final List<?> list;

	/** Attribute. */
	private final ArrayList<?> arrayList;

	/** Attribute. */
	private final Connection connection;

	/** Attribute. */
	private final int intTest;

	/**
	 * Constructor - testing for substitution.
	 * 
	 * @param timestamp
	 * @param list
	 * @param arrayList
	 * @param connection
	 * @param intTest
	 */
	public ConcreteBO(final Timestamp timestamp, final List<?> list,
			final ArrayList<?> arrayList, final Connection connection,
			final int intTest) {
		super();

		this.timestamp = timestamp;
		this.list = list;
		this.arrayList = arrayList;
		this.connection = connection;
		this.intTest = intTest;
	}

	/**
	 * @return Returns the timestamp.
	 */
	public Timestamp getTimestampWithNoSetter() {
		return this.timestamp;
	}

	/**
	 * @return Returns the arrayList.
	 */
	public ArrayList<?> getListWithNoSetter() {
		return (ArrayList<?>) this.list;
	}

	/**
	 * @return Returns the arrayList.
	 */
	public ArrayList<?> getArrayListWithNoSetter() {
		return this.arrayList;
	}

	/**
	 * @return Returns the connection.
	 */
	public Connection getConnectionWithNoSetter() {
		return this.connection;
	}

	/**
	 * @return Returns the intTest.
	 */
	public int getIntTestWithNoSetter() {
		return this.intTest;
	}
}