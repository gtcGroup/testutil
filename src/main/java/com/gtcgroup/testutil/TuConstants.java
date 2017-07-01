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
 * This class provides constants.
 * </p>
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 1999 - 2008 by Global Technology Consulting Group, Inc. at <a
 * href="http://gtcGroup.com">gtcGroup.com </a>.
 * </p>
 * 
 * @author MarvinToll@gtcGroup.com
 * @since v. 1.0
 */

final class TuConstants {

	/**
	 * Constructor (private)
	 * 
	 */
	private TuConstants() {
		super();
	}

	/**
	 * The version number - should be incremented with each release.
	 */
	static final String TEST_UTIL_VERSION = "2.5";

	/**
	 * String correlating to descriptor document name.
	 */
	static final String DESCRIPTOR_DOCUMENT_NAME = "testutil-config.xml";

	/**
	 * String correlating to descriptor document node.
	 */
	static final String DOCUMENT_NODE_CONFIGURATION = "Configuration";

	/**
	 * String correlating to descriptor document node.
	 */
	static final String DOCUMENT_NODE_ELEMENT = "Element";

	/**
	 * String correlating to a descriptor document attribute.
	 */
	static final String DOCUMENT_ATTRIBUTE_NAME = "name";

	/**
	 * String correlating to a descriptor document attribute value.
	 */
	static final String DOCUMENT_ATTRIBUTE_GETTER = "getter";

	/**
	 * String correlating to a descriptor document attribute value.
	 */
	static final String DOCUMENT_ATTRIBUTE_SETTER = "setter";

	/**
	 * String correlating to a descriptor document attribute value.
	 */
	static final String DOCUMENT_ATTRIBUTE_SKIP = "skip";

	/**
	 * String correlating to a descriptor document attribute value.
	 */
	static final String DOCUMENT_ATTRIBUTE_CAUTION = "caution";

	/**
	 * String correlating to Javadoc tag.
	 */
	static final String TAG_EXCLUDE_CLASS = "excludeClass";

	/**
	 * String correlating to Javadoc tag.
	 */
	static final String TAG_EXCLUDE_CLASS_FROM_TESTUTIL_VERIFICATION = "excludeClassFromTestUtilVerification";

	/**
	 * String correlating to Javadoc tag.
	 */
	static final String TAG_EXCLUDE_SETTER = "excludeSetter";

	/**
	 * String correlating to Javadoc tag.
	 */
	static final String TAG_EXCLUDE_SETTER_FROM_TESTUTIL_VERIFICATION = "excludeSetterFromTestUtilVerification";

	/**
	 * String correlating to descriptor document configuration.
	 */
	static final String EXCLUDE_CLASS_FROM_VERIFICATION = "exclude.class.from.verification";

	/**
	 * String correlating to descriptor document configuration.
	 */
	static final String EXCLUDE_METHOD_PAIR_BY_SETTER = "exclude.method.pair.by.setter";

	/**
	 * String correlating to descriptor document configuration.
	 */
	static final String EXCLUDE_METHOD_PAIRS_BY_TYPE = "exclude.method.pairs.by.type";

	/**
	 * String *not* correlating to descriptor document configuration. The TO is
	 * loaded programmatically.
	 */
	static final String INCLUDE_PRIMITIVE_RETURN_TYPE = "include.primitive.return.type";

	/**
	 * String correlating to descriptor document configuration.
	 */
	static final String SUBSTITUTE_SETTER_TYPE = "substitute.setter.type";

	/**
	 * String correlating to true for boolean operations.
	 */
	static final String TRUE_STRING = "true";

	/**
	 * String correlating to false for boolean operations.
	 */
	static final String FALSE_STRING = "false";

	/**
	 * String correlating to <code>String</code> class name.
	 */
	static final String STRING_CLASS_NAME = "java.lang.String";

	/**
	 * String correlating to <code>Enum</code> class name.
	 */
	static final String ENUM_CLASS_NAME = "java.lang.Enum";

	/**
	 * String used as duplicate method key.
	 */
	static final String DUPLICATE_METHOD_KEY = "@D@";

}