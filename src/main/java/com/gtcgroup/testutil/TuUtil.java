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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Random;

import com.gtcgroup.testutil.exceptions.TestUtilWarningException;
import com.gtcgroup.testutil.mock.EnumMock;
import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.DocletTag;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;

/**
 * <p>
 * This class provides verification utility support.
 * </p>
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 1999 - 2008 by Global Technology Consulting Group, Inc. at <a
 * href="http://gtcGroup.com">gtcGroup.com </a>.
 * </p>
 * 
 * @author MarvinToll@gtcGroup.com
 * @since v. 1.0
 */

class TuUtil {

	// Primitives
	/** Attribute. */
	private static Random random = new Random();

	/** Attribute. */
	private static final String BOOLEAN = "boolean";

	/** Attribute. */
	private static final String BYTE = "byte";

	/** Attribute. */
	private static final String CHAR = "char";

	/** Attribute. */
	private static final String DOUBLE = "double";

	/** Attribute. */
	private static final String FLOAT = "float";

	/** Attribute. */
	private static final String INT = "int";

	/** Attribute. */
	private static final String LONG = "long";

	/** Attribute. */
	private static final String SHORT = "short";

	/**
	 * Constructor (private)
	 */
	private TuUtil() {
		super();
	}

	/**
	 * This method returns a collection of all methods beginning with the word
	 * "set". Duplicate methods have a prefixed key for subsequent exclusion.
	 * 
	 * @param methods
	 *            An array of <code>Method</code> objects.
	 * @return HashMap - A collection of all methods beginning with the word
	 *         "set".
	 */
	static HashMap<String, Method> collectSetters(final Method[] methods) {

		// Initialize.
		final HashMap<String, Method> setterMap = new HashMap<String, Method>();

		// Collect the setters.
		for (final Method element : methods) {

			// Determine if method is a setter.
			if (element.getName().startsWith("set")) {

				// Determine if multiple setters with different signatures.
				if (setterMap.containsKey(element.getName())) {

					// Remove the duplicate.
					setterMap.remove(element.getName());

					// Add the duplicate entry.
					setterMap.put(TuConstants.DUPLICATE_METHOD_KEY
							+ element.getName(), null);

				} else if (setterMap
						.containsKey(TuConstants.DUPLICATE_METHOD_KEY
								+ element.getName())) {

					// Do nothing.

				} else {
					// Add the setter to a collection.
					setterMap.put(element.getName(), element);
				}
			}
		}

		return setterMap;
	}

	/**
	 * This method returns a collection of all methods beginning with the words
	 * "get" or "is". Duplicate methods have a prefixed key for subsequent
	 * exclusion.
	 * 
	 * @param methods
	 *            An array of <code>Method</code> objects.
	 * @return HashMap - A collection of all methods beginning with the words
	 *         "get" or "is".
	 */
	static HashMap<String, Method> collectGetters(final Method[] methods) {

		// Initialization.
		final HashMap<String, Method> getterMap = new HashMap<String, Method>();

		// Collect the getters.
		for (final Method element : methods) {

			// Determine if method is a getter.
			if (element.getName().startsWith("get")
					|| element.getName().startsWith("is")) {

				// Determine if multiple getters with different signatures.
				if (getterMap.containsKey(element.getName())) {

					// Remove the duplicate.
					getterMap.remove(element.getName());

					// Add the duplicate entry.
					getterMap.put(TuConstants.DUPLICATE_METHOD_KEY
							+ element.getName(), null);

				} else if (getterMap
						.containsKey(TuConstants.DUPLICATE_METHOD_KEY
								+ element.getName())) {

					// Do nothing.

				} else {
					// Add the getter to a collection.
					getterMap.put(element.getName(), element);
				}
			}
		}
		return getterMap;
	}

