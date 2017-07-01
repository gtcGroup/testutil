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

package com.gtcgroup.testutil.exceptions;

/**
 * This class serves as the base (abstract) class for all TestUtil exceptions.
 * 
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 1999 - 2008 by Global Technology Consulting Group, Inc. at <a
 * href="http://gtcGroup.com">gtcGroup.com </a>.
 * </p>
 * 
 * @author MarvinToll@gtcGroup.com
 * @since v. 1.0
 */
public abstract class TestUtilRuntimeException extends RuntimeException {

	/** UID */
	static final long serialVersionUID = 1L;

	/** Attribute. */
	private Exception exception = null;

	/**
	 * Constructor
	 * 
	 * @param message
	 * @param exception
	 */
	public TestUtilRuntimeException(final String message,
			final Exception exception) {
		super(message);
		this.exception = exception;
		return;
	}

	/**
	 * Constructor
	 */
	TestUtilRuntimeException() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param message
	 */
	public TestUtilRuntimeException(final String message) {
		this(message, null);
		return;
	}

	/**
	 * Constructor
	 * 
	 * @param exception
	 */
	TestUtilRuntimeException(final Exception exception) {
		this(null, exception);
		return;
	}

	/**
	 * Gets the wrapped exception.
	 * 
	 * @return Exception
	 */
	public final Exception getException() {
		return this.exception;
	}

	/**
	 * Retrieves (recursively) the root cause exception.
	 * 
	 * @return Exception
	 */
	public Exception getRootCause() {
		if (this.exception instanceof TestUtilRuntimeException) {
			return ((TestUtilRuntimeException) this.exception).getRootCause();
		}
		return this.exception == null ? this : this.exception;
	}

	/**
	 * @return String
	 */
	@Override
	public final String toString() {
		if (this.exception instanceof TestUtilRuntimeException) {
			return this.exception.toString();
		}
		return this.exception == null ? super.toString() : this.exception
				.toString();
	}
}