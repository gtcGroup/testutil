/*--

 $Id: ContentFilter.java,v 1.14 2004/08/31 04:56:07 jhunter Exp $

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

package jdom.filter;

import jdom.CDATA;
import jdom.Comment;
import jdom.DocType;
import jdom.Document;
import jdom.Element;
import jdom.EntityRef;
import jdom.ProcessingInstruction;
import jdom.Text;

/**
 * A general purpose Filter able to represent all legal JDOM objects or a
 * specific subset. Filtering is accomplished by way of a filtering mask in
 * which each bit represents whether a JDOM object is visible or not. For
 * example to view all Text and CDATA nodes in the content of element x.
 * 
 * <pre><code>
 * Filter filter = new ContentFilter(ContentFilter.TEXT | ContentFilter.CDATA);
 * List content = x.getContent(filter);
 * </code></pre>
 * 
 * <p>
 * For those who don't like bit-masking, set methods are provided as an
 * alternative. For example to allow everything except Comment nodes.
 * 
 * <pre><code>
 * Filter filter = new ContentFilter();
 * filter.setCommentVisible(false);
 * List content = x.getContent(filter);
 * </code></pre>
 * 
 * <p>
 * The default is to allow all valid JDOM objects.
 * 
 * @version $Revision: 1.14 $, $Date: 2004/08/31 04:56:07 $
 * @author Bradley S. Huffman
 */