	/**
	 * Attempts to return an instantiatable <code>Class</code>. (The optional
	 * descriptor documents are consulted for substitution.) The method is
	 * invoked from two contexts. Either a test instance is being instantiated
	 * for invocation by a method - or - an argumet constructor is being invoked
	 * requiring an instantiated object.
	 * 
	 * @param parameterType -
	 *            One of the method's original parameter types.
	 * @param returnType -
	 *            The getters return type or null.
	 * @param method -
	 *            The <code>Method</code> for which a verification object is
	 *            initialized or null.
	 * @param constructor -
	 *            The <code>Constructor</code> for which an instantiated
	 *            object is required or null.
	 * @return Class - Attempts to return an instantiatable <code>Class</code>.
	 * @throws TestUtilWarningException
	 */
	static final Class<?> checkForSubstituteSetterType(
			final Class<?> parameterType, final Class<?> returnType,
			final Method method, final Constructor<?> constructor)
			throws TestUtilWarningException {

		// Initialize.
		Class<?> substituteClass = parameterType;

		try {
			// Initialization
			substituteClass = TuUtil.substituteSetterType(substituteClass);

		} catch (final ClassNotFoundException e) {

			// That didn't work so well.
			TuExceptionWarningHandler.throwSubstitutionWarningForClassNotFound(
					parameterType.getName(), method, constructor, e
							.getMessage());
		} catch (final Error e) {

			// Note: Error - perhaps NoClassDefFoundError?
			TuExceptionWarningHandler.throwSubstitutionWarningForClassNotFound(
					parameterType.getName(), method, constructor,
					"This class excluded because of an error ["
							+ e.getClass().getName() + "].");
		}

		// Determine if parameter type (or substitute) is an interface.
		if (null != returnType) {

			if (substituteClass.isInterface()) {

				// Use the return type.
				substituteClass = returnType;

				// Do we have an abstract class with a concrete return type?
			} else if (Modifier.isAbstract(substituteClass.getModifiers())) {

				// Use the return type.
				substituteClass = returnType;
			}
		}

		return substituteClass;
	}

	/**
	 * This method references the applicable TO containing the optional
	 * descriptor document(s) declaration of substitution.
	 * 
	 * @param theClass
	 * @return Class - Attempts to return an instantiatable <code>Class</code>.
	 * @throws ClassNotFoundException
	 */
	private static final Class<?> substituteSetterType(final Class<?> theClass)
			throws ClassNotFoundException {

		// Initialize.
		Class<?> substituteClass = theClass;

		// Determine if substitute type declared.
		if (TuCacher.getInstance().getSubstituteSetterType().containsElement(
				theClass.getName(), null)) {

			// Obtain the descriptor element.
			final TuElementTO elementTO = TuCacher.getInstance()
					.getSubstituteSetterType().getElementTO(theClass.getName());

			// Substitute the parameter type.
			substituteClass = Class.forName(elementTO.getAttributeTwo());

		}
		return substituteClass;
	}

	/**
	 * This method references the applicable TO containing the optional
	 * descriptor document(s) declaration specified exclusion from verification.
	 * A return of <code>null</code> indicates an exclusion is not warranted.
	 * 
	 * @param theClass
	 *            Class being considered for exclusion.
	 * @return String - Identifies the exlusion reporting category or null.
	 * @since v. 2.0
	 */
	static final String excludeClassFromVerification(final Class<?> theClass) {

		// Initialize.
		String exclusion = null;

		// Determine if substitute type declared.
		if (TuCacher.getInstance().getExcludeClassFromVerification()
				.containsElement(theClass.getName(), null)) {

			// Obtain the descriptor element.
			final TuElementTO tuElementTO = TuCacher.getInstance()
					.getExcludeClassFromVerification().getElementTO(
							theClass.getName());

			// Substitute the parameter type.
			exclusion = tuElementTO.getAttributeTwo();

		}
		return exclusion;
	}

