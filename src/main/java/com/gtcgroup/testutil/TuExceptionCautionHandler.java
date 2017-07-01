package com.gtcgroup.testutil;

import com.gtcgroup.testutil.exceptions.TestUtilCautionException;
import com.gtcgroup.testutil.exceptions.TestUtilInstantiationCaution;

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
final class TuExceptionCautionHandler {

	/**
	 * Constructor (private)
	 */
	private TuExceptionCautionHandler() {
		super();
	}

	/**
	 * This method displays a caution.
	 * 
	 * @param fileName
	 * @param tuCountersListBO
	 * @param caution
	 */
	synchronized static final void processCaution(final String fileName,
			final TuCountersListBO tuCountersListBO,
			final TestUtilCautionException caution) {

		// Increment the caution.
		tuCountersListBO.incrementTotalCautions();

		// Process caution.
		StringBuffer message = new StringBuffer();

		message.append("\n* Caution [# ");
		message.append(tuCountersListBO.getTotalCautions());
		message.append("]: Instantiation [");
		message.append(fileName);
		message.append("] failure.\n");
		message.append("* ");

		if (caution.getRootCause() instanceof ClassNotFoundException) {

			message.append("Verify descriptor document for [");
			message.append(caution.getRootCause().getMessage());
			message.append("], the substitute class not found.");

		} else {
			message.append(caution.getRootCause().getMessage());
		}
		System.out.println(message.toString());
	}

	/**
	 * This method throws an exception.
	 * 
	 * @param exception
	 * @throws TestUtilInstantiationCaution
	 */
	synchronized static final void throwInstantiationCaution(
			final Exception exception) throws TestUtilInstantiationCaution {

		throw new TestUtilInstantiationCaution(exception);
	}
}
