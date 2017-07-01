package jdom;

import java.util.ArrayList;
import java.util.Map;

/**
 * Special factory for building documents without any content or structure
 * checking. This should only be used when you are 100% positive that the input
 * is absolutely correct. This factory can speed builds, but any problems in the
 * input will be uncaught until later when they could cause infinite loops,
 * malformed XML, or worse. Use with extreme caution.
 */
public class UncheckedJDOMFactory implements JDOMFactory {

	// =====================================================================
	// Element Factory
	// =====================================================================

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#element(java.lang.String, jdom.Namespace)
	 */
	public Element element(final String name, Namespace namespace) {
		final Element e = new Element();
		e.name = name;
		if (namespace == null) {
			namespace = Namespace.NO_NAMESPACE;
		}
		e.namespace = namespace;
		return e;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#element(java.lang.String)
	 */
	public Element element(final String name) {
		final Element e = new Element();
		e.name = name;
		e.namespace = Namespace.NO_NAMESPACE;
		return e;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#element(java.lang.String, java.lang.String)
	 */
	public Element element(final String name, final String uri) {
		return this.element(name, Namespace.getNamespace("", uri));
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#element(java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	public Element element(final String name, final String prefix,
			final String uri) {
		return this.element(name, Namespace.getNamespace(prefix, uri));
	}

	// =====================================================================
	// Attribute Factory
	// =====================================================================

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#attribute(java.lang.String, java.lang.String,
	 *      jdom.Namespace)
	 */
	public Attribute attribute(final String name, final String value,
			Namespace namespace) {
		final Attribute a = new Attribute();
		a.name = name;
		a.value = value;
		if (namespace == null) {
			namespace = Namespace.NO_NAMESPACE;
		}
		a.namespace = namespace;
		return a;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#attribute(java.lang.String, java.lang.String, int,
	 *      jdom.Namespace)
	 */
	public Attribute attribute(final String name, final String value,
			final int type, Namespace namespace) {
		final Attribute a = new Attribute();
		a.name = name;
		a.type = type;
		a.value = value;
		if (namespace == null) {
			namespace = Namespace.NO_NAMESPACE;
		}
		a.namespace = namespace;
		return a;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#attribute(java.lang.String, java.lang.String)
	 */
	public Attribute attribute(final String name, final String value) {
		final Attribute a = new Attribute();
		a.name = name;
		a.value = value;
		a.namespace = Namespace.NO_NAMESPACE;
		return a;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#attribute(java.lang.String, java.lang.String, int)
	 */
	public Attribute attribute(final String name, final String value,
			final int type) {
		final Attribute a = new Attribute();
		a.name = name;
		a.type = type;
		a.value = value;
		a.namespace = Namespace.NO_NAMESPACE;
		return a;
	}

	// =====================================================================
	// Text Factory
	// =====================================================================

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#text(java.lang.String)
	 */
	public Text text(final String str) {
		final Text t = new Text();
		t.value = str;
		return t;
	}

	// =====================================================================
	// CDATA Factory
	// =====================================================================

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#cdata(java.lang.String)
	 */
	public CDATA cdata(final String str) {
		final CDATA c = new CDATA();
		c.value = str;
		return c;
	}

	// =====================================================================
	// Comment Factory
	// =====================================================================

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#comment(java.lang.String)
	 */
	public Comment comment(final String str) {
		final Comment c = new Comment();
		c.text = str;
		return c;
	}

	// =====================================================================
	// Processing Instruction Factory
	// =====================================================================

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
		final ProcessingInstruction p = new ProcessingInstruction();
		p.target = target;
		p.setData(data);
		return p;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#processingInstruction(java.lang.String,
	 *      java.lang.String)
	 */
	public ProcessingInstruction processingInstruction(final String target,
			final String data) {
		final ProcessingInstruction p = new ProcessingInstruction();
		p.target = target;
		p.setData(data);
		return p;
	}

	// =====================================================================
	// Entity Ref Factory
	// =====================================================================

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#entityRef(java.lang.String)
	 */
	public EntityRef entityRef(final String name) {
		final EntityRef e = new jdom.EntityRef();
		e.name = name;
		return e;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#entityRef(java.lang.String, java.lang.String)
	 */
	public EntityRef entityRef(final String name, final String systemID) {
		final EntityRef e = new EntityRef();
		e.name = name;
		e.systemID = systemID;
		return e;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#entityRef(java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	public EntityRef entityRef(final String name, final String publicID,
			final String systemID) {
		final EntityRef e = new EntityRef();
		e.name = name;
		e.publicID = publicID;
		e.systemID = systemID;
		return e;
	}

	// =====================================================================
	// DocType Factory
	// =====================================================================

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#docType(java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	public DocType docType(final String elementName, final String publicID,
			final String systemID) {
		final DocType d = new DocType();
		d.elementName = elementName;
		d.publicID = publicID;
		d.systemID = systemID;
		return d;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#docType(java.lang.String, java.lang.String)
	 */
	public DocType docType(final String elementName, final String systemID) {
		return this.docType(elementName, null, systemID);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#docType(java.lang.String)
	 */
	public DocType docType(final String elementName) {
		return this.docType(elementName, null, null);
	}

	// =====================================================================
	// Document Factory
	// =====================================================================

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#document(jdom.Element, jdom.DocType,
	 *      java.lang.String)
	 */
	public Document document(final Element rootElement, final DocType docType,
			final String baseURI) {
		final Document d = new Document();
		if (docType != null) {
			this.addContent(d, docType);
		}
		if (rootElement != null) {
			this.addContent(d, rootElement);
		}
		if (baseURI != null) {
			d.baseURI = baseURI;
		}
		return d;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#document(jdom.Element, jdom.DocType)
	 */
	public Document document(final Element rootElement, final DocType docType) {
		return this.document(rootElement, docType, null);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#document(jdom.Element)
	 */
	public Document document(final Element rootElement) {
		return this.document(rootElement, null, null);
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
		if (parent instanceof Element) {
			final Element elt = (Element) parent;
			elt.content.uncheckedAddContent(child);
		} else {
			final Document doc = (Document) parent;
			doc.content.uncheckedAddContent(child);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#setAttribute(jdom.Element, jdom.Attribute)
	 */
	public void setAttribute(final Element parent, final Attribute a) {
		parent.attributes.uncheckedAddAttribute(a);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see jdom.JDOMFactory#addNamespaceDeclaration(jdom.Element,
	 *      jdom.Namespace)
	 */
	public void addNamespaceDeclaration(final Element parent,
			final Namespace additional) {
		if (parent.additionalNamespaces == null) {
			parent.additionalNamespaces = new ArrayList<Object>(5); // Element.INITIAL_ARRAY_SIZE
		}
		parent.additionalNamespaces.add(additional);
	}
}
