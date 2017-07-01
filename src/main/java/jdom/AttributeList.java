/*--

 $Id: AttributeList.java,v 1.23 2004/02/28 03:30:27 jhunter Exp $

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

import java.io.Serializable;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * <code>AttributeList</code> represents legal JDOM <code>Attribute</code>
 * content. This class is NOT PUBLIC; users should see it as a simple List
 * implementation.
 * 
 * @author Alex Rosen
 * @author Philippe Riand
 * @author Bradley S. Huffman
 * @version $Revision: 1.23 $, $Date: 2004/02/28 03:30:27 $
 * @see CDATA
 * @see Comment
 * @see Element
 * @see EntityRef
 * @see ProcessingInstruction
 * @see Text
 */
@SuppressWarnings("unchecked")
class AttributeList extends AbstractList implements List, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private static final int INITIAL_ARRAY_SIZE = 5;

	/** The backing list */
	private Attribute elementData[];

	/**
	 * 
	 */
	private int size;

	/** The parent Element */
	private Element parent;

	/** Force an Element parent */
	@SuppressWarnings("unused")
	private AttributeList() {
		//
	}

	/**
	 * Create a new instance of the AttributeList representing Element content
	 * 
	 * @param parent
	 *            element whose attributes are to be held
	 */
	AttributeList(final Element parent) {
		this.parent = parent;
	}

	/**
	 * Package internal method to support building from sources that are 100%
	 * trusted.
	 * 
	 * @param a
	 *            attribute to add without any checks
	 */
	final void uncheckedAddAttribute(final Attribute a) {
		a.parent = this.parent;
		this.ensureCapacity(this.size + 1);
		this.elementData[this.size++] = a;
		this.modCount++;
	}

	/**
	 * Add a attribute to the end of the list or replace a existing attribute
	 * with the same name and <code>Namespace</code>.
	 * 
	 * @param obj
	 *            The object to insert into the list.
	 * @return true (as per the general contract of Collection.add).
	 * @throws IndexOutOfBoundsException
	 *             if index < 0 || index > size()
	 */
	@Override
	public boolean add(final Object obj) {
		if (obj instanceof Attribute) {
			final Attribute attribute = (Attribute) obj;
			final int duplicate = this.indexOfDuplicate(attribute);
			if (duplicate < 0) {
				this.add(this.size(), attribute);
			} else {
				this.set(duplicate, attribute);
			}
		} else if (obj == null) {
			throw new IllegalAddException("Cannot add null attribute");
		} else {
			throw new IllegalAddException("Class " + obj.getClass().getName()
					+ " is not an attribute");
		}
		return true;
	}

	/**
	 * Inserts the specified attribute at the specified position in this list.
	 * Shifts the attribute currently at that position (if any) and any
	 * subsequent attributes to the right (adds one to their indices).
	 * 
	 * @param index
	 *            The location to set the value to.
	 * @param obj
	 *            The object to insert into the list. throws
	 *            IndexOutOfBoundsException if index < 0 || index > size()
	 */
	@Override
	public void add(final int index, final Object obj) {
		if (obj instanceof Attribute) {
			final Attribute attribute = (Attribute) obj;
			final int duplicate = this.indexOfDuplicate(attribute);
			if (duplicate >= 0) {
				throw new IllegalAddException("Cannot add duplicate attribute");
			}
			this.add(index, attribute);
		} else if (obj == null) {
			throw new IllegalAddException("Cannot add null attribute");
		} else {
			throw new IllegalAddException("Class " + obj.getClass().getName()
					+ " is not an attribute");
		}
		this.modCount++;
	}

	/**
	 * Check and add the <code>Attribute</code> to this list at the given
	 * index. Note: does not check for duplicate attributes.
	 * 
	 * @param index
	 *            index where to add <code>Attribute</code>
	 * @param attribute
	 *            <code>Attribute</code> to add
	 */
	void add(final int index, final Attribute attribute) {
		if (attribute.getParent() != null) {
			throw new IllegalAddException(
					"The attribute already has an existing parent \""
							+ attribute.getParent().getQualifiedName() + "\"");
		}

		final String reason = Verifier.checkNamespaceCollision(attribute,
				this.parent);
		if (reason != null) {
			throw new IllegalAddException(this.parent, attribute, reason);
		}

		if (index < 0 || index > this.size) {
			throw new IndexOutOfBoundsException("Index: " + index + " Size: "
					+ this.size());
		}

		attribute.setParent(this.parent);

		this.ensureCapacity(this.size + 1);
		if (index == this.size) {
			this.elementData[this.size++] = attribute;
		} else {
			System.arraycopy(this.elementData, index, this.elementData,
					index + 1, this.size - index);
			this.elementData[index] = attribute;
			this.size++;
		}
		this.modCount++;
	}

	/**
	 * Add all the objects in the specified collection.
	 * 
	 * @param collection
	 *            The collection containing all the objects to add.
	 * @return <code>true</code> if the list was modified as a result of the
	 *         add.
	 */
	@Override
	public boolean addAll(final Collection collection) {
		return this.addAll(this.size(), collection);
	}

	/**
	 * Inserts the specified collecton at the specified position in this list.
	 * Shifts the attribute currently at that position (if any) and any
	 * subsequent attributes to the right (adds one to their indices).
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
				final Attribute attribute = this.elementData[i];
				attribute.setParent(null);
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
		final Attribute[] old = this.elementData;
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
				final Attribute attribute = old[i];
				attribute.setParent(null);
			}
		}
		this.modCount++;
	}

	/**
	 * Increases the capacity of this <code>AttributeList</code> instance, if
	 * necessary, to ensure that it can hold at least the number of items
	 * specified by the minimum capacity argument.
	 * 
	 * @param minCapacity
	 *            the desired minimum capacity.
	 */
	private void ensureCapacity(final int minCapacity) {
		if (this.elementData == null) {
			this.elementData = new Attribute[Math.max(minCapacity,
					INITIAL_ARRAY_SIZE)];
		} else {
			final int oldCapacity = this.elementData.length;
			if (minCapacity > oldCapacity) {
				final Attribute oldData[] = this.elementData;
				int newCapacity = (oldCapacity * 3) / 2 + 1;
				if (newCapacity < minCapacity) {
					newCapacity = minCapacity;
				}
				this.elementData = new Attribute[newCapacity];
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
	 * Return the <code>Attribute</code> with the given name and
	 * <code>Namespace</code>.
	 * 
	 * @param name
	 *            name of attribute to return
	 * @param namespace
	 *            <code>Namespace</code> to match
	 * @return the <code>Attribute</code>, or null if one doesn't exist.
	 */
	Object get(final String name, final Namespace namespace) {
		final int index = this.indexOf(name, namespace);
		if (index < 0) {
			return null;
		}
		return this.elementData[index];
	}

	/**
	 * Return index of the <code>Attribute</code> with the given name and uri.
	 * 
	 * @param name
	 * @param namespace
	 * @return int
	 */
	int indexOf(final String name, final Namespace namespace) {
		final String uri = namespace.getURI();
		if (this.elementData != null) {
			for (int i = 0; i < this.size; i++) {
				final Attribute old = this.elementData[i];
				final String oldURI = old.getNamespaceURI();
				final String oldName = old.getName();
				if (oldURI.equals(uri) && oldName.equals(name)) {
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

		final Attribute old = this.elementData[index];
		old.setParent(null);
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
	 * Remove the <code>Attribute</code> with the given name and
	 * <code>Namespace</code>.
	 * 
	 * @param name
	 * @param namespace
	 *            <code>Namespace</code> to match
	 * @return the <code>true</code> if attribute was removed,
	 *         <code>false</code> otherwise
	 */
	boolean remove(final String name, final Namespace namespace) {
		final int index = this.indexOf(name, namespace);
		if (index < 0) {
			return false;
		}
		this.remove(index);
		return true;
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
		if (obj instanceof Attribute) {
			final Attribute attribute = (Attribute) obj;
			final int duplicate = this.indexOfDuplicate(attribute);
			if ((duplicate >= 0) && (duplicate != index)) {
				throw new IllegalAddException("Cannot set duplicate attribute");
			}
			return this.set(index, attribute);
		} else if (obj == null) {
			throw new IllegalAddException("Cannot add null attribute");
		} else {
			throw new IllegalAddException("Class " + obj.getClass().getName()
					+ " is not an attribute");
		}
	}

	/**
	 * Set the object at the specified location to the supplied object. Note:
	 * does not check for duplicate attributes.
	 * 
	 * @param index
	 *            The location to set the value to.
	 * @param attribute
	 *            The attribute to set.
	 * @return The object which was replaced. throws IndexOutOfBoundsException
	 *         if index < 0 || index >= size()
	 */
	Object set(final int index, final Attribute attribute) {
		if (index < 0 || index >= this.size) {
			throw new IndexOutOfBoundsException("Index: " + index + " Size: "
					+ this.size());
		}

		if (attribute.getParent() != null) {
			throw new IllegalAddException(
					"The attribute already has an existing parent \""
							+ attribute.getParent().getQualifiedName() + "\"");
		}

		final String reason = Verifier.checkNamespaceCollision(attribute,
				this.parent);
		if (reason != null) {
			throw new IllegalAddException(this.parent, attribute, reason);
		}

		final Attribute old = this.elementData[index];
		old.setParent(null);

		this.elementData[index] = attribute;
		attribute.setParent(this.parent);
		return old;
	}

	/**
	 * Return index of attribute with same name and Namespace, or -1 if one
	 * doesn't exist
	 * 
	 * @param attribute
	 * @return int
	 */
	private int indexOfDuplicate(final Attribute attribute) {
		int duplicate = -1;
		final String name = attribute.getName();
		final Namespace namespace = attribute.getNamespace();
		duplicate = this.indexOf(name, namespace);
		return duplicate;
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
	 * @return String
	 */
	@Override
	public String toString() {
		return super.toString();
	}
}
