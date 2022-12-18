/*
 * Copyright 2016 Daniel Bechler
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

package com.tujia.myssm.core.diff.danielbechler.diff;

import java.util.Set;
import com.tujia.myssm.core.diff.danielbechler.diff.category.CategoryResolver;
import com.tujia.myssm.core.diff.danielbechler.diff.comparison.ComparisonStrategy;
import com.tujia.myssm.core.diff.danielbechler.diff.comparison.ComparisonStrategyResolver;
import com.tujia.myssm.core.diff.danielbechler.diff.comparison.PrimitiveDefaultValueMode;
import com.tujia.myssm.core.diff.danielbechler.diff.comparison.PrimitiveDefaultValueModeResolver;
import com.tujia.myssm.core.diff.danielbechler.diff.filtering.IsReturnableResolver;
import com.tujia.myssm.core.diff.danielbechler.diff.inclusion.IsIgnoredResolver;
import com.tujia.myssm.core.diff.danielbechler.diff.introspection.IsIntrospectableResolver;
import com.tujia.myssm.core.diff.danielbechler.diff.node.DiffNode;

class DefaultNodeQueryService implements NodeQueryService {
    private final CategoryResolver categoryResolver;
    private final IsIntrospectableResolver introspectableResolver;
    private final IsIgnoredResolver ignoredResolver;
    private final IsReturnableResolver returnableResolver;
    private final ComparisonStrategyResolver comparisonStrategyResolver;
    private final PrimitiveDefaultValueModeResolver primitiveDefaultValueModeResolver;

    public DefaultNodeQueryService(final CategoryResolver categoryResolver, final IsIntrospectableResolver introspectableResolver,
                                   final IsIgnoredResolver ignoredResolver, final IsReturnableResolver returnableResolver,
                                   final ComparisonStrategyResolver comparisonStrategyResolver,
                                   final PrimitiveDefaultValueModeResolver primitiveDefaultValueModeResolver) {
        this.categoryResolver = categoryResolver;
        this.introspectableResolver = introspectableResolver;
        this.ignoredResolver = ignoredResolver;
        this.returnableResolver = returnableResolver;
        this.comparisonStrategyResolver = comparisonStrategyResolver;
        this.primitiveDefaultValueModeResolver = primitiveDefaultValueModeResolver;
    }

    public Set<String> resolveCategories(final DiffNode node) {
        return categoryResolver.resolveCategories(node);
    }

    public boolean isIntrospectable(final DiffNode node) {
        return introspectableResolver.isIntrospectable(node);
    }

    public boolean isIgnored(final DiffNode node) {
        return ignoredResolver.isIgnored(node);
    }

    public boolean isReturnable(final DiffNode node) {
        return returnableResolver.isReturnable(node);
    }

    public ComparisonStrategy resolveComparisonStrategy(final DiffNode node) {
        return comparisonStrategyResolver.resolveComparisonStrategy(node);
    }

    public PrimitiveDefaultValueMode resolvePrimitiveDefaultValueMode(final DiffNode node) {
        return primitiveDefaultValueModeResolver.resolvePrimitiveDefaultValueMode(node);
    }
}
