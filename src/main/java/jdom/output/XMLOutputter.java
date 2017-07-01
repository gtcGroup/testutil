/*--

 $Id: XMLOutputter.java,v 1.112 2004/09/01 06:08:18 jhunter Exp $

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

package jdom.output;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import javax.xml.transform.Result;

import jdom.Attribute;
import jdom.CDATA;
import jdom.Comment;
import jdom.DocType;
import jdom.Document;
import jdom.Element;
import jdom.EntityRef;
import jdom.Namespace;
import jdom.ProcessingInstruction;
import jdom.Text;

/**
 * Outputs a JDOM document as a stream of bytes. The outputter can manage many
 * styles of document formatting, from untouched to pretty printed. The default
 * is to output the document content exactly as created, but this can be changed
 * by setting a new Format object. For pretty-print output, use
 * <code>{@link Format#getPrettyFormat()}</code>. For whitespace-normalized
 * output, use <code>{@link Format#getCompactFormat()}</code>.
 * <p>
 * There are <code>output(...)</code> methods to print any of the standard
 * JDOM classes, including Document and Element, to either a Writer or an
 * OutputStream. <b>Warning</b>: When outputting to a Writer, make sure the
 * writer's encoding matches the encoding setting in the Format object. This
 * ensures the encoding in which the content is written (controlled by the
 * Writer configuration) matches the encoding placed in the document's XML
 * declaration (controlled by the XMLOutputter). Because a Writer cannot be
 * queried for its encoding, the information must be passed to the Format
 * manually in its constructor or via the
 * <code>{@link Format#setEncoding}</code> method. The default encoding is
 * UTF-8.
 * <p>
 * The methods <code>outputString(...)</code> are for convenience only; for
 * top performance you should call one of the <code>
 * output(...)</code> methods
 * and pass in your own Writer or OutputStream if possible.
 * <p>
 * XML declarations are always printed on their own line followed by a line
 * seperator (this doesn't change the semantics of the document). To omit
 * printing of the declaration use
 * <code>{@link Format#setOmitDeclaration}</code>. To omit printing of the
 * encoding in the declaration use <code>{@link Format#setOmitEncoding}</code>.
 * Unfortunatly there is currently no way to know the original encoding of the
 * document.
 * <p>
 * Empty elements are by default printed as &lt;empty/&gt;, but this can be
 * configured with <code>{@link Format#setExpandEmptyElements}</code> to cause
 * them to be expanded to &lt;empty&gt;&lt;/empty&gt;.
 * 
 * @version $Revision: 1.112 $, $Date: 2004/09/01 06:08:18 $
 * @author Brett McLaughlin
 * @author Jason Hunter
 * @author Jason Reid
 * @author Wolfgang Werner
 * @author Elliotte Rusty Harold
 * @author David &amp; Will (from Post Tool Design)
 * @author Dan Schaffer
 * @author Alex Chaffee
 * @author Bradley S. Huffman
 */

public class XMLOutputter implements Cloneable {

	// For normal output
	/**
	 * 
	 */
	private Format userFormat = Format.getRawFormat();

	// For xml:space="preserve"
	/**
	 * 
	 */
	protected static final Format preserveFormat = Format.getRawFormat();

	// What's currently in use
	/**
	 * 
	 */
	protected Format currentFormat = this.userFormat;

	/**
	 * Whether output escaping is enabled for the being processed Element -
	 * default is <code>true</code>
	 */
	private boolean escapeOutput = true;

	// * * * * * * * * * * Constructors * * * * * * * * * *
	// * * * * * * * * * * Constructors * * * * * * * * * *

	/**
	 * This will create an <code>XMLOutputter</code> with the default
	 * {@link Format} matching {@link Format#getRawFormat}.
	 */
	public XMLOutputter() {
		//
	}

	/**
	 * This will create an <code>XMLOutputter</code> with the specified format
	 * characteristics. Note the format object is cloned internally before use.
	 * 
	 * @param format
	 */
	public XMLOutputter(final Format format) {
		this.userFormat = (Format) format.clone();
		this.currentFormat = this.userFormat;
	}

	/**
	 * This will create an <code>XMLOutputter</code> with all the options as
	 * set in the given <code>XMLOutputter</code>. Note that
	 * <code>XMLOutputter two = (XMLOutputter)one.clone();</code> would work
	 * equally well.
	 * 
	 * @param that
	 *            the XMLOutputter to clone
	 */
	public XMLOutputter(final XMLOutputter that) {
		this.userFormat = (Format) that.userFormat.clone();
		this.currentFormat = this.userFormat;
	}

	// * * * * * * * * * * Set parameters methods * * * * * * * * * *
	// * * * * * * * * * * Set parameters methods * * * * * * * * * *

	/**
	 * Sets the new format logic for the outputter. Note the Format object is
	 * cloned internally before use.
	 * 
	 * @param newFormat
	 *            the format to use for output
	 */
	public void setFormat(final Format newFormat) {
		this.userFormat = (Format) newFormat.clone();
		this.currentFormat = this.userFormat;
	}

	/**
	 * Returns the current format in use by the outputter. Note the Format
	 * object returned is a clone of the one used internally.
	 * 
	 * @return Format
	 */
	public Format getFormat() {
		return (Format) this.userFormat.clone();
	}

	// * * * * * * * * * * Output to a OutputStream * * * * * * * * * *
	// * * * * * * * * * * Output to a OutputStream * * * * * * * * * *

	/**
	 * This will print the <code>Document</code> to the given output stream.
	 * The characters are printed using the encoding specified in the
	 * constructor, or a default of UTF-8.
	 * 
	 * @param doc
	 *            <code>Document</code> to format.
	 * @param out
	 *            <code>OutputStream</code> to use.
	 * @throws IOException -
	 *             if there's any problem writing.
	 */
	public void output(final Document doc, final OutputStream out)
			throws IOException {
		final Writer writer = this.makeWriter(out);
		this.output(doc, writer); // output() flushes
	}

	/**
	 * Print out the <code>{@link DocType}</code>.
	 * 
	 * @param doctype
	 *            <code>DocType</code> to output.
	 * @param out
	 *            <code>OutputStream</code> to use.
	 * @throws IOException
	 */
	public void output(final DocType doctype, final OutputStream out)
			throws IOException {
		final Writer writer = this.makeWriter(out);
		this.output(doctype, writer); // output() flushes
	}

