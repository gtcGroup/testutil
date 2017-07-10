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
 * @since v. 1.1
 */

public class ChildBO extends BaseTestBO implements ChildRO {

	/** Attribute. */
	Long longPK;

	/**
	 * Constructor
	 * 
	 * @param longPK
	 */
	public ChildBO(final Long longPK) {
		super();
		this.longPK = longPK;
	}

	/**
	 * @return Long
	 */
	public Long getLongPK() {
		return this.longPK;
	}

	/**
	 * @return int
	 */
	@Override
	public int hashCode() {
		final int PRIME = 1000003;
		int result = super.hashCode();
		if (this.longPK != null) {
			result = PRIME * result + this.longPK.hashCode();
		}

		return result;
	}

	/**
	 * @param oth
	 * @return boolean
	 * 
	 */
	@Override
	public boolean equals(final Object oth) {

		if (this == oth) {
			return true;
		}

		final ChildBO other = (ChildBO) oth;
		if (this.longPK == null) {
			if (other.longPK != null) {
				return false;
			}
		} else {
			if (!this.longPK.equals(other.longPK)) {
				return false;
			}
		}

		return true;
	}
}
