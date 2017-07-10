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
 * @since v. 1.0
 */

public class WithParametersWithExceptionsBO extends BaseTestBO {

	/** Attribute. */
	ConstructorExceptionBO constructorGetterExceptionBO;

	/** Attribute. */
	ConstructorExceptionBO constructorSetterExceptionBO;

	/** Attribute. */
	Exception exception;

	/**
	 * @return Returns the constructorGetterExceptionBO.
	 */
	public ConstructorExceptionBO getConstructorGetterExceptionBO() {
		return this.constructorGetterExceptionBO;
	}

	/**
	 * Should be exluded.
	 * 
	 * @param constructorGetterExceptionBO
	 *            The constructorGetterExceptionBO to set.
	 */
	public void setConstructorGetterExceptionBO(
			final ConstructorExceptionBO constructorGetterExceptionBO) {
		this.constructorGetterExceptionBO = constructorGetterExceptionBO;
	}

	/**
	 * Should be excluded.
	 * 
	 * @return Returns the constructorSetterExceptionBO.
	 */
	public ConstructorExceptionBO getConstructorSetterExceptionBO() {
		return this.constructorSetterExceptionBO;
	}

	/**
	 * Should be excluded.
	 * 
	 * @param constructorSetterExceptionBO
	 *            The constructorSetterExceptionBO to set.
	 */
	public void setConstructorSetterExceptionBO(
			final ConstructorExceptionBO constructorSetterExceptionBO) {
		this.constructorSetterExceptionBO = constructorSetterExceptionBO;
	}

	/**
	 * @return Return an exception.
	 */
	public Exception getException() {

		throw new RuntimeException("Returning an arbitrary runtime exception!");

		// return this.exception;
	}

	/**
	 * @param exception
	 *            The exception to set.
	 */
	public void setException(final Exception exception) {
		this.exception = exception;
	}
}