	/**
	 * Print out an <code>{@link Element}</code>, including its
	 * <code>{@link Attribute}</code>s, and all contained (child) elements,
	 * etc.
	 * 
	 * @param element
	 *            <code>Element</code> to output.
	 * @param out
	 *            <code>Writer</code> to use.
	 * @throws IOException
	 */
	public void output(final Element element, final OutputStream out)
			throws IOException {
		final Writer writer = this.makeWriter(out);
		this.output(element, writer); // output() flushes
	}

	/**
	 * This will handle printing out an <code>{@link
	 * Element}</code>'s
	 * content only, not including its tag, and attributes. This can be useful
	 * for printing the content of an element that contains HTML, like
	 * "&lt;description&gt;JDOM is &lt;b&gt;fun&gt;!&lt;/description&gt;".
	 * 
	 * @param element
	 *            <code>Element</code> to output.
	 * @param out
	 *            <code>OutputStream</code> to use.
	 * @throws IOException
	 */
	public void outputElementContent(final Element element,
			final OutputStream out) throws IOException {
		final Writer writer = this.makeWriter(out);
		this.outputElementContent(element, writer); // output() flushes
	}

	/**
	 * This will handle printing out a list of nodes. This can be useful for
	 * printing the content of an element that contains HTML, like
	 * "&lt;description&gt;JDOM is &lt;b&gt;fun&gt;!&lt;/description&gt;".
	 * 
	 * @param list
	 *            <code>List</code> of nodes.
	 * @param out
	 *            <code>OutputStream</code> to use.
	 * @throws IOException
	 */
	public void output(final List<?> list, final OutputStream out)
			throws IOException {
		final Writer writer = this.makeWriter(out);
		this.output(list, writer); // output() flushes
	}

	/**
	 * Print out a <code>{@link CDATA}</code> node.
	 * 
	 * @param cdata
	 *            <code>CDATA</code> to output.
	 * @param out
	 *            <code>OutputStream</code> to use.
	 * @throws IOException
	 */
	public void output(final CDATA cdata, final OutputStream out)
			throws IOException {
		final Writer writer = this.makeWriter(out);
		this.output(cdata, writer); // output() flushes
	}

	/**
	 * Print out a <code>{@link Text}</code> node. Perfoms the necessary
	 * entity escaping and whitespace stripping.
	 * 
	 * @param text
	 *            <code>Text</code> to output.
	 * @param out
	 *            <code>OutputStream</code> to use.
	 * @throws IOException
	 */
	public void output(final Text text, final OutputStream out)
			throws IOException {
		final Writer writer = this.makeWriter(out);
		this.output(text, writer); // output() flushes
	}

	/**
	 * Print out a <code>{@link Comment}</code>.
	 * 
	 * @param comment
	 *            <code>Comment</code> to output.
	 * @param out
	 *            <code>OutputStream</code> to use.
	 * @throws IOException
	 */
	public void output(final Comment comment, final OutputStream out)
			throws IOException {
		final Writer writer = this.makeWriter(out);
		this.output(comment, writer); // output() flushes
	}

	/**
	 * Print out a <code>{@link ProcessingInstruction}</code>.
	 * 
	 * @param pi
	 *            <code>ProcessingInstruction</code> to output.
	 * @param out
	 *            <code>OutputStream</code> to use.
	 * @throws IOException
	 */
	public void output(final ProcessingInstruction pi, final OutputStream out)
			throws IOException {
		final Writer writer = this.makeWriter(out);
		this.output(pi, writer); // output() flushes
	}

	/**
	 * Print out a <code>{@link EntityRef}</code>.
	 * 
	 * @param entity
	 *            <code>EntityRef</code> to output.
	 * @param out
	 *            <code>OutputStream</code> to use.
	 * @throws IOException
	 */
	public void output(final EntityRef entity, final OutputStream out)
			throws IOException {
		final Writer writer = this.makeWriter(out);
		this.output(entity, writer); // output() flushes
	}

	/**
	 * Get an OutputStreamWriter, using prefered encoding (see
	 * {@link Format#setEncoding}).
	 * 
	 * @param out
	 * @return Writer
	 * @throws java.io.UnsupportedEncodingException
	 */
	private Writer makeWriter(final OutputStream out)
			throws java.io.UnsupportedEncodingException {
		return makeWriter(out, this.userFormat.encoding);
	}

	/**
	 * Get an OutputStreamWriter, use specified encoding.
	 * 
	 * @param out
	 * @param enc
	 * @return Writer
	 * @throws java.io.UnsupportedEncodingException
	 */
	private static Writer makeWriter(final OutputStream out, String enc)
			throws java.io.UnsupportedEncodingException {
		// "UTF-8" is not recognized before JDK 1.1.6, so we'll translate
		// into "UTF8" which works with all JDKs.
		if ("UTF-8".equals(enc)) {
			enc = "UTF8";
		}

		final Writer writer = new BufferedWriter((new OutputStreamWriter(
				new BufferedOutputStream(out), enc)));
		return writer;
	}

	// * * * * * * * * * * Output to a Writer * * * * * * * * * *
	// * * * * * * * * * * Output to a Writer * * * * * * * * * *

	/**
	 * This will print the <code>Document</code> to the given Writer.
	 * 
	 * <p>
	 * Warning: using your own Writer may cause the outputter's preferred
	 * character encoding to be ignored. If you use encodings other than UTF-8,
	 * we recommend using the method that takes an OutputStream instead.
	 * </p>
	 * 
	 * @param doc
	 *            <code>Document</code> to format.
	 * @param out
	 *            <code>Writer</code> to use.
	 * @throws IOException -
	 *             if there's any problem writing.
	 */
	public void output(final Document doc, final Writer out) throws IOException {

		this.printDeclaration(out, doc, this.userFormat.encoding);

		// Print out root element, as well as any root level
		// comments and processing instructions,
		// starting with no indentation
		final List<?> content = doc.getContent();
		final int size = content.size();
		for (int i = 0; i < size; i++) {
			final Object obj = content.get(i);

			if (obj instanceof Element) {
				this.printElement(out, doc.getRootElement(), 0, this
						.createNamespaceStack());
			} else if (obj instanceof Comment) {
				this.printComment(out, (Comment) obj);
			} else if (obj instanceof ProcessingInstruction) {
				this.printProcessingInstruction(out,
						(ProcessingInstruction) obj);
			} else if (obj instanceof DocType) {
				this.printDocType(out, doc.getDocType());
				// Always print line separator after declaration, helps the
				// output look better and is semantically inconsequential
				out.write(this.currentFormat.lineSeparator);
			} else {
				// if we get here then we have a illegal content, for
				// now we'll just ignore it
			}

			this.newline(out);
			this.indent(out, 0);
		}

		// Output final line separator
		// We output this no matter what the newline flags say
		out.write(this.currentFormat.lineSeparator);

		out.flush();
	}

