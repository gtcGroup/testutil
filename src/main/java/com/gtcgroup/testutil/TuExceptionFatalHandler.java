package com.gtcgroup.testutil;

import com.gtcgroup.testutil.exceptions.TestUtilDescriptorDocumentFatal;
import com.gtcgroup.testutil.exceptions.TestUtilDirectoryFatal;
import com.gtcgroup.testutil.exceptions.TestUtilFatalException;

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
final class TuExceptionFatalHandler {

	/**
	 * Constructor (private)
	 */
	private TuExceptionFatalHandler() {
		super();
	}

	/**
	 * This method displays fatals.
	 * 
	 * @param sbSetterName
	 * @param sbGetterName
	 * @param tuMethodPairBO
	 * @param fatal
	 */
	synchronized static final void processFatal(
			final StringBuffer sbSetterName, final StringBuffer sbGetterName,
			final TuMethodPairBO tuMethodPairBO,
			final TestUtilFatalException fatal) {

		// Process warning.
		StringBuffer stringBuffer = new StringBuffer();

		if (null != sbGetterName && null != sbSetterName) {
			stringBuffer.append("\n\tSetter = [");
			stringBuffer.append(sbSetterName.toString());
			stringBuffer.append("()]; Getter = [");
			stringBuffer.append(sbGetterName.toString());

			if (null != tuMethodPairBO) {
				stringBuffer.append(" "
						+ tuMethodPairBO.getTuMethodGetBO().getMethod()
								.getName());
			}
			stringBuffer.append("()]");
		}
		stringBuffer.append("\n\t");
		stringBuffer.append(fatal.getMessage());

		System.out.println(stringBuffer.toString());
	}

	/**
	 * This method throws an exception.
	 * 
	 * @param strSourceClass
	 *            The class detecting the error.
	 * @param strSourceMethod
	 *            The method detecting the error.
	 * @param strMessage
	 *            The validation error message.
	 * @param exception
	 *            The originating exception or null.
	 * @throws TestUtilDescriptorDocumentFatal
	 */
	synchronized static final void throwDescriptorDocumentFatalException(
			final String strSourceClass, final String strSourceMethod,
			final String strMessage, final Exception exception)
			throws TestUtilDescriptorDocumentFatal {

		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// Do nothing.
		}

		// Halt processing.
		StringBuffer message = new StringBuffer();

		message.append("\n\t+++ TestUtil Fatal Error +++");
		message.append("\n\tA TestUtil exception occurred [");
		message.append(strSourceClass);
		message.append(".");
		message.append(strSourceMethod);
		message.append("()].\n\t");
		message.append(strMessage);
		message.append(".");

		// Display.
		System.out.println(message.toString());

		if (null != exception) {

			// Display the stack trace.
			exception.printStackTrace();
		}

		// Display.
		System.out.println("\n\n");

		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// Do nothing.
		}

		throw new TestUtilDescriptorDocumentFatal();
	}

	/**
	 * This method throws an exception.
	 * 
	 * @param pathName
	 *            The representation of a pathname.
	 * @throws TestUtilDirectoryFatal
	 */
	synchronized static final void throwDirectoryFatalException(
			final String pathName) throws TestUtilDirectoryFatal {

		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// Do nothing.
		}

		// Halt processing.
		StringBuffer message = new StringBuffer();

		message.append("The pathname [");
		message.append(pathName);
		message.append("] can not be processed.");

		// Display.
		System.out.println(message.toString());

		// Instantiate exception.
		TestUtilDirectoryFatal testUtilDirectoryFatal = new TestUtilDirectoryFatal(
				message.toString());

		// Display the stack trace.
		testUtilDirectoryFatal.printStackTrace();

		// Display.
		System.out.println("\n\n");

		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// Do nothing.
		}

		throw testUtilDirectoryFatal;
	}
}