public class ContentFilter extends AbstractFilter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Mask for JDOM {@link Element} objects */
	public static final int ELEMENT = 1;

	/** Mask for JDOM {@link CDATA} objects */
	public static final int CDATA = 2;

	/** Mask for JDOM {@link Text} objects */
	public static final int TEXT = 4;

	/** Mask for JDOM {@link Comment} objects */
	public static final int COMMENT = 8;

	/** Mask for JDOM {@link ProcessingInstruction} objects */
	public static final int PI = 16;

	/** Mask for JDOM {@link EntityRef} objects */
	public static final int ENTITYREF = 32;

	/** Mask for JDOM {@link Document} object */
	public static final int DOCUMENT = 64;

	/** Mask for JDOM {@link DocType} object */
	public static final int DOCTYPE = 128;

	/** The JDOM object mask */
	private int filterMask;

	/**
	 * Default constructor that allows any legal JDOM objects.
	 */
	public ContentFilter() {
		this.setDefaultMask();
	}

	/**
	 * Set whether all JDOM objects are visible or not.
	 * 
	 * @param allVisible
	 *            <code>true</code> all JDOM objects are visible,
	 *            <code>false</code> all JDOM objects are hidden.
	 */
	public ContentFilter(final boolean allVisible) {
		if (allVisible) {
			this.setDefaultMask();
		} else {
			this.filterMask &= ~this.filterMask;
		}
	}

	/**
	 * Filter out JDOM objects according to a filtering mask.
	 * 
	 * @param mask
	 *            Mask of JDOM objects to allow.
	 */
	public ContentFilter(final int mask) {
		this.setFilterMask(mask);
	}

	/**
	 * Return current filtering mask.
	 * 
	 * @return the current filtering mask
	 */
	public int getFilterMask() {
		return this.filterMask;
	}

	/**
	 * Set filtering mask.
	 * 
	 * @param mask
	 *            the new filtering mask
	 */
	public void setFilterMask(final int mask) {
		this.setDefaultMask();
		this.filterMask &= mask;
	}

	/**
	 * Set this filter to allow all legal JDOM objects.
	 */
	public void setDefaultMask() {
		this.filterMask = ELEMENT | CDATA | TEXT | COMMENT | PI | ENTITYREF
				| DOCUMENT | DOCTYPE;
	}

	/**
	 * Set filter to match only JDOM objects that are legal document content.
	 */
	public void setDocumentContent() {
		this.filterMask = ELEMENT | COMMENT | PI | DOCTYPE;
	}

	/**
	 * Set filter to match only JDOM objects that are legal element content.
	 */
	public void setElementContent() {
		this.filterMask = ELEMENT | CDATA | TEXT | COMMENT | PI | ENTITYREF;
	}

	/**
	 * Set visiblity of <code>Element</code> objects.
	 * 
	 * @param visible
	 *            whether Elements are visible, <code>true</code> if yes,
	 *            <code>false</code> if not
	 */
	public void setElementVisible(final boolean visible) {
		if (visible) {
			this.filterMask |= ELEMENT;
		} else {
			this.filterMask &= ~ELEMENT;
		}
	}

	/**
	 * Set visiblity of <code>CDATA</code> objects.
	 * 
	 * @param visible
	 *            whether CDATA nodes are visible, <code>true</code> if yes,
	 *            <code>false</code> if not
	 */
	public void setCDATAVisible(final boolean visible) {
		if (visible) {
			this.filterMask |= CDATA;
		} else {
			this.filterMask &= ~CDATA;
		}
	}

	/**
	 * Set visiblity of <code>Text</code> objects.
	 * 
	 * @param visible
	 *            whether Text nodes are visible, <code>true</code> if yes,
	 *            <code>false</code> if not
	 */
	public void setTextVisible(final boolean visible) {
		if (visible) {
			this.filterMask |= TEXT;
		} else {
			this.filterMask &= ~TEXT;
		}
	}

	/**
	 * Set visiblity of <code>Comment</code> objects.
	 * 
	 * @param visible
	 *            whether Comments are visible, <code>true</code> if yes,
	 *            <code>false</code> if not
	 */
	public void setCommentVisible(final boolean visible) {
		if (visible) {
			this.filterMask |= COMMENT;
		} else {
			this.filterMask &= ~COMMENT;
		}
	}

	/**
	 * Set visiblity of <code>ProcessingInstruction</code> objects.
	 * 
	 * @param visible
	 *            whether ProcessingInstructions are visible, <code>true</code>
	 *            if yes, <code>false</code> if not
	 */
	public void setPIVisible(final boolean visible) {
		if (visible) {
			this.filterMask |= PI;
		} else {
			this.filterMask &= ~PI;
		}
	}

	/**
	 * Set visiblity of <code>EntityRef</code> objects.
	 * 
	 * @param visible
	 *            whether EntityRefs are visible, <code>true</code> if yes,
	 *            <code>false</code> if not
	 */
	public void setEntityRefVisible(final boolean visible) {
		if (visible) {
			this.filterMask |= ENTITYREF;
		} else {
			this.filterMask &= ~ENTITYREF;
		}
	}

	/**
	 * Set visiblity of <code>DocType</code> objects.
	 * 
	 * @param visible
	 *            whether the DocType is visible, <code>true</code> if yes,
	 *            <code>false</code> if not
	 */
	public void setDocTypeVisible(final boolean visible) {
		if (visible) {
			this.filterMask |= DOCTYPE;
		} else {
			this.filterMask &= ~DOCTYPE;
		}
	}

	/**
	 * Check to see if the object matches according to the filter mask.
	 * 
	 * @param obj
	 *            The object to verify.
	 * @return <code>true</code> if the objected matched a predfined set of
	 *         rules.
	 */
	public boolean matches(final Object obj) {
		if (obj instanceof Element) {
			return (this.filterMask & ELEMENT) != 0;
		} else if (obj instanceof CDATA) { // must come before Text check
			return (this.filterMask & CDATA) != 0;
		} else if (obj instanceof Text) {
			return (this.filterMask & TEXT) != 0;
		} else if (obj instanceof Comment) {
			return (this.filterMask & COMMENT) != 0;
		} else if (obj instanceof ProcessingInstruction) {
			return (this.filterMask & PI) != 0;
		} else if (obj instanceof EntityRef) {
			return (this.filterMask & ENTITYREF) != 0;
		} else if (obj instanceof Document) {
			return (this.filterMask & DOCUMENT) != 0;
		} else if (obj instanceof DocType) {
			return (this.filterMask & DOCTYPE) != 0;
		}

		return false;
	}

	/**
	 * Returns whether the two filters are equivalent (i&#46;e&#46; the matching
	 * mask values are identical).
	 * 
	 * @param obj
	 *            the object to compare against
	 * @return whether the two filters are equal
	 */
	@Override
	public boolean equals(final Object obj) {
		// Generated by IntelliJ
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ContentFilter)) {
			return false;
		}

		final ContentFilter filter = (ContentFilter) obj;

		if (this.filterMask != filter.filterMask) {
			return false;
		}

		return true;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		// Generated by IntelliJ
		return this.filterMask;
	}
}