	/**
	 * Print out the <code>{@link DocType}</code>.
	 * 
	 * @param doctype
	 *            <code>DocType</code> to output.
	 * @param out
	 *            <code>Writer</code> to use.
	 * @throws IOException
	 */
	public void output(final DocType doctype, final Writer out)
			throws IOException {
		this.printDocType(out, doctype);
		out.flush();
	}

	/**
	 * Print out an <code>{@link Element}</code>, including its
	 * <code>{@link Attribute}</code>s, and all contained (child) elements,
	 * etc.
	 * 
	 * @param element
	 *            <code>Element</code> to output.
	 * @param out
	 *            <code>Writer</code> to use.
	 * @throws IOException
	 */
	public void output(final Element element, final Writer out)
			throws IOException {
		// If this is the root element we could pre-initialize the
		// namespace stack with the namespaces
		this.printElement(out, element, 0, this.createNamespaceStack());
		out.flush();
	}

	/**
	 * This will handle printing out an <code>{@link
	 * Element}</code>'s
	 * content only, not including its tag, and attributes. This can be useful
	 * for printing the content of an element that contains HTML, like
	 * "&lt;description&gt;JDOM is &lt;b&gt;fun&gt;!&lt;/description&gt;".
	 * 
	 * @param element
	 *            <code>Element</code> to output.
	 * @param out
	 *            <code>Writer</code> to use.
	 * @throws IOException
	 */
	public void outputElementContent(final Element element, final Writer out)
			throws IOException {
		final List<?> content = element.getContent();
		this.printContentRange(out, content, 0, content.size(), 0, this
				.createNamespaceStack());
		out.flush();
	}

	/**
	 * This will handle printing out a list of nodes. This can be useful for
	 * printing the content of an element that contains HTML, like
	 * "&lt;description&gt;JDOM is &lt;b&gt;fun&gt;!&lt;/description&gt;".
	 * 
	 * @param list
	 *            <code>List</code> of nodes.
	 * @param out
	 *            <code>Writer</code> to use.
	 * @throws IOException
	 */
	public void output(final List<?> list, final Writer out) throws IOException {
		this.printContentRange(out, list, 0, list.size(), 0, this
				.createNamespaceStack());
		out.flush();
	}

	/**
	 * Print out a <code>{@link CDATA}</code> node.
	 * 
	 * @param cdata
	 *            <code>CDATA</code> to output.
	 * @param out
	 *            <code>Writer</code> to use.
	 * @throws IOException
	 */
	public void output(final CDATA cdata, final Writer out) throws IOException {
		this.printCDATA(out, cdata);
		out.flush();
	}

	/**
	 * Print out a <code>{@link Text}</code> node. Perfoms the necessary
	 * entity escaping and whitespace stripping.
	 * 
	 * @param text
	 *            <code>Text</code> to output.
	 * @param out
	 *            <code>Writer</code> to use.
	 * @throws IOException
	 */
	public void output(final Text text, final Writer out) throws IOException {
		this.printText(out, text);
		out.flush();
	}

	/**
	 * Print out a <code>{@link Comment}</code>.
	 * 
	 * @param comment
	 *            <code>Comment</code> to output.
	 * @param out
	 *            <code>Writer</code> to use.
	 * @throws IOException
	 */
	public void output(final Comment comment, final Writer out)
			throws IOException {
		this.printComment(out, comment);
		out.flush();
	}

	/**
	 * Print out a <code>{@link ProcessingInstruction}</code>.
	 * 
	 * @param pi
	 *            <code>ProcessingInstruction</code> to output.
	 * @param out
	 *            <code>Writer</code> to use.
	 * @throws IOException
	 */
	public void output(final ProcessingInstruction pi, final Writer out)
			throws IOException {
		final boolean currentEscapingPolicy = this.currentFormat.ignoreTrAXEscapingPIs;

		// Output PI verbatim, disregarding TrAX escaping PIs.
		this.currentFormat.setIgnoreTrAXEscapingPIs(true);
		this.printProcessingInstruction(out, pi);
		this.currentFormat.setIgnoreTrAXEscapingPIs(currentEscapingPolicy);

		out.flush();
	}

	/**
	 * Print out a <code>{@link EntityRef}</code>.
	 * 
	 * @param entity
	 *            <code>EntityRef</code> to output.
	 * @param out
	 *            <code>Writer</code> to use.
	 * @throws IOException
	 */
	public void output(final EntityRef entity, final Writer out)
			throws IOException {
		this.printEntityRef(out, entity);
		out.flush();
	}

	// * * * * * * * * * * Output to a String * * * * * * * * * *
	// * * * * * * * * * * Output to a String * * * * * * * * * *

	/**
	 * Return a string representing a document. Uses an internal StringWriter.
	 * Warning: a String is Unicode, which may not match the outputter's
	 * specified encoding.
	 * 
	 * @param doc
	 *            <code>Document</code> to format.
	 * @return String
	 */
	public String outputString(final Document doc) {
		final StringWriter out = new StringWriter();
		try {
			this.output(doc, out); // output() flushes
		} catch (final IOException e) {
			//
		}
		return out.toString();
	}

	/**
	 * Return a string representing a DocType. Warning: a String is Unicode,
	 * which may not match the outputter's specified encoding.
	 * 
	 * @param doctype
	 *            <code>DocType</code> to format.
	 * @return String
	 */
	public String outputString(final DocType doctype) {
		final StringWriter out = new StringWriter();
		try {
			this.output(doctype, out); // output() flushes
		} catch (final IOException e) {
			//
		}
		return out.toString();
	}

	/**
	 * Return a string representing an element. Warning: a String is Unicode,
	 * which may not match the outputter's specified encoding.
	 * 
	 * @param element
	 *            <code>Element</code> to format.
	 * @return String
	 */
	public String outputString(final Element element) {
		final StringWriter out = new StringWriter();
		try {
			this.output(element, out); // output() flushes
		} catch (final IOException e) {
			//
		}
		return out.toString();
	}

