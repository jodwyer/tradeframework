/*
 * Copyright (c) 2012 Jeremy Goetsch
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jgoetsch.ib.handlers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.ib.client.EWrapper;

/**
 * DelegatingWrapper that routes all events to all handlers. It will be the responsibility of
 * each handler to act only on events which are intended for it by implementing only the
 * desired EWrapper methods and checking the request/order id if applicable.
 * 
 * @author jgoetsch
 *
 */
public class SimpleHandlerDelegatingWrapper extends HandlerDelegatingWrapper implements HandlerManager {                           

	private Set<EWrapper> handlers;
	
	public SimpleHandlerDelegatingWrapper() {
		handlers = new HashSet<EWrapper>();
	}
	
	public synchronized void addHandler(EWrapper handler) {
		handlers.add(handler);
	}
	
	public synchronized void removeHandler(EWrapper handler) {
		handlers.remove(handler);
	}
	
	@Override
	protected synchronized void callHandlers(String eventName, int objId, HandlerCallback callback) {
		for (EWrapper handler : handlers) {
			//System.out.println("event = " + eventName + ", calling handler:" + handler);
			callback.callHandler(handler);
		}
	}

	public void addHandler(String eventName, int objectId, EWrapper handler) {
		addHandler(handler);
	}

	public void addHandler(String eventName, EWrapper handler) {
		addHandler(handler);
	}

	public void addHandler(String[] eventNames, int objectId, EWrapper handler) {
		addHandler(handler);
	}

	public void addHandler(String[] eventNames, EWrapper handler) {
		addHandler(handler);
	}

	public synchronized Collection<EWrapper> getHandlers(String eventName, int objectId) {
		return new HashSet<EWrapper>(handlers);
	}

	public synchronized Collection<EWrapper> getHandlers(String eventName) {
		return new HashSet<EWrapper>(handlers);
	}

	public synchronized Collection<EWrapper> getHandlers() {
		return new HashSet<EWrapper>(handlers);
	}
}
