package com.gtcgroup.testutil;

import com.gtcgroup.testutil.exceptions.TestUtilGlitchException;
import com.gtcgroup.testutil.exceptions.TestUtilMethodInvocationGlitch;
import com.gtcgroup.testutil.exceptions.TestUtilNotEqualGlitch;
import com.gtcgroup.testutil.exceptions.TestUtilNotSameGlitch;
import com.gtcgroup.testutil.exceptions.TestUtilProxyGlitch;

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
final class TuExceptionGlitchHandler {

	/**
	 * Constructor (private)
	 */
	private TuExceptionGlitchHandler() {
		super();
	}

	/**
	 * This method displays glitches.
	 * 
	 * @param tuMethodSetBO
	 * @param tuMethodGetBO
	 * @param tuCountersBO
	 * @param exception
	 */
	synchronized static final void processGlitch(
			final TuMethodSetBO tuMethodSetBO,
			final TuMethodGetBO tuMethodGetBO, final TuCountersBO tuCountersBO,
			final TestUtilGlitchException exception) {

		// Process glitch.
		StringBuffer message = new StringBuffer();

		message.append("\n\tGlitch [# ");
		message.append(tuCountersBO.getTotalGlitches());
		message.append("]:  Setter = [");
		message.append(tuMethodSetBO.getMethod().getName());
		message.append("()];  Getter = [");
		message.append(tuMethodGetBO.getMethod().getName());
		message.append("()]\n\t");
		message.append(exception.getMessage());

		System.out.println(message.toString());
	}

	/**
	 * This method throws an exception.
	 * 
	 * @param tuMethodBO
	 * @param parameterTypeName
	 * @param exception
	 * @throws TestUtilMethodInvocationGlitch
	 */
	synchronized static final void throwMethodInvocationGlitch(
			final TuMethodBO tuMethodBO, final String parameterTypeName,
			final Exception exception) throws TestUtilMethodInvocationGlitch {

		StringBuffer message = new StringBuffer();

		message.append("The method [");
		message.append(tuMethodBO.getMethod().getName());
		message.append("] could not be invoked [");
		try {
			if (null == exception.getCause()) {
				message.append(exception.getClass().getName());
			} else {
				message.append(exception.getCause());
			}
		} catch (Throwable e1) {
			message.append(exception.getClass().getName());
		}
		message.append("] with [");
		message.append(parameterTypeName);
		message.append("] parameter type.");

		throw new TestUtilMethodInvocationGlitch(message.toString());
	}

	/**
	 * This method throws an exception.
	 * 
	 * @param objThatWasSet
	 * @param objReturnedFromGet
	 * @throws TestUtilNotEqualGlitch
	 */
	synchronized static final void throwNotEqualGlitch(
			final String objThatWasSet, final String objReturnedFromGet)
			throws TestUtilNotEqualGlitch {

		throw new TestUtilNotEqualGlitch("Randomly set [" + objThatWasSet
				+ "] value does not match returned [" + objReturnedFromGet
				+ "] value.");
	}

	/**
	 * This method throws an exception.
	 * 
	 * @param objSet
	 * @throws TestUtilNotSameGlitch
	 */
	synchronized static final void throwNotSameGlitch(final String objSet)
			throws TestUtilNotSameGlitch {

		throw new TestUtilNotSameGlitch("Randomly set value [" + objSet
				+ "] does not match returned [null] value.");
	}

	/**
	 * This method throws an exception.
	 * 
	 * @param objSet
	 * @param objReturned
	 * @throws TestUtilProxyGlitch
	 */
	synchronized static final void throwProxyGlitch(final String objSet,
			final String objReturned) throws TestUtilProxyGlitch {

		throw new TestUtilProxyGlitch("The proxy object [" + objSet
				+ "] set does not match the proxy object [" + objReturned
				+ "] returned.");
	}
}
