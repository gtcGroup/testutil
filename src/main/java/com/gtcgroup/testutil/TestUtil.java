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

import junit.framework.AssertionFailedError;

/**
 * <p>
 * This class provides the (static) utility methods for verification (unit
 * testing). For additional information please review the annotated <a
 * href="../../../testutil-config.xml">testutil-config.xml </a> descriptor
 * document or the <a href="http://gtcgroup.com/util/">TestUtil Home Page </a>.
 * In the case of a multiple parameter setter, the test object (for
 * verification) is passed to the first parameter.
 * </p>
 * <br/> <b>The following test method demonstrates the simplicity of the
 * TestUtil API:</b> <br/> <br/> <code>
 *   &nbsp;&nbsp;&nbsp;&nbsp;*<br/>
 *   &nbsp;&nbsp;&nbsp;&nbsp;* Verify accessor methods of a mutable business
 *   object.
 *   <br/>
 *   &nbsp;&nbsp;&nbsp;&nbsp;*
 *   <br/>
 *   <br/> &nbsp;&nbsp;&nbsp;&nbsp;public void testPersonBO() {
 *   <br/>
 *   <br/>
 *   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 *   assertTrue(TestUtil.verifyMutable(new PersonBO(), 1, 0));
 *   <br/>
 *   &nbsp;&nbsp;&nbsp;&nbsp;} </code>
 *   <br/>
 *   <br/> <b>Sample console output from execution of the above
 *   <code>verifyMutable()</code> method:</b>
 *   <br/>
 *   <br/> <code>
 *   &nbsp;&nbsp;&nbsp;&nbsp;+ Verification Warning(s) -
 *   com.gtcgroup.testutil.bo.PersonBO +
 *   <br/>
 *   <br/>
 *   &nbsp;&nbsp;&nbsp;&nbsp;Warning [# 1]: Setter = [setInt0()]; Getter = [getInt0()]
 *   <br/>
 *   &nbsp;&nbsp;&nbsp;&nbsp;Setter first parameter [int] and getter return type
 *   [java.lang.String] are not TestUtil verifiable.
 *   <br/>
 *   <br/>
 *   &nbsp;&nbsp;&nbsp;&nbsp;-----&gt; Verification :
 *   com.gtcgroup.testutil.bo.PersonBO
 *   <br/>
 *   &nbsp;&nbsp;&nbsp;&nbsp;-&gt; Anticipated: 25 Successful Verification(s); 1
 *   Warning(s); 0 Glitch(es)
 *   <br/>
 *   &nbsp;&nbsp;&nbsp;&nbsp;-----&gt; Results: 25 Successful Verification(s); 1
 *   Warning(s); 0 Glitch(es)
 *   <br/> </code>
 *   <br/> <b>Verify a complete application with one line of test code:</b>
 *   <br/>
 *   <br/> <code>
 *   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*<br/>
 *   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;* Verify methods for mutable objects within a directory. <br/>
 *	 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*
 *   <br/>
 *   <br/> &nbsp;&nbsp;&nbsp;&nbsp;public void testDirectory() {
 *   <br/>
 *   <br/>
 *   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 *   assertTrue(TestUtil.verifyDirectory("\\C:\\projects\\workspace\\testutil\\testing",
 *   false, "com.gtcgroup", true, 17, 22, 5));
 *   <br/>
 *   &nbsp;&nbsp;&nbsp;&nbsp;} </code>
 *   <br/>
 *   <br/> <b>Sample console output from execution of the above
 *   <code>verifyDirectory()</code> method:</b>
 *   <br/>
 *   <br/> <code>
 *   &nbsp;&nbsp;&nbsp;&nbsp;****************************************************************************************************
 *   <br/>
 *   &nbsp;&nbsp;&nbsp;&nbsp;*
 *   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;TestUtil
 *   v. x.x - Verify Directory Summary Results
 *   <br/>
 *   &nbsp;&nbsp;&nbsp;&nbsp;* -------&gt; Skips: 11 Non-Java files, interfaces, or
 *   abstract classes not attempted.
 *   <br/>
 *   &nbsp;&nbsp;&nbsp;&nbsp;* -&gt; Anticipated: 124 Successful Verification(s); 17
 *   Warning(s); 22 Glitch(es); 5 Caution(s)
 *   <br/>
 *   &nbsp;&nbsp;&nbsp;&nbsp;* -----&gt; Results: 124 Successful Verification(s); 17
 *   Warning(s); 22 Glitch(es); 5 Caution(s)
 *   <br/>
 *   &nbsp;&nbsp;&nbsp;&nbsp;****************************************************************************************************
 *   <br/> </code>
 *   <br/> TestUtil builds upon the <b>Runs</b>, <b>Errors</b> and <b>Failures</b> endemic
 *   to JUnit. Feedback is augmented with <b>Successful Verifications</b>,
 *   <b>Warnings</b>, <b>Glitches</b>, <b>Cautions</b> and <b>Skips</b>. These
 *   additional states are further defined as follows:
 *   <ul>
 *       <li>Successful Verification - When an object returned (with a getter) was the "same"
 *           as the test instance set - or, the objects were (successfully) asserted to be
 *           "equal". </li>
 *       <li>Warning - When a verification was <b><i>not</i></b> attempted for an accessor
 *           pair. A "warning" warrants manual analysis to determine the source of the
 *           anomaly. </li>
 *       <li>Glitch - When a verification was attempted but either reflective invocation or
 *           the assertions failed. If a programming error is not the culprit - in some cases
 *           verification may be enabled with an optional <a href="testutil-config.xml">
 *           testutil-config.xml </a>descriptor document. </li>
 *       <li>Caution [<code>verifyDirectory()</code> only] - When a Java class could
 *           <b><i>not</i></b> be reflectively instantiated by TestUtil - so none of the
 *           accessor methods were verified. In some cases, special handling with an
 *           optional <a href="testutil-config.xml">testutil-config.xml </a>
 *           descriptor document may enable verification to occur. </li>
 *       <li>Skip [<code>verifyDirectory()</code> only] - When a file is an Interface or
 *           Abstract class, or contains no Java at all (e.g. .xml etc). Skips are
 *           informational only and no action is required. </li>
 *   </ul>
 *   Beginning with version 2.0, <b>classes can be excluded from verification with the
 *   (simple) addition of a Javadoc tag</b>. Several variants are supported:
 *   <ul>
 *       <li>@excludeClass </li>
 *       <li>@excludeClassFromTestUtilVerification </li>
 *       <li>@excludeClass caution </li>
 *       <li>@excludeClass skip </li>
 *       <li>@excludeClassFromTestUtilVerification caution </li>
 *       <li>@excludeClassFromTestUtilVerification skip </li>
 *   </ul>
 *   For example:
 *   <br/>
 *   <br/>
 *   <code>
 *   &nbsp;&nbsp;&nbsp;&nbsp; * Javadoc comments here.
 *   <br/>
 *   &nbsp;&nbsp;&nbsp;&nbsp; * @author MarvinToll@gtcGroup.com 
 *   <br/>
 *   &nbsp;&nbsp;&nbsp;&nbsp; * @since v. 2.0
 *   <br/>
 *   &nbsp;&nbsp;&nbsp;&nbsp; * @excludeClass
 *   <br/>
 *   <br/>
 *   &nbsp;&nbsp;&nbsp;&nbsp; public class ExcludeClassUsingJavadocTagBO extends BaseTestBO {
 *   <br/>
 *   <br/>
 *   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; // code here ...
 *   <br/>
 *   &nbsp;&nbsp;&nbsp;&nbsp; }
 *   <br/> </code>
 *   <br/>
 *   
 *   <p> Beginning with version 2.0, <b>method pairs can be excluded from verification with
 *       the (simple) addition of a Javadoc tag on the setter</b>. Both
 *       <code>@excludeSetter</code> and the more descriptive
 *       <code>@excludeSetterFromTestUtilInvocation</code> are supported.
 *       <br/>
 *       <br/> <code>
 *       &nbsp;&nbsp;&nbsp;&nbsp; * Javadoc comments here.
 *       <br/>
 *       &nbsp;&nbsp;&nbsp;&nbsp; * @param strTest
 *       <br/>
 *       &nbsp;&nbsp;&nbsp;&nbsp; *
 *       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The strTest to set.
 *       <br/>
 *       &nbsp;&nbsp;&nbsp;&nbsp; * @excludeSetter
 *       <br/>
 *       <br/>
 *       &nbsp;&nbsp;&nbsp;&nbsp; public void setStrTest(String strTest) {
 *       <br/>
 *       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; this.strTest = strTest;
 *       <br/>
 *       &nbsp;&nbsp;&nbsp;&nbsp; }
 *       <br/>
 *       <br/>
 *       &nbsp;&nbsp;&nbsp;&nbsp; * Javadoc comments here.
 *       <br/>
 *       &nbsp;&nbsp;&nbsp;&nbsp; * @param strTest
 *       <br/>
 *       &nbsp;&nbsp;&nbsp;&nbsp; *
 *       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The strTest to set.
 *       <br/>
 *       &nbsp;&nbsp;&nbsp;&nbsp; * @excludeSetterFromTestUtilVerification
 *       <br/>
 *       <br/>
 *       &nbsp;&nbsp;&nbsp;&nbsp; public void setStrTest(String strTest) {
 *       <br/>
 *       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; this.strTest = strTest;
 *       <br/>
 *       &nbsp;&nbsp;&nbsp;&nbsp; } </code>
 *       <br/> </p>
 * 
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 1999 - 2008 by Global Technology Consulting Group, Inc. at <a
 * href="http://gtcGroup.com">gtcGroup.com </a>.
 * </p>
 * 
 * @author MarvinToll@gtcGroup.com 
 * @since v. 1.0
 */