	/**
	 * Return a string representing a list of nodes. The list is assumed to
	 * contain legal JDOM nodes.
	 * 
	 * @param list
	 *            <code>List</code> to format.
	 * @return String
	 */
	public String outputString(final List<?> list) {
		final StringWriter out = new StringWriter();
		try {
			this.output(list, out); // output() flushes
		} catch (final IOException e) {
			//
		}
		return out.toString();
	}

	/**
	 * Return a string representing a CDATA node. Warning: a String is Unicode,
	 * which may not match the outputter's specified encoding.
	 * 
	 * @param cdata
	 *            <code>CDATA</code> to format.
	 * @return String
	 */
	public String outputString(final CDATA cdata) {
		final StringWriter out = new StringWriter();
		try {
			this.output(cdata, out); // output() flushes
		} catch (final IOException e) {
			//
		}
		return out.toString();
	}

	/**
	 * Return a string representing a Text node. Warning: a String is Unicode,
	 * which may not match the outputter's specified encoding.
	 * 
	 * @param text
	 *            <code>Text</code> to format.
	 * @return String
	 */
	public String outputString(final Text text) {
		final StringWriter out = new StringWriter();
		try {
			this.output(text, out); // output() flushes
		} catch (final IOException e) {
			//
		}
		return out.toString();
	}

	/**
	 * Return a string representing a comment. Warning: a String is Unicode,
	 * which may not match the outputter's specified encoding.
	 * 
	 * @param comment
	 *            <code>Comment</code> to format.
	 * @return String
	 */
	public String outputString(final Comment comment) {
		final StringWriter out = new StringWriter();
		try {
			this.output(comment, out); // output() flushes
		} catch (final IOException e) {
			//
		}
		return out.toString();
	}

	/**
	 * Return a string representing a PI. Warning: a String is Unicode, which
	 * may not match the outputter's specified encoding.
	 * 
	 * @param pi
	 *            <code>ProcessingInstruction</code> to format.
	 * @return String
	 */
	public String outputString(final ProcessingInstruction pi) {
		final StringWriter out = new StringWriter();
		try {
			this.output(pi, out); // output() flushes
		} catch (final IOException e) {
			//
		}
		return out.toString();
	}

	/**
	 * Return a string representing an entity. Warning: a String is Unicode,
	 * which may not match the outputter's specified encoding.
	 * 
	 * @param entity
	 *            <code>EntityRef</code> to format.
	 * @return String
	 */
	public String outputString(final EntityRef entity) {
		final StringWriter out = new StringWriter();
		try {
			this.output(entity, out); // output() flushes
		} catch (final IOException e) {
			//
		}
		return out.toString();
	}

	// * * * * * * * * * * Internal printing methods * * * * * * * * * *
	// * * * * * * * * * * Internal printing methods * * * * * * * * * *

	/**
	 * This will handle printing of the declaration. Assumes XML version 1.0
	 * since we don't directly know.
	 * 
	 * @param doc
	 *            <code>Document</code> whose declaration to write.
	 * @param out
	 *            <code>Writer</code> to use.
	 * @param encoding
	 *            The encoding to add to the declaration
	 * @throws IOException
	 */
	protected void printDeclaration(final Writer out,
			@SuppressWarnings("unused")
			final Document doc, final String encoding) throws IOException {

		// Only print the declaration if it's not being omitted
		if (!this.userFormat.omitDeclaration) {
			// Assume 1.0 version
			out.write("<?xml version=\"1.0\"");
			if (!this.userFormat.omitEncoding) {
				out.write(" encoding=\"" + encoding + "\"");
			}
			out.write("?>");

			// Print new line after decl always, even if no other new lines
			// Helps the output look better and is semantically
			// inconsequential
			out.write(this.currentFormat.lineSeparator);
		}
	}

	/**
	 * This handle printing the DOCTYPE declaration if one exists.
	 * 
	 * @param docType
	 *            <code>Document</code> whose declaration to write.
	 * @param out
	 *            <code>Writer</code> to use.
	 * @throws IOException
	 */
	protected void printDocType(final Writer out, final DocType docType)
			throws IOException {

		final String publicID = docType.getPublicID();
		final String systemID = docType.getSystemID();
		final String internalSubset = docType.getInternalSubset();
		boolean hasPublic = false;

		out.write("<!DOCTYPE ");
		out.write(docType.getElementName());
		if (publicID != null) {
			out.write(" PUBLIC \"");
			out.write(publicID);
			out.write("\"");
			hasPublic = true;
		}
		if (systemID != null) {
			if (!hasPublic) {
				out.write(" SYSTEM");
			}
			out.write(" \"");
			out.write(systemID);
			out.write("\"");
		}
		if ((internalSubset != null) && (!internalSubset.equals(""))) {
			out.write(" [");
			out.write(this.currentFormat.lineSeparator);
			out.write(docType.getInternalSubset());
			out.write("]");
		}
		out.write(">");
	}

	/**
	 * This will handle printing of comments.
	 * 
	 * @param comment
	 *            <code>Comment</code> to write.
	 * @param out
	 *            <code>Writer</code> to use.
	 * @throws IOException
	 */
	protected void printComment(final Writer out, final Comment comment)
			throws IOException {
		out.write("<!--");
		out.write(comment.getText());
		out.write("-->");
	}

	/**
	 * This will handle printing of processing instructions.
	 * 
	 * @param pi
	 *            <code>ProcessingInstruction</code> to write.
	 * @param out
	 *            <code>Writer</code> to use.
	 * @throws IOException
	 */
	protected void printProcessingInstruction(final Writer out,
			final ProcessingInstruction pi) throws IOException {
		final String target = pi.getTarget();
		boolean piProcessed = false;

		if (this.currentFormat.ignoreTrAXEscapingPIs == false) {
			if (target.equals(Result.PI_DISABLE_OUTPUT_ESCAPING)) {
				this.escapeOutput = false;
				piProcessed = true;
			} else if (target.equals(Result.PI_ENABLE_OUTPUT_ESCAPING)) {
				this.escapeOutput = true;
				piProcessed = true;
			}
		}
		if (piProcessed == false) {
			final String rawData = pi.getData();

			// Write <?target data?> or if no data then just <?target?>
			if (!"".equals(rawData)) {
				out.write("<?");
				out.write(target);
				out.write(" ");
				out.write(rawData);
				out.write("?>");
			} else {
				out.write("<?");
				out.write(target);
				out.write("?>");
			}
		}
	}

