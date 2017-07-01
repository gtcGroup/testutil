/*--

 $Id: DOMBuilder.java,v 1.59 2004/09/03 06:03:41 jhunter Exp $

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

import jdom.Attribute;
import jdom.DefaultJDOMFactory;
import jdom.DocType;
import jdom.Document;
import jdom.Element;
import jdom.EntityRef;
import jdom.JDOMFactory;
import jdom.Namespace;

import org.w3c.dom.Attr;
import org.w3c.dom.DocumentType;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Builds a JDOM {@link jdom.Document jdom.Document} from a pre-existing DOM
 * {@link org.w3c.dom.Document org.w3c.dom.Document}. Also handy for testing
 * builds from files to sanity check {@link SAXBuilder}.
 * 
 * @version $Revision: 1.59 $, $Date: 2004/09/03 06:03:41 $
 * @author Brett McLaughlin
 * @author Jason Hunter
 * @author Philip Nelson
 * @author Kevin Regan
 * @author Yusuf Goolamabbas
 * @author Dan Schaffer
 * @author Bradley S. Huffman
 */
public class DOMBuilder {

	/** Adapter class to use */
	@SuppressWarnings("unused")
	private String adapterClass;

	/** The factory for creating new JDOM objects */
	private JDOMFactory factory = new DefaultJDOMFactory();

	/**
	 * This creates a new DOMBuilder which will attempt to first locate a parser
	 * via JAXP, then will try to use a set of default parsers. The underlying
	 * parser will not validate.
	 */
	public DOMBuilder() {
		//
	}

	/**
	 * This creates a new DOMBuilder using the specified DOMAdapter
	 * implementation as a way to choose the underlying parser. The underlying
	 * parser will not validate.
	 * 
	 * @param adapterClass
	 *            <code>String</code> name of class to use for DOM building.
	 */
	public DOMBuilder(final String adapterClass) {
		this.adapterClass = adapterClass;
	}

	/**
	 * This sets a custom JDOMFactory for the builder. Use this to build the
	 * tree with your own subclasses of the JDOM classes.
	 * 
	 * @param factory
	 *            <code>JDOMFactory</code> to use
	 */
	public void setFactory(final JDOMFactory factory) {
		this.factory = factory;
	}

	/**
	 * Returns the current {@link jdom.JDOMFactory} in use.
	 * 
	 * @return the factory in use
	 */
	public JDOMFactory getFactory() {
		return this.factory;
	}

	/**
	 * This will build a JDOM tree from an existing DOM tree.
	 * 
	 * @param domDocument
	 *            <code>org.w3c.dom.Document</code> object
	 * @return <code>Document</code> - JDOM document object.
	 */
	public Document build(final org.w3c.dom.Document domDocument) {
		final Document doc = this.factory.document(null);
		this.buildTree(domDocument, doc, null, true);
		return doc;
	}

	/**
	 * This will build a JDOM Element from an existing DOM Element
	 * 
	 * @param domElement
	 *            <code> org.w3c.dom.Element</code> object
	 * @return <code>Element</code> - JDOM Element object
	 */
	public jdom.Element build(final org.w3c.dom.Element domElement) {
		final Document doc = this.factory.document(null);
		this.buildTree(domElement, doc, null, true);
		return doc.getRootElement();
	}