public class TestUtil {

	/**
	 * Constructor (private)
	 */
	private TestUtil() {
		super();
	}

	/**
	 * This method attempts to invoke all matching accessor methods on a mutable
	 * object. Javadoc exclusion tags are not processed.
	 * 
	 * @param mutableObject
	 *            Mutable object whose methods are subject to verification.
	 * @return <code>boolean</code> - Indicates if verification resulted in
	 *         the anticipated number of "warnings" and "glitches".
	 * @throws AssertionFailedError
	 */
	public static boolean verifyMutable(Object mutableObject)
			throws AssertionFailedError {

		return verifyMutable(mutableObject, null, 0);
	}

	/**
	 * This method attempts to invoke all matching accessor methods on a mutable
	 * object.
	 * 
	 * @param mutableObject
	 *            Mutable object whose methods are subject to verification.
	 * @param sourceCodePathname
	 *            A pathname string or null for no Javadoc tag processing.
	 * @return <code>boolean</code> - Indicates if verification resulted in
	 *         the anticipated number of "warnings" and "glitches".
	 * @throws AssertionFailedError
	 */
	public static boolean verifyMutable(Object mutableObject,
			final String sourceCodePathname) throws AssertionFailedError {

		return verifyMutable(mutableObject, sourceCodePathname, 0);
	}

