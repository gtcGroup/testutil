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

package com.gtcgroup.testutil.testing;

import com.gtcgroup.testutil.TestUtil;
import com.gtcgroup.testutil.to.dependency.SubstituteClassConstructorFatalBO;
import com.gtcgroup.testutil.to.dependency.SubstituteClassEndlessLoopBO;
import com.gtcgroup.testutil.to.dependency.SubstituteClassInvalidBO;
import com.gtcgroup.testutil.to.dependency.SubstituteClassNoSubstituteBO;
import com.gtcgroup.testutil.to.dependency.SubstituteClassValidBO;

/**
 * <p>
 * Test class.
 * </p>
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 1999 - 2017 by Global Technology Consulting Group, Inc. at <a
 * href="http://gtcGroup.com">gtcGroup.com </a>.
 * </p>
 * 
 * @author MarvinToll@gtcGroup.com 
 * @since v. 1.0
 */

public class SubstituteClassTest extends BaseTestUtilCase {

	/**
	 * Verify accessor methods of a mutable business object.
	 */

	public void testSubstituteClassValidBO() {

		assertTrue(TestUtil.verifyMutable(new SubstituteClassValidBO(), null, 0,
				0));
	}

	/**
	 * Verify accessor methods of a mutable business object.
	 */

	public void testSubstituteClassInvalidBO() {

		TestUtil.verifyMutable(new SubstituteClassInvalidBO(), null, 1, 0);
	}

	/**
	 * Verify accessor methods of a mutable business object.
	 */

	public void testSubstituteClassNoSubstitutionBO() {

		assertTrue(TestUtil.verifyMutable(new SubstituteClassNoSubstituteBO(),
				null, 1, 0));

	}

	/**
	 * Verify accessor methods of a mutable business object.
	 */

	public void testSubstituteClassConstructorBO() {

		TestUtil.verifyMutable(new SubstituteClassConstructorFatalBO(), null, 1,
				0);
	}

	/**
	 * Verify accessor methods of a mutable business object.
	 */

	public void testSubstituteClassEndlessLoopBO() {

		assertTrue(TestUtil.verifyMutable(new SubstituteClassEndlessLoopBO(),
				null, 1, 0));
	}
}