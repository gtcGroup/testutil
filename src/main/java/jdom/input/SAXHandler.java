/*--

 $Id: SAXHandler.java,v 1.68 2004/08/31 06:14:05 jhunter Exp $

 Copyright (C) 2000-2004 Jason Hunter & Brett McLaughlin.
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:

 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions, and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions, and the disclaimer that follows
    these conditions in the documentation and/or other materials
    provided with the distribution.

 3. The name "JDOM" must not be used to endorse or promote products
    derived from this software without prior written permission.  For
    written permission, please contact <request_AT_jdom_DOT_org>.

 4. Products derived from this software may not be called "JDOM", nor
    may "JDOM" appear in their name, without prior written permission
    from the JDOM Project Management <request_AT_jdom_DOT_org>.

 In addition, we request (but do not require) that you include in the
 end-user documentation provided with the redistribution and/or in the
 software itself an acknowledgement equivalent to the following:
     "This product includes software developed by the
      JDOM Project (http://www.jdom.org/)."
 Alternatively, the acknowledgment may be graphical using the logos
 available at http://www.jdom.org/images/logos.

 THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED.  IN NO EVENT SHALL THE JDOM AUTHORS OR THE PROJECT
 CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 SUCH DAMAGE.

 This software consists of voluntary contributions made by many
 individuals on behalf of the JDOM Project and was originally
 created by Jason Hunter <jhunter_AT_jdom_DOT_org> and
 Brett McLaughlin <brett_AT_jdom_DOT_org>.  For more information
 on the JDOM Project, please see <http://www.jdom.org/>.

 */

package jdom.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jdom.Attribute;
import jdom.DefaultJDOMFactory;
import jdom.Document;
import jdom.Element;
import jdom.EntityRef;
import jdom.JDOMFactory;
import jdom.Namespace;
import jdom.Parent;

import org.xml.sax.Attributes;
import org.xml.sax.DTDHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A support class for {@link SAXBuilder}.
 * 
 * @version $Revision: 1.68 $, $Date: 2004/08/31 06:14:05 $
 * @author Brett McLaughlin
 * @author Jason Hunter
 * @author Philip Nelson
 * @author Bradley S. Huffman
 * @author phil@triloggroup.com
 */