	/**
	 * This method references the Javadoc tag specifying exclusion from
	 * verification. A return of <code>null</code> indicates an exclusion is
	 * not warranted.
	 * 
	 * @param sourceCodePathname
	 *            A pathname string.
	 * @param theClass
	 *            Class being considered for exclusion.
	 * @return String - Identifies the exlusion reporting category or null.
	 * @since v. 2.0
	 */
	static final String excludeClassUsingJavadocTag(
			final String sourceCodePathname, final Class<?> theClass) {

		// Initialize.
		String exclusion = null;

		// Ensure full path name syntax.
		final String fileName = TuUtil.replace(sourceCodePathname + "\\"
				+ theClass.getName(), ".", "\\")
				+ ".java";

		// Aquire a builder.
		final JavaDocBuilder javaDocBuilder = new JavaDocBuilder();

		// Adding .java file.
		try {

			javaDocBuilder.addSource(new File(fileName));

		} catch (final Exception e) {

			// Early return.
			TuUtil.logJavadocTagIssue(fileName);
			return exclusion;

		} catch (final Error e) {

			// Early return.
			TuUtil.logJavadocTagIssue(fileName);
			return exclusion;

		}

		// Obtain the class.
		final JavaClass javaClass = javaDocBuilder.getClassByName(theClass
				.getName());

		// Obtain the applicable Javadoc tag.
		DocletTag docletTag = javaClass
				.getTagByName(TuConstants.TAG_EXCLUDE_CLASS);

		if (null != docletTag) {

			// Establish exclusion reporting category.
			exclusion = TuUtil.determineTagValue(docletTag);

		} else {

			// Obtain the other applicable Javadoc tag.
			docletTag = javaClass
					.getTagByName(TuConstants.TAG_EXCLUDE_CLASS_FROM_TESTUTIL_VERIFICATION);

			if (null != docletTag) {

				// Establish exclusion reporting category.
				exclusion = TuUtil.determineTagValue(docletTag);
			}
		}
		return exclusion;
	}

	/**
	 * This class provides log information to the console.
	 * 
	 * @param fileName
	 * @since v. 2.3
	 */

	private static final void logJavadocTagIssue(final String fileName) {

		// Retrieve the Javadoc tag value.
		System.out.println();

		System.out.println("FYI: Javadoc tag processing did not occur ["
				+ fileName + "].");

		System.out.println();
	}

	/**
	 * Ensure the class is excluded from verification.
	 * 
	 * @param docletTag
	 * @return String
	 */
	private static final String determineTagValue(final DocletTag docletTag) {

		// Retrieve the Javadoc tag value.
		String tagValue = docletTag.getValue().toLowerCase();

		if (!tagValue.equals(TuConstants.DOCUMENT_ATTRIBUTE_SKIP)) {

			// Replace with default.
			tagValue = TuConstants.DOCUMENT_ATTRIBUTE_CAUTION;
		}

		return tagValue;
	}

	/**
	 * Examines optional descriptor document for exclusions.
	 * 
	 * @param mutableObject
	 * @param sbSetterName
	 */
	static final void excludeMethodPairBySetter(final Object mutableObject,
			final StringBuffer sbSetterName) {

		// Determine if this setter is declared to be excluded.
		if (TuCacher.getInstance().getExcludeMethodPairBySetter()
				.containsElement(mutableObject.getClass().getName(),
						sbSetterName.toString())) {

			// Issue warning for exclusion.
			TuExceptionWarningHandler.throwExcludedWarningForSetter();
		}

		return;
	}

	/**
	 * Examines optional descriptor document for exclusions.
	 * 
	 * @param tuMethodSetBO
	 * @param tuMethodGetBO
	 * @throws TestUtilWarningException
	 */
	static final void excludeMethodPairsByType(
			final TuMethodSetBO tuMethodSetBO, final TuMethodGetBO tuMethodGetBO)
			throws TestUtilWarningException {

		// Check the getter.
		if (TuCacher.getInstance().getExcludeMethodPairsByType()
				.containsElementGetter(tuMethodGetBO.getReturnType().getName())) {

			// Throw a warning.
			TuExceptionWarningHandler
					.throwExcludedWarningForReturnType(tuMethodGetBO
							.getReturnType().getName());
		}

		// Iterate through the setter array.
		for (int i = 0; i < tuMethodSetBO.getOriginalParameterTypes().length; i++) {

			// Determine if a data type was excluded.
			if (TuCacher.getInstance().getExcludeMethodPairsByType()
					.containsElementSetter(
							tuMethodSetBO.getOriginalParameterTypes()[i]
									.getName())) {

				// Throw a warning.
				TuExceptionWarningHandler
						.throwExcludedWarningForParameter(tuMethodSetBO
								.getOriginalParameterTypes()[i].getName());
			}
		}
		return;
	}

