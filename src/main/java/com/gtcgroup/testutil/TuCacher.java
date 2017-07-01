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
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;

/**
 * <p>
 * This singleton class provides caching.
 * </p>
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 1999 - 2008 by Global Technology Consulting Group, Inc. at <a
 * href="http://gtcGroup.com">gtcGroup.com </a>.
 * </p>
 * 
 * @author MarvinToll@gtcGroup.com
 * @since v. 1.0
 */
final class TuCacher {

	/**
	 * Class name for logging exceptions.
	 */
	private final static String CLASS_NAME = TuCacher.class.getName();

	/**
	 * This singleton providing caching.
	 */
	private static final TuCacher cacher;

	/**
	 * An <code>ArrayList</code> of <code>URL</code>s.
	 */
	private static ArrayList<URL> uRLsList = null;

	/**
	 * An <code>ArrayList</code> containing the last modified date of
	 * descriptor document(s).
	 */
	private static ArrayList<Long> modifiedDatesList = new ArrayList<Long>();

	/**
	 * Indicates if descriptor document(s) processing was successfully
	 * completed. Since a descriptor document is optional, one may not have been
	 * loaded (unmarshalled).
	 */
	private static boolean isDescriptorDocumentProcessingCompleted = false;

	/**
	 * Synchronized cache containing Transfer Objects.
	 */
	private static Map<String, TuBaseConfigurationTO> cache = Collections
			.synchronizedMap(new HashMap<String, TuBaseConfigurationTO>());

	/**
	 * Static Method
	 */
	static {
		// Instantiate singleton.
		cacher = new TuCacher();
	}

	/**
	 * Singleton Constructor.
	 */
	private TuCacher() {

		System.out.println("\n\t********************************************"
				+ "\n\t*           TestUtil Version "
				+ TuConstants.TEST_UTIL_VERSION + "           *"
				+ "\n\t*        Copyright (c) 1999 - 2008         *"
				+ "\n\t* Global Technology Consulting Group, Inc. *"
				+ "\n\t*           http://gtcGroup.com            *"
				+ "\n\t********************************************");

		System.out
				.println("\t* Begin - Search for optional testutil-config.xml descriptor document(s).");

		// Initialize cache.
		initializeCache();

		// Unmarshall the documents.
		unmarshallDescriptorDocuments();

		// Preserve the dates the descriptor documents were modified.
		saveModifiedDates();

		System.out
				.println("\t* End - Loaded ["
						+ uRLsList.size()
						+ "] TestUtil descriptor document(s). "
						+ "\n\t******************************************************\n");

		// Indicate document processing successfully completed.
		isDescriptorDocumentProcessingCompleted = true;

	}

	/**
	 * Load (or reload modified) descriptor document(s).
	 */
	static final void unmarshallDescriptorDocuments() {

		// Retrieve and store a collection of URLs.
		uRLsList = DocumentUnmarshaller
				.discoverURLs(TuConstants.DESCRIPTOR_DOCUMENT_NAME);

		// Find all available descriptor documents.
		final ArrayList<Document> alDocuments = DocumentUnmarshaller
				.discoverDocuments(uRLsList);

		// If there are no documents available, use a default.
		if (alDocuments.isEmpty()) {

			// Console message.
			System.out
					.println("\t*     No optional descriptor document(s) found.");

			// Retrieve and store a default substitute types.
			setSubstituteType();

		} else {

			// Load the documents.
			DocumentUnmarshaller.unmarshallDocuments(alDocuments);
		}
		return;
	}

	/**
	 * Save the last modified date of descriptor document(s).
	 */
	static final void setSubstituteType() {

		// Cache the element.
		TuCacher.setCacheElement(TuConstants.SUBSTITUTE_SETTER_TYPE, "boolean",
				"java.lang.Boolean");
		TuCacher.setCacheElement(TuConstants.SUBSTITUTE_SETTER_TYPE, "byte",
				"java.lang.Byte");
		TuCacher.setCacheElement(TuConstants.SUBSTITUTE_SETTER_TYPE, "char",
				"java.lang.Character");
		TuCacher.setCacheElement(TuConstants.SUBSTITUTE_SETTER_TYPE, "double",
				"java.lang.Double");
		TuCacher.setCacheElement(TuConstants.SUBSTITUTE_SETTER_TYPE, "float",
				"java.lang.Float");
		TuCacher.setCacheElement(TuConstants.SUBSTITUTE_SETTER_TYPE, "int",
				"java.lang.Integer");
		TuCacher.setCacheElement(TuConstants.SUBSTITUTE_SETTER_TYPE, "long",
				"java.lang.Long");
		TuCacher.setCacheElement(TuConstants.SUBSTITUTE_SETTER_TYPE, "short",
				"java.lang.Short");

		TuCacher.setCacheElement(TuConstants.SUBSTITUTE_SETTER_TYPE,
				"java.sql.Timestamp",
				"com.gtcgroup.testutil.mock.TimestampMock");
	}

	/**
	 * Save the last modified date of descriptor document(s).
	 */
	static final void saveModifiedDates() {

		// Initialize/Declare.
		URL url;
		File file;
		modifiedDatesList.clear();

		// Iterate through list of URLs.
		for (int i = 0; i < uRLsList.size(); i++) {

			// Retrieve URL.
			url = uRLsList.get(i);

			// Instantiate a file.
			file = new File(url.getPath());

			// Preserve the modified date.
			modifiedDatesList.add(new Long(file.lastModified()));

		}
	}

