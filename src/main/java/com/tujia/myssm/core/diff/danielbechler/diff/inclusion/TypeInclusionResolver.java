/*
 * Copyright 2014 Daniel Bechler
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

package com.tujia.myssm.core.diff.danielbechler.diff.inclusion;

import java.util.HashMap;
import java.util.Map;
import com.tujia.myssm.core.diff.danielbechler.diff.node.DiffNode;

class TypeInclusionResolver implements InclusionResolver {
    private final Map<Class<?>, Inclusion> typeInclusions = new HashMap<Class<?>, Inclusion>();
    private boolean containsIncluded;
    private boolean containsExcluded;

    public Inclusion getInclusion(final DiffNode node) {
        if (isInactive()) {
            return Inclusion.DEFAULT;
        }
        final Class<?> valueType = node.getValueType();
        if (valueType != null && typeInclusions.containsKey(valueType)) {
            final Inclusion inclusion = typeInclusions.get(valueType);
            if (inclusion == Inclusion.INCLUDED || inclusion == Inclusion.EXCLUDED) {
                return inclusion;
            }
        }
        return Inclusion.DEFAULT;
    }

    private boolean isInactive() {
        return !(containsIncluded || containsExcluded);
    }

    public boolean enablesStrictIncludeMode() {
        return containsIncluded;
    }

    void setInclusion(final Class<?> type, final Inclusion inclusion) {
        typeInclusions.put(type, inclusion);
        containsIncluded = typeInclusions.containsValue(Inclusion.INCLUDED);
        containsExcluded = typeInclusions.containsValue(Inclusion.EXCLUDED);
    }
}
