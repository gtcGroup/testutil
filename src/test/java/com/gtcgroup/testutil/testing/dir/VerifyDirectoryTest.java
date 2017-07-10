/*
 * [Licensed per the Open Source "MIT License".]
 *
 * Copyright (c) 1999 - 2017 by
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

package com.gtcgroup.testutil.testing.dir;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;

import com.gtcgroup.justify.core.rulechain.JstRuleChain;
import com.gtcgroup.testutil.TestUtil;

/**
 * <p>
 * Test class.
 * </p>
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 1999 - 2017 by Global Technology Consulting Group, Inc. at
 * <a href="http://gtcGroup.com">gtcGroup.com </a>.
 * </p>
 *
 * @author MarvinToll@gtcGroup.com
 * @since v. 1.0
 */

public class VerifyDirectoryTest {

	@Rule
	public JstRuleChain ruleChain = JstRuleChain.outerRule();

	/**
	 * Verify methods for mutable objects within a directory.
	 *
	 * @throws IOException
	 */

	@Test
	public void testVerifyUsingPackageName() {

		TestUtil.verifyUsingPackageName("com.gtcgroup.testutil");

	}

	// /**
	// * Verify methods for mutable objects within a directory.
	// */
	//
	// public void testDirectory_verbose_false() {
	//
	// try {
	// assertTrue(TestUtil.verifyDirectory("\\C:\\projects\\testutil\\testing",
	// false));
	// fail();
	// } catch (final AssertionFailedError e) {
	// assertTrue(true);
	// }
	// }
	//
	// /**
	// * Verify methods for mutable objects within a directory.
	// */
	//
	// public void testDirectory_verbose_true() {
	//
	// try {
	// TestUtil.verifyDirectory("\\C:\\projects\\testutil\\testing", true);
	// fail();
	// } catch (final AssertionFailedError e) {
	// assertTrue(true);
	// }
	// }
	//
	// /**
	// * Verify methods for mutable objects within a directory.
	// */
	//
	// public void testDirectory_noGlitch() {
	//
	// try {
	// TestUtil.verifyDirectory("\\C:\\projects\\testutil\\testing", true, "",
	// true, 8);
	// fail();
	// } catch (final AssertionFailedError e) {
	// assertTrue(true);
	// }
	// }
	//
	// /**
	// * Verify methods for mutable objects within a directory.
	// */
	//
	// public void testDirectory_testing() {
	//
	// assertTrue(TestUtil.verifyDirectory("\\C:\\projects\\testutil\\testing",
	// true, "", true, 19, 23, 9));
	// }
	//
	// /**
	// * Verify methods for mutable objects within a directory.
	// */
	//
	// public void testDirectory_testing_com_gtcgroup() {
	//
	// assertTrue(
	// TestUtil.verifyDirectory("\\C:\\projects\\testutil\\testing", false,
	// "com.gtcgroup", true, 19, 23, 9));
	// }
	//
	// /**
	// * Verify methods for mutable objects within a directory.
	// */
	//
	// public void testDirectory_noSubDirectories() {
	//
	// assertTrue(TestUtil.verifyDirectory("\\C:\\projects\\testutil\\testing",
	// true, "com.gtcgroup.testutil.bo",
	// false, 19, 19, 8));
	// }
	//
	// /**
	// * Verify methods for mutable objects within a directory.
	// */
	//
	// public void testDirectory_invalidDirectory() {
	//
	// // try {
	// // TestUtil.verifyDirectory("doesNotExist");
	// // fail();
	// // } catch (final TestUtilDirectoryFatal e) {
	// // assertTrue(true);
	// // }
	// }
	//
	// /**
	// * Verify methods for mutable objects within a directory.
	// */
	//
	// public void testDirectory_invalidDirectory_noSubDirectories() {
	//
	// try {
	// TestUtil.verifyDirectory("doesNotExist", true, "", false);
	// fail();
	// } catch (final TestUtilDirectoryFatal e) {
	// assertTrue(true);
	// }
	// }
	//
	// /**
	// * Verify methods for mutable objects within a directory.
	// */
	//
	// public void testDirectory_src_org() {
	//
	// assertTrue(TestUtil.verifyDirectory("src", false, "jdom.", true, 8, 18,
	// 9));
	// }
}