	/**
	 * Determine if any descriptor documents have been modified since the cache
	 * was last loaded. If so, refresh the cache.
	 */
	final synchronized void checkForStaleCache() {

		// Initialize/Declare.
		URL url;
		File file;
		isDescriptorDocumentProcessingCompleted = false;

		// Iterate through list of URLs.
		for (int i = 0; i < uRLsList.size(); i++) {

			// Retrieve URL.
			url = uRLsList.get(i);

			// Instantiate a file.
			file = new File(url.getPath());

			// Determine if a descriptor document was modified.
			if (file.lastModified() > (modifiedDatesList.get(i)).longValue()) {

				System.out
						.println("\n\t******************************************************"
								+ "\n\t* Begin - reloading modified descriptor document(s). ");

				// Unmarshall the documents.
				unmarshallDescriptorDocuments();

				// Preserve the dates the descriptor documents were modified.
				saveModifiedDates();

				System.out
						.println("\t* End - reloading modified descriptor document(s). "
								+ "\n\t******************************************************\n");
			}
		}
		// Indicate document processing successfully completed.
		isDescriptorDocumentProcessingCompleted = true;
	}

	/**
	 * This singleton method verifies a clean load of the descriptor
	 * document(s).
	 * 
	 * @return TuCacher - This singleton instance.
	 */
	static final TuCacher getInstance() {

		final String METHOD_NAME = "getInstance";

		if (!isDescriptorDocumentProcessingCompleted) {

			TuExceptionFatalHandler
					.throwDescriptorDocumentFatalException(
							CLASS_NAME,
							METHOD_NAME,
							"One or more descriptor documents were not effectively processed.",
							null);
		}
		return cacher;
	}

	/**
	 * Returns a cached Transfer Object.
	 * 
	 * @return ExcludeClassFromVerificationTO - A cached Transfer Object.
	 * @since v. 2.0
	 */
	final ExcludeClassFromVerificationTO getExcludeClassFromVerification() {
		return (ExcludeClassFromVerificationTO) cache
				.get(TuConstants.EXCLUDE_CLASS_FROM_VERIFICATION);
	}

	/**
	 * Returns a cached Transfer Object.
	 * 
	 * @return ExcludeMethodPairBySetterTO - A cached Transfer Object.
	 */
	final ExcludeMethodPairBySetterTO getExcludeMethodPairBySetter() {
		return (ExcludeMethodPairBySetterTO) cache
				.get(TuConstants.EXCLUDE_METHOD_PAIR_BY_SETTER);
	}

	/**
	 * Returns a cached Transfer Object.
	 * 
	 * @return ExcludeMethodPairsByTypeTO - A cached Transfer Object.
	 */
	final ExcludeMethodPairsByTypeTO getExcludeMethodPairsByType() {
		return (ExcludeMethodPairsByTypeTO) cache
				.get(TuConstants.EXCLUDE_METHOD_PAIRS_BY_TYPE);
	}

	/**
	 * Returns a cached Transfer Object.
	 * 
	 * @return IncludePrimitiveReturnTypeTO - A cached Transfer Object.
	 */
	final IncludePrimitiveReturnTypeTO getIncludePrimitiveReturnType() {
		return (IncludePrimitiveReturnTypeTO) cache
				.get(TuConstants.INCLUDE_PRIMITIVE_RETURN_TYPE);
	}

	/**
	 * Returns a cached Transfer Object.
	 * 
	 * @return SubstituteSetterTypeTO - A cached Transfer Object.
	 */
	final SubstituteSetterTypeTO getSubstituteSetterType() {
		return (SubstituteSetterTypeTO) cache
				.get(TuConstants.SUBSTITUTE_SETTER_TYPE);
	}

	/**
	 * Initializes cache with empty Transfer Objects. Note: Excepting
	 * <code>IncludePrimitiveReturnTypeTO</code> whose constructor is
	 * self-populating.
	 */
	static final void initializeCache() {
		cache.put(TuConstants.EXCLUDE_CLASS_FROM_VERIFICATION,
				new ExcludeClassFromVerificationTO());
		cache.put(TuConstants.EXCLUDE_METHOD_PAIR_BY_SETTER,
				new ExcludeMethodPairBySetterTO());
		cache.put(TuConstants.EXCLUDE_METHOD_PAIRS_BY_TYPE,
				new ExcludeMethodPairsByTypeTO());
		cache.put(TuConstants.INCLUDE_PRIMITIVE_RETURN_TYPE,
				new IncludePrimitiveReturnTypeTO());
		cache.put(TuConstants.SUBSTITUTE_SETTER_TYPE,
				new SubstituteSetterTypeTO());
	}

	/**
	 * Adds a <code>TuElementTO</code> to a Transfer Object already in cache.
	 * 
	 * @param strConfigurationName
	 *            The name of a descriptor document Configuration.
	 * @param attributeOne
	 *            The first attribute for a descriptor document
	 *            <code>Element</code>.
	 * @param attributeTwo
	 *            The second attribute for a descriptor document
	 *            <code>Element</code>.
	 */
	static final void setCacheElement(final String strConfigurationName,
			final String attributeOne, final String attributeTwo) {

		(cache.get(strConfigurationName)).addElement(attributeOne.trim(),
				attributeTwo.trim());

	}
}
