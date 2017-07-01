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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import jdom.input.SAXBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * <p>
 * This utility class provides a set of static methods associated with loading
 * (unmarshalling) descriptor document elements into their respective Transfer
 * Objects (TOs) which are cached in the <code>TuCacher</code>.
 * </p>
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 1999 - 2008 by Global Technology Consulting Group, Inc. at <a
 * href="http://gtcGroup.com">gtcGroup.com </a>.
 * </p>
 * 
 * @author MarvinToll@gtcGroup.com
 * @since v. 1.0
 */
final class DocumentUnmarshaller {

	/**
	 * Class name for logging exceptions.
	 */
	private final static String CLASS_NAME = DocumentUnmarshaller.class
			.getName();

	/**
	 * Constructor (private)
	 */
	private DocumentUnmarshaller() {
		super();
	}

	/**
	 * Returns an <code>ArrayList</code> of descriptor document
	 * <code>URL</code>s.
	 * 
	 * @param strDocumentName
	 *            XML descriptor document name.
	 * @return ArrayList - Collection of descriptor document <code>URL</code>s.
	 */
	@SuppressWarnings("null")
	static final ArrayList<URL> discoverURLs(final String strDocumentName) {

		// Initialize.
		final String METHOD_NAME = "discoverURLs";
		Enumeration<URL> enumURLs = null;
		final ArrayList<URL> alURLs = new ArrayList<URL>();
		final ClassLoader classLoader = TuCacher.class.getClassLoader();

		// Look for all available descriptor documents.
		try {

			// Retrieve an enumeration.
			enumURLs = classLoader.getResources(strDocumentName);

			if (null == enumURLs) {

				// No URL retrieved.
				throw new IOException("Unable to retrieve a URL.");
			}

		} catch (final IOException e) {
			TuExceptionFatalHandler.throwDescriptorDocumentFatalException(
					CLASS_NAME, METHOD_NAME,
					"Unable to access a descriptor document ["
							+ strDocumentName + "] on the classpath.", e);
		}

		// Build a collection of URLs.
		while (enumURLs.hasMoreElements()) {
			alURLs.add(enumURLs.nextElement());
		}
		return alURLs;
	}

	/**
	 * Returns a descriptor <code>Document</code> collection or empty list if
	 * no optional descriptor documents are available from the classpath.
	 * 
	 * @param uRLsList
	 *            Collection of <code>URL</code> s referencing descriptor
	 *            documents
	 * @return ArrayList - Collection of <code>Document</code>s.
	 */
	static final ArrayList<Document> discoverDocuments(
			final ArrayList<?> uRLsList) {

		// Initialize
		final String METHOD_NAME = "discoverDocuments";
		final ArrayList<Document> alDocuments = new ArrayList<Document>();

		// Initialize DocumentBuilder.
		final DocumentBuilder documentBuilder = instantiateDocumentBuilder();
		final SAXBuilder saxBuilder = new SAXBuilder(true);

		// Build the portfolio of one or more documents.
		URL urlXML;

		// For each URL parse a DOM document.
		for (int i = 0; i < uRLsList.size(); i++) {

			// Retrieve an URL.
			urlXML = (URL) uRLsList.get(i);

			System.out.println("\t*     Loading ... " + urlXML.getPath());

			try {

				// Validate the document.
				saxBuilder.build(urlXML.getPath());

				// Parse the document.
				alDocuments.add(documentBuilder.parse(urlXML.getPath()));

			} catch (final Exception e) {
				TuExceptionFatalHandler
						.throwDescriptorDocumentFatalException(
								CLASS_NAME,
								METHOD_NAME,
								"The ["
										+ urlXML.getPath()
										+ "] descriptor document may not be well-formed or the DTD is not on the classpath.",
								e);

			}
		}
		// Return a portfolio of documents or empty list.
		return alDocuments;
	}

	/**
	 * Returns an initialized <code>DocumentBuilder</code>.
	 * 
	 * @return DocumentBuilder - An initialized object.
	 */
	private static DocumentBuilder instantiateDocumentBuilder() {

		// Initialize
		final String METHOD_NAME = "instantiateDocumentBuilder";
		DocumentBuilder documentBuilder = null;
		final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				.newInstance();

		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
		} catch (final ParserConfigurationException e) {
			TuExceptionFatalHandler
					.throwDescriptorDocumentFatalException(
							CLASS_NAME,
							METHOD_NAME,
							"A DocumentBuilder cannot be instantiated which satisfies the configuration requested.",
							e);
		}
		return documentBuilder;
	}

	/**
	 * Loads (unmarshalls) descriptor document elements into Transfer Objects
	 * stored in <code>TuCacher</code>.
	 * 
	 * @param documentList
	 *            An <code>ArrayList</code> of <code>Document</code>s.
	 */
	static final void unmarshallDocuments(final ArrayList<?> documentList) {

		// Initialization
		final String METHOD_NAME = "unmarshallDocuments";

		// Declaration.
		Document document = null;
		NodeList nlConfiguration;
		NodeList nlElements;
		NamedNodeMap nnmAttributes;
		Node node0;
		Node node1;
		Element element;
		String strConfigurationName;

		// Iterate through all documents in the portfolio.
		for (int i = 0; i < documentList.size(); i++) {

			// Retrieve a document.
			document = (Document) documentList.get(i);

			// Create a node list.
			nlConfiguration = document
					.getElementsByTagName(TuConstants.DOCUMENT_NODE_CONFIGURATION);

			// Iterate through the node list.
			for (int ii = 0; ii < nlConfiguration.getLength(); ii++) {

				// Retrieve an element.
				element = (Element) nlConfiguration.item(ii);

				// Retrieve the configuration type.
				strConfigurationName = element
						.getAttribute(TuConstants.DOCUMENT_ATTRIBUTE_NAME);

				// Create a node list.
				nlElements = element
						.getElementsByTagName(TuConstants.DOCUMENT_NODE_ELEMENT);

				// Iterate through the node list.
				for (int iii = 0; iii < nlElements.getLength(); iii++) {

					// Retrieve an element.
					element = (Element) nlElements.item(iii);

					// Determine if the element is active.
					if (element.getFirstChild().getNodeValue().trim()
							.equalsIgnoreCase("true")) {

						// Retrieve the attributes.
						nnmAttributes = element.getAttributes();

						// Retrieve the attribute nodes.
						node0 = nnmAttributes.item(0);
						node1 = nnmAttributes.item(1);

						try {

							// Cache the element.
							TuCacher.setCacheElement(strConfigurationName,
									node0.getNodeValue().trim(), node1
											.getNodeValue().trim());
						} catch (final RuntimeException e) {
							TuExceptionFatalHandler
									.throwDescriptorDocumentFatalException(
											CLASS_NAME, METHOD_NAME,
											"Unable to process [Configuration name=] with name ["
													+ strConfigurationName
													+ "] specified", e);
						}
					}
				}
			}

		}

		return;
	}
}