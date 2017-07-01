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

/**
 * <p>
 * This mutable Business Object manages counters.
 * </p>
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 1999 - 2008 by Global Technology Consulting Group, Inc. at <a
 * href="http://gtcGroup.com">gtcGroup.com </a>.
 * </p>
 * 
 * @author MarvinToll@gtcGroup.com 
 * @since v. 1.0
 */
class TuCountersBO extends TuBaseBO {

	/**
	 * Attribute maintaining the warning count for a verified object.
	 */
	private int totalVerifications = 0;

	/**
	 * Attribute maintaining the warning count for a verified object.
	 */
	private int totalWarnings = 0;

	/**
	 * Attribute maintaining the glitch count for a verified object.
	 */
	private int totalGlitches = 0;

	/**
	 * Attribute maintaining the anticipated vs actual verification result.
	 */
	private boolean verificationResult;

	/**
	 * Constructor
	 */
	TuCountersBO() {
		super();
	}

	/**
	 * @return boolean - The verification result.
	 */
	boolean getVerificationResult() {
		return this.verificationResult;
	}

	/**
	 * Establishes the state of anticipated vs actual verification results.
	 * 
	 * @param anticipatedWarnings
	 * @param anticipatedGlitches
	 */
	void initializeVerificationResults(int anticipatedWarnings,
			int anticipatedGlitches) {

		this.verificationResult = this.getTotalWarnings() == anticipatedWarnings
				&& this.getTotalGlitches() == anticipatedGlitches;
	}

	/**
	 * Returns the total number of successful verifications.
	 * 
	 * @return int - The total number of successful verifications.
	 */
	int getSuccessfulVerifications() {

		return this.totalVerifications
				- (this.totalWarnings + this.totalGlitches);
	}

	/**
	 * Returns the total number of verifications performed.
	 * 
	 * @return int - The total number of verifications performed.
	 */
	int getTotalVerifications() {
		return this.totalVerifications;
	}

	/**
	 * Increments the verification counter.
	 */
	void addVerification() {
		this.totalVerifications++;
	}

	/**
	 * Returns the total number of glitches.
	 * 
	 * @return int - Returns the total number of glitches.
	 */
	int getTotalGlitches() {
		return this.totalGlitches;
	}

	/**
	 * Increments the glitch counter and verification counter.
	 */
	void addGlitch() {
		this.totalVerifications++;
		this.totalGlitches++;
	}

	/**
	 * Returns the total number of warnings.
	 * 
	 * @return int - The total number of warnings.
	 */
	int getTotalWarnings() {
		return this.totalWarnings;
	}

	/**
	 * Increments the warning counter and verification counter..
	 */
	void addWarning() {
		this.totalVerifications++;
		this.totalWarnings++;
	}

	/**
	 * Returns the total number of warnings and glitches.
	 * 
	 * @return int - The total number of warnings and glitches.
	 */
	int getTotalWarningsAndGlitches() {
		return this.getTotalWarnings() + this.getTotalGlitches();
	}
}