	/**
	 * This will handle printing a <code>{@link EntityRef}</code>. Only the
	 * entity reference such as <code>&amp;entity;</code> will be printed.
	 * However, subclasses are free to override this method to print the
	 * contents of the entity instead.
	 * 
	 * @param entity
	 *            <code>EntityRef</code> to output.
	 * @param out
	 *            <code>Writer</code> to use.
	 * @throws IOException
	 */
	protected void printEntityRef(final Writer out, final EntityRef entity)
			throws IOException {
		out.write("&");
		out.write(entity.getName());
		out.write(";");
	}

	/**
	 * This will handle printing of <code>{@link CDATA}</code> text.
	 * 
	 * @param cdata
	 *            <code>CDATA</code> to output.
	 * @param out
	 *            <code>Writer</code> to use.
	 * @throws IOException
	 */
	protected void printCDATA(final Writer out, final CDATA cdata)
			throws IOException {
		final String str = (this.currentFormat.mode == Format.TextMode.NORMALIZE) ? cdata
				.getTextNormalize()
				: ((this.currentFormat.mode == Format.TextMode.TRIM) ? cdata
						.getText().trim() : cdata.getText());
		out.write("<![CDATA[");
		out.write(str);
		out.write("]]>");
	}

	/**
	 * This will handle printing of <code>{@link Text}</code> strings.
	 * 
	 * @param text
	 *            <code>Text</code> to write.
	 * @param out
	 *            <code>Writer</code> to use.
	 * @throws IOException
	 */
	protected void printText(final Writer out, final Text text)
			throws IOException {
		final String str = (this.currentFormat.mode == Format.TextMode.NORMALIZE) ? text
				.getTextNormalize()
				: ((this.currentFormat.mode == Format.TextMode.TRIM) ? text
						.getText().trim() : text.getText());
		out.write(this.escapeElementEntities(str));
	}

	/**
	 * This will handle printing a string. Escapes the element entities, trims
	 * interior whitespace, etc. if necessary.
	 * 
	 * @param out
	 * @param str
	 * 
	 * @throws IOException
	 */
	private void printString(final Writer out, String str) throws IOException {
		if (this.currentFormat.mode == Format.TextMode.NORMALIZE) {
			str = Text.normalizeString(str);
		} else if (this.currentFormat.mode == Format.TextMode.TRIM) {
			str = str.trim();
		}
		out.write(this.escapeElementEntities(str));
	}

	/**
	 * This will handle printing of a <code>{@link Element}</code>, its
	 * <code>{@link Attribute}</code>s, and all contained (child) elements,
	 * etc.
	 * 
	 * @param element
	 *            <code>Element</code> to output.
	 * @param out
	 *            <code>Writer</code> to use.
	 * @param level
	 *            <code>int</code> level of indention.
	 * @param namespaces
	 *            <code>List</code> stack of Namespaces in scope.
	 * @throws IOException
	 */
	protected void printElement(final Writer out, final Element element,
			final int level, final NamespaceStack namespaces)
			throws IOException {

		final List<?> attributes = element.getAttributes();
		final List<?> content = element.getContent();

		// Check for xml:space and adjust format settings
		String space = null;
		if (attributes != null) {
			space = element.getAttributeValue("space", Namespace.XML_NAMESPACE);
		}

		final Format previousFormat = this.currentFormat;

		if ("default".equals(space)) {
			this.currentFormat = this.userFormat;
		} else if ("preserve".equals(space)) {
			this.currentFormat = preserveFormat;
		}

		// Print the beginning of the tag plus attributes and any
		// necessary namespace declarations
		out.write("<");
		this.printQualifiedName(out, element);

		// Mark our namespace starting point
		final int previouslyDeclaredNamespaces = namespaces.size();

		// Print the element's namespace, if appropriate
		this.printElementNamespace(out, element, namespaces);

		// Print out additional namespace declarations
		this.printAdditionalNamespaces(out, element, namespaces);

		// Print out attributes
		if (attributes != null) {
			this.printAttributes(out, attributes, element, namespaces);
		}

		// Depending on the settings (newlines, textNormalize, etc), we may
		// or may not want to print all of the content, so determine the
		// index of the start of the content we're interested
		// in based on the current settings.

		final int start = this.skipLeadingWhite(content, 0);
		final int size = content.size();
		if (start >= size) {
			// Case content is empty or all insignificant whitespace
			if (this.currentFormat.expandEmptyElements) {
				out.write("></");
				this.printQualifiedName(out, element);
				out.write(">");
			} else {
				out.write(" />");
			}
		} else {
			out.write(">");

			// For a special case where the content is only CDATA
			// or Text we don't want to indent after the start or
			// before the end tag.

			if (nextNonText(content, start) < size) {
				// Case Mixed Content - normal indentation
				this.newline(out);
				this.printContentRange(out, content, start, size, level + 1,
						namespaces);
				this.newline(out);
				this.indent(out, level);
			} else {
				// Case all CDATA or Text - no indentation
				this.printTextRange(out, content, start, size);
			}
			out.write("</");
			this.printQualifiedName(out, element);
			out.write(">");
		}

		// remove declared namespaces from stack
		while (namespaces.size() > previouslyDeclaredNamespaces) {
			namespaces.pop();
		}

		// Restore our format settings
		this.currentFormat = previousFormat;
	}