	/**
	 * This overloaded version enables specifying the number of "warnings"
	 * allowed in a successful unit test.
	 * 
	 * @param mutableObject
	 *            Mutable object whose methods are subject to verification.
	 * @param sourceCodePathname
	 *            A pathname string or null for no Javadoc tag processing.
	 * @param anticipatedWarnings
	 *            Number of tests not attempted.
	 * @return <code>boolean</code> - Indicates if verification resulted in
	 *         the anticipated number of "warnings" and "glitches".
	 * @throws AssertionFailedError
	 */
	public static boolean verifyMutable(Object mutableObject,
			final String sourceCodePathname, int anticipatedWarnings)
			throws AssertionFailedError {

		return verifyMutable(mutableObject, sourceCodePathname,
				anticipatedWarnings, 0);

	}

	/**
	 * This overloaded version enables specifying the number of "glitches"
	 * allowed in a successful unit test.
	 * <p>
	 * A best practise test strategy is to eliminate glitches with a custom <a
	 * href="../../../testutil-config.xml">testutil-config.xml </a>descriptor
	 * document.
	 * </p>
	 * 
	 * @param mutableObject
	 *            Mutable object whose methods are subject to verification.
	 * @param sourceCodePathname
	 *            A pathname string or null for no Javadoc tag processing.
	 * @param anticipatedWarnings
	 *            Number of tests not attempted.
	 * @param anticipatedGlitches
	 *            Number of tests in which execution or assertion failed.
	 * @return <code>boolean</code> - Indicates if verification resulted in
	 *         the anticipated number of "warnings" and "glitches".
	 * @throws AssertionFailedError
	 */
	public static boolean verifyMutable(Object mutableObject,
			final String sourceCodePathname, int anticipatedWarnings,
			int anticipatedGlitches) throws AssertionFailedError {

		return verifyMutable(mutableObject, sourceCodePathname,
				anticipatedWarnings, anticipatedGlitches, true, true)
				.getVerificationResult();
	}

