package com.gtcgroup.testutil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import com.gtcgroup.testutil.exceptions.TestUtilAbstractClassWarning;
import com.gtcgroup.testutil.exceptions.TestUtilEndlessLoopWarning;
import com.gtcgroup.testutil.exceptions.TestUtilExcludedWarning;
import com.gtcgroup.testutil.exceptions.TestUtilInstantiationWarning;
import com.gtcgroup.testutil.exceptions.TestUtilMultiplesWarning;
import com.gtcgroup.testutil.exceptions.TestUtilNotVerifiableWarning;
import com.gtcgroup.testutil.exceptions.TestUtilSetterSignatureWarning;
import com.gtcgroup.testutil.exceptions.TestUtilSubstitutionWarning;
import com.gtcgroup.testutil.exceptions.TestUtilWarningException;

/**
 * <p>
 * This class provides a set of static methods associated with throwing and
 * logging exceptions.
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 1999 - 2008 by Global Technology Consulting Group, Inc. at <a
 * href="http://gtcGroup.com">gtcGroup.com </a>. <br />
 * Use is authorized, provided the source is acknowledged by inclusion of this
 * copyright notice.
 * </p>
 * 
 * @author MarvinToll@gtcGroup.com
 * @since v. 1.0
 */
final class TuExceptionWarningHandler {

	/**
	 * Constructor (private)
	 */
	private TuExceptionWarningHandler() {
		super();
	}

	/**
	 * This method displays warnings.
	 * 
	 * @param sbSetterName
	 * @param sbGetterName
	 * @param tuMethodPairBO
	 * @param tuCountersBO
	 * @param warning
	 */
	synchronized static final void processWarning(
			final StringBuffer sbSetterName, final StringBuffer sbGetterName,
			final TuMethodPairBO tuMethodPairBO,
			final TuCountersBO tuCountersBO,
			final TestUtilWarningException warning) {

		// Process warning.
		final StringBuffer message = new StringBuffer();

		message.append("\n\tWarning [# ");
		message.append(tuCountersBO.getTotalWarnings());
		message.append("]");

		if (null != sbGetterName && null != sbSetterName) {
			message.append(":  Setter = [");
			message.append(sbSetterName.toString());
			message.append("()];  Getter = [");
			message.append(sbGetterName.toString());

			if (null != tuMethodPairBO) {
				message.append(" "
						+ tuMethodPairBO.getTuMethodGetBO().getMethod()
								.getName());
			}
			message.append("()]");
		}
		message.append("\n\t");
		message.append(warning.getMessage());

		System.out.println(message.toString());
	}

	/**
	 * This method throws an exception.
	 * 
	 * @param parameterType
	 * @param returnType
	 * @throws TestUtilNotVerifiableWarning
	 */
	synchronized static final void throwNotVerifiableWarning(
			final String parameterType, final String returnType)
			throws TestUtilNotVerifiableWarning {

		throw new TestUtilNotVerifiableWarning("Setter first parameter ["
				+ parameterType + "] and getter return type [" + returnType
				+ "] are not TestUtil verifiable.");
	}

	/**
	 * This method throws an exception.
	 * 
	 * @param sbSetterName
	 * @throws TestUtilMultiplesWarning
	 */
	synchronized static final void throwMultiplesWarningForSetter(
			final String sbSetterName) throws TestUtilMultiplesWarning {

		throw new TestUtilMultiplesWarning("Multiple occurrences of setter ["
				+ sbSetterName + "] precludes verification.");
	}

	/**
	 * This method throws an exception.
	 * 
	 * @param sbGetterName
	 * @throws TestUtilMultiplesWarning
	 */
	synchronized static final void throwMultiplesWarningForGetter(
			final String sbGetterName) throws TestUtilMultiplesWarning {

		throw new TestUtilMultiplesWarning("Multiple occurrences of getter ["
				+ sbGetterName + "] precludes verification.");
	}

	/**
	 * This method throws an exception.
	 * 
	 * @throws TestUtilExcludedWarning
	 */
	synchronized static final void throwExcludedWarningForSetter()
			throws TestUtilExcludedWarning {

		// No equivalent needed for getter.
		throw new TestUtilExcludedWarning(
				"The setter method was excluded by declaration ["
						+ TuConstants.DESCRIPTOR_DOCUMENT_NAME
						+ "] or Javadoc tag.");
	}

	/**
	 * This method throws an exception.
	 * 
	 * @param parameterType
	 * @throws TestUtilExcludedWarning
	 */
	synchronized static final void throwExcludedWarningForParameter(
			final String parameterType) throws TestUtilExcludedWarning {

		throw new TestUtilExcludedWarning("The setter parameter type ["
				+ parameterType + "] declared ["
				+ TuConstants.DESCRIPTOR_DOCUMENT_NAME
				+ "] was excluded from verification.");
	}

