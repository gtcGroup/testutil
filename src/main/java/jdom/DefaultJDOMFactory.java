/*--

 $Id: DefaultJDOMFactory.java,v 1.6 2004/09/01 05:25:38 jhunter Exp $

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

package jdom;

import java.util.Map;

/**
 * Creates the standard top-level JDOM classes (Element, Document, Comment,
 * etc). A subclass of this factory might construct custom classes.
 * 
 * @version $Revision: 1.6 $, $Date: 2004/09/01 05:25:38 $
 * @author Ken Rune Holland
 * @author Phil Nelson
 * @author Bradley S. Huffman
 */
public class DefaultJDOMFactory implements JDOMFactory {

	/**
	 * 
	 */
	public DefaultJDOMFactory() {
		//
	}

	// Allow Javadocs to inherit from JDOMFactory

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#attribute(java.lang.String, java.lang.String,
	 *      jdom.Namespace)
	 */
	public Attribute attribute(final String name, final String value,
			final Namespace namespace) {
		return new Attribute(name, value, namespace);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#attribute(java.lang.String, java.lang.String, int,
	 *      jdom.Namespace)
	 */
	public Attribute attribute(final String name, final String value,
			final int type, final Namespace namespace) {
		return new Attribute(name, value, type, namespace);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#attribute(java.lang.String, java.lang.String)
	 */
	public Attribute attribute(final String name, final String value) {
		return new Attribute(name, value);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#attribute(java.lang.String, java.lang.String, int)
	 */
	public Attribute attribute(final String name, final String value,
			final int type) {
		return new Attribute(name, value, type);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#cdata(java.lang.String)
	 */
	public CDATA cdata(final String text) {
		return new CDATA(text);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#text(java.lang.String)
	 */
	public Text text(final String text) {
		return new Text(text);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#comment(java.lang.String)
	 */
	public Comment comment(final String text) {
		return new Comment(text);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#docType(java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	public DocType docType(final String elementName, final String publicID,
			final String systemID) {
		return new DocType(elementName, publicID, systemID);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#docType(java.lang.String, java.lang.String)
	 */
	public DocType docType(final String elementName, final String systemID) {
		return new DocType(elementName, systemID);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#docType(java.lang.String)
	 */
	public DocType docType(final String elementName) {
		return new DocType(elementName);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#document(jdom.Element, jdom.DocType)
	 */
	public Document document(final Element rootElement, final DocType docType) {
		return new Document(rootElement, docType);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#document(jdom.Element, jdom.DocType,
	 *      java.lang.String)
	 */
	public Document document(final Element rootElement, final DocType docType,
			final String baseURI) {
		return new Document(rootElement, docType, baseURI);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#document(jdom.Element)
	 */
	public Document document(final Element rootElement) {
		return new Document(rootElement);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#element(java.lang.String, jdom.Namespace)
	 */
	public Element element(final String name, final Namespace namespace) {
		return new Element(name, namespace);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#element(java.lang.String)
	 */
	public Element element(final String name) {
		return new Element(name);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#element(java.lang.String, java.lang.String)
	 */
	public Element element(final String name, final String uri) {
		return new Element(name, uri);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#element(java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	public Element element(final String name, final String prefix,
			final String uri) {
		return new Element(name, prefix, uri);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @param target
	 * @param data
	 * @return ProcessingInstruction
	 * 
	 * @see jdom.JDOMFactory#processingInstruction(java.lang.String,
	 *      java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public ProcessingInstruction processingInstruction(final String target,
			final Map data) {
		return new ProcessingInstruction(target, data);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#processingInstruction(java.lang.String,
	 *      java.lang.String)
	 */
	public ProcessingInstruction processingInstruction(final String target,
			final String data) {
		return new ProcessingInstruction(target, data);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#entityRef(java.lang.String)
	 */
	public EntityRef entityRef(final String name) {
		return new EntityRef(name);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#entityRef(java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	public EntityRef entityRef(final String name, final String publicID,
			final String systemID) {
		return new EntityRef(name, publicID, systemID);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#entityRef(java.lang.String, java.lang.String)
	 */
	public EntityRef entityRef(final String name, final String systemID) {
		return new EntityRef(name, systemID);
	}

	// =====================================================================
	// List manipulation
	// =====================================================================

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#addContent(jdom.Parent, jdom.Content)
	 */
	public void addContent(final Parent parent, final Content child) {
		if (parent instanceof Document) {
			((Document) parent).addContent(child);
		} else {
			((Element) parent).addContent(child);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#setAttribute(jdom.Element, jdom.Attribute)
	 */
	public void setAttribute(final Element parent, final Attribute a) {
		parent.setAttribute(a);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#addNamespaceDeclaration(jdom.Element,
	 *      jdom.Namespace)
	 */
	public void addNamespaceDeclaration(final Element parent,
			final Namespace additional) {
		parent.addNamespaceDeclaration(additional);
	}
}
