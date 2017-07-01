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
import java.lang.reflect.Modifier;

import com.gtcgroup.testutil.exceptions.TestUtilCautionException;
import com.gtcgroup.testutil.exceptions.TestUtilExcludeClassException;
import com.gtcgroup.testutil.exceptions.TestUtilRuntimeException;
import com.gtcgroup.testutil.exceptions.TestUtilSkipException;

/**
 * <p>
 * This (selectively immutable) Application Service Object (ASO) handles
 * verification for all methods associated with all classes in a directory - and
 * its subdirectories if warranted.
 * </p>
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 1999 - 2008 by Global Technology Consulting Group, Inc. at <a
 * href="http://gtcGroup.com">gtcGroup.com </a>.
 * </p>
 * 
 * @author MarvinToll@gtcGroup.com
 * @since v. 1.1
 */
class TuVerifyDirectoryASO {

	/** A source code pathname string. */
	private final String sourceCodePathname;

	/** A fullpathname string. */
	private final String fullyQualifiedPathname;

	/**
	 * Indicates whether console feedback is verbose.
	 */
	private final boolean verbose;

	/** Indicates if sub-directories are processed. */
	private final boolean subDirectoriesProcessed;

	/**
	 * Prefix string used to establish the fully qualified name of the desired
	 * class(es).
	 */
	private final String classPackageUsingDotNotation;

	/** Mutable Business Object managing a list of <code>TuCountersBO</code>s. */
	private final TuCountersListBO tuCountersListBO = new TuCountersListBO();

	/** System-specific file separator */
	// Added 8/2008: MDT
	private static final String SYSTEM_FILE_SEPARATOR = System
			.getProperty("file.separator");

	/**
	 * Constructor
	 * 
	 * @param sourceCodePathname
	 *            A pathname string.
	 * @param verbose
	 *            Indicates console feedback is verbose.
	 * @param classPackageUsingDotNotation
	 *            Prefix string used to establish the fully qualified name of
	 *            the desired class(es).
	 * @param subDirectoriesProcessed
	 *            Indicates if sub-directories are processed.
	 */
	TuVerifyDirectoryASO(final String sourceCodePathname,
			final boolean verbose, final String classPackageUsingDotNotation,
			final boolean subDirectoriesProcessed) {

		super();

		this.sourceCodePathname = sourceCodePathname;

		// Ensure path name syntax.
		if (classPackageUsingDotNotation.length() > 0) {
			this.fullyQualifiedPathname = TuUtil.replace(sourceCodePathname
					+ SYSTEM_FILE_SEPARATOR + classPackageUsingDotNotation,
					".", SYSTEM_FILE_SEPARATOR);
		} else {
			this.fullyQualifiedPathname = sourceCodePathname;
		}
		this.verbose = verbose;

		// Ensure trailing dot.
		if (classPackageUsingDotNotation.endsWith(".")
				|| (classPackageUsingDotNotation.length() < 1)) {
			this.classPackageUsingDotNotation = classPackageUsingDotNotation;
		} else {
			this.classPackageUsingDotNotation = classPackageUsingDotNotation
					+ ".";
		}

		this.subDirectoriesProcessed = subDirectoriesProcessed;

	}

	/**
	 * This method invokes <code>verifyMutable()</code> for all classes in a
	 * directory.
	 * 
	 * @return TuCountersListBO - Mutable Business Object managing the
	 *         <code>TuCountersBO</code> list.
	 */
	TuCountersListBO verifyDirectory() {

		// Instantiate a directory from the URL.
		final File dir = new File(this.fullyQualifiedPathname);

		// Recursively search for a concrete class to verify.
		this.processDirsAndFiles(dir, new String(""), true);

		return this.tuCountersListBO;
	}

	/**
	 * This method recursively iterates through directories and files looking
	 * for a concrete class to verify.
	 * 
	 * @param dir
	 * @param appendedPathname
	 * @param firstTimeThruMethod
	 */
	private void processDirsAndFiles(final File dir,
			final String appendedPathname, final boolean firstTimeThruMethod) {

		// Determine if the file object references a Java class file.
		if (dir.isFile()) {

			// Seek a concrete class implementing the interface.
			this.processFile(dir, appendedPathname);

			// The file object references a directory.
			// 5/6/06 - Directories beginning with a dot are ignored.
		} else if (dir.isDirectory()
				&& (this.subDirectoriesProcessed || firstTimeThruMethod)
				&& (!".".equalsIgnoreCase(dir.getName().substring(0, 1)))) {

			// Check for subdirectory processing.
			if (firstTimeThruMethod || this.subDirectoriesProcessed) {

				// Retrieve all the files and subdirectories.
				final File[] childrenList = dir.listFiles();

				// Iterate through the list.
				for (final File element : childrenList) {

					// Determine if this is the first time through this
					// recursive method.
					if (firstTimeThruMethod) {

						// Process the first element in the list.
						this.processDirsAndFiles(element, appendedPathname,
								false);

					} else {

						// Recursive processing.
						this.processDirsAndFiles(element, appendedPathname
								+ dir.getName() + ".", false);
					}
				}
			}
		} else if ((dir.isDirectory() && !this.subDirectoriesProcessed)
				|| (".".equalsIgnoreCase(dir.getName().substring(0, 1)))) {

			// Do nothing!
		}

		else {
			// Directory may have been incorrectly specified.
			TuExceptionFatalHandler.throwDirectoryFatalException(dir.getName());
		}
		return;
	}