public class SAXHandler extends DefaultHandler implements LexicalHandler,
		DeclHandler, DTDHandler {

	/** Hash table to map SAX attribute type names to JDOM attribute types. */
	private static final Map<String, Integer> attrNameToTypeMap = new HashMap<String, Integer>(
			13);

	/** <code>Document</code> object being built */
	private final Document document;

	/** <code>Element</code> object being built */
	private Element currentElement;

	/** Indicator of where in the document we are */
	private boolean atRoot;

	/**
	 * Indicator of whether we are in the DocType. Note that the DTD consists of
	 * both the internal subset (inside the <!DOCTYPE> tag) and the external
	 * subset (in a separate .dtd file).
	 */
	private boolean inDTD = false;

	/** Indicator of whether we are in the internal subset */
	private boolean inInternalSubset = false;

	/** Indicator of whether we previously were in a CDATA */
	private boolean previousCDATA = false;

	/** Indicator of whether we are in a CDATA */
	private boolean inCDATA = false;

	/** Indicator of whether we should expand entities */
	private boolean expand = true;

	/**
	 * Indicator of whether we are actively suppressing (non-expanding) a
	 * current entity
	 */
	private boolean suppress = false;

	/** How many nested entities we're currently within */
	private int entityDepth = 0; // may not be necessary anymore?

	/**
	 * Temporary holder for namespaces that have been declared with
	 * startPrefixMapping, but are not yet available on the element
	 */
	private final List<Namespace> declaredNamespaces;

	/** Temporary holder for the internal subset */
	private final StringBuffer internalSubset = new StringBuffer();

	/** Temporary holder for Text and CDATA */
	private final TextBuffer textBuffer = new TextBuffer();

	/** The external entities defined in this document */
	private final Map<String, String[]> externalEntities;

	/** The JDOMFactory used for JDOM object creation */
	private JDOMFactory factory;

	/** Whether to ignore ignorable whitespace */
	private boolean ignoringWhite = false;

	/** The SAX Locator object provided by the parser */
	private Locator locator;

	/**
	 * Class initializer: Populate a table to translate SAX attribute type names
	 * into JDOM attribute type value (integer).
	 * <p>
	 * <b>Note that all the mappings defined below are compliant with the SAX
	 * 2.0 specification exception for "ENUMERATION" with is specific to Crimson
	 * 1.1.X and Xerces 2.0.0-betaX which report attributes of enumerated types
	 * with a type "ENUMERATION" instead of the expected "NMTOKEN".
	 * </p>
	 * <p>
	 * Note also that Xerces 1.4.X is not SAX 2.0 compliant either but handling
	 * its case requires {@link #getAttributeType specific code}.
	 * </p>
	 */
	static {
		attrNameToTypeMap.put("CDATA", new Integer(Attribute.CDATA_TYPE));
		attrNameToTypeMap.put("ID", new Integer(Attribute.ID_TYPE));
		attrNameToTypeMap.put("IDREF", new Integer(Attribute.IDREF_TYPE));
		attrNameToTypeMap.put("IDREFS", new Integer(Attribute.IDREFS_TYPE));
		attrNameToTypeMap.put("ENTITY", new Integer(Attribute.ENTITY_TYPE));
		attrNameToTypeMap.put("ENTITIES", new Integer(Attribute.ENTITIES_TYPE));
		attrNameToTypeMap.put("NMTOKEN", new Integer(Attribute.NMTOKEN_TYPE));
		attrNameToTypeMap.put("NMTOKENS", new Integer(Attribute.NMTOKENS_TYPE));
		attrNameToTypeMap.put("NOTATION", new Integer(Attribute.NOTATION_TYPE));
		attrNameToTypeMap.put("ENUMERATION", new Integer(
				Attribute.ENUMERATED_TYPE));
	}

	/**
	 * This will create a new <code>SAXHandler</code> that listens to SAX
	 * events and creates a JDOM Document. The objects will be constructed using
	 * the default factory.
	 */
	public SAXHandler() {
		this(null);
	}

	/**
	 * This will create a new <code>SAXHandler</code> that listens to SAX
	 * events and creates a JDOM Document. The objects will be constructed using
	 * the provided factory.
	 * 
	 * @param factory
	 *            <code>JDOMFactory</code> to be used for constructing objects
	 */
	public SAXHandler(final JDOMFactory factory) {
		if (factory != null) {
			this.factory = factory;
		} else {
			this.factory = new DefaultJDOMFactory();
		}

		this.atRoot = true;
		this.declaredNamespaces = new ArrayList<Namespace>();
		this.externalEntities = new HashMap<String, String[]>();

		this.document = this.factory.document(null);
	}

	/**
	 * Pushes an element onto the tree under construction. Allows subclasses to
	 * put content under a dummy root element which is useful for building
	 * content that would otherwise be a non-well formed document.
	 * 
	 * @param element
	 *            root element under which content will be built
	 */
	protected void pushElement(final Element element) {
		if (this.atRoot) {
			this.document.setRootElement(element); // should we use a factory
			// call?
			this.atRoot = false;
		} else {
			this.factory.addContent(this.currentElement, element);
		}
		this.currentElement = element;
	}

	/**
	 * Returns the document. Should be called after parsing is complete.
	 * 
	 * @return <code>Document</code> - Document that was built
	 */
	public Document getDocument() {
		return this.document;
	}

	/**
	 * Returns the factory used for constructing objects.
	 * 
	 * @return <code>JDOMFactory</code> - the factory used for constructing
	 *         objects.
	 * 
	 * @see #SAXHandler(jdom.JDOMFactory)
	 */
	public JDOMFactory getFactory() {
		return this.factory;
	}

	/**
	 * This sets whether or not to expand entities during the build. A true
	 * means to expand entities as normal content. A false means to leave
	 * entities unexpanded as <code>EntityRef</code> objects. The default is
	 * true.
	 * 
	 * @param expand
	 *            <code>boolean</code> indicating whether entity expansion
	 *            should occur.
	 */
	public void setExpandEntities(final boolean expand) {
		this.expand = expand;
	}

	/**
	 * Returns whether or not entities will be expanded during the build.
	 * 
	 * @return <code>boolean</code> - whether entity expansion will occur
	 *         during build.
	 * 
	 * @see #setExpandEntities
	 */
	public boolean getExpandEntities() {
		return this.expand;
	}

	/**
	 * Specifies whether or not the parser should elminate whitespace in element
	 * content (sometimes known as "ignorable whitespace") when building the
	 * document. Only whitespace which is contained within element content that
	 * has an element only content model will be eliminated (see XML Rec 3.2.1).
	 * For this setting to take effect requires that validation be turned on.
	 * The default value of this setting is <code>false</code>.
	 * 
	 * @param ignoringWhite
	 *            Whether to ignore ignorable whitespace
	 */
	public void setIgnoringElementContentWhitespace(final boolean ignoringWhite) {
		this.ignoringWhite = ignoringWhite;
	}

	/**
	 * Returns whether or not the parser will elminate whitespace in element
	 * content (sometimes known as "ignorable whitespace") when building the
	 * document.
	 * 
	 * @return <code>boolean</code> - whether ignorable whitespace will be
	 *         ignored during build.
	 * 
	 * @see #setIgnoringElementContentWhitespace
	 */
	public boolean getIgnoringElementContentWhitespace() {
		return this.ignoringWhite;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#startDocument()
	 */
	@Override
	public void startDocument() {
		if (this.locator != null) {
			this.document.setBaseURI(this.locator.getSystemId());
		}
	}

	/**
	 * This is called when the parser encounters an external entity declaration.
	 * 
	 * @param name
	 *            entity name
	 * @param publicID
	 *            public id
	 * @param systemID
	 *            system id
	 */
	public void externalEntityDecl(final String name, final String publicID,
			final String systemID) {
		// Store the public and system ids for the name
		this.externalEntities.put(name, new String[] { publicID, systemID });

		if (!this.inInternalSubset) {
			return;
		}

		this.internalSubset.append("  <!ENTITY ").append(name);
		this.appendExternalId(publicID, systemID);
		this.internalSubset.append(">\n");
	}

	/**
	 * This handles an attribute declaration in the internal subset.
	 * 
	 * @param eName
	 *            <code>String</code> element name of attribute
	 * @param aName
	 *            <code>String</code> attribute name
	 * @param type
	 *            <code>String</code> attribute type
	 * @param valueDefault
	 *            <code>String</code> default value of attribute
	 * @param value
	 *            <code>String</code> value of attribute
	 */
	public void attributeDecl(final String eName, final String aName,
			final String type, final String valueDefault, final String value) {

		if (!this.inInternalSubset) {
			return;
		}

		this.internalSubset.append("  <!ATTLIST ").append(eName).append(' ')
				.append(aName).append(' ').append(type).append(' ');
		if (valueDefault != null) {
			this.internalSubset.append(valueDefault);
		} else {
			this.internalSubset.append('\"').append(value).append('\"');
		}
		if ((valueDefault != null) && (valueDefault.equals("#FIXED"))) {
			this.internalSubset.append(" \"").append(value).append('\"');
		}
		this.internalSubset.append(">\n");
	}

	/**
	 * Handle an element declaration in a DTD.
	 * 
	 * @param name
	 *            <code>String</code> name of element
	 * @param model
	 *            <code>String</code> model of the element in DTD syntax
	 */
	public void elementDecl(final String name, final String model) {
		// Skip elements that come from the external subset
		if (!this.inInternalSubset) {
			return;
		}

		this.internalSubset.append("  <!ELEMENT ").append(name).append(' ')
				.append(model).append(">\n");
	}

	/**
	 * Handle an internal entity declaration in a DTD.
	 * 
	 * @param name
	 *            <code>String</code> name of entity
	 * @param value
	 *            <code>String</code> value of the entity
	 */
	public void internalEntityDecl(final String name, final String value) {

		// Skip entities that come from the external subset
		if (!this.inInternalSubset) {
			return;
		}

		this.internalSubset.append("  <!ENTITY ");
		if (name.startsWith("%")) {
			this.internalSubset.append("% ").append(name.substring(1));
		} else {
			this.internalSubset.append(name);
		}
		this.internalSubset.append(" \"").append(value).append("\">\n");
	}

	/**
	 * This will indicate that a processing instruction has been encountered.
	 * (The XML declaration is not a processing instruction and will not be
	 * reported.)
	 * 
	 * @param target
	 *            <code>String</code> target of PI
	 * @param data
	 *            <code>String</code> containing all data sent to the PI. This
	 *            typically looks like one or more attribute value pairs.
	 * @throws SAXException
	 *             when things go wrong
	 */
	@Override
	public void processingInstruction(final String target, final String data)
			throws SAXException {

		if (this.suppress) {
			return;
		}

		this.flushCharacters();

		if (this.atRoot) {
			this.factory.addContent(this.document, this.factory
					.processingInstruction(target, data));
		} else {
			this.factory.addContent(this.getCurrentElement(), this.factory
					.processingInstruction(target, data));
		}
	}

	/**
	 * This indicates that an unresolvable entity reference has been
	 * encountered, normally because the external DTD subset has not been read.
	 * 
	 * @param name
	 *            <code>String</code> name of entity
	 * @throws SAXException
	 *             when things go wrong
	 */
	@Override
	public void skippedEntity(final String name) throws SAXException {

		// We don't handle parameter entity references.
		if (name.startsWith("%")) {
			return;
		}

		this.flushCharacters();

		this.factory.addContent(this.getCurrentElement(), this.factory
				.entityRef(name));
	}

	/**
	 * This will add the prefix mapping to the JDOM <code>Document</code>
	 * object.
	 * 
	 * @param prefix
	 *            <code>String</code> namespace prefix.
	 * @param uri
	 *            <code>String</code> namespace URI.
	 */
	@Override
	public void startPrefixMapping(final String prefix, final String uri) {

		if (this.suppress) {
			return;
		}

		final Namespace ns = Namespace.getNamespace(prefix, uri);
		this.declaredNamespaces.add(ns);
	}

	/**
	 * This reports the occurrence of an actual element. It will include the
	 * element's attributes, with the exception of XML vocabulary specific
	 * attributes, such as <code>xmlns:[namespace prefix]</code> and
	 * <code>xsi:schemaLocation</code>.
	 * 
	 * @param namespaceURI
	 *            <code>String</code> namespace URI this element is associated
	 *            with, or an empty <code>String</code>
	 * @param localName
	 *            <code>String</code> name of element (with no namespace
	 *            prefix, if one is present)
	 * @param qName
	 *            <code>String</code> XML 1.0 version of element name:
	 *            [namespace prefix]:[localName]
	 * @param atts
	 *            <code>Attributes</code> list for this element
	 * @throws SAXException
	 *             when things go wrong
	 */
	@Override
	public void startElement(final String namespaceURI, final String localName,
			final String qName, final Attributes atts) throws SAXException {
		if (this.suppress) {
			return;
		}

		Element element = null;

		if ((namespaceURI != null) && (!namespaceURI.equals(""))) {
			String prefix = "";

			// Determine any prefix on the Element
			if (!qName.equals(localName)) {
				final int split = qName.indexOf(":");
				prefix = qName.substring(0, split);
			}
			final Namespace elementNamespace = Namespace.getNamespace(prefix,
					namespaceURI);
			element = this.factory.element(localName, elementNamespace);
		} else {
			element = this.factory.element(localName);
		}

		// Take leftover declared namespaces and add them to this element's
		// map of namespaces
		if (this.declaredNamespaces.size() > 0) {
			this.transferNamespaces(element);
		}

		// Handle attributes
		for (int i = 0, len = atts.getLength(); i < len; i++) {
			Attribute attribute = null;

			final String attLocalName = atts.getLocalName(i);
			final String attQName = atts.getQName(i);
			final int attType = getAttributeType(atts.getType(i));

			// Bypass any xmlns attributes which might appear, as we got
			// them already in startPrefixMapping().
			// This is sometimes necessary when SAXHandler is used with
			// another source than SAXBuilder, as with JDOMResult.
			if (attQName.startsWith("xmlns:") || attQName.equals("xmlns")) {
				continue;
			}

			if (!attQName.equals(attLocalName)) {
				final String attPrefix = attQName.substring(0, attQName
						.indexOf(":"));
				final Namespace attNs = Namespace.getNamespace(attPrefix, atts
						.getURI(i));

				attribute = this.factory.attribute(attLocalName, atts
						.getValue(i), attType, attNs);
			} else {
				attribute = this.factory.attribute(attLocalName, atts
						.getValue(i), attType);
			}
			this.factory.setAttribute(element, attribute);
		}

		this.flushCharacters();

		if (this.atRoot) {
			this.document.setRootElement(element); // should we use a factory
			// call?
			this.atRoot = false;
		} else {
			this.factory.addContent(this.getCurrentElement(), element);
		}
		this.currentElement = element;
	}

	/**
	 * This will take the supplied <code>{@link Element}</code> and transfer
	 * its namespaces to the global namespace storage.
	 * 
	 * @param element
	 *            <code>Element</code> to read namespaces from.
	 */
	private void transferNamespaces(final Element element) {
		final Iterator<Namespace> i = this.declaredNamespaces.iterator();
		while (i.hasNext()) {
			final Namespace ns = i.next();
			if (ns != element.getNamespace()) {
				element.addNamespaceDeclaration(ns);
			}
		}
		this.declaredNamespaces.clear();
	}

	/**
	 * This will report character data (within an element).
	 * 
	 * @param ch
	 *            <code>char[]</code> character array with character data
	 * @param start
	 *            <code>int</code> index in array where data starts.
	 * @param length
	 *            <code>int</code> length of data.
	 * @throws SAXException
	 */
	@Override
	public void characters(final char[] ch, final int start, final int length)
			throws SAXException {

		if (this.suppress || (length == 0)) {
			return;
		}

		if (this.previousCDATA != this.inCDATA) {
			this.flushCharacters();
		}

		this.textBuffer.append(ch, start, length);
	}

	/**
	 * Capture ignorable whitespace as text. If
	 * setIgnoringElementContentWhitespace(true) has been called then this
	 * method does nothing.
	 * 
	 * @param ch
	 *            <code>[]</code> - char array of ignorable whitespace
	 * @param start
	 *            <code>int</code> - starting position within array
	 * @param length
	 *            <code>int</code> - length of whitespace after start
	 * @throws SAXException
	 *             when things go wrong
	 */
	@Override
	public void ignorableWhitespace(final char[] ch, final int start,
			final int length) throws SAXException {
		if (!this.ignoringWhite) {
			this.characters(ch, start, length);
		}
	}

	/**
	 * This will flush any characters from SAX character calls we've been
	 * buffering.
	 * 
	 * @throws SAXException
	 *             when things go wrong
	 */
	protected void flushCharacters() throws SAXException {
		this.flushCharacters(this.textBuffer.toString());
		this.textBuffer.clear();
	}

	/**
	 * Flush the given string into the document. This is a protected method so
	 * subclassers can control text handling without knowledge of the internals
	 * of this class.
	 * 
	 * @param data
	 *            string to flush
	 * @throws SAXException
	 */
	protected void flushCharacters(final String data) throws SAXException {
		if (data.length() == 0) {
			this.previousCDATA = this.inCDATA;
			return;
		}

		/**
		 * This is commented out because of some problems with the inline DTDs
		 * that Xerces seems to have. if (!inDTD) { if (inEntity) {
		 * getCurrentElement().setContent(factory.text(data)); } else {
		 * getCurrentElement().addContent(factory.text(data)); }
		 */

		if (this.previousCDATA) {
			this.factory.addContent(this.getCurrentElement(), this.factory
					.cdata(data));
		} else {
			this.factory.addContent(this.getCurrentElement(), this.factory
					.text(data));
		}

		this.previousCDATA = this.inCDATA;
	}

	/**
	 * Indicates the end of an element (<code>&lt;/[element name]&gt;</code>)
	 * is reached. Note that the parser does not distinguish between empty
	 * elements and non-empty elements, so this will occur uniformly.
	 * 
	 * @param namespaceURI
	 *            <code>String</code> URI of namespace this element is
	 *            associated with
	 * @param localName
	 *            <code>String</code> name of element without prefix
	 * @param qName
	 *            <code>String</code> name of element in XML 1.0 form
	 * @throws SAXException
	 *             when things go wrong
	 */
	@Override
	public void endElement(@SuppressWarnings("unused")
	final String namespaceURI, final String localName,
			@SuppressWarnings("unused")
			final String qName) throws SAXException {

		if (this.suppress) {
			return;
		}

		this.flushCharacters();

		if (!this.atRoot) {
			final Parent p = this.currentElement.getParent();
			if (p instanceof Document) {
				this.atRoot = true;
			} else {
				this.currentElement = (Element) p;
			}
		} else {
			throw new SAXException(
					"Ill-formed XML document (missing opening tag for "
							+ localName + ")");
		}
	}

	/**
	 * This will signify that a DTD is being parsed, and can be used to ensure
	 * that comments and other lexical structures in the DTD are not added to
	 * the JDOM <code>Document</code> object.
	 * 
	 * @param name
	 *            <code>String</code> name of element listed in DTD
	 * @param publicID
	 *            <code>String</code> public ID of DTD
	 * @param systemID
	 *            <code>String</code> system ID of DTD
	 * @throws SAXException
	 */
	public void startDTD(final String name, final String publicID,
			final String systemID) throws SAXException {

		this.flushCharacters(); // Is this needed here?

		this.factory.addContent(this.document, this.factory.docType(name,
				publicID, systemID));
		this.inDTD = true;
		this.inInternalSubset = true;
	}

	/**
	 * This signifies that the reading of the DTD is complete.
	 */
	public void endDTD() {

		this.document.getDocType().setInternalSubset(
				this.internalSubset.toString());
		this.inDTD = false;
		this.inInternalSubset = false;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.ext.LexicalHandler#startEntity(java.lang.String)
	 */
	public void startEntity(final String name) throws SAXException {
		this.entityDepth++;

		if (this.expand || this.entityDepth > 1) {
			// Short cut out if we're expanding or if we're nested
			return;
		}

		// A "[dtd]" entity indicates the beginning of the external subset
		if (name.equals("[dtd]")) {
			this.inInternalSubset = false;
			return;
		}

		// Ignore DTD references, and translate the standard 5
		if ((!this.inDTD) && (!name.equals("amp")) && (!name.equals("lt"))
				&& (!name.equals("gt")) && (!name.equals("apos"))
				&& (!name.equals("quot"))) {

			if (!this.expand) {
				String pub = null;
				String sys = null;
				final String[] ids = this.externalEntities.get(name);
				if (ids != null) {
					pub = ids[0]; // may be null, that's OK
					sys = ids[1]; // may be null, that's OK
				}
				/**
				 * if no current element, this entity belongs to an attribute in
				 * these cases, it is an error on the part of the parser to call
				 * startEntity but this will help in some cases. See
				 * org/xml/sax/ext/LexicalHandler.html#startEntity(java.lang.String)
				 * for more information
				 */
				if (!this.atRoot) {
					this.flushCharacters();
					final EntityRef entity = this.factory.entityRef(name, pub,
							sys);

					// no way to tell if the entity was from an attribute or
					// element so just assume element
					this.factory.addContent(this.getCurrentElement(), entity);
				}
				this.suppress = true;
			}
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.ext.LexicalHandler#endEntity(java.lang.String)
	 */
	public void endEntity(final String name) {
		this.entityDepth--;
		if (this.entityDepth == 0) {
			// No way are we suppressing if not in an entity,
			// regardless of the "expand" value
			this.suppress = false;
		}
		if (name.equals("[dtd]")) {
			this.inInternalSubset = true;
		}
	}

	/**
	 * Report a CDATA section
	 */
	public void startCDATA() {
		if (this.suppress) {
			return;
		}

		this.inCDATA = true;
	}

	/**
	 * Report a CDATA section
	 */
	public void endCDATA() {
		if (this.suppress) {
			return;
		}

		this.previousCDATA = true;
		this.inCDATA = false;
	}

	/**
	 * This reports that a comments is parsed. If not in the DTD, this comment
	 * is added to the current JDOM <code>Element</code>, or the
	 * <code>Document</code> itself if at that level.
	 * 
	 * @param ch
	 *            <code>ch[]</code> array of comment characters.
	 * @param start
	 *            <code>int</code> index to start reading from.
	 * @param length
	 *            <code>int</code> length of data.
	 * @throws SAXException
	 */
	public void comment(final char[] ch, final int start, final int length)
			throws SAXException {

		if (this.suppress) {
			return;
		}

		this.flushCharacters();

		final String commentText = new String(ch, start, length);
		if (this.inDTD && this.inInternalSubset && (this.expand == false)) {
			this.internalSubset.append("  <!--").append(commentText).append(
					"-->\n");
			return;
		}
		if ((!this.inDTD) && (!commentText.equals(""))) {
			if (this.atRoot) {
				this.factory.addContent(this.document, this.factory
						.comment(commentText));
			} else {
				this.factory.addContent(this.getCurrentElement(), this.factory
						.comment(commentText));
			}
		}
	}

	/**
	 * Handle the declaration of a Notation in a DTD
	 * 
	 * @param name
	 *            name of the notation
	 * @param publicID
	 *            the public ID of the notation
	 * @param systemID
	 *            the system ID of the notation
	 */
	@Override
	public void notationDecl(final String name, final String publicID,
			final String systemID) {

		if (!this.inInternalSubset) {
			return;
		}

		this.internalSubset.append("  <!NOTATION ").append(name);
		this.appendExternalId(publicID, systemID);
		this.internalSubset.append(">\n");
	}

	/**
	 * Handler for unparsed entity declarations in the DTD
	 * 
	 * @param name
	 *            <code>String</code> of the unparsed entity decl
	 * @param publicID
	 *            <code>String</code> of the unparsed entity decl
	 * @param systemID
	 *            <code>String</code> of the unparsed entity decl
	 * @param notationName
	 *            <code>String</code> of the unparsed entity decl
	 */
	@Override
	public void unparsedEntityDecl(final String name, final String publicID,
			final String systemID, final String notationName) {

		if (!this.inInternalSubset) {
			return;
		}

		this.internalSubset.append("  <!ENTITY ").append(name);
		this.appendExternalId(publicID, systemID);
		this.internalSubset.append(" NDATA ").append(notationName);
		this.internalSubset.append(">\n");
	}

	/**
	 * Appends an external ID to the internal subset buffer. Either publicID or
	 * systemID may be null, but not both.
	 * 
	 * @param publicID
	 *            the public ID
	 * @param systemID
	 *            the system ID
	 */
	private void appendExternalId(final String publicID, final String systemID) {
		if (publicID != null) {
			this.internalSubset.append(" PUBLIC \"").append(publicID).append(
					'\"');
		}
		if (systemID != null) {
			if (publicID == null) {
				this.internalSubset.append(" SYSTEM ");
			} else {
				this.internalSubset.append(' ');
			}
			this.internalSubset.append('\"').append(systemID).append('\"');
		}
	}

	/**
	 * Returns the being-parsed element.
	 * 
	 * @return <code>Element</code> - element being built.
	 * @throws SAXException
	 */
	public Element getCurrentElement() throws SAXException {
		if (this.currentElement == null) {
			throw new SAXException(
					"Ill-formed XML document (multiple root elements detected)");
		}
		return this.currentElement;
	}

	/**
	 * Returns the the JDOM Attribute type value from the SAX 2.0 attribute type
	 * string provided by the parser.
	 * 
	 * @param typeName
	 *            <code>String</code> the SAX 2.0 attribute type string.
	 * 
	 * @return <code>int</code> the JDOM attribute type.
	 * 
	 * @see Attribute#setAttributeType
	 */
	private static int getAttributeType(final String typeName) {
		final Integer type = (attrNameToTypeMap.get(typeName));
		if (type == null) {
			if (typeName != null && typeName.length() > 0
					&& typeName.charAt(0) == '(') {
				// Xerces 1.4.X reports attributes of enumerated type with
				// a type string equals to the enumeration definition, i.e.
				// starting with a parenthesis.
				return Attribute.ENUMERATED_TYPE;
			}
			return Attribute.UNDECLARED_TYPE;
		}
		return type.intValue();
	}

	/**
	 * Receives an object for locating the origin of SAX document events. This
	 * method is invoked by the SAX parser.
	 * <p>
	 * {@link jdom.JDOMFactory} implementations can use the
	 * {@link #getDocumentLocator} method to get access to the {@link Locator}
	 * during parse.
	 * </p>
	 * 
	 * @param locator
	 *            <code>Locator</code> an object that can return the location
	 *            of any SAX document event.
	 */
	@Override
	public void setDocumentLocator(final Locator locator) {
		this.locator = locator;
	}

	/**
	 * Provides access to the {@link Locator} object provided by the SAX parser.
	 * 
	 * @return <code>Locator</code> an object that can return the location of
	 *         any SAX document event.
	 */
	public Locator getDocumentLocator() {
		return this.locator;
	}
}