	/**
	 * This private version includes a switch specifying a single mutable object
	 * verification.
	 * 
	 * @param mutableObject
	 *            Mutable object whose methods are subject to verification.
	 * @param sourceCodePathname
	 *            A pathname string or null for no Javadoc tag processing.
	 * @param anticipatedWarnings
	 *            Number of tests not attempted.
	 * @param anticipatedGlitches
	 *            Number of tests in which execution or assertion failed.
	 * @param isSingleObjectVerification
	 *            Switch specifying single object vs. multiple object
	 *            verification.
	 * @param verbose
	 *            Indicates console feedback is verbose.
	 * @return <code>TuCountersBO</code> - Indicates if verification resulted
	 *         in the anticipated number of "warnings" and "glitches".
	 * @throws AssertionFailedError
	 */
	static TuCountersBO verifyMutable(Object mutableObject,
			final String sourceCodePathname, int anticipatedWarnings,
			int anticipatedGlitches, boolean isSingleObjectVerification,
			boolean verbose) throws AssertionFailedError {

		// Fire up the singleton or check for stale cache.
		TuCacher.getInstance().checkForStaleCache();
		
		// Instantiate the application service.
		TuVerifyMutableASO tuVerifyMutableASO = new TuVerifyMutableASO(
				mutableObject, sourceCodePathname);

		// Prepare test objects for verification.
		tuVerifyMutableASO.prepareTestInstances();

		// Invoke getters and setters.
		tuVerifyMutableASO.invokeSettersAndGetters();

		// Get a handle on the counter BOs.
		TuCountersBO tuCountersBO = tuVerifyMutableASO.getTuCountersBO();

		// Prepare results.
		StringBuffer message = new StringBuffer();

		message.append("\n\t");

		// Display class name verified.
		if (verbose
				|| (!verbose && 0 < tuCountersBO.getTotalWarningsAndGlitches())) {

			message.append("-----> Verification : ");
			message.append(mutableObject.getClass().getName());
			message.append("\n\t");
		}

		// Determine if single object messages warranted.
		if (isSingleObjectVerification) {
			message.append("-> Anticipated: ");
			if (0 > tuCountersBO.getTotalVerifications()
					- (anticipatedWarnings + anticipatedGlitches)) {
				// Display zero.
				message.append(0);
			} else {
				message.append(tuCountersBO.getTotalVerifications()
						- (anticipatedWarnings + anticipatedGlitches));
			}
			message.append(" Successful Verification(s); ");
			message.append(anticipatedWarnings);
			message.append(" Warning(s); ");
			message.append(anticipatedGlitches);
			message.append(" Glitch(es)\n\t");
		}

		if (verbose || 0 < tuCountersBO.getTotalWarningsAndGlitches()) {
			message.append("-----> Results: ");
			message.append(tuCountersBO.getSuccessfulVerifications());
			message.append(" Successful Verification(s); ");
			message.append(tuCountersBO.getTotalWarnings());
			message.append(" Warning(s); ");
			message.append(tuCountersBO.getTotalGlitches());
			message.append(" Glitch(es)");
		}

		if (2 < message.length()) {
			// Display results to console.
			System.out.println(message.toString());
		}

		// Initialize results.
		tuCountersBO.initializeVerificationResults(anticipatedWarnings,
				anticipatedGlitches);

		// Check for expected return values.
		if (isSingleObjectVerification && !tuCountersBO.getVerificationResult()) {

			// Display results to JUnit GUI.
			throw new AssertionFailedError(message.toString());
		}

		return tuCountersBO;
	}

	/**
	 * This method invokes <code>verifyMutable()</code> for all classes in a
	 * directory.
	 * 
	 * @param sourceCodePathname
	 *            A pathname string.
	 * @return <code>boolean</code> - Indicating if verification resulted in
	 *         the anticipated number of "warnings", "glitches" and "cautions".
	 * @throws AssertionFailedError
	 */
	public static boolean verifyDirectory(String sourceCodePathname)
			throws AssertionFailedError {

		// Process.
		return verifyDirectory(sourceCodePathname, true);
	}

