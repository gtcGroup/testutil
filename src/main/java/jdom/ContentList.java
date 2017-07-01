/*--

 $Id: ContentList.java,v 1.39 2004/02/28 03:30:27 jhunter Exp $

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
 from the JDOM Project Management <request_AT_jdom_DOT_org).

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

import java.io.Serializable;
import java.util.AbstractList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import jdom.filter.Filter;

/**
 * A non-public list implementation holding only legal JDOM content, including
 * content for Document or Element nodes. Users see this class as a simple List
 * implementation.
 * 
 * @see CDATA
 * @see Comment
 * @see Element
 * @see EntityRef
 * @see ProcessingInstruction
 * @see Text
 * 
 * @version $Revision: 1.39 $, $Date: 2004/02/28 03:30:27 $
 * @author Alex Rosen
 * @author Philippe Riand
 * @author Bradley S. Huffman
 */
@SuppressWarnings("unchecked")
final class ContentList extends AbstractList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private static final int INITIAL_ARRAY_SIZE = 5;

	/**
	 * Used inner class FilterListIterator to help hasNext and hasPrevious the
	 * next index of our cursor (must be here for JDK1.1).
	 */
	private static final int CREATE = 0;

	/**
	 * 
	 */
	private static final int HASPREV = 1;

	/**
	 * 
	 */
	private static final int HASNEXT = 2;

	/**
	 * 
	 */
	private static final int PREV = 3;

	/**
	 * 
	 */
	private static final int NEXT = 4;

	/**
	 * 
	 */
	private static final int ADD = 5;

	/**
	 * 
	 */
	private static final int REMOVE = 6;

	/** Our backing list */
	Content elementData[];

	/**
	 * 
	 */
	int size;

	/** Document or Element this list belongs to */
	private final Parent parent;

	/**
	 * Force either a Document or Element parent
	 * 
	 * @param parent
	 */
	ContentList(final Parent parent) {
		this.parent = parent;
	}

	/**
	 * Package internal method to support building from sources that are 100%
	 * trusted.
	 * 
	 * @param c
	 *            content to add without any checks
	 */
	final void uncheckedAddContent(final Content c) {
		c.parent = this.parent;
		this.ensureCapacity(this.size + 1);
		this.elementData[this.size++] = c;
		this.modCount++;
	}

	/**
	 * Inserts the specified object at the specified position in this list.
	 * Shifts the object currently at that position (if any) and any subsequent
	 * objects to the right (adds one to their indices).
	 * 
	 * @param index
	 *            The location to set the value to.
	 * @param obj
	 *            The object to insert into the list. throws
	 *            IndexOutOfBoundsException if index < 0 || index > size()
	 */
	@Override
	public void add(final int index, final Object obj) {
		if (obj == null) {
			throw new IllegalAddException("Cannot add null object");
		}
		if ((obj instanceof Content)) {
			this.add(index, (Content) obj);
		} else {
			throw new IllegalAddException("Class " + obj.getClass().getName()
					+ " is of unrecognized type and cannot be added");
		}
	}

	/**
	 * @param index
	 * @param child
	 * @throws IllegalAddException
	 * @see jdom.ContentList#add(int, jdom.Content)
	 */
	private void documentCanContain(final int index, final Content child)
			throws IllegalAddException {
		if (child instanceof Element) {
			if (this.indexOfFirstElement() >= 0) {
				throw new IllegalAddException(
						"Cannot add a second root element, only one is allowed");
			}
			if (this.indexOfDocType() > index) {
				throw new IllegalAddException(
						"A root element cannot be added before the DocType");
			}
		}
		if (child instanceof DocType) {
			if (this.indexOfDocType() >= 0) {
				throw new IllegalAddException(
						"Cannot add a second doctype, only one is allowed");
			}
			final int firstElt = this.indexOfFirstElement();
			if (firstElt != -1 && firstElt < index) {
				throw new IllegalAddException(
						"A DocType cannot be added after the root element");
			}
		}
		if (child instanceof CDATA) {
			throw new IllegalAddException(
					"A CDATA is not allowed at the document root");
		}

		if (child instanceof Text) {
			throw new IllegalAddException(
					"A Text is not allowed at the document root");
		}

		if (child instanceof EntityRef) {
			throw new IllegalAddException(
					"An EntityRef is not allowed at the document root");
		}
	}

	/**
	 * @param index
	 * @param child
	 * @throws IllegalAddException
	 */
	private static void elementCanContain(final int index, final Content child)
			throws IllegalAddException {
		if (child instanceof DocType) {
			throw new IllegalAddException(
					"A DocType is not allowed except at the document level ["
							+ index + "].");
		}
	}

	/**
	 * Check and add the <code>Element</code> to this list at the given index.
	 * 
	 * @param index
	 *            index where to add <code>Element</code>
	 * @param child
	 *            <code>Element</code> to add
	 */
	void add(final int index, final Content child) {
		if (child == null) {
			throw new IllegalAddException("Cannot add null object");
		}
		if (this.parent instanceof Document) {
			this.documentCanContain(index, child);
		} else {
			elementCanContain(index, child);
		}

		if (child.getParent() != null) {
			final Parent p = child.getParent();
			if (p instanceof Document) {
				throw new IllegalAddException((Element) child,
						"The Content already has an existing parent document");
			}
			throw new IllegalAddException(
					"The Content already has an existing parent \""
							+ ((Element) p).getQualifiedName() + "\"");
		}

		if (child == this.parent) {
			throw new IllegalAddException(
					"The Element cannot be added to itself");
		}

		// Detect if we have <a><b><c/></b></a> and c.add(a)
		if ((this.parent instanceof Element && child instanceof Element)
				&& ((Element) child).isAncestor((Element) this.parent)) {
			throw new IllegalAddException(
					"The Element cannot be added as a descendent of itself");
		}

		if (index < 0 || index > this.size) {
			throw new IndexOutOfBoundsException("Index: " + index + " Size: "
					+ this.size());
		}

		child.setParent(this.parent);

		this.ensureCapacity(this.size + 1);
		if (index == this.size) {
			this.elementData[this.size++] = child;
		} else {
			System.arraycopy(this.elementData, index, this.elementData,
					index + 1, this.size - index);
			this.elementData[index] = child;
			this.size++;
		}
		this.modCount++;
	}

	/**
	 * Add the specified collecton to the end of this list.
	 * 
	 * @param collection
	 *            The collection to add to the list.
	 * @return <code>true</code> if the list was modified as a result of the
	 *         add.
	 */
	@Override
	public boolean addAll(final Collection collection) {
		return this.addAll(this.size(), collection);
	}

	/**
	 * Inserts the specified collecton at the specified position in this list.
	 * Shifts the object currently at that position (if any) and any subsequent
	 * objects to the right (adds one to their indices).
	 * 
	 * @param index
	 *            The offset to start adding the data in the collection
	 * @param collection
	 *            The collection to insert into the list.
	 * @return <code>true</code> if the list was modified as a result of the
	 *         add. throws IndexOutOfBoundsException if index < 0 || index >
	 *         size()
	 */
	@Override
	public boolean addAll(final int index, final Collection collection) {
		if (index < 0 || index > this.size) {
			throw new IndexOutOfBoundsException("Index: " + index + " Size: "
					+ this.size());
		}

		if ((collection == null) || (collection.size() == 0)) {
			return false;
		}
		this.ensureCapacity(this.size() + collection.size());

		int count = 0;
		try {
			final Iterator i = collection.iterator();
			while (i.hasNext()) {
				final Object obj = i.next();
				this.add(index + count, obj);
				count++;
			}
		} catch (final RuntimeException exception) {
			for (int i = 0; i < count; i++) {
				this.remove(index);
			}
			throw exception;
		}

		return true;
	}

	/**
	 * Clear the current list.
	 */
	@Override
	public void clear() {
		if (this.elementData != null) {
			for (int i = 0; i < this.size; i++) {
				final Content obj = this.elementData[i];
				removeParent(obj);
			}
			this.elementData = null;
			this.size = 0;
		}
		this.modCount++;
	}

	/**
	 * Clear the current list and set it to the contents of the
	 * <code>Collection</code>. object.
	 * 
	 * @param collection
	 *            The collection to use.
	 */
	void clearAndSet(final Collection collection) {
		final Content[] old = this.elementData;
		final int oldSize = this.size;

		this.elementData = null;
		this.size = 0;

		if ((collection != null) && (collection.size() != 0)) {
			this.ensureCapacity(collection.size());
			try {
				this.addAll(0, collection);
			} catch (final RuntimeException exception) {
				this.elementData = old;
				this.size = oldSize;
				throw exception;
			}
		}

		if (old != null) {
			for (int i = 0; i < oldSize; i++) {
				removeParent(old[i]);
			}
		}
		this.modCount++;
	}

	/**
	 * Increases the capacity of this <code>ContentList</code> instance, if
	 * necessary, to ensure that it can hold at least the number of items
	 * specified by the minimum capacity argument.
	 * 
	 * @param minCapacity
	 *            the desired minimum capacity.
	 */
	void ensureCapacity(final int minCapacity) {
		if (this.elementData == null) {
			this.elementData = new Content[Math.max(minCapacity,
					INITIAL_ARRAY_SIZE)];
		} else {
			final int oldCapacity = this.elementData.length;
			if (minCapacity > oldCapacity) {
				final Object oldData[] = this.elementData;
				int newCapacity = (oldCapacity * 3) / 2 + 1;
				if (newCapacity < minCapacity) {
					newCapacity = minCapacity;
				}
				this.elementData = new Content[newCapacity];
				System.arraycopy(oldData, 0, this.elementData, 0, this.size);
			}
		}
	}

	/**
	 * Return the object at the specified offset.
	 * 
	 * @param index
	 *            The offset of the object.
	 * @return The Object which was returned.
	 */
	@Override
	public Object get(final int index) {
		if (index < 0 || index >= this.size) {
			throw new IndexOutOfBoundsException("Index: " + index + " Size: "
					+ this.size());
		}
		return this.elementData[index];
	}

	/**
	 * Return a view of this list based on the given filter.
	 * 
	 * @param filter
	 *            <code>Filter</code> for this view.
	 * @return a list representing the rules of the <code>Filter</code>.
	 */
	List getView(final Filter filter) {
		return new FilterList(filter);
	}

	/**
	 * Return the index of the first Element in the list. If the parent is a
	 * <code>Document</code> then the element is the root element. If the list
	 * contains no Elements, it returns -1.
	 * 
	 * @return index of first element, or -1 if one doesn't exist
	 */
	int indexOfFirstElement() {
		if (this.elementData != null) {
			for (int i = 0; i < this.size; i++) {
				if (this.elementData[i] instanceof Element) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * Return the index of the DocType element in the list. If the list contains
	 * no DocType, it returns -1.
	 * 
	 * @return index of the DocType, or -1 if it doesn't exist
	 */
	int indexOfDocType() {
		if (this.elementData != null) {
			for (int i = 0; i < this.size; i++) {
				if (this.elementData[i] instanceof DocType) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * Remove the object at the specified offset.
	 * 
	 * @param index
	 *            The offset of the object.
	 * @return The Object which was removed.
	 */
	@Override
	public Object remove(final int index) {
		if (index < 0 || index >= this.size) {
			throw new IndexOutOfBoundsException("Index: " + index + " Size: "
					+ this.size());
		}

		final Content old = this.elementData[index];
		removeParent(old);
		final int numMoved = this.size - index - 1;
		if (numMoved > 0) {
			System.arraycopy(this.elementData, index + 1, this.elementData,
					index, numMoved);
		}
		this.elementData[--this.size] = null; // Let gc do its work
		this.modCount++;
		return old;
	}

	/**
	 * Remove the parent of a Object
	 * 
	 * @param c
	 */
	private static void removeParent(final Content c) {
		c.setParent(null);
	}

	/**
	 * Set the object at the specified location to the supplied object.
	 * 
	 * @param index
	 *            The location to set the value to.
	 * @param obj
	 *            The location to set the value to.
	 * @return The object which was replaced. throws IndexOutOfBoundsException
	 *         if index < 0 || index >= size()
	 */
	@Override
	public Object set(final int index, final Object obj) {
		if (index < 0 || index >= this.size) {
			throw new IndexOutOfBoundsException("Index: " + index + " Size: "
					+ this.size());
		}

		if ((obj instanceof Element) && (this.parent instanceof Document)) {
			final int root = this.indexOfFirstElement();
			if ((root >= 0) && (root != index)) {
				throw new IllegalAddException(
						"Cannot add a second root element, only one is allowed");
			}
		}

		if ((obj instanceof DocType) && (this.parent instanceof Document)) {
			final int docTypeIndex = this.indexOfDocType();
			if ((docTypeIndex >= 0) && (docTypeIndex != index)) {
				throw new IllegalAddException(
						"Cannot add a second doctype, only one is allowed");
			}
		}

		final Object old = this.remove(index);
		try {
			this.add(index, obj);
		} catch (final RuntimeException exception) {
			this.add(index, old);
			throw exception;
		}
		return old;
	}

	/**
	 * Return the number of items in this list
	 * 
	 * @return The number of items in this list.
	 */
	@Override
	public int size() {
		return this.size;
	}

	/**
	 * Return this list as a <code>String</code>
	 * 
	 * @return The number of items in this list.
	 */
	@Override
	public String toString() {
		return super.toString();
	}

	/**
	 * Give access of ContentList.modCount to FilterList
	 * 
	 * @return int
	 */
	int getModCount() {
		return this.modCount;
	}

	/* * * * * * * * * * * * * FilterList * * * * * * * * * * * * * * * */
	/* * * * * * * * * * * * * FilterList * * * * * * * * * * * * * * * */

	/**
	 * <code>FilterList</code> represents legal JDOM content, including
	 * content for <code>Document</code>s or <code>Element</code>s.
	 */

	class FilterList extends AbstractList implements java.io.Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/** The Filter */
		Filter filter;

		/** Current number of items in this view */
		int count = 0;

		/** Expected modCount in our backing list */
		int expected = -1;

		// Implementation Note: Directly after size() is called, expected
		// is sync'd with ContentList.modCount and count provides
		// the true size of this view. Before the first call to
		// size() or if the backing list is modified outside this
		// FilterList, both might contain bogus values and should
		// not be used without first calling size();

		/**
		 * Create a new instance of the FilterList with the specified Filter.
		 * 
		 * @param filter
		 */
		FilterList(final Filter filter) {
			this.filter = filter;
		}

		/**
		 * Inserts the specified object at the specified position in this list.
		 * Shifts the object currently at that position (if any) and any
		 * subsequent objects to the right (adds one to their indices).
		 * 
		 * @param index
		 *            The location to set the value to.
		 * @param obj
		 *            The object to insert into the list. throws
		 *            IndexOutOfBoundsException if index < 0 || index > size()
		 */
		@Override
		public void add(final int index, final Object obj) {
			if (this.filter.matches(obj)) {
				final int adjusted = this.getAdjustedIndex(index);
				ContentList.this.add(adjusted, obj);
				this.expected++;
				this.count++;
			} else {
				throw new IllegalAddException("Filter won't allow the "
						+ obj.getClass().getName() + " '" + obj
						+ "' to be added to the list");
			}
		}

		/**
		 * Return the object at the specified offset.
		 * 
		 * @param index
		 *            The offset of the object.
		 * @return The Object which was returned.
		 */
		@Override
		public Object get(final int index) {
			final int adjusted = this.getAdjustedIndex(index);
			return ContentList.this.get(adjusted);
		}

		/**
		 * @return Iterator
		 */
		@Override
		public Iterator iterator() {
			return new FilterListIterator(this.filter, 0);
		}

		/**
		 * @return ListIterator
		 */
		@Override
		public ListIterator listIterator() {
			return new FilterListIterator(this.filter, 0);
		}

		/**
		 * @param index
		 * @return ListIterator
		 */
		@Override
		public ListIterator listIterator(final int index) {
			return new FilterListIterator(this.filter, index);
		}

		/**
		 * Remove the object at the specified offset.
		 * 
		 * @param index
		 *            The offset of the object.
		 * @return The Object which was removed.
		 */
		@Override
		public Object remove(final int index) {
			final int adjusted = this.getAdjustedIndex(index);
			Object old = ContentList.this.get(adjusted);
			if (this.filter.matches(old)) {
				old = ContentList.this.remove(adjusted);
				this.expected++;
				this.count--;
			} else {
				throw new IllegalAddException("Filter won't allow the "
						+ (old.getClass()).getName() + " '" + old + "' (index "
						+ index + ") to be removed");
			}
			return old;
		}

		/**
		 * Set the object at the specified location to the supplied object.
		 * 
		 * @param index
		 *            The location to set the value to.
		 * @param obj
		 *            The location to set the value to.
		 * @return The object which was replaced. throws
		 *         IndexOutOfBoundsException if index < 0 || index >= size()
		 */
		@Override
		public Object set(final int index, final Object obj) {
			Object old = null;
			if (this.filter.matches(obj)) {
				final int adjusted = this.getAdjustedIndex(index);
				old = ContentList.this.get(adjusted);
				if (!this.filter.matches(old)) {
					throw new IllegalAddException("Filter won't allow the "
							+ (old.getClass()).getName() + " '" + old
							+ "' (index " + index + ") to be removed");
				}
				old = ContentList.this.set(adjusted, obj);
				this.expected += 2;
			} else {
				throw new IllegalAddException("Filter won't allow index "
						+ index + " to be set to " + (obj.getClass()).getName());
			}
			return old;
		}

		/**
		 * Return the number of items in this list
		 * 
		 * @return The number of items in this list.
		 */
		@Override
		public int size() {
			// Implementation Note: Directly after size() is called, expected
			// is sync'd with ContentList.modCount and count provides
			// the true size of this view. Before the first call to
			// size() or if the backing list is modified outside this
			// FilterList, both might contain bogus values and should
			// not be used without first calling size();

			if (this.expected == ContentList.this.getModCount()) {
				return this.count;
			}

			this.count = 0;
			for (int i = 0; i < ContentList.this.size(); i++) {
				final Object obj = ContentList.this.elementData[i];
				if (this.filter.matches(obj)) {
					this.count++;
				}
			}
			this.expected = ContentList.this.getModCount();
			return this.count;
		}

		/**
		 * Return the adjusted index
		 * 
		 * @param index
		 *            Index of in this view.
		 * @return True index in backing list
		 */
		final private int getAdjustedIndex(final int index) {
			int adjusted = 0;
			for (int i = 0; i < ContentList.this.size; i++) {
				final Object obj = ContentList.this.elementData[i];
				if (this.filter.matches(obj)) {
					if (index == adjusted) {
						return i;
					}
					adjusted++;
				}
			}

			if (index == adjusted) {
				return ContentList.this.size;
			}

			return ContentList.this.size + 1;
		}
	}

	/* * * * * * * * * * * * * FilterListIterator * * * * * * * * * * * */
	/* * * * * * * * * * * * * FilterListIterator * * * * * * * * * * * */

	/**
	 * @author MarvinToll
	 * 
	 */
	class FilterListIterator implements ListIterator {

		/** The Filter that applies */
		Filter filter;

		/** The last operation performed */
		int lastOperation;

		/** Initial start index in backing list */
		int initialCursor;

		/** Index in backing list of next object */
		int cursor;

		/** Index in backing list of last object returned */
		int last;

		/** Expected modCount in our backing list */
		int expected;

		/**
		 * Default constructor
		 * 
		 * @param filter
		 * @param start
		 */
		FilterListIterator(final Filter filter, final int start) {
			this.filter = filter;
			this.initialCursor = this.initializeCursor(start);
			this.last = -1;
			this.expected = ContentList.this.getModCount();
			this.lastOperation = CREATE;
		}

		/**
		 * Returns <code>true</code> if this list iterator has a next element.
		 * 
		 * @return boolean
		 */
		public boolean hasNext() {
			this.checkConcurrentModification();

			switch (this.lastOperation) {
			case CREATE:
				this.cursor = this.initialCursor;
				break;
			case PREV:
				this.cursor = this.last;
				break;
			case ADD:
			case NEXT:
				this.cursor = this.moveForward(this.last + 1);
				break;
			case REMOVE:
				this.cursor = this.moveForward(this.last);
				break;
			case HASPREV:
				this.cursor = this.moveForward(this.cursor + 1);
				break;
			case HASNEXT:
				break;
			default:
				throw new IllegalStateException("Unknown operation");
			}

			if (this.lastOperation != CREATE) {
				this.lastOperation = HASNEXT;
			}

			return (this.cursor < ContentList.this.size()) ? true : false;
		}

		/**
		 * Returns the next element in the list.
		 * 
		 * @return Object
		 */
		public Object next() {
			this.checkConcurrentModification();

			if (this.hasNext()) {
				this.last = this.cursor;
			} else {
				this.last = ContentList.this.size();
				throw new NoSuchElementException();
			}

			this.lastOperation = NEXT;
			return ContentList.this.get(this.last);
		}

		/**
		 * Returns <code>true</code> if this list iterator has more elements
		 * when traversing the list in the reverse direction.
		 * 
		 * @return boolean
		 */
		public boolean hasPrevious() {
			this.checkConcurrentModification();

			switch (this.lastOperation) {
			case CREATE:
				this.cursor = this.initialCursor;
				final int size = ContentList.this.size();
				if (this.cursor >= size) {
					this.cursor = this.moveBackward(size - 1);
				}
				break;
			case PREV:
			case REMOVE:
				this.cursor = this.moveBackward(this.last - 1);
				break;
			case HASNEXT:
				this.cursor = this.moveBackward(this.cursor - 1);
				break;
			case ADD:
			case NEXT:
				this.cursor = this.last;
				break;
			case HASPREV:
				break;
			default:
				throw new IllegalStateException("Unknown operation");
			}

			if (this.lastOperation != CREATE) {
				this.lastOperation = HASPREV;
			}

			return (this.cursor < 0) ? false : true;
		}

		/**
		 * Returns the previous element in the list.
		 * 
		 * @return Object
		 */
		public Object previous() {
			this.checkConcurrentModification();

			if (this.hasPrevious()) {
				this.last = this.cursor;
			} else {
				this.last = -1;
				throw new NoSuchElementException();
			}

			this.lastOperation = PREV;
			return ContentList.this.get(this.last);
		}

		/**
		 * Returns the index of the element that would be returned by a
		 * subsequent call to <code>next</code>.
		 * 
		 * @return int
		 */
		public int nextIndex() {
			this.checkConcurrentModification();
			this.hasNext();

			int count = 0;
			for (int i = 0; i < ContentList.this.size(); i++) {
				if (this.filter.matches(ContentList.this.get(i))) {
					if (i == this.cursor) {
						return count;
					}
					count++;
				}
			}
			this.expected = ContentList.this.getModCount();
			return count;
		}

		/**
		 * Returns the index of the element that would be returned by a
		 * subsequent call to <code>previous</code>. (Returns -1 if the list
		 * iterator is at the beginning of the list.)
		 * 
		 * @return int
		 */
		public int previousIndex() {
			this.checkConcurrentModification();

			if (this.hasPrevious()) {
				int count = 0;
				for (int i = 0; i < ContentList.this.size(); i++) {
					if (this.filter.matches(ContentList.this.get(i))) {
						if (i == this.cursor) {
							return count;
						}
						count++;
					}
				}
			}
			return -1;
		}

		/**
		 * Inserts the specified element into the list.
		 * 
		 * @param obj
		 */
		public void add(final Object obj) {
			this.checkConcurrentModification();

			if (this.filter.matches(obj)) {
				this.last = this.cursor + 1;
				ContentList.this.add(this.last, obj);
			} else {
				throw new IllegalAddException("Filter won't allow add of "
						+ (obj.getClass()).getName());
			}
			this.expected = ContentList.this.getModCount();
			this.lastOperation = ADD;
		}

		/**
		 * Removes from the list the last element that was returned by
		 * <code>next</code> or <code>previous</code>. the last call to
		 * <code>next</code> or <code>previous</code>.
		 */
		public void remove() {
			this.checkConcurrentModification();

			if ((this.last < 0) || (this.lastOperation == REMOVE)) {
				throw new IllegalStateException("no preceeding call to "
						+ "prev() or next()");
			}

			if (this.lastOperation == ADD) {
				throw new IllegalStateException("cannot call remove() "
						+ "after add()");
			}

			final Object old = ContentList.this.get(this.last);
			if (this.filter.matches(old)) {
				ContentList.this.remove(this.last);
			} else {
				throw new IllegalAddException("Filter won't allow "
						+ (old.getClass()).getName() + " (index " + this.last
						+ ") to be removed");
			}
			this.expected = ContentList.this.getModCount();
			this.lastOperation = REMOVE;
		}

		/**
		 * Replaces the last element returned by <code>next</code> or
		 * <code>previous</code> with the specified element.
		 * 
		 * @param obj
		 */
		public void set(final Object obj) {
			this.checkConcurrentModification();

			if ((this.lastOperation == ADD) || (this.lastOperation == REMOVE)) {
				throw new IllegalStateException("cannot call set() after "
						+ "add() or remove()");
			}

			if (this.last < 0) {
				throw new IllegalStateException("no preceeding call to "
						+ "prev() or next()");
			}

			if (this.filter.matches(obj)) {
				final Object old = ContentList.this.get(this.last);
				if (!this.filter.matches(old)) {
					throw new IllegalAddException("Filter won't allow "
							+ (old.getClass()).getName() + " (index "
							+ this.last + ") to be removed");
				}
				ContentList.this.set(this.last, obj);
			} else {
				throw new IllegalAddException("Filter won't allow index "
						+ this.last + " to be set to "
						+ (obj.getClass()).getName());
			}

			this.expected = ContentList.this.getModCount();
			// Don't set lastOperation
		}

		/**
		 * Returns index in the backing list by moving forward start objects
		 * that match our filter.
		 * 
		 * @param start
		 * @return int
		 */
		private int initializeCursor(final int start) {
			if (start < 0) {
				throw new IndexOutOfBoundsException("Index: " + start);
			}

			int count = 0;
			for (int i = 0; i < ContentList.this.size(); i++) {
				final Object obj = ContentList.this.get(i);
				if (this.filter.matches(obj)) {
					if (start == count) {
						return i;
					}
					count++;
				}
			}

			if (start > count) {
				throw new IndexOutOfBoundsException("Index: " + start
						+ " Size: " + count);
			}

			return ContentList.this.size();
		}

		/**
		 * Returns index in the backing list of the next object matching our
		 * filter, starting at the given index and moving forwards.
		 * 
		 * @param start
		 * @return int
		 */
		private int moveForward(int start) {
			if (start < 0) {
				start = 0;
			}
			for (int i = start; i < ContentList.this.size(); i++) {
				final Object obj = ContentList.this.get(i);
				if (this.filter.matches(obj)) {
					return i;
				}
			}
			return ContentList.this.size();
		}

		/**
		 * Returns index in the backing list of the next object matching our
		 * filter, starting at the given index and moving backwards.
		 * 
		 * @param start
		 * @return int
		 */
		private int moveBackward(int start) {
			if (start >= ContentList.this.size()) {
				start = ContentList.this.size() - 1;
			}

			for (int i = start; i >= 0; --i) {
				final Object obj = ContentList.this.get(i);
				if (this.filter.matches(obj)) {
					return i;
				}
			}
			return -1;
		}

		/**
		 * Check if are backing list is being modified by someone else.
		 */
		private void checkConcurrentModification() {
			if (this.expected != ContentList.this.getModCount()) {
				throw new ConcurrentModificationException();
			}
		}
	}
}
