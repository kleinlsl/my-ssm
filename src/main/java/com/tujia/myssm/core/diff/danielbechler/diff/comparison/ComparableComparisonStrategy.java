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

package com.tujia.myssm.core.diff.danielbechler.diff.comparison;

import static com.tujia.framework.diff.danielbechler.util.Comparables.isEqualByComparison;
import com.tujia.myssm.core.diff.danielbechler.diff.node.DiffNode;

/**
 * @author Daniel Bechler
 */
public class ComparableComparisonStrategy implements ComparisonStrategy {
    public void compare(final DiffNode node, final Class<?> type, final Object working, final Object base) {
        if (Comparable.class.isAssignableFrom(type)) {
            if (isEqualByComparison((Comparable) working, (Comparable) base)) {
                node.setState(DiffNode.State.UNTOUCHED);
            } else {
                node.setState(DiffNode.State.CHANGED);
            }
        }
    }
}