	/**
	 * This overloaded version specifies whether console feedback is verbose.
	 * 
	 * @param sourceCodePathname
	 *            A pathname string.
	 * @param verbose
	 *            Indicates console feedback is verbose.
	 * @return <code>boolean</code> - Indicating if verification resulted in
	 *         the anticipated number of "warnings", "glitches" and "cautions".
	 * @throws AssertionFailedError
	 */
	public static boolean verifyDirectory(String sourceCodePathname,
			boolean verbose) throws AssertionFailedError {

		// Process.
		return verifyDirectory(sourceCodePathname, verbose, "");
	}

	/**
	 * This overloaded version enables using a qualified directory path and
	 * assigning a class name prefix string to establish fully qualified class
	 * names.
	 * 
	 * @param sourceCodePathname
	 *            A pathname string.
	 * @param verbose
	 *            Indicates console feedback is verbose.
	 * @param classPackageUsingDotNotation
	 *            Prefix string used to establish the fully qualified name of
	 *            the desired class(es).
	 * @return <code>boolean</code> - Indicating if verification resulted in
	 *         the anticipated number of "warnings", "glitches" and "cautions".
	 * @throws AssertionFailedError
	 */
	public static boolean verifyDirectory(String sourceCodePathname,
			boolean verbose, String classPackageUsingDotNotation)
			throws AssertionFailedError {

		// Process.
		return verifyDirectory(sourceCodePathname, verbose,
				classPackageUsingDotNotation, true);
	}

	/**
	 * This overloaded version specifies whether sub-directories are processed.
	 * 
	 * @param sourceCodePathname
	 *            A pathname string.
	 * @param verbose
	 *            Indicates console feedback is verbose.
	 * @param classPackageUsingDotNotation
	 *            Prefix string used to establish the fully qualified name of
	 *            the desired class(es).
	 * @param processSubDirectories
	 *            Indicates if sub-directories are processed.
	 * @return <code>boolean</code> - Indicating if verification resulted in
	 *         the anticipated number of "warnings", "glitches" and "cautions".
	 * @throws AssertionFailedError
	 */
	public static boolean verifyDirectory(String sourceCodePathname,
			boolean verbose, String classPackageUsingDotNotation,
			boolean processSubDirectories) throws AssertionFailedError {

		// Process.
		return verifyDirectory(sourceCodePathname, verbose,
				classPackageUsingDotNotation, processSubDirectories, 0);
	}

	/**
	 * This overloaded version specifies the number of "warnings" allowed in a
	 * successful unit test.
	 * 
	 * @param sourceCodePathname
	 *            A pathname string.
	 * @param verbose
	 *            Indicates console feedback is verbose.
	 * @param classPackageUsingDotNotation
	 *            Prefix string used to establish the fully qualified name of
	 *            the desired class(es).
	 * @param processSubDirectories
	 *            Indicates if sub-directories are processed.
	 * @param anticipatedWarnings
	 *            Anticipated number of verifications not attempted.
	 * @return <code>boolean</code> - Indicating if verification resulted in
	 *         the anticipated number of "warnings", "glitches" and "cautions".
	 * @throws AssertionFailedError
	 */
	public static boolean verifyDirectory(String sourceCodePathname,
			boolean verbose, String classPackageUsingDotNotation,
			boolean processSubDirectories, int anticipatedWarnings)
			throws AssertionFailedError {

		// Process.
		return verifyDirectory(sourceCodePathname, verbose,
				classPackageUsingDotNotation, processSubDirectories,
				anticipatedWarnings, 0);
	}

	/**
	 * This overloaded version specifies the number of "glitches" allowed in a
	 * successful unit test.
	 * 
	 * @param sourceCodePathname
	 *            A pathname string.
	 * @param verbose
	 *            Indicates console feedback is verbose.
	 * @param classPackageUsingDotNotation
	 *            Prefix string used to establish the fully qualified name of
	 *            the desired class(es).
	 * @param processSubDirectories
	 *            Indicates if sub-directories are processed.
	 * @param anticipatedWarnings
	 *            Anticipated number of verifications not attempted.
	 * @param anticipatedGlitches
	 *            Anticipated number of verifications for which execution or
	 *            assertion failed.
	 * @return <code>boolean</code> - Indicating if verification resulted in
	 *         the anticipated number of "warnings", "glitches" and "cautions".
	 * @throws AssertionFailedError
	 */
	public static boolean verifyDirectory(String sourceCodePathname,
			boolean verbose, String classPackageUsingDotNotation,
			boolean processSubDirectories, int anticipatedWarnings,
			int anticipatedGlitches) throws AssertionFailedError {

		// Process.
		return verifyDirectory(sourceCodePathname, verbose,
				classPackageUsingDotNotation, processSubDirectories,
				anticipatedWarnings, anticipatedGlitches, 0);
	}