	/**
	 * This method verifies a class and populates verification results.
	 * 
	 * @param file
	 * @param appendedPathname
	 */
	@SuppressWarnings("null")
	private void processFile(final File file, final String appendedPathname) {

		// Declaration.
		String classFileName = null;

		// Initialization.
		final String sourceFileName = this.classPackageUsingDotNotation
				+ appendedPathname + file.getName();
		Class<?> theClass = null;

		// Handle all verifications.
		try {

			// Determine if the source is a java file.
			if (-1 == sourceFileName.lastIndexOf(".java")) {

				// Not Java my friend.
				TuExceptionSkipHandler.throwSkipException("NOT_JAVA_CLASS");
			}

			try {

				// Get the class file name.
				classFileName = sourceFileName.substring(0, sourceFileName
						.indexOf(".java"));

				// Retrieve the class.
				theClass = Class.forName(classFileName);

			} catch (final Exception e) {

				// This should never happen.
				TuExceptionCautionHandler.throwInstantiationCaution(e);
			} catch (final Error e) {

				// Note: Error - perhaps NoClassDefFoundError?
				TuExceptionCautionHandler
						.throwInstantiationCaution(new RuntimeException(
								"This class excluded because of an error ["
										+ e.getClass().getName() + "]."));
			}

			// Determine if exclusion from descriptor document is warranted.
			String exclusion = TuUtil.excludeClassFromVerification(theClass);

			if (exclusion != null) {

				// Process default exclusion.
				if (exclusion.equals(TuConstants.DOCUMENT_ATTRIBUTE_CAUTION)) {

					// Excluded by caution.
					TuExceptionCautionHandler
							.throwInstantiationCaution(new TestUtilExcludeClassException(
									"This class excluded by declaration ["
											+ TuConstants.DESCRIPTOR_DOCUMENT_NAME
											+ "] or Javadoc tag."));
				} else {

					// Excluded by skip.
					TuExceptionSkipHandler.throwSkipException("DECLARED");
				}
			}

			// Determine if exclusion from Javadoc tag is warranted.
			exclusion = TuUtil.excludeClassUsingJavadocTag(
					this.sourceCodePathname, theClass);

			if (exclusion != null) {

				// Process default exclusion.
				if (exclusion.equals(TuConstants.DOCUMENT_ATTRIBUTE_CAUTION)) {

					// Excluded by caution.
					TuExceptionCautionHandler
							.throwInstantiationCaution(new TestUtilExcludeClassException(
									"This class excluded by Javadoc tag specification."));
				} else {

					// Excluded by skip.
					TuExceptionSkipHandler.throwSkipException("JAVADOC TAG");
				}
			}

			if (theClass.isInterface()) {

				// Interface.
				TuExceptionSkipHandler.throwSkipException("INTERFACE");
			}

			if (Modifier.isAbstract(theClass.getModifiers())) {

				// Interface.
				TuExceptionSkipHandler.throwSkipException("ABSTRACT");
			}

			Object object = null;
			try {
				object = TuUtil.instantiateObjectFromClass(theClass, 0);
			} catch (final TestUtilRuntimeException e) {

				TuExceptionCautionHandler.throwInstantiationCaution(e);
			}

			if (null == object) {

				// Fast return.
				this.tuCountersListBO.getClass();

			}

			try {

				// Populate results.
				this.tuCountersListBO.addTuCounters(TestUtil.verifyMutable(
						object, this.sourceCodePathname, 0, 0, false,
						this.verbose));

			} catch (final Exception e) {

				// Should never happen.
				TuExceptionCautionHandler.throwInstantiationCaution(e);
			}

		} catch (final TestUtilCautionException e) {

			TuExceptionCautionHandler.processCaution(sourceFileName,
					this.tuCountersListBO, e);

		} catch (final TestUtilSkipException e) {

			// Increment the caution.
			this.tuCountersListBO.incrementTotalSkips();

			if (this.verbose) {
				TuExceptionSkipHandler.processSkip(sourceFileName,
						this.tuCountersListBO, e);
			}
		}

		return;
	}
}