/*--

 $Id: DescendantIterator.java,v 1.5 2004/02/27 11:32:57 jhunter Exp $

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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Traverse all a parent's descendants (all children at any level below the
 * parent).
 * 
 * @author Bradley S. Huffman
 * @author Jason Hunter
 * @version $Revision: 1.5 $, $Date: 2004/02/27 11:32:57 $
 */
class DescendantIterator implements Iterator<Object> {

	/** Attribute. */
	private Iterator<?> iterator;

	/** Attribute. */
	private Iterator<?> nextIterator;

	/** Attribute. */
	private final List<Iterator<?>> stack = new ArrayList<Iterator<?>>();

	/**
	 * Iterator for the descendants of the supplied object.
	 * 
	 * @param parent
	 *            document or element whose descendants will be iterated
	 */
	DescendantIterator(final Parent parent) {
		if (parent == null) {
			throw new IllegalArgumentException("parent parameter was null");
		}
		this.iterator = parent.getContent().iterator();
	}

	/**
	 * Returns true> if the iteration has more {@link Content} descendants.
	 * 
	 * @return true is the iterator has more descendants
	 */
	public boolean hasNext() {
		if (this.iterator != null && this.iterator.hasNext()) {
			return true;
		}
		if (this.nextIterator != null && this.nextIterator.hasNext()) {
			return true;
		}
		if (this.stackHasAnyNext()) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the next {@link Content} descendant.
	 * 
	 * @return the next descendant
	 */
	public Object next() {
		if (!this.hasNext()) {
			throw new NoSuchElementException();
		}

		// If we need to descend, go for it and record where we are.
		// We do the shuffle here on the next next() call so remove() is easy
		// to code up.
		if (this.nextIterator != null) {
			this.push(this.iterator);
			this.iterator = this.nextIterator;
			this.nextIterator = null;
		}

		// If this iterator is finished, try moving up the stack
		while (!this.iterator.hasNext()) {
			if (this.stack.size() > 0) {
				this.iterator = this.pop();
			} else {
				throw new NoSuchElementException("Somehow we lost our iterator");
			}
		}

		final Content child = (Content) this.iterator.next();
		if (child instanceof Element) {
			this.nextIterator = ((Element) child).getContent().iterator();
		}
		return child;
	}

	/**
	 * Detaches the last {@link jdom.Content} returned by the last call to next
	 * from it's parent. <b>Note</b>: this <b>does not</b> affect iteration
	 * and all children, siblings, and any node following the removed node (in
	 * document order) will be visited.
	 */
	public void remove() {
		this.iterator.remove();
	}

	/**
	 * @return Iterator
	 */
	private Iterator<?> pop() {
		final int stackSize = this.stack.size();
		if (stackSize == 0) {
			throw new NoSuchElementException("empty stack");
		}
		return this.stack.remove(stackSize - 1);
	}

	/**
	 * @param itr
	 */
	private void push(final Iterator<?> itr) {
		this.stack.add(itr);
	}

	/**
	 * @return boolean
	 */
	private boolean stackHasAnyNext() {
		final int size = this.stack.size();
		for (int i = 0; i < size; i++) {
			final Iterator<?> itr = this.stack.get(i);
			if (itr.hasNext()) {
				return true;
			}
		}
		return false;
	}
}