	/**
	 * This overloaded version specifies the number of "cautions" allowed in a
	 * successful unit test.
	 * 
	 * @param sourceCodePathname
	 *            A pathname string.
	 * @param verbose
	 *            Indicates console feedback is verbose.
	 * @param classPackageUsingDotNotation
	 *            Prefix string used to establish the fully qualified name of
	 *            the desired class(es).
	 * @param processSubDirectories
	 *            Indicates if sub-directories are processed.
	 * @param anticipatedWarnings
	 *            Anticipated number of verifications not attempted.
	 * @param anticipatedGlitches
	 *            Anticipated number of verifications for which execution or
	 *            assertion failed.
	 * @param anticipatedCautions
	 *            Anticipated number of verification cautions.
	 * @return <code>boolean</code> - Indicating if verification resulted in
	 *         the anticipated number of "warnings", "glitches" and "cautions".
	 * @throws AssertionFailedError
	 */
	public static boolean verifyDirectory(String sourceCodePathname,
			boolean verbose, String classPackageUsingDotNotation,
			boolean processSubDirectories, int anticipatedWarnings,
			int anticipatedGlitches, int anticipatedCautions)
			throws AssertionFailedError {

		// Fire up the singleton or check for stale cache.
		TuCacher.getInstance().checkForStaleCache();

		// Display begin to console.
		System.out.println("<Begin directory verification of path "
				+ sourceCodePathname + ">");

		// Instantiate an application service object.
		TuVerifyDirectoryASO tuVerifyDirectoryASO = new TuVerifyDirectoryASO(
				sourceCodePathname, verbose, classPackageUsingDotNotation,
				processSubDirectories);

		// Retrieve the results.
		TuCountersListBO tuCountersListBO = tuVerifyDirectoryASO
				.verifyDirectory();

		// Prepare results.
		StringBuffer message = new StringBuffer();

		// Line 1
		message.append("\n\t");
		message
				.append("****************************************************************************************************\n\t");

		// Line 2
		message.append("*                 TestUtil v. ");
		message.append(TuConstants.TEST_UTIL_VERSION);
		message.append(" - Verify Directory Summary Results\n\t");

		// Line 3
		message.append("* -------> Skips: ");
		message.append(tuCountersListBO.getTotalSkips());
		message
				.append(" Non-Java files, interfaces, or abstract classes not attempted.");
		message.append("\n\t");

		// Line 4
		message.append("* -> Anticipated: ");
		if (0 > tuCountersListBO.getTotalVerifications()
				- (anticipatedWarnings + anticipatedGlitches)) {
			// Display zero.
			message.append(0);
		} else {
			message.append(tuCountersListBO.getTotalVerifications()
					- (anticipatedWarnings + anticipatedGlitches));
		}
		message.append(" Successful Verification(s); ");
		message.append(anticipatedWarnings);
		message.append(" Warning(s); ");
		message.append(anticipatedGlitches);
		message.append(" Glitch(es); ");
		message.append(anticipatedCautions);
		message.append(" Caution(s)\n\t");

		// Line 5
		message.append("* -----> Results: ");
		message.append(tuCountersListBO.getSuccessfulVerifications());
		message.append(" Successful Verification(s); ");
		message.append(tuCountersListBO.getTotalWarnings());
		message.append(" Warning(s); ");
		message.append(tuCountersListBO.getTotalGlitches());
		message.append(" Glitch(es); ");
		message.append(tuCountersListBO.getTotalCautions());
		message.append(" Caution(s)");
		message.append("\n\t");

		// Line 6
		message
				.append("****************************************************************************************************\n\n");

		// Display results to console.
		System.out.println(message.toString()
				+ "<End directory verification of path " + sourceCodePathname
				+ ">");

		// Initialize results flag.
		tuCountersListBO.initializeVerificationResults(anticipatedWarnings,
				anticipatedGlitches, anticipatedCautions);

		// Check for expected return values.
		if (!tuCountersListBO.getVerificationResult()) {

			// Display results to JUnit GUI.
			throw new AssertionFailedError(message.toString());
		}
		return true;
	}
}