	/**
	 * This method throws an exception.
	 * 
	 * @param returnType
	 * @throws TestUtilExcludedWarning
	 */
	synchronized static final void throwExcludedWarningForReturnType(
			final String returnType) throws TestUtilExcludedWarning {

		throw new TestUtilExcludedWarning("The getter return type ["
				+ returnType + "] declared ["
				+ TuConstants.DESCRIPTOR_DOCUMENT_NAME
				+ "] was excluded from verification.");
	}

	/**
	 * This method throws an exception.
	 * 
	 * @throws TestUtilSetterSignatureWarning
	 */
	synchronized static final void throwSetterSignatureWarning()
			throws TestUtilSetterSignatureWarning {

		throw new TestUtilSetterSignatureWarning(
				"The setter signature has no parameter and can not be TestUtil verified.");
	}

	/**
	 * This method throws an exception.
	 * 
	 * @param clazz
	 * @param method
	 * @param constructor
	 * @throws TestUtilAbstractClassWarning
	 */
	synchronized static final void throwAbstractClassWarning(
			final Class<?> clazz, final Method method,
			final Constructor<?> constructor)
			throws TestUtilAbstractClassWarning {

		// Initialization.
		final StringBuffer message = new StringBuffer();

		message.append("Unable to reflectively process a ");

		// If the method is not null.
		if (null != method) {
			message.append("method [" + method.getName());
		}
		// If the constructor is not null.
		if (null != constructor) {
			message.append("constructor [" + constructor.getName());
		}

		message.append("()] signature specifying an abstract class [");
		message.append(clazz.getName());
		message.append("].");

		message.append("\n\tConsider declaring a substitute type [");
		message.append(TuConstants.DESCRIPTOR_DOCUMENT_NAME);
		message.append("] for verification.");

		throw new TestUtilAbstractClassWarning(message.toString());
	}

	/**
	 * This method throws an exception.
	 * 
	 * @param parameterType
	 *            The name to be used in the exception message.
	 * @param method -
	 *            The <code>Method</code> for which a verification object is
	 *            initialized or null.
	 * @param constructor -
	 *            The <code>Constructor</code> for which an instantiated
	 *            object is required or null.
	 * @param exceptionMessage -
	 *            The originating exception.
	 * @throws TestUtilSubstitutionWarning
	 */
	synchronized static void throwSubstitutionWarningForClassNotFound(
			final String parameterType, final Method method,
			final Constructor<?> constructor, final String exceptionMessage)
			throws TestUtilSubstitutionWarning {

		// Initialization.
		final StringBuffer message = new StringBuffer();

		message.append("Class Not Found - The ");

		// If the method is not null.
		if (null != method) {
			message.append("method [" + method.getName());
		}
		// If the constructor is not null.
		if (null != constructor) {
			message.append("constructor [" + constructor.getName());
		}

		message.append("()] with parameter type [");
		message.append(parameterType);
		message.append("] \n\tcan not use [");
		message.append(exceptionMessage);
		message.append("] as a declared [");
		message.append(TuConstants.DESCRIPTOR_DOCUMENT_NAME);
		message.append("] substitute.");

		throw new TestUtilSubstitutionWarning(message.toString());
	}

	/**
	 * This method throws an exception.
	 * 
	 * @param theClass
	 * @param loopCounter
	 * @throws TestUtilWarningException
	 */
	synchronized static final void throwEndlessLoopWarning(
			final int loopCounter, final Class<?> theClass) {

		// Decrement.
		int loopTemp = loopCounter;
		loopTemp--;

		throw new TestUtilEndlessLoopWarning("An endless loop [" + theClass
				+ "] attempting instantiation [" + loopTemp + "] times.");
	}

	/**
	 * This method throws an exception.
	 * 
	 * @param theClass
	 * 
	 * @param e
	 * @throws TestUtilInstantiationWarning
	 */
	synchronized static final void throwInstantiationWarning(
			final Class<?> theClass, final Exception e)
			throws TestUtilInstantiationWarning {

		throwInstantiationWarning(theClass, e, null);

	}

	/**
	 * This method throws an exception.
	 * 
	 * @param theClass
	 * @param e
	 * @param constructors
	 * @throws TestUtilInstantiationWarning
	 */
	synchronized static final void throwInstantiationWarning(
			final Class<?> theClass, final Exception e,
			final Constructor<?>[] constructors)
			throws TestUtilInstantiationWarning {

		final StringBuffer message = new StringBuffer();
		message.append("The class [");
		message.append(theClass.getName());
		message.append("] ");

		if (null != constructors) {
			message.append("with [");
			message.append(constructors.length);
			message.append("] constructor(s) ");
		}
		message.append("could not be instantiated [");
		try {
			if (null == e.getCause()) {
				message.append(e.getClass().getName());
			} else {
				message.append(e.getCause());
			}
		} catch (final Throwable throwable) {
			message.append(e.getClass().getName());
		}
		message.append("].");
		message.append("\n\tConsider declaring a substitute type [");
		message.append(TuConstants.DESCRIPTOR_DOCUMENT_NAME);
		message.append("] for verification.");

		throw new TestUtilInstantiationWarning(message.toString());
	}
}
