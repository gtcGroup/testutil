/*
 * [Licensed per the Open Source "MIT License".]
 * 
 * Copyright (c) 1999 - 2008 by
 * Global Technology Consulting Group, Inc. at
 * http://gtcGroup.com
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.gtcgroup.testutil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Random;

/**
 * <p>
 * This class provides verification support (typically) when an interface is
 * used within accessor method signatures.
 * </p>
 * 
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 1999 - 2008 by Global Technology Consulting Group, Inc. at <a
 * href="http://gtcGroup.com">gtcGroup.com </a>.
 * </p>
 * 
 * MarvinToll@gtcGroup.com
 * 
 * @since v. 1.1
 */

class TuDynamicProxy implements InvocationHandler {

	/**
	 * Simply a <code>new Object()</code> placeholder.
	 */
	private final Object objectWrapped;

	/**
	 * A random integer assigned to this instance and used for display if
	 * verification failed.
	 */
	private final Integer uniqueInteger;

	/**
	 * Returns a new <code>TuDynamicProxy</code> instance.
	 * 
	 * @param anInterface
	 *            The interface requiring a concrete test instance.
	 * @return Object - A new <code>TuDynamicProxy</code> instance.
	 */
	static Object newInstance(final Class<?> anInterface) {

		// Instantiate a wrappable object.
		final Object objectWrapped = new Object();

		// Invokes the private constructor.
		return Proxy.newProxyInstance(anInterface.getClassLoader(),
				new Class[] { anInterface }, new TuDynamicProxy(objectWrapped));
	}

	/**
	 * Constructor (private)
	 * 
	 * @param objectWrapped
	 */
	private TuDynamicProxy(final Object objectWrapped) {
		super();

		this.objectWrapped = objectWrapped;

		// Assign a unique int.
		final Random random = new Random();
		this.uniqueInteger = new Integer(random.nextInt());
	}

	/**
	 * Normally processes a method invocation on a proxy instance and returns
	 * the result. However, in this case the method is being used to intercept a
	 * <code>toString()</code> invocation. Any other invocation will return a
	 * null.
	 * 
	 * @param proxy
	 * @param method
	 * @param args
	 * @return Object - Random integer representation or null.
	 * @throws Throwable
	 */
	public Object invoke(final Object proxy, final Method method,
			final Object[] args) throws Throwable {

		// Check for invocation of toString().
		if ("toString".equalsIgnoreCase(method.getName())) {

			// Return a toString() message.
			return this.uniqueInteger.toString();
		}

		// Object result = method.invoke(proxy, args);
		proxy.getClass();
		args.getClass();

		return null;
	}

	/**
	 * Returns the object wrapped.
	 * 
	 * @return Object - The object wrapped.
	 */
	Object getObjectWrapped() {
		return this.objectWrapped;
	}
}