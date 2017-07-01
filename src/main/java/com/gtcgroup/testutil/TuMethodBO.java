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

import java.lang.reflect.Method;

import com.gtcgroup.testutil.exceptions.TestUtilWarningException;

/**
 * <p>
 * This abstract Business Object supports immutable initialization of attributes
 * used for invoking a method with reflection.
 * </p>
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 1999 - 2008 by Global Technology Consulting Group, Inc. at <a
 * href="http://gtcGroup.com">gtcGroup.com </a>.
 * </p>
 * 
 * @author MarvinToll@gtcGroup.com
 * @since v. 1.0
 */
abstract class TuMethodBO extends TuBaseBO {

	/** Mutable object whose methods are subject to verification. */
	private final Object mutableObject;

	/** A getter or setter method for verification. */
	private final Method method;

	/** The getter's return data type. */
	private final Class<?> getterReturnType;

	/** The method's original parameter types. */
	private final Class<?>[] originalParameterTypes;

	/** The method's testable parameter types. */
	private Class<?>[] verifiableParameterTypes;

	/** The method's test values. */
	private Object[] parameterValues;

	/** The method's test values. */
	private Object[] parameterValuesForBoolean = null;

	/**
	 * Constructor
	 * 
	 * @param mutableObject
	 *            Mutable object whose methods are subject to verification.
	 * @param method
	 *            The <code>Method</code> for which test objects are
	 *            initialized.
	 * @param getterReturnType
	 *            The getter's return data type.
	 */
	TuMethodBO(final Object mutableObject, final Method method,
			final Class<?> getterReturnType) {

		super();

		// Retain the object for future reference.
		this.mutableObject = mutableObject;

		// Retain the method for future reference.
		this.method = method;

		// Retain the return data type for future reference.
		this.getterReturnType = getterReturnType;

		// Retain the original parameter types for future reference.
		this.originalParameterTypes = this.method.getParameterTypes();

	}

	/**
	 * This method initializes attributes not set in the constructor.
	 * 
	 * @throws TestUtilWarningException
	 */
	void initialize() throws TestUtilWarningException {

		// Initialize the testable parameter type array.
		this.verifiableParameterTypes = new Class[this
				.getOriginalParameterTypes().length];

		// Iterate through the paramter types for possible substitution.
		for (int i = 0; i < this.getOriginalParameterTypes().length; i++) {

			// Retrieve a candidate substitution if needed.
			final Class<?> originalOrSubstituteClass = TuUtil
					.checkForSubstituteSetterType(this
							.getOriginalParameterTypes()[i],
							this.getterReturnType, this.method, null);

			// Set the original or candidate substitution type.
			this.getVerifiableParameterTypes()[i] = originalOrSubstituteClass;
		}

		// Iterate through the parameter types.
		this.parameterValues = TuUtil.initializeValues(null,
				this.verifiableParameterTypes, this.method, null, 0);

		// Determine if a boolean is involved.
		if (0 < this.getVerifiableParameterTypes().length
				&& this.getVerifiableParameterTypes()[0]
						.isAssignableFrom(Boolean.class)) {

			// A second array so verification occurs with 'true' and 'false'.
			this.parameterValuesForBoolean = TuUtil
					.initializeValuesForBoolean(this.getParameterValues());
		}
	}

	/**
	 * @return Returns the <code>Method</code> for verification.
	 */
	Method getMethod() {
		return this.method;
	}

	/**
	 * @return Returns the original parameter types or an empty array.
	 */
	Class<?>[] getOriginalParameterTypes() {

		return this.originalParameterTypes;
	}

	/**
	 * @return Returns the verifiable parameter types or an empty array.
	 */
	Class<?>[] getVerifiableParameterTypes() {

		return this.verifiableParameterTypes;
	}

	/**
	 * @return Returns a boolean indicating the method has at least one
	 *         parameter.
	 */
	boolean containsParameterTypes() {

		if (this.originalParameterTypes.length > 0) {
			return true;
		}
		return false;
	}

	/**
	 * @return Returns the verification parameter values.
	 */
	Object[] getParameterValues() {
		return this.parameterValues;
	}

	/**
	 * @return Returns the verification parameter values.
	 */
	Object[] getParameterValuesForBoolean() {
		return this.parameterValuesForBoolean;
	}

	/**
	 * @return Returns the mutable object.
	 */
	Object getMutableObject() {
		return this.mutableObject;
	}
}