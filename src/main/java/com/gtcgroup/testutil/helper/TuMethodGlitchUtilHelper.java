package com.gtcgroup.testutil.helper;

import com.gtcgroup.testutil.TuMethodGetBO;
import com.gtcgroup.testutil.exception.TestUtilGlitchException;
import com.gtcgroup.testutil.po.TuMethodPO;
import com.gtcgroup.testutil.po.TuMethodSetPO;

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
public enum TuMethodGlitchUtilHelper {

	INSTANCE;

	public synchronized static final void processGlitch(final TuMethodSetPO tuMethodSetBO,
			final TuMethodGetBO tuMethodGetBO, final TestUtilGlitchException exception) {

		// Process glitch.
		final StringBuffer message = new StringBuffer();

		message.append("\n\tGlitch [# ");
		// message.append(tuCountersBO.getTotalGlitches());
		message.append("]:  Setter = [");
		message.append(tuMethodSetBO.getMethod().getName());
		message.append("()];  Getter = [");
		message.append(tuMethodGetBO.getMethod().getName());
		message.append("()]\n\t");
		message.append(exception.getMessage());

		System.out.println(message.toString());
	}

	public synchronized static final void throwMethodInvocationGlitch(final TuMethodPO tuMethodBO,
			final String parameterTypeName, final Exception exception) {

		final StringBuffer message = new StringBuffer();

		message.append("The method [");
		message.append(tuMethodBO.getMethod().getName());
		message.append("] could not be invoked [");
		try {
			if (null == exception.getCause()) {
				message.append(exception.getClass().getName());
			} else {
				message.append(exception.getCause());
			}
		} catch (final Throwable e1) {
			message.append(exception.getClass().getName());
		}
		message.append("] with [");
		message.append(parameterTypeName);
		message.append("] parameter type.");

		throw new TestUtilGlitchException(message.toString());
	}

	public synchronized static final void throwNotEqualGlitch(final String objThatWasSet,
			final String objReturnedFromGet) {

		throw new TestUtilGlitchException("Randomly set [" + objThatWasSet + "] value does not match returned ["
				+ objReturnedFromGet + "] value.");
	}

	public synchronized static final void throwNotSameGlitch(final String objSet) {

		throw new TestUtilGlitchException("Randomly set value [" + objSet + "] does not match returned [null] value.");
	}

	public synchronized static final void throwProxyGlitch(final String objSet, final String objReturned) {

		throw new TestUtilGlitchException("The proxy object [" + objSet + "] set does not match the proxy object ["
				+ objReturned + "] returned.");
	}
}
