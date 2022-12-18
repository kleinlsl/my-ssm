/*
 * Copyright 2012 Daniel Bechler
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tujia.myssm.core.diff.danielbechler.diff.access;

import com.tujia.myssm.core.diff.danielbechler.diff.selector.ElementSelector;
import com.tujia.myssm.core.diff.danielbechler.diff.selector.RootElementSelector;

public final class RootAccessor implements Accessor {
    private static final RootAccessor instance = new RootAccessor();

    private RootAccessor() {
    }

    public static RootAccessor getInstance() {
        return instance;
    }

    public Object get(final Object target) {
        return target;
    }

    public void set(final Object target, final Object value) {
        throw new UnsupportedOperationException();
    }

    public void unset(final Object target) {
        throw new UnsupportedOperationException();
    }

    public ElementSelector getElementSelector() {
        return RootElementSelector.getInstance();
    }

    @Override
    public String toString() {
        return "root element";
    }
}
