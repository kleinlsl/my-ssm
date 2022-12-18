/*
 * Copyright 2015 Daniel Bechler
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

package com.tujia.myssm.core.diff.danielbechler.diff.identity;

import com.tujia.myssm.core.diff.danielbechler.diff.ObjectDifferBuilder;
import com.tujia.myssm.core.diff.danielbechler.diff.path.NodePath;

public interface IdentityConfigurer {
    /**
     * Allows to configure the way object identities are established between collection items.
     */
    OfCollectionItems ofCollectionItems(NodePath nodePath);

    /**
     * Allows to configure the way object identities are established between collection items.
     */
    OfCollectionItems ofCollectionItems(Class<?> type, String propertyName);

    IdentityConfigurer setDefaultCollectionItemIdentityStrategy(IdentityStrategy identityStrategy);

    ObjectDifferBuilder and();

    interface OfCollectionItems {
        IdentityConfigurer via(IdentityStrategy identityStrategy);
    }
}
