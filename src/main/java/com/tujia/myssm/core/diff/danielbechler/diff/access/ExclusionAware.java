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

package com.tujia.myssm.core.diff.danielbechler.diff.access;

import com.tujia.myssm.core.diff.danielbechler.diff.inclusion.TypePropertyAnnotationInclusionResolver;

/**
 * @author Daniel Bechler
 * @see TypePropertyAnnotationInclusionResolver
 * @deprecated This was a shortcut to extract the "exclude" flag from the ObjectDiffProperty annotation. Since we found
 * a better way to do that, it is not needed anymore and will be removed in future versions.
 */
@Deprecated
public interface ExclusionAware {
    @Deprecated
    boolean isExcludedByAnnotation();
}
