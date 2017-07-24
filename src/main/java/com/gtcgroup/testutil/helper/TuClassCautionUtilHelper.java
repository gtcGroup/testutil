package com.gtcgroup.testutil.helper;

import com.gtcgroup.testutil.exception.TestUtilCautionException;
import com.gtcgroup.testutil.exception.TestUtilRuntimeException;

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
public enum TuClassCautionUtilHelper {

	INSTANCE;

	public synchronized static final void throwCaution(final Exception e) {

		throw new TestUtilCautionException(e.toString());
	}

	public synchronized static final void throwClassInstantiationCaution(final String message) {

		throw new TestUtilCautionException(message);
	}

	public synchronized final void processCaution(final String fileName) {

		// Process caution.
		final StringBuffer message = new StringBuffer();

		message.append("\n* Caution [# ");
		message.append(TestUtilRuntimeException.getTotalClassCautions());
		message.append("]: Instantiation [");
		message.append(fileName);
		message.append("] failure.\n");
		message.append("* ");

		System.out.println(message.toString());
	}
}