	/**
	 * This method implements Javadoc tag declaration of excluded method pairs.
	 * 
	 * @param sourceCodePathname
	 *            A pathname string.
	 * @param theClass
	 * @return JavaMethod[]
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @since v. 2.0
	 */
	static final JavaMethod[] obtainMethodJavadoc(
			final String sourceCodePathname, final Class<?> theClass)
			throws FileNotFoundException, IOException {

		// Ensure full path name syntax.
		final String fileName = TuUtil.replace(sourceCodePathname + "\\"
				+ theClass.getName(), ".", "\\")
				+ ".java";

		// Aquire a builder.
		final JavaDocBuilder javaDocBuilder = new JavaDocBuilder();

		// Adding .java file.
		javaDocBuilder.addSource(new File(fileName));

		// Obtain the class.
		final JavaClass javaClass = javaDocBuilder.getClassByName(theClass
				.getName());

		// Obtain a list of all methods.
		return javaClass.getMethods();

	}

	/**
	 * This method excludes methods from being invoked by TestUtil.
	 * 
	 * @param javaMethods
	 * @param setterName
	 * @return boolean
	 * @since v. 2.0
	 */
	static final boolean excludeMethodUsingJavadocTag(
			final JavaMethod[] javaMethods, final String setterName) {

		// Declaration.
		DocletTag docletTag;

		// Initialization.
		boolean excludeMethod = false;

		// Iterate through the methods.
		for (final JavaMethod element : javaMethods) {

			// If the names match.
			if (setterName.equals(element.getName())) {

				// Obtain the applicable Javadoc tag.
				docletTag = element
						.getTagByName(TuConstants.TAG_EXCLUDE_SETTER);

				// Check for tag.
				if (null != docletTag) {

					// Exclude this setter method.
					excludeMethod = true;
				}

				else {

					// Obtain the other applicable Javadoc tag.
					docletTag = element
							.getTagByName(TuConstants.TAG_EXCLUDE_SETTER_FROM_TESTUTIL_VERIFICATION);

					// Check for tag.
					if (null != docletTag) {

						// Exclude this setter method.
						excludeMethod = true;
					}
				}
				break;
			}
		}
		return excludeMethod;
	}

	/**
	 * This method instantiates objects. It is called in two different contexts:
	 * <ul>
	 * <li>The <code>Class</code> is a parameter data type or recursively
	 * invoked.</li>
	 * <li>The <code>Class</code> is a candidate immutable object for
	 * subsequent verification.</li>
	 * </ul>
	 * 
	 * @param theClass -
	 *            A <code>Class</code> to be instantiated.
	 * @param loopCounter -
	 *            Used to guard against an endless loop.
	 * @return Object - An instantiated <code>Object</code> (instance) or
	 *         null.
	 */
	static final Object instantiateObjectFromClass(final Class<?> theClass,
			final int loopCounter) {

		// Determine if object is a String.
		if (TuConstants.STRING_CLASS_NAME.equals(theClass.getName())) {

			// Fast return with random string.
			final Integer intForString = new Integer(random.nextInt());
			return new String("S" + intForString.toString());
		}

		// Determine if Enum.
		if (TuConstants.ENUM_CLASS_NAME.equals(theClass.getName())) {

			return EnumMock.RANDOM;
		}

		// Initialization.
		Object objInstantiated = null;
		final Constructor<?> constructor = retrieveNoArgumentConstructor(theClass);

		if (null == constructor) {

			// Instantiate the parameter.
			objInstantiated = instantiateObjectWithArgumentConstructor(
					theClass, loopCounter);

		} else {
			// Instantiate the parameter.
			objInstantiated = instantiateObjectWithNoArgumentConstructor(
					theClass, constructor);
		}

		return objInstantiated;
	}

