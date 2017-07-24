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

package com.gtcgroup.testutil.exception;

/**
 * This class serves as the base (abstract) class for all TestUtil exceptions.
 *
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 1999 - 2017 by Global Technology Consulting Group, Inc. at
 * <a href="http://gtcGroup.com">gtcGroup.com </a>.
 * </p>
 *
 * @author MarvinToll@gtcGroup.com
 * @since v. 1.0
 */
public abstract class TestUtilRuntimeException extends RuntimeException {

	/** UID */
	private static final long serialVersionUID = 1L;

	/**
	 * Attribute maintaining the warning count for a verified object.
	 */
	private static int methodVerifications = 0;

	/**
	 * Attribute maintaining the warning count for a verified object.
	 */
	private static int totalWarnings = 0;

	/**
	 * Attribute maintaining the glitch count for a verified object.
	 */
	private static int totalGlitches = 0;

	/**
	 * Attribute maintaining the glitch count for a verified object.
	 */
	private static int classCautions = 0;

	/**
	 * Attribute maintaining the glitch count for a verified object.
	 */
	private static int totalMethodSkips = 0;

	public static void addClassCaution() {
		classCautions++;
	}

	public static void addMethodVerification() {
		methodVerifications++;
	}

	public static int getMethodVerifications() {
		return methodVerifications;
	}

	public static int getTotalClassCautions() {
		return classCautions;
	}

	/** Attribute. */
	private final Exception exception = null;

	/**
	 * Constructor
	 *
	 * @param message
	 */
	public TestUtilRuntimeException(final String message) {
		super(message);
		return;
	}

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
		return this.exception == null ? super.toString() : this.exception.toString();
	}
}