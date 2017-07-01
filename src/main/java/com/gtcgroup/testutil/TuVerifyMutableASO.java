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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.gtcgroup.testutil.exceptions.TestUtilGlitchException;
import com.gtcgroup.testutil.exceptions.TestUtilWarningException;
import com.thoughtworks.qdox.model.JavaMethod;

/**
 * <p>
 * This (immutable) Application Service Object (ASO) handles mutable object
 * verification as a service.
 * </p>
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 1999 - 2008 by Global Technology Consulting Group, Inc. at <a
 * href="http://gtcGroup.com">gtcGroup.com </a>.
 * </p>
 * 
 * @author MarvinToll@gtcGroup.com
 * @since v. 1.0
 */
class TuVerifyMutableASO {

	/** Mutable object whose methods are subject to verification. */
	private final Object mutableObject;

	/** A source code pathname string. */
	private final String sourceCodePathname;

	/**
	 * A collection of all <code>TuMethodPairBO</code>s for which test
	 * objects were successfully instantiated.
	 */
	private final ArrayList<TuMethodPairBO> tuMethodPairBOsList = new ArrayList<TuMethodPairBO>();

	/**
	 * Mutable class managing counters for glitches and warnings.
	 */
	private final TuCountersBO tuCountersBO = new TuCountersBO();

	/**
	 * Constructor
	 * 
	 * @param mutableObject
	 *            Mutable object whose methods are subject to verification.
	 * @param sourceCodePathname
	 *            A pathname string.
	 */
	TuVerifyMutableASO(final Object mutableObject,
			final String sourceCodePathname) {

		super();

		// Maintain handle to object being verified.
		this.mutableObject = mutableObject;
		this.sourceCodePathname = sourceCodePathname;
	}

	/**
	 * Method preparing test instances for verification. Warnings are issued if
	 * the preparation process indicates a verification is not possible.
	 */
	@SuppressWarnings("null")
	void prepareTestInstances() {

		// Declarations
		StringBuffer sbSetterName = null;
		StringBuffer sbGetterName = null;
		TuMethodPairBO tuMethodPairBO = null;

		// Get all the methods.
		final Method[] arrOfMethods = this.mutableObject.getClass()
				.getMethods();

		// Collect the "setters".
		final HashMap<String, Method> setterMap = TuUtil
				.collectSetters(arrOfMethods);

		// Collect the "getters".
		final HashMap<String, Method> getterMap = TuUtil
				.collectGetters(arrOfMethods);

		// Obtain iterator.
		final Iterator<?> keySet = setterMap.keySet().iterator();

		// Obtain the Javadoc for methods.
		JavaMethod[] javaMethods = null;

		// Determine if Javadoc tag processing is warranted.
		if (null != this.sourceCodePathname) {

			try {
				javaMethods = TuUtil.obtainMethodJavadoc(
						this.sourceCodePathname, this.mutableObject.getClass());
			} catch (final Exception e) {
				// do nothing - Javadoc processing will not occur.
			}
		}

		// Iterate.
		while (keySet.hasNext()) {

			try {

				// Initialize
				tuMethodPairBO = null;

				// Retrieve the setter name.
				sbSetterName = new StringBuffer((String) keySet.next());

				// Determine if duplication precludes verification.
				if (sbSetterName.substring(0, 3).equals(
						TuConstants.DUPLICATE_METHOD_KEY)) {

					// Truncate setter name.
					sbSetterName.delete(0, 3);

					// Initialize getter.
					sbGetterName = null;

					// Issue warning for exclusion.
					TuExceptionWarningHandler
							.throwMultiplesWarningForSetter(sbSetterName
									.toString());
				}

				// Obtain the getter name.
				sbGetterName = new StringBuffer(sbSetterName.toString());
				sbGetterName.replace(0, 1, "g");

				// Determine if duplication precludes verification.
				if (getterMap.containsKey(TuConstants.DUPLICATE_METHOD_KEY
						+ sbGetterName)) {

					// Initialize setter.
					sbSetterName = null;

					// Issue warning for exclusion.
					TuExceptionWarningHandler
							.throwMultiplesWarningForGetter(sbGetterName
									.toString());
				}

				// Determine if Javadoc interrogation is warranted.
				if (null != javaMethods) {

					// Should method pair be excluded?
					if (TuUtil.excludeMethodUsingJavadocTag(javaMethods,
							sbSetterName.toString())) {

						// Issue warning for exclusion.
						TuExceptionWarningHandler
								.throwExcludedWarningForSetter();
					}
				}

				// Determine if corresponding getter exists.
				if (!getterMap.containsKey(sbGetterName.toString())) {

					// Obtain the "is" method name.
					sbGetterName.replace(0, 3, "is");
				}

				// Determine if corresponding getter exists.
				if (getterMap.containsKey(sbGetterName.toString())) {

					// Throw an exception if the method pair should be excluded.
					TuUtil.excludeMethodPairBySetter(this.mutableObject,
							sbSetterName);

					// Obtain the method pair business object.
					tuMethodPairBO = this.prepareAccessorPair(setterMap
							.get(sbSetterName.toString()), getterMap
							.get(sbGetterName.toString()));

					// Add to the returning list.
					this.getTuMethodPairBOsList().add(tuMethodPairBO);
				}

			} catch (final TestUtilWarningException warning) {

				// Is this the first warning?
				if (0 == this.getTuCountersBO().getTotalWarnings()) {

					// Sub-header for warnings.
					System.out.println("\n\t+ Verification Warning(s) - "
							+ this.mutableObject.getClass().getName() + " +");
				}

				// Increment warning counter.
				this.tuCountersBO.addWarning();

				// Process warning.
				TuExceptionWarningHandler.processWarning(sbSetterName,
						sbGetterName, tuMethodPairBO, this.getTuCountersBO(),
						warning);
			}
		}

		return;
	}