	/**
	 * Returns an instantiated <code>Object</code> (instance) or null.
	 * 
	 * @param theClass
	 *            A method's parameter data type <code>Class</code>.
	 * @param constructor
	 * @return Object - An instantiated <code>Object</code> (instance) or
	 *         null.
	 * @throws TestUtilWarningException
	 */
	private static final Object instantiateObjectWithNoArgumentConstructor(
			final Class<?> theClass, final Constructor<?> constructor)
			throws TestUtilWarningException {

		// Declare/Initialize
		Object objInstantiated = null;

		try {
			// Instantiate.
			objInstantiated = constructor.newInstance(null);

		} catch (final Exception e) {

			// That did not work so well.
			TuExceptionWarningHandler.throwInstantiationWarning(theClass, e);
		} catch (final Error e) {

			// Note: Error - perhaps NoClassDefFoundError?
			TuExceptionWarningHandler.throwInstantiationWarning(theClass,
					new RuntimeException(
							"The class can not be instantiated because of an error ["
									+ e.getClass().getName() + "]."));
		}

		return objInstantiated;
	}

	/**
	 * Returns an instantiated <code>Object</code> (instance) or null.
	 * 
	 * @param theClass
	 *            A method's parameter data type <code>Class</code>.
	 * @param loopCounter -
	 *            Used to guard against an endless loop.
	 * @return Object - An instantiated <code>Object</code> (instance) or
	 *         null.
	 * @throws TestUtilWarningException
	 */
	private static final Object instantiateObjectWithArgumentConstructor(
			final Class<?> theClass, final int loopCounter)
			throws TestUtilWarningException {

		// Declare/Initialize
		Object objInstantiated = null;

		// Retrieve the constructors.
		final Constructor<?>[] constructors = theClass
				.getDeclaredConstructors();

		// Iterate through the constructors.
		for (int i = 0; i < constructors.length; i++) {

			// Retrieve the parameter types.
			final Class<?> constructorParamTypes[] = constructors[i]
					.getParameterTypes();

			// Iterate through the parameter types for possible substitution.
			for (int ii = 0; ii < constructorParamTypes.length; ii++) {

				// Check for primitives - they should not be substituted.
				if (!constructorParamTypes[ii].isPrimitive()) {

					// Retrieve a candidate substitution if needed.
					final Class<?> originalOrSubstituteClass = TuUtil
							.checkForSubstituteSetterType(
									constructorParamTypes[ii], null, null,
									constructors[i]);

					// Set the original or candidate substitution type.
					constructorParamTypes[ii] = originalOrSubstituteClass;
				}
			}

			// Populate the parameter values.
			final Object objParamValues[] = initializeValues(theClass,
					constructorParamTypes, null, constructors[i], loopCounter);

			try {

				// Instantiate new instance.
				objInstantiated = constructors[i].newInstance(objParamValues);

				// Break out of loop.
				break;

			} catch (final Exception e1) {

				try {

					// Determine if this is the last constructor.
					if (i + 1 >= constructors.length) {

						// No more constructors.
						throw e1;
					}

					//
					// Else - continue with another constructor.
					//

				} catch (final Exception e2) {

					// That didn't work so well.
					TuExceptionWarningHandler.throwInstantiationWarning(
							theClass, e2, constructors);

				}
			} catch (final Error e) {

				// Note: Error - perhaps NoClassDefFoundError?
				TuExceptionWarningHandler.throwInstantiationWarning(theClass,
						new RuntimeException(
								"The class can not be instantiated because of an error ["
										+ e.getClass().getName() + "]."),
						constructors);

			}
		}

		return objInstantiated;
	}