	/**
	 * This will handle printing of content within a given range. The range to
	 * print is specified in typical Java fashion; the starting index is
	 * inclusive, while the ending index is exclusive.
	 * 
	 * @param content
	 *            <code>List</code> of content to output
	 * @param start
	 *            index of first content node (inclusive.
	 * @param end
	 *            index of last content node (exclusive).
	 * @param out
	 *            <code>Writer</code> to use.
	 * @param level
	 *            <code>int</code> level of indentation.
	 * @param namespaces
	 *            <code>List</code> stack of Namespaces in scope.
	 * @throws IOException
	 */
	private void printContentRange(final Writer out, final List<?> content,
			final int start, final int end, final int level,
			final NamespaceStack namespaces) throws IOException {
		boolean firstNode; // Flag for 1st node in content
		Object next; // Node we're about to print
		int first, index; // Indexes into the list of content

		index = start;
		while (index < end) {
			firstNode = (index == start) ? true : false;
			next = content.get(index);

			//
			// Handle consecutive CDATA, Text, and EntityRef nodes all at once
			//
			if ((next instanceof Text) || (next instanceof EntityRef)) {
				first = this.skipLeadingWhite(content, index);
				// Set index to next node for loop
				index = nextNonText(content, first);

				// If it's not all whitespace - print it!
				if (first < index) {
					if (!firstNode) {
						this.newline(out);
					}
					this.indent(out, level);
					this.printTextRange(out, content, first, index);
				}
				continue;
			}

			//
			// Handle other nodes
			//
			if (!firstNode) {
				this.newline(out);
			}

			this.indent(out, level);

			if (next instanceof Comment) {
				this.printComment(out, (Comment) next);
			} else if (next instanceof Element) {
				this.printElement(out, (Element) next, level, namespaces);
			} else if (next instanceof ProcessingInstruction) {
				this.printProcessingInstruction(out,
						(ProcessingInstruction) next);
			} else {
				// if we get here then we have a illegal content, for
				// now we'll just ignore it (probably should throw
				// a exception)
			}

			index++;
		} /* while */
	}

	/**
	 * This will handle printing of a sequence of <code>{@link CDATA}</code>
	 * or <code>{@link Text}</code> nodes. It is an error to have any other
	 * pass this method any other type of node.
	 * 
	 * @param content
	 *            <code>List</code> of content to output
	 * @param start
	 *            index of first content node (inclusive).
	 * @param end
	 *            index of last content node (exclusive).
	 * @param out
	 *            <code>Writer</code> to use.
	 * @throws IOException
	 */
	private void printTextRange(final Writer out, final List<?> content,
			int start, int end) throws IOException {
		String previous; // Previous text printed
		Object node; // Next node to print
		String next; // Next text to print

		previous = null;

		// Remove leading whitespace-only nodes
		start = this.skipLeadingWhite(content, start);

		final int size = content.size();
		if (start < size) {
			// And remove trialing whitespace-only nodes
			end = this.skipTrailingWhite(content, end);

			for (int i = start; i < end; i++) {
				node = content.get(i);

				// Get the unmangled version of the text
				// we are about to print
				if (node instanceof Text) {
					next = ((Text) node).getText();
				} else if (node instanceof EntityRef) {
					next = "&" + ((EntityRef) node).getValue() + ";";
				} else {
					throw new IllegalStateException("Should see only "
							+ "CDATA, Text, or EntityRef");
				}

				// This may save a little time
				if (next == null || "".equals(next)) {
					continue;
				}

				// Determine if we need to pad the output (padding is
				// only need in trim or normalizing mode)
				if (previous != null) { // Not 1st node
					if (this.currentFormat.mode == Format.TextMode.NORMALIZE
							|| this.currentFormat.mode == Format.TextMode.TRIM) {
						if ((this.endsWithWhite(previous))
								|| (this.startsWithWhite(next))) {
							out.write(" ");
						}
					}
				}

				// Print the node
				if (node instanceof CDATA) {
					this.printCDATA(out, (CDATA) node);
				} else if (node instanceof EntityRef) {
					this.printEntityRef(out, (EntityRef) node);
				} else {
					this.printString(out, next);
				}

				previous = next;
			}
		}
	}

	/**
	 * This will handle printing of any needed <code>{@link Namespace}</code>
	 * declarations.
	 * 
	 * @param ns
	 *            <code>Namespace</code> to print definition of
	 * @param out
	 *            <code>Writer</code> to use.
	 * @param namespaces
	 * @throws IOException
	 */
	private void printNamespace(final Writer out, final Namespace ns,
			final NamespaceStack namespaces) throws IOException {
		final String prefix = ns.getPrefix();
		final String uri = ns.getURI();

		// Already printed namespace decl?
		if (uri.equals(namespaces.getURI(prefix))) {
			return;
		}

		out.write(" xmlns");
		if (!prefix.equals("")) {
			out.write(":");
			out.write(prefix);
		}
		out.write("=\"");
		out.write(uri);
		out.write("\"");
		namespaces.push(ns);
	}

	/**
	 * This will handle printing of a <code>{@link Attribute}</code> list.
	 * 
	 * @param attributes
	 *            <code>List</code> of Attribute objcts
	 * @param out
	 *            <code>Writer</code> to use
	 * @param parent
	 * @param namespaces
	 * @throws IOException
	 */
	protected void printAttributes(final Writer out, final List<?> attributes,
			@SuppressWarnings("unused")
			final Element parent, final NamespaceStack namespaces)
			throws IOException {

		// I do not yet handle the case where the same prefix maps to
		// two different URIs. For attributes on the same element
		// this is illegal; but as yet we don't throw an exception
		// if someone tries to do this
		// Set prefixes = new HashSet();
		for (int i = 0; i < attributes.size(); i++) {
			final Attribute attribute = (Attribute) attributes.get(i);
			final Namespace ns = attribute.getNamespace();
			if ((ns != Namespace.NO_NAMESPACE)
					&& (ns != Namespace.XML_NAMESPACE)) {
				this.printNamespace(out, ns, namespaces);
			}

			out.write(" ");
			this.printQualifiedName(out, attribute);
			out.write("=");

			out.write("\"");
			out.write(this.escapeAttributeEntities(attribute.getValue()));
			out.write("\"");
		}
	}

	/**
	 * @param out
	 * @param element
	 * @param namespaces
	 * @throws IOException
	 */
	private void printElementNamespace(final Writer out, final Element element,
			final NamespaceStack namespaces) throws IOException {
		// Add namespace decl only if it's not the XML namespace and it's
		// not the NO_NAMESPACE with the prefix "" not yet mapped
		// (we do output xmlns="" if the "" prefix was already used and we
		// need to reclaim it for the NO_NAMESPACE)
		final Namespace ns = element.getNamespace();
		if (ns == Namespace.XML_NAMESPACE) {
			return;
		}
		if (!((ns == Namespace.NO_NAMESPACE) && (namespaces.getURI("") == null))) {
			this.printNamespace(out, ns, namespaces);
		}
	}

