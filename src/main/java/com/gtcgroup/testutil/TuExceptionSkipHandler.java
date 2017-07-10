package com.gtcgroup.testutil;

import com.gtcgroup.testutil.exception.TestUtilSkipException;

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
public enum TuExceptionSkipHandler {

	INSTANCE;

	/**
	 * This method throws an exception.
	 */
	public static final void throwSkipException(final String className) {

		// Process caution.
		final StringBuffer message = new StringBuffer();

		message.append("\n<Skip [# ");
		// message.append(tuCountersListBO.getTotalSkips());
		message.append("]: Not verifiable [");
		message.append(className);
		message.append("].");

		throw new TestUtilSkipException(message.toString());
	}
}