	/**
	 * Returns a result object after using reflection to invoke a method.
	 * 
	 * @param tuMethodBO
	 *            Abstract Business Object used for invoking a method with
	 *            reflection.
	 * @param booleanVerification
	 *            Indicates if a invocation should happen twice - once with
	 *            'true' and once with 'false'.
	 * @return Object - The object returned from the method invoked.
	 */
	static Object invokeMethod(final TuMethodBO tuMethodBO,
			final boolean booleanVerification) {

		// Initialization.
		Object objReturned = null;
		String parameterTypeName;

		if (tuMethodBO.containsParameterTypes()) {
			parameterTypeName = tuMethodBO.getOriginalParameterTypes()[0]
					.getName();
		} else {
			parameterTypeName = "n/a";
		}

		try {

			// Determine if a boolean verification.
			if (booleanVerification) {

				// Invoke the method.
				objReturned = tuMethodBO.getMethod().invoke(
						tuMethodBO.getMutableObject(),
						tuMethodBO.getParameterValuesForBoolean());

			} else {

				// Invoke the method.
				objReturned = tuMethodBO.getMethod().invoke(
						tuMethodBO.getMutableObject(),
						tuMethodBO.getParameterValues());
			}

		} catch (final Exception e) {

			TuExceptionGlitchHandler.throwMethodInvocationGlitch(tuMethodBO,
					parameterTypeName, e);
		}

		// Return the returned.
		return objReturned;
	}

	/**
	 * This method provides a control function to determine the strategy for
	 * instantiating a parameter (data) type.
	 * 
	 * @param theClass
	 *            The class for which values are being initialized.
	 * @param paramTypes
	 *            Collection of parameter types.
	 * @param method -
	 *            The <code>Method</code> for which a verification object is
	 *            initialized or null.
	 * @param constructor -
	 *            The <code>Constructor</code> for which an instantiated
	 *            object is required or null.
	 * @param loopCounter -
	 *            Used to guard against an endless loop.
	 * @return Object[] - An array of initialized parameter objects for
	 *         verification.
	 */
	static final Object[] initializeValues(final Class<?> theClass,
			final Class<?>[] paramTypes, final Method method,
			final Constructor<?> constructor, int loopCounter) {

		// Increment
		loopCounter++;

		// Determine if endless loop.
		if (15 < loopCounter) {

			TuExceptionWarningHandler.throwEndlessLoopWarning(loopCounter,
					theClass);
		}

		// Initialize.
		final Object[] parameterValues = new Object[paramTypes.length];

		// Iterate through the parameter types.
		for (int i = 0; i < paramTypes.length; i++) {

			// final int modifiers = paramTypes[i].getModifiers();
			// System.out.println(modifiers);
			// System.out.println(paramTypes[i].getSuperclass());

			if (null == paramTypes[i] || paramTypes[i].isInterface()) {

				// Initialize the parameter.
				parameterValues[i] = TuDynamicProxy.newInstance(paramTypes[i]);

			} else if (paramTypes[i].isPrimitive()) {

				// Initialize the parameter.
				parameterValues[i] = TuUtil.initializePrimitive(paramTypes[i]);

			} else if (paramTypes[i].isArray()) {

				// Initialize the parameter.
				parameterValues[i] = Array.newInstance(paramTypes[i]
						.getComponentType(), 0);

			} else if (paramTypes[i] == java.lang.Enum.class) {

				// Retrieve elements of the enum class or null.
				final Object[] enumConstants = paramTypes[i].getEnumConstants();

				// Determine if a valid enum was retrieved.
				if (null == enumConstants || 0 == enumConstants.length) {

					// A mock enum will be used.
					parameterValues[i] = instantiateObjectFromClass(
							paramTypes[i], loopCounter);
				} else {
					parameterValues[i] = enumConstants[0];
				}

			} else if (Modifier.isAbstract(paramTypes[i].getModifiers())) {

				// Issue warning for exclusion.
				TuExceptionWarningHandler.throwAbstractClassWarning(
						paramTypes[i], method, constructor);

			} else {

				// Initialize the parameter.
				parameterValues[i] = instantiateObjectFromClass(paramTypes[i],
						loopCounter);
			}
		}
		return parameterValues;
	}