	/**
	 * @param out
	 * @param element
	 * @param namespaces
	 * @throws IOException
	 */
	private void printAdditionalNamespaces(final Writer out,
			final Element element, final NamespaceStack namespaces)
			throws IOException {
		final List<?> list = element.getAdditionalNamespaces();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				final Namespace additional = (Namespace) list.get(i);
				this.printNamespace(out, additional, namespaces);
			}
		}
	}

	// * * * * * * * * * * Support methods * * * * * * * * * *
	// * * * * * * * * * * Support methods * * * * * * * * * *

	/**
	 * This will print a new line only if the newlines flag was set to true.
	 * 
	 * @param out
	 *            <code>Writer</code> to use
	 * @throws IOException
	 */
	private void newline(final Writer out) throws IOException {
		if (this.currentFormat.indent != null) {
			out.write(this.currentFormat.lineSeparator);
		}
	}

	/**
	 * This will print indents (only if the newlines flag was set to
	 * <code>true</code>, and indent is non-null).
	 * 
	 * @param out
	 *            <code>Writer</code> to use
	 * @param level
	 *            current indent level (number of tabs)
	 * @throws IOException
	 */
	private void indent(final Writer out, final int level) throws IOException {
		if (this.currentFormat.indent == null
				|| this.currentFormat.indent.equals("")) {
			return;
		}

		for (int i = 0; i < level; i++) {
			out.write(this.currentFormat.indent);
		}
	}

	// Returns the index of the first non-all-whitespace CDATA or Text,
	// index = content.size() is returned if content contains
	// all whitespace.
	// @param start index to begin search (inclusive)
	/**
	 * @param content
	 * @param start
	 * @return int
	 */
	private int skipLeadingWhite(final List<?> content, int start) {
		if (start < 0) {
			start = 0;
		}

		int index = start;
		final int size = content.size();
		if (this.currentFormat.mode == Format.TextMode.TRIM_FULL_WHITE
				|| this.currentFormat.mode == Format.TextMode.NORMALIZE
				|| this.currentFormat.mode == Format.TextMode.TRIM) {
			while (index < size) {
				if (!this.isAllWhitespace(content.get(index))) {
					return index;
				}
				index++;
			}
		}
		return index;
	}

	// Return the index + 1 of the last non-all-whitespace CDATA or
	// Text node, index < 0 is returned
	// if content contains all whitespace.
	// @param start index to begin search (exclusive)
	/**
	 * @param content
	 * @param start
	 * @return int
	 */
	private int skipTrailingWhite(final List<?> content, int start) {
		final int size = content.size();
		if (start > size) {
			start = size;
		}

		int index = start;
		if (this.currentFormat.mode == Format.TextMode.TRIM_FULL_WHITE
				|| this.currentFormat.mode == Format.TextMode.NORMALIZE
				|| this.currentFormat.mode == Format.TextMode.TRIM) {
			while (index >= 0) {
				if (!this.isAllWhitespace(content.get(index - 1))) {
					break;
				}
				--index;
			}
		}
		return index;
	}

	// Return the next non-CDATA, non-Text, or non-EntityRef node,
	// index = content.size() is returned if there is no more non-CDATA,
	// non-Text, or non-EntiryRef nodes
	// @param start index to begin search (inclusive)
	/**
	 * @param content
	 * @param start
	 * @return int
	 */
	private static int nextNonText(final List<?> content, int start) {
		if (start < 0) {
			start = 0;
		}

		int index = start;
		final int size = content.size();
		while (index < size) {
			final Object node = content.get(index);
			if (!((node instanceof Text) || (node instanceof EntityRef))) {
				return index;
			}
			index++;
		}
		return size;
	}

	// Determine if a Object is all whitespace
	/**
	 * @param obj
	 * @return boolean
	 */
	private boolean isAllWhitespace(final Object obj) {
		String str = null;

		if (obj instanceof String) {
			str = (String) obj;
		} else if (obj instanceof Text) {
			str = ((Text) obj).getText();
		} else if (obj instanceof EntityRef) {
			return false;
		} else {
			return false;
		}

		for (int i = 0; i < str.length(); i++) {
			if (!isWhitespace(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	// Determine if a string starts with a XML whitespace.
	/**
	 * @param str
	 * @return boolean
	 */
	private boolean startsWithWhite(final String str) {
		if ((str != null) && (str.length() > 0) && isWhitespace(str.charAt(0))) {
			return true;
		}
		return false;
	}

	// Determine if a string ends with a XML whitespace.
	/**
	 * @param str
	 * @return boolean
	 */
	private boolean endsWithWhite(final String str) {
		if ((str != null) && (str.length() > 0)
				&& isWhitespace(str.charAt(str.length() - 1))) {
			return true;
		}
		return false;
	}

	// Determine if a character is a XML whitespace.
	// should this method be in Verifier
	/**
	 * @param c
	 * @return boolean
	 */
	private static boolean isWhitespace(final char c) {
		if (c == ' ' || c == '\n' || c == '\t' || c == '\r') {
			return true;
		}
		return false;
	}

	/**
	 * This will take the pre-defined entities in XML 1.0 and convert their
	 * character representation to the appropriate entity reference, suitable
	 * for XML attributes. It does not convert the single quote (') because it's
	 * not necessary as the outputter writes attributes surrounded by
	 * double-quotes.
	 * 
	 * @param str
	 *            <code>String</code> input to escape.
	 * @return <code>String</code> with escaped content.
	 */
	public String escapeAttributeEntities(final String str) {
		StringBuffer buffer;
		char ch;
		String entity;
		final EscapeStrategy strategy = this.currentFormat.escapeStrategy;

		buffer = null;
		for (int i = 0; i < str.length(); i++) {
			ch = str.charAt(i);
			switch (ch) {
			case '<':
				entity = "&lt;";
				break;
			case '>':
				entity = "&gt;";
				break;
			/*
			 * case '\'' : entity = "&apos;"; break;
			 */
			case '\"':
				entity = "&quot;";
				break;
			case '&':
				entity = "&amp;";
				break;
			case '\r':
				entity = "&#xD;";
				break;
			case '\t':
				entity = "&#x9;";
				break;
			case '\n':
				entity = "&#xA;";
				break;
			default:
				if (strategy.shouldEscape(ch)) {
					entity = "&#x" + Integer.toHexString(ch) + ";";
				} else {
					entity = null;
				}
				break;
			}
			if (buffer == null) {
				if (entity != null) {
					// An entity occurred, so we'll have to use StringBuffer
					// (allocate room for it plus a few more entities).
					buffer = new StringBuffer(str.length() + 20);
					// Copy previous skipped characters and fall through
					// to pickup current character
					buffer.append(str.substring(0, i));
					buffer.append(entity);
				}
			} else {
				if (entity == null) {
					buffer.append(ch);
				} else {
					buffer.append(entity);
				}
			}
		}

		// If there were any entities, return the escaped characters
		// that we put in the StringBuffer. Otherwise, just return
		// the unmodified input string.
		return (buffer == null) ? str : buffer.toString();
	}

	/**
	 * This will take the three pre-defined entities in XML 1.0 (used
	 * specifically in XML elements) and convert their character representation
	 * to the appropriate entity reference, suitable for XML element content.
	 * 
	 * @param str
	 *            <code>String</code> input to escape.
	 * @return <code>String</code> with escaped content.
	 */
	public String escapeElementEntities(final String str) {
		if (this.escapeOutput == false) {
			return str;
		}

		StringBuffer buffer;
		char ch;
		String entity;
		final EscapeStrategy strategy = this.currentFormat.escapeStrategy;

		buffer = null;
		for (int i = 0; i < str.length(); i++) {
			ch = str.charAt(i);
			switch (ch) {
			case '<':
				entity = "&lt;";
				break;
			case '>':
				entity = "&gt;";
				break;
			case '&':
				entity = "&amp;";
				break;
			case '\r':
				entity = "&#xD;";
				break;
			case '\n':
				entity = this.currentFormat.lineSeparator;
				break;
			default:
				if (strategy.shouldEscape(ch)) {
					entity = "&#x" + Integer.toHexString(ch) + ";";
				} else {
					entity = null;
				}
				break;
			}
			if (buffer == null) {
				if (entity != null) {
					// An entity occurred, so we'll have to use StringBuffer
					// (allocate room for it plus a few more entities).
					buffer = new StringBuffer(str.length() + 20);
					// Copy previous skipped characters and fall through
					// to pickup current character
					buffer.append(str.substring(0, i));
					buffer.append(entity);
				}
			} else {
				if (entity == null) {
					buffer.append(ch);
				} else {
					buffer.append(entity);
				}
			}
		}

		// If there were any entities, return the escaped characters
		// that we put in the StringBuffer. Otherwise, just return
		// the unmodified input string.
		return (buffer == null) ? str : buffer.toString();
	}

	/**
	 * Returns a copy of this XMLOutputter.
	 * 
	 * @return Object
	 */
	@Override
	public Object clone() {
		// Implementation notes: Since all state of an XMLOutputter is
		// embodied in simple private instance variables, Object.clone
		// can be used. Note that since Object.clone is totally
		// broken, we must catch an exception that will never be
		// thrown.
		try {
			return super.clone();
		} catch (final java.lang.CloneNotSupportedException e) {
			// even though this should never ever happen, it's still
			// possible to fool Java into throwing a
			// CloneNotSupportedException. If that happens, we
			// shouldn't swallow it.
			throw new RuntimeException(e.toString());
		}
	}

	/**
	 * Return a string listing of the settings for this XMLOutputter instance.
	 * 
	 * @return a string listing the settings for this XMLOutputter instance
	 */
	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < this.userFormat.lineSeparator.length(); i++) {
			final char ch = this.userFormat.lineSeparator.charAt(i);
			switch (ch) {
			case '\r':
				buffer.append("\\r");
				break;
			case '\n':
				buffer.append("\\n");
				break;
			case '\t':
				buffer.append("\\t");
				break;
			default:
				buffer.append("[" + ((int) ch) + "]");
				break;
			}
		}

		return ("XMLOutputter[omitDeclaration = "
				+ this.userFormat.omitDeclaration + ", " + "encoding = "
				+ this.userFormat.encoding + ", " + "omitEncoding = "
				+ this.userFormat.omitEncoding + ", " + "indent = '"
				+ this.userFormat.indent + "'" + ", "
				+ "expandEmptyElements = "
				+ this.userFormat.expandEmptyElements + ", "
				+ "lineSeparator = '" + buffer.toString() + "', "
				+ "textMode = " + this.userFormat.mode + "]");
	}

	/**
	 * Factory for making new NamespaceStack objects. The NamespaceStack created
	 * is actually an inner class extending the package protected
	 * NamespaceStack, as a way to make NamespaceStack "friendly" toward
	 * subclassers.
	 * 
	 * @return NamespaceStack
	 */
	private NamespaceStack createNamespaceStack() {
		// actually returns a XMLOutputter.NamespaceStack (see below)
		return new NamespaceStack();
	}

	/**
	 * Our own null subclass of NamespaceStack. This plays a little trick with
	 * Java access protection. We want subclasses of XMLOutputter to be able to
	 * override protected methods that declare a NamespaceStack parameter, but
	 * we don't want to declare the parent NamespaceStack class as public.
	 */
	protected class NamespaceStack extends jdom.output.NamespaceStack {
		//
	}

	// Support method to print a name without using elt.getQualifiedName()
	// and thus avoiding a StringBuffer creation and memory churn
	/**
	 * @param out
	 * @param e
	 * @throws IOException
	 */
	private void printQualifiedName(final Writer out, final Element e)
			throws IOException {
		if (e.getNamespace().getPrefix().length() == 0) {
			out.write(e.getName());
		} else {
			out.write(e.getNamespace().getPrefix());
			out.write(':');
			out.write(e.getName());
		}
	}

	// Support method to print a name without using att.getQualifiedName()
	// and thus avoiding a StringBuffer creation and memory churn
	/**
	 * @param out
	 * @param a
	 * @throws IOException
	 */
	private void printQualifiedName(final Writer out, final Attribute a)
			throws IOException {
		final String prefix = a.getNamespace().getPrefix();
		if ((prefix != null) && (!prefix.equals(""))) {
			out.write(prefix);
			out.write(':');
			out.write(a.getName());
		} else {
			out.write(a.getName());
		}
	}

	// * * * * * * * * * * Deprecated methods * * * * * * * * * *

	/*
	 * The methods below here are deprecations of protected methods. We don't
	 * usually deprecate protected methods, so they're commented out. They're
	 * left here in case this mass deprecation causes people trouble. Since
	 * we're getting close to 1.0 it's actually better for people to raise
	 * issues early though.
	 */

}