	/**
	 * Method providing verification services once preparation is completed.
	 * Glitches are issued if the verification could not occur or failed.
	 */
	void invokeSettersAndGetters() {

		// Declaration.
		TuMethodPairBO tuMethodPairBO;
		TuMethodSetBO tuMethodSetBO = null;
		TuMethodGetBO tuMethodGetBO = null;

		// Iterate for processing all method pairs.
		for (int i = 0; i < this.getTuMethodPairBOsList().size(); i++) {

			try {

				// Retrieve the method business objects.
				tuMethodPairBO = this.getTuMethodPairBOsList().get(i);
				tuMethodSetBO = tuMethodPairBO.getTuMethodSetBO();
				tuMethodGetBO = tuMethodPairBO.getTuMethodGetBO();

				// Determine if a "boolean" attribute is being verified.
				if (tuMethodSetBO.getVerifiableParameterTypes()[0] == Boolean.class) {

					// Try twice; Once with 'true' and once with 'false'.
					this.invokeMethods(tuMethodSetBO, tuMethodGetBO, true);
					this.invokeMethods(tuMethodSetBO, tuMethodGetBO, false);

				} else {
					// Once is enough!
					this.invokeMethods(tuMethodSetBO, tuMethodGetBO, false);
				}

				// Increment for successful invocation.
				this.getTuCountersBO().addVerification();

			} catch (final TestUtilGlitchException exception) {

				// Is this the first glitch?
				if (0 == this.getTuCountersBO().getTotalGlitches()) {

					// Sub-header for glitches.
					System.out.println("\n\t++ Verification Glitch(es) - "
							+ this.mutableObject.getClass().getName() + " ++");
				}

				// Increment glitch counter.
				this.tuCountersBO.addGlitch();

				// Process glitch.
				TuExceptionGlitchHandler.processGlitch(tuMethodSetBO,
						tuMethodGetBO, this.getTuCountersBO(), exception);
			}
		}
		return;
	}

	/**
	 * Prepares test instances.
	 * 
	 * @param methodSet
	 * @param methodGet
	 * @return TuMethodPairBO
	 * @throws TestUtilWarningException
	 */
	private TuMethodPairBO prepareAccessorPair(final Method methodSet,
			final Method methodGet) throws TestUtilWarningException {

		// Declaration.
		TuMethodPairBO tuMethodPairBO = null;

		// Initialization.
		final TuMethodSetBO tuMethodSetBO = new TuMethodSetBO(
				this.mutableObject, methodSet, methodGet.getReturnType());
		final TuMethodGetBO tuMethodGetBO = new TuMethodGetBO(
				this.mutableObject, methodGet);

		// Process exclusions.
		TuUtil.excludeMethodPairsByType(tuMethodSetBO, tuMethodGetBO);

		// this.e

		// Initialize.
		tuMethodSetBO.initialize();
		tuMethodGetBO.initialize();

		// Ensure at least one setter parameter.
		if (tuMethodSetBO.getOriginalParameterTypes().length > 0) {

			// Check that the first parameter type of the original setter
			// is assignable from the return type of the getter.
			// - or -
			// The return type of the getter is assignable
			// from the first parameter type of the original setter.
			// - or -
			// Primitives are involved.
			if (tuMethodSetBO.getOriginalParameterTypes()[0]
					.isAssignableFrom(tuMethodGetBO.getReturnType())

					|| tuMethodGetBO.getReturnType().isAssignableFrom(
							tuMethodSetBO.getOriginalParameterTypes()[0])

					|| TuCacher.getInstance().getIncludePrimitiveReturnType()
							.containsElement(
									tuMethodGetBO.getReturnType().getName(),
									null)) {

				// Looks good - instantiate the return object.
				tuMethodPairBO = new TuMethodPairBO(tuMethodGetBO,
						tuMethodSetBO);

			} else {

				// Issue warning for exclusion.
				TuExceptionWarningHandler.throwNotVerifiableWarning(
						tuMethodSetBO.getOriginalParameterTypes()[0].getName(),
						tuMethodGetBO.getReturnType().getName());
			}
		} else {

			// Issue warning for exclusion.
			TuExceptionWarningHandler.throwSetterSignatureWarning();
		}

		return tuMethodPairBO;
	}