	/**
	 * Returns an array of initialized parameter objects used for verification.
	 * 
	 * @param parameterValues
	 *            An array of initialized parameter objects for verification.
	 * @return Object[] - An array of initialized parameter objects used for
	 *         verification.
	 */
	static final Object[] initializeValuesForBoolean(
			final Object[] parameterValues) {

		// Initialize.
		final Object[] parameterValuesForBoolean = new Object[parameterValues.length];

		// Iterate through the paramter types.
		for (int i = 0; i < parameterValues.length; i++) {

			// Check for Boolean.
			if (parameterValues[i] instanceof Boolean) {

				// Opposing (false) assignment.
				parameterValuesForBoolean[i] = Boolean.FALSE;

			} else {

				// Initialize the parameter with same value.
				parameterValuesForBoolean[i] = parameterValues[i];
			}
		}
		return parameterValuesForBoolean;
	}

	/**
	 * Return a string with characters replaced.
	 * 
	 * @since v. 2.0
	 * 
	 * @param source
	 * @param pattern
	 * @param replace
	 * @return String
	 */
	static String replace(final String source, final String pattern,
			final String replace) {

		if (source != null) {
			final int len = pattern.length();
			final StringBuffer sb = new StringBuffer();
			int found = -1;
			int start = 0;

			while ((found = source.indexOf(pattern, start)) != -1) {
				sb.append(source.substring(start, found));
				sb.append(replace);
				start = found + len;
			}

			sb.append(source.substring(start));

			return sb.toString();
		}

		return "";
	}

	/**
	 * Returns an initialized parameter object used for verification.
	 * 
	 * @param parameterType
	 *            A method's parameter data type <code>Class</code>.
	 * @return Object - An initialized parameter object used for verification.
	 */
	private static final Object initializePrimitive(final Class<?> parameterType) {

		// Declare/Initialize
		Object objPrimitive = null;

		// Initialize boolean.
		if (BOOLEAN.equals(parameterType.getName())) {

			// Default assignment.
			objPrimitive = Boolean.TRUE;

		} else if (BYTE.equals(parameterType.getName())) {

			// Random selection.
			final byte[] byteArray = new byte[1];
			random.nextBytes(byteArray);
			objPrimitive = new Byte(byteArray[0]);

		} else if (CHAR.equals(parameterType.getName())) {

			// Random selection.
			objPrimitive = new Character((char) random.nextInt(65536));

		} else if (DOUBLE.equals(parameterType.getName())) {

			// Random selection.
			objPrimitive = new Double(random.nextDouble());

		} else if (FLOAT.equals(parameterType.getName())) {

			// Random selection.
			objPrimitive = new Float(random.nextFloat());

		} else if (INT.equals(parameterType.getName())) {

			// Random selection.
			objPrimitive = new Integer(random.nextInt());

		} else if (LONG.equals(parameterType.getName())) {

			// Random selection.
			objPrimitive = new Long(random.nextLong());

		} else if (SHORT.equals(parameterType.getName())) {

			// Random selection.
			objPrimitive = new Short((short) random.nextInt(32767));
		}

		return objPrimitive;
	}

	/**
	 * Returns a no argument <code>Constructor</code> or null.
	 * 
	 * @param theClass
	 *            A method's parameter data type <code>Class</code>.
	 * @return Constructor - A no argument <code>Constructor</code> or null.
	 */
	private static Constructor<?> retrieveNoArgumentConstructor(
			final Class<?> theClass) {

		// Initialization.
		Constructor<?> constructorTemp;
		Constructor<?> constructorReturned = null;

		// Retrieve the constructors.
		final Constructor<?>[] constructors = theClass
				.getDeclaredConstructors();

		// Iterate through the list looking for a no argument constructor.
		for (final Constructor<?> element : constructors) {

			// Retrieve the constructor.
			constructorTemp = element;

			// Determine if there is a no argument constructor.
			if (constructorTemp.getParameterTypes().length == 0) {

				// Check for accessibility.
				if (constructorTemp.isAccessible()) {

					// Fast return.
					constructorReturned = constructorTemp;
					break;
				}

				try {
					// Enable use of a non-public constructor.
					constructorTemp.setAccessible(true);

					// Fast return.
					constructorReturned = constructorTemp;
					break;

				} catch (final SecurityException e) {
					// Do Nothing - Try the next constructor.
				}
			}
		}

		return constructorReturned;
	}
}