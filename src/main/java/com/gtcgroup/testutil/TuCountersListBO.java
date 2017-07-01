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

import java.util.ArrayList;

/**
 * <p>
 * This mutable Business Object manages a list of <code>TuCountersBO</code>
 * objects. Methods providing status details are invoked when more than one
 * immutable object is verified.
 * </p>
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 1999 - 2008 by Global Technology Consulting Group, Inc. at <a
 * href="http://gtcGroup.com">gtcGroup.com </a>.
 * </p>
 * 
 * @author MarvinToll@gtcGroup.com
 * @since v. 1.0
 */
class TuCountersListBO extends TuBaseBO {

	/**
	 * Attribute containing a list of <code>TuCountersBO</code>s.
	 */
	private final ArrayList<TuCountersBO> tuCountersList = new ArrayList<TuCountersBO>();

	/**
	 * Attribute containing the instantiation failure count.
	 */
	private int totalCautions = 0;

	/**
	 * Attribute containing the skip class count.
	 */
	private int totalSkips = 0;

	/**
	 * Attribute maintaining the anticipated vs actual verification result.
	 */
	private boolean verificationResult;

	/**
	 * Constructor
	 */
	TuCountersListBO() {
		super();
	}

	/**
	 * Returns the verification result.
	 * 
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
	 * @param anticipatedCautions
	 */
	void initializeVerificationResults(final int anticipatedWarnings,
			final int anticipatedGlitches, final int anticipatedCautions) {

		this.verificationResult = this.getTotalWarnings() == anticipatedWarnings
				&& this.getTotalGlitches() == anticipatedGlitches
				&& this.getTotalCautions() == anticipatedCautions;
	}

	/**
	 * Adds a business object to the list.
	 * 
	 * @param tuCountersBO
	 */
	void addTuCounters(final TuCountersBO tuCountersBO) {

		this.tuCountersList.add(tuCountersBO);
	}

	/**
	 * Increments the caution count.
	 */
	void incrementTotalCautions() {

		this.totalCautions++;
	}

	/**
	 * Increments the skip count.
	 */
	void incrementTotalSkips() {

		this.totalSkips++;
	}

	/**
	 * Returns the total number of successful verifications.
	 * 
	 * @return int - The total number of successful verifications.
	 */
	int getSuccessfulVerifications() {

		// Declaration.
		TuCountersBO tuCountersBO;
		int totalSuccessful = 0;

		// Iterate.
		for (int i = 0; i < this.tuCountersList.size(); i++) {

			// Increment.
			tuCountersBO = this.tuCountersList.get(i);
			totalSuccessful = totalSuccessful
					+ tuCountersBO.getSuccessfulVerifications();
		}

		return totalSuccessful;
	}

	/**
	 * Returns the total number of verifications.
	 * 
	 * @return int - The total number of verifications.
	 */
	int getTotalVerifications() {

		// Declaration.
		TuCountersBO tuCountersBO;
		int totalVerifications = 0;

		// Iterate.
		for (int i = 0; i < this.tuCountersList.size(); i++) {

			// Increment.
			tuCountersBO = this.tuCountersList.get(i);
			totalVerifications = totalVerifications
					+ tuCountersBO.getTotalVerifications();
		}

		return totalVerifications;
	}

	/**
	 * Returns the total number of warnings.
	 * 
	 * @return int - The total number of warnings.
	 */
	int getTotalWarnings() {
		// Declaration.
		TuCountersBO tuCountersBO;
		int totalWarnings = 0;

		// Iterate.
		for (int i = 0; i < this.tuCountersList.size(); i++) {

			// Increment.
			tuCountersBO = this.tuCountersList.get(i);
			totalWarnings = totalWarnings + tuCountersBO.getTotalWarnings();
		}

		return totalWarnings;
	}

	/**
	 * Returns the total number of glitches.
	 * 
	 * @return int- The total number of glitches.
	 */
	int getTotalGlitches() {
		// Declaration.
		TuCountersBO tuCountersBO;
		int totalGlitches = 0;

		// Iterate.
		for (int i = 0; i < this.tuCountersList.size(); i++) {

			// Increment.
			tuCountersBO = this.tuCountersList.get(i);
			totalGlitches = totalGlitches + tuCountersBO.getTotalGlitches();
		}

		return totalGlitches;
	}

	/**
	 * Returns the total number of cautions.
	 * 
	 * @return int - The total number of cautions.
	 */
	int getTotalCautions() {

		return this.totalCautions;
	}

	/**
	 * Returns the total number of skips.
	 * 
	 * @return int - The total number of skips.
	 */
	int getTotalSkips() {

		return this.totalSkips;
	}
}