	/**
	 * Invokes methods and coordinates examination of the resulting test
	 * instance(s).
	 * 
	 * @param tuMethodSetBO
	 * @param tuMethodGetBO
	 * @param isBooleanVerification
	 */
	private void invokeMethods(final TuMethodSetBO tuMethodSetBO,
			final TuMethodGetBO tuMethodGetBO,
			final boolean isBooleanVerification) {

		// Declaration.
		Object objReturnedFromGetter;

		// Fire the setter.
		TuUtil.invokeMethod(tuMethodSetBO, isBooleanVerification);

		// Fire the getter.
		objReturnedFromGetter = TuUtil.invokeMethod(tuMethodGetBO,
				isBooleanVerification);

		// Determine if boolean verification.
		if (isBooleanVerification) {

			// Check for matching primitive values.
			this.assertEquality(
					tuMethodSetBO.getParameterValuesForBoolean()[0],
					objReturnedFromGetter);

			// Determine if primitive is involved.
		} else if (tuMethodGetBO.getReturnType().isPrimitive()) {

			// Check for matching primitive values.
			this.assertEquality(tuMethodSetBO.getParameterValues()[0],
					objReturnedFromGetter);

		} else {
			// Check for matching objects.
			this.assertSameObject(tuMethodSetBO.getParameterValues()[0],
					objReturnedFromGetter);
		}
	}

	/**
	 * This method asserts the objects are a match.
	 * 
	 * @param objSet
	 * @param objReturned
	 * @throws TestUtilGlitchException
	 */
	@SuppressWarnings("null")
	private void assertSameObject(final Object objSet, final Object objReturned)
			throws TestUtilGlitchException {

		// Asserts that the same object was set and returned.
		if (objReturned == objSet) {

			// Return fast.
			return;
		}

		// Check for null.
		if (null == objReturned) {

			// Issue glitch.
			TuExceptionGlitchHandler.throwNotSameGlitch(objSet.toString());
		}

		// Check for dynamic proxy.
		if ("$Proxy".equalsIgnoreCase(objSet.getClass().getName().substring(0,
				6))
				|| "$Proxy".equalsIgnoreCase(objReturned.getClass().getName()
						.substring(0, 6))) {

			// Issue glitch.
			TuExceptionGlitchHandler.throwProxyGlitch(objSet.toString(),
					objReturned.toString());
		}

		// If not the same, how about equal?
		this.assertEquality(objSet, objReturned);

		return;
	}

	/**
	 * This method is invoked after <code>assertSameObject()</code> fails. It
	 * asserts object equality.
	 * 
	 * @param objThatWasSet
	 * @param objReturnedFromGet
	 * @throws TestUtilGlitchException
	 */
	private void assertEquality(final Object objThatWasSet,
			final Object objReturnedFromGet) throws TestUtilGlitchException {

		// Verify values.
		if (!objReturnedFromGet.equals(objThatWasSet)) {

			// Issue glitch.
			TuExceptionGlitchHandler.throwNotEqualGlitch(objThatWasSet
					.toString(), objReturnedFromGet.toString());
		}
		return;
	}

	/**
	 * Returns the <code>TuCountersBO</code>
	 * 
	 * @return Returns the <code>TuCountersBO</code>.
	 */
	TuCountersBO getTuCountersBO() {
		return this.tuCountersBO;
	}

	/**
	 * Returns the list of <code>TuMethodPairBO</code>s
	 * 
	 * @return Returns the list of <code>TuMethodPairBO</code>s.
	 */
	ArrayList<TuMethodPairBO> getTuMethodPairBOsList() {
		return this.tuMethodPairBOsList;
	}

}