package com.gtcgroup.testutil.helper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import com.gtcgroup.testutil.TuMethodPairBO;
import com.gtcgroup.testutil.exception.TestUtilWarningException;

/**
 * <p>
 * This class provides a set of static methods associated with throwing and
 * logging exceptions.
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 1999 - 2017 by Global Technology Consulting Group, Inc. at
 * <a href="http://gtcGroup.com">gtcGroup.com </a>. <br />
 * Use is authorized, provided the source is acknowledged by inclusion of this
 * copyright notice.
 * </p>
 *
 * @author MarvinToll@gtcGroup.com
 * @since v. 1.0
 */
public enum TuWarningUtilHelper {

	INSTANCE;

	public synchronized static final void processWarning(final StringBuffer sbSetterName,
			final StringBuffer sbGetterName, final TuMethodPairBO tuMethodPairBO,
			final TestUtilWarningException warning) {

		// Process warning.
		final StringBuffer message = new StringBuffer();

		message.append("\n\tWarning [# ");
		// message.append(tuCountersBO.getTotalWarnings());
		message.append("]");

		if (null != sbGetterName && null != sbSetterName) {
			message.append(":  Setter = [");
			message.append(sbSetterName.toString());
			message.append("()];  Getter = [");
			message.append(sbGetterName.toString());

			// if (null != tuMethodPairBO) {
			// message.append(" " +
			// tuMethodPairBO.getTuMethodGetBO().getMethod().getName());
			// }
			message.append("()]");
		}
		message.append("\n\t");
		message.append(warning.getMessage());

		System.out.println(message.toString());
	}

	public synchronized static final void throwAbstractClassWarning(final Class<?> clazz, final Method method,
			final Constructor<?> constructor) {

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

		throw new TestUtilWarningException(message.toString());
	}

	public synchronized static final void throwEndlessLoopWarning(final int loopCounter, final Class<?> theClass) {

		// Decrement.
		int loopTemp = loopCounter;
		loopTemp--;

		throw new TestUtilWarningException(
				"An endless loop [" + theClass + "] attempting instantiation [" + loopTemp + "] times.");
	}

	public synchronized static final void throwInstantiationWarning(final Class<?> theClass, final Exception e,
			final Constructor<?>[] constructors) {

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
		// message.append("\n\tConsider declaring a substitute type [");
		// message.append(TuConstants.DESCRIPTOR_DOCUMENT_NAME);
		// message.append("] for verification.");

		throw new TestUtilWarningException(message.toString());
	}

	public synchronized static final void throwMultiplesWarningForGetter(final String sbGetterName) {

		throw new TestUtilWarningException(
				"Multiple occurrences of getter [" + sbGetterName + "] precludes verification.");
	}

	public synchronized static final void throwMultiplesWarningForSetter(final String sbSetterName) {

		throw new TestUtilWarningException(
				"Multiple occurrences of setter [" + sbSetterName + "] precludes verification.");
	}

	public synchronized static final void throwNotVerifiableWarning(final String parameterType,
			final String returnType) {

		throw new TestUtilWarningException("Setter first parameter [" + parameterType + "] and getter return type ["
				+ returnType + "] are not TestUtil verifiable.");
	}

	/**
	 * This method throws an exception.
	 *
	 * @throws TestUtilSetterSignatureWarning
	 */
	public synchronized static final void throwSetterSignatureWarning() {

		throw new TestUtilWarningException("The setter signature has no parameter and can not be TestUtil verified.");
	}
}
