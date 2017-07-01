/*--

 $Id: TextBuffer.java,v 1.8 2004/02/06 09:28:31 jhunter Exp $

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

/**
 * A non-public utility class similar to StringBuffer but optimized for XML
 * parsing where the common case is that you get only one chunk of characters
 * per text section. TextBuffer stores the first chunk of characters in a
 * String, which can just be returned directly if no second chunk is received.
 * Subsequent chunks are stored in a supplemental char array (like StringBuffer
 * uses). In this case, the returned text will be the first String chunk,
 * concatenated with the subsequent chunks stored in the char array. This
 * provides optimal performance in the common case, while still providing very
 * good performance in the uncommon case. Furthermore, avoiding StringBuffer
 * means that no extra unused char array space will be kept around after parsing
 * is through.
 * 
 * @version $Revision: 1.8 $, $Date: 2004/02/06 09:28:31 $
 * @author Bradley S. Huffman
 * @author Alex Rosen
 */
class TextBuffer {

	/**
	 * The first part of the text value (the "prefix"). If null, the text value
	 * is the empty string.
	 */
	private String prefixString;

	/**
	 * The rest of the text value (the "suffix"). Only the first code>arraySize</code>
	 * characters are valid.
	 */
	private char[] array;

	/**
	 * The size of the rest of the text value. If zero, then only
	 * code>prefixString</code> contains the text value.
	 */
	private int arraySize;

	/** Constructor */
	TextBuffer() {
		this.array = new char[4096]; // initial capacity
		this.arraySize = 0;
	}

	/**
	 * Append the specified text to the text value of this buffer.
	 * 
	 * @param source
	 * @param start
	 * @param count
	 */
	void append(final char[] source, final int start, final int count) {
		if (this.prefixString == null) {
			// This is the first chunk, so we'll store it in the prefix string
			this.prefixString = new String(source, start, count);
		} else {
			// This is a subsequent chunk, so we'll add it to the char array
			this.ensureCapacity(this.arraySize + count);
			System.arraycopy(source, start, this.array, this.arraySize, count);
			this.arraySize += count;
		}
	}

	/**
	 * Returns the size of the text value.
	 * 
	 * @return int
	 */
	int size() {
		if (this.prefixString == null) {
			return 0;
		}
		return this.prefixString.length() + this.arraySize;
	}

	/** Clears the text value and prepares the TextBuffer for reuse. */
	void clear() {
		this.arraySize = 0;
		this.prefixString = null;
	}

	/**
	 * Returns the text value stored in the buffer.
	 * 
	 * @return String
	 */
	@Override
	public String toString() {
		if (this.prefixString == null) {
			return "";
		}

		String str = "";
		if (this.arraySize == 0) {
			// Char array is empty, so the text value is just prefixString.
			str = this.prefixString;
		} else {
			// Char array is not empty, so the text value is prefixString
			// plus the char array.
			str = new StringBuffer(this.prefixString.length() + this.arraySize)
					.append(this.prefixString).append(this.array, 0,
							this.arraySize).toString();
		}
		return str;
	}

	// Ensure that the char array has room for at least "csize" characters.
	/**
	 * @param csize
	 */
	private void ensureCapacity(final int csize) {
		final int capacity = this.array.length;
		if (csize > capacity) {
			final char[] old = this.array;
			int nsize = capacity;
			while (csize > nsize) {
				nsize += (capacity / 2);
			}
			this.array = new char[nsize];
			System.arraycopy(old, 0, this.array, 0, this.arraySize);
		}
	}
}