	/**
	 * This takes a DOM <code>Node</code> and builds up a JDOM tree, recursing
	 * until the DOM tree is exhausted and the JDOM tree results.
	 * 
	 * @param node
	 *            <code>Code</node> to examine.
	 * @param doc JDOM <code>Document</code> being built.
	 * @param current <code>Element</code> that is current parent.
	 * @param atRoot <code>boolean</code> indicating whether at root level.
	 */
	private void buildTree(final Node node, final Document doc,
			final Element current, final boolean atRoot) {
		// Recurse through the tree
		switch (node.getNodeType()) {
		case Node.DOCUMENT_NODE:
			final NodeList nodes = node.getChildNodes();
			for (int i = 0, size = nodes.getLength(); i < size; i++) {
				this.buildTree(nodes.item(i), doc, current, true);
			}
			break;

		case Node.ELEMENT_NODE:
			final String nodeName = node.getNodeName();
			String prefix = "";
			String localName = nodeName;
			int colon = nodeName.indexOf(':');
			if (colon >= 0) {
				prefix = nodeName.substring(0, colon);
				localName = nodeName.substring(colon + 1);
			}

			// Get element's namespace
			Namespace ns = null;
			final String uri = node.getNamespaceURI();
			if (uri == null) {
				ns = (current == null) ? Namespace.NO_NAMESPACE : current
						.getNamespace(prefix);
			} else {
				ns = Namespace.getNamespace(prefix, uri);
			}

			final Element element = this.factory.element(localName, ns);

			if (atRoot) {
				// If at root, set as document root
				doc.setRootElement(element); // should we use a factory call?
			} else {
				// else add to parent element
				this.factory.addContent(current, element);
			}

			// Add namespaces
			final NamedNodeMap attributeList = node.getAttributes();
			final int attsize = attributeList.getLength();

			for (int i = 0; i < attsize; i++) {
				final Attr att = (Attr) attributeList.item(i);

				final String attname = att.getName();
				if (attname.startsWith("xmlns")) {
					String attPrefix = "";
					colon = attname.indexOf(':');
					if (colon >= 0) {
						attPrefix = attname.substring(colon + 1);
					}

					final String attvalue = att.getValue();

					final Namespace declaredNS = Namespace.getNamespace(
							attPrefix, attvalue);

					// Add as additional namespaces if it's different
					// than this element's namespace (perhaps we should
					// also have logic not to mark them as additional if
					// it's been done already, but it probably doesn't
					// matter)
					if (prefix.equals(attPrefix)) {
						element.setNamespace(declaredNS);
					} else {
						this.factory.addNamespaceDeclaration(element,
								declaredNS);
					}
				}
			}

			// Add attributes
			for (int i = 0; i < attsize; i++) {
				final Attr att = (Attr) attributeList.item(i);

				final String attname = att.getName();

				if (!attname.startsWith("xmlns")) {
					String attPrefix = "";
					String attLocalName = attname;
					colon = attname.indexOf(':');
					if (colon >= 0) {
						attPrefix = attname.substring(0, colon);
						attLocalName = attname.substring(colon + 1);
					}

					final String attvalue = att.getValue();

					// Get attribute's namespace
					Namespace attns = null;
					if ("".equals(attPrefix)) {
						attns = Namespace.NO_NAMESPACE;
					} else {
						attns = element.getNamespace(attPrefix);
					}

					final Attribute attribute = this.factory.attribute(
							attLocalName, attvalue, attns);
					this.factory.setAttribute(element, attribute);
				}
			}

			// Recurse on child nodes
			// The list should never be null nor should it ever contain
			// null nodes, but some DOM impls are broken
			final NodeList children = node.getChildNodes();
			if (children != null) {
				final int size = children.getLength();
				for (int i = 0; i < size; i++) {
					final Node item = children.item(i);
					if (item != null) {
						this.buildTree(item, doc, element, false);
					}
				}
			}
			break;

		case Node.TEXT_NODE:
			final String data = node.getNodeValue();
			this.factory.addContent(current, this.factory.text(data));
			break;

		case Node.CDATA_SECTION_NODE:
			final String cdata = node.getNodeValue();
			this.factory.addContent(current, this.factory.cdata(cdata));
			break;

		case Node.PROCESSING_INSTRUCTION_NODE:
			if (atRoot) {
				this.factory.addContent(doc, this.factory
						.processingInstruction(node.getNodeName(), node
								.getNodeValue()));
			} else {
				this.factory.addContent(current, this.factory
						.processingInstruction(node.getNodeName(), node
								.getNodeValue()));
			}
			break;

		case Node.COMMENT_NODE:
			if (atRoot) {
				this.factory.addContent(doc, this.factory.comment(node
						.getNodeValue()));
			} else {
				this.factory.addContent(current, this.factory.comment(node
						.getNodeValue()));
			}
			break;

		case Node.ENTITY_REFERENCE_NODE:
			final EntityRef entity = this.factory.entityRef(node.getNodeName());
			this.factory.addContent(current, entity);
			break;

		case Node.ENTITY_NODE:
			// ??
			break;

		case Node.DOCUMENT_TYPE_NODE:
			final DocumentType domDocType = (DocumentType) node;
			final String publicID = domDocType.getPublicId();
			final String systemID = domDocType.getSystemId();
			final String internalDTD = domDocType.getInternalSubset();

			final DocType docType = this.factory.docType(domDocType.getName());
			docType.setPublicID(publicID);
			docType.setSystemID(systemID);
			docType.setInternalSubset(internalDTD);

			this.factory.addContent(doc, docType);
			break;
		}
	}
}
