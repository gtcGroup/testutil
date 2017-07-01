package com.gtcgroup.testutil;

import com.gtcgroup.testutil.exceptions.TestUtilSkipException;

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
final class TuExceptionSkipHandler {

	/**
	 * Constructor (private)
	 */
	private TuExceptionSkipHandler() {
		super();
	}

	/**
	 * This method displays a caution.
	 * 
	 * @param fileName
	 * @param tuCountersListBO
	 * @param skip
	 */
	synchronized static final void processSkip(final String fileName,
			final TuCountersListBO tuCountersListBO,
			final TestUtilSkipException skip) {

		// Process caution.
		StringBuffer message = new StringBuffer();

		message.append("\n<Skip [# ");
		message.append(tuCountersListBO.getTotalSkips());
		message.append("]: Not verifiable [");
		message.append(fileName);
		message.append("] entity [");
		message.append(skip.getMessage());
		message.append("].");

		System.out.println(message.toString());
	}

	/**
	 * This method throws an exception.
	 * 
	 * @param message
	 * @throws TestUtilSkipException
	 */
	synchronized static final void throwSkipException(final String message)
			throws TestUtilSkipException {

		throw new TestUtilSkipException(message);
	}
}
